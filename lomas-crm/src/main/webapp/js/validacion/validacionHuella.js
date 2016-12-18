//Variables globales
		var htmlApplet=
			'<object id="applet" type="application/x-java-applet" height="0px" width="0px" >'+
			'<param name="MAYSCRIPT" value="true">'+
			'<param name="code" value="org.fousac.crm_clinicas.Principal"/>'+
			'<param name="codebase" value="/clinicas-crm/applet/codigo/">'+
			'<param name="archive" value="clinicas-crm.jar,dpfpenrollment.jar,dpfpverification.jar,dpotapi.jar,dpotjni.jar,commons-codec-1.9.jar">'+
			'<param name="separate_jvm" value="true" /> '+
			'Necesita instalar Java'+
			'</object>';
		var applet;
		var appletCargado=false;
		var estado=-1;
		
		
		//Document Ready
		$(function() {
			//Cargar las huellas previamente registradas
			$.post('/clinicas-crm/usuario', {op:15}, callbackCargaInicial, 'json');
			function callbackCargaInicial(respuesta){
				if(respuesta.data){
					respuesta.data.forEach(function(nodo){
						$('[data-indice='+nodo.dedo+']').css('background-color','rgba(0, 224, 255, 0.4)');
					});
				}
				//Verificar el estado
				$.post('/clinicas-crm/asistencia', {operacion:1}, callbackCargaEstado, 'json');
				function callbackCargaEstado(respuesta){
					estado=respuesta.estado;
					if(respuesta.estado==1){
						$("#btnMarcar").html('Marcar ingreso <i class="fa fa-sign-in"></i>');
						
					}
					else if(respuesta.estado==2){
						$("#btnMarcar").html('Marcar egreso <i class="fa fa-sign-out"></i>');
					}
					$("#btnMarcar").prop('disabled',false);
				}
			}
			//Cargar los marcajes
			table = $('#tabla').DataTable({
				"ajax": {	"url": "/clinicas-crm/asistencia",
					"dataType": "json",
	            	"data": function (d){ 
			         	d.operacion = 2;
			         },
							"type": "POST"},
				"columns": [
							
							{"data": "tipo"},
							{"data": "fecha_hora"},
							
							],
				"bFilter": false,
				"bDestroy": true,
				"pagingType": "bootstrap",
				"lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "Todos"]],
				"processing": true,
				"language": {
					"zeroRecords": "<div style='clear:left;'><center >No hay filas para mostrar</center></div>",
					"lengthMenu": "_MENU_&nbsp;&nbsp;Registros por página",
					"info": "Mostrando la página _PAGE_ de _PAGES_",
					"infoEmpty": "",
					"processing": "<div style='clear:left;'><center ><b>Procesando <i class='fa fa-spinner fa-spin'></i></b></center></div><br>",
					"infoFiltered": "",
					"paginate": {
						"next": "Siguiente",
						"previous": "Anterior"
					}
				},
				"ordering": false
				,
				"initComplete": function (settings, json) {
			
				}
			});
			
			
			
			
			
			
		});
		
		
		
		//Configuración del plugin messenger
		Messenger.options = {extraClasses : 'messenger-fixed messenger-on-top',theme : 'future'};
			
		function obtenerAdvertencia(clase, mensaje) {
		 	Messenger().post({
	        	message: '<center>'+mensaje+'</center>',
	        	type: clase,
	        	showCloseButton: true
	    });

		}
		
		$('#btnMarcar').click(function(){
			
			if(!appletCargado){
				$('body').append(htmlApplet);
				applet=document.getElementById('applet');
				appletCargado=true;
			}
			
			try{
			applet.detectarLectores();
			
			}
			catch(error){
				
				obtenerAdvertencia("error", "Debe aceptar los permisos para continuar");
				$(applet=document.getElementById('applet')).remove();
				appletCargado=false;
			}
		});
		
		function sinDispositivo(){
			msg = Messenger().post({
				  message: "No se detectó ningún lector",
				  type:'error',
				  id:1,
				  hideAfter:3600,
				  actions: {
				    reintentar: {
				      label: "Reintentar",
				      action: function(){
				        applet.detectarLectores();
				        msg.hide();
				      }
				    }
					//adicionar botones
				  }
				});
			
		}
		
		
		
		//Ya está listo para validar
		function dispositivoListo(){
		msg = Messenger().post({
			  message: "¿Desea continuar con el marcaje?",
			  type:'info',
			  showCloseButton:true,
			  id:1,
			  hideAfter:3600,
			  actions: {
			    aceptar: {
			      label: "Aceptar",
			      action: function(){
			        Messenger().post({message: "Coloque el dedo para realizar el marcaje",type: "info",id:1});
			        applet.verificar();
			      }
			    },
			    cancelar: {
				      label: "Cancelar",
				      action: function(){
				        
				        msg.hide();
				      }
				    }
				//adicionar botones
			  }
			});
	}


		//llamar al servicio
		function verificar(bytes){
			
			$('#btnMarcar').prop('disabled',true);
			$('#btnMarcar').html('Espere <i class="fa fa-spinner fa-spin"></i>');
			$.post('/clinicas-crm/asistencia', {huella:bytes,operacion:3,tipo:estado}, callbackVerificar, 'json');
			function callbackVerificar(respuesta){
			
				$('#btnMarcar').prop('disabled',false);
				switch(respuesta.resultado){
				case 1:
					if(estado==1){
						$('#btnMarcar').html('Marcar egreso <i class="fa fa-sign-out"></i>');
						estado=2;}
					else
						{$('#btnMarcar').html('Marcar ingreso <i class="fa fa-sign-in"></i>');
						estado=1;}
					table.ajax.reload();
					obtenerAdvertencia(respuesta.clase,respuesta.descripcion,1);
					break;
				case -1:
					obtenerAdvertencia(respuesta.clase,respuesta.descripcion,1);
					if(estado==2)
						$('#btnMarcar').html('Marcar egreso <i class="fa fa-sign-out"></i>');
					else
						$('#btnMarcar').html('Marcar ingreso <i class="fa fa-sign-in"></i>');
					break;
					
				}
			
				
			
			}
		}
		
var estado=-1;
		
		
		//Document Ready
		$(function() {
			//Verificar el estado del docente para asi saber qué mostrar en el botón
			$.post('/clinicas-crm/asistencia', {operacion:1}, callbackCargaEstado, 'json');
				
			//Ocultar la tabla de huellas
			$('#tabla-dactilar').html('<img src="/clinicas-crm/img/registro.png">').css('text-align','center');

			//Cargar los marcajes del día
			table = $('#tabla').DataTable(parametrosTabla);
			
			
			
			
			
			
		});
		
		
		
		//Configuración del plugin messenger
		
			
		function obtenerAdvertencia(clase, mensaje) {
		 	Messenger().post({
	        	message:'<center>'+ mensaje+'</center>',
	        	type: clase,
	        	showCloseButton: true
	    });

		}
		
		$('#btnMarcar').click(realizarMarcaje);
		
		function realizarMarcaje(){
			$('#btnMarcar').prop('disabled',true).html('Espere <i class="fa fa-spinner fa-spin"></i>');
			$.post('/clinicas-crm/asistencia', {operacion:6,tipo:estado}, callbackVerificar, 'json');
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
		
		
		
		//Funciones completadas
		Messenger.options = {extraClasses : 'messenger-fixed messenger-on-top',theme : 'future'};
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
		
		var parametrosTabla={
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
			};
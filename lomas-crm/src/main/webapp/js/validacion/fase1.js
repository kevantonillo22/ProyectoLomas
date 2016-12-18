


var htmlApplet=
	'<object id="applet" type="application/x-java-applet" height="0px" width="0px" >'+
	'<param name="MAYSCRIPT" value="true">'+
	'<param name="code" value="org.fousac.crm_clinicas.Principal"/>'+
	'<param name="codebase" value="/clinicas-crm/applet/codigo/">'+
	'<param name="archive" value="clinicas-crm.jar,dpfpenrollment.jar,dpfpverification.jar,dpotapi.jar,dpotjni.jar,commons-codec-1.9.jar">'+
	'<param name="separate_jvm" value="true" /> '+
	'Necesita instalar Java'+
	'</object>';

//Variables para la inicializacion del applet
var applet;
var appletCargado=false;
var tratamientos;

$('#txt-comentario').prop('disabled',false);

$('#btn-cerrar').click(
		function(){
			$('#modalVerificacion').modal('hide');
			Messenger().hideAll();
			
		}

);

function sinDispositivo(){
	msg = Messenger().post({
		  message: "<center>No se detectó ningún lector</center>",
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

//llamar al servicio
function verificar(bytes){
	$('#btn-verificar').prop('disabled',true);
	$('#btn-verificar').html('Espere <i class="fa fa-spinner fa-spin"></i>');
	$.post('/clinicas-crm/ficha_clinica', {tratamientos:JSON.stringify(tratamientos),numero_fase:numero_fase,huella:bytes,operacion:16,ficha:ficha,estado:$('#select-verificacion').val(),descripcion:$('#txt-comentario').val()}, callbackVerificar, 'json');
	
	function callbackVerificar(respuesta){
		Messenger().hideAll();
		$('#btnGuardar').prop('disabled',false);
		$('#modalVerificacion').modal('hide');
		switch(respuesta.resultado){
		case 1:
			$('#btnGuardar').html('Fase validada');
			window.setTimeout(function(){
		        window.location.href = "/clinicas-crm/profesor/listarRevisionesProfesor.jsp";
		    }, 5000);
			$('#btnGuardar').unbind('click');
			$('#btnGuardar').removeClass('btn-default');
			$('#btnGuardar').addClass('btn-success');
			
			obtenerAdvertencia(respuesta.clase,'<center>'+respuesta.descripcion+'</center>',1);
			break;
		case -1:
			obtenerAdvertencia(respuesta.clase,'<center>'+respuesta.descripcion+'</center>',1);
			$('#btnGuardar').html('Validar fase');
			$('#btn-verificar').html('Guardar');
			$('#btn-verificar').prop('disabled',false);
			break;
			default:
			
			
		}
	
		
	
	}
}


//Ya está listo para validar
function dispositivoListo(){
	msg = Messenger().post({
		  message: "<center>La fase se marcará como <b>"+$("#select-verificacion option:selected").text()+"</b>. Presione en \"Aceptar\" para continuar</center>",
		  type:'info',
		  showCloseButton:true,
		  id:1,
		  hideAfter:3600,
		  actions: {
		    aceptar: {
		      label: "Aceptar",
		      action: function(){
		    	$('#btn-verificar').prop('disabled',true);  
		        Messenger().post({message: "<center>Coloque el dedo para validar la fase</center>",type: "info",id:1,hideAfter:3600});
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


//Cargar la firma de la fase
$('#btnGuardar').attr('onclick','').unbind('click');
$('#btnGuardar').unbind('click');
$('#btnGuardar').html('Calificar fase');
$('#btnGuardar').click(function(){
	
	if(numero_fase<6)
		$('#modalVerificacion').modal('show');
	else	{
		tratamientos=[];
		$('.registro').each(
				function(){
					var nodo={};
					nodo.identificador=$(this).data('identificador');
					nodo.eps=$(this).find('.eps').prop('checked');
					nodo.vobo=$(this).find('.vobo').prop('checked');
					console.log(nodo);
					tratamientos.push(nodo);
				}
		);
		//Verificar la fse 6
		realizarVerificacion();
    
	}
	
	/**/
});

$('#btn-verificar').click(realizarVerificacion);


function realizarVerificacion(){
	if($('#select-verificacion').val()==0){
		obtenerAdvertencia('error','<center>Debe calificar la fase para poder continuar. Seleccione entre "Aprobada" o "Reprobada"</center>');
	}else{
		
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
		
		
	}
}




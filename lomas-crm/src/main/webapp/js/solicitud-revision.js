var _botonSolicitud;

$(function(){
	_botonSolicitud=$("#btn-solicitar-revision")
	
})

function deshabilitarBotonRevision(){
	_botonSolicitud.unbind('click');
	_botonSolicitud.click(function(e){e.stopPropagation();console.log('espere...');});
}

function habilitarBotonRevision(){
	$("#btn-solicitar-revision").click(solicitarRevision);
}
function solicitarRevision(e){
	e.stopPropagation();
	_botonSolicitud.html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Espere <i class="fa fa-spinner fa-spin"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
	deshabilitarBotonRevision();
	$.post('/clinicas-crm/ficha_clinica?operacion=13',{numero_fase:numero_fase,ficha_clinica:ficha},callbackSolicitud,'json');
			}

function callbackSolicitud(respuesta){
	_botonSolicitud.css('display','inline');
	_botonSolicitud.html('<i class="fa fa-flash fa-fw"></i>Solicitar Revisión');			
	switch(respuesta.resultado){
		case 1:
			bloquearFase();
			$('#btnGuardar').text('Pendiente de revisión');
			obtenerAdvertencia(respuesta.clase, '<center>'+ respuesta.descripcion+'</center>');
			_botonSolicitud.slideToggle();
			break;
		case -100:
			
			break;	
			default:
				obtenerAdvertencia(respuesta.clase, '<center>'+ respuesta.descripcion+'</center>');
				habilitarBotonRevision();
	}
}

function notificarSesionExpirada(){
	$('#modal-error').modal({"backdrop":"static","keyboard":false});
	$('#modalTitulo').text('Sesión expirada');
	$('#modalBody').html('La sesión ha expirado. Debe iniciar sesión para continuar trabajando');
	$('#modalFooter').html('<a href="/clinicas-crm/ingreso.jsp"><button type="button" class="btn btn-primary">Iniciar sesión</button></a>');
}

function notificarError(){
	$('#modal-error').modal({"backdrop":"static","keyboard":false});
	$('#modalTitulo').text('Error');
	$('#modalBody').html('No se pudo cargar la información solicitada');
	$('#modalFooter').html('<a href="/clinicas-crm/index.jsp"><button type="button" class="btn btn-primary">Regresar</button></a>');
}

function callbackGuardarFase(respuesta){
	switch(respuesta.resultado) {
	    case 1:
	    	obtenerAdvertencia('info','<center>'+respuesta.descripcion+'</center>');
	        break;
	    case -100:
	    	notificarSesionExpirada();
	        break;
	    case -101:
		    //No tiene permisos
		    window.location.href = "/clinicas-crm/errorpermisos.jsp";
	        break;
	    default:
	    	obtenerAdvertencia('error', respuesta.descripcion);    
	}
	_habilitarControles();
}

/*
 * Ayuda a cambiar la UI dependiendo de el estado de la fase y de quién la esté visualizando
 * */
function validarEstadoFase(respuesta){
	if(rol==-2)
		switch(respuesta.estado){
		
		case 1:
			//Se puede solicitar una revisión
			$('#btn-solicitar-revision').css('display','inline');
			$("#btn-solicitar-revision").click(solicitarRevision);
			$("#btnGuardar").html('Guardar');
		break;
		case 3:
			bloquearFase();
			$('#btnGuardar').text(respuesta.estado_descripcion);
			editable=false;
			break;
		case 4:
			//Fase pendiente de validación
			bloquearFase();
			$('#btnGuardar').text(respuesta.estado_descripcion);
			editable=false;
			break;
		
		}
		else{
		//Validaciones profesores
		switch(respuesta.estado){
		case 4:
			//Fase pendiente de validación
			editable=false;
			bloquearFase();
			$('#btnGuardar').prop('disabled',false);
			break;
		}
			var src;
		if(valida_huella==0)
			src="/clinicas-crm/js/validacion/fase-teclado.js";
		else
			src="/clinicas-crm/js/validacion/fase1.js";
		$.getScript(src);
		
		}
}



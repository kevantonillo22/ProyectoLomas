/*
 * Funcionalidad exclusiva de la fase 4
 * */

/*
 * Establecer fase. Es el primer método que se llama al cargar la fase
 * */
$(inicializarFase);
$('#btnGuardar').click(guardarFase);
function inicializarFase(){
	Messenger.options = {extraClasses : 'messenger-fixed messenger-on-top',theme : 'future'};
	_deshabilitarControles();
	//Llamada al servidor
	var parametros={};
	parametros.operacion=6;
	parametros.ficha= ficha;
	$.post('/clinicas-crm/ficha_clinica',parametros,callbackCargar,'json');
	
}


function callbackCargar(respuesta){
	console.log(respuesta);
	switch(respuesta.resultado) {
		case 1:
			_habilitarControles();
			restaurarFase(respuesta);
			validarEstadoFase(respuesta);
			break;
		case -102:
			notificarError();
			break;
		default:
  
	}
}


function bloquearFase(){
	_deshabilitarControles();
	$('#btnGuardar').unbind('click');
	$('#btnGuardar').removeClass('btn-default');
	$('#btnGuardar').addClass('btn-success');
}

function restaurarFase(respuesta){
	$('#textArea1').val(respuesta.fd_cara_cuello);
	$('#textArea2').val(respuesta.fd_tejidos_blandos);
	$('#textArea3').val(respuesta.fd_tejidos_duros);
	$('#textArea4').val(respuesta.fd_temp_mandibular);
	$('#textArea5').val(respuesta.fd_oclusion_descripcion);
	$("#inpt1").val(respuesta.fd_pulsaciones_minuto);
	$("#inpt2").val(respuesta.fd_presion_arterial);
	$("#inpt4").val(respuesta.fd_capacidad_respiratoria);
	$("#inpt3").val(respuesta.fd_frecuencia_respiratoria);
	$("#"+respuesta.fd_oclusion_opcion).prop('checked',true);
}

function guardarFase(){
	_deshabilitarControles();
	//Preparar los parámetros
	var parametros={};
	parametros.operacion=5;
	parametros.fd_cara_cuello=$('#textArea1').val();
	parametros.fd_tejidos_blandos=$('#textArea2').val();
	parametros.fd_tejidos_duros=$('#textArea3').val();
	parametros.fd_temp_mandibular=$('#textArea4').val();
	parametros.fd_oclusion_descripcion=$('#textArea5').val();
	parametros.fd_oclusion_opcion=$( "input:checked").first().attr('id');
	parametros.fd_pulsaciones_minuto=$("#inpt1").val();
	parametros.fd_presion_arterial=$("#inpt2").val();
	parametros.fd_capacidad_respiratoria=$("#inpt4").val();
	parametros.fd_frecuencia_respiratoria=$("#inpt3").val();
	parametros.fc_ficha_clinica= ficha;
	//Llamada al server
	$.post('/clinicas-crm/ficha_clinica',parametros,callbackGuardarFase,'json');
}



//No se puede editar nada de la fase
var _controles=$('input,textarea,button').not('#btn-verificar,#btn-cerrar');

function _deshabilitarControles(){
	$("#btnGuardar").prop("disabled", true).html('Espere <i class="fa fa-spinner fa-spin"></i>');
	_controles.not('#select-verificacion').prop('disabled',true);
}

//Habilitar la edición de la fase
function _habilitarControles(){
	$("#btnGuardar").prop("disabled", false).html('Guardar');
	_controles.prop('disabled',false)
}











/******************************
	Guardar la informacion
**********************************/





function obtenerAdvertencia(clase, mensaje) {
	Messenger().post({message: mensaje,type: clase,showCloseButton: true});
}


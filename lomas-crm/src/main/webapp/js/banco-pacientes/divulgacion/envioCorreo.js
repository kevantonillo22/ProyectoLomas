var btnEnviar;
var opcionConfiguracion;
var txtAsunto;
var wrapAsunto;
var txtMensaje;
var wrapMensaje;

var chkAnio5;
var chkAnio4;
var chkPRC;
var wrapCheckbox;

var correos;

var labelAdvertenciaPrincipal;

var modalConfirmacionEnvio;
var btnConfirmacionEnvio;
var labelAdvertenciaConfirmacion;
var listaRemitentes;

var modalConfiguracion;
var txtCorreoConfiguracion;
var txtPassConfiguracion;
var btnConfiguracion;
var configuracionWrapCorreo;
var configuracionWrapPass;
var txtIdConfiguracion;

$(inicializar);

function inicializar(){
	//Inicializar componentes graficos
	opcionConfiguracion = $('#btn-configuracion');
	btnEnviar = $('#btn-enviar');
	txtAsunto = $('#txtAsunto');
	wrapAsunto = $('#wrapAsunto');
	txtMensaje = CKEDITOR.instances.txtCuerpoMensaje;
	wrapMensaje = $('#wrapCuerpoMensaje');
	wrapCheckbox = $('#wrapCheckbox');
	chkAnio5 = $('#chkAnio5');
	chkAnio4 = $('#chkAnio4');
	chkPRC = $('#chkPRC');


	labelAdvertenciaPrincipal = $('#lblAdvertencia');
	
	modalConfirmacionEnvio = $('#confirmacion-envio');
	labelAdvertenciaConfirmacion = $('#confirmacion-envio #lblAdvertencia');
	btnConfirmacionEnvio = $('#confirmacion-envio #btn-guardar');
	listaRemitentes = $('#confirmacion-envio #lista-remitentes');
	
	modalConfiguracion = $('#configuracion');
	txtCorreoConfiguracion = $('#configuracion #txtCorreo');
	txtPassConfiguracion = $('#configuracion #txtPass');
	txtIdConfiguracion = $('#configuracion #txtIdConfiguracion');
	configuracionWrapPass = $('#configuracion #wrapPass');
	configuracionWrapCorreo = $('#configuracion #wrapCorreo');
	btnConfiguracion = $('#configuracion #btn-guardar');
	labelAdvertenciaConfiguracion = $('#configuracion #lblAdvertencia');
	
	btnEnviar.click(function(){listarCorreo();});
	
	btnConfirmacionEnvio.click(function(){enviarCorreo();});
	
	btnConfiguracion.click(function(){guardarConfiguracion();});
	
	opcionConfiguracion.click(function(){UIConfiguracion();});
}

var txt_btn_enviar;
function listarCorreo(){
	var boton = btnEnviar;
	var label = labelAdvertenciaPrincipal;
	var errores = false;
	label.html('');
	
	//validar Asunto
	if(txtAsunto.val().trim()){
		wrapAsunto.removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Ingrese un asunto');
		label.html(advertencia);
		wrapAsunto.addClass("has-error");
		errores = true;
	}
	
	//validar Mensaje
	var text = txtMensaje.getData();
	if(text.trim()){
		wrapMensaje.removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'El mensaje no puede ir vacío');
		label.html(advertencia);
		wrapMensaje.addClass("has-error");
		errores = true;
	}
	
	//validar destinatarios
	if(chkAnio5.is(':checked') || chkAnio4.is(':checked') || chkPRC.is(':checked')){
		wrapCheckbox.removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe de seleccionar por lo menos un remitente');
		label.html(advertencia);
		wrapCheckbox.addClass("has-error");
		errores = true;
	}
	
	if(!errores){
		txt_btn_enviar = boton.html();
		boton.html(' Espere <i class="fa fa-spinner fa-spin"></i>');
		boton.prop("disabled", true);
		
		//preparamos parametros
		var parametros = {};
		parametros.operacion = 1;
		if(chkAnio5.is(':checked'))		{parametros.anio5 = true;}else{parametros.anio5 = false;}
		if(chkAnio4.is(':checked'))		{parametros.anio4 = true;}else{parametros.anio4 = false;}
		if(chkPRC.is(':checked'))		{parametros.prc = true;}else{parametros.prc = false;}
		
		$.post('/clinicas-crm/correo',parametros,callbackListarCorreo,'json');
	}
}




function callbackListarCorreo(response){
	
	var tiempo = 1600;
	var label = labelAdvertenciaPrincipal;
	var boton = btnEnviar;
	boton.html(txt_btn_enviar);
	boton.prop("disabled", false);
	switch(response.resultado){
	
		case -1:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			desplegarMensaje("error", response.descripcion, tiempo);
			
			break;
			
		case 1:
			//limpio la vista de contenido viejo
			listaRemitentes.empty();
			//se setea la variable global
			correos = response.data;
			console.log(response.data);
			//imprimimos una tabla de los destinatarios
			for(var i = 0; i <= response.data.length - 1; i++) {
				var y = i+1;
				listaRemitentes.append('<li class="list-group-item"><span style="color:#2980B9;font-weight:bolder;">' + y + '-&nbsp;</span>' + response.data[i].correo.toLowerCase() + '</li>');
			}
			
			//imprimios el numero de destinatarios
			$('#destinatarios .badge').html(response.data.length);
			//mostramos modal de confirmacion
			modalConfirmacionEnvio.modal();
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia)
			desplegarMensaje("error", response.descripcion, tiempo);
			break;
			
		case -200:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			desplegarMensaje("error", response.descripcion, tiempo);
			break;
	}	
}


function enviarCorreo(){
	var boton = btnConfirmacionEnvio;
	boton.html(' Espere <i class="fa fa-spinner fa-spin"></i>');
	boton.prop("disabled", true);
	
	//se genera el string separado por puntos y comas
	var cor = "";
	for(var i = 0; i <= correos.length - 1; i++) {
		if(i == 0){
			cor = cor + correos[i].correo.toLowerCase();
		}else{
			cor = cor + "%" + correos[i].correo.toLowerCase();
		}
		
	}
	
	var parametros = {};
	parametros.operacion = 2;
	parametros.correos = cor;
	parametros.asunto = txtAsunto.val();
	var mensaje = txtMensaje.getData().replace('"', "");
	mensaje = mensaje.replace("'", "");
	parametros.mensaje = mensaje;
	
	console.log(parametros);
	
	$.post('/clinicas-crm/correo',parametros,callbackEnviarCorreo,'json');
}


function callbackEnviarCorreo(response){
	modalConfirmacionEnvio.modal('hide');
	
	var tiempo = 3600;
	var label = labelAdvertenciaPrincipal;
	var boton = btnConfirmacionEnvio;
	switch(response.resultado){
	
		case -1:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			desplegarMensaje("error", response.descripcion, tiempo);
			boton.html(' Enviar');
			boton.prop("disabled", false);
			break;
			
		case 1:
			boton.html(' Enviar');
			boton.prop("disabled", false);
			desplegarMensaje('success', 'Correos enviados correctamente', tiempo);
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			boton.html(' Enviar');
			desplegarMensaje("error", response.descripcion, tiempo);
			break;
			
		case -200:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			boton.html(' Enviar');
			desplegarMensaje("error", response.descripcion, tiempo);
			break;
	}	
}


function UIConfiguracion(){
	limpiarFormConfiguracion();
	bloquearFormConfiguracion();
	modalConfiguracion.modal();
	//txtIdConfiguracion.val(valores.identificador);
	labelAdvertenciaConfiguracion.html('');
	btnConfiguracion.html(' Espere <i class="fa fa-spinner fa-spin"></i>');
	btnConfiguracion.prop("disabled", true);
	
	//Lista el elemento
	var parametros = {};
	parametros.operacion = 3;
	parametros.id = txtIdConfiguracion.val();
	console.log(parametros);
	$.post('/clinicas-crm/citas',parametros,callbackUIConfiguracion,'json');
}

function callbackUIConfiguracion(response){
	var tiempo = 1600;
	var label = labelAdvertenciaConfiguracion;
	var boton = btnConfiguracion;
	switch(response.resultado){
	
		case -1:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			boton.html(' Guardar');
			desplegarMensaje("error", response.descripcion, 30);
			break;
			
		case 1:
			desbloquearFormConfiguracion();
			boton.html(' Guardar');
			boton.prop("disabled", false);
			setValuesInputsConfiguracion(response.data);
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			boton.html(' Guardar');
			desplegarMensaje("error", response.descripcion, 30);
			break;
			
		case -200:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			boton.html(' Guardar');
			desplegarMensaje("error", response.descripcion, 30);
			break;
	
	}
}


function guardarConfiguracion(){
	var errores = false;
	
	//validar id configuracion
	if(txtIdConfiguracion.val().trim()){
		personalesWrapIngresos.removeClass("has-error");
	}else{
		personalesIngresos.val(0);
	}
	
	//validar ID
	if(txtIdConfiguracion.val().trim()){
		//configuracionWrapNombre.removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Hubo un problema por favor recargue la página y vuelva a intentarlo');
		labelAdvertenciaConfiguracion.html(advertencia);
		errores = true;
	}
	
	//validar correo
	if(txtCorreoConfiguracion.val().trim()){
		configuracionWrapCorreo.removeClass("has-error");
		if(validateEmail(txtCorreoConfiguracion.val().trim())){
			configuracionWrapCorreo.removeClass("has-error");
		}else{
			var advertencia = obtenerAdvertencia(' alert-danger', 'Ingrese un email válido');
			labelAdvertenciaConfiguracion.html(advertencia);
			onfiguracionWrapCorreo.addClass("has-error");
			errores = true;
		}
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'El correo no puede ir vacio');
		labelAdvertenciaConfiguracion.html(advertencia);
		configuracionWrapCorreo.addClass("has-error");
		errores = true;
	}
	
	//validar Pass
	if(txtPassConfiguracion.val().trim()){
		configuracionWrapPass.removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'La contraseña no puede ir vacía');
		labelAdvertenciaConfiguracion.html(advertencia);
		configuracionWrapPass.addClass("has-error");
		errores = true;
	}
	
	if(!false){
		var parametros ={};
		parametros.operacion = 4;
		parametros.id = txtIdConfiguracion.val();
		parametros.correo = txtCorreoConfiguracion.val();
		parametros.pass = txtPassConfiguracion.val();
		
		$.post('/clinicas-crm/citas',parametros,callbackGuardarConfiguracion,'json');
	}
}

function callbackGuardarConfiguracion(response){
	var boton = btnGuardarConfiguracion;
	btnGuardarConfiguracion.html(' Guardar');
	btnGuardarConfiguracion.prop("disabled", false);
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion);
			break;
			
		case 1:
			desplegarMensaje("success", "Se guardaron los datos exitosamente");
			//refrescamos la tabla
			//table.ajax.reload( null, false );
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			desplegarMensaje("error", response.descripcion);
			break;
			
		case -200:
			desplegarMensaje("error", response.descripcion);
	}
}




function limpiarFormConfiguracion(){
	$('#configuracion div').removeClass("has-error");
	txtIdConfiguracion.val('');
	txtCorreoConfiguracion.val('');
	txtPassConfiguracion.val('');
}

function bloquearFormConfiguracion(){
	$('#configuracion form input, #configuracion form select').prop("disabled", true);
}

function desbloquearFormConfiguracion(){
	$('#configuracion form input, #configuracion form select').prop("disabled", false);
}

function setValuesInputsConfiguracion(response){
	txtIdConfiguracion.val(response.id);
	txtCorreoConfiguracion.val(response.correo);
	txtPassConfiguracion.val(response.pass);
}

/*********************************************************************
 * @author kcardona
 * @since 27/09/2015
 * @return String
 * @Descripcion 
 ********************************************************************/
function obtenerAdvertencia(clase, mensaje) {
	return '<div class="alert ' + clase + '">' + mensaje + '</div>';
}

function desplegarMensaje(clase, mensaje, time) {
	Messenger.options = {
	    extraClasses: 'messenger-fixed messenger-on-top messenger-on-center',
	}
	Messenger().post({message:'<center>'+ mensaje+'</center>',type: clase,showCloseButton: true, hideAfter:time});
}

function validateEmail(sEmail) {
    var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    if (filter.test(sEmail)) {
        return true;
    }
    else {
        return false;
    }
}


function permite(elEvento, permitidos) {
	// Variables que definen los caracteres permitidos
	var numeros = "0123456789.";
	var solo_num = "0123456789";
	var caracteres = " abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ:-;'_";
	var numeros_caracteres = numeros + caracteres;
	var teclas_especiales = [8];
	// 8 = BackSpace, 46 = Supr, 37 = flecha izquierda, 39 = flecha derecha
				
	// Seleccionar los caracteres a partir del parámetro de la función
	switch(permitidos) {
	  case 'solo_num':
		  permitidos = solo_num;
		  break;
	  case 'num':
	    permitidos = numeros;
	    break;
	  case 'car':
	    permitidos = caracteres;
	    break;
	  case 'num_car':
	    permitidos = numeros_caracteres;
	    break;
	}
				 // Obtener la tecla pulsada 
	var evento = elEvento || window.event;
	var codigoCaracter = evento.charCode || evento.keyCode;
	var caracter = String.fromCharCode(codigoCaracter);
				 // Comprobar si la tecla pulsada es alguna de las teclas especiales
	// (teclas de borrado y flechas horizontales)
	var tecla_especial = false;
	for(var i in teclas_especiales) {
	  if(codigoCaracter == teclas_especiales[i]) {
	    tecla_especial = true;
	    break;
	  }
	}
	
	 // Comprobar si la tecla pulsada se encuentra en los caracteres permitidos
	 // o si es una tecla especial
	//console.log(permitidos.indexOf(caracter));
	//console.log(tecla_especial);
	//console.log(codigoCaracter);
	return permitidos.indexOf(caracter) != -1 || tecla_especial;
}
var btnPublicar;
var txtPublicacion;
var wrapPublicacion;


$(inicializar);

function inicializar(){
	//Inicializar componentes graficos
	btnPublicar = $('#btn-publicar');
	txtPublicacion = $('#txtPublicacion');
	wrapPublicacion = $('#wrapPublicacion'); 	
	
}




/*********************************************************************
 * @author kcardona
 * @since 25/09/2015
 * @return String
 * @Descripcion Devuelve una cadena en la cual se imprime los botones
 * de opciones de cada tupla de la tabla principal.
 ********************************************************************/

function desplegarMensaje(clase, mensaje, time) {
	Messenger.options = {
	    extraClasses: 'messenger-fixed messenger-on-top messenger-on-center',
	}
	Messenger().post({message:'<center>'+ mensaje+'</center>',type: clase,showCloseButton: true, hideAfter:time});
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
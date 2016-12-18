
$('.dropdown-menu a').click(function(e) {
    e.stopPropagation();
});

$( window ).resize(_responsiveTabs);
$(_responsiveTabs);
function _responsiveTabs(){
	_anchoVentana=$(window).width();
	if(_anchoVentana<1069){
		$('#myTabDrop1').append($('#myTab li').not('.dropdown').detach());
		$('#tab-dropdown').show();
		
	}
	else{
		$('#myTab').prepend($('#myTabDrop1 li').detach());
		$('#tab-dropdown').hide();
		
	}
	
}



function permite(elEvento, permitidos) {
	// Variables que definen los caracteres permitidos
	var numeros = "0123456789.";
	var solo_num = "0123456789";
	var caracteres = " abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ:-;'_";
	var email = "abcdefghijklmnñopqrstuvwxyz_.@0123456789";
	var numeros_caracteres = numeros + caracteres;
	var teclas_especiales = [8,46,37,39];
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
	  case 'email':
		    permitidos = email;
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



/*********************************************************************
 * @author kcardona
 * @since 10/09/2015
 * @return String
 * @Descripcion Devuelve una cadena en la cual se imprime los botones
 * de opciones de cada tupla de la tabla principal.
 ********************************************************************/

function desplegarMensaje(clase, mensaje, time) {
	Messenger.options = {
	    extraClasses: 'messenger-fixed messenger-on-top messenger-on-right',
	    theme: 'future'
	}
	Messenger().post({message:'<center>'+ mensaje+'</center>',type: clase,showCloseButton: true, hideAfter:time});
}


function confirmacionMensaje(parametros){
	Messenger.options = {
		    extraClasses: 'messenger-fixed messenger-on-top messenger-on-center',
		}
	var mensajito=Messenger().post({
		extraClasses: 'messenger-fixed messenger-on-center messenger-on-top',
		  message: "<center>¿Desea guardar los cambios?</center>",
		  type:'info',
		  id:1,
		  hideAfter:36000,
		  actions: {
		  	aceptar: {
		 	label: "Aceptar",
		   	action: function(){
		   			parametros.accept();
		   			mensajito.hide();
		   		}
		    },
		   	cancelar: {
			 	label: "Cancelar",
			   	action: function(){
			   		parametros.cancel();
			   		mensajito.hide();
				}
			}
			//adicionar botones
		  }
	});
}
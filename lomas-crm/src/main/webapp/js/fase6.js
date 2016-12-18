/*
 * Funcionalidad exclusiva de la fase 4
 * */

/*
 * Establecer fase. Es el primer método que se llama al cargar la fase
 * */
function inicializarFase(){
	//Boton
	//$("#btnGuardar").prop("disabled", true);
	//$("#btnGuardar").html('Espere <i class="fa fa-spinner fa-spin"></i>');
	//Inicializar Messenger
	//Messenger.options = { extraClasses : 'messenger-fixed messenger-on-top', theme : 'future'};
	//Inicializar el lienzo de dibujo
	init();
	//Odontograma deshabilitado
	$('.pieza').addClass('pieza-palida');
	//Animar el boton que muestra/oculta el mapa
	$('#boton-mapa').click(function(){ $('#mapa,#marco').fadeToggle();}).hide();
	
		
	//Llamada al servidor para restaurar la fase
	var parametros={};
	parametros.ficha=ficha;
	//parametros.denticion=1;
	parametros.operacion=18;
	parametros.fase=numero_fase;
	$.post('/clinicas-crm/ficha_clinica',parametros,callbackCargar,'json');	
		
	
}

function callbackCargar(respuesta){		
	//Restaurar diagnostico
	console.log(respuesta);
	$("#btnGuardar").prop("disabled", false);
	switch(respuesta.resultado) {
    	case 1:	
    		//Determinar que se hará a partir del estado de la fase
    		validarEstadoFase(respuesta);estado=respuesta.estado;
    		//Restaurar la fase
    		restaurarFase(respuesta);
    		break;	
		}
}

/*
 * Esta función, teniendo como parámetro el objeto respuesta, procede a restaurar toda la información de la fase 4
 * */

function restaurarFase(respuesta){
	//Terminar con la animación de carga
	$('#barra-progreso').slideUp('slow');
	$('.pieza').toggleClass('pieza-normal pieza-palida');
	
	//Restaurar el diagnóstico
	$('#textArea').val(respuesta.diagnostico_especifico);
	if(respuesta.diagnostico>=0)
		try{$('.radio').find('input')[parseInt(respuesta.diagnostico)].checked=true;}
		catch(err){}
		
	
	
	//Restaurar imágenes
	respuesta.imagenes.forEach(
		function(imagen){
			agregarDibujo($('[data-id="'+imagen.pieza+'"]'),imagen.nombre,imagen.miniatura);
		}		
	);
	
	//Restaurar matriz periodontal
	var _matrices=respuesta.matriz.reverse();
	for (var i=_matrices.length; i--; ) {
		var _decidua=true;
		if(_matrices[i].matriz>1)
			_decidua=false;
		agregarFilasMatriz(true,_matrices[i].id,_decidua,_matrices[i].matriz,_matrices[i].fila);
		
	}
	
	//Habilitar menú de cada pieza y funcionalidades para su edición
	if(editable){
		$('.pieza').popoverClosable(opcionesPopOver);
		$('.pieza').click(clickPieza);
	}

}


function bloquearFase(){
	//Deshabilitar popover
	$('.cuadro,.pieza').popover('disable');
	$('input,textarea').prop('disabled',true);
	$('.boton-eliminar').remove();
	$('.pieza').unbind('click');
	$('.pieza').prop('onclick','');
	$('#btnGuardar')
		.attr('onclick','')
		.unbind('click')
		.removeClass('btn-default')
		.addClass('btn-success');
}








$(inicializarFase);




var impresionCargada2=false;
var modalCargado2=false;


function ocultarVistaImpresion2(){
	
	$('#frame-impresion-datos').fadeOut(function(){$("#modal-impresion-datos").modal('hide');});
}

function cargarVistaImpresion2(data_general){
	//Preparar data
	
	
	//Añadir el modal
	if(!modalCargado2){
		$('body').append(modal2);
		modalCargado2=true;
		
	}
		
	//$('#frame-cargando-datos').show();	
	//Mostrar el modal
	$("#modal-impresion-datos").modal('show');
	if(impresionCargada2)
		$('#frame-impresion-datos').fadeIn();
	
	$('#frame-cargando-datos').show()
	$('#frame-impresion-datos').load(function(){
		$('#frame-cargando-datos').hide();
		$('#frame-impresion-datos').fadeIn();
	});
	
	//Cargar la página de impresión
	if(!impresionCargada2)
	{
		var parametros_general = 
		'numero=' + data_general.numero  +
		'&id=' + data_general.id +
		'&lugar=' + data_general.lugar +
		'&fecha=' + data_general.fecha+
		'&nombre=' + data_general.nombre+
		'&cantidad=' + data_general.cantidad +
		'&motivo=' + data_general.motivo+ 
		'&imagen=' + data_general.imagen;
		
		
		$('#frame-impresion-datos').hide();
		$('#frame-impresion-datos').attr('src', '../cuentas/impresionDatosCheque.jsp?' + parametros_general);
		//impresionCargada=true;
	}
	
 
	
}




var modal2=
'<div class="modal" id="modal-impresion-datos" tabindex="-1" role="dialog"  style="overflow-y: hidden;border: hidden;">'+
	'<div class="modal-dialog" style="width: 100%;    height: 100%;    padding: 0;margin: 0;background-color: rgba(0,0,255,0);">'+
		'<div class="modal-content" style="height: 100%;    border-radius: 0;background-color: rgba(0,0,0,0.4);border: hidden;">'+
			'<div id="frame-cargando-datos" style=" color: white; position: relative;    top: 50%;    transform: translateY(-50%);text-align: center; font-size: 17;">Cargando la información <i class="fa fa-spinner fa-spin"></i></div>'+
				'<iframe id="frame-impresion-datos" style="width: 100%;height: 100%;border: hidden;"></iframe>'+
			'</div>'+
		'</div>'+
'</div>';



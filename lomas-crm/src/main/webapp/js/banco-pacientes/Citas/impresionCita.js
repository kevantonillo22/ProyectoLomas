
var impresionCargada=false;
var modalCargado=false;


function ocultarVistaImpresion(){
	
	$('#frame-impresion').fadeOut(function(){$("#modal-impresion").modal('hide');});
}

function cargarVistaImpresion(datos){
	//Añadir el modal
	if(!modalCargado){
		$('body').append(modal);
		modalCargado=true;
		
	}
		
	//$('#frame-cargando').show();	
	//Mostrar el modal
	$("#modal-impresion").modal('show');
	if(impresionCargada)
	$('#frame-impresion').fadeIn();
	
	$('#frame-cargando').show()
	$('#frame-impresion').load(function(){
		$('#frame-cargando').hide();
		$('#frame-impresion').fadeIn();
	});
	
	//Cargar la página de impresión
	if(!impresionCargada)
	{
		var parametros = '?identificador=' + datos.identificador + '&nombre=' + datos.nombre.toUpperCase() + '&fecha=' + datos.fecha + '&hora_inicio=' + datos.hora_inicio;
		$('#frame-impresion').hide();
		$('#frame-impresion').attr('src', '/clinicas-crm/banco-pacientes/impresionCita.jsp' + parametros);
		//impresionCargada=true;
	}
	
 
	
}




var modal=
'<div class="modal" id="modal-impresion" tabindex="-1" role="dialog"  style="overflow-y: hidden;border: hidden;">'+
	'<div class="modal-dialog" style="width: 100%;    height: 100%;    padding: 0;margin: 0;background-color: rgba(0,0,255,0);">'+
		'<div class="modal-content" style="height: 100%;    border-radius: 0;background-color: rgba(0,0,0,0.4);border: hidden;">'+
			'<div id="frame-cargando" style=" color: white; position: relative;    top: 50%;    transform: translateY(-50%);text-align: center; font-size: 17;">Cargando la ficha de recordatorio <i class="fa fa-spinner fa-spin"></i></div>'+
				'<iframe id="frame-impresion" style="width: 100%;height: 100%;border: hidden;"></iframe>'+
			'</div>'+
		'</div>'+
'</div>';



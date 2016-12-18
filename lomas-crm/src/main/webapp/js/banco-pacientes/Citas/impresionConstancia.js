
var impresionCargadaConstancia=false;
var modalCargadoConstancia=false;


function ocultarVistaImpresionConstancia(){
	
	$('#frame-impresion-constancia').fadeOut(function(){$("#modal-impresion-constancia").modal('hide');});
}

function cargarVistaImpresionConstancia(){
	//Añadir el modal
	if(!modalCargadoConstancia){
		$('body').append(modalConstancia);
		modalCargadoConstancia=true;
		
	}
		
	//$('#frame-cargando-constancia').show();	
	//Mostrar el modal
	$("#modal-impresion-constancia").modal('show');
	if(impresionCargadaConstancia)
	$('#frame-impresion-constancia').fadeIn();
	
	$('#frame-cargando-constancia').show()
	$('#frame-impresion-constancia').load(function(){
		$('#frame-cargando-constancia').hide();
		$('#frame-impresion-constancia').fadeIn();
	});
	
	//Cargar la página de impresión
	if(!impresionCargadaConstancia)
	{
		var parametros = '';
		$('#frame-impresion-constancia').hide();
		$('#frame-impresion-constancia').attr('src', '/clinicas-crm/banco-pacientes/impresionConstancia.jsp' + parametros);
		//impresionCargada=true;
	}
	
 
	
}




var modalConstancia=
'<div class="modal" id="modal-impresion-constancia" tabindex="-1" role="dialog"  style="overflow-y: hidden;border: hidden;">'+
	'<div class="modal-dialog" style="width: 100%;    height: 100%;    padding: 0;margin: 0;background-color: rgba(0,0,255,0);">'+
		'<div class="modal-content" style="height: 100%;    border-radius: 0;background-color: rgba(0,0,0,0.4);border: hidden;">'+
			'<div id="frame-cargando-constancia" style=" color: white; position: relative;    top: 50%;    transform: translateY(-50%);text-align: center; font-size: 17;">Cargando la constancia <i class="fa fa-spinner fa-spin"></i></div>'+
				'<iframe id="frame-impresion-constancia" style="width: 100%;height: 100%;border: hidden;"></iframe>'+
			'</div>'+
		'</div>'+
'</div>';



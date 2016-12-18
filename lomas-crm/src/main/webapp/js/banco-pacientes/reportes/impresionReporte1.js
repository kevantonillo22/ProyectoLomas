
var impresionCargadaReporte1=false;
var modalCargadoReporte1=false;


function ocultarVistaImpresionReporte1(){
	
	$('#frame-impresion-reporte1').fadeOut(function(){$("#modal-impresion-reporte1").modal('hide');});
}

function cargarVistaImpresionReporte1(data_general, fecha_inicio, fecha_fin){
	//Preparar data
	
	
	//Añadir el modal
	if(!modalCargadoReporte1){
		$('body').append(modalReporte1);
		modalCargadoReporte1=true;
		
	}
		
	//$('#frame-cargando-reporte1').show();	
	//Mostrar el modal
	$("#modal-impresion-reporte1").modal('show');
	if(impresionCargadaReporte1)
		$('#frame-impresion-reporte1').fadeIn();
	
	$('#frame-cargando-reporte1').show()
	$('#frame-impresion-reporte1').load(function(){
		$('#frame-cargando-reporte1').hide();
		$('#frame-impresion-reporte1').fadeIn();
	});
	
	//Cargar la página de impresión
	if(!impresionCargadaReporte1)
	{
				
		var parametros = '';
		var cont = 1;
		var total = 0;
		for (val in data_general) { 
			parametros = parametros + '&estado' + cont + '=' + data_general[val].estado;
			parametros = parametros + '&total' + cont + '=' + data_general[val].total;
			parametros = parametros + '&label' + cont + '=' + data_general[val].label;
			cont++;
			total = total + data_general[val].total;
		}
		cont--;
		parametros = parametros + '&cont=' + cont;
		parametros = parametros + '&fecha_inicio=' + fecha_inicio;
		parametros = parametros + '&fecha_fin=' + fecha_fin;
		parametros = parametros + '&total_final=' + total;
		
		$('#frame-impresion-reporte1').hide();
		$('#frame-impresion-reporte1').attr('src', '/clinicas-crm/banco-pacientes/impresionReporte1.jsp?' + parametros);
		//impresionCargada=true;
	}
	
 
	
}




var modalReporte1=
'<div class="modal" id="modal-impresion-reporte1" tabindex="-1" role="dialog"  style="overflow-y: hidden;border: hidden;">'+
	'<div class="modal-dialog" style="width: 100%;    height: 100%;    padding: 0;margin: 0;background-color: rgba(0,0,255,0);">'+
		'<div class="modal-content" style="height: 100%;    border-radius: 0;background-color: rgba(0,0,0,0.4);border: hidden;">'+
			'<div id="frame-cargando-reporte1" style=" color: white; position: relative;    top: 50%;    transform: translateY(-50%);text-align: center; font-size: 17;">Cargando la información <i class="fa fa-spinner fa-spin"></i></div>'+
				'<iframe id="frame-impresion-reporte1" style="width: 100%;height: 100%;border: hidden;"></iframe>'+
			'</div>'+
		'</div>'+
'</div>';



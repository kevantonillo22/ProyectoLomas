
var impresionCargadaReporte3=false;
var modalCargadoReporte3=false;


function ocultarVistaImpresionReporte3(){
	
	$('#frame-impresion-reporte3').fadeOut(function(){$("#modal-impresion-reporte3").modal('hide');});
}

function cargarVistaImpresionReporte3(data_general, fecha_anio){
	//Preparar data
	
	
	//Añadir el modal
	if(!modalCargadoReporte3){
		$('body').append(modalReporte3);
		modalCargadoReporte3=true;
		
	}
		
	//$('#frame-cargando-reporte1').show();	
	//Mostrar el modal
	$("#modal-impresion-reporte3").modal('show');
	if(impresionCargadaReporte3)
		$('#frame-impresion-reporte3').fadeIn();
	
	$('#frame-cargando-reporte3').show()
	$('#frame-impresion-reporte3').load(function(){
		$('#frame-cargando-reporte3').hide();
		$('#frame-impresion-reporte3').fadeIn();
	});
	
	//Cargar la página de impresión
	if(!impresionCargadaReporte3)
	{
		var anio = fecha_anio;
		var parametros = '';
		var cont = 1;
		var total = 0;
		for (val in data_general) { 
			parametros = parametros + '&mes' + cont + '=' + data_general[val].mes_numero;
			parametros = parametros + '&citados' + cont + '=' + data_general[val].citados;
			parametros = parametros + '&registrados' + cont + '=' + data_general[val].registrados;
			parametros = parametros + '&ausentes' + cont + '=' + data_general[val].ausentes;
			parametros = parametros + '&aprobados' + cont + '=' + data_general[val].aprobados;
			parametros = parametros + '&rechazados' + cont + '=' + data_general[val].rechazados;
			parametros = parametros + '&referidos' + cont + '=' + data_general[val].referidos;
			parametros = parametros + '&apartados' + cont + '=' + data_general[val].apartados;
			parametros = parametros + '&asignados' + cont + '=' + data_general[val].asignados;
			parametros = parametros + '&otros' + cont + '=' + data_general[val].otros;
			parametros = parametros + '&total' + cont + '=' + data_general[val].total;
			cont++;
		}
		cont--;
		parametros = parametros + '&cont=' + cont;
		parametros = parametros + '&anio=' + anio;
		
		$('#frame-impresion-reporte3').hide();
		$('#frame-impresion-reporte3').attr('src', '/clinicas-crm/banco-pacientes/impresionReporte3.jsp?' + parametros);
		//impresionCargada=true;
	}
	
 
	
}




var modalReporte3=
'<div class="modal" id="modal-impresion-reporte3" tabindex="-1" role="dialog"  style="overflow-y: hidden;border: hidden;">'+
	'<div class="modal-dialog" style="width: 100%;    height: 100%;    padding: 0;margin: 0;background-color: rgba(0,0,255,0);">'+
		'<div class="modal-content" style="height: 100%;    border-radius: 0;background-color: rgba(0,0,0,0.4);border: hidden;">'+
			'<div id="frame-cargando-reporte3" style=" color: white; position: relative;    top: 50%;    transform: translateY(-50%);text-align: center; font-size: 17;">Cargando la información <i class="fa fa-spinner fa-spin"></i></div>'+
				'<iframe id="frame-impresion-reporte3" style="width: 100%;height: 100%;border: hidden;"></iframe>'+
			'</div>'+
		'</div>'+
'</div>';



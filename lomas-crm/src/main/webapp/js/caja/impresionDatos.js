
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
	var caja = data_general.caja[0];
	var documento = data_general.documento;
	var efectivo = data_general.efectivo;
	
	if(!impresionCargada2)
	{
		var parametros_general = 
		'fecha=' + caja.fecha +
		'&fecha_creacion=' + caja.fecha_creacion +
		'&fecha_modificacion=' + caja.fecha_modificacion +
		'&fondo=' + caja.fondo +
		'&id=' + caja.id +
		'&sumatoria=' + caja.sumatoria +
		'&total_documento=' + caja.total_documento +
		'&total_efectivo=' + caja.total_efectivo +
		'&variacion=' + caja.variacion;
		
		var cont = 1;
		for (val in documento) { 
			parametros_general = parametros_general + '&caja_chica_documento' + cont + '=' + documento[val].caja_chica;
			parametros_general = parametros_general + '&id_documento' + cont + '=' + documento[val].id;
			parametros_general = parametros_general + '&monto_documento' + cont + '=' + documento[val].monto;
			parametros_general = parametros_general + '&nit' + cont + '=' + documento[val].nit;
			parametros_general = parametros_general + '&nombre' + cont + '=' + documento[val].nombre;
			cont++;
		}
		cont--;
		parametros_general = parametros_general + '&cont_documento=' + cont;
		
		var cont2 = 1;
		for (val in efectivo) { 
			parametros_general = parametros_general + '&caja_chica_efectivo' + cont2 + '=' + efectivo[val].caja_chica;
			parametros_general = parametros_general + '&cantidad' + cont2 + '=' + efectivo[val].cantidad;
			parametros_general = parametros_general + '&id_efectivo' + cont2 + '=' + efectivo[val].id;
			parametros_general = parametros_general + '&monto_efectivo' + cont2 + '=' + efectivo[val].monto;
			parametros_general = parametros_general + '&tipo' + cont2 + '=' + efectivo[val].tipo;
			cont2++;
		}
		cont2--;
		parametros_general = parametros_general + '&cont_efectivo=' + cont2;
		
		
		$('#frame-impresion-datos').hide();
		$('#frame-impresion-datos').attr('src', '../caja/impresionDatos.jsp?a=1&' + parametros_general);
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



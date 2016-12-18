
var impresionCargadaReporte2=false;
var modalCargadoReporte2=false;


function ocultarVistaImpresionReporte2(){
	
	$('#frame-impresion-reporte2').fadeOut(function(){$("#modal-impresion-reporte2").modal('hide');});
}

function cargarVistaImpresionReporte2(data_general, fecha_mes){
	//Preparar data
	
	
	//Añadir el modal
	if(!modalCargadoReporte2){
		$('body').append(modalReporte2);
		modalCargadoReporte2=true;
		
	}
		
	//$('#frame-cargando-reporte1').show();	
	//Mostrar el modal
	$("#modal-impresion-reporte2").modal('show');
	if(impresionCargadaReporte2)
		$('#frame-impresion-reporte2').fadeIn();
	
	$('#frame-cargando-reporte2').show()
	$('#frame-impresion-reporte2').load(function(){
		$('#frame-cargando-reporte2').hide();
		$('#frame-impresion-reporte2').fadeIn();
	});
	
	//Cargar la página de impresión
	if(!impresionCargadaReporte2)
	{
		var v =fecha_mes.split("/");
		var mes = "";
		var anio = v[1];
		if(v[0] == "01"){mes = "Enero";}
		else if(v[0] == "02"){mes = "Febrero";}
		else if(v[0] == "03"){mes = "Marzo";}
		else if(v[0] == "04"){mes = "Abril";}
		else if(v[0] == "05"){mes = "Mayo";}
		else if(v[0] == "06"){mes = "Junio";}
		else if(v[0] == "07"){mes = "Julio";}
		else if(v[0] == "08"){mes = "Agosto";}
		else if(v[0] == "09"){mes = "Septiembre";}
		else if(v[0] == "10"){mes = "Octubre";}
		else if(v[0] == "11"){mes = "Noviembre";}
		else if(v[0] == "12"){mes = "Diciembre";}
		
		var parametros = '';
		var cont = 1;
		var total = 0;
		for (val in data_general) { 
			parametros = parametros + '&id_caso' + cont + '=' + data_general[val].id_caso;
			parametros = parametros + '&nombre' + cont + '=' + data_general[val].nombre;
			parametros = parametros + '&fecha_cita' + cont + '=' + data_general[val].fecha_cita;
			parametros = parametros + '&label_estado' + cont + '=' + data_general[val].label_estado;
			cont++;
			total = total + data_general[val].total;
		}
		cont--;
		parametros = parametros + '&cont=' + cont;
		parametros = parametros + '&mes=' + mes;
		parametros = parametros + '&anio=' + anio;
		
		$('#frame-impresion-reporte2').hide();
		$('#frame-impresion-reporte2').attr('src', '/clinicas-crm/banco-pacientes/impresionReporte2.jsp?' + parametros);
		//impresionCargada=true;
	}
	
 
	
}




var modalReporte2=
'<div class="modal" id="modal-impresion-reporte2" tabindex="-1" role="dialog"  style="overflow-y: hidden;border: hidden;">'+
	'<div class="modal-dialog" style="width: 100%;    height: 100%;    padding: 0;margin: 0;background-color: rgba(0,0,255,0);">'+
		'<div class="modal-content" style="height: 100%;    border-radius: 0;background-color: rgba(0,0,0,0.4);border: hidden;">'+
			'<div id="frame-cargando-reporte2" style=" color: white; position: relative;    top: 50%;    transform: translateY(-50%);text-align: center; font-size: 17;">Cargando la información <i class="fa fa-spinner fa-spin"></i></div>'+
				'<iframe id="frame-impresion-reporte2" style="width: 100%;height: 100%;border: hidden;"></iframe>'+
			'</div>'+
		'</div>'+
'</div>';




var impresionCargada2=false;
var modalCargado2=false;


function ocultarVistaImpresion2(){
	
	$('#frame-impresion-datos').fadeOut(function(){$("#modal-impresion-datos").modal('hide');});
}

function cargarVistaImpresion2(data_general, data_tratamientos, data_caso){
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
		'general_id=' + data_general.id + '&general_nombre=' + data_general.nombre + '&general_dpi=' + data_general.persona_DPI +
		'&general_edad=' + data_general.edad + '&general_direccion=' + data_general.direccion + '&general_telefono=' + data_general.telefono +
		'&general_correo=' + data_general.correo + '&general_estado_civil=' + data_general.estado_civil + '&general_escolaridad=' + data_general.escolaridad +
		'&general_lugar_trabajo=' + data_general.lugar_trabajo + '&general_ocupacion=' + data_general.ocupacion + '&general_horario=' + data_general.horario +
		'&general_ingresos=' + data_general.ingresos + '&general_tipo_ingreso=' + data_general.tipo_ingreso + '&general_otro=' + data_general.otro +
		'&general_encargado_tratamiento=' + data_general.encargado_tratamiento + '&general_relacion_paciente=' + data_general.relacion_paciente + '&general_trabajo_encargado=' + data_general.trabajo_encargado +
		'&general_salario_encargado=' + data_general.salario_encargado + '&general_situacion_economica=' + data_general.situacion_economica;
		
		var parametros_caso = '&caso_observaciones=' + data_caso.observaciones;
		parametros_caso = parametros_caso + '&caso_asignado_op=' + data_caso.caso_asignado_op;
		
		
		var parametros_tratamientos = '';
		var cont = 1;
		for (val in data_tratamientos) { 
			console.log(data_tratamientos);
			console.log(cont);
			parametros_tratamientos = parametros_tratamientos + '&area_tratamiento' + cont + '=' + data_tratamientos[val].area_tratamiento;
			parametros_tratamientos = parametros_tratamientos + '&caso' + cont + '=' + data_tratamientos[val].caso;
			parametros_tratamientos = parametros_tratamientos + '&caso_tratamiento' + cont + '=' + data_tratamientos[val].caso_tratamiento;
			parametros_tratamientos = parametros_tratamientos + '&observaciones' + cont + '=' + data_tratamientos[val].observaciones;
			parametros_tratamientos = parametros_tratamientos + '&tratamiento_requerido' + cont + '=' + data_tratamientos[val].tratamiento_requerido;
			parametros_tratamientos = parametros_tratamientos + '&tratamiento_requerido_txt' + cont + '=' + data_tratamientos[val].tratamiento_requerido_txt;
			cont++;
		}
		cont--;
		parametros_tratamientos = parametros_tratamientos + '&cont=' + cont;
		
		$('#frame-impresion-datos').hide();
		$('#frame-impresion-datos').attr('src', '/clinicas-crm/banco-pacientes/impresionDatosPaciente.jsp?' + parametros_general + parametros_caso + parametros_tratamientos);
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



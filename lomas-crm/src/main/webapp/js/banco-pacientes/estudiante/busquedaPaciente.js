var dataRow; //variable para guardar info de cada tupla de la tabla
var filtro;


$(inicializar);

function inicializar(){
	dataRow = {};
	filtro = {};
	filtro.gingivitis 		= $('#chkGingivitis');
	filtro.periodontitis 	= $('#chkPeriodontitis');
	filtro.obturaciones 	= $('#chkObturaciones');
	filtro.amalgamas	 	= $('#chkAmalgamas');
	filtro.resinas		 	= $('#chkResinas');
	filtro.incrustaciones 	= $('#chkIncrustaciones');
	filtro.pt 				= $('#chkPT');
	filtro.ppr_inferior	 	= $('#chkPPRInferior');
	filtro.ppr_superior	 	= $('#chkPPRSuperior');
	filtro.ppf			 	= $('#chkPPF');
	filtro.endodoncia	 	= $('#chkEndodoncia');
	filtro.multiradicular_superior 	= $('#chkMultiradicularSuperior');
	filtro.multiradicular_inferior 	= $('#chkMultiradicularInferior');
	filtro.monoradicular	= $('#chkMonoradicular');
	filtro.cirugia		 	= $('#chkCirugia');
	filtro.exodoncia	 	= $('#chkExodoncia');
	filtro.boton_buscar 	= $('#btn-buscar')
	filtro.boton_buscar.click(function(){
		var d ={};
		if(filtro.gingivitis.is(':checked'))	{d.gingivitis = true;}
	 	else{d.gingivitis = false;}
	 	if(filtro.periodontitis.is(':checked'))	{d.periodontitis = true;}
	 	else{d.periodontitis = false;}
	 	if(filtro.obturaciones.is(':checked'))	{d.obturaciones = true;}	
	 	else{d.obturaciones = false;}				
	 	if(filtro.amalgamas.is(':checked'))		{d.amalgamas = true;}
	 	else{d.amalgamas = false;}					
	 	if(filtro.resinas.is(':checked'))		{d.resinas = true;}
	 	else{d.resinas = false;}					
	 	if(filtro.incrustaciones.is(':checked')){d.incrustaciones = true;}
	 	else{d.incrustaciones = false;}					
	 	if(filtro.pt.is(':checked'))			{d.pt = true;}
	 	else{d.pt = false;}					
	 	if(filtro.ppr_inferior.is(':checked'))	{d.ppr_inferior = true;}
	 	else{d.ppr_inferior = false;}					
	 	if(filtro.ppr_superior.is(':checked'))	{d.ppr_superior = true;}
	 	else{d.ppr_superior = false;}					
	 	if(filtro.ppf.is(':checked'))			{d.ppf = true;}	
	 	else{d.ppf = false;}				
	 	if(filtro.endodoncia.is(':checked'))	{d.endodoncia = true;}
	 	else{d.endodoncia = false;}					
	 	if(filtro.multiradicular_superior.is(':checked')){d.multiradicular_superior = true;}
	 	else{d.multiradicular_superior = false;}					
	 	if(filtro.multiradicular_inferior.is(':checked')){d.multiradicular_inferior = true;}
	 	else{d.multiradicular_inferior = false;}					
	 	if(filtro.monoradicular.is(':checked'))	{d.monoradicular = true;}
	 	else{d.monoradicular = false;}					
	 	if(filtro.cirugia.is(':checked'))		{d.cirugia = true;}
	 	else{d.cirugia = false;}					
	 	if(filtro.exodoncia.is(':checked'))		{d.exodoncia = true;}
	 	else{d.exodoncia = false;}		
		var res = d.gingivitis || d.periodontitis || d.obturaciones || d.amalgamas || d.resinas || d.incrustaciones || d.pt
		|| d.ppr_inferior || d.ppr_superior || d.ppf || d.endodoncia || d.multiradicular_superior || d.multiradicular_inferior
		|| d.monoradicular || d.cirugia || d.exodoncia;
		if(res == true){
			listarPacientes(1);
		}else{
			listarPacientes(0);
		}
		
		
	});
	listarPacientes(0);
	
}


var table;
function listarPacientes(bandera){
		table = $('#example').DataTable({
		 "ajax": 
		 {	
			 "url": "/clinicas-crm/busqueda",
			 "data": 
				 function ( d ) {
					if(bandera==0){
						filtro.boton_buscar.html('<b> Espere <i class="fa fa-spinner fa-spin"></i></b>');
						filtro.boton_buscar.prop("disabled", true);
						d.operacion = "0";
					}else if(bandera==1){
						//Deshabilitar el boton y mostrar animacion de cargado
						filtro.boton_buscar.html('<b> Espere <i class="fa fa-spinner fa-spin"></i></b>');
						filtro.boton_buscar.prop("disabled", true);
						d.operacion = "1";
					}
				 	if(filtro.gingivitis.is(':checked'))	{d.gingivitis = true;}
				 	else{d.gingivitis = false;}
				 	if(filtro.periodontitis.is(':checked'))	{d.periodontitis = true;}
				 	else{d.periodontitis = false;}
				 	if(filtro.obturaciones.is(':checked'))	{d.obturaciones = true;}	
				 	else{d.obturaciones = false;}				
				 	if(filtro.amalgamas.is(':checked'))		{d.amalgamas = true;}
				 	else{d.amalgamas = false;}					
				 	if(filtro.resinas.is(':checked'))		{d.resinas = true;}
				 	else{d.resinas = false;}					
				 	if(filtro.incrustaciones.is(':checked')){d.incrustaciones = true;}
				 	else{d.incrustaciones = false;}					
				 	if(filtro.pt.is(':checked'))			{d.pt = true;}
				 	else{d.pt = false;}					
				 	if(filtro.ppr_inferior.is(':checked'))	{d.ppr_inferior = true;}
				 	else{d.ppr_inferior = false;}					
				 	if(filtro.ppr_superior.is(':checked'))	{d.ppr_superior = true;}
				 	else{d.ppr_superior = false;}					
				 	if(filtro.ppf.is(':checked'))			{d.ppf = true;}	
				 	else{d.ppf = false;}				
				 	if(filtro.endodoncia.is(':checked'))	{d.endodoncia = true;}
				 	else{d.endodoncia = false;}					
				 	if(filtro.multiradicular_superior.is(':checked')){d.multiradicular_superior = true;}
				 	else{d.multiradicular_superior = false;}					
				 	if(filtro.multiradicular_inferior.is(':checked')){d.multiradicular_inferior = true;}
				 	else{d.multiradicular_inferior = false;}					
				 	if(filtro.monoradicular.is(':checked'))	{d.monoradicular = true;}
				 	else{d.monoradicular = false;}					
				 	if(filtro.cirugia.is(':checked'))		{d.cirugia = true;}
				 	else{d.cirugia = false;}					
				 	if(filtro.exodoncia.is(':checked'))		{d.exodoncia = true;}
				 	else{d.exodoncia = false;}					
				 },
			"type": "POST"
		 },
		 "preDrawCallback": function( settings ,json) {
	
		  },
		  stateSave: true,
		 "bFilter": false,
			"bDestroy": true,
			"pagingType": "bootstrap",
			"lengthMenu": [[5, 10, 25, 50, 100, -1], [5, 10, 25, 50, 100, "Todos"]],
			"language": {
				"zeroRecords": "<center>No hay filas para mostrar</center>",
				"lengthMenu": "_MENU_&nbsp;&nbsp;Registros por página",
				"info": "Mostrando la página _PAGE_ de _PAGES_",
				"infoEmpty": "",
				"infoFiltered": "",
				"paginate": {
					"next": "Siguiente",
					"previous": "Anterior"
				}
			},
		/*DESCOMENTAR SI SE QUIERE CAMBIAR EL ANCHO DE CADA UNA DE LAS COLUMNAS
		 * "columnDefs": 
			[
			 	{ "width": "5%", "targets": 0 },
			 	{ "width": "10%", "targets": 0 },
			 	{ "width": "30%", "targets": 0 },
			 	{ "width": "20%", "targets": 0 },
			 	{ "width": "20%", "targets": 0 },
			 	{ "width": "10%", "targets": 0 }
			 	
			 	
			],*/
		 "autoWidth": false,
		 "columns": 
			[
			  {
					"class": 'clasetest',
					"orderable": false,
					"data": '',
					"defaultContent": '<button title="Opciones" style="border-radius:50%;" class="btn btn-white"><b>+</b></button>',
					"visible": true
				},
				{
					"data": "id_caso"
				}, {
					"data": "resumen_tratamientos"
				}, {
					"data": "id_paciente",
					"visible": false
				}
			],
			"initComplete": function (settings, json) {
				if (json.resultado == "-100") {
					$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
				} 
				
				$('#example_info').css('display', 'none');
				$('#example tbody tr').each(function () {
					$(this).find('td:eq(0)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 0
					$(this).find('td:eq(1)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 1
				});
				console.log(json);
				//HABILITA DE NUEVO EL BOTON DE BUSCAR
				filtro.boton_buscar.html('Buscar&nbsp;&nbsp;<i class="fa fa-search">');
				filtro.boton_buscar.prop("disabled", false);
				$("#example").css("display","block");
			},
			"fnDrawCallback": function( oSettings ) {
				//CERRAR TODAS LOS DETALLES DE LAS FILAS PARA CUANDO 
				//SE NAVEGUE ENTRE PAGINAS
				$("[role='row']").each(function(){
					if(typeof table !== "undefined") {
					    table.row($(this)).child.hide();
					}
				}
				);
		    }
		 
	 });
		
	//cuando se de click a controles de paginacion se oculten los botones
	$('.pagination li a').click(function(){
		console.log('pppp');
		$("[role='row']").each(function(){
			table.row($(this)).child.hide();
			}
		);
	});
		
	// Restablece el evento click
	$('#example tbody').unbind( "click" );
		
	//Indispensable para detalle de cada tupla
	$('#example tbody').on('click', 'td.clasetest', function () {
		var tr = $(this).parents('tr');
		var row = table.row(tr);
		fila = row;
		id=row.data().id;
		
		$("[role='row']").not(tr).each(function(){
				table.row($(this)).child.hide();
			}
		);
		
		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			row.child(format(row.data())).show();
			tr.addClass('shown');
		}
	});
}


function mostrarVistaDatos(data){
	var parametros = {}
	parametros.operacion = 2;
	parametros.id_paciente = data.id_paciente;
	parametros.id_caso = data.identificador;
	$("#btn-ver").html('<b> Espere <i class="fa fa-spinner fa-spin"></i></b>');
	$("#btn-ver").prop("disabled", true);
	console.log(parametros);
	$.post('/clinicas-crm/busqueda',parametros,callbackMostrarVistaDatos,'json');
	
}

function callbackMostrarVistaDatos(response){
	var tiempo = 10;
	$("#btn-ver").html('<i class="fa fa-pencil-square-o"></i> Ver datos ');
	$("#btn-ver").prop("disabled", false);
	
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion, tiempo);
			break;
			
		case 1:
			cargarVistaImpresion2(response.data_general[0], response.data_tratamientos, response.data_caso[0]);
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			desplegarMensaje("error", response.descripcion, tiempo);
			break;
	
	}
}


function confirmacionApartar(data){
	Messenger.options = {
		    extraClasses: 'messenger-fixed messenger-on-top messenger-on-center',
		}
	var mensajito=Messenger().post({
		extraClasses: 'messenger-fixed messenger-on-center messenger-on-top',
		  message: "<center>¿Desea apartar al paciente?</center>",
		  type:'info',
		  
		  showCloseButton:true,
		  id:1,
		  hideAfter:36000,
		  actions: {
		  	aceptar: {
		 	label: "Apartar",
		   	action: function(){
		   		cambiarCasoApartado(data, 6);
		   		mensajito.hide();
		      }
		    },
		   	cancelar: {
			 	label: "Cancelar",
			   	action: function(){
			   		mensajito.hide();
				}
			}
			//adicionar botones
		  }
	});
}

function cambiarCasoApartado(data, estado){
	$('#btn-apartar').html('<b> Espere <i class="fa fa-spinner fa-spin"></i></b>');
	$("#btn-apartar").prop("disabled", true);
	var parametros = {};
	parametros.operacion = 3;
	parametros.id = data.identificador;
	parametros.caso_estado = estado;
	$.post('/clinicas-crm/busqueda',parametros,callbackCambiarCasoApartado,'json');

}


function callbackCambiarCasoApartado(response){
	var time = 5;
	var boton = $('#btn-apartar');
	boton.html(' <i class="fa fa-check-square-o"></i> Apartar ');
	boton.prop("disabled", false);
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion, time + 10);
			break;
			
		case 1:
			//desbloquearFormCaso();
			desplegarMensaje("success", "El paciente fue apartado exitosamente", time);
			//table.ajax.reload( null, false );
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			desplegarMensaje("error", response.descripcion, time);
			break;
			
		case -200:
			desplegarMensaje("error", response.descripcion, time);
	}
}
/*********************************************************************
 * @author kcardona
 * @since 10/09/2015
 * @return String
 * @Descripcion Devuelve una cadena en la cual se imprime los botones
 * de opciones de cada tupla de la tabla principal.
 ********************************************************************/
function format(d) {
	dataRow.identificador 	= d.id_caso;
	dataRow.id_paciente 	= d.id_paciente;
	
	var ver_datos 	= '<td><a data-toggle="modal" data-target="#basicModal"  href="#" onclick="mostrarVistaDatos(dataRow)"><button id="btn-ver" class="btn btn-green" ><i class="fa fa-pencil-square-o"></i> Ver datos </button></a></td>';
	var apartar 	= '<td><a onclick="confirmacionApartar(dataRow);"><button id="btn-apartar" class="btn btn-blue" ><i class="fa fa-check-square-o"></i> Apartar </button></a></td>';
	var rechazar	= '<td><a data-toggle="modal" data-target="#basicModal"  href="#" onclick="cargarUITratamiento(dataRow)"><button class="btn btn-default" >Apartar</button></a></td>';
	var spin 		= '<td><div id="changeSpin"></div></td>';
	var titulo 		= 'Opciones';
	
	//si es registrado
	if(dataRow.caso_estado == 2){
		rechazar = '';
	}
	//si es apartado
	else if(dataRow.caso_estado == 6){
		apartar = '';
	}
	//si es asignado
	else if(dataRow.caso_estado == 7){
		apartar = '';
		rechazar = '';
	}
	
	return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' + 
	'<tr>' + 
	'<td>' + titulo + '</td>' + 
	'</tr>' + 
	'<tr>' + 
	ver_datos +
	apartar +
	//rechazar +
	spin + 
	//'<td><div class="form-group" id="wrapCasoOP" style="margin-bottom:0px;"><select placeholder="holis" class="form-control" id="casoOP"><option value="" disabled selected>Asignado a O.P. de</option><option value="1">4º año </option><option value="2">5º año </option><option value="3">PRC </option><option value="4">Postgrado </option></select></div></td><td><div id="changeSpin"></div></td>' +
	//'<td><label class="checkbox-inline"><input id="chkCaso" type="checkbox"> Caso Docente</label></td><td><div id="changeSpin2"></div></td>' + 
	
	
	
	'</tr>' + 
	'</table>';
}

function desplegarMensaje(clase, mensaje, time) {
	Messenger.options = {
	    extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
	}
	Messenger().post({message:'<center>'+ mensaje+'</center>',type: clase,showCloseButton: true, hideAfter:time});
}
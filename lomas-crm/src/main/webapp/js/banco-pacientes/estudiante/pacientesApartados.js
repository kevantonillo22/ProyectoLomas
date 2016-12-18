var dataRow; //variable para guardar info de cada tupla de la tabla



$(inicializar);

function inicializar(){
	dataRow = {};
	
	listarPacientes();
	
}


var table;
function listarPacientes(){
		table = $('#example').DataTable({
		 "ajax": 
		 {	
			 "url": "/clinicas-crm/apartados",
			 "data": 
				 function ( d ) {
						d.operacion = 1;
				 },
			"type": "POST"
		 },
		 "preDrawCallback": function( settings ,json) {
	
		  },
		  stateSave: true,
		  processing : true,
		  "bFilter" : false,               
		  "bLengthChange": false,
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
				},
				"sProcessing": '<span style="font-weight:bold;">Cargando <i class="fa fa-spinner fa-spin"></i></span>'
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
					"data": "nombre"
				}, {
					"data": "fecha_apartado"
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

var txt_btn_datos;
function mostrarVistaDatos(data){
	var parametros = {}
	parametros.operacion = 2;
	parametros.id_paciente = data.id_paciente;
	parametros.id_caso = data.identificador;
	txt_btn_datos = $("#btn-ver").html();
	$("#btn-ver").html('<b> Espere <i class="fa fa-spinner fa-spin"></i></b>');
	$("#btn-ver").prop("disabled", true);
	console.log(parametros);
	$.post('/clinicas-crm/busqueda',parametros,callbackMostrarVistaDatos,'json');
	
}

function callbackMostrarVistaDatos(response){
	var tiempo = 10;
	$("#btn-ver").html(txt_btn_datos);
	$("#btn-ver").prop("disabled", false);
	
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion, tiempo);
			break;
			
		case 1:
			cargarVistaImpresion2(response.data_general[0], response.data_tratamientos);
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


function confirmacionDescartar(data){
	Messenger.options = {
		    extraClasses: 'messenger-fixed messenger-on-top messenger-on-center',
		}
	var mensajito=Messenger().post({
		extraClasses: 'messenger-fixed messenger-on-center messenger-on-top',
		  message: "<center>¿Desea descartar al paciente?</center>",
		  type:'info',
		  
		  showCloseButton:true,
		  id:1,
		  hideAfter:36000,
		  actions: {
		  	aceptar: {
		 	label: "Descartar",
		   	action: function(){
		   		cambiarCasoApartado(data, 2);
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

var txt_btn_descartar;
function cambiarCasoApartado(data, estado){
	txt_btn_descartar = $('#btn-descartar').html();
	$('#btn-descartar').html('<b> Espere <i class="fa fa-spinner fa-spin"></i></b>');
	$("#btn-descartar").prop("disabled", true);
	var parametros = {};
	parametros.operacion = 2;
	parametros.id = data.identificador;
	parametros.caso_estado = estado;
	$.post('/clinicas-crm/apartados',parametros,callbackCambiarCasoApartado,'json');
}


function callbackCambiarCasoApartado(response){
	var time = 5;
	var boton = $('#btn-descartar');
	boton.html(txt_btn_descartar);
	boton.prop("disabled", false);
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion, time);
			break;
			
		case 1:
			desplegarMensaje("success", "El paciente fue descartado.", time);
			table.ajax.reload( null, false );
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
	var descartar 	= '<td><a onclick="confirmacionDescartar(dataRow);"><button id="btn-descartar" class="btn btn-blue" ><i class="fa fa-share-square-o"></i> Descartar </button></a></td>';
	var spin 		= '<td><div id="changeSpin"></div></td>';
	var titulo 		= 'Opciones';
	
	//si es registrado
	if(dataRow.caso_estado == 2){
		rechazar = '';
	}
	
	return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' + 
	'<tr>' + 
	'<td>' + titulo + '</td>' + 
	'</tr>' + 
	'<tr>' + 
	ver_datos +
	descartar +
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
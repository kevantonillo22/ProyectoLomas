var dataRow; //variable para guardar info de cada tupla de la tabla
var widgets;
var main;
var cheque;
var modal_modificar;
var archivo_subido;
var txtFiltro;
var permi = per; //val permisitos
$(inicializar);

function inicializar(){
	Dropzone.autoDiscover = false;
	dataRow = {};
	
	archivo_subido = false;
	cheque = {};
	cheque.general 				= $('#txtFiltroGeneral');
	cheque.fecha1 				= $('#txtFecha1');
	cheque.fecha2				= $('#txtFecha2');
	cheque.monto1 				= $('#txtMonto1');
	cheque.monto2				= $('#txtMonto2');
	cheque.boton				= $('#btn-buscar');
	cheque.label_advertencia	= $('#lblAdvertenciaMain');
	
	modal_modificar = {};
	modal_modificar.id			= $('#modal-modificar-cheque #txtId');
	modal_modificar.numero 		= $('#modal-modificar-cheque #txtNumero');
	modal_modificar.lugar 		= $('#modal-modificar-cheque #txtLugar');
	modal_modificar.fecha 		= $('#modal-modificar-cheque #txtFecha');
	modal_modificar.nombre 		= $('#modal-modificar-cheque #txtNombre');
	modal_modificar.cantidad 	= $('#modal-modificar-cheque #txtCantidad');
	modal_modificar.motivo 		= $('#modal-modificar-cheque #txtMotivo');
	modal_modificar.label_advertencia	= $('#modal-modificar-cheque #lblAdvertencia');
	
	modal_modificar.cantidad.on("keyup",function(){
		inputControl($(this),'float');
	});
	
	
	modal_modificar.boton = $('#modal-modificar-cheque #btn-guardar');
	
	modal_modificar.imagen = new Dropzone('#txtFile', {
		addRemoveLinks: true,
		acceptedFiles: 'image/*',
		url: "../cheque" ,
		autoDiscover : false,
		autoProcessQueue:false,
		maxFiles:1,
		maxfilesexceeded: function(file) {
	        this.removeAllFiles();
	        this.addFile(file);
	    }
	});
	
	modal_modificar.imagen.on('sending', function(file, xhr, formData) {
		formData.append('operacion', 7);
		formData.append('id', modal_modificar.id.val());
        formData.append('numero', modal_modificar.numero.val());
        formData.append('lugar', modal_modificar.lugar.val());
        formData.append('fecha', modal_modificar.fecha.val());
        formData.append('nombre', modal_modificar.nombre.val());
        formData.append('cantidad', modal_modificar.cantidad.val());
        formData.append('motivo', modal_modificar.motivo.val());
    });
	
	modal_modificar.imagen.on('success', function(file, response) {
		callbackModificarChequeImagen(response);
    });
	
	modal_modificar.imagen.on('error', function(file, response) {
		callbackModificarChequeImagen(response);
    });
	
	modal_modificar.imagen.on('addedfile', function(file, xhr, formData) {
		//si no tiene un archivo agregado
		if (this.files[0]==null){
			archivo_subido = false;
		}else{
			archivo_subido = true;
		}
    });
	
	modal_modificar.imagen.on('removedfile', function(file, xhr, formData) {
		//si no tiene un archivo agregado
		if (this.files[0]==null){
			archivo_subido = false;
		}else{
			archivo_subido = true;
		}
    });
	
	$('#datepicker3').datepicker({
		format: "dd/mm/yyyy",
	    language: "es",
	    todayBtn: "linked",
	    todayHighlight: true,
	    autoclose:true
    });
	
	modal_modificar.boton.click(function(){
		validarFormModificarCheque();
	});
	
	cheque.boton.click(function(){
		buscarCheque();
	});
	
	$('.portlet-body input[type="text"]').keyup(function(e){
	    if(e.keyCode == 13)
	    {
	    	buscarCheque();
	    }
	});
	
	txtFiltro = $('#txtFiltro');
	$('#txtFecha1').datepicker({
		format: "dd/mm/yyyy",
	    language: "es",
	    todayBtn: "linked",
	    todayHighlight: true,
	    autoclose:true
    });
	$('#txtFecha2').datepicker({
		format: "dd/mm/yyyy",
	    language: "es",
	    todayBtn: "linked",
	    todayHighlight: true,
	    autoclose:true
    });
	
	//controlar el rango de las fechas
	$('#txtFecha1').on("changeDate", function (e) {
		$('#txtFecha2').datepicker('setStartDate', e.date);
    });
	$('#txtFecha2').on("changeDate", function (e) {
		$('#txtFecha1').datepicker('setEndDate', e.date);
    });
}





var txt_btn_buscar_cheque;
function buscarCheque(){
	var lblAdvertencia = cheque.label_advertencia;
	lblAdvertencia.html('');
	var errores = false;
	
	//validar numero
	if(cheque.fecha1.val() && cheque.fecha2.val()){
		cheque.fecha1.parent().removeClass("has-error");
	}else if(!cheque.fecha1.val() && !cheque.fecha2.val()){
		cheque.fecha1.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe de ingresar ambas fechas');
		lblAdvertencia.html(advertencia);
		cheque.fecha1.parent().addClass("has-error");
		errores = true;
	}
	
	if(cheque.monto1.val() && cheque.monto2.val()){
		cheque.monto1.parent().removeClass("has-error");
	}else if(!cheque.monto1.val() && !cheque.monto2.val()){
		cheque.monto1.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe de ingresar ambos montos');
		lblAdvertencia.html(advertencia);
		cheque.monto1.parent().addClass("has-error");
		errores = true;
	}
	
	//sino existieron errores se procesa la información ingresada
	if(!errores){
		listarCheques();
	}
}




var table;
function listarCheques(){
		table = $('#example').DataTable({
		 "ajax": 
		 {	
			 "url": "../cheque",
			 "data": 
				 function ( d ) {
				 	d.operacion = 1;
					d.general 	= cheque.general.val();
					d.fecha1	= cheque.fecha1.val();
					d.fecha2 	= cheque.fecha2.val();
					d.monto1 	= cheque.monto1.val();
					d.monto2 	= cheque.monto2.val();
					
					txt_btn_buscar_cheque = cheque.boton.html();
					cheque.boton.html( ' Espere <i class="fa fa-spinner fa-spin"></i>');
					cheque.boton.prop('disabled', true);
				 },
			"type": "POST"
		 },
		 "preDrawCallback": function( settings ,json) {
	
		  },
		  stateSave: true,
		  "bFilter" : false,               
		  "bLengthChange": true,
		  "bPaginate":true,
			"bDestroy": true,
			"pagingType": "bootstrap",
			"lengthMenu": [[5, 10, 25, 50, 100, -1], [5, 10, 25, 50, 100, "Todos"]],
			"iDisplayLength": 25,
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
					"data": "numero"
				}, {
					"data": "lugar"
				}, {
					"data": "fecha"
				}, {
					"data": "nombre"
				}, {
					"data": "cantidad"
				}, {
					"data": "motivo"
				}, {
					"data": "imagen",
					visible:false
				}, {
					"data": "id",
					visible:false
				}
			],
			"initComplete": function (settings, json) {
				if (json.resultado == -100) {
					$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
				}
				cheque.boton.html(txt_btn_buscar_cheque);
				cheque.boton.prop('disabled', false);
				console.log(json);
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




var txt_btn_guardar_cheque;
function validarFormModificarCheque(){
	var lblAdvertencia = modal_modificar.label_advertencia;
	lblAdvertencia.html('');
	var errores = false;
	
	//validar numero
	if(modal_modificar.numero.val()){
		modal_modificar.numero.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar un número de cheque');
		lblAdvertencia.html(advertencia);
		modal_modificar.numero.parent().addClass("has-error");
		errores = true;
	}
	//lugar
	if(modal_modificar.lugar.val()){
		modal_modificar.lugar.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar un lugar');
		lblAdvertencia.html(advertencia);
		modal_modificar.lugar.parent().addClass("has-error");
		errores = true;
	}
	//fecha
	if(modal_modificar.fecha.val()){
		modal_modificar.fecha.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar una fecha');
		lblAdvertencia.html(advertencia);
		modal_modificar.fecha.parent().addClass("has-error");
		errores = true;
	}
	//validar nombre
	if(modal_modificar.nombre.val()){
		modal_modificar.nombre.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar a nombre de quien fue emitido el cheque');
		lblAdvertencia.html(advertencia);
		modal_modificar.nombre.parent().addClass("has-error");
		errores = true;
	}
	//validar cantidad
	if(modal_modificar.cantidad.val()){
		modal_modificar.cantidad.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar una cantidad');
		lblAdvertencia.html(advertencia);
		modal_modificar.cantidad.parent().addClass("has-error");
		errores = true;
	}
	
	if($.isNumeric( modal_modificar.cantidad.val() )){
		modal_modificar.cantidad.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar un monto válido');
		lblAdvertencia.html(advertencia);
		modal_modificar.cantidad.parent().addClass("has-error");
		errores = true;
	}
	
	//validar motivo
	if(modal_modificar.motivo.val()){
		modal_modificar.motivo.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar un motivo');
		lblAdvertencia.html(advertencia);
		modal_modificar.motivo.parent().addClass("has-error");
		errores = true;
	}
	
	
	
	//sino existieron errores se procesa la informacion ingresada
	if(!errores){
		
		var parametros = {};
		parametros.operacion = 4;
		parametros.numero	= modal_modificar.numero.val();
		parametros.id		= modal_modificar.id.val();
		
		$.post('../cheque', parametros, callbackValidarFormModificarCheque, 'json');
		txt_btn_guardar_cheque = modal_modificar.boton.html();
		modal_modificar.boton.html( ' Espere <i class="fa fa-spinner fa-spin"></i>');
		modal_modificar.boton.prop("disabled", true);
	}
}


function callbackValidarFormModificarCheque(response){
	switch(response.resultado){
	case 1:
		if(response.resp == 1){
			if(modal_modificar.imagen.files[0]== null){
				confirmacionModificarSinImagen();
			}else{
				confirmacionModificarConImagen();
			}
		}else{
			var boton = modal_modificar.boton;
			desplegarMensaje("error", response.descripcion, 10);
			var advertencia = obtenerAdvertencia(' alert-danger', response.descripcion);
			modal_modificar.label_advertencia.html(advertencia);
			modal_modificar.numero.parent().addClass("has-error");
			boton.html(txt_btn_guardar_cheque);
			boton.prop("disabled", false);
		}
		break;
	}
}


function limpiarFormCheque(){
	modal_modificar.numero.val('');
	modal_modificar.nombre.val('');
	modal_modificar.lugar.val('');
	modal_modificar.fecha.val('');
	modal_modificar.cantidad.val('');
	modal_modificar.motivo.val('');
	modal_modificar.numero.val('');
	modal_modificar.imagen.removeAllFiles();
	modal_modificar.label_advertencia.html('');
	modal_modificar.numero.parent().removeClass("has-error");
	modal_modificar.lugar.parent().removeClass("has-error");
	modal_modificar.fecha.parent().removeClass("has-error");
	modal_modificar.nombre.parent().removeClass("has-error");
	modal_modificar.cantidad.parent().removeClass("has-error");
	modal_modificar.motivo.parent().removeClass("has-error");
}




var txt_btn_modificar;
function cargarModificarCheque(data){
	limpiarFormModificar();
	$('#modal-modificar-cheque .modal-title').append(' <i class="fa fa-spinner fa-spin"></i>');
	$('#modal-modificar-cheque').modal();
	
	txt_btn_modificar = modal_modificar.boton.html();
	modal_modificar.boton.html();
	modal_modificar.boton.html( ' Espere <i class="fa fa-spinner fa-spin"></i>');
	modal_modificar.boton.prop('disabled', true);
	
	var parametros = {};
	parametros.operacion = 3;
	parametros.numero = data.numero;
	$.post('../cheque',parametros,callbackCargarModificarCheque,'json');
}


function callbackCargarModificarCheque(response){
	var tiempo = 10;
	var boton = modal_modificar.boton;
	boton.html(txt_btn_modificar);
	boton.prop("disabled", false);
	
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion, tiempo);
			break;
			
		case 1:
			limpiarFormCheque();
			setFormValues(response.data[0]);
			$('#modal-modificar-cheque .modal-title i').remove();
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



function setFormValues(data){
	modal_modificar.id.val(data.id);
	modal_modificar.numero.val(data.numero);
	modal_modificar.lugar.val(data.lugar);
	$('#datepicker3').datepicker('update', data.fecha);
	//modal_modificar.fecha.val(data.fecha);
	modal_modificar.nombre.val(data.nombre);
	modal_modificar.cantidad.val(data.cantidad)
	modal_modificar.motivo.val(data.motivo)
}


function limpiarFormModificar(){
	modal_modificar.id.val('');
	modal_modificar.numero.val('');
	modal_modificar.lugar.val('');
	modal_modificar.fecha.val('');
	modal_modificar.nombre.val('');
	modal_modificar.cantidad.val('');
	modal_modificar.motivo.val('');
	modal_modificar.imagen.removeAllFiles();
}



var txt_btn_datos;
function mostrarVistaDatos(data){
	var parametros = {}
	parametros.operacion = 3;
	parametros.numero = data.numero;
	txt_btn_datos = $("#btn-ver").html();
	$("#btn-ver").html('<b> Espere <i class="fa fa-spinner fa-spin"></i></b>');
	$("#btn-ver").prop("disabled", true);
	console.log(parametros);
	$.post('../cheque',parametros,callbackMostrarVistaDatos,'json');
	
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
			cargarVistaImpresion2(response.data[0]);
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



function modificarCheque(){
	var parametros = {};
	parametros.operacion = 5;
	parametros.numero	= modal_modificar.numero.val();
	parametros.id		= modal_modificar.id.val();
	parametros.fecha	= modal_modificar.fecha.val();
	parametros.lugar	= modal_modificar.lugar.val();
	parametros.nombre	= modal_modificar.nombre.val();
	parametros.cantidad	= modal_modificar.cantidad.val();
	parametros.motivo	= modal_modificar.motivo.val();
	
	$.post('../cheque', parametros, callbackModificarCheque, 'json');
}


function callbackModificarCheque(response){
	var tiempo = 10;
	var boton = modal_modificar.boton;
	boton.html(txt_btn_modificar);
	boton.prop("disabled", false);
	
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion, tiempo);
			break;
			
		case 1:
			desplegarMensaje("success", 'Se guardo la información correctamente', 5);
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


function confirmacionModificarSinImagen(){
	Messenger.options = {
		    extraClasses: 'messenger-fixed messenger-on-top messenger-on-center',
		}
	var mensajito=Messenger().post({
		extraClasses: 'messenger-fixed messenger-on-center messenger-on-top',
		  message: "<center>¿Desea guardar los cambios?</center>",
		  type:'info',
		  
		  showCloseButton:true,
		  id:1,
		  hideAfter:36000,
		  actions: {
		  	aceptar: {
		 	label: "Aceptar",
		   	action: function(){
		   		modificarCheque();
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



function confirmacionModificarConImagen(){
	Messenger.options = {
		    extraClasses: 'messenger-fixed messenger-on-top messenger-on-center',
		}
	var mensajito=Messenger().post({
		extraClasses: 'messenger-fixed messenger-on-center messenger-on-top',
		  message: "<center>¿Desea guardar los cambios (La imagen sobreescribirá la actual)?</center>",
		  type:'info',
		  
		  showCloseButton:true,
		  id:1,
		  hideAfter:36000,
		  actions: {
		  	aceptar: {
		 	label: "Aceptar",
		   	action: function(){
		   		modal_modificar.imagen.processQueue();
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



function callbackModificarChequeImagen(response){
	console.log(response);
	var boton = modal_modificar.boton;
	boton.html(txt_btn_guardar_cheque);
	boton.prop("disabled", false);
	
	switch(response.resultado){
	
	case -1:
		desplegarMensaje("error", response.descripcion, 300);

		break;
		
	case -2:
		desplegarMensaje("error", response.descripcion, 300);
		var advertencia = obtenerAdvertencia(' alert-danger', response.descripcion);
		modal_modificar.label_advertencia.html(advertencia);
		modal_modificar.numero.parent().addClass("has-error");
		break;
		
	case 1:
		desplegarMensaje("success", response.descripcion, 10);
		break;
		
	case -100:
		//Caduco
		$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
		boton.html(txt_btn_guardar_cheque);
		boton.prop("disabled", false);
		break;
		
	case -101:
		desplegarMensaje("error", response.descripcion, 300);
		boton.html(txt_btn_guardar_cheque);
		boton.prop("disabled", false);
		break;
	}
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
	dataRow.identificador 	= d.id;
	dataRow.numero 	= d.numero;
	
	
	var ver_datos 	= '<td><a data-toggle="modal" data-target="#basicModal"  href="#" onclick="mostrarVistaDatos(dataRow)"><button id="btn-ver" class="btn btn-green" ><i class="fa fa-pencil-square-o"></i> Ver datos </button></a></td>';
	var descartar = '';
	if(permi){
		descartar 	= '<td><a onclick="cargarModificarCheque(dataRow);"><button id="btn-modific<r" class="btn btn-blue" ><i class="fa fa-share-square-o"></i> Modificar </button></a></td>';
	}
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
	    theme: 'future'
	}
	Messenger().post({message:'<center>'+ mensaje+'</center>',type: clase,showCloseButton: true, hideAfter:time});
}



function obtenerAdvertencia(clase, mensaje) {
	return '<div class="alert ' + clase + '">' + mensaje + '</div>';
}


function getDate(){
	var now = new Date();

	var day = ("0" + now.getDate()).slice(-2);
	var month = ("0" + (now.getMonth() + 1)).slice(-2);

	var today = (day)+"/"+(month)+"/"+now.getFullYear() ;
	return today;
}

function formatDate(){
	var now = new Date();

	var day = ("0" + now.getDate()).slice(-2);
	var month = ("0" + (now.getMonth() + 1)).slice(-2);

	var today = (day)+"/"+(month)+"/"+now.getFullYear() ;
	return today;
}



function inputControl(input,format) 
{ 
    var value=input.val();
    var values=value.split("");
    var update="";
    var transition="";
    if (format=='int'){
        expression=/^([0-9])$/;
        finalExpression=/^([1-9][0-9]*)$/;
    }
    else if (format=='float')
    {
        var expression=/(^\d+$)|(^\d+\.\d+$)|[,\.]/;
        var finalExpression=/^([1-9][0-9]*[,\.]?\d{0,2})$/;
    }   
    for(id in values)
    {           
        if (expression.test(values[id])==true && values[id]!='')
        {
            transition+=''+values[id].replace(',','.');
            if(finalExpression.test(transition)==true)
            {
                update+=''+values[id].replace(',','.');
            }
        }
    }
    input.val(update);
}
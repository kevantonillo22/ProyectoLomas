var dataRow; //variable para guardar info de cada tupla de la tabla
var widgets;
var main;
var caja;
var modal_modificar;
var archivo_subido;
var txtFiltro;
var permi = per; //val permisitos
var billetes;
var monedas;
var documentos_sub;
var total;
var sumatoria;
var fondo;
var variacion;
var botones;
$(inicializar);

function inicializar(){
	dataRow = {};
	archivo_subido = false;
	caja = {};
	caja.fecha1 				= $('#txtFecha1');
	caja.fecha2				= $('#txtFecha2');
	caja.monto1 				= $('#txtMonto1');
	caja.monto2				= $('#txtMonto2');
	caja.boton				= $('#btn-buscar');
	caja.label_advertencia	= $('#lblAdvertenciaMain');
	
	modal_modificar = {};
	modal_modificar.label_advertencia	= $('#modal-modificar-caja #lblAdvertencia');
	modal_modificar.boton = $('#modal-modificar-caja #btn-guardar');
	modal_modificar.id = $('#modal-modificar-caja #txtId');
	
	$('#datepicker3').datepicker({
		format: "dd/mm/yyyy",
	    language: "es",
	    todayBtn: "linked",
	    todayHighlight: true,
	    autoclose:true
    });

	
	caja.boton.click(function(){
		buscarCaja();
	});
	
	$('.portlet-body input[type="text"]').keyup(function(e){
	    if(e.keyCode == 13)
	    {
	    	buscarCaja();
	    }
	});
	
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
	
	
	
	//Variables para el modal
	billetes = {};
	billetes.cantidad200 = 	$('#txtBilleteCantidad200');
	billetes.monto200 = 	$('#txtBilleteMonto200');
	billetes.cantidad100 = 	$('#txtBilleteCantidad100');
	billetes.monto100 = 	$('#txtBilleteMonto100');
	billetes.cantidad50 = 	$('#txtBilleteCantidad50');
	billetes.monto50 = 		$('#txtBilleteMonto50');
	billetes.cantidad20 = 	$('#txtBilleteCantidad20');
	billetes.monto20 = 		$('#txtBilleteMonto20');
	billetes.cantidad10 = 	$('#txtBilleteCantidad10');
	billetes.monto10 = 		$('#txtBilleteMonto10');
	billetes.cantidad5 = 	$('#txtBilleteCantidad5');
	billetes.monto5 = 		$('#txtBilleteMonto5');
	billetes.cantidad1 = 	$('#txtBilleteCantidad1');
	billetes.monto1 = 		$('#txtBilleteMonto1');
	billetes.subtotal= 		$('#txtBilleteSub');
	
	billetes.monto200.val(0);
	billetes.monto200.prop("disabled", true);
	billetes.monto100.val(0);
	billetes.monto100.prop("disabled", true);
	billetes.monto50.val(0);
	billetes.monto50.prop("disabled", true);
	billetes.monto20.val(0);
	billetes.monto20.prop("disabled", true);
	billetes.monto10.val(0);
	billetes.monto10.prop("disabled", true);
	billetes.monto5.val(0);
	billetes.monto5.prop("disabled", true);
	billetes.monto1.val(0);
	billetes.monto1.prop("disabled", true);
	billetes.subtotal.val(0);
	billetes.subtotal.prop("disabled", true);
	
	
	monedas = {};
	monedas.cantidad1 = 	$('#txtMonedaCantidad1');
	monedas.monto1 = 		$('#txtMonedaMonto1');
	monedas.cantidad050 = 	$('#txtMonedaCantidad05');
	monedas.monto050 = 		$('#txtMonedaMonto05');
	monedas.cantidad025 = 	$('#txtMonedaCantidad025');
	monedas.monto025 = 		$('#txtMonedaMonto025');
	monedas.cantidad010 = 	$('#txtMonedaCantidad010');
	monedas.monto010 = 		$('#txtMonedaMonto010');
	monedas.cantidad005 = 	$('#txtMonedaCantidad005');
	monedas.monto005 = 		$('#txtMonedaMonto005');
	monedas.cantidad001 = 	$('#txtMonedaCantidad001');
	monedas.monto001 = 		$('#txtMonedaMonto001');
	monedas.subtotal = 		$('#txtMonedaSub');
	
	documentos_sub = $('#txtDocumentosSub');
	documentos_sub.val(0);
	documentos_sub.prop('disabled', true);
	
	total 		= $('#txtTotal');
	sumatoria 	= $('#txtSumatoria');
	sumatoria.val(0);
	sumatoria.prop('disabled', true);
	
	fondo 		= $('#txtFondo');
	fondo.val(0);
	fondo.prop('disabled', true);
	variacion 	= $('#txtVariacion');
	variacion.val(0);
	variacion.prop('disabled', true);
	
	monedas.monto1.val(0);
	monedas.monto1.prop("disabled", true);
	monedas.monto050.val(0);
	monedas.monto050.prop("disabled", true);
	monedas.monto025.val(0);
	monedas.monto025.prop("disabled", true);
	monedas.monto010.val(0);
	monedas.monto010.prop("disabled", true);
	monedas.monto005.val(0);
	monedas.monto005.prop("disabled", true);
	monedas.monto001.val(0);
	monedas.monto001.prop("disabled", true);
	monedas.subtotal.val(0);
	monedas.subtotal.prop("disabled", true);
	
	total.val(0);
	total.prop("disabled", true);
	
	
	billetes.cantidad200.bind('input', function(e){
		billetes.monto200.val((billetes.cantidad200.val()*200).toFixed(2));
		sumarBilletes();
    });
	
	billetes.cantidad100.bind('input', function(e){
		billetes.monto100.val((billetes.cantidad100.val()*100).toFixed(2));
		sumarBilletes();
    });
	
	billetes.cantidad50.bind('input', function(e){
		billetes.monto50.val((billetes.cantidad50.val()*50).toFixed(2));
		sumarBilletes();
    });
	
	billetes.cantidad20.bind('input', function(e){
		billetes.monto20.val((billetes.cantidad20.val()*20).toFixed(2));
		sumarBilletes();
    });
	
	billetes.cantidad10.bind('input', function(e){
		billetes.monto10.val((billetes.cantidad10.val()*10).toFixed(2));
		sumarBilletes();
    });
	
	billetes.cantidad5.bind('input', function(e){
		billetes.monto5.val((billetes.cantidad5.val()*5).toFixed(2));
		sumarBilletes();
    });
	
	billetes.cantidad1.bind('input', function(e){
		billetes.monto1.val((billetes.cantidad1.val()*1).toFixed(2));
		sumarBilletes();
    });
	
	
	monedas.cantidad1.bind('input', function(e){
		monedas.monto1.val((monedas.cantidad1.val()*1).toFixed(2));
		sumarMonedas();
    });
	
	monedas.cantidad050.bind('input', function(e){
		monedas.monto050.val((monedas.cantidad050.val()*0.50).toFixed(2));
		sumarMonedas();
    });
	
	monedas.cantidad025.bind('input', function(e){
		monedas.monto025.val((monedas.cantidad025.val()*0.25).toFixed(2));
		sumarMonedas();
    });
	
	monedas.cantidad010.bind('input', function(e){
		monedas.monto010.val((monedas.cantidad010.val()*0.1).toFixed(2));
		sumarMonedas();
    });
	
	monedas.cantidad005.bind('input', function(e){
		monedas.monto005.val((monedas.cantidad005.val()*0.05).toFixed(2));
		sumarMonedas();
    });
	
	monedas.cantidad001.bind('input', function(e){
		monedas.monto001.val((monedas.cantidad001.val()*0.01).toFixed(2));
		sumarMonedas();
    });
	
	$('body').delegate('i.add','click',function(){
		$(this).remove();
		$('.remove').remove();
		$('.facturas-body').append(textRow());
		$(".nit").mask("999999-9");
		$(".montoFactura").bind('input', function(e){
			sumarMontoDocumentos();
	    });
	});
	
	$('body').delegate('i.remove','click',function(){
		$(this).parent().parent().prev().children('.col-sm-1:last-child').remove();
		if($('.nit').length <= 2){
			$(this).parent().parent().prev()
			.append('<div class="col-sm-1"><i id="addRow1"style="  border-radius: 50%;padding: 0px;  width: 20px;  height: 20px; top: 10px;float: left;margin-left: -20px;cursor:pointer;"class="glyphicon glyphicon-plus add"></i></div>');
		}else{
			$(this).parent().parent().prev()
			.append('<div class="col-sm-1"><i id="addRow1" style="  border-radius: 50%;padding: 0px;  width: 20px;  height: 15px; top: 17px;float: left;margin-left: -20px;cursor:pointer;" class="glyphicon glyphicon-plus add"></i>      <i id="removeRow1" style="  border-radius: 50%;padding: 0px;  width: 20px;  height: 15px; top: 1px;float: left;margin-left: -21px;cursor:pointer;" class="glyphicon glyphicon-minus remove"></i></div>');
		}
			
		$(this).parent().parent().remove();
	});
	
	$(".nit").mask("999999-9");
	$(".montoFactura").bind('input', function(e){
		sumarMontoDocumentos();
    });
	
	fondo.bind('input', function(e){
		operarVariacion();
    });
	
	//cargarValorParametro();
	modal_modificar.boton.click(function(){
		var ob = $('.cantidad1, .cantidad2, .montoFactura');
		for(var i = 0; i <= ob.length-1; i++){
			if(ob[i].value.trim().length == 0){
				ob[i].value = 0;
			}
		}
		validarFormModificarCaja();
	});
	
	
	$("#datepicker3 input").mask("99/99/9999");
}

function addControlRow(row){
	
}


function operarVariacion(){
	var val = 0;
	if(fondo.val().length != 0){
		val = fondo.val();
	}
	variacion.val((-parseFloat(sumatoria.val()) + parseFloat(val)).toFixed(2));
}


function sumarMontosSumatoria(){
	sumatoria.val((parseFloat(total.val()) + parseFloat(documentos_sub.val())).toFixed(2));
}


function sumarMontoDocumentos(){
	var sum = 0;
	for(var i = 0; i <= $(".montoFactura").length-1;i++){
		if($(".montoFactura")[i].value.trim().length != 0){
			sum = sum + parseFloat($(".montoFactura")[i].value);
		}
		
	}
	documentos_sub.val(sum.toFixed(2));
	sumarMontosSumatoria();
	operarVariacion();
}


function sumarBilletes(){
	var sum = 0;
	sum = sum + parseFloat(billetes.monto200.val());
	sum = sum + parseFloat(billetes.monto100.val());
	sum = sum + parseFloat(billetes.monto50.val());
	sum = sum + parseFloat(billetes.monto20.val());
	sum = sum + parseFloat(billetes.monto10.val());
	sum = sum + parseFloat(billetes.monto5.val());
	sum = sum + parseFloat(billetes.monto1.val());

	billetes.subtotal.val((sum).toFixed(2));
	total.val((parseFloat(monedas.subtotal.val())+parseFloat(billetes.subtotal.val())).toFixed(2));
	sumarMontosSumatoria();
	operarVariacion();
}


function sumarMonedas(){
	var sum = 0;
	sum = sum + parseFloat(monedas.monto1.val());
	sum = sum + parseFloat(monedas.monto050.val());
	sum = sum + parseFloat(monedas.monto025.val());
	sum = sum + parseFloat(monedas.monto010.val());
	sum = sum + parseFloat(monedas.monto005.val());
	sum = sum + parseFloat(monedas.monto001.val());
	
	monedas.subtotal.val((sum).toFixed(2));
	total.val((parseFloat(monedas.subtotal.val())+parseFloat(billetes.subtotal.val())).toFixed(2));
	sumarMontosSumatoria();
	operarVariacion();
}


var txt_btn_buscar_caja;
function buscarCaja(){
	var lblAdvertencia = caja.label_advertencia;
	lblAdvertencia.html('');
	var errores = false;
	
	//validar numero
	if(caja.fecha1.val() && caja.fecha2.val()){
		caja.fecha1.parent().removeClass("has-error");
	}else if(!caja.fecha1.val() && !caja.fecha2.val()){
		caja.fecha1.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe de ingresar ambas fechas');
		lblAdvertencia.html(advertencia);
		caja.fecha1.parent().addClass("has-error");
		errores = true;
	}
	
	if(caja.monto1.val() && caja.monto2.val()){
		caja.monto1.parent().removeClass("has-error");
	}else if(!caja.monto1.val() && !caja.monto2.val()){
		caja.monto1.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe de ingresar ambos montos');
		lblAdvertencia.html(advertencia);
		caja.monto1.parent().addClass("has-error");
		errores = true;
	}
	
	//sino existieron errores se procesa la información ingresada
	if(!errores){
		listarCajas();
	}
}




var table;
function listarCajas(){
		table = $('#example').DataTable({
		 "ajax": 
		 {	
			 "url": "..//caja",
			 "data": 
				 function ( d ) {
				 	d.operacion = 3;
					d.fecha1	= caja.fecha1.val();
					d.fecha2 	= caja.fecha2.val();
					d.monto1 	= caja.monto1.val();
					d.monto2 	= caja.monto2.val();
					
					txt_btn_buscar_caja = caja.boton.html();
					caja.boton.html( ' Espere <i class="fa fa-spinner fa-spin"></i>');
					caja.boton.prop('disabled', true);
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
					"data": "id"
				}, {
					"data": "total_efectivo"
				}, {
					"data": "total_documento"
				}, {
					"data": "sumatoria"
				}, {
					"data": "fondo"
				}, {
					"data": "variacion"
				}, {
					"data": "fecha"
				}
			],
			"initComplete": function (settings, json) {
				if (json.resultado == -100) {
					$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
				}
				caja.boton.html(txt_btn_buscar_caja);
				caja.boton.prop('disabled', false);
				
				$('#example_info').css('display', 'none');
				$('#example tbody tr').each(function () {
					$(this).find('td:eq(0)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 0
					$(this).find('td:eq(1)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 1
				});
				
				 //$("#example").css("display","block");
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




var txt_btn_guardar_caja;
function validarFormModificarCaja(){
	var lblAdvertencia = modal_modificar.label_advertencia;
	lblAdvertencia.html('');
	
	var errores = false;
	var adver = "";
	

	//efectivo
	var tupla = "";
	tupla = tupla + "1," + billetes.cantidad200.val() + "," +billetes.monto200.val() + ";";
	tupla = tupla + "2," + billetes.cantidad100.val() + "," +billetes.monto100.val() + ";";
	tupla = tupla + "3," + billetes.cantidad50.val() + "," +billetes.monto50.val() + ";";
	tupla = tupla + "4," + billetes.cantidad20.val() + ","+ billetes.monto20.val() + ";";
	tupla = tupla + "5," + billetes.cantidad10.val() + ","+ billetes.monto10.val() + ";";
	tupla = tupla + "6," + billetes.cantidad5.val() + "," +billetes.monto5.val() + ";";
	tupla = tupla + "7," + billetes.cantidad1.val() + "," +billetes.monto1.val() + ";";
	
	tupla = tupla + "8," + monedas.cantidad1.val() + ","+ monedas.monto1.val() + ";";
	tupla = tupla + "9," + monedas.cantidad050.val() + ","+ monedas.monto050.val() + ";";
	tupla = tupla + "10," + monedas.cantidad025.val() + ","+ monedas.monto025.val() + ";";
	tupla = tupla + "11," + monedas.cantidad010.val() + ","+ monedas.monto010.val() + ";";
	tupla = tupla + "12," + monedas.cantidad005.val() + ","+ monedas.monto005.val() + ";";
	tupla = tupla + "13," + monedas.cantidad001.val() + ","+ monedas.monto001.val() + ";";
	
	//documentos
	var nit = $('.nit');
	var nombre = $('.nombre');
	var monto = $('.montoFactura');
	
	var doc = "";
	for(var i = 0; i <= nit.length-1; i++){
		if(documentos_sub.val() == 0){
			break;
		}else{
			if(nit[i].value.trim().length == 0 || nombre[i].value.trim().length == 0 ){
				errores = true;
				adver = "Cada documento debe de tener NIT y nombre";
			}
		}
		doc = doc + nit[i].value + "," + nombre[i].value + "," + monto[i].value + ";";
	}
	
	var parametros = {};
	parametros.operacion = 5;
	parametros.id = $('#txtId').val();
	parametros.documentos = doc;
	parametros.efectivo = tupla;
	parametros.sub_billetes = billetes.subtotal.val();
	parametros.sub_monedas = monedas.subtotal.val();
	parametros.total = total.val();
	parametros.total_documentos = documentos_sub.val();
	parametros.sumatoria = sumatoria.val();
	parametros.fondo = fondo.val();
	parametros.variacion = variacion.val();
	parametros.fecha = $("#datepicker3 input").val();
	
	if($("#datepicker3 input").val()){
		$("#datepicker3").removeClass('has-error');
	}else{
		$("#datepicker3").addClass('has-error');
		errores = true;
		adver = 'Debe de ingresar fecha';
	}
	
	if(errores){
		var advertencia = obtenerAdvertencia('alert-danger', adver);
		lblAdvertencia.html(advertencia);
	}else{
		lblAdvertencia.html('');
		confirmacionModificar(parametros);
		//$.post('../caja', parametros, callbackGuardarCaja, 'json');
	}
}



function confirmacionModificar(parametros){
	Messenger.options = {
		    extraClasses: 'messenger-fixed messenger-on-top messenger-on-center',
		}
	var mensajito=Messenger().post({
		extraClasses: 'messenger-fixed messenger-on-center messenger-on-top',
		  message: "<center>¿Desea guardar los cambios?</center>",
		  type:'info',
		  id:1,
		  hideAfter:36000,
		  actions: {
		  	aceptar: {
		 	label: "Aceptar",
		   	action: function(){
		   		txt_btn_guardar_caja = modal_modificar.boton.html();
				modal_modificar.boton.html(' Espere <i class="fa fa-spinner fa-spin"></i>');
				modal_modificar.boton.prop('disabled', true);
		   		$.post('../caja', parametros, callbackValidarFormModificarCaja, 'json');
		   		mensajito.hide();
		      }
		    },
		   	cancelar: {
			 	label: "Cancelar",
			   	action: function(){
			   		var boton = modal_modificar.boton;
			   		boton.html(txt_btn_guardar_caja);
			   		boton.prop("disabled", false);
			   		mensajito.hide();
				}
			}
			//adicionar botones
		  }
	});
}


function callbackValidarFormModificarCaja(response){
	console.log(response);
	var tiempo = 10;
	var boton = modal_modificar.boton;
	boton.html(txt_btn_guardar_caja);
	boton.prop("disabled", false);
	
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion, tiempo);
			break;
			
		case 1:
			//limpiarFormCheque();
			//setFormValues(response.caja[0], response.documento, response.efectivo);
			//$('#modal-modificar-caja .modal-title i').remove();
			desplegarMensaje("success", response.descripcion, tiempo);
			table.ajax.reload( null, false );
			caja.boton.html(txt_btn_buscar_caja);
			caja.boton.prop('disabled', false);
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

var txt_btn_modificar;
function cargarModificarCaja(data){
	limpiarFormModificar();
	$('#modal-modificar-caja .modal-title').append(' <i class="fa fa-spinner fa-spin"></i>');
	$('#modal-modificar-caja').modal();
	
	txt_btn_modificar = modal_modificar.boton.html();
	modal_modificar.boton.html( ' Espere <i class="fa fa-spinner fa-spin"></i>');
	modal_modificar.boton.prop('disabled', true);
	
	var parametros = {};
	parametros.operacion = 4;
	parametros.id = data.identificador;
	$.post('../caja',parametros,callbackCargarModificarCaja,'json');
}


function callbackCargarModificarCaja(response){
	console.log(response);
	var tiempo = 10;
	var boton = modal_modificar.boton;
	boton.html(txt_btn_modificar);
	boton.prop("disabled", false);
	
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion, tiempo);
			break;
			
		case 1:
			//limpiarFormCheque();
			setFormValues(response.caja[0], response.documento, response.efectivo);
			$('#modal-modificar-caja .modal-title i').remove();
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



function setFormValues(caja, documento, efectivo){
	modal_modificar.id.val(caja.id);
	fondo.val(caja.fondo);
	sumatoria.val(caja.sumatoria);
	total.val(caja.total_efectivo);
	documentos_sub.val(caja.total_documento);
	variacion.val(caja.variacion);
	$('#datepicker3').datepicker('update', caja.fecha);
	
	var c = efectivo.length;
	var billetes_subtotal = 0;
	var monedas_subtotal = 0;
	
	for(var i = 0; i <= c -1; i++){
		if(parseInt(efectivo[i].tipo) == 1){billetes.cantidad200.val(efectivo[i].cantidad);billetes.monto200.val(efectivo[i].monto);billetes_subtotal=billetes_subtotal+parseFloat(efectivo[i].monto);}
		else if(parseInt(efectivo[i].tipo) == 2){billetes.cantidad100.val(efectivo[i].cantidad);billetes.monto100.val(efectivo[i].monto);billetes_subtotal=billetes_subtotal+parseFloat(efectivo[i].monto);}
		else if(parseInt(efectivo[i].tipo) == 3){billetes.cantidad50.val(efectivo[i].cantidad);billetes.monto50.val(efectivo[i].monto);billetes_subtotal=billetes_subtotal+parseFloat(efectivo[i].monto);}
		else if(parseInt(efectivo[i].tipo) == 4){billetes.cantidad20.val(efectivo[i].cantidad);billetes.monto20.val(efectivo[i].monto);billetes_subtotal=billetes_subtotal+parseFloat(efectivo[i].monto);}
		else if(parseInt(efectivo[i].tipo) == 5){billetes.cantidad10.val(efectivo[i].cantidad);billetes.monto10.val(efectivo[i].monto);billetes_subtotal=billetes_subtotal+parseFloat(efectivo[i].monto);}
		else if(parseInt(efectivo[i].tipo) == 6){billetes.cantidad5.val(efectivo[i].cantidad);billetes.monto5.val(efectivo[i].monto);billetes_subtotal=billetes_subtotal+parseFloat(efectivo[i].monto);}
		else if(parseInt(efectivo[i].tipo) == 7){billetes.cantidad1.val(efectivo[i].cantidad);billetes.monto1.val(efectivo[i].monto);billetes_subtotal=billetes_subtotal+parseFloat(efectivo[i].monto);}
		else if(parseInt(efectivo[i].tipo) == 8){monedas.cantidad1.val(efectivo[i].cantidad);monedas.monto1.val(efectivo[i].monto);monedas_subtotal=monedas_subtotal+parseFloat(efectivo[i].monto);}
		else if(parseInt(efectivo[i].tipo) == 9){monedas.cantidad050.val(efectivo[i].cantidad);monedas.monto050.val(efectivo[i].monto);monedas_subtotal=monedas_subtotal+parseFloat(efectivo[i].monto);}
		else if(parseInt(efectivo[i].tipo) == 10){monedas.cantidad025.val(efectivo[i].cantidad);monedas.monto025.val(efectivo[i].monto);monedas_subtotal=monedas_subtotal+parseFloat(efectivo[i].monto);}
		else if(parseInt(efectivo[i].tipo) == 11){monedas.cantidad010.val(efectivo[i].cantidad);monedas.monto010.val(efectivo[i].monto);monedas_subtotal=monedas_subtotal+parseFloat(efectivo[i].monto);}
		else if(parseInt(efectivo[i].tipo) == 12){monedas.cantidad005.val(efectivo[i].cantidad);monedas.monto005.val(efectivo[i].monto);monedas_subtotal=monedas_subtotal+parseFloat(efectivo[i].monto);}
		else if(parseInt(efectivo[i].tipo) == 13){monedas.cantidad001.val(efectivo[i].cantidad);monedas.monto001.val(efectivo[i].monto);monedas_subtotal=monedas_subtotal+parseFloat(efectivo[i].monto);}
	}
	billetes.subtotal.val(billetes_subtotal.toFixed(2));
	monedas.subtotal.val(monedas_subtotal.toFixed(2));
	
	for(var i = $('.nit').length-1; i>=1; i--){
		$( "i.remove" ).trigger( "click" );
	}
	
	var c = documento.length;
	for(var i = 0; i < c -1; i++){
		$( "i.add" ).trigger( "click" );
		$(".nit").mask("999999-9");
	}
	$(".nit").mask("999999-9");
	var nit = $('.nit');
	var nombre = $('.nombre');
	var monto = $('.montoFactura');
	
	for(var i = 0; i <= c - 1; i++){
		nit[i].value=documento[i].nit;
		nombre[i].value=documento[i].nombre;
		monto[i].value=documento[i].monto;
	}
}


function limpiarFormModificar(){
	$('input').val('');
	modal_modificar.label_advertencia.html('');
	$('#modal-modificar-caja div').removeClass('has-error')
}



var txt_btn_datos;
function mostrarVistaDatos(data){
	var parametros ={};
	parametros.operacion = 4;
	parametros.id = dataRow.identificador;
	txt_btn_datos = $("#btn-ver").html();
	$("#btn-ver").html('<b> Espere <i class="fa fa-spinner fa-spin"></i></b>');
	$("#btn-ver").prop("disabled", true);
	$.post('../caja',parametros,callbackMostrarVistaDatos,'json');
	
}


function textRow(){
	var r = "";
	r = r + '<div class="row">';
	r = r + '<div class="col-sm-1"></div>';
	r = r + '<div class="col-sm-3 col-xs-4">';
	r = r + '<div class="form-group ">';
	r = r + '<input type="text" class="form-control nit" id="txtNit1" placeholder="..." maxlength="15" onkeypress="return permite(event, \'solo_num\')">';
	r = r + '</div>';
	r = r + '</div>';
	r = r + '<div class="col-sm-4 col-xs-4">';
	r = r + '<div class="form-group ">';
	r = r + '<input type="text" class="form-control nombre" id="txtNombre1" placeholder="..." maxlength="15" onkeypress="return permite(event, \'num_car\')">';
	r = r + '</div>';
	r = r + '</div>';
	r = r + '<div class="col-sm-3 col-xs-3">';
	r = r + '<div class="form-group">';
	r = r + '<input type="text" class="form-control montoFactura" id="txtMonto1" placeholder="..." maxlength="15" onkeypress="return permite(event, \'num\')">';
	r = r + '</div>';
	r = r + '</div>';
	r = r + '<div class="col-sm-1"><i id="addRow1"style="  border-radius: 50%;padding: 0px;  width: 20px;  height: 15px; top: 17px;float: left;margin-left: -20px;cursor:pointer;"class="glyphicon glyphicon-plus add"></i>      <i id="removeRow1"style="  border-radius: 50%;padding: 0px;  width: 20px;  height: 15px; top: 1px;float: left;margin-left: -21px;cursor:pointer;"class="glyphicon glyphicon-minus remove"></i></div>';

	r = r + '</div>';
	
	return r;
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
			cargarVistaImpresion2(response);
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

/*********************************************************************
 * @author kcardona
 * @since 10/09/2015
 * @return String
 * @Descripcion Devuelve una cadena en la cual se imprime los botones
 * de opciones de cada tupla de la tabla principal.
 ********************************************************************/
function format(d) {
	dataRow.identificador 	= d.id;
	
	var ver_datos 	= '<td><a data-toggle="modal" data-target="#basicModal"  href="#" onclick="mostrarVistaDatos(dataRow)"><button id="btn-ver" class="btn btn-green" ><i class="fa fa-pencil-square-o"></i> Ver datos </button></a></td>';
	var descartar = '';
	if(permi){
		descartar 	= '<td><a onclick="cargarModificarCaja(dataRow);"><button id="btn-modific<r" class="btn btn-blue" ><i class="fa fa-share-square-o"></i> Modificar </button></a></td>';
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

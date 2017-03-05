var dataRow; //variable para guardar info de cada tupla de la tabla
var billetes;
var monedas;
var total;
var documentos_sub;
var sumatoria;
var fondo;
var variacion;
var botones;
$(inicializar);

function inicializar(){
	botones = {};
	dataRow = {};
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
		
		$(".montoFactura").on('keyup', function(e){
			inputControl($(this),'float');
	    });
	});
	
	$(".montoFactura").on('keyup', function(e){
		inputControl($(this),'float');
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
	
	cargarValorParametro();
	
	botones.guardar = $('#btn-guardar');
	botones.impresion = $('#btn-ver');
	botones.impresion.click(function(){
		mostrarVistaDatos();
	});
	botones.impresion.prop('disabled', true);
	botones.guardar.click(function(){
		var ob = $('.cantidad1, .cantidad2, .montoFactura');
		for(var i = 0; i <= ob.length-1; i++){
			if(ob[i].value.trim().length == 0){
				ob[i].value = 0;
			}
		}
		guardarCaja();	
	});
	
	$('#datepicker2').datepicker({
		format: "dd/mm/yyyy",
	    language: "es",
	    todayBtn: "linked",
	    todayHighlight: true,
	    autoclose:true,
	    orientation: "left"
    });
	
	$("#datepicker2 input").mask("99/99/9999");
}

var txt_boton;
function guardarCaja(){
	
	var errores = false;
	var adver = "";
	var parametros = {};
	parametros.operacion = 2;

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
	
	parametros.documentos = doc;
	parametros.efectivo = tupla;
	parametros.sub_billetes = billetes.subtotal.val();
	parametros.sub_monedas = monedas.subtotal.val();
	parametros.total = total.val();
	parametros.total_documentos = documentos_sub.val();
	parametros.sumatoria = sumatoria.val();
	parametros.fondo = fondo.val();
	parametros.variacion = variacion.val();
	parametros.fecha = $("#datepicker2 input").val();
	
	if($("#datepicker2 input").val()){
		$("#datepicker2").removeClass('has-error');
	}else{
		$("#datepicker2").addClass('has-error');
		errores = true;
		adver = 'Debe de ingresar fecha';
	}
	
	if(errores){
		var advertencia = obtenerAdvertencia('alert-danger', adver);
		$('#lblAdvertencia').html(advertencia);
	}else{
		$('#lblAdvertencia').html('');
		txt_boton = botones.guardar.html();
		botones.guardar.html(' Espere <i class="fa fa-spinner fa-spin"></i>');
		botones.guardar.prop('disabled', true);
		$.post('../caja', parametros, callbackGuardarCaja, 'json');
	}
}


function callbackGuardarCaja(response){
	console.log(response);
	botones.guardar.html(txt_boton);
	botones.guardar.prop('disabled', false);
	botones.impresion.prop('disabled', false);
	
	switch(parseInt(response.resultado)){
	
	case -1:
		desplegarMensaje("error", response.descripcion, 300);
		break;
		
	case -2:
		desplegarMensaje("error", response.descripcion, 300);
		break;
		
	case 1:
		desplegarMensaje("success", response.descripcion, 7);
		dataRow.identificador = response.id;
		break;
		
	case -100:
		//Caduco
		$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
		break;
		
	case -101:
		desplegarMensaje("error", response.descripcion, 300);
		break;
	
	}
}

function cargarValorParametro(){
	var parametros = {};
	parametros.operacion = 1;
	parametros.id = 15;
	$.post('../caja', parametros, callbackCargarValorParametro, 'json');
}


function callbackCargarValorParametro(response){
	console.log(response);
	switch(parseInt(response.resultado)){
	
	case -1:
		desplegarMensaje("error", response.descripcion, 300);
		break;
		
	case -2:
		desplegarMensaje("error", response.descripcion, 300);
		break;
		
	case 1:
		fondo.val(response.data[0].valor);
		variacion.val(response.data[0].valor);
		console.log(response.data[0].valor);
		break;
		
	case -100:
		//Caduco
		$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
		
		break;
		
	case -101:
		desplegarMensaje("error", response.descripcion, 300);
		break;
	
	}
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



function textRow(){
	var r = "";
	r = r + '<div class="row">';
	r = r + '<div class="col-sm-1"></div>';
	r = r + '<div class="col-sm-3 col-xs-4">';
	r = r + '<div class="form-group ">';
	r = r + '<input type="text" class="form-control nit" id="txtNit1" placeholder="..." maxlength="15" onkeypress="return permite(event, \'solo_num\')">';
	r = r + '</div>';
	r = r + '</div>';
	r = r + '<div class="col-sm-3 col-xs-4">';
	r = r + '<div class="form-group ">';
	r = r + '<input type="text" class="form-control nombre" id="txtNombre1" placeholder="..." maxlength="15" onkeypress="return permite(event, \'num_car\')">';
	r = r + '</div>';
	r = r + '</div>';
	r = r + '<div class="col-sm-1"></div>';
	r = r + '<div class="col-sm-3 col-xs-3">';
	r = r + '<div class="form-group">';
	r = r + '<input type="text" class="form-control montoFactura" id="txtMonto1" placeholder="..." maxlength="15" onkeypress="return permite(event, \'num\')">';
	r = r + '</div>';
	r = r + '</div>';
	r = r + '<div class="col-sm-1"><i id="addRow1"style="  border-radius: 50%;padding: 0px;  width: 20px;  height: 15px; top: 17px;float: left;margin-left: -20px;cursor:pointer;"class="glyphicon glyphicon-plus add"></i>      <i id="removeRow1"style="  border-radius: 50%;padding: 0px;  width: 20px;  height: 15px; top: 1px;float: left;margin-left: -21px;cursor:pointer;"class="glyphicon glyphicon-minus remove"></i></div>';
	r = r + '</div>';
	
	return r;
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
	
	console.log(sum);
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
	
	console.log(sum);
	monedas.subtotal.val((sum).toFixed(2));
	total.val((parseFloat(monedas.subtotal.val())+parseFloat(billetes.subtotal.val())).toFixed(2));
	sumarMontosSumatoria();
	operarVariacion();
}



var txt_btn_datos;
function mostrarVistaDatos(){
	var parametros ={};
	parametros.operacion = 4;
	parametros.id = dataRow.identificador;
	txt_btn_datos = $("#btn-ver").html();
	$("#btn-ver").html('<b> Espere <i class="fa fa-spinner fa-spin"></i></b>');
	$("#btn-ver").prop("disabled", true);
	console.log(parametros);
	$.post('../caja',parametros,callbackMostrarVistaDatos,'json');
	
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
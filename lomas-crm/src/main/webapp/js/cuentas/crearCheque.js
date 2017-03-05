var dataRow; //variable para guardar info de cada tupla de la tabla
var widgets;
var main;
var guardar_cheque;
var archivo_subido;
var txtFiltro;
$(inicializar);

function inicializar(){
	Dropzone.autoDiscover = false;
	dataRow = {};
	main = {};
	main.modal_nuevo = $('#modal-nuevo-cheque');
	widgets = {};
	widgets.boton_nuevo = $('#btn-nuevo');
	widgets.boton_nuevo.click(function(){limpiarFormCheque();main.modal_nuevo.modal();});
	
	archivo_subido = false;
	guardar_cheque = {};
	guardar_cheque.numero 				= $('#txtNumero');
	guardar_cheque.nombre 				= $('#txtNombre');
	guardar_cheque.cantidad 			= $('#txtCantidad');
	guardar_cheque.motivo 				= $('#txtMotivo');
	guardar_cheque.lugar				= $('#txtLugar');
	guardar_cheque.fecha				= $('#txtFecha');
	guardar_cheque.label_advertencia 	= $('#lblAdvertencia');
	guardar_cheque.boton = $('#btn-guardar');
	guardar_cheque.boton_ver = $('#btn-ver');
	guardar_cheque.boton_ver.prop('disabled', true);
	
	guardar_cheque.cantidad.on("keyup",function(){
		inputControl($(this),'float');
	});
	
	$( "input[type='text']" ).on('input',function() {
		guardar_cheque.boton_ver.prop('disabled', true);
	});
	
	guardar_cheque.boton_ver.click(function(){
		mostrarVistaDatos();
	});
	
	$('.portlet-body input[type="text"]').keyup(function(e){
	    if(e.keyCode == 13)
	    {
	    	guardarCheque1();
	    }
	});
	
	guardar_cheque.imagen_cheque = new Dropzone('#txtFile', {
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
	
	guardar_cheque.imagen_cheque.on('sending', function(file, xhr, formData) {
		formData.append('operacion', 6);
        formData.append('numero', guardar_cheque.numero.val());
        formData.append('lugar', guardar_cheque.lugar.val());
        formData.append('fecha', guardar_cheque.fecha.val());
        formData.append('nombre', guardar_cheque.nombre.val());
        formData.append('cantidad', guardar_cheque.cantidad.val());
        formData.append('motivo', guardar_cheque.motivo.val());
    });
	
	guardar_cheque.imagen_cheque.on('addedfile', function(file, xhr, formData) {
		//si no tiene un archivo agregado
		if (this.files[0]==null){
			archivo_subido = false;
		}else{
			archivo_subido = true;
		}
    });
	
	guardar_cheque.imagen_cheque.on('removedfile', function(file, xhr, formData) {
		//si no tiene un archivo agregado
		if (this.files[0]==null){
			archivo_subido = false;
		}else{
			archivo_subido = true;
		}
    });
	
	guardar_cheque.imagen_cheque.on('success', function(file, response) {
		callbackGuardarCheque(response);
    });
	
	guardar_cheque.imagen_cheque.on('error', function(file, response) {
		callbackGuardarCheque(response);
    });
	
	
	guardar_cheque.boton.click(function(){
		guardarCheque1();
	});
	
	txtFiltro = $('#txtFiltro');
	txtFiltro.val(getDate());
	$('#datepicker1').datepicker({
		format: "dd/mm/yyyy",
	    language: "es",
	    todayBtn: "linked",
	    todayHighlight: true,
	    autoclose:true
    });
	$('#datepicker2').datepicker({
		format: "dd/mm/yyyy",
	    language: "es",
	    todayBtn: "linked",
	    todayHighlight: true,
	    autoclose:true
    });
	
	$('#datepicker2 input[type="text"]').blur(function(){
		if(!$(this).val()){
			$('#datepicker2').datepicker('update', getDate());
		}
	});
}


var txt_btn_guardar_cheque;
function guardarCheque1(){
	var lblAdvertencia = guardar_cheque.label_advertencia;
	lblAdvertencia.html('');
	var errores = false;
	
	//validar numero
	if(guardar_cheque.numero.val()){
		guardar_cheque.numero.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar un número de cheque');
		lblAdvertencia.html(advertencia);
		guardar_cheque.numero.parent().addClass("has-error");
		errores = true;
	}
	//lugar
	if(guardar_cheque.lugar.val()){
		guardar_cheque.lugar.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar un lugar');
		lblAdvertencia.html(advertencia);
		guardar_cheque.lugar.parent().addClass("has-error");
		errores = true;
	}
	//fecha
	if(guardar_cheque.fecha.val()){
		guardar_cheque.fecha.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar una fecha');
		lblAdvertencia.html(advertencia);
		guardar_cheque.fecha.parent().addClass("has-error");
		errores = true;
	}
	//validar nombre
	if(guardar_cheque.nombre.val()){
		guardar_cheque.nombre.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar a nombre de quien fue emitido el cheque');
		lblAdvertencia.html(advertencia);
		guardar_cheque.nombre.parent().addClass("has-error");
		errores = true;
	}
	//validar cantidad
	if(guardar_cheque.cantidad.val()){
		guardar_cheque.cantidad.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar una cantidad');
		lblAdvertencia.html(advertencia);
		guardar_cheque.cantidad.parent().addClass("has-error");
		errores = true;
	}
	
	if($.isNumeric( guardar_cheque.cantidad.val() )){
		guardar_cheque.cantidad.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar un monto válido');
		lblAdvertencia.html(advertencia);
		guardar_cheque.cantidad.parent().addClass("has-error");
		errores = true;
	}
		//validar motivo
	if(guardar_cheque.motivo.val()){
		guardar_cheque.motivo.parent().removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar un motivo');
		lblAdvertencia.html(advertencia);
		guardar_cheque.motivo.parent().addClass("has-error");
		errores = true;
	}
	
	//validamos imagen
	if(archivo_subido == false){
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe agregar la imagen escaneada del cheque');
		lblAdvertencia.html(advertencia);
		errores = true;
	}
	
	//sino existieron errores se procesa la informacion ingresada
	if(!errores){
		
		var parametros = {};
		parametros.operacion = 2;
		parametros.numero = guardar_cheque.numero.val();
		
		$.post('../cheque', parametros, callbackGuardarCheque1, 'json');
		txt_btn_guardar_cheque = guardar_cheque.boton.html();
		guardar_cheque.boton.html( ' Espere <i class="fa fa-spinner fa-spin"></i>');
		guardar_cheque.boton.prop("disabled", true);
	}
}



function callbackGuardarCheque1(response){
	switch(response.resultado){
	case 1:
		if(response.resp == 1){
			guardar_cheque.imagen_cheque.processQueue();
		}else{
			var boton = guardar_cheque.boton;
			desplegarMensaje("error", response.descripcion, 300);
			var advertencia = obtenerAdvertencia(' alert-danger', response.descripcion);
			guardar_cheque.label_advertencia.html(advertencia);
			guardar_cheque.numero.parent().addClass("has-error");
			boton.html(txt_btn_guardar_cheque);
			boton.prop("disabled", false);
		}
		break;
	}
}


function callbackGuardarCheque(response){
	console.log(response);
	var boton = guardar_cheque.boton;
	
	
	switch(response.resultado){
	
	case -1:
		desplegarMensaje("error", response.descripcion, 300);
		boton.html(txt_btn_guardar_cheque);
		boton.prop("disabled", false);
		break;
		
	case -2:
		desplegarMensaje("error", response.descripcion, 300);
		var advertencia = obtenerAdvertencia(' alert-danger', response.descripcion);
		guardar_cheque.label_advertencia.html(advertencia);
		guardar_cheque.numero.parent().addClass("has-error");
		boton.html(txt_btn_guardar_cheque);
		boton.prop("disabled", false);
		break;
		
	case 1:
		desplegarMensaje("success", response.descripcion, 10);
		boton.html(txt_btn_guardar_cheque);
		setTimeout(function(){
			guardar_cheque.imagen_cheque.removeAllFiles();
			boton.prop("disabled", false);
		}, 1000);
		dataRow = response.valor;
		guardar_cheque.boton_ver.prop('disabled', false);
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


function limpiarFormCheque(){
	guardar_cheque.numero.val('');
	guardar_cheque.nombre.val('');
	guardar_cheque.lugar.val('');
	guardar_cheque.fecha.val('');
	guardar_cheque.cantidad.val('');
	guardar_cheque.motivo.val('');
	guardar_cheque.numero.val('');
	guardar_cheque.imagen_cheque.removeAllFiles();
	guardar_cheque.label_advertencia.html('');
	guardar_cheque.numero.parent().removeClass("has-error");
	guardar_cheque.lugar.parent().removeClass("has-error");
	guardar_cheque.fecha.parent().removeClass("has-error");
	guardar_cheque.nombre.parent().removeClass("has-error");
	guardar_cheque.cantidad.parent().removeClass("has-error");
	guardar_cheque.motivo.parent().removeClass("has-error");
}



var txt_btn_datos;
function mostrarVistaDatos(){
	var parametros = {}
	parametros.operacion = 3;
	parametros.id  = dataRow;
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
			console.log(response);
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
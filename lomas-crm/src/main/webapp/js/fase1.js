/*
 * Funcionalidad exclusiva de la fase 4
 * */

/*
 * Establecer fase. Es el primer método que se llama al cargar la fase
 * */

$(inicializarFase);
$('#btnGuardar').click(guardarFase);

function inicializarFase(){
	
	//Inicializar Messenger
	Messenger.options = {extraClasses : 'messenger-fixed messenger-on-top',theme : 'future'};
	_deshabilitarControles();
	
	//Llamada al servidor para obtener fase
	var parametros={};
	parametros.operacion=2;
	parametros.ficha= ficha;
	$.post('/clinicas-crm/ficha_clinica',parametros,callbackCargar,'json');
}



function callbackCargar(respuesta){
	switch(respuesta.resultado) {
		case 1:
			_habilitarControles();
			restaurarFase(respuesta);
			validarEstadoFase(respuesta);
			break;
		case -102:
			notificarError();
			break;
		default:
  
	}
}


function restaurarFase(respuesta){
	//Cargar info de los campos de texto
	$("#textArea1").val(respuesta.motivoConsulta);
	$("#textArea2").val(respuesta.historialEnfermedad);
	$("#textArea3").val(respuesta.historialMedico);
	$("#textArea4").val(respuesta.historialOdontologico);
	$("#textArea5").val(respuesta.precauciones);
	//$("#btnGuardar").prop("disabled", false);
	//Cargar padecimientos guardados
	$.each(respuesta.padecimientos,function(indice,valor) {
		$('#chk'+valor).prop("checked",true);
	});
	
	//Cargar el id de la fase
	fa_fase=respuesta.fa_fase;
	
	//Cargar el dolor dentario
	respuesta.dolorDentario.forEach(function(nodo) {
	    $('#tablaPiezas tr:last').after(fila);
	    $('#tablaPiezas tr:last').find('.selectIntensidad').first().val(nodo.intensidad);
	    $('#tablaPiezas tr:last').find('.selectDuracion').first().val(nodo.duracion);
	    $('#tablaPiezas tr:last').find('.selectInicio').first().val(nodo.inicio);
	    $('#tablaPiezas tr:last').find('.selectFrecuencia').first().val(nodo.frecuencia);
	    $('#tablaPiezas tr:last').find('.piezaSelect').first().html(nodo.cuadrante+"."+nodo.indice); 
	});
}

var _controles=$('input,textarea,select,button').not('#boton-opciones-ficha');

//No se puede editar nada de la fase
function _deshabilitarControles(){
	$("#btnGuardar").prop("disabled", true).html('Espere <i class="fa fa-spinner fa-spin"></i>');
	_controles.not('#select-verificacion').prop('disabled',true);
	$('.close').slideToggle();
}

//Habilitar la edición de la fase
function _habilitarControles(){
	$("#btnGuardar").prop("disabled", false).html('Guardar');
	_controles.prop('disabled',false);
	$('.close').slideToggle();
}

//Guardar la fase
	function guardarFase(){
		_deshabilitarControles();
		//Preparar los parámetros
		var parametros={};
		parametros.str1=$("#textArea1").val();
		parametros.str2=$("#textArea2").val();
		parametros.str3=$("#textArea3").val();
		parametros.str4=$("#textArea4").val();
		parametros.str5=$("#textArea5").val();
		parametros.operacion=1;
		parametros.padecimientos="";
		parametros.fc_ficha_clinica=ficha;
			
		//Se leen cada uno de los checkbox
		for (i = 1; i < 21; i++) { 
			if($('#chk'+i).is(':checked')){
			parametros.padecimientos+=i+",";					
			}
		}	
			
		var stack=[];
		var piezasInvalidas=false;
		$('.nodoDolorDentario').each( function() {
			var nodo={};	
			var pieza=$(this).find('.piezaSelect').first().html();
			if(pieza!=".."){
			pieza=pieza.split('.');
			nodo.cuadrante=pieza[0];
			nodo.indice=pieza[1];
			nodo.intensidad=$(this).find('.selectIntensidad').first().val();
			nodo.duracion=($(this).find('.selectDuracion').first().val());
			nodo.inicio=($(this).find('.selectInicio').first().val());
			nodo.frecuencia=($(this).find('.selectFrecuencia').first().val());
			stack.push(nodo);
			}else{
				piezasInvalidas=true;
			}	
		});
			parametros.dolorDentario=JSON.stringify(stack);
			//Llamada al server
			$.post('/clinicas-crm/ficha_clinica',parametros,callbackGuardarFase,'json');
			if(piezasInvalidas)
				 obtenerAdvertencia('error',"Las piezas inválidas no se guardaron. Corríjalas");
}
		
		
		
		
		
		
		function cargarModalPiezas(botonParametro){
			//Cargar el modal
			if($(botonParametro).html()!=".."){
				//Restaurar valor 
				var res = $(botonParametro).html().split(".");
				var quad=res[0];
				var pz=res[1];
				$("#labelPieza").html(quad+"."+pz);
				$("#selectCuadrante").val(quad);
				$("#selectCuadrante").change();
				$("#selectPieza").val(pz);
				$("#labelPieza").val(quad+"."+pz);
			}else{
				$("#selectCuadrante").val('1');
				$("#selectCuadrante").change();
				$("#labelPieza").html("&nbsp;");
			}
			$('#modalPieza').modal();
			botonPieza=botonParametro;
		}
		
		
		function obtenerAdvertencia(clase, mensaje) {
			Messenger().post({message:'<center>'+ mensaje+'</center>',type: clase,showCloseButton: true});
		}
		
		$( "#selectCuadrante" ).change(function() {
			 var cuadrante=parseInt(($( "#selectCuadrante" ).val()));
			 var piezas=0;
			 if (cuadrante>4)
				 piezas=5;
			 else
				 piezas=8;	
			 $('#selectPieza')
			    .find('option')
			    .remove()
			    .end();
			 $('#selectPieza').append('<option value="0">...</option>');
			 for (x = 1; x < (piezas+1); x++) { 
				 $('#selectPieza').append('<option value="'+x+'" >'+x+'</option>');
				}
		});
		
		$("#selectPieza").change(function() {
			
			if($( "#selectPieza" ).val()=="0"){
				$("#labelPieza").html("&nbsp;");
			}else{
				$("#labelPieza").html($( "#selectCuadrante" ).val()+"."+$( "#selectPieza" ).val());
			}
			
		});
		
		$('#btnGuardarPieza').click(function(e){
			if($("#selectPieza").val()=="0"){
				obtenerAdvertencia("error", "Pieza inválida");
			}else{	
				var encontrada=false;
				$(".piezaSelect").each(function() {
				   if($(this).html()==$("#labelPieza").html())
					   {
					   //Determinar que no es la misma pieza
					   if(botonPieza!=this){
						   encontrada=true; 
					   }
					   return false;
					   }
					
				});
				if(encontrada){
					obtenerAdvertencia("error", "Pieza repetida");
				}else{
					$(botonPieza).html($("#labelPieza").html());
					$('#modalPieza').modal('hide');	
				}
			}
			e.preventDefault();
		});
		
		//Evitar que se pueda cambiar después de solicitar revisión
		function bloquearFase(){
			_deshabilitarControles();
			$('#agregarPieza').remove();
			$('.close').remove();
			$('#btnGuardar').unbind('click');
			$('#btnGuardar').removeClass('btn-default');
			$('#btnGuardar').addClass('btn-success');
		}
		

		
		
/*
 * Componentes para la UI
 * */		
var fila=
'<tr class="nodoDolorDentario">'+
		'<td ><button type="button" class="btn btn-white piezaSelect" onclick="cargarModalPiezas(this);">..</button></td>'+
'<td><select class="form-control selectIntensidad">'+
		'<option value="0">...</option>'+
		'<option value="1">Leve</option>'+
		'<option value="2">Moderada</option>'+
		'<option value="3">Severa</option>'+
'</select></td>'+
'<td><select class="form-control selectDuracion">'+
		'<option value="0">...</option>'+
		'<option value="1">Corta</option>'+
		'<option value="2">Prolongada</option>'+
'</select></td>'+
'<td><select class="form-control selectInicio">'+
		'<option value="0">...</option>'+
		'<option value="1">Espotáneo</option>'+
		'<option value="2">Provocado</option>'+
'</select></td>'+
'<td><select class="form-control selectFrecuencia">'+
		'<option value="0">...</option>'+
		'<option value="1">Constante</option>'+
		'<option value="2">Intermitente</option>'+
'</select></td>'+
'<td><a   class="close"><i class="fa fa-minus-circle"></i></a></td>'+
'</tr>';

function eliminarRegistroHOA(tr){
	tr.toggleClass('danger');
	_deshabilitarControles();
	var mensajito=Messenger().post({
		  message: "<center>¿Está seguro que desea eliminar esta fila?<br>(Esta acción no se puede deshacer)</center>",
		  type:'info',
		  showCloseButton:true,
		  id:1,
		  hideAfter:36000,
		  actions: {
		  	aceptar: {
		 	label: "Eliminar",
		   	action: function(){
		    	tr.slideToggle(function(){tr.remove()});
		    	mensajito.hide();
		    	guardarFase();
		    	_habilitarControles();
		      }
		    },
		   	cancelar: {
			 	label: "Cancelar",
			   	action: function(){
			    mensajito.hide();
			    tr.toggleClass('danger');
			    _habilitarControles();
				}
			}
			//adicionar botones
		  }
	});
	
	
}

$("#agregarPieza").click(function(){
	$('#tablaPiezas tr:last').after(fila);
});
$('#tablaPiezas').on('click', '.close', function(e){
	   //$(this).closest('tr').remove();
		eliminarRegistroHOA($(this).closest('tr'));
});

		
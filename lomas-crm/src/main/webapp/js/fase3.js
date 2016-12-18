var stack=[];
var piecita;
var botonPlus;
var galeria;
var botonMiniatura;
$(inicializarFase);
$('#btnGuardar').click(guardarFase);
function inicializarFase(){
	//Botones de la galería
	$('#grupo-botones a').hide();
	
	
	//Inicializar Messenger
	Messenger.options = {extraClasses : 'messenger-fixed messenger-on-top',theme : 'future'};
	//Agregar al select
	for (i = 0; i < padecimientos.length; i++)  
		$('#select-padecimientos').append('<option data-tipo="'+padecimientos[i].tipo+'" value="'+padecimientos[i].id+'">'+padecimientos[i].nombre+'</option>');
	
	$('#select-padecimientos').multiselect({filterPlaceholder:'Buscar',enableFiltering:true,enableCaseInsensitiveFiltering:true});
	
	
	//Inicializar el file uploader
	$('#fileupload').fileupload({
        url: '/clinicas-crm/guardararchivo?ficha='+ficha,
        dataType: 'json',
        done: function (e, data) {
		$(".progress-bar").css('width','0%');
		switch(data.result.resultado){
		case 1:
			data.result.files.forEach(function(nodo) {
	        	//Mandar a guardar la imagencindia
		        cargarMiniatura(nodo.name,nodo.id);
		        agregarImagen({nombre:nodo.name,id:nodo.id});
		        cargarGaleria();
	        });
			break;
		case -100:
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
		default:
		}          
          
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .progress-bar').css(
                'width',
                progress + '%'
            );
            
            $('#progress .progress-bar').html(progress+" %");
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');
	//Inicializar file uploader
	
	
	_deshabilitarControles();
	//Llamada al servidor
	var parametros={};
	parametros.ficha= ficha;
	parametros.operacion=8;
	$.post('/clinicas-crm/ficha_clinica',parametros,callbackCargar,'json');
	
}

function callbackEliminarFotografia(respuesta){
	switch(respuesta.resultado){
	case 1:
		obtenerAdvertencia(respuesta.tipo, respuesta.descripcion);
		eliminarImagen();
		break;
	case -100:
		notificarSesionExpirada();
		break;	
	default:
		obtenerAdvertencia(respuesta.tipo, respuesta.descripcion);
		
	}
}

function eliminarImagen(){
	botonMiniatura.fadeOut(function(){$(this).remove()});
	$("#links").find("[data-imagen-id='"+botonMiniatura.data('id')+"']" ).remove();
	cargarGaleria();
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

//Imágenes
function ui_mostrarModalImagenes(){
	$('#modal-imagenes').modal({"backdrop":"static","keyboard":false});
}


function agregarImagen(imagen){
	$("#links").append('<a data-imagen-id="'+imagen.id+'" href="/clinicas-crm/imagenes?t=2&f='+imagen.nombre+'">img src="/clinicas-crm/imagenes?t=2&f=m_'+imagen.nombre+'" ></a>');
	
}




function restaurarFase(respuesta){
	//Restaurar tabla de padecimientos roentgenológicos
	console.log(respuesta);
	for (x = 0; x < respuesta.padecimientos.length; x++) {
		fnAgregarFila(respuesta.padecimientos[x].descripcion,respuesta.padecimientos[x].tipo,respuesta.padecimientos[x].valor);
	}
	
	for (x = 0; x < respuesta.imagenes.length; x++){
		cargarMiniatura('m_'+respuesta.imagenes[x].nombre,respuesta.imagenes[x].id);
		agregarImagen(respuesta.imagenes[x]);
	}
	

	

	
	/*Inicializar la galería*/

	cargarGaleria();
	
	
}



function bloquearFase(){
	_deshabilitarControles();
	$('#btnGuardar').unbind('click');
	$('#btnGuardar').removeClass('btn-default');
	$('#btnGuardar').addClass('btn-success');
}


function cargarGaleria(){
	
	document.getElementById('links').onclick = function(event) {
		event = event || window.event;
		var target = event.target || event.srcElement, link = target.src ? target.parentNode
				: target, options = {
			index : link,
			event : event
		}, 	
		links = this.getElementsByTagName('a');
		blueimp.Gallery(links, options);
	};
	
	//Lightbox
	galeria=blueimp.Gallery(document.getElementById('links')
			.getElementsByTagName('a'), {
		container : '#blueimp-gallery-carousel',
		carousel : true,
		startSlideshow : false,

	});
	
	if (($('#links a').length>0))
	{
		
		$('#grupo-botones a').show();
		
	}
	else{
		//Sin fotos
		$('#blueimp-gallery-carousel').slideUp();
		$('#grupo-botones a').hide();
		$('#btn-camara').show();
	}
	//$('#grupo-botones').css('display','block');	
}


function guardarFase(){
	_deshabilitarControles();
	var parametros={};
	parametros.ficha= ficha;
	parametros.operacion=11;
	var listaPadecimientos=[];
	$("#tabla .fila-padecimiento").each(function(){
		nodo={};	
		nodo.tipo=$(this).data('id');
		nodo.descripcion=($(this).find(".input-sm").first().val());
		var piezas=new Array();
		$(this).find(".grupo-piezas .pieza").each(function(){
			piezas.push($(this).html());
		});
		nodo.piezas=piezas;
		listaPadecimientos.push(nodo);
	}
	);
	//Llamada al servidor
	parametros.padecimientos=(JSON.stringify(listaPadecimientos));
	parametros.ficha= ficha ;
	$.post('/clinicas-crm/ficha_clinica',parametros,callbackGuardarFase,'json');
}


function ui_mostrarModalPadecimientos(){
	$('#modalPadecimientos').modal({"backdrop":"static","keyboard":false});
}

function ui_agregarFila(){
	fnAgregarFila("", parseInt($('#select-padecimientos').val())-1)
	$('#modalPadecimientos').modal('hide');
	cambiosPendientes=true;
}

//No se puede editar nada de la fase
var _controles=$('input,textarea,button').not('#btn-verificar,#btn-cerrar');

function _deshabilitarControles(){
	$("#btnGuardar").prop("disabled", true).html('Espere <i class="fa fa-spinner fa-spin"></i>');
	_controles.prop('disabled',true);
	$('.close').fadeOut();
}

//Habilitar la edición de la fase
function _habilitarControles(){
	$("#btnGuardar").prop("disabled", false).html('Guardar');
	_controles.prop('disabled',false)
	$('.close').fadeIn();
}



 function fila(_id)  { 
	 var html='<tr class="fila-padecimiento" data-id="'+_id+'">'
		+'<td>'+padecimientos[_id].nombre+'</td>'
		+ '<td>'
		+ '<input  class="form-control input-sm" type="text" placeholder="Ingrese una descripción"><div class="btn-group btn-group-sm grupo-piezas">'
		+'</div>'
		+'</td>'
		+ '<td><div onclick="agregarPieza(this,\'&nbsp;&nbsp;\')" class="close boton-agregar-pieza"><i class="fa fa-plus-circle"></i></div></td>'
		+'<td onClick="eliminarFila.call(this)"><a class="close" ><i class="fa fa-minus-circle "></i></a></td>'
		+ '</tr>';
		return $(html);
}

 
 function obtenerFilaRoentgenologica(idPadecimiento,descripcion,valor){
	 
	 var _titulofR=$('<td>').html(padecimientos[idPadecimiento].nombre);
	 
	 if(padecimientos[idPadecimiento].tipo==1){}
	 else{}
	 var _contenidofr=$('<td>');
	 
	 var fR=$('<tr>').data('id',idPadecimiento).append(_titulofR.append(_contenidofr));
	 
	 return fR;
	 
 }
 
		
		
		
function fnAgregarFila(descripcion,idPadecimiento, value) {
	var _fila=fila(idPadecimiento);
	$('#tabla tr:last').after(_fila);
	var valor= parseInt(buscarTipo(idPadecimiento));
	//Mostrar la descripción o los botones según el tipo
	if (valor==1){
		//libre
		 _fila.find('.input-sm').val(descripcion);
		 _fila.find('.grupo-piezas').first().hide();
		 _fila.find('.boton-agregar-pieza').first().hide();

	 }else{
		 //Piezas
		 _fila.find('.input-sm').first().hide();
		 //_fila.find('.grupo-piezas').first().css('display','inline');
		 //_fila.find('.boton-agregar-pieza').first().css('display','inline');
		 value.forEach(function(nodo) {
				if(nodo!="&nbsp;&nbsp;")
			    	agregarPieza(_fila.find('.boton-agregar-pieza').first(),nodo);
			}
				);
	 }
	
	
}
		
			


			
			function bloquearFase(){
				$('#btnGuardar').unbind('click');
				$('#btnGuardar').removeClass('btn-default');
				$('#btnGuardar').addClass('btn-success');
				$("#agregarFila").fadeOut();
				$('.multiselect').prop('disabled',true);
				$('.close').remove();						
				$('.pieza').prop('disabled',true);
				$('input').prop('disabled',true);
				$('#btn-camara').remove();
			}
			
			
			
			function obtenerAdvertencia(clase, mensaje) {
				Messenger().post({
					message : '<center>'+mensaje+'</center>',
					type : clase,
					showCloseButton : true
				});
			}
			
			
			var select =  '<div style="width:100px;">'
				+ '<select id="selectCuadrante" class="form-control">'
				+ '<option value="1">1</option>'
				+ '<option value="2">2</option>'
				+ '<option value="3">3</option>'
				+ '<option value="4">4</option>'
				+ '<option value="5">5</option>'
				+ '<option value="6">6</option>'
				+ '<option value="7">7</option>'
				+ '<option value="8">8</option>'
				+ '</select>'
				+ '<select id="selectPieza" class="form-control">'
				+ '<option value="0">&nbsp;&nbsp;.</option>'
				+ '<option value="1">1</option>'
				+ '<option value="2">2</option>'
				+ '<option value="3">3</option>'
				+ '<option value="4">4</option>'
				+ '<option value="5">5</option>'
				+ '<option value="6">6</option>'
				+ '<option value="7">7</option>'
				+ '<option value="8">8</option>'
				+ '</select>'
				+ '<button class="btn btn-red btn-block eliminar-pieza" >Eliminar</button>'
				+ '</div>';	
			var opcionesPopOver = {
					html : 'true',
					content : select,
					title : '<span class="text-info"><strong> Opciones</strong></span>'
				};
				
			
			//Sobre escribir el comportamiento por defecto de popOver
			$.fn.extend({
			    popoverClosable: function (options) {
			    	var defaults = {
				           
				        };
			    	 options = $.extend({}, defaults, options);
				        var $popover_togglers = this;
				        $popover_togglers.popover(options);
				        $popover_togglers.on('click', function (e) {
				            e.preventDefault();
				  
				           // $(this).popover('show');
				          $('.pieza').not(botonPlus).popover('hide');
				          
				          /*Eliminar*/
				          piecita = $(this);
				  		//Eliminaciones
				  		$('.eliminar-pieza').click(function() {
				  			$popover_togglers.popover('hide');
				  			piecita.remove();
				  		});
				  		
				  		//Escogencia de piezas
				  		$("#selectCuadrante").change(
							function() {
							var cuadrante = parseInt(($("#selectCuadrante").val()));
							var piezas = 0;
							if (cuadrante > 4)
								piezas = 5;
							else
								piezas = 8;
							$('#selectPieza').find('option').remove().end();
							$('#selectPieza').append('<option value="0">&nbsp;&nbsp;.</option>');
							for (x = 1; x < (piezas + 1); x++) {
								$('#selectPieza').append('<option value="'+x+'" >'+ x+ '</option>');
							}
							});
							$("#selectPieza").change(function() {
							if ($("#selectPieza").val() == "0") {
							} else {
								piecita.html($("#selectCuadrante").val()+ "."+ $("#selectPieza").val());
							}
						});
				  		//----Escogencia de piezas
				        //Verificar que sea una nueva
				  
						if (piecita.html() == '&nbsp;&nbsp;') {
			
						}else{
							var res = piecita.html()
							.split(".");
							var quad = res[0];
							var pz = res[1];
							$("#selectCuadrante").val(quad);
							$("#selectCuadrante").change();
							$("#selectPieza").val(pz);
						}
				            
				        });
			        $('html').on('click', '[data-dismiss="popover"]', function (e) {
			            $popover_togglers.popover('hide');
			        });
			    }
			});
			
					
			function eliminarFila(filita){
				var f=$(this).parent();
				_deshabilitarControles();
				var mensajito=Messenger().post({
					  message: "<center>¿Desea eliminar esta fila?</center>",
					  type:'info',
					  showCloseButton:true,
					  id:1,
					  hideAfter:36000,
					  actions: {
					  	aceptar: {
					 	label: "Eliminar",
					   	action: function(){
					    	f.remove();
					    	mensajito.hide();
					    	_habilitarControles();
					      }
					    },
					   	cancelar: {
						 	label: "Cancelar",
						   	action: function(){
						    mensajito.hide();
						    _habilitarControles();
							}
						}
						//adicionar botones
					  }
				});
			}
			
			function agregarPieza(boton,valor) {
				$('.pieza').popover('hide');
				var botonNuevo=$('<button type="button" onclick="botonPlus=this" class="btn btn-white pieza" data-placement="top">'+valor+'</button>');
				botonNuevo.popoverClosable(opcionesPopOver);
				stack.push(botonNuevo);
				$(boton).parent().parent()
				.find('.grupo-piezas')
				.first()
				.append(botonNuevo);
			}

			function buscarTipo(valor){
				var valorx;
				padecimientos.forEach(function (nodo){
					if(parseInt(valor)==parseInt(nodo.id)){
						valorx= nodo.tipo;
					}
					
				});
				return valorx;
			}
			
			
			
			
			function cargarMiniatura(nombre,id){
				 $("#album").append($("<button  class=\"btn btn-white btn-square miniatura\"></button>").hide().fadeIn().css('color','#FFFFFF').css('background-image','url(\'/clinicas-crm/imagenes?t=2&f='+nombre+'\')').data('nombre',nombre).data('id',id).mouseover(function(hola){
	      		   $(this).html("&times;");
	      		
	      		
	      		   
	      	   }).mouseleave(function(){$(this).html("");}).
	      	   click(function(){
	      		   
	      		   var i=$(this);
	      		   _deshabilitarControles();
	      		 var mensajito=Messenger().post({
					  message: "<center>¿Está seguro que desea eliminar la imagen? <br> Esta acción no se puede deshacer.</center>",
					  type:'info',
					  showCloseButton:true,
					  id:1,
					  hideAfter:36000,
					  actions: {
					  	aceptar: {
					 	label: "Eliminar",
					   	action: function(){
					   		parametros={};
			      			parametros.nombre=i.data('nombre');
			      			parametros.id=i.data('id');
			      			botonMiniatura=i;
			      			parametros.operacion=9;
			      			$.post('/clinicas-crm/ficha_clinica',parametros,callbackEliminarFotografia,'json');
			      			_habilitarControles();
					    	mensajito.hide();
					      }
					    },
					   	cancelar: {
						 	label: "Cancelar",
						   	action: function(){
						    mensajito.hide();
						    _habilitarControles();
							}
						}
						//adicionar botones
					  }
				});
	      	   })
	      	   
				 );
			}
			
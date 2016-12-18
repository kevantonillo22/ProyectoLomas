	
	var intensidad=	['','Leve','Moderada','Severa'];
	var duracion=	['','Corta','Prolongada'];
	var inicio=		['','Espontáneo','Provocado'];
	var frecuencia=	['','Constante','Intermitente'];
	var oclusion=	['Normoclusión','Maloclusión Clase I','Maloclusión Clase II','Maloclusión Clase III','Oclusión no evaluable'];
	
	
	
	$(iniciar);
	function iniciar(){
		
		$('.contenedor').hide();
		//Cargar la ficha clínica
		
		//ocultarEncabezados();
		
		var parametros={};
		parametros.operacion=0;
		parametros.ficha=ficha;
		$.post('/clinicas-crm/ficha_clinica',parametros,callbackCargar,'json');
	}
	
	function callbackCargar(respuesta){
		console.log(respuesta);
		//Colocar las fechas
		$('#fecha-fase-1').text(respuesta.ficha.fase1.fecha.substring(0,16));
		$('#fecha-fase-2').text(respuesta.ficha.fase2.fecha.substring(0,16));
		$('#fecha-fase-3').text(respuesta.ficha.fase3.fecha.substring(0,16));
		$('#fecha-fase-4').text(respuesta.ficha.fase4.fecha.substring(0,16));
		$('.contenedor').slideToggle();
		$('#frame-cargando').fadeOut();
		//Carga de la fase 1
		$('#motivoConsulta').text(respuesta.ficha.fase1.motivoConsulta);
		$('#historialEnfermedad').text(respuesta.ficha.fase1.historialEnfermedad);
		$.each(respuesta.ficha.fase1.padecimientos,function(indice,valor) {$('#chk'+valor).prop("checked",true);});
		$('#historialOdontologico').text(respuesta.ficha.fase1.historialOdontologico);
		$('#historialMedico').text(respuesta.ficha.fase1.historialMedico);
		$('#precauciones').text(respuesta.ficha.fase1.precauciones);
		respuesta.ficha.fase1.dolorDentario.forEach(function(nodo) {$('#dolor-dentario tr:last').after($('<tr>').append($('<td>').text(nodo.cuadrante+'.'+nodo.indice)).append($('<td>').text(intensidad[nodo.intensidad])).append($('<td>').text(duracion[nodo.duracion])).append($('<td>').text(inicio[nodo.inicio])).append($('<td>').text(frecuencia[nodo.frecuencia])));});
		//Carga de la fase 2
		$('#oclusion').text(oclusion[respuesta.ficha.fase2.fd_oclusion_opcion-1]);
		$('#oclusion-descripcion').text(respuesta.ficha.fase2.fd_oclusion_descripcion);
		$('#evaluacion_clinica').html(respuesta.ficha.fase2.fd_cara_cuello+"&nbsp"+respuesta.ficha.fase2.fd_tejidos_blandos+"&nbsp"+respuesta.ficha.fase2.fd_tejidos_duros+"&nbsp"+respuesta.ficha.fase2.fd_temp_mandibular);
		$('#capacidad_respiratoria').text(respuesta.ficha.fase2.fd_capacidad_respiratoria);
		$('#frecuencia_respiratoria').text(respuesta.ficha.fase2.fd_frecuencia_respiratoria);
		$('#presion_arterial').text(respuesta.ficha.fase2.fd_presion_arterial);
		$('#pulsaciones_minuto').text(respuesta.ficha.fase2.fd_pulsaciones_minuto);
		//Carga de la fase 3
		respuesta.ficha.fase3.evaluacion.forEach(function(nodo){
			var valor;
			if(nodo.tipo==1){
				valor="";
				nodo.valor.forEach(function(pieza){
					valor+=pieza+',';
				});
			if(valor.length>1)
				valor=valor.substr(0,valor.length-1);	
			}else{
				valor=nodo.descripcion;
			}
			$('#tabla-roentgenologica tr:last').after($('<tr>').append($('<td>').text(nodo.padecimiento)).append($('<td>').text(valor)));	
		});
		//Carga de la fase 4
		respuesta.ficha.fase4.imagenes.forEach(
				function(imagen){
					$('[data-id="'+imagen.pieza+'"]').find('.pieza-odontograma').find('.dibujo').css('background-image','url(\''+'/clinicas-crm/imagenes?t=3&f='+imagen.miniatura+'\')');
					$('[data-id="'+imagen.pieza+'"]').data('imagen',imagen.nombre);
				}		
			);					
			
		respuesta.ficha.fase4.matriz.forEach(function(tupla) {
			//Restaurar
			v=tupla.valor.piezas;
			var indice=tupla.valor.id;
		    valoresNuevos=[v[0].valor,v[1].valor,v[2].valor,v[3].valor,v[4].valor,v[5].valor,v[6].valor,v[7].valor];
			//corresponde al primer cuadrante
			if(tupla.valor.piezas[0].pieza==8){
				$('#tabla-1 tbody').append(obtenerFila(valoresNuevos,tratamientos[indice].nombre,indice,false));
			}
			//corresponde al segundo cuadrante
			if(tupla.valor.piezas[0].pieza==9){
				$('#tabla-2 tbody').append(obtenerFila(valoresNuevos,tratamientos[indice].nombre,indice,true));
			}
			if(tupla.valor.piezas[0].pieza==32){
				$('#tabla-4 tbody').append(obtenerFila(valoresNuevos,tratamientos[indice].nombre,indice,false));
			}	
			
			if(tupla.valor.piezas[0].pieza==17){
				$('#tabla-3 tbody').append(obtenerFila(valoresNuevos,tratamientos[indice].nombre,indice,true));
			}
			
		});
		//Cargar la temida fase 5
		$('#span-total').text('Q '+respuesta.ficha.fase5.total.toFixed(2));
		respuesta.ficha.fase5.planes.forEach(function(nodo) {
		    var pieza;
		    if(nodo.pieza<0)
		    	pieza=false;
		    else
		    	pieza=piezas[nodo.pieza];
		    $('#tabla').find('#prespuesto-total').before(agregarFila(pieza ,nodo.tratamiento_nombre,nodo.identificador,nodo.estado,nodo.precio_cadena,nodo.revisor,nodo.fecha,nodo.estado_nombre));
		//La fase 
		    if(nodo.estado==5||nodo.estado==6){
				//Agregar
				var nombre=nodo.tratamiento_nombre;
				if (nodo.pieza>0)
					nombre=nombre=nodo.tratamiento_nombre+' en '+piezas[nodo.pieza];
				agregarTratamiento(nodo.fecha,nombre,'Q '+nodo.precio.toFixed(2),nodo.revisor, nodo.tareas,nodo.identificador);
				//fin
			}
		
		});
		//Los datos generales
		$('#pin').text(respuesta.ficha.datos.pin);
		$('#estudiante').text(respuesta.ficha.datos.estudiante);
		$('#fecha_ingreso').text(respuesta.ficha.datos.fecha_ingreso);
		$('#encargado').text(respuesta.ficha.datos.encargado);
		$('#nacimiento').text(respuesta.ficha.datos.nacimiento);
		$('#telefono').text(respuesta.ficha.datos.telefono);
		$('#direccion').text(respuesta.ficha.datos.direccion);
		
	}
	
	
	function obtenerFila(valores,padecimiento,indice, derecha){
		var clase="";
		
		if(derecha)
			clase=' titulo-derecha';
		var fila=
			'<tr data-indice="'+indice+'">'+
			'<td width="40px">'+
				'<div class="titulo-lateral '+clase+'" ><div class="boton-eliminar"></div><strong>'+padecimiento+'</strong></div><div  class="contenido" >'+valores[0]+'</div>'+
				''+
				'</td>'+
			'<td width="40px">'+valores[1]+'</td>'+
			'<td width="40px">'+valores[2]+'</td>'+
			'<td width="40px">'+valores[3]+'</td>'+
			'<td width="40px">'+valores[4]+'</td>'+
			'<td width="40px">'+valores[5]+'</td>'+
			'<td width="40px">'+valores[6]+'</td>'+
			'<td width="40px">'+valores[7]+'</td>'+
		'</tr>';
		return fila;
		}
	
	function ocultarEncabezados(){		
		if(window.innerWidth<991){
			$('.titulo-derecha').css('display','block');
		}else{
			$('.titulo-derecha').css('display','none');
		}
	}
	function obtenerIdPieza(cuadrante, indice){
		if(cuadrante<5)
			return (cuadrante-1)*8+indice;
		return (cuadrante-5)*5+32+indice;
	}
	
	
	function agregarFila(pieza,nombre,identificador, estado,precio_cadena,revisor, fecha,estadoNombre)  { 
		obtenerIndice();
		//Reiniciar el modal
		
		var botonPieza='<b>'+ pieza+ '</b>';
		if (!pieza)
			botonPieza='';
			
		
		var label;
		var estilo;
		switch(estado){
		case 1:
			label='label label-primary';
			break;
		case 2:
			label='label green';
			break;
		case 3:
			label='label label-warning';
			break;
		case 4:
			label='label label-danger';
			estilo="style=\"text-decoration: line-through; \"";
			break;
		case 5:
			label='label label-default';
			break;
		case 6:
			label='label label-success';
			break;
		}
		//var fecha2;
		if(fecha.length>11)
			fecha=fecha.substr(0,10);
		var html=
			'<tr class="registro" data-estado="'+estado+'" data-identificador="'+identificador+'">'
			//+'<td style="width:50px;"><button class="boton-plus btn btn-white"  title="Opciones" style="border-radius:50%;" class="btn btn-white"><b>+</b></button></td>'
			+'<td class="indice"><label>'+obtenerIndice()+'</label></td>'
			+'<td><label>'
			+ botonPieza
			+'</label></td><td class="td-tratamiento" "><label>'+nombre+'</label> </td>'
			+'<td><label><span class="'+label+'">'+estadoNombre+'</span></label></td>'
			+'<td><label>'+revisor+'</label></td>'
			+'<td><label>'+fecha+'</label></td>'
			
			+'<td '+estilo +'><label>'+precio_cadena+'</small></td>'
			+'</tr>';
		return html;			
	}
	function obtenerIndice(){
		var indice=$('.indice').length;
		return parseInt(indice)+1;
	}
	
	
	function agregarTratamiento(fecha,nombre_tratamiento,valor,revisor, tareas,identificador){
		var detalles=[];
		var celda=$('<td>');
		tareas.forEach(iterarTareas);
		function iterarTareas(tarea){
			var f='';
			if(tarea.realizada)
				f=tarea.fecha;
			var detalle=
				$('<tr>').addClass('warning')
				//.append($('<td>'))
				.append($('<td>').css('text-align','left').html('<label>'+tarea.descripcion+'<label>'))
				.append($('<td>'))
				.append($('<td>').append($('<input type="checkbox">').prop('checked',tarea.realizada).prop('disabled',true)))
				.append($('<td>').text(f.substr(0,10)));
			detalles.push(detalle);
		}
		var registro=
			$('<tr>')
			//.append($('<td>').css('width','50px').append($('<button  class="boton-plus btn btn-white"></button>').click(function(){$(this).find('i').toggleClass('fa-chevron-circle-down fa-chevron-circle-up');$.each(detalles, function(){ $(this).toggle();});}).css('border-radius','20%').append(icono)))
			.append(celda.append($('<label>'+nombre_tratamiento+'</label>').addClass('span-negro').click(function(){})).css('text-align','left'))
			.append($('<td>').append($('<label>').html(valor)))
			.append($('<td>').append($('<label>').text(revisor)))
			.append($('<td>').append($('<label>')).text(fecha))
			.data('identificador',identificador);
		
		
			
		$('#fila-boton').before(registro);
		$.each(detalles, function(){ $('#fila-boton').before($(this));});
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
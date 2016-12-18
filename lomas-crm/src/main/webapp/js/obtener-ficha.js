impresion=true;
$(inicializarFicha);
function inicializarFicha(){
	var parametros={};
	parametros.operacion=0;
	parametros.ficha=ficha;
	$.post('/clinicas-crm/ficha_clinica',parametros,callbackCargar,'json');
}

function callbackCargar(respuesta){
	switch(respuesta.resultado) {
		case 1:
			restaurarFicha(respuesta);
			break;
		case -102:
			notificarError();
			break;
		default:
  
	}
}

function restaurarFicha(respuesta){
	restaurarDatosGenerales(respuesta.datosGenerales);
	restaurarFase1(respuesta.fase1);
	restaurarFase2(respuesta.fase2);
	restaurarFase3(respuesta.fase3);
	restaurarFase4(respuesta.fase4);
		
}

function restaurarFase2(respuesta){
	$('#f2fc').html(respuesta.fd_pulsaciones_minuto);
	$('#f2pa').html(respuesta.fd_presion_arterial);
	$('#f2fr').html(respuesta.fd_frecuencia_respiratoria);
	$('#f2cr').html(respuesta.fd_capacidad_respiratoria);
	$('#f2oo').html(_oclusion[respuesta.fd_oclusion_opcion-1]);
	$('#f2oc').html(respuesta.fd_oclusion_descripcion);
	$('#f2ec').html(respuesta.fd_cara_cuello+'<br>'+respuesta.fd_tejidos_blandos+'<br>'+respuesta.fd_tejidos_duros+'<br>'+respuesta.fd_temp_mandibular);
}

function restaurarFase3(respuesta){
	
	//$('#tf3 tr:eq(1) td:eq('+(i+1)+')').html;
	for (i = 0; i < respuesta.padecimientos.length; i++) {
		var padecimientoGlobal=padecimientos[respuesta.padecimientos[i].tipo];
		$('#tf3 tr:eq('+(i+1)+') td:eq(0)').html(padecimientoGlobal.nombre);
		console.log('El tipo es'+padecimientoGlobal.tipo+' '+padecimientoGlobal.nombre)
		console.log(respuesta.padecimientos[i]);
		if(padecimientoGlobal.tipo==2){
			var _valor='';
			console.log('Largo del array:'+respuesta.padecimientos[i].valor.length);
			for(var j=0;j<respuesta.padecimientos[i].valor.length;j++){
				_valor=_valor+respuesta.padecimientos[i].valor[j]+',';
				
			}
			_valor=_valor.substr(0, _valor.length-1);
			$('#tf3 tr:eq('+(i+1)+') td:eq(1)').html(_valor);
			
		}else{
			$('#tf3 tr:eq('+(i+1)+') td:eq(1)').html(respuesta.padecimientos[i].descripcion);
		}
		
		//console.log(respuesta.padecimientos[i].tipo]);
		
		//$('#tf3 tr:eq('+i+') td:eq(1)');
	}
	
}

function restaurarDatosGenerales(respuesta){
	$("#pin").text(respuesta.pin);
	$("#paciente").text(respuesta.paciente);
	$("#fecha-nacimiento").text(respuesta.paciente_fecha_nacimiento);
	$("#telefono").text(respuesta.paciente_telefono);
}



function restaurarFase1(respuesta){
	$("#f1-mc").html(respuesta.motivoConsulta);
	$("#f1-textArea2").html(respuesta.historialEnfermedad);
	$("#f1-textArea5").html(respuesta.precauciones);
	
	$("#f1-textArea3").html(respuesta.historialMedico);
	$("#f1-textArea4").html(respuesta.historialOdontologico);
	
	//Cargar padecimientos guardados
	for (i = 0; i < respuesta.padecimientos.length; i++) { 
		$('#chk'+respuesta.padecimientos[i]).toggleClass("opcion-false opcion-true");
	}

	for (i = 0; i < respuesta.dolorDentario.length; i++) { 
		nodo=respuesta.dolorDentario[i]
		$('#dolor-dentario tr:eq(1) td:eq('+(i+1)+')').html(nodo.cuadrante+'.'+nodo.indice);
		$('#dolor-dentario tr:eq(2) td:eq('+(i+1)+')').html(_intensidad[nodo.intensidad]);
		$('#dolor-dentario tr:eq(3) td:eq('+(i+1)+')').html(_duracion[nodo.duracion]);
		$('#dolor-dentario tr:eq(4) td:eq('+(i+1)+')').html(_inicio[nodo.inicio]);
		$('#dolor-dentario tr:eq(5) td:eq('+(i+1)+')').html(_frecuencia[nodo.frecuencia]);
	}
}


function restaurarFase4(respuesta){
	//Restaurar las anotaciones periodontales
	for (var i=respuesta.anotaciones.length; i--; ) {
		for (var j=respuesta.anotaciones[i].anotaciones.length; j--; ) {
			agregarAnotacion($('[data-id="'+respuesta.anotaciones[i].pieza+'"]'),respuesta.anotaciones[i].anotaciones[j]);
			
		}
	}
	
	//Restaurar imágenes
	respuesta.imagenes.forEach(
		function(imagen){
			agregarDibujo($('[data-id="'+imagen.pieza+'"]'),imagen.nombre,imagen.miniatura);
		}		
	);
	
	//Restaurar matriz periodontal
	var _matrices=respuesta.matriz.reverse();
	for (var i=_matrices.length; i--; ) {
		var _decidua=true;
		if(_matrices[i].matriz>1)
			_decidua=false;
		agregarFilasMatriz(true,_matrices[i].id,_decidua,_matrices[i].matriz,_matrices[i].fila);
		
	}
}

var _intensidad=['','L','M','S'];
var _duracion=['','C','P'];
var _inicio=['','E','P'];
var _frecuencia=['','C','I'];
var _oclusion=['Normoclusión','Maloclusión Clase I','Maloclusión Clase II','Maloclusión Clase III','Oclusión no evaluable'];


var btnNuevo;
var modalNuevoPaciente;
var btnGuardar;
var input_nombres;
var input_horario_disponible;
var input_ingreso;
var input_tipo_ingreso;


$(inicializar);

function inicializar(){
	
	//Inicializar componentes graficos
	btnNuevo=$('#btn-nuevo');
	modalNuevoPaciente=$('#nuevo-paciente');
	btnGuardar=$('#btn-guardar');
	input_horario_disponible=$('#horario_disponible');
	input_ingreso=$('#ingreso');
	input_tipo_ingreso=$('#tipo_ingreso');
	
	btnNuevo.click(function(){modalNuevoPaciente.modal();});
	btnGuardar.click(function(){guardarPaciente();});
	listarPacientes();
	
	
}

function listarPacientes(){
	/*var parametros={};
	parametros.operacion=2;
	$.post('/clinicas-crm/bancopacientes',parametros,callbackListar,'json');
	*/
	/*$.post("/clinicas-crm/bancopacientes", {"operacion": "2"}, function(result){
        $("span").html(result);
    });*/
	 $('#example').DataTable({
		 "ajax": 
		 {	
			 "url": "/clinicas-crm/bancopacientes",
			 "data": 
				 function ( d ) {
				 	d.operacion = "2";
				 },
			"type": "POST"
		 },
		 "lengthChange": true,
		 "dom": 'rt',
		 "displayLenght":10,
		 "processing": true,
		 "serverSide": true,
		 "bFilter": false,
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
					"data": "persona"
				}, {
					"data": "horario"
				}, {
					"data": "ingreso"
				}, {
					"data": "tipo"
				}, {
					"data": "situacion"
				}
			],
			"order": [
				[1, 'asc']
			],
			"language": {
				"lengthMenu": "Mostrar _MENU_ filas por página",
				search: "Buscar: ",
				"zeroRecords": "<center>No hay filas para mostrar</center>",
				"info": "Mostrando la página _PAGE_ de _PAGES_",
				"infoEmpty": "No hay filas disponibles",
				"processing": "<div style='clear:left;'><center ><b>Procesando <i class='fa fa-spinner fa-spin'></i></b></center></div><br>",
				"infoFiltered": "(filtradas de _MAX_ filas en total)",
				"paginate": {
					"next": "Siguiente",
					"previous": "Anterior"
				}
			}
		 
	 });
}

function callbackListar(response){
console.log(response);
	
	switch(response.resultado){
	
		case -1:
			obtenerAdvertencia("error", response.descripcion);
			break;
			
		case 1:
			obtenerAdvertencia("success", response.descripcion);
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			obtenerAdvertencia("error", response.descripcion);
			break;
			
		case -200:
			obtenerAdvertencia("error", response.descripcion);
			break;
	
	}
}

function guardarPaciente(){
	
	var parametros={};
	parametros.persona=3;
	parametros.horario_disponible=input_horario_disponible.val();
	parametros.ingreso=input_ingreso.val();
	parametros.tipo_ingreso=input_tipo_ingreso.val();
	parametros.situacion_economica=1;
	
	
	//validar todo
	
	btnGuardar
	.html(
			' Espere <i class="fa fa-spinner fa-spin"></i>');
btnGuardar.prop("disabled", true);
	
	
	
	
	parametros.operacion=1;
	console.log(parametros);
	
	$.post('/clinicas-crm/bancopacientes',parametros,callbackGuardar,'json');
	
	
	
}


function callbackGuardar(response){
	console.log('holis');
	console.log(response);
	btnGuardar
	.html(
			' Guardar');
	btnGuardar.prop("disabled", false);
	switch(response.resultado){
	
	case -1:
		obtenerAdvertencia("error", response.descripcion);
		break;
		
	case 1:
		obtenerAdvertencia("success", response.descripcion);
		break;
		
	case -100:
		//Caduco
		$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
		break;
		
	case -101:
		obtenerAdvertencia("error", response.descripcion);
		break;
	
	}
	
}

function obtenerAdvertencia(clase, mensaje) {
	Messenger().post({message:'<center>'+ mensaje+'</center>',type: clase,showCloseButton: true});
}

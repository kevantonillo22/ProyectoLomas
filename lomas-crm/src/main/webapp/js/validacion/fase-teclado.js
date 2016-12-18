var tratamientos;

$('#txt-comentario').prop('disabled', false);

$('#btn-cerrar').click(function() {
	$('#modalVerificacion').modal('hide');
	Messenger().hideAll();
});

// llamar al servicio
function verificar() {
	$('#btn-verificar').prop('disabled', true);
	$('#btn-verificar').html('Espere <i class="fa fa-spinner fa-spin"></i>');
	$.post('/clinicas-crm/ficha_clinica', {
		tratamientos : JSON.stringify(tratamientos),
		numero_fase : numero_fase,
		operacion : 16,
		ficha : ficha,
		estado : $('#select-verificacion').val(),
		descripcion : $('#txt-comentario').val()
	}, callbackVerificar, 'json');

	function callbackVerificar(respuesta) {
		Messenger().hideAll();
		$('#btnGuardar').prop('disabled', false);
		$('#modalVerificacion').modal('hide');
		switch (respuesta.resultado) {
		case 1:
			$('#btnGuardar').html('Fase validada');
			window
					.setTimeout(
							function() {
								window.location.href = "/clinicas-crm/profesor/listarRevisionesProfesor.jsp";
							}, 5000);
			$('#btnGuardar').unbind('click');
			$('#btnGuardar').removeClass('btn-default');
			$('#btnGuardar').addClass('btn-success');

			obtenerAdvertencia(respuesta.clase, '<center>'
					+ respuesta.descripcion + '</center>', 1);
			break;
		case -1:
			obtenerAdvertencia(respuesta.clase, '<center>'
					+ respuesta.descripcion + '</center>', 1);
			$('#btnGuardar').html('Validar fase');
			$('#btn-verificar').html('Guardar');
			$('#btn-verificar').prop('disabled', false);
			break;
		default:

		}

	}
}

// Cargar la firma de la fase
$('#btnGuardar').attr('onclick', '').unbind('click');
$('#btnGuardar').unbind('click');
$('#btnGuardar').html('Calificar fase');
$('#btnGuardar').click(function() {
	console.log('fase-teclado aqui ando');
	if (numero_fase < 6)
		$('#modalVerificacion').modal('show');
	else {
		tratamientos = [];
		$('.registro').each(function() {
			var nodo = {};
			nodo.identificador = $(this).data('identificador');
			nodo.eps = $(this).find('.eps').prop('checked');
			nodo.vobo = $(this).find('.vobo').prop('checked');
			console.log(nodo);
			tratamientos.push(nodo);
		});
		// Verificar la fse 6
		realizarVerificacion();

	}

	/**/
});

$('#btn-verificar').click(realizarVerificacion);

function realizarVerificacion() {
	if ($('#select-verificacion').val() == 0) {
		obtenerAdvertencia(
				'error',
				'<center>Debe calificar la fase para poder continuar. Seleccione entre "Aprobada" o "Reprobada"</center>');
	} else {

		// hay que haer algo aqui porque antes se cargaba el appletindio
		verificar();

	}
}

var btnNuevo;
var modalNuevoPaciente;
var btnGuardar;
var diaSeleccionado = null;
var txtNombre;
var txtFechaCita;
var txtHora;
var wrapNombre;
var wrapFechaCita;
var wrapHora;
var btnImpresion;
var lblAdvertencia;
var calendario;
var data;
var table; // Tabla de datos
var fila; // Fila a la cual se da click


var modalPersonales;
var titleModalPersonales;
var btnGuardarPersonales;
var selectTipoIngreso;
var personalesID;
var personalesNombre;
var personalesDPI;
var personalesEdad;
var personalesDireccion;
var personalesTelefono;
var personalesEmail;
var personalesEstadoCivil;
var personalesEscolaridad;
var personalesTrabajo;
var personalesOcupacion;
var personalesHorario;
var personalesIngresos;
var personalesTipoIngreso;
var personalesOtro;
var personalesEncargado;
var personalesRelacion;
var personalesTrabajoEncargado;
var personalesSalarioEncargado;

var tabDatosPersonales;
var tabSituacionEconomica;
var tabCaso;

var camposSituacionEconomica;
var camposDatosPersonales;
var camposCaso;
var personalesWrapNombre;
var personalesWrapDPI;
var personalesWrapEdad;
var personalesWrapDireccion;
var personalesWrapTelefono;
var personalesWrapEmail;
var personalesWrapEstadoCivil;
var personalesWrapEscolaridad;
var personalesWrapTrabajo;
var personalesWrapOcupacion;
var personalesWrapHorario;
var personalesWrapIngresos;
var personalesWrapTipoIngreso;
var personalesWrapOtro;
var personalesWrapEncargado;
var personalesWrapRelacion;
var personalesWrapTrabajoEncargado;
var personalesWrapSalarioEncargado;
var personalesWrapSituacionEconomica;
var lblAdver;

var titleModalTratamiento;
var tratamientoIDCaso;
var tratamientoGingivitis;
var tratamientoPeriodontitis;
var tratamientoObturaciones;
var tratamientoAmalgamas;
var tratamientoResinas;
var tratamientoIncrustaciones;
var tratamientoObsIncrustaciones;
var tratamientoPT;
var tratamientoPPR;
var tratamientoObsSuperiorPPR;
var tratamientoObsInferiorPPR;
var tratamientoPPF;
var tratamientoObsPPF;
var tratamientoEndodoncia;
var tratamientoMultiradicular;
var tratamientoObsSuperiorMulti;
var tratamientoObsInferiorMulti;
var tratamientoMonoradicular;
var tratamientoObsMonoradicular;
var tratamientoCirugia;
var tratamientoExodoncia;
var modalTratamiento;
var lblAdvertenciaTratamiento;
var btnGuardarTratamiento;

var titleModalCaso;
var modalCaso;
var lblAdvertenciaCaso;
var casoCasoDocente;
var casoCasoReferido;
var casoCasoOp;
var casoObservacion;
var casoWrapCasoDocente;
var casoWrapCasoReferido;
var casoWrapObservacion;
var casoWrapCasoOp;
var btnGuardarCaso;

var btnConstancia;

var dataRow = {}; //info de cada fila
var txtFiltro;

$(inicializar);


function inicializar(){
	
	//Inicializar componentes graficos
	btnNuevo=$('#btn-nuevo');
	btnConstancia = $('#btn-constancia');
	modalNuevaCita=$('#nueva-cita');
	btnGuardar=$('#btn-guardar');
	btnImpresion=$('#btn-impresion');
	btnImpresion.prop("disabled", true);
	txtNombre = $('#txtNombres');
	txtFechaCita = $('#txtFechaCita');
	txtFechaCita.val(getDate());
	txtHora = $('#txtHoraCita');
	wrapNombre = $('#wrapNombres');
	wrapFechaCita = $('#wrapFecha');
	wrapHora = $('#wrapHora');
	lblAdvertencia = $('#lblAdvertencia');
	calendario = $('#calendar');
	data = {};
	modalPersonales = $('#datos-personales');
	titleModalPersonales = $('#datos-personales #myModalLabel');
	btnGuardarPersonales = $('#btn-guardar-personales');
	personalesID = $('#datos-personales #idPacientePotencial');
	personalesNombre = $('#datos-personales #txtNombre');
	personalesDPI = $('#datos-personales #txtDPI');
	personalesEdad = $('#datos-personales #txtEdad');
	personalesDireccion = $('#datos-personales #txtDireccion');
	personalesTelefono = $('#datos-personales #txtTelefono');
	personalesEmail = $('#datos-personales #txtCorreo');
	personalesEstadoCivil = $('#datos-personales #estadoCivil');
	personalesEscolaridad = $('#datos-personales #txtEscolaridad');
	personalesTrabajo = $('#datos-personales #txtLugar');
	personalesOcupacion = $('#datos-personales #txtOcupacion');
	personalesHorario = $('#datos-personales #txtHorario');
	personalesIngresos = $('#datos-personales #txtIngreso');
	personalesTipoIngreso = $('#datos-personales #tipoIngreso');
	personalesOtro = $('#datos-personales #txtOtro');
	personalesEncargado = $('#datos-personales #txtNombreEncargado');
	personalesRelacion = $('#datos-personales #txtRelacion');
	personalesTrabajoEncargado = $('#datos-personales #txtTrabajoEncargado');
	personalesSalarioEncargado = $('#datos-personales #txtSalarioEncargado');
	
	
	tabDatosPersonales = $('#datos-personales-tab a');
	tabSituacionEconomica = $('#situacion-economica-tab a');
	tabCaso = $('#datos-caso-tab a');
	
	camposDatosPersonales = $('.camposDatosPersonales');
	camposSituacionEconomica = $('.camposSituacionEconomica')
	camposCaso = $('.camposCaso');
	personalesWrapNombre = $('#datos-personales #wrapNombre');
	personalesWrapDPI = $('#datos-personales #wrapDPI');
	personalesWrapEdad = $('#datos-personales #wrapEdad');
	personalesWrapDireccion = $('#datos-personales #wrapDireccion');
	personalesWrapTelefono = $('#datos-personales #wrapTelefono');
	personalesWrapEmail = $('#datos-personales #wrapCorreo');
	personalesWrapEstadoCivil = $('#datos-personales #wrapEstadoCivil');
	personalesWrapEscolaridad = $('#datos-personales #wrapEscolaridad');
	personalesWrapTrabajo = $('#datos-personales #wrapLugarTrabajo');
	personalesWrapOcupacion = $('#datos-personales #wrapOcupacion');
	personalesWrapHorario = $('#datos-personales #wrapHorarioDisponible');
	personalesWrapIngresos = $('#datos-personales #wrapIngreso');
	personalesWrapTipoIngreso = $('#datos-personales #wrapTipoIngresos');
	personalesWrapOtro = $('#datos-personales #wrapOtro');
	personalesWrapEncargado = $('#datos-personales #wrapEncargadoTratamiento');
	personalesWrapRelacion = $('#datos-personales #wrapRelacionPaciente');
	personalesWrapTrabajoEncargado = $('#datos-personales #wrapTrabajoEncargado');
	personalesWrapSalarioEncargado = $('#datos-personales #wrapSalarioEncargado');
	personalesWrapSituacionEconomica = $('#datos-personales #wrapSituacionEconomica');
	lblAdver = $('#datos-personales #lblAdvertencia');
	personalesTipoIngreso = $('#tipoIngreso');
	
	titleModalCaso = $('#datos-caso #myModalLabel');
	modalCaso = $('#datos-caso');
	lblAdvertenciaCaso = $('#datos-caso #lblAdvertencia');
	casoIDCaso = $('#datos-caso #txtIDCaso');
	casoCasoDocente = $('#datos-caso #chkCaso');
	casoCasoReferido = $('#datos-caso #txtReferido');
	casoCasoOp = $('#datos-caso #casoOP');
	casoObservacion = $('#datos-caso #txtObservacionCaso');
	casoWrapCasoDocente = $('#datos-caso #chkCaso').parent().parent();
	casoWrapCasoReferido = $('#datos-caso #txtReferido').parent();
	casoWrapCasoOp = $('#datos-caso #wrapCasoOp');
	casoWrapObservacion = $('#datos-caso #wrapObservacionCaso');
	btnGuardarCaso = $('#datos-caso #btn-guardar');
	
	titleModalTratamiento = $('#datos-diagnostico #myModalLabel');
	modalTratamiento = $('#datos-diagnostico');
	lblAdvertenciaTratamiento = $('#datos-diagnostico #lblAdvertencia');
	tratamientoIDCaso = $('#datos-diagnostico #idCaso');
	tratamientoGingivitis = $('#datos-diagnostico #chkGingivitis');
	tratamientoPeriodontitis = $('#datos-diagnostico #chkPeriodontitis');
	tratamientoObturaciones = $('#datos-diagnostico #chkObturaciones');
	tratamientoAmalgamas = $('#datos-diagnostico #chkAmalgamas');
	tratamientoResinas = $('#datos-diagnostico #chkResinas');
	tratamientoIncrustaciones = $('#datos-diagnostico #chkIncrustaciones');
	tratamientoObsIncrustaciones = $('#datos-diagnostico #obsIncrustaciones');
	tratamientoPT = $('#datos-diagnostico #chkPT');
	tratamientoPPR = $('#datos-diagnostico #chkPPR');
	tratamientoObsSuperiorPPR = $('#datos-diagnostico #obsPPRSuperior');
	tratamientoObsInferiorPPR = $('#datos-diagnostico #obsPPRInferior');
	tratamientoPPF = $('#datos-diagnostico #chkPPF');
	tratamientoObsPPF = $('#datos-diagnostico #obsPPF');
	tratamientoEndodoncia = $('#datos-diagnostico #chkEndodoncia');
	tratamientoMultiradicular = $('#datos-diagnostico #chkMultiradicular');
	tratamientoObsSuperiorMulti = $('#datos-diagnostico #chkMultiSuperior');
	tratamientoObsInferiorMulti = $('#datos-diagnostico #chkMultiInferior');
	tratamientoMonoradicular = $('#datos-diagnostico #chkMonoradicular');
	tratamientoObsMonoradicular = $('#datos-diagnostico #obsMonoradicular');
	tratamientoCirugia = $('#datos-diagnostico #chkCirugia');
	tratamientoExodoncia = $('#datos-diagnostico #chkExodoncia');
	btnGuardarTratamiento = $('#datos-diagnostico #btn-guardar');
	
	
	
	$('.obs').hide();
	$('#txtOtro').parent().hide();
	
	txtFiltro = $('#txtFiltro');
	btnBuscar = $('#btn-buscar');
	btnBuscar.html('<b> Espere <i class="fa fa-spinner fa-spin"></i></b>');
	btnBuscar.prop("disabled", true);
	
	txtFiltro.val(getDate());
	limpiarCamposCita();
	listarPacientes();
	
	showDatosPersonales();
	hideSituacionEconomica();
	//Inicializar eventos
	tratamientoObsMonoradicular.parent().hide();
	tratamientoObsSuperiorMulti.parent().parent().hide();
	tratamientoObsPPF.parent().hide();
	tratamientoObsIncrustaciones.parent().hide();
	tratamientoObsSuperiorPPR.parent().parent().hide();

	
	var t = 500;
	casoCasoDocente.change(function(){
	    if(this.checked) {
	    	casoCasoReferido.parent().show(t);
	    }else{
	    	casoCasoReferido.parent().hide(t);
	    	casoCasoReferido.val('');
	    }
	});
	
	
	tratamientoPPR.change(function(){
	    if(this.checked) {
	    	tratamientoObsSuperiorPPR.parent().parent().show(t);
	    }else{
	    	tratamientoObsSuperiorPPR.parent().parent().hide(t);
	    	tratamientoObsSuperiorPPR.prop('checked', false);
	    	tratamientoObsInferiorPPR.prop('checked', false);
	    }
	});
	
	tratamientoIncrustaciones.change(function() {
		if(this.checked) {
			tratamientoObsIncrustaciones.parent().show(t);
	    }else{
	    	tratamientoObsIncrustaciones.parent().hide(t);
	    	tratamientoObsIncrustaciones.val('');
	    }
	});
	
	tratamientoPPF.change(function() {
		if(this.checked) {
			tratamientoObsPPF.parent().show(t);
	    }else{
	    	tratamientoObsPPF.parent().hide(t);
	    	tratamientoObsPPF.val('');
	    }
	});
	
	tratamientoMultiradicular.change(function() {
		if(this.checked) {
			tratamientoObsSuperiorMulti.parent().parent().show(t);
	    }else{
	    	tratamientoObsSuperiorMulti.parent().parent().hide(t);
	    	tratamientoObsSuperiorMulti.prop('checked', false);
	    	tratamientoObsInferiorMulti.prop('checked', false);
	    }
	});
	
	tratamientoMonoradicular.change(function() {
		if(this.checked) {
			tratamientoObsMonoradicular.parent().show(t);
	    }else{
	    	tratamientoObsMonoradicular.parent().hide(t);
	    	tratamientoObsMonoradicular.val('');
	    }
	});
	btnGuardarTratamiento.click(function(){guardarTratamientos();});
	tabSituacionEconomica.click(function(){showSituacionEconomica();hideDatosPersonales();});
	tabDatosPersonales.click(function(){showDatosPersonales();hideSituacionEconomica();});
	
	btnConstancia.click(function(){cargarVistaImpresionConstancia();});
	btnImpresion.click(function(){cargarVistaImpresion(data);});
	btnNuevo.click(function(){btnImpresion.prop("disabled", true);modalNuevaCita.modal();});
	btnGuardar.click(function(){btnImpresion.prop("disabled", true);guardarCita();});
	btnGuardarPersonales.click(function(){guardarDatosPersonales();});
	btnGuardarCaso.click(function(){guardarDatosCaso();});
	// EVENTO DEL BOTON BUSCAR
	btnBuscar.click(function (e) {
			//Deshabilitar el boton y mostrar animacion de cargado
			$("#btn-buscar").html('<b> Espere <i class="fa fa-spinner fa-spin"></i></b>');
			$("#btn-buscar").prop("disabled", true);
			//Si la fecha es válida se procede a listar
			var fecha = txtFiltro.val();
			if(checkdate(fecha)){
				listarPacientes();
			}else{
				txtFiltro.val(getDate());
				listarPacientes();
			}
			
			e.preventDefault();
	});
	personalesTipoIngreso.change(function() {
		var val = $( "#tipoIngreso option:selected" ).val();
		if(val == 5){
			$('#txtOtro').parent().show(500);
		}else{
			$('#txtOtro').parent().hide(500);
		}
	});
	
	//CALENDARIO
	modalNuevaCita.on('shown.bs.modal', function () {
		//inicializar calendario
		var text;
		calendario.fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaDay'
			},
			lang: 'es',
			defaultDate: getDate(),
			editable: false,
			eventLimit: true, // allow "more" link when too many events
			events: /*function(start, end, timezone, callback) */
			{
				url: "/clinicas-crm/citas",
				data: function() { // a function that returns an object
						var val = $('#calendar .fc-center');
						text = val.html();
						val.html(text + '<i style="margin-top:8px;" class="fa fa-spinner fa-spin"></i>');
						$('#calendar .fc-next-button').prop("disabled", true);
						$('#calendar .fc-prev-button').prop("disabled", true);
						
			            return {
			            	operacion: "4"
			            };
		            },
				type: "POST",
				error: function() {
					desplegarMensaje("info", "No se pudieron cargar las citas. Presione F5 e intente de nuevo");
					var val = $('#calendar .fc-center i');
					val.remove();
					$('#calendar .fc-next-button').prop("disabled", false);
					$('#calendar .fc-prev-button').prop("disabled", false);
                },
                success: function (dataQ) {
					if (dataQ.resultado == "-100") {
						$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
					}
					else{
						var val = $('#calendar .fc-center i');
						val.remove();
						$('.fc-next-button').prop("disabled", false);
						$('.fc-prev-button').prop("disabled", false);
					}
                }
			},
			dayClick: function(date, jsEvent, view) {
				
				if(diaSeleccionado == null){
					$(this).css('background-color', '#EFEFEF');
			        diaSeleccionado = $(this);
				}else{
					diaSeleccionado.css('background-color', 'white');
					$(this).css('background-color', '#EFEFEF');
			        diaSeleccionado = $(this);
				}
		        txtFechaCita.val(date.format());
		    },
		    eventClick: function(calEvent, jsEvent, view) {
		    	//var day = ("0" + calEvent.start.getDate()).slice(-2);
				//var month = ("0" + (calEvent.start.getMonth() + 1)).slice(-2);
		        //var year = calEvent.start.getFullYear();
		        var result = calEvent.start.format();
		        var r = result.split("T");
		        txtFechaCita.val(r[0]);
		        console.log(r);
		        var elem = $('td.fc-day[data-date="' + r[0] + '"]');
		        if(diaSeleccionado == null){
					elem.css('background-color', '#EFEFEF');
			        diaSeleccionado = elem;
				}else{
					diaSeleccionado.css('background-color', 'white');
					elem.css('background-color', '#EFEFEF');
			        diaSeleccionado = elem;
				}
		    },
		    eventRender: function(event, element) {
		        $(element).tooltip(
		        		{
		        			title: function(){
		        				return '<h4>'+event.title+'</h4>'/* + 
			    				'<p><b>Hora:</b> '+ fe +'<br />'*/;
		        			},
		    				html:true,
		    				trigger:'hover',
		        			placement:'right',
		        			container: 'body',
		        			template: '<div class="tooltip tooltip-otro" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
		        		});
		    }
		});
		
	});
		
}

var txt_btn_guardar;
function guardarCita(){
	//validar Nombre
	lblAdvertencia.html('');
	var errores = false;
	if(txtNombre.val()){
		wrapNombre.removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar un nombre');
		lblAdvertencia.html(advertencia);
		wrapNombre.addClass("has-error");
		errores = true;
	}
	
	//validar fecha
	if(txtFechaCita.val()){
		//verificamos que se este seleccinando una fecha logicamente valida
		//una fecha que no sea antes a la fecha actual
		var now = getDate();
		if((Date.parse(now)) <= (Date.parse(txtFechaCita.val()))){
			wrapFechaCita.removeClass("has-error");
		}else{
			var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar una fecha a partir del día de hoy');
			lblAdvertencia.html(advertencia);
			errores = true;
		}
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar una fecha válida');
		lblAdvertencia.html(advertencia);
		wrapFechaCita.addClass("has-error");
		errores = true;
	}
	
	//validar hora
	if(txtHora.val()){
		wrapHora.removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar una hora');
		lblAdvertencia.html(advertencia);
		wrapHora.addClass("has-error");
		errores = true;
	}
	
	//sino existieron errores se procesa la informacion ingresada
	if(!errores){
		lblAdvertencia.html('');
		txt_btn_guardar = btnGuardar.html();
		btnGuardar.html(' Espere <i class="fa fa-spinner fa-spin"></i>');
		btnGuardar.prop("disabled", true);
		btnImpresion.click(function(){cargarVistaImpresion(data);});
		
		var parametros={};
		parametros.operacion=1;
		parametros.nombre = txtNombre.val();
		parametros.fecha = txtFechaCita.val();
		parametros.hora_inicio = txtHora.val() + ":00";
		var d = new Date();
		var res = txtHora.val().split(":");
		d.setHours(parseInt(res[0]) + 1, res[1]);
		parametros.hora_fin = addZero(d.getHours()) + ":" +addZero( d.getMinutes()) + ":00";
		
		//guardamos la info para uso de la boleta de recordatorio
		data.nombre = parametros.nombre;
		data.fecha = parametros.fecha;
		var da = new Date(data.fecha + " " + parametros.hora_inicio);
		var hora = formatearHora(da);
		data.hora_inicio = hora;
		
		data.hora_fin = parametros.hora_fin;
		
		$.post('/clinicas-crm/citas',parametros,callbackGuardarCita,'json');
	}
	
	
}


function callbackGuardarCita(response){
	
	btnGuardar.html(txt_btn_guardar);
	btnGuardar.prop("disabled", false);
	
	switch(response.resultado){
	
	case -1:
		desplegarMensaje("error", response.descripcion);
		break;
		
	case 1:
		desplegarMensaje("success", response.descripcion);
		btnImpresion.prop("disabled", false);
		//creamos un evento y lo ingresamos en la interfaz del calendario
		var newEvent = new Object();
		newEvent.title = data.nombre.toUpperCase();
		newEvent.start = new Date(data.fecha + " " + data.hora_inicio);
		newEvent.end = new Date(data.fecha + " " + data.hora_fin);
		newEvent.allDay = false;
		calendario.fullCalendar( 'renderEvent', newEvent );
		data.identificador = response.c_caso;
		//refrescamos la tabla
		table.ajax.reload( null, false );
		break;
		
	case -100:
		//Caduco
		$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
		break;
		
	case -101:
		desplegarMensaje("error", response.descripcion);
		break;
	
	}
	
	
	limpiarCamposCita();
}


var table;
function listarPacientes(){
		table = $('#example').DataTable({
		 "ajax": 
		 {	
			 "url": "/clinicas-crm/citas",
			 "data": 
				 function ( d ) {
				 	d.operacion = "2";
				 	d.filtro = $('#txtFiltro').val();
				 },
			"type": "POST"
		 },
		 "preDrawCallback": function( settings ,json) {
	
		  },
		  "ordering": true,
		  stateSave: true,
		 "bFilter": false,
			"bDestroy": true,
			"pagingType": "bootstrap",
			"lengthMenu": [[5, 10, 25, 50, 100, -1], [5, 10, 25, 50, 100, "Todos"]],
			"language": {
				"zeroRecords": "<center>No hay filas para mostrar</center>",
				"lengthMenu": "_MENU_&nbsp;&nbsp;Registros por página",
				"info": "Mostrando la página _PAGE_ de _PAGES_",
				"infoEmpty": "",
				"infoFiltered": "",
				"paginate": {
					"next": "Siguiente",
					"previous": "Anterior"
				}
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
					"data": "nombre"
				}, {
					"data": "fecha"
				}, {
					"data": "hora"
				}, {
					"data": "label_estado"
				}, {
					"data": "id_paciente",
					"visible": false
				}, {
					"data": "estado",
					"visible": false
				}
			],
			"initComplete": function (settings, json) {
				if (json.resultado == "-100") {
					$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
				} 
				
				$('#example_info').css('display', 'none');
				$('#example tbody tr').each(function () {
					$(this).find('td:eq(0)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 0
					$(this).find('td:eq(1)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 1
				});
				
				//HABILITA DE NUEVO EL BOTON DE BUSCAR
				$("#btn-buscar").html('Buscar&nbsp;&nbsp;<i class="fa fa-search">');
				$("#btn-buscar").prop("disabled", false);
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
		
	//table.columns.adjust().draw();
	$('.pagination li a').click(function(){
		console.log('pppp');
		$("[role='row']").each(function(){
			table.row($(this)).child.hide();
			}
		);
	});
	
	/*$('#example tbody').on('fnDrawCallback', 'tr', function () {
        var data = table.row( this ).data();
        alert( 'You clicked on '+data[0]+'\'s row' );
    } );*/
	
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
			/*var elem = $('#casoOP');
			if(row.data().caso_op){
				elem.val(row.data().caso_op);
			}
			
			if(row.data().caso_docente == false){
				chk.prop('checked', false);
			}else{
				chk.prop('checked', true);
			}*/
			
			/*$('#chkCaso').popover({
			    html : true, 
			    placement:"top",
			    content: function() {
			      //return $('#ventana').html();
			    	return 	'<div class="btn-group-vertical">'
					+ '<input type="text" class="form-control" id="txtReferido" placeholder="Referido a" maxlength="50" onkeypress="return permite(event, \'car\')">'
					+ '<button type="button" onclick="guardarPopOver();" class=" btn btn-default" id="btnPopup" aria-hidden="true">&nbsp;&nbsp;&nbsp;&nbsp;Aceptar&nbsp;&nbsp;&nbsp;&nbsp;</button>'
					+'</div>';
			    },
			    title:'<center><span class="text-info"><strong>Referido a:</strong></span></center>'
			});*/
			
			
			/*chk.change(function(){
				
				if(this.checked){
					
				}else{
					$('#changeSpin2').html('<i class="fa fa-spinner fa-spin"></i>');
					chk.prop('disabled', true);
					var val = $( "#casoOP option:selected" ).val();
					var parametros = {};
					parametros.operacion = 8;
					parametros.id = row.data().id;
					parametros.caso_op = val;
					parametros.caso_docente = false;
					parametros.caso_referido = "";
					$.post('/clinicas-crm/citas',parametros,callbackChkChange,'json');
				}
			});
			elem.change(function() {
				$('#changeSpin').html('<i class="fa fa-spinner fa-spin"></i>');
				elem.prop('disabled', true);
				var val = $( "#casoOP option:selected" ).val();
				var parametros = {};
				parametros.operacion = 8;
				parametros.id = row.data().id;
				parametros.caso_op = val;
				parametros.caso_docente = row.data().caso_docente;
				parametros.caso_referido = row.data().caso_referido;
				console.log(parametros);
				$.post('/clinicas-crm/citas',parametros,callbackChange,'json');
			});*/
			
			//btn btn-lg btn-green
			/*$(".btn-green").popover();
			$(".btn-green").click(function (e) {
				e.preventDefault();
			});*/

			tr.addClass('shown');
		}
	});
}


var txt_btn_personales;
function cargarUIPersonales(valores){
	lblAdver.html('');
	modalPersonales.modal();
	bloquearFormPersonales();
	var textPersonales = titleModalPersonales.html();
	titleModalPersonales.html(textPersonales + ' <i class="fa fa-spinner fa-spin"></i>');
	txt_btn_personales = btnGuardarPersonales.html();
	btnGuardarPersonales.html(' Espere <i class="fa fa-spinner fa-spin"></i>');
	btnGuardarPersonales.prop("disabled", true);
	
	//Lista el elemento
	var parametros = {};
	parametros.operacion = 3;
	parametros.id_paciente = valores.id_paciente;
	console.log(parametros);
	$.post('/clinicas-crm/citas',parametros,callbackUIPersonales,'json');
}


function callbackUIPersonales(response){
	var tiempo = 1600;
	var label = lblAdver;
	var boton = btnGuardarPersonales;
	titleModalPersonales.children('i').remove();
	boton.html(txt_btn_personales);
	boton.prop("disabled", false);
	switch(response.resultado){
	
		case -1:
			//titleModalPersonales.html('<div class="alert alert-danger" role="alert"><span style="font-weight:bolder;"> Error: </span>' + response.descripcion + '</div>' + textPersonales);
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			scrollElemento(label, tiempo);
			desplegarMensaje("error", response.descripcion);
			break;
			
		case 1:
			desbloquearFormPersonales();			
			setValuesInputsDatosPersonales(response.data[0]);
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			//titleModalPersonales.html('<div class="alert alert-danger" role="alert"><span style="font-weight:bolder;"> Error: </span>' + response.descripcion + '</div>' + textPersonales);
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			scrollElemento(label, tiempo);
			desplegarMensaje("error", response.descripcion);
			break;
			
		case -200:
			//titleModalPersonales.html('<div class="alert alert-danger" role="alert"><span style="font-weight:bolder;"> Error: </span>' + response.descripcion + '</div>' + textPersonales);
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			scrollElemento(label, tiempo);
			desplegarMensaje("error", response.descripcion);
			break;
	
	}
}


function setValuesInputsDatosPersonales(response){
	console.log(response);
	personalesID.val(response.id);
	personalesNombre.val(response.nombre);
	personalesDPI.val(response.persona_DPI);
	personalesEdad.val(response.edad);
	personalesDireccion.val(response.direccion);
	personalesTelefono.val(response.telefono);
	personalesEmail.val(response.correo);
	personalesEstadoCivil.val(response.estado_civil);
	personalesEscolaridad.val(response.escolaridad);
	personalesTrabajo.val(response.lugar_trabajo);
	personalesOcupacion.val(response.ocupacion);
	personalesHorario.val(response.horario);
	personalesIngresos.val(response.ingresos);
	personalesTipoIngreso.val(response.tipo_ingreso);
	if(response.tipo_ingreso == 5){$('#datos-personales #txtOtro').parent().stop().show(300);}
	else{$('#datos-personales #txtOtro').parent().stop().hide(300);}
	personalesOtro.val(response.otro);
	personalesEncargado.val(response.encargado_tratamiento);
	personalesRelacion.val(response.relacion_paciente);
	personalesTrabajoEncargado.val(response.trabajo_encargado);
	personalesSalarioEncargado.val(response.salario_encargado);
	$('input:radio[name=situacionEconomica]').filter('[value=' + response.situacion_economica + ']').prop('checked', true);
}


function guardarDatosPersonales(){
	lblAdver.html('');
	var errores = false;
	//validar Nombre
	if(personalesNombre.val().trim()){
		personalesWrapNombre.removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Ingrese el nombre del paciente');
		lblAdver.html(advertencia);
		personalesWrapNombre.addClass("has-error");
		errores = true;
	}
	
	//validar DPI
	if(personalesDPI.val().trim()){
		if(personalesDPI.val().length >= 13 || personalesDPI.val().trim() == "0"){
			personalesWrapDPI.removeClass("has-error");
		}else{
			var advertencia = obtenerAdvertencia(' alert-danger', 'Ingrese un número de DPI válido');
			lblAdver.html(advertencia);
			personalesWrapDPI.addClass("has-error");
			errores = true;
		}
	}
	
	//validar telefono
	if(personalesTelefono.val().trim()){
		if(personalesTelefono.val().length == 8 || personalesTelefono.val().trim() == "0"){
			personalesWrapTelefono.removeClass("has-error");
		}else{
			var advertencia = obtenerAdvertencia(' alert-danger', 'Ingrese un teléfono válido');
			lblAdver.html(advertencia);
			personalesWrapTelefono.addClass("has-error");
			errores = true;
		}
	}
	
	//validar correo
	if(personalesEmail.val().trim()){
		if(validateEmail(personalesEmail.val())){
			personalesWrapEmail.removeClass("has-error");
		}else{
			var advertencia = obtenerAdvertencia(' alert-danger', 'Ingrese un email válido');
			lblAdver.html(advertencia);
			personalesWrapEmail.addClass("has-error");
			errores = true;
		}
	}
	
	//validar Ingresos
	if(personalesIngresos.val().trim()){
		personalesWrapIngresos.removeClass("has-error");
	}else{
		personalesIngresos.val(0);
	}
	
	//validar TipoOtro
	var val = $( "#tipoIngreso option:selected" ).val();
	if(val == 5){
		if(personalesOtro.val().trim()){
			personalesWrapOtro.removeClass("has-error");
		}else{
			var advertencia = obtenerAdvertencia(' alert-danger', 'Ingrese otro tipo de ingreso');
			lblAdver.html(advertencia);
			personalesWrapOtro.addClass("has-error");
			errores = true;
		}
	}else{
		personalesOtro.val('');
	}
	
	if(!errores){
		lblAdvertencia.html('');
		txt_btn_personales = btnGuardarPersonales.html();
		btnGuardarPersonales.html(' Espere <i class="fa fa-spinner fa-spin"></i>');
		btnGuardarPersonales.prop("disabled", true);
		
		var parametros ={};
		parametros.operacion = 5;
		parametros.id_caso = dataRow.identificador;
		parametros.id = personalesID.val();
		parametros.nombre = personalesNombre.val();
		parametros.dpi = personalesDPI.val();
		parametros.edad = personalesEdad.val(); 
		parametros.direccion = personalesDireccion.val();
		parametros.telefono = personalesTelefono.val();
		parametros.email = personalesEmail.val();
		parametros.estado_civil = personalesEstadoCivil.val();
		parametros.escolaridad = personalesEscolaridad.val();
		parametros.trabajo = personalesTrabajo.val();
		parametros.ocupacion = personalesOcupacion.val(); 
		parametros.horario = personalesHorario.val();
		parametros.ingresos = personalesIngresos.val();
		parametros.tipo_ingreso = personalesTipoIngreso.val();
		parametros.otro = personalesOtro.val();
		parametros.encargado = personalesEncargado.val(); 
		parametros.relacion = personalesRelacion.val();
		parametros.trabajo_encargado = personalesTrabajoEncargado.val();
		parametros.salario_encargado = personalesSalarioEncargado.val();
		parametros.situacion_economica = $('input[name="situacionEconomica"]:checked').val();
		
		
		if(!parametros.situacion_economica){parametros.situacion_economica = null;}
		console.log(parametros);
		$.post('/clinicas-crm/citas',parametros,callbackGuardarDatosPersonales,'json');
	}
}



function callbackGuardarDatosPersonales(response){
	btnGuardarPersonales.html(txt_btn_personales);
	btnGuardarPersonales.prop("disabled", false);
	console.log(response.resultado);
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion);
			break;
			
		case 1:
			desplegarMensaje("success", "Se guardaron los datos exitosamente");
			//refrescamos la tabla
			//table.ajax.reload( null, false );
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			desplegarMensaje("error", response.descripcion);
			break;
			
		case -200:
			desplegarMensaje("error", response.descripcion);
	}
}


function bloquearFormPersonales(){
	limpiarCamposDatosPersonales();
	$('#datos-personales form input, #datos-personales form select').prop("disabled", true);
	btnGuardarPersonales.prop("disabled", true);
}




function desbloquearFormPersonales(){
	$('#datos-personales form input, #datos-personales form select').prop("disabled", false);
	btnGuardarPersonales.prop("disabled", false);
}

var txt_btn_tratamientos;
function cargarUITratamiento(valores){
	limpiarFormTratamientos();
	bloquearFormTratamientos();
	modalTratamiento.modal();
	tratamientoIDCaso.val(valores.identificador);
	lblAdvertenciaTratamiento.html('');
	var tex= titleModalTratamiento.html();
	titleModalTratamiento.html(tex+ ' <i class="fa fa-spinner fa-spin"></i>');
	txt_btn_tratamientos = btnGuardarTratamiento.html();
	btnGuardarTratamiento.html(' Espere <i class="fa fa-spinner fa-spin"></i>');
	btnGuardarTratamiento.prop("disabled", true);
	
	//Lista el elemento
	var parametros = {};
	parametros.operacion = 6;
	parametros.id_caso = valores.identificador;
	console.log(parametros);
	$.post('/clinicas-crm/citas',parametros,callbackUITratamiento,'json');
}



function callbackUITratamiento(response){
	var tiempo = 1600;
	var label = lblAdvertenciaTratamiento;
	var boton = btnGuardarTratamiento;
	titleModalTratamiento.children('i').remove();
	boton.html(txt_btn_tratamientos);
	boton.prop("disabled", false);
	switch(response.resultado){
	
		case -1:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			scrollElemento(label, tiempo);
			desplegarMensaje("error", response.descripcion);
			break;
			
		case 1:
			desbloquearFormTratamientos();
			setValuesInputsTratamientos(response.data);
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			scrollElemento(label, tiempo);
			desplegarMensaje("error", response.descripcion);
			break;
			
		case -200:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			scrollElemento(label, tiempo);
			desplegarMensaje("error", response.descripcion);
			break;
	
	}
}

function bloquearFormTratamientos(){
	$('#datos-diagnostico form input, #datos-diagnostico form select').prop("disabled", true);
}

function desbloquearFormTratamientos(){
	$('#datos-diagnostico form input, #datos-diagnostico form select').prop("disabled", false);
}

function limpiarFormTratamientos(){
	$('#datos-diagnostico div').removeClass("has-error");
	tratamientoObsSuperiorMulti.parent().parent().hide();
	tratamientoObsMonoradicular.parent().hide();
	tratamientoObsSuperiorPPR.parent().parent().hide();
	tratamientoObsPPF.parent().hide();
	tratamientoObsIncrustaciones.parent().hide();

	tratamientoGingivitis.prop('checked', false);
	tratamientoPeriodontitis.prop('checked', false);
	tratamientoObturaciones.prop('checked', false);
	tratamientoAmalgamas.prop('checked', false);
	tratamientoResinas.prop('checked', false);
	tratamientoIncrustaciones.prop('checked', false);
	tratamientoObsIncrustaciones.val('');
	tratamientoPT.prop('checked', false);
	tratamientoPPR.prop('checked', false);
	tratamientoObsSuperiorPPR.prop('checked', false);
	tratamientoObsInferiorPPR.prop('checked', false);
	tratamientoPPF.prop('checked', false);
	tratamientoObsPPF.val('');
	tratamientoEndodoncia.prop('checked', false);
	tratamientoMultiradicular.prop('checked', false);
	tratamientoObsSuperiorMulti.prop('checked', false);
	tratamientoObsInferiorMulti.prop('checked', false);
	tratamientoMonoradicular.prop('checked', false);
	tratamientoObsMonoradicular.val('');
	tratamientoCirugia.prop('checked', false);
	tratamientoExodoncia.prop('checked', false);
}


var valuesTratamientos = {};
function setValuesInputsTratamientos(valores){
	for (val in valores) { 
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "gingivitis"){
			tratamientoGingivitis.prop('checked', true);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "periodontitis"){
			tratamientoPeriodontitis.prop('checked', true);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "obturaciones"){
			tratamientoObturaciones.prop('checked', true);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "amalgamas"){
			tratamientoAmalgamas.prop('checked', true);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "resinas"){
			tratamientoResinas.prop('checked', true);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "incrustaciones"){
			tratamientoIncrustaciones.prop('checked', true);
			if(valores[val].observaciones.toLowerCase() == "sin observaciones"){
				tratamientoObsIncrustaciones.val("");
			}else{
				tratamientoObsIncrustaciones.val(valores[val].observaciones);
			}
			tratamientoObsIncrustaciones.parent().show(300);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "protesis parcial fija"){
			tratamientoPPF.prop('checked', true);
			if(valores[val].observaciones.toLowerCase() == "sin observaciones"){
				tratamientoObsPPF.val("");
			}else{
				tratamientoObsPPF.val(valores[val].observaciones);
			}
			tratamientoObsPPF.parent().show(300);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "protesis total"){
			tratamientoPT.prop('checked', true);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "protesis parcial removible superior"){
			tratamientoPPR.prop('checked', true);
			tratamientoObsSuperiorPPR.prop('checked', true);
			tratamientoObsSuperiorPPR.parent().parent().show(300);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "protesis parcial removible inferior"){
			tratamientoPPR.prop('checked', true);
			tratamientoObsInferiorPPR.prop('checked', true);
			tratamientoObsSuperiorPPR.parent().parent().show(300);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "endodoncia"){
			tratamientoEndodoncia.prop('checked', true);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "monoradicular"){
			tratamientoMonoradicular.prop('checked', true);
			if(valores[val].observaciones.toLowerCase() == "sin observaciones"){
				tratamientoObsMonoradicular.val("");
			}else{
				tratamientoObsMonoradicular.val(valores[val].observaciones);
			}
			tratamientoObsMonoradicular.parent().show(300);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "multiradicular s"){
			tratamientoMultiradicular.prop('checked', true);
			tratamientoObsSuperiorMulti.prop('checked', true);
			tratamientoObsSuperiorMulti.parent().parent().show(300);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "multiradicular i"){
			tratamientoMultiradicular.prop('checked', true);
			tratamientoObsInferiorMulti.prop('checked', true);
			tratamientoObsSuperiorMulti.parent().parent().show(300);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "cirugia"){
			tratamientoCirugia.prop('checked', true);
		}
		
		if(valores[val].tratamiento_requerido_txt.toLowerCase() == "exodoncia"){
			tratamientoExodoncia.prop('checked', true);
		}
	}
	
	retenerValoresTratamientos();
}


function retenerValoresTratamientos(){
	if(tratamientoGingivitis.is(':checked')){valuesTratamientos.gingivitis = true;}
	else{valuesTratamientos.gingivitis = false;}
	
	if(tratamientoPeriodontitis.is(':checked')){valuesTratamientos.periodontitis = true;}
	else{valuesTratamientos.periodontitis = false;}
	
	if(tratamientoObturaciones.is(':checked')){valuesTratamientos.obturaciones = true;}
	else{valuesTratamientos.obturaciones = false;}
	
	if(tratamientoAmalgamas.is(':checked')){valuesTratamientos.amalgamas = true;}
	else{valuesTratamientos.amalgamas = false;}
	
	if(tratamientoResinas.is(':checked')){valuesTratamientos.resinas = true;}
	else{valuesTratamientos.resinas = false;}
	
	if(tratamientoIncrustaciones.is(':checked')){valuesTratamientos.incrustaciones = true;}
	else{valuesTratamientos.incrustaciones = false;}
	
	if(tratamientoPPF.is(':checked')){valuesTratamientos.ppf = true;}
	else{valuesTratamientos.ppf = false;}
	
	if(tratamientoPT.is(':checked')){valuesTratamientos.pt = true;}
	else{valuesTratamientos.pt = false;}
	
	if(tratamientoObsInferiorPPR.is(':checked')){valuesTratamientos.ppr_inferior = true;}
	else{valuesTratamientos.ppr_inferior = false;}
	
	if(tratamientoObsSuperiorPPR.is(':checked')){valuesTratamientos.ppr_superior = true;}
	else{valuesTratamientos.ppr_superior = false;}
	
	if(tratamientoEndodoncia.is(':checked')){valuesTratamientos.endodoncia = true;}
	else{valuesTratamientos.endodoncia = false;}
	
	if(tratamientoMonoradicular.is(':checked')){valuesTratamientos.monoradicular = true;}
	else{valuesTratamientos.monoradicular = false;}
	
	if(tratamientoObsSuperiorMulti.is(':checked')){valuesTratamientos.multiradicular_superior = true;}
	else{valuesTratamientos.multiradicular_superior = false;}
	
	if(tratamientoObsInferiorMulti.is(':checked')){valuesTratamientos.multiradicular_inferior = true;}
	else{valuesTratamientos.multiradicular_inferior = false;}
	
	if(tratamientoCirugia.is(':checked')){valuesTratamientos.cirugia = true;}
	else{valuesTratamientos.cirugia = false;}
	
	if(tratamientoExodoncia.is(':checked')){valuesTratamientos.exodoncia = true;}
	else{valuesTratamientos.exodoncia = false;}
}


function guardarTratamientos(){
	lblAdvertenciaTratamiento.html('');
	var errores = false;
	
	//validar Protesis parcial removible
	if(tratamientoPPR.is(':checked')){
		if(tratamientoObsSuperiorPPR.is(':checked') || tratamientoObsInferiorPPR.is(':checked')){
			tratamientoObsSuperiorPPR.parent().parent().removeClass('has-error');
		}
		else{
			tratamientoObsSuperiorPPR.parent().parent().addClass('has-error');
			var advertencia = obtenerAdvertencia(' alert-danger', 'Debe seleccionar al menos una opción');
			lblAdvertenciaTratamiento.html(advertencia);
			errores = true;
		}
	}
	
	//validar multiradicular
	if(tratamientoMultiradicular.is(':checked')){
		if(tratamientoObsSuperiorMulti.is(':checked') || tratamientoObsInferiorMulti.is(':checked')){
			tratamientoObsSuperiorMulti.parent().parent().removeClass('has-error');
		}
		else{
			tratamientoObsSuperiorMulti.parent().parent().addClass('has-error');
			var advertencia = obtenerAdvertencia(' alert-danger', 'Debe seleccionar al menos una opción');
			lblAdvertenciaTratamiento.html(advertencia);
			errores = true;
		}
	}
	
	if(!errores){
		lblAdvertenciaTratamiento.html('');
		txt_btn_tratamientos = btnGuardarTratamiento.html();
		btnGuardarTratamiento.html(' Espere <i class="fa fa-spinner fa-spin"></i>');
		btnGuardarTratamiento.prop("disabled", true);
		bloquearFormTratamientos();
		var parametros = {};
		parametros.operacion = 7;
		parametros.id_caso = tratamientoIDCaso.val();
		parametros.obs_incrustaciones = tratamientoObsIncrustaciones.val();
		parametros.obs_monoradicular = tratamientoObsMonoradicular.val();
		parametros.obs_ppf = tratamientoObsPPF.val();
		if(tratamientoGingivitis.is(':checked')) 	{parametros.gingivitis = true;}else{parametros.gingivitis = false;}
		if(tratamientoPeriodontitis.is(':checked')) {parametros.periodontitis = true;}else{parametros.periodontitis = false;}
		if(tratamientoObturaciones.is(':checked')) 	{parametros.obturaciones = true;}else{parametros.obturaciones = false;}
		if(tratamientoAmalgamas.is(':checked')) 	{parametros.amalgamas = true;}else{parametros.amalgamas = false;}
		if(tratamientoResinas.is(':checked')) 		{parametros.resinas = true;}else{parametros.resinas = false;}
		if(tratamientoIncrustaciones.is(':checked')){parametros.incrustaciones = true;}else{parametros.incrustaciones = false;}
		if(tratamientoPT.is(':checked'))			{parametros.pt = true;}else{parametros.pt = false;}
		if(tratamientoPPR.is(':checked')) 			{parametros.ppr = true;}else{parametros.ppr = false;}
		if(tratamientoObsSuperiorPPR.is(':checked')){parametros.obs_superiorPPR = true;}else{parametros.obs_superiorPPR = false;}
		if(tratamientoObsInferiorPPR.is(':checked')){parametros.obs_inferiorPPR = true;}else{parametros.obs_inferiorPPR = false;}
		if(tratamientoPPF.is(':checked')) 			{parametros.ppf = true;}else{parametros.ppf = false;}
		if(tratamientoEndodoncia.is(':checked')) 	{parametros.endodoncia = true;}else{parametros.endodoncia = false;}
		if(tratamientoMultiradicular.is(':checked')){parametros.multiradicular = true;}else{parametros.multiradicular = false;}
		if(tratamientoObsSuperiorMulti.is(':checked')) 	{parametros.obs_superiorMulti = true;}else{parametros.obs_superiorMulti = false;}
		if(tratamientoObsInferiorMulti.is(':checked')) 	{parametros.obs_inferiorMulti = true;}else{parametros.obs_inferiorMulti = false;}
		if(tratamientoMonoradicular.is(':checked')) 	{parametros.monoradicular = true;}else{parametros.monoradicular = false;}
		if(tratamientoCirugia.is(':checked')) 			{parametros.cirugia= true;}else{parametros.cirugia = false;}
		if(tratamientoExodoncia.is(':checked')) 		{parametros.exodoncia= true;}else{parametros.exodoncia = false;}
		
		parametros.gingivitis2 		= valuesTratamientos.gingivitis;
		parametros.periodontitis2	= valuesTratamientos.periodontitis;
		parametros.obturaciones2	= valuesTratamientos.obturaciones;
		parametros.amalgamas2		= valuesTratamientos.amalgamas;
		parametros.resinas2			= valuesTratamientos.resinas;
		parametros.incrustaciones2	= valuesTratamientos.incrustaciones;
		parametros.pt2				= valuesTratamientos.pt;
		parametros.obs_superiorPPR2	= valuesTratamientos.ppr_superior;
		parametros.obs_inferiorPPR2	= valuesTratamientos.ppr_inferior;
		parametros.ppf2				= valuesTratamientos.ppf;
		parametros.endodoncia2		= valuesTratamientos.endodoncia;
		parametros.obs_superiorMulti2= valuesTratamientos.multiradicular_superior;
		parametros.obs_inferiorMulti2= valuesTratamientos.multiradicular_inferior;
		parametros.monoradicular2	= valuesTratamientos.monoradicular;
		parametros.cirugia2			= valuesTratamientos.cirugia;
		parametros.exodoncia2		= valuesTratamientos.exodoncia;
		
		console.log(parametros);
		$.post('/clinicas-crm/citas',parametros,callbackGuardarTratamientos,'json');
	}
}


function callbackGuardarTratamientos(response){
	btnGuardarTratamiento.html(txt_btn_tratamientos);
	btnGuardarTratamiento.prop("disabled", false);
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion);
			break;
			
		case 1:
			retenerValoresTratamientos();
			desbloquearFormTratamientos();
			desplegarMensaje("success", "Se guardaron los datos correctmente");
			//refrescamos la tabla
			table.ajax.reload( null, false );
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			desplegarMensaje("error", response.descripcion);
			break;
			
		case -200:
			desplegarMensaje("error", response.descripcion);
	}
}

var txt_btn_caso;
function cargarUICaso(valores){
	limpiarFormCaso();
	bloquearFormCaso();
	modalCaso.modal();
	casoIDCaso.val(valores.identificador);
	lblAdvertenciaCaso.html('');
	var tex= titleModalCaso.html();
	titleModalCaso.html(tex+ ' <i class="fa fa-spinner fa-spin"></i>');
	txt_btn_caso = btnGuardarCaso.html();
	btnGuardarCaso.html(' Espere <i class="fa fa-spinner fa-spin"></i>');
	btnGuardarCaso.prop("disabled", true);
	
	//Lista el elemento
	var parametros = {};
	parametros.operacion = 2;
	parametros.id_caso = valores.identificador;
	console.log(parametros);
	$.post('/clinicas-crm/citas',parametros,callbackUICaso,'json');
}


function callbackUICaso(response){
	var tiempo = 1600;
	var label = lblAdvertenciaCaso;
	var boton = btnGuardarCaso;
	titleModalCaso.children('i').remove();
	boton.html(txt_btn_caso);
	boton.prop("disabled", false);
	switch(response.resultado){
	
		case -1:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			scrollElemento(label, tiempo);
			desplegarMensaje("error", response.descripcion);
			break;
			
		case 1:
			desbloquearFormCaso();
			setValuesInputsCaso(response.data[0]);
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			scrollElemento(label, tiempo);
			desplegarMensaje("error", response.descripcion);
			break;
			
		case -200:
			var advertencia = obtenerAdvertencia(' alert-danger', '<span style="font-weight:bolder;"> Error: </span>' + response.descripcion);
			label.html(advertencia);
			scrollElemento(label, tiempo);
			desplegarMensaje("error", response.descripcion);
			break;
	
	}
}


function setValuesInputsCaso(response){
	console.log(response);
	if(response.caso_docente == true){
		casoCasoDocente.prop('checked', true);
		casoCasoReferido.val(response.caso_referido);
		casoCasoReferido.parent().show(300);
	}else{
		casoCasoDocente.prop('checked', false);
	}
	casoCasoOp.val(response.caso_op)
	casoObservacion.val(response.caso_observacion);
}


function limpiarFormCaso(){
	$('#datos-caso div').removeClass("has-error");
	casoCasoReferido.parent().hide();
	casoCasoDocente.prop('checked', false);
	casoCasoReferido.val('');
	casoCasoOp.val('');
	casoObservacion.val('');
}


function bloquearFormCaso(){
	$('#datos-caso form input, #datos-caso form select').prop("disabled", true);
}


function desbloquearFormCaso(){
	$('#datos-caso form input, #datos-caso form select').prop("disabled", false);
}


function guardarDatosCaso(){
	var label = lblAdvertenciaCaso;
	var boton = btnGuardarCaso;
	var state = "";
	label.html('');
	var errores = false;
	//validar Caso Docente
	if(casoCasoDocente.is(':checked')){
		if(casoCasoReferido.val().trim()){
			casoWrapCasoReferido.removeClass("has-error");
		}else{
			var advertencia = obtenerAdvertencia(' alert-danger', 'Ingrese a quien va referido');
			label.html(advertencia);
			casoWrapCasoReferido.addClass("has-error");
			errores = true;
		}
	}else{
		casoCasoReferido.val('');
	}
	
	//validar caso asignado op
	var val = $( "#casoOP option:selected" ).val();
	console.log(val)
	if(/*$("#casoOP").attr("selectedIndex") == 0 &&*/ typeof val !== "undefined"){
		casoWrapCasoOp.removeClass("has-error");
	}else{
		var advertencia = obtenerAdvertencia(' alert-danger', 'Ingrese a que tipo a ha ser asignado');
		label.html(advertencia);
		casoWrapCasoOp.addClass("has-error");
		errores = true;
	}
	
	
	//validar que estado se registrara
	if(casoCasoDocente.is(':checked') && val == 4){
		state = "5";
	}else if(casoCasoDocente.is(':checked') && val != 4){
		state = "5";
	}else if(!casoCasoDocente.is(':checked') && val == 4){
		state = "4";
	}else{
		state = "2";
	}
	
	
	if(!errores){
		label.html('');
		txt_btn_caso = boton.html();
		boton.html(' Espere <i class="fa fa-spinner fa-spin"></i>');
		boton.prop("disabled", true);
		
		var parametros ={};
		parametros.operacion = 8;
		parametros.caso_estado = state;
		parametros.id = casoIDCaso.val();
		if(casoCasoDocente.is(':checked')){parametros.caso_docente = true;}else{parametros.caso_docente = false;}
		parametros.caso_referido = casoCasoReferido.val();
		parametros.caso_op = casoCasoOp.val();
		parametros.observacion = casoObservacion.val();
		

		$.post('/clinicas-crm/citas',parametros,callbackGuardarDatosCaso,'json');
	}
}



function callbackGuardarDatosCaso(response){
	var boton = btnGuardarCaso;
	boton.html(txt_btn_caso);
	boton.prop("disabled", false);
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion);
			break;
			
		case 1:
			desbloquearFormCaso();
			desplegarMensaje("success", "Se guardaron los datos correctmente");
			//refrescamos la tabla
			table.ajax.reload( null, false );
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			desplegarMensaje("error", response.descripcion);
			break;
			
		case -200:
			desplegarMensaje("error", response.descripcion);
	}
}


function noAcudioCita(data){
	confirmacionCambiarEstado(data);
}

function confirmacionCambiarEstado(data){
	Messenger.options = {
		    extraClasses: 'messenger-fixed messenger-on-top messenger-on-center',
		}
	var mensajito=Messenger().post({
		extraClasses: 'messenger-fixed messenger-on-center messenger-on-top',
		  message: "<center>¿Está seguro que desea registrar que el paciente no acudió a la cita?</center>",
		  type:'info',
		  
		  showCloseButton:true,
		  id:1,
		  hideAfter:36000,
		  actions: {
		  	aceptar: {
		 	label: "Cambiar",
		   	action: function(){
		   		cambiarEstado(data, 3);
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

function cambiarEstado(data, estado){
	$('#changeSpin').html('<i class="fa fa-spinner fa-spin"></i>');
	var parametros = {};
	parametros.operacion = 8;
	parametros.id = data.identificador;
	parametros.caso_estado = estado;
	$.post('/clinicas-crm/citas',parametros,callbackCambiarEstado,'json');
}

function callbackCambiarEstado(response){
	var boton = $('#changeSpin');
	boton.html('');
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion);
			break;
			
		case 1:
			//desbloquearFormCaso();
			//desplegarMensaje("success", "Se guardaron los datos correctmente");
			table.ajax.reload( null, false );
			break;
			
		case -100:
			//Caduco
			$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
			break;
			
		case -101:
			desplegarMensaje("error", response.descripcion);
			break;
			
		case -200:
			desplegarMensaje("error", response.descripcion);
	}
}


function mostrarVistaDatos(data){
	var parametros = {}
	parametros.operacion = 9;
	parametros.id_paciente = data.id_paciente;
	parametros.id_caso = data.identificador;
	$("#btn-ver").html('<b> Espere <i class="fa fa-spinner fa-spin"></i></b>');
	$("#btn-ver").prop("disabled", true);
	$.post('/clinicas-crm/citas',parametros,callbackMostrarVistaDatos,'json');
}

function callbackMostrarVistaDatos(response){
	var tiempo = 10;
	$("#btn-ver").html(' Ver datos');
	$("#btn-ver").prop("disabled", false);
	switch(response.resultado){
	
		case -1:
			desplegarMensaje("error", response.descripcion, tiempo);
			break;
			
		case 1:
			cargarVistaImpresion2(response.data_general[0], response.data_tratamientos, response.data_caso[0]);
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
	dataRow.identificador = d.id;
	dataRow.nombre = d.nombre;
	dataRow.fecha = d.fecha;
	dataRow.hora_inicio = d.hora;
	dataRow.id_paciente = d.id_paciente;
	dataRow.caso_op = d.caso_op;
	dataRow.caso_docente = d.caso_docente;
	dataRow.caso_referido = d.caso_referido;
	dataRow.caso_estado = d.estado;
	
	var personales 			= '<td><a data-toggle="modal" data-target="#basicModal"  href="#" onclick="cargarUIPersonales(dataRow)"><button class="btn btn-default" >Datos Generales</button></a></td>';
	var tratamiento 		= '<td><a data-toggle="modal" data-target="#basicModal"  href="#" onclick="cargarUITratamiento(dataRow)"><button class="btn btn-default" >Diagnóstico</button></a></td>';
	var caso 				= '<td><a data-toggle="modal" data-target="#basicModal"  href="#" onclick="cargarUICaso(dataRow)"><button class="btn btn-default" >Datos Caso</button></a></td>';
	var recordatorio 		= '<td><a onclick="cargarVistaImpresion(dataRow)"><button class="btn btn-blue" >Recordatorio</button></a></td>';
	var noacudio 			='<td><a onclick="noAcudioCita(dataRow);"><button class="btn btn-red" >No acudió a cita</button></a></td>';
	var cambiarAingresado 	='<td><a onclick="cambiarEstado(dataRow, 1);"><button class="btn btn-default" >Habilitar paciente</button></a></td>';
	var ver_datos 			= '<td><a data-toggle="modal" data-target="#basicModal"  href="#" onclick="mostrarVistaDatos(dataRow)"><button id="btn-ver" class="btn btn-green" >Ver datos</button></a></td>';
	var spin 				= '<td><div id="changeSpin"></div></td>';
	var titulo 				= 'Opciones';
	
	//si es ingresado
	if(dataRow.caso_estado == 1){
		//se muestran todas las opciones exceptuando cambiar a ingresado
		cambiarAingresado = '';
		ver_datos= '';
	}
	//si es registrado
	else if(dataRow.caso_estado == 2){
		cambiarAingresado = '';
		noacudio = '';
	}
	//si es ausente
	else if(dataRow.caso_estado == 3){
		personales = '';
		tratamiento = '';
		caso = '';
		recordatorio = '';
		noacudio = '';
		ver_datos= '';
	}
	//si es rechazado
	else if(dataRow.caso_estado == 4){
		personales = '';
		tratamiento = '';
		cambiarAingresado = '';
		noacudio = '';
	}
	//si es referido
	else if(dataRow.caso_estado == 5){
		personales = '';
		tratamiento = '';
		cambiarAingresado = '';
		noacudio = '';
	}
	//si es apartado
	else if(dataRow.caso_estado == 6){
		personales = '';
		tratamiento = '';
		cambiarAingresado = '';
		noacudio = '';
	}
	//si es asignado
	else if(dataRow.caso_estado == 7){
		personales = '';
		tratamiento = '';
		cambiarAingresado = '';
		noacudio = '';
	}
	
	return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' + 
	'<tr>' + 
	'<td>' + titulo + '</td>' + 
	'</tr>' + 
	'<tr>' + 
	personales +
	tratamiento +
	caso +
	recordatorio + 
	noacudio +
	cambiarAingresado +
	ver_datos +
	spin + 
	//'<td><div class="form-group" id="wrapCasoOP" style="margin-bottom:0px;"><select placeholder="holis" class="form-control" id="casoOP"><option value="" disabled selected>Asignado a O.P. de</option><option value="1">4º año </option><option value="2">5º año </option><option value="3">PRC </option><option value="4">Postgrado </option></select></div></td><td><div id="changeSpin"></div></td>' +
	//'<td><label class="checkbox-inline"><input id="chkCaso" type="checkbox"> Caso Docente</label></td><td><div id="changeSpin2"></div></td>' + 
	
	
	
	'</tr>' + 
	'</table>';
}




function checkdate(val){
	var v = val.split("-");
	if(isNaN(v[0]) == false && isNaN(v[1]) == false && isNaN(v[2]) == false){
		return true;
	}
	else{
		return false;
	}
}
var tiem = 600;
function showSituacionEconomica(){
	camposSituacionEconomica.stop().show(tiem);
	tabSituacionEconomica.parent().addClass("active");
	tabDatosPersonales.parent().removeClass("active");
}

function hideSituacionEconomica(){
	camposSituacionEconomica.stop().hide(tiem);
}



function showDatosPersonales(){
	camposDatosPersonales.stop().show(tiem);
	tabSituacionEconomica.parent().removeClass("active");
	tabDatosPersonales.parent().addClass("active");
}

function hideDatosPersonales(){
	camposDatosPersonales.stop().hide(tiem);
}


function limpiarCamposCita(){
	$('#nueva-cita div').removeClass("has-error");
	txtNombre.val('');
	txtFechaCita.val(getDate());
	txtHora.val('07:30');
	wrapNombre.removeClass("has-error");
	wrapFechaCita.removeClass("has-error");
	wrapHora.removeClass("has-error");
	lblAdvertencia.html('');
}

function limpiarCamposDatosPersonales(){
	$('#datos-personales div').removeClass("has-error");
	personalesNombre.val('');
	personalesDPI.val('');
	personalesEdad.val(''); 
	personalesDireccion.val('');
	personalesTelefono.val('');
	personalesEmail.val('');
	personalesEstadoCivil.val('');
	personalesEscolaridad.val('');
	personalesTrabajo.val('');
	personalesOcupacion.val(''); 
	personalesHorario.val('');
	personalesIngresos.val('');
	personalesTipoIngreso.val('');
	personalesOtro.val('');
	$('#datos-personales #txtOtro').parent().hide();
	personalesEncargado.val(''); 
	personalesRelacion.val('');
	personalesTrabajoEncargado.val('');
	personalesSalarioEncargado.val('');
	$('input[name="situacionEconomica"]:checked').each(function(){
		$(this).prop('checked', false);
	});
}

function validateEmail(sEmail) {
    var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    if (filter.test(sEmail)) {
        return true;
    }
    else {
        return false;
    }
}

function desplegarMensaje(clase, mensaje) {
	Messenger.options = {
	    extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
	}
	Messenger().post({message:'<center>'+ mensaje+'</center>',type: clase,showCloseButton: true});
}

function desplegarMensaje(clase, mensaje, time) {
	Messenger.options = {
	    extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
	}
	Messenger().post({message:'<center>'+ mensaje+'</center>',type: clase,showCloseButton: true, hideAfter:time});
}


function obtenerAdvertencia(clase, mensaje) {
	return '<div class="alert ' + clase + '">' + mensaje + '</div>';
}

function formatearHora(date) {
  var hours = date.getHours();
  var minutes = date.getMinutes();
  var ampm = hours >= 12 ? 'pm' : 'am';
  hours = hours % 12;
  hours = hours ? hours : 12; // the hour '0' should be '12'
  minutes = minutes < 10 ? '0'+minutes : minutes;
  var strTime = hours + ':' + minutes + ' ' + ampm;
  return strTime;
}

function getDate(){
	var now = new Date();

	var day = ("0" + now.getDate()).slice(-2);
	var month = ("0" + (now.getMonth() + 1)).slice(-2);

	var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
	return today;
}

function formatearFecha(fechaString){
	var fe = fechaString.split("/");
	var year = fe[0];
	var month = fe[1];
	var day = fe[2];
	
	return day + "/" + month + "/" + year;
}

function addZero(i) {
    if (i < 10) {
        i = "0" + i;
    }
    return i;
}

function scrollElemento(element, time){
    var speed = (time) ? time : 600;
    $('#datos-personales').animate({
        scrollTop: $(element).offset().top
    }, speed);
}

function permite(elEvento, permitidos) {
	// Variables que definen los caracteres permitidos
	var numeros = "0123456789.";
	var solo_num = "0123456789";
	var caracteres = " abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ:-;'_";
	var numeros_caracteres = numeros + caracteres;
	var teclas_especiales = [8];
	// 8 = BackSpace, 46 = Supr, 37 = flecha izquierda, 39 = flecha derecha
				
	// Seleccionar los caracteres a partir del parámetro de la función
	switch(permitidos) {
	  case 'solo_num':
		  permitidos = solo_num;
		  break;
	  case 'num':
	    permitidos = numeros;
	    break;
	  case 'car':
	    permitidos = caracteres;
	    break;
	  case 'num_car':
	    permitidos = numeros_caracteres;
	    break;
	}
				 // Obtener la tecla pulsada 
	var evento = elEvento || window.event;
	var codigoCaracter = evento.charCode || evento.keyCode;
	var caracter = String.fromCharCode(codigoCaracter);
				 // Comprobar si la tecla pulsada es alguna de las teclas especiales
	// (teclas de borrado y flechas horizontales)
	var tecla_especial = false;
	for(var i in teclas_especiales) {
	  if(codigoCaracter == teclas_especiales[i]) {
	    tecla_especial = true;
	    break;
	  }
	}
	
	 // Comprobar si la tecla pulsada se encuentra en los caracteres permitidos
	 // o si es una tecla especial
	//console.log(permitidos.indexOf(caracter));
	//console.log(tecla_especial);
	//console.log(codigoCaracter);
	return permitidos.indexOf(caracter) != -1 || tecla_especial;
}
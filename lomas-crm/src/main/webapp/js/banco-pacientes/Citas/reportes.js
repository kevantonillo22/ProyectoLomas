var dataRow; //variable para guardar info de cada tupla de la tabla
var selector;
var exportar;
var tablas;
var texto_espere;
var data_reporte1;
var data_reporte2;
var data_reporte3;

var charts;

$(inicializar);

function inicializar(){
	texto_espere = '<b> Espere <i class="fa fa-spinner fa-spin"></i></b>';
	dataRow = {};
	selector = {};
	charts = {};
	selector.reporte 			= $('#selector');
	selector.boton 				= $('#btn-generar');
	selector.fecha_inicio		= $('#datepicker1');
	selector.fecha_fin			= $('#datepicker2');
	selector.mes 				= $('#datepicker3');
	selector.anio 				= $('#datepicker4');
	selector.lblAdvertencia 	= $('#lblAdvertencia');
	selector.wrap_fecha_inicio 	= $('#wrapDatepicker1');
	selector.wrap_fecha_fin		= $('#wrapDatepicker2');
	selector.wrap_mes 			= $('#wrapDatepicker3');
	selector.wrap_anio 			= $('#wrapDatepicker4');
	
	selector.reporte.change(function(){
		cambioSelectReporte();
	});
	selector.boton.click(function(){
		generarReporteSeleccionado();
	});
	
	exportar = {};
	exportar.boton_csv = $('#btn-csv');
	exportar.boton_pdf = $('#btn-pdf');
	
	exportar.boton_csv.click(function(){
		generarCSV();
	});
	
	exportar.boton_pdf.click(function(){
		generarPDF();
	});
	
	charts.chart1 			= $('#chart1');
	charts.chart2 			= $('#chart2');
	charts.chart3 			= $('#chart3');
	
	tablas = {};
	tablas.reporte1 	= $('#example1');
	tablas.reporte2 	= $('#example2');
	tablas.reporte3 	= $('#example3');
	tablas.reporte2.hide();
	tablas.reporte3.hide();
	
	//inicializar campos fechas
	selector.fecha_inicio.datepicker({
		format: "dd/mm/yyyy",
	    language: "es"/*,
	    todayBtn: "linked",
	    todayHighlight: true*/
	});
	selector.fecha_fin.datepicker({
		format: "dd/mm/yyyy",
	    language: "es"/*,
	    todayBtn: "linked",
	    todayHighlight: true*/
    });
	selector.mes.datepicker( {
	    format: "mm/yyyy",
	    viewMode: "months", 
	    minViewMode: "months",
	    language: "es"
	});
	selector.anio.datepicker( {
	    format: "yyyy",
	    viewMode: "years", 
	    minViewMode: "years",
	    language: "es"
	});
	deshabilitarExportar();
	//controlar el rango de las fechas
	selector.fecha_inicio.on("changeDate", function (e) {
		selector.fecha_fin.datepicker('setStartDate', e.date);
    });
	selector.fecha_fin.on("changeDate", function (e) {
		selector.fecha_inicio.datepicker('setEndDate', e.date);
    });
}


function cambioSelectReporte(){
	var time = 0;
	if(selector.reporte.val() == 1) {
		if (typeof data_reporte1 !== 'undefined') {habilitarExportar();}
		else{deshabilitarExportar();}
		tablas.reporte1.show(time);
		tablas.reporte2.hide(time);
		tablas.reporte3.hide(time);
		
		habilitarReporte1(400);
    }else if(selector.reporte.val() == 2){
    	if (typeof data_reporte2 !== 'undefined') {habilitarExportar();}
		else{deshabilitarExportar();}
    	tablas.reporte1.hide(time);
		tablas.reporte2.show(time);
		tablas.reporte3.hide(time);
		
		habilitarReporte2(400);
    }else{
    	if (typeof data_reporte3 !== 'undefined') {habilitarExportar();}
		else{deshabilitarExportar();}
    	tablas.reporte1.hide(time);
		tablas.reporte2.hide(time);
		tablas.reporte3.show(time);
		
		habilitarReporte3(400);
    }
}


function habilitarExportar(){
	exportar.boton_csv.prop("disabled", false);
	exportar.boton_pdf.prop("disabled", false);
}


function deshabilitarExportar(){
	exportar.boton_csv.prop("disabled", true);
	exportar.boton_pdf.prop("disabled", true);
}


var tiempo_charts = 500;
function habilitarReporte1(time){
	selector.fecha_inicio.parent().show(time);
	selector.fecha_fin.parent().show(time);
	selector.mes.parent().hide(time);
	selector.anio.parent().hide(time);
	selector.anio.children('input').val('');
	selector.mes.children('input').val('');
	
	charts.chart1.show();
	charts.chart2.show();
	charts.chart3.hide(tiempo_charts);
}


function habilitarReporte2(time){

	selector.fecha_inicio.parent().hide(time);
	selector.fecha_fin.parent().hide(time);
	selector.mes.parent().show(time);
	selector.anio.parent().hide(time);
	selector.anio.children('input').val('');
	selector.fecha_inicio.children('input').val('');
	selector.fecha_fin.children('input').val('');
	
	charts.chart1.hide();
	charts.chart2.hide();
	charts.chart3.hide(tiempo_charts);
}

function habilitarReporte3(time){
	selector.fecha_inicio.parent().hide(time);
	selector.fecha_fin.parent().hide(time);
	selector.mes.parent().hide(time);
	selector.mes.children('input').val('');
	selector.anio.parent().show(time);
	
	charts.chart1.hide();
	charts.chart2.hide();
	charts.chart3.show(tiempo_charts);
}

function generarReporteSeleccionado(){
	var label = selector.lblAdvertencia;
	label.html('');
	var errores = false;
	
	if(selector.reporte.val() == 1){
		
		//validar fecha inicio
		if(selector.fecha_inicio.children('input').val().trim()){
			selector.wrap_fecha_inicio.removeClass("has-error");
		}else{
			var advertencia = obtenerAdvertencia(' alert-danger', 'Debe de ingresar una fecha de inicio');
			label.html(advertencia);
			selector.wrap_fecha_inicio.addClass("has-error");
			errores = true;
		}
		
		//validar fecha fin
		if(selector.fecha_fin.children('input').val().trim()){
			selector.wrap_fecha_fin.removeClass("has-error");
		}else{
			var advertencia = obtenerAdvertencia(' alert-danger', 'Debe de ingresar una fecha final');
			label.html(advertencia);
			selector.wrap_fecha_fin.addClass("has-error");
			errores = true;
		}
		
		//si no hay errores
		if(!errores){
			listarReporte1();
		}
	}else if(selector.reporte.val() == 2){
		//validar mes
		if(selector.mes.children('input').val().trim()){
			selector.wrap_mes.removeClass("has-error");
		}else{
			var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar mes y año');
			label.html(advertencia);
			selector.wrap_mes.addClass("has-error");
			errores = true;
		}
		
		//si no hay errores
		if(!errores){
			listarReporte2();
		}
	}else if(selector.reporte.val() == 3){
		//validar mes
		if(selector.anio.children('input').val().trim()){
			selector.wrap_anio.removeClass("has-error");
		}else{
			var advertencia = obtenerAdvertencia(' alert-danger', 'Debe ingresar año');
			label.html(advertencia);
			selector.wrap_anio.addClass("has-error");
			errores = true;
		}
		
		//si no hay errores
		if(!errores){
			listarReporte3();
		}
	}
}


var txt_btn_generar1;
var table1;
function listarReporte1(){
		table1 = $('#example1').DataTable({
		 "ajax": 
		 {	
			 "url": "/clinicas-crm/reportes",
			 "data": 
				 function ( d ) {
				 	txt_btn_generar1 = selector.boton.html();
					selector.boton.html(texto_espere);
					selector.boton.prop("disabled", true);	
					console.log("llega");
				 	d.operacion		= 1;
					d.fecha_inicio 	= selector.fecha_inicio.children('input').val();
					d.fecha_fin		= selector.fecha_fin.children('input').val();
					
				 },
			"type": "POST"
		 },
		 "preDrawCallback": function( settings ,json) {
	
		  },
		  "ordering":true,
		  "bFilter": false,
		  "paging":false,
			"bDestroy": true,
			"pagingType": "bootstrap",
			"lengthMenu": [[5, 10, 25, 50, 100, -1], [5, 10, 25, 50, 100, "Todos"]],
			"iDisplayLength": -1,
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
					"data": "estado",
					"orderable":false	
				}, {
					"data": "total"
				}, {
					"data": "porcentaje",
					"orderable":false	
				}
			],
			"initComplete": function (settings, json) {
				console.log(json);
				if (json.resultado == "-100") {
					$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
				}else{
					data_reporte1 = json.data_bar;
					habilitarExportar();
					generarGraficoPie(json.data_pie);
					generarGraficoBarras(json.data_bar);
				}
				
				$('#example1_info').css('display', 'none');
				$('#example1 tbody tr').each(function () {
					//$(this).find('td:eq(0)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 0
					//$(this).find('td:eq(1)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 1
				});
				console.log(json);
				//HABILITA DE NUEVO EL BOTON DE BUSCAR
				selector.boton.html(txt_btn_generar1);
				selector.boton.prop("disabled", false);
				$("#example1").css("display","block");
			},
			"fnDrawCallback": function( oSettings ) {
				//CERRAR TODAS LOS DETALLES DE LAS FILAS PARA CUANDO 
				//SE NAVEGUE ENTRE PAGINAS
				/*$("[role='row']").each(function(){
					if(typeof table !== "undefined") {
					    table.row($(this)).child.hide();
					}
				}
				);*/
		    }
		 
	 });
		
	//cuando se de click a controles de paginacion se oculten los botones
	/*$('.pagination li a').click(function(){
		console.log('pppp');
		$("[role='row']").each(function(){
			table.row($(this)).child.hide();
			}
		);
	});
		
	// Restablece el evento click
	$('#example1 tbody').unbind( "click" );
		
	//Indispensable para detalle de cada tupla
	$('#example1 tbody').on('click', 'td.clasetest', function () {
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
	});*/
}





var table2;
function listarReporte2(){
		table2 = $('#example2').DataTable({
		 "ajax": 
		 {	
			 "url": "/clinicas-crm/reportes",
			 "data": 
				 function ( d ) {
				 	txt_btn_generar1 = selector.boton.html();
					selector.boton.html(texto_espere);
					selector.boton.prop("disabled", true);	
				 	d.operacion		= 2;
					d.mes 			= selector.mes.children('input').val();
					
				 },
			"type": "POST"
		 },
		 "preDrawCallback": function( settings ,json) {
	
		  },
		  "ordering":true,
		  "bFilter": false,
		  "paging":false,
			"bDestroy": true,
			"pagingType": "bootstrap",
			"lengthMenu": [[5, 10, 25, 50, 100, -1], [5, 10, 25, 50, 100, "Todos"]],
			"iDisplayLength": -1,
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
					"data": "id_caso"	
				}, {
					"data": "nombre"
				}, {
					"data": "fecha_cita"	
				}, {
					"data": "estado"	
				}
			],
			"initComplete": function (settings, json) {
				console.log(json);
				if (json.resultado == "-100") {
					$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
				}else{
					data_reporte2 = json.data;
					habilitarExportar();
					//generarGraficoPie(json.data_pie);
					//generarGraficoBarras(json.data_bar);
				}
				
				$('#example2_info').css('display', 'none');
				$('#example2 tbody tr').each(function () {
					$(this).find('td:eq(0)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 0
					//$(this).find('td:eq(1)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 1
				});
				console.log(json);
				//HABILITA DE NUEVO EL BOTON DE BUSCAR
				selector.boton.html(txt_btn_generar1);
				selector.boton.prop("disabled", false);
				$("#example2").css("display","block");
			},
			"fnDrawCallback": function( oSettings ) {
				//CERRAR TODAS LOS DETALLES DE LAS FILAS PARA CUANDO 
				//SE NAVEGUE ENTRE PAGINAS
				/*$("[role='row']").each(function(){
					if(typeof table !== "undefined") {
					    table.row($(this)).child.hide();
					}
				}
				);*/
		    }
		 
	 });
		
	//cuando se de click a controles de paginacion se oculten los botones
	/*$('.pagination li a').click(function(){
		console.log('pppp');
		$("[role='row']").each(function(){
			table.row($(this)).child.hide();
			}
		);
	});
		
	// Restablece el evento click
	$('#example1 tbody').unbind( "click" );
		
	//Indispensable para detalle de cada tupla
	$('#example1 tbody').on('click', 'td.clasetest', function () {
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
	});*/
}


var table2;
function listarReporte3(){
		table2 = $('#example3').DataTable({
		 "ajax": 
		 {	
			 "url": "/clinicas-crm/reportes",
			 "data": 
				 function ( d ) {
				 	txt_btn_generar1 = selector.boton.html();
					selector.boton.html(texto_espere);
					selector.boton.prop("disabled", true);	
				 	d.operacion		= 3;
					d.anio 			= selector.anio.children('input').val();
					
				 },
			"type": "POST"
		 },
		 "preDrawCallback": function( settings ,json) {
	
		  },
		  "ordering":true,
		  "bFilter": false,
		  "paging":false,
			"bDestroy": true,
			"pagingType": "bootstrap",
			"lengthMenu": [[5, 10, 25, 50, 100, -1], [5, 10, 25, 50, 100, "Todos"]],
			"iDisplayLength": -1,
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
					"data": "mes",
					"orderable":false
				}, {
					"data": "registrados"	
				}, {
					"data": "ausentes"	
				}, {
					"data": "aprobados"	
				}, {
					"data": "rechazados"	
				}, {
					"data": "referidos"	
				}, {
					"data": "apartados"	
				}, {
					"data": "asignados"	
				}, {
					"data": "otros"	
				}, {
					"data": "citados"	
				}
			],
			"initComplete": function (settings, json) {
				if (json.resultado == "-100") {
					$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
				}else{
					data_reporte3 = json.data;
					habilitarExportar();
					generarGraficoLineas(json.data);
					//generarGraficoPie(json.data_pie);
					//generarGraficoBarras(json.data_bar);
				}
				
				$('#example3_info').css('display', 'none');
				$('#example3 tbody tr').each(function () {
					//$(this).find('td:eq(0)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 0
					//$(this).find('td:eq(1)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 1
				});
				console.log(json);
				//HABILITA DE NUEVO EL BOTON DE BUSCAR
				selector.boton.html(txt_btn_generar1);
				selector.boton.prop("disabled", false);
				$("#example3").css("display","block");
			},
			"fnDrawCallback": function( oSettings ) {
				//CERRAR TODAS LOS DETALLES DE LAS FILAS PARA CUANDO 
				//SE NAVEGUE ENTRE PAGINAS
				/*$("[role='row']").each(function(){
					if(typeof table !== "undefined") {
					    table.row($(this)).child.hide();
					}
				}
				);*/
		    }
		 
	 });
		
	//cuando se de click a controles de paginacion se oculten los botones
	/*$('.pagination li a').click(function(){
		console.log('pppp');
		$("[role='row']").each(function(){
			table.row($(this)).child.hide();
			}
		);
	});
		
	// Restablece el evento click
	$('#example1 tbody').unbind( "click" );
		
	//Indispensable para detalle de cada tupla
	$('#example1 tbody').on('click', 'td.clasetest', function () {
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
	});*/
}


function generarGraficoPie(datos){
	if(datos.length != 0){
		var data = datos;/*[
			{ label: "Series 0", data: 1 },
			{ label: "Series 1", data: 3 },
			{ label: "Series 2", data: 9 },
			{ label: "Series 3", data: 20 }
		];*/
		
		var plotObj = $.plot($("#flot-chart-pie"), data, {
			series: {
				pie: {
					show: true,
					label: {
		                show: true,
		                radius: 1,
		                formatter: labelFormatter,
		                background: {
		                    opacity: 0.8
		                }
		            }
				}
			},
			grid: {
				hoverable: true 
			},
			tooltip: true,
			tooltipOpts: {
				content: '%p.0%, %s', // show percentages, rounding to 2 decimal places
				shifts: {
					x: 20,
					y: 0
				},
				defaultTheme: false
			}
		});
	}
	
}

//generarGraficoBarras();
function generarGraficoBarras(datos){
	if(datos.length != 0){
		var largo = datos.length-1;
		var barData = [];
		var ticks = [];
		console.log(datos);
		for(var i = 0; i <= largo; i++){
			barData.push([i, datos[i].total]);
			ticks.push([i, datos[i].label])
		}
		console.log(largo);
		/*var barData = [
			[1, 40],
			[2, 100],
			[3, 24],
			[4, 25],
			[5, 90],
			[6, 4]
		  ];*/
		  
		  var dataset = [
			 { label: "", data: barData }
		  ];
		  //var ticks = [[1, "London"], [2, "New York"], [3, "New Delhi"], [4, "Taipei"],[5, "Beijing"], [6, "Sydney"]];
		   		  
		var barOptions = {
				series: {
					color:"#A2D9CE",
					bars: { show: true, align:"center"}
				},
				xaxis: {
					ticks: ticks
				},
				yaxis: {
				},
				grid:  { hoverable: true },
				legend: { show: false },
				tooltip: true,
				tooltipOpts: {
				  content: 'Total: %y'
				}
			  };
		
			  
			  $.plot($("#flot-chart-bar"), dataset, barOptions);
	}
}


function generarGraficoLineas(datos){
	var citados 	= []; 
	var registrados = [];
	var ausentes 	= [];
	var aprobados 	= [];
	var rechazados 	= [];
	var referidos 	= [];
	var apartados 	= [];
	var asignados 	= [];
	var otros		= [];
	for (var i = 0; i < datos.length - 1; i++) {
		citados.push([gd(datos[i].anio,datos[i].mes_numero-1,1), datos[i].citados]);
		registrados.push([gd(datos[i].anio,datos[i].mes_numero-1,1), datos[i].registrados]);
		ausentes.push([gd(datos[i].anio,datos[i].mes_numero-1,1), datos[i].ausentes]);
		aprobados.push([gd(datos[i].anio,datos[i].mes_numero-1,1), datos[i].aprobados]);
		rechazados.push([gd(datos[i].anio,datos[i].mes_numero-1,1), datos[i].rechazados]);
		referidos.push([gd(datos[i].anio,datos[i].mes_numero-1,1), datos[i].referidos]);
		apartados.push([gd(datos[i].anio,datos[i].mes_numero-1,1), datos[i].apartados]);
		asignados.push([gd(datos[i].anio,datos[i].mes_numero-1,1), datos[i].asignados]);
		otros.push([gd(datos[i].anio,datos[i].mes_numero-1,1), datos[i].otros]);
	}

	var options = {
		series: {
			lines: { show: true },
			points: { show: true }
		},
		grid: {
			hoverable: true //IMPORTANT! this is needed for tooltip to work
		},
		yaxis: { min: 0 },
		xaxis: { 
			mode: "time",
	        tickSize: [1, "month"],        
	        tickLength: 0,
	        axisLabel: "Meses"
	    },
		tooltip: true,
		tooltipOpts: {
			content: '%s en %x.1: %y.4',
			shifts: {
				x: -60,
				y: 25
			}
		},
		colors: ["#C09C0C", "#2980B9", "#F39C12", "#16A085","#E74C3C", "#BB36BB","#F1C40F", "#88BFB4", "#757272"]
	};
	var plotObj = $.plot( $("#flot-chart-line"),
		[ { data: citados, label: "Citados"}, 
		  { data: registrados, label: "Registrados" },
		  { data: ausentes, label: "Ausentes" },
		  { data: aprobados, label: "Aprobados" },
		  { data: rechazados, label: "Rechazados" },
		  { data: referidos, label: "Referidos" },
		  { data: apartados, label: "Apartados" },
		  { data: asignados, label: "Asignados" },
		  { data: otros, label: "otros" }
		  
		],
		options );
}


function gd(year, month, day) {
    return new Date(year, month, day).getTime();
}


function labelFormatter(label, series) {
    return "<div style='font-size:8pt; text-align:center; padding:2px; color:white;'>" + Math.round(series.percent) + "%</div>";
}


function generarCSV(){
	if(selector.reporte.val() == 1) {
		var datos = data_reporte1;
		if (typeof datos !== 'undefined') {
			var data = [];
			var largo = datos.length-1;
			data.push(["Estado", "Numero de casos"]);
			for(var i = 0; i <= largo; i++){
				data.push([datos[i].label, datos[i].total]);
			}
			var csvContent = "data:text/csv;charset=utf-8,";
			data.forEach(function(infoArray, index){

			   dataString = infoArray.join(";");
			   csvContent += index < data.length ? dataString+ "\n" : dataString;

			}); 
			
			var encodedUri = encodeURI(csvContent);
			var link = document.createElement("a");
			link.setAttribute("href", encodedUri);
			link.setAttribute("download", "Sistema de Gestión-FOUSAC_Reporte1.csv");

			link.click();
		}
	}else if(selector.reporte.val() == 2) {
		var datos = data_reporte2;
		if (typeof datos !== 'undefined') {
			var data = [];
			var largo = datos.length-1;
			data.push(["Id", "Nombre", "Fecha cita", "Estado"]);
			for(var i = 0; i <= largo; i++){
				data.push([datos[i].id_caso, datos[i].nombre, datos[i].fecha_cita, datos[i].label_estado]);
			}
			var csvContent = "data:text/csv;charset=utf-8,";
			data.forEach(function(infoArray, index){

			   dataString = infoArray.join(";");
			   csvContent += index < data.length ? dataString+ "\n" : dataString;

			}); 
			
			var encodedUri = encodeURI(csvContent);
			var link = document.createElement("a");
			link.setAttribute("href", encodedUri);
			link.setAttribute("download", "Sistema de Gestión-FOUSAC_Reporte2.csv");

			link.click();
		}
	}else if(selector.reporte.val() == 3) {
		var datos = data_reporte3;
		if (typeof datos !== 'undefined') {
			var data = [];
			var largo = datos.length-1;
			data.push(["Mes", "Citados", "Registrados", "Ausentes", "Aprobados", "Rechazados", "Referidos", "Apartados", "Asignados", "Otros", "Total"]);
			for(var i = 0; i <= largo; i++){
				data.push([datos[i].mes, datos[i].citados, datos[i].registrados, datos[i].ausentes, 
				           datos[i].aprobados, datos[i].rechazados, datos[i].referiso, datos[i].apartados
				           , datos[i].asignados, datos[i].otros, datos[i].total]);
			}
			var csvContent = "data:text/csv;charset=utf-8,";
			data.forEach(function(infoArray, index){

			   dataString = infoArray.join(";");
			   csvContent += index < data.length ? dataString+ "\n" : dataString;

			}); 
			
			var encodedUri = encodeURI(csvContent);
			var link = document.createElement("a");
			link.setAttribute("href", encodedUri);
			link.setAttribute("download", "Sistema de Gestión-FOUSAC_Reporte3.csv");

			link.click();
		}
	}
		
		
		
	
	
}


function generarPDF(){
	if(selector.reporte.val() == 1) {
		cargarVistaImpresionReporte1(data_reporte1, selector.fecha_inicio.children('input').val(), selector.fecha_fin.children('input').val());
	}else if(selector.reporte.val() == 2) {
		cargarVistaImpresionReporte2(data_reporte2, selector.mes.children('input').val());
	}else if(selector.reporte.val() == 3) {
		cargarVistaImpresionReporte3(data_reporte3, selector.anio.children('input').val());
	}
}


function mostrarVistaDatos(data){
	var parametros = {}
	parametros.operacion = 2;
	parametros.id_paciente = data.id_paciente;
	parametros.id_caso = data.identificador;
	$("#btn-ver").html('<b> Espere <i class="fa fa-spinner fa-spin"></i></b>');
	$("#btn-ver").prop("disabled", true);
	console.log(parametros);
	$.post('/clinicas-crm/busqueda',parametros,callbackMostrarVistaDatos,'json');
	
}

function callbackMostrarVistaDatos(response){
	var tiempo = 10;
	$("#btn-ver").html('<i class="fa fa-pencil-square-o"></i> Ver datos ');
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


function desplegarMensaje(clase, mensaje, time) {
	Messenger.options = {
	    extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
	}
	Messenger().post({message:'<center>'+ mensaje+'</center>',type: clase,showCloseButton: true, hideAfter:time});
}

function permite() {
	return false;
}


function obtenerAdvertencia(clase, mensaje) {
	return '<div class="alert ' + clase + '">' + mensaje + '</div>';
}
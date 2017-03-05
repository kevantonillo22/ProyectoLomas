<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="crm_BLL.General_BLL"%>
<%@page import="crm_BE.Sesion_BE"%>
<%@page import="crm_BE.Funciones"%>
<%
	//Valida la sesion
	Sesion_BE sesion = new Sesion_BE();
	if(session.getAttribute("sesion") == null){
		session.invalidate();
		response.sendRedirect("../sesionExpirada.jsp");
	}
	else
	{
		sesion = (Sesion_BE) session.getAttribute("sesion");
		if(!General_BLL.tienePermiso(sesion, Funciones.LISTAR_EVENTOS_DE_BITACORA)){
			response.sendRedirect("../errorpermisos.jsp");
		}
	}
	
%>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>Sistema de Gestión- CUB</title>
		<link href="../css/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="../icons/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="../css/plugins/bootstrap-datepicker/datepicker3.css" rel="stylesheet">
		<link href="../css/style.css" rel="stylesheet">
		<link href="../css/plugins.css" rel="stylesheet">
		<!-- ESTILOS PERSONALIZADOS-->
		<style type="text/css">
			.clasetest{width: 54px; }
		</style>
	</head>
	<body>
		<div id="wrapper">
			<nav class="navbar-top" role="navigation">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle pull-right" data-toggle="collapse" data-target=".sidebar-collapse">
					<i class="fa fa-bars"></i> Menú
					</button>
					<div class="navbar-brand">
						<a href="../index.jsp">
						<img src="../img/logo_home.png" class="img-responsive" alt="">
						</a>
					</div>
				</div>
				<div class="nav-top">
					<!-- BARRA LATERAL -->
					<ul class="nav navbar-left">
						<li class="tooltip-sidebar-toggle">
							<a href="#" id="sidebar-toggle" data-toggle="tooltip" data-placement="right" title="" data-original-title="Barra lateral">
							<i class="fa fa-bars"></i>
							</a>
						</li>
					</ul>
					<ul class="nav navbar-right">
						<!-- MENÃ DESPLEGABLE DERECHO -->
						<li class="dropdown">
							<a href="#" class="dropdown-toggle"
								data-toggle="dropdown"> <i class="fa fa-user"></i> <i
								class="fa fa-caret-down"></i>
							</a>
							<ul class="dropdown-menu dropdown-user">
								<li>
									<a class="logout_open" href="#logout" data-popup-ordinal="0" id="open_22839500">
									<i class="fa fa-sign-out"></i> Cerrar sesión
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</nav>
			<!-- MENÃ IZQUIERDO PRINCIPAL -->
			<nav class="navbar-side" role="navigation">
				<div class="navbar-collapse sidebar-collapse collapse">
					<ul id="side" class="nav navbar-nav side-nav">
						<li class="side-user hidden-xs">
							<img class="img-circle" alt="" style="background: whitesmoke; background-image: url('<%=sesion.se_ruta_foto%>'); width: 150px; height: 150px; background-size: cover; background-repeat: no-repeat; background-position: center center;">
							<p class="welcome">
								<i class="fa fa-key"></i> Inició sesión como
							</p>
							<p class="name tooltip-sidebar-logout"><%=sesion.se_nombres%>
							<span class="last-name"><%=sesion.se_apellidos%></span> <a
									style="color: inherit" class="logout_open" href="#logout"
									data-toggle="tooltip" data-placement="top" title=""
									data-popup-ordinal="1" id="open_61613468"
									data-original-title="Salir"><i class="fa fa-sign-out"></i></a>
							</p>
							<div class="clearfix"></div>
						</li>
						<%= sesion.se_menu %>
					</ul>
				</div>
			</nav>
			<!-- CONTENIDO DE LA PÃGINA -->
			<div id="page-wrapper" class="">
				<div class="page-content page-content-ease-in">
					<!-- TITULO DE LA PÃGINA -->
					<div class="row">
						<div class="col-lg-12">
							<div class="page-title">
								<h1>Bitácora<small></small>
								</h1>
								<ol class="breadcrumb">
									<li><i class="fa fa-dashboard"></i>  <a href="../index.jsp">Inicio</a>
									</li>
									<li class="active">Listar Bitácora</li>
								</ol>
							</div>
						</div>
					</div>
					<div class="row">
						<!-- INICIA FORMULARIO DE LISTAR -->
						<div class="col-lg-12">
							<div id="estado"> </div>
							<div class="portlet portlet-gray">
								<div class="portlet-heading">
									<div class="portlet-title">
										<h4>Eventos de la bitácora</h4>
									</div>
									<div class="clearfix"></div>
								</div>
								<div class="portlet-body">
									<div class="table-responsive">
										<!-- DECLARACION DE TABLA -->
										<div class="row">
									<div class="col-sm-12">
												<form role="form">
  
  													<div id="" class="col-md-2">
                                                    <input  style="text-align:center;"  placeholder="Desde" id="fechaInicio"class="form-control" type="text">
                                                </div><div id="" class="col-md-2">
                                                    <input style="text-align:center;" placeholder="Hasta" id="fechaFin" class="form-control" type="text">
                                                </div>
			                                    	<div class="input-group col-md-4">
					                                    <input type="text" class="form-control" id="txtFiltro" placeholder="Busque por usuario, tipo o función" maxlength="50">
					                                    <span class="input-group-btn">
					                                        <button class="btn btn-default" type="submit" id="btn-buscar">Buscar&nbsp;&nbsp;<i class="fa fa-search"></i></button>
					                                    </span> 
				                                	</div>
				                               	</form>
											</div>
										</div>
										<table id="example"
											class="table table-striped table-bordered table-hover table-green  dataTable"
											cellspacing="0" width="100%" style="display: none;">
											<!-- DECLARACIÃN DE COLUMNAS DE TABLA -->
											<thead>
												<tr>
													<th style="width: 54px;"></th>
													<th style="text-align: center;">Usuario</th>
													<th style="text-align: center;">Tipo de Operación</th>
													<th style="text-align: center;">Fecha y hora</th>
													<th style="text-align: center;">Función</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- VENTANA DE CONFIRMACIÃN DE LOGOUT -->
	    <div id="logout">
	        <div class="logout-message">
	            <img class="img-circle" alt="" style="background: whitesmoke; background-image: url('<%=sesion.se_ruta_foto%>'); width: 150px; height: 150px; background-size: cover; background-repeat: no-repeat; background-position: center center;">
	            <h3>
	                <i class="fa fa-sign-out text-green"></i> ¿Seguro que desea salir?
	            </h3>
	            <p>De click en "Salir" para cerrar sesión.</p>
	            <ul class="list-inline">
	                <li>
	                    <a href="../salir"  class="btn btn-green">
	                        <strong>Salir</strong>
	                    </a>
	                </li>
	                <li>
	                    <button class="logout_close btn btn-green">Cancelar</button>
	                </li>
	            </ul>
	        </div>
	    </div>
		<!-- SCRIPTS GLOBALES -->
		<script src="../js/jquery-1.11.0.min.js"></script>
		<script src="../js/plugins/bootstrap/bootstrap.min.js"></script>
		<script src="../js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script src="../js/plugins/popupoverlay/jquery.popupoverlay.js"></script>
		<script src="../js/plugins/popupoverlay/defaults.js"></script>
		<script src="../js/plugins/popupoverlay/logout.js"></script>
		<script src="../js/plugins/datatables/jquery.dataTables.js"></script>
		<script src="../js/plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script>
		<script src="../js/estiloTabla.js"></script>
		<script src="../js/flex.js"></script>
		<!-- Declaración de las funciones de JavaScript-->
		<script type="text/javascript">
			//Inicializar la fecha
		
		
			// Variables globales
			var table;
			
			//Cargar la tabla de datos
			function cargarTabla(){
				table = $('#example').DataTable({
					"bDestroy": true,
					"ajax": {	"url": "../bitacora",
				            	"data": function ( d ) {
			                	d.filtro = $('#txtFiltro').val();
			                	d.inicio=$('#fechaInicio').val();
			                	d.fin=$('#fechaFin').val();
				            	},
								"type": "POST"},
					"paging": true,
					//"dom": 'ti',
					"processing": true,
					
					"serverSide": true,
					"pagingType": "bootstrap",
					"lengthChange": true,
					"bFilter": false,
					"columns": [{
							"class": 'clasetest',
							"orderable": false,
							"data": '',
							"defaultContent": '<button title="Detalle" style="border-radius:50%;" class="btn btn-white"><b>+</b></button>',
							
						},
						/*MAPEO DE LAS COLUMNAS*/
						{
							"data": "usuario"
						}, {
							"data": "tipo"
						}, {
							"data": "fecha_hora"
						}
						, {
							"data": "funcion"
						}
					],
					
					"order": [
						[1, 'asc']
					],
					"language": {
						"processing": "<div style='clear:left;'><center ><b>Procesando <i class='fa fa-spinner fa-spin'></i></b></center></div><br>",
						"lengthMenu": "Mostrar _MENU_ filas por página",
						"zeroRecords": "<center>No hay filas para mostrar</center>",
						"info": "Mostrando la página _PAGE_ de _PAGES_",
						"infoEmpty": "",
						"infoFiltered": "",
						"paginate": {
							"next": "Siguiente",
							"previous": "Anterior"
						}
					},
					"initComplete": function (settings, json) {
						
						if (json.resultado == "-100") {
							// Sesión caducó
							$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});

						} 
						
						$('#example tbody tr').each(function () {
							$(this).find('td:eq(1)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 1
						});
						
						$("#btn-buscar").html('Buscar&nbsp;&nbsp;<i class="fa fa-search">');
						$("#btn-buscar").prop("disabled", false);
						$("#example").show();
				
					}
				});
				
				// Restablece el evento click
				$('#example tbody').unbind( "click" );
				
				//Indispensable para las opciones de cada tupla
				$('#example tbody').on('click', 'td.clasetest', function () {
					var tr = $(this).parents('tr');
					var row = table.row(tr);

					
					$("[role='row']").not(tr).each(function(){
							table.row($(this)).child.hide();
						}
					);
					if (row.child.isShown()) {
						row.child.hide();
						tr.removeClass('shown');
					} else {
						row.child(format(row.data())).show();
						tr.addClass('shown');
					}
				});
			}
			
			// EVENTO DEL BOTON BUSCAR
			$('#btn-buscar').click(
				function (e) {
					//Deshabilitar el boton y mostrar animacion de cargado
					$("#btn-buscar").html(' Espere <i class="fa fa-spinner fa-spin"></i>');
					$("#btn-buscar").prop("disabled", true);
					cargarTabla();
					e.preventDefault();
			});

			// EVENTO DE LOS BOTONES DE OPCIONES
			function format(d) {
				/*Este mÃ©todo sirve para agregar links con info en la tabla sobre cada tupla*/
				return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
				
					'<tr>' +
					'<td ><h4>Descripción:</h4>' + d.descripcion +	'</td>' +
		
					'</tr>' +
					'</table>';
			}
			
		

			function obtenerAdvertencia(clase, mensaje) {
				return '<div style="text-align:center;" class="alert ' + clase + '">' + mensaje + '</div>';
			}
			$(document).ready(function(){
				
				$('#fechaInicio').datepicker({
                    format: "yyyy-mm-dd",
                    language:'es',
                    	autoclose: true,
                        todayHighlight: true
                });  
				
				$('#fechaFin').datepicker({
                    format: "yyyy-mm-dd",
                    language:'es',
                    	autoclose: true,
                        todayHighlight: true
                });  
				
			});
			
			function obtenerPortlet(titulo, contenido){
			return '<div class="col-lg-4">' + '<div class="portlet">'
						+ '<div class="portlet-heading">'
						+ '<div class="portlet-title">' + '<h4>'+titulo+'</h4>'
						+ '</div>' + '<div class="clearfix"></div>' + '</div>'
						+ '<div class="portlet-body">' + '<p>'+contenido+'<p>'
						+ '</div>' + '</div>' + '</div>';

			}
		</script>
			<!-- Sesión caducada -->
			<div class="modal fade" id="sesionCaducada" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="myModalLabel">Sesión expirada</h4>
						</div>
						<div class="modal-body">La sesión ha expirado. Debe iniciar sesión de nuevo para continuar trabajando</div>
						<div class="modal-footer">
							<a href="../ingreso.jsp">
								<button type="button" class="btn btn-primary">Iniciar sesión</button>
							</a>
						</div>
					</div>
				</div>
			</div>
			<!-- Sesión caducada -->
	</body>
</html>
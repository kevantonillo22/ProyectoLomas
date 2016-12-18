<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="crm_BLL.General_BLL"%>
<%@page import="crm_BE.Sesion_BE"%>
<%@page import="crm_BE.Funciones"%>
<%
	//Valida la sesion
	Sesion_BE sesion = new Sesion_BE();
	if(session.getAttribute("sesion") == null){
		session.invalidate();
		response.sendRedirect("/lomas-crm/sesionExpirada.jsp");
	}
	else
	{
		sesion = (Sesion_BE) session.getAttribute("sesion");
		if(!General_BLL.tienePermiso(sesion, Funciones.LISTAR_PARAMETROS)){
			response.sendRedirect("/lomas-crm/errorpermisos.jsp");
		}
	}
	
	// Consulta los permisos adicionales del usuario
	boolean permiso_modificar = General_BLL.tienePermiso(sesion, Funciones.MODIFICAR_PARAMETROS);
%>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>Sistema de Gestión- CUB</title>
		<link href="/lomas-crm/css/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="/lomas-crm/icons/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="/lomas-crm/css/style.css" rel="stylesheet">
		<link href="/lomas-crm/css/plugins.css" rel="stylesheet">
		<link href="/lomas-crm/css/plugins/messenger/messenger.css" rel="stylesheet">
		<link href="/lomas-crm/css/plugins/messenger/messenger-theme-future.css" rel="stylesheet">
		
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
						<a href="/lomas-crm/index.jsp">
						<img src="/lomas-crm/img/logo_home.png" class="img-responsive" alt="">
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
								<h1>Parámetros<small></small>
								</h1>
								<ol class="breadcrumb">
									<li><i class="fa fa-dashboard"></i>  <a href="/lomas-crm/index.jsp">Inicio</a>
									</li>
									<li class="active">Listar parámetros</li>
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
										<h4>&nbsp;</h4>
									</div>
									<div class="clearfix"></div>
								</div>
								<div class="portlet-body">
									<div class="table-responsive">
										<!-- DECLARACION DE TABLA -->
										<table id="example"
											class="table table-striped table-bordered table-hover table-green  dataTable"
											cellspacing="0" width="100%">
											<!-- DECLARACIÃN DE COLUMNAS DE TABLA -->
											<thead>
												<tr>
													<th style="width: 54px;"></th>
													<th style="text-align: center;">Código</th>
													<th style="text-align: center;">Nombre</th>
													<th style="text-align: center;">Descripcion</th>
													<th style="text-align: center;">Valor</th>
													<th style="text-align: center;">Inicio vigencia</th>
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
	                    <a href="/lomas-crm/salir"  class="btn btn-green">
	                        <strong>Salir</strong>
	                    </a>
	                </li>
	                <li>
	                    <button class="logout_close btn btn-green">Cancelar</button>
	                </li>
	            </ul>
	        </div>
	    </div>
	    
	    <div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">Modificar parámetro</h4>
            </div>
            <div class="modal-body">
                	<form role="form">
										<div class="form-group">
											<label for="lblCodigo">Codigo</label>
											<input type="text" class="form-control" id="txtCodigo" maxlength="10" readonly>
										</div>
										<div class="form-group">
											<label for="lblNombre">Nombre</label>
											<input type="text" class="form-control" id="txtNombre" maxlength="50" readonly>
										</div>
										<div class="form-group">
											<label for="lblValor">Valor</label>
											<input type="text" class="form-control" id="txtValor" maxlength="500">
										</div>
										<div  style="text-align: center;">
											</div>
										<br>
										<div  id="lbl-advertencia" style="text-align: center;">
										</div>
									</form>
            </div>
            <div class="modal-footer">
               <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
               <button type="submit" id="btn-guardar"class="btn btn-default">Guardar</button>
																
        </div>
    </div>
  </div>
</div>
	    
	    
	    
		<!-- SCRIPTS GLOBALES -->
		<script src="/lomas-crm/js/jquery-1.11.0.min.js"></script>
		<script src="/lomas-crm/js/plugins/bootstrap/bootstrap.min.js"></script>
		<script src="/lomas-crm/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script src="/lomas-crm/js/plugins/popupoverlay/jquery.popupoverlay.js"></script>
		<script src="/lomas-crm/js/plugins/popupoverlay/defaults.js"></script>
		<script src="/lomas-crm/js/plugins/popupoverlay/logout.js"></script>
		<script src="/lomas-crm/js/plugins/datatables/jquery.dataTables.js"></script>
		<script src="/lomas-crm/js/estiloTabla.js"></script>
		
		<script src="/lomas-crm/js/flex.js"></script>
		<script src="/lomas-crm/js/plugins/messenger/messenger.min.js"></script>
		<script src="/lomas-crm/js/plugins/messenger/messenger-theme-future.js"></script>
		
		<!-- DECLARACIÃN DE FUNCIONES JAVASCRIPT -->
		<script type="text/javascript">
			//ready
			$(function() {
			Messenger.options = {
				extraClasses : 'messenger-fixed messenger-on-top',
				theme : 'future'
			};
		});
		
		
			// Variables globales
			var table;
			var id;
			var fila;
			function cargarUIModificar(){
			$('#txtNombre').val('');
			$('#txtCodigo').val('');
			$('#txtValor').val('');
			
			

			//Deshabilitar el boton y mostrar animacion de cargado
			$("#btn-guardar").html(' Espere <i class="fa fa-spinner fa-spin"></i>');
			$("#btn-guardar").prop("disabled", true);

			// Consulta la información enviando el id a la consulta
			var datos = {
				"id": id,
				"op": "1"
			};
			$.post('/lomas-crm/parametro', datos, callback2, 'json');

			function callback2(respuesta) {
				if(respuesta){
				$("#txtCodigo").val(respuesta.data[0].codigo);
				$("#txtNombre").val(respuesta.data[0].nombre);
				$("#txtValor").val(respuesta.data[0].valor);
				$("#btn-guardar").html('Guardar');
				$("#btn-guardar").prop("disabled", false);}
				else{
					if (respuesta.resultado == "-100") {
						// Sesión caducó
						$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});

					} else if (respuesta.resultado == "-101") {
						// No tiene permisos
						window.location.href = "/lomas-crm/errorpermisos.jsp";
					}else{
						obtenerAdvertencia('error', respuesta.descripcion);
					}

					
				}
			}	
			
			}
			
			
			$(document).ready(cargarTabla());
			
			// CARGA TABLA DE DATOS
			function cargarTabla(){
				table = $('#example').DataTable({
					"ajax": {	"url": "/lomas-crm/parametro",
								"dataType": "json",
				            	"data": function ( d ) {d.op = "1";},
								"type": "POST"},
					"columns": [{"class": 'clasetest',
								"defaultContent": '<button title="Opciones" style="border-radius:50%;" class="btn btn-white"><b>+</b></button>',
								"visible": <% if(permiso_modificar) { out.print("true"); } else { out.print("false"); }; %>},
								{"data": "codigo"},
								{"data": "nombre"},
								{"data": "descripcion"},
								{"data": "valor"},
								{"data": "fecha_inicio"}],
					"bFilter": false,
					"bDestroy": true,
					"pagingType": "bootstrap",
					"lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "Todos"]],
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
					}
				});
				
				// Restablece el evento click
				$('#example tbody').unbind( "click" );

				//Indispensable para las opciones de cada tupla
				$('#example tbody').on('click', 'td.clasetest', function () {
					var tr = $(this).parents('tr');
					var row = table.row(tr);
					fila=row;
					id=row.data().codigo;
					
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
			
			// EVENTO DE LOS BOTONES DE OPCIONES
			function format(d) {
				// Cadena de opciones
				<% if(permiso_modificar) { 
						out.println("return '<table cellpadding=\"5\" cellspacing=\"0\" border=\"0\" style=\"padding-left:50px;\">\' + ");
						out.println("	\'<tr>\' + ");
						out.println("		\'<td>Opciones</td>\' + ");
						out.println("	\'</tr>\' + ");
						out.println("	\'<tr>\' + ");
						if(permiso_modificar) out.println("		'<td><a data-toggle=\"modal\" data-target=\"#basicModal\"  href=\"#\" onclick=\" cargarUIModificar()\"><button class=\"btn btn-default\">Modificar</button></a></td>\' + ");
						out.println("	\'</tr>\' + ");
						out.println("\'</table>\';"); 
					} else {
						out.print("return '';"); 
					}; 
				%>
			}
			
			
			// Validación de botón guardar
			$('#btn-guardar').click(function (e) {
				
				

				//Se crea un objeto para ser enviado al servidor 
				var datos = {
					id: id,
					valor: $('#txtValor').val().trim(),
					op: "2"
				};
				
				if(datos.valor.length>0){
					$("#btn-guardar").html(' Espere <i class="fa fa-spinner fa-spin"></i>');
					$("#btn-guardar").prop("disabled", true);
				$.post('/lomas-crm/parametro', datos, callback2, 'json');
				}else{
					obtenerAdvertencia("error", "No se puede guardar un parámetro vacío");
					
				}
				function callback2(respuesta) {
					//Se obtiene la respuesta del servidor y se muestra la pagina
					$("#btn-guardar").prop("disabled", false);
					$("#btn-guardar").html('Guardar');
					if (respuesta.resultado == "1") {
						obtenerAdvertencia('info', respuesta.descripcion);
						var d= fila.data();
						
						
					    d.valor=$('#txtValor').val();
					   
					    
			
					    table
					        .row(fila)
					        .data(d)
					        .draw(false);
					} else {	if (respuesta.resultado == "-100") {
						// Sesión caducó
						$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});

					} else if (respuesta.resultado == "-101") {
						// No tiene permisos
						window.location.href = "/lomas-crm/errorpermisos.jsp";
					}else{
						obtenerAdvertencia('error', respuesta.descripcion);
					}

					}
					
				}
				e.preventDefault();
			});
			
			
			function obtenerAdvertencia(clase, mensaje) {
				  Messenger().post({
	        message: mensaje,
	        type: clase,
	        showCloseButton: true
	    });

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
							<a href="/lomas-crm/ingreso.jsp">
								<button type="button" class="btn btn-primary">Iniciar sesión</button>
							</a>
						</div>
					</div>
				</div>
			</div>
		<!-- Sesión caducada -->
	</body>
</html>
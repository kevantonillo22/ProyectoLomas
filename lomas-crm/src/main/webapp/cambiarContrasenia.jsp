<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="crm_BLL.General_BLL"%>
<%@page import="crm_BE.Sesion_BE"%>
<%@page import="crm_BE.Funciones"%>
<%
	//Valida la sesion
	Sesion_BE sesion = new Sesion_BE();
	if(session.getAttribute("sesion") == null){
		session.invalidate();
		response.sendRedirect("sesionExpirada.jsp");
	}
	else
	{
		sesion = (Sesion_BE) session.getAttribute("sesion");
		if (sesion.se_rol == -2 || sesion.se_rol == -3) {
			response.sendRedirect("errorpermisos.jsp");
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
		<link href="css/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="icons/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="css/style.css" rel="stylesheet">
		<link href="css/plugins.css" rel="stylesheet">
		<link href="css/plugins/messenger/messenger.css" rel="stylesheet">
		<link href="css/plugins/messenger/messenger-theme-future.css" rel="stylesheet">
		<!-- ESTILOS PERSONALIZADOS-->
		<style type="text/css">
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
						<a href="index.jsp">
						<img src="img/logo_home.png" class="img-responsive" alt="">
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
						<!-- MENÚ DESPLEGABLE DERECHO -->
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
			<!-- MENÚ IZQUIERDO PRINCIPAL -->
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
			<!-- CONTENIDO DE LA PÁGINA -->
			<div id="page-wrapper" class="">
				<div class="page-content page-content-ease-in">
					<!-- TITULO DE LA PÁGINA -->
					<div class="row">
						<div class="col-lg-12">
							<div class="page-title">
								<h1>
									Cambiar contraseña
									<small></small>
								</h1>
								<ol class="breadcrumb">
									<li><i class="fa fa-dashboard"></i>  <a href="index.jsp">Inicio</a>
									</li>
									<li class="active">Cambiar contraseña</li>
								</ol>
							</div>
						</div>
					</div>
					<!-- CONTENEDOR DEL FORMULARIO -->
					<div class="col-lg-6" style="float: none; margin: 0 auto;">
						<div class="portlet portlet-gray">
							<div class="portlet-heading">
								<div class="portlet-title">
									<h4>Escriba los datos de contraseña</h4>
								</div>
								
								<div class="clearfix"></div>
							</div>
							<!-- POSICIÓN DEL FORMULARIO -->
							<div id="basicFormExample" class="panel-collapse in" style="height: auto;">
								<div class="portlet-body">
									<form role="form">
										<div class="form-group">
											<label for="lblPassword">Contraseña actual</label>
											<input type="password" class="form-control" id="txtPassword"  maxlength="50">
										</div>
										<div class="form-group">
											<label for="lblNewPassword">Nueva contraseña</label>
											<input type="password" class="form-control" id="txtNewPassword"  maxlength="50">
										</div>
										<div class="form-group">
											<label for="lblReNewPassword">Confirmar nueva contraseña</label>
											<input type="password" class="form-control" id="txtReNewPassword"  maxlength="50">
										</div>
										<div  style="text-align: center;">
											<button type="submit" id="btn-guardar" class="btn btn-default">Guardar</button>
										</div>
										<br>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- VENTANA DE CONFIRMACIÓN DE LOGOUT -->
	    <div id="logout">
	        <div class="logout-message">
	            <img class="img-circle" alt="" style="background: whitesmoke; background-image: url('<%=sesion.se_ruta_foto%>'); width: 150px; height: 150px; background-size: cover; background-repeat: no-repeat; background-position: center center;">
	            <h3>
	                <i class="fa fa-sign-out text-green"></i> ¿Seguro que desea salir?
	            </h3>
	            <p>De click en "Salir" para cerrar sesión.</p>
	            <ul class="list-inline">
	                <li>
	                    <a href="salir"  class="btn btn-green">
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
		<script src="js/jquery-1.11.0.min.js"></script>
		<script src="js/plugins/bootstrap/bootstrap.min.js"></script>
		<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script src="js/plugins/popupoverlay/jquery.popupoverlay.js"></script>
		<script src="js/plugins/popupoverlay/defaults.js"></script>
		<script src="js/plugins/popupoverlay/logout.js"></script>
		<script src="js/plugins/messenger/messenger.min.js"></script>
		<script src="js/plugins/messenger/messenger-theme-future.js"></script>
		<script src="js/flex.js"></script>
		<!-- DECLARACIÓN DE FUNCIONES JAVASCRIPT -->
		<script type="text/javascript">
			$(function() {
				Messenger.options = {
					extraClasses : 'messenger-fixed messenger-on-top',
					theme : 'future'
				};
			});
			$('#btn-guardar')
					.click(
							function(e) {
								advertenciaHTML = '';
								//Validación de campos
								var error = 0;
								if ($('#txtPassword').val().trim().length == 0 || $('#txtNewPassword').val().trim().length == 0 || $('#txtReNewPassword').val().trim().length == 0) {
									error = 1;
									advertenciaHTML += obtenerAdvertencia('error', 'Todos los campos son obligatorios');
								}
								if ($('#txtNewPassword').val().trim() != $('#txtReNewPassword').val().trim()) {
									error = 2;
									advertenciaHTML += obtenerAdvertencia('error', 'Las contraseñas no coinciden');
								}
								if (error == 0) {
									//Campos validados exitosamente
									$("#btn-guardar").html(' Espere <i class="fa fa-spinner fa-spin"></i>');
									$("#btn-guardar").prop("disabled", true);
									//Se crea un objeto para ser enviado al servidor 
									var datos = {
										anterior: $('#txtPassword').val().trim(),
										nueva: $('#txtNewPassword').val().trim(),
										op : 11
									};

									$.post('usuario', datos, callback, 'json');

									function callback(respuesta) {
										//Se obtiene la respuesta del servidor y se muestra la pagina
										$("#btn-guardar").prop("disabled", false);
										$("#btn-guardar").html('Guardar');
										if (respuesta.resultado == "1") {
											advertenciaHTML = obtenerAdvertencia('info', respuesta.descripcion);
										} else {
											if (respuesta.resultado == "-100") {
												// Sesión caducó
												divCadena = '<div class="col-lg-4" style="position: fixed;    top: 50%;    left: 50%;    margin-top: -102px;    margin-left: -240px;">'
														+ '<div class="portlet portlet-gray">'
														+ '<div class="portlet-heading">'
														+ '<div class="portlet-title">'
														+ '<h4>Debe iniciar sesión</h4>'
														+ '</div>'
														+ '<div class="clearfix"></div>'
														+ '</div>'
														+ '<div id="defaultPortlet" class="panel-collapse collapse in">'
														+ '<div class="portlet-body">'
														+ '<p>Para acceder a esta página debe iniciar sesión.</p>'
														+ '<a href="ingreso.jsp"><button class="btn btn-default">Iniciar sesión</button></a>'
														+ '</div>'
														+ '</div>  '
														+ '</div>' + '</div>';

												div = $(
														'<div>' + divCadena
																+ '</div>')
														.attr('id',
																'divBloqueo');
												div.appendTo('body');
												div.css('display', 'block');
												div
														.css('background',
																'rgba(255, 255, 255, 0.7)');
												div.css('width', '100%');
												div.css('height', '100%');
												div.css('z-index', '9999');
												div.css('top', '0');
												div.css('left', '0');
												div.css('position', 'fixed');
												div.css('text-align', 'center');

											} else if (respuesta.resultado == "-101") {
												// No tiene permisos
												window.location.href = "errorpermisos.jsp";
											}

											advertenciaHTML = obtenerAdvertencia(
													'error',
													respuesta.descripcion);
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
	</body>
</html>
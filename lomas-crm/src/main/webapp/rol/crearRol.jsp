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
		if(!General_BLL.tienePermiso(sesion,Funciones.CREACION_DE_ROLES)){
			response.sendRedirect("/lomas-crm/errorpermisos.jsp");
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
		<link href="/lomas-crm/css/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="/lomas-crm/icons/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="/lomas-crm/css/style.css" rel="stylesheet">
		<link href="/lomas-crm/css/plugins.css" rel="stylesheet">
		<link href="/lomas-crm/css/plugins/messenger/messenger.css" rel="stylesheet">
		<link href="/lomas-crm/css/plugins/messenger/messenger-theme-future.css" rel="stylesheet">
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
									Roles
									<small></small>
								</h1>
								<ol class="breadcrumb">
									<li><i class="fa fa-dashboard"></i>  <a href="/lomas-crm/index.jsp">Inicio</a>
									</li>
									<li class="active">Crear rol</li>
								</ol>
							</div>
						</div>
					</div>
					<!-- CONTENEDOR DEL FORMULARIO -->
					<div class="col-lg-6" style="float: none; margin: 0 auto;">
						<div class="portlet portlet-gray">
							<div class="portlet-heading">
								<div class="portlet-title">
									<h4>Escriba los datos del rol a crear</h4>
								</div>
								
								<div class="clearfix"></div>
							</div>
							<!-- POSICIÓN DEL FORMULARIO -->
							<div id="basicFormExample" class="panel-collapse in" style="height: auto;">
								<div class="portlet-body">
									<form role="form">
										<div class="form-group">
											<label for="txtNombre">Nombre</label>
											<input type="text" class="form-control" id="txtNombre" placeholder="Nombre del rol" maxlength="50">
										</div>
										<div class="form-group">
											<label for="txtDescripcion">Descripción</label>
											<textarea class="form-control" id="txtDescripcion" placeholder="Descripción" maxlength="200"></textarea>
										</div>
										<div  style="text-align: center;">
											<button type="submit" id="btn-crear"class="btn btn-default">Crear</button>
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
		<!-- SCRIPTS GLOBALES -->
		<script src="/lomas-crm/js/jquery-1.11.0.min.js"></script>
		<script src="/lomas-crm/js/plugins/bootstrap/bootstrap.min.js"></script>
		<script src="/lomas-crm/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script src="/lomas-crm/js/plugins/popupoverlay/jquery.popupoverlay.js"></script>
		<script src="/lomas-crm/js/plugins/popupoverlay/defaults.js"></script>
		<script src="/lomas-crm/js/plugins/popupoverlay/logout.js"></script>
		<script src="/lomas-crm/js/plugins/messenger/messenger.min.js"></script>
		<script src="/lomas-crm/js/plugins/messenger/messenger-theme-future.js"></script>
		<script src="/lomas-crm/js/flex.js"></script>
		<!-- DECLARACIÓN DE FUNCIONES JAVASCRIPT -->
		<script type="text/javascript">
		
		//Validar funciones
		

		
		
		
		
			$(function() {
				Messenger.options = {
					extraClasses : 'messenger-fixed messenger-on-top',
					theme : 'future'
				};
			});
			$('#btn-crear')
					.click(
							function(e) {
								advertenciaHTML = '';
								//Validación de campos
								var error = 0;
								if ($('#txtNombre').val().trim().length == 0) {
									error = 1;
									obtenerAdvertencia(
											'error',
											'El nombre es obligatorio');
								}
								
								if(!(/^[$A-Z_áéíóúÁÉÍÓÚñÑ][0-9A-Z_$áéíóúÁÉÍÓÚñÑ]*$/i.test($('#txtNombre').val()))){
									
									error = 1;
									obtenerAdvertencia(
											'error',
											'Sólo se permiten nombres que empiecen por letras. <br> No se permiten espacios en blanco.	<br> Ejemplos: <ul><li>Secretaria_General</li>  <li>Tesorero</li></ul>');
									
								}
								
								
								if ($('#txtDescripcion').val().trim().length == 0) {
									error = 2;
									obtenerAdvertencia(
											'error',
											'La descripción es obligatoria');
								}
								if (error == 0) {
									//Campos validados exitosamente
									$("#btn-crear")
											.html(
													' Espere <i class="fa fa-spinner fa-spin"></i>');
									$("#btn-crear").prop("disabled", true);
									//Se crea un objeto para ser enviado al servidor 
									var datos = {
										nombre : $('#txtNombre').val(),
										descripcion : $('#txtDescripcion')
												.val(),
										op : 1
									};

									$.post('/lomas-crm/rol', datos,
											callback, 'json');

									function callback(respuesta) {
										//Se obtiene la respuesta del servidor y se muestra la pagina
										$("#btn-crear").prop("disabled", false);
										$("#btn-crear").html('Crear');
										if (respuesta.resultado == "1") {
											advertenciaHTML = obtenerAdvertencia(
													'info',
													respuesta.descripcion);
										} else {

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
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="crm_BLL.General_BLL"%>
<%@page import="crm_BE.Sesion_BE"%>
<%@page import="crm_BE.Funciones"%>
<%
	//Valida la sesion
	Sesion_BE sesion = new Sesion_BE();
	String id = "";
	if(session.getAttribute("sesion") == null){
		session.invalidate();
		response.sendRedirect("/lomas-crm/sesionExpirada.jsp");
	}
	else
	{
		sesion = (Sesion_BE) session.getAttribute("sesion");
		if(!General_BLL.tienePermiso(sesion,Funciones.CREAR_DIRECCION)){
			response.sendRedirect("/lomas-crm/errorpermisos.jsp");
		}
		id = String.valueOf(sesion.se_usuario);
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
		<link href="/lomas-crm/css/plugins/bootstrap-datepicker/datepicker3.css" rel="stylesheet">
		<link href="/lomas-crm/css/style.css" rel="stylesheet">
		<link href="/lomas-crm/css/plugins.css" rel="stylesheet">
		<link href="/lomas-crm/css/plugins/messenger/messenger.css" rel="stylesheet">
		<link href="/lomas-crm/css/plugins/messenger/messenger-theme-future.css" rel="stylesheet">
		
		<!-- ESTILOS PERSONALIZADOS-->
		<style type="text/css">
			.clasetest{width: 54px; }
		</style>
	</head>
	<body ng-app="crearDireccion">
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
			<div id="page-wrapper" class="" ng-controller="crear">
				<div class="page-content page-content-ease-in">
					<!-- TITULO DE LA PÃGINA -->
					<div class="row">
						<div class="col-lg-12">
							<div class="page-title">
								<h1>Dirección de vecinos<small></small>
								</h1>
								<ol class="breadcrumb">
									<li><i class="fa fa-dashboard"></i>  <a href="/lomas-crm/index.jsp">Inicio</a>
									</li>
									<li class="active">Crear Dirección</li>
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
									<div class="portlet-widgets">
			                            <!--  button  class="btn btn-sm btn-blue">Regresar</button>
										<button  class="btn btn-sm btn-green"><i class="fa fa-file-o"></i>&nbsp; Vista de impresión</button>  
			                            <button  class="btn btn-sm btn-blue">Solicitar Revisión</button-->
			                            <div class="btn-group">
			                                <!-- <button type="button" class="btn btn-blue btn-xs dropdown-toggle" id="boton-opciones" data-toggle="dropdown">
			                                    <i class="fa fa-gear"></i> &nbsp;Opciones
			                                    <span class="caret"></span>
			                                </button>
			                                <ul class="dropdown-menu dropdown-left" style="left:auto;right:0;" role="menu">
			                                    <li class="cursor-pointer"><a id="btn-nuevo" ><i class="fa fa-user"></i> Nuevo Registro</a>
			                                    </li>
			                                    
			                                   
			                                </ul>-->
			                            </div>
		                          	</div>	
									<div class="clearfix"></div>
								</div>
								<div class="portlet-body">
									<div class="row" style="text">
										<div class="col-sm-2"></div>
				               			<div class="col-sm-8 col-xs-12">
				               			<form name="formulario" data-ng-submit="formulario.$valid && guardar()">
				               				<div class="row">
												<div class="form-group col-xs-3" data-ng-class="{ 'has-error' : formulario.numero.$invalid && !formulario.numero.$pristine }" >
													<label for="txtNombre">Número *</label>
													<input 
														type="text" 
														class="form-control"  
														id="numero" 
														placeholder="No." 
														maxlength="10" 
														name="numero"
														data-ng-model="numero" 
														data-ng-pattern="/[0-9]+/"
														data-ng-required="true">
												</div>
												<div class="form-group col-xs-3">
													<label for="txtDescripcion">Calle o Avenida *</label>
													<select class="form-control" 
													data-ng-options="option.name for option in values track by option.id"
													data-ng-model="avenidaCalle" 
													data-nq-required="true">
														<option value="" selected disabled>::Seleccione::</option>
													</select>
												</div>
												
												<div class="form-group col-xs-6" data-ng-class="{'has-error': formulario.numeroCasa.$invalid && !formulario.numeroCasa.$pristine}" >
													<label for="txtDescripcion" >Numero casa *</label>
				                                   	<input name="numeroCasa" 
				                                   	type="text" 
				                                   	class="form-control"
				                                   	id="numeroCasa" 
				                                   	placeholder="Número casa" 
				                                   	maxlength="10" 
				                                   	data-ng-model="numeroCasa" 
				                                   	data-ng-required="true">
					                            </div>
												
												<div class="form-group col-xs-6" data-ng-class="{'has-error': formulario.familia.$invalid && !formulario.familia.$pristine}">
													<label for="txtDescripcion">Familia *</label>
													<input 
														type="text" 
														class="form-control" 
														id="familia" 
														name="familia" 
														maxlength="50" 
														placeholder="Familia" 
														onkeypress="return permite(event, 'car')" 
														data-ng-model="familia" 
														data-ng-required="!isAbandonada"
														data-ng-disabled="isAbandonada">
												</div>
												<div class="form-group col-xs-6" data-ng-class="{'has-error': formulario.telefono.$invalid && !formulario.telefono.$pristine}">
													<label for="txtDescripcion">Teléfono</label>
													<div class="input-group">
														<span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>
														<input 
															class="form-control" 
															id="telefono"
															name="telefono" 
															data-ng-model="telefono"
															data-ng-disabled="isAbandonada">
													</div>
												</div>
												<div class="form-group col-md-6 col-lg-6 col-xs-12" data-ng-class="{'has-error': formulario.titular.$invalid && !formulario.titular.$pristine}">
													<label for="txtDescripcion">Nombre titular *</label>
													<input 
														type="text" 
														class="form-control" 
														id="titular" 
														name="titular"
														placeholder="Titular" 
														maxlength="100" 
														data-ng-model="titular" 
														data-ng-required="!isAbandonada"
														data-ng-disabled="isAbandonada">
												</div>
												
												<div class="form-group col-md-6 col-lg-6 col-xs-6" data-ng-class="{'has-error': formulario.email.$invalid && !formulario.email.$pristine}">
													<label for="txtDescripcion">Email</label>
													<input 
														type="email" 
														class="form-control" 
														id="email"
														name="email" 
														placeholder="ejemplo@ejemplo.com" 
														maxlength="50" 
														onkeypress="return permite(event, 'email')" 
														data-ng-model="email"
														data-ng-disabled="isAbandonada">
												</div>
												
												<div class="form-group col-md-6 col-lg-6 col-xs-6" >
													<label for="txtDescripcion">Estado de pago *</label>
													<select class="form-control" 
													data-ng-options="option.name for option in estadosPago track by option.id"
													data-ng-model="estadoPago" 
													data-nq-required="true">
														<option value="" selected disabled>::Seleccione::</option>
													</select>
												</div>
												
												<div class="form-group col-md-6 col-lg-6 col-xs-6" >
													<label for="txtDescripcion">Estado de domicilio *</label>
													<select class="form-control" 
													data-ng-options="option.name for option in estadosDomicilio track by option.id"
													data-ng-model="estadoDomicilio" 
													data-ng-change ="cambio()"
													data-nq-required="true">
														<option value="" selected disabled>::Seleccione::</option>
													</select>
												</div>
											</div>
											
								            <div class="modal-footer" style="margin-top:0;">
								               <button type="submit" data-ng-disabled="formulario.$invalid || disableGuardar" id="btn-guardar" class="btn btn-default" data-ng-bind-html="textoGuardar"><i class="fa fa-check"></i> Guardar</button>
								        	</div>
								        	</form>
										</div>
										<div class="col-sm-2"></div>
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
		<!-- SCRIPTS GLOBALES -->
		
		<script src="/lomas-crm/js/jquery-1.11.0.min.js"></script>
		<script src="/lomas-crm/js/angular/angular.min.js"></script>
		<script src="/lomas-crm/js/angular/Modules/angular-sanitize.js"></script>
		<script src="/lomas-crm/js/plugins/bootstrap/bootstrap.min.js"></script>
		<script src="/lomas-crm/js/plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script>
		<script src="/lomas-crm/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script src="/lomas-crm/js/plugins/popupoverlay/jquery.popupoverlay.js"></script>
		<script src="/lomas-crm/js/plugins/popupoverlay/defaults.js"></script>
		<script src="/lomas-crm/js/plugins/popupoverlay/logout.js"></script>
		<script src="/lomas-crm/js/plugins/datatables/jquery.dataTables.js"></script>
		<script src="/lomas-crm/js/estiloTabla.js"></script>
		<script src="/lomas-crm/js/flex.js"></script>
		<script src="/lomas-crm/js/plugins/messenger/messenger.min.js"></script>
		<script src="/lomas-crm/js/plugins/messenger/messenger-theme-future.js"></script>
		<script src="/lomas-crm/js/plugins/masked-input/jquery.maskedinput.min.js"></script>
		<script src="/lomas-crm/js/generales.js"></script>
		<script src="/lomas-crm/js/garita/crearDireccion.js"></script>
		<!-- DECLARACIÃN DE FUNCIONES JAVASCRIPT -->
		
		<script type="text/javascript">
			var id =<%out.print(id);%>;
			$("#numero").mask("99");
			$("#numeroCasa").mask("99-99");
			$('#telefono').mask("9999-9999");
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
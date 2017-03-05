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
		response.sendRedirect("../sesionExpirada.jsp");
	}
	else
	{
		sesion = (Sesion_BE) session.getAttribute("sesion");
		if(!General_BLL.tienePermiso(sesion,Funciones.CREAR_DIRECCION)){
			response.sendRedirect("../errorpermisos.jsp");
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
		<link href="../css/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="../icons/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="../css/plugins/bootstrap-datepicker/datepicker3.css" rel="stylesheet">
		<link href="../css/style.css" rel="stylesheet">
		<link href="../css/plugins.css" rel="stylesheet">
		<link href="../css/plugins/messenger/messenger.css" rel="stylesheet">
		<link href="../css/plugins/messenger/messenger-theme-future.css" rel="stylesheet">
		
		<!-- ESTILOS PERSONALIZADOS-->
		<style type="text/css">
			.clasetest{width: 54px; }
		</style>
	</head>
	<body ng-app="crearDireccion">
		<div id="wrapper">
			<%@include file="/masterPages/MasterMenus.jsp"%>
			
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
									<li><i class="fa fa-dashboard"></i>  <a href="../index.jsp">Inicio</a>
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
														mask='99?'
														Restrict="reject"
														data-ng-model="numero" 
														data-ng-blur="addZero()"
														data-ng-pattern="/[0-9]+/"
														data-ng-required="true">
												</div>
												<div class="form-group col-xs-3">
													<label for="txtDescripcion">Calle o Avenida *</label>
													<select class="form-control" 
													name="avenidaCalle"
													data-ng-options="option.name for option in values track by option.id"
													data-ng-model="avenidaCalle" 
													required>
														<option value="" disabled>::Seleccione::</option>
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
				                                   	placeholder="99-99"
				                                   	mask='99-99'
													Restrict = "reject"
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
															placeholder="9999-9999" 
															mask='9999-9999'
															Restrict = "reject"
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
													name="estadoPago" 
													data-ng-options="option.name for option in estadosPago track by option.id"
													data-ng-model="estadoPago" 
													required>
														<option value="" disabled>::Seleccione::</option>
													</select>
												</div>
												
												<div class="form-group col-md-6 col-lg-6 col-xs-6" >
													<label for="txtDescripcion">Estado de domicilio *</label>
													<select class="form-control" 
													name="estadoDomicilio"
													data-ng-options="option.name for option in estadosDomicilio track by option.id"
													data-ng-model="estadoDomicilio" 
													data-ng-change ="cambio()"
													required>
														<option value="" disabled>::Seleccione::</option>
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
		<script src="../js/angular/angular.min.js"></script>
		<script src="../js/angular/Modules/angular-sanitize.js"></script>
		<script src="../js/angular/Modules/ngMask.js"></script>
		<script src="../js/plugins/bootstrap/bootstrap.min.js"></script>
		<script src="../js/plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script>
		<script src="../js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script src="../js/plugins/popupoverlay/jquery.popupoverlay.js"></script>
		<script src="../js/plugins/popupoverlay/defaults.js"></script>
		<script src="../js/plugins/popupoverlay/logout.js"></script>
		<script src="../js/plugins/datatables/jquery.dataTables.js"></script>
		<script src="../js/estiloTabla.js"></script>
		<script src="../js/flex.js"></script>
		<script src="../js/plugins/messenger/messenger.min.js"></script>
		<script src="../js/plugins/messenger/messenger-theme-future.js"></script>
		<script src="../js/plugins/masked-input/jquery.maskedinput.min.js"></script>
		<script src="../js/generales.js"></script>
		<script src="../js/garita/crearDireccion.js"></script>
		<!-- DECLARACIÃN DE FUNCIONES JAVASCRIPT -->
		
		<script type="text/javascript">
			var id =<%out.print(id);%>;
			/*$("#numero").mask("99");
			$("#numeroCasa").mask("99-99");
			$('#telefono').mask("9999-9999");*/
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
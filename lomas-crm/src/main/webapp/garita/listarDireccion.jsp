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
		if(!General_BLL.tienePermiso(sesion,Funciones.LISTAR_INGRESO)){
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
	<body data-ng-app="listarDireccion">
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
			<div id="page-wrapper" class="" data-ng-controller="listar">
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
									<li class="active">Listar Dirección</li>
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
									
									<div class="table-responsive">
										<!-- DECLARACION DE TABLA -->
										<div class="row" style="  margin-bottom: 15px;">
											<div class="col-sm-12">
												<div id="estado">
												
												</div>
												<div class="input-group">
													<form name="formulario" data-ng-submit="formulario.$valid && buscar()">
							               				<div class="row">
															<div class="form-group col-xs-3" data-ng-class="{ 'has-error' : formulario.numeroFiltro.$invalid && !formulario.numeroFiltro.$pristine }" >
																<label for="txtNombre">Número *</label>
																	<input 
																		type="text" 
																		class="form-control"  
																		id="numero" 
																		placeholder="No." 
																		maxlength="10" 
																		name="numeroFiltro"
																		data-ng-model="numeroFiltro" 
																		data-ng-pattern="/[0-9]+/"
																		mask='99?'
																		Restrict="reject"
																		data-ng-required="numeroCasaFiltro.length > 0">
																</div>
																<div class="form-group col-xs-3">
																	<label for="txtDescripcion">Calle o Avenida *</label>
																	<select class="form-control" 
																	data-ng-options="option.name for option in values track by option.id"
																	data-ng-model="avenidaCalleFiltro" 
																	data-nq-required="numeroFiltro.length > 0 || numeroCasaFiltro.length > 0">
																		<option value="" disabled>::Seleccione::</option>
																	</select>
																</div>
																
																<div class="form-group col-xs-6" data-ng-class="{'has-error': formulario.numeroCasaFiltro.$invalid && !formulario.numeroCasaFiltro.$pristine}" >
																	<label for="txtDescripcion" >Numero casa *</label>
								                                   	<input name="numeroCasaFiltro" 
								                                   	type="text" 
								                                   	class="form-control"
								                                   	id="numeroCasa" 
								                                   	placeholder="Número casa"
								                                   	mask='99-99'
																	Restrict = "reject"
								                                   	data-ng-model="numeroCasaFiltro"
								                                   	data-ng-required="numeroFiltro.length > 0">
									                            </div>
															</div>
															<button type="submit" class="btn btn-default" style="margin-bottom:10px;" id="btn-buscar"
															data-ng-bind-html="textoBotonBuscar"
															data-ng-disabled="disableBuscar"></button>
													</form>
				                                	
				                                </div>
				                               		
											</div>
										</div>
										<table id="example" class="table table-striped table-bordered table-hover table-green dataTable" cellspacing="0" width="100%">
											<!-- DECLARACIÃN DE COLUMNAS DE TABLA -->
											<thead >
												<tr>
													
													<th style="width: 54px;"></th>
													<th style="text-align:center;" >Dirección</th>
													<th style="text-align:center;" >Nombre titular</th>
													<th style="text-align:center;" >Teléfono</th>
													<th style="text-align:center;" >Email</th>
													<th style="text-align:center;" >Estado pago</th>
													<th style="text-align:center;" >Estado domicilio</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="modal fade" id="modalModificarDireccion" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
				    <div class="modal-dialog">
				        <div class="modal-content">
				            <div class="modal-header">
				            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				            <h4 class="modal-title" id="myModalLabel">Modificar dirección</h4>
				            </div>
				            <form name="formEdicion" data-ng-submit="formEdicion.$valid && realizarEdicion()">
					            <div class="modal-body">
				           			<div class="row">
				               			<div class="form-group col-xs-3" data-ng-class="{ 'has-error' : formEdicion.numero.$invalid && !formEdicion.numero.$pristine }" >
											<label for="txtNombre">Número *</label>
											<input 
												type="text" 
												class="form-control"  
												id="numero" 
												placeholder="No." 
												maxlength="10" 
												name="numero"
												data-ng-model="numero" 
												mask='99?'
												Restrict="reject"
												data-ng-pattern="/[0-9]+/"
												data-ng-required="true"
												data-ng-disabled="true">
										</div>
										<div class="form-group col-xs-3">
											<label for="txtDescripcion">Calle o Avenida *</label>
											<select class="form-control" 
											data-ng-options="option.name for option in values track by option.id"
											data-ng-model="avenidaCalle" 
											data-nq-required="true"
											data-ng-disabled="true">
												<option value="" selected disabled>::Seleccione::</option>
											</select>
										</div>
										
										<div class="form-group col-xs-6" data-ng-class="{'has-error': formEdicion.numeroCasa.$invalid && !formEdicion.numeroCasa.$pristine}" >
											<label for="txtDescripcion" >Numero casa *</label>
				                                 	<input name="numeroCasa" 
				                                 	type="text" 
				                                 	class="form-control"
				                                 	id="numeroCasa" 
				                                 	placeholder="Número casa" 
				                                 	maxlength="10" 
				                                 	data-ng-model="numeroCasa" 
				                                 	mask='99-99'
													Restrict = "reject"
				                                 	data-ng-required="true"
				                                 	data-ng-disabled="true">
				                           </div>
										
										<div class="form-group col-xs-6" data-ng-class="{'has-error': formEdicion.familia.$invalid && !formEdicion.familia.$pristine}">
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
										<div class="form-group col-xs-6" data-ng-class="{'has-error': formEdicion.telefono.$invalid && !formEdicion.telefono.$pristine}">
											<label for="txtDescripcion">Teléfono</label>
											<div class="input-group">
												<span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>
												<input 
													class="form-control" 
													id="telefono"
													name="telefono" 
													mask='9999-9999'
													Restrict = "reject"
													data-ng-model="telefono"
													data-ng-disabled="isAbandonada">
											</div>
										</div>
										<div class="form-group col-md-6 col-lg-6 col-xs-12" data-ng-class="{'has-error': formEdicion.titular.$invalid && !formEdicion.titular.$pristine}">
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
										
										<div class="form-group col-md-6 col-lg-6 col-xs-6" data-ng-class="{'has-error': formEdicion.email.$invalid && !formEdicion.email.$pristine}">
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
					            </div>
					            <div class="modal-footer" style="margin-top:0;">
					               <button type="button"  class="btn btn-red" data-dismiss="modal"><i class="fa fa-times"></i> Cerrar</button>
					               <button type="submit" id="btn-guardar"class="btn btn-default" data-ng-disabled="disabledBotonEditar"><i data-ng-class="claseBotonEditar"></i> Guardar</button>
					        	</div>
				        	</form>
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
		<script src="../js/generales.js"></script>
		<script src="../js/garita/listarDireccion.js"></script>
		<!-- DECLARACIÃN DE FUNCIONES JAVASCRIPT -->
		
		<script type="text/javascript">
			var id =<%out.print(id);%>;
			/*$("#numero").mask("99");
			$("#numeroCasa").mask("99-99");
			$('#telefono').mask("9999-9999");
			$('#identificacion').mask("9999 99999 9999");*/
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
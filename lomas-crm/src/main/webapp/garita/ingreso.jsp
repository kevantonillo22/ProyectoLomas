<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="crm_BLL.General_BLL"%>
<%@page import="crm_BE.Sesion_BE"%>
<%@page import="crm_BE.Funciones"%>
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
		if(!General_BLL.tienePermiso(sesion,Funciones.INGRESO_EGRESO)){
			response.sendRedirect("/lomas-crm/errorpermisos.jsp");
		}
	}
	
	//boolean permiso_modificar = General_BLL.tienePermiso(sesion,Funciones.MODIFICAR_CHEQUES);
%>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
		<link href="/lomas-crm/css/plugins/dropzone/css/dropzone.css" rel="stylesheet">
		<!-- ESTILOS PERSONALIZADOS-->
		<style type="text/css">
			.clasetest{width: 54px; }
			#example td {
			  overflow: hidden; /* this is what fixes the expansion */
			  text-overflow: ellipsis; /* not supported in all browsers, but I accepted the tradeoff */
			  white-space: nowrap;
			}
			
			#example {
				table-layout:fixed;
			}
			
			.preImagen{
				width:100%;
			}
			
			.animate-show {
			  opacity: 1;
			}
			
			.animate-show.ng-hide-add, .animate-show.ng-hide-remove {
			  transition: all ease 0.5s;
			}
			
			.animate-show.ng-hide {
			  opacity: 0;
			}
			
			input.has-error{
				border:solid 1px rgb(217, 83, 79);
			}
			
			.familiaEncontrada{
				color:#5CB85C;
			}
			
			.familiaNoEncontrada{
				color:#D9534F;
			}			
		</style>
	</head>
	<body data-ng-app = "Ingreso">
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
			<!-- MENÃ IZQUIERDO PRINCIPAL -->
			<nav class="navbar-side collapsed" role="navigation" >
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
			<div id="page-wrapper" class="collapsed" data-ng-controller="controladorIngreso">
				<div class="page-content page-content-ease-in">
					<!-- TITULO DE LA PÃGINA -->
					<div class="row">
						<div class="col-lg-12">
							<div class="page-title">
								<h1>Ingreso/Egreso<small></small>
								</h1>
								<!-- <ol class="breadcrumb">
									<li><i class="fa fa-dashboard"></i>  <a href="/lomas-crm/index.jsp">Inicio</a>
									</li>
									<li class="active">Crear cheques</li>
								</ol>-->
							</div>
						</div>
					</div>
					<div class="row">
						<!-- INICIA FORMULARIO DE LISTAR -->
						<div class="col-lg-7 col-md-12">
							<div id="estado"> </div>
							<div class="portlet portlet-gray">
								<div class="portlet-heading">
									<div class="portlet-title">
										<h4>Ingreso</h4>
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
									
									<div id="basicFormExample" class="panel-collapse in" style="height: auto;">
										<div class="portlet-body" style="text-align: center;">
		
		
											<form role="form" name="formulario" data-ng-submit="almacenar()" >
												<div class="row">
													<div class="form-group col-lg-4 col-md-4">
														<label for="txtNombre">Placa:</label>
														<input style="text-transform:uppercase;" 
															type="text" 
															class="form-control" 
															id="placa" 
															placeholder="No." 
															maxlength="10" 
															onkeypress="return permite(event, 'num_car')"
															data-ng-model="placa"
															data-ng-change="cambioTexto()"
															data-ng-required="true"
															data-ng-model="placa">
													</div>
													
													<div class="form-group col-lg-8 col-md-8">
														<label for="txtNombre">Dirección:</label>
														<div class="input-group">
															<input style="width:40%;text-align:right;" 
																data-ng-class="{'has-error': formulario.numero.$invalid && !formulario.numero.$pristine}" 
																type="text" 
																class="form-control" 
																id="numero" 
																name="numero" 
																placeholder="Numero" 
																maxlength="2" 
																onkeypress="return permite(event, 'solo_num')"
																data-ng-required = "true"
																my-enter="formulario.$valid && buscar()"
																data-ng-change="cambioTexto()"
																data-ng-pattern="/[0-9]+/"
																data-ng-model="numero">
															<select style="width:20%;" 
																id="avenidaCalle" 
																class="selectpicker form-control" 
																title="Seleccione ..."
																data-ng-model="avenidaCalle"
																data-ng-change="cambioTexto()"
																data-ng-options="option.name for option in values track by option.id">
															</select>
														  
															<input style="margin-left:10px;width:25%;" 
																data-ng-class="{'has-error': formulario.numeroCasa.$invalid && !formulario.numeroCasa.$pristine}" 
																type="text" 
																class="form-control" 
																id="numeroCasa" 
																name="numeroCasa" 
																placeholder="Numero"
																data-ng-model="numeroCasa"
																data-ng-pattern="/[0-9][0-9]-[0-9][0-9]/" 
																data-ng-required = "true"
																my-enter="formulario.$valid && buscar()"
																data-ng-change="cambioTexto()"
																mask='99-99'
																Restrict = "reject">
															
															<button style="height:34px;margin-left:5px;width:10%;" 
																id='btn-guardar' 
																class="btn btn-primary"
																data-ng-click="buscar()"
																type="button"
																data-ng-disabled="(formulario.numero.$invalid || formulario.avenidaCalle.$invalid || formulario.numeroCasa.$invalid) || disabledBuscar">
																 <i data-ng-class="claseBotonBuscar"></i>
															</button>
														</div>
														
														<div data-ng-class="claseEncontrada" style="margin-top: 10px;text-align: right;font-size: 18px;height:25px;" >
															<span style="font-weight:bolder;" data-ng-show="textoFamilia.length > 0">Familia: </span>  <span data-ng-bind-html="textoFamilia"> </span>
														</div>
													</div>
													
												</div>
												
												<div class="row">
													
															<div class="col-sm-4">
																<label>Documento</label>
																<video data-ng-show="isShowDoc" id="camaraDoc" style="width:100%;" class="camara" data-ng-src="{{camaraDocumento | trustUrl}}" autoplay></video>
																<img data-ng-show="!isShowDoc" id="camarita" data-ng-src="{{imgCamara}}" style="width:100%;">
																<button id='botonIniciar' style="width:100%;border-radius:0px;" type='button'
																	class="btn btn-primary"
																	data-ng-disabled="isDisabledBtnDoc"
																	data-ng-click="iniciarCamaraDocumento()">
																	Asignar cámara &nbsp;<i data-ng-class="classBtnDoc"></i>
																</button>
															</div>
															<div class="col-sm-4">
																<label>Rostro</label>
																<video data-ng-show="isShowRostro" id="camaraRostro" style="width:100%;" class="camara" data-ng-src="{{camaraRostro | trustUrl}}" autoplay></video>
																<img id="camarita" data-ng-show="!isShowRostro" data-ng-src="{{imgCamara}}" style="width:100%;">
																<button id='botonIniciar' style="width:100%;border-radius:0px;" type='button'
																	class="btn btn-primary"
																	data-ng-disabled="isDisabledBtnRostro"
																	data-ng-click="iniciarCamaraRostro()">
																	Asignar cámara &nbsp;<i data-ng-class="classBtnRostro"></i>
																</button>
															</div>
															<div class="col-sm-4">
																<label>Placa</label>
																<video data-ng-show="isShowPlaca" id="camaraPlaca" style="width:100%;" class="camara" data-ng-src="{{camaraPlaca | trustUrl}}" autoplay></video>
																<img id="camarita" data-ng-show="!isShowPlaca" data-ng-src="{{imgCamara}}" style="width:100%;">
																<button id='botonIniciar' style="width:100%;border-radius:0px;" type='button'
																	class="btn btn-primary"
																	data-ng-disabled="isDisabledBtnPlaca"
																	data-ng-click="iniciarCamaraPlaca()">
																	Asignar cámara &nbsp;<i data-ng-class="classBtnPlaca"></i>
																</button>
															</div>
												</div>
												
												<div id='botonera' style="margin-bottom:30px;margin-top:20px;">
													<button id='botonFoto' 
														data-ng-click="capturarImagen()" 
														type="button"
														data-ng-disabled="!(iniciadaCamaraDoc && iniciadaCamaraRostro && iniciadaCamaraPlaca)"
														class="btn btn-success">
														Capturar&nbsp; <span class="fa fa-camera"></span>
													</button>
													<button id='btn-guardar' 
														type="submit"
														data-ng-disabled="formulario.$invalid || disableAlmacenar || disableAlmacenarCaptura"
														class="btn btn-primary">
														Guardar&nbsp; <i data-ng-class="claseBotonAlmacenar"></i>
													</button>
												</div>
												
												<div class="row capturas animate-show" style="margin-top:40px;"  data-ng-show="isShowCapturadas">
													<label>Fotografías tomadas</label>
													<div class="col-sm-12">
														<canvas style="width:calc(100% / 3);margin-right: -4px;" id="fotoDoc"></canvas>
														<canvas style="width:calc(100% / 3);margin-right: -4px;" id="fotoRostro"></canvas>
														<canvas style="width:calc(100% / 3);" id="fotoPlaca"></canvas>
													</div>
												</div>
												
												
		
												
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						
						
						<div class="col-lg-5 col-md-12">
							<div id="estado"> </div>
							<div class="portlet portlet-gray">
								<div class="portlet-heading">
									<div class="portlet-title">
										<h4>Últimos ingresos</h4>
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
									<table id="tabla-ingresos" class="table-striped table-hover" style="width:100%;">
										<tr>
											<th>No.</th>
											<th>Placa</th>
											<th>Dirección</th>
											<th>Fecha Ingreso</th>
										</tr>
										<tr data-ng-repeat="ing in ingresos">
										    <td style="padding:8px 0px">{{ $index + 1}}</td>
										    <td>{{ ing.in_placa }}</td>
										    <td>{{ ing.in_direccion }}</td>
										    <td>{{ ing.in_fecha_entrada }}</td>
									  	</tr>
									</table>
																		

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
	                    <a href="/lomas-crm/salir" class="btn btn-green">
	                        <strong>Salir</strong>
	                    </a>
	                </li>
	                <li>
	                    <button class="logout_close btn btn-green">Cancelar</button>
	                </li>
	            </ul>
	        </div>
	    </div>
	    
	<div class="modal fade" id="modal-modificar-cheque" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	            <h4 class="modal-title" id="myModalLabel">Ingresar Cheque</h4>
	            </div>
	            <div class="modal-body">
               			<div class="row">
	               			<div class="col-xs-12">
	               				<input type="text" class="form-control hide" id="txtId" placeholder="No." maxlength="10" onkeypress="return permite(event, 'solo_num')">
								<div class="form-group col-xs-12 col-sm-12">
									<label for="txtNombre">No. cheque:</label>
									<input type="text" class="form-control" id="txtNumero" placeholder="No." maxlength="10" onkeypress="return permite(event, 'solo_num')">
								</div>
								<div class="form-group col-xs-12 col-sm-6">
									<label for="txtDescripcion">Lugar:</label>
									<input type="text" class="form-control" id="txtLugar" placeholder="Nombre" maxlength="50" onkeypress="return permite(event, 'num_car')">
								</div>
								<div class="form-group col-xs-12 col-sm-6" >
									<label for="txtDescripcion">Fecha:</label>
                                   	<div style="width:100%;" class="input-group date" id="datepicker3">
	                                   	<input onkeypress="return permite(event, 'solo_num')" style="cursor:pointer;" type="text" class="form-control" style="text-align:center;" id="txtFecha" placeholder="Fecha" maxlength="10">
	                                   	<span   class="con-cursor input-group-addon">
						                       <span class="glyphicon glyphicon-calendar"></span>
						                </span>
	                                </div>
                               	</div>
								<div class="form-group col-xs-12 col-sm-6">
									<label for="txtDescripcion">A nombre de:</label>
									<input type="text" class="form-control" id="txtNombre" placeholder="Nombre" maxlength="50" onkeypress="return permite(event, 'num_car')">
								</div>
								<div class="form-group col-xs-12 col-sm-6">
									<label for="txtDescripcion">Monto:</label>
									<div class="input-group">
										<span class="input-group-addon">Q</span>
										<input class="form-control" id="txtCantidad" placeholder="" rows="5" maxlength="20" onkeypress="return permite(event, 'num')">
									</div>
								</div>
								<div class="form-group col-xs-12 col-sm-12">
									<label for="txtDescripcion">Motivo:</label>
									<input type="text" class="form-control" id="txtMotivo" placeholder="Nombre" maxlength="100" onkeypress="return permite(event, 'num_car')">
								</div>
								<div class="form-group col-xs-12 col-sm-12">
									<label for="txtDescripcion">Imagen cheque:</label>
									<div id="txtFile" class="dropzone"></div>
								</div>
							</div>
						</div>
	            </div>
	            <div  id="lblAdvertencia" style="text-align: center;width: 90%;margin: 0 auto;"></div>
	            <div class="modal-footer" style="margin-top:0;">
	                <button type="button"  class="btn btn-red" data-dismiss="modal"><i class="fa fa-times"></i> Cerrar</button>
	               <button type="submit" id="btn-guardar"class="btn btn-default"><i class="fa fa-check"></i> Guardar</button>
	        	</div>
	    	</div>
  		</div>
	</div>
	    
	    
	    
		<!-- SCRIPTS GLOBALES -->
		
		<script src="/lomas-crm/js/jquery-1.11.0.min.js"></script>
		<script src="/lomas-crm/js/angular/angular.min.js"></script>
		<script src="/lomas-crm/js/angular/Modules/angular-sanitize.js"></script>
		<script src="/lomas-crm/js/angular/Modules/ngMask.js"></script>
		<script src="/lomas-crm/js/angular/Modules/angular-animate.js"></script>
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
		<script src="/lomas-crm/js/garita/garita.js"></script>
		<!-- DECLARACIÃN DE FUNCIONES JAVASCRIPT -->
		<script type="text/javascript">
		
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
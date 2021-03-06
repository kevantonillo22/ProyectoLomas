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
		response.sendRedirect("../sesionExpirada.jsp");
	}
	else
	{
		sesion = (Sesion_BE) session.getAttribute("sesion");
		if(!General_BLL.tienePermiso(sesion,Funciones.LISTAR_CAJA)){
			response.sendRedirect("../errorpermisos.jsp");
		}
	}
	
	boolean permiso_modificar = General_BLL.tienePermiso(sesion,Funciones.MODIFICAR_CAJA);
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
		<link href="../css/plugins/dropzone/css/dropzone.css" rel="stylesheet">
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
								<h1>Cuentas<small></small>
								</h1>
								<ol class="breadcrumb">
									<li><i class="fa fa-dashboard"></i>  <a href="../index.jsp">Inicio</a>
									</li>
									<li class="active">Crear cheques</li>
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
													<div class="row">
														
				                                    	<div class="form-group col-sm-4" >
				                                    		<label for="txtDescripcion">Rango Fecha:</label>
				                                    		<div style="width:100%;" class="input-group date" id="datepicker1">
						                                    	<input onkeypress="return permite(event, 'solo_num')" style="cursor:pointer;text-align:center;" type="text" class="form-control" style="text-align:center;" id="txtFecha1" placeholder="Inicio" maxlength="10">
						                                    	<span class="con-cursor input-group-addon">
											                        <span class=""><i class="fa fa-calendar"></i> Hasta</span>
											                    </span>
											                    <input onkeypress="return permite(event, 'solo_num')" style="cursor:pointer;text-align:center;" type="text" class="form-control" style="text-align:center;" id="txtFecha2" placeholder="Fin" maxlength="10">
						                                    </div>
					                                	</div>
					                                	
					                                	<div class="form-group col-sm-4" >
				                                    		<label for="txtDescripcion">Rango Sumatoria:</label>
				                                    		<div style="" class="input-group" id="datepicker1">
						                                    	<input onkeypress="return permite(event, 'num')" style="text-align:center;" type="text" class="form-control" style="text-align:center;" id="txtMonto1" placeholder="Inferior" maxlength="10">
						                                    	<span class="con-cursor input-group-addon">
											                        <span class=""><i class="fa fa-money"></i></span>
											                    </span>
											                    <input onkeypress="return permite(event, 'num')" style="text-align:center;" type="text" class="form-control" style="text-align:center;" id="txtMonto2" placeholder="Superior" maxlength="10">
						                                    </div>
					                                	</div>
				                                	</div>
				                                	<button class="btn btn-default" style="margin-bottom:10px;" type="submit" id="btn-buscar">Buscar&nbsp;&nbsp;<i class="fa fa-search"></i></button>
				                                	<div  id="lblAdvertenciaMain" style="text-align: center;width: 90%;margin: 0 auto;"></div>
				                               		</div>
											</div>
										</div>
										<table id="example" class="table table-striped table-bordered table-hover table-green dataTable" cellspacing="0" width="100%">
											<!-- DECLARACIÃN DE COLUMNAS DE TABLA -->
											<thead >
												<tr>
													
													<th style="width: 54px;"></th>
													<th style="text-align:center;" >Id</th>
													<th style="text-align:center;" >Total Efectivo</th>
													<th style="text-align:center;" >Total Documentos</th>
													<th style="text-align:center;" >Sumatoria</th>
													<th style="text-align:center;" >Fondo Autorizado</th>
													<th style="text-align:center;" >Variación</th>
													<th style="text-align:center;" >Fecha</th>
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
	    
	<div class="modal fade" id="modal-modificar-caja" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		            <h4 class="modal-title" id="myModalLabel">Modificar Caja</h4>
	            </div>
	            <div class="modal-body">
	            	<div class="form-group" style="display:none;">
						<input type="text" class="form-control cantidad1" id="txtId">
					</div>
	            	<div class="row">
						<div class="col-xs-12">
							<div class="row">
							<div class="col-sm-6">
							<div class="row">
								<div class="col-sm-4 col-xs-4">
		               				<label for="txtNombre">Billetes</label>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<label for="txtNombre">Cantidad</label>
								</div>
								<div class="col-sm-4 col-xs-4">
		               				<label for="txtNombre">Monto Q.</label>
								</div>
							</div>
							
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">200</label>
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control cantidad1" id="txtBilleteCantidad200" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control monto1" id="txtBilleteMonto200" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">100</label>
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control cantidad1" id="txtBilleteCantidad100" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control monto1" id="txtBilleteMonto100" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">50</label>
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control cantidad1" id="txtBilleteCantidad50" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control monto1" id="txtBilleteMonto50" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">20</label>
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control cantidad1" id="txtBilleteCantidad20" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control monto1" id="txtBilleteMonto20" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">10</label>
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control cantidad1" id="txtBilleteCantidad10" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control monto1" id="txtBilleteMonto10" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">5</label>
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control cantidad1" id="txtBilleteCantidad5" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control monto1" id="txtBilleteMonto5" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">1</label>
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control cantidad1" id="txtBilleteCantidad1" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control monto1" id="txtBilleteMonto1" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<div class="row">
		               			
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">Sub Total:</label>
									</div>
								</div>
		               			<div class="col-sm-8 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control" id="txtBilleteSub" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<div class="row">
		               			
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">Fecha:</label>
									</div>
								</div>
		               			<div class="col-sm-8 col-xs-4">
		               				<div style="width:100%;" class="input-group date" id="datepicker3">
		                                  	<input onkeypress="return permite(event, 'solo_num')" style="cursor:pointer;" type="text" class="form-control" style="text-align:center;" id="txtFecha" placeholder="Fecha" maxlength="10">
		                                  	<span   class="con-cursor input-group-addon">
						                       <span class="glyphicon glyphicon-calendar"></span>
						                </span>
		                               </div>
								</div>
							</div>
							</div>
							
							
							<!-- Monedas -->
							<div class="col-sm-6">
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<label for="txtNombre">Monedas</label>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<label for="txtNombre">Cantidad</label>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<label for="txtNombre">Monto Q.</label>
								</div>
							</div>
							
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">1.00</label>
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control  cantidad2" id="txtMonedaCantidad1" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control monto2" id="txtMonedaMonto1" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">0.50</label>
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control cantidad2" id="txtMonedaCantidad05" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control monto2" id="txtMonedaMonto05" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">0.25</label>
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control cantidad2" id="txtMonedaCantidad025" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control monto2" id="txtMonedaMonto025" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">0.10</label>
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control cantidad2" id="txtMonedaCantidad010" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control monto2" id="txtMonedaMonto010" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">0.05</label>
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control cantidad2" id="txtMonedaCantidad005" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control monto2" id="txtMonedaMonto005" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">0.01</label>
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control cantidad2" id="txtMonedaCantidad001" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control monto2" id="txtMonedaMonto001" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							<div class="row" style="height:49px;"></div>
							<div class="row">
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">Sub Total:</label>
									</div>
								</div>
		               			<div class="col-sm-8 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control" id="txtMonedaSub" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							<br>
							</div>
							</div>
							
							<div class="row">
							<div class="col-sm-6"></div>
							<div class="col-sm-6">
							<div class="row">
		               			
		               			<div class="col-sm-4 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">Total efectivo:</label>
									</div>
								</div>
		               			<div class="col-sm-8 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control" id="txtTotal" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							</div>
							</div>
							<hr>
							
							<div class="row">
								<div class="col-sm-12 col-xs-12" style="text-align:center;">
		               				<div class="form-group">
										<label for="txtNombre">Documentos a presentar</label>
									</div>
								</div>
		               				
							</div>
							
							<div class="row">
								<div class="col-sm-1"></div>
		               			<div class="col-sm-3 col-xs-4">
		               				<label for="txtNombre">NIT</label>
								</div>
		               			<div class="col-sm-3 col-xs-4">
		               				<label for="txtNombre">Nombre</label>
								</div>
								<div class="col-sm-1"></div>
		               			<div class="col-sm-3 col-xs-4">
		               				<label for="txtNombre">Monto</label>
								</div>
							</div>
							<div class="facturas-body">
								<div class="row">
									<div class="col-sm-1"></div>
			               			<div class="col-sm-3 col-xs-4">
			               				<div class="form-group">
											<input type="text" class="form-control nit" id="txtNit1" placeholder="..." maxlength="8" onkeypress="return permite(event, 'solo_num')">
										</div>
									</div>
			               			<div class="col-sm-4 col-xs-4">
			               				<div class="form-group">
											<input type="text" class="form-control nombre" id="txtNombre1" placeholder="..." maxlength="50" onkeypress="return permite(event, 'num_car')">
										</div>
									</div>
			               			<div class="col-sm-3 col-xs-3">
			               				<div class="form-group">
											<input type="text" class="form-control montoFactura" id="txtMonto1" placeholder="..." maxlength="15" onkeypress="return permite(event, 'num')">
										</div>
									</div>
									<div class="col-sm-1"><i id="addRow1"style="  border-radius: 50%;padding: 0px;  width: 20px;  height: 20px; top: 10px;float: left;margin-left: -20px;cursor:pointer;"class="glyphicon glyphicon-plus add"></i></div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-1"></div>
		               			<div class="col-sm-3 col-xs-4">
		               				
								</div>
		               			<div class="col-sm-3 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">Sub Total:</label>
									</div>
								</div>
								<div class="col-sm-1"></div>
		               			<div class="col-sm-3 col-xs-4">
		               				<div class="form-group">
										<input type="text" class="form-control" id="txtDocumentosSub" placeholder="..." maxlength="15" onkeypress="return permite(event, 'solo_num')">
									</div>
								</div>
							</div>
							
							<hr>
							
							<div class="row">
								<div class="col-sm-1"></div>
		               			<div class="col-sm-3 col-xs-4">
		               				<div class="form-group">
									</div>
								</div>
		               			<div class="col-sm-3 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">Sumatoria:</label>
									</div>
								</div>
								<div class="col-sm-1"></div>
		               			<div class="col-sm-3 col-xs-3">
		               				<div class="form-group">
										<input type="text" class="form-control monto" id="txtSumatoria" placeholder="..." maxlength="15" onkeypress="return permite(event, 'num')">
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-1"></div>
		               			<div class="col-sm-3 col-xs-4">
		               				<div class="form-group">
									</div>
								</div>
		               			<div class="col-sm-3 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">Fondo autorizado:</label>
									</div>
								</div>
								<div class="col-sm-1"></div>
		               			<div class="col-sm-3 col-xs-3">
		               				<div class="form-group">
										<input type="text" class="form-control monto" id="txtFondo" placeholder="..." maxlength="15" onkeypress="return permite(event, 'num')">
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-1"></div>
		               			<div class="col-sm-3 col-xs-4">
		               				<div class="form-group">
									</div>
								</div>
		               			<div class="col-sm-3 col-xs-4">
		               				<div class="form-group">
										<label for="txtNombre">Variación &#177;:</label>
									</div>
								</div>
								<div class="col-sm-1"></div>
		               			<div class="col-sm-3 col-xs-3">
		               				<div class="form-group">
										<input type="text" class="form-control monto" id="txtVariacion" placeholder="..." maxlength="15" onkeypress="return permite(event, 'num')">
									</div>
								</div>
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
		<script type="text/javascript">
			var per = <%=permiso_modificar%>;
		</script>
		<script src="../js/jquery-1.11.0.min.js"></script>
		<script src="../js/plugins/bootstrap/bootstrap.min.js"></script>
		<script src="../js/plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script>
		<script src="../js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script src="../js/plugins/popupoverlay/jquery.popupoverlay.js"></script>
		<script src="../js/plugins/popupoverlay/defaults.js"></script>
		<script src="../js/plugins/popupoverlay/logout.js"></script>
		<script src="../js/plugins/datatables/jquery.dataTables.js"></script>
		<script src="../js/plugins/masked-input/jquery.maskedinput.min.js"></script>
		<script src="../js/estiloTabla.js"></script>
		<script src="../js/flex.js"></script>
		<script src="../js/plugins/messenger/messenger.min.js"></script>
		<script src="../js/plugins/messenger/messenger-theme-future.js"></script>
		<script src="../js/generales.js"></script>
		<script src="../js/caja/impresionDatos.js"></script>
		<script src="../js/caja/listarCaja.js"></script>
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
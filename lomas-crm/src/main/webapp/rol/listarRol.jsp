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
		if(!General_BLL.tienePermiso(sesion,Funciones.LISTAR_ROLES)){
			response.sendRedirect("/lomas-crm/errorpermisos.jsp");
		}
	}
	
	// Consulta los permisos adicionales del usuario
		boolean modificar = General_BLL.tienePermiso(sesion, Funciones.MODIFICACION_DE_ROLES);
		boolean eliminar = General_BLL.tienePermiso(sesion, Funciones.ELIMINAR_ROLES);
		boolean asignar = General_BLL.tienePermiso(sesion, Funciones.ASIGNAR_FUNCIONES_A_UN_ROL);
		boolean algunPermiso=(modificar||eliminar||asignar);
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
								<h1>Roles<small></small>
								</h1>
								<ol class="breadcrumb">
									<li><i class="fa fa-dashboard"></i>  <a href="/lomas-crm/index.jsp">Inicio</a>
									</li>
									<li class="active">Listar rol</li>
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
										<table id="example" class="table table-striped table-bordered table-hover table-green dataTable" cellspacing="0" width="100%">
											<!-- DECLARACIÃN DE COLUMNAS DE TABLA -->
											<thead >
												<tr>
													
													<th style="width: 54px;"></th>
													<th style="text-align:center;" >Código</th>
													<th style="text-align:center;" >Nombre</th>
													<th style="text-align:center;" >Descripción</th>
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
            <h4 class="modal-title" id="myModalLabel">Modificar rol</h4>
            </div>
            <div class="modal-body">
                		<form role="form">
										<div class="form-group">
											<label for="txtNombre">Nombre</label>
											<input type="text" class="form-control" id="txtNombre" placeholder="Nombre del rol" maxlength="50">
										</div>
										<div class="form-group">
											<label for="txtDescripcion">Descripción</label>
											<textarea class="form-control" id="txtDescripcion" placeholder="Descripción" rows="5" maxlength="200"></textarea>
										</div>
										<div  style="text-align: center;">
											
										</div>
										<br>
										
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
		var id;
		
			function cargarUIModificar(){
				$("#txtNombre").val('');
				$("#txtDescripcion").val('');
				//Deshabilitar el boton y mostrar animacion de cargado
				$("#btn-guardar").html(' Espere <i class="fa fa-spinner fa-spin"></i>');
				$("#btn-guardar").prop("disabled", true);

				//Llenar los datos que lleva la peticion
				var datos = {
					"rol": id,
					op:2
					
				};
				$.post('/lomas-crm/rol', datos, callback2, 'json');

				function callback2(respuesta) {
					
					if(respuesta.data){
						$("#txtNombre").val(respuesta.data[0].nombre);
						$("#txtDescripcion").val(respuesta.data[0].descripcion);
						$("#btn-guardar").html('Guardar');
						$("#btn-guardar").prop("disabled", false);
						
					}else{
						
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
			// Variables globales
			var table; // Tabla de datos
			var fila; // Fila a la cual se da click
		 	
			$(document).ready(function () {
				/*messenger*/
				Messenger.options = {
					extraClasses : 'messenger-fixed messenger-on-top',
					theme : 'future'
				};
				
				
				
				table = $('#example').DataTable({
					"ajax": {	"url": "/lomas-crm/rol",
		            	"data": function ( d ) {
	                	d.op = "2";},
						"type": "POST"},
		
					// paging: true,
					"lengthChange": false,
		
					"dom": 'ti',
					"displayLenght":10,
					"processing": true,
					"serverSide": true,
					"bFilter": false,
					"columns": 
					[
					  {
							"class": 'clasetest',
							"orderable": false,
							"data": '',
							"defaultContent": '<button title="Opciones" style="border-radius:50%;" class="btn btn-white"><b>+</b></button>',
							"visible": <% if(algunPermiso) { out.print("true"); } else { out.print("false"); }; %>
						},
						{
							"data": "id"
						}, {
							"data": "nombre"
						}, {
							"data": "descripcion"
						}
					],
					"order": [
						[1, 'asc']
					],
					"language": {
						"lengthMenu": "Mostrar _MENU_ filas por pÃ¡gina",
						search: "Buscar: ",
						"zeroRecords": "<center>No hay filas para mostrar</center>",
						"info": "Mostrando la pÃ¡gina _PAGE_ de _PAGES_",
						"infoEmpty": "No hay filas disponibles",
						"processing": "<div style='clear:left;'><center ><b>Procesando <i class='fa fa-spinner fa-spin'></i></b></center></div><br>",
						"infoFiltered": "(filtradas de _MAX_ filas en total)",
						"paginate": {
							"next": "Siguiente",
							"previous": "Anterior"
						}
					},
				
					"initComplete": function (settings, json) {
						if (json.resultado == "-100") {
							$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});

						} 
						
						
						$('#example_length').addClass("col-sm-6");
						$('#example_info').css('display', 'none');
						$('#example tbody tr').each(function () {
							$(this).find('td:eq(1)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 1
						});
					}
				});
		
				//Indispensable para detalle de cada tupla
				$('#example tbody').on('click', 'td.clasetest', function () {
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
						//btn btn-lg btn-green
						$(".btn-green").popover();
						$(".btn-green").click(function (e) {
							e.preventDefault();
						});
		
						tr.addClass('shown');
					}
				});
			});
		
			function format(d) {
				<% if(algunPermiso) { 
					out.println("return '<table cellpadding=\"5\" cellspacing=\"0\" border=\"0\" style=\"padding-left:50px;\">\' + ");
					out.println("	\'<tr>\' + ");
					out.println("		\'<td>Opciones</td>\' + ");
					out.println("	\'</tr>\' + ");
					out.println("	\'<tr>\' + ");
					if(modificar) out.println("'<td><a data-toggle=\"modal\" data-target=\"#basicModal\"  href=\"#\" onclick=\" cargarUIModificar()\"><button class=\"btn btn-default\" >Modificar</button></a></td>' +");
					if(eliminar) out.println("'<td><a href=\"javascript:eliminar(\\'' + d.nombre + '\\',\\'' + d.id + '\\')\"><button class=\"btn btn-red eliminar\" >Eliminar</button></a></td>' +");						
					if(asignar) out.println("'<td><a href=\"/lomas-crm/rol/funcionesRol.jsp?rol=' + d.id +'&nombre='+d.nombre+ '\"><button class=\"btn btn-default\" >Asignar funciones</button></a></td>' + ");
					out.println("	\'</tr>\' + ");
					out.println("\'</table>\';"); 
				} else {
					out.print("return '';"); 
				}; 
			%>
				
			}
		
			function eliminar(nombre, id) {
		
				
				var mensajito=Messenger().post({
					message: "Está seguro que desea eliminar al rol: " + nombre+"?",
				  	type:'info',
				  	showCloseButton:true,
				  	id:1,
				  	hideAfter:36000,
				  	actions: {
				  		aceptar: {
				 			label: "Aceptar",
				   			action: function(){ejecutarComando();mensajito.hide();}
				    	},
				   		cancelar: {
					 		label: "Cancelar",
					   		action: function(){mensajito.hide();}
						}
				  }
				});
				
				
				function ejecutarComando () {
					var datos = {
						"id": id,
						op:4
					};
		
					$("#estado").html("<center><b>Procesando <i class='fa fa-spinner fa-spin'></i></b></center><br>");
					$.post('/lomas-crm/rol', datos, callback, 'json');
		
					function callback(respuesta) {
						$("#estado").html("");
						if (respuesta.resultado=="1") {
							table.row(fila).remove().draw();
							advertenciaHTML = obtenerAdvertencia('info', respuesta.descripcion);
						} else {
							
							if (respuesta.resultado == "-100") {
								// Sesión caducó
								$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});

							} else if (respuesta.resultado == "-101") {
								// Error de permisos
								window.location.href = "/lomas-crm/errorpermisos.jsp";
							}else{
								obtenerAdvertencia('error', respuesta.descripcion);
							}
							
						}
						
						
					}
				} 
			}
		
			function obtenerAdvertencia(clase, mensaje) {
				  Messenger().post({
	        message: mensaje,
	        type: clase,
	        showCloseButton: true
	    });

				}
			
$('#btn-guardar').click(function (e) {
				
				//Validación de campos
				var error = 0;
				if ($('#txtNombre').val().trim().length == 0) {
					error = 1;
					 obtenerAdvertencia('error', 'El nombre es obligatorio');
				}
				
				if(!(/^[$A-Z_áéíóúÁÉÍÓÚñÑ][0-9A-Z_$áéíóúÁÉÍÓÚñÑ]*$/i.test($('#txtNombre').val()))){
					
					error = 1;
					obtenerAdvertencia(
							'error',
							'Sólo se permiten nombres que empiecen por letras. <br> No se permiten espacios en blanco.	<br> Ejemplos: <ul><li>Secretaria_General</li>  <li>Tesorero</li></ul>');
					
				}
				
				if ($('#txtDescripcion').val().trim().length == 0) {
					error = 2;
					obtenerAdvertencia('error', 'La descripción es obligatoria');
				}
				if (error == 0) {
					//Campos validados exitosamente
					$("#btn-guardar").html(' Espere <i class="fa fa-spinner fa-spin"></i>');
					$("#btn-guardar").prop("disabled", true);

					//Se crea un objeto para ser enviado al servidor 
					var datos = {
						rol: id,
						nombre: $('#txtNombre').val(),
						descripcion: $('#txtDescripcion').val(),
						op:3
					};
					$.post('/lomas-crm/rol', datos, callback2, 'json');

					function callback2(respuesta) {
						//Se obtiene la respuesta del servidor y se muestra la pagina
						$("#btn-guardar").prop("disabled", false);
						$("#btn-guardar").html('Guardar');
						if (respuesta.resultado == "1") {
							
							var d= fila.data();
							
				
						    d.nombre=$('#txtNombre').val();
						    d.descripcion=$('#txtDescripcion').val();
						    table
						        .row(fila)
						        .data(d)
						        .draw(false);
							obtenerAdvertencia('info', respuesta.descripcion);
						} else {
							if (respuesta.resultado == "-100") {
								// Sesión caducó
								$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});

							} else if (respuesta.resultado == "-101") {
								// Error de permisos
								window.location.href = "/lomas-crm/errorpermisos.jsp";
							}else{
								obtenerAdvertencia('error', respuesta.descripcion);
							}
							
						}
						
					}
				}
				e.preventDefault();
			});
			
			
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
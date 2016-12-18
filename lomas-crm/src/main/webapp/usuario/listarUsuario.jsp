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
		if(!General_BLL.tienePermiso(sesion, Funciones.LISTAR_USUARIO)){
			response.sendRedirect("/lomas-crm/errorpermisos.jsp");
		}
	}
	
	// Consulta los permisos adicionales del usuario
	boolean permiso_modificar = General_BLL.tienePermiso(sesion, Funciones.MODIFICAR_USUARIO);
	boolean permiso_bloquear = General_BLL.tienePermiso(sesion, Funciones.CAMBIAR_ESTADO_USUARIO);
	boolean permiso_contrasenia = General_BLL.tienePermiso(sesion, Funciones.RESTABLECER_CONTRASENIA_USUARIO);
	boolean permiso_captura_foto = General_BLL.tienePermiso(sesion, Funciones.CAPTURAR_FOTOGRAFIA_USUARIO);
	boolean permiso_eliminar = General_BLL.tienePermiso(sesion, Funciones.ELIMINAR_USUARIO);
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
		<link href="/lomas-crm/css/dactilar.css" rel="stylesheet">
		<!-- ESTILOS PERSONALIZADOS-->
		<style type="text/css">
			.clasetest{width: 54px; }
			.centrado{text-align:center;}
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
								<h1>Usuarios<small></small>
								</h1>
								<ol class="breadcrumb">
									<li><i class="fa fa-dashboard"></i><a href="/lomas-crm/index.jsp"> Inicio</a>
									</li>
									<li class="active">Listar usuarios</li>
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
										<div class="row">
											<div class="col-sm-6">
												<form role="form">
			                                    	<div class="input-group">
					                                    <input type="text" class="form-control" id="txtFiltro" placeholder="Busque por código, nombre, estado o rol" maxlength="50">
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
											<!-- DECLARACIÓN DE COLUMNAS DE TABLA -->
											<thead>
												<tr>
													<th style="width: 54px;"></th>
													<th style="text-align: center;">Código</th>
													<th style="text-align: center;">Usuario</th>
													<th style="text-align: center;">Nombres</th>
													<th style="text-align: center;">Apellidos</th>
													<th style="text-align: center;">Rol</th>
													<th style="text-align: center;">Estado</th>
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
	    
	    
	    <div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">Modificar Usuario</h4>
            </div>
            <div class="modal-body">
                <form role="form">
										<div class="form-group">
											<label for="lblRol">Rol</label>
											<select id="slcRol" class="form-control">
                                             </select>
										</div>
										<div class="form-group">
											<label for="lblLogin">Usuario</label>
											<input type="text" class="form-control" id="txtLogin" placeholder="Usuario" maxlength="20" readonly>
										</div>
										<div class="form-group">
											<label for="lblNombres">Nombres</label>
											<input type="text" class="form-control" id="txtNombres" placeholder="Nombres" maxlength="50">
										</div>
										<div class="form-group">
											<label for="lblApellidos">Apellidos</label>
											<input type="text" class="form-control" id="txtApellidos" placeholder="Apellidos" maxlength="50">
										</div>
										<div class="form-group">
											<label for="lblEmail">Email</label>
											<input type="text" class="form-control" id="txtEmail" placeholder="Email" maxlength="100">
										</div>
										<div class="form-group">
											<label for="lblDescripcion">Descripción</label>
											<textarea class="form-control" id="txtDescripcion" placeholder="Descripción" maxlength="200"></textarea>
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
		<!-- DECLARACIÓN DE FUNCIONES JAVASCRIPT -->
		<script type="text/javascript">
		var appletCargado=false;
		var veces=4;
		var fila;
		var lectorConectado=false;
		var dedo=-1;
		//Se oculta la barra de progreso
		$(".progress").fadeOut();
		
		function leerDispositivo(){
			//Reintentar
			var applet=document.getElementById("applet");
			applet.detectarLectores();
		}
		function sinDispositivo(){
			var mensajito=Messenger().post({
				  message: "No hay ningún lector conectado. Conecte uno y presione en Reintentar",
				  type:'error',
				  showCloseButton:true,
				  id:1,
				  hideAfter:36000,
				  actions: {
				  	aceptar: {
				 	label: "Reintentar",
				   	action: function(){
				    	iniciarCaptura();
						mensajito.hide();
				      }
				    },
				   	cancelar: {
					 	label: "Cancelar",
					   	action: function(){
					    mensajito.hide();
						}
					}
					//adicionar botones
				  }
			});
		}
		
		function iniciarCaptura(){
			if(!appletCargado){
		    	$('body').append(htmlApplet);
				appletCargado=true;
			}
			leerDispositivo();
		}
		
		function ocultarModal(){
			$(".dedo").unbind('click');
			$("#basicModal2").modal('hide');
			Messenger().hideAll();
		}
		
		function dispositivoListo(){
			$('#txtBloque').text('Captura en progreso');
			$('#txtBloque').prop('disabled',true);
			
			
			var mensajito=Messenger().post({
				  message: "Haga clic en un dedo para iniciar la captura",
				  type:'info',
				  showCloseButton:false,
				  id:1,
				  hideAfter:36000
				  });
			$(".dedo").click(accionDedos);		
		}
		
		$("#txtBloque").click(iniciarCaptura);
		
		function accionDedos(){
				

				var nombreDedo=$(this).attr('class')+","+$(this).parent().attr('class');
				var dClick=$(this);
				
				var mensajito=Messenger().post({
					  message: "Ha seleccionado el "+nombreDedo+". "+"¿Desea continuar con el registro?",
					  type:'info',
					  showCloseButton:true,
					  id:1,
					  hideAfter:3600,
					  actions: {
					    aceptar: {
					      	label: "Aceptar",
					      	action: function(){  
				    		//No se puede bloquear la operacion
				    	  	$("#equis").prop("disabled", true);
				    	  	$(".progress").fadeIn();
				    	  	$('.progress-bar').css('width','20%');
							dClick.css('background-color','rgba(20, 255, 0, 0.44)');
						   	$("#txtDedo").text(nombreDedo.replace('dedo',''));
						   	dedo=dClick.data('indice');		   
						   	$('#paso1').find('.badge').removeClass('red');
						   	$('#paso1').find('.badge').addClass('green').html('<i class="fa fa-check"></i>');
					
						   	//evitar que se interrumpa el proceso de captura
						   	
						   	var applet=document.getElementById("applet");
						
						
						   	applet.inscribirHuella();
						   	mensajito.hide();
					      }
					    },
					    cancelar: {
						      label: "Cancelar",
						      action: function(){
						        
						    	  mensajito.hide();
						      }
						    }
						//adicionar botones
					  }
					});

		
		}
		
		function restarVeces(){
			if(veces==4){obtenerAdvertencia("info", "Coloque el dedo en el lector",1);}
			else{
				var v="veces";
				if (veces==1) v="vez";
				obtenerAdvertencia("info", "Coloque el mismo dedo "+veces+" "+v+" más",1);
			}
			$('#txtVeces').text(veces);
			
			if(veces!=4){
				$('.progress-bar').css('width',(5-veces)*20+"%");
				
			}
			veces=veces-1;
			if(veces==0){veces=4;}
			
		}
		
		function reiniciarUI(){
			veces=4;
			$('#paso1').find('.badge').removeClass('green').addClass('red').html('&times;');
			$('#paso2').find('.badge').removeClass('green').addClass('red').html('&times;');
			$('#paso3').find('.badge').removeClass('green').addClass('red').html('&times;');
			$('#txtDedo').text('');
			$('#txtVeces').text('4');
			dedo=-1;
			
		}
		
		function registrar(bytes){
			if(bytes.length>16){
				$('.progress-bar').css('width',"100%");
				$('#paso2').find('.badge').removeClass('red').addClass('green').html('<i class="fa fa-check"></i>');
				$('#txtVeces').text(veces);
				datos={};
				datos.op=12;
				datos.dedo=dedo;
				datos.usuario=fila.data().usuario;
				datos.valor=bytes;
				$.post('/lomas-crm/usuario', datos, callbackGuardado, 'json');
				$("#txtBloque").prop('disabled',true);
				$('#txtBloque').html('Espere <i class="fa fa-spinner fa-spin"></i>');
				function callbackGuardado(respuesta){
					$('[data-indice='+dedo+']').css('background-color','rgba(0, 224, 255, 0.4)');
					
					$('#paso3').find('.badge').removeClass('red');
					$('#paso3').find('.badge').addClass('green');
					$('#paso3').find('.badge').first().html('<i class="fa fa-check"></i>');
					$("#txtBloque").prop('disabled',false);
					$('#paso3').find('badge').html('&times;');
					$('#txtBloque').html('Click para iniciar la captura');
					obtenerAdvertencia('info', 'Cambios guardados',1);
					reiniciarUI();
					$("#equis").prop("disabled", false);
					$(".progress").fadeOut();
					$(".progress-bar").css('width','0%');
					

				}
			}
			else{
				obtenerAdvertencia("error", "No se pudo capturar la huella. Intente de nuevo");
			}
		}

		
		var htmlApplet=
			'<object id="applet" type="application/x-java-applet" height="0px" width="0px" >'+
			'<param name="MAYSCRIPT" value="true">'+
	  		'<param name="code" value="org.CUB.crm_clinicas.Principal"/>'+
	  		'<param name="codebase" value="/lomas-crm/applet/codigo/">'+
	  		'<param name="archive" value="lomas-crm.jar,dpfpenrollment.jar,dpfpverification.jar,dpotapi.jar,dpotjni.jar,commons-codec-1.9.jar">'+
	  		'<param name="separate_jvm" value="true" /> '+
			'Necesita instalar Java'+
			'</object>';
				
		function validarEmail(email) { 
		    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		    return re.test(email);
		} 
		
	
		
		
			// Variables globales
			var table;
			var id;
			var fila;
			
			// CARGA TABLA DE DATOS
			function cargarTabla(){
				table = $('#example').DataTable({
					"ajax": {	"url": "/lomas-crm/usuario",
								"dataType": "json",
				            	"data": function (d){ 
				            							d.filtro = $('#txtFiltro').val(), 
				            							d.op = "2";
				            							d.tipo=1;
				            						},
								"type": "POST"			
					},
					
					
					"preDrawCallback": function( settings ,json) {
						  
						
						  },
					"columns": [
					            {"class": 'clasetest',
								"defaultContent": '<button title="Opciones" style="border-radius:50%;" class="btn btn-white"><b>+</b></button>',
								"visible": <% if(permiso_bloquear  || permiso_contrasenia || permiso_modificar || permiso_eliminar || permiso_captura_foto) { out.print("true"); } else { out.print("false"); }; %>},
								{"data": "codigo", "class":"centrado"},
								{"data": "usuario"},
								{"data": "nombres"},
								{"data": "apellidos"},
								{"data": "rol"},
								{"data": "estado"}],
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
					},
					"initComplete": function (settings,json) {
						if (json.resultado == "-100") {
							// Sesión caducó
							$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});

						} 

						$("#btn-buscar").html('Buscar&nbsp;&nbsp;<i class="fa fa-search">');
						$("#btn-buscar").prop("disabled", false);
						$("#example").css("display","block");
					},
					
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
				// Este es el menú de opciones de cada tupla
				var nombre_boton;
			
				if ($(d.estado).attr('title') == 'El usuario está bloqueado') {
					nombre_boton = 'Desbloquear';
				} else {
					nombre_boton = 'Bloquear';
				}
				
				// Cadena de opciones
				<% if(permiso_bloquear || permiso_contrasenia || permiso_modificar || permiso_eliminar || permiso_captura_foto ) { 
						out.println("return '<table class=\"tablita\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" style=\"padding-left:50px;\">\' + ");
						out.println("	\'<tr>\' + ");
						out.println("		\'<td>Opciones</td>\' + ");
						out.println("	\'</tr>\' + ");
						out.println("	\'<tr>\' + ");
						if(permiso_modificar) out.println("		'<td><a data-toggle=\"modal\" data-target=\"#basicModal\"  href=\"#\" onclick=\" cargarUIModificar()\"><button class=\"btn btn-default\">Modificar</button></a></td>\' + ");
						if(permiso_captura_foto) out.println("		'<td><a href=\"/lomas-crm/usuario/capturarImagen.jsp?usuario=' + d.codigo + '&imagen='+ d.imagen + '\"><button class=\"btn btn-purple\" >Capturar fotografía</button></a></td>\' + ");						
						if(permiso_contrasenia) out.println("		'<td><a href=\"javascript:operacion(\\'' + d.usuario + '\\',' + d.codigo + ',4)\"><button class=\"btn btn-info\" >Restablecer contraseña</button></a></td>' + ");
						if(permiso_bloquear) out.println("		'<td><a href=\"javascript:operacion(\\'' + d.usuario + '\\',' + d.codigo + ',3)\"><button class=\"btn btn-orange btn-bloquear\" >' + nombre_boton + '</button></a></td>' + ");
						if(permiso_eliminar) out.println("		'<td><a href=\"javascript:operacion(\\'' + d.usuario + '\\',' + d.codigo + ',5)\",><button class=\"btn btn-red\" >Eliminar</button></a></td>' + ");
						out.println("	\'</tr>\' + ");
						out.println("\'</table>\';"); 
					} else {
						out.print("return '';"); 
					}; 
				%>
			}
			
			// OPERACIONES DE LOS BOTONES
			function operacion(nombre, id, op) {
				// Mensaje
				var r;
				
				switch(op) {
				case 3:
					var mensajito=Messenger().post({
						message: "¿Está seguro que desea cambiar el estado del usuario : " + nombre+"?",
					  	type:'info',
					  	showCloseButton:true,
					  	id:1,
					  	hideAfter:36000,
					  	actions: {
					  		aceptar: {
					 			label: "Aceptar",
					   			action: function(){ejecutarComando(3);mensajito.hide();}
					    	},
					   		cancelar: {
						 		label: "Cancelar",
						   		action: function(){mensajito.hide();}
							}
					  }
					});
					
					
				    break;
				case 4:
					
					var mensajito=Messenger().post({
						message:"¿Está seguro que desea restablecer la contraseña del usuario : " + nombre+"?",
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
					
					break;
				case 5:
					var mensajito=Messenger().post({
						message:"¿Está seguro que desea eliminar el usuario : " + nombre+"?",
					  	type:'info',
					  	showCloseButton:true,
					  	id:1,
					  	hideAfter:36000,
					  	actions: {
					  		aceptar: {
					 			label: "Aceptar",
					   			action: function(){ejecutarComando(5);mensajito.hide();table.ajax.reload( null, false );}
					    	},
					   		cancelar: {
						 		label: "Cancelar",
						   		action: function(){mensajito.hide();}
							}
					  }
					});
					break;
				default:
				    // No definido
				}
				
				// Se evalúa la respuesta
				function ejecutarComando(operacion){
			
						var datos = {
							"id": id,
							"op" : op
						};

						$("#estado").html("<center><b>Procesando <i class='fa fa-spinner fa-spin'></i></center><br>");
						$.post('/lomas-crm/usuario', datos, callback, 'json');

						function callback(respuesta) {
							// Refrescar la tabla
							//cargarTabla();
							
							// Mostrar el resultado
							if (respuesta.resultado==1) {
								obtenerAdvertencia('info', respuesta.descripcion);
								if(operacion==3){
									
									if ($(fila.data().estado).attr('title') == 'El usuario está bloqueado') {
								
										$('.btn-bloquear').html('Bloquear');
										var d=fila.data();
										d.estado='<i style="color:green;" title="El usuario está desbloqueado" class="fa fa-unlock"></i>';
										 fila
									        .data(d)
									        .draw();
									} else {
										$('.btn-bloquear').html('Desbloquear');
										var d=fila.data();
										d.estado='<i style="color:red;" title="El usuario está bloqueado" class="fa fa-lock"></i>';
										 fila
									        .data(d)
									        .draw();
									}
									
								}
								
								if(operacion==5){

									fila.remove().draw();
								}
								
									
								
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
							
							$("#estado").html("");
						}
					
					
				}
			}

			
			
			function cargarUIModificar(){
				//Deshabilitar el boton y mostrar animacion de cargado
				$("#btn-guardar").html(' Espere <i class="fa fa-spinner fa-spin"></i>');
				$("#btn-guardar").prop("disabled", true);
				$('input').val('');
				$('#slcRol').empty();
				$('#txtDescripcion').val('');

				// Consulta la información enviando el id a la consulta
				var datos = {
					"id": id,
					"op": "6"
				};
				$.post('/lomas-crm/usuario', datos, callback2, 'json');

				function callback2(respuesta) {

					if(respuesta.data)
					{
					$("#txtLogin").val(respuesta.data[0].usuario);
					$("#txtNombres").val(respuesta.data[0].nombres);
					$("#txtApellidos").val(respuesta.data[0].apellidos);
					$("#txtEmail").val(respuesta.data[0].email);
					$("#txtDescripcion").val(respuesta.data[0].descripcion);
					
					// Llenar el dropdown con los roles del sistema
					var datos_rol = {op: "2"};
					$.post('/lomas-crm/rol', datos_rol, callback2, 'json');
		
					function callback2(respuesta_rol) {
						var nueva_opcion;
						$.each(respuesta_rol.data, function(i, rol) {
							if (rol.id == respuesta.data[0].rol_codigo) {
								nueva_opcion = $('<option value="'+ rol.id + '" selected>' + rol.nombre + '</option>');	
							} else {
								nueva_opcion = $('<option value="'+ rol.id + '">' + rol.nombre + '</option>');
							}
							
						    $('#slcRol').append(nueva_opcion);
						});
						$("#btn-guardar").html('Guardar');
						$("#btn-guardar").prop("disabled", false);
					}					
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
			
			// Validación de botón guardar
			$('#btn-guardar').click(function (e) {
	
				//Validación de campos
				if ($('#slcRol').val() == null) {
					obtenerAdvertencia('error', 'Debe seleccionar un rol válido');
				} else {
					if ($('#txtNombres').val().trim() == "" || $('#txtApellidos').val().trim() == "") {
						obtenerAdvertencia('error', 'Ingrese por lo menos un nombre y un apellido del usuario');
					} else {
						//Campos validados exitosamente
						
	
						//Se crea un objeto para ser enviado al servidor 
						var datos = {
							id: id,
							rol: $('#slcRol').val().trim(),
							nombres: $('#txtNombres').val().trim(),
							apellidos: $('#txtApellidos').val().trim(),
							email: $('#txtEmail').val().trim(),
							descripcion: $('#txtDescripcion').val().trim(),
							op: "7"
						};
						
						if(!(validarEmail(datos.email)||datos.email.trim()=='')){
							obtenerAdvertencia('error', "La dirección de correo electrónico es inválida");
						}else{
							
							if((/^([a-z ñáéíóú]{2,60})$/i.test(datos.nombre+' '+datos.apellidos))){
									$("#btn-guardar").html(' Espere <i class="fa fa-spinner fa-spin"></i>');
								$("#btn-guardar").prop("disabled", true);
								$.post('/lomas-crm/usuario', datos, callback, 'json');
								
							}else{
								
								obtenerAdvertencia('error', "Nombre inválido. Sólo se permiten letras.");
							}
							
						}
							
						
	
						function callback(respuesta) {
							//Se obtiene la respuesta del servidor y se muestra la pagina
							$("#btn-guardar").prop("disabled", false);
							$("#btn-guardar").html('Guardar');
							if (respuesta.resultado == "1") {
								advertenciaHTML = obtenerAdvertencia('info', respuesta.descripcion);
								
								var d= fila.data();
		
							    d.rol=$('#slcRol option:selected').text();
							    d.nombres=$('#txtNombres').val();
							    d.apellidos=$('#txtApellidos').val();
							    table
							        .row(fila)
							        .data(d)
							        .draw(false);
								
								
								
							} else {
								if (respuesta.resultado == "-100") {
									// Sesión caducó
									if (respuesta.resultado == "-100") {
												// Sesión caducó
												$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});

											} else if (respuesta.resultado == "-101") {
												// No tiene permisos
												window.location.href = "/lomas-crm/errorpermisos.jsp";
											}else{
												obtenerAdvertencia('error', respuesta.descripcion);
											}}
							}
					
						}
					}
				}
				e.preventDefault();
			});
			
			
			function obtenerAdvertencia(clase, mensaje,id) {
				Messenger().post({
					message : mensaje,
					type : clase,
					id:id,
					showCloseButton : true
				});
			}
			
			
		//Inicializar Messenger
		Messenger.options = {
			extraClasses : 'messenger-fixed messenger-on-top',
			theme : 'future'
		};	
			
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
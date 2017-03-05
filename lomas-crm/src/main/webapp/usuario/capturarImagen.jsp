<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="crm_BLL.General_BLL"%>
<%@page import="crm_BE.Sesion_BE"%>
<%@page import="crm_BE.Funciones"%>

<%
	//Valida la sesion
	Sesion_BE sesion = new Sesion_BE();
	String id = request.getParameter("usuario");
	//Verificar que el envien los parametros necesarios
	if (id != null) {

		if (session.getAttribute("sesion") == null) {
			session.invalidate();
			response.sendRedirect("../sesionExpirada.jsp");
		} else {
			sesion = (Sesion_BE) session.getAttribute("sesion");
			if (!General_BLL.tienePermiso(sesion,
					Funciones.CAPTURAR_FOTOGRAFIA_USUARIO)) {
				response.sendRedirect("../errorpermisos.jsp");
			}
		}
	} else {
		//Si no se envian los prametros necesarios se redirige	
		response.sendRedirect("errorGeneral.jsp");
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
    <link href="../css/style.css" rel="stylesheet">
    <link href="../css/plugins.css" rel="stylesheet">
	<link href="../css/plugins/messenger/messenger.css" rel="stylesheet"> 	
	<link href="../css/plugins/messenger/messenger-theme-future.css" rel="stylesheet">
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
                <ul class="nav navbar-left">
                    <li class="tooltip-sidebar-toggle">
                        <a href="#" id="sidebar-toggle" data-toggle="tooltip" data-placement="right" title="" data-original-title="Barra lateral">
                            <i class="fa fa-bars"></i>
                        </a>
                    </li>
                </ul>
               <ul class="nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
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
        <nav class="navbar-side" role="navigation">
            <div class="navbar-collapse sidebar-collapse collapse">
                <ul id="side" class="nav navbar-nav side-nav">
                    <!-- begin SIDE NAV USER PANEL -->
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
						<div class="clearfix"></div></li>
        				<%= sesion.se_menu %>
                </ul>
                <!-- /.side-nav -->
            </div>
        </nav>
        <div id="page-wrapper" class="">
            <div class="page-content page-content-ease-in">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="page-title">
                            <h1>
                                Capturar Imagen
                                <small></small>
                            </h1>
                            <ol class="breadcrumb">
                                <li><i class="fa fa-dashboard"></i>  <a href="../index.jsp">Inicio</a>
                                </li>
                                <li ><a href="listarUsuario.jsp">Listar Usuarios</a></li>
                                <li class="active">Capturar imagen</li>
                            </ol>
                        </div>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <!-- end PAGE TITLE ROW -->
				<div class="row">

					<div class="col-lg-6" style="float: none; margin: 0 auto;">
						<div class="portlet portlet-gray" style="width: 650px;">
							<div class="portlet-heading">
								<div class="portlet-title">
									<h4></h4>
								</div>
						
								<div class="clearfix"></div>
							</div>
							<!-- POSICIÓN DEL FORMULARIO -->


							<div id="basicFormExample" class="panel-collapse in"
								style="height: auto;">
								<div class="portlet-body" style="text-align: center;">


									<form role="form">
										<div class="row">
										<table style="margin: 0 auto; height: 240px">
											<tr>
												<td>
													<video style="display:none; float: right; width:320px;height: 240px;" id="camara" autoplay></video>
													<div id="camarita" style="background-image:url('../imagenes?t=1&f=camara.png'); float: right; width:320px;height: 240px;" ></div>
												</td>
												<td>
													<canvas style="display:none;float: right; width:320px;height: 240px;"  id="foto"></canvas>
													<img id="preImagen">
												</td>
											</tr>
										</table>
											
											
										</div>
										<div id='botonera'>
											<button id='botonIniciar' type='button'
												class="btn btn-primary">
												Iniciar &nbsp;<span class="glyphicon glyphicon-play"></span>
											</button>
											<button id='botonDetener' disabled class="btn btn-danger">
												Detener &nbsp;<span class="glyphicon glyphicon-stop"></span>
											</button>
											<button id='botonFoto' disabled class="btn btn-success">
												Capturar&nbsp; <span class="glyphicon glyphicon-camera"></span>
											</button>
											<button id='btn-guardar' disabled class="btn btn-primary">
												Guardar&nbsp; <span class="glyphicon glyphicon-floppy-save"></span>
											</button>
										</div>



									</form>
								</div>
							</div>



						</div>
					</div>







				</div>

			</div>
            <!-- /.page-content -->

        </div>
        <!-- /#page-wrapper -->
        <!-- end MAIN PAGE CONTENT -->

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
                    <a href="../salir" class="btn btn-green">
                        <strong>Salir</strong>
                    </a>
                </li>
                <li>
                    <button class="logout_close btn btn-green">Cancelar</button>
                </li>
            </ul>
        </div>
    </div>
    <!-- GLOBAL SCRIPTS -->
    <script src="../js/jquery-1.11.0.min.js"></script>
    <script src="../js/plugins/bootstrap/bootstrap.min.js"></script>
    <script src="../js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="../js/plugins/popupoverlay/jquery.popupoverlay.js"></script>
    <script src="../js/plugins/popupoverlay/defaults.js"></script>
    <script type="text/javascript" src="../js/jquery-ui-1.10.4.min.js"></script>
    
<script src="../js/plugins/messenger/messenger.min.js"></script>
<script src="../js/plugins/messenger/messenger-theme-future.js"></script>
    
    <!-- Logout Notification Box -->
    
    <!-- /#logout -->
    <!-- Logout Notification jQuery -->
    <script src="../js/plugins/popupoverlay/logout.js"></script>
    <!-- HISRC Retina Images -->

    <!--script src="../js/plugins/hisrc/hisrc.js"></script-->

    <!-- PAGE LEVEL PLUGIN SCRIPTS -->

    <!-- THEME SCRIPTS -->
    <script src="../js/flex.js"></script>
	var img;
    <!--Llamada asíncrona al servidor-->
    <script type="text/javascript">
    
    $(function() {
		Messenger.options = {
			extraClasses : 'messenger-fixed messenger-on-top',
			theme : 'future'
		};
	});
    
    
 		
		//Llenar los campitos en el div lbl-advertencia
		var id =<%out.print(id);%>;
    $(document).ready(function()
   	{
    	
    
  		$('#preImagen').attr('src','../imagenes?t=1&f=<%out.print(request.getParameter("imagen")); %>');
    	
  		obtenerAdvertencia('info', 'Haga clic en iniciar para encender la cámara web');
    	
    	window.URL = window.URL || window.webkitURL;
    	navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia ||
    	function() {
    	    alert('Su navegador no soporta navigator.getUserMedia().');
    	};

    	//Este objeto guardará algunos datos sobre la cámara
    	window.datosVideo = {
    	    'StreamVideo': null,
    	    'url': null
    	};

    	$('#botonIniciar').on('click', function(e) {
    		$('#botonIniciar').prop("disabled",true);
    		
    	    //Pedimos al navegador que nos da acceso a 
    	    //algún dispositivo de video (la webcam)
    	    e.preventDefault();
    	    navigator.getUserMedia({
    	        'audio': false,
    	        'video': true
    	    }, function(streamVideo) {
    	        datosVideo.StreamVideo = streamVideo;
    	        datosVideo.url = window.URL.createObjectURL(streamVideo);
    	        $('#camara').attr('src', datosVideo.url);
    	        //$('#camara').css('width','50%');
    	      	$('#camara').css('display','inherit');
    	      	$('#camarita').css('display','none');
    	      	
    	        $('#botonDetener').prop("disabled",false);
    	        $('#botonFoto').prop("disabled",false);
    	        
    	       
    	        
    	    }, function() {
    	        alert('No fue posible obtener acceso a la cámara.');
    	    });

    	});

    	$('#botonDetener').on('click', function(e) {
    		e.preventDefault();
    	    if (datosVideo.StreamVideo) {
    	        datosVideo.StreamVideo.stop();
    	        window.URL.revokeObjectURL(datosVideo.url);
    	        $('#botonDetener').prop("disabled",true);
    	        $('#botonIniciar').prop("disabled",false);
    	    }

    	});

    	$('#botonFoto').on('click', function(e) {
    		e.preventDefault();

	        $('#btn-guardar').prop("disabled",false);
    	    var oCamara, oFoto, oContexto, w, h;

    	    oCamara = $('#camara');
    	    oFoto = $('#foto');
    	    w = oCamara.width();
    	    h = oCamara.height();
    	    oFoto.attr({
    	        'width': w,
    	        'height': h
    	    });

    	     //$('#foto').css('width','28vw');
    	    // $('#camara').css('width','300px');
    
    	    oContexto = oFoto[0].getContext('2d');
    	    oContexto.drawImage(oCamara[0], 0, 0, w, h);
    	    
    	    $('#preImagen').css('display','none');
	        $('#foto').css('display','inherit');

    	});
    	
    	
    	
    	
    }
    );

	$('#btn-guardar').click(function(e){
		advertenciaHTML = '';
		
	//Evitar que presionen el boton 2 veces
	$("#btn-guardar").html(' Espere <i class="fa fa-spinner fa-spin"></i>');
	$("#btn-guardar").prop("disabled",true);
	
	//Se crea un objeto para ser enviado al servidor 
	var can = document.getElementById("foto");
	var datos = {
		archivo : can.toDataURL("image/png"),
		usuario : id,
		operacion:1
	};
	$.post('../guardararchivo',datos, callbackGuardarArchivo,'json');
	function callbackGuardarArchivo(respuesta) {
	//Se obtiene la respuesta del servidor de archivos
	console.log(respuesta);
		if (respuesta.resultado == "1") {
			
			var foto = respuesta.foto;
			var datos2 = {"foto": foto,"id" : id,op:8};
			$.post('../usuario',datos2, callbackActualizarImagen,'json');
			function callbackActualizarImagen(respuesta2) {
				
				$("#btn-guardar").prop("disabled",false);
				$("#btn-guardar").html('Guardar');
				if (respuesta2.resultado==1){
					advertenciaHTML = obtenerAdvertencia('info',respuesta2.descripcion);
					
					
				}else
				
					{
														if (respuesta.resultado == "-100") {
															// Sesión caducó
															$('#sesionCaducada')
																	.modal(
																			{
																				"backdrop" : "static",
																				"keyboard" : false
																			});

														} else if (respuesta.resultado == "-101") {
															// No tiene permisos
															window.location.href = "../errorpermisos.jsp";
														} else {
															obtenerAdvertencia(
																	'error',
																	respuesta.descripcion);
														}
													}
												}
											}
										}

										e.preventDefault();
									});

					function obtenerAdvertencia(clase, mensaje) {
						Messenger().post({
							message : mensaje,
							type : clase,
							showCloseButton : true
						});

					}
				</script>

<!--Finaliza la llamada asíncrona al servidor-->

<!-- Mirrored from themes.startbootstrap.com/flex-admin-v1.2/blank.html by HTTrack Website Copier/3.x [XR&CO'2013], Mon, 14 Apr 2014 05:33:19 GMT -->


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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<title></title>
		<link href="/lomas-crm/css/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="/lomas-crm/icons/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="/lomas-crm/css/style.css" rel="stylesheet">
		<link href="/lomas-crm/css/plugins.css" rel="stylesheet">
		<link href="/lomas-crm/css/demo.css" rel="stylesheet">
		<style type="text/css">
		.fondo-login{
		/*background-image: url('/lomas-crm/img/widget-bg-afternoon.jpg');
		background-position: center;  
		background-size: cover;  
	    -webkit-filter: blur(3px);
	    position: absolute;
	    width: 100%;
	    height: 100%;*/
		}
		</style>
	</head>
	<body class="login">
	<div class="fondo-login" >

  	</div>
		<div class="container">
			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<div class="login-banner text-center">
						<div style="margin: 0 auto; width: 220px;">
							<div style="margin-left: -33px; margin-top: 0px;"><img src="/lomas-crm/img/logo_home.png" style="width:293px;"></div>
							<!-- <div style="width: 222px; /* position: relative; */ padding: -25px; margin-left: -1px;">
								<h1> CUB</h1>
							</div>-->
						</div>
					</div>
					<div class="portlet portlet-blue" style="margin-top: -20px;">
						<div class="portlet-heading login-heading">
							<div class="portlet-title" style="">
								<h4><strong style="">¡Bienvenido!</strong></h4>
							</div>
							<div class="clearfix"></div>
						</div>
						<div class="portlet-body">
							<form accept-charset="UTF-8">
								<fieldset>
									<div class="form-group">
										<input class="form-control" placeholder="Usuario" id="txtUsuario" type="text">
									</div>
									<div class="form-group">
										<input class="form-control" placeholder="Contraseña" id="txtContrasenia" type="password" value="">
									</div>
									<br>
									<button type="submit" id="btnIngresar" class="btn btn-lg btn-blue btn-block">Ingresar</button>
									<br>
									<div  id="lblAdvertencia" style="text-align: center;"></div>
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- SCRIPTS GLOBALES -->
		<script src="/lomas-crm/js/jquery-1.11.0.min.js"></script>
		<script src="/lomas-crm/js/plugins/bootstrap/bootstrap.min.js"></script>
		<!-- DECLARACIÓN DE FUNCIONES JAVASCRIPT -->
		<script type="text/javascript">
			$('#btnIngresar').click(
				function (e) {
					$('#lblAdvertencia').html('');
					advertenciaHTML = '';
					//ValidaciÃ³n de campos
					if ($('#txtUsuario').val().trim().length == 0) {
						advertenciaHTML += obtenerAdvertencia(' alert-danger', 'Ingrese un usuario válido');
					} else {
						if ($('#txtContrasenia').val().trim().length == 0) {
							advertenciaHTML += obtenerAdvertencia(' alert-danger', 'Debe ingresar una contraseña');
						} else {
							//Campos validados exitosamente, se procede a llamar al servidor
							$("#btnIngresar").html(' Espere <i class="fa fa-spinner fa-spin"></i>');
							$("#btnIngresar").prop("disabled", true);
							var datos = {
								usuario: $('#txtUsuario').val(),
								contrasenia: $('#txtContrasenia').val()
								, prevurl:'<%= request.getParameter("prevurl") %>'
							};

							function callback(respuesta) {

								//Se obtiene la respuesta del servidor y se muestra la pagina
								if (respuesta.resultado == "1") {
									window.location.href = "/lomas-crm/index.jsp";

								}else if(respuesta.resultado=="2"){
									window.location.href = respuesta.urlRetorno;
								}else {
									advertenciaHTML = obtenerAdvertencia(' alert-danger ', respuesta.descripcion);
									$("#btnIngresar").prop("disabled", false);
									$("#btnIngresar").html('Ingresar');
								}
								$('#lblAdvertencia').html(advertenciaHTML);

							}
							$.post('/lomas-crm/ingreso', datos, callback, 'json');
						}
					}
					$('#lblAdvertencia').html(advertenciaHTML);

					e.preventDefault();
				}
			);

			function obtenerAdvertencia(clase, mensaje) {
				return '<div class="alert ' + clase + '">' + mensaje + '</div>';
			}
		</script>
	</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
   // New location to be redirected
   String site = new String("ingreso.jsp");
   response.setStatus(response.SC_MOVED_TEMPORARILY);
   response.setHeader("Location", site); 
%>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>Sistema de Gestión- CUB</title>
		<link href="css/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="icons/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="css/style.css" rel="stylesheet">
		<link href="css/plugins.css" rel="stylesheet">
		<!-- ESTILOS PERSONALIZADOS-->
		<style type="text/css">
		</style>
	</head>
	<body style="background: #ECF0F1;">
		<div class="page-content page-content-ease-in">		
			<div class="row">
               	<div class="col-lg-6 col-lg-offset-4">
                   	<h1 class="error-title">Error 2</h1>
                   	<h4 class="error-msg"><i class="fa fa-warning text-red"></i> Su sesión ha expirado</h4>
                   	<p class="lead">Se ha superado el tiempo máximo de inactividad.</p>
                   	<ul class="list-inline">
                       	<li>
                           	<a class="btn btn-default" href="ingreso.jsp">Volver a la página de ingreso</a>
                       	</li>
                   	</ul>
               	</div>
             </div>
		</div>
		<!-- SCRIPTS GLOBALES -->
		<script src="js/jquery-1.11.0.min.js"></script>
		<script src="js/plugins/bootstrap/bootstrap.min.js"></script>
		<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script src="js/plugins/popupoverlay/jquery.popupoverlay.js"></script>
		<script src="js/plugins/popupoverlay/defaults.js"></script>
		<script src="js/plugins/popupoverlay/logout.js"></script>
		<script src="js/flex.js"></script>
		<!-- DECLARACIÓN DE FUNCIONES JAVASCRIPT -->
		
	</body>
</html>
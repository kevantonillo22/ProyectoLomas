<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="crm_BLL.General_BLL"%>
<%@page import="crm_BE.Sesion_BE"%>
<%
	//Valida la sesion
	Sesion_BE sesion = new Sesion_BE();
	if(session.getAttribute("sesion") == null){
		session.invalidate();
		response.sendRedirect("sesionExpirada.jsp");
	}
	else
	{
		sesion = (Sesion_BE) session.getAttribute("sesion");
	}
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
	<body>
		<div id="wrapper">
			<nav class="navbar-top" role="navigation">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle pull-right" data-toggle="collapse" data-target=".sidebar-collapse">
					<i class="fa fa-bars"></i> Menú
					</button>
					<div class="navbar-brand">
						<a href="index.jsp">
						<img src="img/logo_home.png" class="img-responsive" alt="">
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
				<div class="page-content ">
					<!-- TITULO DE LA PÁGINA -->
					<div class="row">
						<div class="col-lg-12">
							<div class="page-title">
								<h1>
									Sistema de Gestión<small></small>
								</h1>
								<ol class="breadcrumb">
									<li><i class="fa fa-dashboard"></i> <a href="index.jsp">Inicio</a>
									</li>
								</ol>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-12">
							<div class="tile tile-img tile-time afternoon"
								style="height: 200px">
								<p class="time-widget">
									<span class="time-widget-heading">Actualmente es</span> <br>
									<strong> <span id="datetime"></span>
									</strong>
								</p>
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
	                    <a href="salir" class="btn btn-green">
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
		<script src="js/jquery-1.11.0.min.js"></script>
		<script src="js/plugins/bootstrap/bootstrap.min.js"></script>
		<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script src="js/plugins/popupoverlay/jquery.popupoverlay.js"></script>
		<script src="js/plugins/popupoverlay/defaults.js"></script>
		<script src="js/plugins/popupoverlay/logout.js"></script>
		<script src="js/flex.js"></script>
		<script src="js/plugins/moment/moment.min.js"></script>
		<!-- DECLARACIÓN DE FUNCIONES JAVASCRIPT -->
		<script type="text/javascript">
			var datetime = null;
			moment
				.lang(
					'es', {
						months: "enero_febrero_marzo_abril_mayo_junio_julio_agosto_septiembre_octubre_noviembre_diciembre"
							.split("_"),
						monthsShort: "ene._feb._mar_abr._may_jun_jul._ago_sep._oct._nov._dic."
							.split("_"),
						weekdays: "domingo_lunes_martes_miércoles_jueves_viernes_sábado"
							.split("_"),
						weekdaysShort: "dom._lun._mar._mie._jue._vie._sab."
							.split("_"),
						weekdaysMin: "Do_Lu_Ma_Mi_Ju_Vi_Sa"
							.split("_"),
						longDateFormat: {
							LT: "HH:mm",
							L: "DD/MM/YYYY",
							LL: "D MMMM YYYY",
							LLL: "D MMMM YYYY LT",
							LLLL: "dddd D MMMM YYYY LT"
						},
						calendar: {
							sameDay: "[Aujourd'hui à] LT",
							nextDay: '[Demain à] LT',
							nextWeek: 'dddd [à] LT',
							lastDay: '[Hier à] LT',
							lastWeek: 'dddd [dernier à] LT',
							sameElse: 'L'
						},
						relativeTime: {
							future: "en %s",
							past: "il y a %s",
							s: "pocos segundos",
							m: "un minuto",
							mm: "%d minutos",
							h: "una hora",
							hh: "%d horas",
							d: "un día",
							dd: "%d días",
							M: "un mes",
							MM: "%d meses",
							y: "une año",
							yy: "%d años"
						},
						ordinal: function (number) {
							return number + (number === 1 ? 'er' : ' de');
						},
						week: {
							dow: 1, // Monday is the first day of the week.
							doy: 4
							// The week that contains Jan 4th is the first week of the year.
						}
					});
			moment.lang("es");
			date = null;

			var update = function () {
				date = moment(new Date());

				datetime.html(date
					.format('dddd<br>Do MMMM, YYYY<br>h:mm:ss A'));
			};

			$(document).ready(function () {
				datetime = $('#datetime');
				update();
				setInterval(update, 1000);
			});
		</script>
	</body>
</html>
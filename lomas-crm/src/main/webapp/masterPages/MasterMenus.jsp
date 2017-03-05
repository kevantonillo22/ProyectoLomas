<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@page import="crm_BE.Sesion_BE"%>
<%
	//Valida la sesion
	Sesion_BE objeto_sesion = new Sesion_BE();
	HttpSession sessionHTTP = request.getSession(false);
	if(sessionHTTP!=null && request.isRequestedSessionIdValid() )
	{
		objeto_sesion = (Sesion_BE) session.getAttribute("sesion");
	}
%>
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
				<img class="img-circle" alt="" style="background: whitesmoke; background-image: url('<%=objeto_sesion.se_ruta_foto%>'); width: 150px; height: 150px; background-size: cover; background-repeat: no-repeat; background-position: center center;">
				<p class="welcome">
					<i class="fa fa-key"></i> Inició sesión como
				</p>
				<p class="name tooltip-sidebar-logout"><%=objeto_sesion.se_nombres%>
				<span class="last-name"><%=objeto_sesion.se_apellidos%></span> <a
						style="color: inherit" class="logout_open" href="#logout"
						data-toggle="tooltip" data-placement="top" title=""
						data-popup-ordinal="1" id="open_61613468"
						data-original-title="Salir"><i class="fa fa-sign-out"></i></a>
				</p>
				<div class="clearfix"></div>
			</li>
			<%= objeto_sesion.se_menu %>
		</ul>
	</div>
</nav>
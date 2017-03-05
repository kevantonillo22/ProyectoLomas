<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="crm_BLL.General_BLL"%>
<%@page import="crm_BE.Sesion_BE"%>
<%@page import="crm_BE.Funciones"%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.sql.Time"%>
<%@page import="java.text.DecimalFormat"%>

<%
	//Valida la sesion
	Sesion_BE sesion = new Sesion_BE();
	int rol=-9999;
	if(session.getAttribute("sesion") == null){
		session.invalidate();
		response.sendRedirect("../sesionExpirada.jsp");
	}
	else
	{
		sesion = (Sesion_BE) session.getAttribute("sesion");
		if(!(General_BLL.tienePermiso(sesion,Funciones.LISTAR_CHEQUES))){
			response.sendRedirect("../errorpermisos.jsp");
		}
	}
	
	String id = request.getParameter("id");
	String numero = request.getParameter("numero");
	String lugar		 = request.getParameter("lugar");
	String fecha		 = request.getParameter("fecha");
	String nombre		 = request.getParameter("nombre");
	String cantidad		 = request.getParameter("cantidad");
	//cantidad = String.format("%.2f", Float.parseFloat(cantidad)).replace(',', '.');
	DecimalFormat formatter = new DecimalFormat("####.00");
	cantidad = formatter.format(Float.parseFloat(cantidad)).replace(',', '.');
	String motivo		 = request.getParameter("motivo");
	String imagen		 = request.getParameter("imagen");
	
	imagen = "../imagenes?t=2&f=" + imagen;
	
	DateFormat sdf2 = null;
	Date dteFecha2 = null;
	String nombre_dia = "";
	String dia = "";
	String anio = "";
	String mes = "";
	int m = 0;
	//SI LA FECHA VIENE EN FORMATO YYYY-MM-DD
	if(fecha.contains("/")){
		sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		dteFecha2 = null;
		dteFecha2 = sdf2.parse(fecha.replace("-", "/") + " 00:00:00");
		
		dia = dteFecha2.getDate() + "";
		m = Integer.parseInt((dteFecha2.getMonth()+1) + "");
		anio = (dteFecha2.getYear() + 1900)+ "";
		
		//formatting day of week in EEEE format like Sunday, Monday etc.
		String strDateFormat = "EEEE";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		nombre_dia = sdf.format(dteFecha2);
		
	}else{
		sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dteFecha2 = null;
		dteFecha2 = sdf2.parse(fecha.replace("-", "/") + " 00:00:00");
		
		dia = dteFecha2.getDate() + "";
		m = Integer.parseInt((dteFecha2.getMonth()+1) + "");
		anio = (dteFecha2.getYear() + 1900)+ "";
		
		//formatting day of week in EEEE format like Sunday, Monday etc.
		String strDateFormat = "EEEE";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		nombre_dia = sdf.format(dteFecha2);
	}
	
	
	//obtener mecha hoy
	DateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date dteFecha3 = null;
	dteFecha3 = sdf3.parse(General_BLL.obtenerFechaHora());
	
	String dia_actual = dteFecha3.getDate() + "";
	int m_actual = Integer.parseInt((dteFecha3.getMonth()+1) + "");
	String mes_actual = "";
	String anio_actual = (dteFecha3.getYear() + 1900)+ "";
	
	//formatting day of week in EEEE format like Sunday, Monday etc.
	String strDateFormat = "EEEE";
	SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
	String nombre_dia_actual = sdf.format(dteFecha3);
	
	if(m == 1){mes = "enero";}
	else if(m == 2){mes = "febrero";}
	else if(m == 3){mes = "marzo";}
	else if(m == 4){mes = "abril";}
	else if(m == 5){mes = "mayo";}
	else if(m == 6){mes = "junio";}
	else if(m == 7){mes = "julio";}
	else if(m == 8){mes = "agosto";}
	else if(m == 9){mes = "septiembre";}
	else if(m == 10){mes = "octubre";}
	else if(m == 11){mes = "noviembre";}
	else if(m == 12){mes = "diciembre";}
	
	if(nombre_dia.equalsIgnoreCase("monday")){
		nombre_dia = "lunes";
	}else if(nombre_dia.equalsIgnoreCase("tuesday")){
		nombre_dia = "martes";
	}else if(nombre_dia.equalsIgnoreCase("wednesday")){
		nombre_dia = "miércoles";
	}else if(nombre_dia.equalsIgnoreCase("thursday")){
		nombre_dia = "jueves";
	}else if(nombre_dia.equalsIgnoreCase("friday")){
		nombre_dia = "viernes";
	}else if(nombre_dia.equalsIgnoreCase("saturday")){
		nombre_dia = "sabado";
	}else if(nombre_dia.equalsIgnoreCase("sunday")){
		nombre_dia = "domingo";
	}
	
	
	
	if(m_actual == 1){mes_actual = "enero";}
	else if(m_actual == 2){mes_actual = "febrero";}
	else if(m_actual == 3){mes_actual = "marzo";}
	else if(m_actual == 4){mes_actual = "abril";}
	else if(m_actual == 5){mes_actual = "mayo";}
	else if(m_actual == 6){mes_actual = "junio";}
	else if(m_actual == 7){mes_actual = "julio";}
	else if(m_actual == 8){mes_actual = "agosto";}
	else if(m_actual == 9){mes_actual = "septiembre";}
	else if(m_actual == 10){mes_actual = "octubre";}
	else if(m_actual == 11){mes_actual = "noviembre";}
	else if(m_actual == 12){mes_actual = "diciembre";}
	
	if(nombre_dia_actual.equalsIgnoreCase("monday")){
		nombre_dia_actual = "lunes";
	}else if(nombre_dia_actual.equalsIgnoreCase("tuesday")){
		nombre_dia_actual = "martes";
	}else if(nombre_dia_actual.equalsIgnoreCase("wednesday")){
		nombre_dia_actual = "miércoles";
	}else if(nombre_dia_actual.equalsIgnoreCase("thursday")){
		nombre_dia_actual = "jueves";
	}else if(nombre_dia_actual.equalsIgnoreCase("friday")){
		nombre_dia_actual = "viernes";
	}else if(nombre_dia_actual.equalsIgnoreCase("saturday")){
		nombre_dia_actual = "sabado";
	}else if(nombre_dia_actual.equalsIgnoreCase("sunday")){
		nombre_dia_actual = "domingo";
	}

	
%>
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Vista de impresión información cheque</title>
	<link href="../css/banco_pacientes/ficha_cita.css" rel="stylesheet">
</head>
<style>
	.valor-celda{
		word-wrap: break-word;
	}
	
	
	
	.r1{
		width:16%;
	}
	.r2{
		width:38%;
	}
	.r3{
		width:8%;
	}
	.r4{
		width:38%;
	}
</style>
	<body>

	
	
	
	<body style="font-family:Monospace;">
        <div class="book">
            <div class="page">
                <div class="subpage">
                    <div class="encabezado no-imprimir">
                        <div class="encabezado-izquierda">  
                        </div>
                        <div class="encabezado-central">
                            <div class="titulo-ficha-clinica">CUB<br><p style="font-family: Monospace;font-size: 3mm;margin: 0;">INFORMACIÓN CHEQUE</p></div>
                        </div>
                        <div class="encabezado-derecha">
                            <div style="position: relative;top: 50%;transform: translateY(-50%);">
                                <div style="font-size:3mm;text-align:center;">
                                	Colonia Lomas del Norte Zona 17
                                </div>
                                <div id="registro" style="font-size:5mm;text-align:center;font-weight:900;"></div>
                            </div>
                        </div>
                    </div>
                    
                    
                    <table class="sturdy" style="table-layout: fixed; width: 100%">
                        <!-- <thead>
                            <tr>
                                <th style="font-size:16px;color:black;font-weight:bold;background-color:white;" colspan="4">Información</th>
                            </tr>
                        </thead>-->
                        <tbody>
                            <tr>
	                            <td class="titulo-celda r1">No. cheque:</td>
	                            <td class="valor-celda r2"><%=numero%></td>
	                            <td class="titulo-celda r3">ID:</td>
	                            <td class="valor-celda r4"><%=id%></td>
                        	</tr>
                        	<tr>
                        		
	                            <td class="titulo-celda">Lugar:</td>
	                            <td class="valor-celda"><%=lugar%> </td>
                        		<td class="titulo-celda">Fecha:</td>
	                            <td class="valor-celda">Guatemala, <%=nombre_dia%> <%=dia%> de <%=mes%> del <%=anio%> </td>
                        	</tr>
                        	<tr>
                        		
	                            <td class="titulo-celda">Nombre de:</td>
	                            <td class="valor-celda"><%=nombre%> </td>
                        		<td class="titulo-celda">Monto:</td>
	                            <td class="valor-celda">Q.<%=cantidad %> </td>
                        	</tr>
                        	<tr>
                        		
	                            <td class="titulo-celda">Motivo:</td>
	                            <td colspan="3" class="valor-celda"><%=motivo%> </td>
                        	</tr>
                        	<tr>
                        		
	                            <td class="titulo-celda">Fecha impresión:</td>
	                            <td colspan="3" class="valor-celda" >Guatemala, <%=nombre_dia_actual%> <%=dia_actual%> de <%=mes_actual%> del <%=anio_actual%>  </td>
                        	</tr>
                        </tbody>
                    </table>
                    <table>
                        <thead>
                            <tr>
                                <th style="font-size:16px;color:black;font-weight:bold;background-color:white;" colspan="4">Imagen</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
	                            <td style="text-align:center;"><img alt="" style="background: whitesmoke; background-image: url('<%=imagen%>'); width: 700px; height: 600px; background-size: cover; background-repeat: no-repeat; background-position: center center;"></td>
                        	</tr>
                        </tbody>
                    </table>
                    <br>
                    <br>
                    <div class="pie" style="margin-top: 50px;">
                    	<div style="float:left;">Autorizado por: ___________________ <span class="negrita"> </span> </div>
                    	<div style="float:right;">Autorizado por: ___________________ <span class="negrita"> </span> </div>
                    </div>                  
                </div>    
            </div>

        </div>
    </body>
	
	
	
	
	
	

<div id="site-bottom-bar" class="fixed-position no-imprimir">
        
 
               <div class="grupo-botones">
                   <div class="boton" style="cursor:pointer;color:white;" onclick="print();">
                   		<div class="img-icon" style="background-image: url('../img/printer.png');"></div>
                   	</div>
                   
               <div class="boton"  style="cursor:pointer;color:white "onclick="parent.ocultarVistaImpresion2();" >
					<div class="img-icon" style="background-image: url('../img/salir.png');"></div>
				</div>
               </div>
 
        
    </div>


</body>
<script>
	function validacionSuperior(){}
	function validacionInferior(){}
	anchuraMinima=99999;
	media=5;
	_estadoMapa=0;
	matriz_libre=false;
	editable=false;
	anchuraMedia=999999;
	var pieza_actual=false;
	</script>
<script src="../js/jquery-1.11.0.min.js"></script>
<script src="../js/plugins/bootstrap/bootstrap.min.js"></script>
</html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="crm_BLL.General_BLL"%>
<%@page import="crm_BE.Sesion_BE"%>
<%@page import="crm_BE.Funciones"%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.sql.Time"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="crm_BE.cuentas.Documento_BE"%>
<%@page import="crm_BE.cuentas.Efectivo_BE"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%
	//Valida la sesion
	Sesion_BE sesion = new Sesion_BE();
	int rol=-9999;
	if(session.getAttribute("sesion") == null){
		session.invalidate();
		response.sendRedirect("/lomas-crm/sesionExpirada.jsp");
	}
	else
	{
		sesion = (Sesion_BE) session.getAttribute("sesion");
		if(!(General_BLL.tienePermiso(sesion,Funciones.LISTAR_CHEQUES))){
			response.sendRedirect("/lomas-crm/errorpermisos.jsp");
		}
	}
	
	
	
	
	String c = request.getParameter("cont_documento");
	int cont = Integer.parseInt(c);
	float subTotalDocumento = 0;
	List<Documento_BE> nodos_documento = new ArrayList<Documento_BE>();
	for(int i = 1; i <= cont; i++){
		Documento_BE nodo = new Documento_BE();
		String caja_chica_documento		 	= String.valueOf(request.getParameter("caja_chica_documento"+i));
		String id_documento 				= String.valueOf(request.getParameter("id_documento"+i));
		String monto_documento	 			= String.valueOf(request.getParameter("monto_documento"+i));
		String nit_documento 		 		= String.valueOf(request.getParameter("nit"+i));
		String nombre_documento 		 	= String.valueOf(request.getParameter("nombre"+i));
		
		if(!caja_chica_documento.equalsIgnoreCase("null") && !caja_chica_documento.equalsIgnoreCase(""))
			nodo.do_caja_chica = Integer.parseInt(caja_chica_documento);
		
		if(!id_documento.equalsIgnoreCase("null") && !id_documento.equalsIgnoreCase(""))
			nodo.do_documento = Integer.parseInt(id_documento);
		
		if(!monto_documento.equalsIgnoreCase("null") && !monto_documento.equalsIgnoreCase(""))
			nodo.do_monto = Float.parseFloat(monto_documento);
		
		if(!nit_documento.equalsIgnoreCase("null") && !nit_documento.equalsIgnoreCase(""))
			nodo.do_nit = Integer.parseInt(nit_documento);
		
		if(!nombre_documento.equalsIgnoreCase("null") && !nombre_documento.equalsIgnoreCase(""))
			nodo.do_nombre = nombre_documento;
		
		subTotalDocumento = subTotalDocumento + nodo.do_monto;
		nodos_documento.add(nodo);
	}
	
	
	String c1 = request.getParameter("cont_efectivo");
	int cont1 = Integer.parseInt(c1);
	float subTotalBillete = 0;
	float subTotalMoneda = 0;
	
	List<Efectivo_BE> nodos_efectivo = new ArrayList<Efectivo_BE>();
	for(int i = 1; i <= cont1; i++){
		Efectivo_BE nodo = new Efectivo_BE();
		String caja_chica_efectivo	 	= String.valueOf(request.getParameter("caja_chica_efectivo"+i));
		String cantidad_efectivo		= String.valueOf(request.getParameter("cantidad"+i));
		String id_efectivo	 			= String.valueOf(request.getParameter("id_efectivo"+i));
		String monto_efectivo	 		= String.valueOf(request.getParameter("monto_efectivo"+i));
		String tipo_efectivo 		 	= String.valueOf(request.getParameter("tipo"+i));
		System.out.println(monto_efectivo);
		if(!caja_chica_efectivo.equalsIgnoreCase("null") && !caja_chica_efectivo.equalsIgnoreCase(""))
			nodo.ef_caja_chica = Integer.parseInt(caja_chica_efectivo);
		
		if(!cantidad_efectivo.equalsIgnoreCase("null") && !cantidad_efectivo.equalsIgnoreCase(""))
			nodo.ef_cantidad = Integer.parseInt(cantidad_efectivo);
		
		if(!id_efectivo.equalsIgnoreCase("null") && !id_efectivo.equalsIgnoreCase(""))
			nodo.ef_efectivo = Integer.parseInt(id_efectivo);
		
		if(!monto_efectivo.equalsIgnoreCase("null") && !monto_efectivo.equalsIgnoreCase(""))
			nodo.ef_monto = Float.parseFloat(monto_efectivo);
		
		if(!tipo_efectivo.equalsIgnoreCase("null") && !tipo_efectivo.equalsIgnoreCase(""))
			nodo.ef_tipo = Short.parseShort(tipo_efectivo);
		
		if(nodo.ef_tipo <= 7){
			subTotalBillete = subTotalBillete + nodo.ef_monto;
		}else{
			subTotalMoneda = subTotalMoneda + nodo.ef_monto;
		}
		
		nodos_efectivo.add(nodo);
	}
	
	
	
	
	
	String id = request.getParameter("id");
	String fecha 			= request.getParameter("fecha");
	String fecha_creacion 	= request.getParameter("fecha_creacion");
	String fecha_modificaion= request.getParameter("fecha_modificacion");
	String fondo		 	= request.getParameter("fondo");
	String sumatoria	 	= request.getParameter("sumatoria");
	String total_documento	= request.getParameter("total_documento");
	String total_efectivo	= request.getParameter("total_efectivo");
	String variacion		= request.getParameter("variacion");
	
	fondo = String.format("%.2f", Float.parseFloat(fondo));
	sumatoria = String.format("%.2f", Float.parseFloat(sumatoria));
	total_documento = String.format("%.2f", Float.parseFloat(total_documento));
	total_efectivo = String.format("%.2f", Float.parseFloat(total_efectivo));
	variacion = String.format("%.2f", Float.parseFloat(variacion));
	
	DecimalFormat formatter = new DecimalFormat("#,###.00");
	fondo = formatter.format(Float.parseFloat(fondo));
	sumatoria = formatter.format(Float.parseFloat(sumatoria));
	total_documento = formatter.format(Float.parseFloat(total_documento));
	total_efectivo = formatter.format(Float.parseFloat(total_efectivo));
	variacion = formatter.format(Float.parseFloat(variacion));
	
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

	
%>
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Vista de impresión información cheque</title>
	<link href="/lomas-crm/css/banco_pacientes/ficha_cita.css" rel="stylesheet">
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
                        <thead>
                            <tr>
                                <th style="font-size:16px;color:black;font-weight:bold;background-color:white;" colspan="">Billete</th>
                            	<th style="font-size:16px;color:black;font-weight:bold;background-color:white;" colspan="">Cantidad</th>
                            	<th style="font-size:16px;color:black;font-weight:bold;background-color:white;" colspan="">Monto</th>
                            	<th style="font-size:16px;color:black;font-weight:bold;background-color:white;" colspan="">Monedas</th>
                            	<th style="font-size:16px;color:black;font-weight:bold;background-color:white;" colspan="">Cantidad</th>
                            	<th style="font-size:16px;color:black;font-weight:bold;background-color:white;" colspan="">Monto</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<%/*
                        		for(int i = 0; i<=nodos_efectivo.size()-1; i++ ){
                        			out.print("<tr>");
                        			String tipo = "";
                        			if(nodos_efectivo.get(i).ef_tipo==1){
                        				tipo = "200";
                        			}else if(nodos_efectivo.get(i).ef_tipo==2){
                        				tipo = "100";
                        			}else if(nodos_efectivo.get(i).ef_tipo==3){
                        				tipo = "50";
                        			}else if(nodos_efectivo.get(i).ef_tipo==4){
                        				tipo = "20";
                        			}else if(nodos_efectivo.get(i).ef_tipo==5){
                        				tipo = "10";
                        			}else if(nodos_efectivo.get(i).ef_tipo==6){
                        				tipo = "5";
                        			}else if(nodos_efectivo.get(i).ef_tipo==7){
                        				tipo = "1";
                        			}else if(nodos_efectivo.get(i).ef_tipo==8){
                        				tipo = "1.00";
                        			}else if(nodos_efectivo.get(i).ef_tipo==9){
                        				tipo = "0.50";
                        			}else if(nodos_efectivo.get(i).ef_tipo==10){
                        				tipo = "0.25";
                        			}else if(nodos_efectivo.get(i).ef_tipo==11){
                        				tipo = "0.10";
                        			}else if(nodos_efectivo.get(i).ef_tipo==12){
                        				tipo = "0.05";
                        			}else if(nodos_efectivo.get(i).ef_tipo==13){
                        				tipo = "0.01";
                        			}
                        			out.print("<td class=\"titulo-celda\">" + tipo + "</td>");
                        			out.print("<td class=\"valor-celda\">"+efe.ef_cantidad+"</td>");
                        			out.print("<td class=\"valor-celda\">"+efe.ef_monto+"</td>");
                        			out.print("<td class=\"valor-celda\">"+efe.ef_cantidad+"</td>");
                        			out.print("<td class=\"valor-celda\">"+efe.ef_cantidad+"</td>");
                        			out.print("<td class=\"valor-celda\">"+efe.ef_cantidad+"</td>");
                        			out.print("<td class=\"valor-celda\">"+efe.ef_cantidad+"</td>");
                        			
                        			out.print("<tr>");
                        			out.print("</tr>");
                        		}
                        		*/
                        	%>
                            <tr>
                            	
	                            <td class="titulo-celda">200</td>
	                            <td class="valor-celda"><%=nodos_efectivo.get(0).ef_cantidad %></td>
	                            <td class="valor-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", nodos_efectivo.get(0).ef_monto))) %></td>
	                            <td class="titulo-celda">1.00 </td>
	                            <td class="valor-celda"><%=nodos_efectivo.get(7).ef_cantidad %></td>
	                            <td class="valor-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", nodos_efectivo.get(7).ef_monto))) %></td>
                        	</tr>
                        	
                        	<tr>
	                            <td class="titulo-celda">100</td>
	                            <td class="valor-celda"><%=nodos_efectivo.get(1).ef_cantidad %></td>
	                            <td class="valor-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", nodos_efectivo.get(1).ef_monto))) %></td>
	                            <td class="titulo-celda">0.50 </td>
	                            <td class="valor-celda"><%=nodos_efectivo.get(8).ef_cantidad %></td>
	                            <td class="valor-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", nodos_efectivo.get(8).ef_monto))) %></td>
                        	</tr>
                        	
                        	<tr>
	                            <td class="titulo-celda">50</td>
	                            <td class="valor-celda"><%=nodos_efectivo.get(2).ef_cantidad %></td>
	                            <td class="valor-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", nodos_efectivo.get(2).ef_monto))) %></td>
	                            <td class="titulo-celda">0.25 </td>
	                            <td class="valor-celda"><%=nodos_efectivo.get(9).ef_cantidad %></td>
	                            <td class="valor-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", nodos_efectivo.get(9).ef_monto))) %></td>
                        	</tr>
                        	
                        	<tr>
	                            <td class="titulo-celda">20</td>
	                            <td class="valor-celda"><%=nodos_efectivo.get(3).ef_cantidad %></td>
	                            <td class="valor-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", nodos_efectivo.get(3).ef_monto))) %></td>
	                            <td class="titulo-celda">0.10 </td>
	                            <td class="valor-celda"><%=nodos_efectivo.get(10).ef_cantidad %></td>
	                            <td class="valor-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", nodos_efectivo.get(10).ef_monto))) %></td>
                        	</tr>
                        	
                        	<tr>
	                            <td class="titulo-celda">10</td>
	                            <td class="valor-celda"><%=nodos_efectivo.get(4).ef_cantidad %></td>
	                            <td class="valor-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", nodos_efectivo.get(4).ef_monto))) %></td>
	                            <td class="titulo-celda">0.05 </td>
	                            <td class="valor-celda"><%=nodos_efectivo.get(11).ef_cantidad %></td>
	                            <td class="valor-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", nodos_efectivo.get(11).ef_monto))) %></td>
                        	</tr>
                        	
                        	<tr>
	                            <td class="titulo-celda">5</td>
	                            <td class="valor-celda"><%=nodos_efectivo.get(5).ef_cantidad %></td>
	                            <td class="valor-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", nodos_efectivo.get(5).ef_monto))) %></td>
	                            <td class="titulo-celda">0.01 </td>
	                            <td class="valor-celda"><%=nodos_efectivo.get(12).ef_cantidad %></td>
	                            <td class="valor-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", nodos_efectivo.get(12).ef_monto))) %></td>
                        	</tr>
                        	
                        	<tr>
	                            <td class="titulo-celda">1</td>
	                            <td class="valor-celda"><%=nodos_efectivo.get(6).ef_cantidad %></td>
	                            <td class="valor-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", nodos_efectivo.get(6).ef_monto))) %></td>
	                            <td class="valor-celda" colspan="3"></td>
                        	</tr>
                        	
                        	<tr>
	                            <td class="titulo-celda" colspan="2" style="text-align:right;">Subtotal</td>
	                            <td class="titulo-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", subTotalBillete))) %></td>
	                            <td class="titulo-celda" colspan="2" style="text-align:right;">Subtotal</td>
	                            <td class="titulo-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", subTotalMoneda))) %></td>
                        	</tr>
                        </tbody>
                    </table>
                    
                    <table class="sturdy" style="table-layout: fixed; width: 100%">
                        <thead>
                        	<tr>
                                <th style="font-size:16px;color:black;font-weight:bold;background-color:white;" colspan="3">Documentos</th>
                            </tr>
                            <tr>
                                <th style="font-size:16px;color:black;font-weight:bold;background-color:white;" colspan="">Nit</th>
                            	<th style="font-size:16px;color:black;font-weight:bold;background-color:white;" colspan="">Nombre</th>
                            	<th style="font-size:16px;color:black;font-weight:bold;background-color:white;" colspan="">Monto</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<%
                        		for(int i = 0; i<=nodos_documento.size()-1; i++ ){
                        			out.print("<tr>");
                        			String tipo = "";
                        			out.print("<td class=\"titulo-celda\">" + nodos_documento.get(i).do_nit + "</td>");
                        			out.print("<td class=\"valor-celda\">"+nodos_documento.get(i).do_nombre+"</td>");
                        			out.print("<td class=\"valor-celda\">"+formatter.format(Float.parseFloat(String.format("%.2f", nodos_documento.get(i).do_monto)))+"</td>");
                        			out.print("<tr>");
                        			out.print("</tr>");
                        		}
                        		
                        	%>
                            <tr>
                            	
	                            <td class="titulo-celda" colspan="2" style="text-align:right;">Subtotal</td>
	                            <td class="titulo-celda"><%=formatter.format(Float.parseFloat(String.format("%.2f", subTotalDocumento))) %></td>
	                        </tr>
	                        
	                        <tr style="border-top: solid 3px;">
	                            <td class="titulo-celda" colspan="2" style="text-align:right;">Sumatoria</td>
	                            <td class="titulo-celda"><%=sumatoria %></td>
	                        </tr>
	                        
	                        <tr>
	                            <td class="titulo-celda" colspan="2" style="text-align:right;">Fondo autorizado</td>
	                            <td class="titulo-celda"><%=fondo %></td>
	                        </tr>
	                        
	                        <tr>
	                            <td class="titulo-celda" colspan="2" style="text-align:right;">Variación</td>
	                            <td class="titulo-celda"><%=variacion%></td>
	                        </tr>
                        	<!-- <tr>
                        		
	                            <td class="titulo-celda">Fecha impresión:</td>
	                            <td colspan="3" class="valor-celda" >Guatemala, <%=nombre_dia_actual%> <%=dia_actual%> de <%=mes_actual%> del <%=anio_actual%>  </td>
                        	</tr>-->
                        	<tr>
                        		
	                            <td class="titulo-celda" style="text-align:right;" colspan="2">Fecha:</td>
	                            <td colspan="" class="valor-celda" ><%=fecha%>   </td>
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
                   		<div class="img-icon" style="background-image: url('/lomas-crm/img/printer.png');"></div>
                   	</div>
                   
               <div class="boton"  style="cursor:pointer;color:white "onclick="parent.ocultarVistaImpresion2();" >
					<div class="img-icon" style="background-image: url('/lomas-crm/img/salir.png');"></div>
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
<script src="/lomas-crm/js/jquery-1.11.0.min.js"></script>
<script src="/lomas-crm/js/plugins/bootstrap/bootstrap.min.js"></script>
</html>
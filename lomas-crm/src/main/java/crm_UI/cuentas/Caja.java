package crm_UI.cuentas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import crm_BE.Funciones;
import crm_BE.Parametro_BE;
import crm_BE.Resultado_BE;
import crm_BE.Sesion_BE;
import crm_BE.cuentas.Busqueda_BE;
import crm_BE.cuentas.Caja_Chica_BE;
import crm_BE.cuentas.Cheque_BE;
import crm_BE.cuentas.Documento_BE;
import crm_BE.cuentas.Efectivo_BE;
import crm_BLL.General_BLL;
import crm_BLL.Parametro_BLL;
import crm_BLL.Rol_BLL;
import crm_BLL.cuentas.Caja_BLL;
import crm_BLL.cuentas.Cheque_BLL;

/**
 * Servlet implementation class Caja
 */
public class Caja extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Caja() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Codigo para evitar errores por seguridad
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.setContentType("application/json");

		// Declaraci√≥n de variables
		JSONObject respuesta_json = new JSONObject();
		JSONArray tuplas = new JSONArray();
		Resultado_BE resultado = new Resultado_BE();
		Parametro_BE parametro_filtro = new Parametro_BE();
		Caja_Chica_BE caja = new Caja_Chica_BE();
		Efectivo_BE efectivo = new Efectivo_BE();
		Documento_BE documento = new Documento_BE();
		Busqueda_BE busqueda = new Busqueda_BE();
		
		List<Parametro_BE> resultados;
		Sesion_BE sesion = new Sesion_BE();
		PrintWriter out = response.getWriter();

		// Valida la sesion
		HttpSession sesionhttp = request.getSession();

		if (sesionhttp.getAttribute("sesion") == null) {
			// Sesi√≥n vencida
			sesionhttp.invalidate();
			respuesta_json.put("resultado", -100);
			respuesta_json.put("descripcion", "CaducÛ la sesiÛn");
		} else {
			// Si la Sesi√≥n sigue activa
			sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");

			/******************************************************
			 * INICIA SWITCH seg√∫n EL TIPO DE Operaci√≥n A REALIZAR
			 ******************************************************/
			switch (Integer.valueOf(request.getParameter("operacion").toString())) {
			case 1:
				// Lista de usuarios por id
				if (!General_BLL.tienePermiso(sesion, Funciones.CREAR_CAJA)) {
					respuesta_json.put("resultado", "-101");
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Listar parametros del sistema
					if (request.getParameter("id") != null) {
						parametro_filtro.pa_codigo_parametro = Integer.valueOf(request.getParameter("id"));
					}
					
					// Solo mostrar los par√°metros vigentes
					parametro_filtro.pa_estado = 1; 
					resultados = Parametro_BLL.listar(parametro_filtro);

					for (Parametro_BE param : resultados) {
						JSONObject tupla = new JSONObject();
						tupla.put("id", String.valueOf(param.pa_parametro));
						tupla.put("codigo", String.valueOf(param.pa_codigo_parametro));
						tupla.put("nombre", String.valueOf(param.pa_nombre));
						tupla.put("descripcion", param.pa_descripcion);
						tupla.put("valor", param.pa_valor);
						tupla.put("fecha_inicio", String.valueOf(param.pa_fecha_inicio).substring(0, 10));
						tuplas.add(tupla);
					}

					respuesta_json.put("data", tuplas);
					respuesta_json.put("resultado","1");
				}
				break;
				
			case 2:
				// Crea caja
				if (!General_BLL.tienePermiso(sesion, Funciones.CREAR_CAJA)) {
					respuesta_json.put("resultado", -101);
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Listar parametros del sistema
					crearCaja(caja, respuesta_json, request);
				}
				break;
				
			case 3:
				// Lista caja
				if (!General_BLL.tienePermiso(sesion, Funciones.LISTAR_CAJA)) {
					respuesta_json.put("resultado", -101);
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Listar parametros del sistema
					listarCaja(busqueda, respuesta_json, request);
				}
				break;
				
			case 4:
				// Lista de todos los datos de caja
				if (!General_BLL.tienePermiso(sesion, Funciones.LISTAR_CAJA)) {
					respuesta_json.put("resultado", -101);
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Listar parametros del sistema
					listarDatos(caja, efectivo, documento, respuesta_json, request);
				}
				break;
			case 5:
				// modificar caja
				if (!General_BLL.tienePermiso(sesion, Funciones.MODIFICAR_CAJA)) {
					respuesta_json.put("resultado", -101);
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Listar parametros del sistema
					modificarDatos(caja, efectivo, documento, respuesta_json, request);
				}
				break;
			}
		}

		// Respuesta JSON
		out.print(respuesta_json.toJSONString());
	}
	
	
	@SuppressWarnings("unchecked")
	private void crearCaja(Caja_Chica_BE caja, JSONObject respuesta,HttpServletRequest request) {
		// Llenar el objeto con la informaci√≥n del formulario
		Resultado_BE resultado = new Resultado_BE();
		HttpSession sesionhttp = request.getSession();
		Sesion_BE sesion = new Sesion_BE();
		sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");
		List<Efectivo_BE> efectivos = new ArrayList<Efectivo_BE>();
		List<Documento_BE> documentos = new ArrayList<Documento_BE>();
		
		String str_efectivo 	= String.valueOf(request.getParameter("efectivo")).trim();
		String str_documentos 	= String.valueOf(request.getParameter("documentos")).trim();
		String str_sub_billetes	= String.valueOf(request.getParameter("sub_billetes")).trim();
		String str_sub_monedas 	= String.valueOf(request.getParameter("sub_monedas")).trim();
		String str_total 		= String.valueOf(request.getParameter("total")).trim();
		String str_total_documentos = String.valueOf(request.getParameter("total_documentos")).trim();
		String str_sumatoria 	= String.valueOf(request.getParameter("sumatoria")).trim();
		String str_fondo 		= String.valueOf(request.getParameter("fondo")).trim();
		String str_variacion 	= String.valueOf(request.getParameter("variacion")).trim();
		String str_fecha 	= String.valueOf(request.getParameter("fecha")).trim();
		
		if(!str_efectivo.equalsIgnoreCase("null") && !str_efectivo.equalsIgnoreCase("")){
			String tuplas[] = str_efectivo.split(";");
			for (int i = 0; i <= tuplas.length-1; i++) {
				String columnas[] = tuplas[i].split(",");
				Efectivo_BE efec = new Efectivo_BE();
				efec.ef_tipo = Short.parseShort(columnas[0]);
				efec.ef_cantidad= Integer.parseInt(columnas[1]);
				efec.ef_monto = Float.parseFloat(columnas[2]);
				efectivos.add(efec);
			}
		}
		
		if(!str_documentos.equalsIgnoreCase("null") && !str_documentos.equalsIgnoreCase("")){
			String tuplas[] = str_documentos.split(";");
			for (int i = 0; i <= tuplas.length-1; i++) {
				String columnas[] = tuplas[i].split(",");
				Documento_BE doc = new Documento_BE();
				doc.do_nit = Integer.parseInt(columnas[0].replace("-", ""));
				doc.do_nombre= (columnas[1].toUpperCase());
				doc.do_monto = Float.parseFloat(columnas[2]);
				documentos.add(doc);
			}
		}
		
		if(!str_sub_billetes.equalsIgnoreCase("null") && !str_sub_billetes.equalsIgnoreCase("")){
			caja.ca_total_billete = Float.parseFloat(str_sub_billetes);
		}
		
		if(!str_sub_monedas.equalsIgnoreCase("null") && !str_sub_monedas.equalsIgnoreCase("")){
			caja.ca_total_moneda = Float.parseFloat(str_sub_monedas);
		}
		
		if(!str_total.equalsIgnoreCase("null") && !str_total.equalsIgnoreCase("")){
			caja.ca_total_efectivo = Float.parseFloat(str_total);
		}
		
		if(!str_total_documentos.equalsIgnoreCase("null") && !str_total_documentos.equalsIgnoreCase("")){
			caja.ca_total_documentos = Float.parseFloat(str_total_documentos);
		}
		
		if(!str_sumatoria.equalsIgnoreCase("null") && !str_sumatoria.equalsIgnoreCase("")){
			caja.ca_sumatoria = Float.parseFloat(str_sumatoria);
		}
		
		if(!str_fondo.equalsIgnoreCase("null") && !str_fondo.equalsIgnoreCase("")){
			caja.ca_fondo = Float.parseFloat(str_fondo);
		}
		
		if(!str_variacion.equalsIgnoreCase("null") && !str_variacion.equalsIgnoreCase("")){
			caja.ca_variacion = Float.parseFloat(str_variacion);
		}
		
		if(!str_fecha.equalsIgnoreCase("null") && !str_fecha.equalsIgnoreCase("")){
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			java.util.Date dteFecha = null;
			try {
				dteFecha = sdf.parse(str_fecha.replace("-", "/") + " 00:00:00");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			caja.ca_fecha = new Timestamp(dteFecha.getTime());
		}
		
		caja.ca_fecha_hora_creacion = java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());
		caja.ca_fecha_hora_modificacion = caja.ca_fecha_hora_creacion;
		
		// Crear una caja chica a traves de la BLL
		resultado = Caja_BLL.crear(caja, documentos, efectivos, sesion);

		// Respuesta JSON
		respuesta.put("id", resultado.re_identificador);
		respuesta.put("resultado", resultado.re_codigo);
		respuesta.put("descripcion", resultado.re_descripcion);
				
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void listarCaja(Busqueda_BE busqueda, JSONObject respuesta,HttpServletRequest request) {
		List<Caja_Chica_BE> resultados;
		JSONArray tuplas = new JSONArray();
		
		String str_general = String.valueOf(request.getParameter("general")).trim();
		String str_fecha1 = String.valueOf(request.getParameter("fecha1")).trim();
		String str_fecha2 = String.valueOf(request.getParameter("fecha2")).trim();
		String str_monto1 = String.valueOf(request.getParameter("monto1")).trim();
		String str_monto2 = String.valueOf(request.getParameter("monto2")).trim();
		
		if(!str_general.equalsIgnoreCase("null") && !str_general.equalsIgnoreCase(""))
			busqueda.ch_general = (str_general);
		
		if(!str_fecha1.equalsIgnoreCase("null") && !str_fecha1.equalsIgnoreCase("")){
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    		java.util.Date dteFecha = null;
    		try {
    			dteFecha = sdf.parse(str_fecha1.replace("-", "/") + " 00:00:00");
    		} catch (ParseException e1) {
    			e1.printStackTrace();
    		}
    		busqueda.ch_fecha1 = new Timestamp(dteFecha.getTime());
		}
		
		if(!str_fecha2.equalsIgnoreCase("null") && !str_fecha2.equalsIgnoreCase("")){
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    		java.util.Date dteFecha = null;
    		try {
    			dteFecha = sdf.parse(str_fecha2.replace("-", "/") + " 00:00:00");
    		} catch (ParseException e1) {
    			e1.printStackTrace();
    		}
    		busqueda.ch_fecha2 = new Timestamp(dteFecha.getTime());
		}
		
		if(!str_monto1.equalsIgnoreCase("null") && !str_monto1.equalsIgnoreCase(""))
			busqueda.ch_monto1 = Float.parseFloat(str_monto1);
		
		if(!str_monto2.equalsIgnoreCase("null") && !str_monto2.equalsIgnoreCase(""))
			busqueda.ch_monto2 = Float.parseFloat(str_monto2);
		
		// Listar roles a traves de la BLL
		try {

			resultados = Caja_BLL.buscar(busqueda);

			for (Caja_Chica_BE ca : resultados) {
				JSONObject tupla = new JSONObject();
				tupla.put("id", String.valueOf(ca.ca_caja_chica));
				tupla.put("total_efectivo", String.valueOf(ca.ca_total_efectivo).trim());
				tupla.put("total_documento", String.valueOf(ca.ca_total_documentos).trim());
				tupla.put("sumatoria", String.valueOf(ca.ca_sumatoria).trim());
				tupla.put("fondo", String.valueOf(ca.ca_fondo).trim());
				tupla.put("variacion", String.valueOf(ca.ca_variacion).trim());
				tupla.put("fecha", General_BLL.formatearFechaSinHoraUI(ca.ca_fecha).trim());
				
				
				tuplas.add(tupla);
			}

			respuesta.put("resultado", 1);
			respuesta.put("data", tuplas);
			respuesta.put("descripcion", "Se obtuvieron los datos correctamente");
			System.out.println("si se ejecuto");
			

		} catch (Exception e) {
			respuesta.put("resultado", -200);
			respuesta.put("descripcion", "La operaciÛn no fue exitosa");

		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	private void listarDatos(Caja_Chica_BE caja, Efectivo_BE efectivo, Documento_BE documento, JSONObject respuesta,HttpServletRequest request) {
		List<Caja_Chica_BE> resultados1;
		List<Efectivo_BE> resultados2;
		List<Documento_BE> resultados3;
		
		JSONArray tuplas1 = new JSONArray();
		JSONArray tuplas2 = new JSONArray();
		JSONArray tuplas3 = new JSONArray();
		
		String str_id = String.valueOf(request.getParameter("id")).trim();
		
		if(!str_id.equalsIgnoreCase("null") && !str_id.equalsIgnoreCase("")){
			caja.ca_caja_chica= Integer.parseInt(str_id);
			efectivo.ef_caja_chica= Integer.parseInt(str_id);
			documento.do_caja_chica= Integer.parseInt(str_id);
		}
		
		// Listar roles a traves de la BLL
		try {

			resultados1 = Caja_BLL.listarCajaChica(caja);
			resultados2 = Caja_BLL.listarEfectivo(efectivo);
			resultados3 = Caja_BLL.listarDocumento(documento);

			for (Caja_Chica_BE ca : resultados1) {
				JSONObject tupla = new JSONObject();
				tupla.put("id", String.valueOf(ca.ca_caja_chica));
				tupla.put("total_efectivo", String.valueOf(ca.ca_total_efectivo).trim());
				tupla.put("total_documento", String.valueOf(ca.ca_total_documentos).trim());
				tupla.put("sumatoria", String.valueOf(ca.ca_sumatoria).trim());
				tupla.put("fondo", String.valueOf(ca.ca_fondo).trim());
				tupla.put("variacion", String.valueOf(ca.ca_variacion).trim());
				tupla.put("fecha", General_BLL.formatearFechaSinHoraUI(ca.ca_fecha).trim());
				tupla.put("fecha_creacion", General_BLL.formatearFechaSinHoraUI(ca.ca_fecha_hora_creacion).trim());
				tupla.put("fecha_modificacion", General_BLL.formatearFechaSinHoraUI(ca.ca_fecha_hora_modificacion).trim());
				
				tuplas1.add(tupla);
			}
			
			for (Efectivo_BE ca : resultados2) {
				JSONObject tupla = new JSONObject();
				tupla.put("id", String.valueOf(ca.ef_efectivo));
				tupla.put("caja_chica", String.valueOf(ca.ef_caja_chica).trim());
				tupla.put("tipo", String.valueOf(ca.ef_tipo).trim());
				tupla.put("cantidad", String.valueOf(ca.ef_cantidad).trim());
				tupla.put("monto", String.valueOf(ca.ef_monto).trim());
				tuplas2.add(tupla);
			}
			
			for (Documento_BE ca : resultados3) {
				JSONObject tupla = new JSONObject();
				tupla.put("id", String.valueOf(ca.do_documento));
				tupla.put("caja_chica", String.valueOf(ca.do_caja_chica).trim());
				tupla.put("nit", String.valueOf(ca.do_nit).trim());
				tupla.put("nombre", String.valueOf(ca.do_nombre).trim());
				tupla.put("monto", String.valueOf(ca.do_monto).trim());
				tuplas3.add(tupla);
			}

			respuesta.put("resultado", 1);
			respuesta.put("caja", tuplas1);
			respuesta.put("efectivo", tuplas2);
			respuesta.put("documento", tuplas3);
			respuesta.put("descripcion", "Se obtuvieron los datos correctamente");
			System.out.println("si se ejecuto");
			

		} catch (Exception e) {
			respuesta.put("resultado", -200);
			respuesta.put("descripcion", "La operaciÛn no fue exitosa");

		}
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	private void modificarDatos(Caja_Chica_BE caja, Efectivo_BE efectivo, Documento_BE documento, JSONObject respuesta,HttpServletRequest request) {
		HttpSession sesionhttp = request.getSession();
		Sesion_BE sesion = new Sesion_BE();
		Resultado_BE resultado = new Resultado_BE();
		sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");
		List<Efectivo_BE> efectivos = new ArrayList<Efectivo_BE>();
		List<Documento_BE> documentos = new ArrayList<Documento_BE>();
		
		String str_id		 	= String.valueOf(request.getParameter("id")).trim();
		String str_efectivo 	= String.valueOf(request.getParameter("efectivo")).trim();
		String str_documentos 	= String.valueOf(request.getParameter("documentos")).trim();
		String str_sub_billetes	= String.valueOf(request.getParameter("sub_billetes")).trim();
		String str_sub_monedas 	= String.valueOf(request.getParameter("sub_monedas")).trim();
		String str_total 		= String.valueOf(request.getParameter("total")).trim();
		String str_total_documentos = String.valueOf(request.getParameter("total_documentos")).trim();
		String str_sumatoria 	= String.valueOf(request.getParameter("sumatoria")).trim();
		String str_fondo 		= String.valueOf(request.getParameter("fondo")).trim();
		String str_variacion 	= String.valueOf(request.getParameter("variacion")).trim();
		String str_fecha 	= String.valueOf(request.getParameter("fecha")).trim();
		
		if(!str_efectivo.equalsIgnoreCase("null") && !str_efectivo.equalsIgnoreCase("")){
			String tuplas[] = str_efectivo.split(";");
			for (int i = 0; i <= tuplas.length-1; i++) {
				String columnas[] = tuplas[i].split(",");
				Efectivo_BE efec = new Efectivo_BE();
				efec.ef_caja_chica = Integer.parseInt(str_id);
				efec.ef_tipo = Short.parseShort(columnas[0]);
				efec.ef_cantidad= Integer.parseInt(columnas[1]);
				efec.ef_monto = Float.parseFloat(columnas[2]);
				efectivos.add(efec);
			}
		}
		
		if(!str_documentos.equalsIgnoreCase("null") && !str_documentos.equalsIgnoreCase("")){
			String tuplas[] = str_documentos.split(";");
			for (int i = 0; i <= tuplas.length-1; i++) {
				String columnas[] = tuplas[i].split(",");
				Documento_BE doc = new Documento_BE();
				doc.do_caja_chica = Integer.parseInt(str_id);
				doc.do_nit = Integer.parseInt(columnas[0].replace("-", ""));
				doc.do_nombre= (columnas[1].toUpperCase());
				doc.do_monto = Float.parseFloat(columnas[2]);
				documentos.add(doc);
			}
		}
		
		if(!str_sub_billetes.equalsIgnoreCase("null") && !str_sub_billetes.equalsIgnoreCase("")){
			caja.ca_total_billete = Float.parseFloat(str_sub_billetes);
		}
		
		if(!str_sub_monedas.equalsIgnoreCase("null") && !str_sub_monedas.equalsIgnoreCase("")){
			caja.ca_total_moneda = Float.parseFloat(str_sub_monedas);
		}
		
		if(!str_total.equalsIgnoreCase("null") && !str_total.equalsIgnoreCase("")){
			caja.ca_total_efectivo = Float.parseFloat(str_total);
		}
		
		if(!str_total_documentos.equalsIgnoreCase("null") && !str_total_documentos.equalsIgnoreCase("")){
			caja.ca_total_documentos = Float.parseFloat(str_total_documentos);
		}
		
		if(!str_sumatoria.equalsIgnoreCase("null") && !str_sumatoria.equalsIgnoreCase("")){
			caja.ca_sumatoria = Float.parseFloat(str_sumatoria);
		}
		
		if(!str_fondo.equalsIgnoreCase("null") && !str_fondo.equalsIgnoreCase("")){
			caja.ca_fondo = Float.parseFloat(str_fondo);
		}
		
		if(!str_variacion.equalsIgnoreCase("null") && !str_variacion.equalsIgnoreCase("")){
			caja.ca_variacion = Float.parseFloat(str_variacion);
		}
		
		if(!str_fecha.equalsIgnoreCase("null") && !str_fecha.equalsIgnoreCase("")){
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			java.util.Date dteFecha = null;
			try {
				dteFecha = sdf.parse(str_fecha.replace("-", "/") + " 00:00:00");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			caja.ca_fecha = new Timestamp(dteFecha.getTime());
		}
		
		caja.ca_fecha_hora_modificacion = java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());
		caja.ca_caja_chica = Integer.parseInt(str_id);
		resultado = Caja_BLL.modificar(caja, documentos, efectivos, sesion);
		

		// Respuesta JSON
		respuesta.put("resultado", resultado.re_codigo);
		respuesta.put("descripcion", resultado.re_descripcion);	
		
	}
}



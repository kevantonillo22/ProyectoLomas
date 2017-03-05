package crm_UI.garita;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import crm_BE.Funciones;
import crm_BE.General_BE;
import crm_BE.Parametro_BE;
import crm_BE.Resultado_BE;
import crm_BE.Sesion_BE;
import crm_BE.cuentas.Busqueda_BE;
import crm_BE.cuentas.Caja_Chica_BE;
import crm_BE.cuentas.Documento_BE;
import crm_BE.cuentas.Efectivo_BE;
import crm_BE.garita.Direccion_BE;
import crm_BE.garita.Ingreso_BE;
import crm_BLL.General_BLL;
import crm_BLL.cuentas.Caja_BLL;
import crm_BLL.garita.Direccion_BLL;
import crm_BLL.garita.Ingreso_BLL;
import crm_UI.GuardarArchivo;

/**
 * Servlet implementation class Direccion
 */
public class Direccion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Direccion() {
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
		
		JSONObject respuesta_json = new JSONObject();
		JSONArray tuplas = new JSONArray();
		Resultado_BE resultado = new Resultado_BE();
		Parametro_BE parametro_filtro = new Parametro_BE();
		Direccion_BE direccion = new Direccion_BE();
		Ingreso_BE ingreso = new Ingreso_BE();
		
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
			JSONObject js = General_BLL.ParsearRequestAngular(request);
			switch (Integer.valueOf(js.get("operacion").toString())) {
			case 1:
				// crea direccion
				if (!General_BLL.tienePermiso(sesion, Funciones.CREAR_DIRECCION)) {
					respuesta_json.put("resultado", "-101");
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					crearDireccion(direccion, respuesta_json, request, js);
				}
				break;
				
			case 2:
				// Lista direccion
				if (!General_BLL.tienePermiso(sesion, Funciones.LISTAR_DIRECCION) && !General_BLL.tienePermiso(sesion, Funciones.ADMINISTRACION_GARITA)) {
					respuesta_json.put("resultado", -101);
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Listar parametros del sistema
					listarDireccion(direccion, respuesta_json, request, js);
				}
				break;
			case 3:
				// modifica direccion
				if (!General_BLL.tienePermiso(sesion, Funciones.LISTAR_DIRECCION)) {
					respuesta_json.put("resultado", -101);
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Listar parametros del sistema
					modificarDireccion(direccion, respuesta_json, request, js);
				}
				break;
			case 4:
				// Registrar ingreso
				if (!General_BLL.tienePermiso(sesion, Funciones.INGRESO_EGRESO)) {
					respuesta_json.put("resultado", -101);
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Listar parametros del sistema
					crearIngreso(ingreso, respuesta_json, request, js);
				}
				break;
			case 5:
				// Listar ingreso
				if (!General_BLL.tienePermiso(sesion, Funciones.INGRESO_EGRESO)) {
					respuesta_json.put("resultado", -101);
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Listar parametros del sistema
					listarIngreso(ingreso, respuesta_json, request, js);
				}
				break;
			case 6:
				// Listar ingreso
				if (!General_BLL.tienePermiso(sesion, Funciones.LISTAR_INGRESO)) {
					respuesta_json.put("resultado", -101);
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Listar parametros del sistema
					listarIngresoReporte(ingreso, direccion, respuesta_json, request, js);
				}
				break;	
				
			}
		}

		// Respuesta JSON
		out.print(respuesta_json.toJSONString());
	}
	
	@SuppressWarnings("unchecked")
	private void crearDireccion(Direccion_BE direccion, JSONObject respuesta,HttpServletRequest request, JSONObject requestAngular) {
		// Llenar el objeto con la informaci√≥n del formulario
		Resultado_BE resultado = new Resultado_BE();
		HttpSession sesionhttp = request.getSession();
		Sesion_BE sesion = new Sesion_BE();
		sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");
		List<Direccion_BE> resultados;
		JSONArray tuplas = new JSONArray();
		Direccion_BE busqueda = new Direccion_BE();
		
		JSONObject js = requestAngular;
		
		String str_numero_calle_av = String.valueOf(js.get("numero")).trim();
		String str_calle_av 	= String.valueOf(js.get("avenidaCalle")).trim();
		String str_num_casa		= String.valueOf(js.get("numeroCasa")).trim();
		String str_familia		= String.valueOf(js.get("familia")).trim();
		String str_telefono		= String.valueOf(js.get("telefono")).trim();
		String str_nombre_titular= String.valueOf(js.get("titular")).trim();
		String str_email		= String.valueOf(js.get("email")).trim();
		String str_estado_pago	= String.valueOf(js.get("estado_pago")).trim();
		String str_estado_domicilio	= String.valueOf(js.get("estado_domicilio")).trim();
		
		String str_estado		= 1 + "";
		
		
		if(!str_numero_calle_av.equalsIgnoreCase("null") && !str_numero_calle_av.equalsIgnoreCase("")){
			//Se le concatena un cero a la cadena
			str_numero_calle_av = str_numero_calle_av.length() == 1 ? "0" + str_numero_calle_av : str_numero_calle_av;
			direccion.di_numero_calle_av = (str_numero_calle_av);
			busqueda.di_numero_calle_av = (str_numero_calle_av);
		}
		if(!str_calle_av.equalsIgnoreCase("null") && !str_calle_av.equalsIgnoreCase("")){
			direccion.di_calle_av = Integer.parseInt(str_calle_av);
			busqueda.di_calle_av = Integer.parseInt(str_calle_av);
		}
		if(!str_num_casa.equalsIgnoreCase("null") && !str_num_casa.equalsIgnoreCase("")){
			direccion.di_num_casa = (str_num_casa);
			busqueda.di_num_casa = (str_num_casa);
		}
		if(!str_familia.equalsIgnoreCase("null") && !str_familia.equalsIgnoreCase("")){
			direccion.di_familia = (str_familia);
		}
		if(!str_telefono.equalsIgnoreCase("null") && !str_telefono.equalsIgnoreCase("")){
			direccion.di_telefono = (str_telefono);
		}
		if(!str_nombre_titular.equalsIgnoreCase("null") && !str_nombre_titular.equalsIgnoreCase("")){
			direccion.di_nombre_titular = (str_nombre_titular);
		}
		if(!str_email.equalsIgnoreCase("null") && !str_email.equalsIgnoreCase("")){
			direccion.di_email = (str_email);
		}
		if(!str_estado.equalsIgnoreCase("null") && !str_estado.equalsIgnoreCase("")){
			direccion.di_estado = Integer.parseInt(str_estado);
		}
		if(!str_estado_pago.equalsIgnoreCase("null") && !str_estado_pago.equalsIgnoreCase("")){
			direccion.di_estado_pago = Integer.parseInt(str_estado_pago);
		}
		if(!str_estado_domicilio.equalsIgnoreCase("null") && !str_estado_domicilio.equalsIgnoreCase("")){
			direccion.di_estado_domicilio = Integer.parseInt(str_estado_domicilio);
		}
		// Crear una caja chica a traves de la BLL
		
		busqueda.di_estado = 1;
		resultados = Direccion_BLL.listar(busqueda);
		
		String av = busqueda.di_calle_av == 2 ? "calle":"avenida"; 
		
		if(resultados.isEmpty()){
			resultado = Direccion_BLL.crear(direccion,  sesion);
			// Respuesta JSON
			respuesta.put("id", resultado.re_identificador);
			respuesta.put("resultado", resultado.re_codigo);
			respuesta.put("descripcion", resultado.re_descripcion);
		}
		else{
			// Respuesta JSON
			respuesta.put("id", "1");
			respuesta.put("resultado", "-300");
			respuesta.put("descripcion", "Ya fue registrada una direcciÛn en la "  + 
			busqueda.di_numero_calle_av + " " + av + " " + busqueda.di_num_casa);
		}		
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void listarDireccion(Direccion_BE busqueda, JSONObject respuesta,HttpServletRequest request, JSONObject requestAngular) {
		List<Direccion_BE> resultados;
		JSONArray tuplas = new JSONArray();
		JSONObject js = requestAngular;
		
		String str_numero_calle_av = String.valueOf(js.get("numero")).trim();
		String str_calle_av 	= String.valueOf(js.get("avenidaCalle")).trim();
		String str_num_casa		= String.valueOf(js.get("numeroCasa")).trim();
		String str_id_direccion		= String.valueOf(js.get("id_direccion")).trim();
		
		if(!str_numero_calle_av.equalsIgnoreCase("null") && !str_numero_calle_av.equalsIgnoreCase("")){
			str_numero_calle_av = str_numero_calle_av.length() == 1 ? "0" + str_numero_calle_av : str_numero_calle_av;
			busqueda.di_numero_calle_av = (str_numero_calle_av);
		}
		if(!str_calle_av.equalsIgnoreCase("null") && !str_calle_av.equalsIgnoreCase("")){
			busqueda.di_calle_av = Integer.parseInt(str_calle_av);
		}
		if(!str_num_casa.equalsIgnoreCase("null") && !str_num_casa.equalsIgnoreCase("")){
			busqueda.di_num_casa = (str_num_casa);
		}
		if(!str_id_direccion.equalsIgnoreCase("null") && !str_id_direccion.equalsIgnoreCase("")){
			busqueda.di_direccion = Integer.parseInt(str_id_direccion);
		}
		//Registros que estÈn de alta
		busqueda.di_estado = 1;
		
		resultados = Direccion_BLL.listar(busqueda);

		for (Direccion_BE ca : resultados) {
			JSONObject tupla = new JSONObject();
			String av = ca.di_calle_av == 2 ? "calle":"avenida";  
			String direccion = ca.di_numero_calle_av + " " + av + " " + ca.di_num_casa;
			String familia = ca.di_familia == null ? "": ca.di_familia;
			String telefono = ca.di_telefono == null ? "": ca.di_telefono;
			String nombre = ca.di_nombre_titular == null ? "": ca.di_nombre_titular;
			String email = ca.di_email == null ? "": ca.di_email;
			
			tupla.put("id_direccion", String.valueOf(ca.di_direccion));
			tupla.put("numero_calle", String.valueOf(ca.di_numero_calle_av));
			tupla.put("calle_av", String.valueOf(ca.di_calle_av).trim());
			tupla.put("num_casa", String.valueOf(ca.di_num_casa).trim());
			tupla.put("familia", String.valueOf(familia).trim());
			tupla.put("telefono", String.valueOf(telefono).trim());
			tupla.put("nombre_titular", String.valueOf(nombre).trim());
			tupla.put("email", String.valueOf(email).trim());
			tupla.put("estado_pago", String.valueOf(ca.di_estado_pago).trim());
			tupla.put("estado_domicilio", String.valueOf(ca.di_estado_domicilio).trim());
			tupla.put("direccion", String.valueOf(direccion).trim());
			String label_pago = ca.di_estado_pago == 1 ?  "Paga" : "No Paga";
			String label_domicilio = "";
			if(ca.di_estado_domicilio == 1 ){
				label_domicilio =  "Normal";
			}else if(ca.di_estado_domicilio == 1 ){
				label_domicilio = "Alquilada";
			}else if(ca.di_estado_domicilio == 3 ){
				label_domicilio = "Abandonada";
			}
			
			
			tupla.put("label_pago",label_pago);
			tupla.put("label_domicilio",label_domicilio);
			
			tuplas.add(tupla);
		}

		respuesta.put("resultado", 1);
		respuesta.put("data", tuplas);
		respuesta.put("descripcion", "Se obtuvieron los datos correctamente");
		
	}
	
	@SuppressWarnings("unchecked")
	private void modificarDireccion(Direccion_BE direccion, JSONObject respuesta,HttpServletRequest request, JSONObject requestAngular) {
		// Llenar el objeto con la informaci√≥n del formulario
		Resultado_BE resultado = new Resultado_BE();
		HttpSession sesionhttp = request.getSession();
		Sesion_BE sesion = new Sesion_BE();
		sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");
		Direccion_BE busqueda = new Direccion_BE();
		
		JSONObject js = requestAngular;
		
		String str_id_direcccion = String.valueOf(js.get("id_direccion")).trim();
		String str_numero_calle_av = String.valueOf(js.get("numero")).trim();
		String str_calle_av 	= String.valueOf(js.get("avenidaCalle")).trim();
		String str_num_casa		= String.valueOf(js.get("numeroCasa")).trim();
		String str_familia		= String.valueOf(js.get("familia")).trim();
		String str_telefono		= String.valueOf(js.get("telefono")).trim();
		String str_nombre_titular= String.valueOf(js.get("titular")).trim();
		String str_email		= String.valueOf(js.get("email")).trim();
		String str_estado_pago	= String.valueOf(js.get("estado_pago")).trim();
		String str_estado_domicilio	= String.valueOf(js.get("estado_domicilio")).trim();
		
		String str_estado		= String.valueOf(js.get("estado")).trim();
		
		
		if(!str_id_direcccion.equalsIgnoreCase("null") && !str_id_direcccion.equalsIgnoreCase("")){
			direccion.di_direccion = Integer.parseInt(str_id_direcccion);
			busqueda.di_direccion = Integer.parseInt(str_id_direcccion);
		}
		if(!str_numero_calle_av.equalsIgnoreCase("null") && !str_numero_calle_av.equalsIgnoreCase("")){
			direccion.di_numero_calle_av = (str_numero_calle_av);
			busqueda.di_numero_calle_av = (str_numero_calle_av);
		}
		if(!str_calle_av.equalsIgnoreCase("null") && !str_calle_av.equalsIgnoreCase("")){
			direccion.di_calle_av = Integer.parseInt(str_calle_av);
			busqueda.di_calle_av = Integer.parseInt(str_calle_av);
		}
		if(!str_num_casa.equalsIgnoreCase("null") && !str_num_casa.equalsIgnoreCase("")){
			direccion.di_num_casa = (str_num_casa);
			busqueda.di_num_casa = (str_num_casa);
		}
		if(!str_familia.equalsIgnoreCase("null") && !str_familia.equalsIgnoreCase("")){
			direccion.di_familia = (str_familia);
		}
		if(!str_telefono.equalsIgnoreCase("null") && !str_telefono.equalsIgnoreCase("")){
			direccion.di_telefono = (str_telefono);
		}
		if(!str_nombre_titular.equalsIgnoreCase("null") && !str_nombre_titular.equalsIgnoreCase("")){
			direccion.di_nombre_titular = (str_nombre_titular);
		}
		if(!str_email.equalsIgnoreCase("null") && !str_email.equalsIgnoreCase("")){
			direccion.di_email = (str_email);
		}
		if(!str_estado.equalsIgnoreCase("null") && !str_estado.equalsIgnoreCase("")){
			direccion.di_estado = Integer.parseInt(str_estado);
		}
		if(!str_estado_pago.equalsIgnoreCase("null") && !str_estado_pago.equalsIgnoreCase("")){
			direccion.di_estado_pago = Integer.parseInt(str_estado_pago);
		}
		if(!str_estado_domicilio.equalsIgnoreCase("null") && !str_estado_domicilio.equalsIgnoreCase("")){
			direccion.di_estado_domicilio = Integer.parseInt(str_estado_domicilio);
		}
		// Crear una caja chica a traves de la BLL
		
		//resultados = Direccion_BLL.listar(busqueda);
		System.out.println("asdfasd adf asdfasd fad " + direccion.di_estado);
		String av = busqueda.di_calle_av == 2 ? "calle":"avenida"; 
		
		resultado = Direccion_BLL.modificar(direccion,  sesion);
			// Respuesta JSON
			respuesta.put("id", resultado.re_identificador);
			respuesta.put("resultado", resultado.re_codigo);
			respuesta.put("descripcion", resultado.re_descripcion);
				
	}
	
	@SuppressWarnings("unchecked")
	private void crearIngreso(Ingreso_BE ingreso, JSONObject respuesta,HttpServletRequest request, JSONObject requestAngular) {
		// Llenar el objeto con la informaci√≥n del formulario
		Resultado_BE resultado = new Resultado_BE();
		HttpSession sesionhttp = request.getSession();
		Sesion_BE sesion = new Sesion_BE();
		sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");
		Direccion_BE busqueda = new Direccion_BE();
		
		JSONObject js = requestAngular;
		
		
		String archivoDoc 		= String.valueOf(js.get("archivoDoc")).trim();
		String archivoRostro 	= String.valueOf(js.get("archivoRostro")).trim();
		String archivoPlaca 	= String.valueOf(js.get("archivoPlaca")).trim();
		String id_direccion		= String.valueOf(js.get("id_direccion")).trim();
		String placa 			= String.valueOf(js.get("placa")).trim().toUpperCase();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		
		String hash = General_BLL.obtenerHash(dateFormat.format(date), "MD5").substring(0,12);
		String nombreArchivoDoc 		= "documento_" + hash + ".png";
		String nombreArchivoRostro		= "rostro_" + hash + ".png";
		String nombreArchivoPlaca	 	= "placa_" + hash + ".png";
		
		//String nombreArchivo ="C:\\"+"p_"+ id + ".png";
		File newFileDoc = new File(General_BE.INGRESO_IMAGENES + nombreArchivoDoc);
		File newFileRostro = new File(General_BE.INGRESO_IMAGENES + nombreArchivoRostro);
		File newFilePlaca = new File(General_BE.INGRESO_IMAGENES + nombreArchivoPlaca);
		
		byte[] imageByteArrayDoc = GuardarArchivo.decodeImage(archivoDoc.replace(
				"data:image/png;base64,", ""));
		byte[] imageByteArrayRostro = GuardarArchivo.decodeImage(archivoRostro.replace(
				"data:image/png;base64,", ""));
		byte[] imageByteArrayPlaca = GuardarArchivo.decodeImage(archivoPlaca.replace(
				"data:image/png;base64,", ""));

		FileOutputStream imageOutFile1;
		FileOutputStream imageOutFile2;
		FileOutputStream imageOutFile3;
		try {
			imageOutFile1 = new FileOutputStream(newFileDoc, false);
			imageOutFile2 = new FileOutputStream(newFileRostro, false);
			imageOutFile3 = new FileOutputStream(newFilePlaca, false);
			imageOutFile1.write(imageByteArrayDoc);imageOutFile1.close();
			imageOutFile2.write(imageByteArrayRostro);imageOutFile2.close();
			imageOutFile3.write(imageByteArrayPlaca);imageOutFile3.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// Crear un ingreso
		ingreso.in_direccion =Integer.parseInt(id_direccion);
		ingreso.in_imagen_dpi=(nombreArchivoDoc);
		ingreso.in_imagen_rostro=(nombreArchivoRostro);
		ingreso.in_imagen_placa=(nombreArchivoPlaca);
		ingreso.in_placa=placa;
		ingreso.in_fecha_entrada=java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());;
		ingreso.in_usuario = sesion.se_usuario;
		ingreso.in_estado = 1;
		
		resultado = Ingreso_BLL.crear(ingreso, sesion);
		
		// Respuesta JSON
		respuesta.put("id", resultado.re_identificador);
		respuesta.put("resultado", resultado.re_codigo);
		respuesta.put("descripcion", resultado.re_descripcion);			
	}
	
	@SuppressWarnings("unchecked")
	private void listarIngreso(Ingreso_BE ingreso, JSONObject respuesta,HttpServletRequest request, JSONObject requestAngular) {
		List<Ingreso_BE> resultados;
		JSONArray tuplas = new JSONArray();
		JSONObject js = requestAngular;
		
		String in_ingreso 		= String.valueOf(js.get("in_ingreso")).trim();
		String in_placa		 	= String.valueOf(js.get("in_placa")).trim().toUpperCase();
		String in_direccion		= String.valueOf(js.get("in_direccion")).trim();
		//String in_fecha_entrada	= String.valueOf(js.get("in_fecha_entrada")).trim();
		String in_usuario		= String.valueOf(js.get("in_fecha_entrada")).trim();
		String in_rownum		= String.valueOf(js.get("in_rownum")).trim();
		
		
		if(!in_ingreso.equalsIgnoreCase("null") && !in_ingreso.equalsIgnoreCase("")){
			ingreso.in_ingreso = Integer.parseInt(in_ingreso);
		}
		if(!in_placa.equalsIgnoreCase("null") && !in_placa.equalsIgnoreCase("")){
			ingreso.in_placa = (in_placa);
		}
		if(!in_direccion.equalsIgnoreCase("null") && !in_direccion.equalsIgnoreCase("")){
			ingreso.in_direccion = Integer.parseInt(in_direccion);
		}
		/*if(!in_fecha_entrada.equalsIgnoreCase("null") && !in_fecha_entrada.equalsIgnoreCase("")){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		    Date parsedDate;
			try {
				parsedDate = dateFormat.parse(in_fecha_entrada + " 00:00:00");
				Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
				ingreso.in_fecha_entrada = timestamp;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}*/
		
		if(!in_usuario.equalsIgnoreCase("null") && !in_usuario.equalsIgnoreCase("")){
			ingreso.in_usuario = Integer.parseInt(in_usuario);
		}
		
		if(!in_rownum.equalsIgnoreCase("null") && !in_rownum.equalsIgnoreCase("")){
			ingreso.in_rownum = Integer.parseInt(in_rownum);
		}
		//Registros que estÈn de alta
		ingreso.in_estado = 1;
		
		resultados = Ingreso_BLL.listar(ingreso);

		for (Ingreso_BE ing : resultados) {
			JSONObject tupla = new JSONObject();
			
			tupla.put("in_ingreso", String.valueOf(ing.in_ingreso));
			tupla.put("in_placa", String.valueOf(ing.in_placa));
			tupla.put("in_direccion", String.valueOf(ing.in_texto_direccion));
			tupla.put("in_imagen_placa", String.valueOf(ing.in_imagen_placa));
			tupla.put("in_imagen_rostro", String.valueOf(ing.in_imagen_rostro));
			tupla.put("in_imagen_dpi", String.valueOf(ing.in_imagen_dpi));
			tupla.put("in_fecha_entrada", String.valueOf(General_BLL.formatearFechaConHora(ing.in_fecha_entrada)));
			tupla.put("in_usuario", String.valueOf(ing.in_usuario));
			tupla.put("in_ingreso", String.valueOf(ing.in_estado));
			
			tuplas.add(tupla);
		}

		respuesta.put("resultado", 1);
		respuesta.put("data", tuplas);
		respuesta.put("descripcion", "Se obtuvieron los datos correctamente");
		
	}
	
	@SuppressWarnings("unchecked")
	private void listarIngresoReporte(Ingreso_BE ingreso, Direccion_BE direccion,JSONObject respuesta,HttpServletRequest request, JSONObject requestAngular) {
		List<Ingreso_BE> resultados;
		
		JSONArray tuplas = new JSONArray();
		JSONObject js = requestAngular;
		
		String in_fecha_entrada	= String.valueOf(js.get("fecha")).trim();
		String str_numero_calle_av = String.valueOf(js.get("numeroFiltro")).trim();
		String str_calle_av 	= String.valueOf(js.get("avenidaCalleFiltro")).trim();
		String str_num_casa		= String.valueOf(js.get("numeroCasaFiltro")).trim();
		
		
		if(!in_fecha_entrada.equalsIgnoreCase("null") && !in_fecha_entrada.equalsIgnoreCase("")){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		    Date parsedDate;
			try {
				parsedDate = dateFormat.parse(in_fecha_entrada + " 00:00:00");
				Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
				ingreso.in_fecha_entrada = timestamp;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(!str_numero_calle_av.equalsIgnoreCase("null") && !str_numero_calle_av.equalsIgnoreCase("")){
			direccion.di_numero_calle_av = (str_numero_calle_av);
		}
		if(!str_calle_av.equalsIgnoreCase("null") && !str_calle_av.equalsIgnoreCase("") && direccion.di_numero_calle_av != null){
			direccion.di_calle_av = Integer.parseInt(str_calle_av);
		}
		if(!str_num_casa.equalsIgnoreCase("null") && !str_num_casa.equalsIgnoreCase("")){
			direccion.di_num_casa = (str_num_casa);
		}
		
		resultados= Ingreso_BLL.listarPorFechaDireccion(ingreso, direccion);
		
		
		for (Ingreso_BE ing : resultados) {
			JSONObject tupla = new JSONObject();
			
			tupla.put("in_ingreso", String.valueOf(ing.in_ingreso));
			tupla.put("in_placa", String.valueOf(ing.in_placa));
			tupla.put("in_direccion", String.valueOf(ing.in_texto_direccion));
			tupla.put("in_imagen_placa", String.valueOf(ing.in_imagen_placa));
			tupla.put("in_imagen_rostro", String.valueOf(ing.in_imagen_rostro));
			tupla.put("in_imagen_dpi", String.valueOf(ing.in_imagen_dpi));
			tupla.put("in_fecha_entrada", String.valueOf(General_BLL.formatearFechaConHora(ing.in_fecha_entrada)));
			tupla.put("in_usuario", String.valueOf(ing.in_usuario));
			tupla.put("in_ingreso", String.valueOf(ing.in_estado));
			
			tuplas.add(tupla);
		}

		respuesta.put("resultado", 1);
		respuesta.put("data", tuplas);
		respuesta.put("descripcion", "Se obtuvieron los datos correctamente");
		
	}
}

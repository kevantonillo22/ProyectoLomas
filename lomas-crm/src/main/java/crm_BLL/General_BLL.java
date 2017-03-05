package crm_BLL;

import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.io.UnsupportedEncodingException;
//import java.io.Writer;
import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.Properties;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/*import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;*/
import javax.servlet.http.HttpServletRequest;

import crm_BE.General_BE;
import crm_BE.Rol_funcion_BE;
import crm_BE.Sesion_BE;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/*********************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 23/04/2014
 * @FechaModificacion 23/04/2014
 * @Descripcion Contiene rutinas generales utilizadas en el proyecto.
 ********************************************************************/

@SuppressWarnings("deprecation")
public class General_BLL {

	/*********************************************************************
	 * @author rbarrios
	 * @since 1/05/2014
	 * @return Connection
	 * @Descripcion Realiza la conexión con la base de datos.
	 ********************************************************************/
	public static Connection obtenerConexion() {
		// Declaración de variables
		Connection conexion;

		// Inicialización de variables
		conexion = null;

		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager
					.getConnection(
							General_BE.DB_CADENA_CONEXION,
							General_BE.DB_USUARIO_CONEXION, General_BE.DB_PASSWORD);
			conexion.setAutoCommit(false);
	
		} catch (Exception e) {
			e.printStackTrace();
			conexion = null;
		}
		return conexion;
	}

	/*********************************************************************
	 * @author rbarrios
	 * @since 1/05/2014
	 * @return String
	 * @Descripcion Devuelve la fecha actual del sistema.
	 ********************************************************************/
	public static String obtenerFecha() {
		Date fecha = new Date();
		DateFormat fechaSinHora = new SimpleDateFormat("yyyy-MM-dd");
		return fechaSinHora.format(fecha);
	}

	/*********************************************************************
	 * @author rbarrios
	 * @since 1/05/2014
	 * @return String
	 * @Descripcion Devuelve la fecha y hora actual del sistema.
	 ********************************************************************/
	public static String obtenerFechaHora() {
		Date fecha = new Date();
		DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fechaHora.format(fecha);
	}

	
	/*********************************************************************
	 * @author rbarrios
	 * @since 1/05/2014
	 * @return String
	 * @Descripcion Devuelve la fecha y hora actual del sistema.
	 ********************************************************************/
	public static String obtenerFechaHoraSegundos() {
		Date fecha = new Date();
		DateFormat fechaHora = new SimpleDateFormat("yyMMddHHmmssSSS");
		return fechaHora.format(fecha);
	}
	
	/*********************************************************************
	 * @author rbarrios
	 * @since 1/05/2014
	 * @return Boolean
	 * @Descripcion Valida si el usuario tiene permiso a una función.
	 ********************************************************************/
	public static boolean tienePermiso(Sesion_BE sesion, int funcion) {
		// Declaración de variables
		Rol_funcion_BE rol_funcion;

		// Inicialización de variables
		rol_funcion = new Rol_funcion_BE();

		// Definir parámetros
		rol_funcion.rf_rol = sesion.se_rol;
		rol_funcion.rf_funcion = funcion;

		if (Rol_funcion_BLL.listar(rol_funcion).isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	/*********************************************************************
	 * @author rbarrios
	 * @since 1/05/2014
	 * @return String
	 * @Descripcion Construye el string para el menÃº del sitio administrativo.
	 ********************************************************************/
	public static String obtieneMenu(Sesion_BE sesion) {
		// Declaración de variables
		List<Rol_funcion_BE> lista_rol_funcion;
		Rol_funcion_BE rol_funcion;
		Rol_funcion_BE menu_raiz;
		String respuesta;
		String temp_respuesta;
		int contador;

		// Inicialización de variables
		rol_funcion = new Rol_funcion_BE();
		menu_raiz = null;
		contador = 0;
		respuesta = "";
		temp_respuesta = "";

		// Definir parámetros
		rol_funcion.rf_rol = sesion.se_rol;

		lista_rol_funcion = Rol_funcion_BLL.listar(rol_funcion);

		if (!lista_rol_funcion.isEmpty()) {

			for (Rol_funcion_BE temp_rol_funcion : lista_rol_funcion) {
				if (temp_rol_funcion.rf_visible == 1) {
					if (temp_rol_funcion.rf_padre == -9999) {
						if (menu_raiz != null) {
							if (contador > 1) {
								respuesta = respuesta
										+ "<li class=\"panel\">\r\n"
										+ "\t<a href=\"javascript:;\" data-parent=\"#side\" data-toggle=\"collapse\" class=\"accordion-toggle\" data-target=\"#menu-"
										+ menu_raiz.rf_funcion
										+ "\">\r\n"
										+ "\t\t<i class=\"fa "
										+ menu_raiz.rf_icono
										+ "\"></i> "
										+ menu_raiz.rf_nombre
										+ " <i class=\"fa fa-caret-down\"></i>\r\n"
										+ "\t</a>\r\n"
										+ "\t<ul class=\"collapse nav\" id=\"menu-"
										+ menu_raiz.rf_funcion + "\">\r\n"
										+ temp_respuesta + "\t</ul>\r\n"
										+ "</li>\r\n";
							} else {
								if (menu_raiz.rf_link != null) {
									respuesta = respuesta + "<li>\r\n"
											+ "\t<a href=\"" + menu_raiz.rf_link
											+ "\">\r\n" + "\t\t<i class=\"fa "
											+ menu_raiz.rf_icono + "\"></i>"
											+ menu_raiz.rf_nombre + "\r\n"
											+ "\t</a>\r\n" + "</li>\r\n";	
								}
							}
						}
						menu_raiz = temp_rol_funcion;
						temp_respuesta = "";
						contador = 1;
					} else {
						contador = contador + 1;
						temp_respuesta = temp_respuesta
								+ "\t\t<li>\r\n"
								+ "\t\t\t<a href=\""
								+ temp_rol_funcion.rf_link
								+ "\">\r\n"
								+ "\t\t\t\t<i class=\"fa fa-angle-double-right\"></i>"
								+ temp_rol_funcion.rf_nombre + "\r\n"
								+ "\t\t\t</a>\r\n" + "\t\t</li>\r\n";
					}
				}
			}

			if (menu_raiz != null) {
				if (contador > 1) {
					respuesta = respuesta
							+ "<li class=\"panel\">\r\n"
							+ "\t<a href=\"javascript:;\" data-parent=\"#side\" data-toggle=\"collapse\" class=\"accordion-toggle\" data-target=\"#menu-"
							+ menu_raiz.rf_funcion + "\">\r\n"
							+ "\t\t<i class=\"fa " + menu_raiz.rf_icono
							+ "\"></i> " + menu_raiz.rf_nombre
							+ "\t<i class=\"fa fa-caret-down\"></i>\r\n"
							+ "\t</a>\r\n"
							+ "\t<ul class=\"collapse nav\" id=\"menu-"
							+ menu_raiz.rf_funcion + "\">\r\n" + temp_respuesta
							+ "\t</ul>\r\n" + "</li>\r\n";
				} else {
					if (menu_raiz.rf_link != null) {
							respuesta = respuesta + "<li>\r\n" + "\t<a href=\""
							+ menu_raiz.rf_link + "\">\r\n"
							+ "\t\t<i class=\"fa " + menu_raiz.rf_icono
							+ "\"></i>" + menu_raiz.rf_nombre + "\r\n"
							+ "\t</a>\r\n" + "</li>\r\n";
					}
				}
			}
		}
		
		if (sesion.se_rol != -2 && sesion.se_rol != -3) {
			respuesta = respuesta + "<li>\r\n"
            + "<a href=\"/cambiarContrasenia.jsp\">\r\n"
            +    "<i class=\"fa fa-lock\"></i> Cambiar contraseña\r\n"
            + "</a>\r\n"
            + "</li>\r\n";
		}

		return respuesta;
	}

	
	public static String obtenerCadenaAleatoria(){
		String cadena = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();

		
		{
		   StringBuilder sb = new StringBuilder( 6 );
		   for( int i = 0; i < 6; i++ ) 
		      sb.append( cadena.charAt( rnd.nextInt(cadena.length()) ) );
		   return sb.toString();
		
		}
		
	}
	
	
	public static String generarContrasenia(String parametro) {
		String cadena = parametro + "F0uS4C";
		String valor = "";
		try {
			String keyEncrypt = "ODONTOLOGIA2013W"; // 128 bit key ;
			// Create key and cipher
			Key aesKey = new SecretKeySpec(keyEncrypt.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			// encrypt the text
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);

			byte[] encrypted = cipher.doFinal(cadena.getBytes());
			String hash = "";
			for (byte aux : encrypted) {
				int b = aux & 0xff;
				if (Integer.toHexString(b).length() == 1)
					hash += "0";
				hash += Integer.toHexString(b);
			}
			valor = hash.toUpperCase();

		} catch (Exception e) {
			// return false;
		}
		return valor;
	}

	/*public static String solicitudGET(String url) {
		String respuesta = "";
		try {
			@SuppressWarnings("resource")
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			HttpEntity entidad = response.getEntity();
			respuesta = EntityUtils.toString(entidad, "UTF-8");
		} catch (Exception e) {
			// Error en el servicio
		}
		return respuesta;
	}*/
	
	//SE DUPLICO ESTE METODO PARA PODER RETORNAR UN TEXTO DE ERROR
	//POR SI EL SERVICIO FALLABA, NO SE AGREGO EN EL METODO ANTERIOR
	//PARA EVITAR QUE SI OTRO PROCESO UTILIZABA EL METODO FALLARA
	/*public static String solicitudGET2(String url) {
		String respuesta = "";
		try {
			@SuppressWarnings("resource")
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			HttpEntity entidad = response.getEntity();
			respuesta = EntityUtils.toString(entidad, "UTF-8");
		} catch (Exception e) {
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			String err = writer.toString();
			
			respuesta = "error%" + err;
		}
		return respuesta;
	}*/
	/*
	public static void sendOneMail(String user, String pass, String to, String asunto, String mensaje){
		Properties props = new Properties();

		// Nombre del host de correo, es smtp.gmail.com
		props.setProperty("mail.smtp.host", "smtp.gmail.com");

		// TLS si está disponible
		props.setProperty("mail.smtp.starttls.enable", "true");

		// Puerto de gmail para envio de correos
		props.setProperty("mail.smtp.port","587");

		// Nombre del usuario
		//props.setProperty("mail.smtp.user", "asdfasdfasdf@gmail.com");

		// Si requiere o no usuario y password para conectarse.
		props.setProperty("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props);
		session.setDebug(false);
		
		MimeMessage message = new MimeMessage(session);
		
		try {
			// Quien envia el correo
			message.setFrom(new InternetAddress(user, "Sistema de gestión FOUSAC"));
			// A quien va dirigido
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(asunto);
			message.setText(mensaje, "utf-8", "html");
			
			Transport t = session.getTransport("smtp");
			t.connect(user,pass);
			t.sendMessage(message,message.getAllRecipients());
			t.close();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}*/
	/*
	public static boolean sendMasiveMail(String user, String pass, ArrayList<String> to, String asunto, String mensaje){
		boolean envioCorrecto = true;
		
		Properties props = new Properties();

		// Nombre del host de correo, es smtp.gmail.com
		props.setProperty("mail.smtp.host", "smtp.gmail.com");

		// TLS si está disponible
		props.setProperty("mail.smtp.starttls.enable", "true");

		// Puerto de gmail para envio de correos
		props.setProperty("mail.smtp.port","587");

		// Nombre del usuario
		props.setProperty("mail.smtp.user", user);

		// Si requiere o no usuario y password para conectarse.
		props.setProperty("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
		
		MimeMessage message = new MimeMessage(session);
		
		
		try {
			message.addHeader("Content-type", "text/HTML; charset=UTF-8");
			message.addHeader("format", "flowed");
			message.addHeader("Content-Transfer-Encoding", "8bit");
			
			// Quien envia el correo
			message.setFrom(new InternetAddress(user, "Sistema de gestión FOUSAC"));
			// A quien va dirigido
			int tam = to.size();
			for(int i = 0; i <= tam - 1;i++){
				System.out.println(i +" debug-->"+to.get(i));
				if(i == 0){
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to.get(i)));
				}else{
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(to.get(i)));
				}
				
				//message.addRecipient(Message.RecipientType.BCC, null);
			}
			message.setSubject(asunto);
			message.setText(mensaje, "utf-8", "html");
			
			Transport t = session.getTransport("smtp");
			t.connect(user,pass);
			t.sendMessage(message,message.getAllRecipients());
			t.close();
		} catch (AddressException e) {
			envioCorrecto = false;
			e.printStackTrace();
		} catch (MessagingException e) {
			envioCorrecto = false;
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			envioCorrecto = false;
			e.printStackTrace();
		}
		
		return envioCorrecto;
	}*/
	
	/*********************************************************************
	 * @author kcardona
	 * @since 01/10/2015
	 * @return ArrayList<String>
	 * @Descripcion Obtiene correos del servicio web de control academico.
	 ********************************************************************/
	/*public static ArrayList<String> obtenerCorreos(int opcion){
		ArrayList<String> resultado = new ArrayList<String>();
		
		try{
			String resp = "";
			switch(opcion){
			case 1:
				//4to año
				resp = General_BLL.solicitudGET2(General_BE.IP_CONTROL_ACADEMICO + "/WS/controlAcademico/estudianteGrado/197AAB75663FDC6A2603C5654DE84CC9/4").replace("&lt;","<").replace("&gt;", ">");
				break;
				
			case 2:
				//5to año
				resp = General_BLL.solicitudGET2(General_BE.IP_CONTROL_ACADEMICO + "/WS/controlAcademico/estudianteGrado/94EDD4227CD6EC48A854ADD024F60C82/5").replace("&lt;","<").replace("&gt;", ">");
				
				break;
				
			case 3:
				//PRC
				resp = General_BLL.solicitudGET2(General_BE.IP_CONTROL_ACADEMICO + "/WS/controlAcademico/estudianteGrado/458C76443B67D994F5EC0CA2D7727D3C/5990").replace("&lt;","<").replace("&gt;", ">");
				
				break;
			}
			
			if(!resp.contains("error")){
				// Parseo de la respuesta
				DocumentBuilderFactory dbFactory2 = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder2 = dbFactory2.newDocumentBuilder();
				Document doc2 = dBuilder2.parse(new InputSource(new ByteArrayInputStream(resp.getBytes("UTF-8"))));
				doc2.getDocumentElement().normalize();
				
				// Obtener el valor del tag Válido
				NodeList lista_nodos2 = doc2.getElementsByTagName("estudiante");
				for(int i = 0; i <= lista_nodos2.getLength() - 1; i++){
					Node nodo2 = lista_nodos2.item(i);
					Element elemento2 = (Element) nodo2;
					String correo = elemento2.getElementsByTagName("correo").item(0).getTextContent().toString();
					resultado.add(correo);
				}
			}else{
				//si hubo error se reporta al correo de soporte
				String[] err = resp.split("%");
				General_BLL.sendOneMail(General_BE.CORREO_ORIGEN, General_BE.CORREO_ORIGEN_PASS, General_BE.CORREO_DESTINATARIO, General_BE.CORREO_ASUNTO, General_BE.CORREO_MENSAJE + "<br><p>" + err[1] + "<p>");
			}
			
		}
		catch(Exception ex){
			
		}
		
		return resultado;
		
	}*/
	
	public static String iniciarMayuscula(String cadena)
	{
		String resultado = "";

        if(cadena.trim().length()>1){
			String[] cadena_separada = cadena.trim().split(" ");
	        for (int i = 0; i < cadena_separada.length; i++) {
	            resultado = resultado + " "+ cadena_separada[i].substring(0, 1).toUpperCase() +cadena_separada[i].substring(1).toLowerCase();;
	        }
        }
		return resultado;
	}

	public static String obtenerHash(String entrada, String algoritmo){
		String cadenaHASH=null;
		String plaintext = entrada;
		MessageDigest m;
		try {
			m = MessageDigest.getInstance(algoritmo);
			
			m.reset();
			m.update(plaintext.getBytes());
			byte[] resumen = m.digest();
			BigInteger bigInt = new BigInteger(1,resumen);
			 cadenaHASH = bigInt.toString(16);

			while(cadenaHASH.length() < 32 ){
			  cadenaHASH = "0"+cadenaHASH;
			}

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cadenaHASH;
	}

	public static boolean verificarFirmaSolicitud(List<String> clave,List<String> valor, String hashEntrada, String contrasena){
		String texto="";
		String hash="";
		int tamanoLista=clave.size();
		
		//Concatenar claves y valores
		for(int i=0;i<tamanoLista;i++){
			texto+=clave.get(i)+valor.get(i);
		}
		//Concatenar la firma
		texto+=contrasena;
		hash=obtenerHash(texto, "MD5");
		if(hash.equalsIgnoreCase(hashEntrada))
			return true;
		return false;
	}
	
	public static boolean eliminarArchivo(String nombreArchivo){
		boolean resultado=false;
		try{	 

    		java.io.File file = new java.io.File(nombreArchivo);
    		if(file.exists())
    			if(file.delete())
    	    		resultado=true;
 
    	}catch(Exception e){
 
    		e.printStackTrace();
 
    	}
		return resultado;
	}

	public static boolean crearArchivoImagen(String nombreArchivo, String contenido){
		File archivo= new File(nombreArchivo);
		byte[] bytes = decodificarImagen(contenido.replace("data:image/png;base64,", ""));

		// Write a image byte array into file system
		FileOutputStream salida;
		try {
			//Sobreescribir
			salida = new FileOutputStream(archivo,false);
			salida.write(bytes);
			salida.close();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			
		}
		
		
	}
	
	public static byte[] decodificarImagen(String imageDataString) {
		return Base64.decodeBase64(imageDataString.getBytes());
	}
	
	/*********************************************************************
	 * @author kcardona
	 * @since 08/09/2015
	 * @return String
	 * @Descripcion Formatea una fecha.
	 ********************************************************************/
	public static String formatearFechaConHora(Timestamp fecha){
		String day = fecha.getDate() + "";
		String month = (fecha.getMonth()+1) + "";
		String year = (fecha.getYear() + 1900) + "";
		
		String hour = fecha.getHours() + "";
		String minute = fecha.getMinutes() + "";
		String second = fecha.getSeconds() + "";
		
		String resultado = "";
		
		if(Integer.parseInt(day)<10){day = "0"+ day;}
		if(Integer.parseInt(month)<10){month = "0"+ month;}
		if(Integer.parseInt(year)<10){year = "0"+ year;}
		if(Integer.parseInt(hour)<10){hour = "0"+ hour;}
		if(Integer.parseInt(minute)<10){minute = "0"+ minute;}
		if(Integer.parseInt(second)<10){second = "0"+ second;}
		
		resultado = day + "/" + month + "/" + year + " " + hour + ":" + minute + ":" + second;
		
		return resultado;
	}
	
	/*********************************************************************
	 * @author kcardona
	 * @since 08/09/2015
	 * @return String
	 * @Descripcion Formatea una fecha.
	 ********************************************************************/
	public static String formatearFechaSinHoraUI(Timestamp fecha){
		String day = fecha.getDate() + "";
		String month = (fecha.getMonth()+1) + "";
		String year = (fecha.getYear() + 1900) + "";
		
		String resultado = "";
		
		if(Integer.parseInt(day)<10){day = "0"+ day;}
		if(Integer.parseInt(month)<10){month = "0"+ month;}
		if(Integer.parseInt(year)<10){year = "0"+ year;}
		
		resultado = day + "/" + month + "/" + year;
		
		return resultado;
	}
	
	/*********************************************************************
	 * @author kcardona
	 * @since 08/09/2015
	 * @return String
	 * @Descripcion Formatea una hora.
	 ********************************************************************/
	public static String formatearHora(Time hora){
		String hour = hora.getHours() + "";
		String minute = hora.getMinutes() + "";
		String second = hora.getSeconds() + "";
		
		String resultado = "";
		
		if(Integer.parseInt(hour)<10){hour = "0"+ hour;}
		if(Integer.parseInt(minute)<10){minute = "0"+ minute;}
		if(Integer.parseInt(second)<10){second = "0"+ second;}
		
		resultado = hour + ":" + minute + ":" + second;
		
		return resultado;
	}
	
	/*********************************************************************
	 * @author kcardona
	 * @since 08/09/2015
	 * @return String
	 * @Descripcion Obtiene la fecha de un campo de tipo timestamp.
	 ********************************************************************/
	public static String obtenerFecha(Timestamp fecha){
		String day = fecha.getDate() + "";
		String month = (fecha.getMonth()+1) + "";
		String year = (fecha.getYear() + 1900) + "";

		
		String resultado = "";
		
		if(Integer.parseInt(day)<10){day = "0"+ day;}
		if(Integer.parseInt(month)<10){month = "0"+ month;}
		if(Integer.parseInt(year)<10){year = "0"+ year;}

		
		resultado = year + "-" + month + "-" + day;
		
		return resultado;
	}
	
	/*********************************************************************
	 * @author kcardona
	 * @since 09/09/2015
	 * @return String
	 * @Descripcion Obtiene la hora de un campo de tipo timestamp.
	 ********************************************************************/
	public static String obtenerHora(Timestamp fecha){
		String hour = fecha.getHours() + "";
		String minute = fecha.getMinutes() + "";
		String second = fecha.getSeconds() + "";

		
		String resultado = "";
		
		if(Integer.parseInt(hour)<10){hour = "0"+ hour;}
		if(Integer.parseInt(minute)<10){minute = "0"+ minute;}
		if(Integer.parseInt(second)<10){second = "0"+ second;}

		
		resultado = hour + ":" + minute + ":" + second;
		
		return resultado;
	}
	
	/*********************************************************************
	 * @author kcardona
	 * @since 09/09/2015
	 * @return String
	 * @Descripcion Obtiene la hora de un campo de tipo timestamp.
	 ********************************************************************/
	public static String formatearHoraUI(Timestamp fecha){
		String hour = fecha.getHours() + "";
		String minute = fecha.getMinutes() + "";
		String second = fecha.getSeconds() + "";

		
		String resultado = "";
		
		if(Integer.parseInt(hour)<10){hour = "0"+ hour;}
		if(Integer.parseInt(minute)<10){minute = "0"+ minute;}
		if(Integer.parseInt(second)<10){second = "0"+ second;}

		resultado = hour + ":" + minute + ":" + second;
		
		String originalString = "2010-07-14 " + resultado;
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String newString = new SimpleDateFormat("hh:mm a").format(date);
		
		return newString;
	}
	
	/*********************************************************************
	 * @author kcardona
	 * @since 25/09/2016
	 * @return JSONObject
	 * @Descripcion Parsea el request de los parametros provenidos de angular.
	 ********************************************************************/
	public static JSONObject ParsearRequestAngular(HttpServletRequest request){
		InputStreamReader isr;
		JSONObject json = null;
		try {
			StringBuffer sb = new StringBuffer();
			isr = new InputStreamReader(request.getInputStream());
			BufferedReader in = new BufferedReader(isr);
			String line = null;
			while ((line = in.readLine()) != null)
			{
				sb.append(line);
  			}
			JSONParser parser = new JSONParser();
		    JSONObject joUser = (JSONObject) parser.parse(sb.toString());
			json = joUser;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	

}

package crm_UI;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import crm_BE.General_BE;
import crm_BE.Parametro_BE;
import crm_BE.Parametros;
import crm_BE.Sesion_BE;
import crm_BE.Usuario_BE;
import crm_BLL.General_BLL;
import crm_BLL.Parametro_BLL;
import crm_BLL.Usuario_BLL;

/**
 * Servlet implementation class Ingreso
 */
public class Ingreso extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Ingreso() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/* Codigo para evitar errores por seguridad */
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		// Declaración de variables
		List<Usuario_BE> lst_usuario = new ArrayList<Usuario_BE>();
		Usuario_BE usuario_parametro = new Usuario_BE();
		JSONObject respuesta_json = new JSONObject();
		Usuario_BE usuario = new Usuario_BE();
		Sesion_BE sesion = new Sesion_BE();
		boolean exitoso = false;
		String respuesta = "El usuario o contrase�a ingresados son incorrectos";

		// Inicialización de parámetros
		usuario_parametro.us_login = String.valueOf(request.getParameter("usuario"));

		try {
			// Consulta de usuario
			lst_usuario = Usuario_BLL.listar(usuario_parametro);

			if (lst_usuario.size() != 0) {
				// Recuperamos el nodo del usuario
				usuario = lst_usuario.get(0);

				// Si el usuario existe se comprueba que no esté bloqueado
				if (usuario.us_estado == 1) {
					// Se verifica el tipo
					switch (usuario.us_tipo) {
					case 1:
						// Administrativo
						usuario_parametro.us_contrasenia = String.valueOf(request.getParameter("contrasenia"));
						if (!Usuario_BLL.listar(usuario_parametro).isEmpty()) {
							// Login exitoso
							exitoso = true;
						}
						break;
					case 2:
						// Estudiante
						//exitoso = validar_login_conaca(lst_usuario.get(0).us_login, String.valueOf(request.getParameter("contrasenia")), "estudianteapp");
						System.out.println("este es " + exitoso);
						exitoso = true;
						break;
					case 3:
						// Docente
						//exitoso = validar_login_conaca(lst_usuario.get(0).us_login, String.valueOf(request.getParameter("contrasenia")), "docenteapp");
						exitoso=true;
						break;
					}
				} else {
					// Usuario bloqueado
					respuesta = "Usuario bloqueado";
				}
			}

			// Valida si el login fue exitoso
			if (exitoso) {
				// Parámetro máximo tiempo de inactividad según el rol
				Parametro_BE parametro_inactividad = new Parametro_BE();
				parametro_inactividad.pa_estado = 1;
				
				// Parámetro si se valida o no con huella digital
				//Parametro_BE parametro_valida_huella = new Parametro_BE();
				//parametro_valida_huella.pa_codigo_parametro = Parametros.VALIDA_HUELLA;
				//parametro_valida_huella.pa_estado = 1;
				//sesion.se_valida_huella = Integer.valueOf(Parametro_BLL.listar(parametro_valida_huella).get(0).pa_valor.trim());
				
				sesion.se_login = usuario.us_login;
				sesion.se_rol = usuario.us_rol;
				sesion.se_usuario = usuario.us_usuario;
				
				switch (usuario.us_tipo) {
				default:
					// Obtener datos de bd
					sesion.se_nombres = General_BLL.iniciarMayuscula(usuario.us_nombres);
					sesion.se_apellidos = General_BLL.iniciarMayuscula(usuario.us_apellidos);
					sesion.se_nombre = usuario.us_nombres + " " + usuario.us_apellidos;
					sesion.se_ruta_foto = General_BE.ROOT_PATH + "imagenes?t=1&f=" + usuario.us_foto;
					
					//Tiempo máximo de inactividad
					parametro_inactividad.pa_codigo_parametro = Parametros.TIEMPO_MAXIMO_INACTIVIDAD_ADMINISTRATIVO;
					break;
				}
				
				// Cargar el menú según el rol
				sesion.se_menu = General_BLL.obtieneMenu(sesion);
				HttpSession session = request.getSession();
				session.setAttribute("sesion", sesion);
				session.setMaxInactiveInterval(Integer.valueOf(Parametro_BLL.listar(parametro_inactividad).get(0).pa_valor.trim()) * 60);

				// Respuesta JSON
				String prevurl = request.getParameter("prevurl");
				if (prevurl != null && !prevurl.isEmpty() && prevurl.compareTo("null") != 0) {
					respuesta_json.put("urlRetorno", request.getParameter("prevurl"));
					respuesta_json.put("resultado", 2);
				} else {
					respuesta_json.put("resultado", "1");
				}
			} else {
				// Respuesta JSON
				respuesta_json.put("resultado", "-1");
				respuesta_json.put("descripcion", respuesta);
			}
		} catch (Exception e) {
			// Respuesta JSON
			e.printStackTrace();
			respuesta_json.put("resultado", "-1");
			respuesta_json.put("descripcion", "Error de conexi�n. Vuelve a intentarlo en unos minutos.");
		}

		out.print(respuesta_json.toJSONString());
	}

	private boolean validar_login_conaca(String usuario, String contrasenia, String rol){
		// Variable global
		boolean exitoso = false;
		try {
			String url = "http://odontologia.desarrollo.usac.edu.gt/Login/security_manual";
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			
			// Credenciales de usuario
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("username", usuario));
			nameValuePairs.add(new BasicNameValuePair("password", contrasenia));
			nameValuePairs.add(new BasicNameValuePair("role",rol));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			// Llamada al servicio
			HttpResponse respuesta_http = client.execute(post);
			HttpEntity entidad = respuesta_http.getEntity(); 
			
			String resultado = EntityUtils.toString(entidad,"UTF-8"); 

			// Parseo de la respuesta
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(resultado.getBytes("UTF-8"))));
			doc.getDocumentElement().normalize();
			
			// Obtener el valor del tag Válido
			NodeList lista_nodos = doc.getElementsByTagName("Login");
			Node nodo = lista_nodos.item(0);
			Element elemento = (Element) nodo;
			if (elemento.getElementsByTagName("Válido").item(0).getTextContent().equalsIgnoreCase("si")) {
				exitoso = true;
			}
		} catch (Exception e) {
			// Error de servicio
		}
		return exitoso;
	}	
}
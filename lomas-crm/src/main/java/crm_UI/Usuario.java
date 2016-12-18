package crm_UI;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.InputSource;

import crm_BE.Funciones;
import crm_BE.Resultado_BE;
import crm_BE.Sesion_BE;
import crm_BE.Usuario_BE;
import crm_BLL.General_BLL;
import crm_BLL.Usuario_BLL;

/**
 * Servlet implementation class Usuario
 */
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Usuario() {
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
		// Codigo para evitar errores por seguridad
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.setContentType("application/json");
		// Declaración de variables
		JSONObject respuesta_json = new JSONObject();
		JSONArray tuplas = new JSONArray();
		Resultado_BE resultado = new Resultado_BE();
		Usuario_BE usuario_parametro = new Usuario_BE();
		List<Usuario_BE> resultados;
		Sesion_BE sesion = new Sesion_BE();
		PrintWriter out = response.getWriter();

		// Valida la sesion
		HttpSession sesionhttp = request.getSession();

		if (sesionhttp.getAttribute("sesion") == null) {
			// Sesión vencida
			sesionhttp.invalidate();
			respuesta_json.put("resultado", "-100");
			respuesta_json.put("descripcion", "Caduc� la sesi�n");
			respuesta_json.put("data", tuplas);
		} else {
			// Si la Sesión sigue activa
			sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");

			/****************************************************** 
			 * INICIA SWITCH SEGÚN EL TIPO DE OPERACIÓN A REALIZAR 
			 ******************************************************/
			switch (Integer.valueOf(request.getParameter("op").toString())) {
			case 1:
				// Creación de usuarios
				if (!General_BLL.tienePermiso(sesion, Funciones.CREAR_USUARIO)) {
					respuesta_json.put("resultado", "-101");
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Llenar el objeto con la información del formulario
					usuario_parametro.us_rol = Integer.valueOf(request.getParameter("rol").trim());
					usuario_parametro.us_login = String.valueOf(request.getParameter("usuario").trim());
					usuario_parametro.us_nombres = String.valueOf(request.getParameter("nombres").trim());
					usuario_parametro.us_apellidos = String.valueOf(request.getParameter("apellidos").trim());
					usuario_parametro.us_contrasenia = usuario_parametro.us_login;
					usuario_parametro.us_email = String.valueOf(request.getParameter("email").trim());
					usuario_parametro.us_descripcion = String.valueOf(request.getParameter("descripcion").trim());

					// Crear un rol a traves de la BLL
					resultado = Usuario_BLL.crear(usuario_parametro, sesion);

					// Respuesta JSON
					respuesta_json.put("resultado", resultado.re_codigo);
					respuesta_json.put("descripcion", resultado.re_descripcion);
				}
				break;
			case 2:
				// Lista de usuarios por búsqueda
				//Usuarios administrativos
				
				if (!General_BLL.tienePermiso(sesion, Funciones.LISTAR_USUARIO)) {
					respuesta_json.put("resultado", "-101");
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Obtiene los parámetros de busqueda
					if (request.getParameter("filtro") != null) {
						usuario_parametro.us_filtro_busqueda = String.valueOf(request.getParameter("filtro").trim());
						usuario_parametro.us_tipo= Short.parseShort(String.valueOf(request.getParameter("tipo").trim()));
					}

					resultados = Usuario_BLL.listar_busqueda(usuario_parametro);

					for (Usuario_BE us : resultados) {
						JSONObject tupla = new JSONObject();
						tupla.put("codigo", String.valueOf(us.us_usuario));
						tupla.put("usuario", String.valueOf(us.us_login));
						tupla.put("nombres", us.us_nombres);
						tupla.put("apellidos", us.us_apellidos);
						tupla.put("rol", us.us_rol_nombre);
						tupla.put("email", us.us_email);
						tupla.put("imagen", us.us_foto);
						
						if(us.us_estado==1){
							tupla.put("estado", "<i style=\"color:green;\" title=\"El usuario est� desbloqueado\" class=\"fa fa-unlock\"></i>");
						}else{
							tupla.put("estado", "<i style=\"color:red;\" title=\"El usuario est� bloqueado\" class=\"fa fa-lock\"></i>");
						}
						
						tupla.put("descripcion", us.us_descripcion);
						tuplas.add(tupla);
					}

					respuesta_json.put("data", tuplas);
				}
				break;
			case 3:
				// Cambio de estado
				if (!General_BLL.tienePermiso(sesion,Funciones.CAMBIAR_ESTADO_USUARIO)) {
					respuesta_json.put("resultado", "-101");
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					String codigo = String.valueOf(request.getParameter("id").trim());
					usuario_parametro.us_usuario = Integer.valueOf(codigo);
					resultado = Usuario_BLL.cambiar_estado(usuario_parametro, sesion);
					respuesta_json.put("resultado", resultado.re_exitoso);
					respuesta_json.put("descripcion", resultado.re_descripcion);
				}
				break;
			case 4:
				// Restablecer contraseña
				if (!General_BLL.tienePermiso(sesion,Funciones.RESTABLECER_CONTRASENIA_USUARIO)) {
					respuesta_json.put("resultado", "-101");
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					String codigo = String.valueOf(request.getParameter("id").trim());
					usuario_parametro.us_usuario = Integer.valueOf(codigo);
					resultado = Usuario_BLL.restablecer_contrasenia(usuario_parametro, sesion);
					respuesta_json.put("resultado", resultado.re_exitoso);
					respuesta_json.put("descripcion", resultado.re_descripcion);
				}
				break;
			case 5:
				// Eliminar usuario
				if (!General_BLL.tienePermiso(sesion,
						Funciones.ELIMINAR_USUARIO)) {
					respuesta_json.put("resultado", "-101");
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					String codigo = String.valueOf(request.getParameter("id").trim());
					usuario_parametro.us_usuario = Integer.valueOf(codigo);
					resultado = Usuario_BLL.eliminar(usuario_parametro, sesion);
					respuesta_json.put("resultado", resultado.re_exitoso);
					respuesta_json.put("descripcion", resultado.re_descripcion);
				}
				break;
			case 6:
				// Lista de usuarios por id
				if (!General_BLL.tienePermiso(sesion, Funciones.LISTAR_USUARIO)) {
					respuesta_json.put("resultado", "-101");
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Obtener filtro de búsqueda
					if (request.getParameter("id") != null) {
						usuario_parametro.us_usuario = Integer.valueOf(request.getParameter("id").trim());
					}

					resultados = Usuario_BLL.listar(usuario_parametro);

					for (Usuario_BE us : resultados) {
						JSONObject tupla = new JSONObject();
						tupla.put("codigo", String.valueOf(us.us_usuario));
						tupla.put("usuario", String.valueOf(us.us_login));
						tupla.put("nombres", us.us_nombres);
						tupla.put("apellidos", us.us_apellidos);
						tupla.put("rol_codigo", String.valueOf(us.us_rol));
						tupla.put("rol", us.us_rol_nombre);
						tupla.put("email", us.us_email);
						tupla.put("estado", us.us_estado_nombre);
						tupla.put("descripcion", us.us_descripcion);
						tuplas.add(tupla);
					}

					respuesta_json.put("data", tuplas);
				}
				break;
			case 7:
				// Modificar usuario
				if (!General_BLL.tienePermiso(sesion, Funciones.MODIFICAR_USUARIO)) {
					respuesta_json.put("resultado", "-101");
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Obtiene el valor de los campos
					usuario_parametro.us_usuario = Integer.valueOf(request.getParameter("id").trim());
					usuario_parametro.us_nombres = String.valueOf(request.getParameter("nombres").trim());
					usuario_parametro.us_apellidos = String.valueOf(request.getParameter("apellidos").trim());
					usuario_parametro.us_rol = Integer.valueOf(request.getParameter("rol").trim());
					usuario_parametro.us_email = String.valueOf(request.getParameter("email").trim());
					usuario_parametro.us_descripcion = String.valueOf(request.getParameter("descripcion").trim());

					// Realiza la modificación
					resultado = Usuario_BLL.modificar(usuario_parametro, sesion);
					respuesta_json.put("resultado", resultado.re_exitoso);
					respuesta_json.put("descripcion", resultado.re_descripcion);
				}
				break;
			case 8:
				// Capturar fotografía
				if (!General_BLL.tienePermiso(sesion, Funciones.CAPTURAR_FOTOGRAFIA_USUARIO)) {
					respuesta_json.put("resultado", "-101");
					respuesta_json.put("descripcion", "Acceso denegado");

				} else {
					usuario_parametro.us_foto = String.valueOf(request.getParameter("foto"));
					usuario_parametro.us_usuario = Integer.valueOf(request.getParameter("id"));
					
					resultado=Usuario_BLL.modificar(usuario_parametro, sesion);
					respuesta_json.put("resultado", resultado.re_codigo);
					respuesta_json.put("descripcion", resultado.re_descripcion);
				}
				break;
			case 11:
				usuario_parametro.us_usuario = sesion.se_usuario;
				usuario_parametro.us_contrasenia = String.valueOf(request.getParameter("anterior").trim());
				usuario_parametro.us_contrasenia_nueva = String.valueOf(request.getParameter("nueva").trim());
				resultado = Usuario_BLL.cambiar_contrasenia(usuario_parametro, sesion);
				respuesta_json.put("resultado", resultado.re_exitoso);
				respuesta_json.put("descripcion", resultado.re_descripcion);
				break;
			
			case 101:
				// Lista de usuarios para dropdown pedidos
				// Obtener filtro de busqueda

				resultados = Usuario_BLL.listar(usuario_parametro);

				for (Usuario_BE us : resultados) {
					JSONObject tupla = new JSONObject();
					tupla.put("codigo", String.valueOf(us.us_usuario));
					tupla.put("usuario", String.valueOf(us.us_login));
					tupla.put("nombres", us.us_nombres);
					tupla.put("apellidos", us.us_apellidos);
					tupla.put("rol_codigo", String.valueOf(us.us_rol));
					tupla.put("rol", us.us_rol_nombre);
					tupla.put("email", us.us_email);
					tupla.put("estado", us.us_estado_nombre);
					tupla.put("descripcion", us.us_descripcion);
					tuplas.add(tupla);
				}

				respuesta_json.put("data", tuplas);
				break;
			default:
				// Error de operación
				break;
			}
		}

		// Respuesta JSON
		out.print(respuesta_json.toJSONString());
	}

}

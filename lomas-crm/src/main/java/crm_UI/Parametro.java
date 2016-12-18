package crm_UI;

import java.io.IOException;
import java.io.PrintWriter;
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
import crm_BLL.General_BLL;
import crm_BLL.Parametro_BLL;
/**
 * Servlet implementation class Parametro
 */
public class Parametro extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Parametro() {
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
		Parametro_BE parametro_filtro = new Parametro_BE();
		List<Parametro_BE> resultados;
		Sesion_BE sesion = new Sesion_BE();
		PrintWriter out = response.getWriter();

		// Valida la sesion
		HttpSession sesionhttp = request.getSession();

		if (sesionhttp.getAttribute("sesion") == null) {
			// Sesión vencida
			sesionhttp.invalidate();
			respuesta_json.put("resultado", "-100");
			respuesta_json.put("descripcion", "Caduc� la sesi�n");
		} else {
			// Si la Sesión sigue activa
			sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");

			/******************************************************
			 * INICIA SWITCH según EL TIPO DE Operación A REALIZAR
			 ******************************************************/
			switch (Integer.valueOf(request.getParameter("op").toString())) {
			case 1:
				// Lista de usuarios por id
				if (!General_BLL.tienePermiso(sesion, Funciones.LISTAR_PARAMETROS)) {
					respuesta_json.put("resultado", "-101");
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Listar roles a traves de la BLL
					if (request.getParameter("id") != null) {
						parametro_filtro.pa_codigo_parametro = Integer.valueOf(request.getParameter("id"));
					}
					
					// Solo mostrar los parámetros vigentes
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
				// Modificar usuario
				if (!General_BLL.tienePermiso(sesion,Funciones.MODIFICAR_PARAMETROS)) {
					respuesta_json.put("resultado", "-101");
					respuesta_json.put("descripcion", "Acceso denegado");
				} else {
					// Obtiene el valor de los campos
					parametro_filtro.pa_codigo_parametro = Integer.valueOf(request.getParameter("id"));
					parametro_filtro.pa_valor = String.valueOf(request.getParameter("valor").trim());

					// Realiza la modificación
					resultado = Parametro_BLL.modificar(parametro_filtro, sesion);
					respuesta_json.put("resultado", resultado.re_exitoso);
					respuesta_json.put("descripcion", resultado.re_descripcion);
				}
				break;
			default:
				// Error de Operación
				break;
			}
		}

		// Respuesta JSON
		out.print(respuesta_json.toJSONString());
	}

}

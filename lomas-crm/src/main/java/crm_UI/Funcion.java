package crm_UI;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import crm_BE.Funcion_BE;
import crm_BE.Funciones;
import crm_BE.Rol_BE;
import crm_BE.Sesion_BE;
import crm_BLL.General_BLL;
import crm_BLL.Funcion_BLL;
import crm_BLL.Rol_funcion_BLL;

/**
 * Servlet implementation class ListarFuncion
 */
public class Funcion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Funcion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/* Codigo para evitar errores por seguridad */
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.setContentType("application/json");

		// Declaración de variables
		JSONObject respuesta_json = new JSONObject();
		
		Funcion_BE funcion = new Funcion_BE();
		funcion.fu_tipo=1;
		List<Funcion_BE> resultados;
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

			// Comprobar si tiene permisos
			if (!General_BLL.tienePermiso(sesion, Funciones.LISTAR_ROLES)) {
				respuesta_json.put("resultado", "-101");
				respuesta_json.put("descripcion", "Acceso Denegado");
			} else {
				// Permiso aceptado
				// Llenar el objeto con la información del formulario
				String str_rol = String.valueOf(request.getParameter("rol"));
				String str_funcion = String.valueOf(request.getParameter("funcion"));
				
				// Obtener atributos numericos
				if (str_funcion !="null") {
					try {
						funcion.fu_funcion = Integer.valueOf(str_funcion);
						funcion.fu_tipo=1;
						System.out.println("hola");
						resultados = Funcion_BLL.listar(funcion);
						// respuesta_json.put("resultado", "1");
						JSONArray tuplas = new JSONArray();
						for (Funcion_BE temp : resultados) {
							JSONObject tupla = new JSONObject();
							tupla.put("funcion", temp.fu_funcion);
							tupla.put("descripcion", temp.fu_descripcion);
							tupla.put("icono", temp.fu_icono);
							tupla.put("link", temp.fu_link);
							tupla.put("nombre", temp.fu_nombre);
							tupla.put("padre", temp.fu_padre);
							tupla.put("tipo", temp.fu_tipo);
							tupla.put("visible", temp.fu_visible);
							tuplas.add(tupla);
						}

						respuesta_json.put("data", tuplas);
						//respuesta_json.put("data", tuplas);
					} catch (Exception e) {
						respuesta_json.put("resultado", "-200");
						respuesta_json.put("descripcion","La operación no fue exitosa");
					}
				} else {
					
					Rol_BE rol = new Rol_BE();
					try {
						rol.ro_rol = Integer.valueOf(str_rol);
							
						resultados = Rol_funcion_BLL.listarFunciones(rol,false);
						JSONArray no_asignadas = new JSONArray();
						for (Funcion_BE temp : resultados) {
							JSONObject tupla = new JSONObject();
							tupla.put("funcion", temp.fu_funcion);
							tupla.put("descripcion", temp.fu_descripcion);
							tupla.put("icono", temp.fu_icono);
							tupla.put("link", temp.fu_link);
							tupla.put("nombre", temp.fu_nombre);
							tupla.put("padre", temp.fu_padre);
							tupla.put("tipo", temp.fu_tipo);
							tupla.put("visible", temp.fu_visible);
							no_asignadas.add(tupla);
						}
						respuesta_json.put("no_asignadas", no_asignadas);
						
						resultados = Rol_funcion_BLL.listarFunciones(rol,true);
						JSONArray asignadas = new JSONArray();
						for (Funcion_BE temp : resultados) {
							JSONObject tupla = new JSONObject();
							tupla.put("funcion", temp.fu_funcion);
							tupla.put("descripcion", temp.fu_descripcion);
							tupla.put("icono", temp.fu_icono);
							tupla.put("link", temp.fu_link);
							tupla.put("nombre", temp.fu_nombre);
							tupla.put("padre", temp.fu_padre);
							tupla.put("tipo", temp.fu_tipo);
							tupla.put("visible", temp.fu_visible);
							asignadas.add(tupla);
						}
						respuesta_json.put("asignadas", asignadas);

					} catch (Exception e) {
						e.printStackTrace();
						respuesta_json.put("resultado", "-200");
						respuesta_json.put("descripcion","La operación no fue exitosa");
					}

				}
			}
		}
		out.print(respuesta_json.toJSONString());
		
	}

}

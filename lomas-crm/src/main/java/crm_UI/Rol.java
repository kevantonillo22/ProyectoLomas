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
import crm_BE.Resultado_BE;
import crm_BE.Rol_BE;
import crm_BE.Sesion_BE;
import crm_BLL.General_BLL;
import crm_BLL.Rol_BLL;

/**
 * Servlet implementation class Rol
 */
public class Rol extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Rol() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		/* Codigo para evitar errores por seguridad */
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		
		// Declaraci√≥n de variables
		JSONObject respuesta = new JSONObject();
		Rol_BE rol_parametro = new Rol_BE();
		Sesion_BE sesion = new Sesion_BE();
		PrintWriter out = response.getWriter();

		// Valida la sesion
		HttpSession sesionhttp = request.getSession();

		if (sesionhttp.getAttribute("sesion") == null) {
			// Sesi√≥n vencida
			sesionhttp.invalidate();
			respuesta.put("resultado", "-100");
			respuesta.put("descripcion", "CaducÛ la sesiÛn");
		} else {
			// Si la Sesi√≥n sigue activa
			sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");

			// Comprobar si tiene permisos

			// Aqui se hace de todo
			int op;

			try {
				op = Integer.valueOf(request.getParameter("op").toString());
				switch (op) {
				case 1:
					// Crear
					if (!General_BLL.tienePermiso(sesion,Funciones.CREACION_DE_ROLES)) {
						respuesta.put("resultado", "-101");
						respuesta.put("descripcion", "Acceso Denegado");
					} else 
						crear(rol_parametro, respuesta, request);
					break;
				case 2:
					// Listar
					if (!(General_BLL.tienePermiso(sesion,Funciones.LISTAR_ROLES)|| General_BLL.tienePermiso(sesion, Funciones.CREAR_USUARIO)|| General_BLL.tienePermiso(sesion, Funciones.MODIFICAR_USUARIO))) {
						respuesta.put("resultado", "-101");
						respuesta.put("descripcion", "Acceso Denegado");
						respuesta.put("data", new JSONArray());
					}
					else
						listar(rol_parametro, respuesta, request);
					break;
				
				case 3:
					//Modificar 
					if (!General_BLL.tienePermiso(sesion, Funciones.MODIFICACION_DE_ROLES)) {
						respuesta.put("resultado", "-101");
						respuesta.put("descripcion", "Acceso Denegado");
					} else 
						modificar(rol_parametro, respuesta, request);
					break;
					
				case 4:
					if (!General_BLL.tienePermiso(sesion, Funciones.ELIMINAR_ROLES)) {
						respuesta.put("resultado", "-101");
						respuesta.put("descripcion", "Acceso Denegado");
					}
					else
						eliminar(rol_parametro, respuesta, request);
					break;
				}

			} catch (Exception e) {
				
			}

		}

		out.print(respuesta.toJSONString());
	}
				
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	private void listar(Rol_BE rol_parametro, JSONObject respuesta,HttpServletRequest request) {
		List<Rol_BE> resultados;
		JSONArray tuplas = new JSONArray();
		rol_parametro.ro_nombre = String
				.valueOf(request.getParameter("nombre"));
		if (rol_parametro.ro_nombre.trim().equalsIgnoreCase("null"))
			rol_parametro.ro_nombre = null;
		String str_ro_rol = String.valueOf(request.getParameter("rol"));

		if (str_ro_rol.trim().equalsIgnoreCase("null"))
			str_ro_rol = "-9999";

		rol_parametro.ro_rol = Integer.valueOf(str_ro_rol);
		rol_parametro.ro_visible = 1;

		// Listar roles a traves de la BLL
		try {

			resultados = Rol_BLL.listar(rol_parametro, "", "");

			for (Rol_BE rol : resultados) {
				JSONObject tupla = new JSONObject();
				tupla.put("id", String.valueOf(rol.ro_rol));
				tupla.put("nombre", rol.ro_nombre);
				tupla.put("descripcion", rol.ro_descripcion);
				tuplas.add(tupla);
			}

			int total = Rol_BLL.contar();
			respuesta.put("recordsTotal", total);
			// cambiar
			respuesta.put("recordsFiltered", total);
			respuesta.put("data", tuplas);

		} catch (Exception e) {
			respuesta.put("resultado", "-200");
			respuesta.put("descripcion", "La operaciÛn no fue exitosa");

		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void crear(Rol_BE rol_parametro,JSONObject respuesta,HttpServletRequest request){
		// Llenar el objeto con la informaci√≥n del formulario
		Resultado_BE resultado = new Resultado_BE();
		HttpSession sesionhttp = request.getSession();
		Sesion_BE sesion = new Sesion_BE();
		sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");
		rol_parametro.ro_descripcion = String.valueOf(request.getParameter("descripcion").trim());
		rol_parametro.ro_nombre = String.valueOf(request.getParameter("nombre").trim());
		rol_parametro.ro_visible = 1; // Visible

		// Crear un rol a traves de la BLL
		resultado = Rol_BLL.crear(rol_parametro, sesion);

		// Respuesta JSON
		respuesta.put("resultado", resultado.re_codigo);
		respuesta.put("descripcion", resultado.re_descripcion);
		
	}
	
	@SuppressWarnings("unchecked")
	private void modificar(Rol_BE rol_parametro,JSONObject respuesta,HttpServletRequest request)
	
	{
		HttpSession sesionhttp = request.getSession();
		Sesion_BE sesion = new Sesion_BE();
		Resultado_BE resultado = new Resultado_BE();
		sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");
		// Llenar el objeto con la informaci√≥n del formulario
		rol_parametro.ro_descripcion = String.valueOf(request
				.getParameter("descripcion").trim());
		rol_parametro.ro_nombre = String.valueOf(request
				.getParameter("nombre").trim());
		rol_parametro.ro_visible = 1; // Visible
		rol_parametro.ro_rol = Integer.valueOf(request
				.getParameter("rol").trim());
		
		// Crear un rol a traves de la BLL
		resultado = Rol_BLL.modificar(rol_parametro, sesion);

		// Respuesta JSON
		respuesta.put("resultado", resultado.re_codigo);
		respuesta.put("descripcion", resultado.re_descripcion);
		
	}
	@SuppressWarnings("unchecked")
	private void eliminar(Rol_BE rol,JSONObject respuesta_json,HttpServletRequest request)
	{
		HttpSession sesionhttp = request.getSession();
		Sesion_BE sesion = new Sesion_BE();
		Resultado_BE resultado = new Resultado_BE();
		sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");
		String id=String.valueOf(request.getParameter("id"));

		rol.ro_rol=Integer.valueOf(id);
		resultado=Rol_BLL.eliminar(rol,sesion);
		respuesta_json.put("resultado", resultado.re_exitoso);
		respuesta_json.put("descripcion", resultado.re_descripcion);
		
	}
	
	
	
	
	
	
	

}

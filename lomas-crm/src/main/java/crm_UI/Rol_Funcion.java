package crm_UI;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import crm_BE.Funcion_BE;
import crm_BE.Funciones;
import crm_BE.Resultado_BE;
import crm_BE.Rol_BE;
import crm_BE.Rol_funcion_BE;
import crm_BE.Sesion_BE;
import crm_BLL.General_BLL;
import crm_BLL.Rol_BLL;
import crm_BLL.Rol_funcion_BLL;

import java.util.List;
import java.util.ArrayList;;

/**
 * Servlet implementation class ModificarRol_Funcion
 */
public class Rol_Funcion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Rol_Funcion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		Resultado_BE resultado = new Resultado_BE();
		Rol_BE rol_parametro = new Rol_BE();
		Sesion_BE sesion = new Sesion_BE();
		PrintWriter out = response.getWriter();
		String q=request.getParameter("q");

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

			if (!General_BLL.tienePermiso(sesion,Funciones.ASIGNAR_FUNCIONES_A_UN_ROL)) {
				respuesta_json.put("resultado", "-101");
				respuesta_json.put("descripcion", "AccesoDenegado");
			} else {
				
				if(q!=null){
					// Llenar el objeto con la información del formulario
					rol_parametro.ro_rol =Integer.valueOf(request.getParameter("rol")); 

					List<Funcion_BE> lista= new ArrayList<Funcion_BE>();
					String[] result = q.split("\\|");
					if(result.length>1)
					{
					String []resultado2=result[1].split(",");
					 	
				        for(String s : resultado2){
				            Funcion_BE tmp= new Funcion_BE();
				            tmp.fu_funcion=Integer.valueOf(s);
				            lista.add(tmp);
				        }
					 	}
				        resultado=Rol_funcion_BLL.guardar(rol_parametro, lista, sesion);
					
					// Respuesta JSON
					respuesta_json.put("resultado", resultado.re_codigo);
					respuesta_json.put("descripcion", resultado.re_descripcion);
					
				}else{
					respuesta_json.put("resultado", "-101");
					respuesta_json.put("descripcion", "Error general: se esperaba un parametro");
					
				}
				
				
			}
			// Enviar respuesta JSON
			
		}
		out.print(respuesta_json.toJSONString());


		
		
	}

}

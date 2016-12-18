package crm_UI;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;
//import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import crm_BE.Funciones;
import crm_BE.Registro_Bitacora_BE;

import crm_BE.Sesion_BE;
import crm_BLL.General_BLL;

import crm_BLL.Registro_Bitacora_BLL;
import java.text.Normalizer;
import java.text.Normalizer.Form;
/**
 * Servlet implementation class bitacora
 */
public class Bitacora_Registro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bitacora_Registro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		/* Codigo para evitar errores por seguridad */
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.setContentType("application/json");
		// parámetros de paginación
		String start = String.valueOf(request.getParameter("start"));
		String length = String.valueOf(request.getParameter("length"));
		String filtro= removeAccents(String.valueOf(request.getParameter("filtro")).toLowerCase());
		String inicio= String.valueOf(request.getParameter("inicio"));
		String fin= String.valueOf(request.getParameter("fin"));
		PrintWriter out = response.getWriter();
		
		
		//Map params = request.getParameterMap();
		//Iterator i = params.keySet().iterator();
/*
		while ( i.hasNext() )
		  {
		    String key = (String) i.next();
		    String value = ((String[]) params.get( key ))[ 0 ];
		    System.out.println(key+" => "+value);
		    
		  }
		*/

		// Valida la sesion
		HttpSession sesionhttp = request.getSession();
		JSONObject respuesta = new JSONObject();
		JSONArray tuplas = new JSONArray();

		Sesion_BE sesion = new Sesion_BE();
		if (sesionhttp.getAttribute("sesion") == null) {
			// Sesión vencida
			sesionhttp.invalidate();
			respuesta.put("resultado", "-100");
			respuesta.put("descripcion", "Caduc� la sesi�n");
			respuesta.put("data",new JSONArray());
		} else {
			// Si la Sesión sigue activa
			sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");

			// Comprobar si tiene permisos
			if (!General_BLL.tienePermiso(sesion, Funciones.LISTAR_EVENTOS_DE_BITACORA)) {
				respuesta.put("resultado", "-101");
				respuesta.put("descripcion", "AccesoDenegado");
			} else {
				// Permiso aceptado
				// Llenar el objeto con la información del formulario
				List<Registro_Bitacora_BE> lista = new ArrayList<Registro_Bitacora_BE>();
				lista=Registro_Bitacora_BLL.buscar(filtro, inicio, fin, start, length);
				for (Registro_Bitacora_BE registro : lista) {
					JSONObject tupla = new JSONObject();
					tupla.put("usuario", String.valueOf(registro.login));
					tupla.put("fecha_hora", String.valueOf(registro.fecha_hora.toString().substring(0,19)));
					tupla.put("funcion", String.valueOf(registro.funcion));
					tupla.put("tipo", String.valueOf(registro.tipo));
					tupla.put("descripcion", String.valueOf(registro.descripcion));
					tuplas.add(tupla);
				}
				
				int total=Registro_Bitacora_BLL.contar(filtro, inicio, fin);
				respuesta.put("recordsTotal",total);
				respuesta.put("recordsFiltered",total);
				respuesta.put("data", tuplas);
				
			
				
				
				// Efectuar filtrado

				// Listar roles a traves de la BLL

			}

		}
		
		out.print(respuesta.toJSONString());

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
	}
	public static String removeAccents(String text) {
	    return text == null ? null
	        : Normalizer.normalize(text, Form.NFD)
	            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
	
}

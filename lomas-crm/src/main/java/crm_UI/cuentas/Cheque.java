package crm_UI.cuentas;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import crm_BE.Funciones;
import crm_BE.General_BE;
import crm_BE.Resultado_BE;
import crm_BE.Rol_BE;
import crm_BE.Sesion_BE;
import crm_BE.cuentas.Busqueda_BE;
import crm_BE.cuentas.Cheque_BE;
import crm_BLL.General_BLL;
import crm_BLL.Rol_BLL;
import crm_BLL.cuentas.Cheque_BLL;

/**
 * Servlet implementation class Cheque
 */
public class Cheque extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cheque() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		/* Codigo para evitar errores por seguridad */
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		
		// DeclaraciÃ³n de variables
		JSONObject respuesta = new JSONObject();
		Cheque_BE cheque_parametro = new Cheque_BE();
		Busqueda_BE busqueda_parametro = new Busqueda_BE();
		Sesion_BE sesion = new Sesion_BE();
		PrintWriter out = response.getWriter();

		// Valida la sesion
		HttpSession sesionhttp = request.getSession();
		

		if (sesionhttp.getAttribute("sesion") == null) {
			// SesiÃ³n vencida
			JSONArray tuplas = new JSONArray();
			JSONObject tupla = new JSONObject();
			tupla.put("id", "");
			tupla.put("numero", "");
			tupla.put("lugar", "");
			tupla.put("fecha", "");
			tupla.put("nombre", "");
			tupla.put("cantidad", "");
			tupla.put("motivo", "");
			tupla.put("imagen", "");
			tuplas.add(tupla);
			
			sesionhttp.invalidate();
			respuesta.put("resultado", -100); 
			respuesta.put("descripcion", "Caducó la sesión");
		} else {
			// Si la SesiÃ³n sigue activa
			sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");
		    // Verify the content type
		    String contentType = request.getContentType();
		    if ((contentType.indexOf("multipart/form-data") >= 0)) {
				File file ;
			    int maxFileSize = 20000 * 1024;
			    int maxMemSize = 20000 * 1024;
			    ServletContext context = request.getServletContext();
			    

		       DiskFileItemFactory factory = new DiskFileItemFactory();
		       // maximum size that will be stored in memory
		       factory.setSizeThreshold(maxMemSize);
		       // Location to save data that is larger than maxMemSize.
		       factory.setRepository(new File(General_BE.CHEQUE_IMAGENES));

		       // Create a new file upload handler
		       ServletFileUpload upload = new ServletFileUpload(factory);
		       // maximum file size to be uploaded.
		       upload.setSizeMax( maxFileSize );
		       try{
		    	   Resultado_BE resultado = new Resultado_BE();
		    	   String op = "";
		    	   String id = "";
		    	   String hash_imagen 		= "";
		    	   String str_no_cheque 	= "";
		    	   String str_nombre_de 	= "";
		    	   String str_lugar 		= "";
		    	   String str_fecha		 	= "";
		    	   String str_cantidad 		= "";
		    	   String str_motivo 		= "";
		    	   String str_imagen 		= "";
		    	  
		    	  
		    	   // Parse the request to get file items.
		    	   List fileItems = upload.parseRequest(request); 

		    	   // Process the uploaded file items
		    	   Iterator i = fileItems.iterator();
		    	   //procesamos de primero la informacion adicional
		    	   while ( i.hasNext () ) {
		        	  System.out.println("Si esarchivo 333333");
		        	  FileItem fi = (FileItem)i.next();
		        	
		        	  if(fi.isFormField()){
		        		  String fieldname = fi.getFieldName();
		        		  String fieldvalue = fi.getString("UTF-8");
		        		  if (fieldname.equals("id")) {
		        			  id = String.valueOf(fieldvalue);
		        		  }else if (fieldname.equals("operacion")) {
		        			  op = String.valueOf(fieldvalue).trim();
		        		  }else if (fieldname.equals("numero")) {
		        			  str_no_cheque = String.valueOf(fieldvalue).trim();
		        		  }else if (fieldname.equals("lugar")) {
		        			  str_lugar = String.valueOf(fieldvalue).trim();
		        		  }else if (fieldname.equals("fecha")) {
		        			  str_fecha = String.valueOf(fieldvalue).trim();
		        		  }else if (fieldname.equals("nombre")) {
		        			  str_nombre_de = String.valueOf(fieldvalue).trim().toUpperCase();
		        		  }else if (fieldname.equals("cantidad")) {
		        			  str_cantidad = String.valueOf(fieldvalue).trim();
		        		  }else if (fieldname.equals("motivo")) {
		        			  str_motivo = String.valueOf(fieldvalue).trim();
		        		  }
		        	  }
		    	   }
		    	   
		    	   // Comprobar si tiene permisos
		    	   	switch(Integer.parseInt(op)){
		    	   	case 6:
		    	   		//crear el registro
		    	   		if (!General_BLL.tienePermiso(sesion,Funciones.CREAR_CHEQUES)) {
							respuesta.put("resultado", -101);
							respuesta.put("descripcion", "Acceso Denegado");
						}else{
							i = fileItems.iterator();
					    	   //procesamos el archivo
				    	    while(i.hasNext()){
				    		   FileItem fi = (FileItem)i.next();
			    		   		if ( !fi.isFormField () )	{
				    			   	// Get the uploaded file parameters
						           	//String fieldName = fi.getFieldName();
						           	String fileName = fi.getName();
						           	String filePath = General_BE.CHEQUE_IMAGENES;
						           	hash_imagen = General_BLL.obtenerHash(str_no_cheque, "MD5").substring(0,12);
						           	String nombreArchivo ="c_"+ hash_imagen + fileName.substring(fileName.lastIndexOf('.'));
						           	//boolean isInMemory = fi.isInMemory();
						           	//long sizeInBytes = fi.getSize();
						           	// Write the file
						           	file = new File( filePath + "/" + nombreArchivo ) ;
						           	/*if( fileName.lastIndexOf("\\") >= 0 ){
							           
						           	}else{
						            	file = new File( filePath + "/" + nombreArchivo ) ;
						           	}*/
						           	
						           	str_imagen = nombreArchivo;
						           	
						           	if(!str_no_cheque.equalsIgnoreCase("null") && !str_no_cheque.equalsIgnoreCase(""))
						           		cheque_parametro.ch_numero = Integer.parseInt(str_no_cheque);
						           	
						           	if(!str_nombre_de.equalsIgnoreCase("null") && !str_nombre_de.equalsIgnoreCase(""))
						           		cheque_parametro.ch_nombre = (str_nombre_de);
						           	
						           	if(!str_lugar.equalsIgnoreCase("null") && !str_lugar.equalsIgnoreCase(""))
						           		cheque_parametro.ch_lugar = (str_lugar);
						           	
						           	if(!str_fecha.equalsIgnoreCase("null") && !str_fecha.equalsIgnoreCase("")){
						           		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						        		java.util.Date dteFecha = null;
						        		try {
						        			dteFecha = sdf.parse(str_fecha.replace("-", "/") + " 00:00:00");
						        		} catch (ParseException e1) {
						        			e1.printStackTrace();
						        		}
						        		cheque_parametro.ch_fecha = new Timestamp(dteFecha.getTime());
						           	}
						           	
						           	if(!str_cantidad.equalsIgnoreCase("null") && !str_cantidad.equalsIgnoreCase(""))
						           		cheque_parametro.ch_cantidad = Float.parseFloat(str_cantidad);
						           	
						           	if(!str_motivo.equalsIgnoreCase("null") && !str_motivo.equalsIgnoreCase(""))
						           		cheque_parametro.ch_motivo = (str_motivo);
						           	
						           	if(!str_imagen.equalsIgnoreCase("null") && !str_imagen.equalsIgnoreCase(""))
						           		cheque_parametro.ch_imagen = (str_imagen);
						           	
						           	cheque_parametro.ch_fecha_hora = java.sql.Timestamp
						           			.valueOf(General_BLL.obtenerFechaHora());
									// Crear un cheque a traves de la BLL
									resultado = Cheque_BLL.crear(cheque_parametro,fi,file, sesion);
									
									// Respuesta JSON
									respuesta.put("resultado", resultado.re_codigo);
									respuesta.put("descripcion", resultado.re_descripcion);
									respuesta.put("valor", resultado.re_identificador);
					           
				    		   }
				    	    }
						}
		    	   		break;
		    	   	case 7:
		    	   		//modificar
		    	   		if (!General_BLL.tienePermiso(sesion,Funciones.MODIFICAR_CHEQUES)) {
							respuesta.put("resultado", -101);
							respuesta.put("descripcion", "Acceso Denegado");
						}else{
							i = fileItems.iterator();
					    	   //procesamos el archivo
				    	    while(i.hasNext()){
				    		   FileItem fi = (FileItem)i.next();
			    		   		if ( !fi.isFormField () )	{
				    			   	// Get the uploaded file parameters
						           	//String fieldName = fi.getFieldName();
						           	String fileName = fi.getName();
						           	String filePath = General_BE.CHEQUE_IMAGENES;
						           	hash_imagen = General_BLL.obtenerHash(str_no_cheque, "MD5").substring(0,12);
						           	String nombreArchivo ="c_"+ hash_imagen + fileName.substring(fileName.lastIndexOf('.'));
						           	//boolean isInMemory = fi.isInMemory();
						           	//long sizeInBytes = fi.getSize();
						           	// Write the file
						           	file = new File( filePath + "/" + nombreArchivo ) ;
						           	/*if( fileName.lastIndexOf("\\") >= 0 ){
							           
						           	}else{
						            	file = new File( filePath + "/" + nombreArchivo ) ;
						           	}*/
						           	
						           	str_imagen = nombreArchivo;
						           	if(!id.equalsIgnoreCase("null") && !id.equalsIgnoreCase(""))
						           		cheque_parametro.ch_cheque = Integer.parseInt(id);
						           	
						           	if(!str_no_cheque.equalsIgnoreCase("null") && !str_no_cheque.equalsIgnoreCase(""))
						           		cheque_parametro.ch_numero = Integer.parseInt(str_no_cheque);
						           	
						           	if(!str_nombre_de.equalsIgnoreCase("null") && !str_nombre_de.equalsIgnoreCase(""))
						           		cheque_parametro.ch_nombre = (str_nombre_de);
						           	
						           	if(!str_lugar.equalsIgnoreCase("null") && !str_lugar.equalsIgnoreCase(""))
						           		cheque_parametro.ch_lugar = (str_lugar);
						           	
						           	if(!str_fecha.equalsIgnoreCase("null") && !str_fecha.equalsIgnoreCase("")){
						           		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						        		java.util.Date dteFecha = null;
						        		try {
						        			dteFecha = sdf.parse(str_fecha.replace("-", "/") + " 00:00:00");
						        		} catch (ParseException e1) {
						        			e1.printStackTrace();
						        		}
						        		cheque_parametro.ch_fecha = new Timestamp(dteFecha.getTime());
						           	}
						           	
						           	if(!str_cantidad.equalsIgnoreCase("null") && !str_cantidad.equalsIgnoreCase(""))
						           		cheque_parametro.ch_cantidad = Float.parseFloat(str_cantidad);
						           	
						           	if(!str_motivo.equalsIgnoreCase("null") && !str_motivo.equalsIgnoreCase(""))
						           		cheque_parametro.ch_motivo = (str_motivo);
						           	
						           	if(!str_imagen.equalsIgnoreCase("null") && !str_imagen.equalsIgnoreCase(""))
						           		cheque_parametro.ch_imagen = (str_imagen);
						           	
									// Crear un cheque a traves de la BLL
						           	fi.write( file ) ;
									resultado = Cheque_BLL.modificar(cheque_parametro, sesion);
									
									// Respuesta JSON
									respuesta.put("resultado", resultado.re_codigo);
									respuesta.put("descripcion", resultado.re_descripcion);
					           
				    		   }
				    	    }
						}
		    	   		break;
		    	   	}
					
		          
		    	   
		       }catch(Exception ex) {
		    	   // Respuesta JSON
		    	   respuesta.put("resultado", -101);
		    	   respuesta.put("descripcion", "Hubo un error al enviar la imagen");
		       }
				
		    	
		   }else{
			   int op;

				try {
					op = Integer.valueOf(request.getParameter("operacion"));
					System.out.println("asdfasdfasdf333333");
					switch (op) {
					case 1:
						// Listar
						if (!General_BLL.tienePermiso(sesion,Funciones.LISTAR_CHEQUES)) {
							respuesta.put("resultado", "-101");
							respuesta.put("descripcion", "Acceso Denegado");
						} else{
							buscarCheque(busqueda_parametro, respuesta, request);
						}
							
						break;
					case 2:
						// Verificar
						if (!General_BLL.tienePermiso(sesion,Funciones.LISTAR_CHEQUES)) {
							respuesta.put("resultado", "-101");
							respuesta.put("descripcion", "Acceso Denegado");
						} else{
							verificarCheque(cheque_parametro, respuesta, request);
						}
							
						break;
					case 3:
						// Listar
						if (!General_BLL.tienePermiso(sesion,Funciones.LISTAR_CHEQUES)) {
							respuesta.put("resultado", "-101");
							respuesta.put("descripcion", "Acceso Denegado");
						} else{
							listarCheque(cheque_parametro, respuesta, request);
						}
							
						break;
					case 4:
						// Listar
						if (!General_BLL.tienePermiso(sesion,Funciones.LISTAR_CHEQUES)) {
							respuesta.put("resultado", "-101");
							respuesta.put("descripcion", "Acceso Denegado");
						} else{
							verificarChequeModificar(cheque_parametro, respuesta, request);
						}
							
						break;
					case 5:
						// Listar
						if (!General_BLL.tienePermiso(sesion,Funciones.MODIFICAR_CHEQUES)) {
							respuesta.put("resultado", "-101");
							respuesta.put("descripcion", "Acceso Denegado");
						} else{
							modificarCheque(cheque_parametro, respuesta, request);
						}
							
						break;
					}

				} catch (Exception e) {
					
				}
		   }
			// Aqui se hace de todo
			/*int op;

			try {
				op = Integer.valueOf(request.getParameter("operacion"));
				System.out.println("asdfasdfasdf333333");
				switch (op) {
				case 1:
					// Crear
					if (!General_BLL.tienePermiso(sesion,Funciones.CREAR_CHEQUES)) {
						respuesta.put("resultado", "-101");
						respuesta.put("descripcion", "Acceso Denegado");
					} else
						System.out.println(String.valueOf(request.getParameter("operacion")));
						System.out.println(String.valueOf(request.getParameter("nombre")));
					
						crearCheque(rol_parametro, respuesta, request);
					break;
				}

			} catch (Exception e) {
				
			}*/

		}

		out.print(respuesta.toJSONString());
	}
	
	@SuppressWarnings("unchecked")
	private void buscarCheque(Busqueda_BE busqueda, JSONObject respuesta,HttpServletRequest request) {
		List<Cheque_BE> resultados;
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

			resultados = Cheque_BLL.buscar(busqueda);

			for (Cheque_BE ch : resultados) {
				JSONObject tupla = new JSONObject();
				tupla.put("id", String.valueOf(ch.ch_cheque));
				tupla.put("numero", String.valueOf(ch.ch_numero).trim());
				tupla.put("lugar", ch.ch_lugar.trim());
				tupla.put("fecha", General_BLL.formatearFechaSinHoraUI(ch.ch_fecha).trim());
				tupla.put("nombre", ch.ch_nombre);
				tupla.put("cantidad", String.valueOf(ch.ch_cantidad).trim());
				tupla.put("motivo", ch.ch_motivo.trim());
				tupla.put("imagen", ch.ch_imagen.trim());
				
				
				tuplas.add(tupla);
			}

			respuesta.put("resultado", 1);
			respuesta.put("data", tuplas);
			respuesta.put("descripcion", "Se obtuvieron los datos correctamente");
			System.out.println("si se ejecuto");
			

		} catch (Exception e) {
			respuesta.put("resultado", -200);
			respuesta.put("descripcion", "La operación no fue exitosa");

		}
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void verificarCheque(Cheque_BE cheque, JSONObject respuesta,HttpServletRequest request) {
		List<Cheque_BE> resultados;
		JSONArray tuplas = new JSONArray();
		
		String str_numero = String.valueOf(request.getParameter("numero")).trim();
		
		if(!str_numero.equalsIgnoreCase("null") && !str_numero.equalsIgnoreCase(""))
			cheque.ch_numero = Integer.parseInt(str_numero);
		
		
		// Listar roles a traves de la BLL
		try {

			resultados = Cheque_BLL.listar(cheque);
			
			if(resultados != null){
				if(resultados.isEmpty()){
					respuesta.put("resp", 1);
					respuesta.put("descripcion", "Operacion exitosa");
				}else{
					respuesta.put("resp", 0);
					respuesta.put("descripcion", "Ya existe un cheque con el número ingresado");
				}
			}
			/*
			for (Cheque_BE ch : resultados) {
				JSONObject tupla = new JSONObject();
				tupla.put("numero", String.valueOf(ch.ch_numero));
				tupla.put("lugar", ch.ch_lugar);
				tupla.put("fecha", General_BLL.formatearFechaSinHoraUI(ch.ch_fecha));
				tupla.put("nombre", ch.ch_nombre);
				tupla.put("cantidad", String.valueOf(ch.ch_cantidad));
				tupla.put("motivo", ch.ch_motivo);
				tupla.put("imagen", ch.ch_imagen);
				
				
				tuplas.add(tupla);
			}*/

			respuesta.put("resultado", 1);
			respuesta.put("data", tuplas);
			
			System.out.println("si se ejecuto");
			

		} catch (Exception e) {
			respuesta.put("resultado", -200);
			respuesta.put("descripcion", "La operación no fue exitosa");

		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	private void listarCheque(Cheque_BE cheque, JSONObject respuesta,HttpServletRequest request) {
		List<Cheque_BE> resultados;
		JSONArray tuplas = new JSONArray();
		
		String str_numero = String.valueOf(request.getParameter("numero")).trim();
		String str_id = String.valueOf(request.getParameter("id")).trim();
		
		if(!str_numero.equalsIgnoreCase("null") && !str_numero.equalsIgnoreCase(""))
			cheque.ch_numero = Integer.parseInt(str_numero);
		
		if(!str_id.equalsIgnoreCase("null") && !str_id.equalsIgnoreCase(""))
			cheque.ch_cheque = Integer.parseInt(str_id);
		
		
		// Listar roles a traves de la BLL
		try {

			resultados = Cheque_BLL.listar(cheque);
			
			for (Cheque_BE ch : resultados) {
				JSONObject tupla = new JSONObject();

				tupla.put("id", String.valueOf(ch.ch_cheque));
				tupla.put("numero", String.valueOf(ch.ch_numero));
				tupla.put("lugar", ch.ch_lugar);
				tupla.put("fecha", General_BLL.formatearFechaSinHoraUI(ch.ch_fecha));
				tupla.put("nombre", ch.ch_nombre);
				tupla.put("cantidad", String.valueOf(ch.ch_cantidad));
				tupla.put("motivo", ch.ch_motivo);
				tupla.put("imagen", ch.ch_imagen);
				
				tuplas.add(tupla);
			}

			respuesta.put("resultado", 1);
			respuesta.put("data", tuplas);
			

		} catch (Exception e) {
			respuesta.put("resultado", -200);
			respuesta.put("descripcion", "La operación no fue exitosa");

		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	private void verificarChequeModificar(Cheque_BE cheque, JSONObject respuesta,HttpServletRequest request) {
		List<Cheque_BE> resultados;
		JSONArray tuplas = new JSONArray();
		
		String str_numero = String.valueOf(request.getParameter("numero")).trim();
		String str_id = String.valueOf(request.getParameter("id")).trim();
		
		if(!str_numero.equalsIgnoreCase("null") && !str_numero.equalsIgnoreCase(""))
			cheque.ch_numero = Integer.parseInt(str_numero);
		//if(!str_id.equalsIgnoreCase("null") && !str_id.equalsIgnoreCase(""))
			//cheque.ch_cheque = Integer.parseInt(str_id);
		
		
		// Listar roles a traves de la BLL
		try {

			resultados = Cheque_BLL.listar(cheque);
			
			if(resultados.isEmpty()){
				respuesta.put("resp", 1);
				respuesta.put("descripcion", "Operacion exitosa");
				System.out.println("11111111");
			}else{
				if(resultados.get(0).ch_cheque == Integer.parseInt(str_id)){
					respuesta.put("resp", 1);
					respuesta.put("descripcion", "Operacion exitosa");
				}else{
					respuesta.put("resp", 0);
					respuesta.put("descripcion", "Ya existe un cheque con el número ingresado");
				}
				
			}
			
			/*
			for (Cheque_BE ch : resultados) {
				JSONObject tupla = new JSONObject();
				tupla.put("numero", String.valueOf(ch.ch_numero));
				tupla.put("lugar", ch.ch_lugar);
				tupla.put("fecha", General_BLL.formatearFechaSinHoraUI(ch.ch_fecha));
				tupla.put("nombre", ch.ch_nombre);
				tupla.put("cantidad", String.valueOf(ch.ch_cantidad));
				tupla.put("motivo", ch.ch_motivo);
				tupla.put("imagen", ch.ch_imagen);
				
				
				tuplas.add(tupla);
			}*/

			respuesta.put("resultado", 1);
			respuesta.put("data", tuplas);
			
			System.out.println("si se ejecuto");
			

		} catch (Exception e) {
			respuesta.put("resultado", -200);
			respuesta.put("descripcion", "La operación no fue exitosa");

		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	private void modificarCheque(Cheque_BE cheque, JSONObject respuesta, HttpServletRequest request){
		HttpSession sesionhttp = request.getSession();
		Sesion_BE sesion = new Sesion_BE();
		Resultado_BE resultado = new Resultado_BE();
		sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");
		
		
		
		// Llenar el objeto con la información del formulario
		String str_id = String.valueOf(request.getParameter("id")).trim().toUpperCase();
		String str_numero = String.valueOf(request.getParameter("numero")).trim().toUpperCase();
		String str_fecha = String.valueOf(request.getParameter("fecha")).trim();
		String str_lugar = String.valueOf(request.getParameter("lugar")).trim();
		String str_nombre = String.valueOf(request.getParameter("nombre")).trim();
		String str_cantidad = String.valueOf(request.getParameter("cantidad")).trim();
		String str_motivo = String.valueOf(request.getParameter("motivo")).trim();
		String str_imagen = String.valueOf(request.getParameter("imagen")).trim();
		
		
		if(!str_id.equalsIgnoreCase("null") && !str_id.equalsIgnoreCase(""))
			cheque.ch_cheque = Integer.parseInt(str_id);
		if(!str_numero.equalsIgnoreCase("null") && !str_numero.equalsIgnoreCase(""))
			cheque.ch_numero = Integer.parseInt(str_numero);
		if(!str_fecha.equalsIgnoreCase("null") && !str_fecha.equalsIgnoreCase("")){
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    		java.util.Date dteFecha = null;
    		try {
    			dteFecha = sdf.parse(str_fecha.replace("-", "/") + " 00:00:00");
    		} catch (ParseException e1) {
    			e1.printStackTrace();
    		}
    		cheque.ch_fecha = new Timestamp(dteFecha.getTime());
		}
		if(!str_lugar.equalsIgnoreCase("null") && !str_lugar.equalsIgnoreCase(""))
			cheque.ch_lugar = (str_lugar);
		if(!str_nombre.equalsIgnoreCase("null") && !str_nombre.equalsIgnoreCase(""))
			cheque.ch_nombre = (str_nombre);
		if(!str_cantidad.equalsIgnoreCase("null") && !str_cantidad.equalsIgnoreCase(""))
			cheque.ch_cantidad = Float.parseFloat(str_cantidad);
		if(!str_motivo.equalsIgnoreCase("null") && !str_motivo.equalsIgnoreCase(""))
			cheque.ch_motivo = (str_motivo);
		if(!str_imagen.equalsIgnoreCase("null") && !str_imagen.equalsIgnoreCase(""))
			cheque.ch_imagen = (str_imagen);


		
		
		// Modificar un paciente potencial a traves de la BLL
		resultado = Cheque_BLL.modificar(cheque, sesion);

		// Respuesta JSON
		respuesta.put("resultado", resultado.re_codigo);
		respuesta.put("descripcion", resultado.re_descripcion);	
		
	}

}

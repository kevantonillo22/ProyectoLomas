package crm_UI;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
 
import java.io.PrintWriter;

import java.util.List;


import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;




import org.apache.commons.io.FilenameUtils;
import org.apache.jempbox.xmp.Thumbnail;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
















import crm_BE.General_BE;
import crm_BE.Sesion_BE;






import crm_BLL.General_BLL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 


/**
 * Servlet implementation class GuardarArchivo
 */
public class GuardarArchivo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final String UPLOAD_DIRECTORY = "/home/ubuntu/";
	
	private static final int THRESHOLD_SIZE     = 1024 * 1024 * 1;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 4; // 4MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 5; // 5MB
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuardarArchivo() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject respuesta_json = new JSONObject();

		PrintWriter out = response.getWriter();
		// Valida la sesion
		

		
		int operacion=-1;
		try{
			if(String.valueOf(request.getParameter("operacion"))=="null"){
				operacion=2;
			}else{
				
				operacion=Integer.parseInt(String.valueOf(request.getParameter("operacion")));
			}
			
			switch(operacion){
			case 1:
				/* *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *   *
				 * 					Guardar imagen desde webcam								*
				 * * * *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  */
				guardarImagenWebCam(request, out, respuesta_json);
				break;
			default:
				respuesta_json.put("resultado", -201);
				respuesta_json.put("descripcion", "Operación inválida");	
			
			
			}
			}
		catch(Exception e){
			respuesta_json.put("resultado", -200);
			respuesta_json.put("descripcion", "La operación es inválida o no está presente");
		
		}
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		out.print(respuesta_json.toJSONString());

	}

	public static byte[] decodeImage(String imageDataString) {
		return Base64.decodeBase64(imageDataString.getBytes());
	}
	@SuppressWarnings("unchecked")
	public void guardarImagenWebCam(HttpServletRequest request,PrintWriter out ,JSONObject respuesta_json){
		HttpSession sesionhttp = request.getSession();
		Sesion_BE sesion = new Sesion_BE();
		String id=request.getParameter("usuario");
		if (sesionhttp.getAttribute("sesion") == null) {
			// Sesión vencida
			sesionhttp.invalidate();
			respuesta_json.put("resultado", "-100");
			respuesta_json.put("descripcion", "Caduc� la sesi�n");
		} else {
			
			// Si la Sesión sigue activa
			sesion = (Sesion_BE) sesionhttp.getAttribute("sesion");

			// Llenar el objeto con la información del formulario

			try {
				id=General_BLL.obtenerHash(id, "MD5").substring(0,12);
				String nombreArchivo =General_BE.PERFIL_IMAGENES+"/p_"+ id + ".png";
				//String nombreArchivo ="C:\\"+"p_"+ id + ".png";
				File newFile = new File(nombreArchivo);
				byte[] imageByteArray = decodeImage(String.valueOf(
						request.getParameter("archivo")).replace(
						"data:image/png;base64,", ""));

				// Write a image byte array into file system
				FileOutputStream imageOutFile = new FileOutputStream(newFile,
						false);
				imageOutFile.write(imageByteArray);
				imageOutFile.close();
				respuesta_json.put("resultado", "1");
				respuesta_json.put("descripcion", "Archivo guardado");
				respuesta_json.put("foto", "p_"+ id + ".png");
			} catch (Exception e) {
				respuesta_json.put("resultado", "-100");
				respuesta_json.put("descripcion",
						"Hubo un error al guardar la imagen");
				e.printStackTrace();
			}

		}
	}
	
	
	
	
	
	
	
	/*Mètodo necesario para subidas*/



	}

	
	
	
	
	
	






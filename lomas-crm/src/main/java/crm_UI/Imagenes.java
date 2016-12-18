package crm_UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_BE.General_BE;



/**
 * Servlet implementation class Imagenes
 */
public class Imagenes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Imagenes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		  ServletContext cntx= getServletContext();
	      // Get the absolute path of the image
		  
		  
		  
		  String archivo=String.valueOf(request.getParameter("f"));
		  String t=String.valueOf(request.getParameter("t"));
		  String filename="";
		  try{
			  int tipo=Integer.parseInt(t);
			  
			  switch(tipo){
			  case 1:
				  //Imágenes de perfil
				  filename = General_BE.PERFIL_IMAGENES+"/"+archivo;
				  break;
			  case 2:
				  //Imágenes de perfil
				  filename = General_BE.CHEQUE_IMAGENES+"/"+archivo;
				  break;
				  
			  }
		  }catch(Exception e){
			  
			  e.printStackTrace();
		  }
		  
		  
	      
	      // retrieve mimeType dynamically
	      String mime=cntx.getMimeType(filename);
	      
	 
	      response.setContentType(mime);
	      File file = new File(filename);
	      response.setContentLength((int)file.length());

	   try{   FileInputStream in = new FileInputStream(file);
	      OutputStream out = response.getOutputStream();

	      // Copy the contents of the file to the output stream
	       byte[] buf = new byte[1024];
	       int count = 0;
	       while ((count = in.read(buf)) >= 0) {
	         out.write(buf, 0, count);
	      }
	    out.close();
	    in.close();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		
		
	}

}

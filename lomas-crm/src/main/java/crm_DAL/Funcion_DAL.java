package crm_DAL;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import crm_BE.Funcion_BE;
/*********************************************************************
 * @author kev
 * @version 1.0
 * @since 12/05/2014
 * @FechaModificacion 12/05/2014
 * @Descripcion Funciones de acceso a la base de datos para las funciones
 ********************************************************************/
public class Funcion_DAL {

	
	/*********************************************************************
	 * @author kev
	 * @since 12/05/2014
	 * @param Funcion_BE
	 * @param Connection
	 * @return List<Funcion_BE>
	 * @throws Exception
	 * @Descripcion Listar las funciones disponibles en al base de datos
	 ********************************************************************/
	public static List<Funcion_BE> listar(Funcion_BE funcion,
			Connection conexion) throws SQLException {
		// Declaración de variables		
		List<Funcion_BE> lista_resultado;
		Funcion_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Funcion_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion.prepareStatement("select * from fn_funcion_listar(?,?,?,?,?,?)");

			
			
			//1)
			if(funcion.fu_tipo!=-9999)
				pstmt.setShort	(1,funcion.fu_tipo);
			else
				pstmt.setNull	(1,Types.NULL);
			//2)
			if(funcion.fu_visible!=-9999)
				pstmt.setShort	(2,funcion.fu_visible);
			else
				pstmt.setNull	(2,Types.NULL);
			//3)
			if(funcion.fu_descripcion!=null)
				pstmt.setString	(3,funcion.fu_descripcion);
			else
				pstmt.setNull	(3,Types.NULL);
			//4)
			if(funcion.fu_nombre!=null)
				pstmt.setString	(4,funcion.fu_nombre);
			else
				pstmt.setNull	(4,Types.NULL);
			//5)
			if(funcion.fu_padre!=-9999)
				pstmt.setInt	(5,funcion.fu_padre);
			else
				pstmt.setNull	(5,Types.NULL);
			//6)
			if(funcion.fu_funcion!=-9999)
				pstmt.setInt	(6,funcion.fu_funcion);
			else
				pstmt.setNull	(6,Types.NULL);
			
		
			
		
			// Definición de los parámetros de la función
			
			
			
			
			
			
			// Ejecuta la función
			rs = pstmt.executeQuery();

		
			
			
			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Funcion_BE();
				
				// Llenar entidad temporal con cada columna
				temp.fu_tipo = rs.getShort("fu_tipo");
				temp.fu_visible = rs.getShort("fu_visible");
				temp.fu_descripcion = rs.getString("fu_descripcion");
				temp.fu_nombre = rs.getString("fu_nombre");
				temp.fu_padre = rs.getInt("fu_padre");
				temp.fu_funcion = rs.getShort("fu_funcion");
				temp.fu_link= rs.getString("fu_link");
				temp.fu_icono = rs.getString("fu_icono");
				
				// Agregar a la lista
				lista_resultado.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			lista_resultado = null;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}

		return lista_resultado;
	}
	
	
	
	
}

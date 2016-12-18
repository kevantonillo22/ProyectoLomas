package crm_DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Resultado_BE;
import crm_BE.Rol_BE;

/*********************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 4/05/2014
 * @FechaModificacion 4/05/2014
 * @Descripcion Controla el acceso a datos de los roles del sistema.
 ********************************************************************/

public class Rol_DAL {
	
	/*********************************************************************
	 * @author kev
	 * @since 5/05/2014
	 * @param Rol_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Creación de una entidad rol
	 ********************************************************************/
	public static Resultado_BE crear(Rol_BE rol,
		Connection conexion) throws SQLException {
	// Declaración de variables
	Resultado_BE resultado;
	PreparedStatement pstmt;
	ResultSet rs;

	// Inicialización de variables
	resultado = new Resultado_BE();
	pstmt = null;
	rs = null;

	try {
		// Declaración de la función
		pstmt = conexion
				.prepareStatement("SELECT * FROM fn_rol_crear(?,?,?);");

		// Definición de los parámetros de la función
		
		pstmt.setString(1,rol.ro_nombre);
		pstmt.setString(2,rol.ro_descripcion);
		pstmt.setShort(3, rol.ro_visible);

		// Ejecuta la función

		rs = pstmt.executeQuery();

		while (rs.next()) {
			// Creación exitosa
			resultado.re_exitoso = true;
			resultado.re_identificador = rs.getInt("ro_rol");
		}
	} catch (SQLException e) {
		// Error no manejado
		resultado.re_exitoso = false;
		resultado.re_descripcion = e.getMessage();
	} catch (Exception e) {
		// Error no manejado
		resultado.re_exitoso = false;
		resultado.re_descripcion = e.getMessage();
	} finally {
		if (pstmt != null) {
			pstmt.close();
		}
		if (rs != null) {
			rs.close();
		}
	}

	return resultado;
}
	
	/*********************************************************************
	 * @author kev
	 * @since 5/05/2014
	 * @param Rol_BE
	 * @param Connection
	 * @return List<Rol_BE>
	 * @throws Exception
	 * @Descripcion Método para listar los roles existentes
	 ********************************************************************/
	public static List<Rol_BE> listar(Rol_BE rol,
			Connection conexion, String start, String length) throws SQLException {
		// Declaración de variables
		List<Rol_BE> lista_resultado;
		Rol_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Rol_BE>();
		pstmt = null;
		rs = null;
	
		try {
			// Declaración de la función
			String filtrado="";
			if(start!=null&&length!=null&&start.length()>0&&length.length()>0){
				filtrado=" LIMIT "+length+" OFFSET "+start;
			}
			
			
			pstmt = conexion.prepareStatement("select * from fn_rol_listar(?,?,?,?)"+filtrado);
			
			if(rol.ro_rol!=-9999)
				pstmt.setInt(1, rol.ro_rol);
			else
				pstmt.setNull(1, Types.NULL);
			
			if(rol.ro_nombre!=null)
				pstmt.setString(2, rol.ro_nombre);
			else
				pstmt.setNull(2, Types.NULL);
			
			if(rol.ro_descripcion!=null)
				pstmt.setString(3, rol.ro_descripcion);
			else
				pstmt.setNull(3, Types.NULL);
			
			if(rol.ro_visible!=-9999)
				pstmt.setShort(4, rol.ro_visible);
			else
				pstmt.setNull(4, Types.NULL);

			// Ejecuta la función

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Rol_BE();

				// Llenar entidad temporal con cada columna
				temp.ro_rol = rs.getInt("ro_rol");
				temp.ro_nombre = rs.getString("ro_nombre");
				temp.ro_descripcion = rs.getString("ro_descripcion");
				temp.ro_visible = rs.getShort("ro_visible");
				
				// Agregar a la lista
				lista_resultado.add(temp);
			}
		} catch (Exception e) {
			// Error no manejado
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
	
	/*********************************************************************
	 * @author kev
	 * @since 5/05/2014
	 * @param Rol_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Método usado para eliminar un rol
	 ********************************************************************/
	public static Resultado_BE eliminar(Rol_BE rol,
			Connection conexion) throws SQLException {
		// Declaración de variables
		Resultado_BE resultado;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		resultado = new Resultado_BE();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion
					.prepareStatement("SELECT * FROM fn_rol_eliminar(?);");

			// Definición de los parámetros de la función
			pstmt.setInt(1, rol.ro_rol);

			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Eliminación exitosa
				resultado.re_exitoso = true;
				resultado.re_filas_afectadas = rs.getInt("resultado");
			}
		} catch (SQLException e) {
			// Error no manejado
			resultado.re_exitoso = false;
			resultado.re_descripcion = e.getMessage();
		} catch (Exception e) {
			// Error no manejado
			resultado.re_exitoso = false;
			resultado.re_descripcion = e.getMessage();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}

		return resultado;
	}
	
	/*********************************************************************
	 * @author kev
	 * @since 5/05/2014
	 * @param Rol_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Se utiliza para actualizar un rol
	 ********************************************************************/
	public static Resultado_BE modificar(Rol_BE rol,
			Connection conexion) throws SQLException {
		// Declaración de variables
		Resultado_BE resultado;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		resultado = new Resultado_BE();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion
					.prepareStatement("SELECT * FROM fn_rol_modificar(?,?,?);");
			// Definición y verificación de los parámetros de la función
			if(rol.ro_rol!=-9999)
				pstmt.setInt(1, rol.ro_rol);
			else
				pstmt.setNull(1, Types.NULL);
			
			if(rol.ro_nombre!=null)
				pstmt.setString(2, rol.ro_nombre);
			else
				pstmt.setNull(2, Types.NULL);
			
			if(rol.ro_descripcion!=null)
				pstmt.setString(3, rol.ro_descripcion);
			else
				pstmt.setNull(3, Types.NULL);

			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Modificación exitosa
				resultado.re_exitoso = true;
				resultado.re_filas_afectadas = rs.getInt("resultado");
			}
		} catch (SQLException e) {
			// Error no manejado
			resultado.re_exitoso = false;
			resultado.re_descripcion = e.getMessage();
		} catch (Exception e) {
			// Error no manejado
			resultado.re_exitoso = false;
			resultado.re_descripcion = e.getMessage();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}

		return resultado;
	}
	
	/*********************************************************************
	 * @author kev
	 * @since 9/05/2014
	 * @param Connection
	 * @return int
	 * @throws Exception
	 * @Descripcion Realiza el conteo del metodo listar
	 ********************************************************************/
	public static int contar(Connection conexion) throws SQLException {
		PreparedStatement pstmt;
		ResultSet rs;
		int resultado;
		// Inicialización de variables
		pstmt = null;
		rs = null;
		try {
			// Declaración de la función para realizar el conteo
			pstmt = conexion.prepareStatement("SELECT  COUNT(*) as total from fn_rol_listar(?,?,?,?) WHERE ro_visible=1");
			// Definición de los parámetros de la función
			pstmt.setNull(1,Types.NULL);
			pstmt.setNull(2,Types.NULL);
			pstmt.setNull(3,Types.NULL);
			pstmt.setNull(4,Types.NULL);
			// Ejecuta la función
			rs = pstmt.executeQuery();
			rs.next();
			resultado=rs.getInt("total");
		
		} catch (Exception e) {
			// Error no manejado
			resultado=-1;
		e.printStackTrace();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}

		return resultado;
	}
	
	
	
	
}

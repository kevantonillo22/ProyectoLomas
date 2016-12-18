package crm_DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Funcion_BE;
import crm_BE.Resultado_BE;
import crm_BE.Rol_BE;
import crm_BE.Rol_funcion_BE;

/*********************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 1/05/2014
 * @FechaModificacion 1/05/2014
 * @Descripcion Lógica de acceso a datos para asociación rol-función.
 ********************************************************************/

public class Rol_funcion_DAL {
	
	/*********************************************************************
	 * @author rbarrios
	 * @since 1/05/2014
	 * @param Rol_funcion_BE
	 * @param Connection
	 * @return List<Rol_funcion_BE>
	 * @throws Exception
	 * @Descripcion Lista las asociaciones entre un rol y una funcion.
	 ********************************************************************/
	public static List<Rol_funcion_BE> listar(Rol_funcion_BE rol_funcion,
			Connection conexion) throws SQLException {
		// Declaración de variables
		List<Rol_funcion_BE> lista_resultado;
		Rol_funcion_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Rol_funcion_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion.prepareStatement("select * from fn_rol_funcion_listar(?,?,?)");

			// Definición de los parámetros de la función
			if (rol_funcion.rf_rol_funcion != -9999) {
				pstmt.setInt(1, rol_funcion.rf_rol_funcion);
			} else {
				pstmt.setNull(1,Types.NULL);
			}
			
			if (rol_funcion.rf_rol != -9999) {
				pstmt.setInt(2, rol_funcion.rf_rol);
			} else {
				pstmt.setNull(2,Types.NULL);
			}
			
			if (rol_funcion.rf_funcion != -9999) {
				pstmt.setInt(3, rol_funcion.rf_funcion);
			} else {
				pstmt.setNull(3,Types.NULL);
			}

			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Rol_funcion_BE();

				// Llenar entidad temporal con cada columna
				temp.rf_rol_funcion = rs.getInt("rf_rol_funcion");
				temp.rf_rol = rs.getInt("rf_rol");
				temp.rf_funcion = rs.getInt("rf_funcion");
				temp.rf_tipo = rs.getShort("rf_tipo");
				temp.rf_visible = rs.getShort("rf_visible");
				temp.rf_link = rs.getString("rf_link");
				temp.rf_descripcion = rs.getString("rf_descripcion");
				temp.rf_nombre = rs.getString("rf_nombre");
				temp.rf_padre = rs.getInt("rf_padre");
				if (rs.wasNull()) {
					temp.rf_padre = -9999;
				}
				temp.rf_icono = rs.getString("rf_icono");

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
	 * @since 13/05/2014
	 * @param Rol_funcion_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Creacion en la base de datos de la tabla rol funcion
	 ********************************************************************/
	public static Resultado_BE crear(Rol_funcion_BE rol_funcion,
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
					.prepareStatement("SELECT * FROM fn_rol_funcion_crear(?,?);");

			// Definición de los parámetros de la función
			pstmt.setInt(1, rol_funcion.rf_rol);
			pstmt.setInt(2, rol_funcion.rf_funcion);

			// Ejecuta la función
			rs = pstmt.executeQuery();

		
				// Creación exitosa
				rs.next();
				resultado.re_exitoso = true;
				resultado.re_identificador = rs.getInt("rf_rol_funcion");
			
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
	 * @since 13/05/2014
	 * @param Funcion_BE
	 * @param Connection
	 * @return List<Funcion_BE>
	 * @throws Exception
	 * @Descripcion Con entrada un rol, la funcion devuelve una listas de las funciones asociadas
	 ********************************************************************/
	public static List<Funcion_BE> listarFunciones(Rol_BE rol, boolean asignadas,
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
			if(asignadas){
				pstmt = conexion.prepareStatement("SELECT * from fn_rol_funcion_asignadas(?)");

			}
			else{
				pstmt = conexion.prepareStatement("SELECT * from fn_rol_funcion_no_asignadas(?) WHERE fu_tipo=1");
			
			}
			// Definición de los parámetros de la función
			pstmt.setInt(1, rol.ro_rol); 

			// Ejecuta la función
		
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Funcion_BE();

				// Llenar entidad temporal con cada columna
				temp.fu_descripcion= 	rs.getString("fu_descripcion");
				temp.fu_funcion=		rs.getInt("fu_funcion");
				temp.fu_icono=			rs.getString("fu_icono");
				temp.fu_link=			rs.getString("fu_link");
				temp.fu_nombre=			rs.getString("fu_nombre");
				temp.fu_padre=			rs.getInt("fu_padre");
				temp.fu_tipo=			rs.getShort("fu_tipo");
				temp.fu_visible=		rs.getShort("fu_visible");

				// Agregar a la lista
				lista_resultado.add(temp);
			}
		} catch (Exception e) {
			// Error no manejado
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

	/*********************************************************************
	 * @author kev
	 * @since 14/05/2014
	 * @param Rol_funcion_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Eliminar un permiso 
	 ********************************************************************/
	public static Resultado_BE eliminar(Rol_funcion_BE rol_funcion,
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
					.prepareStatement("SELECT * FROM fn_rol_funcion_eliminar(?,?);");

			//Definición de los parámetros de la función
			pstmt.setInt(1, rol_funcion.rf_rol);
			pstmt.setInt(2, rol_funcion.rf_funcion);

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
	
	
}






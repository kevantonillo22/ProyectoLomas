package crm_DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Parametro_BE;
import crm_BE.Resultado_BE;

/*********************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 21/05/2014
 * @FechaModificacion 21/05/2014
 * @Descripcion Capa de acceso a datos de los parámetros del sistema.
 ********************************************************************/

public class Parametro_DAL {
	/*********************************************************************
	 * @author rbarrios
	 * @since 21/05/2014
	 * @param Parametro_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Crea un parámetro en el sistema
	 ********************************************************************/
	public static Resultado_BE crear(Parametro_BE parametro,
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
					.prepareStatement("SELECT * FROM fn_parametro_crear(?,?,?,?,?,?);");

			// Definición de los parámetros de la función
			pstmt.setInt(1, parametro.pa_codigo_parametro);
			pstmt.setString(2, parametro.pa_nombre);
			pstmt.setString(3, parametro.pa_descripcion);
			pstmt.setString(4, parametro.pa_valor);
			pstmt.setShort(5, parametro.pa_estado);
			pstmt.setTimestamp(6, parametro.pa_fecha_inicio);

			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Creación exitosa
				resultado.re_exitoso = true;
				resultado.re_identificador = rs.getInt("pa_parametro");
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
	 * @author rbarrios
	 * @since 21/05/2014
	 * @param Parametro_BE
	 * @param Connection
	 * @return List<Parametro_BE>
	 * @throws Exception
	 * @Descripcion Lista los parámetros del sistema.
	 ********************************************************************/
	public static List<Parametro_BE> listar(Parametro_BE parametro,
			Connection conexion) throws SQLException {
		// Declaración de variables
		List<Parametro_BE> lista_resultado;
		Parametro_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Parametro_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion.prepareStatement("select * from fn_parametro_listar(?,?)");

			// Definición de los parámetros de la función
			if (parametro.pa_codigo_parametro != -9999) {
				pstmt.setInt(1, parametro.pa_codigo_parametro);
			} else {
				pstmt.setNull(1, Types.NULL);
			}
			
			if (parametro.pa_estado != -9999) {
				pstmt.setShort(2, parametro.pa_estado);
			} else {
				pstmt.setNull(2, Types.NULL);
			}
			
			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Parametro_BE();

				// Llenar entidad temporal con cada columna
				temp.pa_parametro = rs.getInt("pa_parametro");
				temp.pa_codigo_parametro = rs.getInt("pa_codigo_parametro");
				temp.pa_nombre = rs.getString("pa_nombre");
				temp.pa_descripcion = rs.getString("pa_descripcion");
				temp.pa_valor = rs.getString("pa_valor");
				temp.pa_estado = rs.getShort("pa_estado");
				temp.pa_estado_nombre = rs.getString("pa_estado_nombre");
				temp.pa_fecha_inicio = rs.getTimestamp("pa_fecha_inicio");
				temp.pa_fecha_fin = rs.getTimestamp("pa_fecha_fin");

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
	 * @author rbarrios
	 * @since 21/05/2014
	 * @param Parametro_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Modifica un parámetro del sistema.
	 ********************************************************************/
	public static Resultado_BE modificar(Parametro_BE parametro,
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
					.prepareStatement("SELECT * FROM fn_parametro_modificar(?,?,?,?);");

			// Definición de los parámetros de la función
			pstmt.setInt(1, parametro.pa_parametro);
			pstmt.setString(2, parametro.pa_valor);
			pstmt.setShort(3, parametro.pa_estado);
			pstmt.setTimestamp(4, parametro.pa_fecha_fin);

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
}

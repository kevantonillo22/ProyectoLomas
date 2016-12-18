package crm_DAL.garita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Resultado_BE;
import crm_BE.garita.Direccion_BE;

public class Direccion_DAL {
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 27/09/2016
	 * @param Direccion_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion M�todo que realiza un insert en la tabla de direcci�n
	 ********************************************************************/
	public static Resultado_BE crear(Direccion_BE direccion, Connection conexion) throws SQLException {
		// Declaraci�n de variables
		Resultado_BE resultado;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicializaci�n de variables
		resultado = new Resultado_BE();
		pstmt = null;
		rs = null;

		try {
			// Declaraci�n de la funci�n
			pstmt = conexion.prepareStatement("SELECT * FROM fn_direccion_crear(?,?,?,?,?,?,?,?,?,?);");

			// Definici�n de los par�metros de la funci�n
			//pstmt.setTipo(index, value);
			if (direccion.di_numero_calle_av != null) {
				pstmt.setString(1, direccion.di_numero_calle_av);
		    } else {
				pstmt.setNull(1, Types.NULL);
		    }
			
			if (direccion.di_calle_av != -9999) {
				pstmt.setInt(2, direccion.di_calle_av);
		    } else {
				pstmt.setNull(2, Types.NULL);
		    }
			
			if (direccion.di_num_casa != null) {
				pstmt.setString(3, direccion.di_num_casa);
		    } else {
				pstmt.setNull(3, Types.NULL);
		    }
			
			if (direccion.di_familia != null) {
				pstmt.setString(4, direccion.di_familia);
		    } else {
				pstmt.setNull(4, Types.NULL);
		    }
			
			if (direccion.di_telefono != null) {
				pstmt.setString(5, direccion.di_telefono);
		    } else {
				pstmt.setNull(5, Types.NULL);
		    }
			
			if (direccion.di_nombre_titular != null) {
				pstmt.setString(6, direccion.di_nombre_titular);
		    } else {
				pstmt.setNull(6, Types.NULL);
		    }
			
			if (direccion.di_email != null) {
				pstmt.setString(7, direccion.di_email);
		    } else {
				pstmt.setNull(7, Types.NULL);
		    }
			
			if (direccion.di_estado != -9999) {
				pstmt.setInt(8, direccion.di_estado);
		    } else {
				pstmt.setNull(8, Types.NULL);
		    }
			
			if (direccion.di_estado_pago != -9999) {
				pstmt.setInt(9, direccion.di_estado_pago);
		    } else {
				pstmt.setNull(9, Types.NULL);
		    }
			
			if (direccion.di_estado != -9999) {
				pstmt.setInt(10, direccion.di_estado_domicilio);
		    } else {
				pstmt.setNull(10, Types.NULL);
		    }
			
			// Ejecuta la funci�n
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Creaci�n exitosa
				resultado.re_exitoso = true;
				resultado.re_identificador = rs.getInt("c_direccion");
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
	 * @author Kevin Cardona
	 * @since 1/10/2016
	 * @param Direccion_BE
	 * @param Connection
	 * @return List<Direccion_BE>
	 * @throws Exception
	 * @Descripcion M�todo que lista a la tabla direccion
	 ********************************************************************/
	public static List<Direccion_BE> listar(Direccion_BE direccion, Connection conexion)
			throws SQLException {
		// Declaraci�n de variables
		List<Direccion_BE> lista_resultado;
		Direccion_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicializaci�n de variables
		lista_resultado = new ArrayList<Direccion_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaraci�n de la funci�n
			pstmt = conexion.prepareStatement("select * from fn_direccion_listar(?,?,?,?,?,?,?,?,?,?,?)");

			// Definici�n de los par�metros de la funci�n
			// pstmt.setString(posicion, valor); Seg�n el tipo de dato
			if (direccion.di_direccion != -9999) {
				pstmt.setInt(1, direccion.di_direccion);
		    } else {
				pstmt.setNull(1, Types.NULL);
		    }
			
			if (direccion.di_numero_calle_av != null) {
				pstmt.setString(2, direccion.di_numero_calle_av);
		    } else {
				pstmt.setNull(2, Types.NULL);
		    }
			
			if (direccion.di_calle_av != -9999) {
				pstmt.setInt(3, direccion.di_calle_av);
		    } else {
				pstmt.setNull(3, Types.NULL);
		    }
			
			if (direccion.di_num_casa != null) {
				pstmt.setString(4, direccion.di_num_casa);
		    } else {
				pstmt.setNull(4, Types.NULL);
		    }
			
			if (direccion.di_familia != null) {
				pstmt.setString(5, direccion.di_familia);
		    } else {
				pstmt.setNull(5, Types.NULL);
		    }
			
			if (direccion.di_telefono != null) {
				pstmt.setString(6, direccion.di_telefono);
		    } else {
				pstmt.setNull(6, Types.NULL);
		    }
			
			if (direccion.di_nombre_titular != null) {
				pstmt.setString(7, direccion.di_nombre_titular);
		    } else {
				pstmt.setNull(7, Types.NULL);
		    }
			
			if (direccion.di_email != null) {
				pstmt.setString(8, direccion.di_email);
		    } else {
				pstmt.setNull(8, Types.NULL);
		    }
			
			if (direccion.di_estado != -9999) {
				pstmt.setInt(9, direccion.di_estado);
		    } else {
				pstmt.setNull(9, Types.NULL);
		    }
			
			if (direccion.di_estado_pago != -9999) {
				pstmt.setInt(10, direccion.di_estado_pago);
		    } else {
				pstmt.setNull(10, Types.NULL);
		    }
			
			if (direccion.di_estado_domicilio != -9999) {
				pstmt.setInt(11, direccion.di_estado_domicilio);
		    } else {
				pstmt.setNull(11, Types.NULL);
		    }
			
			// Ejecuta la funci�n
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Direccion_BE();

				// Llenar entidad temporal con cada columna
				//temp.atributo = rs.getTipo("nombre_columna");
				temp.di_direccion = rs.getInt("di_direccion");
				temp.di_numero_calle_av = rs.getString("di_numero_calle_av");
				temp.di_calle_av = rs.getInt("di_calle_av");
				temp.di_num_casa = rs.getString("di_num_casa");
				temp.di_familia = rs.getString("di_familia");
				temp.di_telefono = rs.getString("di_telefono");
				temp.di_nombre_titular = rs.getString("di_nombre_titular");
				temp.di_email = rs.getString("di_email");
				temp.di_estado = rs.getInt("di_estado");
				temp.di_estado_pago = rs.getInt("di_estado_pago");
				temp.di_estado_domicilio = rs.getInt("di_estado_domicilio");
				// Agregar a la lista
				lista_resultado.add(temp);
			}
		} catch (Exception e) {
			// Error no manejado
			lista_resultado = null;
			e.printStackTrace();
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
	 * @author Kevin Cardona
	 * @since 22/10/2016
	 * @param Direccion_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion M�todo que actualiza un registro de la tabla crm_direccion
	 ********************************************************************/
	public static Resultado_BE modificar(Direccion_BE direccion, Connection conexion) throws SQLException {
		// Declaraci�n de variables
		Resultado_BE resultado;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicializaci�n de variables
		resultado = new Resultado_BE();
		pstmt = null;
		rs = null;

		try {
			// Declaraci�n de la funci�n
			pstmt = conexion.prepareStatement("SELECT * FROM fn_direccion_modificar(?,?,?,?,?,?,?,?,?,?,?);");

			// Definici�n de los par�metros de la funci�n
			//pstmt.setTipo(index, value);
			if (direccion.di_direccion != -9999) {
				pstmt.setInt(1, direccion.di_direccion);
		    } else {
				pstmt.setNull(1, Types.NULL);
		    }
			
			if (direccion.di_numero_calle_av != null) {
				pstmt.setString(2, direccion.di_numero_calle_av);
		    } else {
				pstmt.setNull(2, Types.NULL);
		    }
			
			if (direccion.di_calle_av != -9999) {
				pstmt.setInt(3, direccion.di_calle_av);
		    } else {
				pstmt.setNull(3, Types.NULL);
		    }
			
			if (direccion.di_num_casa != null) {
				pstmt.setString(4, direccion.di_num_casa);
		    } else {
				pstmt.setNull(4, Types.NULL);
		    }
			
			if (direccion.di_familia != null) {
				pstmt.setString(5, direccion.di_familia);
		    } else {
				pstmt.setNull(5, Types.NULL);
		    }
			
			if (direccion.di_telefono != null) {
				pstmt.setString(6, direccion.di_telefono);
		    } else {
				pstmt.setNull(6, Types.NULL);
		    }
			
			if (direccion.di_nombre_titular != null) {
				pstmt.setString(7, direccion.di_nombre_titular);
		    } else {
				pstmt.setNull(7, Types.NULL);
		    }
			
			if (direccion.di_email != null) {
				pstmt.setString(8, direccion.di_email);
		    } else {
				pstmt.setNull(8, Types.NULL);
		    }
			
			if (direccion.di_estado != -9999) {
				pstmt.setInt(9, direccion.di_estado);
		    } else {
				pstmt.setNull(9, Types.NULL);
		    }
			
			if (direccion.di_estado_pago != -9999) {
				pstmt.setInt(10, direccion.di_estado_pago);
		    } else {
				pstmt.setNull(10, Types.NULL);
		    }
			
			if (direccion.di_estado_domicilio != -9999) {
				pstmt.setInt(11, direccion.di_estado_domicilio);
		    } else {
				pstmt.setNull(11, Types.NULL);
		    }
			
			// Ejecuta la funci�n
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Modificaci�n exitosa
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

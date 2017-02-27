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
import crm_BE.garita.Ingreso_BE;

public class Ingreso_DAL {
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 10/12/2016
	 * @param Ingreso_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Método que crea el registro para el ingreso
	 ********************************************************************/
	public static Resultado_BE crear(Ingreso_BE ingreso, Connection conexion) throws SQLException {
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
			pstmt = conexion.prepareStatement("SELECT * FROM fn_ingreso_crear(?,?,?,?,?,?,?,?);");

			// Definición de los parámetros de la función
			if (ingreso.in_placa != null) {
				pstmt.setString(1, ingreso.in_placa);
		    } else {
				pstmt.setNull(1, Types.NULL);
		    }

			if (ingreso.in_direccion != -9999) {
				pstmt.setInt(2, ingreso.in_direccion);
		    } else {
				pstmt.setNull(2, Types.NULL);
		    }
			
			if (ingreso.in_imagen_placa != null) {
				pstmt.setString(3, ingreso.in_imagen_placa);
		    } else {
				pstmt.setNull(3, Types.NULL);
		    }
			
			if (ingreso.in_imagen_rostro != null) {
				pstmt.setString(4, ingreso.in_imagen_rostro);
		    } else {
				pstmt.setNull(4, Types.NULL);
		    }
			
			if (ingreso.in_imagen_dpi != null) {
				pstmt.setString(5, ingreso.in_imagen_dpi);
		    } else {
				pstmt.setNull(5, Types.NULL);
		    }
			
			if (ingreso.in_fecha_entrada != null) {
				pstmt.setTimestamp(6, ingreso.in_fecha_entrada );
		    } else {
				pstmt.setNull(6, Types.NULL);
		    }
			
			if (ingreso.in_usuario != -9999) {
				pstmt.setInt(7, ingreso.in_usuario );
		    } else {
				pstmt.setNull(7, Types.NULL);
		    }
			
			if (ingreso.in_estado != -9999) {
				pstmt.setInt(8, ingreso.in_estado );
		    } else {
				pstmt.setNull(8, Types.NULL);
		    }
			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Creación exitosa
				resultado.re_exitoso = true;
				resultado.re_identificador = rs.getInt("c_ingreso");
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
	 * @since 11/12/2016
	 * @param Ingreso_BE
	 * @param Connection
	 * @return List<Ingreso_BE>
	 * @throws Exception
	 * @Descripcion Método que lista los registros de los ingresos
	 ********************************************************************/
	public static List<Ingreso_BE> listar(Ingreso_BE ingreso, Connection conexion)
			throws SQLException {
		// Declaración de variables
		List<Ingreso_BE> lista_resultado;
		Ingreso_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Ingreso_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion.prepareStatement("select * from fn_ingreso_listar(?,?,?,?,?,?,?,?,?)");

			// Definición de los parámetros de la función
			if (ingreso.in_ingreso != -9999) {
				pstmt.setInt(1, ingreso.in_ingreso);
		    } else {
				pstmt.setNull(1, Types.NULL);
		    }
			
			if (ingreso.in_placa != null) {
				pstmt.setString(2, ingreso.in_placa);
		    } else {
				pstmt.setNull(2, Types.NULL);
		    }

			if (ingreso.in_direccion != -9999) {
				pstmt.setInt(3, ingreso.in_direccion);
		    } else {
				pstmt.setNull(3, Types.NULL);
		    }
			
			if (ingreso.in_imagen_placa != null) {
				pstmt.setString(4, ingreso.in_imagen_placa);
		    } else {
				pstmt.setNull(4, Types.NULL);
		    }
			
			if (ingreso.in_imagen_rostro != null) {
				pstmt.setString(5, ingreso.in_imagen_rostro);
		    } else {
				pstmt.setNull(5, Types.NULL);
		    }
			
			if (ingreso.in_imagen_dpi != null) {
				pstmt.setString(6, ingreso.in_imagen_dpi);
		    } else {
				pstmt.setNull(6, Types.NULL);
		    }
			
			if (ingreso.in_usuario != -9999) {
				pstmt.setInt(7, ingreso.in_usuario );
		    } else {
				pstmt.setNull(7, Types.NULL);
		    }
			
			if (ingreso.in_estado != -9999) {
				pstmt.setInt(8, ingreso.in_estado );
		    } else {
				pstmt.setNull(8, Types.NULL);
		    }

			if (ingreso.in_rownum != -9999) {
				pstmt.setInt(9, ingreso.in_rownum);
		    } else {
				pstmt.setNull(9, Types.NULL);
		    }
			
			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Ingreso_BE();

				// Llenar entidad temporal con cada columna
				temp.in_ingreso = rs.getInt("in_ingreso");
				temp.in_placa = rs.getString("in_placa");
				temp.in_texto_direccion = rs.getString("in_direccion");
				temp.in_imagen_placa = rs.getString("in_imagen_placa");
				temp.in_imagen_rostro = rs.getString("in_imagen_rostro");
				temp.in_imagen_dpi = rs.getString("in_imagen_dpi");
				temp.in_fecha_entrada = rs.getTimestamp("in_fecha_entrada");
				temp.in_usuario = rs.getInt("in_usuario");
				temp.in_estado = rs.getInt("in_estado");

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
	 * @author Kevin Cardona
	 * @since 22/01/2017
	 * @param Ingreso_BE
	 * @param Connection
	 * @return List<Ingreso_BE>
	 * @throws Exception
	 * @Descripcion Lista los datos de ingreso en base a una fecha y/o direccion
	 ********************************************************************/
	public static List<Ingreso_BE> listarPorFechaDireccion(Ingreso_BE ingreso, Direccion_BE direccion, Connection conexion)
			throws SQLException {
		// Declaración de variables
		List<Ingreso_BE> lista_resultado;
		Ingreso_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Ingreso_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion.prepareStatement("select * from fn_ingreso_listarPorFechaDireccion(?,?,?,?)");

			// Definición de los parámetros de la función
			if (ingreso.in_fecha_entrada != null) {
				pstmt.setTimestamp(1, ingreso.in_fecha_entrada);
				System.out.println(ingreso.in_fecha_entrada.toString());
		    } else {
				pstmt.setNull(1, Types.NULL);
				System.out.println("null 1");
		    }
			
			if (direccion.di_numero_calle_av != null) {
				pstmt.setString(2, direccion.di_numero_calle_av );
		    } else {
				pstmt.setNull(2, Types.NULL);
				System.out.println("null 2");
		    }
			
			if (direccion.di_calle_av != -9999) {
				pstmt.setInt(3, direccion.di_calle_av);
		    } else {
				pstmt.setNull(3, Types.NULL);
				System.out.println("null 3");
		    }
			
			if (direccion.di_num_casa != null) {
				pstmt.setString(4, direccion.di_num_casa);
		    } else {
				pstmt.setNull(4, Types.NULL);
				System.out.println("null 4");
		    }
			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Ingreso_BE();

				// Llenar entidad temporal con cada columna
				temp.in_ingreso = rs.getInt("in_ingreso");
				temp.in_placa = rs.getString("in_placa");
				temp.in_texto_direccion = rs.getString("in_direccion");
				temp.in_imagen_placa = rs.getString("in_imagen_placa");
				temp.in_imagen_rostro = rs.getString("in_imagen_rostro");
				temp.in_imagen_dpi = rs.getString("in_imagen_dpi");
				temp.in_fecha_entrada = rs.getTimestamp("in_fecha_entrada");
				temp.in_usuario = rs.getInt("in_usuario");
				temp.in_estado = rs.getInt("in_estado");
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
}

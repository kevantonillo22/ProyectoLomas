package crm_DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import crm_BE.Registro_Bitacora_BE;
import crm_BE.Bitacora_BE;
import crm_BE.Resultado_BE;



/*********************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 23/04/2014
 * @FechaModificacion 23/04/2014
 * @Descripcion Clase que controla el acceso a datos de la bitácora.
 ********************************************************************/

public class Bitacora_DAL {
	/*********************************************************************
	 * @author rbarrios
	 * @since 23/04/2014
	 * @param Bitacora_DAL
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Creación de un registro de bitácora.
	 ********************************************************************/
	public static Resultado_BE crear(Bitacora_BE bitacora,
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
			pstmt = conexion.prepareStatement("SELECT * FROM fn_bitacora_crear(?,?,?,?,?,?);");

			// Definición de los parómetros de la función
			pstmt.setInt(1, bitacora.bi_usuario);
			pstmt.setInt(2, bitacora.bi_funcion);
			pstmt.setDate(3, bitacora.bi_fecha);
			pstmt.setTimestamp(4, bitacora.bi_fecha_hora);
			pstmt.setString(5, bitacora.bi_descripcion);
			pstmt.setShort(6, bitacora.bi_tipo);

			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Creación exitosa
				resultado.re_exitoso = true;
				resultado.re_identificador = rs.getInt("bi_bitacora");
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
	 * @since 22/05/2014
	 * @param Bitacora_BE
	 * @param Connection
	 * @return List<Bitacora_BE>
	 * @throws Exception
	 * @Descripcion Lista la bitácora del sistema.
	 ********************************************************************/
	public static List<Bitacora_BE> listar(Bitacora_BE bitacora,
			Connection conexion) throws SQLException {
		// Declaración de variables
		List<Bitacora_BE> lista_resultado;
		Bitacora_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Bitacora_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion.prepareStatement("select * from fn_bitacora_listar(?,?,?,?)");

			// Definición de los parámetros de la función
			if (bitacora.bi_bitacora != -9999) {
				pstmt.setInt(1, bitacora.bi_bitacora);
			} else {
				pstmt.setNull(1, Types.NULL);
			}
			
			if (bitacora.bi_usuario != -9999) {
				pstmt.setInt(2, bitacora.bi_usuario);
			} else {
				pstmt.setNull(2, Types.NULL);
			}
			
			if (bitacora.bi_funcion != -9999) {
				pstmt.setInt(3, bitacora.bi_funcion);
			} else {
				pstmt.setNull(3, Types.NULL);
			}
			
			if (bitacora.bi_tipo != -9999) {
				pstmt.setShort(4, bitacora.bi_tipo);
			} else {
				pstmt.setNull(4, Types.NULL);
			}

			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Bitacora_BE();

				// Llenar entidad temporal con cada columna
				temp.bi_bitacora = rs.getInt("bi_bitacora");
				temp.bi_usuario = rs.getInt("bi_usuario");
				temp.bi_funcion = rs.getInt("bi_funcion");
				temp.bi_fecha = rs.getDate("bi_fecha");
				temp.bi_fecha_hora = rs.getTimestamp("bi_fecha_hora");
				temp.bi_descripcion = rs.getString("bi_descripcion");
				temp.bi_tipo = rs.getShort("bi_tipo");
				temp.bi_tipo_nombre = rs.getString("bi_tipo_nombre");

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
	 * @since 19/05/2014
	 * @param Registro_Bitacora_BE
	 * @param Connection
	 * @return List<Registro_Bitacora_BE>
	 * @throws Exception
	 * @Descripcion Busca los registros de bitácora.
	 ********************************************************************/
	public static List<Registro_Bitacora_BE> buscar(String filtro, String inicio, String fin, String start, String length,
			Connection conexion) throws SQLException {
		// Declaración de variables
		List<Registro_Bitacora_BE> lista_resultado;
		Registro_Bitacora_BE nodo;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Registro_Bitacora_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función y los parametros de la paginacion
			String filtrado=" LIMIT "+length+" OFFSET "+start;
			pstmt = conexion.prepareStatement("SELECT * FROM fn_bitacora_buscar(?,?,?)"+filtrado);
		
			// Definición de los parámetros de la función
			if(filtro==null)
				pstmt.setNull(1, Types.NULL);
			else
				pstmt.setString(1, filtro);
			
			if(inicio==null)
				pstmt.setNull(2, Types.NULL);
			else
				pstmt.setDate(2, convertirFecha(inicio));
			
			if(fin==null)
				pstmt.setNull(3, Types.NULL);
			else
				pstmt.setDate(3, convertirFecha(fin));
			

			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad nodooral
				nodo = new Registro_Bitacora_BE();

				// Llenar entidad nodo con cada columna
				nodo.descripcion= 	rs.getString("descripcion");
				nodo.fecha_hora=	rs.getTimestamp("fecha_hora");
				nodo.funcion=		rs.getString("funcion");
				nodo.login=			rs.getString("login");
				nodo.tipo=			rs.getString("tipo");
				
				// Agregar a la lista
				lista_resultado.add(nodo);
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
	
	public static int contar(String filtro, String inicio, String fin,Connection conexion) throws SQLException {
		int resultado=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			// Declaración de la función y los parametros de la paginacion
			pstmt = conexion.prepareStatement("SELECT COUNT(*) as conteo FROM fn_bitacora_listar(?,?,?)");
			
			// Definición de los parámetros de la función
			if(filtro==null)
				pstmt.setNull(1, Types.NULL);
			else
				pstmt.setString(1, filtro);
			
			if(inicio==null)
				pstmt.setNull(2, Types.NULL);
			else
				pstmt.setDate(2, convertirFecha(inicio));
			
			if(fin==null)
				pstmt.setNull(3, Types.NULL);
			else
				pstmt.setDate(3, convertirFecha(fin));
			
			// Ejecuta la función
			rs = pstmt.executeQuery();
			rs.next();
			resultado=rs.getInt("conteo");
		} catch (Exception e) {
			// Error no manejado
			e.printStackTrace();
			resultado =-1;
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
	
	
	
	
	private static java.sql.Date convertirFecha(String fecha){
		  	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        java.util.Date parsed=null;
	        java.sql.Date sqlDate=null;
			try {
				parsed = format.parse(fecha);
				   java.util.Date sql = new java.sql.Date(parsed.getTime());
			         sqlDate= new java.sql.Date(sql.getTime());
			      
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				
				
			}
	     
	        return sqlDate;
		
	}
	
	
}

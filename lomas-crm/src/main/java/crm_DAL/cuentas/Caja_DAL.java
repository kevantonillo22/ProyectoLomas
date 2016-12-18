package crm_DAL.cuentas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Resultado_BE;
import crm_BE.cuentas.Busqueda_BE;
import crm_BE.cuentas.Caja_Chica_BE;
import crm_BE.cuentas.Cheque_BE;
import crm_BE.cuentas.Documento_BE;
import crm_BE.cuentas.Efectivo_BE;

public class Caja_DAL {
	
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 3/11/2015
	 * @param Caja_Chica_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion descripcion
	 ********************************************************************/
	public static Resultado_BE crearCajaChica(Caja_Chica_BE caja, Connection conexion) throws SQLException {
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
			pstmt = conexion.prepareStatement("SELECT * FROM fn_caja_chica_crear(?,?,?,?,?,?,?,?,?,?);");

			// Definición de los parámetros de la función
			
			if(caja.ca_total_billete != -9999){
				pstmt.setFloat(1, caja.ca_total_billete);
			}else{
				pstmt.setNull	(1, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_total_moneda != -9999){
				pstmt.setFloat(2, caja.ca_total_moneda);
			}else{
				pstmt.setNull	(2, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_total_efectivo != -9999){
				pstmt.setFloat(3, caja.ca_total_efectivo);
			}else{
				pstmt.setNull	(3, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_total_documentos != -9999){
				pstmt.setFloat(4, caja.ca_total_documentos);
			}else{
				pstmt.setNull	(4, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_sumatoria != -9999){
				pstmt.setFloat(5, caja.ca_sumatoria);
			}else{
				pstmt.setNull	(5, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_fondo != -9999){
				pstmt.setFloat(6, caja.ca_fondo);
			}else{
				pstmt.setNull	(6, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_variacion != -9999){
				pstmt.setFloat(7, caja.ca_variacion);
			}else{
				pstmt.setNull	(7, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_fecha != null){
				pstmt.setTimestamp(8, caja.ca_fecha);
			}else{
				pstmt.setNull	(8, Types.NULL);
			}
			
			if(caja.ca_fecha_hora_modificacion != null){
				pstmt.setTimestamp(9, caja.ca_fecha_hora_modificacion);
			}else{
				pstmt.setNull	(9, Types.NULL);
				System.out.println("1,Null");
			}
			
			if(caja.ca_fecha_hora_creacion != null){
				pstmt.setTimestamp(10, caja.ca_fecha_hora_creacion);
			}else{
				pstmt.setNull	(10, Types.NULL);
				System.out.println("1,Null");
			}

			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Creación exitosa
				resultado.re_exitoso = true;
				resultado.re_identificador = rs.getInt("c_caja_chica");
			}
		} catch (SQLException e) {
			// Error no manejado
			resultado.re_exitoso = false;
			resultado.re_descripcion = e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			// Error no manejado
			resultado.re_exitoso = false;
			resultado.re_descripcion = e.getMessage();
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
	
	
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 3/11/2015
	 * @param Documento_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Crea un registro de documento
	 ********************************************************************/
	public static Resultado_BE crearDocumento(Documento_BE documento, Connection conexion) throws SQLException {
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
			pstmt = conexion.prepareStatement("SELECT * FROM fn_documento_crear(?,?,?,?);");

			// Definición de los parámetros de la función
			
			if(documento.do_caja_chica!= -9999){
				pstmt.setInt(1, documento.do_caja_chica);
			}else{
				pstmt.setNull	(1, Types.INTEGER);
				//System.out.println("1,Null");
			}
			
			if(documento.do_nit != -9999){
				pstmt.setInt(2, documento.do_nit);
			}else{
				pstmt.setNull	(2, Types.INTEGER);
				//System.out.println("1,Null");
			}
			
			if(documento.do_nombre != null){
				pstmt.setString(3, documento.do_nombre);
			}else{
				pstmt.setNull	(3, Types.VARCHAR);
				//System.out.println("1,Null");
			}
			
			if(documento.do_monto != -9999){
				pstmt.setFloat(4, documento.do_monto);
			}else{
				pstmt.setNull	(4, Types.REAL);
				//System.out.println("1,Null");
			}

			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Creación exitosa
				resultado.re_exitoso = true;
				resultado.re_identificador = rs.getInt("c_documento");
			}
		} catch (SQLException e) {
			// Error no manejado
			resultado.re_exitoso = false;
			resultado.re_descripcion = e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			// Error no manejado
			resultado.re_exitoso = false;
			resultado.re_descripcion = e.getMessage();
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
	
	
	
	
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 3/11/2015
	 * @param Efectivo_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Crea un registro de efectivo de la caja chica asociada
	 ********************************************************************/
	public static Resultado_BE crearEfectivo(Efectivo_BE efectivo, Connection conexion) throws SQLException {
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
			pstmt = conexion.prepareStatement("SELECT * FROM fn_efectivo_crear(?,?,?,?);");

			// Definición de los parámetros de la función
			
			if(efectivo.ef_caja_chica != -9999){
				pstmt.setInt(1, efectivo.ef_caja_chica);
			}else{
				pstmt.setNull	(1, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(efectivo.ef_tipo != -9999){
				pstmt.setShort(2, efectivo.ef_tipo);
			}else{
				pstmt.setNull	(2, Types.SMALLINT);
				//System.out.println("1,Null");
			}
			
			if(efectivo.ef_cantidad != -9999){
				pstmt.setInt(3, efectivo.ef_cantidad);
			}else{
				pstmt.setNull	(3, Types.INTEGER);
				//System.out.println("1,Null");
			}
			
			if(efectivo.ef_monto != -9999){
				pstmt.setFloat(4, efectivo.ef_monto);
			}else{
				pstmt.setNull	(4, Types.REAL);
				//System.out.println("1,Null");
			}

			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Creación exitosa
				resultado.re_exitoso = true;
				resultado.re_identificador = rs.getInt("c_efectivo");
			}
		} catch (SQLException e) {
			// Error no manejado
			resultado.re_exitoso = false;
			resultado.re_descripcion = e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			// Error no manejado
			resultado.re_exitoso = false;
			resultado.re_descripcion = e.getMessage();
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
	
	
	
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 05/11/2015
	 * @param Busqueda_BE
	 * @param Connection
	 * @return List<Busqueda_BE>
	 * @throws Exception
	 * @Descripcion Realiza una busqueda de caja chica
	 ********************************************************************/
	public static List<Caja_Chica_BE> buscar(Busqueda_BE busqueda, Connection conexion)
			throws SQLException {
		// Declaración de variables
		List<Caja_Chica_BE> lista_resultado;
		Caja_Chica_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Caja_Chica_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion.prepareStatement("select * from fn_caja_chica_buscar(?,?,?,?)");

			// Definición de los parámetros de la función
			
			
			if(busqueda.ch_fecha1 != null){
				pstmt.setTimestamp(1, busqueda.ch_fecha1);
			}else{
				pstmt.setNull	(1, Types.NULL);
				System.out.println("1,Null");
			}
			
			if(busqueda.ch_fecha2 != null){
				pstmt.setTimestamp(2, busqueda.ch_fecha2);
			}else{
				pstmt.setNull	(2, Types.NULL);
				System.out.println("1,Null");
			}
			
			if(busqueda.ch_monto1 != -9999){
				pstmt.setFloat(3, busqueda.ch_monto1);
			}else{
				pstmt.setNull	(3, Types.REAL);
				System.out.println("1,Null");
			}
			
			if(busqueda.ch_monto2 != -9999){
				pstmt.setFloat(4, busqueda.ch_monto2);
			}else{
				pstmt.setNull	(4, Types.REAL);
				System.out.println("1,Null");
			}
			
						
			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Caja_Chica_BE();

				// Llenar entidad temporal con cada columna
				temp.ca_caja_chica 			= rs.getInt("ca_caja_chica");
				temp.ca_total_billete 		= rs.getInt("ca_total_billete");
				temp.ca_total_moneda 		= rs.getFloat("ca_total_moneda");
				temp.ca_total_efectivo 		= rs.getFloat("ca_total_efectivo");
				temp.ca_total_documentos 	= rs.getFloat("ca_total_documentos");
				temp.ca_sumatoria 			= rs.getFloat("ca_sumatoria");
				temp.ca_fondo 				= rs.getFloat("ca_fondo");
				temp.ca_variacion 			= rs.getFloat("ca_variacion");
				temp.ca_fecha 				= rs.getTimestamp("ca_fecha");
				temp.ca_fecha_hora_modificacion = rs.getTimestamp("ca_fecha_hora_modificacion");
				temp.ca_fecha_hora_creacion 	= rs.getTimestamp("ca_fecha_hora_creacion");
				

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
	 * @since 05/11/2015
	 * @param Busqueda_BE
	 * @param Connection
	 * @return List<Busqueda_BE>
	 * @throws Exception
	 * @Descripcion Lista de caja chica
	 ********************************************************************/
	public static List<Caja_Chica_BE> listarCajaChica(Caja_Chica_BE caja, Connection conexion)
			throws SQLException {
		// Declaración de variables
		List<Caja_Chica_BE> lista_resultado;
		Caja_Chica_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Caja_Chica_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion.prepareStatement("select * from fn_caja_chica_listar(?,?,?,?,?,?,?,?,?,?,?)");

			// Definición de los parámetros de la función
			
			
			if(caja.ca_caja_chica!= -9999){
				pstmt.setInt(1, caja.ca_caja_chica);
			}else{
				pstmt.setNull	(1, Types.INTEGER);
				System.out.println("1,Null");
			}
			
			if(caja.ca_total_billete!= -9999){
				pstmt.setFloat(2, caja.ca_total_billete);
			}else{
				pstmt.setNull	(2, Types.REAL);
				System.out.println("1,Null");
			}
			
			if(caja.ca_total_moneda!= -9999){
				pstmt.setFloat(3, caja.ca_total_moneda);
			}else{
				pstmt.setNull	(3, Types.REAL);
				System.out.println("1,Null");
			}
			
			if(caja.ca_total_efectivo!= -9999){
				pstmt.setFloat(4, caja.ca_total_efectivo);
			}else{
				pstmt.setNull	(4, Types.REAL);
				System.out.println("1,Null");
			}
			
			if(caja.ca_total_documentos!= -9999){
				pstmt.setFloat(5, caja.ca_total_documentos);
			}else{
				pstmt.setNull	(5, Types.REAL);
				System.out.println("1,Null");
			}
			
			if(caja.ca_sumatoria!= -9999){
				pstmt.setFloat(6, caja.ca_sumatoria);
			}else{
				pstmt.setNull	(6, Types.REAL);
				System.out.println("1,Null");
			}
			
			if(caja.ca_fondo!= -9999){
				pstmt.setFloat(7, caja.ca_fondo);
			}else{
				pstmt.setNull	(7, Types.REAL);
				System.out.println("1,Null");
			}
			
			if(caja.ca_variacion!= -9999){
				pstmt.setFloat(8, caja.ca_variacion);
			}else{
				pstmt.setNull	(8, Types.REAL);
				System.out.println("1,Null");
			}
			
			if(caja.ca_fecha!= null){
				pstmt.setTimestamp(9, caja.ca_fecha);
			}else{
				pstmt.setNull	(9, Types.NULL);
				System.out.println("1,Null");
			}
			
			if(caja.ca_fecha_hora_modificacion!= null){
				pstmt.setTimestamp(10, caja.ca_fecha_hora_modificacion);
			}else{
				pstmt.setNull	(10, Types.NULL);
				System.out.println("1,Null");
			}
			
			if(caja.ca_fecha_hora_creacion!= null){
				pstmt.setTimestamp(11, caja.ca_fecha_hora_creacion);
			}else{
				pstmt.setNull	(11, Types.NULL);
				System.out.println("1,Null");
			}
			
						
			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Caja_Chica_BE();

				// Llenar entidad temporal con cada columna
				temp.ca_caja_chica 			= rs.getInt("ca_caja_chica");
				temp.ca_total_billete 		= rs.getInt("ca_total_billete");
				temp.ca_total_moneda 		= rs.getFloat("ca_total_moneda");
				temp.ca_total_efectivo 		= rs.getFloat("ca_total_efectivo");
				temp.ca_total_documentos 	= rs.getFloat("ca_total_documentos");
				temp.ca_sumatoria 			= rs.getFloat("ca_sumatoria");
				temp.ca_fondo 				= rs.getFloat("ca_fondo");
				temp.ca_variacion 			= rs.getFloat("ca_variacion");
				temp.ca_fecha 				= rs.getTimestamp("ca_fecha");
				temp.ca_fecha_hora_modificacion = rs.getTimestamp("ca_fecha_hora_modificacion");
				temp.ca_fecha_hora_creacion 	= rs.getTimestamp("ca_fecha_hora_creacion");
				

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
	 * @since 05/11/2015
	 * @param Busqueda_BE
	 * @param Connection
	 * @return List<Busqueda_BE>
	 * @throws Exception
	 * @Descripcion Lista de registros de efectivo
	 ********************************************************************/
	public static List<Efectivo_BE> listarEfectivo(Efectivo_BE efectivo, Connection conexion)
			throws SQLException {
		// Declaración de variables
		List<Efectivo_BE> lista_resultado;
		Efectivo_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Efectivo_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion.prepareStatement("select * from fn_efectivo_listar(?,?,?,?,?)");

			// Definición de los parámetros de la función
			if(efectivo.ef_efectivo!= -9999){
				pstmt.setInt(1, efectivo.ef_efectivo);
			}else{
				pstmt.setNull	(1, Types.INTEGER);
				System.out.println("1,Null");
			}
			
			if(efectivo.ef_caja_chica!= -9999){
				pstmt.setInt(2, efectivo.ef_caja_chica);
			}else{
				pstmt.setNull	(2, Types.INTEGER);
				System.out.println("1,Null");
			}
			
			if(efectivo.ef_tipo!= -9999){
				pstmt.setInt(3, efectivo.ef_tipo);
			}else{
				pstmt.setNull	(3, Types.SMALLINT);
				System.out.println("1,Null");
			}
			
			if(efectivo.ef_cantidad!= -9999){
				pstmt.setInt(4, efectivo.ef_cantidad);
			}else{
				pstmt.setNull	(4, Types.INTEGER);
				System.out.println("1,Null");
			}
			
			if(efectivo.ef_monto!= -9999){
				pstmt.setFloat(5, efectivo.ef_monto);
			}else{
				pstmt.setNull	(5, Types.REAL);
				System.out.println("1,Null");
			}
			
						
			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Efectivo_BE();

				// Llenar entidad temporal con cada columna
				temp.ef_efectivo 	= rs.getInt("ef_efectivo");
				temp.ef_caja_chica 	= rs.getInt("ef_caja_chica");
				temp.ef_tipo 		= rs.getShort("ef_tipo");
				temp.ef_cantidad 	= rs.getInt("ef_cantidad");
				temp.ef_monto 		= rs.getFloat("ef_monto");

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
	 * @since 05/11/2015
	 * @param Busqueda_BE
	 * @param Connection
	 * @return List<Busqueda_BE>
	 * @throws Exception
	 * @Descripcion Lista de registros de documentos
	 ********************************************************************/
	public static List<Documento_BE> listarDocumento(Documento_BE documento, Connection conexion)
			throws SQLException {
		// Declaración de variables
		List<Documento_BE> lista_resultado;
		Documento_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Documento_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion.prepareStatement("select * from fn_documento_listar(?,?,?,?,?)");

			// Definición de los parámetros de la función
			if(documento.do_documento!= -9999){
				pstmt.setInt(1, documento.do_documento);
			}else{
				pstmt.setNull	(1, Types.INTEGER);
				System.out.println("1,Null");
			}
			
			if(documento.do_caja_chica!= -9999){
				pstmt.setInt(2, documento.do_caja_chica);
			}else{
				pstmt.setNull	(2, Types.INTEGER);
				System.out.println("1,Null");
			}
			
			if(documento.do_nit!= -9999){
				pstmt.setInt(3, documento.do_nit);
			}else{
				pstmt.setNull	(3, Types.INTEGER);
				System.out.println("1,Null");
			}
			
			if(documento.do_nombre!= null){
				pstmt.setString(4, documento.do_nombre);
			}else{
				pstmt.setNull	(4, Types.VARCHAR);
				System.out.println("1,Null");
			}
			
			if(documento.do_monto!= -9999){
				pstmt.setFloat(5, documento.do_monto);
			}else{
				pstmt.setNull	(5, Types.REAL);
				System.out.println("1,Null");
			}
			
						
			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Documento_BE();

				// Llenar entidad temporal con cada columna
				temp.do_documento 	= rs.getInt("do_documento");
				temp.do_caja_chica 	= rs.getInt("do_caja_chica");
				temp.do_nit 		= rs.getInt("do_nit");
				temp.do_nombre 		= rs.getString("do_nombre");
				temp.do_monto 		= rs.getFloat("do_monto");

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
	 * @since 8/11/2015
	 * @param Caja_Chica_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Modifica un registro de caja chica
	 ********************************************************************/
	public static Resultado_BE modificarCajaChica(Caja_Chica_BE caja, Connection conexion) throws SQLException {
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
			pstmt = conexion.prepareStatement("SELECT * FROM fn_caja_chica_modificar(?,?,?,?,?,?,?,?,?,?,?);");

			// Definición de los parámetros de la función
			if(caja.ca_caja_chica != -9999){
				pstmt.setInt(1, caja.ca_caja_chica);
			}else{
				pstmt.setNull	(1, Types.INTEGER);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_total_billete != -9999){
				pstmt.setFloat(2, caja.ca_total_billete);
			}else{
				pstmt.setNull	(2, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_total_moneda != -9999){
				pstmt.setFloat(3, caja.ca_total_moneda);
			}else{
				pstmt.setNull	(3, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_total_efectivo != -9999){
				pstmt.setFloat(4, caja.ca_total_efectivo);
			}else{
				pstmt.setNull	(4, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_total_documentos != -9999){
				pstmt.setFloat(5, caja.ca_total_documentos);
			}else{
				pstmt.setNull	(5, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_sumatoria != -9999){
				pstmt.setFloat(6, caja.ca_sumatoria);
			}else{
				pstmt.setNull	(6, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_fondo != -9999){
				pstmt.setFloat(7, caja.ca_fondo);
			}else{
				pstmt.setNull	(7, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_variacion != -9999){
				pstmt.setFloat(8, caja.ca_variacion);
			}else{
				pstmt.setNull	(8, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(caja.ca_fecha != null){
				pstmt.setTimestamp(9, caja.ca_fecha);
			}else{
				pstmt.setNull	(9, Types.NULL);
			}
			
			if(caja.ca_fecha_hora_modificacion != null){
				pstmt.setTimestamp(10, caja.ca_fecha_hora_modificacion);
			}else{
				pstmt.setNull	(10, Types.NULL);
				System.out.println("1,Null");
			}
			
			if(caja.ca_fecha_hora_creacion != null){
				pstmt.setTimestamp(11, caja.ca_fecha_hora_creacion);
			}else{
				pstmt.setNull	(11, Types.NULL);
				System.out.println("1,Null");
			}

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
			e.printStackTrace();
		} catch (Exception e) {
			// Error no manejado
			resultado.re_exitoso = false;
			resultado.re_descripcion = e.getMessage();
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
	
	
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 8/11/2015
	 * @param Efectivo_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Modifica un registro de efectivo
	 ********************************************************************/
	public static Resultado_BE modificarEfectivo(Efectivo_BE efectivo, Connection conexion) throws SQLException {
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
			pstmt = conexion.prepareStatement("SELECT * FROM fn_efectivo_modificar(?,?,?,?,?);");

			// Definición de los parámetros de la función
			if(efectivo.ef_efectivo != -9999){
				pstmt.setInt(1, efectivo.ef_efectivo);
			}else{
				pstmt.setNull	(1, Types.INTEGER);
				//System.out.println("1,Null");
			}
			
			if(efectivo.ef_caja_chica != -9999){
				pstmt.setInt(2, efectivo.ef_caja_chica);
			}else{
				pstmt.setNull	(2, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(efectivo.ef_tipo != -9999){
				pstmt.setShort(3, efectivo.ef_tipo);
			}else{
				pstmt.setNull	(3, Types.SMALLINT);
				//System.out.println("1,Null");
			}
			
			if(efectivo.ef_cantidad != -9999){
				pstmt.setInt(4, efectivo.ef_cantidad);
			}else{
				pstmt.setNull	(4, Types.INTEGER);
				//System.out.println("1,Null");
			}
			
			if(efectivo.ef_monto != -9999){
				pstmt.setFloat(5, efectivo.ef_monto);
			}else{
				pstmt.setNull	(5, Types.REAL);
				//System.out.println("1,Null");
			}

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
			e.printStackTrace();
		} catch (Exception e) {
			// Error no manejado
			resultado.re_exitoso = false;
			resultado.re_descripcion = e.getMessage();
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
	
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 8/11/2015
	 * @param Documento_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Elimina un conjunto de registros basados en el id de caja chica
	 ********************************************************************/
	public static Resultado_BE eliminarDocumento(Documento_BE documento, Connection conexion) throws SQLException {
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
			pstmt = conexion.prepareStatement("SELECT * FROM fn_documento_eliminar(?);");

			// Definición de los parámetros de la función
			if(documento.do_caja_chica!= -9999){
				pstmt.setInt(1, documento.do_caja_chica);
			}else{
				pstmt.setNull	(1, Types.INTEGER);
				//System.out.println("1,Null");
			}

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
			e.printStackTrace();
		} catch (Exception e) {
			// Error no manejado
			resultado.re_exitoso = false;
			resultado.re_descripcion = e.getMessage();
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

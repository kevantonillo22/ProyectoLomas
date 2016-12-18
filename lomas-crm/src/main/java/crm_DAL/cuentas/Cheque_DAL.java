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
import crm_BE.cuentas.Cheque_BE;

public class Cheque_DAL {
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 20/10/2015
	 * @param Cheque_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Crea un registro de cheque
	 ********************************************************************/
	public static Resultado_BE crear(Cheque_BE cheque, Connection conexion) throws SQLException {
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
			pstmt = conexion.prepareStatement("SELECT * FROM fn_cheque_crear(?,?,?,?,?,?,?,?);");

			// Definición de los parámetros de la función
			if(cheque.ch_numero != -9999){
				pstmt.setInt(1, cheque.ch_numero);
			}else{
				pstmt.setNull	(1, Types.INTEGER);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_lugar != null){
				pstmt.setString(2, cheque.ch_lugar);
			}else{
				pstmt.setNull	(2, Types.VARCHAR);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_fecha != null){
				pstmt.setTimestamp(3, cheque.ch_fecha);
			}else{
				pstmt.setNull	(3, Types.NULL);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_nombre != null){
				pstmt.setString(4, cheque.ch_nombre);
			}else{
				pstmt.setNull	(4, Types.VARCHAR);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_cantidad != -9999){
				pstmt.setFloat(5, cheque.ch_cantidad);
			}else{
				pstmt.setNull	(5, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_motivo != null){
				pstmt.setString(6, cheque.ch_motivo);
			}else{
				pstmt.setNull	(6, Types.VARCHAR);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_imagen != null){
				pstmt.setString(7, cheque.ch_imagen);
			}else{
				pstmt.setNull	(7, Types.VARCHAR);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_fecha_hora != null){
				pstmt.setTimestamp(8, cheque.ch_fecha_hora);
			}else{
				pstmt.setNull	(8, Types.NULL);
				//System.out.println("1,Null");
			}

			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Creación exitosa
				resultado.re_exitoso = true;
				resultado.re_identificador = rs.getInt("c_cheque");
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
	 * @since 21/10/2015
	 * @param Cheque_BE
	 * @param Connection
	 * @return List<Cheque_BE>
	 * @throws Exception
	 * @Descripcion Lista los datos de cheque
	 ********************************************************************/
	public static List<Cheque_BE> listar(Cheque_BE cheque, Connection conexion)
			throws SQLException {
		// Declaración de variables
		List<Cheque_BE> lista_resultado;
		Cheque_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Cheque_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion.prepareStatement("select * from fn_cheque_listar(?,?,?,?,?,?,?,?,?)");

			// Definición de los parámetros de la función
			if(cheque.ch_cheque != -9999){
				pstmt.setInt(1, cheque.ch_cheque);
			}else{
				pstmt.setNull	(1, Types.INTEGER);
				System.out.println("1,Null");
			}
			
			// Definición de los parámetros de la función
			if(cheque.ch_numero != -9999){
				pstmt.setInt(2, cheque.ch_numero);
			}else{
				pstmt.setNull	(2, Types.INTEGER);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_lugar != null){
				pstmt.setString(3, cheque.ch_lugar);
			}else{
				pstmt.setNull	(3, Types.VARCHAR);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_fecha != null){
				pstmt.setTimestamp(4, cheque.ch_fecha);
			}else{
				pstmt.setNull	(4, Types.NULL);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_nombre != null){
				pstmt.setString(5, cheque.ch_nombre);
			}else{
				pstmt.setNull	(5, Types.VARCHAR);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_cantidad != -9999){
				pstmt.setFloat(6, cheque.ch_cantidad);
			}else{
				pstmt.setNull	(6, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_motivo != null){
				pstmt.setString(7, cheque.ch_motivo);
			}else{
				pstmt.setNull	(7, Types.VARCHAR);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_imagen != null){
				pstmt.setString(8, cheque.ch_imagen);
			}else{
				pstmt.setNull	(8, Types.VARCHAR);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_fecha_hora != null){
				pstmt.setTimestamp(9, cheque.ch_fecha_hora);
			}else{
				pstmt.setNull	(9, Types.TIMESTAMP);
				//System.out.println("1,Null");
			}

			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Cheque_BE();

				// Llenar entidad temporal con cada columna
				temp.ch_cheque 		= rs.getInt("ch_cheque");
				temp.ch_numero 		= rs.getInt("ch_numero");
				temp.ch_lugar 		= rs.getString("ch_lugar");
				temp.ch_fecha 		= rs.getTimestamp("ch_fecha");
				temp.ch_nombre 		= rs.getString("ch_nombre");
				temp.ch_cantidad 	= rs.getFloat("ch_cantidad");
				temp.ch_motivo 		= rs.getString("ch_motivo");
				temp.ch_imagen 		= rs.getString("ch_imagen");
				temp.ch_fecha_hora 	= rs.getTimestamp("ch_fecha_hora_creacion");
				

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
	 * @author Kevin Cardona
	 * @since 21/10/2015
	 * @param Busqueda_BE
	 * @param Connection
	 * @return List<Busqueda_BE>
	 * @throws Exception
	 * @Descripcion Realiza una busqueda de cheques
	 ********************************************************************/
	public static List<Cheque_BE> buscar(Busqueda_BE busqueda, Connection conexion)
			throws SQLException {
		// Declaración de variables
		List<Cheque_BE> lista_resultado;
		Cheque_BE temp;
		PreparedStatement pstmt;
		ResultSet rs;

		// Inicialización de variables
		lista_resultado = new ArrayList<Cheque_BE>();
		pstmt = null;
		rs = null;

		try {
			// Declaración de la función
			pstmt = conexion.prepareStatement("select * from fn_cheque_buscar(?,?,?,?,?)");

			// Definición de los parámetros de la función
			if(busqueda.ch_general != null){
				pstmt.setString(1, busqueda.ch_general);
			}else{
				pstmt.setNull	(1, Types.VARCHAR);
				System.out.println("1,Null");
			}
			
			if(busqueda.ch_fecha1 != null){
				pstmt.setTimestamp(2, busqueda.ch_fecha1);
			}else{
				pstmt.setNull	(2, Types.NULL);
				System.out.println("1,Null");
			}
			
			if(busqueda.ch_fecha2 != null){
				pstmt.setTimestamp(3, busqueda.ch_fecha2);
			}else{
				pstmt.setNull	(3, Types.NULL);
				System.out.println("1,Null");
			}
			
			if(busqueda.ch_monto1 != -9999){
				pstmt.setFloat(4, busqueda.ch_monto1);
			}else{
				pstmt.setNull	(4, Types.REAL);
				System.out.println("1,Null");
			}
			
			if(busqueda.ch_monto2 != -9999){
				pstmt.setFloat(5, busqueda.ch_monto2);
			}else{
				pstmt.setNull	(5, Types.REAL);
				System.out.println("1,Null");
			}
			
						
			// Ejecuta la función
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// Inicializar la entidad temporal
				temp = new Cheque_BE();

				// Llenar entidad temporal con cada columna
				temp.ch_cheque 		= rs.getInt("ch_cheque");
				temp.ch_numero 		= rs.getInt("ch_numero");
				temp.ch_lugar 		= rs.getString("ch_lugar");
				temp.ch_fecha 		= rs.getTimestamp("ch_fecha");
				temp.ch_nombre 		= rs.getString("ch_nombre");
				temp.ch_cantidad 	= rs.getFloat("ch_cantidad");
				temp.ch_motivo 		= rs.getString("ch_motivo");
				temp.ch_imagen 		= rs.getString("ch_imagen");
				temp.ch_fecha_hora 	= rs.getTimestamp("ch_fecha_hora_creacion");
				

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
	 * @since 22/10/2015
	 * @param Cheque_BE
	 * @param Connection
	 * @return Resultado_BE
	 * @throws SQLException
	 * @throws Exception
	 * @Descripcion Modifica un cheque
	 ********************************************************************/
	public static Resultado_BE modificar(Cheque_BE cheque, Connection conexion) throws SQLException {
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
			pstmt = conexion.prepareStatement("SELECT * FROM fn_cheque_modificar(?,?,?,?,?,?,?,?,?);");

			// Definición de los parámetros de la función
			if(cheque.ch_cheque != -9999){
				pstmt.setInt(1, cheque.ch_cheque);
			}else{
				pstmt.setNull	(1, Types.INTEGER);
				System.out.println("1,Null");
			}
			
			// Definición de los parámetros de la función
			if(cheque.ch_numero != -9999){
				pstmt.setInt(2, cheque.ch_numero);
			}else{
				pstmt.setNull	(2, Types.INTEGER);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_lugar != null){
				pstmt.setString(3, cheque.ch_lugar);
			}else{
				pstmt.setNull	(3, Types.VARCHAR);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_fecha != null){
				pstmt.setTimestamp(4, cheque.ch_fecha);
			}else{
				pstmt.setNull	(4, Types.NULL);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_nombre != null){
				pstmt.setString(5, cheque.ch_nombre);
			}else{
				pstmt.setNull	(5, Types.VARCHAR);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_cantidad != -9999){
				pstmt.setFloat(6, cheque.ch_cantidad);
			}else{
				pstmt.setNull	(6, Types.REAL);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_motivo != null){
				pstmt.setString(7, cheque.ch_motivo);
			}else{
				pstmt.setNull	(7, Types.VARCHAR);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_imagen != null){
				pstmt.setString(8, cheque.ch_imagen);
			}else{
				pstmt.setNull	(8, Types.VARCHAR);
				//System.out.println("1,Null");
			}
			
			if(cheque.ch_fecha_hora != null){
				pstmt.setTimestamp(9, cheque.ch_fecha_hora);
			}else{
				pstmt.setNull	(9, Types.TIMESTAMP);
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
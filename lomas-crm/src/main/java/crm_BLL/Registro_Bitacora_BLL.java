package crm_BLL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Registro_Bitacora_BE;
import crm_DAL.Bitacora_DAL;

public class Registro_Bitacora_BLL {

	
	/*********************************************************************
	 * @author kev
	 * @since 19/05/2014
	 * @param Registro_Bitacora_BE
	 * @return List<Registro_Bitacora_BE>
	 * @throws Exception
	 * @Descripcion Se obtienen los registros de la bitacora
	 ********************************************************************/
	public static List<Registro_Bitacora_BE> buscar(String filtro, String inicio, String fin, String start, String length) {
		// Declaraci�n de variables
		Connection conexion;
		List<Registro_Bitacora_BE> lista_resultado;

		// Inicializaci�n de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Registro_Bitacora_BE>();

		// Verifica que la conexi�n no sea nula
		if (conexion != null) {
			try {
				// L�gica de negocio

				// Aquí se validan los par�metros y se realiza la llamada a DAL
				lista_resultado = Bitacora_DAL.buscar(filtro, inicio, fin, start, length,
						conexion);
				conexion.commit();

			} catch (Exception e) {
				// Error no manejado
				lista_resultado = null;
				try {
					conexion.rollback();
				} catch (SQLException e1) {
					//Error no manejado en la conexi�n
				}
			} finally {
				if (conexion != null) {
					try {
						conexion.close();
					} catch (SQLException e) {
						//Error no manejado en la conexi�n
					}
				}
			}
		} else {
			// Error de conexi�n
			lista_resultado = null;
		}
		return lista_resultado;
	}
	
	public static int contar(String filtro, String inicio, String fin){
		int resultado=-1;
		Connection conexion;
		conexion = General_BLL.obtenerConexion();
		try {
			resultado= Bitacora_DAL.contar(filtro, inicio, fin, conexion);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	
}

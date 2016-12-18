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
		// Declaración de variables
		Connection conexion;
		List<Registro_Bitacora_BE> lista_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Registro_Bitacora_BE>();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			try {
				// Lógica de negocio

				// AquÃ­ se validan los parámetros y se realiza la llamada a DAL
				lista_resultado = Bitacora_DAL.buscar(filtro, inicio, fin, start, length,
						conexion);
				conexion.commit();

			} catch (Exception e) {
				// Error no manejado
				lista_resultado = null;
				try {
					conexion.rollback();
				} catch (SQLException e1) {
					//Error no manejado en la conexión
				}
			} finally {
				if (conexion != null) {
					try {
						conexion.close();
					} catch (SQLException e) {
						//Error no manejado en la conexión
					}
				}
			}
		} else {
			// Error de conexión
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

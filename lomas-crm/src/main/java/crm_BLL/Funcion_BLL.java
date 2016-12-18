package crm_BLL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Funcion_BE;
import crm_DAL.Funcion_DAL;

public class Funcion_BLL {

	/*********************************************************************
	 * @author kev
	 * @since 12/05/2014
	 * @param Funcion_BE
	 * @return List<Funcion_BE>
	 * @throws Exception
	 * @Descripcion Lógica de negocio para las funciones	
	 ********************************************************************/
	public static List<Funcion_BE> listar(
			Funcion_BE funcion) {
		// Declaración de variables
		Connection conexion;
		List<Funcion_BE> lista_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Funcion_BE>();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			try {
				// Lógica de negocio

				// AquÃ­ se validan los parámetros y se realiza la llamada a DAL
				lista_resultado = Funcion_DAL.listar(funcion,
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
		
		}
		return lista_resultado;
	}
	
	
}

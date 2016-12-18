package crm_BLL.garita;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Bitacora_BE;
import crm_BE.Funciones;
import crm_BE.Resultado_BE;
import crm_BE.Sesion_BE;
import crm_BE.garita.Ingreso_BE;
import crm_BLL.General_BLL;
import crm_DAL.Bitacora_DAL;
import crm_DAL.garita.Ingreso_DAL;

public class Ingreso_BLL {
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 10/12/2016
	 * @param Ingreso_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion M�todo que crea un registro de ingreso
	 ********************************************************************/
	public static Resultado_BE crear(Ingreso_BE ingreso, Sesion_BE sesion) {
		// Declaraci�n de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE creacion_resultado;

		// Inicializaci�n de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		creacion_resultado = new Resultado_BE();

		// Verifica que la conexi�n no sea nula
		if (conexion != null) {
			// L�gica de negocio
			try {
				// Aqu� se completa toda la l�gica de negocio

				// Realizamos la creaci�n
				creacion_resultado = Ingreso_DAL.crear(ingreso, conexion);

				// Validamos el resultado de la operaci�n
				if (creacion_resultado.re_exitoso) {
					// Guardamos la bit�cora
					bitacora.bi_usuario = sesion.se_usuario;
					bitacora.bi_funcion = Funciones.INGRESO_EGRESO; // AGREGAR LA FUNCION
					bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL.obtenerFecha());
					bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());
					bitacora.bi_descripcion = "Se cre� un registro de ingreso de persona en garita";
					bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
					bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

					if (bitacora_resultado.re_exitoso) {
						// Operaci�n exitosa
						app_resultado.re_exitoso = true;
						app_resultado.re_codigo = 1;
						app_resultado.re_identificador = creacion_resultado.re_identificador;
						app_resultado.re_descripcion = "Operaci�n realizada correctamente";
						conexion.commit();
					} else {
						// Error en inserci�n de bit�cora
						app_resultado.re_exitoso = false;
						app_resultado.re_codigo = -1;
						app_resultado.re_descripcion = bitacora_resultado.re_descripcion;
						conexion.rollback();
					}
				} else {
					// Error en la creaci�n
					app_resultado.re_exitoso = false;
					app_resultado.re_codigo = -2;
					app_resultado.re_descripcion = creacion_resultado.re_descripcion;
					conexion.rollback();
				}
			} catch (Exception e) {
				// Error no manejado
				app_resultado.re_exitoso = false;
				app_resultado.re_codigo = 0;
				app_resultado.re_descripcion = e.getMessage();
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
			app_resultado.re_exitoso = false;
			app_resultado.re_codigo = 0;
			app_resultado.re_descripcion = "No se pudo obtener la conexi�n con la base de datos";
		}
		return app_resultado;
	}
	
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 11/12/2016
	 * @param Ingreso_BE
	 * @return List<Ingreso_BE>
	 * @throws Exception
	 * @Descripcion M�todo que obtiene los registros de los ingresos a garita
	 ********************************************************************/
	public static List<Ingreso_BE> listar(Ingreso_BE ingreso) {
		// Declaraci�n de variables
		Connection conexion;
		List<Ingreso_BE> lista_resultado;

		// Inicializaci�n de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Ingreso_BE>();

		// Verifica que la conexi�n no sea nula
		if (conexion != null) {
			try {
				// L�gica de negocio

				// Aqu� se validan los par�metros y se realiza la llamada a DAL
				lista_resultado = Ingreso_DAL.listar(ingreso, conexion);
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

}

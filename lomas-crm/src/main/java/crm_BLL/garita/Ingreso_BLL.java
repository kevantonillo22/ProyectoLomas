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
	 * @Descripcion Método que crea un registro de ingreso
	 ********************************************************************/
	public static Resultado_BE crear(Ingreso_BE ingreso, Sesion_BE sesion) {
		// Declaración de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE creacion_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		creacion_resultado = new Resultado_BE();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			// Lógica de negocio
			try {
				// Aquí se completa toda la lógica de negocio

				// Realizamos la creación
				creacion_resultado = Ingreso_DAL.crear(ingreso, conexion);

				// Validamos el resultado de la operación
				if (creacion_resultado.re_exitoso) {
					// Guardamos la bitácora
					bitacora.bi_usuario = sesion.se_usuario;
					bitacora.bi_funcion = Funciones.INGRESO_EGRESO; // AGREGAR LA FUNCION
					bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL.obtenerFecha());
					bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());
					bitacora.bi_descripcion = "Se creó un registro de ingreso de persona en garita";
					bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
					bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

					if (bitacora_resultado.re_exitoso) {
						// Operación exitosa
						app_resultado.re_exitoso = true;
						app_resultado.re_codigo = 1;
						app_resultado.re_identificador = creacion_resultado.re_identificador;
						app_resultado.re_descripcion = "Operación realizada correctamente";
						conexion.commit();
					} else {
						// Error en inserción de bitácora
						app_resultado.re_exitoso = false;
						app_resultado.re_codigo = -1;
						app_resultado.re_descripcion = bitacora_resultado.re_descripcion;
						conexion.rollback();
					}
				} else {
					// Error en la creación
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
			app_resultado.re_exitoso = false;
			app_resultado.re_codigo = 0;
			app_resultado.re_descripcion = "No se pudo obtener la conexión con la base de datos";
		}
		return app_resultado;
	}
	
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 11/12/2016
	 * @param Ingreso_BE
	 * @return List<Ingreso_BE>
	 * @throws Exception
	 * @Descripcion Método que obtiene los registros de los ingresos a garita
	 ********************************************************************/
	public static List<Ingreso_BE> listar(Ingreso_BE ingreso) {
		// Declaración de variables
		Connection conexion;
		List<Ingreso_BE> lista_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Ingreso_BE>();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			try {
				// Lógica de negocio

				// Aquí se validan los parámetros y se realiza la llamada a DAL
				lista_resultado = Ingreso_DAL.listar(ingreso, conexion);
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

}

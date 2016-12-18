package crm_BLL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Bitacora_BE;
import crm_BE.Funciones;
import crm_BE.Parametro_BE;
import crm_BE.Resultado_BE;
import crm_BE.Sesion_BE;
import crm_DAL.Bitacora_DAL;
import crm_DAL.Parametro_DAL;

/*********************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 21/05/2014
 * @FechaModificacion 21/05/2014
 * @Descripcion Lógica de negocio de los parámetros del sistema.
 ********************************************************************/
public class Parametro_BLL {
	/*********************************************************************
	 * @author rbarrios
	 * @since 21/05/2014
	 * @param Parametro_BE
	 * @return List<Parametro_BE>
	 * @throws Exception
	 * @Descripcion Listar los parámetros del sistema.
	 ********************************************************************/
	public static List<Parametro_BE> listar(Parametro_BE parametro) {
		// Declaración de variables
		Connection conexion;
		List<Parametro_BE> lista_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Parametro_BE>();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			try {
				// AquÃ­ se validan los parámetros y se realiza la llamada a
				// DAL
				lista_resultado = Parametro_DAL.listar(parametro, conexion);
				conexion.commit();

			} catch (Exception e) {
				// Error no manejado
				lista_resultado = null;
				try {
					conexion.rollback();
				} catch (SQLException e1) {
					// Error no manejado en la conexión
				}
			} finally {
				if (conexion != null) {
					try {
						conexion.close();
					} catch (SQLException e) {
						// Error no manejado en la conexión
					}
				}
			}
		} else {
			// Error de conexión
			lista_resultado = null;
		}
		return lista_resultado;
	}

	/*********************************************************************
	 * @author rbarrios
	 * @since 21/05/2014
	 * @param Parametro_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Modifica los parámetros del sistema.
	 ********************************************************************/
	public static Resultado_BE modificar(Parametro_BE parametro, Sesion_BE sesion) {
		// Declaración de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE modificacion_resultado;
		Resultado_BE creacion_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		modificacion_resultado = new Resultado_BE();
		creacion_resultado = new Resultado_BE();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			// Lógica de negocio
			try {
				// Consultamos el parámetro actual
				Parametro_BE param_busqueda = new Parametro_BE();
				param_busqueda.pa_codigo_parametro = parametro.pa_codigo_parametro;
				param_busqueda.pa_estado = 1; // Vigente
				List<Parametro_BE> lst_parametro = Parametro_DAL.listar(param_busqueda, conexion);

				if (!lst_parametro.get(0).pa_valor.equalsIgnoreCase(parametro.pa_valor)) {
					// Obtenemos la fecha actual
					java.sql.Timestamp fecha_actual = java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());

					// Actualizamos el valor del registro actual marcandolo como
					// no vigente y su fecha fin de vigencia
					Parametro_BE parametro_modificar = new Parametro_BE();
					parametro_modificar.pa_parametro = lst_parametro.get(0).pa_parametro;
					parametro_modificar.pa_estado = 2; // No vigente
					parametro_modificar.pa_fecha_fin = fecha_actual;

					// Realizamos la modificación
					modificacion_resultado = Parametro_DAL.modificar(parametro_modificar, conexion);

					// Validamos la Operación
					if (modificacion_resultado.re_exitoso) {
						// Creamos el nuevo registro con los valores
						// actualizados
						Parametro_BE parametro_crear = new Parametro_BE();
						parametro_crear.pa_codigo_parametro = lst_parametro.get(0).pa_codigo_parametro;
						parametro_crear.pa_nombre = lst_parametro.get(0).pa_nombre;
						parametro_crear.pa_descripcion = lst_parametro.get(0).pa_descripcion;
						parametro_crear.pa_estado = 1; // Vigente
						parametro_crear.pa_valor = parametro.pa_valor; // El
																	   // nuevo
																	   // valor
						parametro_crear.pa_fecha_inicio = fecha_actual;

						// Realizamos la creación del nuevo registro
						creacion_resultado = Parametro_DAL.crear(parametro_crear, conexion);

						if (creacion_resultado.re_exitoso) {
							// Guardamos la bitácora
							bitacora.bi_usuario = sesion.se_usuario;
							bitacora.bi_funcion = Funciones.MODIFICAR_PARAMETROS;
							bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL.obtenerFecha());
							bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());
							bitacora.bi_descripcion = "Se modificó el parámetro " + parametro.pa_nombre + " código "
									+ parametro.pa_codigo_parametro;
							bitacora.bi_tipo = 1; // 1:Administrativo;
												  // 2:Operativo
							bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

							if (bitacora_resultado.re_exitoso) {
								// Operación exitosa
								app_resultado.re_exitoso = true;
								app_resultado.re_codigo = 1;
								app_resultado.re_filas_afectadas = modificacion_resultado.re_filas_afectadas;
								app_resultado.re_descripcion = "El parámetro fue modificado exitosamente";
								conexion.commit();
							} else {
								// Error en inserción de bitácora
								app_resultado.re_exitoso = false;
								app_resultado.re_codigo = -1;
								app_resultado.re_descripcion = bitacora_resultado.re_descripcion;
								conexion.rollback();
							}
						} else {
							// Error en la creación del nuevo parámetro
							app_resultado.re_exitoso = false;
							app_resultado.re_codigo = -2;
							app_resultado.re_descripcion = creacion_resultado.re_descripcion;
							conexion.rollback();
						}
					} else {
						// Error en la modificación
						app_resultado.re_exitoso = false;
						app_resultado.re_codigo = -3;
						app_resultado.re_descripcion = modificacion_resultado.re_descripcion;
						conexion.rollback();
					}
				} else {
					// No se modificó el valor
					app_resultado.re_exitoso = true;
					app_resultado.re_codigo = 1;
					app_resultado.re_descripcion = "El valor del parámetro no sufrió modificaciones";
				}
			} catch (Exception e) {
				// Error no manejado
				app_resultado.re_exitoso = false;
				app_resultado.re_codigo = 0;
				app_resultado.re_descripcion = e.getMessage();
				try {
					conexion.rollback();
				} catch (SQLException e1) {
					// Error no manejado en la conexión
				}
			} finally {
				if (conexion != null) {
					try {
						conexion.close();
					} catch (SQLException e) {
						// Error no manejado en la conexión
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
	 * @author eyat
	 * @since Sep 10, 2014
	 * @param Parametro_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Modificar el valor de los parametros de reservas de unidades
	 *              dentales.
	 ********************************************************************/
	public static Resultado_BE modificarValor(Parametro_BE objParametro, Sesion_BE sesion) {
		// Declaración de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE modificacion_resultado;
		ArrayList<Parametro_BE> listaParametros;
		Parametro_BE objParametroTemp;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		modificacion_resultado = new Resultado_BE();

		// Lógica de negocio
		try {
			// AquÃ­ se completa toda la Lógica de negocio
			listaParametros = (ArrayList<Parametro_BE>) Parametro_BLL.listar(objParametro);
			objParametroTemp = listaParametros.get(0);

			objParametro.pa_parametro = objParametroTemp.pa_parametro;
			objParametro.pa_estado = 3;
			// Realizamos la modificación
			modificacion_resultado = Parametro_DAL.modificar(objParametro, conexion);

			// Validamos la Operación
			if (modificacion_resultado.re_exitoso) {
				// Guardamos la bitácora
				bitacora.bi_usuario = sesion.se_usuario;
				bitacora.bi_funcion = Funciones.MODIFICAR_PARAMETROS;
				bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL.obtenerFecha());
				bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());
				bitacora.bi_descripcion = "Modificar parametro de reservas de unidades dentales.";
				bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
				bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

				if (bitacora_resultado.re_exitoso) {
					// Operación exitosa
					app_resultado.re_exitoso = true;
					app_resultado.re_codigo = 1;
					app_resultado.re_filas_afectadas = modificacion_resultado.re_filas_afectadas;
					app_resultado.re_descripcion = "OPERACION EXITOSA";
					conexion.commit();
				} else {
					// Error en inserción de bitácora
					app_resultado.re_exitoso = false;
					app_resultado.re_codigo = -1;
					app_resultado.re_descripcion = bitacora_resultado.re_descripcion;
					conexion.rollback();
				}
			} else {
				// Error en la modificación
				app_resultado.re_exitoso = false;
				app_resultado.re_codigo = -2;
				app_resultado.re_descripcion = modificacion_resultado.re_descripcion;
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
				// Error no manejado en la conexión
			}
		} finally {
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					// Error no manejado en la conexión
				}
			}
		}

		return app_resultado;
	}
}

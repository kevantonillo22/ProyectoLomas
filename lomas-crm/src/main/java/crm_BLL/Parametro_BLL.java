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
 * @Descripcion L�gica de negocio de los par�metros del sistema.
 ********************************************************************/
public class Parametro_BLL {
	/*********************************************************************
	 * @author rbarrios
	 * @since 21/05/2014
	 * @param Parametro_BE
	 * @return List<Parametro_BE>
	 * @throws Exception
	 * @Descripcion Listar los par�metros del sistema.
	 ********************************************************************/
	public static List<Parametro_BE> listar(Parametro_BE parametro) {
		// Declaraci�n de variables
		Connection conexion;
		List<Parametro_BE> lista_resultado;

		// Inicializaci�n de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Parametro_BE>();

		// Verifica que la conexi�n no sea nula
		if (conexion != null) {
			try {
				// Aquí se validan los par�metros y se realiza la llamada a
				// DAL
				lista_resultado = Parametro_DAL.listar(parametro, conexion);
				conexion.commit();

			} catch (Exception e) {
				// Error no manejado
				lista_resultado = null;
				try {
					conexion.rollback();
				} catch (SQLException e1) {
					// Error no manejado en la conexi�n
				}
			} finally {
				if (conexion != null) {
					try {
						conexion.close();
					} catch (SQLException e) {
						// Error no manejado en la conexi�n
					}
				}
			}
		} else {
			// Error de conexi�n
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
	 * @Descripcion Modifica los par�metros del sistema.
	 ********************************************************************/
	public static Resultado_BE modificar(Parametro_BE parametro, Sesion_BE sesion) {
		// Declaraci�n de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE modificacion_resultado;
		Resultado_BE creacion_resultado;

		// Inicializaci�n de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		modificacion_resultado = new Resultado_BE();
		creacion_resultado = new Resultado_BE();

		// Verifica que la conexi�n no sea nula
		if (conexion != null) {
			// L�gica de negocio
			try {
				// Consultamos el par�metro actual
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

					// Realizamos la modificaci�n
					modificacion_resultado = Parametro_DAL.modificar(parametro_modificar, conexion);

					// Validamos la Operaci�n
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

						// Realizamos la creaci�n del nuevo registro
						creacion_resultado = Parametro_DAL.crear(parametro_crear, conexion);

						if (creacion_resultado.re_exitoso) {
							// Guardamos la bit�cora
							bitacora.bi_usuario = sesion.se_usuario;
							bitacora.bi_funcion = Funciones.MODIFICAR_PARAMETROS;
							bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL.obtenerFecha());
							bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());
							bitacora.bi_descripcion = "Se modific� el par�metro " + parametro.pa_nombre + " c�digo "
									+ parametro.pa_codigo_parametro;
							bitacora.bi_tipo = 1; // 1:Administrativo;
												  // 2:Operativo
							bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

							if (bitacora_resultado.re_exitoso) {
								// Operaci�n exitosa
								app_resultado.re_exitoso = true;
								app_resultado.re_codigo = 1;
								app_resultado.re_filas_afectadas = modificacion_resultado.re_filas_afectadas;
								app_resultado.re_descripcion = "El par�metro fue modificado exitosamente";
								conexion.commit();
							} else {
								// Error en inserci�n de bit�cora
								app_resultado.re_exitoso = false;
								app_resultado.re_codigo = -1;
								app_resultado.re_descripcion = bitacora_resultado.re_descripcion;
								conexion.rollback();
							}
						} else {
							// Error en la creaci�n del nuevo par�metro
							app_resultado.re_exitoso = false;
							app_resultado.re_codigo = -2;
							app_resultado.re_descripcion = creacion_resultado.re_descripcion;
							conexion.rollback();
						}
					} else {
						// Error en la modificaci�n
						app_resultado.re_exitoso = false;
						app_resultado.re_codigo = -3;
						app_resultado.re_descripcion = modificacion_resultado.re_descripcion;
						conexion.rollback();
					}
				} else {
					// No se modific� el valor
					app_resultado.re_exitoso = true;
					app_resultado.re_codigo = 1;
					app_resultado.re_descripcion = "El valor del par�metro no sufri� modificaciones";
				}
			} catch (Exception e) {
				// Error no manejado
				app_resultado.re_exitoso = false;
				app_resultado.re_codigo = 0;
				app_resultado.re_descripcion = e.getMessage();
				try {
					conexion.rollback();
				} catch (SQLException e1) {
					// Error no manejado en la conexi�n
				}
			} finally {
				if (conexion != null) {
					try {
						conexion.close();
					} catch (SQLException e) {
						// Error no manejado en la conexi�n
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
		// Declaraci�n de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE modificacion_resultado;
		ArrayList<Parametro_BE> listaParametros;
		Parametro_BE objParametroTemp;

		// Inicializaci�n de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		modificacion_resultado = new Resultado_BE();

		// L�gica de negocio
		try {
			// Aquí se completa toda la L�gica de negocio
			listaParametros = (ArrayList<Parametro_BE>) Parametro_BLL.listar(objParametro);
			objParametroTemp = listaParametros.get(0);

			objParametro.pa_parametro = objParametroTemp.pa_parametro;
			objParametro.pa_estado = 3;
			// Realizamos la modificaci�n
			modificacion_resultado = Parametro_DAL.modificar(objParametro, conexion);

			// Validamos la Operaci�n
			if (modificacion_resultado.re_exitoso) {
				// Guardamos la bit�cora
				bitacora.bi_usuario = sesion.se_usuario;
				bitacora.bi_funcion = Funciones.MODIFICAR_PARAMETROS;
				bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL.obtenerFecha());
				bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());
				bitacora.bi_descripcion = "Modificar parametro de reservas de unidades dentales.";
				bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
				bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

				if (bitacora_resultado.re_exitoso) {
					// Operaci�n exitosa
					app_resultado.re_exitoso = true;
					app_resultado.re_codigo = 1;
					app_resultado.re_filas_afectadas = modificacion_resultado.re_filas_afectadas;
					app_resultado.re_descripcion = "OPERACION EXITOSA";
					conexion.commit();
				} else {
					// Error en inserci�n de bit�cora
					app_resultado.re_exitoso = false;
					app_resultado.re_codigo = -1;
					app_resultado.re_descripcion = bitacora_resultado.re_descripcion;
					conexion.rollback();
				}
			} else {
				// Error en la modificaci�n
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
				// Error no manejado en la conexi�n
			}
		} finally {
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					// Error no manejado en la conexi�n
				}
			}
		}

		return app_resultado;
	}
}

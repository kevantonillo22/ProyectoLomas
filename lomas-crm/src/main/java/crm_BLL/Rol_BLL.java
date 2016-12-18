package crm_BLL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Bitacora_BE;
import crm_BE.Funciones;
import crm_BE.Resultado_BE;
import crm_BE.Rol_BE;
import crm_BE.Rol_funcion_BE;
import crm_BE.Sesion_BE;
import crm_BE.Usuario_BE;
import crm_DAL.Bitacora_DAL;
import crm_DAL.Rol_DAL;
import crm_DAL.Rol_funcion_DAL;
import crm_DAL.Usuario_DAL;

/*********************************************************************
 * @author kev
 * @version 1.0
 * @since 5/05/2014
 * @FechaModificacion 5/05/2014
 * @Descripcion Clase de Lógica de negocio de roles
 ********************************************************************/

public class Rol_BLL {

	/*********************************************************************
	 * @author kev
	 * @since 5/05/2014
	 * @param Rol_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Método para la creación de un rol en la Lógica de negocio
	 ********************************************************************/
	public static Resultado_BE crear(Rol_BE rol, Sesion_BE sesion) {
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

		// Lógica de negocio
		try {

			// Validar dos roles con el mismo nombre
			Rol_BE parametro = new Rol_BE();
			parametro.ro_nombre = rol.ro_nombre;

			if (Rol_DAL.listar(parametro, conexion,"","").isEmpty()) {
				// No hay rol repetido
				creacion_resultado = Rol_DAL.crear(rol, conexion);

				// Validamos el resultado de la Operación
				if (creacion_resultado.re_exitoso) {
					// Guardamos la bitácora
					bitacora.bi_usuario = sesion.se_usuario;
					bitacora.bi_funcion = Funciones.CREACION_DE_ROLES; // AGREGAR EL código DE LA función
					bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL
							.obtenerFecha());
					bitacora.bi_fecha_hora = java.sql.Timestamp
							.valueOf(General_BLL.obtenerFechaHora());
					bitacora.bi_descripcion = "Se creó el rol "
							+ creacion_resultado.re_identificador;
					bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
					bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

					if (bitacora_resultado.re_exitoso) {
						// Operación exitosa
						app_resultado.re_exitoso = true;
						app_resultado.re_codigo = 1;
						app_resultado.re_identificador = creacion_resultado.re_identificador;
						app_resultado.re_descripcion = "El rol fue creado exitosamente";
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
			} else {
				// Error en la creación
				app_resultado.re_exitoso = false;
				app_resultado.re_codigo = -3;
				app_resultado.re_descripcion = "Ya existe un rol con ese nombre";
				// conexion.rollback();
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

	/*********************************************************************
	 * @author rbarrios
	 * @since 6/05/2014
	 * @param Rol_BE
	 * @return List<Rol_BE>
	 * @throws Exception
	 * @Descripcion Lista los roles del sistema
	 ********************************************************************/
	public static List<Rol_BE> listar(Rol_BE rol, String start, String lenght) {
		// Declaración de variables
		Connection conexion;
		List<Rol_BE> lista_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Rol_BE>();

		try {
			// AquÃ­ se validan los parámetros y se realiza la llamada a DAL
			lista_resultado = Rol_DAL.listar(rol, conexion, start, lenght);
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

		return lista_resultado;
	}

	/*********************************************************************
	 * @author rbarrios
	 * @since 6/05/2014
	 * @param Rol_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Modifica un rol del sistema.
	 ********************************************************************/
	public static Resultado_BE modificar(Rol_BE rol, Sesion_BE sesion) {
		// Declaración de variables
		Connection conexion;
		Rol_BE rol_parametro;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		List<Rol_BE> lst_rol;
		Resultado_BE bitacora_resultado;
		Resultado_BE modificacion_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		rol_parametro = new Rol_BE();
		lst_rol = new ArrayList<Rol_BE>();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		modificacion_resultado = new Resultado_BE();

		// Lógica de negocio
		try {
			// AquÃ­ se completa toda la Lógica de negocio
			rol_parametro.ro_nombre = rol.ro_nombre;

			// Validar que el nombre del rol no exista
			lst_rol = Rol_DAL.listar(rol_parametro, conexion,"","");

			if (lst_rol.size() < 2) {
				if ((lst_rol.size() == 0) || (lst_rol.get(0).ro_rol == rol.ro_rol)) {
					// Realizamos la modificación
					modificacion_resultado = Rol_DAL.modificar(rol, conexion);

					// Validamos la Operación
					if (modificacion_resultado.re_exitoso) {
						// Guardamos la bitácora
						bitacora.bi_usuario = sesion.se_usuario;
						bitacora.bi_funcion = Funciones.MODIFICACION_DE_ROLES; // AGREGAR EL código DE LA función
						bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL
								.obtenerFecha());
						bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL
								.obtenerFechaHora());
						bitacora.bi_descripcion = "Se modificó el rol " + rol.ro_rol;
						bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
						bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

						if (bitacora_resultado.re_exitoso) {
							// Operación exitosa
							app_resultado.re_exitoso = true;
							app_resultado.re_codigo = 1;
							app_resultado.re_filas_afectadas = modificacion_resultado.re_filas_afectadas;
							app_resultado.re_descripcion = "Modificación realizada con éxito";
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
				} else {
					// El nombre del rol ya existe y no es él mismo
					app_resultado.re_exitoso = false;
					app_resultado.re_codigo = -3;
					app_resultado.re_descripcion = "El nombre ingresado ya fue utilizado";
				}
			} else {
				// El nombre del rol ya existe
				app_resultado.re_exitoso = false;
				app_resultado.re_codigo = -4;
				app_resultado.re_descripcion = "El nombre del rol ya fue utilizado";
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
	
	/*********************************************************************
	 * @author rbarrios
	 * @since 6/05/2014
	 * @param Rol_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Elimina un rol del sistema.
	 ********************************************************************/
	public static Resultado_BE eliminar(Rol_BE rol,
			Sesion_BE sesion) {
		// Declaración de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE eliminacion_resultado;
		Usuario_BE usuario_parametro;
		Rol_funcion_BE rol_funcion_parametro;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		eliminacion_resultado = new Resultado_BE();
		usuario_parametro = new Usuario_BE();
		rol_funcion_parametro = new Rol_funcion_BE();

		// Lógica de negocio
		try {
			// Validar que no exista un usuario asociado al rol
			usuario_parametro.us_rol = rol.ro_rol;
			if (Usuario_DAL.listar(usuario_parametro, conexion).size()==0) {
				// Validar que no exista una función asociada al Rol
				rol_funcion_parametro.rf_rol = rol.ro_rol;
				if (Rol_funcion_DAL.listar(rol_funcion_parametro, conexion).size()==0) {
					// Realizamos la eliminación
					eliminacion_resultado = Rol_DAL.eliminar(
							rol, conexion);

					// Validamos la Operación
					if (eliminacion_resultado.re_exitoso) {
						// Guardamos la bitácora
						bitacora.bi_usuario = sesion.se_usuario;
						bitacora.bi_funcion = Funciones.MODIFICACION_DE_ROLES; // AGREGAR EL código DE LA función
						bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL
								.obtenerFecha());
						bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL
								.obtenerFechaHora());
						bitacora.bi_descripcion = "Se eliminó el rol " + rol.ro_rol;
						bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
						bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

						if (bitacora_resultado.re_exitoso) {
							// Operación exitosa
							app_resultado.re_exitoso = true;
							app_resultado.re_codigo = 1;
							app_resultado.re_filas_afectadas = eliminacion_resultado.re_filas_afectadas;
							app_resultado.re_descripcion = "Eliminación completada con éxito";
							conexion.commit();
						} else {
							// Error en inserción de bitácora
							app_resultado.re_exitoso = false;
							app_resultado.re_codigo = -1;
							app_resultado.re_descripcion = bitacora_resultado.re_descripcion;
							conexion.rollback();
						}
					} else {
						// Error en la eliminación
						app_resultado.re_exitoso = false;
						app_resultado.re_codigo = -2;
						app_resultado.re_descripcion = eliminacion_resultado.re_descripcion;
						conexion.rollback();
					}
				} else {
					// Error en la eliminación
					app_resultado.re_exitoso = false;
					app_resultado.re_codigo = -3;
					app_resultado.re_descripcion = "No se completó la eliminación. El rol tiene funciones  o usuarios asociados.";
				}
			} else {
				// Error en la eliminación
				app_resultado.re_exitoso = false;
				app_resultado.re_codigo = -4;
				app_resultado.re_descripcion = "No se completó la eliminación. El rol tiene funciones  o usuarios asociados.";
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

		return app_resultado;
	}

	/*********************************************************************
	 * @author kev
	 * @since 9/05/2014
	 * @return Integer
	 * @throws Exception
	 * @Descripcion Capa de la BLL que accede al conteo de una funcion
	 ********************************************************************/
	public static int contar() {
		// Declaración de variables
		Connection conexion;
		int resultado=-1;
		conexion = General_BLL.obtenerConexion();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			try {
				// Lógica de negocio

				// AquÃ­ se validan los parámetros y se realiza la llamada a DAL
				resultado = Rol_DAL.contar(conexion);
				conexion.commit();

			} catch (Exception e) {
				// Error no manejado
				resultado=-1;
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
			resultado = -1;
		}
		return resultado;
	}
}

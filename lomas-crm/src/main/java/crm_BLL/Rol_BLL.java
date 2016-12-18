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
 * @Descripcion Clase de L�gica de negocio de roles
 ********************************************************************/

public class Rol_BLL {

	/*********************************************************************
	 * @author kev
	 * @since 5/05/2014
	 * @param Rol_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion M�todo para la creaci�n de un rol en la L�gica de negocio
	 ********************************************************************/
	public static Resultado_BE crear(Rol_BE rol, Sesion_BE sesion) {
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

		// L�gica de negocio
		try {

			// Validar dos roles con el mismo nombre
			Rol_BE parametro = new Rol_BE();
			parametro.ro_nombre = rol.ro_nombre;

			if (Rol_DAL.listar(parametro, conexion,"","").isEmpty()) {
				// No hay rol repetido
				creacion_resultado = Rol_DAL.crear(rol, conexion);

				// Validamos el resultado de la Operaci�n
				if (creacion_resultado.re_exitoso) {
					// Guardamos la bit�cora
					bitacora.bi_usuario = sesion.se_usuario;
					bitacora.bi_funcion = Funciones.CREACION_DE_ROLES; // AGREGAR EL c�digo DE LA funci�n
					bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL
							.obtenerFecha());
					bitacora.bi_fecha_hora = java.sql.Timestamp
							.valueOf(General_BLL.obtenerFechaHora());
					bitacora.bi_descripcion = "Se cre� el rol "
							+ creacion_resultado.re_identificador;
					bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
					bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

					if (bitacora_resultado.re_exitoso) {
						// Operaci�n exitosa
						app_resultado.re_exitoso = true;
						app_resultado.re_codigo = 1;
						app_resultado.re_identificador = creacion_resultado.re_identificador;
						app_resultado.re_descripcion = "El rol fue creado exitosamente";
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
			} else {
				// Error en la creaci�n
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

	/*********************************************************************
	 * @author rbarrios
	 * @since 6/05/2014
	 * @param Rol_BE
	 * @return List<Rol_BE>
	 * @throws Exception
	 * @Descripcion Lista los roles del sistema
	 ********************************************************************/
	public static List<Rol_BE> listar(Rol_BE rol, String start, String lenght) {
		// Declaraci�n de variables
		Connection conexion;
		List<Rol_BE> lista_resultado;

		// Inicializaci�n de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Rol_BE>();

		try {
			// Aquí se validan los par�metros y se realiza la llamada a DAL
			lista_resultado = Rol_DAL.listar(rol, conexion, start, lenght);
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
		// Declaraci�n de variables
		Connection conexion;
		Rol_BE rol_parametro;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		List<Rol_BE> lst_rol;
		Resultado_BE bitacora_resultado;
		Resultado_BE modificacion_resultado;

		// Inicializaci�n de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		rol_parametro = new Rol_BE();
		lst_rol = new ArrayList<Rol_BE>();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		modificacion_resultado = new Resultado_BE();

		// L�gica de negocio
		try {
			// Aquí se completa toda la L�gica de negocio
			rol_parametro.ro_nombre = rol.ro_nombre;

			// Validar que el nombre del rol no exista
			lst_rol = Rol_DAL.listar(rol_parametro, conexion,"","");

			if (lst_rol.size() < 2) {
				if ((lst_rol.size() == 0) || (lst_rol.get(0).ro_rol == rol.ro_rol)) {
					// Realizamos la modificaci�n
					modificacion_resultado = Rol_DAL.modificar(rol, conexion);

					// Validamos la Operaci�n
					if (modificacion_resultado.re_exitoso) {
						// Guardamos la bit�cora
						bitacora.bi_usuario = sesion.se_usuario;
						bitacora.bi_funcion = Funciones.MODIFICACION_DE_ROLES; // AGREGAR EL c�digo DE LA funci�n
						bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL
								.obtenerFecha());
						bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL
								.obtenerFechaHora());
						bitacora.bi_descripcion = "Se modific� el rol " + rol.ro_rol;
						bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
						bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

						if (bitacora_resultado.re_exitoso) {
							// Operaci�n exitosa
							app_resultado.re_exitoso = true;
							app_resultado.re_codigo = 1;
							app_resultado.re_filas_afectadas = modificacion_resultado.re_filas_afectadas;
							app_resultado.re_descripcion = "Modificaci�n realizada con �xito";
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
				} else {
					// El nombre del rol ya existe y no es �l mismo
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
		// Declaraci�n de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE eliminacion_resultado;
		Usuario_BE usuario_parametro;
		Rol_funcion_BE rol_funcion_parametro;

		// Inicializaci�n de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		eliminacion_resultado = new Resultado_BE();
		usuario_parametro = new Usuario_BE();
		rol_funcion_parametro = new Rol_funcion_BE();

		// L�gica de negocio
		try {
			// Validar que no exista un usuario asociado al rol
			usuario_parametro.us_rol = rol.ro_rol;
			if (Usuario_DAL.listar(usuario_parametro, conexion).size()==0) {
				// Validar que no exista una funci�n asociada al Rol
				rol_funcion_parametro.rf_rol = rol.ro_rol;
				if (Rol_funcion_DAL.listar(rol_funcion_parametro, conexion).size()==0) {
					// Realizamos la eliminaci�n
					eliminacion_resultado = Rol_DAL.eliminar(
							rol, conexion);

					// Validamos la Operaci�n
					if (eliminacion_resultado.re_exitoso) {
						// Guardamos la bit�cora
						bitacora.bi_usuario = sesion.se_usuario;
						bitacora.bi_funcion = Funciones.MODIFICACION_DE_ROLES; // AGREGAR EL c�digo DE LA funci�n
						bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL
								.obtenerFecha());
						bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL
								.obtenerFechaHora());
						bitacora.bi_descripcion = "Se elimin� el rol " + rol.ro_rol;
						bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
						bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

						if (bitacora_resultado.re_exitoso) {
							// Operaci�n exitosa
							app_resultado.re_exitoso = true;
							app_resultado.re_codigo = 1;
							app_resultado.re_filas_afectadas = eliminacion_resultado.re_filas_afectadas;
							app_resultado.re_descripcion = "Eliminaci�n completada con �xito";
							conexion.commit();
						} else {
							// Error en inserci�n de bit�cora
							app_resultado.re_exitoso = false;
							app_resultado.re_codigo = -1;
							app_resultado.re_descripcion = bitacora_resultado.re_descripcion;
							conexion.rollback();
						}
					} else {
						// Error en la eliminaci�n
						app_resultado.re_exitoso = false;
						app_resultado.re_codigo = -2;
						app_resultado.re_descripcion = eliminacion_resultado.re_descripcion;
						conexion.rollback();
					}
				} else {
					// Error en la eliminaci�n
					app_resultado.re_exitoso = false;
					app_resultado.re_codigo = -3;
					app_resultado.re_descripcion = "No se complet� la eliminaci�n. El rol tiene funciones  o usuarios asociados.";
				}
			} else {
				// Error en la eliminaci�n
				app_resultado.re_exitoso = false;
				app_resultado.re_codigo = -4;
				app_resultado.re_descripcion = "No se complet� la eliminaci�n. El rol tiene funciones  o usuarios asociados.";
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
		// Declaraci�n de variables
		Connection conexion;
		int resultado=-1;
		conexion = General_BLL.obtenerConexion();

		// Verifica que la conexi�n no sea nula
		if (conexion != null) {
			try {
				// L�gica de negocio

				// Aquí se validan los par�metros y se realiza la llamada a DAL
				resultado = Rol_DAL.contar(conexion);
				conexion.commit();

			} catch (Exception e) {
				// Error no manejado
				resultado=-1;
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
			resultado = -1;
		}
		return resultado;
	}
}

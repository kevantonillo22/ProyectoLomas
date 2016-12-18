package crm_BLL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Bitacora_BE;
import crm_BE.Funciones;
import crm_BE.Resultado_BE;
import crm_BE.Rol_BE;
import crm_BE.Sesion_BE;
import crm_BE.Usuario_BE;
import crm_DAL.Bitacora_DAL;
import crm_DAL.Usuario_DAL;

/*********************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 4/05/2014
 * @FechaModificacion 4/05/2014
 * @Descripcion Lógica de negocio de los usuarios del sistema.
 ********************************************************************/

public class Usuario_BLL {
	/*********************************************************************
	 * @author rbarrios
	 * @since 4/05/2014
	 * @param Usuario_BE
	 * @return List<Usuario_BE>
	 * @throws Exception
	 * @Descripcion Lista los usuarios registrados en el sistema.
	 ********************************************************************/
	public static List<Usuario_BE> listar(Usuario_BE usuario) {
		// Declaración de variables
		Connection conexion;
		List<Usuario_BE> lista_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Usuario_BE>();
		if (conexion != null) {
			try {
				// AquÃ­ se validan los parámetros y se realiza la llamada a DAL
				lista_resultado = Usuario_DAL.listar(usuario, conexion);
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
			lista_resultado = null;
		}
		return lista_resultado;
	}
	
	/*********************************************************************
	 * @author rbarrios
	 * @since 16/05/2014
	 * @param Usuario_BE
	 * @return List<Usuario_BE>
	 * @throws Exception
	 * @Descripcion Consulta los usuarios por un parámetro de bÃºsqueda.
	 ********************************************************************/
	public static List<Usuario_BE> listar_busqueda(
			Usuario_BE usuario) {
		// Declaración de variables
		Connection conexion;
		List<Usuario_BE> lista_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Usuario_BE>();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			try {
				// AquÃ­ se validan los parámetros y se realiza la llamada a DAL
				lista_resultado = Usuario_DAL.listar_busqueda(usuario,
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
	
	/*********************************************************************
	 * @author rbarrios
	 * @since 13/05/2014
	 * @param Usuario_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Crea un usuario en el sistema.
	 ********************************************************************/
	public static Resultado_BE crear(Usuario_BE usuario,
			Sesion_BE sesion) {
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
				// Validar que el rol exista
				Rol_BE rol_parametro = new Rol_BE();
				rol_parametro.ro_rol = usuario.us_rol;
				List<Rol_BE> lst_rol = Rol_BLL.listar(rol_parametro, null, null);
				
				if (lst_rol.size() > 0) {
					// Validar que no exista otro usuario con el mismo login
					Usuario_BE usuario_parametro = new Usuario_BE();
					usuario_parametro.us_login = usuario.us_login;
					List<Usuario_BE> lst_usuario = Usuario_DAL.listar(usuario_parametro, conexion);
					
					if (lst_usuario.isEmpty()) {
						// Inicilizamos valores por defecto para los usuarios administrativos
						usuario.us_estado = 1; // Desbloqueado
						usuario.us_tipo = 1; // Administrativo
						usuario.us_visible = 1; // Visible
						
						// Realizamos la creación
						creacion_resultado = Usuario_DAL.crear(usuario,
								conexion);

						// Validamos el resultado de la Operación
						if (creacion_resultado.re_exitoso) {
							// Guardamos la bitácora
							bitacora.bi_usuario = sesion.se_usuario;
							bitacora.bi_funcion = Funciones.CREAR_USUARIO; // AGREGAR LA FUNCION
							bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL
									.obtenerFecha());
							bitacora.bi_fecha_hora = java.sql.Timestamp
									.valueOf(General_BLL.obtenerFechaHora());
							bitacora.bi_descripcion = "Se creó el usuario " + usuario.us_login + " código " + creacion_resultado.re_identificador;
							bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
							bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

							if (bitacora_resultado.re_exitoso) {
								// Operación exitosa
								app_resultado.re_exitoso = true;
								app_resultado.re_codigo = 1;
								app_resultado.re_descripcion = "El usuario fue creado exitosamente. La contraseña por defecto es el nombre de usuario.";
								app_resultado.re_identificador = creacion_resultado.re_identificador;
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
						// Ya existe el usuario
						app_resultado.re_exitoso = false;
						app_resultado.re_codigo = -3;
						app_resultado.re_descripcion = "El nombre de usuario ya existe";
					}
				} else {
					// El rol no existe
					app_resultado.re_exitoso = false;
					app_resultado.re_codigo = -4;
					app_resultado.re_descripcion = "El rol asignado no existe";
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
	 * @author rbarrios
	 * @since 13/05/2014
	 * @param Usuario_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Modifica la información de un registro de usuario.
	 ********************************************************************/
	public static Resultado_BE modificar(Usuario_BE usuario,
			Sesion_BE sesion) {
		// Declaración de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE modificacion_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		modificacion_resultado = new Resultado_BE();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			// Lógica de negocio
			try {
				// Validar que el rol exista
				Rol_BE rol_parametro = new Rol_BE();
				rol_parametro.ro_rol = usuario.us_rol;
				List<Rol_BE> lst_rol = Rol_BLL.listar(rol_parametro, null, null);
				
				if (usuario.us_rol == -9999 || lst_rol.size() > 0) {
					// Realizamos la modificación
					modificacion_resultado = Usuario_DAL.modificar(
							usuario, conexion);
					
					// Validamos la Operación
					if (modificacion_resultado.re_exitoso) {
						// Guardamos la bitácora
						bitacora.bi_usuario = sesion.se_usuario;
						bitacora.bi_funcion = Funciones.MODIFICAR_USUARIO; // AGREGAR LA FUNCION
						bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL
								.obtenerFecha());
						bitacora.bi_fecha_hora = java.sql.Timestamp
								.valueOf(General_BLL.obtenerFechaHora());
						bitacora.bi_descripcion = "Se modificó el usuario código " + usuario.us_usuario;
						bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
						bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

						if (bitacora_resultado.re_exitoso) {
							// Operación exitosa
							app_resultado.re_exitoso = true;
							app_resultado.re_codigo = 1;
							app_resultado.re_filas_afectadas = modificacion_resultado.re_filas_afectadas;
							app_resultado.re_descripcion = "El usuario fue modificado exitosamente";
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
					// No existe el rol
					app_resultado.re_exitoso = false;
					app_resultado.re_codigo = -3;
					app_resultado.re_descripcion = "El rol asignado no existe";
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
	 * @author rbarrios
	 * @since 13/05/2014
	 * @param Usuario_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Elimina un usuario del sistema.
	 ********************************************************************/
	public static Resultado_BE eliminar(Usuario_BE usuario,
			Sesion_BE sesion) {
		// Declaración de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE eliminacion_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		eliminacion_resultado = new Resultado_BE();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			// Lógica de negocio
			try {
				// Verificar que el usuario no tenga algÃºn registro en la bitácora del sistema para mantener la integridad
				Bitacora_BE bitacora_parametro = new Bitacora_BE();
				bitacora_parametro.bi_usuario = usuario.us_usuario;
				List<Bitacora_BE> lst_bitacora = Bitacora_DAL.listar(bitacora_parametro, conexion);
				
				if (lst_bitacora.isEmpty()) {
					// Realizamos la eliminación
					eliminacion_resultado = Usuario_DAL.eliminar(
							usuario, conexion);

					// Validamos la Operación
					if (eliminacion_resultado.re_exitoso) {
						// Guardamos la bitácora
						bitacora.bi_usuario = sesion.se_usuario;
						bitacora.bi_funcion = Funciones.ELIMINAR_USUARIO; // AGREGAR LA FUNCION
						bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL
								.obtenerFecha());
						bitacora.bi_fecha_hora = java.sql.Timestamp
								.valueOf(General_BLL.obtenerFechaHora());
						bitacora.bi_descripcion = "Se eliminó el usuario código " + usuario.us_usuario;
						bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
						bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

						if (bitacora_resultado.re_exitoso) {
							// Operación exitosa
							app_resultado.re_exitoso = true;
							app_resultado.re_codigo = 1;
							app_resultado.re_filas_afectadas = eliminacion_resultado.re_filas_afectadas;
							app_resultado.re_descripcion = "El usuario fue eliminado con éxito";
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
						app_resultado.re_descripcion = "El usuario no puede ser eliminado. Tiene información relacionada.";
						conexion.rollback();
					}
				} else {
					// Existen registros en bitácora
					app_resultado.re_exitoso = false;
					app_resultado.re_codigo = -3;
					app_resultado.re_descripcion = "El usuario no puede ser eliminado. Tiene información relacionada.";
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
	 * @author rbarrios
	 * @since 13/05/2014
	 * @param Usuario_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Cambia el estado del usuario (bloqueado, desbloqueado)
	 ********************************************************************/
	public static Resultado_BE cambiar_estado(Usuario_BE usuario,
			Sesion_BE sesion) {
		// Declaración de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE modificacion_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		modificacion_resultado = new Resultado_BE();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			// Lógica de negocio
			try {
				// Consultar estado actual del usuario
				Usuario_BE usuario_parametro = new Usuario_BE();
				usuario_parametro.us_usuario = usuario.us_usuario;
				List<Usuario_BE> lst_usuario = Usuario_DAL.listar(usuario_parametro, conexion);
				
				if (!lst_usuario.isEmpty()) {
					
					if (lst_usuario.get(0).us_estado == 1) {
						usuario.us_estado = 2; // Bloqueado
					} else {
						usuario.us_estado = 1; // Desbloqueado
					}
					
					// Realizamos la modificación
					modificacion_resultado = Usuario_DAL.modificar(
							usuario, conexion);

					// Validamos la Operación
					if (modificacion_resultado.re_exitoso) {
						// Guardamos la bitácora
						bitacora.bi_usuario = sesion.se_usuario;
						bitacora.bi_funcion = Funciones.CAMBIAR_ESTADO_USUARIO; // AGREGAR LA FUNCION
						bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL
								.obtenerFecha());
						bitacora.bi_fecha_hora = java.sql.Timestamp
								.valueOf(General_BLL.obtenerFechaHora());
						bitacora.bi_descripcion = "Se cambió el estado del usuario " + lst_usuario.get(0).us_login + " código " + usuario.us_usuario;
						bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
						bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

						if (bitacora_resultado.re_exitoso) {
							// Operación exitosa
							app_resultado.re_exitoso = true;
							app_resultado.re_codigo = 1;
							app_resultado.re_filas_afectadas = modificacion_resultado.re_filas_afectadas;
							app_resultado.re_descripcion = "El cambio de estado fue procesado con éxito";
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
					// El usuario no existe
					app_resultado.re_exitoso = false;
					app_resultado.re_codigo = -3;
					app_resultado.re_descripcion = "El usuario no existe";
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
	 * @author rbarrios
	 * @since 13/05/2014
	 * @param Usuario_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Restablecer la contrasenia del usuario.
	 ********************************************************************/
	public static Resultado_BE restablecer_contrasenia(Usuario_BE usuario,
			Sesion_BE sesion) {
		// Declaración de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE modificacion_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		modificacion_resultado = new Resultado_BE();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			// Lógica de negocio
			try {
				// Consultar estado actual del usuario
				Usuario_BE usuario_parametro = new Usuario_BE();
				usuario_parametro.us_usuario = usuario.us_usuario;
				List<Usuario_BE> lst_usuario = Usuario_BLL.listar(usuario_parametro);
				
				if (!lst_usuario.isEmpty()) {
					
					usuario.us_contrasenia = lst_usuario.get(0).us_login;
					
					// Realizamos la modificación
					modificacion_resultado = Usuario_DAL.modificar(
							usuario, conexion);

					// Validamos la Operación
					if (modificacion_resultado.re_exitoso) {
						// Guardamos la bitácora
						bitacora.bi_usuario = sesion.se_usuario;
						bitacora.bi_funcion = Funciones.RESTABLECER_CONTRASENIA_USUARIO; // AGREGAR LA FUNCION
						bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL
								.obtenerFecha());
						bitacora.bi_fecha_hora = java.sql.Timestamp
								.valueOf(General_BLL.obtenerFechaHora());
						bitacora.bi_descripcion = "Se restableció la contraseña del usuario " + lst_usuario.get(0).us_login + " código " + usuario.us_usuario;
						bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
						bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

						if (bitacora_resultado.re_exitoso) {
							// Operación exitosa
							app_resultado.re_exitoso = true;
							app_resultado.re_codigo = 1;
							app_resultado.re_filas_afectadas = modificacion_resultado.re_filas_afectadas;
							app_resultado.re_descripcion = "La contraseña fue restablecida exitosamente";
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
					// El usuario no existe
					app_resultado.re_exitoso = false;
					app_resultado.re_codigo = -3;
					app_resultado.re_descripcion = "El usuario no existe";
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
	 * @author rbarrios
	 * @since 1/07/2014
	 * @param Usuario_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Cambiar la contraseña de un administrativo.
	 ********************************************************************/
	public static Resultado_BE cambiar_contrasenia(Usuario_BE usuario, Sesion_BE sesion) {
		// Declaración de variables
		Connection conexion;
		Resultado_BE app_resultado;
		Resultado_BE modificacion_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		app_resultado = new Resultado_BE();
		modificacion_resultado = new Resultado_BE();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			try {
				// Comprobar la contraseña anterior
				Usuario_BE usuario_cambio = new Usuario_BE();
				List<Usuario_BE> lst_usuario_cambio = new ArrayList<Usuario_BE>();
				usuario_cambio.us_usuario = usuario.us_usuario;
				usuario_cambio.us_contrasenia = usuario.us_contrasenia;
				lst_usuario_cambio = Usuario_DAL.listar(usuario_cambio, conexion);
				
				// Comprobamos que la contraseña anterior coincida
				if (lst_usuario_cambio.size() != 0) {
					// Realizamos la modificación
					usuario.us_contrasenia = usuario.us_contrasenia_nueva; // Colocamos la nueva contraseña como la vigente
					modificacion_resultado = Usuario_DAL.modificar(usuario, conexion);

					// Validamos la operación
					if (modificacion_resultado.re_exitoso) {
						// Operación exitosa
						app_resultado.re_exitoso = true;
						app_resultado.re_codigo = 1;
						app_resultado.re_filas_afectadas = modificacion_resultado.re_filas_afectadas;
						app_resultado.re_descripcion = "El cambio de contraseña fue realizado con éxito";
						conexion.commit();
					} else {
						// Error en la modificación
						app_resultado.re_exitoso = false;
						app_resultado.re_codigo = -1;
						app_resultado.re_descripcion = modificacion_resultado.re_descripcion;
						conexion.rollback();
					}
				} else {
					// Error en la modificación
					app_resultado.re_exitoso = false;
					app_resultado.re_codigo = -2;
					app_resultado.re_descripcion = "La contraseña actual no coincide";
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
}

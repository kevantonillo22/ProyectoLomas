package crm_BLL.cuentas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Bitacora_BE;
import crm_BE.Funciones;
import crm_BE.Resultado_BE;
import crm_BE.Sesion_BE;
import crm_BE.cuentas.Busqueda_BE;
import crm_BE.cuentas.Caja_Chica_BE;
import crm_BE.cuentas.Cheque_BE;
import crm_BE.cuentas.Documento_BE;
import crm_BE.cuentas.Efectivo_BE;
import crm_BLL.General_BLL;
import crm_DAL.Bitacora_DAL;
import crm_DAL.cuentas.Caja_DAL;
import crm_DAL.cuentas.Cheque_DAL;

public class Caja_BLL {
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 3/11/2015
	 * @param Caja_Chica_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Crea todos los registros necesarios para crear una caja chica
	 ********************************************************************/
	public static Resultado_BE crear(Caja_Chica_BE caja, List<Documento_BE> documentos, List<Efectivo_BE> efectivos, Sesion_BE sesion) {
		// Declaración de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE creacion_resultado1;
		Resultado_BE creacion_resultado2;
		Resultado_BE creacion_resultado3;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		creacion_resultado1 = new Resultado_BE();
		creacion_resultado2 = new Resultado_BE();
		creacion_resultado3 = new Resultado_BE();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			// Lógica de negocio
			try {
				// Aquí se completa toda la lógica de negocio

				// Realizamos la creación
				creacion_resultado1 = Caja_DAL.crearCajaChica(caja, conexion);
				int id_caja_chica = creacion_resultado1.re_identificador;
				
				for(Efectivo_BE efe: efectivos){
					efe.ef_caja_chica = id_caja_chica;
					creacion_resultado2 = Caja_DAL.crearEfectivo(efe, conexion);
				}
				
				// sino se va a guardar ningun documento se obvia el registro
				if(!documentos.isEmpty()){
					for(Documento_BE doc: documentos){
						doc.do_caja_chica = id_caja_chica;
						creacion_resultado3 = Caja_DAL.crearDocumento(doc, conexion);
					}
				}else{
					creacion_resultado3.re_exitoso = true;
				}
				
				System.out.println(creacion_resultado1.re_exitoso);
				System.out.println(creacion_resultado2.re_exitoso);
				System.out.println(creacion_resultado3.re_exitoso);
				
				// Validamos el resultado de la operación
				if (creacion_resultado1.re_exitoso &&
						creacion_resultado2.re_exitoso &&
						creacion_resultado3.re_exitoso) {
					// Guardamos la bitácora
					bitacora.bi_usuario = sesion.se_usuario;
					bitacora.bi_funcion = Funciones.CREAR_CAJA; // AGREGAR LA FUNCION
					bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL.obtenerFecha());
					bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());
					bitacora.bi_descripcion = "SE AGREGO UN REGISTRO DE CAJA CHICA";
					bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
					bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

					if (bitacora_resultado.re_exitoso) {
						// Operación exitosa
						app_resultado.re_exitoso = true;
						app_resultado.re_codigo = 1;
						app_resultado.re_identificador = creacion_resultado1.re_identificador;
						app_resultado.re_descripcion = "Se agregó el registro exitosamente";
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
					app_resultado.re_descripcion = creacion_resultado1.re_descripcion;
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
	 * @since 05/11/2015
	 * @param Busqueda_BE
	 * @return List<Busqueda_BE>
	 * @throws Exception
	 * @Descripcion Realiza una busqueda de caja chica
	 ********************************************************************/
	public static List<Caja_Chica_BE> buscar(Busqueda_BE busqueda) {
		// Declaración de variables
		Connection conexion;
		List<Caja_Chica_BE> lista_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Caja_Chica_BE>();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			try {
				// Lógica de negocio

				// Aquí se validan los parámetros y se realiza la llamada a DAL
				lista_resultado = Caja_DAL.buscar(busqueda, conexion);
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
	 * @author Kevin Cardona
	 * @since 5/11/2015
	 * @param Caja_Chica_BE
	 * @return List<Caja_Chica_BE>
	 * @throws Exception
	 * @Descripcion Listar caja chica
	 ********************************************************************/
	public static List<Caja_Chica_BE> listarCajaChica(Caja_Chica_BE caja) {
		// Declaración de variables
		Connection conexion;
		List<Caja_Chica_BE> lista_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Caja_Chica_BE>();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			try {
				// Lógica de negocio

				// Aquí se validan los parámetros y se realiza la llamada a DAL
				lista_resultado = Caja_DAL.listarCajaChica(caja, conexion);
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
	 * @author Kevin Cardona
	 * @since 5/11/2015
	 * @param Caja_Chica_BE
	 * @return List<Caja_Chica_BE>
	 * @throws Exception
	 * @Descripcion Listar efectivo
	 ********************************************************************/
	public static List<Efectivo_BE> listarEfectivo(Efectivo_BE efectivo) {
		// Declaración de variables
		Connection conexion;
		List<Efectivo_BE> lista_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Efectivo_BE>();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			try {
				// Lógica de negocio

				// Aquí se validan los parámetros y se realiza la llamada a DAL
				lista_resultado = Caja_DAL.listarEfectivo(efectivo, conexion);
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
	 * @author Kevin Cardona
	 * @since 5/11/2015
	 * @param Documento_BE
	 * @return List<Documento_BE>
	 * @throws Exception
	 * @Descripcion descripcion
	 ********************************************************************/
	public static List<Documento_BE> listarDocumento(Documento_BE documento) {
		// Declaración de variables
		Connection conexion;
		List<Documento_BE> lista_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Documento_BE>();

		// Verifica que la conexión no sea nula
		if (conexion != null) {
			try {
				// Lógica de negocio

				// Aquí se validan los parámetros y se realiza la llamada a DAL
				lista_resultado = Caja_DAL.listarDocumento(documento, conexion);
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
	 * @author Kevin Cardona
	 * @since 8/11/2015
	 * @param Caja_Chica_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Modifica el conjunto de registros de caja chica
	 ********************************************************************/
	public static Resultado_BE modificar(Caja_Chica_BE caja, List<Documento_BE> documentos, List<Efectivo_BE> efectivos, Sesion_BE sesion) {
		// Declaración de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE modificacion_resultado;
		Resultado_BE modificacion_resultado2;
		Resultado_BE modificacion_resultado3;
		Resultado_BE modificacion_resultado4;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		modificacion_resultado = new Resultado_BE();
		modificacion_resultado2 = new Resultado_BE();
		modificacion_resultado3 = new Resultado_BE();
		modificacion_resultado4 = new Resultado_BE();
		
		// Verifica que la conexión no sea nula
		if (conexion != null) {
			// Lógica de negocio
			try {
				// Aquí se completa toda la lógica de negocio

				// Realizamos la modificación
				boolean mod_efectivo = true;
				boolean mod_documento= true;
				//update a caja chica
				System.out.println("aqui empieza");
				modificacion_resultado = Caja_DAL.modificarCajaChica(caja, conexion);
				System.out.println("caja chica");
				//update a los registros de efectivo
				for(Efectivo_BE ef: efectivos){
					modificacion_resultado2 = Caja_DAL.modificarEfectivo(ef, conexion);
					if(!modificacion_resultado2.re_exitoso){mod_efectivo = false;}
				}
				System.out.println("efectivos");
				//eliminamos los registros de documento guardados
				Documento_BE elim = new Documento_BE();
				elim.do_caja_chica = caja.ca_caja_chica;
				modificacion_resultado3 = Caja_DAL.eliminarDocumento(elim, conexion);
				System.out.println("elimino documentos");
				//guardamos los nuevos registros de documento
				// sino se va a guardar ningun documento se obvia el registro
				if(!documentos.isEmpty()){
					for(Documento_BE doc: documentos){
						modificacion_resultado4 = Caja_DAL.crearDocumento(doc, conexion);
						if(!modificacion_resultado4.re_exitoso){mod_documento = false;}
					}
				}
				System.out.println("agrgo documentos");
				
				
				// Validamos la operación
				if (modificacion_resultado.re_exitoso && 
						mod_efectivo && mod_documento && modificacion_resultado3.re_exitoso) {
					// Guardamos la bitácora
					bitacora.bi_usuario = sesion.se_usuario;
					bitacora.bi_funcion = Funciones.MODIFICAR_CAJA; // AGREGAR LA FUNCION
					bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL.obtenerFecha());
					bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());
					bitacora.bi_descripcion = "SE MODIFICÓ UNA CAJA CHICA";
					bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
					bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

					if (bitacora_resultado.re_exitoso) {
						// Operación exitosa
						app_resultado.re_exitoso = true;
						app_resultado.re_codigo = 1;
						app_resultado.re_filas_afectadas = modificacion_resultado.re_filas_afectadas;
						app_resultado.re_descripcion = "Se modificaron los datos correctamente";
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
					app_resultado.re_descripcion = "Hubo un problema en la modificación"/*modificacion_resultado.re_descripcion*/;
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
}

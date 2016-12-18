package crm_BLL.cuentas;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

import crm_BE.Bitacora_BE;
import crm_BE.Funciones;
import crm_BE.Resultado_BE;
import crm_BE.Sesion_BE;
import crm_BE.cuentas.Busqueda_BE;
import crm_BE.cuentas.Cheque_BE;
import crm_BLL.General_BLL;
import crm_DAL.Bitacora_DAL;
import crm_DAL.cuentas.Cheque_DAL;

public class Cheque_BLL {
	/*********************************************************************
	 * @author Kevin
	 * @since 20/10/2015
	 * @param Cheque_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Crea un registro de cheque
	 ********************************************************************/
	public static Resultado_BE crear(Cheque_BE cheque, FileItem fi, File f,Sesion_BE sesion) {
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

				//verificamos si ya existe un registro con el valor de numero de cheque
				Cheque_BE ch = new Cheque_BE();
				ch.ch_numero = cheque.ch_numero;
				List<Cheque_BE> res = Cheque_DAL.listar(ch, conexion);
				if(res.isEmpty()){
					// Realizamos la creaci�n
					fi.write( f ) ;
					creacion_resultado = Cheque_DAL.crear(cheque, conexion);
				}else{
					creacion_resultado.re_exitoso = false;
					creacion_resultado.re_descripcion = "Ya existe un cheque con el n�mero ingresado";
				}
				

				// Validamos el resultado de la operaci�n
				if (creacion_resultado.re_exitoso) {
					// Guardamos la bit�cora
					bitacora.bi_usuario = sesion.se_usuario;
					bitacora.bi_funcion = Funciones.CREAR_CHEQUES; // AGREGAR LA FUNCION
					bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL.obtenerFecha());
					bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());
					bitacora.bi_descripcion = "SE CREO REGISTRO DE CHEQUE";
					bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
					bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

					if (bitacora_resultado.re_exitoso) {
						// Operaci�n exitosa
						app_resultado.re_exitoso = true;
						app_resultado.re_codigo = 1;
						app_resultado.re_identificador = creacion_resultado.re_identificador;
						app_resultado.re_descripcion = "Se registro el cheque correctamente";
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
				e.printStackTrace();
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
	 * @since 21/10/2015
	 * @param Cheque_BE
	 * @return List<Cheque_BE>
	 * @throws Exception
	 * @Descripcion Lista los datos de cheque
	 ********************************************************************/
	public static List<Cheque_BE> listar(Cheque_BE cheque) {
		// Declaraci�n de variables
		Connection conexion;
		List<Cheque_BE> lista_resultado;

		// Inicializaci�n de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Cheque_BE>();

		// Verifica que la conexi�n no sea nula
		if (conexion != null) {
			try {
				// L�gica de negocio

				// Aqu� se validan los par�metros y se realiza la llamada a DAL
				lista_resultado = Cheque_DAL.listar(cheque, conexion);
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
	
	
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 21/10/2015
	 * @param Busqueda_BE
	 * @return List<Busqueda_BE>
	 * @throws Exception
	 * @Descripcion Realiza una busqueda de cheques
	 ********************************************************************/
	public static List<Cheque_BE> buscar(Busqueda_BE busqueda) {
		// Declaraci�n de variables
		Connection conexion;
		List<Cheque_BE> lista_resultado;

		// Inicializaci�n de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Cheque_BE>();

		// Verifica que la conexi�n no sea nula
		if (conexion != null) {
			try {
				// L�gica de negocio

				// Aqu� se validan los par�metros y se realiza la llamada a DAL
				lista_resultado = Cheque_DAL.buscar(busqueda, conexion);
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
	
	
	/*********************************************************************
	 * @author Kevin Cardona
	 * @since 22/10/2015
	 * @param Cheque_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion Modifica un cheque
	 ********************************************************************/
	public static Resultado_BE modificar(Cheque_BE cheque, Sesion_BE sesion) {
		// Declaraci�n de variables
		Connection conexion;
		Bitacora_BE bitacora;
		Resultado_BE app_resultado;
		Resultado_BE bitacora_resultado;
		Resultado_BE modificacion_resultado;

		// Inicializaci�n de variables
		conexion = General_BLL.obtenerConexion();
		bitacora = new Bitacora_BE();
		app_resultado = new Resultado_BE();
		bitacora_resultado = new Resultado_BE();
		modificacion_resultado = new Resultado_BE();

		// Verifica que la conexi�n no sea nula
		if (conexion != null) {
			// L�gica de negocio
			try {
				// Aqu� se completa toda la l�gica de negocio

				// Realizamos la modificaci�n
				modificacion_resultado = Cheque_DAL.modificar(cheque, conexion);

				// Validamos la operaci�n
				if (modificacion_resultado.re_exitoso) {
					// Guardamos la bit�cora
					bitacora.bi_usuario = sesion.se_usuario;
					bitacora.bi_funcion = Funciones.MODIFICAR_CHEQUES; // AGREGAR LA FUNCION
					bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL.obtenerFecha());
					bitacora.bi_fecha_hora = java.sql.Timestamp.valueOf(General_BLL.obtenerFechaHora());
					bitacora.bi_descripcion = "SE MODIFIC� UN CHEQUE";
					bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
					bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);

					if (bitacora_resultado.re_exitoso) {
						// Operaci�n exitosa
						app_resultado.re_exitoso = true;
						app_resultado.re_codigo = 1;
						app_resultado.re_filas_afectadas = modificacion_resultado.re_filas_afectadas;
						app_resultado.re_descripcion = "Se modificaron los datos correctamente";
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

}

package crm_BLL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Bitacora_BE;
import crm_BE.Funcion_BE;
import crm_BE.Funciones;
import crm_BE.Resultado_BE;
import crm_BE.Rol_BE;
import crm_BE.Rol_funcion_BE;
import crm_BE.Sesion_BE;
import crm_DAL.Bitacora_DAL;
import crm_DAL.Funcion_DAL;
import crm_DAL.Rol_funcion_DAL;

/***************************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 2/05/2014
 * @FechaModificacion 2/05/2014
 * @Descripcion Lógica de negocio para la asignación de funciones a un rol.
 **************************************************************************/

public class Rol_funcion_BLL {

	/*********************************************************************
	 * @author rbarrios
	 * @since 2/05/2014
	 * @param Rol_funcion_BE
	 * @return List<Rol_funcion_BE>
	 * @throws Exception
	 * @Descripcion Lista las asociaciones de rol y función del sistema.
	 ********************************************************************/
	public static List<Rol_funcion_BE> listar(
			Rol_funcion_BE rol_funcion) {
		// Declaración de variables
		Connection conexion;
		List<Rol_funcion_BE> lista_resultado;

		// Inicialización de variables
		conexion = General_BLL.obtenerConexion();
		lista_resultado = new ArrayList<Rol_funcion_BE>();
		if (conexion!=null){
			try {
				// AquÃ­ se validan los parámetros y se realiza la llamada a DAL
				lista_resultado = Rol_funcion_DAL.listar(rol_funcion,
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
		}else{
			
			//No se pudo hacer
			
		}
		return lista_resultado;
	}
	
	
	/*********************************************************************
	 * @author kev
	 * @since 13/05/2014
	 * @param Funcion_BE
	 * @return List<Funcion_BE>
	 * @throws Exception
	 * @Descripcion Se obtienen las funciones asignadas a un rol
	 ********************************************************************/
	public static List<Funcion_BE> listarFunciones(
			Rol_BE rol, boolean asignadas) {
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
		
				lista_resultado = Rol_funcion_DAL.listarFunciones(rol,asignadas,conexion);
				conexion.commit();

			} catch (Exception e) {
				// Error no manejado
				e.printStackTrace();
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
	
	
	private static boolean buscar(Funcion_BE funcion, List<Funcion_BE> lista) {
		for (Funcion_BE f : lista) {
			if (f.fu_funcion == funcion.fu_funcion)
				return true;
		}
		return false;
	}
	
	private static boolean tieneHermanos(Funcion_BE funcion, List<Funcion_BE> lista){
		for (Funcion_BE f : lista) {
			if (f.fu_padre == funcion.fu_padre)
				return true;
		}
		return false;
		
	};
	
	/*********************************************************************
	 * @author kev
	 * @since 14/05/2014
	 * @param Rol_funcion_BE
	 * @param Sesion_BE
	 * @return Resultado_BE
	 * @throws Exception
	 * @Descripcion descripcion
	 ********************************************************************/
	public static Resultado_BE guardar(Rol_BE rol, List <Funcion_BE> listaFuncionesActual,Sesion_BE sesion) 
	{
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
				// AquÃ­ se completa toda la Lógica de negocio

				// Realizamos la creación de todos los permisos
				for(Funcion_BE funcion: listaFuncionesActual){
					Rol_funcion_BE rol_funcion = new Rol_funcion_BE();
					rol_funcion.rf_rol=rol.ro_rol;
					rol_funcion.rf_funcion=funcion.fu_funcion;
					creacion_resultado = Rol_funcion_DAL.crear(rol_funcion,conexion);
					Funcion_BE temp= new Funcion_BE();
					temp.fu_funcion=funcion.fu_funcion;
				
					int funcionPadre=Funcion_DAL.listar(temp, conexion).get(0).fu_padre;
					rol_funcion.rf_funcion=funcionPadre;
					funcion.fu_padre=funcionPadre;
					creacion_resultado = Rol_funcion_DAL.crear(rol_funcion,conexion);
					
					//Agregar al padre
					
					
					bitacora.bi_descripcion = "Se creó un registro entre la funcion:"+rol_funcion.rf_funcion+" y el rol:"+rol_funcion.rf_rol+ "con el id "+rol_funcion.rf_rol_funcion;
				}
				
				//Realizar la eliminacion de permisos
				List<Funcion_BE> listaFuncionesAnterior=Rol_funcion_DAL.listarFunciones(rol, true, conexion);
				for(Funcion_BE funcion: listaFuncionesAnterior){
					
					if(!buscar(funcion,listaFuncionesActual))
					{
						Rol_funcion_BE temp= new Rol_funcion_BE();
						temp.rf_funcion=funcion.fu_funcion;
						temp.rf_rol=rol.ro_rol;
						Rol_funcion_DAL.eliminar(temp, conexion);
						//Eliminar funcion padre
						if(!tieneHermanos(funcion,listaFuncionesActual)){
							temp.rf_funcion=funcion.fu_padre;
							Rol_funcion_DAL.eliminar(temp, conexion);
						}
						
					}
					
				}
				
				
				
			creacion_resultado.re_exitoso=true;
				
				
				
				// Validamos el resultado de la Operación
				if (creacion_resultado.re_exitoso) {
					// Guardamos la bitácora

					bitacora.bi_usuario = sesion.se_usuario;
					bitacora.bi_funcion = Funciones.ASIGNAR_FUNCIONES_A_UN_ROL; // AGREGAR LA FUNCION
					bitacora.bi_fecha = java.sql.Date.valueOf(General_BLL
							.obtenerFecha());
					bitacora.bi_fecha_hora = java.sql.Timestamp
							.valueOf(General_BLL.obtenerFechaHora());
					
					bitacora.bi_tipo = 1; // 1:Administrativo; 2:Operativo
					bitacora.bi_descripcion="Se actualizo un rol"+rol.ro_rol;
					bitacora_resultado = Bitacora_DAL.crear(bitacora, conexion);
					

					if (bitacora_resultado.re_exitoso) {
						// Operación exitosa
						app_resultado.re_exitoso = true;
						app_resultado.re_codigo = 1;
						//app_resultado.re_identificador = creacion_resultado.re_identificador;
						app_resultado.re_descripcion = "Cambios guardados";
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
				
				}
			} finally {
				if (conexion != null) {
					try {
						conexion.close();
					} catch (SQLException e) {
					
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

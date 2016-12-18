package crm_DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import crm_BE.Resultado_BE;
import crm_BE.Usuario_BE;

/*********************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 4/05/2014
 * @FechaModificacion 4/05/2014
 * @Descripcion Acceso a datos de los usuarios del sistema.
 ********************************************************************/
public class Usuario_DAL {
    /*********************************************************************
     * @author rbarrios
     * @since 4/05/2014
     * @param Usuario_BE
     * @param Connection
     * @return List<Usuario_BE>
     * @throws Exception
     * @Descripcion Lista los usuarios del sistema.
     ********************************************************************/
    public static List<Usuario_BE> listar(Usuario_BE usuario, Connection conexion) throws SQLException {
	// Declaración de variables
	List<Usuario_BE> lista_resultado;
	Usuario_BE temp;
	PreparedStatement pstmt;
	ResultSet rs;

	// Inicialización de variables
	lista_resultado = new ArrayList<Usuario_BE>();
	pstmt = null;
	rs = null;

	try {
	    // Declaración de la función
	    pstmt = conexion.prepareStatement("select * from fn_usuario_listar(?,?,?,?,?,?,?,?,?)");

	    // Definición de los parámetros de la función
	    if (usuario.us_usuario != -9999) {
		pstmt.setInt(1, usuario.us_usuario);
	    } else {
		pstmt.setNull(1, Types.NULL);
	    }

	    if (usuario.us_rol != -9999) {
		pstmt.setInt(2, usuario.us_rol);
	    } else {
		pstmt.setNull(2, Types.NULL);
	    }

	    if (usuario.us_login != null) {
		pstmt.setString(3, usuario.us_login);
	    } else {
		pstmt.setNull(3, Types.NULL);
	    }

	    if (usuario.us_contrasenia != null) {
		pstmt.setString(4, usuario.us_contrasenia);
	    } else {
		pstmt.setNull(4, Types.NULL);
	    }

	    if (usuario.us_email != null) {
		pstmt.setString(5, usuario.us_email);
	    } else {
		pstmt.setNull(5, Types.NULL);
	    }

	    if (usuario.us_descripcion != null) {
		pstmt.setString(6, usuario.us_descripcion);
	    } else {
		pstmt.setNull(6, Types.NULL);
	    }

	    if (usuario.us_estado != -9999) {
		pstmt.setInt(7, usuario.us_estado);
	    } else {
		pstmt.setNull(7, Types.NULL);
	    }

	    if (usuario.us_tipo != -9999) {
		pstmt.setInt(8, usuario.us_tipo);
	    } else {
		pstmt.setNull(8, Types.NULL);
	    }

	    if (usuario.us_visible != -9999) {
		pstmt.setShort(9, usuario.us_visible);
	    } else {
		pstmt.setNull(9, Types.NULL);
	    }

	    // Ejecuta la función
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		// Inicializar la entidad temporal
		temp = new Usuario_BE();

		// Llenar entidad temporal con cada columna
		temp.us_tipo = rs.getShort("us_tipo");
		temp.us_estado = rs.getShort("us_estado");
		temp.us_contrasenia = rs.getString("us_contrasenia");
		temp.us_email = rs.getString("us_email");
		temp.us_descripcion = rs.getString("us_descripcion");
		temp.us_login = rs.getString("us_login");
		temp.us_nombres = rs.getString("us_nombres");
		temp.us_apellidos = rs.getString("us_apellidos");
		temp.us_rol = rs.getInt("us_rol");
		temp.us_usuario = rs.getInt("us_usuario");
		temp.us_rol_nombre = rs.getString("us_rol_nombre");
		temp.us_rol_visible = rs.getShort("us_rol_visible");
		temp.us_visible = rs.getShort("us_visible");
		temp.us_foto = rs.getString("us_foto");

		// Agregar a la lista
		lista_resultado.add(temp);
	    }
	} catch (Exception e) {
	    // Error no manejado
	    lista_resultado = null;
	    e.printStackTrace();
	} finally {
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (rs != null) {
		rs.close();
	    }
	}

	return lista_resultado;
    }

    /*********************************************************************
     * @author rbarrios
     * @since 13/05/2014
     * @param Usuario_BE
     * @param Connection
     * @return Resultado_BE
     * @throws SQLException
     * @throws Exception
     * @Descripcion Crea un nuevo usuario en el sistema.
     ********************************************************************/
    public static Resultado_BE crear(Usuario_BE usuario, Connection conexion) throws SQLException {
	// Declaración de variables
	Resultado_BE resultado;
	PreparedStatement pstmt;
	ResultSet rs;

	// Inicialización de variables
	resultado = new Resultado_BE();
	pstmt = null;
	rs = null;

	try {
	    // Declaración de la función
	    pstmt = conexion.prepareStatement("SELECT * FROM fn_usuario_crear(?,?,?,?,?,?,?,?,?,?);");

	    // Definición de los parámetros de la función
	    pstmt.setInt(1, usuario.us_rol);
	    pstmt.setString(2, usuario.us_login);

	    if (usuario.us_nombres != null) {
		pstmt.setString(3, usuario.us_nombres);
	    } else {
		pstmt.setNull(3, Types.NULL);
	    }

	    if (usuario.us_apellidos != null) {
		pstmt.setString(4, usuario.us_apellidos);
	    } else {
		pstmt.setNull(4, Types.NULL);
	    }

	    if (usuario.us_contrasenia != null) {
		pstmt.setString(5, usuario.us_contrasenia);
	    } else {
		pstmt.setNull(5, Types.NULL);
	    }

	    if (usuario.us_email != null) {
		pstmt.setString(6, usuario.us_email);
	    } else {
		pstmt.setNull(6, Types.NULL);
	    }

	    if (usuario.us_descripcion != null) {
		pstmt.setString(7, usuario.us_descripcion);
	    } else {
		pstmt.setNull(7, Types.NULL);
	    }

	    pstmt.setShort(8, usuario.us_estado);
	    pstmt.setShort(9, usuario.us_tipo);
	    pstmt.setShort(10, usuario.us_visible);

	    // Ejecuta la función
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		// Creación exitosa
		resultado.re_exitoso = true;
		resultado.re_identificador = rs.getInt("us_usuario");
	    }
	} catch (SQLException e) {
	    // Error no manejado
	    resultado.re_exitoso = false;
	    resultado.re_descripcion = e.getMessage();
	} catch (Exception e) {
	    // Error no manejado
	    resultado.re_exitoso = false;
	    resultado.re_descripcion = e.getMessage();
	} finally {
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (rs != null) {
		rs.close();
	    }
	}

	return resultado;
    }

    /*********************************************************************
     * @author rbarrios
     * @since 13/05/2014
     * @param Usuario_BE
     * @param Connection
     * @return Resultado_BE
     * @throws SQLException
     * @throws Exception
     * @Descripcion Modifica la información de un usuario.
     ********************************************************************/
    public static Resultado_BE modificar(Usuario_BE usuario, Connection conexion) throws SQLException {
	// Declaración de variables
	Resultado_BE resultado;
	PreparedStatement pstmt;
	ResultSet rs;

	// Inicialización de variables
	resultado = new Resultado_BE();
	pstmt = null;
	rs = null;

	try {
	    // Declaración de la función
	    pstmt = conexion.prepareStatement("SELECT * FROM fn_usuario_modificar(?,?,?,?,?,?,?,?,?,?,?);");

	    // Definición de los parámetros de la función
	    if (usuario.us_usuario != -9999) {
		pstmt.setInt(1, usuario.us_usuario);
	    } else {
		pstmt.setNull(1, Types.INTEGER);
	    }

	    if (usuario.us_rol != -9999) {
		pstmt.setInt(2, usuario.us_rol);
	    } else {
		pstmt.setNull(2, Types.INTEGER);
	    }

	    if (usuario.us_login != null) {
		pstmt.setString(3, usuario.us_login);
	    } else {
		pstmt.setNull(3, Types.VARCHAR);
	    }

	    if (usuario.us_nombres != null) {
		pstmt.setString(4, usuario.us_nombres);
	    } else {
		pstmt.setNull(4, Types.VARCHAR);
	    }

	    if (usuario.us_apellidos != null) {
		pstmt.setString(5, usuario.us_apellidos);
	    } else {
		pstmt.setNull(5, Types.VARCHAR);
	    }

	    if (usuario.us_contrasenia != null) {
		pstmt.setString(6, usuario.us_contrasenia);
	    } else {
		pstmt.setNull(6, Types.VARCHAR);
	    }

	    if (usuario.us_email != null) {
		pstmt.setString(7, usuario.us_email);
	    } else {
		pstmt.setNull(7, Types.VARCHAR);
	    }

	    if (usuario.us_descripcion != null) {
		pstmt.setString(8, usuario.us_descripcion);
	    } else {
		pstmt.setNull(8, Types.VARCHAR);
	    }

	    if (usuario.us_estado != -9999) {
		pstmt.setShort(9, usuario.us_estado);
	    } else {
		pstmt.setNull(9, Types.SMALLINT);
	    }

	    if (usuario.us_tipo != -9999) {
		pstmt.setShort(10, usuario.us_tipo);
	    } else {
		pstmt.setNull(10, Types.SMALLINT);
	    }

	    if (usuario.us_foto != null) {
		pstmt.setString(11, usuario.us_foto);
	    } else {
		pstmt.setNull(11, Types.VARCHAR);
	    }

	    // Ejecuta la función
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		// Modificación exitosa
		resultado.re_exitoso = true;
		resultado.re_filas_afectadas = rs.getInt("resultado");
	    }
	} catch (SQLException e) {
	    // Error no manejado
	    resultado.re_exitoso = false;
	    resultado.re_descripcion = e.getMessage();
	} catch (Exception e) {
	    // Error no manejado
	    resultado.re_exitoso = false;
	    resultado.re_descripcion = e.getMessage();
	} finally {
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (rs != null) {
		rs.close();
	    }
	}

	return resultado;
    }

    /*********************************************************************
     * @author rbarrios
     * @since 13/05/2014
     * @param Usuario_BE
     * @param Connection
     * @return Resultado_BE
     * @throws SQLException
     * @throws Exception
     * @Descripcion Elimina un usuario del sistema.
     ********************************************************************/
    public static Resultado_BE eliminar(Usuario_BE usuario, Connection conexion) throws SQLException {
	// Declaración de variables
	Resultado_BE resultado;
	PreparedStatement pstmt;
	ResultSet rs;

	// Inicialización de variables
	resultado = new Resultado_BE();
	pstmt = null;
	rs = null;

	try {
	    // Declaración de la función
	    pstmt = conexion.prepareStatement("SELECT * FROM fn_usuario_eliminar(?);");

	    // Definición de los parámetros de la función
	    pstmt.setInt(1, usuario.us_usuario);

	    // Ejecuta la función
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		// Eliminación exitosa
		resultado.re_exitoso = true;
		resultado.re_filas_afectadas = rs.getInt("resultado");
	    }
	} catch (SQLException e) {
	    // Error no manejado
	    resultado.re_exitoso = false;
	    resultado.re_descripcion = e.getMessage();
	} catch (Exception e) {
	    // Error no manejado
	    resultado.re_exitoso = false;
	    resultado.re_descripcion = e.getMessage();
	} finally {
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (rs != null) {
		rs.close();
	    }
	}

	return resultado;
    }

    /*********************************************************************
     * @author rbarrios
     * @since 16/05/2014
     * @param Usuario_BE
     * @param Connection
     * @return List<Usuario_BE>
     * @throws Exception
     * @Descripcion Lista los usuarios del sistema por un parámetro.
     ********************************************************************/
    public static List<Usuario_BE> listar_busqueda(Usuario_BE usuario, Connection conexion) throws SQLException {
	// Declaración de variables
	List<Usuario_BE> lista_resultado;
	Usuario_BE temp;
	PreparedStatement pstmt;
	ResultSet rs;

	// Inicialización de variables
	lista_resultado = new ArrayList<Usuario_BE>();
	pstmt = null;
	rs = null;

	try {
	    // Declaración de la función
	    pstmt = conexion.prepareStatement("select * from fn_usuario_buscar(?,?)");

	    // Definición de los parámetros de la función
	    if (usuario.us_filtro_busqueda != null) {
		pstmt.setString(1, usuario.us_filtro_busqueda);
	    } else {
		pstmt.setNull(1, Types.NULL);
	    }
	    
	    pstmt.setShort(2, usuario.us_tipo);
	    
	    
	    // Ejecuta la función
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		// Inicializar la entidad temporal
		temp = new Usuario_BE();

		// Llenar entidad temporal con cada columna
		temp.us_tipo = rs.getShort("us_tipo");
		temp.us_estado = rs.getShort("us_estado");
		temp.us_estado_nombre = rs.getString("us_estado_nombre");
		temp.us_contrasenia = rs.getString("us_contrasenia");
		temp.us_email = rs.getString("us_email");
		temp.us_descripcion = rs.getString("us_descripcion");
		temp.us_login = rs.getString("us_login");
		temp.us_nombres = rs.getString("us_nombres");
		temp.us_apellidos = rs.getString("us_apellidos");
		temp.us_rol = rs.getInt("us_rol");
		temp.us_usuario = rs.getInt("us_usuario");
		temp.us_rol_nombre = rs.getString("us_rol_nombre");
		temp.us_rol_visible = rs.getShort("us_rol_visible");
		temp.us_visible = rs.getShort("us_visible");
		temp.us_foto = rs.getString("us_foto");

		// Agregar a la lista
		lista_resultado.add(temp);
	    }
	} catch (Exception e) {
	    // Error no manejado
	    lista_resultado = null;
	    e.printStackTrace();
	} finally {
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (rs != null) {
		rs.close();
	    }
	}

	return lista_resultado;
    }
}

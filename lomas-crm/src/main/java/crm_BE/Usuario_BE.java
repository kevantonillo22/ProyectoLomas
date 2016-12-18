package crm_BE;

/*********************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 4/05/2014
 * @FechaModificacion 4/05/2014
 * @Descripcion Entidad que guarda los usuarios del sistema.
 ********************************************************************/
public class Usuario_BE {
    public int us_usuario;
    public int us_rol;
    public String us_login;
    public String us_nombres;
    public String us_apellidos;
    public String us_contrasenia;
    public String us_email;
    public String us_descripcion;
    public short us_estado;
    public short us_visible;
    public short us_tipo;
    public short us_rol_visible;
    public String us_rol_nombre;
    public String us_estado_nombre;
    public String us_foto;
    public String us_filtro_busqueda;
    public String us_grado;
    public String us_contrasenia_nueva;

    public Usuario_BE() {
	us_usuario = -9999;
	us_rol = -9999;
	us_estado = -9999;
	us_tipo = -9999;
	us_rol_visible = -9999;
	us_visible = -9999;
	us_grado = null;
    }
}

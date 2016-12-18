package crm_BE;

/*********************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 1/05/2014
 * @FechaModificacion 1/05/2014
 * @Descripcion Guarda los atributos de sesión.
 ********************************************************************/

public class Sesion_BE {
	public int se_usuario;
	public int se_rol;
	public String se_login;
	public String se_nombre;
	public String se_nombres;
	public String se_apellidos;
	public String se_ruta_foto;
	public String se_menu;
	public int se_valida_huella;
	
	public Sesion_BE() {
		this.se_usuario = -9999;
		this.se_rol = -9999;
		this.se_valida_huella = -9999;
	}
}

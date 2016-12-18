package crm_BE;

/*********************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 1/05/2014
 * @FechaModificacion 1/05/2014
 * @Descripcion Almacena la asociación de un rol y una función.
 ********************************************************************/
public class Rol_funcion_BE {
	public int rf_rol_funcion;
	public int rf_rol;
	public int rf_funcion;
	public int rf_padre;
	public String rf_nombre;
	public String rf_descripcion;
	public String rf_link;
	public String rf_icono;
	public short rf_visible;
	public short rf_tipo;
	
	public Rol_funcion_BE() {
		this.rf_rol_funcion = -9999;
		this.rf_rol = -9999;
		this.rf_funcion = -9999;
		this.rf_padre = -9999;
		this.rf_visible = -9999;
		this.rf_tipo = -9999;
	}
}
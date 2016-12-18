package crm_BE;

/*********************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 4/05/2014
 * @FechaModificacion 4/05/2014
 * @Descripcion Mapeo de la entidad de roles del sistema.
 ********************************************************************/
public class Rol_BE {
	public static final int C_ADMINISTRADOR = -1;
	public static final int C_ESTUDIANTE = -2;
	public static final int C_CATEDRATICO = -3;

	public int ro_rol;
	public String ro_nombre;
	public String ro_descripcion;
	public short ro_visible;

	public Rol_BE() {
		ro_rol = -9999;
		ro_visible = -9999;
	}
}

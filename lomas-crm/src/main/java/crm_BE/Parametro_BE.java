package crm_BE;

import java.sql.Timestamp;

/*********************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 21/05/2014
 * @FechaModificacion 21/05/2014
 * @Descripcion Mapeo de la entidad de parámetros.
 ********************************************************************/

public class Parametro_BE {
	public int pa_parametro;
	public int pa_codigo_parametro;
	public short pa_estado;
	public String pa_estado_nombre;
	public String pa_nombre;
	public String pa_descripcion;
	public String pa_valor;
	public Timestamp pa_fecha_inicio;
	public Timestamp pa_fecha_fin;
	
	public Parametro_BE() {
		this.pa_parametro = -9999;
		this.pa_codigo_parametro = -9999;
		this.pa_estado = -9999;
	}
}

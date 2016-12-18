package crm_BE.cuentas;

import java.sql.Timestamp;

/*********************************************************************
 * @author kcardona
 * @version 1.0
 * @since 03/11/2015
 * @FechaModificacion 03/11/2015
 * @Descripcion Entidad para el manejo de caja chica.
 ********************************************************************/

public class Caja_Chica_BE {
		
	public int ca_caja_chica;
	public float ca_total_billete;
	public float ca_total_moneda;
	public float ca_total_efectivo;
	public float ca_total_documentos;
	public float ca_sumatoria;
	public float ca_fondo;
	public float ca_variacion;
	public Timestamp ca_fecha;
	public Timestamp ca_fecha_hora_modificacion;
	public Timestamp ca_fecha_hora_creacion;
	
	public Caja_Chica_BE() {
		this.ca_caja_chica 					= -9999;
		this.ca_total_billete 				= -9999;
		this.ca_total_moneda 				= -9999;
		this.ca_total_efectivo 				= -9999;
		this.ca_total_documentos 			= -9999;
		this.ca_sumatoria 					= -9999;
		this.ca_fondo 						= -9999;
		this.ca_variacion 					= -9999;
		this.ca_fecha						= null;
		this.ca_fecha_hora_modificacion 	= null;
		this.ca_fecha_hora_creacion 		= null;
	}
	
}

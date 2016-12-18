package crm_BE.cuentas;

import java.sql.Timestamp;

/*********************************************************************
 * @author kcardona
 * @version 1.0
 * @since 03/11/2015
 * @FechaModificacion 03/11/2015
 * @Descripcion Entidad para el manejo de caja chica.
 ********************************************************************/

public class Efectivo_BE {
		
	public int ef_efectivo;
	public int ef_caja_chica;
	public Short ef_tipo;
	public int ef_cantidad;
	public float ef_monto;

	
	public Efectivo_BE() {
		this.ef_efectivo 	= -9999;
		this.ef_efectivo 	= -9999;
		this.ef_tipo 		= -9999;
		this.ef_cantidad 	= -9999;
		this.ef_monto 		= -9999;
	}
	
}

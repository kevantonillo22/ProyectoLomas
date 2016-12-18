package crm_BE.cuentas;

import java.sql.Timestamp;

/*********************************************************************
 * @author kcardona
 * @version 1.0
 * @since 23/04/2014
 * @FechaModificacion 23/04/2014
 * @Descripcion Entidad de negocio de la bitácora del sistema.
 ********************************************************************/

public class Busqueda_BE {
		
	public String ch_general;
	public Timestamp ch_fecha1;
	public Timestamp ch_fecha2;
	public float ch_monto1;
	public float ch_monto2;
	
	
	public Busqueda_BE() {
		this.ch_general 	= null;
		this.ch_fecha1	 	= null;
		this.ch_fecha2 		= null;
		this.ch_monto1 		= -9999;
		this.ch_monto2 		= -9999;
	}
	
}

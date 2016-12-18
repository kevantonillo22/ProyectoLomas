package crm_BE.cuentas;

import java.sql.Timestamp;

/*********************************************************************
 * @author kcardona
 * @version 1.0
 * @since 23/04/2014
 * @FechaModificacion 23/04/2014
 * @Descripcion Entidad de negocio de la bitácora del sistema.
 ********************************************************************/

public class Cheque_BE {
		
	public int ch_cheque;
	public int ch_numero;
	public String ch_lugar;
	public Timestamp ch_fecha;
	public String ch_nombre;
	public float ch_cantidad;
	public String ch_motivo;
	public String ch_imagen;
	public Timestamp ch_fecha_hora;
	
	public Cheque_BE() {
		this.ch_cheque 		= -9999;
		this.ch_numero 		= -9999;
		this.ch_lugar 		= null;
		this.ch_fecha 		= null;
		this.ch_nombre 		= null;
		this.ch_cantidad 	= -9999;
		this.ch_motivo		= null;
		this.ch_imagen	 	= null;
		this.ch_fecha_hora	= null;
	}
	
}

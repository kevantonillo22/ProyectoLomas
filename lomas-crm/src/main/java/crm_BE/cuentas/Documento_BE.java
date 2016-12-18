package crm_BE.cuentas;

import java.sql.Timestamp;

/*********************************************************************
 * @author kcardona
 * @version 1.0
 * @since 03/11/2015
 * @FechaModificacion 03/11/2015
 * @Descripcion Entidad para el manejo de caja chica.
 ********************************************************************/

public class Documento_BE {
		
	public int do_documento;
	public int do_caja_chica;
	public int do_nit;
	public String do_nombre;
	public float do_monto;

	
	public Documento_BE() {
		this.do_documento 	= -9999;
		this.do_caja_chica 	= -9999;
		this.do_nit 		= -9999;
		this.do_nombre 		= null;
		this.do_monto 		= -9999;
	}
	
}

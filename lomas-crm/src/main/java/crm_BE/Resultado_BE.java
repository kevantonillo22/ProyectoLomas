package crm_BE;

/*********************************************************************************
 * @author rbarrios
 * @version 1.0
 * @since 23/04/2014
 * @FechaModificacion 23/04/2014
 * @Descripcion Entidad que guarda el resultado de una operación realizada desde
 *              la aplicación.
 ********************************************************************************/

public class Resultado_BE {
	public int re_codigo;
	public int re_identificador;
	public int re_filas_afectadas;
	public boolean re_exitoso;
	public String re_descripcion;

	public Resultado_BE() {
		this.re_codigo = -9999;
		this.re_identificador = -9999;
		this.re_filas_afectadas = -9999;
	}
}

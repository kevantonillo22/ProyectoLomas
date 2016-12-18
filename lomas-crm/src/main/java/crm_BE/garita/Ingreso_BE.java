package crm_BE.garita;

import java.sql.Timestamp;

public class Ingreso_BE {
	public int in_ingreso;
	public String in_placa;
	public int in_direccion;
	public String in_imagen_placa;
	public String in_imagen_rostro;
	public String in_imagen_dpi;
	public Timestamp in_fecha_entrada;
	public int in_usuario;
	public int in_estado;
	public int in_rownum;
	public String in_texto_direccion;
	
	public Ingreso_BE() {
		this.in_ingreso=-9999;
		this.in_placa=null;
		this.in_direccion=-9999;
		this.in_imagen_placa=null;
		this.in_imagen_rostro=null;
		this.in_imagen_dpi=null;
		this.in_fecha_entrada=null;
		this.in_usuario=-9999;
		this.in_estado=-9999;
		this.in_rownum=-9999;
		this.in_texto_direccion = null;
	}
}

package crm_BE.garita;


public class Direccion_BE {
	public int di_direccion;
	public String di_numero_calle_av;
	public int di_calle_av;
	public String di_num_casa;
	public String di_familia;
	public String di_telefono;
	public String di_nombre_titular;
	public String di_email;
	public int di_estado;
	public int di_estado_pago;
	public int di_estado_domicilio;
	
	
	
	public Direccion_BE() {
		this.di_direccion=-9999;
		this.di_numero_calle_av=null;
		this.di_calle_av=-9999;
		this.di_num_casa=null;
		this.di_familia=null;
		this.di_telefono=null;
		this.di_nombre_titular=null;
		this.di_email=null;
		this.di_estado=-9999;
		this.di_estado_pago=-9999;
		this.di_estado_domicilio=-9999;
	}
}

package crm_BE;
/*********************************************************************
 * @author Kev
 * @version 1.0
 * @since 12/05/2014
 * @FechaModificacion 12/05/2014
 * @Descripcion Entidad de Función
 ********************************************************************/
public class Funcion_BE {
	public String 	fu_icono;
	public short 	fu_tipo;
	public short 	fu_visible;
	public String 	fu_link;
	public String 	fu_descripcion;
	public String 	fu_nombre;
	public int 		fu_padre;
	public int		fu_funcion;
		
	public Funcion_BE(){
		this.fu_tipo=		-9999;
		this.fu_visible=	-9999;
		this.fu_padre=		-9999;
		this.fu_funcion=	-9999;		
	}
	
	
}

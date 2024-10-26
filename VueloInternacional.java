
public class VueloInternacional extends VueloPublico{
	private int cantRefrigerios;
	private String[] escalas;
	
	public VueloInternacional(String origen, String destino, String fecha, int tripulantes, double valorRefrigerio, 
			int cantRefrigerios, double[] precios,  int[] cantAsientos,  String[] escalas) {
		super(origen, destino, fecha, tripulantes, valorRefrigerio, precios, cantAsientos);
		
		this.cantRefrigerios = cantRefrigerios;
		//inicializar lo correspondiente a vuelo internacional
	}

}

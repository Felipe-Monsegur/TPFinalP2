
public class VueloPublico extends Vuelo {
	private double valorRefrigerio;
	
	//puede que se tenga que modificar algo
	private double[] precios;
	private int[] cantAsientos;
	
	public VueloPublico(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, double[] precios, int[] cantAsientos) {
		super(origen, destino, fecha, tripulantes);
		
		this.valorRefrigerio = valorRefrigerio;
		//inicializar lo correspondiente a vuelo publico
	}

}

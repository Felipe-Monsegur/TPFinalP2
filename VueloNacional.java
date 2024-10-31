
public class VueloNacional extends VueloPublico{
	
	public VueloNacional(String codigo, String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, double[] precios, int[] cantAsientos) {
		super(codigo, origen, destino, fecha, tripulantes, valorRefrigerio, precios, cantAsientos);
	}
	
	@Override
	public String toString() {
		return super.toString()+" - NACIONAL";
	}

}

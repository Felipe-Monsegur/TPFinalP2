public class VueloInternacional extends VueloPublico {
	private int cantRefrigerios;
	private String[] escalas;

	public VueloInternacional(String codigo, String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, int cantRefrigerios, double[] precios, int[] cantAsientos, String[] escalas) {
		super(codigo, origen, destino, fecha, tripulantes, valorRefrigerio, precios, cantAsientos);

		this.cantRefrigerios = cantRefrigerios;
		this.escalas = escalas;
	}
	
	@Override
	public double calcularValorPasaje(int nroAsiento) {
		Asiento asiento = asientos.get(nroAsiento);
		double valor = asiento.getPrecioBase();
		valor += super.getValorRefrigerio() * cantRefrigerios;
		valor *= 1.2;
		return valor;
	}
	
	@Override
	public String toString() {
		return super.toString() + " - INTERNACIONAL";
	}
	
}

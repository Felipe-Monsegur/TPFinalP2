public class VueloPrivado extends Vuelo {
	private double precioJet;
	private int dniComprador;
	private final static int capacidadMaxima = 15;
	private int[] acompaniantes;
	private int cantidadJets;
	
	public VueloPrivado(String codigo, String origen, String destino, String fecha, int tripulantes, double precio,
			int dniComprador, int[] acompaniantes) {
		super(codigo, origen, destino, fecha, tripulantes);
		this.precioJet = precio;
		this.dniComprador = dniComprador;
		this.acompaniantes = acompaniantes;

		// calcular cantidad de jets necesarios
		int totalPasajeros = 1 + acompaniantes.length; // incluye al comprador
		this.cantidadJets = (int) Math.ceil((double) totalPasajeros / capacidadMaxima);
	}

	public int getCantidadJets() {
		return cantidadJets;
	}

	// costo total del vuelo privado
	public double calcularValor() {
		double valor = this.cantidadJets * this.precioJet;
		return valor * 1.3;
	}
	
	@Override
	public String toString() {
		return super.toString() + " - PRIVADO (" + getCantidadJets() + ")";
	}

}


public class VueloPrivado extends Vuelo {
	private double precioJet;
	private int dniComprador;
	private final static int capacidadMaxima = 15;
	private int[] acompaniantes;
	private int cantidadJets;
	private double valor;
	
	public VueloPrivado(String codigo, String origen, String destino, String fecha, int tripulantes, double precio, int dniComprador,
			int[] acompaniantes) {
		super(codigo, origen, destino, fecha, tripulantes);
		this.precioJet = precio;
		this.dniComprador = dniComprador;
		this.acompaniantes= acompaniantes;
		
	       //calcular cantidad de jets necesarios y el costo total del vuelo privado
        int totalPasajeros = 1 + acompaniantes.length;  //incluye al comprador
        this.cantidadJets = (int) Math.ceil((double) totalPasajeros / capacidadMaxima);
        this.valor = this.cantidadJets * precioJet;
	}

	public double getValor(){
		return valor;
	}
	public int getCantidadJets() {
		return cantidadJets;
	}
	
	@Override
	public String toString() {
	    return super.toString()+" - PRIVADO ("+getCantidadJets()+")";
	}

}

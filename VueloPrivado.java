import java.util.HashSet;

public class VueloPrivado extends Vuelo {
	private double precioJet;
	private int dniComprador;
	// private HashSet<Integer> acompaniantes;

	//faltan mas parametros para la clase vuelo privado
	
	public VueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio, int dniComprador,
			int[] acompaniantes) {
		super(origen, destino, fecha, tripulantes);
		this.precioJet = precio;
		this.dniComprador = dniComprador;
	}

}

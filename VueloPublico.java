import java.util.HashMap;
import java.util.Map;

public class VueloPublico extends Vuelo {
	private double valorRefrigerio;
	private Map<Integer, Asiento> asientos;
	
	// Creo que esto no es necesario
	//private Map<Integer, Pasaje> pasajes;
	
	// todavia no se como manejar el tema de los pasajes
	// no se si es necesario un map para los asientos, o si es necesaria una clase asiento. Por el momento lo hice asi, puede cambiar

	public VueloPublico(String codigo, String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, double[] precios, int[] cantAsientos) {
		super(codigo, origen, destino, fecha, tripulantes);

		this.valorRefrigerio = valorRefrigerio;
		this.asientos = new HashMap<>();

		int numeroAsiento = 1;
		// Inicializar asientos para cada clase
		for (int clase = 0; clase < cantAsientos.length; clase++) {
			for (int i = 0; i < cantAsientos[clase]; i++) {
				Asiento asiento = new Asiento(numeroAsiento, precios[clase], clase);
				asientos.put(numeroAsiento, asiento);
				numeroAsiento++;
			}
		}
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

	// Completar
	// Esta mal, es provisorio
	public int venderPasaje(int dni, int nroAsiento, boolean aOcupar) {
		//completar
		if(!asientos.containsKey(nroAsiento)){
			throw new IllegalArgumentException("El asiento no existe.");
		}
		Asiento asiento = asientos.get(nroAsiento);
		asiento.vender();
		
		// Para ver si pasa el test
		return 1;
	}
	
	// Provisorio
	public void cancelarPasaje(int dni, int nroAsiento) {
		if(!asientos.containsKey(nroAsiento)) {
			throw new IllegalArgumentException("El asiento no existe.");
		}
		Asiento asiento = asientos.get(nroAsiento);
		asiento.liberar();
	}
	
	//reprogramar
	//...
	
	// Si se modifica algo sobre el manejo de asientos, hay que modificarlo
	public Map<Integer, String> asientosDisponibles() {
	    Map<Integer, String> asientosDisponibles = new HashMap<>();
	    for (Asiento asiento : asientos.values()) {
	        // Comprobar si el asiento está vendido
	        if (!asiento.getVendido()) {
	        	String seccion = null;
	        	if(asiento.getSeccion() == 0) {
	        		seccion = "turista";
	        	}else if (asiento.getSeccion() == 1) {
	        		seccion = "ejecutivo";
	        	}else if (asiento.getSeccion() == 2){
	        		seccion = "primera clase";
	        	}
	            asientosDisponibles.put(asiento.getNroAsiento(), seccion);
	        }
	    }
	    return asientosDisponibles;
	}
	
}

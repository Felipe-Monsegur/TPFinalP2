import java.util.HashMap;
import java.util.Map;

public class VueloPublico extends Vuelo {
	private double valorRefrigerio;
	private Map<Integer, Asiento> asientos;
	private Map<Integer, Pasaje> pasajes;

	public VueloPublico(String codigo, String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, double[] precios, int[] cantAsientos) {
		super(codigo, origen, destino, fecha, tripulantes);
		this.valorRefrigerio = valorRefrigerio;
		this.asientos = new HashMap<>();
		
		int numeroAsiento = 1;
		// Inicializar asientos para cada clase
		for (int clase = 0; clase < cantAsientos.length; clase++) {
			String nomClase = null;
			if(clase == 0) {
				nomClase = "turista";
			}else if (clase == 1) {
				nomClase = "ejecutivo";
			}else if (clase == 2) {
				nomClase = "primera clase";
			}
			for (int i = 0; i < cantAsientos[clase]; i++) {
				Asiento asiento = new Asiento(numeroAsiento, precios[clase], nomClase);
				asientos.put(numeroAsiento, asiento);
				numeroAsiento++;
			}
		}
		this.pasajes = new HashMap<>();
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	// puede cambiar
	public int venderPasaje(int dni, int nroAsiento, boolean aOcupar) {
        // esto NO se cambia
		if (!asientos.containsKey(nroAsiento)) {
            throw new IllegalArgumentException("El asiento no existe.");
        }
        Asiento asiento = asientos.get(nroAsiento);

        if (asiento.getVendido()) {
            throw new IllegalStateException("El asiento ya está vendido.");
        }
        asiento.vender();
        if (aOcupar) { // Ocupar asiento si es requerido
            asiento.ocupar();
        }
        
        // puede cambiar
    	int nroPasaje = pasajes.size() + 1;
        Pasaje pasaje = new Pasaje(nroPasaje, asiento.getSeccion(), asiento.getPrecio(), dni);
        pasajes.put(nroAsiento, pasaje);  // Guardar pasaje por nroAsiento

        return nroPasaje;
	}
	
	// puede cambiar
	public void cancelarPasaje(int dni, int nroAsiento) {
		// esto no se cambia
		if(!asientos.containsKey(nroAsiento)) {
			throw new IllegalArgumentException("El asiento no existe.");
		}
		Asiento asiento = asientos.get(nroAsiento);
		asiento.liberar();
		
		// falta
	}

	public Map<Integer, String> asientosDisponibles() {
        Map<Integer, String> asientosDisponibles = new HashMap<>();
        for (Asiento asiento : asientos.values()) {
            if (!asiento.getVendido()) {
                asientosDisponibles.put(asiento.getNroAsiento(), asiento.getSeccion());
            }
        }
        return asientosDisponibles;
    }
	
	public void asignarPasaje(int nroAsiento) {
		Asiento asiento = asientos.get(nroAsiento);
		asiento.vender();
	}
	
	public Map<Integer, Pasaje> getPasajes(){
		return pasajes;
	}

}

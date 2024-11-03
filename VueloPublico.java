import java.util.HashMap;
import java.util.Map;

public class VueloPublico extends Vuelo {
	protected double valorRefrigerio;
	protected Map<Integer, Asiento> asientos;
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
	
	// Función que se encarga de vender un pasaje y registrarlo en el vuelo
	public void venderPasaje(int codigo, double precioPasaje, int dni, int nroAsiento, boolean aOcupar) {
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
        
        Pasaje pasaje = new Pasaje(codigo, asiento.getSeccion(), precioPasaje, dni);
        pasajes.put(nroAsiento, pasaje);  // Guardar pasaje por nroAsiento
	}

	
	// Funcion que se encarga de cancelar un pasaje
	public void cancelarPasaje(int dni, int nroAsiento) {
		if(!asientos.containsKey(nroAsiento)) {
			throw new IllegalArgumentException("El asiento no existe.");
		}
		Pasaje pasaje = pasajes.get(nroAsiento);
		int dniComprador = pasaje.getDNIcliente();
		if(dniComprador == dni) {
			Asiento asiento = asientos.get(nroAsiento);
			asiento.liberar();
			pasajes.remove(nroAsiento);
		}
	}

	// Funcion que devuelve un mapa con los asientos disponibles del vuelo
	public Map<Integer, String> asientosDisponibles() {
        Map<Integer, String> asientosDisponibles = new HashMap<>();
        for (Asiento asiento : asientos.values()) {
            if (!asiento.getVendido()) {
                asientosDisponibles.put(asiento.getNroAsiento(), asiento.getSeccion());
            }
        }
        return asientosDisponibles;
    }
	
	// se puede cambiar !!!!
	public void asignarPasaje(int nroAsiento) {
		Asiento asiento = asientos.get(nroAsiento);
		asiento.vender();
	}
	
	// Calcula el valor de un pasaje dado el nro de asiento que ocupa
	public double calcularValorPasaje(int nroAsiento) {
		Asiento asiento = asientos.get(nroAsiento);
		double valor = asiento.getPrecioBase();
		valor += this.valorRefrigerio;
		valor *= 1.2;
		return valor;
	}
	
	// Devuelve los pasajes del vuelo
	public Map<Integer, Pasaje> getPasajes(){
		return pasajes;
	}

}

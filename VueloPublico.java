import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VueloPublico extends Vuelo {
	private double valorRefrigerio;
	private Map<Integer, Asiento> asientos;

	// Creo que esto no es necesario
	//private Map<Integer, Pasaje> pasajes;

	// todavia no se como manejar el tema de los pasajes
	// no se si es necesario un map para los asientos, o si es necesaria una clase
	// asiento. Por el momento lo hice asi, puede cambiar

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

	public Map<Integer, Asiento> getAsientos() {
		return asientos;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public double recaudacion() {
		double total = 0;
		for (Asiento asiento : asientos.values()) {
			if (asiento.getVendido()) {
				total += asiento.getPrecio();
			}
		}
		return total;
	}

	// Completar
	// Esta mal, es provisorio
	public int venderPasaje(int dni, int nroAsiento, boolean aOcupar) {
        if (!asientos.containsKey(nroAsiento)) {
            throw new IllegalArgumentException("El asiento no existe.");
        }
        Asiento asiento = asientos.get(nroAsiento);

        if (asiento.getVendido()) {
            throw new IllegalStateException("El asiento ya está vendido.");
        }
        asiento.vender();

        // Ocupar asiento si es requerido
        if (aOcupar) {
            asiento.ocupar();
        }

        // Crear el pasaje
        String seccion = seccionAsiento(asiento.getSeccion());
        Pasaje pasaje = new Pasaje(nroAsiento, seccion, asiento.getPrecio(), dni);
        pasajes.put(dni, pasaje);  // Guardar pasaje por DNI del cliente.

        return pasaje.getCodigo();
    }

	// Provisorio
	public void cancelarPasaje(int dni) {
        Pasaje pasaje = pasajes.get(dni);
        if (pasaje == null) {
            throw new IllegalArgumentException("No existe un pasaje con el DNI proporcionado.");
        }
        
        int nroAsiento = pasaje.getCodigo();  // Código del pasaje es el número de asiento.
        if (asientos.containsKey(nroAsiento)) {
            Asiento asiento = asientos.get(nroAsiento);
            asiento.liberar();
            pasajes.remove(dni);
        }
    }

	// Si se modifica algo sobre el manejo de asientos, hay que modificarlo
	public Map<Integer, String> asientosDisponibles() {
        Map<Integer, String> asientosDisponibles = new HashMap<>();
        for (Asiento asiento : asientos.values()) {
            if (!asiento.getVendido()) {
                String seccion = seccionAsiento(asiento.getSeccion());
                asientosDisponibles.put(asiento.getNroAsiento(), seccion);
            }
        }
        return asientosDisponibles;
    }


	private String seccionAsiento(int seccion) {
		switch (seccion) {
			case 0:
				return "turista";
			case 1:
				return "ejecutivo";
			case 2:
				return "primera clase";
			default:
				throw new IllegalArgumentException("Sección desconocida");
		}
	}


	public boolean mismaOmejorSeccion(int seccionAsiento, int seccionCliente) {
		return seccionAsiento >= seccionCliente;
	}

}

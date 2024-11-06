import java.util.HashMap;
import java.util.Map;

public class VueloPublico extends Vuelo {
	private double valorRefrigerio;
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
	
	// Función que se encarga de vender un pasaje y registrarlo en el vuelo
	public void venderPasaje(int codigo, int dni, int nroAsiento, boolean aOcupar) {
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
        
        Pasaje pasaje = new Pasaje(codigo, nroAsiento, asiento.getSeccion(), dni);
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
	
	// Si el vuelo contiene el pasaje con el codigo y el dni pasados por parametros, devuelve el numero de asiento,
	// en caso contrario, devuelve 0
	public int contienePasaje(int dniPasaje, int codPasaje) {
		for(Pasaje pasaje: pasajes.values()) {
			int cod = pasaje.getCodigo();
			int dni = pasaje.getDNIcliente();
			if(cod == codPasaje && dni == dniPasaje) {
				int nroAsiento = pasaje.getNroAsiento();
				return nroAsiento;
			}
		}
		return 0;
	}
	
	// devuelve true si tiene al menos un asiento disponible en la seccion pasada
    public boolean tieneDisponibleSeccion(String seccion) {
        for (Asiento asiento : asientos.values()) {
            if (!asiento.getVendido() && asiento.getSeccion().equals(seccion)) {
                asiento.ocupar(); 
                return true;
            }
        }
       return false;
    }
    
    // asigna al sistema un pasaje reprogramado
    public void asignarPasajeReprogramado(int codigo, int dni, String seccion) {
        for (Asiento asiento : asientos.values()) {
            if (!asiento.getVendido() && asiento.getSeccion().equals(seccion)) {
          
            	int nroAsiento = asiento.getNroAsiento();
            	Pasaje pasaje = new Pasaje(codigo, nroAsiento, seccion, dni);
            	pasajes.put(nroAsiento, pasaje);
            	
               	asiento.vender();
               	return;
            }
        }
    }

	// Calcula el valor de un pasaje dado el asiento que ocupa
	public double calcularValorPasaje(int nroAsiento) {
		Asiento asiento = asientos.get(nroAsiento);
		double valor = asiento.getPrecioBase();
		valor += this.valorRefrigerio;
		valor *= 1.2;
		return valor;
	}
	
	public Map<Integer, Pasaje> getPasajes(){
		return pasajes;
	}
	
	public double getValorRefrigerio() {
		return valorRefrigerio;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	

}

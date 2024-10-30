import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Aerolinea implements IAerolinea {
	private String nombre;
	private String CUIT;
	private Map<Integer, Cliente> clientes;
	private Map<String, Aeropuerto> aeropuertos;
	private Map<String, Vuelo> vuelos;
	
	/*
	es para la parte del enunciado que nos pide conocer el total recaudado dado un destino
	private Map<String, double> recaudacionTotal;
	String = nombre destino
	double = precio total
	*/ 

	//ejercicio 1
	public Aerolinea(String nombre, String CUIT) {
		this.nombre = nombre;
		this.CUIT = CUIT;
		this.clientes = new HashMap<>();
		this.aeropuertos = new HashMap<>();
		this.vuelos = new HashMap<>();
	}

	//ejercicio 2
	@Override
	public void registrarCliente(int dni, String nombre, String telefono) {
		// Verificar si el cliente ya está registrado por su DNI
		if (clientes.containsKey(dni)) {
			throw new IllegalArgumentException("El cliente ya está registrado.");
		}

		// Crear un nuevo cliente
		Cliente cliente = new Cliente(dni, nombre, telefono);
		clientes.put(dni, cliente);
	}

	//ejercicio 3
	@Override
	public void registrarAeropuerto(String nombre, String pais, String provincia, String direccion) {
		if (aeropuertos.containsKey(nombre)) {
			throw new IllegalArgumentException("El aeropuerto ya está registrado.");
		}
		Aeropuerto aeropuerto = new Aeropuerto(nombre, pais, provincia, direccion);
		aeropuertos.put(nombre, aeropuerto);
	}

	//ejercicio 4
	@Override
	public String registrarVueloPublicoNacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, double[] precios, int[] cantAsientos) {
		if (!aeropuertos.containsKey(origen) || !aeropuertos.containsKey(destino)) {
			throw new IllegalArgumentException("El aeropuerto de origen o destino no esta registrado");
		}
		if (!aeropuertos.get(origen).getPais().equals("Argentina")
				|| !aeropuertos.get(destino).getPais().equals("Argentina")) {
			throw new IllegalArgumentException("Los vuelos nacionales deben tener origen y destino en Argentina.");
		}

		// Generar codigo del vuelo
		int nroVuelo = vuelos.size() + 1;
		String codVuelo = nroVuelo + "-PUB";

		// Crear vuelo y añadirlo al registro
		Vuelo vuelo = new VueloNacional(codVuelo, origen, destino, fecha, tripulantes, valorRefrigerio, precios, cantAsientos);
		vuelos.put(codVuelo, vuelo);
		return codVuelo;
	}

	//ejercicio 5
	@Override
	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, int cantRefrigerios, double[] precios, int[] cantAsientos, String[] escalas) {
		if (!aeropuertos.containsKey(origen) || !aeropuertos.containsKey(destino)) {
			throw new IllegalArgumentException("El aeropuerto de origen o destino no esta registrado");
		}
		
		//se podria verificar que los destinos de la escala sean validos y algunas otras cosas
		
		// Generar codigo del vuelo
		int nroVuelo = vuelos.size() + 1;
		String codVuelo = nroVuelo + "-PUB";

		// Crear vuelo y añadirlo al registro
		Vuelo vuelo = new VueloInternacional(codVuelo, origen, destino, fecha, tripulantes, valorRefrigerio, cantRefrigerios, precios, cantAsientos, escalas);
		vuelos.put(codVuelo, vuelo);
		return codVuelo;
	}

	//ejercicio 6
	@Override
	public String VenderVueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio,
			int dniComprador, int[] acompaniantes) {
		// Validar aeropuertos para vuelos privados
		if (!aeropuertos.containsKey(origen) || !aeropuertos.containsKey(destino)) {
			throw new IllegalArgumentException("El aeropuerto de origen o destino no está registrado.");
		}

		// Generar código de vuelo privado
		int nroVuelo = vuelos.size() + 1;
		String codVuelo = nroVuelo + "-PRI";

		// Crear vuelo privado y añadirlo al registro
		Vuelo vueloPrivado = new VueloPrivado(codVuelo, origen, destino, fecha, tripulantes, precio, dniComprador, acompaniantes);
		vuelos.put(codVuelo, vueloPrivado);
		return codVuelo;
	}
	
	//ejercicio 7
	@Override
	public Map<Integer, String> asientosDisponibles(String codVuelo){
		return null;
	}
	
	//ejercicio 8
	@Override
	public int venderPasaje(int dni, String codVuelo, int nroAsiento, boolean aOcupar) {
		if (!clientes.containsKey(dni)) {
			throw new IllegalArgumentException("El cliente no está registrado en el sistema.");
		}
		if(!vuelos.containsKey(codVuelo)) {
			throw new IllegalArgumentException("El vuelo no existe");
		}
		
		//completar..
		return 0;
	}
    
	//ejercicio 11
	@Override
	public List<String> consultarVuelosSimilares(String origen, String destino, String fecha) {
	    List<String> vuelosSimilares = new ArrayList<>();
	    
	    //paso la fecha a localDate para manejarla con las funciones propias de la clase
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    LocalDate fechaDada = LocalDate.parse(fecha, formatter);
	    LocalDate fechaLimite = fechaDada.plusDays(7);

	    for (Vuelo vuelo : vuelos.values()) {
	        LocalDate fechaVuelo = LocalDate.parse(vuelo.getFecha(), formatter);
	        
	        if (vuelo.getOrigen().equals(origen) &&
	            vuelo.getDestino().equals(destino) &&
	            (fechaVuelo.isAfter(fechaDada.minusDays(1)) && fechaVuelo.isBefore(fechaLimite.plusDays(1)))) {
	            
	            vuelosSimilares.add(vuelo.getCodigo());
	        }
	    }
	    return vuelosSimilares;
	}
	
	@Override
	public void cancelarPasaje(int dni, String codVuelo, int nroAsiento) {
	}

	@Override
	public void cancelarPasaje(int dni, int codPasaje) {
	}

	@Override
	public List<String> cancelarVuelo(String codVuelo) {
		return null;
	}

	@Override
	public double totalRecaudado(String destino) {
		return 0;
	}

	@Override
	public String detalleDeVuelo(String codVuelo) {
		return null;
	}
}
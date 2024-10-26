import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Aerolinea implements IAerolinea {
	private String nombre;
	private String CUIT;
	private Map<Integer, Cliente> clientes;
	private Map<String, Aeropuerto> aeropuertos;
	private Map<String, Vuelo> vuelos;
	
	/*
	es una variable "global" que enumera los vuelos y a partir de eso les da un codigo
	no sé con que criterio se debe dar el numero de codigo, si se trata de un num aleatorio o es secuencialmente.
	por el momento hice eso, igual ACLARO no me parece correcto 
	
	ademas esta mal porque nunca se actualiza. En caso de que se ingresen vuelos siempre es el mismo numero,
	asi que es temporal no mas, era mas que nada para ver si pasaba los test 
	
	*/
	
	private int contVuelos;
	
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
		this.contVuelos++;
		String codVuelo = contVuelos + "-PUB";

		// Crear vuelo y añadirlo al registro
		Vuelo vuelo = new VueloNacional(codVuelo, origen, destino, fecha, tripulantes, valorRefrigerio, precios, cantAsientos);
		vuelos.put(codVuelo, vuelo);
		return codVuelo;
	}

	//ejercicio 5
	@Override
	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, int cantRefrigerios, double[] precios, int[] cantAsientos, String[] escalas) {
		return null;
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
		contVuelos++;
		String codVuelo = contVuelos + "-PRI";

		// Crear vuelo privado y añadirlo al registro
		Vuelo vueloPrivado = new VueloPrivado(codVuelo, origen, destino, fecha, tripulantes, precio, dniComprador, acompaniantes);
		vuelos.put(codVuelo, vueloPrivado);
		return codVuelo;
	}
	
	@Override
	public Map<Integer, String> asientosDisponibles(String codVuelo){
		return null;
	}
	
	@Override
	public int venderPasaje(int dni, String codVuelo, int nroAsiento, boolean aOcupar) {
		return 0;
	}
    
	//ejercicio 11
	@Override
	public List<String> consultarVuelosSimilares(String origen, String destino, String fecha){
		List<String> vuelosSimilares = new ArrayList<>();
		//falta que los vuelos esten entre la fecha pasada hasta 1 semana despues
		
		for (Vuelo vuelo : vuelos.values()) {
			if (vuelo.getFecha().equals(fecha) && vuelo.getOrigen().equals(origen)
					&& vuelo.getDestino().equals(destino)) {
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
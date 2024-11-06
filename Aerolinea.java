import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Aerolinea implements IAerolinea {
	private String nombre;
	private String CUIT;
	private Map<String, Aeropuerto> aeropuertos;
	private Map<Integer, Cliente> clientes;
	private Map<String, Vuelo> vuelos;
	private Map<String, Double> recaudacionPorDestino; // Mapa para almacenar la recaudación por destino
	private static Integer pasajesVendidos = 0; // Variable global

	// Ejercicio 1
	public Aerolinea(String nombre, String CUIT) {
		this.nombre = nombre;
		this.CUIT = CUIT;
		this.clientes = new HashMap<>();
		this.aeropuertos = new HashMap<>();
		this.vuelos = new HashMap<>();
        this.recaudacionPorDestino = new HashMap<>();
	}

	// Ejercicio 2
	@Override
	public void registrarCliente(int dni, String nombre, String telefono) {
		// Verifica si el cliente ya está registrado por su DNI
		if (clientes.containsKey(dni)) {
			throw new IllegalArgumentException("El cliente ya está registrado.");
		}

		// Crea un nuevo cliente
		Cliente cliente = new Cliente(dni, nombre, telefono);
		clientes.put(dni, cliente);
	}

	// Ejercicio 3
	@Override
	public void registrarAeropuerto(String nombre, String pais, String provincia, String direccion) {
		if (aeropuertos.containsKey(nombre)) {
			throw new IllegalArgumentException("El aeropuerto ya está registrado.");
		}
		Aeropuerto aeropuerto = new Aeropuerto(nombre, pais, provincia, direccion);
		aeropuertos.put(nombre, aeropuerto);
	}

	// Ejercicio 4
	@Override
	public String registrarVueloPublicoNacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, double[] precios, int[] cantAsientos) {
		if (!aeropuertos.containsKey(origen) || !aeropuertos.containsKey(destino)) {
			throw new IllegalArgumentException("El aeropuerto de origen o destino no está registrado.");
		}
		if (!aeropuertos.get(origen).getPais().equals("Argentina")
				|| !aeropuertos.get(destino).getPais().equals("Argentina")) {
			throw new IllegalArgumentException("Los vuelos nacionales deben tener origen y destino en Argentina.");
		}

		// Genera código del vuelo
		int nroVuelo = vuelos.size() + 1;
		String codVuelo = nroVuelo + "-PUB";

		// Crea vuelo nacional
		Vuelo vueloNacional = new VueloNacional(codVuelo, origen, destino, fecha, tripulantes, valorRefrigerio, precios,
				cantAsientos);
		
		// Lo añade al registro
		vuelos.put(codVuelo, vueloNacional);
		return codVuelo;
	}

	// Ejercicio 5
	@Override
	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, int cantRefrigerios, double[] precios, int[] cantAsientos, String[] escalas) {
		if (!aeropuertos.containsKey(origen) || !aeropuertos.containsKey(destino)) {
			throw new IllegalArgumentException("El aeropuerto de origen o destino no esta registrado");
		}

		// Genera código del vuelo
		int nroVuelo = vuelos.size() + 1;
		String codVuelo = nroVuelo + "-PUB";

		// Crea vuelo internacional
		Vuelo vueloInternacional = new VueloInternacional(codVuelo, origen, destino, fecha, tripulantes, valorRefrigerio,
				cantRefrigerios, precios, cantAsientos, escalas);

		// Lo añade al registro
		vuelos.put(codVuelo, vueloInternacional);
		return codVuelo;
	}
 
	// Ejercicio 6 y 10
	@Override
	public String VenderVueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio,
			int dniComprador, int[] acompaniantes) {
		// Valida aeropuertos para vuelos privados
		if (!aeropuertos.containsKey(origen) || !aeropuertos.containsKey(destino)) {
			throw new IllegalArgumentException("El aeropuerto de origen o destino no está registrado.");
		}

		// Genera código de vuelo privado
		int nroVuelo = vuelos.size() + 1;
		String codVuelo = nroVuelo + "-PRI";

		// Crea vuelo privado 
		Vuelo vueloPrivado = new VueloPrivado(codVuelo, origen, destino, fecha, tripulantes, precio, dniComprador,
				acompaniantes);
		
		// Calcula el valor total del vuelo y lo agrega a la recaudacion por destino
		VueloPrivado vPrivado = (VueloPrivado) vueloPrivado; // Casteo
		double valorTotal = vPrivado.calcularValor();
		recaudacionPorDestino.merge(destino, valorTotal, Double::sum);
		
		// Lo añade al registro
		vuelos.put(codVuelo, vueloPrivado);
		return codVuelo;
	}

	// Ejercicio 7
	@Override
	public Map<Integer, String> asientosDisponibles(String codVuelo) {
		if (!vuelos.containsKey(codVuelo)) {
			throw new IllegalArgumentException("El vuelo no existe.");
		}

		Vuelo vuelo = vuelos.get(codVuelo);
		if (vuelo instanceof VueloPublico) {
			VueloPublico vueloPublico = (VueloPublico) vuelo;
			return vueloPublico.asientosDisponibles();
		}
		throw new IllegalArgumentException("No es posible con vuelos privados.");
	}

	// Ejercicio 8 y 9
	@Override
	public int venderPasaje(int dni, String codVuelo, int nroAsiento, boolean aOcupar) {
		if (!clientes.containsKey(dni)) {
			throw new IllegalArgumentException("El cliente no está registrado en el sistema.");
		}
		if (!vuelos.containsKey(codVuelo)) {
			throw new IllegalArgumentException("El vuelo no existe.");
		}
		
		Vuelo vuelo = vuelos.get(codVuelo);
		if (vuelo instanceof VueloPublico) {
			VueloPublico vueloPublico = (VueloPublico) vuelo; // Casteo
			
			// Calcula el valor del pasaje
			double valorPasaje = vueloPublico.calcularValorPasaje(nroAsiento);
			
			// Genera el codigo de pasaje
			Integer codigoPasaje = generarCodigoPasaje();
			
			// Vende el pasaje
			vueloPublico.venderPasaje(codigoPasaje, dni, nroAsiento, aOcupar);	
			// Lo agrega a la recaudación por destino
			String destino = vuelo.getDestino();	
			recaudacionPorDestino.merge(destino, valorPasaje, Double::sum);
			
			return codigoPasaje;
		}
		throw new IllegalArgumentException("No es posible con vuelos privados.");
	}

	// Ejercicio 11
	@Override
	public List<String> consultarVuelosSimilares(String origen, String destino, String fecha) {
		List<String> vuelosSimilares = new ArrayList<>();

		// Pasa la fecha a localDate para manejarla con las funciones propias de la clase
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

	// Ejercicio 12-A
	@Override
	public void cancelarPasaje(int dni, String codVuelo, int nroAsiento) {
	    if (!clientes.containsKey(dni)) {
	        throw new IllegalArgumentException("El cliente no está registrado en el sistema.");
	    }
	    if (!vuelos.containsKey(codVuelo)) {
	        throw new IllegalArgumentException("El vuelo no existe.");
	    }
	    Vuelo vuelo = vuelos.get(codVuelo);
	    VueloPublico vueloPublico = (VueloPublico) vuelo;
	    
	    vueloPublico.cancelarPasaje(dni, nroAsiento);
	}

	// Ejercicio 12-B
	@Override
	public void cancelarPasaje(int dni, int codPasaje) {
	    if (!clientes.containsKey(dni)) {
	        throw new IllegalArgumentException("El cliente no está registrado en el sistema.");
	    }
		for(Vuelo vuelo: vuelos.values()) {
			VueloPublico vueloPublico = (VueloPublico) vuelo;
			int contienePasaje = vueloPublico.contieneCodigoPasaje(dni, codPasaje);
			
			// Si contienePasaje devuelve un numero mayor a 0, significa que lo tiene y devuelve el num de asiento
			if(contienePasaje > 0) {
				// Llama a cancelarPasaje con el dni y el num de asiento correspondiente
				vueloPublico.cancelarPasaje(dni, contienePasaje);
			}	
		}
	}

	// Ejercicio 13
	@Override
	public List<String> cancelarVuelo(String codVuelo) {
	    if (!vuelos.containsKey(codVuelo)) {
	        throw new IllegalArgumentException("El vuelo no existe.");
	    }

	    Vuelo vuelo = vuelos.get(codVuelo);
	    VueloPublico vueloCancelado = (VueloPublico) vuelo;

	    Map<Integer, Pasaje> pasajesAfectados = vueloCancelado.getPasajes();
	    List<String> vuelosAlternativos = consultarVuelosSimilares(vuelo.getOrigen(), vuelo.getDestino(), vuelo.getFecha());
	    vuelosAlternativos.remove(codVuelo);

	    List<String> resultado = new ArrayList<>();
	    
	    Iterator<Pasaje> iterator = pasajesAfectados.values().iterator();
	    while (iterator.hasNext()) {
	        Pasaje pasaje = iterator.next();
	        String resultadoPasaje = reprogramarPasaje(pasaje, vuelosAlternativos);
	        resultado.add(resultadoPasaje);
	    }

	    vuelos.remove(codVuelo); // Elimina el vuelo cancelado

	    return resultado;
	}

	// funcion auxiliar para reprogramar un pasaje en algun vuelo similar 
	private String reprogramarPasaje(Pasaje pasaje, List<String> vuelosAlternativos) {
	    int dni = pasaje.getDNIcliente();
	    Cliente cliente = clientes.get(dni);
	    String seccion = pasaje.getSeccion();
	    
	    StringBuilder resultado = new StringBuilder(cliente.toString());
	    for (String codigoVueloAlternativo : vuelosAlternativos) {
	        VueloPublico vueloAlternativo = (VueloPublico) vuelos.get(codigoVueloAlternativo);
	        if (vueloAlternativo.tieneDisponibleSeccion(seccion)) {
	            resultado.append(" - ").append(vueloAlternativo.getCodigo());
	            
	            // agrega el pasaje al vuelo
	        	int codigo = generarCodigoPasaje();
	        	vueloAlternativo.asignarPasajeReprogramado(codigo, dni, seccion);
	            
	            return resultado.toString();
	        }
	    }
	    resultado.append(" - CANCELADO");
	    return resultado.toString();
	}

	// Ejercicio 14
	@Override
	public double totalRecaudado(String destino) {
	    // Busca el destino en el mapa de recaudaciones. Si no existe, retorna 0.0
	    return recaudacionPorDestino.getOrDefault(destino, 0.0);
	}

	// Ejercicio 15
	@Override
	public String detalleDeVuelo(String codVuelo) {
		Vuelo vuelo = vuelos.get(codVuelo);

		if (!vuelos.containsKey(codVuelo)) {
			throw new IllegalArgumentException("El vuelo con el código " + codVuelo + " no existe.");
		}

		return vuelo.toString();
	}

	@Override 
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("AEROLINEA: " + this.nombre + "\n");
	    sb.append("CUIT: " + this.CUIT + "\n\n");

	    sb.append("Aeropuertos: " + aeropuertos.size() + " registrados\n");
	    
	    for (Aeropuerto aeropuerto : aeropuertos.values()) {
	        sb.append(aeropuerto.toString() + "\n");
	    }
	    sb.append("\n");  // Espacio adicional 

	    sb.append("Clientes: " + clientes.size() + " registrados\n");
	    for (Cliente cliente : clientes.values()) {
	        sb.append(cliente.toString() + "\n");
	    }
	    sb.append("\n"); 

	    sb.append("Vuelos: " + vuelos.size() + " registrados\n");
	    for (Vuelo vuelo : vuelos.values()) {
	        sb.append(vuelo.toString() + "\n");
	    }
	    sb.append("\n");  

	    return sb.toString();
	}

	private int generarCodigoPasaje() {
		pasajesVendidos++;
		int cod = pasajesVendidos;
		return cod;
	}

}
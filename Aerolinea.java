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
	private Map<String, Double> recaudacionPorDestino; // Mapa para almacenar la recaudación por destino

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
		// Verificar si el cliente ya está registrado por su DNI
		if (clientes.containsKey(dni)) {
			throw new IllegalArgumentException("El cliente ya está registrado.");
		}

		// Crear un nuevo cliente
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

		// Generar codigo del vuelo
		int nroVuelo = vuelos.size() + 1;
		String codVuelo = nroVuelo + "-PUB";

		// Crear vuelo nacional
		Vuelo vueloNacional = new VueloNacional(codVuelo, origen, destino, fecha, tripulantes, valorRefrigerio, precios,
				cantAsientos);
		
		// Lo añado al registro
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

		// Generar codigo del vuelo
		int nroVuelo = vuelos.size() + 1;
		String codVuelo = nroVuelo + "-PUB";

		// Crear vuelo y añadirlo al registro
		Vuelo vueloInternacional = new VueloInternacional(codVuelo, origen, destino, fecha, tripulantes, valorRefrigerio,
				cantRefrigerios, precios, cantAsientos, escalas);

		// Lo añado al registro
		vuelos.put(codVuelo, vueloInternacional);
		return codVuelo;
	}
 
	// Ejercicio 6 y 10
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

		// Crear vuelo privado 
		Vuelo vueloPrivado = new VueloPrivado(codVuelo, origen, destino, fecha, tripulantes, precio, dniComprador,
				acompaniantes);
		
		// Calculo el valor total del vuelo y lo agrego a la recaudacion por destino
		VueloPrivado vueloPriv = (VueloPrivado) vueloPrivado;
		double valorTotal = vueloPriv.calcularValor();
		recaudacionPorDestino.merge(destino, valorTotal, Double::sum);
		
		// Lo añado al registro
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
		String destino = vuelo.getDestino();	

		if (vuelo instanceof VueloPublico) {
			VueloPublico vueloPublico = (VueloPublico) vuelo; // hacer cast a VueloPublico
			double valorPasaje = vueloPublico.calcularValorPasaje(nroAsiento);
			recaudacionPorDestino.merge(destino, valorPasaje, Double::sum);
			
			return vueloPublico.venderPasaje(dni, nroAsiento, aOcupar);
		}
		throw new IllegalArgumentException("No es posible con vuelos privados.");
	}

	// Ejercicio 11
	@Override
	public List<String> consultarVuelosSimilares(String origen, String destino, String fecha) {
		List<String> vuelosSimilares = new ArrayList<>();

		// Paso la fecha a localDate para manejarla con las funciones propias de la clase
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
	}

	// Ejercicio 13
	@Override
	public List<String> cancelarVuelo(String codVuelo) {
	    Vuelo vuelo = vuelos.get(codVuelo);
	    VueloPublico vueloCancelado = (VueloPublico) vuelo; // Casteo

	    Map<Integer, Pasaje> pasajesAfectados = vueloCancelado.getPasajes();
	    String destinoCancelado = vueloCancelado.getDestino();
	    
	    List<VueloPublico> vuelosAlternativos = obtenerVuelosAlternativos(codVuelo, destinoCancelado); 
	    List<String> resultado = procesarPasajesAfectados(pasajesAfectados, vuelosAlternativos); 

	    vuelos.remove(codVuelo); // Eliminar el vuelo cancelado

	    return resultado;
	}

	// Funcion auxiliar para obtener vuelos alternativos con el mismo destino
	private List<VueloPublico> obtenerVuelosAlternativos(String codVuelo, String destinoCancelado) {
	    List<VueloPublico> vuelosAlternativos = new ArrayList<>();
	    for (Vuelo vuelo : vuelos.values()) {
	        if (!vuelo.getCodigo().equals(codVuelo) && vuelo.getDestino().equals(destinoCancelado) &&
	            vuelo instanceof VueloPublico) {
	                vuelosAlternativos.add((VueloPublico) vuelo);
	        }
	    }
	    return vuelosAlternativos;
	}

	// Funcion auxiliar para procesar los pasajes afectados por la cancelacion del vuelo
	private List<String> procesarPasajesAfectados(Map<Integer, Pasaje> pasajesAfectados, List<VueloPublico> vuelosAlternativos) {
	    List<String> resultado = new ArrayList<>();

	    for (Pasaje pasaje : pasajesAfectados.values()) {
	        String resultadoPasaje = reprogramarPasaje(pasaje, vuelosAlternativos);
	        resultado.add(resultadoPasaje);
	    }
	    return resultado;
	}

	// Funcion auxiliar para intentar reprogramar un pasaje en un vuelo alternativo
	private String reprogramarPasaje(Pasaje pasaje, List<VueloPublico> vuelosAlternativos) {
	    int dni = pasaje.getDNIcliente();
	    Cliente cliente = clientes.get(dni);
		for (VueloPublico vueloAlternativo : vuelosAlternativos) {
	        if (asignarAsientoEnVuelo(pasaje, vueloAlternativo)) {
	        	return cliente.toString() + " - " + vueloAlternativo.getCodigo();
	        }
	    }
	    // Si no se pudo reprogramar, devolver el formato de cancelacion
	    return cliente.toString() + " - " + "CANCELADO";
	}

	// Funcion auxiliar para intentar asignar un asiento en la misma seccion
	private boolean asignarAsientoEnVuelo(Pasaje pasaje, VueloPublico vuelo) {
	    String seccionPasaje = pasaje.getSeccion();
	    Map<Integer, String> asientos = vuelo.asientosDisponibles();

	    for (Map.Entry<Integer, String> entry : asientos.entrySet()) {
	        int numeroAsiento = entry.getKey();
	        String seccionAsiento = entry.getValue();

	        // Si encontramos un asiento en la misma seccion
	        if (seccionPasaje.equals(seccionAsiento)) {
	            // Asignamos el asiento al pasaje y actualizamos disponibilidad
	        	
	        	// esta parte se puede llegar a modificar !!!!
	            vuelo.asignarPasaje(numeroAsiento); 
	            return true;
	        }
	    }
	    return false; // no se encontro asiento en la misma o mejor seccion
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
}
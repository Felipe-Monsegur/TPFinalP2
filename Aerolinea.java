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
	 * es para la parte del enunciado que nos pide conocer el total recaudado dado
	 * un destino
	 * private Map<String, double> recaudacionTotal;
	 * String = nombre destino
	 * double = precio total
	 */

	// ejercicio 1
	public Aerolinea(String nombre, String CUIT) {
		this.nombre = nombre;
		this.CUIT = CUIT;
		this.clientes = new HashMap<>();
		this.aeropuertos = new HashMap<>();
		this.vuelos = new HashMap<>();
	}

	// ejercicio 2
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

	// ejercicio 3
	@Override
	public void registrarAeropuerto(String nombre, String pais, String provincia, String direccion) {
		if (aeropuertos.containsKey(nombre)) {
			throw new IllegalArgumentException("El aeropuerto ya está registrado.");
		}
		Aeropuerto aeropuerto = new Aeropuerto(nombre, pais, provincia, direccion);
		aeropuertos.put(nombre, aeropuerto);
	}

	// ejercicio 4
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
		Vuelo vuelo = new VueloNacional(codVuelo, origen, destino, fecha, tripulantes, valorRefrigerio, precios,
				cantAsientos);
		vuelos.put(codVuelo, vuelo);
		return codVuelo;
	}

	// ejercicio 5
	@Override
	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, int cantRefrigerios, double[] precios, int[] cantAsientos, String[] escalas) {
		if (!aeropuertos.containsKey(origen) || !aeropuertos.containsKey(destino)) {
			throw new IllegalArgumentException("El aeropuerto de origen o destino no esta registrado");
		}

		// Se podria verificar que los destinos de la escala sean validos y algunas
		// otras cosas

		// Generar codigo del vuelo
		int nroVuelo = vuelos.size() + 1;
		String codVuelo = nroVuelo + "-PUB";

		// Crear vuelo y añadirlo al registro
		Vuelo vuelo = new VueloInternacional(codVuelo, origen, destino, fecha, tripulantes, valorRefrigerio,
				cantRefrigerios, precios, cantAsientos, escalas);
		vuelos.put(codVuelo, vuelo);
		return codVuelo;
	}

	// ejercicio 6
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
		Vuelo vueloPrivado = new VueloPrivado(codVuelo, origen, destino, fecha, tripulantes, precio, dniComprador,
				acompaniantes);
		vuelos.put(codVuelo, vueloPrivado);
		return codVuelo;
	}

	// Creo que esta bien, si se modifica algo sobre el manejo de asientos, hay que
	// modificarlo
	// ejercicio 7
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

	// ejercicio 8
	// Es una idea de como se deberia hacer ESTA MAL
	@Override
	public int venderPasaje(int dni, String codVuelo, int nroAsiento, boolean aOcupar) {
		if (!clientes.containsKey(dni)) {
			throw new IllegalArgumentException("El cliente no está registrado en el sistema.");
		}
		if (!vuelos.containsKey(codVuelo)) {
			throw new IllegalArgumentException("El vuelo no existe.");
		}

		Vuelo vuelo = vuelos.get(codVuelo);

		// Es para ver si pasa el test
		int numPasaje = 0;

		if (vuelo instanceof VueloPublico) {
			VueloPublico vueloPublico = (VueloPublico) vuelo; // hacer cast a VueloPublico
			numPasaje = vueloPublico.venderPasaje(dni, nroAsiento, aOcupar);
		}

		return numPasaje;
	}

	// ejercicio 11
	@Override
	public List<String> consultarVuelosSimilares(String origen, String destino, String fecha) {
		List<String> vuelosSimilares = new ArrayList<>();

		// paso la fecha a localDate para manejarla con las funciones propias de la
		// clase
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

	// @Override
	// // Es provisorio, hay que modificarlo
	public void cancelarPasaje(int dni, String codVuelo, int nroAsiento) {
		if (!clientes.containsKey(dni)) {
			throw new IllegalArgumentException("El cliente no está registrado en el sistema.");
		}
		if (!vuelos.containsKey(codVuelo)) {
			throw new IllegalArgumentException("El vuelo no existe.");
		}
		Vuelo vuelo = vuelos.get(codVuelo);
		if (!(vuelo instanceof VueloPublico)) {
			throw new IllegalArgumentException(
					"El vuelo no es de tipo público y no permite cancelaciones de esta manera.");
		}

		VueloPublico vueloPublico = (VueloPublico) vuelo;
		vueloPublico.cancelarPasaje(dni);
	}

	// Ejercicio 12
	// Arreglar lo de liberar el asiento luego de cancelar el pasajr
	@Override
	public void cancelarPasaje(int dni, int codPasaje) {
		Cliente cliente = clientes.get(dni);
		if (cliente == null) {
			throw new IllegalArgumentException("El cliente con DNI " + dni + " no existe.");
		}

	
		boolean pasajeEncontrado = false;
		for (Vuelo vuelo : vuelos.values()) {
			Map<Integer, Pasaje> pasajes = vuelo.getPasajes();

			// Si el vuelo contiene el pasaje
			if (pasajes.containsKey(codPasaje)) {
				Pasaje pasaje = pasajes.get(codPasaje);

				// Verificar que el pasaje pertenece al cliente con el DNI dado
				if (pasaje.getDNIcliente() != dni) {
					throw new IllegalArgumentException("El pasaje no pertenece al cliente" + dni);
				}

				
				pasajes.remove(codPasaje);
				//FALTA liberar el asiento de ese pasaje

				pasajeEncontrado = true;
				System.out.println(
						"Pasaje " + codPasaje + " cancelado por el cliente" + dni);
				break;
			}
		}

		if (!pasajeEncontrado) {
			throw new IllegalArgumentException(
					"No se encontró el pasaje " + codPasaje + " para el cliente " + dni );
		}
	}

	// Ejercicio 13
	@Override
	public List<String> cancelarVuelo(String codVuelo) {
		if (!vuelos.containsKey(codVuelo)) {
			throw new IllegalArgumentException("El vuelo no existe");
		}

		List<String> resultados = new ArrayList<>();
		VueloPublico vueloCancelado = (VueloPublico) vuelos.get(codVuelo);
		Map<Integer, Pasaje> pasajesAfectados = vueloCancelado.getPasajes();

		List<VueloPublico> vuelosAlternativos = obtenerVuelosAlternativos(codVuelo, vueloCancelado.getDestino());

		for (Pasaje pasaje : pasajesAfectados.values()) {
			int dniPasajero = pasaje.getDNIcliente();
			Cliente cliente = clientes.get(dniPasajero);
			String resultado = reprogramarPasajero(pasaje, vuelosAlternativos, cliente);
			resultados.add(resultado);
		}

		vuelos.remove(codVuelo); // Eliminamos el vuelo cancelado.
		return resultados;
	}

	// Método para obtener vuelos alternativos con el mismo destino
	private List<VueloPublico> obtenerVuelosAlternativos(String codVuelo, String destinoCancelado) {
		List<VueloPublico> vuelosAlternativos = new ArrayList<>();
		for (Vuelo vuelo : vuelos.values()) {
			if (!vuelo.getCodigo().equals(codVuelo) && vuelo.getDestino().equals(destinoCancelado)
					&& vuelo instanceof VueloPublico) {
				vuelosAlternativos.add((VueloPublico) vuelo);
			}
		}
		return vuelosAlternativos;
	}

	// Método para intentar reprogramar un pasajero en vuelos alternativos
	private String reprogramarPasajero(Pasaje pasaje, List<VueloPublico> vuelosAlternativos, Cliente cliente) {
		int dniPasajero = pasaje.getDNIcliente();
		boolean reprogramado = false;

		for (VueloPublico vueloAlt : vuelosAlternativos) {
			for (Map.Entry<Integer, String> asiento : vueloAlt.asientosDisponibles().entrySet()) {
				if (vueloAlt.mismaOmejorSeccion(asiento.getKey(), convertirSeccionANumero(pasaje.getSeccion()))) {
					try {
						vueloAlt.venderPasaje(dniPasajero, asiento.getKey(), true);
						return generarRegistro(dniPasajero, cliente, vueloAlt.getCodigo());
					} catch (Exception e) {
						continue; // intenta el proximo asiento
					}
				}
			}
		}

		return generarRegistroCancelado(dniPasajero, cliente);
	}

	// metodos para generar registros de reprogramacion o cancelacion
	private String generarRegistro(int dniPasajero, Cliente cliente, String codigoVuelo) {
		return cliente.toString() + codigoVuelo;
	}
	private String generarRegistroCancelado(int dniPasajero, Cliente cliente) {
		return cliente.toString() + " - CANCELADO";
	}

	//metodo para convertir la seccion a numero
	private int convertirSeccionANumero(String seccion) {
		switch (seccion.toLowerCase()) {
			case "turista":
				return 0;
			case "ejecutivo":
				return 1;
			case "primera clase":
				return 2;
			default:
				throw new IllegalArgumentException("Sección desconocida: " + seccion);
		}
	}

	// Ejercicio 14
	@Override
	public double totalRecaudado(String destino) {
		double total = 0;
		for (Vuelo vuelo : vuelos.values()) {
			if (vuelo instanceof VueloPrivado) {
				VueloPrivado vueloPrivado = (VueloPrivado) vuelo;
				total += vueloPrivado.getValor();
			} else if (vuelo instanceof VueloPublico) {
				VueloPublico vueloPublico = (VueloPublico) vuelo;
				total += vueloPublico.recaudacion();
			}
		}
		return total;
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
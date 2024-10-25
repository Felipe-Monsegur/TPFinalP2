import java.util.List;
import java.util.Map;

public class BondiJet implements IAerolinea{
    private String nombre;
    private Integer CUIT;
    private Map<Integer, Cliente> clientes;
    private Map<String, Aeropuerto> aeropuertos;
    private Map<String, Vuelo> vuelos;
    private double recaudacionTotal;

    // Constructor
    public BondiJet(String nombre, Integer CUIT) {
        this.nombre = nombre;
        this.CUIT = CUIT;
        this.clientes = new Map<>();
        this.aeropuertos = new Map<>();
        this.vuelos = new Map<>();
        this.recaudacionTotal = 0.0;
    }


    // Registrar un cliente
    public boolean registrarCliente(String nombre, Integer telefono, Integer dni) { 
        if (!clientes.containsKey(dni)) {
            clientes.put(dni, new Cliente(nombre, telefono, dni));
            return true;
        }
        return false;
    }

   // Registrar un aeropuerto
    public boolean registrarAeropuerto(String nomAeropuerto, String ubicacion, String direccion) {  
        if (!aeropuertos.containsKey(nomAeropuerto)) {
            aeropuertos.put(nomAeropuerto, new Aeropuerto(nomAeropuerto, ubicacion, direccion));
            return true;
        }
        return false;
    }

     // Crear vuelo público nacional
     public void crearVueloPublicoNacional(int codigo, String destino, int asientos, int capSec1, int capSec2, double precioSec1, double precioSec2) {
     }

     // Crear vuelo público internacional
    public void crearVueloPublicoInternacional(int codigo, String destino, int asientos, int capSec1, int capSec2, int capSec3, double precioSec1, double precioSec2, double precioSec3, boolean escala) {
    }

     // Registrar vuelo privado
     public boolean registrarVueloPrivado(int codigo, String destino, int asientos, Integer DNIcomprador, List<Integer> DNIpasajeros) {
        if (!clientes.containsKey(DNIcomprador) || !clientes.keySet().containsAll(DNIpasajeros)) {
            return false;
        }
        if (!aeropuertos.containsKey(destino) || !aeropuertos.get(destino).esDestinoNacional()) {
            return false;
        }
        vuelos.put(codigo, new VueloPrivado(codigo, destino, asientos, DNIcomprador, DNIpasajeros));
        return true;
    }

     // Consultar asientos disponibles
     public int consultarAsientosDisponibles(int codigoVuelo) {
     }

     // Vender pasaje para vuelo nacional
    public boolean venderPasajeVueloNacional(Integer DNI, Integer codigo, Integer numAsiento) {
    }

    // Vender pasaje para vuelo Internacionjal
    public boolean venderPasajeVueloInternacional(Integer DNI, Integer codigo, Integer numAsiento) {
    }

    // Vender vuelo Privado
    public boolean venderVueloPrivado(Integer DNIcomprador,List<Integer>DNIpasajeros){
    }

    //Consultar vuelos segun fehca y destino
    public List<Vuelo> consultarVuelos(String fecha, String origen, String destino) {
        List<Vuelo> vuelosSimilares = new List<>();
        for (Vuelo vuelo : vuelos.values()) {
            if (vuelo.getFecha().equals(fecha) && vuelo.getOrigen().equals(origen) && vuelo.getDestino().equals(destino)) {
                vuelosSimilares.add(vuelo);
            }
        }
        return vuelosSimilares;
    }

     // Cancelar pasaje
     public boolean cancelarPasaje(Integer DNIpasajero, int codigoVuelo, int nroAsiento) {
     }

      // Calcular total recaudado en pasajes a un destino específico
    public double totalRecaudadoPasajes(String destino) {
    }
}
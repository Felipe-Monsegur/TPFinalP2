import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


public class Aerolinea implements IAerolinea{
    private String nombre;
    private Integer CUIT;
    private Map<Integer, Cliente> clientes;
    private Map<String, Aeropuerto> aeropuertos;
    private Map<String, Vuelo> vuelos;
    private double recaudacionTotal;

    // Constructor
    public Aerolinea(String nombre, Integer CUIT) {
        this.nombre = nombre;
        this.CUIT = CUIT;
        this.clientes =new HashMap<>();
        this.aeropuertos = new HashMap<>();
        this.vuelos = new HashMap<>();
    }


    //PAULA puse void y no boolean como en nuestra especificacion, por q en la clase IAerolinea dice void //O(1)
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

    //PAULA puse void y no boolean como en nuestra especificacion, por q en la clase IAerolinea dice void
    @Override
    public void registrarAeropuerto(String nombre, String pais, String provincia, String direccion) {
        if (aeropuertos.containsKey(nombre)) {
            throw new IllegalArgumentException("El aeropuerto ya está registrado.");
        }
        Aeropuerto aeropuerto = new Aeropuerto(nombre, pais, provincia, direccion);
        aeropuertos.put(nombre, aeropuerto);
    }



    @Override
    public String registrarVueloPublicoNacional(String origen, String destino, String fecha, int tripulantes, double valorRefrigerio,double[] precios, int[] cantAsientos) {
        if (!aeropuertos.containsKey(origen) || !aeropuertos.containsKey(destino)) {
            throw new IllegalArgumentException("El aeropuerto de origen o destino no esta registrado");
        }
        if (!aeropuertos.get(origen).getPais().equals("Argentina") || !aeropuertos.get(destino).getPais().equals("Argentina")) {
            throw new IllegalArgumentException("Los vuelos nacionales deben tener origen y destino en Argentina.");
        }

        int contVuelosPublicos;
        // Generar codigo del vuelo
        contVuelosPublicos++;
        String codVuelo = contVuelosPublicos + "-PUB";

        // Crear vuelo y añadirlo al registro
        Vuelo vuelo = new Vuelo(codVuelo, origen, destino, fecha, tripulantes, valorRefrigerio, precios, cantAsientos);
        vuelos.put(codVuelo, vuelo);
        return codVuelo;
    }

    
    @Override
     public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes, double valorRefrigerio, int cantRefrigerios, double[] precios,  int[] cantAsientos,  String[] escalas){
    }


    @Override
    public String VenderVueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio, int dniComprador, int[] acompaniantes) {
        // Validar aeropuertos para vuelos privados
        if (!aeropuertos.containsKey(origen) || !aeropuertos.containsKey(destino)) {
            throw new IllegalArgumentException("El aeropuerto de origen o destino no está registrado.");
        }

        // Generar código de vuelo privado
        contVuelosPrivados++;
        String codVuelo = contVuelosPrivados + "-PRI";

        // Crear vuelo privado y añadirlo al registro
        Vuelo vueloPrivado = new VueloPrivado(codVuelo, origen, destino, fecha, tripulantes, precio, dniComprador, acompaniantes);
        vuelos.put(codVuelo, vueloPrivado);
        return codVuelo;
    }

     // Consultar asientos disponibles
     public int consultarAsientosDisponibles(int codVuelo) {
     }

     // Vender pasaje para vuelo Nacional e Internacional
    public boolean /*int*/ venderPasaje(int dni, String codVuelo, int nroAsiento, boolean aOcupar) {
    }


    // Vender vuelo Privado
    public boolean venderVueloPrivado(Integer DNIcomprador,List<Integer>DNIpasajeros){
    }

    
    @Override
    //Consultar vuelos similares
    public List<Vuelo> consultarVuelos(String fecha, String origen, String destino) {
        List<Vuelo> vuelosSimilares = new List<>();
        for (Vuelo vuelo : vuelos.values()) {
            if (vuelo.getFecha().equals(fecha) && vuelo.getOrigen().equals(origen) && vuelo.getDestino().equals(destino)) {
                vuelosSimilares.add(vuelo);
            }
        }
        return vuelosSimilares;
    }


    @Override
     // Cancelar pasaje //PAULA nosotros pusimos booolean pero pide q sea void
     public void /*boolean */ void cancelarPasaje(int dni, String codVuelo, int nroAsiento) {
     }

     @Override
     public  void /*boolean */ cancelarPasaje(int dni, int codPasaje){
     }
     
     @Override
     List<String> cancelarVuelo(String codVuelo){

     }
     @Override
      // Calcular total recaudado en pasajes a un destino específico
    public double totalRecaudado(String destino) {
    }

    @Override
    public String detalleDeVuelo(String codVuelo){

    }
}
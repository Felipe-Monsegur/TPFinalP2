public abstract class Vuelo {
    private int codigo;
    private String destino;
    private String origen;
    private int totalTripulantes;
    private int cantidadAsientos;
    private int asientosDisponibles;
    private String aeropuertoSalida;
    private String aeropuertoLlegada;
    private int horaSalida;
    private int horaLLegada;
    private Fecha fechaSalida;
    
     // Constructor
     public Vuelo(int codigo, String origen, String destino, int totalTripulantes, int cantidadAsientos,
     String aeropuertoSalida, String aeropuertoLlegada, int horaSalida, int horaLlegada, Fecha fechaSalida) {
        this.codigo = codigo;
        this.origen = origen;
        this.destino = destino;
        this.totalTripulantes = totalTripulantes;
        this.cantidadAsientos = cantidadAsientos;
        this.asientosDisponibles = cantidadAsientos; 
        this.aeropuertoSalida = aeropuertoSalida;
        this.aeropuertoLlegada = aeropuertoLlegada;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;

        this.fechaSalida = fechaSalida;
        }


    // Getters y Setters
    public int getCodigo() {
        return codigo;
    }
    public String getDestino() {
        return destino;
    }
    public String getOrigen() {
        return origen;
    }
    public int getTotalTripulantes() {
        return totalTripulantes;
    }
    public int getCantidadAsientos() {
        return cantidadAsientos;
    }
    public int getAsientosDisponibles() {
        return asientosDisponibles;
    }
    public String getAeropuertoSalida() {
        return aeropuertoSalida;
    }
    public String getAeropuertoLlegada() {
        return aeropuertoLlegada;
    }
    public int getHoraSalida() {
        return horaSalida;
    }
    public int getHoraLlegada() {
        return horaLlegada;
    }
    public Fecha getFechaSalida() {
        return fechaSalida;
    }



    ////No se si hace falta si ya tenemos los getters
    // Devuelve el destino del vuelo
    public String devolverDestino() {
        return destino;
    }

    // Devuelve la cantidad de asientos disponibles del vuelo
    public int cantidadAsientosDisponibles() {
        return asientosDisponibles;
    }

    // Devuelve la fecha del vuelo
    public Fecha devolverFecha() {
        return fechaSalida;
    }

    // Devuelve el nombre del aeropuerto de origen
    public String devolverAeropuertoOrigen() {
        return aeropuertoSalida;
    }

    // Devuelve el nombre del aeropuerto de destino
    public String devolverAeropuertoDestino() {
        return aeropuertoLlegada;
    }


    //Actualizar los asientos disponibles
    public void actualizarAsientosDisponibles(int actualiz) {
        asientosDisponibles += actualiz;
    }

    // Método abstracto para vender un pasaje
    public abstract boolean venderPasaje(int numAsiento, int dniPasajero);
    // Método abstracto para cancelar un pasaje
    public abstract boolean cancelarPasaje(int dniPasajero, int numAsiento);
    // Método abstracto para calcular la recaudación total del vuelo
    public abstract double calcularRecaudacion();
}

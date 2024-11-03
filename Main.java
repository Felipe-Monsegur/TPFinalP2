import java.util.List;
import java.util.Map;
public class Main {

	public static void main(String[] args) {
        IAerolinea aerolinea = new Aerolinea("BondiJet", "30-12345678-9");
        
        
        
        // Registrar aeropuertos nacionales (Argentina)
        aerolinea.registrarAeropuerto("Aeroparque", "Argentina", "Provincia de Buenos Aires", "Av. Costanera Rafael Obligado");
        aerolinea.registrarAeropuerto("Ezeiza", "Argentina", "Provincia de Buenos Aires", "Autopista Riccheri km 33.5");
        aerolinea.registrarAeropuerto("Bariloche", "Argentina", "Provincia de Río Negro", "Ruta Nacional 237");
        // Registrar aeropuertos internacionales (Europa y América)
        aerolinea.registrarAeropuerto("Charles de Gaulle", "Francia", "Departamento de Val-d'Oise", "95700 Roissy-en-France");
        aerolinea.registrarAeropuerto("JFK", "Estados Unidos", "Estado de Nueva York", "Queens, NY 11430");
		aerolinea.registrarAeropuerto("Guarulhos", "Brasil", "São Paulo", "Rod. Hélio Smidt, s/n - Cumbica, Guarulhos");
        // Registrar clientes
        aerolinea.registrarCliente(12345678, "Juan Perez", "011-1234-5678");
        aerolinea.registrarCliente(87654321, "Ana Lopez", "011-8765-4321");
        
        aerolinea.registrarCliente(46343328, "Paula Moragues", "011-4402-4971");
        
 
        // Registrar un vuelo internacional con escalas
        double[] preciosInternacional = {20000.0, 40000.0, 60000.0};
        int[] cantAsientosInternacional = {200, 50, 10};
        String[] escalas = {"Guarulhos", "JFK"};
        String codVueloInternacional = aerolinea.registrarVueloPublicoInternacional("Ezeiza", "Charles de Gaulle", "20/11/2024", 12, 6000, 3, preciosInternacional, cantAsientosInternacional, escalas);
        double[] preciosInternacional2 = {20000.0, 40000.0, 60000.0};
        int[] cantAsientosInternacional2 = {2, 0, 0};
        String[] escalas2 = {"Guarulhos", "JFK"};
        String codVueloInternacional2 = aerolinea.registrarVueloPublicoInternacional("Ezeiza", "Charles de Gaulle", "26/11/2024", 12, 6000, 3, preciosInternacional, cantAsientosInternacional2, escalas);
        
        
        // Vender pasajes
        int codPasaje1 = aerolinea.venderPasaje(12345678, codVueloInternacional, 1, true);
        int codPasaje2 = aerolinea.venderPasaje(87654321, codVueloInternacional, 2, true);
        int codPasaje3 = aerolinea.venderPasaje(46343328, codVueloInternacional, 3, true);
        
        Map<Integer, String> asientosDispo = aerolinea.asientosDisponibles(codVueloInternacional2);
        System.out.println(asientosDispo);
        
        Map<Integer, String> asientosDispo3 = aerolinea.asientosDisponibles(codVueloInternacional);
        System.out.println(asientosDispo3);
        
        List<String> vueloPasajes = aerolinea.cancelarVuelo(codVueloInternacional);
        System.out.println(vueloPasajes);	
        
	}


}

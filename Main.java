import java.util.List;
import java.util.Map;
public class Main {

	//es para probar lo de cancelar vuelo no ma
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
		aerolinea.registrarAeropuerto("Barajas", "España", "Madrid", "28042 Madrid");

		double[] precios = { 15000.0, 30000.0, 50000.0 };
		int[] cantAsientos = { 100, 30, 10 };
		String[] escalas = { "JFK", "Charles de Gaulle" };
		String codVuelo = aerolinea.registrarVueloPublicoInternacional("Ezeiza", "Barajas", "15/12/2024", 8, 2000, 3,
				precios, cantAsientos, escalas);
		
	    for (int i = 1; i < 141; i++) {
	        int dni = 10000000 + i; // Simplemente como ejemplo
	        aerolinea.registrarCliente(dni, "Pasajero " + (i + 1), "011-1234-" + (5670 + i) ); // Registrar acompañante
	        aerolinea.venderPasaje(dni, codVuelo, i, false);
	    }
	    
	    double total = aerolinea.totalRecaudado("Barajas");
	    System.out.println(total);
        
        

	}

}

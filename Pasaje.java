public class Pasaje {
	private int codigo;
	private int nroAsiento;
	private String seccion;
	private int DNIcliente;

	public Pasaje(int codigo, int nroAsiento, String seccion, int DNIcliente) {
		this.codigo = codigo;
		this.nroAsiento = nroAsiento;
		this.seccion = seccion;
		this.DNIcliente = DNIcliente;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getSeccion() {
		return seccion;
	}

	public int getDNIcliente() {
		return DNIcliente;
	}
	
	public int getNroAsiento() {
		return nroAsiento;
	}
	
	@Override
	public String toString() {
		return "[Código: " + getCodigo() + " / Número de asiento: " + getNroAsiento() + " / Sección: " + getSeccion() + 
				" / DNI del cliente: " + getDNIcliente() + " ] ";
	}

}
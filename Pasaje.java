public class Pasaje {
	private int codigo;
	private int nroAsiento;
	private String seccion;
	private int DNIcliente;
	private double precioTotal;

	public Pasaje(int codigo, int nroAsiento, String seccion, double precioTotal, int DNIcliente) {
		this.codigo = codigo;
		this.nroAsiento = nroAsiento;
		this.seccion = seccion;
		this.precioTotal = precioTotal;
		this.DNIcliente = DNIcliente;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getSeccion() {
		return seccion;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public int getDNIcliente() {
		return DNIcliente;
	}
	
	public int getNroAsiento() {
		return nroAsiento;
	}
	
	@Override
	public String toString() {
		return getCodigo() + " - " + getNroAsiento() + " - " + getSeccion() + " - " + getDNIcliente() + " - "
				+ getPrecioTotal();
	}

}
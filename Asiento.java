public class Asiento {
	private Integer nroAsiento;
	private double precioBase;
	private String seccion;
	private boolean ocupado;
	private boolean vendido;

	public Asiento(Integer nroAsiento, double precioBase, String seccion) {
		this.nroAsiento = nroAsiento;
		this.precioBase = precioBase;
		this.seccion = seccion;
		this.vendido = false;
		this.ocupado = false;
	}

	public boolean getOcupado() {
		return ocupado;
	}

	public Integer getNroAsiento() {
		return nroAsiento;
	}

	public String getSeccion() {
		return seccion;
	}

	public Double getPrecioBase() {
		return precioBase;
	}

	public boolean getVendido() {
		return vendido;
	}
	
	public void liberar() {
		this.vendido = false;
		this.ocupado = false;
	}

	public void vender() {
		this.vendido = true;
	}

	public void ocupar() {
		this.ocupado = true;
	}
	
	public String toString() {
		return getNroAsiento() + " - " + getPrecioBase() + " - " + getSeccion();
	}

}

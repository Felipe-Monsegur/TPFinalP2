public class Asiento {
	private Integer nroAsiento;
	private double precio;
	private String seccion;
	private boolean ocupado;
	private boolean vendido;

	public Asiento(Integer nroAsiento, double precio, String seccion) {
		this.nroAsiento = nroAsiento;
		this.precio = precio;
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

	public Double getPrecio() {
		return precio;
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

}

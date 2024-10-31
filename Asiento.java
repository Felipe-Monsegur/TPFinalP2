
public class Asiento {
	private Integer nroAsiento;
	private double precio;
	private Integer seccion;
	private boolean ocupado;
	private boolean vendido;

	public Asiento(Integer nroAsiento, double precio, Integer seccion) {
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
	public Integer getSeccion() {
		return seccion;
	}
	
	public boolean getVendido() {
		return vendido;
	}
	
	//provisorio
	public void liberar() {
		this.vendido = false;
	}
	public void vender() {
		this.vendido = true;
	}
	
}

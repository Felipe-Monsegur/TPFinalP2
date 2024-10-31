
public class Asiento {
	private Integer nroAsiento;
	private double precio;
	private Integer seccion;
	private boolean ocupado;

	public Asiento(Integer nroAsiento, double precio, Integer seccion) {
		this.nroAsiento = nroAsiento;
		this.precio = precio;
		this.seccion = seccion;
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
	
	//provisorio
	public void desocupar() {
		this.ocupado = false;
	}
}

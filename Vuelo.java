public abstract class Vuelo {
	private String codigo;
	private String origen;
	private String destino;
	private String fecha;
	private int tripulantes;

	public Vuelo(String codigo, String origen, String destino, String fecha, int tripulates) {
		this.codigo = codigo;
		this.origen = origen;
		this.destino = destino;
		this.fecha = fecha;
		this.tripulantes = tripulantes;
	}

	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}

	public String getFecha() {
		return fecha;
	}

	public String getCodigo() {
		return codigo;
	}

	public String toString() {
		return getCodigo() + " - " + getOrigen() + " - " + getDestino() + " - " + getFecha();
	}

}

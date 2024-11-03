public class Cliente {
	private int dni;
	private String nombre;
	private String telefono;

	public Cliente(int dni, String nombre, String telefono) {
		this.dni = dni;
		this.nombre = nombre;
		this.telefono = telefono;
	}

	public int getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	@Override
	public String toString() {
		return getDni() + " - " + getNombre() + " - " + getTelefono();
	}
}

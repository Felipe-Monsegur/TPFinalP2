
public class Pasaje {
    private int codigo;
    private String seccion;
    private double precio;
    private int DNIcliente;

	public Pasaje(int codigo, String seccion, double precio, int DNIcliente){
		this.codigo =codigo;
		this.seccion=seccion;
		this.precio=precio;
		this.DNIcliente= DNIcliente;
	}

	
    public int getCodigo() {
        return codigo;
    }

    public String getSeccion() {
        return seccion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getDNIcliente() {
        return DNIcliente;
    }

    @Override
	public String toString() {
		return getCodigo()+" - "+getDNIcliente()+" - "+getSeccion();
	}

}
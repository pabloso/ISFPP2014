package hospital;

public class Doctor implements Runnable{
	private int tipo; // 0: F mn, 1: NF
	private int tAtencion;
	private int tLibre;

	public Doctor(int _tipo) {
		tipo = _tipo;
	}

	public void run(){
		
	}
	public void atender(int _tAtencion, int _tLibre) {
		tAtencion = _tAtencion;
		tLibre = _tLibre;
	}

	public boolean estaOcupado(int t) {
		return t < tLibre;
	}

	/****************/
	public int getTipo() {
		return tipo;
	}

	public void setTipo(int _tipo) {
		this.tipo = _tipo;
	}

	public int getAtencion() {
		return tAtencion;
	}

	public void setAtencion(int _tAtencion) {
		this.tAtencion = _tAtencion;
	}

	public int gettLibre() {
		return tLibre;
	}

	public void settLibre(int _tLibre) {
		this.tLibre = _tLibre;
	}
}

package hospital;

public class Paciente {
	private int tLlegada;
	private int tAtencion;
	private int tipoServicio; // Alta =0 , Media =1 , Baja =2

	public Paciente(int _tLlegada, int _tipoServicio) {
		tLlegada = _tLlegada;
		tipoServicio = _tipoServicio;
	}

	public void esAtendido(int t) {
		tAtencion = t;
	}

	public int getTLlegada() {
		return tLlegada;
	}

	public int getTipoServicio() {
		return tipoServicio;
	}

	public int getEspera() {
		return tAtencion - tLlegada;
	}
}

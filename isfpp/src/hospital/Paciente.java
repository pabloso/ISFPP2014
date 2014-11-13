package hospital;

public class Paciente implements Runnable{
	private int tLlegada;
	private int tAtencion;
	private int tipoServicio; // Alta =0 , Media =1 , Baja =2

	
	public Paciente(int _tLlegada, int _tipoServicio) {
		tLlegada = _tLlegada;
		tipoServicio = _tipoServicio;
	}

	public void run(){
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
	public int compareTo(Paciente p1){
		if(this.tipoServicio != p1.tipoServicio)
			return this.tipoServicio - p1.tipoServicio;
		return this.tLlegada - p1.tLlegada;
	}
}

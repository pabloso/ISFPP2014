package hospital;

public class Paciente{
	private int tLlegada;
	private int tAtencion;
	private int tipoPrioridad; // Alta =0 , Media =1 , Baja =2

	
	public Paciente(int _tLlegada, int _tipoPrioridad) {
		tLlegada = _tLlegada;
		tipoPrioridad = _tipoPrioridad;
	}

	public void esAtendido(int t) {
		tAtencion = t;
	}

	public int getTLlegada() {
		return tLlegada;
	}

	public int getTipoServicio() {
		return tipoPrioridad;
	}

	public int getEspera() {
		return tAtencion - tLlegada;
	}
	public int compareTo(Paciente p1){
		if(this.tipoPrioridad != p1.tipoPrioridad)
			return this.tipoPrioridad - p1.tipoPrioridad;
		return this.tLlegada - p1.tLlegada;
	}
}

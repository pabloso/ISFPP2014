package hospital;

public class Paciente implements Comparable{
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
	@Override
	public int compareTo(Object p1){
		if(this.tipoPrioridad != ((Paciente) p1).getTipoServicio())
			return this.tipoPrioridad - ((Paciente) p1).tipoPrioridad;
		return this.tLlegada - ((Paciente) p1).tLlegada;
	}
}

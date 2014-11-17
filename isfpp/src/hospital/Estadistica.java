package hospital;

public class Estadistica {

	private int tiempoTotalDeEspera;
	private int numPacientesAtendidos;
	private int numPacientesMuertos;
	private int numPeriodos;
	private int pacientesAtenidosAlta;
	private int pacientesAtenidosMedia;
	private int pacientesAtenidosBaja;

	public Estadistica(	int tiempoTotalDeEspera,int numPacientesAtendidos,int numPacientesMuertos,
						int numPeriodos, int pacientesAtenidosAlta,	int pacientesAtenidosMedia,
						int pacientesAtenidosBaja){
		
		this.setNumPacientesAtendidos(numPacientesAtendidos);
		this.setNumPacientesMuertos(numPacientesMuertos);
		this.setPacientesAtendidosAlta(pacientesAtenidosAlta);
		this.setPacientesAtendidosMedia(pacientesAtenidosMedia);
		this.setPacientesAtendidosBaja(pacientesAtenidosBaja);
		this.setNumPeriodos(numPeriodos);
	}

	public synchronized int getTiempoTotalDeEspera() {
		return tiempoTotalDeEspera;
	}

	public synchronized void setTiempoTotalDeEspera(int tiempoTotalDeEspera) {
		this.tiempoTotalDeEspera = tiempoTotalDeEspera;
	}

	public synchronized int getNumPacientesAtendidos() {
		return numPacientesAtendidos;
	}

	public synchronized void setNumPacientesAtendidos(int numPacientesAtendidos) {
		this.numPacientesAtendidos = numPacientesAtendidos;
	}

	public synchronized void incrementarNumPacientesAtendidos() {
		this.numPacientesAtendidos++;
	}
	public synchronized int getNumPacientesMuertos() {
		return numPacientesMuertos;
	}

	public synchronized void setNumPacientesMuertos(int numPacientesMuertos) {
		this.numPacientesMuertos = numPacientesMuertos;
	}

	public synchronized void incrementarNumPacientesMuertos() {
		this.numPacientesMuertos++;
	}
	
	public synchronized int getNumPeriodos() {
		return numPeriodos;
	}

	public synchronized void setNumPeriodos(int numPeriodos) {
		this.numPeriodos = numPeriodos;
	}

	public synchronized int getPacientesAtendidosAlta() {
		return pacientesAtenidosAlta;
	}

	public synchronized void setPacientesAtendidosAlta(int pacientesAtenidosAlta) {
		this.pacientesAtenidosAlta = pacientesAtenidosAlta;
	}
	
	public synchronized void incrementarPacientesAtendidosAlta() {
		this.pacientesAtenidosAlta++;
	}
	
	public synchronized int getPacientesAtendidosMedia() {
		return pacientesAtenidosMedia;
	}

	public synchronized void setPacientesAtendidosMedia(int pacientesAtenidosMedia) {
		this.pacientesAtenidosMedia = pacientesAtenidosMedia;
	}

	public synchronized void incrementarPacientesAtendidosMedia() {
		this.pacientesAtenidosMedia++;
	}
	
	public synchronized int getPacientesAtendidosBaja() {
		return pacientesAtenidosBaja;
	}

	public synchronized void setPacientesAtendidosBaja(int pacientesAtenidosBaja) {
		this.pacientesAtenidosBaja = pacientesAtenidosBaja;
	}
	
	public synchronized void incrementarPacientesAtendidosBaja() {
		this.pacientesAtenidosBaja++;
	}
	public double esperaPromedio() {
		/*
		 * Cuando un paciente es atendido avisa cuanto demor[o en la cola
		 * incrementa la cantidad de pacientes atendidos
		 * suma el tiempo
		 * sumas de las demoras / cantidad de pacientes
		 */
		return (double) getTiempoTotalDeEspera() / getNumPacientesAtendidos();
	}
}

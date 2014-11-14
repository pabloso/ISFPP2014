package hospital;

public class PeriodoDia {
	private String horaInicio;
	private String horaTermino;
	private int tMin;
	private int tMax;
	private double[] probs;
	private int numPacientes;
	private int[] numPacientesTipo;
	private int numPacientesTrauma;

	public PeriodoDia(String _horaInicio, String _horaTermino, double _probAlta, double _probMedia, double _probBaja) {
		horaInicio = _horaInicio;
		horaTermino = _horaTermino;
		String[] inicio = horaInicio.split(":");
		String[] termino = horaTermino.split(":");
		tMin = horaAMinuto(Integer.parseInt(inicio[0]), Integer.parseInt(inicio[1]));
		tMax = horaAMinuto(Integer.parseInt(termino[0]), Integer.parseInt(termino[1]));
		probs = new double[3];
		probs[0] = _probAlta;
		probs[1] = _probMedia;
		probs[2] = _probBaja;
		numPacientesTipo = new int[3];
		numPacientes = 0;
		numPacientesTrauma = 0;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public String getHoraTermino() {
		return horaTermino;
	}

	private int horaAMinuto(int hora, int minuto) {
		return minuto + 60 * hora;
	}

	public boolean estaEnPeriodo(int t) {
		return (t >= tMin && t <= tMax);
	}

	public double[] getProbs() {
		return probs;
	}

	/*public void agregarPacientesTipo(int tipo) {
		numPacientesTipo[tipo]++;
		numPacientes++;
	}

	/*public void agregarPacientesTrauma() {
		numPacientesTrauma++;
	}*/

	public int getTotalPacientes() {
		return numPacientes;
	}

	public int getPacientesTipo(int tipo) {
		return numPacientesTipo[tipo];
	}

	/*public int getPacientesTrauma() {
		return numPacientesTrauma;
	}*/
}

package hospital;

import java.util.PriorityQueue;

public class Cola extends PriorityQueue<Paciente> implements Buffer {

//	private final int prioridad;
	private int cantidadTotalPacientes;

	public Cola() {
		super();
//		prioridad = 3;
		cantidadTotalPacientes = 0;
	}

	public PriorityQueue<Paciente> getPacientes(int tipo) {
		PriorityQueue<Paciente> p = new PriorityQueue<Paciente>();
		for (Paciente pc : this) {
			if (pc.getTipoServicio() == tipo)
				p.offer(pc);
		}
		return p;
	}

	public boolean agregarPaciente(Paciente paciente) {
		this.offer(paciente);
		this.cantidadTotalPacientes++;
		return true;
	}

	public Paciente sacarPaciente() {
		return this.poll();
	}

	/*
	 * public Paciente sacarPacienteTipo(int tipo) {
	 * Paciente ptipo;
	 * for (Paciente p : this)
	 * if (p.getTipoServicio() == tipo) {
	 * ptipo = new Paciente(p.getTLlegada(), p.getTipoServicio());
	 * this.remove(p);
	 * return ptipo;
	 * }
	 * return null;
	 * }
	 */

	public boolean hayPacientes() {
		return !this.isEmpty();
	}

	/*
	 * public boolean hayPacientesTipo(int tipo) {
	 * for (Paciente p : this)
	 * if (p.getTipoServicio() == tipo) {
	 * return true;
	 * }
	 * return false;
	 * }
	 */

	public int numPacientesEnCola() {
		return this.size();
	}

	/*
	 * public int numPacientesEnColaTipo(int tipo) {
	 * int i = 0;
	 * for (Paciente p : this) {
	 * if (p.getTipoServicio() == tipo)
	 * i++;
	 * }
	 * return i;
	 * }
	 */
	@Override
	public synchronized void set(Paciente paciente) throws InterruptedException {
		this.add(paciente);
		System.out.println("Llega " + paciente);
		notifyAll();
	}

	@Override
	public synchronized Paciente get(Doctor doctor) throws InterruptedException {
		Paciente paciente = null;

		while (this.isEmpty() && doctor.isAtiende()) {
			System.out.println("Doctor " + doctor.getNombre() + " espera Pacientes");
			wait();
		}

		if (!this.isEmpty())
			paciente = this.poll();

		notifyAll();

		return paciente;
	}

	public int numTotalPacientes() {
		//return this.size();
		return this.cantidadTotalPacientes;
	}

	public double esperaPromedio(/* recibe un hora */) {
		double prom = 0;
		/*
		 * Cuando un paciente es atendido avisa cuanto demor[o en la cola
		 * incrementa la cantidad de pacientes atendidos
		 * suma el tiempo
		 * sumas de las demoras / cantidad de pacientes
		 */
		return prom;
	}
}

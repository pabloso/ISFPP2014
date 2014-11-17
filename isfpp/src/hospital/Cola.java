package hospital;

import java.util.PriorityQueue;

public class Cola extends PriorityQueue<Paciente> implements Buffer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Cola() {
		super();
	}

	public PriorityQueue<Paciente> getPacientes(int tipo) {
		PriorityQueue<Paciente> p = new PriorityQueue<Paciente>();
		for (Paciente pc : this) {
			if (pc.getTipoServicio() == tipo)
				p.offer(pc);
		}
		return p;
	}

	public boolean hayPacientes() {
		return !this.isEmpty();
	}

	public int numPacientesEnCola() {
		return this.size();
	}

	@Override
	public synchronized void set(Paciente paciente) throws InterruptedException {
		this.add(paciente);
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

	public synchronized boolean isNotEmpty() {
		return !isEmpty();
	}
}

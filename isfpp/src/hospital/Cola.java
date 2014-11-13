package hospital;

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

import javax.crypto.spec.OAEPParameterSpec;

public class Cola extends PriorityQueue<Paciente> implements Buffer {
	private final int tipos = 3;
	public Cola() {
		super();
	}

	/*private  PriorityQueue<Paciente> pacientes;
	private int[] numPacientes;
	private int[] posActualCola;
	private int[] posPrimeroCola;

	public Cola() {
		pacientes = new PriorityQueue<Paciente>();
		numPacientes = new int[3];
		posActualCola = new int[3];
		posPrimeroCola = new int[3];
	}
*/
	public PriorityQueue<Paciente> getPacientes(int tipo) {
		PriorityQueue<Paciente> p = new PriorityQueue<Paciente>();
		for (Paciente pc: this){
			if(pc.getTipoServicio() == tipo)
				p.offer(pc);
		}
		return p;
	}

	public boolean agregarPaciente(Paciente paciente) {
		this.offer(paciente);
		return true;
	}
	public Paciente sacarPaciente() {
		return this.poll();
	}
		/*	if (posActualCola[paciente.getTipoServicio()] >= pacientes[paciente.getTipoServicio()].size()) {
			return false;
		}
		pacientes[paciente.getTipoServicio()].get(posActualCola[paciente.getTipoServicio()]) = paciente;
		
		if(pacientes.isEmpty())
			return false;
		if(pacientes.contains(paciente))
			return false;
		pacientes.offer(paciente);
		posActualCola[paciente.getTipoServicio()]++;
		numPacientes[paciente.getTipoServicio()]++;
		return true;
	}

	public Paciente sacarPaciente() {
		for (Paciente p >this) {
			if (hayPacientesTipo(i)) {
				return sacarPacienteTipo(i);
			}
		}
		return null;
	}

	public Paciente sacarPacienteTipo(int tipo) {
		if (posPrimeroCola[tipo] >= posActualCola[tipo]) {
			return null;
		}
		Paciente sacado = pacientes[tipo][posPrimeroCola[tipo]];
		posPrimeroCola[tipo]++;
		numPacientes[tipo]--;
		return sacado;
	}
*/
	public Paciente sacarPacienteTipo(int tipo) {
		Paciente ptipo;
		
		for(Paciente p:this)
			if (p.getTipoServicio() == tipo){
				ptipo =	new Paciente(p.getTLlegada(),p.getTipoServicio());
				this.remove(p);
				return ptipo;
			}
		return null;
	}
	
	public boolean hayPacientes() {
		return !this.isEmpty();
	}

	public boolean hayPacientesTipo(int tipo) {
		for(Paciente p:this)
			if (p.getTipoServicio() == tipo){
				return true;
			}
		return false;
	}

	public int numPacientesEnCola() {
		return this.size();
	}

	public int numPacientesEnColaTipo(int tipo) {
		int i=0;
		for(Paciente p : this){
			if( p.getTipoServicio() == tipo)
				i++;
		}
		return i;
	}

}

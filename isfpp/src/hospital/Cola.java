package hospital;

import java.util.List;
import java.util.ArrayList;

public class Cola {
	private List<Paciente>[] pacientes;
	private int[] numPacientes;
	private int[] posActualCola;
	private int[] posPrimeroCola;

	public Cola() {
		for(int i=0; i < 3;i++)
			pacientes[i] = new ArrayList<Paciente>();
		numPacientes = new int[3];
		posActualCola = new int[3];
		posPrimeroCola = new int[3];
	}

	public List<Paciente> getPacientes(int tipo) {
		return pacientes[tipo];
	}

	public boolean agregarPaciente(Paciente paciente) {
		if (posActualCola[paciente.getTipoServicio()] >= pacientes[paciente.getTipoServicio()].size()) {
			return false;
		}
		pacientes[paciente.getTipoServicio()].get(posActualCola[paciente.getTipoServicio()]) = paciente;
		posActualCola[paciente.getTipoServicio()]++;
		numPacientes[paciente.getTipoServicio()]++;
		return true;
	}

	public Paciente sacarPaciente() {
		for (int i = 0; i < 3; i++) {
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

	public boolean hayPacientes() {
		return hayPacientesTipo(0) || hayPacientesTipo(1) || hayPacientesTipo(2);
	}

	public boolean hayPacientesTipo(int tipo) {
		return numPacientes[tipo] > 0;
	}

	public int numPacientesEnCola() {
		return numPacientesEnColaTipo(0) + numPacientesEnColaTipo(1) + numPacientesEnColaTipo(2);
	}

	public int numPacientesEnColaTipo(int tipo) {
		return numPacientes[tipo];
	}

	public int numTotalPacientes() {
		return numTotalPacientesTipo(0) + numTotalPacientesTipo(1) + numTotalPacientesTipo(2);
	}

	public int numTotalPacientesTipo(int tipo) {
		return posActualCola[tipo];
	}

	public double esperaPromedio() {
		double prom = 0;
		for (int tipo = 0; tipo < 3; tipo++) {
			for (int i = 0; i < posActualCola[tipo]; i++) {
				prom += pacientes[tipo][i].getEspera();
			}
		}
		return prom;
	}
}

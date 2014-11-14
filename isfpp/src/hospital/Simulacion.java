package hospital;

import hospital.Principal.Aleatorio;
import hospital.Principal.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Simulacion {
	private Cola colaPacientes;

	// private enum nombresDoc{}
	private List<Doctor> doctores;
	private List<PeriodoDia> periodos;

	private int tiempoTotalDeEspera;
	private int numPacientesAtendidos;
	private int numPacientesMuertos;
	private int numPeriodos;

	public Simulacion(int docs) {
		periodos = new ArrayList<PeriodoDia>();
		colaPacientes = new Cola();

		numPeriodos = 0;
		doctores = new ArrayList<Doctor>();
		for (int i = 0; i < docs; i++) {
			doctores.add(new Doctor("" + i, colaPacientes));
		}
	}

	public void cargarArchivo(String path) throws FileNotFoundException {
		File archivo = new File(path);
		Scanner lector = new Scanner(archivo);
		while (lector.hasNext()) {
			String linea = lector.nextLine();
			String[] arreglo = linea.split("%");
			double probAlta = Double.parseDouble(arreglo[2]) / 100.0;
			double probMedia = Double.parseDouble(arreglo[3]) / 100.0;
			double probBaja = Double.parseDouble(arreglo[4]) / 100.0;
			PeriodoDia p = new PeriodoDia(arreglo[0], arreglo[1], probAlta,
					probMedia, probBaja);
//			periodos[numPeriodos] = p;
			periodos.add(p);
			numPeriodos++;
		}
		lector.close();
	}

	public void simular(int horaInicio, int minutoInicio, int horaTermino,
			int minutoTermino) {
		int tMax = 60 * horaTermino + minutoTermino;
		int t = 60 * horaInicio + minutoInicio;
		// Ciclo principal de simulacion
		while (t < tMax || colaPacientes.hayPacientes()) {
			// Llegada de pacientes
			if (Aleatorio.real(0, 1) < 0.2 && t < tMax) {
				PeriodoDia p = buscarPeriodo(t);
				if (p == null) {
					p = (periodos.get(periodos.size() - 1));
				}
				double[] probsActuales = p.getProbs();
				double prioridad = Aleatorio.real(0, 1) * 100;
				if (prioridad < probsActuales[0]) {
					colaPacientes.agregarPaciente(new Paciente(t, 0));
				} else if (probsActuales[0] < prioridad
						&& prioridad < probsActuales[0] + probsActuales[1]) {
					colaPacientes.agregarPaciente(new Paciente(t, 1));
				} else {
					colaPacientes.agregarPaciente(new Paciente(t, 2));
				}
				t++;
			}
			// E s t a d i s t i c a s
			estadisticasClientes();
		}
	}

	public void estadisticasClientes() {
		for (int i = 0; i < numPeriodos; i++) {
			Usuario.mensajeConsola(" Periodo "
					+ periodos.get(i).getHoraInicio() + " - "
					+ periodos.get(i).getHoraTermino());
			Usuario.mensajeConsola("Numero total de pacientes: "
					+ periodos.get(i).getTotalPacientes());
//			Usuario.mensajeConsola("Numero pacientes Urgencia Alta: "
//					+ periodos.get(i).getPacientesTipo(0));
//			Usuario.mensajeConsola("Numero pacientes Urgencia Media: "
//					+ periodos.get(i).getPacientesTipo(1));
//			Usuario.mensajeConsola("Numero pacientes Urgencia Baja: "
//					+ periodos.get(i).getPacientesTipo(2));
			// Usuario.mensajeConsola("Numero pacientes traumatologicos: "
			// + periodos.get(i).getPacientesTrauma());
			Usuario.mensajeConsola("-------------------");
		}
		/*
		 * Cada paciente debe ser un hilo y al finalizar debe incrementar tiempo
		 * de espera,numPacientesAtendidos, tipo Si un paciente muere, tiempo de
		 * espera es desde su llegada hasta su muerte.
		 */
		Usuario.mensajeConsola("Tiempo espera promedio: "
				+ colaPacientes.esperaPromedio());
		int numDigitales = colaPacientes.numTotalPacientes();
		int numAnalogos = colaPacientes.numTotalPacientes();
		double percDigitales = (double) numDigitales
				/ (double) (numDigitales + numAnalogos);
		double percAnalogos = (double) numAnalogos
				/ (double) (numDigitales + numAnalogos);
		Usuario.mensajeConsola("Tiempo espera promedio todos : "
				+ (colaPacientes.esperaPromedio() * percAnalogos + colaPacientes
						.esperaPromedio() * percDigitales));
	}

	/**
	 * Recibe un horario devuelve un periodo del dia
	 * 
	 * @param t
	 * @return
	 */
	private PeriodoDia buscarPeriodo(int t) {
		for (int i = 0; i < numPeriodos; i++) {
			if (periodos.get(i).estaEnPeriodo(t)) {
				return periodos.get(i);
			}
		}
		return null;
	}
}

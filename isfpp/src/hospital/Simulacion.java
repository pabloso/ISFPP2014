package hospital;

import hospital.Principal.Aleatorio;
import hospital.Principal.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulacion {
	private Cola colaPacientes;
//	private Cola colaPacientes;
	private List<Doctor> doctores;
	private List<PeriodoDia>  periodos;
	private int numPeriodos;
	
	public Simulacion(int docs) {//, int numAnalogos) {
		periodos = new ArrayList<PeriodoDia>();//[100]
		numPeriodos = 0;
		doctores = new ArrayList<Doctor>();//[numDigitales]
		for (int i = 0; i < docs; i++) {
			doctores.add( new Doctor(0));
		}
		/*
		doctores = new Doctor[numAnalogos];
		for (int i = 0; i < numAnalogos; i++) {
			doctores[i] = new Doctor(1);
		}
		*/
		colaPacientes = new Cola();
//		colaPacientes = new Cola();
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
			PeriodoDia p = new PeriodoDia(arreglo[0], arreglo[1], probAlta, probMedia, probBaja);
			//periodos[numPeriodos] = p;
			periodos.add(p);
			//numPeriodos++;
		}
		lector.close();
	}

	public void simular(int horaInicio, int minutoInicio, int horaTermino, int minutoTermino) {
		int tMax = 60 * horaTermino + minutoTermino;
		int t = 60 * horaInicio + minutoInicio;
		// Ciclo p rincipal de simulacion
		while (t < tMax || colaPacientes.hayPacientes() ) {//|| colaPacientes.hayPacientes()
			// Llegada de p a c ientes
			if (Aleatorio.real(0, 1) < 0.2 && t < tMax) {
				PeriodoDia p = buscarPeriodo(t);
				if (p == null) {
					p = (periodos.get(periodos.size() - 1));
				}
				double[] probsActuales = p.getProbs();
				double randomTipo = Aleatorio.real(0, 1);
				int tipoServicio = 0;
				if (randomTipo >= probsActuales[0] && randomTipo < probsActuales[0] + probsActuales[1])
					tipoServicio = 1;
				else if (randomTipo >= probsActuales[0] + probsActuales[1])
					tipoServicio = 2;
				p.agregarPacientesTipo(tipoServicio);
				Paciente paciente = new Paciente(t, tipoServicio);
				/*
				if (Aleatorio.real(0, 1) < 0.7) {
					p.agregarPacientesTrauma();
					colaPacientes.agregarPaciente(paciente);
				} else {*/
				colaPacientes.agregarPaciente(paciente);
				//}
			}
			/* Atencion doctores
			 * Esto tendría que ser otro hilo aparte
			 * 
			 * un hilo la generacion
			 **** un hilo cada paciente
			 *
			 * un hilo la atención
			 *
			 */
			for (int i = 0; i < doctores.size(); i++) {
				if (!doctores.get(i).estaOcupado(t)) {
					if (colaPacientes.hayPacientes()) {
						doctores.get(i).atender(t, t + Aleatorio.entero(10, 20));
						Paciente c = colaPacientes.sacarPaciente();
						c.esAtendido(t);
					}
				}
			}
			/*
			for (int i = 0; i < trauma.length; i++) {
				if (!trauma[i].estaOcupado(t)) {
					if (colaPacientes.hayPacientes()) {
						trauma[i].atender(t, t + Aleatorio.entero(10, 20));
						Paciente c = colaPacientes.sacarPaciente();
						c.esAtendido(t);
					}
				}
			}
			*/
			t++;
		}
		// E s t a d i s t i c a s
		estadisticasClientes();
	}

	public void estadisticasClientes() {
		for (int i = 0; i < numPeriodos; i++) {
			Usuario.mensajeConsola(" Periodo " + periodos.get(i).getHoraInicio() + " - " + periodos.get(i).getHoraTermino());
			Usuario.mensajeConsola("Numero total de pacientes: " + periodos.get(i).getTotalPacientes());
			Usuario.mensajeConsola("Numero pacientes Urgencia Alta: " + periodos.get(i).getPacientesTipo(0));
			Usuario.mensajeConsola("Numero pacientes Urgencia Media: " + periodos.get(i).getPacientesTipo(1));
			Usuario.mensajeConsola("Numero pacientes Urgencia Baja: " + periodos.get(i).getPacientesTipo(2));
			Usuario.mensajeConsola("Numero pacientes traumatologicos: " + periodos.get(i).getPacientesTrauma());
			Usuario.mensajeConsola("-------------------");
		}
		Usuario.mensajeConsola("Tiempo espera promedio pacientes no traumatologicos: " + colaPacientes.esperaPromedio());
		Usuario.mensajeConsola("Tiempo espera promedio pacientes traumatologicos: " + colaPacientes.esperaPromedio());
		int numDigitales = colaPacientes.numTotalPacientes();
		int numAnalogos = colaPacientes.numTotalPacientes();
		double percDigitales = (double) numDigitales / (double) (numDigitales + numAnalogos);
		double percAnalogos = (double) numAnalogos / (double) (numDigitales + numAnalogos);
		Usuario.mensajeConsola("Tiempo espera promedio todos : " + (colaPacientes.esperaPromedio() * percAnalogos + colaPacientes.esperaPromedio() * percDigitales));
	}

	/**
	 * Recibe un horario devuelve un periodo del dia
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

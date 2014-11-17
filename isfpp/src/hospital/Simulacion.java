package hospital;

import hospital.Principal.Aleatorio;
import hospital.Principal.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulacion {
	private Cola colaPacientes;
	private List<Doctor> doctores;
	private List<PeriodoDia> periodos;
	private Estadistica e;
	private ExecutorService poolDeDoctores;

	private static int baseMinutoEnMs;
	private int tFin;
	private int tInicio;

	public Simulacion() {

		Simulacion.setBaseMinutoMs(40);

		periodos = new ArrayList<PeriodoDia>();
		colaPacientes = new Cola();
		e = new Estadistica(0, 0, 0, 0, 0, 0, 0);
		doctores = new ArrayList<Doctor>();

	}

	public void cargarArchivo(String nombre) throws FileNotFoundException {
		// File archivo = new File(path);
		URL url = getClass().getResource(nombre);
		File archivo = new File(url.getPath());

		Scanner lector = new Scanner(archivo);
		while (lector.hasNext()) {
			String linea = lector.nextLine();
			String[] arreglo = linea.split("%");
			double probAlta = Double.parseDouble(arreglo[2]) / 100.0;
			double probMedia = Double.parseDouble(arreglo[3]) / 100.0;
			double probBaja = Double.parseDouble(arreglo[4]) / 100.0;
			PeriodoDia p = new PeriodoDia(arreglo[0], arreglo[1], probAlta,
					probMedia, probBaja);
			periodos.add(p);
			e.setNumPeriodos(e.getNumPeriodos() + 1);

		}
		System.out.println("Cantidad de periodos: " + e.getNumPeriodos());
		lector.close();
	}

	public void simular(int docs, int horaInicio, int minutoInicio,
			int horaTermino, int minutoTermino) throws Throwable {

		// Conversion a minutos
		tFin = 60 * horaTermino + minutoTermino;
		setTinicio(60 * horaInicio + minutoInicio);
		System.out.println("T inicio: " + tInicio);
		System.out.println("T fin   : " + tFin);

		// defualt de 1 doctor
		if (docs < 1)
			docs = 1;
		for (int i = 0; i < docs; i++) {
			doctores.add(new Doctor("" + i, this, colaPacientes));
		}

		poolDeDoctores = Executors.newCachedThreadPool();
		for (Doctor d : doctores)
			poolDeDoctores.execute(d);

		// Ciclo principal de simulacion
		while (tInicio < tFin || colaPacientes.hayPacientes()) {
			// Espero 1' virtual = baseMinutoMs
			Thread.sleep(Simulacion.getBaseMinutoMs());
			if (tInicio < tFin) {
				if (Aleatorio.real(0, 1) < 0.2) {
					//Ingreso paciente
					PeriodoDia p = buscarPeriodo(tInicio);
					if (p == null) {
						p = (periodos.get(periodos.size() - 1));
					}
					double[] probsActuales = p.getProbs();
					double prioridad = Aleatorio.real(0, 1);
					if (prioridad < probsActuales[0]) {
						colaPacientes.set(new Paciente(gettInicio(), 0));
						e.incrementarPacientesAtendidosAlta();
					} else if (probsActuales[0] < prioridad
							&& prioridad < (probsActuales[0] + probsActuales[1])) {
						colaPacientes.set(new Paciente(gettInicio(), 1));
						e.incrementarPacientesAtendidosMedia();
					} else {
						colaPacientes.set(new Paciente(gettInicio(), 2));
						e.incrementarPacientesAtendidosBaja();
					}
					System.out.println("Paciente en Cola:" + colaPacientes.numPacientesEnCola());
				}
			}
			incrementarTinicio();
		}
		System.out.println("Termino aplicacion monito");
		// Estadisticas
		estadisticasClientes();
		poolDeDoctores.shutdown();
	}

	public void estadisticasClientes() {
		for (int i = 0; i < e.getNumPeriodos(); i++) {
			Usuario.mensajeConsola("Periodo " + periodos.get(i).getHoraInicio()
					+ " - " + periodos.get(i).getHoraTermino());
			Usuario.mensajeConsola("Numero total de pacientes: "
					+ this.e.getNumPacientesAtendidos());
			Usuario.mensajeConsola("Numero pacientes Urgencia Alta: "
					+ this.e.getPacientesAtendidosAlta());
			Usuario.mensajeConsola("Numero pacientes Urgencia Media: "
					+ this.e.getPacientesAtendidosMedia());
			Usuario.mensajeConsola("Numero pacientes Urgencia Baja: "
					+ this.e.getPacientesAtendidosBaja());
			Usuario.mensajeConsola("Espera Promedio: "
					+ this.e.esperaPromedio());
			Usuario.mensajeConsola("Cantidad total de pacientes: "
					+ this.e.getNumPacientesAtendidos());
			Usuario.mensajeConsola("-------------------");
		}
	}

	/**
	 * Recibe un horario devuelve un periodo del dia
	 * 
	 * @param t
	 * @return
	 */
	private PeriodoDia buscarPeriodo(int t) {
		for (int i = 0; i < e.getNumPeriodos(); i++) {
			if (periodos.get(i).estaEnPeriodo(t)) {
				return periodos.get(i);
			}
		}
		return null;
	}

	public synchronized int gettInicio() {
		return tInicio;
	}

	private synchronized void incrementarTinicio() {
		tInicio++;
	}

	private synchronized void setTinicio(int i) {
		tInicio = i;
	}

	public synchronized int gettFin() {
		return tFin;
	}

	public synchronized Estadistica getEstadistica() {
		return e;
	}

	public void stop() {

	}

	private static void setBaseMinutoMs(int i) {
		baseMinutoEnMs = i;
	}

	public static int getBaseMinutoMs() {
		return baseMinutoEnMs;
	}

}

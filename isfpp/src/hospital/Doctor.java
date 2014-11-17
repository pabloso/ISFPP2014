package hospital;

import java.util.Random;

/**
 * Doctor: Desencola un paciente Completa la estadistica Demora proporcional en
 * ms
 * 
 * @author Damian Blanco - Pablo Sosa
 * 
 */
public class Doctor implements Runnable {
	private final static Random generator = new Random();
	private final Simulacion s;
	private final Buffer sharedLocation;
	private String nombre;
	private boolean atiende;
	private long tiempoFin;
	private int tiempoAtencion;

	public Doctor(String nombre, Simulacion s, Buffer shared) {
		sharedLocation = shared;
		this.nombre = nombre;
		this.s = s;
		this.tiempoFin = s.gettFin();
		this.atiende = true;
	}

	public void run() {
		Paciente paciente;
		int horaActual = 0;

		try {
			while (horaActual < tiempoFin || sharedLocation.isNotEmpty()) {
				try {
					paciente = sharedLocation.get(this);

					if (paciente != null) {
						horaActual = s.gettInicio();
						tiempoAtencion = generator.nextInt(40) + 20;

						s.getEstadistica().incrementarNumPacientesAtendidos();
						// s.getEstadistica().setNumPacientesAtendidos(s.getEstadistica().getNumPacientesAtendidos()+1);
						s.getEstadistica()
								.setTiempoTotalDeEspera(
										s.getEstadistica()
												.getTiempoTotalDeEspera()
												+ (horaActual - paciente
														.getTLlegada()));

						System.out.println("Doctor: " + nombre + " Atiende: "
								+ paciente + " Hora Actual: " + horaActual
								+ " TiempoDeAtencion: " + tiempoAtencion);

						while (tiempoAtencion + horaActual > s.gettInicio())
							Thread.sleep(Simulacion.getBaseMinutoMs());
					}
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Doctor " + nombre + " termino atención.");
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isAtiende() {
		return atiende;
	}

}
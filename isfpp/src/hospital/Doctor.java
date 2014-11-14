package hospital;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Doctor implements Runnable {
	private final static Random generator = new Random();
	private final Buffer sharedLocation;
	private String nombre;
	//	private ExecutorService application;
	private boolean atiende;
	private long tiempoFin;
	private long tiempoAtencion;

	public Doctor(String nombre, Buffer shared) {
		sharedLocation = shared;
		this.nombre = nombre;
		//	application = Executors.newCachedThreadPool();
		//	application.execute( new Reloj(15000, this, shared) );
		tiempoFin = System.currentTimeMillis() + 10000;
		this.atiende = true;
	}

	public void run() {
		Paciente paciente;

		while (System.currentTimeMillis() < tiempoFin) {

			try {
				paciente = sharedLocation.get(this);
				if (paciente != null) {
					System.out.println("Doctor " + nombre + " Atiende " + paciente);
					tiempoAtencion = System.currentTimeMillis() + generator.nextInt(3000);
					Thread.sleep(3000);
				}
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}

		}

		System.out.println("Doctor " + nombre + " termino atención.");
		//application.shutdown();
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isAtiende() {
		return atiende;
	}

}
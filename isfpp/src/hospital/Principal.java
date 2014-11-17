package hospital;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Principal {
	/* *
	 * 
	 * @param args
	 * 
	 * @throws FileNotFoundException
	 */
	public static class Usuario {

		public static int entero(String s) {
			System.out.println(s);
			Scanner ent = new Scanner(System.in);
			int i = ent.nextInt();
			ent.close();
			return i;
		}

		public static void mensajeConsola(String s) {
			System.out.println(s);
		}

	}

	public static class Aleatorio {
		// private double real;
		public static double real(double d1, double d2) {
			return (new Random().nextDouble()) % (d2 - d1);
		}

		public static int entero(int d1, int d2) {
			return ((new Random()).nextInt()) % (d2 - d1);
		}
	}

	public static void main(String[] args) throws Throwable {
		int cantidadDoctores = Usuario.entero("Ingrese el numero de doctores: ");
		Simulacion sim = new Simulacion();
		try {
			sim.cargarArchivo("urgencias.txt");
			sim.simular(cantidadDoctores, 6, 0, 18, 0);
		} catch (FileNotFoundException e) {
			System.out.println("Error al abrir el archivo");
			System.out.println(e.getMessage());
		}
	}
}

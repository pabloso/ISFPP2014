package hospital;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Principal {
	/* *
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static class Usuario {
		public Usuario() {
		};

		public static int entero(String s) {
			System.out.println(s);
			Scanner ent = new Scanner(System.in);
			return ent.nextInt();
		}

		public static void mensajeConsola(String s) {
			System.out.println(s);
		}

	}

	public static class Aleatorio {
		//private double real;

		public Aleatorio() {
		};

		public static double real(double d1, double d2) {
			Random dou = new Random();
			return dou.nextDouble() % d2 - dou.nextDouble() % d1;
		}

		public static int entero(int d1, int d2) {
			Random dou = new Random();
			return dou.nextInt() % d2 - dou.nextInt() % d1;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		int noTrauma = Usuario.entero("Ingrese el numero de doctores no traumatologos: ");
		int trauma = Usuario.entero("Ingrese el numero de traumatologos: ");
		Simulacion sim = new Simulacion(noTrauma, trauma);
		try {
			//sim.cargarArchivo("urgencia.txt");
			sim.cargarArchivo("/home/deimon/Programacion/Java/ISFPP/src/hospital/urgencia.txt");
			sim.simular(6, 0, 18, 0);
		} catch (FileNotFoundException e) {
			System.out.println("Error al abrir el archivo");
			System.out.println(e.getMessage());
		}
	}
}

package hospital;

import hospital.Principal.Aleatorio;
import hospital.Principal.Usuario;

import java.io.File ;
import java.io.FileNotFoundException;
import java.util.Scanner ;

public class Simulacion{
	private Cola colaNoTrauma;
	private Cola colaTrauma;
	private Doctor [] noTrauma ;
	private Doctor [] trauma ;
	private PeriodoDia[] periodos ;
	private int numPeriodos;
	public Simulacion( int numDigitales , int numAnalogos) {
		periodos = new PeriodoDia [100];
		numPeriodos = 0;
		noTrauma = new Doctor [ numDigitales];
		for ( int i = 0; i < numDigitales; i ++) {
			noTrauma [ i ] = new Doctor (0);
		}
		trauma = new Doctor [ numAnalogos];
		for ( int i = 0; i < numAnalogos; i ++) {
			trauma [ i ] = new Doctor (1);
		}
		colaNoTrauma = new Cola ();
		colaTrauma = new Cola ();
	}
	public void cargarArchivo( String path ) throws FileNotFoundException {
		File archivo = new File ( path );
		Scanner lector = new Scanner ( archivo );
		while ( lector.hasNext ()) {
			String linea = lector.nextLine ();
			String [] arreglo = linea . split ( " %" );
			double probAlta = Double.parseDouble( arreglo [2]) / 100.0;
			double probMedia = Double.parseDouble( arreglo [3]) / 100.0;
			double probBaja = Double.parseDouble( arreglo [4]) / 100.0;
			PeriodoDia p = new PeriodoDia( arreglo [0] , arreglo [1] , probAlta ,
					probMedia , probBaja );
			periodos [ numPeriodos] = p ;
			numPeriodos++;
		}
		lector.close();
	}
	public void simular ( int horaInicio , int minutoInicio , int horaTermino ,
			int minutoTermino) {
		int tMax = 60 * horaTermino + minutoTermino;
		int t = 60 * horaInicio + minutoInicio;
		// Ciclo p r i n c i p a l de s i m u l a c i o n
		while ( t < tMax || colaNoTrauma.hayPacientes()||colaTrauma.hayPacientes()) {
			// Llegada de p a c i e n t e s
			if ( Aleatorio.real (0 , 1) < 0.2 && t < tMax ) {
				PeriodoDia p = buscarPeriodo( t );
				if ( p == null ) {
					p = periodos [ numPeriodos - 1];
				}
				double [] probsActuales = p.getProbs ();
				double randomTipo = Aleatorio.real (0 , 1);
				int tipoServicio = 0;
				if ( randomTipo >= probsActuales [0]
						&& randomTipo < probsActuales [0] + probsActuales [1])
					tipoServicio = 1;
				else if ( randomTipo >= probsActuales [0] + probsActuales [1])
					tipoServicio = 2;
				p.agregarPacientesTipo( tipoServicio);
				Paciente paciente = new Paciente (t , tipoServicio);
				if ( Aleatorio.real (0 , 1) < 0.7) {
					p.agregarPacientesTrauma ();
					colaTrauma.agregarPaciente( paciente );
				} else {
					colaNoTrauma.agregarPaciente( paciente );
				}
			}
			// Atencion doctores
			for ( int i = 0; i < noTrauma.length ; i ++) {
				if (! noTrauma[i].estaOcupado( t )) {
					if ( colaNoTrauma.hayPacientes ()) {
						noTrauma[i].atender (t , t + Aleatorio.entero(10,20));
						Paciente c = colaNoTrauma. sacarPaciente();
						c.esAtendido( t );
					}
				}
			}
			for ( int i = 0; i < trauma.length ; i++) {
				if (!trauma[i].estaOcupado( t )) {
					if (colaTrauma.hayPacientes()) {
						trauma [i].atender (t , t + Aleatorio.entero (10,20));
						Paciente c =colaTrauma.sacarPaciente();
						c.esAtendido( t );
					}
				}
			}
			t++;
		}
		// E s t a d i s t i c a s
		estadisticasClientes ();
	}
	public void estadisticasClientes() {
		for ( int i = 0; i < numPeriodos; i ++) {
			Usuario.mensajeConsola( " Periodo " + periodos [ i ].getHoraInicio()
			+ " - " + periodos [ i ].getHoraTermino());
			Usuario.mensajeConsola( " Numero total de p a c i e n t e s: "
					+ periodos [ i ].getTotalPacientes());
			Usuario.mensajeConsola( " Numero p a c i e n t e s Urgencia Alta : "
					+ periodos [ i ].getPacientesTipo(0));
			Usuario.mensajeConsola( " Numero p a c i e n t e s Urgencia Media : "
					+ periodos [ i ].getPacientesTipo(1));
			Usuario.mensajeConsola( " Numero p a c i e n t e s Urgencia Baja : "
					+ periodos [ i ].getPacientesTipo(2));
			Usuario.mensajeConsola( " Numero p a c i e n t e s t r a u m a t o l o g i c o s: "
					+ periodos [ i ].getPacientesTrauma());
		}
		Usuario.mensajeConsola( " Tiempo espera promedio p a c i e n t e s no t r a u m a t o l o g i c o s: "
				+ colaNoTrauma.esperaPromedio());
		Usuario.mensajeConsola( " Tiempo espera promedio p a c i e n t e s t r a u m a t o l o g i c o s: "
				+colaTrauma.esperaPromedio());
		int numDigitales = colaNoTrauma.numTotalPacientes();
		int numAnalogos =colaTrauma.numTotalPacientes ();
		double percDigitales = ( double ) numDigitales
				/ ( double ) ( numDigitales + numAnalogos);
		double percAnalogos = ( double ) numAnalogos
				/ ( double ) ( numDigitales + numAnalogos);
		Usuario.mensajeConsola( " Tiempo espera promedio todos : "+ (colaTrauma.esperaPromedio() * percAnalogos + 
				colaNoTrauma.esperaPromedio() * percDigitales ));
	}
	private PeriodoDia buscarPeriodo( int t ) {
		for ( int i = 0; i < numPeriodos; i ++) {
			if ( periodos [ i ].estaEnPeriodo( t )) {
				return periodos [ i ];
			}
		}
		return null ;
	}
}

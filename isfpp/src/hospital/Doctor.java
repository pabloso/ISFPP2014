package hospital;

public class Doctor {
	private int tipo ; // 0: F , 1: NF
	private int tAtencion;
	private int tLibre ;
	public Doctor ( int _tipo ) {
		tipo = _tipo ;
	}
	public void atender ( int _tAtencion , int _tLibre ) {
		tAtencion = _tAtencion;
		tLibre = _tLibre ;
	}
	public boolean estaOcupado( int t ) {
		return t < tLibre ;
	}
}

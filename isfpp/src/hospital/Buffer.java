package hospital;

public interface Buffer {
	public void set(Paciente paciente) throws InterruptedException;
	public Paciente get(Doctor doctor) throws InterruptedException;
	public boolean isNotEmpty() throws InterruptedException;
}

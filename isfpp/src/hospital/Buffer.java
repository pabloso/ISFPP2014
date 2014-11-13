package hospital;
public interface Buffer
{
   public void set(Paciente paciente) throws InterruptedException; 

   public Paciente get(Doctor doctor) throws InterruptedException; 
   
   public void finAtencion(Doctor doctor) throws InterruptedException;
} 



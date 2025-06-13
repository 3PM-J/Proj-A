package upeu.edu.pe.registroa.exception;

public class PacienteNotFoundException extends RuntimeException {
    
    public PacienteNotFoundException(String message) {
        super(message);
    }
    
    public PacienteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PacienteNotFoundException(Long id) {
        super("Paciente no encontrado con ID: " + id);
    }
}
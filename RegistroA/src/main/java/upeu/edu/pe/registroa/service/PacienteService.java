package upeu.edu.pe.registroa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import upeu.edu.pe.registroa.exception.PacienteNotFoundException;
import upeu.edu.pe.registroa.model.Paciente;
import upeu.edu.pe.registroa.repository.PacienteRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Transactional(readOnly = true)
    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Paciente> findById(Long id) {
        return pacienteRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public Paciente getById(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundException(id));
    }
    
    @Transactional(readOnly = true)
    public Optional<Paciente> findByDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }
    
    @Transactional(readOnly = true)
    public List<Paciente> findByNombresContainingIgnoreCase(String nombres) {
        return pacienteRepository.findByNombresContainingIgnoreCase(nombres);
    }
    
    @Transactional(readOnly = true)
    public List<Paciente> findByApellidosContainingIgnoreCase(String apellidos) {
        return pacienteRepository.findByApellidosContainingIgnoreCase(apellidos);
    }
    
    public Paciente save(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo");
        }
        
        // Validaciones adicionales
        if (paciente.getNombres() == null || paciente.getNombres().trim().isEmpty()) {
            throw new IllegalArgumentException("Los nombres son obligatorios");
        }
        
        if (paciente.getApellidos() == null || paciente.getApellidos().trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos son obligatorios");
        }
        
        if (paciente.getDni() == null || paciente.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI es obligatorio");
        }
        
        // Limpiar espacios en blanco
        paciente.setNombres(paciente.getNombres().trim());
        paciente.setApellidos(paciente.getApellidos().trim());
        paciente.setDni(paciente.getDni().trim());
        
        if (paciente.getTelefono() != null) {
            paciente.setTelefono(paciente.getTelefono().trim());
        }
        
        if (paciente.getEmail() != null) {
            paciente.setEmail(paciente.getEmail().trim().toLowerCase());
        }
        
        if (paciente.getDireccion() != null) {
            paciente.setDireccion(paciente.getDireccion().trim());
        }
        
        return pacienteRepository.save(paciente);
    }
    
    public void deleteById(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new PacienteNotFoundException(id);
        }
        pacienteRepository.deleteById(id);
    }
    
    public void delete(Paciente paciente) {
        if (paciente == null || paciente.getId() == null) {
            throw new IllegalArgumentException("El paciente o su ID no pueden ser nulos");
        }
        deleteById(paciente.getId());
    }
    
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return pacienteRepository.existsById(id);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByDni(String dni) {
        return pacienteRepository.existsByDni(dni);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByDniAndIdNot(String dni, Long id) {
        return pacienteRepository.findByDni(dni)
                .map(p -> !p.getId().equals(id))
                .orElse(false);
    }
    
    @Transactional(readOnly = true)
    public long count() {
        return pacienteRepository.count();
    }
}
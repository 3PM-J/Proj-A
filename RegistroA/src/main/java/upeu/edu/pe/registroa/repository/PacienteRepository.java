package upeu.edu.pe.registroa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upeu.edu.pe.registroa.model.Paciente;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    Optional<Paciente> findByDni(String dni);
    
    boolean existsByDni(String dni);
    
    List<Paciente> findByNombresContainingIgnoreCase(String nombres);
    
    List<Paciente> findByApellidosContainingIgnoreCase(String apellidos);
    
    List<Paciente> findByEmailContainingIgnoreCase(String email);
    
    List<Paciente> findByGenero(String genero);
    
    List<Paciente> findByFechaNacimientoBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT p FROM Paciente p WHERE " +
           "LOWER(p.nombres) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "p.dni LIKE CONCAT('%', :searchTerm, '%') OR " +
           "LOWER(p.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Paciente> findBySearchTerm(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT COUNT(p) FROM Paciente p WHERE p.genero = :genero")
    long countByGenero(@Param("genero") String genero);
}
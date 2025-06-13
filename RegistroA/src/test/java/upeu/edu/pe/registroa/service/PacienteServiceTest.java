package upeu.edu.pe.registroa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import upeu.edu.pe.registroa.exception.PacienteNotFoundException;
import upeu.edu.pe.registroa.model.Paciente;
import upeu.edu.pe.registroa.repository.PacienteRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombres("Juan");
        paciente.setApellidos("Pérez");
        paciente.setDni("12345678");
        paciente.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        paciente.setTelefono("987654321");
        paciente.setEmail("juan@email.com");
        paciente.setDireccion("Lima");
        paciente.setGenero("Masculino");
    }

    @Test
    void testFindAll() {
        List<Paciente> pacientes = Arrays.asList(paciente);
        when(pacienteRepository.findAll()).thenReturn(pacientes);

        List<Paciente> result = pacienteService.findAll();

        assertEquals(1, result.size());
        assertEquals(paciente.getNombres(), result.get(0).getNombres());
        verify(pacienteRepository).findAll();
    }

    @Test
    void testFindById() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        Optional<Paciente> result = pacienteService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(paciente.getNombres(), result.get().getNombres());
        verify(pacienteRepository).findById(1L);
    }

    @Test
    void testGetById_Success() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        Paciente result = pacienteService.getById(1L);

        assertEquals(paciente.getNombres(), result.getNombres());
        verify(pacienteRepository).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PacienteNotFoundException.class, () -> pacienteService.getById(1L));
        verify(pacienteRepository).findById(1L);
    }

    @Test
    void testSave() {
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente result = pacienteService.save(paciente);

        assertEquals(paciente.getNombres(), result.getNombres());
        verify(pacienteRepository).save(paciente);
    }

    @Test
    void testSave_NullPaciente() {
        assertThrows(IllegalArgumentException.class, () -> pacienteService.save(null));
    }

    @Test
    void testSave_EmptyNombres() {
        paciente.setNombres("");
        assertThrows(IllegalArgumentException.class, () -> pacienteService.save(paciente));
    }

    @Test
    void testDeleteById_Success() {
        when(pacienteRepository.existsById(1L)).thenReturn(true);

        pacienteService.deleteById(1L);

        verify(pacienteRepository).existsById(1L);
        verify(pacienteRepository).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(pacienteRepository.existsById(1L)).thenReturn(false);

        assertThrows(PacienteNotFoundException.class, () -> pacienteService.deleteById(1L));
        verify(pacienteRepository).existsById(1L);
        verify(pacienteRepository, never()).deleteById(anyLong());
    }

    @Test
    void testExistsByDni() {
        when(pacienteRepository.existsByDni("12345678")).thenReturn(true);

        boolean result = pacienteService.existsByDni("12345678");

        assertTrue(result);
        verify(pacienteRepository).existsByDni("12345678");
    }

    @Test
    void testExistsByDniAndIdNot() {
        Paciente otherPaciente = new Paciente();
        otherPaciente.setId(2L);
        otherPaciente.setDni("12345678");
        
        when(pacienteRepository.findByDni("12345678")).thenReturn(Optional.of(otherPaciente));

        boolean result = pacienteService.existsByDniAndIdNot("12345678", 1L);

        assertTrue(result);
        verify(pacienteRepository).findByDni("12345678");
    }
}
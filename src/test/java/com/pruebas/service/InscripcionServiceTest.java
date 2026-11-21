package com.pruebas.service;

import com.pruebas.model.Inscripcion;
import com.pruebas.model.services.InscripcionService;
import com.pruebas.repository.InscripcionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InscripcionServiceTest {

    @Mock
    private InscripcionRepository inscripcionRepository;

    @InjectMocks
    private InscripcionService inscripcionService;

    private Inscripcion inscripcion;

    @BeforeEach
    void setUp() {
        inscripcion = new Inscripcion();
        inscripcion.setId(1L);
        inscripcion.setCodigoEstudiante("I2024077");
        inscripcion.setCursoId(3L);
    }

    @Test
    void testInscribirEstudianteCorrectamente() {
        when(inscripcionRepository.existsByCodigoEstudianteAndCursoId("I2024077", 3L)).thenReturn(false);
        when(inscripcionRepository.save(any(Inscripcion.class))).thenReturn(inscripcion);

        Inscripcion resultado = inscripcionService.inscribir(inscripcion);

        assertNotNull(resultado);
        assertEquals("I2024077", resultado.getCodigoEstudiante());
        assertEquals(3L, resultado.getCursoId());
        verify(inscripcionRepository, times(1)).save(any(Inscripcion.class));
    }

    @Test
    void testInscribirEstudianteYaInscrito() {
        when(inscripcionRepository.existsByCodigoEstudianteAndCursoId("I2024077", 3L)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            inscripcionService.inscribir(inscripcion);
        });

        assertEquals("Estudiante ya inscrito", exception.getMessage());
        verify(inscripcionRepository, never()).save(any(Inscripcion.class));
    }

    @Test
    void testCancelarInscripcionExistente() {
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcion));
        doNothing().when(inscripcionRepository).delete(inscripcion);

        assertDoesNotThrow(() -> {
            inscripcionService.cancelar(1L);
        });

        verify(inscripcionRepository, times(1)).delete(inscripcion);
    }
}
package com.example.votacao.service;

import com.example.votacao.exception.ValidacaoException;
import com.example.votacao.model.Pauta;
import com.example.votacao.repository.PautaRepository;
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
public class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    private Pauta pauta;

    @BeforeEach
    public void setUp() {
        pauta = new Pauta();
        pauta.setId(1L);
        pauta.setDescricao("Descrição da Pauta");
    }

    @Test
    public void testIncluir() {
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

        Pauta result = pautaService.incluir(pauta);

        assertNotNull(result);
        assertEquals(pauta.getId(), result.getId());
        verify(pautaRepository).save(pauta);
    }

    @Test
    public void testAlterar() throws ValidacaoException {
        Pauta pautaRecebida = new Pauta();
        pautaRecebida.setDescricao("Descrição Alterada");

        when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(pauta));
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pautaRecebida);

        Pauta result = pautaService.alterar(pautaRecebida, 1L);

        assertNotNull(result);
        assertEquals(pauta.getId(), result.getId());
        assertEquals("Descrição Alterada", result.getDescricao());
        verify(pautaRepository).findById(1L);
        verify(pautaRepository).save(pautaRecebida);
    }

    @Test
    public void testDeletar() {
        doNothing().when(pautaRepository).deleteById(anyLong());

        pautaService.deletar(1L);

        verify(pautaRepository).deleteById(1L);
    }

    @Test
    public void testGetPauta() throws ValidacaoException {
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(pauta));

        Pauta result = pautaService.getPauta(1L);

        assertNotNull(result);
        assertEquals(pauta.getId(), result.getId());
        verify(pautaRepository).findById(1L);
    }

    @Test
    public void testGetPautaThrowsException() {
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ValidacaoException.class, () -> pautaService.getPauta(1L));

        assertTrue(exception.getMessage().contains("Pauta não encontrada"));
        verify(pautaRepository).findById(1L);
    }
}

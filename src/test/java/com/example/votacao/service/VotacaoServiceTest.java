package com.example.votacao.service;

import com.example.votacao.dto.request.AberturaVotacaoRequest;
import com.example.votacao.dto.request.RegistroVotacaoRequest;
import com.example.votacao.dto.response.ResultadoResponse;
import com.example.votacao.exception.ValidacaoException;
import com.example.votacao.model.Pauta;
import com.example.votacao.model.Usuario;
import com.example.votacao.model.Votacao;
import com.example.votacao.model.Voto;
import com.example.votacao.repository.VotacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VotacaoServiceTest {

    @Mock
    private PautaService pautaService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private VotacaoRepository votacaoRepository;

    @InjectMocks
    private VotacaoService votacaoService;

    private Pauta pauta;
    private Votacao votacao;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        pauta = new Pauta();
        pauta.setId(1L);

        votacao = new Votacao();
        votacao.setPauta(pauta);
        votacao.setDataInicio(LocalDateTime.now());
        votacao.setDuracaoMinutos(10L);

        usuario = new Usuario();
        usuario.setId(1L);
    }

    @Test
    public void testAberturaVotacao() throws ValidacaoException {
        when(pautaService.getPauta(anyLong())).thenReturn(pauta);
        when(votacaoRepository.findByPauta(any(Pauta.class))).thenReturn(Optional.empty());
        when(votacaoRepository.save(any(Votacao.class))).thenReturn(votacao);

        AberturaVotacaoRequest aberturaRequest = new AberturaVotacaoRequest();
        aberturaRequest.setIdPauta(1L);
        aberturaRequest.setDuracao(10L);

        votacaoService.aberturaVotacao(aberturaRequest);

        verify(votacaoRepository).save(any(Votacao.class));
    }

    @Test
    public void testAberturaVotacaoThrowsExceptionIfAlreadyExists() throws ValidacaoException {
        when(pautaService.getPauta(anyLong())).thenReturn(pauta);
        when(votacaoRepository.findByPauta(any(Pauta.class))).thenReturn(Optional.of(votacao));

        AberturaVotacaoRequest aberturaRequest = new AberturaVotacaoRequest();
        aberturaRequest.setIdPauta(1L);

        Exception exception = assertThrows(ValidacaoException.class, () -> votacaoService.aberturaVotacao(aberturaRequest));

        assertTrue(exception.getMessage().contains("Votacão da pauta já foi iniciada"));
        verify(votacaoRepository, never()).save(any(Votacao.class));
    }

    @Test
    public void testRetornaResultado() throws ValidacaoException {
        when(pautaService.getPauta(anyLong())).thenReturn(pauta);
        when(votacaoRepository.findByPauta(any(Pauta.class))).thenReturn(Optional.of(votacao));

        Voto votoSim = new Voto();
        votoSim.setVoto(true);
        Voto votoNao = new Voto();
        votoNao.setVoto(false);

        votacao.getVotos().add(votoSim);
        votacao.getVotos().add(votoNao);

        ResultadoResponse resultado = votacaoService.retornaResultado(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getPauta().getId());
        assertEquals(2, resultado.getQtdVotos());
        assertEquals(1, resultado.getQtdSim());
        assertEquals(1, resultado.getQtdNao());
        assertFalse(resultado.isAprovada());
    }

    @Test
    public void testRegistraVoto() throws ValidacaoException {
        when(pautaService.getPauta(anyLong())).thenReturn(pauta);
        when(votacaoRepository.findByPauta(any(Pauta.class))).thenReturn(Optional.of(votacao));
        when(usuarioService.getUsuario(anyLong())).thenReturn(usuario);
        when(votacaoRepository.save(any(Votacao.class))).thenReturn(votacao);

        RegistroVotacaoRequest registroRequest = new RegistroVotacaoRequest();
        registroRequest.setIdPauta(1L);
        registroRequest.setIdUsuario(1L);
        registroRequest.setVoto(true);

        votacaoService.registraVoto(registroRequest);

        verify(votacaoRepository).save(any(Votacao.class));
    }

    @Test
    public void testRegistraVotoThrowsExceptionIfAlreadyVoted() throws ValidacaoException {
        when(pautaService.getPauta(anyLong())).thenReturn(pauta);
        when(votacaoRepository.findByPauta(any(Pauta.class))).thenReturn(Optional.of(votacao));

        Voto voto = new Voto();
        voto.setUsuario(usuario);
        votacao.getVotos().add(voto);

        RegistroVotacaoRequest registroRequest = new RegistroVotacaoRequest();
        registroRequest.setIdPauta(1L);
        registroRequest.setIdUsuario(1L);
        registroRequest.setVoto(true);

        Exception exception = assertThrows(ValidacaoException.class, () -> votacaoService.registraVoto(registroRequest));

        assertTrue(exception.getMessage().contains("Usuário já votou nessa pauta"));
        verify(votacaoRepository, never()).save(any(Votacao.class));
    }

    @Test
    public void testRegistraVotoThrowsExceptionIfVotacaoEncerrada() throws ValidacaoException {
        when(pautaService.getPauta(anyLong())).thenReturn(pauta);
        when(votacaoRepository.findByPauta(any(Pauta.class))).thenReturn(Optional.of(votacao));

        votacao.setDataInicio(LocalDateTime.now().minusMinutes(20));

        RegistroVotacaoRequest registroRequest = new RegistroVotacaoRequest();
        registroRequest.setIdPauta(1L);
        registroRequest.setIdUsuario(1L);
        registroRequest.setVoto(true);

        Exception exception = assertThrows(ValidacaoException.class, () -> votacaoService.registraVoto(registroRequest));

        assertTrue(exception.getMessage().contains("Votacão já foi encerrada"));
        verify(votacaoRepository, never()).save(any(Votacao.class));
    }
}

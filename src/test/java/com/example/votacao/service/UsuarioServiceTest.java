package com.example.votacao.service;

import com.example.votacao.exception.ValidacaoException;
import com.example.votacao.model.Usuario;
import com.example.votacao.repository.UsuarioRepository;
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
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Nome do Usuário");
    }

    @Test
    public void testIncluir() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioService.incluir(usuario);

        assertNotNull(result);
        assertEquals(usuario.getId(), result.getId());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    public void testAlterar() throws ValidacaoException {
        Usuario usuarioRecebido = new Usuario();
        usuarioRecebido.setNome("Nome Alterado");

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioRecebido);



        Usuario result = usuarioService.alterar(usuarioRecebido, 1L);

        assertNotNull(result);
        assertEquals(usuario.getId(), result.getId());
        assertEquals("Nome Alterado", result.getNome());
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(usuarioRecebido);
    }

    @Test
    public void testDeletar() {
        doNothing().when(usuarioRepository).deleteById(anyLong());

        usuarioService.deletar(1L);

        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    public void testGetUsuario() throws ValidacaoException {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.getUsuario(1L);

        assertNotNull(result);
        assertEquals(usuario.getId(), result.getId());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    public void testGetUsuarioThrowsException() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ValidacaoException.class, () -> usuarioService.getUsuario(1L));

        assertTrue(exception.getMessage().contains("Usuário não encontrado"));
        verify(usuarioRepository).findById(1L);
    }
}

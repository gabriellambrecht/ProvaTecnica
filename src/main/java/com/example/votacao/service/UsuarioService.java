package com.example.votacao.service;

import com.example.votacao.exception.ValidacaoException;
import com.example.votacao.model.Usuario;
import com.example.votacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario incluir(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public Usuario alterar(Usuario usuarioRecebido, Long id) throws ValidacaoException {
		Usuario usuario = getUsuario(id);
		usuarioRecebido.setId(usuario.getId());
		return usuarioRepository.save(usuarioRecebido);
	}

	public void deletar(Long id) {
		usuarioRepository.deleteById(id);
	}
	
	
	public Usuario getUsuario(Long idUsuario) throws ValidacaoException {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
		return usuarioOptional.orElseThrow(() -> new ValidacaoException("Usuário não encontrado"));
	}

}

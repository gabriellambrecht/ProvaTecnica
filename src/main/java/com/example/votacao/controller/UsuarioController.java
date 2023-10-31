package com.example.votacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.votacao.ValidacaoException;
import com.example.votacao.model.Usuario;
import com.example.votacao.repository.UsuarioRepository;
import com.example.votacao.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarios;

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public List<Usuario> getAll() {
		return usuarios.findAll();
	}
	
	@PostMapping
	public Usuario adicionar(@RequestBody @Valid Usuario usuario) {
		return usuarioService.incluir(usuario);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity alterar(@PathVariable Long id, @RequestBody @Valid Usuario usuario) {
		try {
			return ResponseEntity.ok(usuarioService.alterar(usuario, id));
		} catch (ValidacaoException e) {

			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public void deletar(@PathVariable Long id) {
		usuarioService.deletar(id);
	}

}

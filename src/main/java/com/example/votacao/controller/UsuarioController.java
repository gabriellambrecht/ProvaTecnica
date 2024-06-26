package com.example.votacao.controller;

import com.example.votacao.exception.ValidacaoException;
import com.example.votacao.model.Usuario;
import com.example.votacao.repository.UsuarioRepository;
import com.example.votacao.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuario", description = "API para manutenção de usuário")
@RestController
@RequestMapping("v1/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarios;

	@Autowired
	private UsuarioService usuarioService;
	
	@Operation(summary = "Retorna todos os usuários cadastrados",
		       description = "Retorna uma lista de todos os usuários cadastrados")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class)), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
				  })
	@GetMapping
	public List<Usuario> getAll() {
		return usuarios.findAll();
	}
	
	@Operation(summary = "Inclusão de usuário",
		       description = "Inclusão de usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Usuario.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
				  })
	@PostMapping
	public Usuario adicionar(@RequestBody @Valid Usuario usuario) {
		return usuarioService.incluir(usuario);
	}
	
	@Operation(summary = "Alteração de usuário",
		       description = "Alteração de usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Usuario.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
				  })
	@PutMapping(value = "/{id}")
	public Usuario alterar(@PathVariable Long id, @RequestBody @Valid Usuario usuario) throws ValidacaoException {
		return usuarioService.alterar(usuario, id);
	}
	
	@Operation(summary = "Exclusão de usuário",
		       description = "Exclusão de usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
				  })
	@DeleteMapping(value = "/{id}")
	public void deletar(@PathVariable Long id) {
		usuarioService.deletar(id);
	}

}

package com.example.votacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.votacao.exception.ValidacaoException;
import com.example.votacao.model.Pauta;
import com.example.votacao.repository.PautaRepository;
import com.example.votacao.service.PautaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Pauta", description = "API para manutenção de pautas")
@RestController
@RequestMapping("v1/pauta")
public class PautaController {

	@Autowired
	private PautaRepository pautas;
	
	@Autowired
	private PautaService pautaService;
	
	@Operation(summary = "Retorna todas as pautas cadastradas",
		       description = "Retorna uma lista de todas as pautas cadastradas")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = Pauta.class)), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
				  })
	@GetMapping
	public List<Pauta> getAll() {
		return pautas.findAll();
	}
	
	@Operation(summary = "Inclusão de pauta",
		       description = "Inclusão de pauta")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Pauta.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
				  })
	@PostMapping
	public Pauta adicionar(@RequestBody @Valid Pauta pauta) {
		return pautaService.incluir(pauta);
	}
	
	@Operation(summary = "Alteração de pauta",
		       description = "Alteração de pauta")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Pauta.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
				  })
	@PutMapping(value = "/{id}")
	public Pauta alterar(@PathVariable Long id, @RequestBody @Valid Pauta pauta) throws ValidacaoException {
		return pautaService.alterar(pauta, id);
	}
	
	@Operation(summary = "Exclusão de pauta",
		       description = "Exclusão de pauta")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
				  })
	@DeleteMapping(value = "/{id}")
	public void deletar(@PathVariable Long id) {
		pautaService.deletar(id);
	}

}

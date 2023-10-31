package com.example.votacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.votacao.ValidacaoException;
import com.example.votacao.dto.request.AberturaVotacaoRequest;
import com.example.votacao.dto.request.RegistroVotacaoRequest;
import com.example.votacao.dto.response.ResultadoResponse;
import com.example.votacao.model.Usuario;
import com.example.votacao.service.VotacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Votação", description = "API para controle de votação de pautas")
@RestController
@RequestMapping("v1/votacao")
public class VotacaoController {
	
	@Autowired
	private VotacaoService votacaoService;
	
	@Operation(summary = "Abertura de Votaçao",
		       description = "Realiza a abertura de votação de uma pauta")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
				  })
	@PostMapping(value = "abertura")
	public ResponseEntity<String> aberturaVotacao(@RequestBody @Valid AberturaVotacaoRequest aberturaVotacao) {
		try {
			votacaoService.aberturaVotacao(aberturaVotacao);
			return new ResponseEntity<>("Votação aberta corretamente", HttpStatus.OK);
		} catch (ValidacaoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@Operation(summary = "Registro de Voto na Votaçao",
		       description = "Realiza a registro de voto na votação de uma pauta")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
				  })
	@PostMapping(value = "voto")
	public ResponseEntity<String> registraVoto(@RequestBody @Valid RegistroVotacaoRequest registroVotacao) {
		try {
			votacaoService.registraVoto(registroVotacao);
			return new ResponseEntity<>("Voto registrado corretamente.", HttpStatus.OK);
		} catch (ValidacaoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@Operation(summary = "Resultado de Votaçao",
		       description = "Retorna o resultado da votação de uma pauta")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResultadoResponse.class), mediaType = "application/json") }),
		@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
				  })
	@PostMapping(value = "resultado/{idPauta}")
	public ResponseEntity<?> retornaResultado(@PathVariable Long idPauta) {
		try {
			ResultadoResponse resultado = votacaoService.retornaResultado(idPauta);
			return new ResponseEntity<>(resultado, HttpStatus.OK);
		} catch (ValidacaoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}

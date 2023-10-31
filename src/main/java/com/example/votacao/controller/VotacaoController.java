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
import com.example.votacao.service.VotacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/votacao")
public class VotacaoController {
	
	@Autowired
	private VotacaoService votacaoService;
	
	@PostMapping(value = "abertura")
	public ResponseEntity<String> aberturaVotacao(@RequestBody @Valid AberturaVotacaoRequest aberturaVotacao) {
		try {
			votacaoService.aberturaVotacao(aberturaVotacao);
			return new ResponseEntity<>("Votação aberta corretamente", HttpStatus.OK);
		} catch (ValidacaoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "voto")
	public ResponseEntity<String> registraVoto(@RequestBody @Valid RegistroVotacaoRequest registroVotacao) {
		try {
			votacaoService.registraVoto(registroVotacao);
			return new ResponseEntity<>("Voto registrado corretamente.", HttpStatus.OK);
		} catch (ValidacaoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
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

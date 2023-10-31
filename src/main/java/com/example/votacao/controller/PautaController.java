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
import com.example.votacao.model.Pauta;
import com.example.votacao.repository.PautaRepository;
import com.example.votacao.service.PautaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/pauta")
public class PautaController {

	@Autowired
	private PautaRepository pautas;
	
	@Autowired
	private PautaService pautaService;

	@GetMapping
	public List<Pauta> getAll() {
		return pautas.findAll();
	}
	
	@PostMapping
	public Pauta adicionar(@RequestBody @Valid Pauta pauta) {
		return pautaService.incluir(pauta);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity alterar(@PathVariable Long id, @RequestBody @Valid Pauta pauta) {
		try {
			return ResponseEntity.ok(pautaService.alterar(pauta, id));
		} catch (ValidacaoException e) {

			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public void deletar(@PathVariable Long id) {
		pautaService.deletar(id);
	}

}

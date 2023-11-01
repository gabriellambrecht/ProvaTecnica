package com.example.votacao.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.votacao.exception.ValidacaoException;
import com.example.votacao.model.Pauta;
import com.example.votacao.repository.PautaRepository;

@Service
public class PautaService {
	
	@Autowired
	private PautaRepository pautaRepository;
	
	public Pauta incluir(Pauta pauta) {	
		return pautaRepository.save(pauta);
	}
	
	public Pauta alterar(Pauta pautaRecebida, Long id) throws ValidacaoException {
		Pauta pauta = getPauta(id);
		pautaRecebida.setId(pauta.getId());
		return pautaRepository.save(pautaRecebida);
	}

	public void deletar(Long id) {
		pautaRepository.deleteById(id);
	}
	
	
	public Pauta getPauta(Long idPauta) throws ValidacaoException {
		Optional<Pauta> pautaOptional = pautaRepository.findById(idPauta);
		return pautaOptional.orElseThrow(() -> new ValidacaoException("Pauta não encontrada"));
	}

}

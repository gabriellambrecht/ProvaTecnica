package com.example.votacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.votacao.model.Pauta;
import com.example.votacao.model.Votacao;


public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
	
	Optional<Votacao> findByPauta(Pauta pauta);

}

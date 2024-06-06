package com.example.votacao.repository;

import com.example.votacao.model.Pauta;
import com.example.votacao.model.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
	
	Optional<Votacao> findByPauta(Pauta pauta);

}

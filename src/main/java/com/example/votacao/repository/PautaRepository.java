package com.example.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.votacao.model.Pauta;

public interface PautaRepository  extends JpaRepository<Pauta, Long> {

}

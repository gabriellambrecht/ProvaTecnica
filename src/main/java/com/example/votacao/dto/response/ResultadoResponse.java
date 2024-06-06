package com.example.votacao.dto.response;

import com.example.votacao.model.Pauta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadoResponse {
	
	private Pauta pauta;
	
	private boolean aprovada;
	
	private long qtdSim;
	
	private long qtdNao;
	
	private int qtdVotos;

}

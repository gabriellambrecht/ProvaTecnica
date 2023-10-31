package com.example.votacao.dto.request;

import jakarta.validation.constraints.NotNull;

public class AberturaVotacaoRequest {
	
	@NotNull
	private Long idPauta;
	
	private Long duracao;

	public Long getIdPauta() {
		return idPauta;
	}

	public void setIdPauta(Long idPauta) {
		this.idPauta = idPauta;
	}

	public Long getDuracao() {
		return duracao;
	}

	public void setDuracao(Long duracao) {
		this.duracao = duracao;
	}	

}

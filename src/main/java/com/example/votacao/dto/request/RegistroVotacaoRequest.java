package com.example.votacao.dto.request;

import jakarta.validation.constraints.NotNull;

public class RegistroVotacaoRequest {
	
	@NotNull
	private Long idPauta;
	
	@NotNull
	private Long idUsuario;
	
	@NotNull
	private Boolean voto;

	public Long getIdPauta() {
		return idPauta;
	}

	public void setIdPauta(Long idPauta) {
		this.idPauta = idPauta;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Boolean getVoto() {
		return voto;
	}

	public void setVoto(Boolean voto) {
		this.voto = voto;
	}	

}

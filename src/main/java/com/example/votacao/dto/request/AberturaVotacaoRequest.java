package com.example.votacao.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AberturaVotacaoRequest {
	
	@NotNull
	private Long idPauta;
	
	private Long duracao;

}

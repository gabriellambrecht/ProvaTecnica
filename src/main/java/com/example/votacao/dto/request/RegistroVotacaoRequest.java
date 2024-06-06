package com.example.votacao.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroVotacaoRequest {
	
	@NotNull
	private Long idPauta;
	
	@NotNull
	private Long idUsuario;
	
	@NotNull
	private Boolean voto;

}

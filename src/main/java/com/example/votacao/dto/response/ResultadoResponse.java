package com.example.votacao.dto.response;

import com.example.votacao.model.Pauta;

public class ResultadoResponse {
	
	private Pauta pauta;
	
	private boolean aprovada;
	
	private long qtdSim;
	
	private long qtdNao;
	
	private int qtdVotos;

	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}

	public boolean isAprovada() {
		return aprovada;
	}

	public void setAprovada(boolean aprovada) {
		this.aprovada = aprovada;
	}

	public long getQtdSim() {
		return qtdSim;
	}

	public void setQtdSim(long qtdSim) {
		this.qtdSim = qtdSim;
	}

	public long getQtdNao() {
		return qtdNao;
	}

	public void setQtdNao(long qtdNao) {
		this.qtdNao = qtdNao;
	}

	public int getQtdVotos() {
		return qtdVotos;
	}

	public void setQtdVotos(int qtdVotos) {
		this.qtdVotos = qtdVotos;
	}

}

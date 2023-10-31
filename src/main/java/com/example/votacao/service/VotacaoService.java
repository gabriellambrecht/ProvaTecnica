package com.example.votacao.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.votacao.ValidacaoException;
import com.example.votacao.dto.request.AberturaVotacaoRequest;
import com.example.votacao.dto.request.RegistroVotacaoRequest;
import com.example.votacao.dto.response.ResultadoResponse;
import com.example.votacao.model.Pauta;
import com.example.votacao.model.Usuario;
import com.example.votacao.model.Votacao;
import com.example.votacao.model.Voto;
import com.example.votacao.repository.PautaRepository;
import com.example.votacao.repository.UsuarioRepository;
import com.example.votacao.repository.VotacaoRepository;

@Service
public class VotacaoService {
	
	@Autowired
	private PautaService pautaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private VotacaoRepository votacaoRepository;
	
	public void aberturaVotacao(AberturaVotacaoRequest abertura) throws ValidacaoException {

		Pauta pauta = pautaService.getPauta(abertura.getIdPauta());

		Optional<Votacao> votacaoOptional = votacaoRepository.findByPauta(pauta);
		if (!votacaoOptional.isEmpty()) {
			throw new ValidacaoException("Votacão da pauta já foi iniciada");
		}
		
		Votacao votacao = new Votacao();
		votacao.setDataInicio(LocalDateTime.now());
		if (abertura.getDuracao() != null) {
			votacao.setDuracaoMinutos(abertura.getDuracao());
		} else {
			votacao.setDuracaoMinutos(Long.valueOf(1));
		}
		votacao.setPauta(pauta);
		
		votacaoRepository.save(votacao);	
	}
	
	public ResultadoResponse retornaResultado(Long idPauta) throws ValidacaoException {
		Pauta pauta = pautaService.getPauta(idPauta);
		Votacao votacao = getVotacao(pauta);
		
		long qtdSim = votacao.getVotos().stream().filter(voto -> voto.isSim()).count();
		long qtdNao = votacao.getVotos().stream().filter(voto -> voto.isNao()).count();
		
		ResultadoResponse resultado = new ResultadoResponse();
		resultado.setPauta(pauta);
		resultado.setQtdVotos(votacao.getVotos().size());
		resultado.setQtdNao(qtdNao);
		resultado.setQtdSim(qtdSim);
		resultado.setAprovada(qtdSim > qtdNao);
		
		return resultado;
	}
	
	public void registraVoto(RegistroVotacaoRequest registro) throws ValidacaoException {
		Pauta pauta = pautaService.getPauta(registro.getIdPauta());
		Votacao votacao = getVotacao(pauta);
		if (isVotacaoEncerrada(votacao)) {
			throw new ValidacaoException("Votacão já foi encerrada");
		}
		if (verificaExistenciaVotoUsuario(votacao, registro.getIdUsuario())) {
			throw new ValidacaoException("Usuário já votou nessa pauta");
		}
		Usuario usuario = usuarioService.getUsuario(registro.getIdUsuario());
		
		Voto voto = new Voto();
		voto.setUsuario(usuario);
		voto.setVotacao(votacao);
		voto.setVoto(registro.getVoto());
		
		votacao.getVotos().add(voto);

		votacaoRepository.save(votacao);	
	}
	
	public boolean verificaExistenciaVotoUsuario(Votacao votacao, Long idUsuario) {
		return votacao.getVotos()
				.stream()
				.anyMatch(voto -> voto.getUsuario().getId() == idUsuario);
	}
	
	public boolean isVotacaoEncerrada(Votacao votacao) {
		return LocalDateTime.now().isAfter(votacao.getDateFinal());
	}
	
	public Votacao getVotacao(Pauta pauta) throws ValidacaoException {
		Optional<Votacao> votacaoOptional = votacaoRepository.findByPauta(pauta);
		if (votacaoOptional.isEmpty()) {
			throw new ValidacaoException("Pauta não encontrada");
		}
		return votacaoOptional.get();
	}
}

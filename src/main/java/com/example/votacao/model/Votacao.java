package com.example.votacao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "votacao")
public class Votacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
    @JoinColumn(name ="pauta_id", nullable = false)
	private Pauta pauta;
	
    @Column(nullable = false)
	private Long duracaoMinutos;
	
    @Column(nullable = false)
	private LocalDateTime dataInicio;
	
	@JsonIgnoreProperties("votacao")
	@OneToMany(mappedBy = "votacao", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Voto> votos;

	public Votacao() {
		votos = new ArrayList<>();
	}

	public LocalDateTime getDateFinal() {
		return dataInicio.plusMinutes(duracaoMinutos);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Votacao other = (Votacao) obj;
		return Objects.equals(id, other.id);
	}

}

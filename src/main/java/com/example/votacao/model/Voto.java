package com.example.votacao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "voto")
public class Voto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name ="votacao_id", nullable = false)
	private Votacao votacao;
	
	@ManyToOne
    @JoinColumn(name ="usuario_id", nullable = false)
	private Usuario usuario;
	
    @Column
	private boolean voto;

	public boolean isSim() {
		return voto;
	}

	public boolean isNao() {
		return !voto;
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
		Voto other = (Voto) obj;
		return Objects.equals(id, other.id);
	}

}

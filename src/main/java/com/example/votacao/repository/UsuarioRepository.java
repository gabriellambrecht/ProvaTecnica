package com.example.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.votacao.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}

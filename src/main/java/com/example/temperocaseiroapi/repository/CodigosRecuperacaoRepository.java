package com.example.temperocaseiroapi.repository;

import com.example.temperocaseiroapi.model.Usuario;
import com.example.temperocaseiroapi.model.CodigosRecuperacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodigosRecuperacaoRepository extends JpaRepository<CodigosRecuperacao, Long> {

    Optional<CodigosRecuperacao> findByUsuarioAndCodigo(
            Usuario usuario,
            String codigo
    );

    Optional<CodigosRecuperacao> findTopByUsuarioAndUsadoFalseOrderByCriadoEmDesc(Usuario usuario);
}
package com.example.temperocaseiroapi.repository;

import com.example.temperocaseiroapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> { // -> esse extends vem da onde? 

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
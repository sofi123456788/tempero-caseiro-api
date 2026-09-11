package com.example.temperocaseiroapi.repository;

import com.example.temperocaseiroapi.model.CredencialEmail;
import com.example.temperocaseiroapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredencialEmailRepository extends JpaRepository<CredencialEmail, Long> {

    Optional<CredencialEmail> findByUsuario(Usuario usuario);
}
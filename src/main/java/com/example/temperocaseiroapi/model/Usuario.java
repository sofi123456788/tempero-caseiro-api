package com.example.temperocaseiroapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo", nullable = false, length = 120)
    private String nomeCompleto;

    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "genero", length = 20)
    private String genero;

    @Column(name = "status_conta", nullable = false, length = 20)
    private String statusConta = "ativo";

    @Column(name = "motivo_bloqueio")
    private String motivoBloqueio;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(name = "excluido_em")
    private LocalDateTime excluidoEm;

    public Usuario() {
    }

    public Usuario(String nomeCompleto, String email, String genero) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.genero = genero;
        this.statusConta = "ativo";
    }

    @PrePersist
    public void antesDeSalvar() { // porção do código que roda antes de salvar o usuário no banco
        this.criadoEm = LocalDateTime.now(); // indica quando o usuário foi criado
        this.atualizadoEm = LocalDateTime.now(); // atualiza o status do usuário (data e hora de agora)
    }

    @PreUpdate // para o caso de acontecer alguma alteração depois da criação do usuário no banco 
    public void antesDeAtualizar() {
        this.atualizadoEm = LocalDateTime.now(); // atualiza a data e a hora da alteração 
    }

    public Long getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getStatusConta() {
        return statusConta;
    }

    public void setStatusConta(String statusConta) {
        this.statusConta = statusConta;
    }

    public String getMotivoBloqueio() {
        return motivoBloqueio;
    }

    public void setMotivoBloqueio(String motivoBloqueio) {
        this.motivoBloqueio = motivoBloqueio;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public LocalDateTime getExcluidoEm() {
        return excluidoEm;
    }

    public void setExcluidoEm(LocalDateTime excluidoEm) {
        this.excluidoEm = excluidoEm;
    }
}
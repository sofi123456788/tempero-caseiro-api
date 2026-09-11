package com.example.temperocaseiroapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "codigos_recuperacao")

public class CodigosRecuperacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;  

    @Column(name = "codigo", nullable = false, length = 10)
    private String codigo; 

    @Column(name = "usado", nullable = false)
    private boolean usado; 

    @Column(name= "criado_em")
    private LocalDateTime criadoEm; 

    @Column(name= "expira_em")
    private LocalDateTime expiraEm; 

    public CodigosRecuperacao() { // qual sua utilidade? 

    }

    @PrePersist
    public void antesDeSalvar() {
        this.usado = false;
        this.criadoEm = LocalDateTime.now();
        this.expiraEm = LocalDateTime.now().plusMinutes(10);
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getExpiraEm() {
        return expiraEm;
    }
}



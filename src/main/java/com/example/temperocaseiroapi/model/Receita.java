package com.example.temperocaseiroapi.model;

import jakarta.persistence.*; // Importa as anotações do JPA para mapear a classe para o banco de dados.

@Entity // Indica que esta classe representa uma tabela do banco.
@Table(name = "receita") // Informa que a tabela correspondente se chama "receita".
public class Receita {

    @Id // Define o atributo como chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco gera automaticamente o ID.
    private Integer id;

    @Column(nullable = false, length = 100)
    // Campo obrigatório para armazenar o título da receita.
    private String titulo;

    @Column(nullable = false, length = 50)
    // Campo obrigatório para armazenar a categoria da receita.
    private String categoria;

    @Column(nullable = false, columnDefinition = "TEXT")
    // Campo obrigatório para armazenar os ingredientes.
    private String ingredientes;

    @Column(name = "modo_preparo", nullable = false, columnDefinition = "TEXT")
    // Mapeia o atributo modoPreparo para a coluna "modo_preparo".
    private String modoPreparo;

    @Column(name = "tempo_preparo", length = 50)
    // Mapeia o atributo tempoPreparo para a coluna "tempo_preparo".
    private String tempoPreparo;

    @Column(length = 50)
    // Armazena o rendimento da receita.
    private String rendimento;

    @Column(name = "usuario_id", nullable = false)
    // Guarda o ID do usuário que cadastrou a receita.
    private Integer usuarioId;

    // Construtor vazio obrigatório para o JPA.
    public Receita() {
    }

    // Construtor utilizado para criar um objeto Receita já preenchido.
    public Receita(String titulo, String categoria, String ingredientes,
                   String modoPreparo, String tempoPreparo,
                   String rendimento, Integer usuarioId) {

        this.titulo = titulo;
        this.categoria = categoria;
        this.ingredientes = ingredientes;
        this.modoPreparo = modoPreparo;
        this.tempoPreparo = tempoPreparo;
        this.rendimento = rendimento;
        this.usuarioId = usuarioId;
    }

    // Retorna o ID da receita.
    public Integer getId() {
        return id;
    }

    // Retorna o título da receita.
    public String getTitulo() {
        return titulo;
    }

    // Define o título da receita.
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Retorna a categoria da receita.
    public String getCategoria() {
        return categoria;
    }

    // Define a categoria da receita.
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Retorna os ingredientes da receita.
    public String getIngredientes() {
        return ingredientes;
    }

    // Define os ingredientes da receita.
    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    // Retorna o modo de preparo.
    public String getModoPreparo() {
        return modoPreparo;
    }

    // Define o modo de preparo.
    public void setModoPreparo(String modoPreparo) {
        this.modoPreparo = modoPreparo;
    }

    // Retorna o tempo de preparo.
    public String getTempoPreparo() {
        return tempoPreparo;
    }

    // Define o tempo de preparo.
    public void setTempoPreparo(String tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    // Retorna o rendimento da receita.
    public String getRendimento() {
        return rendimento;
    }

    // Define o rendimento da receita.
    public void setRendimento(String rendimento) {
        this.rendimento = rendimento;
    }

    // Retorna o ID do usuário que cadastrou a receita.
    public Integer getUsuarioId() {
        return usuarioId;
    }

    // Define o ID do usuário que cadastrou a receita.
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
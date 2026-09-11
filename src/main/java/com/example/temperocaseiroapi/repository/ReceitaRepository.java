package com.example.temperocaseiroapi.repository;

import com.example.temperocaseiroapi.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//ELE QUE CONVERSA COM O BANCO DE DADOS

/*
 * Repository responsável por realizar operações no banco
 * relacionadas à tabela receita.
 *
 * O JpaRepository já fornece automaticamente métodos como:
 *
 * save()
 * findAll()
 * findById()
 * delete()
 * deleteById()
 * existsById()
 *
 * sem precisarmos programá-los.
 */

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Integer> {

}
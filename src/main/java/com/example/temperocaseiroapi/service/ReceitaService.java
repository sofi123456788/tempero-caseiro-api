package com.example.temperocaseiroapi.service;

import com.example.temperocaseiroapi.model.Receita;
import com.example.temperocaseiroapi.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    // Salvar receita
    public Receita salvar(Receita receita) {
        return receitaRepository.save(receita);
    }

    // Listar todas as receitas
    public List<Receita> listarTodas() {
        return receitaRepository.findAll();
    }

    // Buscar por ID
    public Receita buscarPorId(Integer id) {
        return receitaRepository.findById(id).orElse(null);
    }

    // Excluir
    public void excluir(Integer id) {
        receitaRepository.deleteById(id);
    }
}
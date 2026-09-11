package com.example.temperocaseiroapi.controller;

import com.example.temperocaseiroapi.model.Receita;
import com.example.temperocaseiroapi.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
@CrossOrigin("*")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    // Adicionar receita
    @PostMapping
    public Receita salvar(@RequestBody Receita receita) {
        return receitaService.salvar(receita);
    }

    // Listar receitas
    @GetMapping
    public List<Receita> listar() {
        return receitaService.listarTodas();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Receita buscar(@PathVariable Integer id) {
        return receitaService.buscarPorId(id);
    }

    // Excluir receita
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        receitaService.excluir(id);
    }
}
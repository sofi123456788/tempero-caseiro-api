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
    // Toda receita nova deve começar como "não aprovada".
    @PostMapping
    public Receita salvar(@RequestBody Receita receita) {

        // Garante que uma receita nova fique aguardando aprovação.
        receita.setAprovada(false);

        return receitaService.salvar(receita);
    }

    // Listar somente receitas aprovadas
    // O aplicativo Android vai receber apenas as receitas que foram aprovadas.
    @GetMapping
    public List<Receita> listar() {
        return receitaService.listarAprovadas();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Receita buscar(@PathVariable Integer id) {
        return receitaService.buscarPorId(id);
    }

    // Aprovar uma receita
    // Exemplo: PUT /receitas/5/aprovar
    @PutMapping("/{id}/aprovar")
    public Receita aprovar(@PathVariable Integer id) {

        Receita receita = receitaService.buscarPorId(id);

        if (receita == null) {
            return null;
        }

        // Altera a receita para aprovada.
        receita.setAprovada(true);

        return receitaService.salvar(receita);
    }

    // Excluir receita
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        receitaService.excluir(id);
    }
}
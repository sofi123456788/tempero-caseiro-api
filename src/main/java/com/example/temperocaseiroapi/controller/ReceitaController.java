package com.example.temperocaseiroapi.controller;

import com.example.temperocaseiroapi.model.Receita;
import com.example.temperocaseiroapi.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Operações HTTP de receitas.
 *
 * ATENÇÃO antes de disponibilizar em produção:
 * - Restringir a aprovação e exclusão a usuários autorizados.
 * - Avaliar a necessidade de CORS irrestrito (*).
 * - Conferir a autorização do acesso a receitas não aprovadas por ID.
 *
 * As rotas foram preservadas nesta revisão para não quebrar o Android.
 */
@RestController
@RequestMapping("/receitas")
@CrossOrigin("*")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    /** Cria uma receita ainda não aprovada para avaliação. */
    @PostMapping
    public Receita salvar(@RequestBody Receita receita) {
        receita.setAprovada(false);
        return receitaService.salvar(receita);
    }

    /** Retorna somente receitas aprovadas. */
    @GetMapping
    public List<Receita> listar() {
        return receitaService.listarAprovadas();
    }

    /** Busca por ID; revisar autorização para receitas pendentes. */
    @GetMapping("/{id}")
    public Receita buscar(@PathVariable Integer id) {
        return receitaService.buscarPorId(id);
    }

    /**
     * Aprova uma receita.
     * TODO segurança: proteger este endpoint no servidor.
     */
    @PutMapping("/{id}/aprovar")
    public Receita aprovar(@PathVariable Integer id) {
        Receita receita = receitaService.buscarPorId(id);
        if (receita == null) {
            return null;
        }
        receita.setAprovada(true);
        return receitaService.salvar(receita);
    }

    /**
     * Exclui uma receita por ID.
     * TODO segurança: verificar autenticação e permissão.
     */
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        receitaService.excluir(id);
    }
}

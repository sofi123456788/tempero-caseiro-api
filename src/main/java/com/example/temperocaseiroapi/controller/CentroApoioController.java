package com.example.temperocaseiroapi.controller;

import com.example.temperocaseiroapi.model.CentroApoio;
import com.example.temperocaseiroapi.repository.CentroApoioRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/centrosapoio")
public class CentroApoioController {

    private final CentroApoioRepository repository;

    public CentroApoioController(CentroApoioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CentroApoio> listarTodos() {
        return repository.findAll();
    }
}
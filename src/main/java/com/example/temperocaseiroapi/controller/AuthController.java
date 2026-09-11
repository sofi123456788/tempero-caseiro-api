package com.example.temperocaseiroapi.controller;

import com.example.temperocaseiroapi.dto.CadastroRequest;
import com.example.temperocaseiroapi.dto.LoginRequest;
import com.example.temperocaseiroapi.dto.ExcluirContaRequest;
import com.example.temperocaseiroapi.dto.RecEmailRequest;
import com.example.temperocaseiroapi.service.AuthService;
import com.example.temperocaseiroapi.dto.ValidarCodigoRequest;
import com.example.temperocaseiroapi.dto.RedefinirSenhaRequest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // indica que a classe recebe requisções HTTP e returna respostas
@RequestMapping("/auth") // tudo dentro dessa classe começa com /auth
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/cadastro") // cria a rota POST/auth/cadastro 
    public ResponseEntity<String> cadastrar(@RequestBody CadastroRequest request) { // recebe o JSON enviado para a API e transforma 
        // ... em um objeto do tipo CadastroRequest
        String resposta = authService.cadastrarUsuario(request); // passa os dados recebidos pro service que fará o real ação de cadastro 
        return ResponseEntity.ok(resposta);
    }
    
    @PostMapping("/login") // cria a rota POST /auth/login
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String resposta2 = authService.loginUsuario(request);
        return ResponseEntity.ok(resposta2);
    }

    @PostMapping("/excluir")
    public ResponseEntity<String> excluir(@RequestBody ExcluirContaRequest request) {
        String resposta3 = authService.excluirUsuario(request);
        return ResponseEntity.ok(resposta3);
    }

    @PostMapping("/recuperar")
    public ResponseEntity<String> recuperar(@RequestBody RecEmailRequest request) {
        String resposta4 = authService.gerarCodigo(request);
        return ResponseEntity.ok(resposta4);
    }

    @PostMapping("/validar")
    public ResponseEntity<String> validar(@RequestBody ValidarCodigoRequest request) {
        String resposta5 = authService.validarCodigo(request);
        return ResponseEntity.ok(resposta5);
    }

    @PostMapping("/alterar") 
    public ResponseEntity<String> alterar(@RequestBody RedefinirSenhaRequest request) {
        String resposta6 = authService.criarNovaSenha(request);
        return ResponseEntity.ok(resposta6);
    }
}
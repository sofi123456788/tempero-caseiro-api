package com.example.temperocaseiroapi.controller;

import com.example.temperocaseiroapi.dto.CadastroRequest;
import com.example.temperocaseiroapi.dto.ExcluirContaRequest;
import com.example.temperocaseiroapi.dto.LoginRequest;
import com.example.temperocaseiroapi.dto.RecEmailRequest;
import com.example.temperocaseiroapi.dto.RedefinirSenhaRequest;
import com.example.temperocaseiroapi.dto.ValidarCodigoRequest;
import com.example.temperocaseiroapi.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rotas HTTP relacionadas a contas e recuperação de acesso.
 *
 * O controller recebe os dados da requisição e delega as regras de negócio
 * para AuthService. Os caminhos e tipos de resposta foram preservados para
 * não alterar o contrato consumido pelo aplicativo existente.
 *
 * ATENÇÃO: a exclusão atualmente recebe apenas o e-mail. Antes de publicar
 * o aplicativo, proteger a operação com autenticação e comprovação de
 * titularidade no servidor.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /** Cria uma conta após validação pelo serviço. */
    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrar(@RequestBody CadastroRequest request) {
        return ResponseEntity.ok(authService.cadastrarUsuario(request));
    }

    /**
     * Autentica a pessoa usuária.
     * TODO: conferir o contrato de resposta: este método retorna String,
     * enquanto versões do aplicativo Android esperam JSON com token.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.loginUsuario(request));
    }

    /** Solicita exclusão de conta — requer reforço de autorização antes de produção. */
    @PostMapping("/excluir")
    public ResponseEntity<String> excluir(@RequestBody ExcluirContaRequest request) {
        return ResponseEntity.ok(authService.excluirUsuario(request));
    }

    /** Envia um código de recuperação para o e-mail cadastrado. */
    @PostMapping("/recuperar")
    public ResponseEntity<String> recuperar(@RequestBody RecEmailRequest request) {
        return ResponseEntity.ok(authService.gerarCodigo(request));
    }

    /** Confere o código de recuperação enviado. */
    @PostMapping("/validar")
    public ResponseEntity<String> validar(@RequestBody ValidarCodigoRequest request) {
        return ResponseEntity.ok(authService.validarCodigo(request));
    }

    /** Atualiza a senha após a verificação do código. */
    @PostMapping("/alterar")
    public ResponseEntity<String> alterar(@RequestBody RedefinirSenhaRequest request) {
        return ResponseEntity.ok(authService.criarNovaSenha(request));
    }
}

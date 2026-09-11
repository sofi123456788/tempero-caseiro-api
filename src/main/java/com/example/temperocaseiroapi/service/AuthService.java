package com.example.temperocaseiroapi.service;

import com.example.temperocaseiroapi.dto.CadastroRequest;
import com.example.temperocaseiroapi.model.CredencialEmail;
import com.example.temperocaseiroapi.model.Usuario;
import com.example.temperocaseiroapi.repository.CredencialEmailRepository;
import com.example.temperocaseiroapi.repository.UsuarioRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.temperocaseiroapi.dto.LoginRequest;
import java.util.Optional;
import com.example.temperocaseiroapi.dto.ExcluirContaRequest;
import java.time.LocalDateTime;

import com.example.temperocaseiroapi.dto.RecEmailRequest;
import com.example.temperocaseiroapi.model.CodigosRecuperacao;
import com.example.temperocaseiroapi.repository.CodigosRecuperacaoRepository;
import com.example.temperocaseiroapi.dto.ValidarCodigoRequest;
import com.example.temperocaseiroapi.dto.RedefinirSenhaRequest;
import com.example.temperocaseiroapi.service.EmailService;

import java.security.SecureRandom;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final CredencialEmailRepository credencialEmailRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CodigosRecuperacaoRepository codigosRecuperacaoRepository;
    private final EmailService emailService; 

    public AuthService(
            UsuarioRepository usuarioRepository,
            CredencialEmailRepository credencialEmailRepository,
            CodigosRecuperacaoRepository codigosRecuperacaoRepository,
            EmailService emailService

    ) {
        this.usuarioRepository = usuarioRepository;
        this.credencialEmailRepository = credencialEmailRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.codigosRecuperacaoRepository = codigosRecuperacaoRepository;
        this.emailService = emailService;
    }

    public String cadastrarUsuario(CadastroRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            return "E-mail já cadastrado";
        }

        Usuario usuario = new Usuario(
                request.getNomeCompleto(),
                request.getEmail(),
                request.getGenero()
        );

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        String senhaCriptografada = passwordEncoder.encode(request.getSenha());

        CredencialEmail credencialEmail = new CredencialEmail(
                usuarioSalvo,
                senhaCriptografada
        );

        credencialEmailRepository.save(credencialEmail);

        return "Cadastro realizado com sucesso";
    }

public String loginUsuario(LoginRequest request) {

    Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(request.getEmailLogin());

    if (usuarioOptional.isEmpty()) {
        return "E-mail não cadastrado";
    }

    Usuario usuario = usuarioOptional.get();

    Optional<CredencialEmail> credencialOptional = credencialEmailRepository.findByUsuario(usuario);

    if (credencialOptional.isEmpty()) {
        return "Credencial não encontrada";
    }

    CredencialEmail credencialEmail = credencialOptional.get();

    boolean senhaCorreta = passwordEncoder.matches(
            request.getSenhaLogin(),
            credencialEmail.getSenhaHash()
    );

    if (!senhaCorreta) {
        return "Senha incorreta";
    }

    return "Login realizado com sucesso";
}

public String excluirUsuario(ExcluirContaRequest request) {

    Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(request.getEmailExcluir());

    if (usuarioOptional.isEmpty()) {
        return "E-mail não cadastrado";
    }

    Usuario usuario = usuarioOptional.get();

    usuario.setStatusConta("excluido");
    usuario.setExcluidoEm(LocalDateTime.now());

    usuarioRepository.save(usuario);

    return "Conta excluída com sucesso";
  }


public String gerarCodigo(RecEmailRequest request) { // voordena a regra de recuperação

    Optional <Usuario> usuarioOptional = usuarioRepository.findByEmail(request.getEmailRec()); // cofere se o email que chega existe no bd

    if(usuarioOptional.isEmpty()) { // caso não exista...
        return "E-mail não cadastrado";
    }

    Usuario usuario = usuarioOptional.get(); // caso exista, cria um objeto usuario do tipo usuario

    Optional<CodigosRecuperacao> codigoExistente =
        codigosRecuperacaoRepository
                .findTopByUsuarioAndUsadoFalseOrderByCriadoEmDesc(usuario);

    if (codigoExistente.isPresent()) {

        CodigosRecuperacao ultimoCodigo = codigoExistente.get();

        LocalDateTime limiteReenvio = ultimoCodigo.getCriadoEm().plusSeconds(60);

    if (LocalDateTime.now().isBefore(limiteReenvio)) {
        return "Aguarde 60 segundos para solicitar outro código";
    }
}
    
    
    SecureRandom random = new SecureRandom(); // random para gerar os números aleatórios da recuperação
    int numero = random.nextInt(1000000);
    
    String codigoGerado = String.format("%06d", numero); 

    CodigosRecuperacao codigoRecuperacao = new CodigosRecuperacao(); // cria um novo objeto CodigoRecuperacao para o preenchimento das colunas

    codigoRecuperacao.setUsuario(usuario); // prenche a coluna do id do usuário
    codigoRecuperacao.setCodigo(codigoGerado); // prenche a coluna do código gerado 

    codigosRecuperacaoRepository.save(codigoRecuperacao);

    emailService.enviarCodigoRecuperacao(
        usuario.getEmail(),
        codigoGerado
    );

    return "Código de recuperação enviado com sucesso";
  }
 public String validarCodigo(ValidarCodigoRequest request) {

    Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(request.getEmailCod());

    if (usuarioOptional.isEmpty()) {
        return "E-mail não cadastrado";
    }

    Usuario usuario = usuarioOptional.get();

    Optional<CodigosRecuperacao> codigoOptional =
            codigosRecuperacaoRepository.findByUsuarioAndCodigo(
                    usuario,
                    request.getCodigo()
            );

    if (codigoOptional.isEmpty()) {
        return "Código inválido";
    }

    CodigosRecuperacao codigoRecuperacao =
            codigoOptional.get();

    if (codigoRecuperacao.isUsado()) {
        return "Código já utilizado";
    }

    if (LocalDateTime.now()
            .isAfter(codigoRecuperacao.getExpiraEm())) {
        return "Código expirado";
    }

    return "Código validado";
}
public String criarNovaSenha(RedefinirSenhaRequest request) {

    Optional<Usuario> usuarioOptional =
            usuarioRepository.findByEmail(request.getEmailRed());

    if (usuarioOptional.isEmpty()) {
        return "E-mail não cadastrado";
    }

    Usuario usuario = usuarioOptional.get();

    Optional<CodigosRecuperacao> codigoOptional =
            codigosRecuperacaoRepository.findByUsuarioAndCodigo(
                    usuario,
                    request.getCodigoRed()
            );

    if (codigoOptional.isEmpty()) {
        return "Código inválido";
    }

    CodigosRecuperacao codigoRecuperacao = codigoOptional.get();

    if (codigoRecuperacao.isUsado()) {
        return "Código já utilizado";
    }

    if (LocalDateTime.now().isAfter(codigoRecuperacao.getExpiraEm())) {
        return "Código expirado";
    }

    Optional<CredencialEmail> credencialOptional =
            credencialEmailRepository.findByUsuario(usuario);

    if (credencialOptional.isEmpty()) {
        return "Credencial não encontrada";
    }

    CredencialEmail credencial = credencialOptional.get();

    String novaSenhaHash =
            passwordEncoder.encode(request.getNovaSenha());

    credencial.setSenhaHash(novaSenhaHash);

    credencialEmailRepository.save(credencial);

    codigoRecuperacao.setUsado(true);

    codigosRecuperacaoRepository.save(codigoRecuperacao);

    return "Senha redefinida com sucesso";
}
    
}
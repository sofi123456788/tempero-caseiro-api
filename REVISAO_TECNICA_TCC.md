# Revisão técnica — API Tempero Caseiro (TCC)

Esta revisão documenta o código publicado no GitHub em 23/11/2026. O aplicativo em execução no Fedora pode conter alterações locais não publicadas; por isso, **não presumir que a versão do GitHub seja idêntica à que está em uso no VS Code**.

## O que foi verificado

- O projeto usa Spring Boot 3.5.16 e Java 25 no `pom.xml`.
- Rotas observadas em `AuthController`: `/auth/cadastro`, `/auth/login`, `/auth/excluir`, `/auth/recuperar`, `/auth/validar` e `/auth/alterar`.
- A execução local informada iniciou o Tomcat na porta 8080 e conectou ao PostgreSQL, mas apresentou avisos de conexões encerradas pelo HikariCP.
- A API Java **não precisa da chave Anthropic**. O aplicativo Android chama a Aurora Python na porta 8000 diretamente (em desenvolvimento com `adb reverse`).

## Pendências antes de declarar pronto para produção

1. **Contrato de login:** a implementação publicada de `AuthService.loginUsuario` retorna `String`, enquanto o Android modela uma resposta com `mensagem`, `token` e `nomeCompleto`. Verificar qual versão executa no computador. Não alterar o contrato sem testar cliente e servidor juntos.
2. **Exclusão de conta:** a rota `/auth/excluir` recebe um e-mail e o serviço marca a conta como `excluido`, sem uma verificação de titularidade visível nessa implementação. Requer autenticação/autorização no servidor, inclusive para impedir excluir contas de terceiros. Trata-se de exclusão lógica, não de remoção física imediata dos dados.
3. **Receitas:** `PUT /receitas/{id}/aprovar` e `DELETE /receitas/{id}` não têm controle de autorização visível em `ReceitaController`. Restringir essas operações a administradores e conferir a segurança das demais rotas antes de produção.
4. **Conexões PostgreSQL:** verificar timeout do banco e parâmetros Hikari (`maxLifetime`, `keepaliveTime`) no ambiente local; não atribuir a falha apenas ao pool sem observar a rede/serviço.
5. **Códigos de recuperação:** revisar tentativas máximas, expiração, invalidação após o uso e política de não divulgar dados de contas existentes.
6. **Erros HTTP:** vários fluxos devolvem `200 OK` com texto de erro de negócio; considerar status adequados e respostas JSON consistentes, ajustando o Android em conjunto.
7. **CORS e autenticação:** conferir a presença de `@CrossOrigin("*")` no controller de receitas e sua necessidade; conferir autorização de escrita.
8. **Testes:** executar `./mvnw test`, `./mvnw spring-boot:run` e testes de login, exclusão e receitas contra banco de teste antes de mesclar.

## Ambiente de desenvolvimento

```bash
cd ~/Downloads/Tempeiro-CaseiroAPI/tempero-caseiro-api
export JWT_SECRET="$(openssl rand -base64 32)"
./mvnw spring-boot:run
```

A variável JWT acima deve corresponder à implementação local do token; em ambientes persistentes, **gerar um novo segredo a cada inicialização pode invalidar sessões já emitidas**. Nunca enviar credenciais reais ao GitHub.

## Aurora — para quando chegar a chave

- Manter a chave **somente no processo Python** como `ANTHROPIC_API_KEY`. Não adicionar chaves ao código Android, ao repositório ou à API Java.
- Já existem as rotas Android `POST /api/anonimo/iniciar` e `POST /api/anonimo/chat`.
- Testado parcialmente no celular: início da sessão `200`; envio de mensagem `503` sem chave configurada.
- Para a Aurora original, preservar modelo Claude, prompts e Motor V3. Não editar o repositório original de terceiros.
- Rodar a API Python com chave no ambiente e validar resposta real, erro de banco e privacidade antes de declarar concluído.

## Escopo da organização aplicada

Foi padronizada a organização e documentação de `AuthController` **sem alterar as rotas nem a lógica de negócio**. As vulnerabilidades observadas não foram corrigidas automaticamente porque exigem decisões de autenticação, migração e testes de integração.

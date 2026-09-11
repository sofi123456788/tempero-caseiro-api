package com.example.temperocaseiroapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service 
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCodigoRecuperacao(String email, String codigo) { // recebe o email e o código enviado pela função de gerarCodigo
        
        
        SimpleMailMessage mensagem = new SimpleMailMessage(); // cria mensagem

        mensagem.setTo(email); // coloca o email do usuário como destinatário
        mensagem.setSubject("Código de recuperação"); //define o corpo do email
        mensagem.setText(
                "Seu código de recuperação é: " + codigo +
                "\n\nEste código expira em 10 minutos."
        );

        mailSender.send(mensagem); // envia para o usuário
    }
    
}
    
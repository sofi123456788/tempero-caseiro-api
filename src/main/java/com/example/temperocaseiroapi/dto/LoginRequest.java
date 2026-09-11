package com.example.temperocaseiroapi.dto;

public class LoginRequest {

    private String emailLogin;
    private String senhaLogin;

    public LoginRequest() {
    }

    public String getEmailLogin() {
        return emailLogin;
    }

    public void setEmailLogin(String emailLogin) {
        this.emailLogin = emailLogin;
    }

    public String getSenhaLogin() {
        return senhaLogin;
    }

    public void setSenhaLogin(String senhaLogin) {
        this.senhaLogin = senhaLogin;
    }
}
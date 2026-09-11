package com.example.temperocaseiroapi.dto;

public class ValidarCodigoRequest {

    private String emailCod;
    private String codigo;

    public ValidarCodigoRequest (){

    }

    public String getEmailCod() {
        return emailCod;
    }

    public void setEmailCod(String emailCod ) {
        this.emailCod = emailCod;
    }

    public String getCodigo(){
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo; 
    }
}
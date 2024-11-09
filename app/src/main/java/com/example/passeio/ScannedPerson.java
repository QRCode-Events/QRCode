package com.example.passeio; // Replace with your package name

public class ScannedPerson {

    public long id;
    public String nome;
    public String dataNascimento;
    public String email;public String senha;
    public String qrCode;

    public ScannedPerson(long id, String nome, String dataNascimento, String email, String senha, String qrCode) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
        this.qrCode = qrCode;
    }
}

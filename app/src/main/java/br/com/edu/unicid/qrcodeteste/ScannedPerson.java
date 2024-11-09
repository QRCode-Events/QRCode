package br.com.edu.unicid.qrcodeteste;

public class ScannedPerson {

    private long id;
    private String nome;
    private String dataNascimento;
    private String email;
    private String senha;
    private String qrCode;

    public ScannedPerson(long id, String nome, String dataNascimento, String email, String senha, String qrCode) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
        this.qrCode = qrCode;
    }

    // Getter methods
    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getQrCode() {
        return qrCode;
    }
}
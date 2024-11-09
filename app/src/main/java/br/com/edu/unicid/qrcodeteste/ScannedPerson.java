package br.com.edu.unicid.qrcodeteste;

public class ScannedPerson {

    private long id;private String nome;
    private String dataNascimento;
    private String qrCode;
    private String email;
    private String senha;
    private int scanned; // Add scanned property

    // Constructor
    public ScannedPerson(long id, String nome, String dataNascimento, String qrCode, String email, String senha, int scanned) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.qrCode = qrCode;
        this.email = email;
        this.senha = senha;
        this.scanned = scanned;
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

    public int getScanned() {
        return scanned;
    }

    public void setScanned(int scanned) {
        this.scanned = scanned;
    }
}
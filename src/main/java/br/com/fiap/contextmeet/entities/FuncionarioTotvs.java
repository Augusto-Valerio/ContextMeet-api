package br.com.fiap.contextmeet.entities;

public class FuncionarioTotvs {
    private int idFuncionario;
    private int idUsuario;
    private String nome;
    private String email;
    private String cargo;

    public FuncionarioTotvs() {
    }

    public FuncionarioTotvs(int idUsuario, String nome, String email, String cargo) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.cargo = cargo;
    }

    public FuncionarioTotvs(int idFuncionario, int idUsuario, String nome, String email, String cargo) {
        this.idFuncionario = idFuncionario;
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.cargo = cargo;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "FuncionarioTOTVS: " +
                "\nidFuncionario=" + idFuncionario +
                "\nidUsuario=" + idUsuario +
                "\nnome='" + nome + '\'' +
                "\nemail='" + email + '\'' +
                "\ncargo='" + cargo + '\'';
    }
}

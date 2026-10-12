package br.com.fiap.contextmeet.entities;

import java.time.LocalDate;

public class Reuniao {
    private int idReuniao;
    private int idFuncionario;
    private String titulo;
    private LocalDate dataReuniao;
    private String status;

    public Reuniao() {
    }

    public Reuniao(int idFuncionario, String titulo, LocalDate dataReuniao, String status) {
        this.idFuncionario = idFuncionario;
        this.titulo = titulo;
        this.dataReuniao = dataReuniao;
        this.status = status;
    }

    public Reuniao(int idReuniao, int idFuncionario, String titulo, LocalDate dataReuniao, String status) {
        this.idReuniao = idReuniao;
        this.idFuncionario = idFuncionario;
        this.titulo = titulo;
        this.dataReuniao = dataReuniao;
        this.status = status;
    }

    public int getIdReuniao() {
        return idReuniao;
    }

    public void setIdReuniao(int idReuniao) {
        this.idReuniao = idReuniao;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getDataReuniao() {
        return dataReuniao;
    }

    public void setDataReuniao(LocalDate dataReuniao) {
        this.dataReuniao = dataReuniao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reuniao: " +
                "idReuniao=" + idReuniao +
                "\nidFuncionario=" + idFuncionario +
                "\ntitulo='" + titulo + '\'' +
                "\ndataReuniao=" + dataReuniao +
                "\nstatus='" + status + '\'';
    }
}

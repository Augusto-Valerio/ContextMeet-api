package br.com.fiap.contextmeet.entities;

public class Transcricao {
    private int idTranscricao;
    private int idReuniao;
    private String conteudo;

    public Transcricao() {
    }

    public Transcricao(int idReuniao, String conteudo) {
        this.idReuniao = idReuniao;
        this.conteudo = conteudo;
    }

    public Transcricao(int idTranscricao, int idReuniao, String conteudo) {
        this.idTranscricao = idTranscricao;
        this.idReuniao = idReuniao;
        this.conteudo = conteudo;
    }

    public int getIdTranscricao() {
        return idTranscricao;
    }

    public void setIdTranscricao(int idTranscricao) {
        this.idTranscricao = idTranscricao;
    }

    public int getIdReuniao() {
        return idReuniao;
    }

    public void setIdReuniao(int idReuniao) {
        this.idReuniao = idReuniao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public String toString() {
        return "Transcricao: " +
                "\nidTranscricao=" + idTranscricao +
                "\nidReuniao=" + idReuniao +
                "\nconteudo='" + conteudo + '\'';
    }
}



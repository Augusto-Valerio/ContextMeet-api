package br.com.fiap.contextmeet.entities;

public class Insight {
    private int idInsight;
    private int idTranscricao;
    private String tipo;
    private String descricao;
    private String sentimento;
    private String sugestaoAcao;

    public Insight() {
    }

    public Insight(int idTranscricao, String tipo, String descricao, String sentimento, String sugestaoAcao) {
        this.idTranscricao = idTranscricao;
        this.tipo = tipo;
        this.descricao = descricao;
        this.sentimento = sentimento;
        this.sugestaoAcao = sugestaoAcao;
    }

    public Insight(int idInsight, int idTranscricao, String tipo, String descricao, String sentimento, String sugestaoAcao) {
        this.idInsight = idInsight;
        this.idTranscricao = idTranscricao;
        this.tipo = tipo;
        this.descricao = descricao;
        this.sentimento = sentimento;
        this.sugestaoAcao = sugestaoAcao;
    }

    public int getIdInsight() {
        return idInsight;
    }

    public void setIdInsight(int idInsight) {
        this.idInsight = idInsight;
    }

    public int getIdTranscricao() {
        return idTranscricao;
    }

    public void setIdTranscricao(int idTranscricao) {
        this.idTranscricao = idTranscricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSentimento() {
        return sentimento;
    }

    public void setSentimento(String sentimento) {
        this.sentimento = sentimento;
    }

    public String getSugestaoAcao() {
        return sugestaoAcao;
    }

    public void setSugestaoAcao(String sugestaoAcao) {
        this.sugestaoAcao = sugestaoAcao;
    }

    @Override
    public String toString() {
        return "Insight: " +
                "idInsight=" + idInsight +
                "\nidTranscricao=" + idTranscricao +
                "\ntipo='" + tipo + '\'' +
                "\ndescricao='" + descricao + '\'' +
                "\nsentimento='" + sentimento + '\'' +
                "\nsugestaoAcao='" + sugestaoAcao + '\'';
    }
}

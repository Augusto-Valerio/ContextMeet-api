package br.com.fiap.contextmeet.main;

import br.com.fiap.contextmeet.bo.InsightBo;
import br.com.fiap.contextmeet.entities.Insight;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;

public class TesteAnalisarTranscricao {

    // Inteiro
    static int inteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        InsightBo insightBo = new InsightBo();

        int idTranscricao = inteiro("Informe o ID da transcrição que será analisada");

        ArrayList<Insight> insights = insightBo.analisarTranscricaoBo(idTranscricao);

        System.out.println("Análise realizada com sucesso!");
        System.out.println("Quantidade de insights: " + insights.size());

        StringBuilder resumo = new StringBuilder();

        for (Insight insight : insights) {
            System.out.println("--------------------------------");
            System.out.println("ID: " + insight.getIdInsight());
            System.out.println("Tipo: " + insight.getTipo());
            System.out.println("Descrição: " + insight.getDescricao());
            System.out.println("Sentimento: " + insight.getSentimento());
            System.out.println("Sugestão de ação: " + insight.getSugestaoAcao());

            resumo.append("\n")
                    .append(insight.getTipo())
                    .append(" - ")
                    .append(insight.getSentimento());
        }

        JOptionPane.showMessageDialog(
                null,
                "Análise realizada com sucesso!\n"
                        + "Quantidade de insights: "
                        + insights.size()
                        + "\n"
                        + resumo
        );
    }
}

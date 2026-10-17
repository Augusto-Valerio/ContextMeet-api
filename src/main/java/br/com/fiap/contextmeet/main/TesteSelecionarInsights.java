package br.com.fiap.contextmeet.main;

import br.com.fiap.contextmeet.bo.InsightBo;
import br.com.fiap.contextmeet.entities.Insight;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;

public class TesteSelecionarInsights {

    // Inteiro
    static int inteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        InsightBo insightBo = new InsightBo();

        int idTranscricao = inteiro("Informe o ID da transcrição");

        ArrayList<Insight> insights = insightBo.buscarPorIdTranscricaoBo(idTranscricao);

        System.out.println("Quantidade de insights encontrados: " + insights.size());

        StringBuilder resumo = new StringBuilder();

        for (Insight insight : insights) {
            System.out.println("--------------------------------");
            System.out.println("ID: " + insight.getIdInsight());
            System.out.println("ID da transcrição: " + insight.getIdTranscricao());
            System.out.println("Tipo: " + insight.getTipo());
            System.out.println("Descrição: " + insight.getDescricao());
            System.out.println("Sentimento: " + insight.getSentimento());
            System.out.println("Sugestão de ação: " + insight.getSugestaoAcao());

            resumo.append("\nID: ")
                    .append(insight.getIdInsight())
                    .append(" - ")
                    .append(insight.getTipo())
                    .append(" - ")
                    .append(insight.getSentimento());
        }

        JOptionPane.showMessageDialog(
                null,
                "Consulta realizada com sucesso!\n"
                        + "Quantidade de insights: "
                        + insights.size()
                        + "\n"
                        + resumo
        );
    }
}

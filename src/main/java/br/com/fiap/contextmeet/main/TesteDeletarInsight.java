package br.com.fiap.contextmeet.main;

import br.com.fiap.contextmeet.bo.InsightBo;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class TesteDeletarInsight {

    // Inteiro
    static int inteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        InsightBo insightBo = new InsightBo();

        int idInsight = inteiro("Informe o ID do insight que será excluído");

        boolean deletado = insightBo.deletarBo(idInsight);

        System.out.println("Insight excluído: " + deletado);

        if (deletado) {
            JOptionPane.showMessageDialog(
                    null,
                    "Insight excluído com sucesso!\n" +
                            "ID: " + idInsight
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível excluir o insight."
            );
        }
    }
}

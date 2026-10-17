package br.com.fiap.contextmeet.main;

import br.com.fiap.contextmeet.bo.ReuniaoBo;
import br.com.fiap.contextmeet.entities.Reuniao;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.time.LocalDate;

public class TesteAtualizarReuniao {

    // String
    static String texto(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    // Inteiro
    static int inteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    // Data
    static LocalDate data(String mensagem) {
        return LocalDate.parse(JOptionPane.showInputDialog(mensagem));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Reuniao reuniao = new Reuniao();
        ReuniaoBo reuniaoBo = new ReuniaoBo();

        reuniao.setIdReuniao(inteiro("Informe o ID da reunião que será atualizada"));
        reuniao.setIdFuncionario(inteiro("Informe o ID do funcionário"));
        reuniao.setTitulo(texto("Informe o novo título da reunião"));
        reuniao.setDataReuniao(data("Informe a nova data (AAAA-MM-DD)"));
        reuniao.setStatus(texto("Informe o status: AGENDADA, REALIZADA ou CANCELADA").toUpperCase());

        boolean atualizada = reuniaoBo.atualizarBo(reuniao);

        System.out.println("Reunião atualizada: " + atualizada);

        if (atualizada) {
            JOptionPane.showMessageDialog(
                    null,
                    "Reunião atualizada com sucesso!\n" +
                            "ID: " + reuniao.getIdReuniao()
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível atualizar a reunião."
            );
        }
    }
}

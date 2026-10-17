package br.com.fiap.contextmeet.main;

import br.com.fiap.contextmeet.bo.ReuniaoBo;
import br.com.fiap.contextmeet.entities.Reuniao;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class TesteBuscarReuniaoPorId {

    // Inteiro
    static int inteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ReuniaoBo reuniaoBo = new ReuniaoBo();

        int idReuniao = inteiro("Informe o ID da reunião");

        Reuniao reuniao = reuniaoBo.buscarPorIdBo(idReuniao);

        System.out.println("Reunião encontrada com sucesso!");
        System.out.println("ID: " + reuniao.getIdReuniao());
        System.out.println("ID do funcionário: " + reuniao.getIdFuncionario());
        System.out.println("Título: " + reuniao.getTitulo());
        System.out.println("Data: " + reuniao.getDataReuniao());
        System.out.println("Status: " + reuniao.getStatus());

        JOptionPane.showMessageDialog(
                null,
                "Reunião encontrada com sucesso!\n"
                        + "ID: " + reuniao.getIdReuniao() + "\n"
                        + "Funcionário: "
                        + reuniao.getIdFuncionario() + "\n"
                        + "Título: " + reuniao.getTitulo() + "\n"
                        + "Data: " + reuniao.getDataReuniao() + "\n"
                        + "Status: " + reuniao.getStatus()
        );
    }
}

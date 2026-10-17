package br.com.fiap.contextmeet.main;

import br.com.fiap.contextmeet.bo.FuncionarioTotvsBo;
import br.com.fiap.contextmeet.entities.FuncionarioTotvs;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class TesteInserirFuncionario {

    // String
    static String texto(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    // Inteiro
    static int inteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        FuncionarioTotvs funcionario = new FuncionarioTotvs();
        FuncionarioTotvsBo funcionarioBo = new FuncionarioTotvsBo();

        funcionario.setIdUsuario(inteiro("Informe o ID do usuário"));
        funcionario.setNome(texto("Informe o nome do funcionário"));
        funcionario.setEmail(texto("Informe o email do funcionário"));
        funcionario.setCargo(texto("Informe o cargo do funcionário"));

        int idFuncionario = funcionarioBo.inserirBo(funcionario);

        System.out.println("ID do funcionário cadastrado: " + idFuncionario);

        JOptionPane.showMessageDialog(
                null,
                "Funcionário cadastrado com sucesso!\n" +
                        "ID gerado: " + idFuncionario
        );

    }
}

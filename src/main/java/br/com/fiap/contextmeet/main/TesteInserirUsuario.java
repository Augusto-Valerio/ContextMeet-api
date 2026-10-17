package br.com.fiap.contextmeet.main;

import br.com.fiap.contextmeet.bo.UsuarioBo;
import br.com.fiap.contextmeet.entities.Usuario;

import javax.swing.*;
import java.sql.SQLException;

public class TesteInserirUsuario {

    // String
    static String texto(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Usuario usuario = new Usuario();
        UsuarioBo usuarioBo = new UsuarioBo();

        usuario.setNome(texto("Informe o nome do usuário"));
        usuario.setEmail(texto("Informe o e-mail do usuário"));
        usuario.setSenha(texto("Informe a senha do usuário"));
        usuario.setTipo("FUNCIONARIO");

        int idUsuario = usuarioBo.inserirBo(usuario);

        System.out.println("ID do usuário cadastrado: " + idUsuario);

        JOptionPane.showMessageDialog(
                null,
                "Usuário cadastrado com sucesso!\n" +
                "ID gerado: " + idUsuario);
    }
}

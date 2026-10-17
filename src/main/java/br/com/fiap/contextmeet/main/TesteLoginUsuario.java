package br.com.fiap.contextmeet.main;

import br.com.fiap.contextmeet.bo.UsuarioBo;
import br.com.fiap.contextmeet.entities.Usuario;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class TesteLoginUsuario {

    // String
    static String texto(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        UsuarioBo usuarioBo = new UsuarioBo();

        String email = texto("Informe o e-mail do usuário");

        String senha = texto("Informe a senha do usuário");

        Usuario usuario = usuarioBo.autenticarBo(email, senha);

        System.out.println("Login realizado com sucesso!");
        System.out.println("ID do usuário: " + usuario.getIdUsuario());
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Tipo: " + usuario.getTipo());

        JOptionPane.showMessageDialog(
                null,
                "Login realizado com sucesso!\n"
                        + "ID: " + usuario.getIdUsuario() + "\n"
                        + "Nome: " + usuario.getNome() + "\n"
                        + "E-mail: " + usuario.getEmail() + "\n"
                        + "Tipo: " + usuario.getTipo()
        );
    }
}

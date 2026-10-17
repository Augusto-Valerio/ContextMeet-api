package br.com.fiap.contextmeet.main;

import br.com.fiap.contextmeet.bo.TranscricaoBo;
import br.com.fiap.contextmeet.entities.Transcricao;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class TesteInserirTranscricao {
    // String
    static String texto(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    // Inteiro
    static int inteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Transcricao transcricao = new Transcricao();
        TranscricaoBo transcricaoBo = new TranscricaoBo();

        transcricao.setIdReuniao(inteiro("Informe o ID da reunião"));
        transcricao.setConteudo(texto("Informe o conteúdo da transcrição"));

        int idTranscricao = transcricaoBo.inserirBo(transcricao);

        System.out.println("ID da transcrição cadastrada: " + idTranscricao);

        JOptionPane.showMessageDialog(
                null,
                "Transcrição cadastrada com sucesso!\n"
                        + "ID gerado: " + idTranscricao
        );
    }
}

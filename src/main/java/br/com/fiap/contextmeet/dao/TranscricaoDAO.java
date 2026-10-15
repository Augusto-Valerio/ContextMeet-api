package br.com.fiap.contextmeet.dao;


import br.com.fiap.contextmeet.conexoes.ConexaoFactory;
import br.com.fiap.contextmeet.entities.Transcricao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TranscricaoDAO {

    // Insert
    public int inserir(Transcricao transcricao) throws SQLException, ClassNotFoundException {

        String sqlInserir = "INSERT INTO TRANSCRICAO (ID_REUNIAO, CONTEUDO) VALUES (?, ?)";

        String sqlBuscarId = "SELECT MAX(ID_TRANSCRICAO) AS ID_TRANSCRICAO FROM TRANSCRICAO WHERE ID_REUNIAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sqlInserir)) {

            stmt.setInt(1, transcricao.getIdReuniao());
            stmt.setString(2, transcricao.getConteudo());

            stmt.executeUpdate();

            try (PreparedStatement consulta = conexao.prepareStatement(sqlBuscarId)) {

                consulta.setInt(1, transcricao.getIdReuniao());

                try (ResultSet rs = consulta.executeQuery()) {
                    if (rs.next()) {
                        int idTranscricao = rs.getInt("ID_TRANSCRICAO");

                        if (idTranscricao > 0) {
                            return idTranscricao;
                        }
                    }
                }
            }
        }

        throw new SQLException("Não foi possível recuperar o ID da transcrição cadastrada.");
    }

    // Select
    public ArrayList<Transcricao> selecionar() throws SQLException, ClassNotFoundException {

        ArrayList<Transcricao> transcricoes = new ArrayList<>();

        String sql = "SELECT ID_TRANSCRICAO, ID_REUNIAO, CONTEUDO FROM TRANSCRICAO ORDER BY ID_TRANSCRICAO";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                transcricoes.add(mapearTranscricao(rs));
            }
        }

        return transcricoes;
    }

    // Buscar por ID
    public Transcricao buscarPorId(int idTranscricao) throws SQLException, ClassNotFoundException {

        String sql = "SELECT ID_TRANSCRICAO, ID_REUNIAO, CONTEUDO FROM TRANSCRICAO WHERE ID_TRANSCRICAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idTranscricao);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTranscricao(rs);
                }
            }
        }

        return null;
    }

    // Buscar transcrições da reunião
    public ArrayList<Transcricao> buscarPorIdReuniao(int idReuniao) throws SQLException, ClassNotFoundException {

        ArrayList<Transcricao> transcricoes = new ArrayList<>();

        String sql = "SELECT ID_TRANSCRICAO, ID_REUNIAO, CONTEUDO FROM TRANSCRICAO WHERE ID_REUNIAO = ? ORDER BY ID_TRANSCRICAO";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idReuniao);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transcricoes.add(mapearTranscricao(rs));
                }
            }
        }

        return transcricoes;
    }

    // Update
    public boolean atualizar(Transcricao transcricao) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE TRANSCRICAO SET ID_REUNIAO = ?, CONTEUDO = ? WHERE ID_TRANSCRICAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, transcricao.getIdReuniao());
            stmt.setString(2, transcricao.getConteudo());
            stmt.setInt(3, transcricao.getIdTranscricao());

            return stmt.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deletar(int idTranscricao) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM TRANSCRICAO WHERE ID_TRANSCRICAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idTranscricao);

            return stmt.executeUpdate() > 0;
        }
    }

    private Transcricao mapearTranscricao(ResultSet rs) throws SQLException {

        return new Transcricao(
                rs.getInt("ID_TRANSCRICAO"),
                rs.getInt("ID_REUNIAO"),
                rs.getString("CONTEUDO")
        );
    }
}

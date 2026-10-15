package br.com.fiap.contextmeet.dao;

import br.com.fiap.contextmeet.conexoes.ConexaoFactory;
import br.com.fiap.contextmeet.entities.Insight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsightDAO {

    // Insert
    public int inserir(Insight insight) throws SQLException, ClassNotFoundException {

        String sqlInserir = "INSERT INTO INSIGHT (ID_TRANSCRICAO, TIPO, DESCRICAO, SENTIMENTO, SUGESTAO_ACAO) VALUES (?, ?, ?, ?, ?)";

        String sqlBuscarId = "SELECT MAX(ID_INSIGHT) AS ID_INSIGHT FROM INSIGHT WHERE ID_TRANSCRICAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sqlInserir)) {

            stmt.setInt(1, insight.getIdTranscricao());
            stmt.setString(2, insight.getTipo());
            stmt.setString(3, insight.getDescricao());
            stmt.setString(4, insight.getSentimento());
            stmt.setString(5, insight.getSugestaoAcao());

            stmt.executeUpdate();

            try (PreparedStatement consulta = conexao.prepareStatement(sqlBuscarId)) {

                consulta.setInt(1, insight.getIdTranscricao());

                try (ResultSet rs = consulta.executeQuery()) {
                    if (rs.next()) {
                        int idInsight = rs.getInt("ID_INSIGHT");

                        if (idInsight > 0) {
                            return idInsight;
                        }
                    }
                }
            }
        }

        throw new SQLException("Não foi possível recuperar o ID do insight cadastrado.");
    }

    // Select
    public ArrayList<Insight> selecionar() throws SQLException, ClassNotFoundException {

        ArrayList<Insight> insights = new ArrayList<>();

        String sql = "SELECT ID_INSIGHT, ID_TRANSCRICAO, TIPO, DESCRICAO, SENTIMENTO, SUGESTAO_ACAO FROM INSIGHT ORDER BY ID_INSIGHT";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                insights.add(mapearInsight(rs));
            }
        }

        return insights;
    }

    // Buscar por ID
    public Insight buscarPorId(int idInsight) throws SQLException, ClassNotFoundException {

        String sql = "SELECT ID_INSIGHT, ID_TRANSCRICAO, TIPO, DESCRICAO, SENTIMENTO, SUGESTAO_ACAO FROM INSIGHT WHERE ID_INSIGHT = ?";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idInsight);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearInsight(rs);
                }
            }
        }

        return null;
    }

    // Buscar insights da transcrição
    public ArrayList<Insight> buscarPorIdTranscricao(int idTranscricao) throws SQLException, ClassNotFoundException {

        ArrayList<Insight> insights = new ArrayList<>();

        String sql = "SELECT ID_INSIGHT, ID_TRANSCRICAO, TIPO, DESCRICAO, SENTIMENTO, SUGESTAO_ACAO FROM INSIGHT WHERE ID_TRANSCRICAO = ? ORDER BY ID_INSIGHT";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idTranscricao);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    insights.add(mapearInsight(rs));
                }
            }
        }

        return insights;
    }

    // Update
    public boolean atualizar(Insight insight) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE INSIGHT SET ID_TRANSCRICAO = ?, TIPO = ?, DESCRICAO = ?, SENTIMENTO = ?, SUGESTAO_ACAO = ? WHERE ID_INSIGHT = ?";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, insight.getIdTranscricao());
            stmt.setString(2, insight.getTipo());
            stmt.setString(3, insight.getDescricao());
            stmt.setString(4, insight.getSentimento());
            stmt.setString(5, insight.getSugestaoAcao());
            stmt.setInt(6, insight.getIdInsight());

            return stmt.executeUpdate() > 0;
        }
    }

    // Deletar por ID
    public boolean deletar(int idInsight) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM INSIGHT WHERE ID_INSIGHT = ?";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idInsight);

            return stmt.executeUpdate() > 0;
        }
    }

    // Deletar todos os insights de uma transcrição
    public int deletarPorIdTranscricao(int idTranscricao) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM INSIGHT WHERE ID_TRANSCRICAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idTranscricao);

            return stmt.executeUpdate();
        }
    }

    private Insight mapearInsight(ResultSet rs) throws SQLException {

        return new Insight(
                rs.getInt("ID_INSIGHT"),
                rs.getInt("ID_TRANSCRICAO"),
                rs.getString("TIPO"),
                rs.getString("DESCRICAO"),
                rs.getString("SENTIMENTO"),
                rs.getString("SUGESTAO_ACAO")
        );
    }
}

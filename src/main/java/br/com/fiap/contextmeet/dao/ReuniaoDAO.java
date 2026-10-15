package br.com.fiap.contextmeet.dao;

import br.com.fiap.contextmeet.conexoes.ConexaoFactory;
import br.com.fiap.contextmeet.entities.Reuniao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReuniaoDAO {

    // Insert
    public int inserir(Reuniao reuniao) throws SQLException, ClassNotFoundException {

        String sqlInserir = "INSERT INTO REUNIAO (ID_FUNCIONARIO, TITULO, DATA_REUNIAO, STATUS) VALUES (?, ?, ?, ?)";

        String sqlBuscarId = "SELECT MAX(ID_REUNIAO) AS ID_REUNIAO FROM REUNIAO WHERE ID_FUNCIONARIO = ? AND TITULO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sqlInserir)) {

            stmt.setInt(1, reuniao.getIdFuncionario());
            stmt.setString(2, reuniao.getTitulo());
            stmt.setDate(3, Date.valueOf(reuniao.getDataReuniao()));
            stmt.setString(4, reuniao.getStatus());

            stmt.executeUpdate();

            try (PreparedStatement consulta = conexao.prepareStatement(sqlBuscarId)) {

                consulta.setInt(1, reuniao.getIdFuncionario());
                consulta.setString(2, reuniao.getTitulo());

                try (ResultSet rs = consulta.executeQuery()) {
                    if (rs.next()) {
                        int idReuniao = rs.getInt("ID_REUNIAO");

                        if (idReuniao > 0) {
                            return idReuniao;
                        }
                    }
                }
            }
        }

        throw new SQLException("Não foi possível recuperar o ID da reunião cadastrada.");
    }

    // Select
    public ArrayList<Reuniao> selecionar() throws SQLException, ClassNotFoundException {

        ArrayList<Reuniao> reunioes = new ArrayList<>();

        String sql = "SELECT ID_REUNIAO, ID_FUNCIONARIO, TITULO, DATA_REUNIAO, STATUS FROM REUNIAO ORDER BY ID_REUNIAO";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reunioes.add(mapearReuniao(rs));
            }
        }

        return reunioes;
    }

    // Buscar por ID
    public Reuniao buscarPorId(int idReuniao) throws SQLException, ClassNotFoundException {

        String sql = "SELECT ID_REUNIAO, ID_FUNCIONARIO, TITULO, DATA_REUNIAO, STATUS FROM REUNIAO WHERE ID_REUNIAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idReuniao);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearReuniao(rs);
                }
            }
        }

        return null;
    }

    // Buscar reuniões do funcionário
    public ArrayList<Reuniao> buscarPorIdFuncionario(int idFuncionario) throws SQLException, ClassNotFoundException {

        ArrayList<Reuniao> reunioes = new ArrayList<>();

        String sql = "SELECT ID_REUNIAO, ID_FUNCIONARIO, TITULO, DATA_REUNIAO, STATUS FROM REUNIAO WHERE ID_FUNCIONARIO = ? ORDER BY DATA_REUNIAO DESC";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reunioes.add(mapearReuniao(rs));
                }
            }
        }

        return reunioes;
    }

    // Update
    public boolean atualizar(Reuniao reuniao) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE REUNIAO SET ID_FUNCIONARIO = ?, TITULO = ?, DATA_REUNIAO = ?, STATUS = ? WHERE ID_REUNIAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, reuniao.getIdFuncionario());
            stmt.setString(2, reuniao.getTitulo());
            stmt.setDate(3, Date.valueOf(reuniao.getDataReuniao()));
            stmt.setString(4, reuniao.getStatus());
            stmt.setInt(5, reuniao.getIdReuniao());

            return stmt.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deletar(int idReuniao) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM REUNIAO WHERE ID_REUNIAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idReuniao);

            return stmt.executeUpdate() > 0;
        }
    }

    private Reuniao mapearReuniao(ResultSet rs) throws SQLException {

        return new Reuniao(
                rs.getInt("ID_REUNIAO"),
                rs.getInt("ID_FUNCIONARIO"),
                rs.getString("TITULO"),
                rs.getDate("DATA_REUNIAO").toLocalDate(),
                rs.getString("STATUS")
        );
    }
}

package br.com.fiap.contextmeet.dao;

import br.com.fiap.contextmeet.conexoes.ConexaoFactory;
import br.com.fiap.contextmeet.entities.FuncionarioTotvs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FuncionarioTotvsDAO {

    // Insert
    public int inserir(FuncionarioTotvs funcionario) throws SQLException, ClassNotFoundException {
        String sqlInserir = "INSERT INTO FUNCIONARIO_TOTVS(USER_ID, NOME, EMAIL, CARGO) VALUES (?, ?, ?, ?)";

        String sqlBuscarId = "SELECT ID_FUNCIONARIO FROM FUNCIONARIO_TOTVS WHERE EMAIL = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sqlInserir)) {

            stmt.setInt(1, funcionario.getIdUsuario());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getEmail());
            stmt.setString(4, funcionario.getCargo());

            stmt.executeUpdate();

            try (PreparedStatement consulta = conexao.prepareStatement(sqlBuscarId)) {
                consulta.setString(1, funcionario.getEmail());

                try (ResultSet rs = consulta.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("ID_FUNCIONARIO");
                    }
                }
            }
        }

        throw new SQLException("Não foi possível recuperar o ID do funcionário cadastrado.");
    }

    // Select
    public ArrayList<FuncionarioTotvs> selecionar() throws SQLException, ClassNotFoundException {
        ArrayList<FuncionarioTotvs> funcionarios = new ArrayList<>();

        String sql = "SELECT ID_FUNCIONARIO, USER_ID, NOME, EMAIL, CARGO FROM FUNCIONARIO_TOTVS ORDER BY ID_FUNCIONARIO";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                funcionarios.add(mapearFuncionario(rs));
            }
        }

        return funcionarios;
    }

    // Buscar por ID
    public FuncionarioTotvs buscarPorId(int idFuncionario) throws SQLException, ClassNotFoundException {

        String sql = "SELECT ID_FUNCIONARIO, USER_ID, NOME, EMAIL, CARGO FROM FUNCIONARIO_TOTVS WHERE ID_FUNCIONARIO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearFuncionario(rs);
                }
            }
        }

        return null;
    }

    // Buscar pelo ID do usuário
    public FuncionarioTotvs buscarPorIdUsuario(int idUsuario) throws SQLException, ClassNotFoundException {

        String sql = "SELECT ID_FUNCIONARIO, USER_ID, NOME, EMAIL, CARGO FROM FUNCIONARIO_TOTVS WHERE USER_ID = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearFuncionario(rs);
                }
            }
        }

        return null;
    }

    // Atualizar
    public boolean atualizar(FuncionarioTotvs funcionario) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE FUNCIONARIO_TOTVS SET USER_ID = ?, NOME = ?, EMAIL = ?, CARGO = ? WHERE ID_FUNCIONARIO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, funcionario.getIdUsuario());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getEmail());
            stmt.setString(4, funcionario.getCargo());
            stmt.setInt(5, funcionario.getIdFuncionario());

            return stmt.executeUpdate() > 0;
        }
    }

    // Deletar
    public boolean deletar(int idFuncionario)
            throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM FUNCIONARIO_TOTVS WHERE ID_FUNCIONARIO = ?";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);

            return stmt.executeUpdate() > 0;
        }
    }


    private FuncionarioTotvs mapearFuncionario(ResultSet rs) throws SQLException {

        return new FuncionarioTotvs(
                rs.getInt("ID_FUNCIONARIO"),
                rs.getInt("USER_ID"),
                rs.getString("NOME"),
                rs.getString("EMAIL"),
                rs.getString("CARGO")
        );
    }
}

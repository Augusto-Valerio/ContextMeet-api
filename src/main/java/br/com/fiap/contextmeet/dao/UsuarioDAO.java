package br.com.fiap.contextmeet.dao;

import br.com.fiap.contextmeet.conexoes.ConexaoFactory;
import br.com.fiap.contextmeet.entities.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {

    // Insert
    public int inserir(Usuario usuario) throws SQLException, ClassNotFoundException {
        String sqlInserir = "INSERT INTO USUARIO (USER_NAME, USER_EMAIL, USER_PASSWORD, USER_TYPE) VALUES (?, ?, ?, ?)";

        String sqlBuscarId = "SELECT USER_ID FROM USUARIO WHERE USER_EMAIL = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sqlInserir)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipo());

            stmt.executeUpdate();

            try (PreparedStatement consulta = conexao.prepareStatement(sqlBuscarId)) {
                consulta.setString(1, usuario.getEmail());

                try (ResultSet rs = consulta.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("USER_ID");
                    }
                }
            }
        }

        throw new SQLException("não foi possível recuperar o ID do usuário cadastrado.");
    }


    // Select
    public ArrayList<Usuario> selecionar() throws SQLException, ClassNotFoundException {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_TYPE FROM USUARIO ORDER BY USER_ID";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        }

        return usuarios;
    }

    // Buscar por ID
    public Usuario buscarPorId(int idUsuario) throws SQLException, ClassNotFoundException {
        String sql = "SELECT USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_TYPE FROM USUARIO WHERE USER_ID = ?";

        try (Connection conexao = new ConexaoFactory().conexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }

        return null;
    }

    // Buscar por e-mail
    public Usuario buscarPorEmail(String email)
            throws SQLException, ClassNotFoundException {

        String sql = "SELECT USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_TYPE FROM USUARIO WHERE USER_EMAIL = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }

        return null;
    }

    // Update
    public boolean atualizar(Usuario usuario) throws SQLException, ClassNotFoundException {
        String sql = """
                UPDATE USUARIO
                SET USER_NAME = ?, USER_EMAIL = ?, USER_PASSWORD = ?, USER_TYPE = ?
                WHERE USER_ID = ?
                """;

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipo());
            stmt.setInt(5, usuario.getIdUsuario());

            return stmt.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deletar(int idUsuario) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM USUARIO WHERE USER_ID = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            return stmt.executeUpdate() > 0;
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("USER_ID"),
                rs.getString("USER_NAME"),
                rs.getString("USER_EMAIL"),
                rs.getString("USER_PASSWORD"),
                rs.getString("USER_TYPE")
        );
    }
}

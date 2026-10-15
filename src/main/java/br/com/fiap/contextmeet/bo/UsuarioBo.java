package br.com.fiap.contextmeet.bo;

import br.com.fiap.contextmeet.dao.UsuarioDAO;
import br.com.fiap.contextmeet.entities.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class UsuarioBo {

    // Select
    public ArrayList<Usuario> selecionarBo() throws SQLException, ClassNotFoundException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        return usuarioDAO.selecionar();
    }

    // Buscar por ID
    public Usuario buscarPorIdBo(int idUsuario) throws SQLException, ClassNotFoundException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (idUsuario <= 0) {
            throw new IllegalArgumentException("O ID do usuário deve ser maior que zero.");
        }

        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

        if (usuario == null) {
            throw new NoSuchElementException("Usuário não encontrado.");
        }

        return usuario;
    }

    // Buscar por e-mail
    public Usuario buscarPorEmailBo(String email) throws SQLException, ClassNotFoundException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        validarEmail(email);

        String emailNormalizado = email.trim().toLowerCase();

        Usuario usuario = usuarioDAO.buscarPorEmail(emailNormalizado);

        if (usuario == null) {
            throw new NoSuchElementException("Usuário não encontrado.");
        }

        return usuario;
    }

    // Insert
    public int inserirBo(Usuario usuario) throws SQLException, ClassNotFoundException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        normalizarUsuario(usuario);
        validarUsuario(usuario);

        Usuario usuarioExistente = usuarioDAO.buscarPorEmail(usuario.getEmail());

        if (usuarioExistente != null) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse e-mail.");
        }

        return usuarioDAO.inserir(usuario);
    }

    // Update
    public boolean atualizarBo(Usuario usuario) throws SQLException, ClassNotFoundException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuario == null) {
            throw new IllegalArgumentException("Os dados do usuário são obrigatórios.");
        }

        if (usuario.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("O ID do usuário deve ser maior que zero.");
        }

        normalizarUsuario(usuario);
        validarUsuario(usuario);

        Usuario usuarioCadastrado = usuarioDAO.buscarPorId(usuario.getIdUsuario());

        if (usuarioCadastrado == null) {
            throw new NoSuchElementException("Usuário não encontrado.");
        }

        Usuario usuarioComMesmoEmail = usuarioDAO.buscarPorEmail(usuario.getEmail());

        if (usuarioComMesmoEmail != null && usuarioComMesmoEmail.getIdUsuario() != usuario.getIdUsuario()) {

            throw new IllegalArgumentException("Já existe outro usuário cadastrado com esse e-mail.");
        }

        return usuarioDAO.atualizar(usuario);
    }

    // Delete
    public boolean deletarBo(int idUsuario) throws SQLException, ClassNotFoundException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (idUsuario <= 0) {
            throw new IllegalArgumentException("O ID do usuário deve ser maior que zero.");
        }

        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

        if (usuario == null) {
            throw new NoSuchElementException("Usuário não encontrado.");
        }

        return usuarioDAO.deletar(idUsuario);
    }

    // Regras de negócio
    private void validarUsuario(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException("Os dados do usuário são obrigatórios.");
        }

        validarNome(usuario.getNome());
        validarEmail(usuario.getEmail());
        validarSenha(usuario.getSenha());
        validarTipo(usuario.getTipo());
    }

    private void validarNome(String nome) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do usuário é obrigatório.");
        }

        if (nome.length() > 60) {
            throw new IllegalArgumentException("O nome do usuário deve possuir no máximo 60 caracteres.");
        }
    }

    private void validarEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O e-mail do usuário é obrigatório.");
        }

        if (email.length() > 100) {
            throw new IllegalArgumentException("O e-mail deve possuir no máximo 100 caracteres.");
        }

        int posicaoArroba = email.indexOf("@");

        if (posicaoArroba <= 0 || posicaoArroba == email.length() - 1) {

            throw new IllegalArgumentException("O e-mail informado é inválido.");
        }
    }

    private void validarSenha(String senha) {

        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha do usuário é obrigatória.");
        }

        if (senha.length() < 6 || senha.length() > 30) {
            throw new IllegalArgumentException("A senha deve possuir entre 6 e 30 caracteres.");
        }
    }

    private void validarTipo(String tipo) {

        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("O tipo do usuário é obrigatório.");
        }

        if (!tipo.equals("FUNCIONARIO")) {
            throw new IllegalArgumentException("O tipo do usuário deve ser FUNCIONARIO.");
        }
    }

    private void normalizarUsuario(Usuario usuario) {

        if (usuario == null) {
            return;
        }

        if (usuario.getNome() != null) {
            usuario.setNome(usuario.getNome().trim());
        }

        if (usuario.getEmail() != null) {
            usuario.setEmail(usuario.getEmail().trim().toLowerCase());
        }

        if (usuario.getTipo() != null) {
            usuario.setTipo(usuario.getTipo().trim().toUpperCase());
        }
    }
}

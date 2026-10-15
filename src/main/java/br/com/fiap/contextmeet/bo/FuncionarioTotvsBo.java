package br.com.fiap.contextmeet.bo;

import br.com.fiap.contextmeet.dao.FuncionarioTotvsDAO;
import br.com.fiap.contextmeet.dao.UsuarioDAO;
import br.com.fiap.contextmeet.entities.FuncionarioTotvs;
import br.com.fiap.contextmeet.entities.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FuncionarioTotvsBo {

    // Select
    public ArrayList<FuncionarioTotvs> selecionarBo() throws SQLException, ClassNotFoundException {

        FuncionarioTotvsDAO funcionarioDAO = new FuncionarioTotvsDAO();

        return funcionarioDAO.selecionar();
    }

    // Buscar por ID
    public FuncionarioTotvs buscarPorIdBo(int idFuncionario) throws SQLException, ClassNotFoundException {

        FuncionarioTotvsDAO funcionarioDAO = new FuncionarioTotvsDAO();

        if (idFuncionario <= 0) {
            throw new IllegalArgumentException("O ID do funcionário deve ser maior que zero.");
        }

        FuncionarioTotvs funcionario = funcionarioDAO.buscarPorId(idFuncionario);

        if (funcionario == null) {
            throw new NoSuchElementException("Funcionário não encontrado.");
        }

        return funcionario;
    }

    // Buscar pelo ID do usuário
    public FuncionarioTotvs buscarPorIdUsuarioBo(int idUsuario) throws SQLException, ClassNotFoundException {

        FuncionarioTotvsDAO funcionarioDAO = new FuncionarioTotvsDAO();

        if (idUsuario <= 0) {
            throw new IllegalArgumentException("O ID do usuário deve ser maior que zero.");
        }

        FuncionarioTotvs funcionario = funcionarioDAO.buscarPorIdUsuario(idUsuario);

        if (funcionario == null) {
            throw new NoSuchElementException("Funcionário não encontrado para o usuário informado.");
        }

        return funcionario;
    }

    // Insert
    public int inserirBo(FuncionarioTotvs funcionario) throws SQLException, ClassNotFoundException {

        FuncionarioTotvsDAO funcionarioDAO = new FuncionarioTotvsDAO();

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        normalizarFuncionario(funcionario);
        validarFuncionario(funcionario);

        Usuario usuario = usuarioDAO.buscarPorId(funcionario.getIdUsuario());

        if (usuario == null) {
            throw new NoSuchElementException("O usuário informado não foi encontrado.");
        }

        FuncionarioTotvs funcionarioDoUsuario = funcionarioDAO.buscarPorIdUsuario(funcionario.getIdUsuario());

        if (funcionarioDoUsuario != null) {
            throw new IllegalArgumentException("O usuário informado já possui um funcionário cadastrado.");
        }

        ArrayList<FuncionarioTotvs> funcionarios = funcionarioDAO.selecionar();

        for (FuncionarioTotvs funcionarioCadastrado : funcionarios) {
            if (funcionarioCadastrado.getEmail().equalsIgnoreCase(funcionario.getEmail())) {

                throw new IllegalArgumentException("Já existe um funcionário cadastrado com esse e-mail.");
            }
        }

        return funcionarioDAO.inserir(funcionario);
    }

    // Update
    public boolean atualizarBo(FuncionarioTotvs funcionario) throws SQLException, ClassNotFoundException {

        FuncionarioTotvsDAO funcionarioDAO = new FuncionarioTotvsDAO();

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (funcionario == null) {
            throw new IllegalArgumentException("Os dados do funcionário são obrigatórios.");
        }

        if (funcionario.getIdFuncionario() <= 0) {
            throw new IllegalArgumentException("O ID do funcionário deve ser maior que zero.");
        }

        normalizarFuncionario(funcionario);
        validarFuncionario(funcionario);

        FuncionarioTotvs funcionarioCadastrado = funcionarioDAO.buscarPorId(funcionario.getIdFuncionario());

        if (funcionarioCadastrado == null) {
            throw new NoSuchElementException("Funcionário não encontrado.");
        }

        Usuario usuario = usuarioDAO.buscarPorId(funcionario.getIdUsuario());

        if (usuario == null) {
            throw new NoSuchElementException("O usuário informado não foi encontrado.");
        }

        FuncionarioTotvs funcionarioDoUsuario = funcionarioDAO.buscarPorIdUsuario(funcionario.getIdUsuario());

        if (funcionarioDoUsuario != null && funcionarioDoUsuario.getIdFuncionario() != funcionario.getIdFuncionario()) {

            throw new IllegalArgumentException("O usuário informado já está vinculado a outro funcionário.");
        }

        ArrayList<FuncionarioTotvs> funcionarios = funcionarioDAO.selecionar();

        for (FuncionarioTotvs outroFuncionario : funcionarios) {
            boolean mesmoEmail = outroFuncionario.getEmail().equalsIgnoreCase(funcionario.getEmail());

            boolean outroCadastro = outroFuncionario.getIdFuncionario() != funcionario.getIdFuncionario();

            if (mesmoEmail && outroCadastro) {
                throw new IllegalArgumentException("Já existe outro funcionário cadastrado com esse e-mail.");
            }
        }

        return funcionarioDAO.atualizar(funcionario);
    }

    // Delete
    public boolean deletarBo(int idFuncionario) throws SQLException, ClassNotFoundException {

        FuncionarioTotvsDAO funcionarioDAO = new FuncionarioTotvsDAO();

        if (idFuncionario <= 0) {
            throw new IllegalArgumentException("O ID do funcionário deve ser maior que zero.");
        }

        FuncionarioTotvs funcionario = funcionarioDAO.buscarPorId(idFuncionario);

        if (funcionario == null) {
            throw new NoSuchElementException("Funcionário não encontrado.");
        }

        return funcionarioDAO.deletar(idFuncionario);
    }

    // Regras de negócio
    private void validarFuncionario(FuncionarioTotvs funcionario) {

        if (funcionario == null) {
            throw new IllegalArgumentException("Os dados do funcionário são obrigatórios.");
        }

        if (funcionario.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("O ID do usuário deve ser maior que zero.");
        }

        validarNome(funcionario.getNome());
        validarEmail(funcionario.getEmail());
        validarCargo(funcionario.getCargo());
    }

    private void validarNome(String nome) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do funcionário é obrigatório.");
        }

        if (nome.length() > 80) {
            throw new IllegalArgumentException("O nome do funcionário deve possuir no máximo 80 caracteres.");
        }
    }

    private void validarEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O e-mail do funcionário é obrigatório.");
        }

        if (email.length() > 100) {
            throw new IllegalArgumentException("O e-mail deve possuir no máximo 100 caracteres.");
        }

        int posicaoArroba = email.indexOf("@");

        if (posicaoArroba <= 0 || posicaoArroba == email.length() - 1) {

            throw new IllegalArgumentException("O e-mail informado é inválido.");
        }
    }

    private void validarCargo(String cargo) {

        if (cargo != null && cargo.length() > 60) {
            throw new IllegalArgumentException("O cargo deve possuir no máximo 60 caracteres.");
        }
    }

    private void normalizarFuncionario(FuncionarioTotvs funcionario) {

        if (funcionario == null) {
            return;
        }

        if (funcionario.getNome() != null) {
            funcionario.setNome(funcionario.getNome().trim());
        }

        if (funcionario.getEmail() != null) {
            funcionario.setEmail(funcionario.getEmail().trim().toLowerCase());
        }

        if (funcionario.getCargo() != null) {
            funcionario.setCargo(funcionario.getCargo().trim());
        }
    }
}

package br.com.fiap.contextmeet.bo;

import br.com.fiap.contextmeet.dao.FuncionarioTotvsDAO;
import br.com.fiap.contextmeet.dao.ReuniaoDAO;
import br.com.fiap.contextmeet.entities.FuncionarioTotvs;
import br.com.fiap.contextmeet.entities.Reuniao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ReuniaoBo {

    // Select
    public ArrayList<Reuniao> selecionarBo() throws SQLException, ClassNotFoundException {

        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();

        return reuniaoDAO.selecionar();
    }

    // Buscar por ID
    public Reuniao buscarPorIdBo(int idReuniao) throws SQLException, ClassNotFoundException {
        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();

        if (idReuniao <= 0) {
            throw new IllegalArgumentException("O ID da reunião deve ser maior que zero.");
        }

        Reuniao reuniao = reuniaoDAO.buscarPorId(idReuniao);

        if (reuniao == null) {
            throw new NoSuchElementException("Reunião não encontrada.");
        }

        return reuniao;
    }

    // Buscar reuniões pelo ID do funcionário
    public ArrayList<Reuniao> buscarPorIdFuncionarioBo(int idFuncionario) throws SQLException, ClassNotFoundException {

        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();
        FuncionarioTotvsDAO funcionarioDAO = new FuncionarioTotvsDAO();

        if (idFuncionario <= 0) {
            throw new IllegalArgumentException("O ID do funcionário deve ser maior que zero.");
        }

        FuncionarioTotvs funcionario = funcionarioDAO.buscarPorId(idFuncionario);

        if (funcionario == null) {
            throw new NoSuchElementException("Funcionário não encontrado.");
        }

        return reuniaoDAO.buscarPorIdFuncionario(idFuncionario);
    }

    // Insert
    public int inserirBo(Reuniao reuniao) throws SQLException, ClassNotFoundException {

        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();
        FuncionarioTotvsDAO funcionarioDAO = new FuncionarioTotvsDAO();

        normalizarReuniao(reuniao);
        validarReuniao(reuniao);

        FuncionarioTotvs funcionario = funcionarioDAO.buscarPorId(reuniao.getIdFuncionario());

        if (funcionario == null) {
            throw new NoSuchElementException("O funcionário informado não foi encontrado.");
        }

        return reuniaoDAO.inserir(reuniao);
    }

    // Update
    public boolean atualizarBo(Reuniao reuniao)
            throws SQLException, ClassNotFoundException {

        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();
        FuncionarioTotvsDAO funcionarioDAO = new FuncionarioTotvsDAO();

        if (reuniao == null) {
            throw new IllegalArgumentException("Os dados da reunião são obrigatórios.");
        }

        if (reuniao.getIdReuniao() <= 0) {
            throw new IllegalArgumentException("O ID da reunião deve ser maior que zero.");
        }

        normalizarReuniao(reuniao);
        validarReuniao(reuniao);

        Reuniao reuniaoCadastrada = reuniaoDAO.buscarPorId(reuniao.getIdReuniao());

        if (reuniaoCadastrada == null) {
            throw new NoSuchElementException("Reunião não encontrada.");
        }

        FuncionarioTotvs funcionario =
                funcionarioDAO.buscarPorId(reuniao.getIdFuncionario());

        if (funcionario == null) {
            throw new NoSuchElementException("O funcionário informado não foi encontrado.");
        }

        return reuniaoDAO.atualizar(reuniao);
    }

    // Delete
    public boolean deletarBo(int idReuniao) throws SQLException, ClassNotFoundException {

        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();

        if (idReuniao <= 0) {
            throw new IllegalArgumentException("O ID da reunião deve ser maior que zero.");
        }

        Reuniao reuniao = reuniaoDAO.buscarPorId(idReuniao);

        if (reuniao == null) {
            throw new NoSuchElementException("Reunião não encontrada.");
        }

        return reuniaoDAO.deletar(idReuniao);
    }

    // Regras de negócio
    private void validarReuniao(Reuniao reuniao) {

        if (reuniao == null) {
            throw new IllegalArgumentException("Os dados da reunião são obrigatórios.");
        }

        if (reuniao.getIdFuncionario() <= 0) {
            throw new IllegalArgumentException("O ID do funcionário deve ser maior que zero.");
        }

        validarTitulo(reuniao.getTitulo());

        if (reuniao.getDataReuniao() == null) {
            throw new IllegalArgumentException("A data da reunião é obrigatória.");
        }

        validarStatus(reuniao.getStatus());
    }

    private void validarTitulo(String titulo) {

        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título da reunião é obrigatório.");
        }

        if (titulo.length() > 100) {
            throw new IllegalArgumentException("O título deve possuir no máximo 100 caracteres.");
        }
    }

    private void validarStatus(String status) {

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("O status da reunião é obrigatório.");
        }

        boolean statusValido = status.equals("AGENDADA") || status.equals("REALIZADA") || status.equals("CANCELADA");

        if (!statusValido) {
            throw new IllegalArgumentException("O status deve ser AGENDADA, REALIZADA ou CANCELADA.");
        }
    }

    private void normalizarReuniao(Reuniao reuniao) {

        if (reuniao == null) {
            return;
        }

        if (reuniao.getTitulo() != null) {
            reuniao.setTitulo(reuniao.getTitulo().trim());
        }

        if (reuniao.getStatus() != null) {
            reuniao.setStatus(reuniao.getStatus().trim().toUpperCase());
        }
    }
}

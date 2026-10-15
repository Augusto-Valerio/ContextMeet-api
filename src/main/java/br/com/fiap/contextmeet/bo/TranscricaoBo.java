package br.com.fiap.contextmeet.bo;

import br.com.fiap.contextmeet.dao.ReuniaoDAO;
import br.com.fiap.contextmeet.dao.TranscricaoDAO;
import br.com.fiap.contextmeet.entities.Reuniao;
import br.com.fiap.contextmeet.entities.Transcricao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TranscricaoBo {

    // Select
    public ArrayList<Transcricao> selecionarBo() throws SQLException, ClassNotFoundException {

        TranscricaoDAO transcricaoDAO = new TranscricaoDAO();

        return transcricaoDAO.selecionar();
    }

    // Buscar por ID
    public Transcricao buscarPorIdBo(int idTranscricao) throws SQLException, ClassNotFoundException {

        TranscricaoDAO transcricaoDAO = new TranscricaoDAO();

        if (idTranscricao <= 0) {
            throw new IllegalArgumentException("O ID da transcrição deve ser maior que zero.");
        }

        Transcricao transcricao = transcricaoDAO.buscarPorId(idTranscricao);

        if (transcricao == null) {
            throw new NoSuchElementException("Transcrição não encontrada.");
        }

        return transcricao;
    }

    // Buscar transcrições pelo ID da reunião
    public ArrayList<Transcricao> buscarPorIdReuniaoBo(int idReuniao) throws SQLException, ClassNotFoundException {

        TranscricaoDAO transcricaoDAO = new TranscricaoDAO();
        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();

        if (idReuniao <= 0) {
            throw new IllegalArgumentException("O ID da reunião deve ser maior que zero.");
        }

        Reuniao reuniao = reuniaoDAO.buscarPorId(idReuniao);

        if (reuniao == null) {
            throw new NoSuchElementException("Reunião não encontrada.");
        }

        return transcricaoDAO.buscarPorIdReuniao(idReuniao);
    }

    // Insert
    public int inserirBo(Transcricao transcricao) throws SQLException, ClassNotFoundException {

        TranscricaoDAO transcricaoDAO = new TranscricaoDAO();
        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();

        normalizarTranscricao(transcricao);
        validarTranscricao(transcricao);

        Reuniao reuniao = reuniaoDAO.buscarPorId(transcricao.getIdReuniao());

        if (reuniao == null) {
            throw new NoSuchElementException("A reunião informada não foi encontrada.");
        }

        return transcricaoDAO.inserir(transcricao);
    }

    // Update
    public boolean atualizarBo(Transcricao transcricao) throws SQLException, ClassNotFoundException {

        TranscricaoDAO transcricaoDAO = new TranscricaoDAO();
        ReuniaoDAO reuniaoDAO = new ReuniaoDAO();

        if (transcricao == null) {
            throw new IllegalArgumentException("Os dados da transcrição são obrigatórios.");
        }

        if (transcricao.getIdTranscricao() <= 0) {
            throw new IllegalArgumentException("O ID da transcrição deve ser maior que zero.");
        }

        normalizarTranscricao(transcricao);
        validarTranscricao(transcricao);

        Transcricao transcricaoCadastrada = transcricaoDAO.buscarPorId(transcricao.getIdTranscricao());

        if (transcricaoCadastrada == null) {
            throw new NoSuchElementException("Transcrição não encontrada.");
        }

        Reuniao reuniao = reuniaoDAO.buscarPorId(transcricao.getIdReuniao());

        if (reuniao == null) {
            throw new NoSuchElementException("A reunião informada não foi encontrada.");
        }

        return transcricaoDAO.atualizar(transcricao);
    }

    // Delete
    public boolean deletarBo(int idTranscricao) throws SQLException, ClassNotFoundException {

        TranscricaoDAO transcricaoDAO = new TranscricaoDAO();

        if (idTranscricao <= 0) {
            throw new IllegalArgumentException("O ID da transcrição deve ser maior que zero.");
        }

        Transcricao transcricao = transcricaoDAO.buscarPorId(idTranscricao);

        if (transcricao == null) {
            throw new NoSuchElementException("Transcrição não encontrada.");
        }

        return transcricaoDAO.deletar(idTranscricao);
    }

    // Regras de negócio
    private void validarTranscricao(Transcricao transcricao) {

        if (transcricao == null) {
            throw new IllegalArgumentException("Os dados da transcrição são obrigatórios.");
        }

        if (transcricao.getIdReuniao() <= 0) {
            throw new IllegalArgumentException("O ID da reunião deve ser maior que zero.");
        }

        validarConteudo(transcricao.getConteudo());
    }

    private void validarConteudo(String conteudo) {

        if (conteudo == null || conteudo.isBlank()) {
            throw new IllegalArgumentException("O conteúdo da transcrição é obrigatório.");
        }

        if (conteudo.length() > 1000) {
            throw new IllegalArgumentException("O conteúdo deve possuir no máximo 1000 caracteres.");
        }
    }

    private void normalizarTranscricao(Transcricao transcricao) {

        if (transcricao == null) {
            return;
        }

        if (transcricao.getConteudo() != null) {
            transcricao.setConteudo(transcricao.getConteudo().trim());
        }
    }
}

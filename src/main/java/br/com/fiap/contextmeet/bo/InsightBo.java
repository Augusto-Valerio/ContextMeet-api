package br.com.fiap.contextmeet.bo;

import br.com.fiap.contextmeet.dao.InsightDAO;
import br.com.fiap.contextmeet.dao.TranscricaoDAO;
import br.com.fiap.contextmeet.entities.Insight;
import br.com.fiap.contextmeet.entities.Transcricao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class InsightBo {


    // Select todos
    public ArrayList<Insight> selecionarBo() throws SQLException, ClassNotFoundException {

        InsightDAO insightDAO = new InsightDAO();

        return insightDAO.selecionar();
    }

    // Buscar por ID
    public Insight buscarPorIdBo(int idInsight) throws SQLException, ClassNotFoundException {

        InsightDAO insightDAO = new InsightDAO();

        if (idInsight <= 0) {
            throw new IllegalArgumentException("O ID do insight deve ser maior que zero.");
        }

        Insight insight = insightDAO.buscarPorId(idInsight);

        if (insight == null) {
            throw new NoSuchElementException("Insight não encontrado.");
        }

        return insight;
    }

    // Buscar insights pelo ID da transcrição
    public ArrayList<Insight> buscarPorIdTranscricaoBo(int idTranscricao) throws SQLException, ClassNotFoundException {

        InsightDAO insightDAO = new InsightDAO();
        TranscricaoDAO transcricaoDAO = new TranscricaoDAO();

        if (idTranscricao <= 0) {
            throw new IllegalArgumentException("O ID da transcrição deve ser maior que zero.");
        }

        Transcricao transcricao = transcricaoDAO.buscarPorId(idTranscricao);

        if (transcricao == null) {
            throw new NoSuchElementException("Transcrição não encontrada.");
        }

        return insightDAO.buscarPorIdTranscricao(idTranscricao);
    }

    // Insert
    public int inserirBo(Insight insight) throws SQLException, ClassNotFoundException {

        InsightDAO insightDAO = new InsightDAO();
        TranscricaoDAO transcricaoDAO = new TranscricaoDAO();

        normalizarInsight(insight);
        validarInsight(insight);

        Transcricao transcricao = transcricaoDAO.buscarPorId(insight.getIdTranscricao());

        if (transcricao == null) {
            throw new NoSuchElementException("A transcrição informada não foi encontrada.");
        }

        return insightDAO.inserir(insight);
    }

    // Update
    public boolean atualizarBo(Insight insight) throws SQLException, ClassNotFoundException {

        InsightDAO insightDAO = new InsightDAO();
        TranscricaoDAO transcricaoDAO = new TranscricaoDAO();

        if (insight == null) {
            throw new IllegalArgumentException("Os dados do insight são obrigatórios.");
        }

        if (insight.getIdInsight() <= 0) {
            throw new IllegalArgumentException("O ID do insight deve ser maior que zero.");
        }

        normalizarInsight(insight);
        validarInsight(insight);

        Insight insightCadastrado = insightDAO.buscarPorId(insight.getIdInsight());

        if (insightCadastrado == null) {
            throw new NoSuchElementException("Insight não encontrado.");
        }

        Transcricao transcricao = transcricaoDAO.buscarPorId(insight.getIdTranscricao());

        if (transcricao == null) {
            throw new NoSuchElementException("A transcrição informada não foi encontrada.");
        }

        return insightDAO.atualizar(insight);
    }

    // Delete
    public boolean deletarBo(int idInsight) throws SQLException, ClassNotFoundException {

        InsightDAO insightDAO = new InsightDAO();

        if (idInsight <= 0) {
            throw new IllegalArgumentException("O ID do insight deve ser maior que zero.");
        }

        Insight insight = insightDAO.buscarPorId(idInsight);

        if (insight == null) {
            throw new NoSuchElementException("Insight não encontrado.");
        }

        return insightDAO.deletar(idInsight);
    }

    // Analisar o conteúdo de uma transcrição
    public ArrayList<Insight> analisarTranscricaoBo(int idTranscricao) throws SQLException, ClassNotFoundException {

        InsightDAO insightDAO = new InsightDAO();
        TranscricaoDAO transcricaoDAO = new TranscricaoDAO();

        if (idTranscricao <= 0) {
            throw new IllegalArgumentException("O ID da transcrição deve ser maior que zero.");
        }

        Transcricao transcricao = transcricaoDAO.buscarPorId(idTranscricao);

        if (transcricao == null) {
            throw new NoSuchElementException("Transcrição não encontrada.");
        }

        String conteudo = transcricao.getConteudo().toLowerCase();

        ArrayList<Insight> insightsGerados = new ArrayList<>();

        // Remove análises anteriores para evitar duplicações
        insightDAO.deletarPorIdTranscricao(idTranscricao);

        if (contemAlgumaPalavra(conteudo, "atraso", "atrasado", "prazo", "urgente")) {

            salvarInsight(insightsGerados, insightDAO, idTranscricao, "RISCO",
                    "Foi identificado um possível risco relacionado a prazo ou atraso.",
                    "NEGATIVO",
                    "Revisar o cronograma e definir responsáveis pelos prazos."
            );
        }

        if (contemAlgumaPalavra(conteudo, "problema", "erro", "falha", "dificuldade")) {

            salvarInsight(insightsGerados, insightDAO, idTranscricao,
                    "PROBLEMA",
                    "Foi identificado um problema que necessita de acompanhamento.",
                    "NEGATIVO",
                    "Definir um responsável para investigar e resolver o problema."
            );
        }

        if (contemAlgumaPalavra(conteudo, "decisão", "decisao", "decidido", "definido", "aprovado")) {

            salvarInsight(insightsGerados, insightDAO, idTranscricao,
                    "DECISAO",
                    "A transcrição apresenta uma decisão tomada durante a reunião.",
                    "NEUTRO",
                    "Registrar a decisão e comunicá-la aos participantes."
            );
        }

        if (contemAlgumaPalavra(conteudo, "ação", "acao", "tarefa", "responsável", "responsavel", "entrega")) {

            salvarInsight(insightsGerados, insightDAO, idTranscricao,
                    "ACAO",
                    "Foi identificada uma possível ação ou tarefa na reunião.",
                    "NEUTRO",
                    "Definir responsável e prazo para acompanhar a ação."
            );
        }

        if (contemAlgumaPalavra(conteudo, "oportunidade", "melhoria", "sucesso", "resultado positivo", "elogio")) {

            salvarInsight(insightsGerados, insightDAO, idTranscricao,
                    "OPORTUNIDADE",
                    "Foi identificada uma oportunidade ou resultado positivo.",
                    "POSITIVO",
                    "Avaliar como aproveitar ou repetir o resultado identificado."
            );
        }

        // Caso nenhuma palavra-chave seja encontrada
        if (insightsGerados.isEmpty()) {

            salvarInsight(insightsGerados, insightDAO, idTranscricao,
                    "RESUMO",
                    "Nenhum risco, problema, decisão ou ação foi identificado automaticamente.",
                    "NEUTRO",
                    "Revisar manualmente a transcrição para identificar outros pontos."
            );
        }

        return insightsGerados;
    }

    // Salvar um insight gerado pela análise
    private void salvarInsight(ArrayList<Insight> insightsGerados, InsightDAO insightDAO, int idTranscricao, String tipo,
                               String descricao, String sentimento, String sugestaoAcao) throws SQLException, ClassNotFoundException {

        Insight novoInsight = new Insight(0, idTranscricao, tipo, descricao, sentimento, sugestaoAcao);

        int idInsight = insightDAO.inserir(novoInsight);

        Insight insightSalvo = new Insight(idInsight, idTranscricao, tipo, descricao, sentimento, sugestaoAcao);

        insightsGerados.add(insightSalvo);
    }

    // Verificar palavras-chave
    private boolean contemAlgumaPalavra(String conteudo, String... palavras) {

        for (String palavra : palavras) {
            if (conteudo.contains(palavra)) {
                return true;
            }
        }

        return false;
    }

    // Regras de negócio
    private void validarInsight(Insight insight) {

        if (insight == null) {
            throw new IllegalArgumentException("Os dados do insight são obrigatórios.");
        }

        if (insight.getIdTranscricao() <= 0) {
            throw new IllegalArgumentException("O ID da transcrição deve ser maior que zero.");
        }

        validarTipo(insight.getTipo());
        validarDescricao(insight.getDescricao());
        validarSentimento(insight.getSentimento());
        validarSugestaoAcao(insight.getSugestaoAcao());
    }

    private void validarTipo(String tipo) {

        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("O tipo do insight é obrigatório.");
        }

        if (tipo.length() > 50) {
            throw new IllegalArgumentException("O tipo deve possuir no máximo 50 caracteres.");
        }

        boolean tipoValido = tipo.equals("RISCO")
                        || tipo.equals("PROBLEMA")
                        || tipo.equals("DECISAO")
                        || tipo.equals("ACAO")
                        || tipo.equals("OPORTUNIDADE")
                        || tipo.equals("RESUMO");

        if (!tipoValido) {
            throw new IllegalArgumentException("O tipo informado não é válido.");
        }
    }

    private void validarDescricao(String descricao) {

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição do insight é obrigatória.");
        }

        if (descricao.length() > 200) {
            throw new IllegalArgumentException("A descrição deve possuir no máximo 200 caracteres.");
        }
    }

    private void validarSentimento(String sentimento) {

        if (sentimento == null || sentimento.isBlank()) {
            return;
        }

        if (sentimento.length() > 30) {
            throw new IllegalArgumentException("O sentimento deve possuir no máximo 30 caracteres.");
        }

        boolean sentimentoValido = sentimento.equals("POSITIVO") || sentimento.equals("NEGATIVO") || sentimento.equals("NEUTRO");

        if (!sentimentoValido) {
            throw new IllegalArgumentException("O sentimento deve ser POSITIVO, NEGATIVO ou NEUTRO.");
        }
    }

    private void validarSugestaoAcao(String sugestaoAcao) {

        if (sugestaoAcao != null && sugestaoAcao.length() > 200) {

            throw new IllegalArgumentException("A sugestão de ação deve possuir no máximo 200 caracteres.");
        }
    }

    private void normalizarInsight(Insight insight) {

        if (insight == null) {
            return;
        }

        if (insight.getTipo() != null) {
            insight.setTipo(insight.getTipo().trim().toUpperCase());
        }

        if (insight.getDescricao() != null) {
            insight.setDescricao(insight.getDescricao().trim());
        }

        if (insight.getSentimento() != null) {
            insight.setSentimento(insight.getSentimento().trim().toUpperCase());
        }

        if (insight.getSugestaoAcao() != null) {
            insight.setSugestaoAcao(insight.getSugestaoAcao().trim());
        }
    }
}

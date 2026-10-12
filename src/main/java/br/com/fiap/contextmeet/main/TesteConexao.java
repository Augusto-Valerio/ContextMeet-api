package br.com.fiap.contextmeet.main;

import br.com.fiap.contextmeet.conexoes.ConexaoFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {
        ConexaoFactory conexaoFactory = new ConexaoFactory();

        try (Connection conexao = conexaoFactory.conexao()) {
            System.out.println("Conectado ao banco de dados Oracle com sucesso!");
        } catch (IllegalStateException e) {
            System.out.println("Erro de configuração: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Oracle não encontrado.");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o Oracle: " + e.getMessage());
        }
    }
}

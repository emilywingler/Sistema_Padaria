package io;

import java.io.*;
import java.util.*;

public class Escrita {

    private void escreverArquivo(String caminho, String cabecalho, List<String[]> dados) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {
            pw.println(cabecalho);
            for (String[] linha : dados) {
                pw.println(String.join(";", linha));
            }
        } catch (IOException e) {
            System.out.println("Erro de I/O.");
            System.exit(1);
        }
    }
    
    public void escreverLinha(String caminho, String[] linha) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho, true))) {
            pw.println(String.join(";", linha));
        } catch (IOException e) {
            System.out.println("Erro de I/O.");
        }
    }
    
    public void escreverClientes(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "idCliente;nome;endereco;telefone;dataCadastro;tipo;documento;inscricaoEstadual", dados);
    }
    
    public void escreverFornecedores(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "idFornecedor;nome;endereco;telefone;cnpj;pessoaContato", dados);
    }

    public void escreverApagar(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "nomeFornecedor;cnpj;pessoaContato;telefone;totalAPagar", dados);
    }

    public void escreverAreceber(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "nomeCliente;tipo;cpfCnpj;telefone;dataCadastro;totalAReceber", dados);
    }

    public void escreverVendasPorProduto(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "idProduto;descricao;receitaBruta;lucro", dados);
    }

    public void escreverVendasPorPagamento(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "meioPagamento;receitaBruta;lucro", dados);
    }

    public void escreverEstoque(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "idProduto;descricao;estoqueAtual;observacao", dados);
    }
}


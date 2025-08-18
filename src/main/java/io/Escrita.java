package io;

import java.io.*;
import java.util.*;

/**
 * Classe utilitária responsável pela escrita de arquivos CSV para diferentes relatórios do sistema.
 * <p>
 * Todos os métodos públicos utilizam o método privado {@link #escreverArquivo(String, String, List)}
 * que abstrai a lógica de escrita, garantindo consistência no formato CSV e tratamento de erros de I/O.
 * </p>
 * <p>
 * Relatórios gerados por esta classe incluem:
 * <ul>
 *   <li>Contas a pagar de fornecedores</li>
 *   <li>Contas a receber de clientes</li>
 *   <li>Vendas agrupadas por produto</li>
 *   <li>Vendas agrupadas por meio de pagamento</li>
 *   <li>Estoque de produtos</li>
 * </ul>
 */
public class Escrita {

    /**
     * Escreve os dados fornecidos em um arquivo CSV com um cabeçalho.
     * <p>
     * Cada array de {@code String} na lista {@code dados} representa uma linha no CSV.
     * Os valores são separados por ponto e vírgula (";").
     * </p>
     *
     * @param caminho caminho completo do arquivo de saída
     * @param cabecalho linha de cabeçalho do CSV
     * @param dados lista de arrays de {@code String}, cada array representa uma linha de dados
     */
    private void escreverArquivo(String caminho, String cabecalho, List<String[]> dados) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {
            pw.println(cabecalho);
            for (String[] linha : dados) {
                pw.println(String.join(";", linha));
            }
        } catch (IOException e) {
            System.out.println("Erro de I/O ao escrever o arquivo: " + caminho);
            System.exit(1);
        }
    }

    /**
     * Gera um arquivo CSV contendo as contas a pagar de fornecedores.
     *
     * @param caminho caminho do arquivo CSV de saída
     * @param dados lista de dados das contas a pagar, cada item é um array de {@code String} representando uma linha
     */
    public void escreverApagar(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "nomeFornecedor;cnpj;pessoaContato;telefone;totalAPagar", dados);
    }

    /**
     * Gera um arquivo CSV contendo as contas a receber de clientes.
     *
     * @param caminho caminho do arquivo CSV de saída
     * @param dados lista de dados das contas a receber, cada item é um array de {@code String} representando uma linha
     */
    public void escreverAreceber(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "nomeCliente;tipo;cpfCnpj;telefone;dataCadastro;totalAReceber", dados);
    }

    /**
     * Gera um arquivo CSV contendo as vendas agrupadas por produto.
     *
     * @param caminho caminho do arquivo CSV de saída
     * @param dados lista de dados das vendas por produto, cada item é um array de {@code String} representando uma linha
     */
    public void escreverVendasPorProduto(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "idProduto;descricao;receitaBruta;lucro", dados);
    }

    /**
     * Gera um arquivo CSV contendo as vendas agrupadas por meio de pagamento.
     *
     * @param caminho caminho do arquivo CSV de saída
     * @param dados lista de dados das vendas por pagamento, cada item é um array de {@code String} representando uma linha
     */
    public void escreverVendasPorPagamento(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "meioPagamento;receitaBruta;lucro", dados);
    }

    /**
     * Gera um arquivo CSV contendo informações de estoque de produtos.
     *
     * @param caminho caminho do arquivo CSV de saída
     * @param dados lista de dados de estoque, cada item é um array de {@code String} representando uma linha
     */
    public void escreverEstoque(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "idProduto;descricao;estoqueAtual;observacao", dados);
    }
}

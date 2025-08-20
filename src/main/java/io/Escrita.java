package io;

import java.io.*;
import java.util.*;

/**
 * Classe utilitária responsável pela escrita de arquivos CSV para diferentes entidades e relatórios do sistema.
 * <p>
 * Todos os métodos públicos de escrita de relatórios utilizam o método privado {@link #escreverArquivo(String, String, List)}
 * que abstrai a lógica de escrita, garantindo consistência no formato CSV e tratamento de erros de I/O.
 * </p>
 * <p>
 * Entidades e relatórios gerados por esta classe incluem:
 * <ul>
 * <li>Clientes</li>
 * <li>Fornecedores</li>
 * <li>Produtos</li>
 * <li>Vendas</li>
 * <li>Compras</li>
 * <li>Contas a pagar de fornecedores</li>
 * <li>Contas a receber de clientes</li>
 * <li>Vendas agrupadas por produto</li>
 * <li>Vendas agrupadas por meio de pagamento</li>
 * <li>Estoque de produtos</li>
 * </ul>
 */
public class Escrita {

    /**
     * Escreve os dados fornecidos em um arquivo CSV com um cabeçalho, sobrescrevendo o arquivo se ele existir.
     * <p>
     * Cada array de {@code String} na lista {@code dados} representa uma linha no CSV.
     * Os valores são separados por ponto e vírgula (";").
     * </p>
     *
     * @param caminho caminho completo do arquivo de saída
     * @param cabecalho linha de cabeçalho do CSV
     * @param dados lista de arrays de {@code String}, onde cada array representa uma linha de dados
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
     * Adiciona uma única linha ao final de um arquivo CSV existente, sem sobrescrevê-lo.
     * <p>
     * Este método é útil para adicionar novos registros a um arquivo de dados sem a necessidade
     * de ler e reescrever o arquivo inteiro.
     * </p>
     *
     * @param caminho o caminho completo para o arquivo CSV.
     * @param linha um array de {@code String} representando os dados da linha a ser adicionada.
     */
    public void escreverLinha(String caminho, String[] linha) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho, true))) {
            bw.write(String.join(";", linha));
            bw.newLine(); 
        } catch (IOException e) {
            System.out.println("Erro de I/O: " + e.getMessage());
        }
    }
    
    /**
     * Gera um arquivo CSV com os dados de clientes.
     *
     * @param caminho o caminho do arquivo CSV de saída.
     * @param dados uma lista de arrays de {@code String}, cada um representando um cliente.
     */
    public void escreverClientes(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "idCliente;nome;endereco;telefone;dataCadastro;tipo;documento;inscricaoEstadual", dados);
    }
    
    /**
     * Gera um arquivo CSV com os dados de fornecedores.
     *
     * @param caminho o caminho do arquivo CSV de saída.
     * @param dados uma lista de arrays de {@code String}, cada um representando um fornecedor.
     */
    public void escreverFornecedores(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "idFornecedor;nome;endereco;telefone;cnpj;pessoaContato", dados);
    }
    
    /**
     * Gera um arquivo CSV com os dados de produtos.
     *
     * @param caminho o caminho do arquivo CSV de saída.
     * @param dados uma lista de arrays de {@code String}, cada um representando um produto.
     */
    public void escreverProdutos(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "idProduto;descricao;estoqueMinimo;estoqueAtual;valorCusto;percentualLucro", dados);
    }  
    
    /**
     * Gera um arquivo CSV com os dados de vendas.
     *
     * @param caminho o caminho do arquivo CSV de saída.
     * @param dados uma lista de arrays de {@code String}, cada um representando uma venda.
     */
    public void escreverVendas(String caminho, List<String[]> dados){
        escreverArquivo(caminho,"idCliente;dataVenda;idProduto;quantidade;meioPagamento", dados);
    }
    
    /**
     * Gera um arquivo CSV com os dados de compras.
     *
     * @param caminho o caminho do arquivo CSV de saída.
     * @param dados uma lista de arrays de {@code String}, cada um representando uma compra.
     */
    public void escreverCompras(String caminho, List<String[]> dados){
        escreverArquivo(caminho,"numeroNF;idFornecedor;dataCompra;idProduto;quantidade", dados);
    }

    /**
     * Gera um arquivo CSV contendo o relatório de contas a pagar de fornecedores.
     *
     * @param caminho caminho do arquivo CSV de saída
     * @param dados lista de dados das contas a pagar, cada item é um array de {@code String} representando uma linha
     */
    public void escreverApagar(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "nomeFornecedor;cnpj;pessoaContato;telefone;totalAPagar", dados);
    }

    /**
     * Gera um arquivo CSV contendo o relatório de contas a receber de clientes.
     *
     * @param caminho caminho do arquivo CSV de saída
     * @param dados lista de dados das contas a receber, cada item é um array de {@code String} representando uma linha
     */
    public void escreverAreceber(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "nomeCliente;tipo;cpfCnpj;telefone;dataCadastro;totalAReceber", dados);
    }

    /**
     * Gera um arquivo CSV contendo o relatório de vendas agrupadas por produto.
     *
     * @param caminho caminho do arquivo CSV de saída
     * @param dados lista de dados das vendas por produto, cada item é um array de {@code String} representando uma linha
     */
    public void escreverVendasPorProduto(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "idProduto;descricao;receitaBruta;lucro", dados);
    }

    /**
     * Gera um arquivo CSV contendo o relatório de vendas agrupadas por meio de pagamento.
     *
     * @param caminho caminho do arquivo CSV de saída
     * @param dados lista de dados das vendas por pagamento, cada item é um array de {@code String} representando uma linha
     */
    public void escreverVendasPorPagamento(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "meioPagamento;receitaBruta;lucro", dados);
    }

    /**
     * Gera um arquivo CSV contendo o relatório de estoque de produtos.
     *
     * @param caminho caminho do arquivo CSV de saída
     * @param dados lista de dados de estoque, cada item é um array de {@code String} representando uma linha
     */
    public void escreverEstoque(String caminho, List<String[]> dados) {
        escreverArquivo(caminho, "idProduto;descricao;estoqueAtual;observacao", dados);
    }
}

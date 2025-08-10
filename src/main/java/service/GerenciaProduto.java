
package service;

import model.Produto;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import io.Leitura;
import io.Escrita;


/**
 *
 * @author mikae
 */
public class GerenciaProduto{
    private List<Produto> produtos; //lista que armazena os objetos Produto em memoria
    private final String ARQUIVO_PRODUTO = "produtos.csv"; //nome do arquivo csv que os produtos sao salvos 
    private Leitura leitorCSV;//objeto responsavel por lerdados no csv
    private Escrita escritorCSV;//objeto responsavel por escrever dados no csv
    
    public GerenciaProduto() {
        produtos = new ArrayList<>();
        leitorCSV = new Leitura();
        escritorCSV = new Escrita();
    }
    
    public void carregarProdutosCSV(String caminhoArquivo){
        List<String[]> linhas = leitorCSV.lerArquivo(caminhoArquivo);
        
        for (String[] campos : linhas) {
            int idProduto = Integer.parseInt(campos[0]);
            String descricao = campos[1];
            int minEstoque = Integer.parseInt(campos[2]);
            int estoqueAtual = Integer.parseInt(campos[3]);
            BigDecimal custo = new BigDecimal(campos[4]);
            int percentualLucro = Integer.parseInt(campos[5]);
            
            Produto produto = new Produto(idProduto, descricao, minEstoque, estoqueAtual, custo, percentualLucro);
            produtos.add(produto);
       
        }
        
    }
    
    //aqui adiciona o novo produto na lista em memoria
    //chama o metodo atualizarArquivo para reescrer o csv com o produtos atualizados 
    public void inserirProduto(Produto p) {
        produtos.add(p);
        //escritorCSV.atualizarArquivo(ARQUIVO_PRODUTO);
    }
    
    public void removerProduto(int codigo) {
        Produto p = buscarProduto(codigo);
        if (p != null) {
            produtos.remove(p);
            //escritorCSV.atualizarArquivo(ARQUIVO_PRODUTO);
        } else {
            System.out.println("Produto não encontrado.");
        }
    }
    
    //percorre a lista e retorna o produto com o id correspondente 
    public Produto buscarProduto(int codigo) {
        for (Produto p : produtos) {
            if (p.getIdProduto() == codigo) return p;
        }
        return null;
    }
    
    //exibe todos os produtos cadastrados no terminal com suas informações 
    //mostra o valor de venda ja calculado
     public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Lista de produtos vazia.");
        } else {
            for (Produto p : produtos) {
                System.out.println(p); // usa o toString()
            }
        }
     }
    
    //permite editar um produto interativamente 
    //as alterações são feitas diretamente no objeto em memoria 
    // e no final salva todas as mudanças no arquivo csv 
    public void editarProduto(int codigo) {
        Produto p = buscarProduto(codigo);
        if (p == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int op = 0;

        while (op != 6) {
            System.out.println("""
                               1. Descrição
                               2. Estoque Mínimo
                               3. Estoque Atual
                               4. Custo
                               5. Percentual de Lucro
                               6. Sair
                               """);
            System.out.print("Escolha uma opção: ");
            op = sc.nextInt(); sc.nextLine();

            switch (op) {
                case 1 -> {
                    System.out.print("Nova descrição: ");
                    p.setDescricao(sc.nextLine());
                }
                case 2 -> {
                    System.out.print("Novo estoque mínimo: ");
                    p.setMinEstoque(sc.nextInt());
                }
                case 3 -> {
                    System.out.print("Novo estoque atual: ");
                    p.setEstoqueAtual(sc.nextInt());
                }
                case 4 -> {
                    System.out.print("Novo valor de custo: ");
                    p.setCusto(new BigDecimal(sc.nextLine()));
                }
                case 5 -> {
                    System.out.print("Novo percentual de lucro: ");
                    p.setPercentualLucro(sc.nextInt());
                }
                case 6 -> {}
                default -> System.out.println("Opção inválida.");
            }
        }

        //escritorCSV.atualizarArquivo(ARQUIVO_PRODUTO);
        // sc.close(); // não feche aqui!
    }
        
    //analisar se estoque mim será usada em outra classe para obter esse retorno 
    public void verificarEstoqueBaixo(){
        for (Produto p : produtos) {
            if (p.getEstoqueAtual() < p.getMinEstoque()) {
                System.out.println(" PRODUTO COM ESTOQUE BAIXO: " +
                                   p.getDescricao() + " (Estoque: " + 
                                   p.getEstoqueAtual() + "/" + p.getMinEstoque() + ")");
            }
        }
    }
    public boolean existeProdutoComEstoqueBaixo() {
        for (Produto p : produtos) {
            if (p.getEstoqueAtual() < p.getMinEstoque()) {
                return true; // achou pelo menos 1
            }
        }
    return false; // nenhum produto está abaixo do mínimo
    }
    
}
          



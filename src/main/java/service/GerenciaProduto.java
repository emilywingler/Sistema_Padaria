
package service;

import model.Produto;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import io.Leitura;
import io.Escrita;

/**
 * Classe responsável pelo gerenciamento de produtos,
 * permite operações de CRUD (criar, ler, atualizar, remover),
 * verificação de estoque baixo e integração com arquivos CSV.
 * 
 * <p>
 * Esta classe mantém uma lista de produtos em memória e fornece
 * métodos para manipular esses produtos. Os dados podem ser
 * carregados ou salvos em arquivos CSV através das classes Leitura e Escrita.
 * </p>
 * 
 *  @author mikae
 */

public class GerenciaProduto{
    /** Lista que armazena os objetos Produto em memória */
    private List<Produto> produtos;
    
    /** Nome do arquivo CSV onde os produtos são salvos */
    private final String ARQUIVO_PRODUTO = "produtos.csv";
    
    /** Objeto responsável por ler dados de arquivos CSV */
    private Leitura leitorCSV;
    
    /** Objeto responsável por escrever dados em arquivos CSV */
    private Escrita escritorCSV;
    
    /**
     * Construtor da classe GerenciaProduto.
     * Inicializa a lista de produtos e os objetos de leitura/escrita de CSV.
     */
    public GerenciaProduto() {
        produtos = new ArrayList<>();
        leitorCSV = new Leitura();
        escritorCSV = new Escrita();     
    }
    
    
    /**
     * Carrega produtos de um arquivo CSV para a memória.
     * 
     * @param caminhoArquivo Caminho do arquivo CSV a ser lido.
     */
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
    
    /**
    * Realiza o cadastro de um novo produto interativamente via terminal.
    * 
    * O método solicita ao usuário todos os dados necessários para criar um objeto {@link Produto}, 
    * incluindo ID, descrição, estoque mínimo, estoque atual, custo e percentual de lucro.
    * 
    * Antes de aceitar o ID, o método verifica se já existe um produto com o mesmo identificador. 
    * Caso exista, o usuário será solicitado a digitar um novo ID até que seja único.
    * 
    * Ao final, o produto é criado e adicionado à lista interna {@code produtos}, 
    * e uma mensagem de sucesso é exibida.
    *
    * @param sc Scanner utilizado para leitura dos dados digitados pelo usuário no terminal.
    */
    public void inserirProduto(Scanner sc) {
        
        int idProduto;
        
        while (true) {
            System.out.println("Digite o ID do produto: ");
            idProduto = sc.nextInt();
            sc.nextLine();

            if (this.buscarProduto(idProduto) != null) {
                System.out.println("Já existe um produto com esse ID! Digite novamente");
            } else {
                break; // sai do loop quando o ID é válido
            }
        }

        System.out.println("Digite a descrição do produto: ");
        String descricao = sc.nextLine();

        System.out.println("Digite o estoque mínimo: ");
        int minEstoque= sc.nextInt();
        sc.nextLine();

        System.out.println("Digite o estoque atual: ");
        int estoqueAtual = sc.nextInt();
        sc.nextLine();

        System.out.println("Digite o valor de custo desse produto: ");
        String custoString = sc.nextLine();
        BigDecimal custo = new BigDecimal(custoString);

        System.out.println("Digite o percentual de lucro: ");
        int percentualLucro = sc.nextInt();
        sc.nextLine();

        Produto produto = new Produto(idProduto, descricao, minEstoque, estoqueAtual, custo, percentualLucro);

        produtos.add(produto);
        String[] linha = new String[]{
        String.valueOf(produto.getIdProduto()),
            produto.getDescricao(),
            String.valueOf(produto.getMinEstoque()),
            String.valueOf(produto.getEstoqueAtual()),
            String.valueOf(produto.getCusto()),
            String.valueOf(produto.getPercentualLucro())
        };
        escritorCSV.escreverLinha(ARQUIVO_PRODUTO, linha);
        System.out.println(">>> Produto cadastrado com sucesso! <<<");
    }

    
    /**
     * Remove um produto da lista com base no seu ID.
     * 
     * @param idProduto ID do produto a ser removido.
     */
    public void removerProduto(int idProduto) {
        Produto p = buscarProduto(idProduto);
        if (p != null) {
            produtos.remove(p);
            reescreverProdutosCSV();
        } else {
            System.out.println("Produto não encontrado.");
        }
    }
    
    /**
     * Busca um produto pelo seu ID.
     * 
     * @param idProduto ID do produto a ser buscado.
     * @return Produto correspondente ao ID ou null se não encontrado.
     */
    public Produto buscarProduto(int idProduto) {
        for (Produto p : produtos) {
            if (p.getIdProduto() == idProduto) return p;
        }
        return null;
    }
    
    /**
     * Lista todos os produtos cadastrados no console.
     */
    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Lista de produtos vazia.");
        } else {
            for (Produto p : produtos) {
                System.out.println(p); // usa o toString()
            }
        }
    }
    
    
    /**
     * Permite editar os atributos de um produto pelo seu ID.
     * Exibe um menu interativo no console para alterar descrição,
     * estoque mínimo, estoque atual, custo e percentual de lucro.
     * 
     * @param codigo ID do produto a ser editado.
     */
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
            op = sc.nextInt();
            sc.nextLine(); // consome a quebra de linha após nextInt()

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
                    try {
                        p.setCusto(sc.nextBigDecimal());
                        sc.nextLine(); // consome a quebra de linha
                    } catch (Exception e) {
                        System.out.println("Valor inválido para custo.");
                        sc.nextLine(); // limpa entrada inválida
                    }
                }
                case 5 -> {
                    System.out.print("Novo percentual de lucro: ");
                    p.setPercentualLucro(sc.nextInt());
                }
                case 6 -> {}
                default -> System.out.println("Opção inválida.");
            }
        }

        reescreverProdutosCSV();
    }

  
    /**
     * Verifica quais produtos estão com estoque abaixo do mínimo.
     */ 
    public void verificarEstoqueBaixo(){
        for (Produto p : produtos) {
            if (p.getEstoqueAtual() < p.getMinEstoque()) {
                System.out.println(" PRODUTO COM ESTOQUE BAIXO: " +
                                   p.getDescricao() + " (Estoque: " + 
                                   p.getEstoqueAtual() + "/" + p.getMinEstoque() + ")");
            }
        }
    }
    
    /**
     * Verifica se existe algum produto com estoque abaixo do mínimo.
     * 
     * @return true se existir pelo menos um produto com estoque baixo, false caso contrário.
     */
    public boolean existeProdutoComEstoqueBaixo() {
        for (Produto p : produtos) {
            if (p.getEstoqueAtual() < p.getMinEstoque()) {
                return true; // achou pelo menos 1
            }
        }
    return false; // nenhum produto está abaixo do mínimo
    }
    
    
    /**
     * Gera um novo ID único para um produto.
     * 
     * @return Próximo ID disponível.
     */
    public int gerarNovoId() {
        int maxId = 0;
        for (Produto p : produtos) {
            if (p.getIdProduto() > maxId) {
                maxId = p.getIdProduto();
            }
        }
        return maxId + 1;
    }

    public void reescreverProdutosCSV() {
    List<String[]> dados = new ArrayList<>();
    for (Produto p : produtos) {
        dados.add(new String[]{
            String.valueOf(p.getIdProduto()),
            p.getDescricao(),
            String.valueOf(p.getMinEstoque()),
            String.valueOf(p.getEstoqueAtual()),
            String.valueOf(p.getCusto()),
            String.valueOf(p.getPercentualLucro())
        });
    }
    escritorCSV.escreverProdutos(ARQUIVO_PRODUTO, dados);
    }
}       



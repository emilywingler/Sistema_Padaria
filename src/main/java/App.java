
import java.util.Scanner;
import model.*;
import io.*;
import java.util.List;
import report.*;
import service.*;

/**
 * Classe principal da aplicação da Padaria.
 * <p>
 * Responsável por inicializar o sistema, carregar os dados a partir de arquivos CSV
 * (clientes, fornecedores, produtos, compras e vendas) e oferecer um menu interativo
 * no console para o usuário realizar operações de cadastro, registro de vendas,
 * controle de contas e geração de relatórios.
 * </p>
 *
 * <p>
 * Principais funcionalidades:
 * <ul>
 *   <li>Cadastro de clientes, fornecedores e produtos</li>
 *   <li>Registro e listagem de vendas</li>
 *   <li>Controle de contas a pagar e a receber</li>
 *   <li>Geração de relatórios mensais em arquivos CSV</li>
 * </ul>
 */
public class App {
    private static String caminhoArquivoCliente;
    private static String caminhoArquivoFornecedor;
    private static String caminhoArquivoProduto;
    private static String caminhoArquivoCompra;
    private static String caminhoArquivoVenda;
    
    private static GerenciaCliente gerenciaCliente= new GerenciaCliente();
    private static GerenciaProduto gerenciaProduto = new GerenciaProduto();
    private static GerenciaFornecedor gerenciaFornecedor = new GerenciaFornecedor();
    private static GerenciaCompra gerenciaCompra = new GerenciaCompra(gerenciaProduto,gerenciaFornecedor);
    private static GerenciaVenda gerenciaVenda = new GerenciaVenda(gerenciaProduto,gerenciaCliente);
    
    /**
     * Método principal da aplicação.
     * Inicializa os gerenciadores, carrega os arquivos CSV e exibe o menu principal.
     *
     * @param args argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int op = 0;
        
        
        while(op != 5){
            menuPrincipal();
            op = selecionarOpcao(sc);
            switch(op){
                case 1 ->{
                    menuCadastro(sc);
                }
                case 2 ->{
                    menuRegistroVendas(sc);
                }
                case 3 ->{
                    menuControleContas(sc);
                }
                case 4 ->{
                    gerarRelatorios();
                }
                case 5 ->{
                    System.out.println("Fechando aplicação!");
                    return;
                }
                default ->{
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
            
        }
    }
    
    /**
     * Exibe o menu principal com as opções disponíveis.
     */
    public static void menuPrincipal(){
        System.out.println("""
                           ----------------------------------
                               M E N U  P R I N C I P A L
                           ----------------------------------
                           1. Cadastro (Clientes, Fornecedores, Produtos)
                           2. Registro de Vendas
                           3. Controle de Contas (a Pagar e a Receber)
                           4. Geração de Relatórios Mensais
                           5. Sair             
                           """);
    }
    
    /**
     * Exibe o menu de cadastro e permite inserir clientes, fornecedores ou produtos.
     *
     * @param sc Scanner para entrada de dados do usuário.
     */
    public static void menuCadastro(Scanner sc){
        int op=0;
        while(op != 4){
            System.out.println("""
                           ----------------------------------
                                M E N U  C A D A S T R O
                           ----------------------------------
                           1. Cadastrar Novo Cliente
                           2. Cadastrar Novo Fornecedor
                           3. Cadastrar Novo Produto
                           4. Voltar ao Menu Principal
                           5. Carregar Arquivos CSV's
                           """);
            op = selecionarOpcao(sc);

            switch(op){
                case 1 ->{
                    gerenciaCliente.inserirCliente(sc);
                }
                case 2 ->{
                    gerenciaFornecedor.inserirFornecedor(sc);
                }
                case 3 ->{
                    gerenciaProduto.inserirProduto(sc);
                }
                case 4 ->{ 
                    return;
                }
                case 5 ->{
                    sc.nextLine();
                    System.out.println("Digite o caminho do arquivo csv contendo os clientes: ");
                    caminhoArquivoCliente = sc.nextLine();
                    System.out.println("Digite o caminho do arquivo csv contendo os fornecedores: ");
                    caminhoArquivoFornecedor = sc.nextLine();
                    System.out.println("Digite o caminho do arquivo csv contendo os produtos: ");
                    caminhoArquivoProduto = sc.nextLine();
                    System.out.println("Digite o caminho do arquivo csv contendo as compras: ");
                    caminhoArquivoCompra = sc.nextLine();
                    System.out.println("Digite o caminho do arquivo csv contendo as vendas: ");
                    caminhoArquivoVenda = sc.nextLine();
                    
                    gerenciaCliente.carregarClientesCSV(caminhoArquivoCliente);
                    gerenciaFornecedor.carregarFornecedorCSV(caminhoArquivoFornecedor);
                    gerenciaProduto.carregarProdutosCSV(caminhoArquivoProduto);
                    gerenciaCompra.carregarComprasCSV(caminhoArquivoCompra);
                    gerenciaVenda.carregarVendasCSV(caminhoArquivoVenda);
                    
                    
                }
                default ->{
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }
    }
    
    /**
     * Exibe o menu de registro de vendas, permitindo cadastrar novas vendas
     * ou listar as vendas registradas.
     *
     * @param sc Scanner para entrada de dados do usuário.
     */
    public static void menuRegistroVendas(Scanner sc){
        int op=0;
        while(op != 4){
            System.out.println("""
                           ----------------------------------
                                M E N U  R E G I S T R O
                           ----------------------------------
                           1. Registrar Nova Venda
                           2. Listar Todas as Vendas
                           3. Voltar ao Menu Principal
                           """);
            op = selecionarOpcao(sc);
            switch(op){
                case 1 ->{
                    gerenciaVenda.registrarVenda(sc);
                }
                case 2 ->{
                    gerenciaVenda.listarVendas();
                }
                case 3 ->{ 
                    return;
                }
                default ->{
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }
        
    }
    
    /**
     * Exibe o menu de controle de contas, permitindo consultar
     * contas a pagar (fornecedores) ou contas a receber (clientes).
     *
     * @param sc Scanner para entrada de dados do usuário.
     */
    public static void menuControleContas(Scanner sc){
        int op=0;
        while(op != 3){
            System.out.println("""
                           ----------------------------------
                                M E N U  C O N T R O L E
                           ----------------------------------
                           1. Visualizar Contas a Pagar (Fornecedores)
                           2. Visualizar Contas a Receber (Clientes)
                           3. Voltar ao Menu Principal
                           """);
            op = selecionarOpcao(sc);
            switch(op){
                case 1 ->{
                    gerenciaCompra.listarCompras();
                }
                case 2 ->{
                    gerenciaVenda.listarVendas();
                }
                case 3 ->{
                    return;
                }
                default ->{
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }

    }
    
    /**
     * Gera relatórios em formato CSV para:
     * <ul>
     *   <li>Contas a pagar</li>
     *   <li>Contas a receber</li>
     *   <li>Estoque</li>
     *   <li>Vendas por forma de pagamento</li>
     *   <li>Vendas por produto</li>
     * </ul>
     */
    public static void gerarRelatorios(){
        Apagar apagar = new Apagar(gerenciaCompra,gerenciaFornecedor); 
        apagar.gerarCSV("1-apagar.csv");
        
        Areceber areceber = new Areceber(gerenciaVenda,gerenciaCliente);
        areceber.gerarCSV("2-areceber.csv");
        
        Estoque estoque = new Estoque(gerenciaProduto);
        estoque.gerarCSV("5-estoque.csv");
        
        VendasPorPagamento vppg = new VendasPorPagamento(gerenciaVenda);
        vppg.gerarCSV("4-vendaspgto.csvv");
        
        VendasPorProduto vpp = new VendasPorProduto(gerenciaVenda,gerenciaProduto);
        vpp.gerarCSV("3-vendasprod.csv");
        
        System.out.println("<<< Relatórios gerados com sucesso! >>>");
    }
    
    /**
     * Função utilizada para guardar a opção que será utilizada para guiar o menu.
     * 
     * @param sc Scanner para entrada de dados do usuário.
     * @return opção seleionada
     */
    public static int selecionarOpcao(Scanner sc){
        System.out.println("Selecione uma opção: ");
        return sc.nextInt();
    }
}
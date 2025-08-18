
import java.util.Scanner;
import model.*;
import io.*;
import java.util.List;
import report.*;
import service.*;

@SuppressWarnings("FieldMayBeFinal")
public class App {
    private static String caminhoArquivoCliente;
    private static String caminhoArquivoFornecedor;
    private static String caminhoArquivoProduto;
    
    private static GerenciaCliente gerenciaCliente= new GerenciaCliente();
    private static GerenciaProduto gerenciaProduto = new GerenciaProduto();
    private static GerenciaFornecedor gerenciaFornecedor = new GerenciaFornecedor();
    private static GerenciaCompra gerenciaCompra = new GerenciaCompra(gerenciaProduto,gerenciaFornecedor);
    private static GerenciaVenda gerenciaVenda = new GerenciaVenda(gerenciaProduto,gerenciaCliente);
    
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int op = 0;
        gerenciaCliente.carregarClientesCSV("clientes.csv");
        gerenciaFornecedor.carregarFornecedorCSV("fornecedores.csv");
        gerenciaProduto.carregarProdutosCSV("produtos.csv");
        gerenciaCompra.carregarComprasCSV("compras.csv");
        gerenciaVenda.carregarVendasCSV("vendas.csv");
        
        
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
                default ->{
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }
    }
    
    
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
    
    public static void gerarRelatorios(){
        Apagar apagar = new Apagar(gerenciaCompra,gerenciaFornecedor); 
        apagar.gerarCSV("apagar.csv");
        
        Areceber areceber = new Areceber(gerenciaVenda,gerenciaCliente);
        areceber.gerarCSV("areceber.csv");
        
        Estoque estoque = new Estoque(gerenciaProduto);
        estoque.gerarCSV("estoque.csv");
        
        VendasPorPagamento vppg = new VendasPorPagamento(gerenciaVenda);
        vppg.gerarCSV("vendasprod.csv");
        
        VendasPorProduto vpp = new VendasPorProduto(gerenciaVenda,gerenciaProduto);
        vpp.gerarCSV("vendaspgmt.csv");
        
        System.out.println("<<< Relatórios gerados com sucesso! >>>");
    }
    
    public static int selecionarOpcao(Scanner sc){
        System.out.println("Selecione uma opção: ");
        return sc.nextInt();
    }
}
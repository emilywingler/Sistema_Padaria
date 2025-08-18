
import java.util.Scanner;
import model.*;
import io.*;
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
        //carregarClientesCSV(caminhoArquivoCliente);;
        //carregarFornecedoresCSV(caminhoArquivoFornecedor);
        //carregarProdutoCSV(caminhoArquivoProduto);
        
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
                    System.out.println("AINDA NÃO IMPLEMENTADO");
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
                    
                }
                case 2 ->{
                    
                }
                case 3 ->{ }
                default ->{
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }

    }
    
    public static int selecionarOpcao(Scanner sc){
        System.out.println("Selecione uma opção: ");
        return sc.nextInt();
    }
}
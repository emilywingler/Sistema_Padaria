
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        
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
    public static void menuCadastro(){
        System.out.println("""
                           ----------------------------------
                                M E N U  C A D A S T R O
                           ----------------------------------
                           1. Cadastrar Novo Cliente
                           2. Cadastrar Novo Fornecedor
                           3. Cadastrar Novo Produto
                           4. Voltar ao Menu Principal
                           """);
    }
    public static void menuRegistroVendas(){
        System.out.println("""
                           ----------------------------------
                                M E N U  R E G I S T R O
                           ----------------------------------
                           1. Registrar Nova Venda
                           2. Listar Todas as Vendas
                           3. Cadastrar Novo Produto
                           4. Voltar ao Menu Principal
                           """);
    }
    public static void menuControleContas(){
        System.out.println("""
                           ----------------------------------
                                M E N U  C O N T R O L E
                           ----------------------------------
                           1. Visualizar Contas a Pagar (Fornecedores)
                           2. Visualizar Contas a Receber (Clientes)
                           3. Voltar ao Menu Principal
                           """);
    }
}
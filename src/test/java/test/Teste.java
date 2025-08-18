package test;


import java.util.Scanner;
import report.Apagar;
import report.Areceber;
import report.Estoque;
import report.VendasPorPagamento;
import report.VendasPorProduto;
import service.GerenciaCliente;
import service.GerenciaCompra;
import service.GerenciaFornecedor;
import service.GerenciaProduto;
import service.GerenciaVenda;

/**
 *
 * @author clara
 */

// TESTANDO SE OS CLIENTES SÃO CARREGADOS DO CSV
public class Teste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        GerenciaCliente teste = new GerenciaCliente();
        GerenciaProduto mockGerenciaProduto = new GerenciaProduto();
        GerenciaFornecedor mockGerenciaFornecedor = new GerenciaFornecedor();
        GerenciaCompra teste1 = new GerenciaCompra(mockGerenciaProduto, mockGerenciaFornecedor);
        GerenciaVenda teste2 = new GerenciaVenda(mockGerenciaProduto, teste);
        
        teste.carregarClientesCSV("clientes.csv");
        mockGerenciaFornecedor.carregarFornecedorCSV("fornecedores.csv");
        mockGerenciaProduto.carregarProdutosCSV("produtos.csv");
        teste1.carregarComprasCSV("compras.csv");
        teste2.carregarVendasCSV("vendas.csv");
        System.out.println("Sucesso!");
        
        //teste.listarClientes();
        //mockGerenciaProduto.listarProdutos();
        //mockGerenciaFornecedor.listarFornecedores();
        teste1.listarCompras();
        teste2.listarVendas();
        
        Apagar apagar = new Apagar(teste1, mockGerenciaFornecedor); 
        apagar.gerarCSV("apagar.csv");
        
        Areceber areceber = new Areceber(teste2, teste);
        areceber.gerarCSV("areceber.csv");
        
        Estoque estoque = new Estoque(mockGerenciaProduto);
        estoque.gerarCSV("estoque.csv");
        
        VendasPorPagamento vppg = new VendasPorPagamento(teste2);
        vppg.gerarCSV("vendasprod.csv");
        
        VendasPorProduto vpp = new VendasPorProduto(teste2, mockGerenciaProduto);
        vpp.gerarCSV("vendaspgmt.csv");
        
        Scanner sc = new Scanner(System.in);
        //teste.inserirCliente(sc);
        //teste.editarCliente(5);
        //teste.removerCliente(5);
        
        
        
        
        
    }
    
}
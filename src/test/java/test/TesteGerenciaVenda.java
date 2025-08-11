package test;

import model.ClienteFisico;
import model.Produto;
import service.GerenciaCliente;
import service.GerenciaProduto;
import service.GerenciaVenda;

import java.math.BigDecimal;

public class TesteGerenciaVenda {
    public static void main(String[] args) {
        // Instanciando gerenciadores mockados
        GerenciaProduto gerenciaProduto = new GerenciaProduto();
        GerenciaCliente gerenciaCliente = new GerenciaCliente();

        // Criando produtos
        Produto produto1 = new Produto(
                1, //id 
                "Caneta", // descrição
                10, // estoque mínimo
                100, // estoque atual
                new BigDecimal("2.50"), // custo
                100); //percentual de lucro (100%)

        Produto produto2 = new Produto(
                2,
                "Caderno",
                5,
                50,
                new BigDecimal("10.00"),
                50);

        gerenciaProduto.inserirProduto(produto1);
        gerenciaProduto.inserirProduto(produto2);
        
        gerenciaProduto.listarProdutos();

        // Criando cliente
        ClienteFisico cliente = new ClienteFisico(
                1, //id
                "João", //nome
                "Rua A", // endereço
                "1111-1111", //telefone
                "01/01/2020", //data
                "Físico", //tipo do cliente
                "123.456.789-00"); //cpf

        gerenciaCliente.inserirCliente(cliente);
        gerenciaCliente.listarClientes();

        // Instanciando gerenciador de vendas
        GerenciaVenda gerenciaVenda = new GerenciaVenda(gerenciaProduto, gerenciaCliente);

        // Registrando vendas
        gerenciaVenda.registrarVenda(1, "08/08/2025", 1, 5, 'D'); // à vista
        gerenciaVenda.registrarVenda(1, 2, "08/08/2025", 2, 2, 'F'); // fiado

        // Listando vendas
        System.out.println("--- Vendas Registradas ---");
        gerenciaVenda.listarVendas();

        // Receita e lucro por produto
        System.out.println("\nReceita Produto 1: " + gerenciaVenda.receitaPorProduto(1)); // RESP. ESPERADA : 25 REAIS
        System.out.println("Lucro Produto 1: " + gerenciaVenda.lucroPorProduto(1)); 
        
        System.out.println("\nReceita Produto 2: " + gerenciaVenda.receitaPorProduto(2)); // RESP. ESPERADA : 30 REAIS
        System.out.println("Lucro Produto 2: " + gerenciaVenda.lucroPorProduto(2));

        // Receita e lucro por meio de pagamento
        System.out.println("\nReceita por MP 'D': " + gerenciaVenda.receitaPorMP('D')); // RESP. ESPERADA : 25 REAIS
        System.out.println("\nReceita por MP 'F': " + gerenciaVenda.receitaPorMP('F')); // RESP. ESPERADA : 30 REAIS
        
        System.out.println("Lucro por MP 'D': " + gerenciaVenda.lucroPorMP('D'));
        System.out.println("Lucro por MP 'F': " + gerenciaVenda.lucroPorMP('F'));

        // Total a receber do cliente
        System.out.println("\nTotal a receber do cliente 1: " + gerenciaVenda.totalAReceberCliente(1));

        // Filtrar vendas fiado
        System.out.println("\n--- Vendas Fiadas ---");
        for (var v : gerenciaVenda.filtrarVendasAReceber()) {
            System.out.println(v);
        }
    }
}


package test;
// Arquivo: GerenciaCompraTest.java

import java.math.BigDecimal;
import model.Compra;
import model.Fornecedor;
import model.Produto;
import service.GerenciaCompra;
import service.GerenciaFornecedor;
import service.GerenciaProduto;
import report.Apagar;

/**
 * Classe de teste para GerenciaCompra.
 * Para usar: Execute o método main() e verifique as saídas no console.
 */
public class GerenciaCompraTeste {

    public static void main(String[] args) {
        // --- 1. CONFIGURAÇÃO DO AMBIENTE DE TESTE ---
        System.out.println("--- INICIANDO TESTES PARA GERENCIACOMPRA ---");
        

        // Criando gerenciadores de mock (simulados) para os testes
        GerenciaProduto mockGerenciaProduto = new GerenciaProduto();
        GerenciaFornecedor mockGerenciaFornecedor = new GerenciaFornecedor();

        // Adicionando dados de teste (produtos e fornecedores)
        Produto p1 = new Produto(101, "Pão Francês", 20, 50, new BigDecimal("0.20"), 150);
        Produto p2 = new Produto(102, "Leite Integral", 12, 30, new BigDecimal("3.50"), 40);
        mockGerenciaProduto.inserirProduto(p1);
        mockGerenciaProduto.inserirProduto(p2);
        
        System.out.println("Produtos Inseridos:");
        mockGerenciaProduto.listarProdutos();

        Fornecedor f1 = new Fornecedor(1, "Farinhas & Cia", "Rua A, 123", "1111-1111", "11.111.111/0001-11", "Carlos");
        Fornecedor f2 = new Fornecedor(2, "Laticínios do Vale", "Av. B, 456", "2222-2222", "22.222.222/0001-22", "Ana");
        mockGerenciaFornecedor.inserirFornecedor(f1);
        mockGerenciaFornecedor.inserirFornecedor(f2);
        
        System.out.println("Fornecedores Inseridos:");
        mockGerenciaFornecedor.listarFornecedores();
        
        // Criando a instância da classe que queremos testar (com as correções)
        GerenciaCompra gerenciaCompra = new GerenciaCompra(mockGerenciaProduto, mockGerenciaFornecedor);
        
        Apagar apagarCSV = new Apagar(mockGerenciaFornecedor,gerenciaCompra);
        // --- 2. EXECUÇÃO DOS TESTES ---
        
        testarRegistrarEBuscarCompra(gerenciaCompra, mockGerenciaProduto);
        testarCalculosDeValores(gerenciaCompra, mockGerenciaProduto);
        testarListarCompras(gerenciaCompra);
        System.out.println(apagarCSV.geraLinha(1));

        System.out.println("\n--- TESTES FINALIZADOS ---");
    }

    public static void testarRegistrarEBuscarCompra(GerenciaCompra gc, GerenciaProduto gp) {
        System.out.println("\n--> Teste: Registrar e Buscar Compra");

        // Estoque inicial do produto 101
        int estoqueAntes = gp.buscarProduto(101).getEstoqueAtual();
        System.out.println("Estoque inicial do Produto 101: " + estoqueAntes);

        // Registrando uma compra
        gc.registrarCompra(1, 1, "08/08/2025", 101, 100);
        System.out.println("Registrando compra de 100 unidades do produto 101...");

        // Buscando a compra registrada
        Compra compraEncontrada = gc.buscarCompra(1);
        if (compraEncontrada != null && compraEncontrada.getIdProduto() == 101) {
            System.out.println("SUCESSO: Compra ID 1 encontrada.");
        } else {
            System.out.println("FALHA: Compra ID 1 não foi encontrada.");
        }

        // Verificando se o estoque foi atualizado CORRETAMENTE (aumentado)
        int estoqueDepois = gp.buscarProduto(101).getEstoqueAtual();
        System.out.println("Estoque final do Produto 101: " + estoqueDepois);
        if (estoqueDepois == estoqueAntes + 100) {
            System.out.println("SUCESSO: O estoque foi aumentado corretamente.");
        } else {
            System.out.println("FALHA: O estoque não foi atualizado corretamente. Valor esperado: " + (estoqueAntes + 100));
        }
    }

    public static void testarCalculosDeValores(GerenciaCompra gc, GerenciaProduto gp) {
        System.out.println("\n--> Teste: Cálculos de Valores");

        // Registrando mais compras para o teste de total
        gc.registrarCompra(2, 2, "08/08/2025", 102, 20); // Fornecedor 2, Produto 102, Qtd 20
        gc.registrarCompra(3, 1, "08/08/2025", 102, 10); // Fornecedor 1, Produto 102, Qtd 10

        // Teste 1: valorTotalDaCompra
        Compra compra2 = gc.buscarCompra(2);
        Produto produto102 = gp.buscarProduto(102);
        BigDecimal valorCompra2 = gc.valorTotalDaCompra(compra2, produto102);
        BigDecimal esperadoCompra2 = new BigDecimal("70.00"); // 3.50 * 20
        System.out.println("Calculando valor da Compra ID 2... Esperado: " + esperadoCompra2 + " | Calculado: " + valorCompra2);
        if (valorCompra2.compareTo(esperadoCompra2) == 0) {
            System.out.println("SUCESSO: O valor total da compra individual está correto.");
        } else {
            System.out.println("FALHA: O valor total da compra individual está incorreto.");
        }
        
        // Teste 2: totalAPagarPorFornecedor
        // Fornecedor 1 comprou: 100x prod 101 (R$0.20) + 10x prod 102 (R$3.50)
        // Total = (100 * 0.20) + (10 * 3.50) = 20.00 + 35.00 = 55.00
        BigDecimal totalF1 = gc.totalAPagarPorFornecedor(1);
        BigDecimal esperadoF1 = new BigDecimal("55.00");
        System.out.println("Calculando total a pagar para Fornecedor ID 1... Esperado: " + esperadoF1 + " | Calculado: " + totalF1);
        if (totalF1.compareTo(esperadoF1) == 0) {
            System.out.println("SUCESSO: O valor total a pagar ao fornecedor 1 está correto.");
        } else {
            System.out.println("FALHA: O valor total a pagar ao fornecedor 1 está incorreto.");
        }
    }

    public static void testarListarCompras(GerenciaCompra gc) {
        System.out.println("\n--> Teste: Listar Todas as Compras Registradas");
        gc.listarCompras();
    }
}
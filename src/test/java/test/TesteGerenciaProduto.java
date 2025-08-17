package test;

import service.GerenciaProduto;
import model.Produto;
import java.math.BigDecimal;
import java.util.Scanner;


/**
 * Classe de teste para GerenciaProduto.
 * Permite testar inserção, listagem, busca, edição e remoção de produtos.
 */
public class TesteGerenciaProduto {
    public static void main(String[] args) {
        GerenciaProduto gerencia = new GerenciaProduto();
        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 6) {
            System.out.println("""
                    ===== MENU DE TESTE GERENCIAMENTO DE PRODUTOS =====
                    1. Inserir Produto
                    2. Listar Produtos
                    3. Buscar Produto por ID
                    4. Editar Produto
                    5. Remover Produto
                    6. Sair
                    """);
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // consome a quebra de linha

            switch (opcao) {
                case 1 -> {
                    System.out.print("Descrição: ");
                    String descricao = sc.nextLine();
                    System.out.print("Estoque mínimo: ");
                    int minEstoque = sc.nextInt();
                    System.out.print("Estoque atual: ");
                    int estoqueAtual = sc.nextInt();
                    System.out.print("Custo: ");
                    BigDecimal custo = sc.nextBigDecimal();
                    System.out.print("Percentual de lucro: ");
                    int percentualLucro = sc.nextInt();
                    sc.nextLine(); // consome a quebra de linha

                    Produto p = new Produto(gerencia.gerarNovoId(), descricao, minEstoque, estoqueAtual, custo, percentualLucro);
                    gerencia.inserirProduto(p);
                    System.out.println("Produto inserido com sucesso!");
                }
                case 2 -> gerencia.listarProdutos();
                case 3 -> {
                    System.out.print("ID do produto: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    Produto p = gerencia.buscarProduto(id);
                    if (p != null) {
                        System.out.println("Produto encontrado: " + p);
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                }
                case 4 -> {
                    System.out.print("ID do produto para editar: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    gerencia.editarProduto(id);
                }
                case 5 -> {
                    System.out.print("ID do produto para remover: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    gerencia.removerProduto(id);
                }
                case 6 -> System.out.println("Encerrando o teste...");
                default -> System.out.println("Opção inválida!");
            }
        }

        sc.close();
    }
}

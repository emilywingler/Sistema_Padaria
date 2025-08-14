package report;

import java.util.*;
import service.GerenciaProduto;
import model.Produto;

/**
 * Classe responsável por gerar um relatório do estoque de produtos.
 * <p>
 * A classe percorre todos os produtos cadastrados, verificando a quantidade
 * disponível no estoque e adicionando uma observação caso a quantidade atual
 * seja inferior ao estoque mínimo configurado.
 * </p>
 * 
 * <p>
 * O relatório gerado é estruturado como uma lista de arrays de {@code String},
 * onde cada array contém:
 * <ul>
 *   <li>ID do produto</li>
 *   <li>Descrição</li>
 *   <li>Quantidade atual em estoque</li>
 *   <li>Observação (por exemplo, "COMPRAR MAIS" caso o estoque esteja abaixo do mínimo)</li>
 * </ul>
 * </p>
 */
public class Estoque {

    /** Gerencia de produtos, utilizada para buscar e consultar dados de estoque. */
    private GerenciaProduto gp;

    /**
     * Construtor da classe {@code Estoque}.
     *
     * @param gp instância de {@link GerenciaProduto} para consulta de produtos
     */
    public Estoque(GerenciaProduto gp) {
        this.gp = gp;
    }

    /**
     * Gera o relatório de estoque de todos os produtos cadastrados.
     * <p>
     * O método percorre um intervalo de IDs para buscar produtos (0 a 9999),
     * obtém a quantidade atual e, se o estoque estiver abaixo do mínimo, 
     * adiciona uma observação indicando a necessidade de reposição.
     * Ao final, ordena os dados pela descrição do produto.
     * </p>
     *
     * @return uma lista de arrays de {@code String} contendo os dados do estoque
     *         no seguinte formato: [idProduto, descricao, estoqueAtual, observacao]
     */
    public List<String[]> gerar() {
        List<String[]> dados = new ArrayList<>();

        // Lista de produtos
        List<Produto> produtos = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Produto p = gp.buscarProduto(i);
            if (p != null) produtos.add(p);
        }

        // Montar dados de cada produto
        for (Produto p : produtos) {
            String observacao = "";
            if (p.getEstoqueAtual() < p.getMinEstoque()) {
                observacao = "COMPRAR MAIS";
            }

            dados.add(new String[]{
                String.valueOf(p.getIdProduto()),
                p.getDescricao(),
                String.valueOf(p.getEstoqueAtual()),
                observacao
            });
        }

        // Ordenar por descrição
        dados.sort(Comparator.comparing(a -> a[1]));
        return dados;
    }
}

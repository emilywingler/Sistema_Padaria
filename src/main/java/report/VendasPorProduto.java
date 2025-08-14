package report;

import java.math.BigDecimal;
import java.util.*;
import service.GerenciaVenda;
import service.GerenciaProduto;
import model.Produto;

/**
 * Classe responsável por gerar um relatório de vendas e lucros por produto.
 * <p>
 * A classe percorre todos os produtos cadastrados, consultando para cada um
 * a receita total e o lucro obtido nas vendas. Valores nulos são tratados como zero.
 * </p>
 * 
 * <p>
 * O relatório gerado é estruturado como uma lista de arrays de {@code String},
 * onde cada array contém:
 * <ul>
 *   <li>ID do produto</li>
 *   <li>Descrição</li>
 *   <li>Receita total</li>
 *   <li>Lucro total</li>
 * </ul>
 * </p>
 * 
 * <p>
 * O resultado final é ordenado de forma decrescente pelo lucro e, em caso de empate,
 * pelo código do produto (crescente).
 * </p>
 * 
 * @author Clara
 * @version 1.0
 */
public class VendasPorProduto {

    /** Gerencia de vendas, utilizada para consultar receitas e lucros por produto. */
    private GerenciaVenda gv;

    /** Gerencia de produtos, utilizada para buscar dados dos produtos. */
    private GerenciaProduto gp;

    /**
     * Construtor da classe {@code VendasPorProduto}.
     *
     * @param gv instância de {@link GerenciaVenda} para cálculo de valores de receita e lucro
     * @param gp instância de {@link GerenciaProduto} para busca de produtos
     */
    public VendasPorProduto(GerenciaVenda gv, GerenciaProduto gp) {
        this.gv = gv;
        this.gp = gp;
    }

    /**
     * Gera o relatório de vendas e lucros de todos os produtos cadastrados.
     * <p>
     * O método percorre um intervalo de IDs para buscar produtos (0 a 9999),
     * obtém a receita e o lucro correspondentes a cada um e adiciona os dados
     * a uma lista. Valores nulos são substituídos por {@code BigDecimal.ZERO}.
     * </p>
     *
     * @return uma lista de arrays de {@code String} contendo os dados no seguinte formato:
     *         [idProduto, descricao, receita, lucro]
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
            BigDecimal receita = gv.receitaPorProduto(p.getIdProduto());
            BigDecimal lucro = gv.lucroPorProduto(p.getIdProduto());

            if (receita == null) receita = BigDecimal.ZERO;
            if (lucro == null) lucro = BigDecimal.ZERO;

            dados.add(new String[]{
                String.valueOf(p.getIdProduto()),
                p.getDescricao(),
                String.format("%.2f", receita),
                String.format("%.2f", lucro)
            });
        }

        // Ordenar por lucro decrescente, depois por código
        dados.sort((a, b) -> {
            int cmp = new BigDecimal(b[3]).compareTo(new BigDecimal(a[3]));
            if (cmp != 0) return cmp;
            return Integer.compare(Integer.parseInt(a[0]), Integer.parseInt(b[0]));
        });

        return dados;
    }
}

package report;

import io.Escrita;
import java.math.BigDecimal;
import java.util.*;
import java.util.Locale;
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

        // Estrutura temporária para manter BigDecimal até ordenar
        List<Object[]> temp = new ArrayList<>();

        for (Produto p : produtos) {
            BigDecimal receita = gv.receitaPorProduto(p.getIdProduto());
            BigDecimal lucro = gv.lucroPorProduto(p.getIdProduto());

            if (receita == null) receita = BigDecimal.ZERO;
            if (lucro == null) lucro = BigDecimal.ZERO;

            temp.add(new Object[]{
                p.getIdProduto(),
                p.getDescricao(),
                receita,
                lucro
            });
        }

        // Ordenar por lucro decrescente, depois por código do produto
        temp.sort((a, b) -> {
            int cmp = ((BigDecimal) b[3]).compareTo((BigDecimal) a[3]);
            if (cmp != 0) return cmp;
            return Integer.compare((int) a[0], (int) b[0]);
        });

        // Converter para String já formatada no padrão americano (ponto decimal)
        for (Object[] t : temp) {
            dados.add(new String[]{
                String.valueOf(t[0]),
                (String) t[1],
                String.format(Locale.US, "%.2f", (BigDecimal) t[2]),
                String.format(Locale.US, "%.2f", (BigDecimal) t[3])
            });
        }

        return dados;
    }
    /**
    * Gera um arquivo CSV contendo os dados de vendas agrupadas por produto.
    * <p>
    * Este método obtém os dados utilizando o método {@link #gerar()}, que retorna uma lista
    * de arrays de {@code String}, cada um represent
    * 
    * @param caminhoArquivo Caminho do arquivo
   */
    public void gerarCSV(String caminhoArquivo) {
        List<String[]> dados = gerar();
        Escrita escrita = new Escrita();
        escrita.escreverVendasPorProduto(caminhoArquivo, dados);
    }
}


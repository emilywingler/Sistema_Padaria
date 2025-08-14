package report;

import java.math.BigDecimal;
import java.util.*;
import service.GerenciaVenda;

/**
 * Classe responsável por gerar um relatório de vendas agrupadas por meio de pagamento.
 * <p>
 * Para cada tipo de meio de pagamento, o relatório apresenta:
 * <ul>
 *   <li>Código do meio de pagamento</li>
 *   <li>Receita total obtida</li>
 *   <li>Lucro total obtido</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Os meios de pagamento considerados são representados pelos seguintes códigos:
 * <pre>
 *   '$' - Dinheiro
 *   'X' - Pix
 *   'D' - Débito
 *   'C' - Crédito
 *   'T' - Transferência
 *   'F' - Fiado
 * </pre>
 * </p>
 * 
 * <p>
 * O relatório final é ordenado de forma decrescente pelo lucro e, em caso de empate, 
 * alfabeticamente pelo código do meio de pagamento.
 * </p>
 * 
 * @author Clara
 * @version 1.0
 */
public class VendasPorPagamento {

    /** Gerencia de vendas, utilizada para consultar receitas e lucros por meio de pagamento. */
    private GerenciaVenda gv;

    /**
     * Construtor da classe {@code VendasPorPagamento}.
     *
     * @param gv instância de {@link GerenciaVenda} para cálculo de valores de receita e lucro
     */
    public VendasPorPagamento(GerenciaVenda gv) {
        this.gv = gv;
    }

    /**
     * Gera o relatório de vendas e lucros agrupados por meio de pagamento.
     * <p>
     * Para cada meio de pagamento, obtém a receita e o lucro correspondentes,
     * substituindo valores nulos por {@code BigDecimal.ZERO}.
     * Ao final, os dados são ordenados por lucro (decrescente) e, em caso de empate,
     * pelo código do meio de pagamento (crescente).
     * </p>
     *
     * @return uma lista de arrays de {@code String} contendo os dados no seguinte formato:
     *         [codigoMeioPagamento, receita, lucro]
     */
    public List<String[]> gerar() {
        List<String[]> dados = new ArrayList<>();

        // Lista de meios de pagamento
        char[] meios = {'$', 'X', 'D', 'C', 'T', 'F'};
        for (char mp : meios) {
            BigDecimal receita = gv.receitaPorMP(mp);
            BigDecimal lucro = gv.lucroPorMP(mp);

            if (receita == null) receita = BigDecimal.ZERO;
            if (lucro == null) lucro = BigDecimal.ZERO;

            dados.add(new String[]{
                String.valueOf(mp),
                String.format("%.2f", receita),
                String.format("%.2f", lucro)
            });
        }

        // Ordenar por lucro decrescente, depois por letra
        dados.sort((a, b) -> {
            int cmp = new BigDecimal(b[2]).compareTo(new BigDecimal(a[2]));
            if (cmp != 0) return cmp;
            return a[0].compareTo(b[0]);
        });

        return dados;
    }
}
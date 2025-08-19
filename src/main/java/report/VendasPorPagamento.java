package report;

import io.Escrita;
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

    char[] meios = {'$', 'X', 'D', 'C', 'T', 'F'};
    for (char mp : meios) {
        BigDecimal receita = gv.receitaPorMP(mp);
        BigDecimal lucro = gv.lucroPorMP(mp);

        if (receita == null) receita = BigDecimal.ZERO;
        if (lucro == null) lucro = BigDecimal.ZERO;

        dados.add(new String[]{
            String.valueOf(mp),
            receita.toPlainString(), // mantém formato "123.45"
            lucro.toPlainString()
        });
    }

    // Ordenar por lucro decrescente, depois por letra
    dados.sort((a, b) -> {
        int cmp = new BigDecimal(b[2]).compareTo(new BigDecimal(a[2]));
        if (cmp != 0) return cmp;
        return a[0].compareTo(b[0]);
    });

    // Só aqui você formata para exibir no CSV
    for (String[] linha : dados) {
        linha[1] = String.format(Locale.US, "%.2f", new BigDecimal(linha[1]));
        linha[2] = String.format(Locale.US, "%.2f", new BigDecimal(linha[2]));
    }

    return dados;
}

    /**
    * Gera um arquivo CSV contendo os dados de vendas agrupadas por meio de pagamento.
    * <p>
    * Este método obtém os dados utilizando o método {@link #gerar()}, que retorna uma lista
    * de arrays de {@code String} representando cada linha do relatório. Em seguida, utiliza
    * a classe {@link io.Escrita} para escrever os dados no arquivo CSV especificado.
    * </p>
    *
    * @param caminhoArquivo o caminho completo (incluindo nome e extensão) do arquivo CSV a ser gerado
    */
    public void gerarCSV(String caminhoArquivo) {
        List<String[]> dados = gerar();
        Escrita escrita = new Escrita();
        escrita.escreverVendasPorPagamento(caminhoArquivo, dados);
    }
}
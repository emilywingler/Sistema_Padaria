package report;

import java.math.BigDecimal;
import java.util.*;
import service.GerenciaVenda;

public class VendasPorPagamento {

    private GerenciaVenda gv;

    public VendasPorPagamento(GerenciaVenda gv) {
        this.gv = gv;
    }

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

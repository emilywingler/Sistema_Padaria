package report;

import java.math.BigDecimal;
import java.util.*;
import service.GerenciaVenda;
import service.GerenciaProduto;
import model.Produto;

public class VendasPorProduto {

    private GerenciaVenda gv;
    private GerenciaProduto gp;

    public VendasPorProduto(GerenciaVenda gv, GerenciaProduto gp) {
        this.gv = gv;
        this.gp = gp;
    }

    public List<String[]> gerar() {
        List<String[]> dados = new ArrayList<>();

        List<Produto> produtos = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Produto p = gp.buscarProduto(i);
            if (p != null) produtos.add(p);
        }

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

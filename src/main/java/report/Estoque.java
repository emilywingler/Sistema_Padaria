package report;

import java.util.*;
import service.GerenciaProduto;
import model.Produto;

public class Estoque {

    private GerenciaProduto gp;

    public Estoque(GerenciaProduto gp) {
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

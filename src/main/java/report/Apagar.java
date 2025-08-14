package report;

import java.math.BigDecimal;
import java.util.*;
import service.GerenciaCompra;
import service.GerenciaFornecedor;
import model.Fornecedor;

public class Apagar {

    private GerenciaCompra gc;
    private GerenciaFornecedor gf;

    public Apagar(GerenciaCompra gc, GerenciaFornecedor gf) {
        this.gc = gc;
        this.gf = gf;
    }

    public List<String[]> gerar() {
        List<String[]> dados = new ArrayList<>();

        // Lista de fornecedores
        List<Fornecedor> fornecedores = new ArrayList<>();
        for (int i = 0; i < 10000; i++) { 
            Fornecedor f = gf.buscarFornecedor(i);
            if (f != null) fornecedores.add(f);
        }

        for (Fornecedor f : fornecedores) {
            BigDecimal total = gc.totalAPagarPorFornecedor(f.getIdFornecedor());
            if (total == null) total = BigDecimal.ZERO;

            dados.add(new String[]{
                f.getNomeEmpresa(),
                f.getCnpj(),
                f.getPessoaContato(),
                f.getTelefone(),
                String.format("%.2f", total)
            });
        }

        // Ordenar por nome do fornecedor
        dados.sort(Comparator.comparing(a -> a[0]));
        return dados;
    }
}


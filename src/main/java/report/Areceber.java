package report;

import java.math.BigDecimal;
import java.util.*;
import service.GerenciaVenda;
import service.GerenciaCliente;
import model.Cliente;
import model.ClienteFisico;
import model.ClienteJuridico;

public class Areceber {

    private GerenciaVenda gv;
    private GerenciaCliente gc;

    public Areceber(GerenciaVenda gv, GerenciaCliente gc) {
        this.gv = gv;
        this.gc = gc;
    }

    public List<String[]> gerar() {
        List<String[]> dados = new ArrayList<>();

        List<Cliente> clientes = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Cliente c = gc.buscarCliente(i);
            if (c != null) clientes.add(c);
        }

        for (Cliente c : clientes) {
        BigDecimal total = gv.totalAReceberCliente(c.getId());
        if (total == null) total = BigDecimal.ZERO;

        String documento = "";
            switch (c) {
                case ClienteFisico clienteFisico -> documento = clienteFisico.getCpf();
                case ClienteJuridico clienteJuridico -> documento = clienteJuridico.getCnpj();
                default -> {
                }
            }

        dados.add(new String[]{
            c.getNome(),
            c instanceof ClienteFisico ? "F" : "J",
            documento,
            c.getTelefone(),
            c.getDataCadastro(),
            String.format("%.2f", total)
        });
}

        // Ordenar por nome do cliente
        dados.sort(Comparator.comparing(a -> a[0]));
        return dados;
    }
}

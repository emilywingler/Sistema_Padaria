package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import model.Compra;
import model.Venda;
import model.VendaFiado;
import model.VendaAVista;
import model.Produto;
import service.GerenciaCompra;
import service.GerenciaVenda;
import service.GerenciaProduto;
import service.GerenciaCliente;

public class TelaControleContas {

    private final GerenciaCompra gerenciaCompra;
    private final GerenciaVenda gerenciaVenda;
    private final GerenciaProduto gerenciaProduto;
    private final GerenciaCliente gerenciaCliente;

    private final NumberFormat moedaBR = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    // UI refs para atualizar quando necessário
    private JTable tabelaPagar;
    private JTable tabelaReceber;
    private JLabel lblTotalPagar;
    private JLabel lblTotalReceber;

    private JPanel containerViews;
    private CardLayout viewsLayout;

    public TelaControleContas(GerenciaCompra gc,
                              GerenciaVenda gv,
                              GerenciaProduto gp,
                              GerenciaCliente gcli) {
        this.gerenciaCompra = gc;
        this.gerenciaVenda = gv;
        this.gerenciaProduto = gp;
        this.gerenciaCliente = gcli;
    }

    public JPanel criarPainelControleContas(JPanel painelPrincipal, CardLayout cardLayout) {
        JPanel raiz = new JPanel(new BorderLayout(12, 12));
        raiz.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Barra de ações (botões)
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JButton btnAPagar = new JButton("Contas a Pagar");
        JButton btnAReceber = new JButton("Contas a Receber");
        JButton btnVoltar = new JButton("Voltar");

        barra.add(btnAPagar);
        barra.add(btnAReceber);
        barra.add(Box.createHorizontalStrut(16));
        barra.add(btnVoltar);

        raiz.add(barra, BorderLayout.NORTH);

        // Container que troca entre as duas visões
        viewsLayout = new CardLayout();
        containerViews = new JPanel(viewsLayout);

        containerViews.add(criarViewPagar(), "pagar");
        containerViews.add(criarViewReceber(), "receber");

        raiz.add(containerViews, BorderLayout.CENTER);

        // Ações dos botões
        btnAPagar.addActionListener(e -> {
            viewsLayout.show(containerViews, "pagar");
            atualizarPagar();
        });

        btnAReceber.addActionListener(e -> {
            viewsLayout.show(containerViews, "receber");
            atualizarReceber();
        });

        btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, "menuPrincipal"));

        // Carrega dados iniciais
        atualizarPagar();
        atualizarReceber();

        return raiz;
    }

    // ========= View "A Pagar" =========
    private JPanel criarViewPagar() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));

        tabelaPagar = new JTable();
        tabelaPagar.setModel(modeloPagarVazio());
        tabelaPagar.setFillsViewportHeight(true);
        tabelaPagar.setAutoCreateRowSorter(true);

        JScrollPane scroll = new JScrollPane(tabelaPagar);
        painel.add(scroll, BorderLayout.CENTER);

        lblTotalPagar = new JLabel("Total a pagar: R$ 0,00");
        lblTotalPagar.setHorizontalAlignment(SwingConstants.RIGHT);
        painel.add(lblTotalPagar, BorderLayout.SOUTH);

        return painel;
    }

    private DefaultTableModel modeloPagarVazio() {
        return new DefaultTableModel(
            new Object[]{"NF", "Fornecedor", "Data", "Produto", "Quantidade", "Custo unitário", "Total item"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
    }

    private void atualizarPagar() {
        DefaultTableModel model = modeloPagarVazio();

        List<Compra> compras = gerenciaCompra.getCompras();
        BigDecimal total = BigDecimal.ZERO;

        if (compras != null) {
            for (Compra c : compras) {
                Produto p = gerenciaProduto.buscarProduto(c.getIdProduto());
                BigDecimal custo = (p != null && p.getCusto() != null) ? p.getCusto() : BigDecimal.ZERO;
                BigDecimal totalItem = custo.multiply(BigDecimal.valueOf(c.getQuantidade()));
                total = total.add(totalItem);

                model.addRow(new Object[]{
                    c.getIdCompra(),
                    c.getIdFornecedor(),
                    c.getDataCompra(),
                    c.getIdProduto(),
                    c.getQuantidade(),
                    moedaBR.format(custo),
                    moedaBR.format(totalItem)
                });
            }
        }

        tabelaPagar.setModel(model);
        tabelaPagar.getRowSorter().allRowsChanged();
        lblTotalPagar.setText("Total a pagar: " + moedaBR.format(total));
    }

    // ========= View "A Receber" =========
    private JPanel criarViewReceber() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));

        tabelaReceber = new JTable();
        tabelaReceber.setModel(modeloReceberVazio());
        tabelaReceber.setFillsViewportHeight(true);
        tabelaReceber.setAutoCreateRowSorter(true);

        JScrollPane scroll = new JScrollPane(tabelaReceber);
        painel.add(scroll, BorderLayout.CENTER);

        lblTotalReceber = new JLabel("Total a receber: R$ 0,00");
        lblTotalReceber.setHorizontalAlignment(SwingConstants.RIGHT);
        painel.add(lblTotalReceber, BorderLayout.SOUTH);

        return painel;
    }

    private DefaultTableModel modeloReceberVazio() {
        return new DefaultTableModel(
            new Object[]{"Data", "Cliente (id)", "Produto", "Quantidade", "Preço unitário", "Total item"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
    }

    private void atualizarReceber() {
        DefaultTableModel model = modeloReceberVazio();

        List<Venda> fiados = gerenciaVenda.filtrarVendasAReceber();
        BigDecimal total = BigDecimal.ZERO;

        if (fiados != null) {
            for (Venda v : fiados) {
                // Só fiado deve vir aqui, mas conferimos mesmo assim
                if (!(v instanceof VendaFiado)) continue;

                VendaFiado vf = (VendaFiado) v;

                Produto p = gerenciaProduto.buscarProduto(vf.getIdProduto());
                BigDecimal preco = (p != null && p.getValorDeVenda() != null) ? p.getValorDeVenda() : BigDecimal.ZERO;
                BigDecimal totalItem = preco.multiply(BigDecimal.valueOf(vf.getQuantidade()));
                total = total.add(totalItem);

                model.addRow(new Object[]{
                    vf.getDataVenda(),
                    vf.getIdCliente(),
                    vf.getIdProduto(),
                    vf.getQuantidade(),
                    moedaBR.format(preco),
                    moedaBR.format(totalItem)
                });
            }
        }

        tabelaReceber.setModel(model);
        tabelaReceber.getRowSorter().allRowsChanged();
        lblTotalReceber.setText("Total a receber: " + moedaBR.format(total));
    }

    /** Chame isto depois de carregar CSVs para atualizar as duas tabelas. */
    public void recarregarDados() {
        atualizarPagar();
        atualizarReceber();
    }
}

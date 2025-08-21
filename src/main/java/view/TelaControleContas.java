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

/**
 * Representa a tela (painel) de controle de contas do sistema.
 * <p>
 * Esta classe é responsável por criar e gerenciar a interface do usuário para
 * visualização de "Contas a Pagar" (originadas de compras) e "Contas a Receber"
 * (originadas de vendas a prazo/fiado).
 * </p>
 * <p>
 * Utiliza um {@link CardLayout} para alternar entre as duas visualizações principais
 * e depende das classes de serviço para obter os dados a serem exibidos.
 * </p>
 */
public class TelaControleContas {

    // --- Camadas de Serviço ---
    /** Serviço para gerenciar dados de compras. */
    private final GerenciaCompra gerenciaCompra;
    /** Serviço para gerenciar dados de vendas. */
    private final GerenciaVenda gerenciaVenda;
    /** Serviço para gerenciar dados de produtos. */
    private final GerenciaProduto gerenciaProduto;
    /** Serviço para gerenciar dados de clientes. */
    private final GerenciaCliente gerenciaCliente;

    /** Formatador de números para a moeda brasileira (Real). */
    private final NumberFormat moedaBR = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    // --- Referências de UI ---
    /** Tabela que exibe os detalhes das contas a pagar. */
    private JTable tabelaPagar;
    /** Tabela que exibe os detalhes das contas a receber. */
    private JTable tabelaReceber;
    /** Rótulo que exibe o valor total das contas a pagar. */
    private JLabel lblTotalPagar;
    /** Rótulo que exibe o valor total das contas a receber. */
    private JLabel lblTotalReceber;

    /** Painel que contém as visualizações (pagar/receber) e alterna entre elas. */
    private JPanel containerViews;
    /** Gerenciador de layout para o {@code containerViews}. */
    private CardLayout viewsLayout;

    /**
     * Constrói a tela de controle de contas com as dependências de serviço necessárias.
     *
     * @param gc   Instância de {@link GerenciaCompra} para obter dados de compras.
     * @param gv   Instância de {@link GerenciaVenda} para obter dados de vendas.
     * @param gp   Instância de {@link GerenciaProduto} para obter detalhes dos produtos.
     * @param gcli Instância de {@link GerenciaCliente} para obter detalhes dos clientes.
     */
    public TelaControleContas(GerenciaCompra gc,
                              GerenciaVenda gv,
                              GerenciaProduto gp,
                              GerenciaCliente gcli) {
        this.gerenciaCompra = gc;
        this.gerenciaVenda = gv;
        this.gerenciaProduto = gp;
        this.gerenciaCliente = gcli;
    }

    /**
     * Cria e retorna o painel principal para a interface de controle de contas.
     * <p>
     * Este método monta a estrutura completa do painel, incluindo os botões de navegação
     * ("Contas a Pagar", "Contas a Receber", "Voltar") e o contêiner com as tabelas.
     * </p>
     * @param painelPrincipal O painel raiz da aplicação, para onde o botão "Voltar" retorna.
     * @param cardLayout      O gerenciador de layout do painel raiz, usado para efetuar a troca de telas.
     * @return um {@link JPanel} totalmente configurado para ser adicionado à janela principal.
     */
    public JPanel criarPainelControleContas(JPanel painelPrincipal, CardLayout cardLayout) {
        JPanel raiz = new JPanel(new BorderLayout(12, 12));
        raiz.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Barra de ações superior com os botões
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JButton btnAPagar = new JButton("Contas a Pagar");
        JButton btnAReceber = new JButton("Contas a Receber");
        JButton btnVoltar = new JButton("Voltar");

        barra.add(btnAPagar);
        barra.add(btnAReceber);
        barra.add(Box.createHorizontalStrut(16));
        barra.add(btnVoltar);

        raiz.add(barra, BorderLayout.NORTH);

        // Contêiner que troca entre as visões de "pagar" e "receber"
        viewsLayout = new CardLayout();
        containerViews = new JPanel(viewsLayout);

        containerViews.add(criarViewPagar(), "pagar");
        containerViews.add(criarViewReceber(), "receber");

        raiz.add(containerViews, BorderLayout.CENTER);

        // Ações dos botões de navegação
        btnAPagar.addActionListener(e -> {
            viewsLayout.show(containerViews, "pagar");
            atualizarPagar();
        });

        btnAReceber.addActionListener(e -> {
            viewsLayout.show(containerViews, "receber");
            atualizarReceber();
        });

        btnVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, "menuPrincipal"));

        // Carrega os dados nas tabelas na primeira exibição
        atualizarPagar();
        atualizarReceber();

        return raiz;
    }

    /**
     * Cria o painel de visualização para "Contas a Pagar".
     *
     * @return um {@link JPanel} contendo a tabela e o rótulo de total.
     */
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

    /**
     * Cria um modelo de tabela vazio e não editável para as contas a pagar.
     *
     * @return um {@link DefaultTableModel} com as colunas pré-definidas.
     */
    private DefaultTableModel modeloPagarVazio() {
        return new DefaultTableModel(
            new Object[]{"NF", "Fornecedor", "Data", "Produto", "Quantidade", "Custo unitário", "Total item"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
    }

    /**
     * Busca os dados mais recentes de compras, calcula os totais e atualiza a tabela e o rótulo de "Contas a Pagar".
     */
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

    /**
     * Cria o painel de visualização para "Contas a Receber".
     *
     * @return um {@link JPanel} contendo a tabela e o rótulo de total.
     */
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

    /**
     * Cria um modelo de tabela vazio e não editável para as contas a receber.
     *
     * @return um {@link DefaultTableModel} com as colunas pré-definidas.
     */
    private DefaultTableModel modeloReceberVazio() {
        return new DefaultTableModel(
            new Object[]{"Data", "Cliente (id)", "Produto", "Quantidade", "Preço unitário", "Total item"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
    }

    /**
     * Busca os dados mais recentes de vendas a prazo, calcula os totais e atualiza a tabela e o rótulo de "Contas a Receber".
     */
    private void atualizarReceber() {
        DefaultTableModel model = modeloReceberVazio();
        List<Venda> fiados = gerenciaVenda.filtrarVendasAReceber();
        BigDecimal total = BigDecimal.ZERO;

        if (fiados != null) {
            for (Venda v : fiados) {
                // A lista já deve ser apenas de VendaFiado, mas a verificação é uma boa prática.
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

    /**
     * Força a atualização dos dados em ambas as tabelas (pagar e receber).
     * <p>
     * Este método deve ser chamado externamente quando os dados subjacentes
     * (como após o carregamento de novos arquivos CSV) foram alterados.
     * </p>
     */
    public void recarregarDados() {
        atualizarPagar();
        atualizarReceber();
    }
}
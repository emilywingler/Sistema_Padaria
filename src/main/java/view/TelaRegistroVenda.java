package view;

import model.*;
import service.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import view.forms.*;
import model.*;
import java.util.stream.Collectors;
import view.utils.FormUtils;

public class TelaRegistroVenda extends JPanel {

    // Serviços
    private GerenciaVenda gv;
    private GerenciaProduto gp;
    private GerenciaCliente gc;

    // Componentes da UI
    private JTable tabelaVendas;
    private DefaultTableModel tableModel;
    private JComboBox<Object> filtroProduto;
    private JComboBox<String> filtroMeioPagamento;
    private JLabel labelReceitaTotal;
    private JLabel labelLucroTotal;
    private JPanel painelPrincipal;
    private CardLayout cardLayout;

    
    public TelaRegistroVenda(GerenciaVenda gv, GerenciaProduto gp, GerenciaCliente gc, 
                                JPanel painelPrincipal, CardLayout cardLayout) {
        this.gv = gv;
        this.gp = gp;
        this.gc = gc;
        this.painelPrincipal = painelPrincipal; // Armazena a referência
        this.cardLayout = cardLayout;           // Armazena a referência

        setLayout(new BorderLayout(10, 10));
        inicializarComponentes();
    }
    // Este método será chamado para recarregar os dados quando esta tela for exibida
    public void carregarDados() {
        atualizarTabela();
    }
    
    private void inicializarComponentes() {
        // Cria e adiciona os painéis de filtros, tabela e sumário
        add(criarPainelFiltros(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);
        add(criarPainelSumario(), BorderLayout.SOUTH);
    }
    
    // No seu arquivo da tela de vendas (TelaRegistroVenda.java ou similar)

// No seu arquivo da tela de vendas (TelaRegistroVenda.java ou similar)

    private JPanel criarPainelFiltros() {
        // O painel principal do topo continua usando BorderLayout.
        JPanel painelPrincipalTopo = new JPanel(new BorderLayout(10, 5)); // Diminuí o gap vertical

        // --- A MUDANÇA PRINCIPAL ESTÁ AQUI ---
        // 1. O sub-painel de filtros agora usa GridLayout com 2 linhas e 2 colunas.
        // GridLayout(linhas, colunas, gap horizontal, gap vertical)
        JPanel painelFiltros = new JPanel(new GridLayout(2, 2, 5, 5));

        // Cria e popula o JComboBox de produtos
        filtroProduto = new JComboBox<>();
        filtroProduto.addItem("Todos os Produtos");
        List<Produto> produtos = gp.getProdutos();
        for (Produto p : produtos) {
            filtroProduto.addItem(p);
        }
        filtroProduto.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Produto) {
                    setText(((Produto) value).getDescricao());
                }
                return this;
            }
        });

        // Cria o JComboBox de meios de pagamento
        String[] meios = {"Todos os Pagamentos", "Dinheiro", "Cheque", "Cartão de Débito", "Cartão de Crédito", "Ticket Alimentação", "Fiado"};
        filtroMeioPagamento = new JComboBox<>(meios);

        painelFiltros.add(new JLabel("Filtrar por Pagamento:")); // Agora este ficará na linha de baixo
        painelFiltros.add(filtroMeioPagamento);

        // 3. O sub-painel de botões à direita continua igual
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnRegistrarVenda = new JButton("Registrar Nova Venda");
        JButton btnVoltar = new JButton("Voltar ao Menu Principal");
        painelBotoes.add(btnRegistrarVenda);
        painelBotoes.add(btnVoltar);

        filtroMeioPagamento.addActionListener(e -> atualizarTabela());

        btnRegistrarVenda.addActionListener(e -> {
            JFrame framePai = (JFrame) SwingUtilities.getWindowAncestor(this);
            exibirFormularioCadastroVenda(framePai); 
        });

        btnVoltar.addActionListener(e -> {
            cardLayout.show(painelPrincipal, "menuPrincipal");
        });

        // 5. Adiciona os sub-painéis ao painel principal do topo
        painelPrincipalTopo.add(painelFiltros, BorderLayout.CENTER);
        painelPrincipalTopo.add(painelBotoes, BorderLayout.EAST);

        return painelPrincipalTopo;
    }

    // Dentro do método criarPainelTabela()

    private JScrollPane criarPainelTabela() {
        String[] colunas = {"ID Venda", "Data", "Produto", "Qtd", "Receita", "Lucro", "Pagamento", "Cliente"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
            }
        };
        tabelaVendas = new JTable(tableModel);
        return new JScrollPane(tabelaVendas);
    }

    // Dentro da classe PainelRegistroVendas

    private JPanel criarPainelSumario() {
        // Usamos GridLayout para alinhar os totais
        JPanel painel = new JPanel(new GridLayout(1, 2, 20, 5));

        labelReceitaTotal = new JLabel("Receita Total: R$ 0,00");
        labelReceitaTotal.setFont(new Font("Arial", Font.BOLD, 14));

        labelLucroTotal = new JLabel("Lucro Total: R$ 0,00");
        labelLucroTotal.setFont(new Font("Arial", Font.BOLD, 14));
        labelLucroTotal.setForeground(new Color(0, 102, 0)); // Um verde escuro para o lucro

        painel.add(labelReceitaTotal);
        painel.add(labelLucroTotal);
        return painel;
    }

   private void exibirFormularioCadastroVenda(JFrame framePai) {
    while (true) {
        FormularioVendaPanel formulario = new FormularioVendaPanel(gp, gc, gv);

        int resultado = JOptionPane.showConfirmDialog(framePai, formulario, 
                                                      "Registrar Nova Venda", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            if (formulario.validarCampos(framePai)) {
                try {
                    // Pega os dados do formulário
                    String dataVenda = formulario.getDataVenda();
                    int idProduto = formulario.getIdProduto();
                    int quantidade = formulario.getQuantidade();
                    // Pega o nome do meio de pagamento (ex: "Dinheiro", "Fiado")
                    String meioPagamentoString = formulario.getMeioPagamento(); 

                    Produto produto = gp.buscarProduto(idProduto);
                    Venda venda;

                    char meioPagamentoCodigo = FormUtils.mapearPagamentoParaCodigo(meioPagamentoString);

                    // A verificação continua sendo pelo nome, o que é mais legível
                    if ("Fiado".equals(meioPagamentoString)) {
                        int idCliente = formulario.getIdCliente();
                        // A validação se o cliente existe já está no método validarCampos,
                        // então aqui podemos assumir que ele é válido.
                        
                        // Usa o código convertido no construtor
                        venda = new VendaFiado(idCliente, dataVenda, idProduto, quantidade, meioPagamentoCodigo);
                    } else {
                        // Usa o código convertido no construtor
                        venda = new VendaAVista(dataVenda, idProduto, quantidade, meioPagamentoCodigo);
                    }

                    // Insere no gerenciador (supondo que seu método se chame assim)
                    gv.registrarVenda(venda,produto);

                    JOptionPane.showMessageDialog(framePai, 
                            "Venda do produto '" + produto.getDescricao() + "' registrada com sucesso!");
                    atualizarTabela(); // Atualiza a tabela de vendas
                    break; // sai do loop

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(framePai, 
                            "Erro: ID e Quantidade devem ser números válidos.", 
                            "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            break; // usuário cancelou
        }
    }
}
// No seu arquivo view.PainelRegistroVendas

// Dentro da classe PainelRegistroVendas

    private void atualizarTabela() {
        tableModel.setRowCount(0);

        Object produtoSelecionado = filtroProduto.getSelectedItem();
        String pagamentoSelecionado = (String) filtroMeioPagamento.getSelectedItem();

        List<Venda> vendasParaExibir = gv.getVendasFiltradas(produtoSelecionado, pagamentoSelecionado);

        // 1. Criamos duas variáveis BigDecimal para os totais
        BigDecimal receitaTotal = BigDecimal.ZERO;
        BigDecimal lucroTotal = BigDecimal.ZERO;

            // Dentro do método atualizarTabela() em PainelRegistroVendas.java

    // ... (declaração dos BigDecimals para os totais) ...

    for (Venda venda : vendasParaExibir) {
        Produto p = gp.buscarProduto(venda.getIdProduto());

        // --- AQUI ESTÁ A CORREÇÃO COM INSTANCEOF ---
        Cliente c = null; // O cliente começa como nulo por padrão.

        // Verifica se o objeto 'venda' é uma instância da subclasse 'VendaFiado'.
        // Se for, cria uma variável 'vf' já convertida (cast) para VendaFiado.
        if (venda instanceof VendaFiado vf) {
            // Agora, dentro deste if, podemos usar 'vf' para acessar métodos exclusivos de VendaFiado.
            c = gc.buscarCliente(vf.getIdCliente());
        }

        // O resto do código continua igual, pois ele já funciona com 'c' sendo nulo ou não.
        BigDecimal valorReceita = (p != null) ? gv.receitaTotalDoPedido(venda, p) : BigDecimal.ZERO;
        BigDecimal valorLucro = (p != null) ? gv.lucroTotalDoPedido(venda, p) : BigDecimal.ZERO;

        Object[] rowData = {
            venda.getIdVenda(),
            venda.getDataVenda(),
            p != null ? p.getDescricao() : "PRODUTO APAGADO",
            venda.getQuantidade(),
            String.format("%.2f", valorReceita.doubleValue()),
            String.format("%.2f", valorLucro.doubleValue()),
            mapearCodigoParaPagamento(venda.getMeioPagamento()),
            c != null ? c.getNome() : "N/A" // Esta linha agora funciona perfeitamente
        };
        tableModel.addRow(rowData);

        receitaTotal = receitaTotal.add(valorReceita);
        lucroTotal = lucroTotal.add(valorLucro);
    }

        // ... (atualização dos JLabels de sumário) ...

        // 5. No final, atualizamos os dois JLabels do sumário
        labelReceitaTotal.setText(String.format("Receita Total: R$ %.2f", receitaTotal.doubleValue()));
        labelLucroTotal.setText(String.format("Lucro Total: R$ %.2f", lucroTotal.doubleValue()));
    }

    private String mapearCodigoParaPagamento(char codigo) {
            return switch (codigo) {
                case '$' -> "Dinheiro";
                case 'X' -> "Cheque";
                case 'D' -> "Cartão de Débito";
                case 'C' -> "Cartão de Crédito";
                case 'T' -> "Ticket Alimentação";
                case 'F' -> "Fiado";
                default -> "Desconhecido";
            };
    }
        
}

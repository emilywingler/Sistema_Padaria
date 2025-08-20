/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.forms;

import io.Escrita;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import report.*;
import service.*;

public class GerarRelatorioMensal extends JDialog {

    // --- Componentes da Interface ---
    private JRadioButton radApagar, radAreceber, radVendasProduto, radVendasPagamento, radEstoque;
    private ButtonGroup grupoRadios;
    private JButton btnVisualizar, btnGerarCSV, btnCancelar;
    private JTable tabelaVisualizacao; // <<< MUDANÇA
    private JScrollPane scrollPane;

    // --- Dados do Relatório Atual ---
    private List<String[]> dadosAtuais;
    private String nomeArquivoAtual;

    // --- Dependências de Serviço ---
    private GerenciaCompra gerenciaCompra;
    private GerenciaFornecedor gerenciaFornecedor;
    private GerenciaVenda gerenciaVenda;
    private GerenciaCliente gerenciaCliente;
    private GerenciaProduto gerenciaProduto;
    private Escrita escritorCSV = new Escrita();

    public GerarRelatorioMensal(Frame owner, GerenciaCompra gerenciaCompra, GerenciaFornecedor gerenciaFornecedor,
            GerenciaVenda gerenciaVenda, GerenciaCliente gerenciaCliente, GerenciaProduto gerenciaProduto) {
        super(owner, "Gerar Relatórios Mensais", true);
        this.gerenciaCompra = gerenciaCompra;
        this.gerenciaFornecedor = gerenciaFornecedor;
        this.gerenciaVenda = gerenciaVenda;
        this.gerenciaCliente = gerenciaCliente;
        this.gerenciaProduto = gerenciaProduto;
        initComponents();
    }

    private void initComponents() {
        // Painel de seleção (Norte)
        JPanel painelSelecao = new JPanel();
        painelSelecao.setLayout(new BoxLayout(painelSelecao, BoxLayout.Y_AXIS));
        painelSelecao.setBorder(BorderFactory.createTitledBorder("1. Selecione o Relatório"));

        radApagar = new JRadioButton("Total a pagar por fornecedor");
        radAreceber = new JRadioButton("Total a receber por cliente");
        radVendasProduto = new JRadioButton("Vendas e lucro por produto");
        radVendasPagamento = new JRadioButton("Vendas e lucro por forma de pagamento");
        radEstoque = new JRadioButton("Estado do estoque");
        radApagar.setSelected(true);

        grupoRadios = new ButtonGroup();
        grupoRadios.add(radApagar);
        grupoRadios.add(radAreceber);
        grupoRadios.add(radVendasProduto);
        grupoRadios.add(radVendasPagamento);
        grupoRadios.add(radEstoque);

        painelSelecao.add(radApagar);
        painelSelecao.add(radAreceber);
        painelSelecao.add(radVendasProduto);
        painelSelecao.add(radVendasPagamento);
        painelSelecao.add(radEstoque);

        // Painel de visualização com JTable (Centro)
        JPanel painelVisualizacao = new JPanel(new BorderLayout());
        painelVisualizacao.setBorder(BorderFactory.createTitledBorder("2. Pré-Visualização"));
        tabelaVisualizacao = new JTable();
        scrollPane = new JScrollPane(tabelaVisualizacao);
        painelVisualizacao.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões (Sul)
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnVisualizar = new JButton("Visualizar Relatório");
        btnGerarCSV = new JButton("Gerar CSV");
        btnCancelar = new JButton("Fechar");
        btnGerarCSV.setVisible(false);

        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnVisualizar);
        painelBotoes.add(btnGerarCSV);

        // Adiciona painéis à janela
        setLayout(new BorderLayout(10, 10));
        add(painelSelecao, BorderLayout.NORTH);
        add(painelVisualizacao, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Listeners
        btnVisualizar.addActionListener(e -> visualizarRelatorio());
        btnGerarCSV.addActionListener(e -> gerarArquivoCSV());
        btnCancelar.addActionListener(e -> dispose());

        setSize(900, 600);
        setLocationRelativeTo(getParent());
    }

    private void visualizarRelatorio() {
        dadosAtuais = null;
        nomeArquivoAtual = null;
        String[] cabecalho = new String[0];

        if (radApagar.isSelected()) {
            Apagar relatorio = new Apagar(gerenciaCompra, gerenciaFornecedor);
            dadosAtuais = relatorio.gerar();
            nomeArquivoAtual = "1-apagar.csv";
            cabecalho = new String[]{"Fornecedor", "CNPJ", "Contato", "Telefone", "Total a Pagar"};
        } else if (radAreceber.isSelected()) {
            Areceber relatorio = new Areceber(gerenciaVenda, gerenciaCliente);
            dadosAtuais = relatorio.gerar();
            nomeArquivoAtual = "2-areceber.csv";
            cabecalho = new String[]{"Cliente", "Tipo", "CPF/CNPJ", "Telefone", "Cadastro", "Total a Receber"};
        } else if (radVendasProduto.isSelected()) {
            VendasPorProduto relatorio = new VendasPorProduto(gerenciaVenda, gerenciaProduto);
            dadosAtuais = relatorio.gerar();
            nomeArquivoAtual = "3-vendasprod.csv";
            cabecalho = new String[]{"Cód. Produto", "Descrição", "Receita Bruta", "Lucro"};
        } else if (radVendasPagamento.isSelected()) {
            VendasPorPagamento relatorio = new VendasPorPagamento(gerenciaVenda);
            dadosAtuais = relatorio.gerar();
            nomeArquivoAtual = "4-vendaspgto.csv";
            cabecalho = new String[]{"Modo de Pagamento", "Receita Bruta", "Lucro"};
        } else if (radEstoque.isSelected()) {
            Estoque relatorio = new Estoque(gerenciaProduto);
            dadosAtuais = relatorio.gerar();
            nomeArquivoAtual = "5-estoque.csv";
            cabecalho = new String[]{"Cód. Produto", "Descrição", "Qtd. em Estoque", "Observações"};
        }

        // Converte List<String[]> para um formato que a JTable entende (String[][])
        String[][] dadosTabela = dadosAtuais.toArray(new String[0][]);

        // Cria um modelo de tabela não editável e o aplica à JTable
        DefaultTableModel model = new DefaultTableModel(dadosTabela, cabecalho) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede a edição das células
            }
        };
        tabelaVisualizacao.setModel(model);

        // Habilita ou desabilita o botão de gerar CSV
        btnGerarCSV.setVisible(dadosAtuais != null && !dadosAtuais.isEmpty());
    }

    private void gerarArquivoCSV() {
        if (dadosAtuais == null || nomeArquivoAtual == null) {
            JOptionPane.showMessageDialog(this, "Por favor, visualize um relatório primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório Como...");
        fileChooser.setSelectedFile(new File(nomeArquivoAtual));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                filePath += ".csv";
            }

            try {
                // A sua classe Escrita precisa ter métodos para cada tipo de relatório
                switch (nomeArquivoAtual) {
                    case "1-apagar.csv" -> escritorCSV.escreverApagar(filePath, dadosAtuais);
                    case "2-areceber.csv" -> escritorCSV.escreverAreceber(filePath, dadosAtuais);
                    case "3-vendasprod.csv" -> escritorCSV.escreverVendasPorProduto(filePath, dadosAtuais);
                    case "4-vendaspgto.csv" -> escritorCSV.escreverVendasPorPagamento(filePath, dadosAtuais);
                    case "5-estoque.csv" -> escritorCSV.escreverEstoque(filePath, dadosAtuais);
                }

                JOptionPane.showMessageDialog(this, "Relatório gerado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}

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

/**
 * Representa uma janela de diálogo modal para a geração de relatórios mensais.
 * <p>
 * Esta classe fornece uma interface para o usuário selecionar um tipo de relatório,
 * pré-visualizar os dados em uma tabela e, em seguida, exportar o resultado para um arquivo CSV.
 * </p>
 * <p>
 * Ela atua como um orquestrador, utilizando as classes de serviço para acessar os dados brutos,
 * as classes do pacote {@code report} para processar e gerar os dados do relatório,
 * e a classe {@link Escrita} para salvar o arquivo final.
 * </p>
 */
public class GerarRelatorioMensal extends JDialog {

    // --- Componentes da Interface ---
    private JRadioButton radApagar, radAreceber, radVendasProduto, radVendasPagamento, radEstoque;
    private ButtonGroup grupoRadios;
    private JButton btnVisualizar, btnGerarCSV, btnCancelar;
    /** Tabela para a pré-visualização dos dados do relatório gerado. */
    private JTable tabelaVisualizacao;
    private JScrollPane scrollPane;

    // --- Estado do Relatório Atual ---
    /** Armazena os dados do último relatório visualizado, prontos para serem exportados. */
    private List<String[]> dadosAtuais;
    /** Armazena o nome de arquivo padrão para o último relatório visualizado. */
    private String nomeArquivoAtual;

    // --- Dependências de Serviço e I/O ---
    private GerenciaCompra gerenciaCompra;
    private GerenciaFornecedor gerenciaFornecedor;
    private GerenciaVenda gerenciaVenda;
    private GerenciaCliente gerenciaCliente;
    private GerenciaProduto gerenciaProduto;
    /** Instância da classe utilitária para escrita de arquivos CSV. */
    private Escrita escritorCSV = new Escrita();

    /**
     * Constrói a janela de diálogo para geração de relatórios.
     *
     * @param owner O Frame proprietário a partir do qual o diálogo é exibido.
     * @param gerenciaCompra Serviço de compras, necessário para relatórios de contas a pagar.
     * @param gerenciaFornecedor Serviço de fornecedores, necessário para relatórios de contas a pagar.
     * @param gerenciaVenda Serviço de vendas, necessário para relatórios de vendas e contas a receber.
     * @param gerenciaCliente Serviço de clientes, necessário para relatórios de contas a receber.
     * @param gerenciaProduto Serviço de produtos, necessário para relatórios de vendas e estoque.
     */
    public GerarRelatorioMensal(Frame owner, GerenciaCompra gerenciaCompra, GerenciaFornecedor gerenciaFornecedor,
            GerenciaVenda gerenciaVenda, GerenciaCliente gerenciaCliente, GerenciaProduto gerenciaProduto) {
        super(owner, "Gerar Relatórios Mensais", true); // true para modal
        this.gerenciaCompra = gerenciaCompra;
        this.gerenciaFornecedor = gerenciaFornecedor;
        this.gerenciaVenda = gerenciaVenda;
        this.gerenciaCliente = gerenciaCliente;
        this.gerenciaProduto = gerenciaProduto;
        initComponents();
    }

    /**
     * Inicializa, configura e posiciona todos os componentes da interface gráfica na janela.
     */
    private void initComponents() {
        // Painel de seleção de relatório (Norte)
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

        // Painel de pré-visualização com JTable (Centro)
        JPanel painelVisualizacao = new JPanel(new BorderLayout());
        painelVisualizacao.setBorder(BorderFactory.createTitledBorder("2. Pré-Visualização"));
        tabelaVisualizacao = new JTable();
        scrollPane = new JScrollPane(tabelaVisualizacao);
        painelVisualizacao.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões de ação (Sul)
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnVisualizar = new JButton("Visualizar Relatório");
        btnGerarCSV = new JButton("Gerar CSV");
        btnCancelar = new JButton("Fechar");
        btnGerarCSV.setVisible(false); // Visível apenas após visualização

        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnVisualizar);
        painelBotoes.add(btnGerarCSV);

        // Adiciona os painéis principais à janela de diálogo
        setLayout(new BorderLayout(10, 10));
        add(painelSelecao, BorderLayout.NORTH);
        add(painelVisualizacao, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Define as ações para os botões
        btnVisualizar.addActionListener(e -> visualizarRelatorio());
        btnGerarCSV.addActionListener(e -> gerarArquivoCSV());
        btnCancelar.addActionListener(e -> dispose());

        setSize(900, 600);
        setLocationRelativeTo(getParent());
    }

    /**
     * Gera os dados do relatório selecionado e os exibe na tabela de pré-visualização.
     * <p>
     * Com base na seleção do {@code JRadioButton}, este método instancia a classe de relatório
     * apropriada, invoca seu método {@code gerar()}, e popula a {@code JTable} com os
     * resultados. Também habilita o botão "Gerar CSV".
     * </p>
     */
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

        String[][] dadosTabela = dadosAtuais.toArray(new String[0][]);

        // Cria um modelo de tabela não editável e o aplica à JTable
        DefaultTableModel model = new DefaultTableModel(dadosTabela, cabecalho) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede a edição das células
            }
        };
        tabelaVisualizacao.setModel(model);

        btnGerarCSV.setVisible(dadosAtuais != null && !dadosAtuais.isEmpty());
    }

    /**
     * Salva os dados do relatório atualmente visualizado em um arquivo CSV.
     * <p>
     * Abre um {@link JFileChooser} para que o usuário escolha o local e o nome do arquivo.
     * Em seguida, utiliza a classe {@link Escrita} para salvar os dados armazenados em
     * {@code dadosAtuais} no disco.
     * </p>
     */
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
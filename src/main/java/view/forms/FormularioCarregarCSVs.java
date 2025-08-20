package view.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import service.*;
import view.CarregamentoCSVListener;

public class FormularioCarregarCSVs extends JFrame {

    private GerenciaCliente gerenciaCliente;
    private GerenciaProduto gerenciaProduto;
    private GerenciaFornecedor gerenciaFornecedor;
    private GerenciaCompra gerenciaCompra;
    private GerenciaVenda gerenciaVenda;
    private CarregamentoCSVListener listener;

    private JTextField txtClientes;
    private JTextField txtFornecedores;
    private JTextField txtProdutos;
    private JTextField txtCompras;
    private JTextField txtVendas;
    private JButton btnCarregar;

    public FormularioCarregarCSVs(
            GerenciaCliente gerCli,
            GerenciaFornecedor gerFor,
            GerenciaProduto gerProd,
            GerenciaCompra gerComp,
            GerenciaVenda gerVend,
            CarregamentoCSVListener listener) {

        this.gerenciaCliente = gerCli;
        this.gerenciaFornecedor = gerFor;
        this.gerenciaProduto = gerProd;
        this.gerenciaCompra = gerComp;
        this.gerenciaVenda = gerVend;
        this.listener = listener;

        initComponents();
    }

    private void initComponents() {
        setTitle("Carregar arquivos CSV");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha só esta janela
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 3, 5, 5));

        // Campos e botões para escolher arquivos
        txtClientes = criarCampoArquivo("Clientes:");
        txtFornecedores = criarCampoArquivo("Fornecedores:");
        txtProdutos = criarCampoArquivo("Produtos:");
        txtCompras = criarCampoArquivo("Compras:");
        txtVendas = criarCampoArquivo("Vendas:");

        // Botão Carregar
        btnCarregar = new JButton("Carregar CSVs");
        btnCarregar.addActionListener(e -> carregarArquivos());
        add(new JLabel()); // espaço vazio
        add(btnCarregar);
        add(new JLabel()); // espaço vazio
    }

    private JTextField criarCampoArquivo(String label) {
        JLabel lbl = new JLabel(label);
        JTextField txt = new JTextField();
        JButton btn = new JButton("Escolher");
        btn.addActionListener(e -> escolherArquivo(txt));
        add(lbl);
        add(txt);
        add(btn);
        return txt;
    }

    private void escolherArquivo(JTextField txtCampo) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos CSV", "csv"));
        int retorno = fc.showOpenDialog(this);
        if (retorno == JFileChooser.APPROVE_OPTION) {
            File arquivo = fc.getSelectedFile();
            txtCampo.setText(arquivo.getAbsolutePath());
        }
    }

    // <<< MÉTODO ATUALIZADO >>>
    private void carregarArquivos() {
        // <<< ADICIONADO: Bloco de Validação para evitar o erro >>>
        String caminhoClientes = txtClientes.getText();
        String caminhoFornecedores = txtFornecedores.getText();
        String caminhoProdutos = txtProdutos.getText();
        String caminhoCompras = txtCompras.getText();
        String caminhoVendas = txtVendas.getText();

        if (caminhoClientes == null || caminhoClientes.isBlank() ||
            caminhoFornecedores == null || caminhoFornecedores.isBlank() ||
            caminhoProdutos == null || caminhoProdutos.isBlank() ||
            caminhoCompras == null || caminhoCompras.isBlank() ||
            caminhoVendas == null || caminhoVendas.isBlank()) {

            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha todos os campos com os caminhos dos arquivos CSV.",
                    "Campos Vazios",
                    JOptionPane.WARNING_MESSAGE);
            return; // Impede a execução do resto do método
        }
        // <<< FIM DA VALIDAÇÃO >>>

        try {
            // A lógica de carregamento agora usa as variáveis locais
            gerenciaCliente.carregarClientesCSV(caminhoClientes);
            gerenciaFornecedor.carregarFornecedorCSV(caminhoFornecedores);
            gerenciaProduto.carregarProdutosCSV(caminhoProdutos);
            gerenciaCompra.carregarComprasCSV(caminhoCompras);
            gerenciaVenda.carregarVendasCSV(caminhoVendas);

            if (listener != null) {
                listener.onCSVsCarregadosComSucesso();
            }

            dispose();

        } catch (Exception ex) {
            // <<< ADICIONADO: Linha essencial para diagnóstico >>>
            // Esta linha imprimirá o erro completo e detalhado no console da sua IDE
            ex.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar arquivos: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

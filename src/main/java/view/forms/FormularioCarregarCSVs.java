package view.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import service.*;

public class FormularioCarregarCSVs extends JFrame {

    private GerenciaCliente gerenciaCliente;
    private GerenciaProduto gerenciaProduto;
    private GerenciaFornecedor gerenciaFornecedor;
    private GerenciaCompra gerenciaCompra;
    private GerenciaVenda gerenciaVenda;

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
            GerenciaVenda gerVend) {

        this.gerenciaCliente = gerCli;
        this.gerenciaFornecedor = gerFor;
        this.gerenciaProduto = gerProd;
        this.gerenciaCompra = gerComp;
        this.gerenciaVenda = gerVend;

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

    private void carregarArquivos() {
        try {
            gerenciaCliente.carregarClientesCSV(txtClientes.getText());
            gerenciaFornecedor.carregarFornecedorCSV(txtFornecedores.getText());
            gerenciaProduto.carregarProdutosCSV(txtProdutos.getText());
            gerenciaCompra.carregarComprasCSV(txtCompras.getText());
            gerenciaVenda.carregarVendasCSV(txtVendas.getText());

            JOptionPane.showMessageDialog(this, "Arquivos carregados com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar arquivos: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

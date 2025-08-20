package view;

import javax.swing.*;
import java.awt.*;
import model.*;
import io.*;
import java.util.List;
import report.*;
import service.*;
import view.forms.FormularioCarregarCSVs;

public class TelaPrincipal {
    
    private static GerenciaCliente gerenciaCliente= new GerenciaCliente();
    private static GerenciaProduto gerenciaProduto = new GerenciaProduto();
    private static GerenciaFornecedor gerenciaFornecedor = new GerenciaFornecedor();
    private static GerenciaCompra gerenciaCompra = new GerenciaCompra(gerenciaProduto,gerenciaFornecedor);
    private static GerenciaVenda gerenciaVenda = new GerenciaVenda(gerenciaProduto,gerenciaCliente);
    
    public static void main(String[] args) {
        gerenciaCliente.carregarClientesCSV("bancoclientes.csv");
        gerenciaFornecedor.carregarFornecedorCSV("bancofornecedores.csv");
        gerenciaProduto.carregarProdutosCSV("bancoprodutos.csv");
        gerenciaCompra.carregarComprasCSV("bancocompras.csv");
        gerenciaVenda.carregarVendasCSV("bancovendas.csv");
        
        // Garante que o código da interface rode na thread correta
        SwingUtilities.invokeLater(() -> {
            criarEExibirGUI();
        });
    }

    private static void criarEExibirGUI() {
        // --- PARTE 1: CRIAR A BASE DA INTERFACE ---
        // Primeiro, criamos a janela, o painel principal e o gerenciador de layout.
        JFrame frame = new JFrame("Sistema de Gestão de Padaria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        CardLayout cardLayout = new CardLayout();
        JPanel painelPrincipal = new JPanel(cardLayout);

        // --- PARTE 2: CRIAR AS CLASSES CONTROLADORAS E OS PAINÉIS ---
        // Agora que a "base" existe, podemos criar nossas telas e passar a base para elas.
        TelaCadastro cadastro = new TelaCadastro(gerenciaProduto, gerenciaFornecedor, gerenciaCliente);
        TelaControleContas telaContas = new TelaControleContas(gerenciaCompra, gerenciaVenda, gerenciaProduto, gerenciaCliente);

        // Usamos o nome correto da sua classe e passamos a base da interface
        TelaRegistroVenda painelRegistroVendas = new TelaRegistroVenda(gerenciaVenda, gerenciaProduto, gerenciaCliente, painelPrincipal, cardLayout);

        // Criamos os painéis que serão exibidos
        JPanel painelMenuCadastro = cadastro.criarPainelMenuCadastro(painelPrincipal, cardLayout);
        JPanel painelContas = telaContas.criarPainelControleContas(painelPrincipal, cardLayout);
        JPanel painelMenuPrincipal = criarPainelMenuPrincipal(painelPrincipal, cardLayout, painelRegistroVendas);

        // --- PARTE 3: ADICIONAR OS PAINÉIS AO LAYOUT ---
        painelPrincipal.add(painelMenuPrincipal, "menuPrincipal");
        painelPrincipal.add(painelMenuCadastro, "menuCadastro");
        painelPrincipal.add(painelContas, "menuContas");
        painelPrincipal.add(painelRegistroVendas, "telaVendas");

        // --- PARTE 4: TORNAR TUDO VISÍVEL ---
        frame.add(painelPrincipal);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
}
    
    // Adicione este método dentro da classe TelaPrincipal.java

    private static JPanel criarPainelMenuPrincipal(JPanel painelPrincipal, CardLayout cardLayout, TelaRegistroVenda painelVendas) {
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 linhas, 1 coluna, com espaçamento

        // Criar os botões
        JButton btnCadastro = new JButton("Cadastro (Clientes, Fornecedores, Produtos)");
        JButton btnVendas = new JButton("Registro de Vendas");
        JButton btnContas = new JButton("Controle de Contas");
        JButton btnRelatorios = new JButton("Geração de Relatórios Mensais");
        JButton btnCarregarCSV = new JButton("Carregar arquivos CSV");
        JButton btnSair = new JButton("Sair");
        
        

        // Adicionar "ação" ao botão de Cadastro
        btnCadastro.addActionListener(e -> {
            // Pede ao "maquinista" (cardLayout) para mostrar o painel "menuCadastro"
            cardLayout.show(painelPrincipal, "menuCadastro");
        });

        // Adicionar "ação" ao botão de Sair
        btnSair.addActionListener(e -> System.exit(0));
        
        
        btnCarregarCSV.addActionListener(e -> {
            FormularioCarregarCSVs formCSV = new FormularioCarregarCSVs(
                gerenciaCliente,
                gerenciaFornecedor,
                gerenciaProduto,
                gerenciaCompra,
                gerenciaVenda
            );
            formCSV.setVisible(true);
        });


        btnVendas.addActionListener(e -> {
            painelVendas.carregarDados();
            cardLayout.show(painelPrincipal, "telaVendas");
        });

// ... (adiciona o botão ao painel) ...
        
        btnContas.addActionListener(e -> {
            cardLayout.show(painelPrincipal, "menuContas");
        });
        
        // (Adicionar ações para os outros botões depois)

        // Adicionar os botões ao painel
        painel.add(btnCadastro);
        painel.add(btnVendas);
        painel.add(btnContas);
        painel.add(btnRelatorios);
        painel.add(btnCarregarCSV);
        painel.add(btnSair);

        return painel;
        
        // Adicione este método também dentro da classe TelaPrincipal.java
    }
    
    
    
}

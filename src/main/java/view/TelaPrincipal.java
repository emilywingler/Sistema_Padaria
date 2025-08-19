package view;

import javax.swing.*;
import java.awt.*;
import model.*;
import io.*;
import java.util.List;
import report.*;
import service.*;

public class TelaPrincipal {
    
    private static GerenciaCliente gerenciaCliente= new GerenciaCliente();
    private static GerenciaProduto gerenciaProduto = new GerenciaProduto();
    private static GerenciaFornecedor gerenciaFornecedor = new GerenciaFornecedor();
    private static GerenciaCompra gerenciaCompra = new GerenciaCompra(gerenciaProduto,gerenciaFornecedor);
    private static GerenciaVenda gerenciaVenda = new GerenciaVenda(gerenciaProduto,gerenciaCliente);
    
    public static void main(String[] args) {
        
        
        // Garante que o código da interface rode na thread correta
        SwingUtilities.invokeLater(() -> {
            criarEExibirGUI();
        });
    }

    private static void criarEExibirGUI() {
        TelaCadastro cadastro = new TelaCadastro(gerenciaProduto,gerenciaFornecedor,gerenciaCliente);
        // 1. Criar a janela principal (JFrame)
        JFrame frame = new JFrame("Sistema de Gestão de Padaria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao clicar no X
        frame.setSize(800, 600); // Define um tamanho

        // 2. Configurar o painel principal com CardLayout (nosso "maquinista")
        CardLayout cardLayout = new CardLayout();
        JPanel painelPrincipal = new JPanel(cardLayout);

        // 3. Criar os painéis para cada "tela" (os "cenários")
        JPanel painelMenuPrincipal = criarPainelMenuPrincipal(painelPrincipal, cardLayout);
        JPanel painelMenuCadastro = cadastro.criarPainelMenuCadastro(painelPrincipal, cardLayout);
        // ... aqui você criaria os outros painéis (Vendas, Contas, Relatórios)

        // 4. Adicionar os painéis ao nosso "baralho" de cartões (cenários)
        painelPrincipal.add(painelMenuPrincipal, "menuPrincipal");
        painelPrincipal.add(painelMenuCadastro, "menuCadastro");
        // ... adicionar outros painéis

        // 5. Adicionar o painel principal à janela e torná-la visível
        frame.add(painelPrincipal);
        frame.setLocationRelativeTo(null); // Centraliza a janela
        frame.setVisible(true);
    }
    
    // Adicione este método dentro da classe TelaPrincipal.java

    private static JPanel criarPainelMenuPrincipal(JPanel painelPrincipal, CardLayout cardLayout) {
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

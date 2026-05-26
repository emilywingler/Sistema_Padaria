package view;

// ADICIONADO: Import para a nova tela de registro de vendas
import view.TelaRegistroVenda; 

import javax.swing.*;
import java.awt.*;
import model.*;
import service.*;
import view.forms.FormularioCarregarCSVs;
import view.forms.GerarRelatorioMensal;

public class TelaPrincipal {

    // --- Serviços de Gerência ---
    private static GerenciaCliente gerenciaCliente = new GerenciaCliente(null);
    private static GerenciaProduto gerenciaProduto = new GerenciaProduto(null);
    private static GerenciaFornecedor gerenciaFornecedor = new GerenciaFornecedor(null);
    private static GerenciaCompra gerenciaCompra = new GerenciaCompra(gerenciaProduto, gerenciaFornecedor, null);
    private static GerenciaVenda gerenciaVenda = new GerenciaVenda(gerenciaProduto, gerenciaCliente, null);
    

    // --- Componentes da UI ---
    private static CardLayout cardLayout;
    private static JPanel painelPrincipal;
    private static JFrame frame;

    // --- Botões de Navegação ---
    private static JButton btnCadastro;
    private static JButton btnVendas;
    private static JButton btnContas;
    private static JButton btnRelatorios;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            criarEExibirGUI(false);

            abrirFormularioCSVs(() -> {
                habilitarBotoesPrincipais(true);
                frame.setVisible(true);
                JOptionPane.showMessageDialog(frame, "Arquivos carregados com sucesso! O sistema está pronto para uso.");
            });
        });
    }

    /**
     * Constrói e configura a interface gráfica principal da aplicação.
     */
    private static void criarEExibirGUI(boolean visível) {

        frame = new JFrame("Sistema de Gestão de Padaria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);


        TelaCadastro cadastro = new TelaCadastro(gerenciaProduto, gerenciaFornecedor, gerenciaCliente);
        TelaControleContas telaContas = new TelaControleContas(gerenciaCompra, gerenciaVenda, gerenciaProduto, gerenciaCliente);
        TelaRegistroVenda painelRegistroVendas = new TelaRegistroVenda(gerenciaVenda, gerenciaProduto, gerenciaCliente, painelPrincipal, cardLayout);

        // Terceiro, criamos os painéis que serão efetivamente adicionados ao layout.
        JPanel painelMenuCadastro = cadastro.criarPainelMenuCadastro(painelPrincipal, cardLayout);
        JPanel painelContas = telaContas.criarPainelControleContas(painelPrincipal, cardLayout);
        JPanel painelMenuPrincipal = criarPainelMenuPrincipal(painelRegistroVendas); // A chamada também foi simplificada.

        // Adiciona todos os painéis ao gerenciador CardLayout
        painelPrincipal.add(painelMenuPrincipal, "menuPrincipal");
        painelPrincipal.add(painelMenuCadastro, "menuCadastro");
        painelPrincipal.add(painelContas, "menuContas");
        painelPrincipal.add(painelRegistroVendas, "telaVendas");
        
        frame.add(painelPrincipal);
        frame.setLocationRelativeTo(null);
        frame.setVisible(visível);
    }
    
    private static JPanel criarPainelMenuPrincipal(TelaRegistroVenda painelVendas) {
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(6, 1, 10, 10));

        btnCadastro = new JButton("Cadastro (Clientes, Fornecedores, Produtos)");
        btnVendas = new JButton("Registro de Vendas");
        btnContas = new JButton("Controle de Contas");
        btnRelatorios = new JButton("Geração de Relatórios Mensais");
        JButton btnCarregarCSV = new JButton("Carregar/Recarregar arquivos CSV");
        JButton btnSair = new JButton("Sair");

        habilitarBotoesPrincipais(false);

        // Action Listeners para navegação
        btnCadastro.addActionListener(e -> cardLayout.show(painelPrincipal, "menuCadastro"));
        btnContas.addActionListener(e -> cardLayout.show(painelPrincipal, "menuContas"));
        btnSair.addActionListener(e -> System.exit(0));

        btnCarregarCSV.addActionListener(e -> {
            abrirFormularioCSVs(() -> {
                habilitarBotoesPrincipais(true);
                JOptionPane.showMessageDialog(frame, "Novos arquivos carregados com sucesso!");
            });
        });

        btnVendas.addActionListener(e -> {
            painelVendas.carregarDados();
            cardLayout.show(painelPrincipal, "telaVendas");
        });
        
        btnRelatorios.addActionListener(e -> {
            GerarRelatorioMensal formRelatorios = new GerarRelatorioMensal(
                    frame, gerenciaCompra, gerenciaFornecedor, gerenciaVenda, gerenciaCliente, gerenciaProduto
            );
            formRelatorios.setVisible(true);
        });

        painel.add(btnCadastro);
        painel.add(btnVendas);
        painel.add(btnContas);
        painel.add(btnRelatorios);
        painel.add(btnCarregarCSV);
        painel.add(btnSair);

        return painel;
    }

    /**
     * Abre o formulário para carregar os arquivos CSV.
     */
    private static void abrirFormularioCSVs(CarregamentoCSVListener listener) {
        FormularioCarregarCSVs formCSV = new FormularioCarregarCSVs(
                gerenciaCliente, gerenciaFornecedor, gerenciaProduto, gerenciaCompra, gerenciaVenda,
                listener // Passa o listener para o formulário
        );
        formCSV.setVisible(true);
    }

    /**
     * Habilita ou desabilita os botões de navegação principais do sistema.
     */
    private static void habilitarBotoesPrincipais(boolean habilitar) {
        btnCadastro.setEnabled(habilitar);
        btnVendas.setEnabled(habilitar);
        btnContas.setEnabled(habilitar);
        btnRelatorios.setEnabled(habilitar);
    }
}
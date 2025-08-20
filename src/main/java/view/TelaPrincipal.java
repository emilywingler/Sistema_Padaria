package view;

import javax.swing.*;
import java.awt.*;
import model.*;
import service.*;
import view.forms.FormularioCarregarCSVs;
import view.forms.GerarRelatorioMensal;

/**
 * Classe principal que serve como ponto de entrada e controlador central da interface gráfica (GUI)
 * do Sistema de Gestão de Padaria.
 * <p>
 * Esta classe é responsável por:
 * <ul>
 * <li>Inicializar os serviços de gerência ({@link GerenciaCliente}, {@link GerenciaProduto}, etc.).</li>
 * <li>Construir a janela principal ({@link JFrame}) e o painel com {@link CardLayout} para navegação.</li>
 * <li>Orquestrar o fluxo de inicialização, que exige o carregamento de arquivos CSV antes de habilitar
 * as funcionalidades principais do sistema.</li>
 * <li>Configurar os botões de navegação e suas respectivas ações.</li>
 * </ul>
 * A aplicação só se torna totalmente funcional após o usuário carregar os dados iniciais através do formulário de CSVs.
 */
public class TelaPrincipal {

    // --- Serviços de Gerência ---
    /** Instância estática para gerenciar as operações de clientes. */
    private static GerenciaCliente gerenciaCliente = new GerenciaCliente(null);
    /** Instância estática para gerenciar as operações de produtos. */
    private static GerenciaProduto gerenciaProduto = new GerenciaProduto(null);
    /** Instância estática para gerenciar as operações de fornecedores. */
    private static GerenciaFornecedor gerenciaFornecedor = new GerenciaFornecedor(null);
    /** Instância estática para gerenciar as operações de compras. */
    private static GerenciaCompra gerenciaCompra = new GerenciaCompra(gerenciaProduto, gerenciaFornecedor, null);
    /** Instância estática para gerenciar as operações de vendas. */
    private static GerenciaVenda gerenciaVenda = new GerenciaVenda(gerenciaProduto, gerenciaCliente, null);

    // --- Componentes da UI ---
    /** Gerenciador de layout que permite alternar entre diferentes painéis (telas). */
    private static CardLayout cardLayout;
    /** Painel principal que contém todos os outros painéis gerenciados pelo CardLayout. */
    private static JPanel painelPrincipal;
    /** A janela principal da aplicação. Referência estática para ser acessível globalmente na classe. */
    private static JFrame frame;

    // --- Botões de Navegação ---
    /** Botão para acessar a tela de cadastros. */
    private static JButton btnCadastro;
    /** Botão para acessar a tela de registro de vendas. */
    private static JButton btnVendas;
    /** Botão para acessar a tela de controle de contas. */
    private static JButton btnContas;
    /** Botão para acessar a tela de geração de relatórios. */
    private static JButton btnRelatorios;

    /**
     * O ponto de entrada principal da aplicação.
     * <p>
     * Inicia a GUI na Event Dispatch Thread (EDT) do Swing. O fluxo de inicialização
     * primeiro cria a janela principal de forma invisível, depois exibe um formulário
     * para carregar arquivos CSV. Somente após o carregamento bem-sucedido, a janela
     * principal é exibida e suas funcionalidades são habilitadas.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Cria a GUI principal, mas a mantém invisível inicialmente.
            criarEExibirGUI(false);

            // Abre o formulário para carregar os CSVs, passando uma ação de callback (listener).
            abrirFormularioCSVs(() -> {
                // Ação executada após o carregamento dos CSVs:
                // 1. Habilita os botões principais da aplicação.
                habilitarBotoesPrincipais(true);
                // 2. Torna a janela principal visível.
                frame.setVisible(true);
                JOptionPane.showMessageDialog(frame, "Arquivos carregados com sucesso! O sistema está pronto para uso.");
            });
        });
    }

    /**
     * Constrói e configura a interface gráfica principal da aplicação.
     *
     * @param visivel Controla se a janela principal deve ser tornada visível imediatamente após a criação.
     */
    private static void criarEExibirGUI(boolean visivel) {
        TelaCadastro cadastro = new TelaCadastro(gerenciaProduto, gerenciaFornecedor, gerenciaCliente);
        TelaControleContas telaContas = new TelaControleContas(gerenciaCompra, gerenciaVenda, gerenciaProduto, gerenciaCliente);

        frame = new JFrame("Sistema de Gestão de Padaria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        // Cria os diferentes painéis (telas) da aplicação
        JPanel painelMenuPrincipal = criarPainelMenuPrincipal();
        JPanel painelMenuCadastro = cadastro.criarPainelMenuCadastro(painelPrincipal, cardLayout);
        JPanel painelContas = telaContas.criarPainelControleContas(painelPrincipal, cardLayout);

        // Adiciona os painéis ao gerenciador CardLayout
        painelPrincipal.add(painelMenuPrincipal, "menuPrincipal");
        painelPrincipal.add(painelMenuCadastro, "menuCadastro");
        painelPrincipal.add(painelContas, "menuContas");

        frame.add(painelPrincipal);
        frame.setLocationRelativeTo(null);
        frame.setVisible(visivel);
    }

    /**
     * Cria e configura o painel do menu principal com os botões de navegação.
     * Inicialmente, os botões de funcionalidade são desabilitados até que os dados CSV sejam carregados.
     *
     * @return O {@link JPanel} configurado com o menu principal.
     */
    private static JPanel criarPainelMenuPrincipal() {
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(6, 1, 10, 10));

        btnCadastro = new JButton("Cadastro (Clientes, Fornecedores, Produtos)");
        btnVendas = new JButton("Registro de Vendas");
        btnContas = new JButton("Controle de Contas");
        btnRelatorios = new JButton("Geração de Relatórios Mensais");
        JButton btnCarregarCSV = new JButton("Carregar/Recarregar arquivos CSV");
        JButton btnSair = new JButton("Sair");

        // Bloqueia os botões principais até que os arquivos CSV sejam carregados.
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
     *
     * @param listener Ação de callback (implementando a interface funcional {@link CarregamentoCSVListener})
     * a ser executada quando o carregamento for concluído com sucesso.
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
     * Usado para prevenir o uso do sistema antes do carregamento de dados.
     *
     * @param habilitar {@code true} para habilitar os botões, {@code false} para desabilitá-los.
     */
    private static void habilitarBotoesPrincipais(boolean habilitar) {
        btnCadastro.setEnabled(habilitar);
        btnVendas.setEnabled(habilitar);
        btnContas.setEnabled(habilitar);
        btnRelatorios.setEnabled(habilitar);
    }
}
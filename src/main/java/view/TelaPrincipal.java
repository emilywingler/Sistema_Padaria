package view;

import javax.swing.*;
import java.awt.*;
import model.*;
import service.*;
import view.forms.FormularioCarregarCSVs;
import view.forms.GerarRelatorioMensal;

public class TelaPrincipal {

    // As gerências continuam as mesmas
    private static GerenciaCliente gerenciaCliente = new GerenciaCliente(null);
    private static GerenciaProduto gerenciaProduto = new GerenciaProduto(null);
    private static GerenciaFornecedor gerenciaFornecedor = new GerenciaFornecedor(null);
    private static GerenciaCompra gerenciaCompra = new GerenciaCompra(gerenciaProduto, gerenciaFornecedor, null);
    private static GerenciaVenda gerenciaVenda = new GerenciaVenda(gerenciaProduto, gerenciaCliente, null);

    // Referências para os componentes da UI que precisam ser atualizados
    private static CardLayout cardLayout;
    private static JPanel painelPrincipal;
    private static JFrame frame; // <<< MUDANÇA: Frame agora é um campo estático para ser acessível

    // <<< MUDANÇA: Botões agora são campos estáticos para serem acessados pelo listener
    private static JButton btnCadastro;
    private static JButton btnVendas;
    private static JButton btnContas;
    private static JButton btnRelatorios;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // <<< MUDANÇA: Criamos a GUI principal primeiro, mas a deixamos invisível
            criarEExibirGUI(false);

            // Abre a tela para carregar os CSVs com um "listener"
            // Este listener dirá o que fazer quando os arquivos forem carregados
            abrirFormularioCSVs(() -> {
                // Esta é a ação de callback:
                // 1. Habilita os botões
                habilitarBotoesPrincipais(true);
                // 2. Mostra a janela principal
                frame.setVisible(true);
                JOptionPane.showMessageDialog(frame, "Arquivos carregados com sucesso! O sistema está pronto para uso.");
            });
        });
    }

    // <<< MUDANÇA: Método agora aceita um booleano para controlar a visibilidade inicial
    private static void criarEExibirGUI(boolean visivel) {
        TelaCadastro cadastro = new TelaCadastro(gerenciaProduto, gerenciaFornecedor, gerenciaCliente);
        TelaControleContas telaContas = new TelaControleContas(gerenciaCompra, gerenciaVenda, gerenciaProduto, gerenciaCliente);

        // Janela principal
        frame = new JFrame("Sistema de Gestão de Padaria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Painel principal com CardLayout
        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        // Menus
        JPanel painelMenuPrincipal = criarPainelMenuPrincipal();
        JPanel painelMenuCadastro = cadastro.criarPainelMenuCadastro(painelPrincipal, cardLayout);
        JPanel painelContas = telaContas.criarPainelControleContas(painelPrincipal, cardLayout);

        // Adiciona os painéis
        painelPrincipal.add(painelMenuPrincipal, "menuPrincipal");
        painelPrincipal.add(painelMenuCadastro, "menuCadastro");
        painelPrincipal.add(painelContas, "menuContas");

        frame.add(painelPrincipal);
        frame.setLocationRelativeTo(null);
        frame.setVisible(visivel); // <<< MUDANÇA: Controla a visibilidade
    }

    // <<< MUDANÇA: Método não precisa mais dos parâmetros do CardLayout, pois são estáticos
    private static JPanel criarPainelMenuPrincipal() {
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(6, 1, 10, 10));

        // <<< MUDANÇA: Atribui os botões aos campos estáticos
        btnCadastro = new JButton("Cadastro (Clientes, Fornecedores, Produtos)");
        btnVendas = new JButton("Registro de Vendas");
        btnContas = new JButton("Controle de Contas");
        btnRelatorios = new JButton("Geração de Relatórios Mensais");
        JButton btnCarregarCSV = new JButton("Carregar/Recarregar arquivos CSV");
        JButton btnSair = new JButton("Sair");

        // Bloqueia o acesso até carregar os CSVs
        habilitarBotoesPrincipais(false);

        // Action Listeners
        btnCadastro.addActionListener(e -> cardLayout.show(painelPrincipal, "menuCadastro"));
        btnContas.addActionListener(e -> cardLayout.show(painelPrincipal, "menuContas"));
        btnSair.addActionListener(e -> System.exit(0));

        // <<< MUDANÇA: Lógica do ActionListener refatorada
        btnCarregarCSV.addActionListener(e -> {
            abrirFormularioCSVs(() -> {
                habilitarBotoesPrincipais(true); // Apenas habilita os botões
                JOptionPane.showMessageDialog(frame, "Novos arquivos carregados com sucesso!");
            });
        });
        
        // ActionListener para o botão de Relatórios
        btnRelatorios.addActionListener(e -> {
            // Cria uma nova instância do formulário de relatórios
            GerarRelatorioMensal formRelatorios = new GerarRelatorioMensal(
                    frame, // Passa a janela principal como "pai"
                    gerenciaCompra,
                    gerenciaFornecedor,
                    gerenciaVenda,
                    gerenciaCliente,
                    gerenciaProduto);
            
            // Exibe o formulário de relatórios
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


    private static void abrirFormularioCSVs(CarregamentoCSVListener listener) {
        FormularioCarregarCSVs formCSV = new FormularioCarregarCSVs(
                gerenciaCliente,
                gerenciaFornecedor,
                gerenciaProduto,
                gerenciaCompra,
                gerenciaVenda,
                listener // Passa o listener para o formulário
        );
        formCSV.setVisible(true);
    }


    private static void habilitarBotoesPrincipais(boolean habilitar) {
        btnCadastro.setEnabled(habilitar);
        btnVendas.setEnabled(habilitar);
        btnContas.setEnabled(habilitar);
        btnRelatorios.setEnabled(habilitar);
    }
}
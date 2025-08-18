package view;


import javax.swing.*;
import java.awt.*;


public class TelaPrincipal {

    public static void main(String[] args) {
        // Garante que o código da interface rode na thread correta
        SwingUtilities.invokeLater(() -> {
            criarEExibirGUI();
        });
    }

    private static void criarEExibirGUI() {
        // 1. Criar a janela principal (JFrame)
        JFrame frame = new JFrame("Sistema de Gestão de Padaria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao clicar no X
        frame.setSize(800, 600); // Define um tamanho

        // 2. Configurar o painel principal com CardLayout (nosso "maquinista")
        CardLayout cardLayout = new CardLayout();
        JPanel painelPrincipal = new JPanel(cardLayout);

        // 3. Criar os painéis para cada "tela" (os "cenários")
        JPanel painelMenuPrincipal = criarPainelMenuPrincipal(painelPrincipal, cardLayout);
        JPanel painelMenuCadastro = criarPainelMenuCadastro(painelPrincipal, cardLayout);
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
        painel.add(btnSair);

        return painel;
        
        // Adicione este método também dentro da classe TelaPrincipal.java
    }
    
    private static JPanel criarPainelMenuCadastro(JPanel painelPrincipal, CardLayout cardLayout) {
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(4, 1, 10, 10));

        // Criar os botões
        JButton btnCadastrarCliente = new JButton("Cadastrar Novo Cliente");
        JButton btnCadastrarFornecedor = new JButton("Cadastrar Novo Fornecedor");
        JButton btnCadastrarProduto = new JButton("Cadastrar Novo Produto");
        JButton btnVoltar = new JButton("Voltar ao Menu Principal");

        // Adicionar "ação" ao botão de Voltar
        btnVoltar.addActionListener(e -> {
            // Pede ao "maquinista" para mostrar o painel "menuPrincipal"
            cardLayout.show(painelPrincipal, "menuPrincipal");
        });

        // Adicionar ações para os botões de cadastro...
        // Por exemplo, para cadastrar um cliente:
        // Dentro do método criarPainelMenuCadastro(...)

        btnCadastrarCliente.addActionListener(e -> {
            // Para obter o 'framePai', você pode precisar de uma referência ao JFrame principal.
            // Uma forma simples é pegar a partir do próprio botão.
            JFrame framePai = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            exibirFormularioCadastroCliente(framePai);
        });

        // Adicionar os botões ao painel
        painel.add(btnCadastrarCliente);
        painel.add(btnCadastrarFornecedor);
        painel.add(btnCadastrarProduto);
        painel.add(btnVoltar);

        return painel;
    }
    
    // Este método pode ficar na sua classe da interface, como a TelaPrincipal.java

private static void exibirFormularioCadastroCliente(JFrame framePai) {
    // 1. Criar o painel do formulário com GridLayout
    // GridLayout(0, 2, 10, 10) -> (linhas, colunas, espaçamento horizontal, espaçamento vertical)
    // 0 linhas significa que ele se ajusta conforme adicionamos componentes.
    JPanel painelFormulario = new JPanel(new GridLayout(0, 2, 10, 10));

    // 2. Criar os componentes e adicioná-los ao painel
    // Usamos JTextField para os campos de texto. O número no construtor é a largura sugerida.
    JTextField campoCodigo = new JTextField(10);
    JTextField campoNome = new JTextField(30);
    JTextField campoEndereco = new JTextField(30);
    JTextField campoTelefone = new JTextField(15);
    JTextField campoCpfCnpj = new JTextField(20);

    // Adicionando os componentes em pares: Rótulo (JLabel) e Campo de Texto (JTextField)
    painelFormulario.add(new JLabel("Código:"));
    painelFormulario.add(campoCodigo);

    painelFormulario.add(new JLabel("Nome Completo:"));
    painelFormulario.add(campoNome);
    
    painelFormulario.add(new JLabel("Endereço:"));
    painelFormulario.add(campoEndereco);
    
    painelFormulario.add(new JLabel("Telefone:"));
    painelFormulario.add(campoTelefone);
    
    painelFormulario.add(new JLabel("CPF ou CNPJ:"));
    painelFormulario.add(campoCpfCnpj);
    
    // (Para o campo "Tipo" F ou J, um JComboBox seria o ideal, mas para agilizar,
    // podemos pedir para o usuário digitar F ou J no campo de CPF/CNPJ ou um novo campo)

    // 3. Exibir o formulário em um JOptionPane
    int resultado = JOptionPane.showConfirmDialog(
        framePai,                 // A janela "pai" do diálogo
        painelFormulario,         // O painel do formulário que criamos
        "Cadastrar Novo Cliente", // O título da janela de diálogo
        JOptionPane.OK_CANCEL_OPTION, // Botões OK e Cancelar
        JOptionPane.PLAIN_MESSAGE     // Ícone
    );

    // 4. Processar os dados se o usuário clicou em "OK"
    if (resultado == JOptionPane.OK_OPTION) {
        try {
            // Pegar os dados dos campos de texto
            int codigo = Integer.parseInt(campoCodigo.getText());
            String nome = campoNome.getText();
            String endereco = campoEndereco.getText();
            String telefone = campoTelefone.getText();
            String cpfCnpj = campoCpfCnpj.getText();
            
            // Aqui você faria as validações (campos vazios, etc.)

            // E finalmente, chamaria o seu serviço para cadastrar o cliente
            // Exemplo: gerenciaCliente.cadastrar(codigo, nome, endereco, telefone, ...);

            JOptionPane.showMessageDialog(framePai, "Cliente '" + nome + "' cadastrado com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(framePai, "Erro: O código deve ser um número.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}
}

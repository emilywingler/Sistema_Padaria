package view.forms;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import javax.swing.*;
import view.utils.FormUtils;
import service.*;
import model.*;

/**
     * @author emily
 */
public class FormularioVendaPanel extends JPanel {
    private JTextField campoDataVenda; //String
    private JTextField campoIdProduto; //int
    private JTextField campoQuantidade; //int
    private JComboBox<String> seletorMeioPagamento; //String
    private JTextField campoIdCliente; //int
    private JLabel labelNomeProduto; // Label para exibir o nome do produto
    private JLabel labelNomeCliente; // Label para exibir o nome do cliente
    private GerenciaProduto gp;
    private GerenciaCliente gc;
    private GerenciaVenda gv;
    
    public FormularioVendaPanel(GerenciaProduto gp, GerenciaCliente gc, GerenciaVenda gv) {
        // Configura o layout principal do painel
        super(new GridLayout(0, 2, 10, 10));
        this.gc = gc;
        this.gp =gp;
        this.gv = gv;
        // Inicializa e adiciona os componentes
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
    // --- PARTE 1: CRIAÇÃO DE TODOS OS COMPONENTES ---

    // Campos de texto
    campoIdProduto = new JTextField(10);
    campoQuantidade = new JTextField(10);
    campoDataVenda = FormUtils.criarCampoDataComPlaceholder();
    campoIdCliente = new JTextField(20);

    // Labels de exibição dinâmica
    labelNomeProduto = new JLabel("Digite o ID do produto e tecle TAB");
    labelNomeProduto.setForeground(Color.BLUE);
    labelNomeCliente = new JLabel(""); // Começa vazio
    labelNomeCliente.setForeground(Color.BLUE);

    // Seletor de pagamento
    String[] meiosPagamento = {"Dinheiro", "Cheque", "Cartão de Débito", 
                               "Cartão de Crédito", "Ticket Alimentação", "Fiado"};
    seletorMeioPagamento = new JComboBox<>(meiosPagamento);

    // Painéis para o layout dinâmico
    CardLayout cardLayoutDinamico = new CardLayout();
    JPanel painelDinamico = new JPanel(cardLayoutDinamico);
    JPanel painelFiado = new JPanel(new GridLayout(0, 2, 10, 10));
    JPanel painelVazio = new JPanel();


    // --- PARTE 2: CONFIGURAÇÃO DOS LISTENERS E PAINÉIS ---

    // Listener para buscar o nome do produto quando o foco sair do campo ID
    campoIdProduto.addFocusListener(new FocusAdapter() {
        @Override
        public void focusLost(FocusEvent e) {
            try {
                int id = Integer.parseInt(campoIdProduto.getText());
                Produto p = gp.buscarProduto(id);
                if (p != null) {
                    labelNomeProduto.setText(id + ": " + p.getDescricao() + "    Estoque: " + p.getEstoqueAtual());
                    labelNomeProduto.setForeground(Color.BLUE);
                } else {
                    labelNomeProduto.setText("Produto não encontrado");
                    labelNomeProduto.setForeground(Color.RED);
                }
            } catch (NumberFormatException ex) {
                labelNomeProduto.setText("ID inválido");
                labelNomeProduto.setForeground(Color.RED);
            }
        }
    });
    
    // Listener para buscar o nome do cliente (funcionalidade que faltava)
    campoIdCliente.addFocusListener(new FocusAdapter() {
        @Override
        public void focusLost(FocusEvent e) {
            try {
                int id = Integer.parseInt(campoIdCliente.getText());
                Cliente c = gc.buscarCliente(id);
                if (c != null) {
                    labelNomeCliente.setText(id + ": " + c.getNome() + "    Divida: R$ " + gv.totalAReceberCliente(c));
                    labelNomeCliente.setForeground(Color.BLUE);
                } else {
                    labelNomeCliente.setText("Cliente não encontrado");
                    labelNomeCliente.setForeground(Color.RED);
                }
            } catch (NumberFormatException ex) {
                labelNomeCliente.setText("ID inválido");
                labelNomeCliente.setForeground(Color.RED);
            }
        }
    });

    // Listener do seletor de pagamento para mostrar/esconder o campo de cliente
    seletorMeioPagamento.addItemListener(e -> {
        if ("Fiado".equals(e.getItem())) {
            cardLayoutDinamico.show(painelDinamico, "FIADO");
        } else {
            cardLayoutDinamico.show(painelDinamico, "NAO_FIADO");
        }
    });

    // Monta o painel que só aparece na venda a fiado
    painelFiado.add(new JLabel("ID Cliente:"));
    painelFiado.add(campoIdCliente);
    painelFiado.add(new JLabel("Cliente:"));
    painelFiado.add(labelNomeCliente);
    
    // Monta o painel dinâmico com as duas "cartas"
    painelDinamico.add(painelVazio, "NAO_FIADO");
    painelDinamico.add(painelFiado, "FIADO");


    // --- PARTE 3: MONTAGEM FINAL DO LAYOUT ---
    // Adiciona tudo ao painel principal (this) na ordem correta, UMA ÚNICA VEZ.
    
    add(new JLabel("ID do Produto:"));
    add(campoIdProduto);

    add(new JLabel("Produto:"));
    add(labelNomeProduto);

    add(new JLabel("Quantidade:"));
    add(campoQuantidade);

    add(new JLabel("Data da Venda:"));
    add(campoDataVenda);
    
    add(new JLabel("Meio de Pagamento:"));
    add(seletorMeioPagamento);

    // Adiciona o painel dinâmico, que vai se ajustar sozinho
    add(painelDinamico);
}
    
    public boolean validarCampos(Component framePai) {
    try {
        // --- 1. VALIDAÇÃO DO PRODUTO ---
        if (!FormUtils.validarCampoTextoVazio(campoIdProduto, "ID do Produto", framePai)) return false;
        
        int idProduto = Integer.parseInt(campoIdProduto.getText());
        Produto produto = gp.buscarProduto(idProduto);

        if (produto == null) {
            JOptionPane.showMessageDialog(framePai, "Nenhum produto encontrado com o código informado.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // --- 2. VALIDAÇÃO DA QUANTIDADE E ESTOQUE ---
        if (!FormUtils.validarCampoTextoVazio(campoQuantidade, "Quantidade", framePai)) return false;

        int quantidade = Integer.parseInt(campoQuantidade.getText());

        if (quantidade <= 0) {
            JOptionPane.showMessageDialog(framePai, "A quantidade deve ser maior que zero.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        //Verificar se o estoque não é nulo...
        if(produto.getEstoqueAtual() == 0){
            String mensagem = "Esse produto está SEM estoque! Tente com outro produto.";
            JOptionPane.showMessageDialog(framePai, mensagem, "Erro de Estoque", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Regra de negócio CRÍTICA: Verificar se há estoque suficiente
        if (quantidade > produto.getEstoqueAtual()) {
            String mensagem = "Estoque insuficiente para '" + produto.getDescricao() + "'.\n"
                            + "Apenas " + produto.getEstoqueAtual() + " unidades disponíveis.";
            JOptionPane.showMessageDialog(framePai, mensagem, "Erro de Estoque", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // --- 3. VALIDAÇÃO DA DATA ---
        String dataTexto = campoDataVenda.getText();
        if (dataTexto.trim().isEmpty() || dataTexto.equals("dd/MM/yyyy")) {
            JOptionPane.showMessageDialog(framePai, "O campo 'Data da Venda' deve ser preenchido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // --- 4. VALIDAÇÃO CONDICIONAL DO CLIENTE (SE FOR FIADO) ---
        String meioPagamento = (String) seletorMeioPagamento.getSelectedItem();
        if ("Fiado".equals(meioPagamento)) {
            if (!FormUtils.validarCampoTextoVazio(campoIdCliente, "ID Cliente", framePai)) return false;

            int idCliente = Integer.parseInt(campoIdCliente.getText());
            Cliente cliente = gc.buscarCliente(idCliente);

            if (cliente == null) {
                JOptionPane.showMessageDialog(framePai, "Nenhum cliente encontrado com o código informado para a venda fiado.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(framePai, "Erro: Campos de ID e Quantidade devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Se passou por todas as verificações, o formulário é válido!
    return true;
}
}

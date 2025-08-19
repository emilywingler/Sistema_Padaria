package view;

import javax.swing.*;
import java.awt.*;
import model.*;
import io.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import report.*;
import service.*;

public class TelaCadastro{
    
    private GerenciaProduto gp;
    private GerenciaFornecedor gf;
    private GerenciaCliente gc;

    public TelaCadastro(GerenciaProduto gp, GerenciaFornecedor gf, GerenciaCliente gc) {
        this.gp = gp;
        this.gf = gf;
        this.gc = gc;
    }
    
    public JPanel criarPainelMenuCadastro(JPanel painelPrincipal, CardLayout cardLayout) {
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
    private void exibirFormularioCadastroCliente(JFrame framePai) {
        
        while(true){
            // --- PARTE ESTÁTICA DO FORMULÁRIO ---
        JPanel painelFormulario = new JPanel(new GridLayout(0, 2, 10, 10));

        JTextField campoCodigo = new JTextField(10);
        JTextField campoNome = new JTextField(30);
        JTextField campoEndereco = new JTextField(30);
        JTextField campoTelefone = new JTextField(15);

        painelFormulario.add(new JLabel("Código:"));
        painelFormulario.add(campoCodigo);

        painelFormulario.add(new JLabel("Nome Completo:"));
        painelFormulario.add(campoNome);

        painelFormulario.add(new JLabel("Endereço:"));
        painelFormulario.add(campoEndereco);

        painelFormulario.add(new JLabel("Telefone:"));
        painelFormulario.add(campoTelefone);
        
        // No método exibirFormularioCadastroCliente, adicione isso junto com os outros campos:

        JTextField campoData = new JTextField("dd/MM/yyyy");
        campoData.setForeground(Color.GRAY); // Deixa o texto de ajuda cinza

        campoData.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoData.getText().equals("dd/MM/yyyy")) {
                    campoData.setText("");
                    campoData.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (campoData.getText().isEmpty()) {
                    campoData.setForeground(Color.GRAY);
                    campoData.setText("dd/MM/yyyy");
                    }
                }
            });

        // E adicione o campo ao painel do formulário:
        painelFormulario.add(new JLabel("Data de Cadastro:"));
        painelFormulario.add(campoData);

        // --- PARTE DINÂMICA DO FORMULÁRIO ---

        // 1. O SELETOR (JComboBox)
        String[] tiposPessoa = { "Pessoa Física", "Pessoa Jurídica" };
        JComboBox<String> seletorTipo = new JComboBox<>(tiposPessoa);

        // 2. O "MINI PALCO" com CardLayout para os campos que vão mudar
        CardLayout cardLayoutDinamico = new CardLayout();
        JPanel painelDinamico = new JPanel(cardLayoutDinamico);

        // 3. CENÁRIO 1: Painel para Pessoa Física
        JPanel painelFisica = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField campoCpf = new JTextField(20);
        painelFisica.add(new JLabel("CPF:"));
        painelFisica.add(campoCpf);

        // 4. CENÁRIO 2: Painel para Pessoa Jurídica
        JPanel painelJuridica = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField campoCnpj = new JTextField(20);
        JTextField campoInscricao = new JTextField(20);
        painelJuridica.add(new JLabel("CNPJ:"));
        painelJuridica.add(campoCnpj);
        painelJuridica.add(new JLabel("Inscrição Estadual:"));
        painelJuridica.add(campoInscricao);

        // Adiciona os cenários ao "mini palco" com nomes
        painelDinamico.add(painelFisica, "Pessoa Física");
        painelDinamico.add(painelJuridica, "Pessoa Jurídica");

        // 5. O "OUVINTE" que faz a mágica acontecer
        // Adiciona uma ação ao seletor para trocar o painel visível
        seletorTipo.addItemListener(e -> {
            String itemSelecionado = (String) e.getItem();
            cardLayoutDinamico.show(painelDinamico, itemSelecionado);
        });

        // 6. ADICIONA O SELETOR E O PAINEL DINÂMICO AO FORMULÁRIO PRINCIPAL
        painelFormulario.add(new JLabel("Tipo de Cliente:"));
        painelFormulario.add(seletorTipo);

        // Adicionamos o painel dinâmico, fazendo-o ocupar as 2 colunas
        // Criamos um painel extra para conter o painelDinamico e evitar que o GridLayout o divida
        JPanel containerDinamico = new JPanel(new BorderLayout());
        containerDinamico.add(painelDinamico, BorderLayout.CENTER);
        painelFormulario.add(containerDinamico);


        // --- EXIBIÇÃO E PROCESSAMENTO ---
        int resultado = JOptionPane.showConfirmDialog(framePai, painelFormulario, "Cadastrar Novo Cliente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            int codigo = 0;
            try{
                codigo = Integer.parseInt(campoCodigo.getText());
                if(gc.buscarCliente(codigo)!=null){
                    JOptionPane.showMessageDialog(framePai, "Já existe um usuário cadastrao com esse ID, tente novamente!", "Erro de Duplicata", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
            }
            catch(NumberFormatException ex){
                 JOptionPane.showMessageDialog(framePai, "Erro: O código deve ser um número.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
            String nome = campoNome.getText();
            String endereco = campoEndereco.getText();
            String telefone = campoTelefone.getText();
            String dataCadastro = campoData.getText(); // Vou adicionar Verificação depois.
            String tipoSelecionado = (String) seletorTipo.getSelectedItem();
            
            if (nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(framePai, "O campo 'Nome' não pode estar vazio.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            continue;
            }
            if (endereco.trim().isEmpty()) {
                JOptionPane.showMessageDialog(framePai, "O campo 'Endereço' não pode estar vazio.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            if (telefone.trim().isEmpty()) {
                JOptionPane.showMessageDialog(framePai, "O campo 'Telefone' não pode estar vazio.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            // Valida se a data está vazia ou se ainda é o placeholder
            if (dataCadastro.trim().isEmpty() || dataCadastro.equals("dd/MM/yyyy")) {
                JOptionPane.showMessageDialog(framePai, "O campo 'Data de Cadastro' deve ser preenchido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            // Adicione a validação do formato da data aqui se desejar (com try-catch para DateTimeParseException)
            
            if("Pessoa Física".equals(tipoSelecionado)){
                String cpf = campoCpf.getText();
                if (cpf.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(framePai, "O campo 'CPF' não pode estar vazio.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                System.out.println("Cadastrando Pessoa Física: " + nome + " com CPF: " + cpf);
                gc.inserirCliente(codigo, nome, endereco, telefone, dataCadastro,"F", cpf);
                JOptionPane.showMessageDialog(framePai, "Cliente '" + nome + "' cadastrado com sucesso!");
                break;
            }
            else{
                String cnpj = campoCnpj.getText();
                if (cnpj.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(framePai, "O campo 'CNPJ' não pode estar vazio.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                try{
                    int inscricao = Integer.parseInt(campoInscricao.getText());
                    System.out.println("Cadastrando Pessoa Jurídica: " + nome + " com CNPJ: " + cnpj + " e IE: " + inscricao);
                    gc.inserirCliente(cnpj, inscricao, codigo, nome, endereco, telefone, dataCadastro, "J");
                    JOptionPane.showMessageDialog(framePai, "Cliente '" + nome + "' cadastrado com sucesso!");
                    break;
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(framePai, "Erro: A inscrição estadual deve ser um número.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                }
                
                
            }
            

            }
        else{
            break;
        }
        }
        
     }
}


    



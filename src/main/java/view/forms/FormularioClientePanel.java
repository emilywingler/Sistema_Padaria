package view.forms;

import java.awt.*;
import javax.swing.*;
import view.utils.FormUtils; // Importando nossa classe utilitária

/**
 * Painel que encapsula todos os componentes do formulário de cadastro de cliente.
 */
public class FormularioClientePanel extends JPanel {

    // Componentes do formulário são agora campos da classe
    private JTextField campoCodigo;
    private JTextField campoNome;
    private JTextField campoEndereco;
    private JTextField campoTelefone;
    private JTextField campoData;
    private JComboBox<String> seletorTipo;
    private JTextField campoCpf;
    private JTextField campoCnpj;
    private JTextField campoInscricao;

    public FormularioClientePanel() {
        // Configura o layout principal do painel
        super(new GridLayout(0, 2, 10, 10));

        // Inicializa e adiciona os componentes
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // --- Componentes Estáticos ---
        campoCodigo = new JTextField(10);
        campoNome = new JTextField(30);
        campoEndereco = new JTextField(30);
        campoTelefone = new JTextField(15);
        campoData = FormUtils.criarCampoDataComPlaceholder(); // REUTILIZANDO NOSSO MÉTODO!

        add(new JLabel("Código:"));
        add(campoCodigo);
        add(new JLabel("Nome Completo:"));
        add(campoNome);
        add(new JLabel("Endereço:"));
        add(campoEndereco);
        add(new JLabel("Telefone:"));
        add(campoTelefone);
        add(new JLabel("Data de Cadastro:"));
        add(campoData);
        
        // --- Componentes Dinâmicos ---
        String[] tiposPessoa = { "Pessoa Física", "Pessoa Jurídica" };
        seletorTipo = new JComboBox<>(tiposPessoa);

        CardLayout cardLayoutDinamico = new CardLayout();
        JPanel painelDinamico = new JPanel(cardLayoutDinamico);

        // Painel PF
        JPanel painelFisica = new JPanel(new GridLayout(0, 2, 10, 10));
        campoCpf = new JTextField(20);
        painelFisica.add(new JLabel("CPF:"));
        painelFisica.add(campoCpf);

        // Painel PJ
        JPanel painelJuridica = new JPanel(new GridLayout(0, 2, 10, 10));
        campoCnpj = new JTextField(20);
        campoInscricao = new JTextField(20);
        painelJuridica.add(new JLabel("CNPJ:"));
        painelJuridica.add(campoCnpj);
        painelJuridica.add(new JLabel("Inscrição Estadual:"));
        painelJuridica.add(campoInscricao);

        painelDinamico.add(painelFisica, "Pessoa Física");
        painelDinamico.add(painelJuridica, "Pessoa Jurídica");
        
        seletorTipo.addItemListener(e -> cardLayoutDinamico.show(painelDinamico, (String) e.getItem()));

        add(new JLabel("Tipo de Cliente:"));
        add(seletorTipo);
        add(painelDinamico); // Adicionando o painel dinâmico
    }
    
        // Dentro da classe FormularioClientePanel, adicione este método:

    public boolean validarCampos(Component framePai) {
        // Valida campos de texto comuns
        if (!FormUtils.validarCampoTextoVazio(this.campoNome, "Nome", framePai)) return false;
        if (!FormUtils.validarCampoTextoVazio(this.campoEndereco, "Endereço", framePai)) return false;
        if (!FormUtils.validarCampoTextoVazio(this.campoTelefone, "Telefone", framePai)) return false;

        // Validação específica para a data (que também tem o placeholder)
        if (this.campoData.getText().trim().isEmpty() || this.campoData.getText().equals("dd/MM/yyyy")) {
            JOptionPane.showMessageDialog(framePai, "O campo 'Data de Cadastro' deve ser preenchido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validação condicional
        if ("Pessoa Física".equals(getTipoPessoa())) {
            if (!FormUtils.validarCampoTextoVazio(this.campoCpf, "CPF", framePai)) return false;
        } else { // Pessoa Jurídica
            if (!FormUtils.validarCampoTextoVazio(this.campoCnpj, "CNPJ", framePai)) return false;
            // Validação de formato para Inscrição Estadual (não pode estar vazio e deve ser número)
            try {
                Integer.parseInt(this.campoInscricao.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(framePai, "O campo 'Inscrição Estadual' deve ser um número e não pode estar vazio.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Se passou por todas as validações
        return true;
    }
    // --- Métodos para obter os dados preenchidos ---
    public int getCodigo() throws NumberFormatException {
        return Integer.parseInt(campoCodigo.getText());
    }
    
    public String getNome() { return campoNome.getText(); }
    public String getEndereco() { return campoEndereco.getText(); }
    public String getTelefone() { return campoTelefone.getText(); }
    public String getDataCadastro() { return campoData.getText(); }
    public String getTipoPessoa() { return (String) seletorTipo.getSelectedItem(); }
    public String getCpf() { return campoCpf.getText(); }
    public String getCnpj() { return campoCnpj.getText(); }
    public int getInscricaoEstadual() throws NumberFormatException {
        return Integer.parseInt(campoInscricao.getText());
    }
}

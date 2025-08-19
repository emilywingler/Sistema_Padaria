package view.forms;

import java.awt.*;
import javax.swing.*;
import view.utils.FormUtils;

/**
 *
 * @author emily
 * 
 */
public class FormularioFornecedorPanel extends JPanel{
    private JTextField campoIdFornecedor;
    private JTextField campoNomeEmpresa;
    private JTextField campoEndereco;
    private JTextField campoTelefone;
    private JTextField campoCnpj;
    private JTextField campoPessoaContato;
    
    public FormularioFornecedorPanel() {
        super(new GridLayout(0, 2, 10, 10));

        // Inicializa e adiciona os componentes
        inicializarComponentes();
    }
    
    private void inicializarComponentes(){
        campoIdFornecedor = new JTextField(10);
        campoNomeEmpresa = new JTextField(30);
        campoEndereco = new JTextField(30);
        campoTelefone = new JTextField(15);
        campoCnpj = new JTextField(30);
        campoPessoaContato = new JTextField(30);
                
        add(new JLabel("ID: "));
        add(campoIdFornecedor);
        add(new JLabel("Nome da Empresa:"));
        add(campoNomeEmpresa);
        add(new JLabel("CNPJ:"));
        add(campoCnpj);
        add(new JLabel("Endereço:"));
        add(campoEndereco);
        add(new JLabel("Telefone:"));
        add(campoTelefone);
        add(new JLabel("Pessoa de Contato: "));
        add(campoPessoaContato);
    }
    
    public boolean validarCampos(Component framePai){
        if(!FormUtils.validarCampoTextoVazio(this.campoNomeEmpresa, "Nome da Empresa", framePai)) return false;
        if (!FormUtils.validarCampoTextoVazio(this.campoEndereco, "Endereço", framePai)) return false;
        if (!FormUtils.validarCampoTextoVazio(this.campoTelefone, "Telefone", framePai)) return false;
        if(!FormUtils.validarCampoTextoVazio(this.campoCnpj, "CNPJ", framePai)) return false;
        if(!FormUtils.validarCampoTextoVazio(this.campoPessoaContato, "Pessoa de Contato", framePai)) return false;
        return true;
    }

    // --- Métodos para obter os dados preenchidos ---
    public int getIdFornecedor() throws NumberFormatException {
        return Integer.parseInt(campoIdFornecedor.getText());
    }
    public String getNomeEmpresa() {
        return campoNomeEmpresa.getText();
    }

    public String getEndereco() {
        return campoEndereco.getText();
    }

    public String getTelefone() {
        return campoTelefone.getText();
    }

    public String getCnpj() {
        return campoCnpj.getText();
    }

    public String getPessoaContato() {
        return campoPessoaContato.getText();
    }

}

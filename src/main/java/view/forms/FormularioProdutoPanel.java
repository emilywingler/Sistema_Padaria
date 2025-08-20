package view.forms;

import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import view.utils.FormUtils;

public class FormularioProdutoPanel extends JPanel {
    private JTextField campoIdProduto;        // int
    private JTextField campoDescricao;        // String
    private JTextField campoMinEstoque;       // int
    private JTextField campoEstoqueAtual;     // int
    private JTextField campoCusto;            // BigDecimal
    private JTextField campoPercentualLucro;  // int

    public FormularioProdutoPanel() {
        super(new GridLayout(0, 2, 10, 10));

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        campoIdProduto = new JTextField(10);
        campoDescricao = new JTextField(30);
        campoMinEstoque = new JTextField(10);
        campoEstoqueAtual = new JTextField(10);
        campoCusto = new JTextField(15);
        campoPercentualLucro = new JTextField(5);

        add(new JLabel("ID Produto:"));
        add(campoIdProduto);

        add(new JLabel("Descrição:"));
        add(campoDescricao);

        add(new JLabel("Estoque Mínimo:"));
        add(campoMinEstoque);

        add(new JLabel("Estoque Atual:"));
        add(campoEstoqueAtual);

        add(new JLabel("Custo:"));
        add(campoCusto);

        add(new JLabel("Percentual de Lucro (%):"));
        add(campoPercentualLucro);
    }

    public boolean validarCampos(Component framePai) {
        if (!FormUtils.validarCampoTextoVazio(this.campoDescricao, "Descrição", framePai)) return false;
        if (!FormUtils.validarCampoTextoVazio(this.campoMinEstoque, "Estoque Mínimo", framePai)) return false;
        if (!FormUtils.validarCampoTextoVazio(this.campoEstoqueAtual, "Estoque Atual", framePai)) return false;
        if (!FormUtils.validarCampoTextoVazio(this.campoCusto, "Custo", framePai)) return false;
        if (!FormUtils.validarCampoTextoVazio(this.campoPercentualLucro, "Percentual de Lucro", framePai)) return false;
        return true;
    }

    // --- Métodos para obter os dados preenchidos ---
    public int getIdProduto() throws NumberFormatException {
        return Integer.parseInt(campoIdProduto.getText());
    }

    public String getDescricao() {
        return campoDescricao.getText();
    }

    public int getMinEstoque() throws NumberFormatException {
        return Integer.parseInt(campoMinEstoque.getText());
    }

    public int getEstoqueAtual() throws NumberFormatException {
        return Integer.parseInt(campoEstoqueAtual.getText());
    }

    public BigDecimal getCusto() throws NumberFormatException {
        return new BigDecimal(campoCusto.getText());
    }

    public int getPercentualLucro() throws NumberFormatException {
        return Integer.parseInt(campoPercentualLucro.getText());
    }
}

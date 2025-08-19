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
import view.forms.*;
import view.utils.FormUtils;

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
        
        btnCadastrarFornecedor.addActionListener(e -> {
            JFrame framePai = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            exibirFormularioCadastroFornecedor(framePai);
        
        });

        // Adicionar os botões ao painel
        painel.add(btnCadastrarCliente);
        painel.add(btnCadastrarFornecedor);
        painel.add(btnCadastrarProduto);
        painel.add(btnVoltar);

        return painel;
    }
    
    private void exibirFormularioCadastroCliente(JFrame framePai) {
    while (true) {
        FormularioClientePanel formulario = new FormularioClientePanel();

        int resultado = JOptionPane.showConfirmDialog(framePai, formulario, "Cadastrar Novo Cliente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            // 1. CHAMA O MÉTODO DE VALIDAÇÃO DO PRÓPRIO FORMULÁRIO
            if (formulario.validarCampos(framePai)) {
                try {
                    // 2. SE A VALIDAÇÃO PASSOU, OBTÉM OS DADOS E SALVA
                    int codigo = formulario.getCodigo();
                    if (gc.buscarCliente(codigo) != null) {
                        JOptionPane.showMessageDialog(framePai, "Já existe um cliente cadastrado com este código.", "Erro de Duplicata", JOptionPane.ERROR_MESSAGE);
                        continue; // Pede para corrigir o código
                    }

                    String nome = formulario.getNome(); // Pega o nome para a mensagem de sucesso
                    
                    if ("Pessoa Física".equals(formulario.getTipoPessoa())) {
                        gc.inserirCliente(codigo, nome, formulario.getEndereco(), formulario.getTelefone(), 
                                          formulario.getDataCadastro(), "F", formulario.getCpf());
                    } else {
                        gc.inserirCliente(formulario.getCnpj(), formulario.getInscricaoEstadual(), codigo, nome, 
                                          formulario.getEndereco(), formulario.getTelefone(), formulario.getDataCadastro(), "J");
                    }

                    JOptionPane.showMessageDialog(framePai, "Cliente '" + nome + "' cadastrado com sucesso!");
                    break; // SUCESSO: Sai do loop

                } catch (NumberFormatException e) {
                    // Este catch agora serve principalmente para o campo Código
                    JOptionPane.showMessageDialog(framePai, "Erro: O 'Código' deve ser um número e não pode estar vazio.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    // O loop continua para o usuário corrigir
                }
            }
            // Se validarCampos() retornou false, a mensagem de erro já foi exibida.
            // O loop continua para o usuário corrigir os dados.
        } else {
            // Se o usuário clicou em Cancelar ou fechou a janela
            break;
        }
    }
}
    
    private void exibirFormularioCadastroFornecedor(JFrame framePai){
        while(true){
            FormularioFornecedorPanel formulario = new FormularioFornecedorPanel();
            
            int resultado = JOptionPane.showConfirmDialog(framePai, formulario, "Cadastrar Novo Fornecedor",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if(resultado == JOptionPane.OK_OPTION){
                
                if(formulario.validarCampos(framePai)){
                    try{
                        int id = formulario.getIdFornecedor();
                        
                        if(gf.buscarFornecedor(id)!=null){
                            JOptionPane.showMessageDialog(framePai, "Já existe um fornecedor cadastrado com este código.", "Erro de Duplicata", JOptionPane.ERROR_MESSAGE);
                            continue; 
                        }
                        
                        String nome = formulario.getNomeEmpresa();
                        gf.inserirFornecedor(id, nome, formulario.getEndereco(), formulario.getTelefone(), formulario.getCnpj(), formulario.getPessoaContato());
                        
                        JOptionPane.showMessageDialog(framePai, "Fornecedor '" + nome + "' cadastrado com sucesso!");
                        break;
                    }
                    catch(NumberFormatException e){
                        JOptionPane.showMessageDialog(framePai, "Erro: O 'ID' deve ser um número e não pode estar vazio.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else{
                break;
            }
            
    }
}
}   


    



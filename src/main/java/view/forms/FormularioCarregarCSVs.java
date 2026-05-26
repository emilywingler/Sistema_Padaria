package view.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import service.*;
import view.CarregamentoCSVListener;

/**
 * Representa uma janela (formulário) para o carregamento inicial de dados via arquivos CSV.
 * <p>
 * Esta classe fornece a interface gráfica para que o usuário possa selecionar os
 * caminhos dos arquivos CSV de clientes, fornecedores, produtos, compras e vendas.
 * </p>
 * <p>
 * Após a seleção e o clique no botão "Carregar", a classe delega o processamento
 * dos arquivos para as respectivas classes de serviço. Em caso de sucesso, notifica
 * um {@link CarregamentoCSVListener} para que a aplicação principal possa prosseguir
 * com seu fluxo (por exemplo, habilitando a interface principal).
 * </p>
 */
public class FormularioCarregarCSVs extends JFrame {

    // --- Camadas de Serviço ---
    private GerenciaCliente gerenciaCliente;
    private GerenciaProduto gerenciaProduto;
    private GerenciaFornecedor gerenciaFornecedor;
    private GerenciaCompra gerenciaCompra;
    private GerenciaVenda gerenciaVenda;
    
    /** Listener para notificar a conclusão bem-sucedida do carregamento. */
    private CarregamentoCSVListener listener;

    // --- Componentes da UI ---
    private JTextField txtClientes;
    private JTextField txtFornecedores;
    private JTextField txtProdutos;
    private JTextField txtCompras;
    private JTextField txtVendas;
    private JButton btnCarregar;

    /**
     * Constrói o formulário de carregamento de CSVs, injetando as dependências necessárias.
     *
     * @param gerCli   Serviço para carregar dados de clientes.
     * @param gerFor   Serviço para carregar dados de fornecedores.
     * @param gerProd  Serviço para carregar dados de produtos.
     * @param gerComp  Serviço para carregar dados de compras.
     * @param gerVend  Serviço para carregar dados de vendas.
     * @param listener O listener que será notificado quando os arquivos forem carregados com sucesso.
     */
    public FormularioCarregarCSVs(
            GerenciaCliente gerCli,
            GerenciaFornecedor gerFor,
            GerenciaProduto gerProd,
            GerenciaCompra gerComp,
            GerenciaVenda gerVend,
            CarregamentoCSVListener listener) {

        this.gerenciaCliente = gerCli;
        this.gerenciaFornecedor = gerFor;
        this.gerenciaProduto = gerProd;
        this.gerenciaCompra = gerComp;
        this.gerenciaVenda = gerVend;
        this.listener = listener;

        initComponents();
    }

    /**
     * Inicializa e configura os componentes da interface gráfica da janela.
     */
    private void initComponents() {
        setTitle("Carregar arquivos CSV");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas esta janela, não a aplicação inteira.
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 3, 5, 5));

        // Cria as linhas de seleção de arquivo
        txtClientes = criarCampoArquivo("Clientes:");
        txtFornecedores = criarCampoArquivo("Fornecedores:");
        txtProdutos = criarCampoArquivo("Produtos:");
        txtCompras = criarCampoArquivo("Compras:");
        txtVendas = criarCampoArquivo("Vendas:");

        // Botão para iniciar o carregamento
        btnCarregar = new JButton("Carregar CSVs");
        btnCarregar.addActionListener(e -> carregarArquivos());
        add(new JLabel()); // Célula vazia para alinhamento
        add(btnCarregar);
        add(new JLabel()); // Célula vazia para alinhamento
    }

    /**
     * Método fábrica que cria uma linha completa de componentes para seleção de arquivo.
     * Inclui um rótulo, um campo de texto e um botão "Escolher".
     *
     * @param label O texto a ser exibido no rótulo (e.g., "Clientes:").
     * @return O {@link JTextField} criado, para que sua referência possa ser armazenada.
     */
    private JTextField criarCampoArquivo(String label) {
        JLabel lbl = new JLabel(label);
        JTextField txt = new JTextField();
        JButton btn = new JButton("Escolher");
        btn.addActionListener(e -> escolherArquivo(txt));
        
        add(lbl);
        add(txt);
        add(btn);
        
        return txt;
    }

    /**
     * Abre um {@link JFileChooser} para permitir que o usuário selecione um arquivo CSV.
     * O caminho do arquivo selecionado é então inserido no campo de texto fornecido.
     *
     * @param txtCampo O campo de texto a ser preenchido com o caminho do arquivo.
     */
    private void escolherArquivo(JTextField txtCampo) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos CSV", "csv"));
        
        int retorno = fc.showOpenDialog(this);
        if (retorno == JFileChooser.APPROVE_OPTION) {
            File arquivo = fc.getSelectedFile();
            txtCampo.setText(arquivo.getAbsolutePath());
        }
    }

    /**
     * Executa o processo de carregamento dos arquivos.
     * <p>
     * Primeiramente, valida se todos os campos de caminho de arquivo foram preenchidos.
     * Em seguida, invoca os métodos de carregamento em cada serviço correspondente.
     * Se tudo ocorrer sem erros, notifica o listener e fecha a janela.
     * Caso contrário, exibe uma mensagem de erro.
     * </p>
     */
    private void carregarArquivos() {
        String caminhoClientes = txtClientes.getText();
        String caminhoFornecedores = txtFornecedores.getText();
        String caminhoProdutos = txtProdutos.getText();
        String caminhoCompras = txtCompras.getText();
        String caminhoVendas = txtVendas.getText();

        // Validação para garantir que todos os campos estão preenchidos
        if (caminhoClientes == null || caminhoClientes.isBlank() ||
            caminhoFornecedores == null || caminhoFornecedores.isBlank() ||
            caminhoProdutos == null || caminhoProdutos.isBlank() ||
            caminhoCompras == null || caminhoCompras.isBlank() ||
            caminhoVendas == null || caminhoVendas.isBlank()) {

            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha todos os campos com os caminhos dos arquivos CSV.",
                    "Campos Vazios",
                    JOptionPane.WARNING_MESSAGE);
            return; // Interrompe a execução
        }

        try {
            // Delega o carregamento para cada serviço
            gerenciaCliente.carregarClientesCSV(caminhoClientes);
            gerenciaFornecedor.carregarFornecedorCSV(caminhoFornecedores);
            gerenciaProduto.carregarProdutosCSV(caminhoProdutos);
            gerenciaCompra.carregarComprasCSV(caminhoCompras);
            gerenciaVenda.carregarVendasCSV(caminhoVendas);

            // Notifica o listener sobre o sucesso
            if (listener != null) {
                listener.onCSVsCarregadosComSucesso();
            }

            // Fecha a janela de formulário
            dispose();

        } catch (Exception ex) {
            // Em caso de erro, exibe uma mensagem e imprime o stack trace para depuração
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar arquivos: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
package service;
import model.Cliente;
import io.Leitura;
import io.Escrita;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import model.Produto;
import model.Venda;
import model.VendaAVista;
import model.VendaFiado;

/**
 * Classe responsável por gerenciar as operações relacionadas a vendas,
 * incluindo registro de vendas à vista e a fiado, cálculo de receita e lucro
 * por produto ou meio de pagamento, além do controle do total a receber por cliente.
 *
 * Utiliza listas em memória para armazenar as vendas, além de fazer leituras e escritas em arquivos CSV.
 *
 * @author Emily
 */
@SuppressWarnings("FieldMayBeFinal")
public class GerenciaVenda {
    /**
     * Lista que armazena as vendas em memória.
     */
    private List<Venda> vendas;

    /**
     * Nome do arquivo CSV onde as vendas são persistidas.
     */
    private String ARQUIVO_VENDA;

    /**
     * Responsável por ler dados de arquivos.
     */
    private Leitura leitorCSV;

    /**
     * Responsável por escrever dados em arquivos.
     */
    private Escrita escritorCSV;

    /**
     * Gerenciador de produtos utilizado para buscar informações de produtos.
     */
    private GerenciaProduto gp;

    /**
     * Gerenciador de clientes utilizado para buscar informações de clientes.
     */
    private GerenciaCliente gc;

    /**
     * Construtor da classe GerenciaVenda.
     *
     * @param gerenciaProd instância de GerenciaProduto
     * @param gerenciaCliente instância de GerenciaCliente
     */
    public GerenciaVenda(GerenciaProduto gerenciaProd, GerenciaCliente gerenciaCliente, String ARQUIVO_VENDA) {
        vendas = new ArrayList<>();
        leitorCSV = new Leitura();
        escritorCSV = new Escrita();
        this.gp = gerenciaProd;
        this.gc = gerenciaCliente;
        this.ARQUIVO_VENDA = ARQUIVO_VENDA;
    }
    
    /**
    * Carrega as vendas a partir de um arquivo CSV e as adiciona à lista interna de vendas.
    * <p>
    * O método lê cada linha do arquivo CSV usando a classe {@link Leitura}, extrai os campos
    * correspondentes e cria objetos de {@link VendaFiado} ou {@link VendaAVista} dependendo
    * do tipo de pagamento indicado no arquivo.
    * </p>
    * 
    * <p>
    * Estrutura esperada do CSV:
    * <ul>
    *   <li>Para vendas fiado (meioPagamento = 'F'): idCliente, dataVenda, idProduto, quantidade, meioPagamento</li>
    *   <li>Para vendas à vista (meioPagamento diferente de 'F'): campoCliente vazio ou irrelevante, dataVenda, idProduto, quantidade, meioPagamento</li>
    * </ul>
    *
    * <p>
    * A função converte os campos numéricos usando {@code Integer.parseInt} e o campo do
    * meio de pagamento usando {@code charAt(0)}. Cada venda criada é adicionada à lista
    * interna {@code vendas}.
    *
    * @param caminhoArquivo O caminho completo do arquivo CSV que contém os dados das vendas.
    */
    public void carregarVendasCSV(String caminhoArquivo) {
        List<String[]> linhas = leitorCSV.lerArquivo(caminhoArquivo);
       
        for (String[] campos : linhas) {
            String campoCliente = campos[0];
            String dataVenda = campos[1];
            int idProduto = Integer.parseInt(campos[2]);
            int quantidade = Integer.parseInt(campos[3]);
            char meioPagamento = campos[4].charAt(0);

            if (meioPagamento == 'F') {
                // venda fiado → cliente identificado
                int idCliente = Integer.parseInt(campoCliente);
                Venda v = new VendaFiado(idCliente, dataVenda, idProduto, quantidade, meioPagamento);
                vendas.add(v);
            } else {
                // venda à vista → cliente vazio
                Venda v = new VendaAVista(dataVenda, idProduto, quantidade, meioPagamento);
                vendas.add(v);
            }
        }
        this.ARQUIVO_VENDA = caminhoArquivo;
    }

        
   /**
    * Registra uma nova venda no sistema de forma interativa via terminal.
    * <p>
    * O método guia o usuário pelo processo de registro de uma venda:
    * <ul>
    *   <li>Lista os produtos disponíveis e solicita a seleção de um ID válido.</li>
    *   <li>Solicita a quantidade a ser vendida, validando se há estoque suficiente.</li>
    *   <li>Pede a data da venda.</li>
    *   <li>Solicita o meio de pagamento, aceitando apenas os seguintes caracteres:
    *       <ul>
    *           <li>`$` - Dinheiro</li>
    *           <li>`X` - Cheque</li>
    *           <li>`D` - Cartão de Débito</li>
    *           <li>`C` - Cartão de Crédito</li>
    *           <li>`T` - Ticket Alimentação</li>
    *           <li>`F` - Fiado</li>
    *       </ul>
    *   </li>
    *   <li>Se o pagamento for <b>Fiado</b>, também é solicitado o ID de um cliente válido.</li>
    * </ul>
    * Após as validações, o estoque do produto é atualizado e uma instância de {@link VendaAVista} 
    * ou {@link VendaFiado} é criada e adicionada à lista de vendas.
    * 
    * @param sc Scanner utilizado para capturar a entrada do usuário no terminal.
    * 
    * @throws NullPointerException se, em algum ponto, o produto selecionado não for encontrado 
    *         (tratado internamente com {@link java.util.Objects#requireNonNull(Object)}).
    */
    public void registrarVenda(Scanner sc){
        int idProduto;
        int quantidade;
        Produto produto;
        char MeioPagamento;
        
        System.out.println("Produtos que podem ser vendidos");
        gp.listarProdutos();
        System.out.println();

        while (true) {
        System.out.println("Digite o ID do Produto que foi vendido: ");
        idProduto = sc.nextInt();
        sc.nextLine();
        produto = gp.buscarProduto(idProduto);
        if (produto == null) {
            System.out.println("Produto não encontrado. Digite Novamente!");
        } else {
            break;
            }
        }

        produto = Objects.requireNonNull(produto); // garante para o compilador que o produto nunca será nulo

        while (true) {
            System.out.println("Digite a quantidade: ");
            quantidade = sc.nextInt();
            sc.nextLine();
            if (produto.getEstoqueAtual() < quantidade) {
                System.out.println("Não há estoque para essa compra. O estoque atual desse produto é " 
                    + produto.getEstoqueAtual() + "\nTente Novamente!");
            } else {
                break;
            }
        }
        
        System.out.println("Digite a Data da Venda: ");
        String DataVenda = sc.nextLine();
        

        while(true) {
            System.out.println("""
                               * `$` Dinheiro
                               * `X` Cheque
                               * `D` Cartão de Débito
                               * `C` Cartão de Crédito
                               * `T` Ticket Alimentação
                               * `F` Fiado

                               Digite o meio de Pagamento:
                               """);

            String entrada = sc.nextLine().trim(); // remove espaços extras
            if (entrada.isEmpty()) {
                System.out.println("Você não digitou nada. Tente novamente!");
                continue;
            }

            MeioPagamento = entrada.charAt(0); // pega o primeiro caractere

            // verifica se é um dos caracteres válidos
            if (MeioPagamento == 'D' || MeioPagamento == 'C' || MeioPagamento == 'T' ||
                MeioPagamento == 'F' || MeioPagamento == 'X' || MeioPagamento == '$') {
                break; // entrada válida
            } else {
                System.out.println("Meio de pagamento inválido. Digite um dos caracteres válidos!");
            }
        }
        
        if(MeioPagamento == 'F'){
            int idCliente;
            
            while(true){
                System.out.println("Digite o ID do Cliente: ");
                idCliente = sc.nextInt();
                Cliente cliente = gc.buscarCliente(idCliente);
                if(cliente == null){
                    System.out.println("Cliente não encontrado! Tente Novamente");
                }
                else{
                    break;
                }
            }
            
            produto.setEstoqueAtual(produto.getEstoqueAtual() - quantidade);
            
            Venda v = new VendaFiado(idCliente, DataVenda, idProduto, quantidade, MeioPagamento);
            vendas.add(v);
            String[] linha = new String[]{
            String.valueOf(v.getIdVenda()),
            v.getDataVenda(),
            String.valueOf(v.getIdProduto()),
            String.valueOf(v.getQuantidade()),
            Character.toString(v.getMeioPagamento())
        };
            escritorCSV.escreverLinha(ARQUIVO_VENDA, linha);
            gp.reescreverProdutosCSV();
        }
        
        else{
            produto.setEstoqueAtual(produto.getEstoqueAtual() - quantidade);
            
            Venda v = new VendaAVista(DataVenda, idProduto, quantidade, MeioPagamento);
            vendas.add(v);
            
            String[] linha = new String[]{
            String.valueOf(v.getIdVenda()),
            v.getDataVenda(),
            String.valueOf(v.getIdProduto()),
            String.valueOf(v.getQuantidade()),
            Character.toString(v.getMeioPagamento())
        };
            escritorCSV.escreverLinha(ARQUIVO_VENDA, linha);
            gp.reescreverProdutosCSV();
            
        }

        
    }
    
       /**
    * Registra uma nova venda no sistema de forma interativa via terminal.
    * <p>
    * O método guia o usuário pelo processo de registro de uma venda:
    * <ul>
    *   <li>Lista os produtos disponíveis e solicita a seleção de um ID válido.</li>
    *   <li>Solicita a quantidade a ser vendida, validando se há estoque suficiente.</li>
    *   <li>Pede a data da venda.</li>
    *   <li>Solicita o meio de pagamento, aceitando apenas os seguintes caracteres:
    *       <ul>
    *           <li>`$` - Dinheiro</li>
    *           <li>`X` - Cheque</li>
    *           <li>`D` - Cartão de Débito</li>
    *           <li>`C` - Cartão de Crédito</li>
    *           <li>`T` - Ticket Alimentação</li>
    *           <li>`F` - Fiado</li>
    *       </ul>
    *   </li>
    *   <li>Se o pagamento for <b>Fiado</b>, também é solicitado o ID de um cliente válido.</li>
    * </ul>
    * Após as validações, o estoque do produto é atualizado e uma instância de {@link VendaAVista} 
    * ou {@link VendaFiado} é criada e adicionada à lista de vendas.
    * 
     * @param v
     * @param produto
    * 
    * @throws NullPointerException se, em algum ponto, o produto selecionado não for encontrado 
    *         (tratado internamente com {@link java.util.Objects#requireNonNull(Object)}).
    */
    public void registrarVenda(Venda v, Produto produto){
            
            produto.setEstoqueAtual(produto.getEstoqueAtual() - v.getQuantidade());

            vendas.add(v);
            String[] linha = new String[]{
            String.valueOf(v.getIdVenda()),
            v.getDataVenda(),
            String.valueOf(v.getIdProduto()),
            String.valueOf(v.getQuantidade()),
            Character.toString(v.getMeioPagamento())
        };
            escritorCSV.escreverLinha(ARQUIVO_VENDA, linha);
            gp.reescreverProdutosCSV();
        }
    /**
     * Lista todas as vendas registradas.
     */
    public void listarVendas(){
        if(!vendas.isEmpty()){
            for(Venda v : vendas){
                System.out.println(v.toString());
            }
        }
        else{
            System.out.println("Não há vendas registradas no sistema");
        }
    }
    
    /**
     * Busca uma venda específica pelo código.
     *
     * @param idVenda Código da venda
     * @return A venda correspondente ou null, se não encontrada
     */
    public Venda buscarVenda(int idVenda){
        if(!vendas.isEmpty()){
            for(Venda v : vendas){
                if(v.getIdVenda() == idVenda){
                    return v;
                }
            }
        }
        return null;
    }
    
    /**
    * Calcula o lucro total obtido com um pedido específico.
    * 
    * Este método considera a quantidade vendida e o lucro unitário de um produto.
    * Útil para análises de rentabilidade por item vendido.
    *
    * @param v A venda cujo lucro será calculado. A quantidade vendida é extraída deste objeto.
    * @param p O produto associado à venda. O lucro unitário é extraído deste objeto.
    * @return O valor total de lucro obtido com o pedido (lucro unitário × quantidade).
    */
    public BigDecimal lucroTotalDoPedido(Venda v, Produto p){
        BigDecimal quantidadeProd = new BigDecimal(v.getQuantidade());
        return p.getLucro().multiply(quantidadeProd);
    }
    
    /**
    * Calcula a receita bruta total de um pedido.
    *
    * Considera a quantidade vendida e o valor de venda unitário do produto.
    * Útil para gerar relatórios de faturamento, independentemente dos custos envolvidos.
    *
    * @param v A venda considerada. A quantidade vendida será usada no cálculo.
    * @param p O produto associado à venda. O valor de venda unitário será usado.
    * @return O total faturado com o pedido (valor de venda × quantidade).
    */
    public BigDecimal receitaTotalDoPedido(Venda v, Produto p){
        BigDecimal quantidadeProd = new BigDecimal(v.getQuantidade());
        return p.getValorDeVenda().multiply(quantidadeProd);
    }
    
    /**
    * Calcula a receita bruta total gerada por um produto específico.
    *
    * A receita é a soma do valor de venda (unitário) multiplicado pela quantidade vendida,
    * considerando todas as vendas registradas no sistema que contenham esse produto.
    *
    * @param idProd O ID do produto que será analisado.
    * @return A receita bruta total gerada por esse produto ou {@code null} se o produto não for encontrado
    *         ou se não houver vendas registradas.
    */
    public BigDecimal receitaPorProduto(int idProd){
        Produto produto =gp.buscarProduto(idProd);
        BigDecimal total = BigDecimal.ZERO;
        if(produto == null){
            System.out.println("Produto não encontrado");
            return null;
        }
        else if (!vendas.isEmpty()){
            for(Venda v : vendas){
                if(produto.getIdProduto() == v.getIdProduto()){
                    total = total.add(this.receitaTotalDoPedido(v, produto));
                }
            }
        }
        else{
            System.out.println("Ainda não há nehuma venda registrada no sistema!");
            return null;
        }
        return total;
    }
    
    /**
    * Calcula o lucro total obtido com um produto específico.
    *
    * O lucro considera o lucro unitário do produto multiplicado pela quantidade vendida
    * em todas as vendas registradas.
    *
    * @param idProd O ID do produto para o qual se deseja calcular o lucro total.
    * @return O valor total de lucro obtido com esse produto ou {@code null} se o produto não for encontrado
    *         ou se não houver vendas registradas.
    */
    public BigDecimal lucroPorProduto(int idProd){
        Produto produto =gp.buscarProduto(idProd);
        BigDecimal total = BigDecimal.ZERO;
        if(produto == null){
            System.out.println("Produto não encontrado");
            return null;
        }
        else if (!vendas.isEmpty()){
            for(Venda v : vendas){
                if(produto.getIdProduto() == v.getIdProduto()){
                   total = total.add(this.lucroTotalDoPedido(v, produto));
                }
            }
        }
        else{
            System.out.println("Ainda não há nehuma venda registrada no sistema!");
            return null;
        }
        return total;
    }
    
    /**
    * Calcula a receita total com base em um meio de pagamento específico.
    *
    * Filtra todas as vendas cujo meio de pagamento (ex: 'C' para cartão, 'D' para débito, 'F' para fiado)
    * seja igual ao informado, e soma o valor de venda total dos produtos vendidos nesse filtro.
    * 
    * Utiliza cache para evitar múltiplas buscas do mesmo produto em memória.
    *
    * @param meioPagamento O código do meio de pagamento (char) a ser filtrado.
    * @return O total de receita gerada através desse meio de pagamento ou {@code null} se não houver vendas.
    */
    public BigDecimal receitaPorMP(char meioPagamento){
        BigDecimal total = BigDecimal.ZERO;
        Map<Integer,Produto>  cacheProdutos = new HashMap<>(); //evitar buscas infinitas na memória
        
        
        if (!vendas.isEmpty()){
            
            for(Venda v : vendas){
                if(v.getMeioPagamento() == meioPagamento){
                    
                    int idProduto = v.getIdProduto();
                    
                    Produto produto = cacheProdutos.get(idProduto);
                    if(produto == null){
                        produto = gp.buscarProduto(idProduto);
                        
                        if(produto != null){
                            cacheProdutos.put(idProduto,produto);
                        }
                        else{
                            continue;
                        }
                    }
                    
                    total = total.add(this.receitaTotalDoPedido(v, produto));
                }
            }
            
            return total;
        }
         else{
            System.out.println("Ainda não há nehuma venda registrada no sistema!");
            return null;
        }
    }
    
    /**
    * Calcula o lucro total obtido através de um determinado meio de pagamento.
    *
    * Filtra todas as vendas que foram feitas usando o meio de pagamento informado,
    * e soma o lucro total (lucro unitário × quantidade vendida) de cada uma.
    * 
    * Utiliza cache para otimizar a busca dos produtos.
    *
    * @param meioPagamento O código do meio de pagamento (char) a ser considerado no filtro.
    * @return O lucro total associado a esse meio de pagamento ou {@code null} se não houver vendas.
    */
    public BigDecimal lucroPorMP(char meioPagamento){
        BigDecimal total = BigDecimal.ZERO;
        Map<Integer,Produto>  cacheProdutos = new HashMap<>(); //evitar buscas infinitas na memória
        
        if (!vendas.isEmpty()){
            
            for(Venda v : vendas){
                if(v.getMeioPagamento() == meioPagamento){
                    
                    int idProduto = v.getIdProduto();
                    
                    Produto produto = cacheProdutos.get(idProduto);
                    if(produto == null){
                        produto = gp.buscarProduto(idProduto);
                        
                        if(produto != null){
                            cacheProdutos.put(idProduto,produto);
                        }
                        else{
                            continue;
                        }
                    }
                    total = total.add(this.lucroTotalDoPedido(v, produto));
                }
            }
            
            return total;
        }
         else{
            System.out.println("Ainda não há nehuma venda registrada no sistema!");
            return null;
        }
    }
    
   /**
    * VERSÃO TERMINAL
    * Calcula o total a receber (em aberto) de um cliente específico.
    *
    * Considera apenas as vendas realizadas a prazo (fiado), filtrando as que pertencem ao cliente informado.
    * Soma o valor total das vendas fiadas (valor unitário × quantidade).
    *
    * @param idCliente O ID do cliente para o qual se deseja calcular o total em aberto.
    * @return O valor total a receber do cliente ou {@code null} se ele não for encontrado
    *         ou não possuir vendas fiadas.
    */
    public BigDecimal totalAReceberCliente(int idCliente) {
    Cliente cliente = gc.buscarCliente(idCliente);
    if (cliente == null) {
        System.out.println("Cliente não encontrado");
        return null;
    }

    BigDecimal total = BigDecimal.ZERO;

    for (Venda v : vendas) {
        if (v instanceof VendaFiado vf) {
            if (vf.getIdCliente() == idCliente) {
                Produto p = gp.buscarProduto(vf.getIdProduto());
                if (p != null) {
                    total = total.add(receitaTotalDoPedido(vf, p));
                }
            }
        }
    }

    return total;
    }
    
    /**
     * VERSÃO INTERFACE
    * Calcula o total a receber (em aberto) de um cliente específico.
    *
    * Considera apenas as vendas realizadas a prazo (fiado), filtrando as que pertencem ao cliente informado.
    * Soma o valor total das vendas fiadas (valor unitário × quantidade).
    *
     * @param cliente cliente para o qual se deseja calcular o total em aberto.
    * @return O valor total a receber do cliente ou {@code null} se ele não for encontrado
    *         ou não possuir vendas fiadas.
    */
    public BigDecimal totalAReceberCliente(Cliente cliente) {
        
    BigDecimal total = BigDecimal.ZERO;

    for (Venda v : vendas) {
        if (v instanceof VendaFiado vf) {
            if (vf.getIdCliente() == cliente.getId()) {
                Produto p = gp.buscarProduto(vf.getIdProduto());
                if (p != null) {
                    total = total.add(receitaTotalDoPedido(vf, p));
                }
            }
        }
    }

    return total;
    }
    
    /**
    * Retorna uma lista de todas as vendas que ainda estão em aberto (fiado).
    *
    * Este método é útil para relatórios de contas a receber, exibindo todas as transações
    * cujo meio de pagamento foi registrado como fiado (indicador: 'F').
    *
    * @return Uma lista contendo as vendas fiadas, ou uma lista vazia caso não existam.
    */
    public List<Venda> filtrarVendasAReceber(){
        List<Venda> vendasAreceber = new ArrayList<>();
        if(!vendas.isEmpty()){
            for(Venda v:vendas){
                if(v.getMeioPagamento() == 'F'){
                    vendasAreceber.add(v);
                }
            }
        }
        return vendasAreceber;
        } 

    // filtro da interface
    public List<Venda> filtrarMeioPagamento(char meio){
        List<Venda> filtro = new ArrayList<>();
        if(!vendas.isEmpty()){
            for(Venda v:vendas){
                if(v.getMeioPagamento() == meio){
                    filtro.add(v);
                }
            }
        }
        return filtro;
        } 
    
    //filtro da interface
    public List<Venda> filtrarProduto(int IdProduto){
        List<Venda> filtro = new ArrayList<>();
        if(!vendas.isEmpty()){
            for(Venda v:vendas){
                if(v.getIdProduto() == IdProduto){
                    filtro.add(v);
                }
            }
        }
        return filtro;
        }
    
    // Adicione este método na sua classe service.GerenciaVenda

    // Substitua a versão com streams por esta, na sua classe GerenciaVenda

    /**
     * Retorna uma lista de vendas filtrada por múltiplos critérios, usando loops tradicionais.
     *
     * @param filtroProduto O objeto selecionado no JComboBox de produtos. Pode ser um Produto ou uma String "Todos...".
     * @param filtroPagamento A String selecionada no JComboBox de pagamento (ex: "Dinheiro", "Fiado", "Todos...").
     * @return Uma lista de Venda contendo apenas os itens que passam pelos filtros.
     */
    public List<Venda> getVendasFiltradas(Object filtroProduto, String filtroPagamento) {
        List<Venda> listaIntermediaria;

        // ETAPA 1: Aplica o filtro de Produto, se necessário
        if (filtroProduto instanceof Produto produtoSelecionado) {
            // Usa a sua função já existente para o primeiro filtro
            listaIntermediaria = this.filtrarProduto(produtoSelecionado.getIdProduto());
        } else {
            // Se nenhum produto foi selecionado, começa com a lista completa
            listaIntermediaria = new ArrayList<>(this.vendas);
        }

        // ETAPA 2: Aplica o filtro de Meio de Pagamento sobre a lista da etapa 1
        if (filtroPagamento != null && !"Todos os Pagamentos".equals(filtroPagamento)) {
            char codigoPagamento = mapearPagamentoParaCodigo(filtroPagamento);
            List<Venda> resultadoFinal = new ArrayList<>();

            // Usa a lógica da sua função filtrarMeioPagamento aqui
            for (Venda v : listaIntermediaria) {
                if (v.getMeioPagamento() == codigoPagamento) {
                    resultadoFinal.add(v);
                }
            }
            return resultadoFinal; // Retorna a lista duplamente filtrada
        }

        // Se nenhum filtro de pagamento foi aplicado, retorna a lista da etapa 1
        return listaIntermediaria;
    }

    // Garanta que este método auxiliar continue na sua classe
    private char mapearPagamentoParaCodigo(String pagamento) {
        switch (pagamento) {
            case "Dinheiro": return '$';
            case "Cheque": return 'X';
            case "Cartão de Débito": return 'D';
            case "Cartão de Crédito": return 'C';
            case "Ticket Alimentação": return 'T';
            case "Fiado": return 'F';
            default: return ' ';
        }
    }

    private void reescreverVendasCSV() {
    List<String[]> dados = new ArrayList<>();
        for (Venda v : vendas) {
            if (v instanceof VendaAVista va) {
                dados.add(new String[]{
                    "",
                    va.getDataVenda(),
                    String.valueOf(va.getIdProduto()),
                    String.valueOf(va.getQuantidade()),
                    String.valueOf(va.getMeioPagamento()) 
                });
            } else if (v instanceof VendaFiado vf) {
                dados.add(new String[]{
                    String.valueOf(vf.getIdCliente()),
                    vf.getDataVenda(),
                    String.valueOf(vf.getIdProduto()),

                    String.valueOf(vf.getQuantidade()),
                    "F"  

                });
            }
        }
        escritorCSV.escreverVendas(ARQUIVO_VENDA, dados);
}
    
    public List<Venda> getVendas() {
        return vendas;
    }


}
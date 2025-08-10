package service;
import model.Cliente;
import io.Leitura;
import io.Escrita;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final String ARQUIVO_VENDA = "vendas.csv";

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
     */
    public GerenciaVenda(GerenciaProduto gerenciaProd, GerenciaCliente gerenciaCliente) {
        vendas = new ArrayList<>();
        leitorCSV = new Leitura();
        escritorCSV = new Escrita();
        this.gp = gerenciaProd;
        this.gc = gerenciaCliente;
    }
    

    
    //public void registrarVenda(Venda v){
        
    /**
     * Registra uma venda à vista no sistema.
     *
     * @param idVenda ID da venda
     * @param DataVenda Data da venda
     * @param idProduto ID do produto vendido
     * @param quantidade Quantidade vendida
     * @param MeioPagamento Meio de pagamento utilizado
     */
    public void registrarVenda(int idVenda, String DataVenda, int idProduto, int quantidade, char MeioPagamento){
        Produto produto =gp.buscarProduto(idProduto);
        if(produto == null){
            System.out.println("Produto não encontrado");
        }
        else if (produto.getEstoqueAtual() < quantidade){
            System.out.println("Não há estoque para essa compra");
        }
        else{
            produto.setEstoqueAtual(produto.getEstoqueAtual() - quantidade);
            // gerenciaProdutos.salvarProdutosAtualizados(); //criar esse método - package IO!!!
            //escritorCSV.atualizarArquivoVenda(,v); 
            Venda v = new VendaAVista(idVenda, DataVenda, idProduto, quantidade, MeioPagamento);
            vendas.add(v);
        } 

    }
    
    /**
     * Registra uma venda fiado (cliente identificado).
     *
     * @param idCliente ID do cliente
     * @param idVenda ID da venda
     * @param DataVenda Data da venda
     * @param idProduto ID do produto vendido
     * @param quantidade Quantidade vendida
     * @param MeioPagamento Meio de pagamento utilizado
     */
    public void registrarVenda( int idCliente, int idVenda, String DataVenda, int idProduto, int quantidade, char MeioPagamento){
        Produto produto =gp.buscarProduto(idProduto);
        if(produto == null){
            System.out.println("Produto não encontrado");
        }
        else if (produto.getEstoqueAtual() < quantidade){
            System.out.println("Não há estoque para essa compra");
        }
        else{
            produto.setEstoqueAtual(produto.getEstoqueAtual() - quantidade);
            // gerenciaProdutos.salvarProdutosAtualizados(); //criar esse método - package IO!!!
            //escritorCSV.atualizarArquivoVenda(,v); 
            Venda v = new VendaFiado(idCliente, idVenda, DataVenda, idProduto, quantidade, MeioPagamento);
            vendas.add(v);
        } 
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
    }
    
    /**
     * Busca uma venda específica pelo código.
     *
     * @param codigoVenda Código da venda
     * @return A venda correspondente ou null, se não encontrada
     */
    public Venda buscarVenda(int codigoVenda){
        if(!vendas.isEmpty()){
            for(Venda v : vendas){
                if(v.getIdVenda() == codigoVenda){
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
                    total.add(this.receitaTotalDoPedido(v, produto));
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
                    total.add(this.lucroTotalDoPedido(v, produto));
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
                    
                    total.add(this.receitaTotalDoPedido(v, produto));
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
                    total.add(this.lucroTotalDoPedido(v, produto));
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
    }

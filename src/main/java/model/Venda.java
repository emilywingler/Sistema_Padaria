package model;

/**
 * Representa uma venda realizada no sistema.
 * 
 * Contém informações sobre o produto vendido, quantidade, data da venda e meio de pagamento.
 * Também mantém uma contagem total de vendas realizadas.
 * 
 * Esta classe é usada para registrar transações e pode ser estendida ou manipulada
 * para gerar relatórios de receita, lucro, ou análise de vendas.
 * 
 * @author emily
 */
public class Venda {

    /** Identificador único da venda */
    private final int idVenda;

    /** Data em que a venda foi realizada */
    private String DataVenda;

    /** Identificador do produto vendido */
    private int idProduto;

    /** Quantidade de unidades vendidas */
    private int quantidade;

    /** Meio de pagamento utilizado na venda (Ex: 'D' = Dinheiro, 'C' = Cartão, etc.) */
    private char MeioPagamento;

    /** Contador estático que registra o número total de vendas */
    private static int totalVendas = 0;

    /**
     * Construtor da classe {@code Venda}.
     *Aqui cada venda tem o seu id único atribuido pela variável estática total vendas
     * 
     * @param DataVenda      Data da venda (formato string, ex: "2025-08-07")
     * @param idProduto      ID do produto vendido
     * @param quantidade     Quantidade de unidades vendidas
     * @param MeioPagamento  Código representando o meio de pagamento
     */
    public Venda(String DataVenda, int idProduto, int quantidade, char MeioPagamento) {
        this.idVenda = totalVendas;
        this.DataVenda = DataVenda;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.MeioPagamento = MeioPagamento;
        totalVendas++;
    }

    /**
     * Retorna o ID da venda.
     *
     * @return o identificador único da venda
     */
    public int getIdVenda() {
        return idVenda;
    }

    /**
     * Retorna a data da venda.
     *
     * @return a data em que a venda foi registrada
     */
    public String getDataVenda() {
        return DataVenda;
    }

    /**
     * Retorna o ID do produto vendido.
     *
     * @return o identificador do produto
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * Retorna a quantidade de produtos vendidos.
     *
     * @return o número de unidades vendidas
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Retorna o meio de pagamento utilizado na venda.
     *
     * @return um caractere representando o tipo de pagamento
     */
    public char getMeioPagamento() {
        return MeioPagamento;
    }

    /**
     * Retorna o número total de vendas registradas no sistema.
     *
     * @return a quantidade total de objetos {@code Venda} instanciados
     */
    public static int getTotalVendas() {
        return totalVendas;
    }

    /**
     * Retorna uma representação textual da venda.
     *
     * @return uma {@code String} contendo os dados da venda
     */
    @Override
    public String toString() {
        return "idVenda: " + idVenda +
               ", DataVenda: " + DataVenda +
               ", idProduto: " + idProduto +
               ", quantidade: " + quantidade +
               ", MeioPagamento: " + MeioPagamento;
    }
}

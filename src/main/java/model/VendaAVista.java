package model;

/**
 * Representa uma venda realizada à vista, ou seja, sem parcelamento ou crédito.
 * 
 * Esta classe estende {@link Venda} e herda todas as suas propriedades e comportamentos.
 * É utilizada para distinguir logicamente esse tipo de transação de outros, 
 * como vendas a prazo ou parceladas.
 * 
 * A criação de objetos desta classe deve ser feita informando todos os dados da venda,
 * incluindo o identificador, data, produto, quantidade e meio de pagamento.
 * 
 * @author emily
 * @see Venda
 */
public class VendaAVista extends Venda {

    /**
     * Construtor que inicializa uma venda à vista com os dados fornecidos.
     * 
     * @param DataVenda      data em que a venda foi realizada
     * @param idProduto      identificador do produto vendido
     * @param quantidade     quantidade de produtos vendidos
     * @param MeioPagamento  meio de pagamento utilizado (ex: D para dinheiro, C para cartão)
     */
    public VendaAVista(String DataVenda, int idProduto, int quantidade, char MeioPagamento) {
        super(DataVenda, idProduto, quantidade, MeioPagamento);
    }
}

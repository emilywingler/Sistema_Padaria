package model;

/**
 * Representa uma venda fiado (a prazo), estendendo a classe {@code Venda}.
 * Armazena o ID do cliente responsável pela compra.
 * 
 * @author emily
 */
public class VendaFiado extends Venda {
    
    /** ID do cliente que realizou a compra fiado. */
    private final int idCliente;

    /**
     * Construtor da classe {@code VendaFiado}.
     *
     * @param idCliente      o ID do cliente
     * @param DataVenda      a data da venda
     * @param idProduto      o ID do produto vendido
     * @param quantidade     a quantidade vendida
     * @param MeioPagamento  o meio de pagamento utilizado
     */
    public VendaFiado(int idCliente, String DataVenda, int idProduto, int quantidade, char MeioPagamento) {
        super(DataVenda, idProduto, quantidade, MeioPagamento);
        this.idCliente = idCliente;
    }

    /**
     * Retorna o ID do cliente.
     *
     * @return o ID do cliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Retorna uma representação em {@code String} da venda fiado,
     * incluindo os dados da superclasse e o ID do cliente.
     *
     * @return representação textual da venda fiado
     */
    @Override
    public String toString() {
        return "idCliente: " + idCliente +", " +super.toString();
    }
}

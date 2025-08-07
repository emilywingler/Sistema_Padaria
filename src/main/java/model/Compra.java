package model;

/**
 * Representa uma compra realizada junto a um fornecedor, contendo informações
 * sobre o produto adquirido, quantidade e data da transação.
 * 
 * Cada instância de {@code Compra} é identificada por um ID único e mantém
 * os dados essenciais para controle de estoque e histórico de compras.
 * 
 * @author emily
 */
public class Compra {
    /** Identificador único da compra */
    private final int idCompra;

    /** Identificador do fornecedor responsável pela venda */
    private int idFornecedor;

    /** Data em que a compra foi realizada (formato: dd/MM/yyyy ou similar) */
    private String DataCompra;

    /** Identificador do produto adquirido */
    private int idProduto;

    /** Quantidade adquirida do produto */
    private int quantidade;

    /**
     * Construtor da classe {@code Compra}.
     *
     * @param idCompra      o ID único da compra
     * @param idFornecedor  o ID do fornecedor que realizou a venda
     * @param DataCompra    a data da compra
     * @param idProduto     o ID do produto comprado
     * @param quantidade    a quantidade adquirida do produto
     */
    public Compra(int idCompra, int idFornecedor, String DataCompra, int idProduto, int quantidade) {
        this.idCompra = idCompra;
        this.idFornecedor = idFornecedor;
        this.DataCompra = DataCompra;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    /**
     * Retorna o ID da compra.
     *
     * @return o ID da compra
     */
    public int getIdCompra() {
        return idCompra;
    }

    /**
     * Retorna o ID do fornecedor associado a esta compra.
     *
     * @return o ID do fornecedor
     */
    public int getIdFornecedor() {
        return idFornecedor;
    }

    /**
     * Retorna a data da compra.
     *
     * @return a data da compra como {@code String}
     */
    public String getDataCompra() {
        return DataCompra;
    }

    /**
     * Retorna o ID do produto adquirido.
     *
     * @return o ID do produto
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * Retorna a quantidade de produtos adquiridos.
     *
     * @return a quantidade comprada
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Retorna uma representação textual do objeto {@code Compra}.
     *
     * @return uma string com os dados principais da compra
     */
    @Override
    public String toString() {
        return "idCompra: " + idCompra + ", idFornecedor: " + idFornecedor
             + ", DataCompra: " + DataCompra + ", idProduto: " + idProduto
             + ", quantidade: " + quantidade;
    }
}


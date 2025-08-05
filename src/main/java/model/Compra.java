
package model;

/**
 *
 * @author emily
 */
public class Compra {
    private final int idCompra;
    private int idFornecedor;
    private String DataCompra;
    private int idProduto;
    private int quantidade;

    public Compra(int idCompra, int idFornecedor, String DataCompra, int idProduto, int quantidade) {
        this.idCompra = idCompra;
        this.idFornecedor = idFornecedor;
        this.DataCompra = DataCompra;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public String getDataCompra() {
        return DataCompra;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        return "idCompra: " + idCompra + ", idFornecedor: " + idFornecedor + ", DataCompra: " + DataCompra + ", idProduto: " + idProduto + ", quantidade: " + quantidade;
    }
    
    
    
    
}

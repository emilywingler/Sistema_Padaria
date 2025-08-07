
package model;

/**
 *
 * @author emily
 */
public class VendaFiado extends Venda {
    private int idCliente;

    public VendaFiado(int idCliente, int idVenda, String DataVenda, int idProduto, int quantidade, char MeioPagamento) {
        super(idVenda, DataVenda, idProduto, quantidade, MeioPagamento);
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    @Override
    public String toString() {
        return super.toString() + ", idCliente: " + idCliente;
    }
    
    
}

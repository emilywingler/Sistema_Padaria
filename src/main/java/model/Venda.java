package model;
//import java.math.BigDecimal;
//import java.math.RoundingMode;

/**
 *
 * @author emily
 */
public class Venda {
    private final int idVenda;
    private String DataVenda;
    private int idProduto;
    private int quantidade;
    private char MeioPagamento;
    private static int totalVendas = 0;

    public Venda(int idVenda, String DataVenda, int idProduto, int quantidade, char MeioPagamento) {
        this.idVenda = idVenda;
        this.DataVenda = DataVenda;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.MeioPagamento = MeioPagamento;
        totalVendas++;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public String getDataVenda() {
        return DataVenda;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public char getMeioPagamento() {
        return MeioPagamento;
    }

    public static int getTotalVendas() {
        return totalVendas;
    }

    
    
}

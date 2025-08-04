
package model;
import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 *
 * @author emily
 */
public class Produto {
    private final int idProduto;
    private String descricao;
    private int minEstoque;
    private int estoqueAtual;
    private BigDecimal custo;
    private int percentualLucro;

    public Produto(int idProduto, String descricao, int minEstoque, int estoqueAtual, BigDecimal custo, int percentualLucro) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.minEstoque = minEstoque;
        this.estoqueAtual = estoqueAtual;
        this.custo = custo;
        this.percentualLucro = percentualLucro;
    }
    
    public int getIdProduto(){
        return idProduto;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getMinEstoque() {
        return minEstoque;
    }

    public void setMinEstoque(int minEstoque) {
        this.minEstoque = minEstoque;
    }

    public int getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(int estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }

    public int getPercentualLucro() {
        return percentualLucro;
    }

    public void setPercentualLucro(int percentualLucro) {
        this.percentualLucro = percentualLucro;
    }
    
    public BigDecimal getValorDeVenda() {
        BigDecimal cem = new BigDecimal("100");
        BigDecimal percentual = new BigDecimal(percentualLucro);
        BigDecimal lucro = custo.multiply(percentual).divide(cem, 2, RoundingMode.HALF_UP);
        return custo.add(lucro).setScale(2, RoundingMode.HALF_UP);
    }
    
    @Override
    public String toString() {
        return "ID: " + idProduto +
               " | Descrição: " + descricao +
               " | Estoque Atual: " + estoqueAtual +
               " | Estoque Mínimo: " + minEstoque +
               " | Custo: R$" + custo +
               " | Lucro: " + percentualLucro + "%" +
               " | Venda: R$" + getValorDeVenda();
    }
  
}

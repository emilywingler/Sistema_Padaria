
package model;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Representa um produto com suas informações básicas e métodos para
 * cálculo de preço de venda com base no custo e percentual de lucro.
 * 
 * Essa classe é utilizada para controle de estoque e geração de vendas,
 * oferecendo métodos utilitários para retornar lucro e preço final de venda.
 * 
 * @author emily
 */
public class Produto {
    
    /** Identificador único do produto. */
    private final int idProduto;

    /** Descrição detalhada do produto. */
    private String descricao;

    /** Quantidade mínima de estoque recomendada. */
    private int minEstoque;

    /** Quantidade atual em estoque. */
    private int estoqueAtual;

    /** Custo de aquisição ou produção do produto. */
    private BigDecimal custo;

    /** Percentual de lucro aplicado sobre o custo. */
    private int percentualLucro;

    /**
     * Construtor completo para a classe Produto.
     * 
     * @param idProduto        Identificador do produto
     * @param descricao        Descrição do produto
     * @param minEstoque       Estoque mínimo recomendado
     * @param estoqueAtual     Estoque atual
     * @param custo            Custo de aquisição
     * @param percentualLucro  Lucro aplicado sobre o custo
     */
    public Produto(int idProduto, String descricao, int minEstoque, int estoqueAtual, BigDecimal custo, int percentualLucro) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.minEstoque = minEstoque;
        this.estoqueAtual = estoqueAtual;
        this.custo = custo;
        this.percentualLucro = percentualLucro;
    }
    
    /**
 * Retorna o identificador único do produto.
 * @return id do produto.
 */
public int getIdProduto() {
    return idProduto;
}

/**
 * Retorna a descrição atual do produto.
 * @return descrição do produto.
 */
public String getDescricao() {
    return descricao;
}

/**
 * Define a descrição do produto.
 * @param descricao nova descrição do produto.
 */
public void setDescricao(String descricao) {
    this.descricao = descricao;
}

/**
 * Retorna o estoque mínimo recomendado para o produto.
 * @return estoque mínimo.
 */
public int getMinEstoque() {
    return minEstoque;
}

/**
 * Define o estoque mínimo recomendado para o produto.
 * @param minEstoque novo estoque mínimo.
 */
public void setMinEstoque(int minEstoque) {
    this.minEstoque = minEstoque;
}

/**
 * Retorna a quantidade atual em estoque do produto.
 * @return estoque atual.
 */
public int getEstoqueAtual() {
    return estoqueAtual;
}

/**
 * Define a quantidade atual em estoque do produto.
 * @param estoqueAtual nova quantidade em estoque.
 */
public void setEstoqueAtual(int estoqueAtual) {
    this.estoqueAtual = estoqueAtual;
}

/**
 * Retorna o custo do produto.
 * @return custo do produto.
 */
public BigDecimal getCusto() {
    return custo;
}

/**
 * Define o custo do produto.
 * @param custo novo custo do produto.
 */
public void setCusto(BigDecimal custo) {
    this.custo = custo;
}

/**
 * Retorna o percentual de lucro aplicado ao produto.
 * @return percentual de lucro.
 */
public int getPercentualLucro() {
    return percentualLucro;
}

/**
 * Define o percentual de lucro aplicado ao produto.
 * @param percentualLucro novo percentual de lucro.
 */
public void setPercentualLucro(int percentualLucro) {
    this.percentualLucro = percentualLucro;
}

    /**
     * Calcula o valor de venda do produto, somando o custo com o lucro.
     * 
     * @return Valor de venda com duas casas decimais, usando arredondamento HALF_UP
     */
    public BigDecimal getValorDeVenda() {
        return custo.add(this.getLucro()).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calcula o valor absoluto do lucro com base no percentual informado.
     * 
     * @return Lucro em reais, arredondado para duas casas decimais
     */
    public BigDecimal getLucro(){
        BigDecimal cem = new BigDecimal("100");
        BigDecimal percentual = new BigDecimal(percentualLucro);
        BigDecimal lucro = custo.multiply(percentual).divide(cem, 2, RoundingMode.HALF_UP);
        return lucro.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Retorna uma representação textual completa do produto, incluindo o valor de venda.
     * 
     * @return String com as informações do produto formatadas
     */
    @Override
    public String toString() {
        return  "idProduto: " + idProduto + 
                ", descricao: " + descricao + 
                ", minEstoque: " + minEstoque + 
                ", estoqueAtual: " + estoqueAtual + 
                ", custo: " + custo.toString() +
                ", percentualLucro: " + percentualLucro + 
                ", valor venda" +  this.getValorDeVenda().toString();
    }
    
}
    

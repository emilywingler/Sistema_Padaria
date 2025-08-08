
package service;

import io.Escrita;
import io.Leitura;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.Compra;
import model.Produto;
import model.Fornecedor;

/**
 * Registar compra
 * Listar Compras
 * Valor da Compra
 * Buscar Compra
 * Listar Total a pagar por fornecedor
 * @author emily
 */
public class GerenciaCompra {
    private List<Compra> compras;
    private final String ARQUIVO_VENDA = "compras.csv";
    private Leitura leitorCSV;
    private Escrita escritorCSV;
    private GerenciaProduto gp;
    private GerenciaFornecedor gf;
    
    public GerenciaCompra(GerenciaProduto gerenciarProd){
        compras = new ArrayList<>();
        leitorCSV = new Leitura();
        escritorCSV = new Escrita();
        this.gp = gerenciarProd;
    }
    
    public void registrarCompra(int idCompra, int idFornecedor, String DataCompra, int idProduto, int quantidade){
        Produto produto = gp.buscarProduto(idProduto);
        if(produto == null){
            System.out.println("Produto não encontrado");
        }
        else{
            produto.setEstoqueAtual(produto.getEstoqueAtual() - quantidade);
            Compra c = new Compra(idCompra, idFornecedor,DataCompra,idProduto,quantidade);
            compras.add(c);
        }
    }
    
    public void listarVendas(){
        if(!compras.isEmpty()){
            for(Compra c : compras){
                System.out.println(c.toString());
            }
        }
        else{
            System.out.println("Não há vendas registradas no sistema");
        }
    }
    
    public Compra buscarCompra(int idCompra){
        if(!compras.isEmpty()){
            for(Compra c : compras){
                if(c.getIdCompra() == idCompra){
                    return c;
                }
            }
        }
        return null;
    }
    
    public BigDecimal valorTotalDaCompra(Compra c ,Produto p){
        BigDecimal quantidadeProd = new BigDecimal(c.getQuantidade());
        return p.getCusto().multiply(quantidadeProd);
    }
    
    public BigDecimal totalAPagarPorFornecedor(int idFornecedor){
        Fornecedor fornecedor = gf.buscarFornecedor(idFornecedor);
        if(fornecedor == null){
            System.out.println("Fornecedor não encontrado!");
            return null;
        }
        else{
            BigDecimal total = BigDecimal.ZERO;
            for(Compra c: compras){
                if(c.getIdFornecedor() == idFornecedor){
                    Produto produto = gp.buscarProduto(c.getIdProduto());
                    total = total.add(this.valorTotalDaCompra(c, produto));
                }
            }
            return total;
        }
    }
}

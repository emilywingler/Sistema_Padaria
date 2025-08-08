
package service;

import io.Escrita;
import io.Leitura;
import java.util.ArrayList;
import java.util.List;
import model.Compra;
import model.Produto;
import model.Venda;

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
}

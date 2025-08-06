/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import model.Cliente;
import model.ClienteFisico;
import model.ClienteJuridico;
import model.Compra;
import io.Leitura;
import io.Escrita;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Produto;
import model.Venda;
import model.VendaAVista;
import model.VendaFiado;

/**
 *
 * @author emily
 * 
 * + registrarVenda() : void
+ listarVendas(): void
+ totalAReceberCliente(String codigo) : BigDecimal
+ receitaPorProduto(String codigoProd): BigDecimal
+ lucroPorProduto(String codigoProd): BigDecimal
+ receitaPorMP(char meioPagamento): BigDecimal
+ lucroPorMP(char meioPagamento): BigDecimal
+ filtrarVendasAReceber() : List<Venda>
* 
* 
* Relatórios gerados:
1-apagar.csv <nome>;<cnpj>;<pessoa contato>;<telefone>;<valor total a pagar>
2-areceber.csv <nome>;<tipo>;<cpf/cnpj>;<telefone>;<data>;<valor total a receber>
3-vendasprod.csv <código>;<descrição>;<receita bruta>;<lucro>
4-vendaspgto.csv <modo de pagamento>;<receita bruta>;<lucro>
5-estoque.csv <código>;<descrição>;<quantidade>;<observações>
Observação: "COMPRAR MAIS" se estoque < mínimo.
 */
public class GerenciaVenda {
    private List<Venda> vendas; //lista que armazena os objetos Venda em memoria
    private final String ARQUIVO_VENDA= "vendas.csv"; //nome do arquivo csv que os produtos sao salvos 
    private Leitura leitorCSV;//objeto responsavel por ler dados no csv
    private Escrita escritorCSV;
    private GerenciaProdutos gp;//objeto responsavel por escrever dados no csv
    
    public GerenciaVenda(GerenciaProdutos gerenciaProd) {
        vendas = new ArrayList<>();
        leitorCSV = new Leitura();
        escritorCSV = new Escrita();
        this.gp = gerenciaProd;
    }
    
    //VENDA A VISTA
    public void registrarVenda(int idVenda, String DataVenda, int idProduto, int quantidade, char MeioPagamento){
        Produto produto =gp.buscarProduto(idProduto);
        if(produto == null){
            System.out.println("Produto não encontrado");
        }
        else if (produto.getEstoqueAtual() < quantidade){
            System.out.println("Não há estoque para essa compra");
        }
        else{
            produto.setEstoqueAtual(produto.getEstoqueAtual() - quantidade);
            // gerenciaProdutos.salvarProdutosAtualizados(); //criar esse método - package IO!!!
            //escritorCSV.atualizarArquivoVenda(,v); 
            Venda v = new VendaAVista(idVenda, DataVenda, idProduto, quantidade, MeioPagamento);
            vendas.add(v);
        } 
    }
    
    //VENDA FIADO
    public void registrarVenda( int idCliente, int idVenda, String DataVenda, int idProduto, int quantidade, char MeioPagamento){
        Produto produto =gp.buscarProduto(idProduto);
        if(produto == null){
            System.out.println("Produto não encontrado");
        }
        else if (produto.getEstoqueAtual() < quantidade){
            System.out.println("Não há estoque para essa compra");
        }
        else{
            produto.setEstoqueAtual(produto.getEstoqueAtual() - quantidade);
            // gerenciaProdutos.salvarProdutosAtualizados(); //criar esse método - package IO!!!
            //escritorCSV.atualizarArquivoVenda(,v); 
            Venda v = new VendaFiado(idCliente, idVenda, DataVenda, idProduto, quantidade, MeioPagamento);
            vendas.add(v);
        } 
    }
    
    public void listarVendas(){
        if(!vendas.isEmpty()){
            for(Venda v : vendas){
                System.out.println(v.toString());
            }
        }
    }
    
    public Venda buscarVenda(int codigoVenda){
        if(!vendas.isEmpty()){
            for(Venda v : vendas){
                if(v.getIdVenda() == codigoVenda){
                    return v;
                }
            }
        }
        return null;
    }
    
    public BigDecimal lucroTotalDoPedido(Venda v, Produto p){
        BigDecimal quantidadeProd = new BigDecimal(v.getQuantidade());
        return p.getLucro().multiply(quantidadeProd);
    }
    
    public BigDecimal receitaTotalDoPedido(Venda v, Produto p){
        BigDecimal quantidadeProd = new BigDecimal(v.getQuantidade());
        return p.getValorDeVenda().multiply(quantidadeProd);
    }
    
    public BigDecimal receitaPorProduto(String idProd){
        Produto produto =gp.buscarProduto(idProd);
        BigDecimal total = BigDecimal.ZERO;
        if(produto == null){
            System.out.println("Produto não encontrado");
            return null;
        }
        else if (!vendas.isEmpty()){
            for(Venda v : vendas){
                if(produto.getIdProduto() == v.getIdProduto()){
                    total.add(this.receitaTotalDoPedido(v, produto));
                }
            }
        }
        else{
            System.out.println("Ainda não há nehuma venda registrada no sistema!");
            return null;
        }
        return total;
    }
    
    public BigDecimal lucroPorProduto(String idProd){
        Produto produto =gp.buscarProduto(idProd);
        BigDecimal total = BigDecimal.ZERO;
        if(produto == null){
            System.out.println("Produto não encontrado");
            return null;
        }
        else if (!vendas.isEmpty()){
            for(Venda v : vendas){
                if(produto.getIdProduto() == v.getIdProduto()){
                    total.add(this.lucroTotalDoPedido(v, produto));
                }
            }
        }
        else{
            System.out.println("Ainda não há nehuma venda registrada no sistema!");
            return null;
        }
        return total;
    }
    
    public BigDecimal receitaPorMP(char meioPagamento){
        BigDecimal total = BigDecimal.ZERO;
        Map<Integer,Produto>  cacheProdutos = new HashMap<>(); //evitar buscas infinitas na memória
        
        
        if (!vendas.isEmpty()){
            
            for(Venda v : vendas){
                if(v.getMeioPagamento() == meioPagamento){
                    
                    int idProduto = v.getIdProduto();
                    
                    Produto produto = cacheProdutos.get(idProduto);
                    if(produto == null){
                        produto = gp.buscarProduto(idProduto);
                        
                        if(produto != null){
                            cacheProdutos.put(idProduto,produto);
                        }
                        else{
                            continue;
                        }
                    }
                    
                    total.add(this.receitaTotalDoPedido(v, produto));
                }
            }
            
            return total;
        }
         else{
            System.out.println("Ainda não há nehuma venda registrada no sistema!");
            return null;
        }
    }
        
    public BigDecimal buscaValorDeVenda(String idProduto){
        
    }
    public BigDecimal lucroPorMP(char meioPagamento){
    
    }
    
    
    public BigDecimal totalAReceberCliente(String codigo){
    
    }
    
    public List<Venda> filtrarVendasAReceber(){
        List<Venda> vendasAreceber = new ArrayList<>();
        if(!vendas.isEmpty()){
            for(Venda v:vendas){
                if(v.getMeioPagamento() == 'F'){
                    vendasAreceber.add(v);
                }
            }
        }
        return vendasAreceber;
        }
    
    
    
    }

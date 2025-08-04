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
import java.util.ArrayList;
import java.util.List;
import model.Produto;
import model.Venda;


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
    private List<Produto> produtos//objeto responsavel por escrever dados no csv
    
    public GerenciaVenda(List<Produto>) {
        vendas = new ArrayList<>();
        leitorCSV = new Leitura();
        escritorCSV = new Escrita();produtos = new ArrayList<>();
        
    }
    
// USAR o Polimorfismo de INclusão aqui, para simplificar
    
// Não tem deletar vendas ou editar vendas, porque cada venda é única;
    public void registrarVenda(int idVenda, String DataVenda, int idProduto, int quantidade, char MeioPagamento){
        Produto p;
        p = buscar
        Venda v = new Venda(idVenda, DataVenda, idProduto, quantidade, MeioPagamento);
        vendas.add(v);
        //escritorCSV.atualizarArquivoVenda(,v);
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
                else{
                    return null;
                }
            }
        }
    }
    
    
    public BigDecimal receitaPorProduto(String codigoProd){
        
    }
    public BigDecimal lucroPorProduto(String codigoProd){
    }
    
    public BigDecimal receitaPorMP(char meioPagamento){}
    public BigDecimal lucroPorMP(char meioPagamento){}
    
    
    public BigDecimal totalAReceberCliente(String codigo){}
    
    public List<Venda> filtrarVendasAReceber(){}
}

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
import java.util.ArrayList;
import java.util.List;
import model.Produto;
import model.Venda;


/**
 *
 * @author emily
 */
public class GerenciaVenda {
    private List<Venda> vendas; //lista que armazena os objetos Produto em memoria
    private final String ARQUIVO_VENDA= "vendas.csv"; //nome do arquivo csv que os produtos sao salvos 
    private Leitura leitorCSV;//objeto responsavel por lerdados no csv
    private Escrita escritorCSV;//objeto responsavel por escrever dados no csv
    
    public GerenciaVenda() {
        vendas = new ArrayList<>();
        leitorCSV = new Leitura();
        escritorCSV = new Escrita();
    }
    
    
    public void registrarVenda(Venda v){
        
    }
    
    public void listarVendas(){
        
    }
}

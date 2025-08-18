/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;
import model.Fornecedor;

import service.GerenciaFornecedor;

/**
 *
 * @author Usuario
 */
public class GerenciaFornecedorTeste {
    
    public static void main(String[] args){
        
        GerenciaFornecedor gerenciaFornecedor = new GerenciaFornecedor();
        
        Fornecedor f1 = new Fornecedor(
            9394,
            "nome empresa 1",
            "endereço 1",
            "telefone 1",
            "cnpj 1",
            "contato 1");
        
        Fornecedor f2 = new Fornecedor(
            9395,
            "nome empresa 2",
            "endereço 2",
            "telefone 2",
            "cnpj 2",
            "contato 2");
        
        gerenciaFornecedor.inserirFornecedor(f1);
        gerenciaFornecedor.inserirFornecedor(f2);
        
        gerenciaFornecedor.listarFornecedores();
        
        System.out.println("Buscando f2");
        Fornecedor c = gerenciaFornecedor.buscarFornecedor(9395);
        System.out.println(c.toString());
        
        
        gerenciaFornecedor.editarFornecedor(9394);
        
        gerenciaFornecedor.listarFornecedores();
        
        System.out.println("Removendo f2");
        gerenciaFornecedor.removerFornecedor(9395);
        gerenciaFornecedor.listarFornecedores();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package service;

import model.ClienteFisico;

/**
 *
 * @author clara
 */
public class Teste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        GerenciaCliente teste = new GerenciaCliente();
        
        //ClienteFisico cliente1 = new ClienteFisico(1,"Juliana Ferreira","Rua Maranhão, 123 - Bairro Sol","2799239467","2024-09-28","F","466.750.508-77");
        teste.carregarClientesCSV("C:\\Users\\clara\\Documents\\clientes.csv");
        //System.out.println(cliente1.getCpf());
    }
    
}

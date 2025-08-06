/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package service;

/**
 *
 * @author clara
 */
public class Service {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        GerenciaCliente teste = new GerenciaCliente();
        
        teste.carregarClientesCSV("clientes_20.csv");
    }
    
}

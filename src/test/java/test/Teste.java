package test;

import model.ClienteFisico;
import service.GerenciaCliente;

/**
 *
 * @author clara
 */

// TESTANDO SE OS CLIENTES SÃO CARREGADOS DO CSV
public class Teste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        GerenciaCliente teste = new GerenciaCliente();
        
        ClienteFisico cliente1 = new ClienteFisico(1,"Juliana Ferreira","Rua Maranhão, 123 - Bairro Sol","2799239467","2024-09-28","F","466.750.508-77");
        teste.carregarClientesCSV("C:\\Users\\clara\\Documents\\clientes.csv");
        System.out.println(cliente1.getCpf());
    }
    
}

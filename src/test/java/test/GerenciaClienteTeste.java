/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;
import model.Cliente;
import model.ClienteFisico;
import model.ClienteJuridico;
import service.GerenciaCliente;

/**
 *
 * @author Usuario
 */
public class GerenciaClienteTeste {
    
    public static void main(String[] args){
        
        GerenciaCliente gerenciaCliente = new GerenciaCliente();
        
        Cliente cf = new ClienteFisico(
            9394,
            "nome",
            "endereço",
            "telefone",
            "dataCad",
            "F",
            "CPF");
        
        Cliente cj = new ClienteJuridico(
            "CNPJ",
            1111,
            9395,
            "nome",
            "endereço",
            "telefone",
            "dataCad",
            "J");
        
        gerenciaCliente.inserirCliente(cf);
        gerenciaCliente.inserirCliente(cj);
        
        gerenciaCliente.listarClientes();
        
        System.out.println("Buscando cj");
        Cliente c = gerenciaCliente.buscarCliente(9395);
        System.out.println(c.toString());
        
        
        gerenciaCliente.editarCliente(9394);
        
        gerenciaCliente.listarClientes();
        
        System.out.println("Removendo cj");
        gerenciaCliente.removerCliente(9395);
        gerenciaCliente.listarClientes();
    }
}

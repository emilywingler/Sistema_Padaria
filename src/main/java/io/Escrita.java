
package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import model.ClienteFisico;
import model.ClienteJuridico;
import model.Fornecedor;
import model.Produto;
import model.Venda;

public class Escrita {
    
    /*
        Estou em duvida se passo como parametro um objeto Cliente ou uma lista de Clientes
        
    */

    public void atualizarArquivoClienteFisico(String caminhoArquivo, ClienteFisico cliente) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            String linha = cliente.getId() + ";" +
                    cliente.getNome() + ";" +
                    cliente.getEndereco() + ";" +
                    cliente.getTelefone() + ";" +
                    cliente.getDataCadastro() + ";" +
                    cliente.getTipo() + ";" +
                    cliente.getCpf() + ";";
                                        
            bw.write(linha);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo: " + e.getMessage());
        }
    }
    
    public void atualizarArquivoClienteJuridico(String caminhoArquivo, ClienteJuridico cliente) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            String linha = cliente.getId() + ";" +
                    cliente.getNome() + ";" +
                    cliente.getEndereco() + ";" +
                    cliente.getTelefone() + ";" +
                    cliente.getDataCadastro() + ";" +
                    cliente.getTipo() + ";" +
                    cliente.getCnpj() + ";" +
                    cliente.getInscricaoEstadual() + ";";
                                        
            bw.write(linha);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo: " + e.getMessage());
        }
    }
    
    public void atualizarArquivoFornecedor(String caminhoArquivo, Fornecedor fornecedor) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            String linha = fornecedor.getIdFornecedor() + ";" +
                    fornecedor.getNomeEmpresa() + ";" + 
                    fornecedor.getEndereco() + ";" + 
                    fornecedor.getTelefone() + ";" + 
                    fornecedor.getCnpj() + ";" + 
                    fornecedor.getPessoaContato();                    
            bw.write(linha);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo: " + e.getMessage());
        }
    }
    
   /* public void atualizarArquivoProdutos(String caminhoArquivo, Produto produto) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            //adicionar cliente 
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo: " + e.getMessage());
        }
    }
    
    public void atualizarArquivoVendas(String caminhoArquivo, Venda venda) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            //adicionar cliente 
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo: " + e.getMessage());
        }
    }*/

}
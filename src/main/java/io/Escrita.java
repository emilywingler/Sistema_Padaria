package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import model.Cliente;
import model.ClienteFisico;
import model.ClienteJuridico;
import model.Fornecedor;
//import model.Produto;
//import model.Venda;

public class Escrita{
    

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
       
    public void reescreverArquivoCliente(List<Cliente> clientes, String caminhoArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bw.write("id;nome;endereco;telefone;dataCadastro;tipo;documento;inscricaoEstadual");
            bw.newLine();
            for (Cliente c : clientes) {
                String documento = "";
                int inscricaoEstadual = 0;

                if (c instanceof ClienteFisico) {
                    documento = ((ClienteFisico) c).getCpf();
                } else if (c instanceof ClienteJuridico) {
                    documento = ((ClienteJuridico) c).getCnpj();
                    inscricaoEstadual = ((ClienteJuridico) c).getInscricaoEstadual();
                }

                bw.write(String.format("%d;%s;%s;%s;%s;%s;%s;%d",
                        c.getId(),
                        c.getNome(),
                        c.getEndereco(),
                        c.getTelefone(),
                        c.getDataCadastro(),
                        c.getTipo(),
                        documento,
                        inscricaoEstadual
                ));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
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


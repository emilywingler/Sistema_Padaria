
package io;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Leitura {
    
    public List<String[]> lerArquivo(String caminhoArquivo) {
        
    List<String[]> linhas = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                linhas.add(campos);
            }
        } catch (IOException e) {
            System.out.println("Erro de I/O");
            System.exit(1);
        }

        return linhas;
    }
}
    
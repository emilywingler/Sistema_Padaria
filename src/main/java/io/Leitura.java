package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária responsável pela leitura de arquivos CSV.
 * <p>
 * Este componente lê arquivos delimitados por ponto e vírgula (";") e retorna os dados
 * como uma lista de arrays de {@code String}, cada array representando uma linha do arquivo.
 * </p>
 * <p>
 * O método principal {@link #lerArquivo(String)} ignora a primeira linha do CSV,
 * assumindo que ela contém o cabeçalho.
 * </p>
 */
public class Leitura {

    /**
     * Lê um arquivo CSV e retorna seu conteúdo como uma lista de arrays de {@code String}.
     * <p>
     * Cada array de {@code String} na lista representa uma linha do arquivo CSV, 
     * com os valores separados por ponto e vírgula (";"). O cabeçalho do CSV é ignorado.
     * </p>
     *
     * @param caminhoArquivo caminho completo do arquivo CSV a ser lido
     * @return uma lista de arrays de {@code String}, cada array representa uma linha do CSV
     */
    public List<String[]> lerArquivo(String caminhoArquivo) {
        List<String[]> linhas = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            // Ignora a primeira linha (cabeçalho)
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                linhas.add(campos);
            }
        } catch (IOException e) {
            System.out.println("Erro de I/O ao ler o arquivo: " + caminhoArquivo);
            System.exit(1);
        }

        return linhas;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author clara
 */
public class Escrita {
    public static void escreverCSV(String caminhoArquivo, List<String[]> dados) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (String[] linha : dados) {
                // Junta os campos com ";" e escreve no arquivo
                String linhaFormatada = String.join(";", linha);
                bw.write(linhaFormatada);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo: " + e.getMessage());
        }
    }
}

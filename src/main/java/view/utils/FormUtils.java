
package view.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Classe com métodos utilitários para criar componentes de formulário.
 */
public class FormUtils {

    /**
     * Cria um JTextField para datas com um texto de ajuda (placeholder).
     * O texto de ajuda some quando o campo ganha foco e reaparece se o campo ficar vazio.
     * @return Um JTextField configurado para entrada de data.
     */
    public static JTextField criarCampoDataComPlaceholder() {
        JTextField campoData = new JTextField("dd/MM/yyyy");
        campoData.setForeground(Color.GRAY);

        campoData.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoData.getText().equals("dd/MM/yyyy")) {
                    campoData.setText("");
                    campoData.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (campoData.getText().isEmpty()) {
                    campoData.setForeground(Color.GRAY);
                    campoData.setText("dd/MM/yyyy");
                }
            }
        });
        return campoData;
    }
    
    /**
     * Valida se um campo de texto está vazio.
     * Se estiver vazio, exibe uma mensagem de erro e retorna false.
     * @param campo O JTextField a ser validado.
     * @param nomeDoCampo O nome do campo para a mensagem de erro (ex: "Nome").
     * @param framePai O componente pai para a caixa de diálogo de erro.
     * @return true se o campo for válido (não vazio), false caso contrário.
     */
    public static boolean validarCampoTextoVazio(JTextField campo, String nomeDoCampo, Component framePai) {
        if (campo.getText().trim().isEmpty()) {
            String mensagem = "O campo '" + nomeDoCampo + "' não pode estar vazio.";
            JOptionPane.showMessageDialog(framePai, mensagem, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false; // Validação falhou
        }
        return true; // Validação passou
    }
}
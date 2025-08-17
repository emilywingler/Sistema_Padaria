package report;

import io.Escrita;
import java.math.BigDecimal;
import java.util.*;
import service.GerenciaCompra;
import service.GerenciaFornecedor;
import model.Fornecedor;

/**
 * Classe responsável por gerar um relatório de valores a pagar para cada fornecedor.
 * <p>
 * A classe consulta os fornecedores cadastrados e, para cada um,
 * calcula o valor total devido, retornando as informações formatadas
 * e ordenadas alfabeticamente pelo nome da empresa.
 * </p>
 * 
 * <p>
 * O relatório gerado é estruturado como uma lista de arrays de {@code String},
 * onde cada array contém:
 * <ul>
 *   <li>Nome da empresa</li>
 *   <li>CNPJ</li>
 *   <li>Pessoa de contato</li>
 *   <li>Telefone</li>
 *   <li>Valor total a pagar (formatado com duas casas decimais)</li>
 * </ul>
 * </p>
 * 
 * @author Clara
 * @version 1.0
 */
public class Apagar {

    /** Gerencia de compras, utilizada para calcular valores a pagar por fornecedor. */
    private GerenciaCompra gc;

    /** Gerencia de fornecedores, utilizada para buscar dados de fornecedores. */
    private GerenciaFornecedor gf;

    /**
     * Construtor da classe {@code Apagar}.
     *
     * @param gc instância de {@link GerenciaCompra} para consulta de valores a pagar
     * @param gf instância de {@link GerenciaFornecedor} para busca de fornecedores
     */
    public Apagar(GerenciaCompra gc, GerenciaFornecedor gf) {
        this.gc = gc;
        this.gf = gf;
    }

    /**
     * Gera o relatório de valores a pagar para todos os fornecedores cadastrados.
     * <p>
     * O método percorre um intervalo de IDs para buscar fornecedores (0 a 9999),
     * calcula o total a pagar para cada fornecedor encontrado e armazena
     * as informações em uma lista. Ao final, a lista é ordenada pelo nome da empresa.
     * </p>
     *
     * @return uma lista de arrays de {@code String} contendo os dados dos fornecedores e valores a pagar
     *         no seguinte formato: [nomeEmpresa, cnpj, pessoaContato, telefone, totalAPagar]
     */
    public List<String[]> gerar() {
        List<String[]> dados = new ArrayList<>();

        // Lista de fornecedores
        List<Fornecedor> fornecedores = new ArrayList<>();
        for (int i = 0; i < 10000; i++) { 
            Fornecedor f = gf.buscarFornecedor(i);
            if (f != null) fornecedores.add(f);
        }

        // Montar os dados de cada fornecedor
        for (Fornecedor f : fornecedores) {
            BigDecimal total = gc.totalAPagarPorFornecedor(f.getIdFornecedor());
            if (total == null) total = BigDecimal.ZERO;

            dados.add(new String[]{
                f.getNomeEmpresa(),
                f.getCnpj(),
                f.getPessoaContato(),
                f.getTelefone(),
                String.format("%.2f", total)
            });
        }

        // Ordenar por nome do fornecedor
        dados.sort(Comparator.comparing(a -> a[0]));
        return dados;
    }
    
    /**
     * Gera e salva o relatório em arquivo CSV.
     *
     * @param caminhoArquivo caminho do CSV de saída
     */
    public void gerarCSV(String caminhoArquivo) {
        List<String[]> dados = gerar();
        Escrita escrita = new Escrita();
        escrita.escreverApagar(caminhoArquivo, dados);
    }
}


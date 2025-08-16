package report;

import io.Escrita;
import java.math.BigDecimal;
import java.util.*;
import service.GerenciaVenda;
import service.GerenciaCliente;
import model.Cliente;
import model.ClienteFisico;
import model.ClienteJuridico;

/**
 * Classe responsável por gerar um relatório de valores a receber de clientes.
 * <p>
 * A classe percorre os clientes cadastrados, identifica se são pessoas físicas
 * ou jurídicas, recupera o documento correspondente (CPF ou CNPJ) e calcula o
 * valor total pendente de recebimento.
 * </p>
 * 
 * <p>
 * O relatório gerado é estruturado como uma lista de arrays de {@code String},
 * onde cada array contém:
 * <ul>
 *   <li>Nome do cliente</li>
 *   <li>Tipo (F para físico, J para jurídico)</li>
 *   <li>CPF ou CNPJ</li>
 *   <li>Telefone</li>
 *   <li>Data de cadastro</li>
 *   <li>Valor total a receber (formatado com duas casas decimais)</li>
 * </ul>
 * </p>
 */
public class Areceber {

    /** Gerencia de vendas, utilizada para calcular valores a receber por cliente. */
    private GerenciaVenda gv;

    /** Gerencia de clientes, utilizada para buscar dados de clientes. */
    private GerenciaCliente gc;

    /**
     * Construtor da classe {@code Areceber}.
     *
     * @param gv instância de {@link GerenciaVenda} para consulta de valores a receber
     * @param gc instância de {@link GerenciaCliente} para busca de clientes
     */
    public Areceber(GerenciaVenda gv, GerenciaCliente gc) {
        this.gv = gv;
        this.gc = gc;
    }

    /**
     * Gera o relatório de valores a receber de todos os clientes cadastrados.
     * <p>
     * O método percorre um intervalo de IDs para buscar clientes (0 a 9999),
     * determina se o cliente é físico ou jurídico, obtém o documento correspondente
     * (CPF ou CNPJ) e calcula o total a receber. Ao final, ordena os dados pelo
     * nome do cliente.
     * </p>
     *
     * @return uma lista de arrays de {@code String} contendo os dados dos clientes e valores a receber
     *         no seguinte formato: [nome, tipo(F/J), documento, telefone, dataCadastro, totalAReceber]
     */
    public List<String[]> gerar() {
        List<String[]> dados = new ArrayList<>();

        // Lista de clientes
        List<Cliente> clientes = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Cliente c = gc.buscarCliente(i);
            if (c != null) clientes.add(c);
        }

        // Montar dados de cada cliente
        for (Cliente c : clientes) {
            BigDecimal total = gv.totalAReceberCliente(c.getId());
            if (total == null) total = BigDecimal.ZERO;

            String documento = "";
            switch (c) {
                case ClienteFisico clienteFisico -> documento = clienteFisico.getCpf();
                case ClienteJuridico clienteJuridico -> documento = clienteJuridico.getCnpj();
                default -> {}
            }

            dados.add(new String[]{
                c.getNome(),
                c instanceof ClienteFisico ? "F" : "J",
                documento,
                c.getTelefone(),
                c.getDataCadastro(),
                String.format("%.2f", total)
            });
        }

        // Ordenar por nome do cliente
        dados.sort(Comparator.comparing(a -> a[0]));
        return dados;
    }
    
    public void gerarCSV(String caminhoArquivo) {
        List<String[]> dados = gerar();
        Escrita escrita = new Escrita();
        escrita.escreverAreceber(caminhoArquivo, dados);
    }
}

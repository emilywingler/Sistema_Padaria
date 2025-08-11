package model;

/**
 * Representa um cliente do tipo pessoa física, com CPF.
 * Estende a classe {@link Cliente} e inclui um campo específico para CPF,
 * além de contar o total de clientes físicos instanciados.
 * 
 * @author emily
 */
public class ClienteFisico extends Cliente {

    /** CPF do cliente físico. */
    private String cpf;

    /** Total de instâncias criadas de ClienteFisico. */
    private static int totalClientesFisicos = 0;

    /**
     * Cria um novo cliente físico com os dados fornecidos.
     *
     * @param idCliente      ID do cliente
     * @param nome           Nome do cliente
     * @param endereco       Endereço do cliente
     * @param telefone       Telefone do cliente
     * @param dataCadastro   Data de cadastro do cliente
     * @param tipo           Tipo do cliente (ex: "Físico")
     * @param cpf            CPF do cliente
     */
    public ClienteFisico(int idCliente, String nome, String endereco, String telefone, String dataCadastro, String tipo, String cpf) {
        super(idCliente, nome, endereco, telefone, dataCadastro, tipo);
        this.cpf = cpf;
        totalClientesFisicos++;
    }

    /**
     * Retorna o CPF do cliente.
     *
     * @return CPF como {@code String}
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Define o CPF do cliente.
     *
     * @param cpf Novo CPF
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Retorna o total de clientes físicos instanciados.
     *
     * @return total de {@code ClienteFisico}
     */
    public static int getTotalClientesFisicos() {
        return totalClientesFisicos;
    }

    /**
     * Retorna a representação textual do cliente físico, incluindo o CPF.
     *
     * @return String com os dados do cliente
     */
    @Override
    public String toString() {
        return super.toString() + ", cpf=" + cpf;
    }
}

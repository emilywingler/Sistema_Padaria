package model;

/**
 * Representa um cliente do tipo pessoa jurídica.
 * Estende a classe {@link Cliente} e adiciona os campos de CNPJ e inscrição estadual,
 * além de contabilizar o total de clientes jurídicos instanciados.
 * 
 * @author emily
 */
public class ClienteJuridico extends Cliente {

    /** CNPJ do cliente jurídico. */
    private String cnpj;

    /** Inscrição estadual do cliente jurídico. */
    private int InscricaoEstadual;

    /** Total de instâncias criadas de ClienteJuridico. */
    private static int totalClientesJuridicos = 0;

    /**
     * Cria um novo cliente jurídico com os dados fornecidos.
     *
     * @param cnpj               CNPJ da empresa
     * @param InscricaoEstadual Inscrição estadual da empresa
     * @param idCliente          ID do cliente
     * @param nome               Nome da empresa
     * @param endereco           Endereço da empresa
     * @param telefone           Telefone da empresa
     * @param dataCadastro       Data de cadastro
     * @param tipo               Tipo de cliente (ex: "Jurídico")
     */
    public ClienteJuridico(String cnpj, int InscricaoEstadual, int idCliente, String nome, String endereco, String telefone, String dataCadastro, String tipo) {
        super(idCliente, nome, endereco, telefone, dataCadastro, tipo);
        this.cnpj = cnpj;
        this.InscricaoEstadual = InscricaoEstadual;
        totalClientesJuridicos++;
    }

    /**
     * Retorna o CNPJ do cliente jurídico.
     *
     * @return CNPJ como {@code String}
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * Define o CNPJ do cliente jurídico.
     *
     * @param cnpj Novo CNPJ
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * Retorna a inscrição estadual do cliente jurídico.
     *
     * @return número da inscrição estadual
     */
    public int getInscricaoEstadual() {
        return InscricaoEstadual;
    }

    /**
     * Define a inscrição estadual do cliente jurídico.
     *
     * @param InscricaoEstadual Novo número de inscrição estadual
     */
    public void setInscricaoEstadual(int InscricaoEstadual) {
        this.InscricaoEstadual = InscricaoEstadual;
    }

    /**
     * Retorna o total de clientes jurídicos instanciados.
     *
     * @return total de {@code ClienteJuridico}
     */
    public static int getTotalClientesJuridicos() {
        return totalClientesJuridicos;
    }

    /**
     * Retorna a representação textual do cliente jurídico, incluindo CNPJ e inscrição estadual.
     *
     * @return String com os dados completos do cliente
     */
    @Override
    public String toString() {
        return super.toString() + "cnpj: " + cnpj + ", InscricaoEstadual: " + InscricaoEstadual;
    }
}

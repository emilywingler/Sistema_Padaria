package model;

/**
 * Representa um fornecedor no sistema, contendo informações essenciais para
 * identificação, contato e controle de compras.
 *
 * Cada fornecedor possui um identificador único, dados cadastrais e é
 * responsável por fornecer produtos à empresa.
 * 
 * Também mantém uma contagem estática do número total de fornecedores criados.
 * 
 * @author emily
 */
public class Fornecedor {
    /** Identificador único do fornecedor */
    private final int idFornecedor;

    /** Nome da empresa fornecedora */
    private String nomeEmpresa;

    /** Endereço da empresa */
    private String endereco;

    /** Telefone de contato */
    private String telefone;

    /** CNPJ da empresa fornecedora */
    private String cnpj;

    /** Nome da pessoa de contato principal */
    private String pessoaContato;

    /** Quantidade total de fornecedores criados */
    private static int totalFornecedores = 0;

    /**
     * Construtor da classe {@code Fornecedor}.
     *
     * @param idFornecedor   o ID único do fornecedor
     * @param nomeEmpresa    o nome da empresa fornecedora
     * @param endereco       o endereço da empresa
     * @param telefone       o telefone de contato
     * @param cnpj           o CNPJ da empresa fornecedora
     * @param pessoaContato  o nome da pessoa responsável pelo contato
     */
    public Fornecedor(int idFornecedor, String nomeEmpresa, String endereco, String telefone, String cnpj, String pessoaContato) {
        this.idFornecedor = idFornecedor;
        this.nomeEmpresa = nomeEmpresa;
        this.endereco = endereco;
        this.telefone = telefone;
        this.cnpj = cnpj;
        this.pessoaContato = pessoaContato;
        totalFornecedores++;
    }

    /**
     * Retorna o ID do fornecedor.
     *
     * @return o identificador do fornecedor
     */
    public int getIdFornecedor() {
        return idFornecedor;
    }

    /**
     * Retorna o nome da empresa fornecedora.
     *
     * @return o nome da empresa
     */
    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    /**
     * Atualiza o nome da empresa fornecedora.
     *
     * @param nomeEmpresa o novo nome da empresa
     */
    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    /**
     * Retorna o endereço da empresa.
     *
     * @return o endereço do fornecedor
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Atualiza o endereço da empresa.
     *
     * @param endereco o novo endereço
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna o telefone de contato do fornecedor.
     *
     * @return o telefone de contato
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Atualiza o telefone de contato do fornecedor.
     *
     * @param telefone o novo telefone
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna o CNPJ da empresa fornecedora.
     *
     * @return o CNPJ
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * Atualiza o CNPJ da empresa fornecedora.
     *
     * @param cnpj o novo CNPJ
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * Retorna o nome da pessoa de contato principal.
     *
     * @return o nome da pessoa de contato
     */
    public String getPessoaContato() {
        return pessoaContato;
    }

    /**
     * Atualiza o nome da pessoa de contato principal.
     *
     * @param pessoaContato o novo nome da pessoa de contato
     */
    public void setPessoaContato(String pessoaContato) {
        this.pessoaContato = pessoaContato;
    }

    /**
     * Retorna o total de fornecedores registrados no sistema.
     *
     * @return a quantidade total de fornecedores
     */
    public static int getTotalFornecedores() {
        return totalFornecedores;
    }

    /**
     * Retorna uma representação textual do objeto {@code Fornecedor}.
     *
     * @return uma string com os dados principais do fornecedor
     */
    @Override
    public String toString() {
        return "idFornecedor: " + idFornecedor +
               ", nomeEmpresa: " + nomeEmpresa +
               ", endereco: " + endereco +
               ", telefone: " + telefone +
               ", cnpj: " + cnpj +
               ", pessoaContato: " + pessoaContato;
    }
}

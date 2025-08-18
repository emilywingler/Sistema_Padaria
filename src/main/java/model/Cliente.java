package model;

/**
 * Representa um cliente da padaria, podendo ser pessoa física ou jurídica.
 * Cada cliente possui dados de identificação, contato e cadastro.
 * 
 * @author emily
 */
public class Cliente {
    private final int idCliente;
    private String nome;
    private String endereco;
    private String telefone;
    private String dataCadastro;
    private String tipo;
    private static int totalClientes = 0;

    /**
     * Construtor que inicializa os dados do cliente.
     * @param idCliente identificador único do cliente.
     * @param nome nome completo do cliente.
     * @param endereco endereço do cliente.
     * @param telefone telefone para contato.
     * @param dataCadastro data em que o cliente foi cadastrado.
     * @param tipo tipo do cliente (por exemplo: "F" para físico, "J" para jurídico).
     */
    public Cliente(int idCliente, String nome, String endereco, String telefone, String dataCadastro, String tipo) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
        this.tipo = tipo;
        totalClientes++;
    }

    /**
     * Retorna o ID único do cliente.
     * @return ID do cliente.
     */
    public int getId() {
        return idCliente;
    }

    /**
     * Retorna o nome do cliente.
     * @return nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do cliente.
     * @param nome novo nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o endereço do cliente.
     * @return endereço.
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define o endereço do cliente.
     * @param endereco novo endereço.
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna o telefone do cliente.
     * @return telefone.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone do cliente.
     * @param telefone novo telefone.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna a data de cadastro do cliente.
     * @return data de cadastro.
     */
    public String getDataCadastro() {
        return dataCadastro;
    }

    /**
     * Define a data de cadastro do cliente.
     * @param dataCadastro nova data de cadastro.
     */
    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    /**
     * Retorna o tipo do cliente (físico ou jurídico).
     * @return tipo do cliente.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o tipo do cliente.
     * @param tipo novo tipo (esperado "F" ou "J").
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Retorna o número total de clientes criados no sistema.
     * @return total de clientes.
     */
    public static int getTotalClientes() {
        return totalClientes;
    }

    /**
     * Retorna uma representação textual do cliente.
     * @return string formatada com os dados do cliente.
     */
    @Override
    public String toString() {
        return "idCliente: " + idCliente + 
               ", nome: " + nome + 
               ", endereco: " + endereco + 
               ", telefone: " + telefone + 
               ", dataCadastro: " + dataCadastro + 
               ", tipo: " + tipo;
    }
}

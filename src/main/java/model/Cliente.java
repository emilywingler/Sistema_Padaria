package model;

/**
 *
 * @author emily
 * Tem exeções que precisam ser lançadas aqui viu... Depois vemos isso
 */
public class Cliente {
    private final int idCliente;
    private String nome;
    private String endereco;
    private String telefone;
    private String dataCadastro;
    private String tipo;
    private static int totalClientes = 0;

    public Cliente(int idCliente, String nome, String endereco, String telefone, String dataCadastro, String tipo) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
        this.tipo = tipo;
        totalClientes++;
    }
    
    public int getId(){
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public static int getTotalClientes() {
        return totalClientes;
    }

    @Override
    public String toString() {
        return "idCliente: " + idCliente + ", nome: " + nome + ", endereco: " + endereco + ", telefone: " + telefone + ", dataCadastro: " + dataCadastro + ", tipo: " + tipo;
    }
    
    
}

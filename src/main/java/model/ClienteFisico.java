package model;

/**
 *
 * @author emily
 */
public class ClienteFisico extends Cliente{
    private String cpf;
    private static int totalClientesFisicos = 0;

    public ClienteFisico(String cpf, int idCliente, String nome, String endereco, String telefone, String dataCadastro, String tipo) {
        super(idCliente, nome, endereco, telefone, dataCadastro, tipo);
        this.cpf = cpf;
        totalClientesFisicos++;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public static int getTotalClientesFisicos() {
        return totalClientesFisicos;
    }

    @Override
    public String toString() {
        return  super.toString() + ", cpf=" + cpf;
    }
    
    
}

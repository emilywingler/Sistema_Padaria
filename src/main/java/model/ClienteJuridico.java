
package model;

/**
 *
 * @author emily
 */
public class ClienteJuridico extends Cliente{
    private String cnpj;
    private int InscricaoEstadual;
    private static int totalClientesJuridicos = 0;

    public ClienteJuridico(String cnpj, int InscricaoEstadual, int idCliente, String nome, String endereco, String telefone, String dataCadastro, String tipo) {
        super(idCliente, nome, endereco, telefone, dataCadastro, tipo);
        this.cnpj = cnpj;
        this.InscricaoEstadual = InscricaoEstadual;
        totalClientesJuridicos++;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public int getInscricaoEstadual() {
        return InscricaoEstadual;
    }

    public void setInscricaoEstadual(int InscricaoEstadual) {
        this.InscricaoEstadual = InscricaoEstadual;
    }

    public static int getTotalClientesJuridicos() {
        return totalClientesJuridicos;
    }

    @Override
    public String toString() {
        return super.toString() + "cnpj: " + cnpj + ", InscricaoEstadual: " + InscricaoEstadual;
    }
    
    
}

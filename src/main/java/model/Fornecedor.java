
package model;

/**
 *
 * @author emily
 */
public class Fornecedor {
    private final int idFornecedor;
    private String nomeEmpresa;
    private String endereco;
    private String telefone;
    private String cnpj;
    private String pessoaContato;
    private static int totalFornecedores = 0;

    public Fornecedor(int idFornecedor, String nomeEmpresa, String endereco, String telefone, String cnpj, String pessoaContato) {
        this.idFornecedor = idFornecedor;
        this.nomeEmpresa = nomeEmpresa;
        this.endereco = endereco;
        this.telefone = telefone;
        this.cnpj = cnpj;
        this.pessoaContato = pessoaContato;
        totalFornecedores++;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPessoaContato() {
        return pessoaContato;
    }

    public void setPessoaContato(String pessoaContato) {
        this.pessoaContato = pessoaContato;
    }

    public static int getTotalFornecedores() {
        return totalFornecedores;
    }

    
}

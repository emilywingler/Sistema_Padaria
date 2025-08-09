package report;
import java.math.BigDecimal;
import model.Fornecedor;
import service.GerenciaFornecedor;
import service.GerenciaCompra;

/**
 *
 * @author emily
 * 
 */
public class Apagar {
    private GerenciaFornecedor gf;
    private GerenciaCompra gc;

    public Apagar(GerenciaFornecedor gf, GerenciaCompra gc) {
        this.gf = gf;
        this.gc = gc;
    }
    
    public String geraLinha(int IdFornecedor){
        Fornecedor fornecedor = gf.buscarFornecedor(IdFornecedor);
        if(fornecedor != null){
            return fornecedor.getNomeEmpresa() + ";"
                    + fornecedor.getCnpj() + ";" 
                    + fornecedor.getPessoaContato() + ";"
                    + fornecedor.getTelefone() + ";"
                    + gc.totalAPagarPorFornecedor(IdFornecedor);
                                                    
        }
        else{
            System.out.println("Fornecedor não encontrado");
            return null;
        }
    }
    
    
}

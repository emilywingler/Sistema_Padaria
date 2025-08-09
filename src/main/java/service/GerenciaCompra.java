package service;

import io.Escrita;
import io.Leitura;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.Compra;
import model.Produto;
import model.Fornecedor;

/**
 * Classe responsável pelo gerenciamento das operações relacionadas a compras.
 * 
 * <p>Funcionalidades principais:</p>
 * <ul>
 *   <li>Registrar novas compras de produtos;</li>
 *   <li>Listar todas as compras registradas;</li>
 *   <li>Buscar uma compra específica pelo seu ID;</li>
 *   <li>Calcular o valor total de uma compra;</li>
 *   <li>Calcular o total a pagar para um fornecedor específico.</li>
 * </ul>
 * 
 * <p>Esta classe interage com {@link GerenciaProduto} para validar e obter informações
 * sobre os produtos envolvidos nas compras, e com {@link GerenciaFornecedor} para
 * verificar dados dos fornecedores.</p>
 * 
 * <p>Os registros podem ser persistidos em arquivo CSV por meio das classes 
 * {@link io.Leitura} e {@link io.Escrita}.</p>
 * 
 * @author emily
 */
public class GerenciaCompra {

    /**
     * Lista de todas as compras registradas no sistema.
     */
    private List<Compra> compras;

    /**
     * Nome do arquivo CSV utilizado para armazenar as compras.
     */
    private final String ARQUIVO_VENDA = "compras.csv";

    /**
     * Utilitário para leitura de arquivos CSV.
     */
    private Leitura leitorCSV;

    /**
     * Utilitário para escrita de arquivos CSV.
     */
    private Escrita escritorCSV;

    /**
     * Gerenciador de produtos para validação e acesso aos dados de produtos.
     */
    private GerenciaProduto gp;

    /**
     * Gerenciador de fornecedores para validação e acesso aos dados de fornecedores.
     */
    private GerenciaFornecedor gf;
    
    /**
     * Constrói um gerenciador de compras.
     * 
     * @param gerenciarProd instância de {@link GerenciaProduto} para controle de produtos
     */
    public GerenciaCompra(GerenciaProduto gerenciarProd) {
        compras = new ArrayList<>();
        leitorCSV = new Leitura();
        escritorCSV = new Escrita();
        this.gp = gerenciarProd;
    }
    
    /**
     * Registra uma nova compra no sistema, atualizando o estoque do produto.
     * 
     * @param idCompra    identificador único da compra
     * @param idFornecedor identificador do fornecedor
     * @param DataCompra   data da compra no formato dd/MM/yyyy
     * @param idProduto    identificador do produto adquirido
     * @param quantidade   quantidade comprada
     */
    public void registrarCompra(int idCompra, int idFornecedor, String DataCompra, int idProduto, int quantidade) {
        Produto produto = gp.buscarProduto(idProduto);
        if (produto == null) {
            System.out.println("Produto não encontrado");
        } else {
            produto.setEstoqueAtual(produto.getEstoqueAtual() - quantidade);
            Compra c = new Compra(idCompra, idFornecedor, DataCompra, idProduto, quantidade);
            compras.add(c);
        }
    }
    
    /**
     * Lista todas as compras registradas no sistema.
     * Caso não haja compras, informa ao usuário.
     */
    public void listarCompras() {
        if (!compras.isEmpty()) {
            for (Compra c : compras) {
                System.out.println(c.toString());
            }
        } else {
            System.out.println("Não há compras registradas no sistema");
        }
    }
    
    /**
     * Busca uma compra pelo seu identificador.
     * 
     * @param idCompra identificador da compra
     * @return a instância de {@link Compra} encontrada ou {@code null} se não existir
     */
    public Compra buscarCompra(int idCompra) {
        if (!compras.isEmpty()) {
            for (Compra c : compras) {
                if (c.getIdCompra() == idCompra) {
                    return c;
                }
            }
        }
        return null;
    }
    
    /**
     * Calcula o valor total de uma compra específica com base na quantidade adquirida
     * e no custo unitário do produto.
     * 
     * @param c instância da compra
     * @param p instância do produto
     * @return valor total da compra
     */
    public BigDecimal valorTotalDaCompra(Compra c, Produto p) {
        BigDecimal quantidadeProd = new BigDecimal(c.getQuantidade());
        return p.getCusto().multiply(quantidadeProd);
    }
    
    /**
     * Calcula o valor total a pagar para um fornecedor específico, somando o valor de
     * todas as compras feitas a ele.
     * 
     * @param idFornecedor identificador do fornecedor
     * @return total a pagar ao fornecedor ou {@code null} se ele não existir
     */
    public BigDecimal totalAPagarPorFornecedor(int idFornecedor) {
        Fornecedor fornecedor = gf.buscarFornecedor(idFornecedor);
        if (fornecedor == null) {
            System.out.println("Fornecedor não encontrado!");
            return null;
        } else {
            BigDecimal total = BigDecimal.ZERO;
            for (Compra c : compras) {
                if (c.getIdFornecedor() == idFornecedor) {
                    Produto produto = gp.buscarProduto(c.getIdProduto());
                    total = total.add(this.valorTotalDaCompra(c, produto));
                }
            }
            return total;
        }
    }
}

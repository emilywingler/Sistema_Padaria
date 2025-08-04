
package service;
import model.Cliente;
import model.ClienteFisico;
import model.ClienteJuridico;
import io.Leitura;
import io.Escrita;
import java.util.List;
import java.util.ArrayList;

public class GerenciaCliente {
    private List<Cliente> clientes;
    private final String ARQUIVO_CLIENTE = "clientes.csv";
    private Leitura leitorCSV;
    private Escrita escritorCSV;
    
    public GerenciaCliente(){
        clientes = new ArrayList<>();
        leitorCSV = new Leitura();
        escritorCSV = new Escrita();
    }
    
    /**
    * Adiciona um novo cliente ao sistema e persiste a alteração no arquivo de dados.
    * <p>
    * Este método primeiro insere o objeto {@code Cliente} na lista em memória e,
    * em seguida, invoca o método de atualização do {@code escritorCSV} para
    * garantir que o novo cliente seja salvo permanentemente no arquivo CSV.
    *
    * @param cliente O objeto {@code Cliente} a ser adicionado. Este objeto deve estar
    * devidamente instanciado e preenchido.
    */
    public void inserirCliente(Cliente cliente){
        clientes.add(cliente);
        escritorCSV.atualizarArquivo();
    }
    
    /**
    * Remove um cliente do sistema com base no seu código de identificação.
    * <p>
    * O método primeiro utiliza o {@link #buscarCliente(int)} para encontrar a instância
    * do cliente correspondente ao código. Se encontrado, o cliente é removido
    * da lista em memória e o arquivo CSV é reescrito para refletir esta remoção.
    *
    * @param codigo O código inteiro (ID) do cliente que deve ser removido.
    */
    public void removerCliente(int codigo){
        Cliente c = buscarCliente(codigo);
        clientes.remove(c);
        escritorCSV.atualizarArquivo(ARQUIVO_CLIENTE);
    }
    
    /**
     * Busca um cliente na lista interna com base no seu código de identificação.
     * <p>
     * Este método percorre a lista de clientes carregada na memória e compara
     * o código de cada um com o valor fornecido.
     * </p>
     *
     * @param codigo O código único (int) do cliente a ser procurado. 
     * Não deve ser nulo ou vazio.
     * @return O objeto {@code Cliente} correspondente ao código, se encontrado. 
     * Retorna {@code null} se nenhum cliente com o código especificado
     * for encontrado na lista.
     */
    public Cliente buscarCliente(int codigo){
        
        for(Cliente c : clientes){
            if(c.getId() == codigo){
                return c;
            }
        }return null;
    }
    
    /**
    * Imprime os detalhes de todos os clientes cadastrados na saída padrão (console).
    * <p>
    * Este método verifica se a lista interna de clientes está vazia. Se estiver, exibe
    * uma mensagem informativa. Caso contrário, itera sobre a lista e imprime os dados
    * de cada cliente, incluindo informações específicas como CPF para {@code ClienteFisico}
    * ou CNPJ e Inscrição Estadual para {@code ClienteJuridico}.
    */
    public void listarClientes(){
        
        if(!clientes.isEmpty()){
            for(Cliente c : clientes){
                System.out.println(c.getId()+" - "+c.getNome()+" - "+c.getEndereco()+" - "+c.getTelefone()+" - "+c.getDataCadastro()+" - ");
                if(c.getTipo().equals("Pessoa Física")) System.out.print(((ClienteFisico)c).getCpf());
                else System.out.print(((ClienteJuridico)c).getCnpj()+" - "+((ClienteJuridico)c).getInscricaoEstadual());
            }
        }else System.out.println("Lista vazia.");  
    }
    
    
    public void editarCliente(int codigo){
        Cliente c = buscarCliente(c);
        
        System.out.println("O que você deseja alterar?");
        System.out.println("1. Nome"
                + "2. Endereço"
                + "Telefone")
    }
    
}

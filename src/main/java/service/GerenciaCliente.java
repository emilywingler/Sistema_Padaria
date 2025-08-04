
package service;
import model.Cliente;
import model.ClienteFisico;
import model.ClienteJuridico;
import io.Leitura;
import io.Escrita;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

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
    
    /**
    * Inicia uma sessão interativa no console para editar os dados de um cliente específico.
    * <p>
    * Primeiramente, o método busca o cliente pelo código fornecido. Uma vez encontrado,
    * um menu de opções é exibido em loop, permitindo ao usuário escolher qual campo
    * do cliente deseja alterar. O loop continua até que a opção "Sair" (8) seja selecionada.
    * <p>
    * O método realiza verificações de tipo (usando {@code instanceof}) para campos
    * específicos como CPF e CNPJ, garantindo que apenas os dados pertinentes a um
    * {@link ClienteFisico} ou {@link ClienteJuridico} possam ser editados e informando
    * o usuário em caso de uma escolha inadequada.
    * <p>
    * Todas as alterações são aplicadas ao objeto em memória durante a sessão e são
    * persistidas no arquivo CSV de uma só vez, apenas ao final do processo, quando
    * o usuário decide sair do menu de edição.
    *
    * @param codigo O código numérico (ID) do cliente que se deseja editar.
    */
    public void editarCliente(int codigo){
        Cliente c = buscarCliente(codigo);
        int op = 0;
        Scanner sc = new Scanner(System.in);
        
        while(op != 8){
            System.out.println("""
                               1. Nome
                               2. Endereco
                               3. Telefone
                               4. Data de Cadastro
                               5. CPF
                               6. CNPJ
                               7. Inscricao Estadual
                               8. Sair
                               """);
            System.out.println("\nInsira uma opcao: ");
            op = sc.nextInt();
            sc.nextLine();
        
            switch(op){
                case 1 ->{
                    System.out.println("Insira o novo nome: ");
                    c.setNome(sc.nextLine());
                }
                case 2 ->{
                    System.out.println("Insira o novo endereco: ");
                    c.setEndereco(sc.nextLine());
                }
                case 3 ->{
                    System.out.println("Insira o novo telefone: ");
                    c.setTelefone(sc.nextLine());
                }
                case 4 ->{
                    System.out.println("Insira a nova data de cadastro: ");
                    c.setDataCadastro(sc.nextLine());
                }
                case 5 ->{
                    if(c instanceof ClienteFisico){
                        System.out.println("Insira o novo CPF: ");
                        ((ClienteFisico)c).setCpf(sc.nextLine());
                    }else{
                        System.out.println("Um cliente juridico nao possui CPF.");
                    }
                }
                case 6 ->{
                    if(c instanceof ClienteJuridico){
                        System.out.println("Insira o novo CNPJ: ");
                        ((ClienteJuridico)c).setCnpj(sc.nextLine());
                    }else{
                        System.out.println("Um cliente fisico nao possui CNPJ.");
                    }
                }
                case 7 ->{
                    if(c instanceof ClienteJuridico){
                        System.out.println("Insira a nova Inscrição Estadual: ");
                        ((ClienteJuridico)c).setInscricaoEstadual(sc.nextLine());
                    }else{
                        System.out.println("Um cliente fisico nao possui Inscricao Estadual.");
                    }
                }
                case 8 ->{}
                default ->{
                    System.out.println("Valor invalido. Tente novamente.");
                }
            }
        }
        escritorCSV.atualizarArquivo(ARQUIVO_CLIENTE);  
        sc.close();
    }   
}

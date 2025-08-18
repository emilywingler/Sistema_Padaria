
package service;
import model.Cliente;
import model.ClienteFisico;
import model.ClienteJuridico;
import io.Leitura;
//import io.Escrita;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class GerenciaCliente {
    private List<Cliente> clientes;
    private final String ARQUIVO_CLIENTE = "clientes_20.csv";
    private Leitura leitorCSV;
   
    /**
    * Construtor padrão da classe GerenciaCliente.
    * Inicializa a lista de clientes como um novo ArrayList e instancia um objeto
    * da classe Leitura para manipulação de arquivos CSV.
    */
    public GerenciaCliente(){
        clientes = new ArrayList<>();
        leitorCSV = new Leitura();
    }
    
    /**
    * Carrega os dados de clientes a partir de um arquivo CSV para a lista de clientes.
    * O método lê o arquivo especificado, processa cada linha para extrair os dados
    * do cliente e instancia objetos de {@code ClienteFisico} ou {@code ClienteJuridico}
    * com base no tipo de cliente indicado no arquivo.
    *
    * <p>A estrutura esperada do CSV é a seguinte:
    * <ul>
    * <li><b>Para Cliente Físico (tipo "F"):</b> idCliente, nome, endereco, telefone, dataCadastro, tipo, cpf</li>
    * <li><b>Para Cliente Jurídico (tipo "J"):</b> idCliente, nome, endereco, telefone, dataCadastro, tipo, cnpj, inscricaoEstadual</li>
    * </ul>
    * </p>
    *
    * @param caminhoArquivo O caminho completo para o arquivo CSV que contém os dados dos clientes.
    */
    public void carregarClientesCSV(String caminhoArquivo) {
        List<String[]> linhas = leitorCSV.lerArquivo(caminhoArquivo);

        for (String[] campos : linhas) {
            int idCliente = Integer.parseInt(campos[0]);
            String nome = campos[1];
            String endereco = campos[2];
            String telefone = campos[3];
            String dataCadastro = campos[4];
            String tipo = campos[5];

            if (tipo.equalsIgnoreCase("F")) {
                String cpf = campos[6];
                ClienteFisico cf = new ClienteFisico(idCliente, nome, endereco, telefone, dataCadastro, tipo, cpf);
                clientes.add(cf);
            } else if (tipo.equalsIgnoreCase("J")) {
                String cnpj = campos[6];
                int inscricaoEstadual = Integer.parseInt(campos[7]);
                ClienteJuridico cj = new ClienteJuridico(cnpj, inscricaoEstadual, idCliente, nome, endereco, telefone, dataCadastro, tipo);
                clientes.add(cj);
            }
        }
    }

    
    /**
     * Solicita os dados ao usuário, cria um ClienteFisico ou ClienteJuridico
     * e o adiciona à lista de clientes.
     * 
     * @param sc Scanner que será utilizado.
     */
    public void inserirCliente(Scanner sc){

        int idCliente;
        while (true) {
            System.out.println("Digite o ID do cliente: ");
            idCliente = sc.nextInt();
            sc.nextLine(); // Consome a quebra de linha

            if (this.buscarCliente(idCliente) == null) {
                break; // ID válido, sai do laço
            } else {
                System.out.println("Já existe um cliente com esse ID! Digite novamente.");
            }
        }

        System.out.println("Digite o nome: ");
        String nome = sc.nextLine();

        System.out.println("Digite o endereço: ");
        String endereco = sc.nextLine();

        System.out.println("Digite o telefone: ");
        String telefone = sc.nextLine();
        
        System.out.println("Digite a data de cadastro: ");
        String dataCadastro = sc.nextLine();

        Cliente cliente;
        String tipo;

        // Loop para garantir que o usuário digite 'F' ou 'J'
        do {
            System.out.println("Digite o tipo (F para Físico, J para Jurídico): ");
            tipo = sc.nextLine().toUpperCase();
            if (!tipo.equals("F") && !tipo.equals("J")) {
                System.out.println("Opção inválida! Por favor, digite F ou J.");
            }
        } while (!tipo.equals("F") && !tipo.equals("J"));


        // Verifica o tipo para solicitar os dados específicos
        if (tipo.equals("F")) {
            System.out.println("Digite o CPF: ");
            String cpf = sc.nextLine();
            
            cliente = new ClienteFisico(idCliente, nome, endereco, telefone, dataCadastro, tipo, cpf);
        } else { // Se não é "F", com certeza é "J"
            System.out.println("Digite o CNPJ: ");
            String cnpj = sc.nextLine();
            
            System.out.println("Digite a Inscrição Estadual: ");
            int inscricaoEstadual = sc.nextInt();

            cliente = new ClienteJuridico(cnpj, inscricaoEstadual, idCliente, nome, endereco, telefone, dataCadastro, tipo);
        }

        clientes.add(cliente);
        System.out.println(">>> Cliente cadastrado com sucesso! <<<");
    }
    
    /**
    * Remove um cliente do sistema com base no seu código de identificação.
    * <p>
    * O método primeiro utiliza o {@link #buscarCliente(int)} para encontrar a instância
    * do cliente correspondente ao código. Se encontrado, o cliente é removido
    * da lista em memória.
    *
    * @param idCliente O código inteiro (ID) do cliente que deve ser removido.
    */
    public void removerCliente(int idCliente){
        Cliente cliente = buscarCliente(idCliente);
        if(cliente != null){
            clientes.remove(cliente);
            /*for (Cliente c : clientes) {
                if(c.getTipo().equalsIgnoreCase("F")){
                    escritorCSV.reescreverArquivoCliente(clientes, ARQUIVO_CLIENTE);
                }
                                
            }*/
        }else System.out.println("Cliente nao encontrado");   
    }
    
    /**
     * Busca um cliente na lista interna com base no seu código de identificação.
     * <p>
     * Este método percorre a lista de clientes carregada na memória e compara
     * o código de cada um com o valor fornecido.
     * </p>
     *
     * @param idCliente O código único (int) do cliente a ser procurado. 
     * Não deve ser nulo ou vazio.
     * @return O objeto {@code Cliente} correspondente ao código, se encontrado. 
     * Retorna {@code null} se nenhum cliente com o código especificado
     * for encontrado na lista.
     */
    public Cliente buscarCliente(int idCliente){
        
        for(Cliente c : clientes){
            if(c.getId() == idCliente){
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
    public void listarClientes() {
    if (!clientes.isEmpty()) {
        for (Cliente c : clientes) {
            System.out.println(c.toString()); // chama o toString() da instância real
        }
    } else {
        System.out.println("Lista vazia.");
    }
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
    * Todas as alterações são aplicadas ao objeto em memória durante a sessão.
    *
    * @param idCliente O código numérico (ID) do cliente que se deseja editar.
    */
    public void editarCliente(int idCliente){
        Cliente c = buscarCliente(idCliente);
        if(c != null){
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
                            ((ClienteJuridico)c).setInscricaoEstadual(sc.nextInt());
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
            sc.close();
        }else System.out.println("Cliente nao encontrado.");
        
    }   
}

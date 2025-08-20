package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Fornecedor;
import io.Leitura;
import io.Escrita;

/**
 * Classe de serviço responsável pelo gerenciamento de fornecedores.
 * <p>
 * Encapsula operações de CRUD (criação, leitura, atualização e remoção) sobre
 * fornecedores, mantendo uma lista em memória e oferecendo integração com
 * arquivos CSV para persistência dos dados.
 * </p>
 *
 * <p>
 * Funcionalidades principais:
 * <ul>
 *   <li>Carregar fornecedores a partir de um arquivo CSV</li>
 *   <li>Cadastrar novos fornecedores interativamente</li>
 *   <li>Buscar fornecedores pelo código identificador</li>
 *   <li>Remover fornecedores existentes</li>
 *   <li>Listar todos os fornecedores cadastrados</li>
 *   <li>Editar dados de fornecedores já registrados</li>
 * </ul>
 *
 * @author Usuario
 */
public class GerenciaFornecedor {
    
    private List<Fornecedor> fornecedores;
    private  String ARQUIVO_FORNECEDOR;
    private Leitura leitorCSV;
    private Escrita escritorCSV;
    
    /**
     * Construtor padrão.
     * <p>
     * Inicializa a lista de fornecedores em memória e as instâncias utilitárias
     * para leitura e escrita em arquivos CSV.
     * </p>
     */
    public GerenciaFornecedor(String ARQUIVO_FORNECEDOR){
        fornecedores = new ArrayList<>();
        leitorCSV = new Leitura();
        escritorCSV = new Escrita();
        this.ARQUIVO_FORNECEDOR = ARQUIVO_FORNECEDOR;
    }
    
    /**
     * Carrega fornecedores de um arquivo CSV para a memória.
     * <p>
     * Cada linha do arquivo deve representar um fornecedor, com os campos
     * organizados na seguinte ordem:
     * <ol>
     *   <li>ID do fornecedor</li>
     *   <li>Nome da empresa</li>
     *   <li>Endereço</li>
     *   <li>Telefone</li>
     *   <li>CNPJ</li>
     *   <li>Pessoa de contato</li>
     * </ol>
     *
     * @param caminhoArquivo caminho para o arquivo CSV de fornecedores.
     */
    public void carregarFornecedorCSV(String caminhoArquivo){
        List<String[]> linhas = leitorCSV.lerArquivo(caminhoArquivo);
        
        for (String[] campos : linhas) {
            int idFornecedor = Integer.parseInt(campos[0]);
            String nomeEmpresa = campos[1];
            String endereco = campos[2];
            String telefone = campos[3];
            String cnpj = campos[4];
            String pessoaContato = campos[5];
        
            Fornecedor fornecedor = new Fornecedor(idFornecedor, nomeEmpresa, endereco, telefone, cnpj, pessoaContato);
            fornecedores.add(fornecedor);
        }
    this.ARQUIVO_FORNECEDOR = caminhoArquivo;   
    }
    /**
     * VERSÃO TERMINAL
    * Adiciona um novo fornecedor ao sistema e persiste a alteração no arquivo de dados.
    * <p>
    * Este método primeiro insere o objeto {@code Fornecedor} na lista em memória e,
    * em seguida, invoca o método de atualização do {@code escritorCSV} para
    * garantir que o novo fornecedor seja salvo permanentemente no arquivo CSV.
    *
    * @param sc O scanner que será utilizado.
    */
    public void inserirFornecedor(Scanner sc){

        int idFornecedor;

        while (true) {
            System.out.println("Digite o ID do fornecedor: ");
            idFornecedor = sc.nextInt();
            sc.nextLine();

            if (this.buscarFornecedor(idFornecedor) != null) {
                System.out.println("Já existe um fornecedor com esse ID! Digite novamente.");
            } else {
                break; // sai do loop quando o ID for válido
            }
        }

        System.out.println("Digite o nome da empresa: ");
        String nomeEmpresa = sc.nextLine();

        System.out.println("Digite o endereço: ");
        String endereco = sc.nextLine();

        System.out.println("Digite o telefone: ");
        String telefone = sc.nextLine();

        System.out.println("Digite o CNPJ: ");
        String cnpj = sc.nextLine();

        System.out.println("Digite o nome da pessoa de contato: ");
        String pessoaContato = sc.nextLine();

        Fornecedor fornecedor = new Fornecedor(idFornecedor, nomeEmpresa, endereco, telefone, cnpj, pessoaContato);

        fornecedores.add(fornecedor);
        
        String[] linha = new String[]{
                String.valueOf(fornecedor.getIdFornecedor()),
                fornecedor.getNomeEmpresa(),
                fornecedor.getEndereco(),
                fornecedor.getTelefone(),
                fornecedor.getCnpj(),
                fornecedor.getPessoaContato()
            };
            escritorCSV.escreverLinha(ARQUIVO_FORNECEDOR, linha);
        
        System.out.println("Fornecedor cadastrado com sucesso! ");
    }
    
        /**
     * VERSÃO INTERFACE GRÁFICA
    * Adiciona um novo fornecedor ao sistema e persiste a alteração no arquivo de dados.
    * <p>
    * Este método primeiro insere o objeto {@code Fornecedor} na lista em memória e,
    * em seguida, invoca o método de atualização do {@code escritorCSV} para
    * garantir que o novo fornecedor seja salvo permanentemente no arquivo CSV.
    *
     * @param idFornecedor
     * @param nomeEmpresa
     * @param endereco
     * @param telefone
     * @param cnpj
     * @param pessoaContato
    */
    public void inserirFornecedor(int
            idFornecedor, String nomeEmpresa, String endereco, String telefone, String cnpj, String pessoaContato){
        Fornecedor fornecedor = new Fornecedor(idFornecedor, nomeEmpresa, endereco, telefone, cnpj, pessoaContato);

        fornecedores.add(fornecedor);
        
        String[] linha = new String[]{
                String.valueOf(fornecedor.getIdFornecedor()),
                fornecedor.getNomeEmpresa(),
                fornecedor.getEndereco(),
                fornecedor.getTelefone(),
                fornecedor.getCnpj(),
                fornecedor.getPessoaContato()
            };
            escritorCSV.escreverLinha(ARQUIVO_FORNECEDOR, linha);
        
        System.out.println("Fornecedor cadastrado com sucesso! ");
    }
    
    
    /**
    * Remove um fornecedor do sistema com base no seu código de identificação.
    * <p>
    * O método primeiro utiliza o {@link #buscarFornecedor(int)} para encontrar a instância
    * do fornecedor correspondente ao código. Se encontrado, o fornecedor é removido
    * da lista em memória e o arquivo CSV é reescrito para refletir esta remoção.
    *
    * @param codigo O código inteiro (ID) do cliente que deve ser removido.
    */
    public void removerFornecedor(int codigo){
        Fornecedor f = buscarFornecedor(codigo);
        if(f != null){
            fornecedores.remove(f);
            reescreverFornecedoresCSV();
        }else System.out.println("Fornecedor nao encontrado.");
        
    }
    
    /**
     * Busca um fornecedor na lista interna com base no seu código de identificação.
     * <p>
     * Este método percorre a lista de fornecedores carregada na memória e compara
     * o código de cada um com o valor fornecido.
     * </p>
     *
     * @param codigo O código único (int) do fornecedor a ser procurado. 
     * Não deve ser nulo ou vazio.
     * @return O objeto {@code Fornecedor} correspondente ao código, se encontrado. 
     * Retorna {@code null} se nenhum fornecedor com o código especificado
     * for encontrado na lista.
     */
    public Fornecedor buscarFornecedor(int codigo){
        
        for(Fornecedor f : fornecedores){
            if(f.getIdFornecedor() == codigo){
                return f;
            }
        }return null;
    }
    
    /**
    * Imprime os detalhes de todos os fornecedores cadastrados na saída padrão (console).
    * <p>
    * Este método verifica se a lista interna de fornecedoress está vazia. Se estiver, exibe
    * uma mensagem informativa. Caso contrário, itera sobre a lista e imprime os dados
    * de cada fornecedor.
    */
    public void listarFornecedores(){
        
        if(!fornecedores.isEmpty()){
            for(Fornecedor f : fornecedores){
                System.out.println(f.toString());
            }
        }else System.out.println("Lista vazia.");  
    }
    
    /**
    * Inicia uma sessão interativa no console para editar os dados de um fornecedor específico.
    * <p>
    * Primeiramente, o método busca o fornecedor pelo código fornecido. Uma vez encontrado,
    * um menu de opções é exibido em loop, permitindo ao usuário escolher qual campo
    * do fornecedor deseja alterar. O loop continua até que a opção "Sair" (8) seja selecionada.
    * <p>
    * Todas as alterações são aplicadas ao objeto em memória durante a sessão e são
    * persistidas no arquivo CSV de uma só vez, apenas ao final do processo, quando
    * o usuário decide sair do menu de edição.
    *
    * @param codigo O código numérico (ID) do fornecedor que se deseja editar.
    */
    public void editarFornecedor(int codigo){
        Fornecedor f = buscarFornecedor(codigo);
        if(f != null){
            int op = 0;
            Scanner sc = new Scanner(System.in);

            while(op != 6){
                System.out.println("""
                                   1. Nome da Empresa
                                   2. Endereco
                                   3. Telefone
                                   4. CNPJ
                                   5. Pessoa de Contato
                                   6. Sair
                                   """);
                System.out.println("\nInsira uma opcao: ");
                op = sc.nextInt();
                sc.nextLine();

                switch(op){
                    case 1 ->{
                        System.out.println("Insira o novo nome da empresa: ");
                        f.setNomeEmpresa(sc.nextLine());
                    }
                    case 2 ->{
                        System.out.println("Insira o novo endereco: ");
                        f.setEndereco(sc.nextLine());
                    }
                    case 3 ->{
                        System.out.println("Insira o novo telefone: ");
                        f.setTelefone(sc.nextLine());
                    }
                    case 4 ->{
                        System.out.println("Insira o novo CNPJ: ");
                        f.setCnpj(sc.nextLine());
                    }
                    case 5 ->{
                        System.out.println("Insira a nova pessoa de contato: ");
                        f.setPessoaContato(sc.nextLine());
                    }
                    case 6 ->{}
                    default ->{
                        System.out.println("Valor invalido. Tente novamente.");
                    }
                }
            }
            sc.close();
            reescreverFornecedoresCSV();
            System.out.println("Fornecedor editado com sucesso!");
            
        }else{
            System.out.println("Fornecedor nao encontrado.");
        }
        
    } 
    
    private void reescreverFornecedoresCSV() {
        List<String[]> dados = new ArrayList<>();
        for (Fornecedor f : fornecedores) {
            dados.add(new String[]{
                String.valueOf(f.getIdFornecedor()),
                f.getNomeEmpresa(),
                f.getEndereco(),
                f.getTelefone(),
                f.getCnpj(),
                f.getPessoaContato()
            });
        }
        escritorCSV.escreverFornecedores(ARQUIVO_FORNECEDOR, dados);
    }
}

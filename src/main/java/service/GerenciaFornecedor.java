/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Fornecedor;
import io.Leitura;
import io.Escrita;

/**
 *
 * @author Usuario
 */
public class GerenciaFornecedor {
    
    private List<Fornecedor> fornecedores;
    private final String ARQUIVO_FORNECEDOR = "fornecedores.csv";
    private Leitura leitorCSV;
    private Escrita escritorCSV;
    
    public GerenciaFornecedor(){
        fornecedores = new ArrayList<>();
        leitorCSV = new Leitura();
        escritorCSV = new Escrita();
    }
    
    /**
    * Adiciona um novo fornecedor ao sistema e persiste a alteração no arquivo de dados.
    * <p>
    * Este método primeiro insere o objeto {@code Fornecedor} na lista em memória e,
    * em seguida, invoca o método de atualização do {@code escritorCSV} para
    * garantir que o novo fornecedor seja salvo permanentemente no arquivo CSV.
    *
    * @param fornecedor O objeto {@code Fornecedor} a ser adicionado. Este objeto deve estar
    * devidamente instanciado e preenchido.
    */
    public void inserirFornecedor(Fornecedor fornecedor){
        fornecedores.add(fornecedor);
        escritorCSV.atualizarArquivo();
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
            escritorCSV.atualizarArquivo(ARQUIVO_FORNECEDOR);
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

            while(op != 8){
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
            escritorCSV.atualizarArquivo(ARQUIVO_FORNECEDOR);
            sc.close();
        }else{
            System.out.println("Fornecedor nao encontrado.");
        }
        
    }   
}

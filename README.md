## Sistema de Gestão de Padaria

**(Prática baseada nos trabalhos elaborados pelo professor Vitor E. Silva Souza, UFES)**

[Link da organização em UML](https://lucid.app/lucidchart/6489d7bc-bc9a-4903-986c-0b3dabb231fd/edit?invitationId=inv_f00eb130-1baf-4d5a-bcbe-ecc79af7f850&page=hF_BlsUKGquW#)

## 1. Descrição do problema

A Padaria do Senhor Oak está tecnologicamente atrasada. O sistema de controle de contas ainda é feito com papel e caneta. A padaria possui uma clientela fiel, pois o Sr. Oak permite que os clientes anotem os produtos “na conta”, pagando apenas no fim do mês. Para viabilizar isso, ele também negocia prazos com seus fornecedores para pagar suas compras no início do mês seguinte.

Porém, ao final de cada mês, o Sr. Oak enfrenta sempre os mesmos problemas: não consegue calcular com precisão quanto tem a receber e quanto tem a pagar, já que tudo está anotado em cadernos.

Cansado dessa situação, ele decidiu informatizar a gestão da padaria e contratou você para desenvolver um sistema que permita:

* Cadastro de clientes e fornecedores;
* Cadastro de produtos;
* Registro de vendas fiadas e à vista;
* Controle de contas a receber e a pagar;
* Geração de relatórios mensais.

A padaria também trabalha com vendas à vista, que devem ser consideradas no fechamento mensal.

---

### 1.1 Controle de Clientes e Fornecedores

**Clientes com conta fiado:**

* Código identificador
* Nome completo
* Endereço
* Telefone
* Data de cadastro
* Tipo: Pessoa Física (CPF) ou Pessoa Jurídica (CNPJ e Inscrição Estadual)

**Fornecedores:**

* Código identificador
* Nome da empresa
* Endereço
* Telefone
* CNPJ
* Pessoa de contato

O sistema deve permitir cadastrar, editar, listar, buscar e remover clientes e fornecedores, diferenciando tipos de clientes por herança ou composição.

Os dados serão armazenados em arquivos separados: `clientes.csv` e `fornecedores.csv`.

---

### 1.2 Cadastro de Produtos

Cada produto deve conter os seguintes dados:

* Código do produto (inteiro)
* Descrição (texto)
* Estoque mínimo (inteiro)
* Quantidade atual em estoque (inteiro)
* Valor de custo (decimal, em Reais)
* Percentual de lucro (inteiro)

**Valor de venda = Valor de custo + (Valor de custo × Percentual de lucro)**

Funcionalidades obrigatórias incluem: cadastro, listagem, busca, atualização e remoção de produtos, com exibição do valor de venda calculado.

Os dados serão armazenados em `produtos.csv`, com separador ponto e vírgula.

---

### 1.3 Contas a Pagar e a Receber

**Compras:**

* Número da nota fiscal (inteiro)
* Fornecedor (código)
* Data da compra
* Produto comprado (código)
* Quantidade (inteiro)

O valor da compra é: `valor de custo × quantidade`

**Vendas:**

* Cliente (código), apenas se for pagamento fiado
* Data da venda
* Produto vendido (código)
* Quantidade (inteiro)
* Meio de pagamento (dinheiro, cheque, cartão débito/crédito, ticket, fiado)

Os registros devem ser armazenados em `compras.csv` e `vendas.csv`.

---

### 1.4 Relatórios

Relatórios que o sistema deve gerar:

* Total a pagar por fornecedor
* Total a receber por cliente
* Vendas e lucro por produto
* Vendas e lucro por forma de pagamento
* Estado do estoque, com alertas para estoques abaixo do mínimo

---

## 2. Formatos de Entrada e Saída

Os cadastros serão mantidos em planilhas eletrônicas (CSV com `;` como separador).

Arquivos:

* Clientes: `clientes.csv`
* Fornecedores: `fornecedores.csv`
* Produtos: `produtos.csv`
* Compras: `compras.csv`
* Vendas: `vendas.csv`

---

### 2.1 Entrada de Dados

**Cadastro de Clientes:**
`<código>;<nome>;<endereço>;<telefone>;<data de cadastro>;<tipo>;<cpf/cnpj>;<inscrição estadual>`

**Cadastro de Fornecedores:**
`<código>;<nome>;<endereço>;<telefone>;<cnpj>;<pessoa de contato>`

**Cadastro de Produtos:**
`<código>;<descrição>;<estoque mínimo>;<estoque atual>;<valor de custo>;<percentual de lucro>`

**Registro de Compras:**
`<nota fiscal>;<código fornecedor>;<data>;<código produto>;<quantidade>`

**Registro de Vendas:**
`<código cliente>;<data>;<código produto>;<quantidade>;<forma pagamento>`

Formas de pagamento:

* `$` Dinheiro
* `X` Cheque
* `D` Cartão de Débito
* `C` Cartão de Crédito
* `T` Ticket Alimentação
* `F` Fiado

---

### 2.2 Saída de Dados

Relatórios gerados:

* **1-apagar.csv**
  `<nome>;<cnpj>;<pessoa contato>;<telefone>;<valor total a pagar>`

* **2-areceber.csv**
  `<nome>;<tipo>;<cpf/cnpj>;<telefone>;<data>;<valor total a receber>`

* **3-vendasprod.csv**
  `<código>;<descrição>;<receita bruta>;<lucro>`

* **4-vendaspgto.csv**
  `<modo de pagamento>;<receita bruta>;<lucro>`

* **5-estoque.csv**
  `<código>;<descrição>;<quantidade>;<observações>`

**Observação:** "COMPRAR MAIS" se estoque < mínimo.

---

### 2.3 Tratamento de Exceções

* Tratar somente erros de entrada e saída (I/O).
* Mensagem: `Erro de I/O.`
* Formatos inválidos de dados podem ser ignorados.

---

## 3. Menu Principal

Opções:

* Cadastro

  * Novo Cliente
  * Novo Fornecedor
  * Novo Produto
* Registro de Vendas

  * Registrar Nova Venda
  * Listar Vendas
* Controle de Contas

  * Contas a Pagar
  * Contas a Receber
* Geração de Relatórios
* Sair

---

## 4. Organização das Classes

Estrutura modular, com pacotes e classes organizados logicamente.

src/
|--- main/
|   |--- model/
|   |--- service/
|   |--- io/
|   |--- repport/
|   |--- App.java
---

## 5. Condições de entrega

* Grupos de 4 a 5 pessoas
* Entrega: até **20/08/2025**
* Entrega fora do padrão: penalidade de 2 pontos
* Compilar nas máquinas do laboratório

Entrega pelo Google Classroom, em formato compactado.

---

## 6. Critérios de Avaliação

**Objetiva (10 pts):**

| Situação                       | Penalidade |
| ------------------------------ | ---------- |
| Grupo fora do tamanho          | -2         |
| Não compilou                   | -7         |
| Compilou com correções manuais | -2         |
| Sem compilação nem manual      | -10        |
| Sem saídas automáticas         | -5         |
| Sem saídas nem manual          | -7         |
| Diferenças leves               | -1         |
| Diferenças grandes             | -2         |
| Atraso                         | -2 por dia |

**Subjetiva (10 pts):**

* Princípios da OO (encapsulamento, modularidade, etc.)
* Legibilidade e estilo
* Eficiência
* Uso eficaz da API Java
* Entrevistas para esclarecimento do código

---

## 7. Pontos extras

| Funcionalidade                                                | Pontos |
| ------------------------------------------------------------- | ------ |
| Interface gráfica com menus                                   | até 2  |
| Interface gráfica para selecionar arquivos e gerar relatórios | até 1  |

**Nota máxima da disciplina permanece 10.**

---

## 8. Observações finais

* Erratas serão divulgadas em sala ou na página da disciplina
* Aulas de laboratório poderão ser usadas para o projeto a partir de **30/07**


## Coisas para fazer depois
* refatorar gerenciarProdutos para gerenciarProduto
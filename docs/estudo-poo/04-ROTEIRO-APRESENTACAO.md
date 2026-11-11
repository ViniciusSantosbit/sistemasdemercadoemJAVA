# Roteiros para apresentação oral

Os tempos incluem navegação pela IDE e pausas para apontar linhas. Abra os arquivos antecipadamente. A demonstração pode ser feita sobre o código, com os números fictícios abaixo.

## Roteiro A · 5 minutos

### 0:00–0:50 · Finalidade e interface

“Nosso projeto simula a operação de um supermercado. No menu temos cadastro, consulta, alteração e exclusão de categoria, fornecedor, funcionário, produto e venda. O caixa registra os itens de uma compra. O relatório consulta vendas entre duas datas e mostra o total das finalizadas. A interface é desktop, feita com Swing. Aqui no main, o Java agenda a abertura da janela que concentra os menus.”

Mostre `src/br/com/visao/TelaPrincipal.java:440–469,476–506`.

### 0:50–1:40 · Organização do código

“Dividimos o projeto em três pacotes. As telas ficam em visão. As classes que representam os dados, como Produto e Venda, ficam em controle. Em entidade ficam a conexão e as classes que executam SQL. Quando o formulário precisa consultar um produto, chama ManterProduto e recebe o objeto com dados que vieram do MySQL.”

Mostre a árvore de `src/br/com` e `src/br/com/entidade/ManterProduto.java:109–127`.

### 1:40–2:45 · Carrinho

“Vou simular um produto de quatro reais com estoque cinco. Digito ou seleciono o código de barras. Com quantidade dois, a tela verifica o estoque, cria um ItemVenda e passa para a Venda. Esse item tem subtotal de oito reais. A Venda mantém uma lista e soma os subtotais toda vez que adicionamos ou removemos uma linha. Se eu pedir mais quatro unidades do mesmo produto, ela considera que duas já estão reservadas neste carrinho e avisa que restam três.”

Mostre `src/br/com/visao/CadastroVenda.java:274–302`, `src/br/com/controle/ItemVenda.java:47–49` e `src/br/com/controle/Venda.java:86–108`.

### 2:45–3:55 · Finalização

“Na finalização eu informo operador e pagamento, com CPF opcional. Com dez reais em dinheiro, oito reais de total deixam dois de troco. A tela passa a venda a ManterVenda. O banco gera um número, o código usa esse número para gravar os itens e baixa duas unidades. O commit confirma as escritas juntas. Se houver erro durante a gravação, o DAO tenta rollback.”

Mostre `src/br/com/visao/CadastroVenda.java:325–368` e `src/br/com/entidade/ManterVenda.java:24–74`.

### 3:55–5:00 · POO e fechamento

“Os atributos de Produto e Venda são privados, e a classe oferece métodos para acessar ou mudar os dados. Temos também herança. Funcionário é um tipo de Pessoa, herda seus métodos e completa o getTipo. No carrinho, o relacionamento é diferente. A Venda guarda vários itens numa lista. São essas classes e chamadas que levam uma ação do usuário até um registro persistido e consultável no relatório.”

Mostre `src/br/com/controle/Pessoa.java:8–48`, `src/br/com/controle/Funcionario.java:7–23` e a chamada do relatório em `src/br/com/visao/FormRelatorioVendas.java:137–161`.

## Roteiro B · 8 a 10 minutos

### 0:00–1:05 · Entrada, eventos e controle de acesso

“O Java começa no main de TelaPrincipal, definido nas propriedades do projeto. O main configura a aparência e coloca a criação da janela na fila do Swing. Depois disso a aplicação reage a eventos. O listener recebe o clique e chama um método da tela. Aqui dá para ver que o menu de relatórios pede autorização administrativa. O menu de abertura do caixa abre direto.”

Mostre `nbproject/project.properties:76` e `src/br/com/visao/TelaPrincipal.java:440–506`. Aponte um listener em `TelaPrincipal.java:253–262`.

### 1:05–2:15 · Pacotes, SQL e CRUD

“A interface lê os campos. O modelo representa os dados. A classe de persistência usa o driver do MySQL pela API JDBC. No cadastro de produto, a tela cria o objeto e o DAO consulta se o código de barras já existe. O banco também tem restrição de unicidade. O produto carrega o código da categoria e uma consulta com JOIN traz o nome para exibir. Nas outras operações, o código do registro identifica o alvo para pesquisar, alterar ou apagar.”

Mostre `src/br/com/visao/CadastroProduto.java:149–180`, `src/br/com/entidade/ManterProduto.java:17–39,81–107` e `database/supermercado_trabalho.sql:39–47`.

### 2:15–4:10 · Carrinho por dentro

“Vou acompanhar um item. A busca por barras devolve uma referência de Produto quando acha o registro. O clique valida a quantidade, soma o que já está no carrinho para o mesmo código e compara com a disponibilidade. A tela cria ItemVenda e copia código, quantidade e preço. Venda recebe a referência. Ela chama calcularTotal, que começa em zero e percorre a lista. O item sabe calcular o subtotal, e a venda sabe somar.”

“Por exemplo, duas unidades a quatro reais dão oito. Se acrescentarmos um produto de três reais e cinquenta, a venda chega a onze e cinquenta. Ao remover a segunda linha, volta a oito. Enquanto estamos montando o carrinho, essas alterações são em memória.”

Mostre `src/br/com/visao/CadastroVenda.java:274–323`, `src/br/com/controle/Venda.java:86–108` e `src/br/com/controle/ItemVenda.java:47–49`.

### 4:10–6:10 · Confirmação e estoque

“A tela coleta CPF opcional, operador e meio de pagamento. Em dinheiro, compara o recebido com o total e calcula o troco. O DAO abre a conexão e começa uma transação. Ele confere o estoque, grava o cabeçalho e pega a chave gerada. Cada item usa essa chave para apontar para a venda. O DAO reduz os saldos e faz commit.”

“Tem um detalhe importante. O código da venda é escrito no objeto antes do commit. Se uma etapa posterior falhar e o DAO tratar o erro internamente, a tela pode mostrar sucesso por causa desse código. Também seria preciso proteger a consulta e a baixa contra dois caixas que leem o mesmo saldo.”

Mostre `src/br/com/visao/CadastroVenda.java:325–368` e `src/br/com/entidade/ManterVenda.java:24–74`.

### 6:10–7:15 · Edição e relatório

“Na edição, lemos o status anterior para decidir o ajuste. Cancelar devolve as unidades e reativar reduz o saldo novamente. A exclusão apaga primeiro os itens para respeitar a chave estrangeira. Para o relatório, a consulta usa as duas datas e o formulário soma só as vendas finalizadas. A impressão usa a própria tabela.”

Mostre `src/br/com/entidade/ManterVenda.java:76–95,121–123,170–200` e `src/br/com/visao/FormRelatorioVendas.java:137–179`.

### 7:15–8:40 · Conceitos e fechamento

“A Venda tem uma lista privada de itens. A operação adicionarItem representa a regra de incluir e recalcular. Existe uma limitação: getItens expõe a lista e permite mexer por fora da regra. Funcionário especializa Pessoa, que é abstrata e define getTipo. O domínio tem essa sobrescrita, e um exemplo de chamada por contrato de interface aparece quando o Swing executa o Runnable que registramos. No cadastro de produto, dois construtores permitem abrir pela tela principal ou pelo caixa com um aviso de conclusão.”

“Para fechar, temos objetos diferentes cuidando da interface, do carrinho e da persistência. Dá para acompanhar essa divisão desde o clique até a gravação do estoque e o relatório.”

Mostre `src/br/com/controle/Funcionario.java:7–23`, `src/br/com/visao/TelaPrincipal.java:501–505` e `src/br/com/visao/CadastroProduto.java:25–42`.

### Ordem de abas

1. `nbproject/project.properties`
2. `src/br/com/visao/TelaPrincipal.java`
3. `src/br/com/visao/CadastroProduto.java`
4. `src/br/com/entidade/ManterProduto.java`
5. `src/br/com/visao/CadastroVenda.java`
6. `src/br/com/controle/ItemVenda.java`
7. `src/br/com/controle/Venda.java`
8. `src/br/com/entidade/ManterVenda.java`
9. `src/br/com/visao/FormRelatorioVendas.java`
10. `src/br/com/controle/Pessoa.java` e `src/br/com/controle/Funcionario.java`

Para ficar perto de 10 minutos, faça o cálculo de duas linhas com a turma e volte ao trecho de estoque para explicar o rollback. Para ficar perto de 8, mostre edição e relatório na mesma passagem e use um item no exemplo.

# Perguntas do professor · 30 questões

Os caminhos com `visao/`, `controle/` e `entidade/` começam em `src/br/com/`. Tente responder em voz alta antes de abrir a resposta.

## Nível fácil

### 1. Qual é a entrada oficial?

- **Resposta técnica.** A JVM chama o main estático de TelaPrincipal, definido nas propriedades NetBeans.
- **Em termos simples.** É o primeiro método da aplicação.
- **Resposta oral.** “O programa começa no main de TelaPrincipal e abre a janela Swing.”
- **No código.** `nbproject/project.properties:76` e `visao/TelaPrincipal.java:476–506`.
- **Repregunta.** Por que outros arquivos também têm main?

### 2. O que são classe e objeto no caixa?

- **Resposta técnica.** Venda declara estrutura e métodos; cada new Venda() cria uma instância.
- **Em termos simples.** A classe é a ficha em branco e o objeto é uma ficha preenchida.
- **Resposta oral.** “Cada carrinho é uma instância de Venda.”
- **No código.** `controle/Venda.java:12–20` e `visao/CadastroVenda.java:370–382`.
- **Repregunta.** Duas vendas compartilham a mesma lista?

### 3. O que um construtor faz?

- **Resposta técnica.** Inicializa a instância. CadastroVenda() monta a janela e carrega dados.
- **Em termos simples.** É a preparação inicial da tela.
- **Resposta oral.** “O construtor configura componentes e traz produtos e operadores.”
- **No código.** `visao/CadastroVenda.java:29–77`.
- **Repregunta.** Construtor declara tipo de retorno?

### 4. Para que serve private?

- **Resposta técnica.** Restringe acesso direto. O preço de Produto é acessado por métodos públicos.
- **Em termos simples.** O campo fica atrás da interface da classe.
- **Resposta oral.** “A tela usa getPreco e setPreco para acessar o preço privado.”
- **No código.** `controle/Produto.java:12,41–47`.
- **Repregunta.** Isso impede preço negativo?

### 5. O que significa this.codigo = codigo?

- **Resposta técnica.** O lado esquerdo é o campo do objeto atual; o direito é o parâmetro.
- **Em termos simples.** Copia a entrada para a ficha do objeto.
- **Resposta oral.** “Uso this para distinguir o atributo do parâmetro.”
- **No código.** `controle/Venda.java:26–28`.
- **Repregunta.** Qual lado continua existindo depois da chamada?

### 6. Como um item calcula o subtotal?

- **Resposta técnica.** getSubtotal retorna quantidade multiplicada por precoUnitario como double.
- **Em termos simples.** Três unidades a quatro reais dão doze.
- **Resposta oral.** “O item multiplica o preço unitário pela quantidade.”
- **No código.** `controle/ItemVenda.java:47–49`.
- **Repregunta.** Esse método modifica o objeto?

### 7. Que tipo representa o código de barras?

- **Resposta técnica.** String, capaz de manter zeros iniciais e permitir busca textual.
- **Em termos simples.** É um identificador, sem conta aritmética.
- **Resposta oral.** “Guardamos código de barras como texto.”
- **No código.** `controle/Produto.java:11` e `entidade/ManterProduto.java:109–127`.
- **Repregunta.** Qual seria a consequência de convertê-lo em int?

### 8. O que significa void?

- **Resposta técnica.** A chamada não devolve valor, embora possa alterar estado.
- **Em termos simples.** Ela faz uma ação sem entregar um resultado direto.
- **Resposta oral.** “Adicionar item é void porque altera a própria venda.”
- **No código.** `controle/Venda.java:92–95`.
- **Repregunta.** Pode haver return em método void?

### 9. O que acontece quando as barras não existem?

- **Resposta técnica.** PesquisarCodigoBarras retorna null e o evento avisa e encerra.
- **Em termos simples.** O caixa para antes de montar o item.
- **Resposta oral.** “O caixa testa null e mostra produto não encontrado.”
- **No código.** `entidade/ManterProduto.java:109–127` e `visao/CadastroVenda.java:274–282`.
- **Repregunta.** Se tirar o teste, qual exceção pode surgir?

### 10. Quais pagamentos aparecem?

- **Resposta técnica.** Dinheiro, PIX, cartão de débito e de crédito. Dinheiro calcula troco.
- **Em termos simples.** Escolhe-se uma opção para registrar na venda.
- **Resposta oral.** “O sistema grava a forma escolhida e calcula troco em dinheiro.”
- **No código.** `visao/CadastroVenda.java:54–57,341–356`.
- **Repregunta.** O aplicativo confirma PIX externamente?

## Nível intermediário

### 11. Como o clique chega ao banco?

- **Resposta técnica.** O listener chama o evento, o formulário popula a Venda, e ManterVenda usa JDBC e SQL.
- **Em termos simples.** O botão é o começo de uma sequência de chamadas.
- **Resposta oral.** “A tela coleta os dados e o DAO grava venda e itens.”
- **No código.** `visao/CadastroVenda.java:325–368` e `entidade/ManterVenda.java:24–74`.
- **Repregunta.** Onde é atribuído o ID?

### 12. Onde aparece herança?

- **Resposta técnica.** Funcionario herda Pessoa e os DAOs específicos herdam DAO.
- **Em termos simples.** A subclasse aproveita dados ou operações comuns.
- **Resposta oral.** “Funcionario especializa Pessoa, e ManterVenda reaproveita conexão de DAO.”
- **No código.** `controle/Funcionario.java:7–22` e `entidade/ManterVenda.java:17–25`.
- **Repregunta.** Os atributos privados de Pessoa são acessíveis diretamente?

### 13. O que prova a sobrescrita de getTipo?

- **Resposta técnica.** Pessoa declara a operação abstrata, e Funcionario fornece método de mesma assinatura com @Override.
- **Em termos simples.** O funcionário completa um comportamento previsto pela classe base.
- **Resposta oral.** “A classe abstrata define o contrato e Funcionario fornece o retorno.”
- **No código.** `controle/Pessoa.java:48` e `controle/Funcionario.java:19–22`.
- **Repregunta.** Há chamada a getTipo no fluxo de vendas?

### 14. Que sobrecarga existe no cadastro de produtos?

- **Resposta técnica.** Dois construtores recebem listas de parâmetros diferentes. O que recebe Consumer delega com this().
- **Em termos simples.** A janela pode receber uma ação para avisar depois de salvar.
- **Resposta oral.** “O caixa abre a tela com callback e o menu usa o construtor sem parâmetros.”
- **No código.** `visao/CadastroProduto.java:25–42,161–166` e `visao/CadastroVenda.java:38–45`.
- **Repregunta.** Quando accept é invocado?

### 15. Como a venda reúne itens?

- **Resposta técnica.** ArrayList de referências ItemVenda, criadas fora da venda e adicionadas por método de instância.
- **Em termos simples.** O carrinho agrupa várias linhas de compra.
- **Resposta oral.** “A venda guarda a lista e recalcula quando recebe um item.”
- **No código.** `controle/Venda.java:86–108` e `visao/CadastroVenda.java:296–302`.
- **Repregunta.** Por que agregação é uma descrição razoável?

### 16. Quando o saldo muda?

- **Resposta técnica.** A adição reserva quantidades só na tela. O UPDATE de estoque ocorre na transação de finalização.
- **Em termos simples.** Escolher produtos no carrinho e vender são duas etapas.
- **Resposta oral.** “Ao adicionar eu calculo disponibilidade local. Na gravação eu baixo o estoque.”
- **No código.** `visao/CadastroVenda.java:288–302` e `entidade/ManterVenda.java:53–68`.
- **Repregunta.** O que acontece se eu remover um item antes de finalizar?

### 17. Por que PreparedStatement?

- **Resposta técnica.** Vincula valores aos placeholders e permite tipagem com setInt, setString e setDouble.
- **Em termos simples.** Os dados entram em espaços reservados do comando.
- **Resposta oral.** “Uso parâmetros para enviar valores ao SQL.”
- **No código.** `entidade/ManterVenda.java:41–48`.
- **Repregunta.** Quais índices têm os parâmetros JDBC?

### 18. Por que a consulta de venda usa JOIN?

- **Resposta técnica.** Obtém o nome do funcionário relacionado pelo código e mapeia para a venda.
- **Em termos simples.** A venda guarda a ligação e a consulta traz o nome legível.
- **Resposta oral.** “O JOIN busca o operador vinculado à venda.”
- **No código.** `entidade/ManterVenda.java:19–22,202–211`.
- **Repregunta.** De onde vem o nome do produto nos itens?

### 19. Como o relatório lida com canceladas?

- **Resposta técnica.** O SELECT filtra o período. A tela lista as vendas retornadas e soma só as FINALIZADA.
- **Em termos simples.** A tabela é mais ampla do que o valor somado.
- **Resposta oral.** “Canceladas aparecem na lista e ficam fora do total vendido.”
- **No código.** `entidade/ManterVenda.java:121–123` e `visao/FormRelatorioVendas.java:137–161`.
- **Repregunta.** O dia final entra no filtro?

### 20. Qual é a diferença entre == e equals neste projeto?

- **Resposta técnica.** O filtro compara int por valor e a escolha de dinheiro compara String por conteúdo.
- **Em termos simples.** Códigos numéricos e textos usam critérios apropriados.
- **Resposta oral.** “Uso == nos códigos inteiros e equals nos textos.”
- **No código.** `visao/CadastroVenda.java:288–290,345`.
- **Repregunta.** O que == compara quando os operandos são referências?

## Nível difícil

### 21. Como pode haver falso sucesso após rollback?

- **Resposta técnica.** O ID é escrito antes do commit. A captura interna pode desfazer o SQL e retornar normalmente com o ID positivo.
- **Em termos simples.** O número existe no objeto mesmo se a venda foi desfeita.
- **Resposta oral.** “A tela confunde chave gerada com confirmação da transação.”
- **No código.** `entidade/ManterVenda.java:49–73` e `visao/CadastroVenda.java:357–367`.
- **Repregunta.** Como comunicar sucesso explicitamente?

### 22. A checagem do estoque é segura para dois caixas?

- **Resposta técnica.** O SELECT comum precede UPDATE sem condição de saldo. Duas transações podem validar o mesmo saldo.
- **Em termos simples.** As duas telas podem contar com as mesmas unidades.
- **Resposta oral.** “A transação agrupa as escritas, e ainda é preciso proteger a disponibilidade concorrente.”
- **No código.** `entidade/ManterVenda.java:28–40,63–68`.
- **Repregunta.** Como usar UPDATE condicional e contagem de linhas?

### 23. O que acontece ao reativar uma venda?

- **Resposta técnica.** O DAO escolhe sinal de subtração e atualiza estoque pelo JOIN. A rotina carece de verificação de saldo.
- **Em termos simples.** A reativação tenta tirar novamente os produtos.
- **Resposta oral.** “Reativar reduz o estoque, com risco de saldo negativo.”
- **No código.** `entidade/ManterVenda.java:170–200`.
- **Repregunta.** O que ocorre se falhar depois de ajustar o estoque?

### 24. Por que apagar itens antes de apagar a venda?

- **Resposta técnica.** FK impede deixar itens dependentes sem cabeçalho. A ordem respeita a dependência.
- **Em termos simples.** Primeiro removo as linhas que apontam para a venda.
- **Resposta oral.** “A venda tem itens com chave estrangeira, então o DAO apaga os itens primeiro.”
- **No código.** `database/supermercado_trabalho.sql:62–70` e `entidade/ManterVenda.java:76–95`.
- **Repregunta.** A exclusão inteira tem commit explícito?

### 25. Como um getter pode quebrar a regra do total?

- **Resposta técnica.** getItens devolve a lista real e seus itens mutáveis, permitindo alterações fora do recálculo.
- **Em termos simples.** Alguém pode mudar o carrinho por outro caminho.
- **Resposta oral.** “Expor a lista permite mudar os itens sem atualizar o total.”
- **No código.** `controle/Venda.java:82–108`.
- **Repregunta.** Uma cópia rasa resolve mutação dos itens?

### 26. Onde podem escapar erros de entrada na finalização?

- **Resposta técnica.** A seleção do funcionário e a conversão do recebido ocorrem antes do try de gravação.
- **Em termos simples.** Combo vazio ou texto malformado pode interromper o clique.
- **Resposta oral.** “Essas leituras precisam de tratamento próprio para produzir um aviso claro.”
- **No código.** `visao/CadastroVenda.java:341–358`.
- **Repregunta.** Qual exceção ocorre com valor monetário textual inválido?

### 27. CPF válido é garantido?

- **Resposta técnica.** A validação retira ponto e hífen, aceita vazio e verifica comprimento. Carece de teste de dígitos e verificador.
- **Em termos simples.** Onze letras podem passar pela verificação da tela.
- **Resposta oral.** “A regra atual confere o tamanho do CPF, sem validar seu conteúdo.”
- **No código.** `visao/CadastroVenda.java:331–340`.
- **Repregunta.** Como separar formato de validação dos dígitos verificadores?

### 28. A arquitetura isola o domínio da interface?

- **Resposta técnica.** Há divisão de pacotes, mas telas instanciam DAOs e DAOs usam diálogos Swing.
- **Em termos simples.** Existe separação parcial de papéis e dependência cruzada.
- **Resposta oral.** “Chamaria de organização por apresentação, domínio e persistência, com acoplamento entre tela e DAO.”
- **No código.** `visao/CadastroVenda.java:357–367` e `entidade/ManterVenda.java:34–36,69–72`.
- **Repregunta.** Quem deveria transformar falha de persistência em mensagem?

### 29. Por que double é delicado para valores monetários?

- **Resposta técnica.** A representação binária aproxima muitos decimais. O SQL grava em DECIMAL com escala 2.
- **Em termos simples.** Centavos podem sofrer arredondamentos em pontos diferentes.
- **Resposta oral.** “Para exatidão monetária, seria melhor definir escala e arredondamento com BigDecimal.”
- **No código.** `controle/ItemVenda.java:47–49` e `database/supermercado_trabalho.sql:39–69`.
- **Repregunta.** Construir BigDecimal a partir de double recupera a entrada decimal original?

### 30. Onde a autorização é aplicada?

- **Resposta técnica.** Menus administrativos e atalho de produto chamam um diálogo com credenciais fixas. Operações de venda abrem diretamente.
- **Em termos simples.** O acesso é verificado em pontos da interface.
- **Resposta oral.** “O controle é local aos caminhos que solicitam o diálogo.”
- **No código.** `visao/LoginAdministrador.java:12–42` e `visao/TelaPrincipal.java:296–471`.
- **Repregunta.** Abrir um formulário pelo main auxiliar passa pelo mesmo controle?

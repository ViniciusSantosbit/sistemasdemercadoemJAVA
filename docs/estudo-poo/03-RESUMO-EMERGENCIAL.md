# Revisão de emergência · 10 a 15 minutos

## 0–3 min · O que o sistema faz

Aplicativo desktop Java 17 com Swing. O menu abre telas de cadastro, listagem, alteração e exclusão de categorias, fornecedores, funcionários, produtos e vendas. O caixa registra itens e baixa estoque na finalização. O relatório lista vendas por intervalo e totaliza as finalizadas. Swing recebe os dados, objetos Java os representam, JDBC executa SQL e MySQL persiste os registros. Veja `nbproject/project.properties:36–50,76` e `src/br/com/visao/TelaPrincipal.java:296–506`.

| Classe | O que dizer |
|---|---|
| `TelaPrincipal` | Entrada oficial e menus, com verificação administrativa em operações específicas. |
| `CadastroVenda` | Coordena o caixa, converte entradas, consulta produtos, valida itens e pede a gravação. |
| `Venda` | Guarda os itens e soma os subtotais. |
| `ManterVenda` | Grava cabeçalho e itens, movimenta estoque e consulta vendas. |
| `DAO` | Abre e fecha a conexão JDBC reutilizada pelas classes `ManterXxx`. |

## 3–8 min · Dez métodos para saber localizar

Os caminhos abaixo começam em `src/br/com/`.

| Método e arquivo | Resposta curta |
|---|---|
| `visao/TelaPrincipal.java:476–506 · main` | Agenda a abertura da janela Swing. |
| `visao/CadastroVenda.java:274–312 · jBadicionarActionPerformed` | Busca por barras e desconta da disponibilidade as unidades já no carrinho. |
| `entidade/ManterProduto.java:109–127 · PesquisarCodigoBarras` | Retorna um Produto encontrado ou null. |
| `controle/Venda.java:92–95 · adicionarItem` | Adiciona o objeto e recalcula o total. |
| `controle/ItemVenda.java:47–49 · getSubtotal` | Multiplica quantidade pelo preço unitário. |
| `controle/Venda.java:103–108 · calcularTotal` | Zera o total e soma cada subtotal. |
| `visao/CadastroVenda.java:325–368 · jBfinalizarActionPerformed` | Valida carrinho e CPF, coleta operador e pagamento, calcula troco quando em dinheiro e chama o DAO. |
| `entidade/ManterVenda.java:24–74 · inserir` | Confere estoque, grava venda e itens, baixa estoque e confirma com commit. |
| `entidade/ManterVenda.java:170–200 · editarVenda` | Ajusta estoque se o status muda entre finalizada e cancelada. |
| `visao/FormRelatorioVendas.java:137–161 · jBgerarActionPerformed` | Busca por período, monta tabela e soma as finalizadas. |

Ensaie com estoque 5 e preço R$ 4. Adicione duas unidades, veja R$ 8 no total e três unidades disponíveis para novas adições. Ao finalizar com R$ 10 em dinheiro, o troco é R$ 2 e o estoque gravado passa a 3 se a operação concluir.

## 8–12 min · Cinco respostas obrigatórias

1. **Como a venda chega ao banco?** O clique aciona o formulário. Ele passa a referência da `Venda` a `ManterVenda.inserir`, que usa SQL parametrizado.
2. **Onde está a herança?** `Funcionario extends Pessoa`, as classes `ManterXxx extends DAO` e as janelas estendem `JFrame`. `Pessoa` é abstrata e `Funcionario` sobrescreve `getTipo`.
3. **Como os itens se relacionam com a venda?** A venda guarda referências `ItemVenda` em um `ArrayList`. O caixa cria os itens e entrega à venda. O banco usa a chave estrangeira `item_venda.codigo_venda`.
4. **O que garante o total?** Cada item multiplica quantidade por preço. Adicionar e remover chamam o recálculo. A lista exposta permite contornar essa regra.
5. **O que acontece se a gravação falhar?** O DAO tenta desfazer as escritas com rollback. Existe uma falha de confirmação se o código já foi preenchido antes do erro e a tela o interpreta como sucesso.

## 12–15 min · Vocabulário e limites

`new` cria objeto e chama construtor. `this` aponta para o objeto atual. `private` restringe acesso. `public` expõe a operação. `static` pertence à classe. `void` indica ausência de valor de retorno. `return` encerra o método. `null` indica ausência de objeto. `extends` declara herança e `@Override` marca sobrescrita. `try/catch` trata exceções. `String.equals` compara conteúdo.

O pagamento é registrado como forma escolhida, com cálculo de troco para dinheiro. O CPF é opcional. Ausências no código examinado incluem cadastro de clientes, descontos e integração financeira. Consulte `CadastroVenda.java:29–77,325–368` em `visao` e `database/supermercado_trabalho.sql:14–70`.

Se tiver 30 minutos, faça esta revisão em 12, leia `CadastroVenda.java:274–368` e `ManterVenda.java:24–74` em 10 e ensaie as cinco respostas em 8.

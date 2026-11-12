/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.visao;

import br.com.controle.Funcionario;
import br.com.controle.ItemVenda;
import br.com.controle.Produto;
import br.com.controle.Venda;
import br.com.entidade.ManterFuncionario;
import br.com.entidade.ManterProduto;
import br.com.entidade.ManterVenda;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sena
 */
public class CadastroVenda extends javax.swing.JFrame {

    /**
     * Creates new form CadastroVenda
     */
    public CadastroVenda() {
        initComponents();
        configurarInterface();
        jCproduto.addActionListener(evt -> {
            OpcaoProduto opcao = (OpcaoProduto) jCproduto.getSelectedItem();
            if (opcao != null && opcao.produto() != null) {
                jTcodigoBarras.setText(opcao.produto().getCodigoBarras());
            }
        });
        jBnovoProduto.addActionListener(evt -> {
            if (!LoginAdministrador.solicitar(this)) {
                return;
            }
            CadastroProduto tela = new CadastroProduto(produto -> carregarProdutos(produto.getCodigoBarras()));
            tela.setLocationRelativeTo(this);
            tela.setVisible(true);
        });
        carregarProdutos(null);
        jTquantidade.setText("1");
        // ENTER no codigo de barras ja adiciona o item (como o leitor do caixa)
        jTcodigoBarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBadicionarActionPerformed(evt);
            }
        });
        jCpagamento.addItem("Dinheiro");
        jCpagamento.addItem("PIX");
        jCpagamento.addItem("Cartao de Debito");
        jCpagamento.addItem("Cartao de Credito");
        jTbitens.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jBremover.setEnabled(false);
        jTbitens.getSelectionModel().addListSelectionListener(evt ->
                jBremover.setEnabled(jTbitens.getSelectedRow() >= 0));
        // carrega os funcionarios (operadores) no combo
        try {
            ManterFuncionario mf = new ManterFuncionario();
            ArrayList<Funcionario> funcionarios = mf.PesquisarTudo();
            for (int i = 0; i < funcionarios.size(); i++) {
                jCfuncionario.addItem(funcionarios.get(i).getCodigo() + " - " + funcionarios.get(i).getNome());
            }
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
    }

    private void configurarInterface() {
        jLabel1.setLabelFor(jCfuncionario);
        jLabel1.setDisplayedMnemonic(KeyEvent.VK_O);
        jLabel2.setLabelFor(jTcpf);
        jLabel2.setDisplayedMnemonic(KeyEvent.VK_F);
        jLabel3.setText("Código de barras:");
        jLabel3.setLabelFor(jTcodigoBarras);
        jLabel3.setDisplayedMnemonic(KeyEvent.VK_B);
        jLproduto.setLabelFor(jCproduto);
        jLproduto.setDisplayedMnemonic(KeyEvent.VK_P);
        jLabel4.setLabelFor(jTquantidade);
        jLabel4.setDisplayedMnemonic(KeyEvent.VK_Q);
        jLabel5.setLabelFor(jCpagamento);
        jLabel5.setDisplayedMnemonic(KeyEvent.VK_M);
        jBadicionar.setMnemonic(KeyEvent.VK_A);
        jBremover.setMnemonic(KeyEvent.VK_R);
        jTcodigoBarras.getAccessibleContext().setAccessibleDescription("Pressione Enter para adicionar o item.");
        jTbitens.getAccessibleContext().setAccessibleName("Itens da venda");
        jTbitens.setRowHeight(32);
        jTbitens.getColumnModel().getColumn(0).setHeaderValue("Código");
        jTbitens.getColumnModel().getColumn(2).setHeaderValue("Quantidade");
        jTbitens.getColumnModel().getColumn(3).setHeaderValue("Preço unitário");
        jTbitens.getColumnModel().getColumn(1).setPreferredWidth(300);

        jLtotal.setFont(jLtotal.getFont().deriveFont(Font.BOLD, 22f));
        jLtotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLtotal.setForeground(TemaInterface.DESTAQUE);
        jBfinalizar.setFont(jBfinalizar.getFont().deriveFont(Font.BOLD));
        setMinimumSize(getMinimumSize());
    }

    private record OpcaoProduto(Produto produto) {
        @Override
        public String toString() {
            return produto == null ? "Selecionar produto" : produto.getCodigo() + " - " + produto.getNome();
        }
    }

    private void carregarProdutos(String codigoBarras) {
        try {
            javax.swing.DefaultComboBoxModel<OpcaoProduto> opcoes = new javax.swing.DefaultComboBoxModel<>();
            opcoes.addElement(new OpcaoProduto(null));
            for (Produto produto : new ManterProduto().PesquisarTudo()) {
                OpcaoProduto opcao = new OpcaoProduto(produto);
                opcoes.addElement(opcao);
                if (produto.getCodigoBarras().equals(codigoBarras)) {
                    opcoes.setSelectedItem(opcao);
                }
            }
            jCproduto.setModel(opcoes);
            if (codigoBarras != null) {
                jTcodigoBarras.setText(codigoBarras);
            }
        } catch (Exception ex) {
            Logger.getLogger(CadastroVenda.class.getName()).log(Level.SEVERE, "Erro ao carregar produtos", ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jCfuncionario = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTcpf = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTcodigoBarras = new javax.swing.JTextField();
        jLproduto = new javax.swing.JLabel();
        jCproduto = new javax.swing.JComboBox<>();
        jBnovoProduto = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTquantidade = new javax.swing.JTextField();
        jBadicionar = new javax.swing.JButton();
        jBremover = new javax.swing.JButton();
        jLtotal = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jCpagamento = new javax.swing.JComboBox<>();
        jBfinalizar = new javax.swing.JButton();
        jBnova = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTbitens = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Caixa - Nova Venda");

        jLabel1.setText("Operador do caixa:");

        jLabel2.setText("CPF na nota (opcional):");

        jLabel3.setText("Codigo de barras:");
        jLproduto.setText("Produto:");
        jLproduto.setLabelFor(jCproduto);
        jBnovoProduto.setText("Cadastrar Produto");

        jLabel4.setText("Quantidade:");

        jBadicionar.setText("Adicionar Item");
        jBadicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBadicionarActionPerformed(evt);
            }
        });

        jBremover.setText("Remover item selecionado");
        jBremover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBremoverActionPerformed(evt);
            }
        });

        jLtotal.setText("TOTAL: R$ 0,00");

        jLabel5.setText("Forma de pagamento:");

        jBfinalizar.setText("Finalizar Venda");
        jBfinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBfinalizarActionPerformed(evt);
            }
        });

        jBnova.setText("Nova Venda");
        jBnova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBnovaActionPerformed(evt);
            }
        });

        jTbitens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Produto", "Qtd", "Preco unit.", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTbitens);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGap(18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(10)
.addComponent(jCfuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(10)
.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(10)
.addComponent(jTcpf, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
.addGroup(layout.createSequentialGroup()
.addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(10)
.addComponent(jTcodigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(10)
.addComponent(jLproduto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(10)
.addComponent(jCproduto, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(10)
.addComponent(jBnovoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
.addGroup(layout.createSequentialGroup()
.addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(10)
.addComponent(jTquantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(10)
.addComponent(jBadicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
.addGroup(layout.createSequentialGroup()
.addComponent(jBremover, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(18, 18, Short.MAX_VALUE)
.addComponent(jLtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
.addGroup(layout.createSequentialGroup()
.addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(10)
.addComponent(jCpagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(18, 18, Short.MAX_VALUE)
.addComponent(jBnova, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(10)
.addComponent(jBfinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane1, 840, 920, Short.MAX_VALUE))
            .addGap(18));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGap(18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jCfuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jTcpf, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jTcodigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jLproduto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jCproduto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jBnovoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jTquantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jBadicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18)
            .addComponent(jScrollPane1, 230, 280, Short.MAX_VALUE).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jBremover, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jLtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jCpagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jBnova, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jBfinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(18));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jBadicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBadicionarActionPerformed
        // TODO add your handling code here:
        try {
            ManterProduto mp = new ManterProduto();
            Produto p = mp.PesquisarCodigoBarras(jTcodigoBarras.getText().trim());
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Produto nao encontrado!");
                return;
            }
            int quantidade = Integer.parseInt(jTquantidade.getText());
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero.");
                return;
            }
            int reservado = venda.getItens().stream()
                    .filter(item -> item.getCodigoProduto() == p.getCodigo())
                    .mapToInt(ItemVenda::getQuantidade).sum();
            int disponivel = Math.max(0, p.getEstoque() - reservado);
            if (quantidade > disponivel) {
                JOptionPane.showMessageDialog(this, "Estoque insuficiente! Disponivel: " + disponivel);
                return;
            }
            ItemVenda item = new ItemVenda();
            item.setCodigoProduto(p.getCodigo());
            item.setNomeProduto(p.getNome());
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(p.getPreco());
            venda.adicionarItem(item);
            atualizarTabela();
            jCproduto.setSelectedIndex(0);
            jTcodigoBarras.setText("");
            jTquantidade.setText("1");
            jTcodigoBarras.requestFocus();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade invalida.");
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
    }//GEN-LAST:event_jBadicionarActionPerformed

    private void jBremoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBremoverActionPerformed
        // TODO add your handling code here:
        int linha = jTbitens.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um item na tabela.");
            return;
        }
        venda.removerItem(linha);
        atualizarTabela();
    }//GEN-LAST:event_jBremoverActionPerformed

    private void jBfinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBfinalizarActionPerformed
        // TODO add your handling code here:
        if (venda.getItens().size() == 0) {
            JOptionPane.showMessageDialog(this, "Adicione pelo menos um produto.");
            return;
        }
        String cpf = jTcpf.getText().replace(".", "").replace("-", "").trim();
        if (!cpf.equals("") && cpf.length() != 11) {
            JOptionPane.showMessageDialog(this, "CPF deve ter 11 numeros (ou deixe em branco).");
            return;
        }
        if (cpf.equals("")) {
            venda.setCpfCliente(null);// venda sem CPF na nota
        } else {
            venda.setCpfCliente(cpf);
        }
        venda.setCodigoFuncionario(Integer.parseInt(jCfuncionario.getSelectedItem().toString().split(" - ")[0]));
        venda.setFormaPagamento(jCpagamento.getSelectedItem().toString());

        String troco = "";
        if (venda.getFormaPagamento().equals("Dinheiro")) {
            String recebido = JOptionPane.showInputDialog(this, String.format("Total: R$ %.2f\nValor recebido:", venda.getTotal()));
            if (recebido == null) {
                return;
            }
            double valor = Double.parseDouble(recebido.replace(",", "."));
            if (valor < venda.getTotal()) {
                JOptionPane.showMessageDialog(this, "Valor recebido menor que o total!");
                return;
            }
            troco = String.format("\nTroco: R$ %.2f", valor - venda.getTotal());
        }
        try {
            ManterVenda mv = new ManterVenda();
            mv.inserir(venda);
            if (venda.getCodigo() > 0) {
                JOptionPane.showMessageDialog(this, "Venda numero " + venda.getCodigo() + " finalizada!"
                        + String.format("\nTotal: R$ %.2f", venda.getTotal()) + troco);
                jBnovaActionPerformed(null);
            }
        } catch (Exception ex) {
            Logger.getLogger(CadastroVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBfinalizarActionPerformed

    private void jBnovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBnovaActionPerformed
        // TODO add your handling code here:
        venda = new Venda();
        carregarProdutos(null);
        jTcpf.setText("");
        jTcodigoBarras.setText("");
        jTquantidade.setText("1");
        atualizarTabela();
        jTcodigoBarras.requestFocus();
    }//GEN-LAST:event_jBnovaActionPerformed

    // venda que esta sendo feita no caixa
    private Venda venda = new Venda();

    // mostra os itens da venda na tabela e o total
    private void atualizarTabela() {
        DefaultTableModel tabela = (DefaultTableModel) jTbitens.getModel();
        tabela.setRowCount(0);
        for (int i = 0; i < venda.getItens().size(); i++) {
            ItemVenda item = venda.getItens().get(i);
            tabela.addRow(new Object[]{item.getCodigoProduto(), item.getNomeProduto(), item.getQuantidade(),
                String.format("R$ %.2f", item.getPrecoUnitario()), String.format("R$ %.2f", item.getSubtotal())});
        }
        NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        jLtotal.setText("TOTAL: " + moeda.format(venda.getTotal()));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            TemaInterface.instalar();
            new CadastroVenda().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBadicionar;
    private javax.swing.JButton jBfinalizar;
    private javax.swing.JButton jBnova;
    private javax.swing.JButton jBnovoProduto;
    private javax.swing.JButton jBremover;
    private javax.swing.JComboBox<String> jCfuncionario;
    private javax.swing.JComboBox<String> jCpagamento;
    private javax.swing.JComboBox<OpcaoProduto> jCproduto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLtotal;
    private javax.swing.JLabel jLproduto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTbitens;
    private javax.swing.JTextField jTcodigoBarras;
    private javax.swing.JTextField jTcpf;
    private javax.swing.JTextField jTquantidade;
    // End of variables declaration//GEN-END:variables
}

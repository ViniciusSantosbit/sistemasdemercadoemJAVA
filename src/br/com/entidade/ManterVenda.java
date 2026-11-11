/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.ItemVenda;
import br.com.controle.Venda;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

// Entidade da ROTINA PRINCIPAL: grava a venda, os itens e atualiza o estoque.
public class ManterVenda extends DAO {

    // consulta com INNER JOIN: traz o NOME do funcionario em vez do codigo
    private String selectJoin = "select v.codigo, v.cpf_cliente, v.codigo_funcionario, f.nome as nome_funcionario, "
            + "DATE_FORMAT(v.data_venda, '%d/%m/%Y %H:%i') as data_venda, v.forma_pagamento, v.status, v.total "
            + "FROM venda v INNER JOIN funcionario f ON f.codigo = v.codigo_funcionario ";

    public void inserir(Venda v) throws Exception {
        abrirBanco();
        con.setAutoCommit(false);// inicia uma transacao: grava tudo ou nada
        try {
            // confere o estoque de cada item antes de gravar
            for (int i = 0; i < v.getItens().size(); i++) {
                ItemVenda item = v.getItens().get(i);
                pst = (PreparedStatement) con.prepareStatement("select estoque FROM produto where codigo=?");
                pst.setInt(1, item.getCodigoProduto());
                ResultSet tr = pst.executeQuery();
                if (tr.next() && tr.getInt("estoque") < item.getQuantidade()) {
                    JOptionPane.showMessageDialog(null, "Estoque insuficiente para: " + item.getNomeProduto());
                    con.rollback();
                    fecharBanco();
                    return;
                }
            }
            String query = "INSERT INTO venda (codigo,cpf_cliente,codigo_funcionario,data_venda,forma_pagamento,status,total) values(null,?,?,now(),?,?,?)";
            pst = (PreparedStatement) con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, v.getCpfCliente());
            pst.setInt(2, v.getCodigoFuncionario());
            pst.setString(3, v.getFormaPagamento());
            pst.setString(4, "FINALIZADA");
            pst.setDouble(5, v.getTotal());
            pst.execute();
            ResultSet chave = pst.getGeneratedKeys();
            chave.next();
            v.setCodigo(chave.getInt(1));// codigo gerado pelo auto_increment

            for (int i = 0; i < v.getItens().size(); i++) {
                ItemVenda item = v.getItens().get(i);
                query = "INSERT INTO item_venda (codigo,codigo_venda,codigo_produto,quantidade,preco_unitario) values(null,?,?,?,?)";
                pst = (PreparedStatement) con.prepareStatement(query);
                pst.setInt(1, v.getCodigo());
                pst.setInt(2, item.getCodigoProduto());
                pst.setInt(3, item.getQuantidade());
                pst.setDouble(4, item.getPrecoUnitario());
                pst.execute();
                // baixa no estoque
                pst = (PreparedStatement) con.prepareStatement("UPDATE produto set estoque = estoque - ? where codigo = ?");
                pst.setInt(1, item.getQuantidade());
                pst.setInt(2, item.getCodigoProduto());
                pst.executeUpdate();
            }
            con.commit();// confirma a transacao
        } catch (Exception e) {
            con.rollback();// desfaz tudo se der erro
            JOptionPane.showMessageDialog(null, "Erro ao gravar a venda: " + e.getMessage());
        }
        fecharBanco();
    }

    public void deletarVenda(Venda v) throws Exception {
        abrirBanco();
        // se a venda nao estava cancelada, os produtos voltam para o estoque
        String query = "UPDATE produto p INNER JOIN item_venda i ON i.codigo_produto = p.codigo "
                + "INNER JOIN venda v ON v.codigo = i.codigo_venda "
                + "set p.estoque = p.estoque + i.quantidade where v.codigo = ? and v.status <> 'CANCELADA'";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setInt(1, v.getCodigo());
        pst.executeUpdate();
        query = "delete from item_venda where codigo_venda=?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setInt(1, v.getCodigo());
        pst.execute();
        query = "delete from venda where codigo=?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setInt(1, v.getCodigo());
        pst.execute();
        JOptionPane.showMessageDialog(null, "Venda deletada com sucesso!");
        fecharBanco();
    }

   public void PesquisarRegistro(Venda v) throws Exception {
	try {
		abrirBanco();
		String query = selectJoin + "where v.codigo=?";
		pst = (PreparedStatement) con.prepareStatement(query);
		pst.setInt(1, v.getCodigo());
		ResultSet tr = pst.executeQuery();
		if (tr.next()) {
			preencher(v, tr);
		} else {
			v.setCodigo(0);
			JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado! ");
		}
		fecharBanco();
	} catch (Exception e) {
		System.out.println("Erro " + e.getMessage());
	}
    }

    public ArrayList<Venda> PesquisarTudo() throws Exception {
        return pesquisar(selectJoin + "order by v.codigo desc", null, null);
    }

    // RELATORIO: vendas de um periodo (datas no formato aaaa-mm-dd)
    public ArrayList<Venda> relatorio(String dataInicio, String dataFim) throws Exception {
        return pesquisar(selectJoin + "where date(v.data_venda) between ? and ? order by v.data_venda", dataInicio, dataFim);
    }

    private ArrayList<Venda> pesquisar(String query, String data1, String data2) throws Exception {
        ArrayList<Venda> vendas = new ArrayList<Venda>();
        try {
            abrirBanco();
            pst = (PreparedStatement) con.prepareStatement(query);
            if (data1 != null) {
                pst.setString(1, data1);
                pst.setString(2, data2);
            }
            ResultSet tr = pst.executeQuery();
            Venda v;
            while (tr.next()) {
                v = new Venda();
                preencher(v, tr);
                vendas.add(v);
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
        return vendas;
    }

    // itens de uma venda (INNER JOIN com produto para mostrar o nome)
    public ArrayList<ItemVenda> PesquisarItens(int codigoVenda) throws Exception {
        ArrayList<ItemVenda> itens = new ArrayList<ItemVenda>();
        abrirBanco();
        String query = "select i.codigo_produto, p.nome, i.quantidade, i.preco_unitario FROM item_venda i "
                + "INNER JOIN produto p ON p.codigo = i.codigo_produto where i.codigo_venda=?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setInt(1, codigoVenda);
        ResultSet tr = pst.executeQuery();
        while (tr.next()) {
            ItemVenda item = new ItemVenda();
            item.setCodigoProduto(tr.getInt("codigo_produto"));
            item.setNomeProduto(tr.getString("nome"));
            item.setQuantidade(tr.getInt("quantidade"));
            item.setPrecoUnitario(tr.getDouble("preco_unitario"));
            itens.add(item);
        }
        fecharBanco();
        return itens;
    }

    // Metodo da Classe Entidade: altera CPF, forma de pagamento e situacao
    public void editarVenda(Venda v) throws Exception {
        abrirBanco();
        // descobre a situacao antiga para acertar o estoque
        pst = (PreparedStatement) con.prepareStatement("select status FROM venda where codigo=?");
        pst.setInt(1, v.getCodigo());
        ResultSet tr = pst.executeQuery();
        tr.next();
        String statusAntigo = tr.getString("status");
        String sinal = null;
        if (statusAntigo.equals("FINALIZADA") && v.getStatus().equals("CANCELADA")) {
            sinal = "+";// cancelou: produtos voltam para o estoque
        } else if (statusAntigo.equals("CANCELADA") && v.getStatus().equals("FINALIZADA")) {
            sinal = "-";// reativou: produtos saem do estoque de novo
        }
        if (sinal != null) {
            String query = "UPDATE produto p INNER JOIN item_venda i ON i.codigo_produto = p.codigo "
                    + "set p.estoque = p.estoque " + sinal + " i.quantidade where i.codigo_venda = ?";
            pst = (PreparedStatement) con.prepareStatement(query);
            pst.setInt(1, v.getCodigo());
            pst.executeUpdate();
        }
        String query = "UPDATE venda set cpf_cliente = ?, forma_pagamento = ?, status = ? where codigo = ?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setString(1, v.getCpfCliente());
        pst.setString(2, v.getFormaPagamento());
        pst.setString(3, v.getStatus());
        pst.setInt(4, v.getCodigo());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Venda alterada com sucesso!");
        fecharBanco();
    }

    private void preencher(Venda v, ResultSet tr) throws Exception {
        v.setCodigo(tr.getInt("codigo"));
        v.setCpfCliente(tr.getString("cpf_cliente"));
        v.setCodigoFuncionario(tr.getInt("codigo_funcionario"));
        v.setNomeFuncionario(tr.getString("nome_funcionario"));
        v.setDataVenda(tr.getString("data_venda"));
        v.setFormaPagamento(tr.getString("forma_pagamento"));
        v.setStatus(tr.getString("status"));
        v.setTotal(tr.getDouble("total"));
    }
}

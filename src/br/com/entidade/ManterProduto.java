/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.Produto;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ManterProduto extends DAO {

    public boolean inserir(Produto p) throws Exception {
        abrirBanco();
        String existe = "select codigo FROM produto where codigo_barras=?";
        pst = (PreparedStatement) con.prepareStatement(existe);
        pst.setString(1, p.getCodigoBarras());
        ResultSet tr = pst.executeQuery();
        boolean cadastrado = !tr.next();
        if (!cadastrado) {
            JOptionPane.showMessageDialog(null, "Codigo de barras ja cadastrado anteriormente!");
        } else {
            String query = "INSERT INTO produto (codigo,nome,codigo_barras,preco,estoque,codigo_categoria) values(null,?,?,?,?,?)";
            pst = (PreparedStatement) con.prepareStatement(query);
            pst.setString(1, p.getNome());
            pst.setString(2, p.getCodigoBarras());
            pst.setDouble(3, p.getPreco());
            pst.setInt(4, p.getEstoque());
            pst.setInt(5, p.getCodigoCategoria());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Produto inserido com sucesso!");
        }
        fecharBanco();
        return cadastrado;
    }

    public void deletarProduto(Produto p) throws Exception {
        abrirBanco();
        String query = "delete from produto where codigo=?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setInt(1, p.getCodigo());
        try {
            pst.execute();
            JOptionPane.showMessageDialog(null, "Produto deletado com sucesso!");
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            // chave estrangeira: o registro esta sendo usado em outra tabela
            JOptionPane.showMessageDialog(null, "Nao foi possivel deletar: este registro esta sendo usado em outra tabela.");
        }
        fecharBanco();
    }

   public void PesquisarRegistro(Produto p) throws Exception {
	try {
		abrirBanco();
		String query = "select p.*, c.nome as nome_categoria FROM produto p "
                    + "INNER JOIN categoria c ON c.codigo = p.codigo_categoria where p.codigo=?";
		pst = (PreparedStatement) con.prepareStatement(query);
		pst.setInt(1, p.getCodigo());
		ResultSet tr = pst.executeQuery();
		if (tr.next()) {
			p.setCodigo(tr.getInt("codigo"));
			p.setNome(tr.getString("nome"));
			p.setCodigoBarras(tr.getString("codigo_barras"));
			p.setPreco(tr.getDouble("preco"));
			p.setEstoque(tr.getInt("estoque"));
			p.setCodigoCategoria(tr.getInt("codigo_categoria"));
			p.setNomeCategoria(tr.getString("nome_categoria"));
		} else {
			JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado! ");
		}
		fecharBanco();
	} catch (Exception e) {
		System.out.println("Erro " + e.getMessage());
	}
    }

    public ArrayList<Produto> PesquisarTudo() throws Exception {
        ArrayList<Produto> lista = new ArrayList<Produto>();
        try {
            abrirBanco();
            String query = "select p.*, c.nome as nome_categoria FROM produto p "
                    + "INNER JOIN categoria c ON c.codigo = p.codigo_categoria order by p.codigo";
            pst = (PreparedStatement) con.prepareStatement(query);
            ResultSet tr = pst.executeQuery();
            Produto p;
            while (tr.next()) {
                p = new Produto();
                p.setCodigo(tr.getInt("codigo"));
                p.setNome(tr.getString("nome"));
                p.setCodigoBarras(tr.getString("codigo_barras"));
                p.setPreco(tr.getDouble("preco"));
                p.setEstoque(tr.getInt("estoque"));
                p.setCodigoCategoria(tr.getInt("codigo_categoria"));
                p.setNomeCategoria(tr.getString("nome_categoria"));
                lista.add(p);
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
        return lista;
    }

    // pesquisa o produto pelo codigo de barras (usado no caixa)
    public Produto PesquisarCodigoBarras(String codigoBarras) throws Exception {
        Produto p = null;
        abrirBanco();
        String query = "select * FROM produto where codigo_barras=?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setString(1, codigoBarras);
        ResultSet tr = pst.executeQuery();
        if (tr.next()) {
            p = new Produto();
            p.setCodigo(tr.getInt("codigo"));
            p.setNome(tr.getString("nome"));
            p.setCodigoBarras(tr.getString("codigo_barras"));
            p.setPreco(tr.getDouble("preco"));
            p.setEstoque(tr.getInt("estoque"));
        }
        fecharBanco();
        return p;
    }

    // Metodo da Classe Entidade
    public void editarProduto(Produto p) throws Exception {
        abrirBanco();
        String query = "UPDATE produto set nome = ?, codigo_barras = ?, preco = ?, estoque = ?, codigo_categoria = ? where codigo = ?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setString(1, p.getNome());
        pst.setString(2, p.getCodigoBarras());
        pst.setDouble(3, p.getPreco());
        pst.setInt(4, p.getEstoque());
        pst.setInt(5, p.getCodigoCategoria());
        pst.setInt(6, p.getCodigo());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Produto alterado com sucesso!");
        fecharBanco();
    }
}

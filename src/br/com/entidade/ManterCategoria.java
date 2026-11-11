/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.Categoria;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ManterCategoria extends DAO {

    public void inserir(Categoria c) throws Exception {
        abrirBanco();
        String existe = "select codigo FROM categoria where nome=?";
        pst = (PreparedStatement) con.prepareStatement(existe);
        pst.setString(1, c.getNome());
        ResultSet tr = pst.executeQuery();
        if (tr.next()) {
            JOptionPane.showMessageDialog(null, "Nome ja cadastrado anteriormente!");
        } else {
            String query = "INSERT INTO categoria (codigo,nome,descricao) values(null,?,?)";
            pst = (PreparedStatement) con.prepareStatement(query);
            pst.setString(1, c.getNome());
            pst.setString(2, c.getDescricao());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Categoria inserida com sucesso!");
        }
        fecharBanco();
    }

    public void deletarCategoria(Categoria c) throws Exception {
        abrirBanco();
        String query = "delete from categoria where codigo=?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setInt(1, c.getCodigo());
        try {
            pst.execute();
            JOptionPane.showMessageDialog(null, "Categoria deletada com sucesso!");
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            // chave estrangeira: o registro esta sendo usado em outra tabela
            JOptionPane.showMessageDialog(null, "Nao foi possivel deletar: este registro esta sendo usado em outra tabela.");
        }
        fecharBanco();
    }

   public void PesquisarRegistro(Categoria c) throws Exception {
	try {
		abrirBanco();
		String query = "select * FROM categoria where codigo=?";
		pst = (PreparedStatement) con.prepareStatement(query);
		pst.setInt(1, c.getCodigo());
		ResultSet tr = pst.executeQuery();
		if (tr.next()) {
			c.setCodigo(tr.getInt("codigo"));
			c.setNome(tr.getString("nome"));
			c.setDescricao(tr.getString("descricao"));
		} else {
			JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado! ");
		}
		fecharBanco();
	} catch (Exception e) {
		System.out.println("Erro " + e.getMessage());
	}
    }

    public ArrayList<Categoria> PesquisarTudo() throws Exception {
        ArrayList<Categoria> lista = new ArrayList<Categoria>();
        try {
            abrirBanco();
            String query = "select * FROM categoria order by codigo";
            pst = (PreparedStatement) con.prepareStatement(query);
            ResultSet tr = pst.executeQuery();
            Categoria c;
            while (tr.next()) {
                c = new Categoria();
                c.setCodigo(tr.getInt("codigo"));
                c.setNome(tr.getString("nome"));
                c.setDescricao(tr.getString("descricao"));
                lista.add(c);
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
        return lista;
    }

    // Metodo da Classe Entidade
    public void editarCategoria(Categoria c) throws Exception {
        abrirBanco();
        String query = "UPDATE categoria set nome = ?, descricao = ? where codigo = ?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setString(1, c.getNome());
        pst.setString(2, c.getDescricao());
        pst.setInt(3, c.getCodigo());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Categoria alterada com sucesso!");
        fecharBanco();
    }
}

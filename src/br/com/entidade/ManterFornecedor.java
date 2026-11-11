/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.Fornecedor;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ManterFornecedor extends DAO {

    public void inserir(Fornecedor f) throws Exception {
        abrirBanco();
        String existe = "select codigo FROM fornecedor where cnpj=?";
        pst = (PreparedStatement) con.prepareStatement(existe);
        pst.setString(1, f.getCnpj());
        ResultSet tr = pst.executeQuery();
        if (tr.next()) {
            JOptionPane.showMessageDialog(null, "CNPJ ja cadastrado anteriormente!");
        } else {
            String query = "INSERT INTO fornecedor (codigo,razao_social,cnpj,telefone,email) values(null,?,?,?,?)";
            pst = (PreparedStatement) con.prepareStatement(query);
            pst.setString(1, f.getRazaoSocial());
            pst.setString(2, f.getCnpj());
            pst.setString(3, f.getTelefone());
            pst.setString(4, f.getEmail());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Fornecedor inserido com sucesso!");
        }
        fecharBanco();
    }

    public void deletarFornecedor(Fornecedor f) throws Exception {
        abrirBanco();
        String query = "delete from fornecedor where codigo=?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setInt(1, f.getCodigo());
        try {
            pst.execute();
            JOptionPane.showMessageDialog(null, "Fornecedor deletado com sucesso!");
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            // chave estrangeira: o registro esta sendo usado em outra tabela
            JOptionPane.showMessageDialog(null, "Nao foi possivel deletar: este registro esta sendo usado em outra tabela.");
        }
        fecharBanco();
    }

   public void PesquisarRegistro(Fornecedor f) throws Exception {
	try {
		abrirBanco();
		String query = "select * FROM fornecedor where codigo=?";
		pst = (PreparedStatement) con.prepareStatement(query);
		pst.setInt(1, f.getCodigo());
		ResultSet tr = pst.executeQuery();
		if (tr.next()) {
			f.setCodigo(tr.getInt("codigo"));
			f.setRazaoSocial(tr.getString("razao_social"));
			f.setCnpj(tr.getString("cnpj"));
			f.setTelefone(tr.getString("telefone"));
			f.setEmail(tr.getString("email"));
		} else {
			JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado! ");
		}
		fecharBanco();
	} catch (Exception e) {
		System.out.println("Erro " + e.getMessage());
	}
    }

    public ArrayList<Fornecedor> PesquisarTudo() throws Exception {
        ArrayList<Fornecedor> lista = new ArrayList<Fornecedor>();
        try {
            abrirBanco();
            String query = "select * FROM fornecedor order by codigo";
            pst = (PreparedStatement) con.prepareStatement(query);
            ResultSet tr = pst.executeQuery();
            Fornecedor f;
            while (tr.next()) {
                f = new Fornecedor();
                f.setCodigo(tr.getInt("codigo"));
                f.setRazaoSocial(tr.getString("razao_social"));
                f.setCnpj(tr.getString("cnpj"));
                f.setTelefone(tr.getString("telefone"));
                f.setEmail(tr.getString("email"));
                lista.add(f);
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
        return lista;
    }

    // Metodo da Classe Entidade
    public void editarFornecedor(Fornecedor f) throws Exception {
        abrirBanco();
        String query = "UPDATE fornecedor set razao_social = ?, cnpj = ?, telefone = ?, email = ? where codigo = ?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setString(1, f.getRazaoSocial());
        pst.setString(2, f.getCnpj());
        pst.setString(3, f.getTelefone());
        pst.setString(4, f.getEmail());
        pst.setInt(5, f.getCodigo());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Fornecedor alterado com sucesso!");
        fecharBanco();
    }
}

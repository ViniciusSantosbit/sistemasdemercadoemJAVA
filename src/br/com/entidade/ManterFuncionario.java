/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.Funcionario;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ManterFuncionario extends DAO {

    public void inserir(Funcionario f) throws Exception {
        abrirBanco();
        String existe = "select codigo FROM funcionario where cpf=?";
        pst = (PreparedStatement) con.prepareStatement(existe);
        pst.setString(1, f.getCpf());
        ResultSet tr = pst.executeQuery();
        if (tr.next()) {
            JOptionPane.showMessageDialog(null, "CPF ja cadastrado anteriormente!");
        } else {
            String query = "INSERT INTO funcionario (codigo,nome,cpf,telefone,cargo) values(null,?,?,?,?)";
            pst = (PreparedStatement) con.prepareStatement(query);
            pst.setString(1, f.getNome());
            pst.setString(2, f.getCpf());
            pst.setString(3, f.getTelefone());
            pst.setString(4, f.getCargo());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Funcionario inserido com sucesso!");
        }
        fecharBanco();
    }

    public void deletarFuncionario(Funcionario f) throws Exception {
        abrirBanco();
        String query = "delete from funcionario where codigo=?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setInt(1, f.getCodigo());
        try {
            pst.execute();
            JOptionPane.showMessageDialog(null, "Funcionario deletado com sucesso!");
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            // chave estrangeira: o registro esta sendo usado em outra tabela
            JOptionPane.showMessageDialog(null, "Nao foi possivel deletar: este registro esta sendo usado em outra tabela.");
        }
        fecharBanco();
    }

   public void PesquisarRegistro(Funcionario f) throws Exception {
	try {
		abrirBanco();
		String query = "select * FROM funcionario where codigo=?";
		pst = (PreparedStatement) con.prepareStatement(query);
		pst.setInt(1, f.getCodigo());
		ResultSet tr = pst.executeQuery();
		if (tr.next()) {
			f.setCodigo(tr.getInt("codigo"));
			f.setNome(tr.getString("nome"));
			f.setCpf(tr.getString("cpf"));
			f.setTelefone(tr.getString("telefone"));
			f.setCargo(tr.getString("cargo"));
		} else {
			JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado! ");
		}
		fecharBanco();
	} catch (Exception e) {
		System.out.println("Erro " + e.getMessage());
	}
    }

    public ArrayList<Funcionario> PesquisarTudo() throws Exception {
        ArrayList<Funcionario> lista = new ArrayList<Funcionario>();
        try {
            abrirBanco();
            String query = "select * FROM funcionario order by codigo";
            pst = (PreparedStatement) con.prepareStatement(query);
            ResultSet tr = pst.executeQuery();
            Funcionario f;
            while (tr.next()) {
                f = new Funcionario();
                f.setCodigo(tr.getInt("codigo"));
                f.setNome(tr.getString("nome"));
                f.setCpf(tr.getString("cpf"));
                f.setTelefone(tr.getString("telefone"));
                f.setCargo(tr.getString("cargo"));
                lista.add(f);
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
        return lista;
    }

    // Metodo da Classe Entidade
    public void editarFuncionario(Funcionario f) throws Exception {
        abrirBanco();
        String query = "UPDATE funcionario set nome = ?, cpf = ?, telefone = ?, cargo = ? where codigo = ?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setString(1, f.getNome());
        pst.setString(2, f.getCpf());
        pst.setString(3, f.getTelefone());
        pst.setString(4, f.getCargo());
        pst.setInt(5, f.getCodigo());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Funcionario alterado com sucesso!");
        fecharBanco();
    }
}

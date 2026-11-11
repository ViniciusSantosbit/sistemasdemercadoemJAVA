/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

import java.util.ArrayList;

// Rotina principal do sistema: a venda no caixa.
// Uma venda e composta por varios itens (Agregacao).
// O CPF do cliente e opcional (nao existe cadastro de cliente).
public class Venda {
   private int codigo;
   private String cpfCliente;
   private int codigoFuncionario;
   private String nomeFuncionario;
   private String dataVenda;
   private String formaPagamento;
   private String status;
   private double total;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public int getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(int codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    private ArrayList<ItemVenda> itens = new ArrayList<ItemVenda>();

    public ArrayList<ItemVenda> getItens() {
        return itens;
    }

    public void adicionarItem(ItemVenda item) {
        itens.add(item);
        calcularTotal();
    }

    public void removerItem(int posicao) {
        itens.remove(posicao);
        calcularTotal();
    }

    // o total da venda e a soma dos subtotais dos itens
    public void calcularTotal() {
        total = 0;
        for (int i = 0; i < itens.size(); i++) {
            total = total + itens.get(i).getSubtotal();
        }
    }
}

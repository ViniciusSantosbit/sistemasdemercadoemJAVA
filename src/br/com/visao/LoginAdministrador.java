package br.com.visao;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.*;

/** Login fixo solicitado para as areas administrativas. */
final class LoginAdministrador {
    private LoginAdministrador() {}

    static boolean credenciaisValidas(String usuario, char[] senha) {
        return "admin".equals(usuario) && Arrays.equals(senha, new char[]{'1', '2', '3', '4'});
    }

    static boolean solicitar(Component origem) {
        JTextField usuario = new JTextField(20);
        JPasswordField senha = new JPasswordField(20);
        JLabel mensagem = new JLabel("Informe o usuario e a senha do administrador.");
        JPanel painel = new JPanel(new GridLayout(0, 1, 4, 6));
        painel.add(mensagem);
        painel.add(new JLabel("Usuario:"));
        painel.add(usuario);
        painel.add(new JLabel("Senha:"));
        painel.add(senha);
        Object[] opcoes = {"Entrar", "Cancelar"};
        while (true) {
            int escolha = JOptionPane.showOptionDialog(origem, painel, "Acesso administrativo",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
            if (escolha != 0) {
                senha.setText("");
                return false;
            }
            char[] informada = senha.getPassword();
            boolean autorizado = credenciaisValidas(usuario.getText(), informada);
            Arrays.fill(informada, '\0');
            senha.setText("");
            if (autorizado) return true;
            mensagem.setText("Usuario ou senha incorretos. Tente novamente.");
            mensagem.setForeground(new java.awt.Color(160, 35, 35));
        }
    }
}

package br.com.visao;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
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
        JLabel mensagem = new JLabel("Informe o usuário e a senha do administrador.");
        JLabel rotuloUsuario = new JLabel("Usuário");
        rotuloUsuario.setLabelFor(usuario);
        rotuloUsuario.setDisplayedMnemonic(KeyEvent.VK_U);
        JLabel rotuloSenha = new JLabel("Senha");
        rotuloSenha.setLabelFor(senha);
        rotuloSenha.setDisplayedMnemonic(KeyEvent.VK_S);

        JPanel painel = new JPanel(new GridLayout(0, 1, 0, 4));
        painel.setBorder(BorderFactory.createEmptyBorder(4, 8, 12, 8));
        painel.add(mensagem);
        painel.add(rotuloUsuario);
        painel.add(usuario);
        painel.add(rotuloSenha);
        painel.add(senha);
        Object[] opcoes = {"Entrar", "Cancelar"};
        while (true) {
            JOptionPane aviso = new JOptionPane(painel, JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.DEFAULT_OPTION, null, opcoes, opcoes[0]) {
                @Override
                public void selectInitialValue() {
                    super.selectInitialValue();
                    usuario.requestFocusInWindow();
                }
            };
            JDialog dialogo = aviso.createDialog(origem, "Acesso administrativo");
            dialogo.setVisible(true);
            dialogo.dispose();
            if (!opcoes[0].equals(aviso.getValue())) {
                senha.setText("");
                return false;
            }
            char[] informada = senha.getPassword();
            boolean autorizado = credenciaisValidas(usuario.getText(), informada);
            Arrays.fill(informada, '\0');
            senha.setText("");
            if (autorizado) return true;
            mensagem.setText("Usuário ou senha incorretos. Tente novamente.");
            mensagem.setForeground(new java.awt.Color(160, 35, 35));
        }
    }
}

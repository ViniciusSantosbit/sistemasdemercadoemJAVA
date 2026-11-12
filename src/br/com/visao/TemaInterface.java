package br.com.visao;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

final class TemaInterface {
    static final Color FUNDO = new Color(0xEFF4F3);
    static final Color SUPERFICIE = Color.WHITE;
    static final Color TEXTO = new Color(0x182E2B);
    static final Color TEXTO_SECUNDARIO = new Color(0x45605B);
    static final Color DESTAQUE = new Color(0x124B40);
    static final Color BORDA = new Color(0xBFCFCA);

    private TemaInterface() {}

    static void instalar() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            return;
        }
        UIManager.put("control", FUNDO);
        UIManager.put("text", TEXTO);
        UIManager.put("nimbusBase", DESTAQUE);
        UIManager.put("nimbusFocus", new Color(0x076959));
        UIManager.put("nimbusSelectionBackground", DESTAQUE);
        UIManager.put("nimbusSelectedText", Color.WHITE);
    }
}

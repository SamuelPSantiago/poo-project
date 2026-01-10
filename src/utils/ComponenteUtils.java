package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ComponenteUtils {
    
    public static final Color COR_FUNDO = new Color(236, 240, 241);
    public static final Color COR_TITULO = new Color(44, 62, 80);
    public static final Color COR_PRIMARIA = new Color(41, 128, 185);
    public static final Color COR_PRIMARIA_HOVER = new Color(52, 152, 219);
    public static final Color COR_SUCESSO = new Color(39, 174, 96);
    public static final Color COR_SUCESSO_HOVER = new Color(46, 204, 113);
    public static final Color COR_SUCESSO_DESABILITADO = new Color(20, 90, 50);
    public static final Color COR_CANCELAR = new Color(192, 57, 43);
    public static final Color COR_CANCELAR_HOVER = new Color(231, 76, 60);
    public static final Color COR_BORDA = new Color(189, 195, 199);
    public static final Color COR_TEXTO = Color.WHITE;

    public static JButton criarBotao(String texto, Color corNormal, Color corHover, int tamanhoFonte) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, tamanhoFonte));
        botao.setForeground(COR_TEXTO);
        botao.setBackground(corNormal);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (botao.isEnabled()) {
                    botao.setBackground(corHover);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (botao.isEnabled()) {
                    botao.setBackground(corNormal);
                }
            }
        });

        return botao;
    }
}

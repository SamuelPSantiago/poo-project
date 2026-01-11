package utils;

import javax.swing.*;
import java.awt.*;

public class ComponenteUtils {
    
    public static final Color COR_FUNDO = new Color(236, 240, 241);
    public static final Color COR_TITULO = new Color(44, 62, 80);
    public static final Color COR_PRIMARIA = new Color(41, 128, 185);
    public static final Color COR_SUCESSO = new Color(39, 174, 96);
    public static final Color COR_SUCESSO_DESABILITADO = new Color(20, 90, 50);
    public static final Color COR_CANCELAR = new Color(192, 57, 43);
    public static final Color COR_BORDA = new Color(189, 195, 199);
    public static final Color COR_TEXTO = Color.WHITE;

    public static JButton criarBotao(String texto, Color corNormal, int tamanhoFonte) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, tamanhoFonte));
        botao.setForeground(COR_TEXTO);
        botao.setBackground(corNormal);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botao.setFocusPainted(false);

        return botao;
    }
}

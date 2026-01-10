package view;

import service.Estacionamento;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaPrincipal extends JFrame {

    private Estacionamento estacionamento;

    // Cores do tema
    private static final Color COR_PRIMARIA = new Color(41, 128, 185);
    private static final Color COR_HOVER = new Color(52, 152, 219);
    private static final Color COR_SAIR = new Color(192, 57, 43);
    private static final Color COR_SAIR_HOVER = new Color(231, 76, 60);
    private static final Color COR_FUNDO = new Color(236, 240, 241);
    private static final Color COR_TEXTO = Color.WHITE;

    public TelaPrincipal() {
        this.estacionamento = new Estacionamento();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Configuracao da janela
        setTitle("Sistema de Estacionamento");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(COR_FUNDO);

        // Titulo no topo
        JLabel titulo = new JLabel("Sistema de Estacionamento", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(44, 62, 80));
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Painel de botoes no centro
        JPanel painelBotoes = new JPanel(new GridLayout(5, 1, 10, 15));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        painelBotoes.setBackground(COR_FUNDO);

        JButton btnEntrada = criarBotao("Registrar Entrada", COR_PRIMARIA, COR_HOVER);
        JButton btnSaida = criarBotao("Registrar Saida", COR_PRIMARIA, COR_HOVER);
        JButton btnConsulta = criarBotao("Consultar Veiculo", COR_PRIMARIA, COR_HOVER);
        JButton btnRelatorio = criarBotao("Relatorio de Ocupacao", COR_PRIMARIA, COR_HOVER);
        JButton btnSair = criarBotao("Sair", COR_SAIR, COR_SAIR_HOVER);

        // ActionListeners
        btnEntrada.addActionListener(e -> abrirTelaEntrada());
        btnSaida.addActionListener(e -> abrirTelaSaida());
        btnConsulta.addActionListener(e -> abrirTelaConsulta());
        btnRelatorio.addActionListener(e -> abrirTelaRelatorio());
        btnSair.addActionListener(e -> System.exit(0));

        painelBotoes.add(btnEntrada);
        painelBotoes.add(btnSaida);
        painelBotoes.add(btnConsulta);
        painelBotoes.add(btnRelatorio);
        painelBotoes.add(btnSair);

        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

        add(painelPrincipal);
    }

    private JButton criarBotao(String texto, Color corNormal, Color corHover) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setForeground(COR_TEXTO);
        botao.setBackground(corNormal);
        botao.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efeito hover
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(corHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(corNormal);
            }
        });

        return botao;
    }

    private void abrirTelaEntrada() {
        new TelaEntrada(this, estacionamento);
    }

    private void abrirTelaSaida() {
        JOptionPane.showMessageDialog(this, "Tela de Saida - Em desenvolvimento");
    }

    private void abrirTelaConsulta() {
        JOptionPane.showMessageDialog(this, "Tela de Consulta - Em desenvolvimento");
    }

    private void abrirTelaRelatorio() {
        JOptionPane.showMessageDialog(this, "Tela de Relatorio - Em desenvolvimento");
    }

    public Estacionamento getEstacionamento() {
        return estacionamento;
    }
}

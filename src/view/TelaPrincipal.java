package view;

import service.Estacionamento;
import utils.ComponenteUtils;
import javax.swing.*;
import java.awt.*;

import static utils.ComponenteUtils.*;

public class TelaPrincipal extends JFrame {

    private Estacionamento estacionamento;

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
        titulo.setForeground(COR_TITULO);
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Painel de botoes no centro
        JPanel painelBotoes = new JPanel(new GridLayout(5, 1, 10, 15));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        painelBotoes.setBackground(COR_FUNDO);

        JButton btnEntrada = ComponenteUtils.criarBotao("Registrar Entrada", COR_PRIMARIA, COR_PRIMARIA_HOVER, 16);
        JButton btnSaida = ComponenteUtils.criarBotao("Registrar Saida", COR_PRIMARIA, COR_PRIMARIA_HOVER, 16);
        JButton btnConsulta = ComponenteUtils.criarBotao("Consultar Veiculo", COR_PRIMARIA, COR_PRIMARIA_HOVER, 16);
        JButton btnRelatorio = ComponenteUtils.criarBotao("Relatorio de Ocupacao", COR_PRIMARIA, COR_PRIMARIA_HOVER, 16);
        JButton btnSair = ComponenteUtils.criarBotao("Sair", COR_CANCELAR, COR_CANCELAR_HOVER, 16);

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

    private void abrirTelaEntrada() {
        new TelaEntrada(this, estacionamento);
    }

    private void abrirTelaSaida() {
        new TelaSaida(this, estacionamento);
    }

    private void abrirTelaConsulta() {
        JOptionPane.showMessageDialog(this, "Tela de Consulta - Em desenvolvimento");
    }

    private void abrirTelaRelatorio() {
        new TelaRelatorio(this, estacionamento);
    }

    public Estacionamento getEstacionamento() {
        return estacionamento;
    }
}

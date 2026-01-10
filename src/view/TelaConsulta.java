package view;

import model.*;
import service.Estacionamento;
import utils.ComponenteUtils;
import javax.swing.*;
import java.awt.*;

import static utils.ComponenteUtils.*;

public class TelaConsulta extends JDialog {

    private Estacionamento estacionamento;
    private JTextField txtPlaca;
    private JLabel lblInfo;

    public TelaConsulta(JFrame parent, Estacionamento estacionamento) {
        super(parent, "Consultar Veiculo", true);
        this.estacionamento = estacionamento;
        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        setSize(450, 420);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(COR_FUNDO);

        // Titulo
        JLabel titulo = new JLabel("Consultar Veiculo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(COR_TITULO);
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Painel central
        JPanel painelCentro = new JPanel(new BorderLayout(10, 10));
        painelCentro.setBackground(COR_FUNDO);

        // Painel de busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBusca.setBackground(COR_FUNDO);

        JLabel lblPlaca = new JLabel("Placa:");
        lblPlaca.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPlaca = new JTextField(12);
        txtPlaca.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton btnBuscar = ComponenteUtils.criarBotao("Buscar", COR_PRIMARIA, COR_PRIMARIA_HOVER, 14);
        btnBuscar.addActionListener(e -> buscarVeiculo());

        painelBusca.add(lblPlaca);
        painelBusca.add(txtPlaca);
        painelBusca.add(btnBuscar);

        painelCentro.add(painelBusca, BorderLayout.NORTH);

        // Label de informacoes
        lblInfo = new JLabel("<html><center>Digite a placa e clique em Buscar</center></html>", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInfo.setForeground(COR_TITULO);
        lblInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_BORDA, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        lblInfo.setOpaque(true);
        lblInfo.setBackground(Color.WHITE);

        painelCentro.add(lblInfo, BorderLayout.CENTER);

        painelPrincipal.add(painelCentro, BorderLayout.CENTER);

        // Botao fechar
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.setBackground(COR_FUNDO);

        JButton btnFechar = ComponenteUtils.criarBotao("Fechar", COR_CANCELAR, COR_CANCELAR_HOVER, 14);
        btnFechar.addActionListener(e -> dispose());

        painelBotoes.add(btnFechar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private void buscarVeiculo() {
        String placa = txtPlaca.getText().trim();

        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Digite a placa do veiculo!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Ticket ticket = estacionamento.buscarTicketPorPlaca(placa);

        if (ticket == null) {
            lblInfo.setText("<html><center><b>Veiculo nao encontrado!</b><br><br>Verifique a placa digitada.</center></html>");
            return;
        }

        Veiculo veiculo = ticket.getVeiculo();
        long tempo = ticket.calcularTempo();
        double valor = ticket.calcularValor();

        String info = String.format(
            "<html><center>" +
            "<b>Veiculo Encontrado!</b><br><br>" +
            "<b>Placa:</b> %s<br>" +
            "<b>Modelo:</b> %s<br>" +
            "<b>Tipo:</b> %s<br>" +
            "<b>Vaga:</b> %d<br><br>" +
            "<b>Tempo atual:</b> %d hora(s)<br>" +
            "<b style='color: #27ae60; font-size: 14px;'>Valor estimado: R$ %.2f</b>" +
            "</center></html>",
            veiculo.getPlaca(),
            veiculo.getModelo(),
            veiculo.getTipo(),
            ticket.getVaga().getId(),
            tempo,
            valor
        );

        lblInfo.setText(info);
    }
}

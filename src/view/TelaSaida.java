package view;

import model.*;
import service.Estacionamento;
import utils.ComponenteUtils;
import javax.swing.*;
import java.awt.*;

import static utils.ComponenteUtils.*;

public class TelaSaida extends JDialog {

    private Estacionamento estacionamento;
    private JTextField txtPlaca;
    private JLabel lblInfo;
    private JButton btnConfirmar;
    private Ticket ticketAtual;

    public TelaSaida(JFrame parent, Estacionamento estacionamento) {
        super(parent, "Registrar Saida", true);
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
        JLabel titulo = new JLabel("Registrar Saida de Veiculo", SwingConstants.CENTER);
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

        JButton btnBuscar = ComponenteUtils.criarBotao("Buscar", COR_PRIMARIA, 14);
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

        // Botoes
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.setBackground(COR_FUNDO);

        btnConfirmar = ComponenteUtils.criarBotao("Confirmar Saida", COR_SUCESSO, 14);
        btnConfirmar.setBackground(COR_SUCESSO_DESABILITADO);
        btnConfirmar.setEnabled(false);
        btnConfirmar.addActionListener(e -> confirmarSaida());

        JButton btnCancelar = ComponenteUtils.criarBotao("Cancelar", COR_CANCELAR, 14);
        btnCancelar.addActionListener(e -> dispose());

        painelBotoes.add(btnConfirmar);
        painelBotoes.add(btnCancelar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private boolean validarPlaca(String placa) {
        String padrao = "^[A-Z]{3}-?[0-9]{4}$|^[A-Z]{3}[0-9][A-Z][0-9]{2}$";
        return placa.toUpperCase().matches(padrao);
    }

    private void buscarVeiculo() {
        String placa = txtPlaca.getText().trim().toUpperCase();

        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Digite a placa do veiculo!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validarPlaca(placa)) {
            JOptionPane.showMessageDialog(this,
                "Formato de placa invalido!\nUse: ABC-1234 ou ABC1D23",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        ticketAtual = estacionamento.buscarTicketPorPlaca(placa);

        if (ticketAtual == null) {
            lblInfo.setText("<html><center><b>Veiculo nao encontrado!</b><br><br>Verifique a placa digitada.</center></html>");
            btnConfirmar.setEnabled(false);
            btnConfirmar.setBackground(COR_SUCESSO_DESABILITADO);
            return;
        }

        Veiculo veiculo = ticketAtual.getVeiculo();
        long tempo = ticketAtual.calcularTempo();
        double valor = ticketAtual.calcularValor();

        String info = String.format(
            "<html><center>" +
            "<b>Veiculo Encontrado!</b><br><br>" +
            "<b>Placa:</b> %s<br>" +
            "<b>Modelo:</b> %s<br>" +
            "<b>Tipo:</b> %s<br>" +
            "<b>Vaga:</b> %d<br><br>" +
            "<b>Tempo:</b> %d hora(s)<br>" +
            "<b style='color: #27ae60; font-size: 14px;'>Valor: R$ %.2f</b>" +
            "</center></html>",
            veiculo.getPlaca(),
            veiculo.getModelo(),
            veiculo.getTipo(),
            ticketAtual.getVaga().getId(),
            tempo,
            valor
        );

        lblInfo.setText(info);
        btnConfirmar.setEnabled(true);
        btnConfirmar.setBackground(COR_SUCESSO);
    }

    private void confirmarSaida() {
        if (ticketAtual == null) return;

        String placa = ticketAtual.getVeiculo().getPlaca();
        double valor = estacionamento.registrarSaida(placa);

        if (valor < 0) {
            JOptionPane.showMessageDialog(this,
                "Erro ao registrar saida!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String comprovante = String.format(
            "=== COMPROVANTE DE SAIDA ===\n\n" +
            "Ticket: #%d\n" +
            "Placa: %s\n" +
            "Modelo: %s\n" +
            "Tipo: %s\n\n" +
            "Valor Pago: R$ %.2f\n\n" +
            "Obrigado pela preferencia!",
            ticketAtual.getNumero(),
            ticketAtual.getVeiculo().getPlaca(),
            ticketAtual.getVeiculo().getModelo(),
            ticketAtual.getVeiculo().getTipo(),
            valor
        );

        JOptionPane.showMessageDialog(this,
            comprovante,
            "Saida Registrada",
            JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}
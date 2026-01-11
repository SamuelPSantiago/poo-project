package view;

import model.Caminhao;
import model.Carro;
import model.Moto;
import model.Ticket;
import model.Veiculo;
import service.Estacionamento;

import javax.swing.*;
import java.awt.*;

public class TelaEntrada extends JDialog {

    private final Estacionamento estacionamento;
    private JTextField txtPlaca;
    private JTextField txtModelo;
    private JComboBox<String> cmbTipo;

    public TelaEntrada(JFrame parent, Estacionamento estacionamento) {
        super(parent, "Registrar Entrada", true);
        this.estacionamento = estacionamento;
        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titulo = new JLabel("Registrar Entrada de Veiculo", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel painelFormulario = new JPanel(new GridLayout(3, 2, 10, 12));

        painelFormulario.add(new JLabel("Placa:"));
        txtPlaca = new JTextField();
        painelFormulario.add(txtPlaca);

        painelFormulario.add(new JLabel("Modelo:"));
        txtModelo = new JTextField();
        painelFormulario.add(txtModelo);

        painelFormulario.add(new JLabel("Tipo:"));
        cmbTipo = new JComboBox<>(new String[]{"Carro", "Moto", "Caminhao"});
        painelFormulario.add(cmbTipo);

        painelPrincipal.add(painelFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");

        btnRegistrar.addActionListener(e -> registrarEntrada());
        btnCancelar.addActionListener(e -> dispose());

        painelBotoes.add(btnRegistrar);
        painelBotoes.add(btnCancelar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private boolean validarPlaca(String placa) {
        String padrao = "^[A-Z]{3}-?[0-9]{4}$|^[A-Z]{3}[0-9][A-Z][0-9]{2}$";
        return placa.toUpperCase().matches(padrao);
    }

    private void registrarEntrada() {
        String placa = txtPlaca.getText().trim().toUpperCase();
        String modelo = txtModelo.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();

        if (placa.isEmpty() || modelo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos!",
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

        Veiculo veiculo;
        switch (tipo) {
            case "Carro":
                veiculo = new Carro(placa, modelo);
                break;
            case "Moto":
                veiculo = new Moto(placa, modelo);
                break;
            case "Caminhao":
                veiculo = new Caminhao(placa, modelo);
                break;
            default:
                return;
        }

        Ticket ticket = estacionamento.registrarEntrada(veiculo);

        if (ticket == null) {
            if (estacionamento.buscarVeiculoPorPlaca(placa) != null) {
                JOptionPane.showMessageDialog(this,
                        "Veiculo ja esta no estacionamento!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nao ha vagas disponiveis para " + tipo + "!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        String mensagem = String.format(
                "Entrada registrada com sucesso!\n\nTicket: #%d\nPlaca: %s\nModelo: %s\nTipo: %s\nVaga: %d",
                ticket.getNumero(),
                placa,
                modelo,
                tipo,
                ticket.getVaga().getId()
        );

        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}
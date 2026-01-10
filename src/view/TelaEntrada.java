package view;

import model.*;
import service.Estacionamento;
import utils.ComponenteUtils;
import javax.swing.*;
import java.awt.*;

import static utils.ComponenteUtils.*;

public class TelaEntrada extends JDialog {

    private Estacionamento estacionamento;
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
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(COR_FUNDO);

        // Titulo
        JLabel titulo = new JLabel("Registrar Entrada de Veiculo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(COR_TITULO);
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Formulario
        JPanel painelForm = new JPanel(new GridLayout(3, 2, 10, 15));
        painelForm.setBackground(COR_FUNDO);
        painelForm.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel lblPlaca = new JLabel("Placa:");
        lblPlaca.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPlaca = new JTextField();
        txtPlaca.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel lblModelo = new JLabel("Modelo:");
        lblModelo.setFont(new Font("Arial", Font.PLAIN, 14));
        txtModelo = new JTextField();
        txtModelo.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbTipo = new JComboBox<>(new String[]{"Carro", "Moto", "Caminhao"});
        cmbTipo.setFont(new Font("Arial", Font.PLAIN, 14));

        painelForm.add(lblPlaca);
        painelForm.add(txtPlaca);
        painelForm.add(lblModelo);
        painelForm.add(txtModelo);
        painelForm.add(lblTipo);
        painelForm.add(cmbTipo);

        painelPrincipal.add(painelForm, BorderLayout.CENTER);

        // Botoes
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        painelBotoes.setBackground(COR_FUNDO);

        JButton btnRegistrar = ComponenteUtils.criarBotao("Registrar", COR_SUCESSO, COR_SUCESSO_HOVER, 14);
        JButton btnCancelar = ComponenteUtils.criarBotao("Cancelar", COR_CANCELAR, COR_CANCELAR_HOVER, 14);

        btnRegistrar.addActionListener(e -> registrarEntrada());
        btnCancelar.addActionListener(e -> dispose());

        painelBotoes.add(btnRegistrar);
        painelBotoes.add(btnCancelar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private void registrarEntrada() {
        String placa = txtPlaca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();

        // Validacao
        if (placa.isEmpty() || modelo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Preencha todos os campos!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Criar veiculo do tipo selecionado
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

        // Registrar entrada
        Ticket ticket = estacionamento.registrarEntrada(veiculo);

        if (ticket == null) {
            // Verificar motivo do erro
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

        // Sucesso
        String mensagem = String.format(
            "Entrada registrada com sucesso!\n\n" +
            "Ticket: #%d\n" +
            "Placa: %s\n" +
            "Modelo: %s\n" +
            "Tipo: %s\n" +
            "Vaga: %d",
            ticket.getNumero(),
            placa,
            modelo,
            tipo,
            ticket.getVaga().getId()
        );

        JOptionPane.showMessageDialog(this,
            mensagem,
            "Sucesso",
            JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}

package view;

import service.Estacionamento;
import utils.ComponenteUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import static utils.ComponenteUtils.*;

public class TelaRelatorio extends JDialog {

    private Estacionamento estacionamento;
    private DefaultTableModel tableModel;
    private JLabel lblPercentual;

    public TelaRelatorio(JFrame parent, Estacionamento estacionamento) {
        super(parent, "Relatorio de Ocupacao", true);
        this.estacionamento = estacionamento;
        inicializarComponentes();
        atualizarDados();
        setVisible(true);
    }

    private void inicializarComponentes() {
        setSize(500, 360);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelPrincipal.setBackground(COR_FUNDO);

        // Titulo
        JLabel titulo = new JLabel("Relatorio de Ocupacao", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(COR_TITULO);
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"Tipo", "Total", "Ocupadas", "Disponiveis"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(tableModel);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));
        tabela.setRowHeight(30);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabela.getTableHeader().setBackground(COR_PRIMARIA);
        tabela.getTableHeader().setForeground(Color.WHITE);

        // Centralizar conteudo das celulas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createLineBorder(COR_BORDA, 1));

        // Painel central com tabela e percentual
        JPanel painelCentro = new JPanel(new BorderLayout(10, 10));
        painelCentro.setBackground(COR_FUNDO);
        painelCentro.add(scrollPane, BorderLayout.CENTER);

        // Label de percentual
        lblPercentual = new JLabel("", SwingConstants.CENTER);
        lblPercentual.setFont(new Font("Arial", Font.BOLD, 16));
        lblPercentual.setForeground(COR_TITULO);
        lblPercentual.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        painelCentro.add(lblPercentual, BorderLayout.SOUTH);

        painelPrincipal.add(painelCentro, BorderLayout.CENTER);

        // Botoes
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.setBackground(COR_FUNDO);

        JButton btnAtualizar = ComponenteUtils.criarBotao("Atualizar", COR_PRIMARIA, COR_PRIMARIA_HOVER, 14);
        JButton btnFechar = ComponenteUtils.criarBotao("Fechar", COR_CANCELAR, COR_CANCELAR_HOVER, 14);

        btnAtualizar.addActionListener(e -> atualizarDados());
        btnFechar.addActionListener(e -> dispose());

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnFechar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private void atualizarDados() {
        // Limpar tabela
        tableModel.setRowCount(0);

        // Dados
        int motoTotal = 10;
        int carroTotal = 20;
        int caminhaoTotal = 5;

        int motoOcupadas = estacionamento.contarVagasOcupadas("Moto");
        int carroOcupadas = estacionamento.contarVagasOcupadas("Carro");
        int caminhaoOcupadas = estacionamento.contarVagasOcupadas("Caminhao");

        // Adicionar linhas
        tableModel.addRow(new Object[]{"Moto", motoTotal, motoOcupadas, motoTotal - motoOcupadas});
        tableModel.addRow(new Object[]{"Carro", carroTotal, carroOcupadas, carroTotal - carroOcupadas});
        tableModel.addRow(new Object[]{"Caminhao", caminhaoTotal, caminhaoOcupadas, caminhaoTotal - caminhaoOcupadas});

        // Totais
        int total = motoTotal + carroTotal + caminhaoTotal;
        int totalOcupadas = motoOcupadas + carroOcupadas + caminhaoOcupadas;
        tableModel.addRow(new Object[]{"TOTAL", total, totalOcupadas, total - totalOcupadas});

        // Percentual
        double percentual = (totalOcupadas * 100.0) / total;
        lblPercentual.setText(String.format("Ocupacao Geral: %.1f%%", percentual));
    }
}

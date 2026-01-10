package service;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento {

    private List<Vaga> listaVagas;
    private List<Ticket> listaTickets;
    private int contadorTickets;

    public Estacionamento() {
        this.listaVagas = new ArrayList<>();
        this.listaTickets = new ArrayList<>();
        this.contadorTickets = 0;
        inicializarVagas();
    }

    // Inicializa as vagas do estacionamento.
    private void inicializarVagas() {
        // 10 vagas para Moto
        for (int i = 1; i <= 10; i++) listaVagas.add(new Vaga(i, "Moto"));
        
        // 20 vagas para Carro
        for (int i = 11; i <= 30; i++) listaVagas.add(new Vaga(i, "Carro"));
        
        // 5 vagas para Caminhao
        for (int i = 31; i <= 35; i++) listaVagas.add(new Vaga(i, "Caminhao"));
    }

    public List<Vaga> getListaVagas() {
        return listaVagas;
    }

    public List<Ticket> getListaTickets() {
        return listaTickets;
    }

    // Busca a primeira vaga disponivel do tipo especificado.
    public Vaga buscarVagaDisponivel(String tipo) {
        for (Vaga vaga : listaVagas) {
            if (vaga.getTipo().equals(tipo) && !vaga.isOcupada()) {
                return vaga;
            }
        }
        return null;
    }

    // Busca um veiculo pela placa nos tickets ativos.
    public Veiculo buscarVeiculoPorPlaca(String placa) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getHoraSaida() == null && ticket.getVeiculo().getPlaca().equals(placa)) {
                return ticket.getVeiculo();
            }
        }
        return null;
    }

    // Registra a entrada de um veiculo no estacionamento.
    public Ticket registrarEntrada(Veiculo veiculo) {
        // Verifica se veiculo ja esta no estacionamento
        if (buscarVeiculoPorPlaca(veiculo.getPlaca()) != null) {
            return null;
        }

        // Busca vaga disponivel
        Vaga vaga = buscarVagaDisponivel(veiculo.getTipo());
        if (vaga == null) {
            return null;
        }

        // Ocupa a vaga
        vaga.ocupar();

        // Cria o ticket
        contadorTickets++;
        Ticket ticket = new Ticket(contadorTickets, veiculo, vaga);

        // Adiciona ticket na lista
        listaTickets.add(ticket);

        return ticket;
    }

    // Busca o ticket ativo de um veiculo pela placa.
    public Ticket buscarTicketPorPlaca(String placa) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getHoraSaida() == null && ticket.getVeiculo().getPlaca().equals(placa)) {
                return ticket;
            }
        }
        return null;
    }

    // Registra a saida de um veiculo e retorna o valor a pagar.
    public double registrarSaida(String placa) {
        Ticket ticket = buscarTicketPorPlaca(placa);
        if (ticket == null) {
            return -1;
        }

        // Finaliza o ticket
        ticket.finalizarTicket();

        // Libera a vaga
        ticket.getVaga().liberar();

        return ticket.getValorPago();
    }

    // Retorna informacoes do veiculo estacionado.
    public String consultarVeiculo(String placa) {
        Ticket ticket = buscarTicketPorPlaca(placa);
        if (ticket == null) {
            return null;
        }

        Veiculo veiculo = ticket.getVeiculo();
        long tempo = ticket.calcularTempo();
        double valorEstimado = ticket.calcularValor();

        return String.format(
            "Placa: %s\nModelo: %s\nTipo: %s\nVaga: %d\nTempo: %d hora(s)\nValor Estimado: R$ %.2f",
            veiculo.getPlaca(),
            veiculo.getModelo(),
            veiculo.getTipo(),
            ticket.getVaga().getId(),
            tempo,
            valorEstimado
        );
    }

    // Conta vagas disponiveis por tipo.
    public int contarVagasDisponiveis(String tipo) {
        int count = 0;
        for (Vaga vaga : listaVagas) {
            if (vaga.getTipo().equals(tipo) && !vaga.isOcupada()) {
                count++;
            }
        }
        return count;
    }

    // Lista vagas disponiveis por tipo.
    public String listarVagasDisponiveis() {
        int motoDisponiveis = contarVagasDisponiveis("Moto");
        int carroDisponiveis = contarVagasDisponiveis("Carro");
        int caminhaoDisponiveis = contarVagasDisponiveis("Caminhao");

        return String.format(
            "Vagas Disponiveis:\n- Moto: %d/10\n- Carro: %d/20\n- Caminhao: %d/5",
            motoDisponiveis,
            carroDisponiveis,
            caminhaoDisponiveis
        );
    }

    // Conta vagas ocupadas por tipo.
    public int contarVagasOcupadas(String tipo) {
        int count = 0;
        for (Vaga vaga : listaVagas) {
            if (vaga.getTipo().equals(tipo) && vaga.isOcupada()) {
                count++;
            }
        }
        return count;
    }

    // Gera relatorio de ocupacao do estacionamento.
    public String gerarRelatorio() {
        int motoTotal = 10;
        int carroTotal = 20;
        int caminhaoTotal = 5;
        int total = motoTotal + carroTotal + caminhaoTotal;

        int motoOcupadas = contarVagasOcupadas("Moto");
        int carroOcupadas = contarVagasOcupadas("Carro");
        int caminhaoOcupadas = contarVagasOcupadas("Caminhao");
        int totalOcupadas = motoOcupadas + carroOcupadas + caminhaoOcupadas;

        double percentualGeral = (totalOcupadas * 100.0) / total;

        return String.format(
            "============ RELATORIO DE OCUPACAO ============\n\n" +
            "| Tipo       | Total | Ocupadas | Disponiveis |\n" +
            "|------------|-------|----------|-------------+\n" +
            "| Moto       |  %2d   |    %2d    |     %2d |\n" +
            "| Carro      |  %2d   |    %2d    |     %2d |\n" +
            "| Caminhao   |  %2d   |    %2d    |     %2d |\n" +
            "+------------|-------|----------|-------------+\n" +
            "| TOTAL      |  %2d   |    %2d    |     %2d |\n\n" +
            "+------------|-------|----------|-------------+\n" +
            "| Percentual de Ocupacao: %6.1f%%              |"+
            "===============================================\n\n",
            motoTotal, motoOcupadas, motoTotal - motoOcupadas,
            carroTotal, carroOcupadas, carroTotal - carroOcupadas,
            caminhaoTotal, caminhaoOcupadas, caminhaoTotal - caminhaoOcupadas,
            total, totalOcupadas, total - totalOcupadas,
            percentualGeral
        );
    }
}

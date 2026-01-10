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
}

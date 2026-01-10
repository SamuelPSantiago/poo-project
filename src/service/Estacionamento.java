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
}

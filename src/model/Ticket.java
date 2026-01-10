package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Ticket {

    private int numero;
    private Veiculo veiculo;
    private Vaga vaga;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private double valorPago;

    public Ticket(int numero, Veiculo veiculo, Vaga vaga) {
        this.numero = numero;
        this.veiculo = veiculo;
        this.vaga = vaga;
        this.horaEntrada = LocalDateTime.now();
        this.horaSaida = null;
        this.valorPago = 0.0;
    }

    public int getNumero() {
        return numero;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public double getValorPago() {
        return valorPago;
    }

    // Calcula o tempo de permanencia em horas.
    public long calcularTempo() {
        LocalDateTime saida = (horaSaida != null) ? horaSaida : LocalDateTime.now();
        long horas = ChronoUnit.HOURS.between(horaEntrada, saida);
        if (horas < 1) {
            horas = 1; // Minimo 1 hora
        }
        return horas;
    }

    // Calcula o valor a pagar usando a tarifa do veiculo.
    public double calcularValor() {
        return veiculo.calcularTarifa(calcularTempo());
    }

    // Finaliza o ticket registrando a saida e calculando o valor.
    public void finalizarTicket() {
        this.horaSaida = LocalDateTime.now();
        this.valorPago = calcularValor();
    }
}

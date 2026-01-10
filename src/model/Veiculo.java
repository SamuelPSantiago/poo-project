package model;

import java.time.LocalDateTime;

public abstract class Veiculo {

    private String placa;
    private String modelo;
    private LocalDateTime horaEntrada;

    public Veiculo(String placa, String modelo) {
        this.placa = placa;
        this.modelo = modelo;
        this.horaEntrada = LocalDateTime.now();
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    // Calcula a tarifa com base no tempo de permanencia.
    public abstract double calcularTarifa(long horas);

    // Retorna o tipo do veiculo.
    public abstract String getTipo();
}

package model;

public class Carro extends Veiculo {

    private static final double TARIFA_HORA = 5.00;

    public Carro(String placa, String modelo) {
        super(placa, modelo);
    }

    @Override
    public double calcularTarifa(long horas) {
        return horas * TARIFA_HORA;
    }

    @Override
    public String getTipo() {
        return "Carro";
    }
}

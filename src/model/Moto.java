package model;

public class Moto extends Veiculo {

    private static final double TARIFA_HORA = 3.00;

    public Moto(String placa, String modelo) {
        super(placa, modelo);
    }

    @Override
    public double calcularTarifa(long horas) {
        return horas * TARIFA_HORA;
    }

    @Override
    public String getTipo() {
        return "Moto";
    }
}

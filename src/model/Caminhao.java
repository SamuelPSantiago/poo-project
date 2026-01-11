package model;

public class Caminhao extends Veiculo {

    private static final double TARIFA_HORA = 10.00;

    public Caminhao(String placa, String modelo) {
        super(placa, modelo);
    }

    @Override
    public double calcularTarifa(long horas) {
        return horas * TARIFA_HORA;
    }

    @Override
    public String getTipo() {
        return "Caminhao";
    }
    
}

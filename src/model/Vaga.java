package model;

public class Vaga {

    private int id;
    private String tipo;
    private boolean ocupada;

    public Vaga(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
        this.ocupada = false;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    // Define a vaga como ocupada.
    public void ocupar() {
        this.ocupada = true;
    }

    // Libera a vaga para uso.
    public void liberar() {
        this.ocupada = false;
    }
}

package pt.isec.pd.comum.modelos;

public class Despesa {
    private double valor;

    public Despesa(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Despesa{" +
                "valor=" + valor +
                '}';
    }
}

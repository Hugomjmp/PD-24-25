package pt.isec.pd.comum.modelos.mensagens;

public class CriaDespesa {
    private double valor;

    public CriaDespesa(double valor) {
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

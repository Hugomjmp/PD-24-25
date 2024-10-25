package pt.isec.pd.comum.modelos;

import java.io.Serializable;
import java.util.List;

public class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private double valor;
    private String data;
    private String pagaPor;
    private String recebidoPor;
    private String groupId;

    private List<Pagamento> pagamentosList;

    public Pagamento(double valor, String data, String pagaPor, String recebidoPor, String groupId) {
        this.valor = valor;
        this.data = data;
        this.pagaPor = pagaPor;
        this.recebidoPor = recebidoPor;
        this.groupId = groupId;
    }

    public List<Pagamento> getPagamentosList() {
        return pagamentosList;
    }

    public void setPagamentosList(List<Pagamento> pagamentosList) {
        this.pagamentosList = pagamentosList;
    }

    // Getters e Setters restantes
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPagaPor() {
        return pagaPor;
    }

    public void setPagaPor(String pagaPor) {
        this.pagaPor = pagaPor;
    }

    public String getRecebidoPor() {
        return recebidoPor;
    }

    public void setRecebidoPor(String recebidoPor) {
        this.recebidoPor = recebidoPor;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "valor=" + valor +
                ", data='" + data + '\'' +
                ", pagaPor='" + pagaPor + '\'' +
                ", recebidoPor='" + recebidoPor + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}

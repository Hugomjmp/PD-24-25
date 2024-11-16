package pt.isec.pd.comum.modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private String groupId; // provavelmente vais ter que passar isto int groupID
    private String data;
    private double valor;
    private String pagaPor;
    private String recebidoPor;

    private List<Pagamento> pagamentos;


/*    public Pagamento(String groupId, String data, double valor, String pagaPor, String recebidoPor) {
        this.groupId = groupId;
        this.data = data;
        this.valor = valor;
        this.pagaPor = pagaPor;
        this.recebidoPor = recebidoPor;
        this.pagamentos = new ArrayList<>();
    }*/

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
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


    public void adicionarPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "groupId='" + groupId + '\'' +
                ", data='" + data + '\'' +
                ", valor=" + valor +
                ", pagaPor='" + pagaPor + '\'' +
                ", recebidoPor='" + recebidoPor + '\'' +
                '}';
    }
}

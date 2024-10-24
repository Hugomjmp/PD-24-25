package pt.isec.pd.comum.modelos.mensagens;

import java.io.Serializable;

public class EliminaPagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private String paymentId; 
    private String groupId;

    public EliminaPagamento(String paymentId, String groupId) {
        this.paymentId = paymentId;
        this.groupId = groupId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "EliminaPagamento{" +
                "paymentId='" + paymentId + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}

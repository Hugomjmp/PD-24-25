package pt.isec.pd.comum.modelos.mensagens;

import java.io.Serializable;

public class ListarGrupo implements Serializable {
    private String solicitadoPor;

    // Construtor
    public ListarGrupo(String solicitadoPor) {
        this.solicitadoPor = solicitadoPor;
    }

    // Getter e Setter para solicitadoPor
    public String getSolicitadoPor() {
        return solicitadoPor;
    }

    public void setSolicitadoPor(String solicitadoPor) {
        this.solicitadoPor = solicitadoPor;
    }

    @Override
    public String toString() {
        return "ListarGrupo{" +
                "solicitadoPor='" + solicitadoPor + '\'' +
                '}';
    }
}

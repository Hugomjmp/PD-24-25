package pt.isec.pd.comum.modelos;

import java.io.Serializable;

public class Convites implements Serializable {
    private String nomeRecepiente;
    private String grupoNome;
    private String estado;



    public String getNomeRecepiente() {
        return nomeRecepiente;
    }

    public void setNomeRecepiente(String nomeRecepiente) {
        this.nomeRecepiente = nomeRecepiente;
    }

    public String getGrupoNome() {
        return grupoNome;
    }

    public void setGrupoNome(String grupoNome) {
        this.grupoNome = grupoNome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Convites{" +
                " nomeRecepiente='" + nomeRecepiente + '\'' +
                ", grupoNome='" + grupoNome + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}

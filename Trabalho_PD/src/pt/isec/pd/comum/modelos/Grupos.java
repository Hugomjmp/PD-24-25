package pt.isec.pd.comum.modelos;

import javax.swing.plaf.basic.BasicDesktopIconUI;
import java.util.List;

public class Grupos {
    private String nomeGrupo;
    private String criadoPor;
    private List<Grupos> gruposList;


    //private String eliminadoPor;

    public List<Grupos> getGruposList() {
        return gruposList;
    }

    public void setGruposList(List<Grupos> gruposList) {
        this.gruposList = gruposList;
    }

    public Grupos(String nomeGrupo, String criadoPor ){ // String eliminadoPor
        this.criadoPor = criadoPor;
        this.nomeGrupo = nomeGrupo;
        //this.eliminadoPor = eliminadoPor;
    }
    public String getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "criadoPor='" + criadoPor + '\'' +
                ", nomeGrupo='" + nomeGrupo + '\'' +
                '}';
    }
}

package pt.isec.pd.cliente.modelos;

import pt.isec.pd.comum.modelos.Convites;
import pt.isec.pd.comum.modelos.Grupos;
import pt.isec.pd.comum.modelos.User;
import java.util.List;


public class Dados {
    private User utilizadorLogado;
    private Convites convites;
    private Grupos GrupoSelecionado;
    List<User> utilizadores;
    private Grupos grupos;

    public Dados(){
        this.utilizadorLogado = null;
    }

    public synchronized Grupos getGrupoSelecionado() {
        return GrupoSelecionado;
    }

    public synchronized void setGrupoSelecionado(Grupos grupoSelecionado) {
        GrupoSelecionado = grupoSelecionado;
    }

    public synchronized Grupos getGrupos() {
        return grupos;
    }

    public synchronized void setGrupos(Grupos grupos) {
        this.grupos = grupos;
    }

    public synchronized User getUtilizadorLogado(){
        return utilizadorLogado;
    }
    public synchronized void setUtilizadorLogado(User utilizadorLogado){
        this.utilizadorLogado = utilizadorLogado;
    }

    public synchronized List<User> getUtilizadores(){
        return utilizadores;
    }
    public synchronized void setUtilizadores(List<User> conteudo){
        this.utilizadores = conteudo;
    }

    public synchronized Convites getConvites(){
        return convites;
    }






}

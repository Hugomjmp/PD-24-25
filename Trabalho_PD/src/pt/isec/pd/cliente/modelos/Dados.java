package pt.isec.pd.cliente.modelos;

import pt.isec.pd.comum.modelos.Convites;
import pt.isec.pd.comum.modelos.User;
import java.util.List;


public class Dados {
    private User utilizadorLogado;
    private Convites convites;
    List<User> utilizadores;

    public Dados(){
        this.utilizadorLogado = null;
    }

    public User getUtilizadorLogado(){
        return utilizadorLogado;
    }
    public void setUtilizadorLogado(User utilizadorLogado){
        this.utilizadorLogado = utilizadorLogado;
    }

    public List<User> getUtilizadores(){
        return  utilizadores;
    }
    public void setUtilizadores(List<User> conteudo){
        this.utilizadores = conteudo;
    }

    public Convites getConvites(){
        return convites;
    }






}

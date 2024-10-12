package pt.isec.pd.cliente.modelos;

import pt.isec.pd.comum.modelos.User;
import java.util.List;


public class Dados {
    private User utilizadorLogado;
    List<User> utilizadores;

    public Dados(){
        this.utilizadorLogado = null;
    }

    public User getUtilizadorLogado(){
        return utilizadorLogado;
    }
    public void setUtilizadorLogado(User estado){
        this.utilizadorLogado = estado;
    }

    public List<User> getUtilizadores(){
        return  utilizadores;
    }
    public void setUtilizadores(List<User> conteudo){
        this.utilizadores = conteudo;
    }





}

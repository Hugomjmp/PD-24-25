package pt.isec.pd.cliente.modelos;

import pt.isec.pd.comum.User;
import java.util.List;


public class Dados {
    private User estado;
    List<User> utilizadores;

    public Dados(){
        this.estado = null;
    }

    public User getEstado(){
        return estado;
    }
    public void setEstado(User estado){
        this.estado = estado;
    }

    public List<User> getUserSet(){
        return  utilizadores;
    }
    public void setUserSet(List<User> conteudo){
        this.utilizadores = conteudo;
    }





}

package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.User;
import pt.isec.pd.comum.modelos.Login;
import pt.isec.pd.comum.modelos.Registo;
import pt.isec.pd.db.Bd;

public class ControladorAutenticacao {

    public static void registo(Registo registo){
        System.out.println(registo.getNome()+registo.getnTelefone()+registo.getEmail()+registo.getPassword());
        Bd.setUserDB(registo.getNome(),registo.getnTelefone(),registo.getEmail(),registo.getPassword());
    }
    public static void login(Login login){
        System.out.println(login.getEmail() + login.getPassword());
        Bd.getUserDB(login.getEmail(), login.getPassword());

    }
    public boolean edita(){
        return true; //se teve sucesso
    }
}

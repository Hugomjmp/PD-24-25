package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.modelos.User;
import pt.isec.pd.comum.modelos.mensagens.EditaConta;
import pt.isec.pd.comum.modelos.mensagens.Login;
import pt.isec.pd.comum.modelos.mensagens.Registo;
import pt.isec.pd.db.Bd;

import java.io.Serializable;

public class ControladorAutenticacao {

    public static void registo(Registo registo){
        System.out.println(registo.getNome()+registo.getnTelefone()+registo.getEmail()+registo.getPassword());
        Bd.setUserDB(registo.getNome(),registo.getnTelefone(),registo.getEmail(),registo.getPassword());
    }
    public static User login(Login login){
        /*Serializable userLogin = null;*/
        return Bd.getUserDB(login.getEmail(), login.getPassword());
        //System.out.println(userLogin);

    }
    public static void edita(EditaConta edita){
        System.out.println(edita.getEmail() + edita.getPassword() + edita.getnTelefone());
        Bd.editaUserBD(edita.getnTelefone(),edita.getEmail(),edita.getPassword());
    }
}

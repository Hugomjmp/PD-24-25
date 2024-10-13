package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.modelos.User;
import pt.isec.pd.comum.modelos.mensagens.EditaConta;
import pt.isec.pd.comum.modelos.mensagens.Login;
import pt.isec.pd.comum.modelos.mensagens.Registo;
import pt.isec.pd.db.Bd;

import java.io.Serializable;

public class ControladorAutenticacao {

    public static Estados registo(Registo registo){
        //System.out.println(registo.getNome()+registo.getnTelefone()+registo.getEmail()+registo.getPassword());
        Serializable  userRegisto = null;
        try {
            userRegisto = Bd.setUserDB(registo.getNome(),registo.getnTelefone(),registo.getEmail(),registo.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userRegisto == null ? Estados.ERRO_REGISTO : Estados.USER_REGISTADO_COM_SUCESSO.setDados(userRegisto);


    }
    public static Estados login(Login login){
        Serializable userLogin = null;
        try {
            userLogin = Bd.getUserDB(login.getEmail(), login.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userLogin == null ? Estados.ERRO_AUTENTICACAO : Estados.USER_LOGADO_COM_SUCESSO.setDados(userLogin);

        //System.out.println(userLogin);

    }
    public static void edita(EditaConta edita){
        System.out.println(edita.getEmail() + edita.getPassword() + edita.getnTelefone());
        Bd.editaUserBD(edita.getnTelefone(),edita.getEmail(),edita.getPassword());
    }
}

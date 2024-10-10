package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.modelos.Login;
import pt.isec.pd.comum.modelos.Mensagem;

public class ControladorMensagem {
    private static String msg;
    public static void RespostaServidor(Mensagem mensagem){

        switch (mensagem.getTipoMensagem()){
            case USER_REGISTO:
            {

            }
            case LOGIN:
            {

                ControladorAutenticacao.login((Login) mensagem.getConteudo());
                msg = "Login recebido para: " + mensagem.getConteudo();
                break;
            }
            case LOGOUT:
            {

            }


        }
    }
    @Override
    public String toString() {
        return "Mensagem: " + msg;
    }
}

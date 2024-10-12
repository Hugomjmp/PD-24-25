package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.modelos.RespostaServidorMensagem;
import pt.isec.pd.comum.modelos.mensagens.Login;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.Registo;

public class ControladorMensagem {
    private static String msg;
    public static RespostaServidorMensagem respostaServidor(Mensagem mensagem){

        switch (mensagem.getTipoMensagem()){
            case USER_REGISTO:
            {
                ControladorAutenticacao.registo((Registo) mensagem.getConteudo());
                msg = "Registo recebido para: " + mensagem.getConteudo();
                RespostaServidorMensagem resposta = new RespostaServidorMensagem(msg);
                return resposta;
            }
            case LOGIN:
            {

                ControladorAutenticacao.login((Login) mensagem.getConteudo());

                msg = "Login recebido para: " + mensagem.getConteudo();
                RespostaServidorMensagem resposta = new RespostaServidorMensagem(msg);
                return resposta;


            }
            case LOGOUT: //PARA FAZER
            {

            }


        }
        return respostaServidor(null);
    }
    @Override
    public String toString() {
        return "Mensagem: " + msg;
    }
}

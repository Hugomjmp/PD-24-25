package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.modelos.RespostaServidorMensagem;
import pt.isec.pd.comum.modelos.User;
import pt.isec.pd.comum.modelos.mensagens.Login;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.Registo;

public class ControladorMensagemServidor {
    private static String msg;
    public static RespostaServidorMensagem respostaServidor(Mensagem mensagem){

        RespostaServidorMensagem resposta;
        switch (mensagem.getTipoMensagem()){
            case USER_REGISTO:
            {
                /*Estados estado = ControladorAutenticacao.registo((Registo) mensagem.getConteudo());
                msg = "Registo recebido para: " + mensagem.getConteudo();
                resposta = new RespostaServidorMensagem(estado,estado.getDados());
                return resposta;*/
            }
            case LOGIN:
            {
                //User user;
                Estados estado = ControladorAutenticacao.login((Login) mensagem.getConteudo());

                //msg = "Login recebido para: " + mensagem.getConteudo();
                //RespostaServidorMensagem resposta = new RespostaServidorMensagem(msg);

                resposta = new RespostaServidorMensagem(estado,estado.getDados());
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

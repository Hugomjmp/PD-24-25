package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.modelos.RespostaServidorMensagem;
import pt.isec.pd.comum.modelos.mensagens.CriaGrupo;
import pt.isec.pd.comum.modelos.mensagens.Login;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.Registo;

public class ControladorMensagemServidor {
    private static String msg;
    public static RespostaServidorMensagem respostaServidor(Mensagem mensagem){

        RespostaServidorMensagem resposta = null;
        switch (mensagem.getTipoMensagem()){
            case USER_REGISTO:
            {
                resposta = new RespostaServidorMensagem(ControladorAutenticacao.registo((Registo)
                        mensagem.getConteudo()),null);
                break;
            }
            case LOGIN:
            {
                //User user;
                Estados estado = ControladorAutenticacao.login((Login) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado,estado.getDados());
                break;


            }
            case LOGOUT: //PARA FAZER
            {

            }
            case USER_CRIA_GRUPO:
            {
                System.out.println("OLA");
                System.out.println(mensagem);
                Estados estado = ControladorGrupoServidor.grupoRegisto((CriaGrupo) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
                //Estados estado =
            }

        }
        return resposta;
    }
    @Override
    public String toString() {
        return "Mensagem: " + msg;
    }
}

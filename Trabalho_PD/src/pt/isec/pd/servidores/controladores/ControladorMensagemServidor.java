package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.modelos.RespostaListagemGrupos;
import pt.isec.pd.comum.modelos.RespostaServidorMensagem;
import pt.isec.pd.comum.modelos.mensagens.*;
import pt.isec.pd.comum.modelos.Mensagem;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
            case USER_INSERIDO_NO_GRUPO:
            {
                System.out.println(mensagem);
                Estados estado = ControladorGrupoServidor.insereGrupo((InsereGrupo) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado,estado.getDados());
                break;
            }

            case USER_LISTA_GRUPOS: {
                System.out.println("A Listar grupos...");
                List<String> grupos = ControladorGrupoServidor.listarGrupos((ListarGrupo) mensagem.getConteudo());


                if (grupos != null && !grupos.isEmpty()) {
                    System.out.println("Rumo 1");
                    resposta = new RespostaListagemGrupos(Estados.GRUPO_LISTADO_COM_SUCESSO, grupos);
                }
                break;
            }

            case USER_ELIMINA_GRUPO:
            {
                System.out.println("Cheguei");
                System.out.println(mensagem);
                Estados estado = ControladorGrupoServidor.EliminaRegisto((EliminaGrupo) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }

        }
        return resposta;
    }
    @Override
    public String toString() {
        return "Mensagem: " + msg;
    }
}

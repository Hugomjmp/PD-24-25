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
            case USER_EDITA_INFORMACAO:
            {
                Estados estados = ControladorAutenticacao.edita((EditaConta) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estados,estados.getDados());
                break;
            }
            case LOGOUT: //PARA FAZER
            {
                break;
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
            case USER_EDITA_GRUPO:
            {
                Estados estado = ControladorGrupoServidor.editaGrupo((EditarGrupo) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }

            case USER_LISTA_GRUPOS:
            {
                System.out.println("A Listar grupos...");
                Estados estado = ControladorGrupoServidor.listarGrupos((ListarGrupo) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                //System.out.println("USER_LISTA_GRUPOS -> " +  estado);
                //System.out.println("RESPOSTA -> " +resposta);
                break;
            }
            case USER_SAI_GRUPO: {
                System.out.println("Sai do Grupo...");

                SairGrupo sairGrupo = (SairGrupo) mensagem.getConteudo();
                String grupoNome = sairGrupo.getNomeGrupo();
                String emailUser = sairGrupo.getEmailUser();

                Estados estado = ControladorGrupoServidor.sairGrupo(grupoNome, emailUser);


                resposta = new RespostaServidorMensagem(estado, null);
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
            case USER_CRIA_CONVITE: {
                System.out.println("cria convite...");
                System.out.println(mensagem);
                Estados estado = ControladorGrupoServidor.criaConvite((CriaConvite) mensagem.getConteudo());

                resposta = new RespostaServidorMensagem(estado, null);

                break;
            }
            case USER_LISTA_CONVITES: {

                Estados estado = ControladorGrupoServidor.verConvite((VerConvites) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }
            case USER_DECIDE_CONVITE: {
                Estados estado = ControladorGrupoServidor.decidirConvite((DecidirConvite) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }
        }
        ///System.out.println("->Controlador Mensagem Servidor " + resposta);
        return resposta;
    }
}

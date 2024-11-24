package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.modelos.Pagamento;
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
                Estados estado = ControladorAutenticacao.registo((Registo) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
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
                System.out.println("ESTADINHO " + estado);
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                System.out.println("RESPOSTA..... -> "+ resposta);
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
            case USER_SELECIONA_GRUPO:
            {
                System.out.println("A selecionar grupo...");
                Estados estado = ControladorGrupoServidor.selecionaGrupo((SelecionarGrupo) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }
            case USER_SAI_GRUPO: {
                System.out.println("Sai do Grupo...");

                SairGrupo sairGrupo = (SairGrupo) mensagem.getConteudo();
                String grupoNome = sairGrupo.getNomeGrupo();
                String emailUser = sairGrupo.getEmailUser();

                Estados estado = ControladorGrupoServidor.sairGrupo(grupoNome, emailUser);
                //System.out.println("ESTADO -> " + estado.getMensagem());
                resposta = new RespostaServidorMensagem(estado, estado.getMensagem());
                //System.out.println("RESPOSTA -> " + resposta);
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

            case USER_INSERE_PAGAMENTO: {
                System.out.println("Insere Pagamento...");
                System.out.println(mensagem);
                Estados estado = ControladorPagamentoServidor.InserirPagamento((InserePagamento) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }

            case USER_ELIMINA_PAGAMENTO:{
                System.out.println("Elimina Pagamento...");
                System.out.println(mensagem);
                Estados estado = ControladorPagamentoServidor.eliminarPagamento((EliminaPagamento) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
                }

            case USER_LISTA_PAGAMENTOS: {
                System.out.println("Listar Pagamento...");
                ListarPagamentos listarPagamentos = (ListarPagamentos) mensagem.getConteudo();
                Estados estado = ControladorPagamentoServidor.ListarPagamentos(listarPagamentos);

                /*if (estado == Estados.PAGAMENTO_LISTADO_COM_SUCESSO && estado.getDados() instanceof List<?>) {
                    System.out.println("Pagamentos listados com sucesso.");
                } else {
                    System.out.println("Erro ao listar pagamentos: " + estado);
                }*/

                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }


            case USER_CRIA_CONVITE:
            {
                System.out.println("cria convite...");
                System.out.println(mensagem);
                Estados estado = ControladorGrupoServidor.criaConvite((CriaConvite) mensagem.getConteudo());

                resposta = new RespostaServidorMensagem(estado, null);

                break;
            }
            case USER_LISTA_CONVITES:
            {

                Estados estado = ControladorGrupoServidor.verConvite((VerConvites) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }
            case USER_DECIDE_CONVITE:
            {
                Estados estado = ControladorGrupoServidor.decidirConvite((DecidirConvite) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }

            case USER_INSERE_DESPESA:
            {
                Estados estado = ControladorDespesaServidor.criaDespesa((CriaDespesa) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }
            case USER_VISUALIZA_GASTOS:
            {
                Estados estado = ControladorDespesaServidor.verGastoTotal((VerGastoTotal) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }
            case USER_VISUALIZA_TOTAL_DEVE:
            {
                Estados estado = ControladorDespesaServidor.verTotalDeve((VerTotalDeve) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }
            case USER_VISUALIZA_DEVE_POR_MEMBRO:
            {
                Estados estado = ControladorDespesaServidor.verTotalDeveDividido((VerDevePorMembro) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado,estado.getDados());
                break;
            }
            case USER_EXPORTA_HISTORICO_CSV_FICHEIRO:
            {
                Estados estado = ControladorDespesaServidor.exportCSV((ExportarDespesas) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }
            case USER_EDITA_DESPESA:
            {
                Estados estado = ControladorDespesaServidor.editaDespesa((EditarDespesa) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado, estado.getDados());
                break;
            }
            case USER_HISTORICO_DESPESAS:
            {
                Estados estado = ControladorDespesaServidor.historicoDespesa((HistoricoDespesa) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado,estado.getDados());
                break;
            }
            case USER_ELIMINA_DESPESA:
            {
                Estados estado = ControladorDespesaServidor.eliminaDespesa((EliminaDespesa) mensagem.getConteudo());
                resposta = new RespostaServidorMensagem(estado,estado.getDados());
                break;
            }


        }
        return resposta;
    }
}

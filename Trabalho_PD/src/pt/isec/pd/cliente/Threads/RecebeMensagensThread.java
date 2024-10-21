package pt.isec.pd.cliente.Threads;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.cliente.modelos.Dados;
import pt.isec.pd.cliente.vistas.Vista;
import pt.isec.pd.comum.modelos.Convites;
import pt.isec.pd.comum.modelos.Grupos;
import pt.isec.pd.comum.modelos.RespostaServidorMensagem;
import pt.isec.pd.comum.modelos.User;

public class RecebeMensagensThread extends Thread {
    private Ligacao ligacao;
    private Dados dados;


    public RecebeMensagensThread(Ligacao ligacao, Dados dados){
        this.ligacao = ligacao;
        this.dados = dados;
    }

    @Override
    public void run() {
        while (true) {

            RespostaServidorMensagem resposta = ligacao.recebeMensagem();
            System.out.println("Recebe Mensagem THREAD -> " + resposta);
            try {

                switch (resposta.getEstado()) {
                    case USER_LOGADO_COM_SUCESSO -> {
                        dados.setUtilizadorLogado((User) resposta.getConteudo());
                        resposta.getEstado();
                        System.out.println("DADOS THREAD: " + dados.getUtilizadorLogado());
                    }

                    case USER_REGISTADO_COM_SUCESSO -> {


                    }
                    case USER_GRUPO_SELECIONADO_COM_SUCESSO -> {
                        dados.setGrupoSelecionado((Grupos) resposta.getConteudo());
                        resposta.getEstado();
                    }
/*              case ERRO_CRIA_CONVITE -> {


                }*/
                    case CONSULTA_DESPESA_TOTAL_COM_SUCESSO -> {
                        System.out.println(" HELLO DESPESA TOTAL " + resposta.getEstado());
                        System.out.println("O gasto total no grupo foi de: " + resposta.getConteudo() + "€");
                        resposta.getEstado();
                    }
                    case USER_CRIA_DESPESA_COM_SUCESSO -> {
                        System.out.println(" HELLO DESPESA " + resposta.getEstado());
                        resposta.getEstado();
                    }

                    case GRUPO_USER_INSERIDO_COM_SUCESSO -> {
                        System.out.println(" HELLO INTEGRA " + resposta.getEstado());
                        resposta.getEstado();
                    }
                    case GRUPO_REGISTADO_COM_SUCESSO -> {
                        System.out.println(" HELLO REGISTO" + resposta.getEstado());
                        resposta.getEstado();
                    }
                    case GRUPO_CONVITE_COM_SUCESSO -> {
                        System.out.println(" HELLO " + resposta.getEstado());
                        resposta.getEstado();
                    }
                    case GRUPO_NOME_ALTERADO_COM_SUCESSO -> {
                        System.out.println(" HELLO " + resposta.getEstado());
                        resposta.getEstado();
                    }
                    case VER_CONVITES_COM_SUCESSO -> {
                        Convites convites = (Convites) resposta.getConteudo();
                        Vista.tabelaConvites(convites);
                        resposta.getEstado();
                    }

                    case GRUPO_LISTADO_COM_SUCESSO -> { //tratar deste warnig depois
                        Grupos grupos = (Grupos) resposta.getConteudo();
                        Vista.tabelaGrupos(grupos);
                        System.out.println(" HELLO " + resposta.getEstado());
                        resposta.getEstado();

                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}








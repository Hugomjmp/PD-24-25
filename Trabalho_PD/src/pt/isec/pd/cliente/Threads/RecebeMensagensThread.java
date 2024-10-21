package pt.isec.pd.cliente.Threads;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.cliente.modelos.Dados;
import pt.isec.pd.cliente.vistas.Vista;
import pt.isec.pd.comum.enumeracoes.Estados;
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
    public void run(){
        while (true){
            RespostaServidorMensagem resposta = ligacao.recebeMensagem();
            try {

                //System.out.println("Recebe Mensagem -> "+resposta);

                    switch (resposta.getEstado()){
                        case CONSULTA_DESPESA_TOTAL_COM_SUCESSO -> {
                            System.out.println(" HELLO DESPESA TOTAL ");
                            System.out.println("O gasto total no grupo foi de: "  + "€");

                        }
                        case USER_CRIA_DESPESA_COM_SUCESSO -> {
                            System.out.println(" HELLO DESPESA " );

                        }

                        case GRUPO_USER_INSERIDO_COM_SUCESSO ->{
                            System.out.println(" HELLO INTEGRA ");

                        }
                        case GRUPO_REGISTADO_COM_SUCESSO ->{
                            System.out.println(" HELLO REGISTO");

                        }
                        case GRUPO_CONVITE_COM_SUCESSO -> {
                            System.out.println(" HELLO ");

                        }
                        case GRUPO_NOME_ALTERADO_COM_SUCESSO -> {
                            System.out.println(" HELLO ");

                        }
                        case VER_CONVITES_COM_SUCESSO -> {
                            //Convites convites = (Convites) estado.getDados();
                            //Vista.tabelaConvites(convites);
                            System.out.println(" HELLO ");
                        }

                        case GRUPO_LISTADO_COM_SUCESSO -> { //tratar deste warnig depois
                            /*Grupos grupos = (Grupos) estado.getDados();
                            Vista.tabelaGrupos(grupos);*/
                            System.out.println(" HELLO ");


                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }
        }
}








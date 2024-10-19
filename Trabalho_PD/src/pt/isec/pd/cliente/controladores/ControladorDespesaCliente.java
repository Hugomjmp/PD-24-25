package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.enumeracoes.Tipomensagemenum;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.CriaDespesa;

public class ControladorDespesaCliente {
    public static void inserirDespesa(Ligacao ligacao, String email,String grupo, double despesa, String quemPagou, String descricao, String data ){
        CriaDespesa criaDespesa = new CriaDespesa(despesa,grupo,email,quemPagou,descricao,data);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_INSERE_DESPESA,criaDespesa);
        ligacao.enviaMensagem(mensagem);
    }
}

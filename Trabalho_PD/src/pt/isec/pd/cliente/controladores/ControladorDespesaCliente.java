package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.enumeracoes.Tipomensagemenum;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.CriaDespesa;
import pt.isec.pd.comum.modelos.mensagens.ExportarDespesas;
import pt.isec.pd.comum.modelos.mensagens.VerGastoTotal;

public class ControladorDespesaCliente {
    public static void inserirDespesa(Ligacao ligacao, String email,String grupo, double despesa, String quemPagou, String descricao, String data ){
        CriaDespesa criaDespesa = new CriaDespesa(despesa,grupo,email,quemPagou,descricao,data);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_INSERE_DESPESA,criaDespesa);
        ligacao.enviaMensagem(mensagem);
    }
    public static void verDespesaTotal(Ligacao ligacao, String email, String grupoNome){
        VerGastoTotal verGastoTotal = new VerGastoTotal(grupoNome,email);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_VISUALIZA_GASTOS, verGastoTotal);
        ligacao.enviaMensagem(mensagem);
    }
    public static void exportarDespesas(Ligacao ligacao, String grupo, String email){
        ExportarDespesas exportar = new ExportarDespesas(grupo, email);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_EXPORTA_HISTORICO_CSV_FICHEIRO, exportar);
        ligacao.enviaMensagem(mensagem);
    }

}

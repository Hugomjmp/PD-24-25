package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.enumeracoes.Tipomensagemenum;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.*;

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
    public static void editarDespesa(Ligacao ligacao, String email,String grupo, double despesa, String quemPagou, String descricao, String data ){
        EditarDespesa editaDespesa = new EditarDespesa(email,grupo,despesa,quemPagou,descricao,data);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_EDITA_DESPESA, editaDespesa);
        ligacao.enviaMensagem(mensagem);
    }
    public static void historicoDespesa(Ligacao ligacao, String grupo){
        HistoricoDespesa historicoDespesa = new HistoricoDespesa(grupo);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_HISTORICO_DESPESAS, historicoDespesa);
        ligacao.enviaMensagem(mensagem);
    }
}

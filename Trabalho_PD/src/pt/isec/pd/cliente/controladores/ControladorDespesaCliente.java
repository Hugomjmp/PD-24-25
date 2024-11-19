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
    public static void editarDespesa(Ligacao ligacao, String email,String grupo, String despesa, String quemPagou, String descricao, String data,String ID_despesa ){
        EditarDespesa editaDespesa = new EditarDespesa(email,grupo,despesa,quemPagou,descricao,data, ID_despesa);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_EDITA_DESPESA, editaDespesa);
        ligacao.enviaMensagem(mensagem);
    }
    public static void historicoDespesa(Ligacao ligacao, String grupo){
        HistoricoDespesa historicoDespesa = new HistoricoDespesa(grupo);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_HISTORICO_DESPESAS, historicoDespesa);
        ligacao.enviaMensagem(mensagem);
    }
    public static void eliminarDespesa(Ligacao ligacao, String email,String grupoSelecionado, String ID_despesa){

        EliminaDespesa eliminaDespesa = new EliminaDespesa(email, grupoSelecionado,ID_despesa);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_ELIMINA_DESPESA, eliminaDespesa);
        ligacao.enviaMensagem(mensagem);
    }
    public static void verTotalDeve(Ligacao ligacao, String email, String grupoNome) {
        VerTotalDeve verTotalDeve = new VerTotalDeve(grupoNome, email);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_VISUALIZA_TOTAL_DEVE, verTotalDeve);
        ligacao.enviaMensagem(mensagem);
    }

    public static void verDevePorMembro(Ligacao ligacao, String email, String grupoNome) {
        VerDevePorMembro verDevePorMembro = new VerDevePorMembro(grupoNome, email);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_VISUALIZA_DEVE_POR_MEMBRO, verDevePorMembro);
        ligacao.enviaMensagem(mensagem);
    }
    /*
    public static void verTotalReceber(Ligacao ligacao, String email, String grupoNome){
        VerTotalReceber verTotalReceber = new VerTotalReceber(grupoNome, email);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_VISUALIZA_TOTAL_RECEBER, verTotalReceber);
        ligacao.enviaMensagem(mensagem);
    }
    public static void verReceberPorMembro(Ligacao ligacao, String email, String grupoNome){
        VerReceberPorMembro verReceberPorMembro = new VerReceberPorMembro(grupoNome, email);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_VISUALIZA_RECEBER_POR_MEMBRO, verReceberPorMembro);
        ligacao.enviaMensagem(mensagem);
    }
     */






}

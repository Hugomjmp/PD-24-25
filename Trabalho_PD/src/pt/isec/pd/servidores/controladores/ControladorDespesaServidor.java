package pt.isec.pd.servidores.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.enumeracoes.Tipomensagemenum;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.*;
import pt.isec.pd.db.Bd;

import java.io.Serializable;

public class ControladorDespesaServidor {

    public static Estados criaDespesa(CriaDespesa criaDespesa){
        Serializable despesa = null;
        try {
            despesa = Bd.criaDespesa(criaDespesa.getEmail(),criaDespesa.getGrupo() ,criaDespesa.getDespesa(), criaDespesa.getQuemPagou(), criaDespesa.getDescricao(), criaDespesa.getData() );


        } catch (Exception e) {
            System.err.println("Erro ao criar despesa para grupo: " + e.getMessage());
            //return Estados.ERRO_CRIAR_DESPESA;
        }
        return despesa == null ? Estados.ERRO_CRIAR_DESPESA : Estados.USER_CRIA_DESPESA_COM_SUCESSO.setDados(despesa);
    }
    public static Estados editaDespesa(EditarDespesa editarDespesa){
        Serializable edita = null;
        try {
            edita = Bd.editaDespesa(editarDespesa.getEmail(),editarDespesa.getGrupo(),editarDespesa.getDespesa(),editarDespesa.getQuemPagou(),editarDespesa.getDescricao(), editarDespesa.getData(), editarDespesa.getID_despesa());
        }catch (Exception e) {
            System.err.println("Erro ao editar uma despesa: " + e.getMessage());
            //return Estados.ERRO_CRIAR_DESPESA;
        }
        return edita == null ? Estados.ERRO_EDITAR_DESPESA : Estados.USER_EDITA_DESPESA_COM_SUCESSO.setDados(edita);
    }

    public static Estados verGastoTotal(VerGastoTotal verGastoTotal){
        Serializable gasto = null;
        try {
            gasto = Bd.verGasto(verGastoTotal.getEmail(),verGastoTotal.getGrupoNome());
        } catch (Exception e) {
            System.err.println("Erro ao ver o custo total para grupo: " + e.getMessage());
        }
        return gasto == null ? Estados.ERRO_CONSULTA_DESPESA_TOTAL : Estados.CONSULTA_DESPESA_TOTAL_COM_SUCESSO.setDados(gasto);

    }
    public static Estados verTotalDeve(VerTotalDeve verTotalDeve){
        String valorTotal = null;

        try {

            valorTotal = Bd.valorTotalDeve(verTotalDeve.getEmail(), verTotalDeve.getGrupoNome());
        } catch (Exception e) {
            System.err.println("Erro ao consultar o total que deve: " + e.getMessage());
        }


        return valorTotal == null ? Estados.ERRO_GRUPO_NAO_ENCONTRADO : Estados.CONSULTA_DESPESA_TOTAL_COM_SUCESSO.setDados(valorTotal);
    }
    public static Estados verTotalDeveDividido(VerDevePorMembro verDevePorMembro){
        String valorTotal = null;
        try {

            valorTotal = Bd.valorTotalDeveDividido(verDevePorMembro.getEmail(), verDevePorMembro.getGrupoNome());
        } catch (Exception e) {
            System.err.println("Erro ao consultar o total que deve: " + e.getMessage());
        }

        return valorTotal == null ? Estados.ERRO_GRUPO_NAO_ENCONTRADO : Estados.CONSULTA_DESPESA_TOTAL_COM_SUCESSO.setDados(valorTotal);
    }



    public static Estados exportCSV(ExportarDespesas exportarDespesas){
        Serializable exportar = null;
        try {
            exportar = Bd.export(exportarDespesas.getGrupo(),exportarDespesas.getNome());
        } catch (Exception e) {
            System.err.println("Erro ao exportar as despesas do grupo: " + e.getMessage());
        }
        return exportar == null ? Estados.ERRO_AO_EXPORTAR_CSV : Estados.USER_EXPORTA_COM_SUCESSO.setDados(exportar);
    }

    public static Estados historicoDespesa(HistoricoDespesa historicoDespesa){
        Serializable historico = null;
        try {
            historico = Bd.historio(historicoDespesa.getGrupo());
        }catch (Exception e) {
            System.err.println("Erro ao exportar as despesas do grupo: " + e.getMessage());
        }
        return historico == null ? Estados.ERRO_OBTER_HISTORICO : Estados.USER_OBTEM_HISTORICO_DESPESA_COM_SUCESSO.setDados(historico);

    }

    public static Estados eliminaDespesa(EliminaDespesa eliminaDespesa){
        Serializable elimina = null;
        try {
            elimina = Bd.eliminarDespesa(eliminaDespesa.getEmail(),eliminaDespesa.getGrupoNome(),eliminaDespesa.getID());
        } catch (Exception e) {
            System.err.println("Erro ao eliminar despesa do grupo: " + e.getMessage());
        }
        return elimina == null ? Estados.ERRO_ELIMINAR_DESPESA : Estados.DESPESA_ELIMINADA_COM_SUCESSO.setDados(elimina);
    }


}

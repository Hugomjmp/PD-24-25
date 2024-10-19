package pt.isec.pd.servidores.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.enumeracoes.Tipomensagemenum;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.CriaDespesa;
import pt.isec.pd.db.Bd;

public class ControladorDespesaServidor {

    public static Estados criaDespesa(CriaDespesa criaDespesa){
        try {
            Estados estado = Bd.criaDespesa(criaDespesa.getEmail(),criaDespesa.getGrupo() ,criaDespesa.getDespesa(), criaDespesa.getQuemPagou(), criaDespesa.getDescricao(), criaDespesa.getData() );
            return estado;
        } catch (Exception e) {
            System.err.println("Erro ao criar despesa para grupo: " + e.getMessage());
            return Estados.ERRO_CRIAR_DESPESA;
        }
    }





    /*
    *     public static Estados criaConvite(CriaConvite criaConvite){
        try {
            Estados estado = Bd.criaConvite(criaConvite.getEmail(), criaConvite.getNomeGrupo(), criaConvite.getEmailDestinatario());
            return estado;
        } catch (Exception e) {
            System.err.println("Erro ao criar convite para grupo: " + e.getMessage());
            return Estados.ERRO_CRIA_CONVITE;
        }

    }
    *
    *
    * */


}

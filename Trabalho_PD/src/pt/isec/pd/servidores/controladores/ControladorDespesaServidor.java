package pt.isec.pd.servidores.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.enumeracoes.Tipomensagemenum;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.CriaDespesa;
import pt.isec.pd.comum.modelos.mensagens.EditarGrupo;
import pt.isec.pd.db.Bd;

import java.io.Serializable;

public class ControladorDespesaServidor {

    public static Estados criaDespesa(CriaDespesa criaDespesa){
        Serializable despesa = null;
        try {
            despesa = Bd.criaDespesa(criaDespesa.getEmail(),criaDespesa.getGrupo() ,criaDespesa.getDespesa(), criaDespesa.getQuemPagou(), criaDespesa.getDescricao(), criaDespesa.getData() );
            System.out.println(despesa);

        } catch (Exception e) {
            System.err.println("Erro ao criar despesa para grupo: " + e.getMessage());
            //return Estados.ERRO_CRIAR_DESPESA;
        }
        return despesa == null ? Estados.ERRO_CRIAR_DESPESA : Estados.USER_CRIA_DESPESA_COM_SUCESSO.setDados(despesa);
    }

/*
    public static Estados editaGrupo(EditarGrupo editarGrupo){
        Serializable edita = null;
        try {
            edita = Bd.editarNomeGrupoDB(editarGrupo.getEmail(), editarGrupo.getGrupoNome(), editarGrupo.getGrupoNovoNome());
        } catch (Exception e) {
            System.err.println("Erro ao editar grupo: " + e.getMessage());
        }
        //System.out.println("ControladorGrupoServidor ->" + edita);
        return edita == null ? Estados.ERRO_GRUPO_NAO_ENCONTRADO : Estados.GRUPO_NOME_ALTERADO_COM_SUCESSO.setDados(edita);
    }
*/




}

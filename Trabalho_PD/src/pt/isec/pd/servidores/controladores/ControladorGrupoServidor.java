package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.enumeracoes.Estados;

import pt.isec.pd.comum.modelos.mensagens.CriaGrupo;
import pt.isec.pd.db.Bd;

import java.io.Serializable;


public class ControladorGrupoServidor {

    public static Estados grupoRegisto(CriaGrupo criaGrupo){
        Serializable grupoRegisto = null;
        try {
            grupoRegisto = Bd.setGrupoDB(criaGrupo.getNome(), criaGrupo.getCriadoPor());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return grupoRegisto == null ? Estados.ERRO_GRUPO : Estados.GRUPO_REGISTADO_COM_SUCESSO;


    }


}


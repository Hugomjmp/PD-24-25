package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.enumeracoes.Estados;

import pt.isec.pd.comum.modelos.mensagens.CriaGrupo;
import pt.isec.pd.comum.modelos.mensagens.EliminaGrupo;
import pt.isec.pd.comum.modelos.mensagens.ListarGrupo;
import pt.isec.pd.db.Bd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ControladorGrupoServidor {

    public static Estados grupoRegisto(CriaGrupo criaGrupo) {
        Serializable grupoRegisto = null;
        try {
            grupoRegisto = Bd.setGrupoDB(criaGrupo.getNome(), criaGrupo.getCriadoPor());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return grupoRegisto == null ? Estados.ERRO_GRUPO : Estados.GRUPO_REGISTADO_COM_SUCESSO;


    }

    public static Estados EliminaRegisto(EliminaGrupo eliminaGrupo) {
        try {
            Estados resultado = Bd.eliminarGrupoDB(eliminaGrupo.getNome(), eliminaGrupo.getEliminadoPor());
            return resultado;
        } catch (Exception e) {
            System.err.println("Erro ao eliminar grupo: " + e.getMessage());
            return Estados.ERRO_GRUPO; // Retorna erro em caso de exceção
        }
    }

    public static List<String> listarGrupos(ListarGrupo listarGrupo) {
        List<String> grupos = new ArrayList<>();
        try {
            grupos = Bd.listarGruposDB(listarGrupo.getSolicitadoPor());
        } catch (Exception e) {
            System.err.println("Erro ao listar grupos: " + e.getMessage());
        }
        return grupos;
    }



}


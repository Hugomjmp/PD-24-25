package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.enumeracoes.Estados;

import pt.isec.pd.comum.modelos.mensagens.*;
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
    public static Estados insereGrupo(InsereGrupo insereGrupo) {
        Serializable insertGroup = null;
        try {
            insertGroup = Bd.integraGrupo(insereGrupo.getNomeGrupo(),insereGrupo.getEmail());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return insertGroup == null ? Estados.ERRO_GRUPO : Estados.GRUPO_USER_INSERIDO_COM_SUCESSO;


    }

    public static Estados EliminaRegisto(EliminaGrupo eliminaGrupo) {
        try {
            Estados resultado = Bd.eliminarGrupoDB(eliminaGrupo.getNome(), eliminaGrupo.getEliminadoPor());
            return resultado;
        } catch (Exception e) {
            System.err.println("Erro ao eliminar grupo: " + e.getMessage());
            return Estados.ERRO_GRUPO;
        }
    }

    public static Estados sairGrupo(String grupoNome, String emailUsuario) {

        Estados estado = Bd.sairDoGrupoDB(grupoNome, emailUsuario);

        if (estado == Estados.USER_REMOVIDO_COM_SUCESSO) {
            System.out.println("Utilizador removido do grupo com sucesso.");
        } else {
            System.out.println("Erro ao remover o usuário do grupo.");
        }
        return estado;
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

    public static Estados criaConvite(CriaConvite criaConvite){
        try {
            Estados estado = Bd.criaConvite(criaConvite.getEmail(), criaConvite.getNomeGrupo(), criaConvite.getEmailDestinatario());
            return estado;
        } catch (Exception e) {
            System.err.println("Erro ao criar convite para grupo: " + e.getMessage());
            return Estados.ERRO_CRIA_CONVITE;
        }

    }



}


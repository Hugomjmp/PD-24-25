package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.enumeracoes.Tipomensagemenum;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.CriaGrupo;
import pt.isec.pd.comum.modelos.mensagens.EliminaGrupo;

public class ControladorGrupoCliente {
    public static void criaGrupo(Ligacao ligacao, String nomeGrupo , String nomeUser){
        CriaGrupo criaGrupo = new CriaGrupo(nomeGrupo, nomeUser);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_CRIA_GRUPO, criaGrupo);
        ligacao.enviaMensagem(mensagem);
        System.out.println(mensagem);
    }

    public static void eliminaGrupo(Ligacao ligacao, String nomeGrupo , String nomeUser){
        EliminaGrupo eliminaGrupo = new EliminaGrupo(nomeGrupo, nomeUser);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_ELIMINA_GRUPO, eliminaGrupo);
        ligacao.enviaMensagem(mensagem);
        System.out.println(mensagem);
    }
}

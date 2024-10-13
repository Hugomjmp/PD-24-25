package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.enumeracoes.Tipomensagemenum;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.CriaGrupo;

public class ControladorGrupoCliente {
    public static void criaGrupo(Ligacao ligacao, String nomeGrupo , String nomeUser){
        CriaGrupo criaGrupo = new CriaGrupo(nomeGrupo, nomeUser);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_CRIA_GRUPO, criaGrupo);
        ligacao.enviaMensagem(mensagem);
        System.out.println(mensagem);
    }
}

package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.enumeracoes.Tipomensagemenum;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.*;

public class ControladorGrupoCliente {
    public static void criaGrupo(Ligacao ligacao, String nomeGrupo , String nomeUser){
        CriaGrupo criaGrupo = new CriaGrupo(nomeGrupo, nomeUser);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_CRIA_GRUPO, criaGrupo);
        ligacao.enviaMensagem(mensagem);
        System.out.println(mensagem);
    }
    public static void insereGrupo(Ligacao ligacao, String nomeGrupo, String Email){
        InsereGrupo insereGrupo = new InsereGrupo(Email, nomeGrupo);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_INSERIDO_NO_GRUPO,insereGrupo);
        ligacao.enviaMensagem(mensagem);
        System.out.println(mensagem);
    }

    public static void eliminaGrupo(Ligacao ligacao, String nomeGrupo , String nomeUser){
        EliminaGrupo eliminaGrupo = new EliminaGrupo(nomeGrupo, nomeUser);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_ELIMINA_GRUPO, eliminaGrupo);
        ligacao.enviaMensagem(mensagem);
        System.out.println(mensagem);
    }

    public static void listarGrupos(Ligacao ligacao, String nomeUser) {

        ListarGrupo listarGrupo = new ListarGrupo(nomeUser);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_LISTA_GRUPOS, listarGrupo);
        ligacao.enviaMensagem(mensagem);
        System.out.println(mensagem);
    }

    public static void sairGrupo(Ligacao ligacao, String grupoNome, String emailUsuario) {
        SairGrupo sairGrupo = new SairGrupo(grupoNome, emailUsuario);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_SAI_GRUPO, sairGrupo);
        ligacao.enviaMensagem(mensagem);
        System.out.println(mensagem);
    }



}

package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.modelos.Login;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.enumeracoes.Tipomensagemenum;

public class ControladorAutenticacaoCliente {
    public static void registo(){}
    public static void login(Ligacao ligacao, String [] dados){
        Login login = new Login(dados[0],dados[1]);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.LOGIN,login);
        ligacao.enviaMensagem(ligacao.getSocket(),mensagem);
    }
    public static void edita(){}

}

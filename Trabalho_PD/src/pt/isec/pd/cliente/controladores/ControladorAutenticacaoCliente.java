package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.User;
import pt.isec.pd.comum.modelos.Login;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.Registo;
import pt.isec.pd.enumeracoes.Tipomensagemenum;

public class ControladorAutenticacaoCliente {
    public static void registo(Ligacao ligacao, User user){
        Registo registo = new Registo(user.getEmail(), user.getPassword(), user.getnTelefone(), user.getNome());
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_REGISTO, registo);
        ligacao.enviaMensagem(ligacao.getSocket(),mensagem);
    }
    public static void login(Ligacao ligacao, String [] dados){
        Login login = new Login(dados[0],dados[1]);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.LOGIN,login);
        ligacao.enviaMensagem(ligacao.getSocket(),mensagem);
    }
    public static void edita(){}

}

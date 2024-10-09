package pt.isec.pd.comum;


//esta classe está responsável para fazer a serialização para o objecto!!!!!


import pt.isec.pd.enumeracoes.Tipomensagemenum;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Mensagem implements Serializable {

    private Tipomensagemenum tipomensagemenum;  //tipo de mensagem que vai ser passada para o servidor  ou para cliente
    private Serializable conteudo;  //conteudo do objecto, em Array de bytes, a ser passado

    public  Mensagem(Tipomensagemenum tipo, Serializable conteudo){
        this.tipomensagemenum = tipo;
        this.conteudo = conteudo;
    }

    public Tipomensagemenum getTipoMensagem(){
        return tipomensagemenum;
    }
}

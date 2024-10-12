package pt.isec.pd.comum.modelos;

import java.io.Serializable;

public class RespostaServidorMensagem implements Serializable{
    private Serializable conteudo;  //conteudo do objecto, em Array de bytes, a ser passado

    public RespostaServidorMensagem(Serializable conteudo){
        this.conteudo = conteudo;
    }

    public Serializable getConteudo() {
        return conteudo;
    }

    @Override
    public String toString() {
        return "Mensagem{" +
                "conteudo=" + conteudo +
                '}';
    }
}

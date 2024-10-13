package pt.isec.pd.comum.enumeracoes;

import java.io.Serializable;

public enum Estados {

    USER_LOGADO_COM_SUCESSO("Utilizador logado com sucesso"),
    USER_REGISTADO_COM_SUCESSO("Utilizador registado com sucesso"),





    ERRO_AUTENTICACAO("O Utilizador não existe"),
    ERRO_REGISTO("O Utilizador já existe")
    ;

    String mensagem;
    Serializable dados;

    Estados(String s, Serializable dados) {
        this.mensagem = s;
        this.dados = dados;
    }
    Estados(String s){
        this(s,null);
    }

    public String getMensagem(){
        return mensagem;
    }
    public Serializable getDados(){
        return dados;
    }
    public Estados setDados(Serializable dados){
        this.dados = dados;
        return this;
    }
}

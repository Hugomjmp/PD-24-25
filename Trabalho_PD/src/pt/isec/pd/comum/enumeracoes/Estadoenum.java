package pt.isec.pd.comum.enumeracoes;

import java.io.Serializable;

public enum Estadoenum {

    UTLIZADOR_LOGADO("Utilizador entrou com sucesso");








    String mensagem;
    Serializable dados;

    Estadoenum(String mensagem,Serializable dados){
        this.dados = dados;
        this.mensagem = mensagem;
    }

    public Serializable getDados() {
        return dados;
    }

    public void setDados(Serializable dados) {
        this.dados = dados;
    }
}

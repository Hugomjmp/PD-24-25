package pt.isec.pd.comum.enumeracoes;

import java.io.Serializable;

public enum Estados {

    USER_LOGADO_COM_SUCESSO("Utilizador logado com sucesso"),
    USER_REGISTADO_COM_SUCESSO("Utilizador registado com sucesso"),
    USER_REMOVIDO_COM_SUCESSO("User removido do Grupo com sucesso"),



    GRUPO_REGISTADO_COM_SUCESSO("Grupo registado com sucesso"),
    GRUPO_USER_INSERIDO_COM_SUCESSO("Utilizador Inserido com Sucesso"),
    GRUPO_LISTADO_COM_SUCESSO("Grupo Listado com sucesso"),
    GRUPO_ELIMINADO_COM_SUCESSO("Grupo Eliminado com sucesso"),
    GRUPO_CONVITE_COM_SUCESSO("Convite creado com sucesso"),
    VER_CONVITES_COM_SUCESSO("Convites encontrados"),





    ERRO_AUTENTICACAO("O Utilizador não existe"),
    ERRO_REGISTO("O Utilizador já existe"),
    ERRO_GRUPO_NAO_ENCONTRADO("Grupo nao existe"),

    ERRO_GRUPO("Grupo já existe"),
    ERRO_CRIA_CONVITE("Convite já existente"),
    ERRO_VER_CONVITES("Sem convites")
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

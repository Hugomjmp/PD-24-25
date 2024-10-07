package pt.isec.pd.formularios;

public class UserCommandManager {

    String comando;


    public void setComando(String comando) {
        this.comando = comando;
    }





    @Override
    public String toString() {
        return "User Command Manager: recebi o comando: " + comando;
    }



}

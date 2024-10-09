package pt.isec.pd.comum.modelos;

import java.io.Serializable;
//O que eu tenho de enviar para o servidor...
public class Login implements Serializable {
    private String email;
    private String password;

    public Login(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword(){
        return password;
    }
}

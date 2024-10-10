package pt.isec.pd.comum.modelos;

public class EditaConta {
    private String email;
    private String password;
    private String nome;
    private Integer nTelefone;


    public EditaConta(String email, String password, Integer nTelefone){
        this.email=email;
        this.password=password;
        this.nTelefone=nTelefone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getnTelefone() {
        return nTelefone;
    }

    public String getNome() {
        return nome;
    }
}

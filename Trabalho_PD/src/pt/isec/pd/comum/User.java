package pt.isec.pd.comum;

public class User {
    private int nTelefone;
    private String email;
    private String password;
    private String nome;


    public User(String email, String password, String nome, int nTelefone){
        this.email=email;
        this.password=password;
        this.nome = nome;
        this.nTelefone = nTelefone;
    }

    public void setnTelefone(int nTelefone) {
        this.nTelefone = nTelefone;
    }
    public int getnTelefone() {
        return nTelefone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
}

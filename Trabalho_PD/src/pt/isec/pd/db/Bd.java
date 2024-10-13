package pt.isec.pd.db;

import pt.isec.pd.comum.modelos.User;

import java.sql.*;

public class Bd {


    private static Connection conn = null;
    private static boolean estaConectado = false;

    public static void ligaBD(String bd){
       try {
           String link = "jdbc:sqlite:";
           System.out.println("A ligar à base de dados...");
           bd = "src/pt/isec/pd/db/" + bd + ".db";
           conn = DriverManager.getConnection(link + bd);
           //System.out.println(conn);
           System.out.println("Ligação efectuada com sucesso!");
           setEstaConectado(true);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }

    public static boolean isEstaConectado() {
        return estaConectado;
    }

    public static void setEstaConectado(boolean estaConectado) {
        Bd.estaConectado = estaConectado;
    }
//TERMINAR ISTO DEPOIS
    private static void criaTabelas(String bd){
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("");
            stmt.executeUpdate("");
            stmt.executeUpdate("");
            stmt.executeUpdate("");
            stmt.executeUpdate("");
            stmt.executeUpdate("");
            stmt.executeUpdate("CREATE TABLE VERSAO (NUMERO_VERSAO INTEGER NOT NULL PRIMARY KEY);");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }








    public static void setUserDB(String nome, int nTelefone, String Email, String password){
        try{
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO USERS (NOME, N_TELEFONE, EMAIL, PASSWORD)" +
                    " VALUES ('" +
                    nome + "','" +
                    nTelefone + "','" +
                    Email + "','" +
                    password +
                    "')");
            versaoUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editaUserBD(Integer nTelefone, String Email, String password){
        try {
            Statement stmt = conn.createStatement();
            if(nTelefone == null){
                stmt.executeUpdate("UPDATE USERS" +
                        " SET PASSWORD = '" + password +
                        "' WHERE EMAIL=" + "'" + Email + "'");
                versaoUpdate();
            }
            if(password == null || password.isEmpty()){
                stmt.executeUpdate("UPDATE USERS" +
                        " SET N_TELEFONE = '" + nTelefone +
                        "' WHERE EMAIL=" + "'" + Email + "'");
                versaoUpdate();
            }
            if (password != null && nTelefone != null){
                stmt.executeUpdate("UPDATE USERS" +
                        " SET N_TELEFONE = '" + nTelefone + "', PASSWORD = '" + password +
                        "' WHERE EMAIL=" + "'" + Email + "'");
                versaoUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getUserDB(String email, String password){
        User user = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS WHERE EMAIL='" +
                    email + "' AND PASSWORD= '" + password+"'");
            if(rs.next()){
                String emailDB = rs.getString("EMAIL");
                String nomeDB = rs.getString("NOME");
                String passDB = rs.getString("PASSWORD");
                String telefoneDB = rs.getString("N_TELEFONE");
                System.out.println( "\nTABELA: \n" + nomeDB + "\n" + emailDB + "\n"+ passDB + "\n" + telefoneDB + "\n");
                user = new User();
                user.setNome(nomeDB);
                user.setEmail(emailDB);
                user.setnTelefone(Integer.parseInt(telefoneDB));
                user.setPassword(passDB);
                user.setEstado(true);

            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("O Utilizador não existe!");
            user.setEstado(false);
            return user;
        }

        return user;
    }
    public static void versaoUpdate(){
        int NUMERO_VERSAO = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NUMERO_VERSAO FROM VERSAO" +
                    " ORDER BY NUMERO_VERSAO DESC LIMIT 1");
            if(rs.next()){
                NUMERO_VERSAO = rs.getInt("NUMERO_VERSAO");

                System.out.println( "\nTABELA: \n" + NUMERO_VERSAO + "\n");
                NUMERO_VERSAO++; //incrementa a versão
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{

            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO VERSAO (NUMERO_VERSAO)" +
                    " VALUES ('" +
                    NUMERO_VERSAO +
                    "')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

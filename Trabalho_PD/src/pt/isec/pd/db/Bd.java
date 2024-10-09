package pt.isec.pd.db;

import pt.isec.pd.comum.User;

import java.sql.*;

public class Bd {


    private static Connection conn = null;


    public static void ligaBD(String bd){
       try {
           String link = "jdbc:sqlite:";
           System.out.println("A ligar à base de dados...");
           bd = "src/pt/isec/pd/db/" + bd + ".db";
           conn = DriverManager.getConnection(link + bd);
           System.out.println(conn);
           System.out.println("Ligação efectuada com sucesso!");
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getUserDB(String email, String password){
        User user = new User();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS WHERE EMAIL=" +
                    email + " AND PASSWORD= " + password);
            if(rs.next()){
                String emailDB = rs.getString("EMAIL");
                String nomeDB = rs.getString("NOME");
                String passDB = rs.getString("PASSWORD");
                String telefoneDB = rs.getString("N_TELEFONE");
                System.out.println( "\nTABELA: \n" + nomeDB + "\n" + emailDB + "\n"+ passDB + "\n" + telefoneDB + "\n");
                user.setNome(nomeDB);
                user.setEmail(emailDB);
                user.setnTelefone(Integer.parseInt(telefoneDB));
                user.setPassword(passDB);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) {

    }
}

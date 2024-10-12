package pt.isec.pd.db;

import pt.isec.pd.comum.User;

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

    public static void editaUserBD(Integer nTelefone, String Email, String password){
        try {
            Statement stmt = conn.createStatement();
            if(nTelefone == null){
                stmt.executeUpdate("UPDATE USERS" +
                        " SET PASSWORD = '" + password +
                        "' WHERE EMAIL=" + "'" + Email + "'");
            }
            if(password == null || password.isEmpty()){
                stmt.executeUpdate("UPDATE USERS" +
                        " SET N_TELEFONE = '" + nTelefone +
                        "' WHERE EMAIL=" + "'" + Email + "'");
            }
            if (password != null && nTelefone != null){
                stmt.executeUpdate("UPDATE USERS" +
                        " SET N_TELEFONE = '" + nTelefone + "', PASSWORD = '" + password +
                        "' WHERE EMAIL=" + "'" + Email + "'");
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
                //System.out.println( "\nTABELA: \n" + nomeDB + "\n" + emailDB + "\n"+ passDB + "\n" + telefoneDB + "\n");
                user = new User();
                user.setNome(nomeDB);
                user.setEmail(emailDB);
                user.setnTelefone(Integer.parseInt(telefoneDB));
                user.setPassword(passDB);

            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}

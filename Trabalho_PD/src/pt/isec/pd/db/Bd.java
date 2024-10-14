package pt.isec.pd.db;

import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.modelos.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Bd {


    private static Connection conn = null;
    private static boolean estaConectado = false;

    public static void ligaBD(String bd) {
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
    private static void criaTabelas(String bd) {
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

    public static Estados setGrupoDB(String grupoNome, String nomeUser) {

        try {
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO GRUPO (NOME, CRIADO_POR)" +
                    " VALUES ('" +
                    grupoNome + "','" +
                    nomeUser +
                    "')");
            versaoUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Estados.GRUPO_REGISTADO_COM_SUCESSO;
    }

    public static Estados integraGrupo(String grupoNome, String email) {

        //User user = null;
        String userID = null;
        String grupoID = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS WHERE EMAIL='" +
                    email + "'");
            if (rs.next()) {
                userID = rs.getString("ID");
            }

            try {
                //Statement stmt = conn.createStatement();
                ResultSet rs2 = stmt.executeQuery("SELECT * FROM GRUPO WHERE NOME='" +
                        grupoNome + "'");
                if (rs.next()) {
                    grupoID = rs2.getString("ID");
                }
            } catch (SQLException e) {
                System.out.println("O Utilizador não existe!");
                /*user.setEstado(false);*/
                /*return user;*/
            }

            try {
                //Statement stmt = conn.createStatement();

                stmt.executeUpdate("INSERT INTO INTEGRA (USER_ID, GROUP_ID)" +
                        " VALUES ('" +
                        userID + "','" +
                        grupoID +
                        "')");
                versaoUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("O Utilizador não existe!");
            /*user.setEstado(false);*/
            /*return user;*/
        }
        return Estados.GRUPO_NAO_ENCONTRADO;
    }

    public static Estados sairDoGrupoDB(String grupoNome, String emailUsuario) {
        String sqlDelete = "DELETE FROM INTEGRA WHERE GROUP_ID = (SELECT ID FROM GRUPO WHERE NOME = ?) " +
                "AND USER_ID = (SELECT ID FROM USERS WHERE EMAIL = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            pstmt.setString(1, grupoNome);
            pstmt.setString(2, emailUsuario);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                versaoUpdate();
                return Estados.USER_REMOVIDO_COM_SUCESSO;
            } else {
                return Estados.GRUPO_NAO_ENCONTRADO;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao remover usuário do grupo: " + e.getMessage());
            return Estados.ERRO_GRUPO;
        }
    }

    //Não esquecer que para eliminar o grupo, primeiro tem de se verificar se há dividas por salvar
    public static Estados eliminarGrupoDB(String grupoNome, String eliminadoPor) {
        String sql = "DELETE FROM GRUPO WHERE NOME = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, grupoNome);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                versaoUpdate();
                return Estados.GRUPO_ELIMINADO_COM_SUCESSO;
            } else {
                return Estados.GRUPO_NAO_ENCONTRADO;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao eliminar grupo: " + e.getMessage());
            return Estados.ERRO_GRUPO; // Retorna erro em caso de exceção
        }
    }

    public static List<String> listarGruposDB(String solicitadoPor) {
        List<String> grupos = new ArrayList<>();

        String sql = "SELECT NOME FROM GRUPO";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {


            while (rs.next()) {
                String nomeGrupo = rs.getString("NOME");
                grupos.add(nomeGrupo);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar grupos: " + e.getMessage());
            return null;
        }

        return grupos;
    }



    public static Estados setUserDB(String nome, int nTelefone, String Email, String password){
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
        User user = new User();
        user.setNome(nome);
        user.setEmail(Email);
        user.setnTelefone(nTelefone);
        user.setPassword(password);
        return Estados.USER_REGISTADO_COM_SUCESSO.setDados(user);
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

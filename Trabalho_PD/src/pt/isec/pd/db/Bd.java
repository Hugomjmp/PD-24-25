package pt.isec.pd.db;

import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.modelos.Convites;
import pt.isec.pd.comum.modelos.Grupos;
import pt.isec.pd.comum.modelos.User;

import java.sql.*;
import java.time.LocalDate;
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
            //conn.setAutoCommit(false);
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
        return Estados.ERRO_GRUPO_NAO_ENCONTRADO;
    }

    public static Estados criaConvite(String email, String groupNome, String emailDestinatario){
        String userID;
        String destinatarioID;
        String grupo;

        String querySelect = "SELECT * FROM CONVITES c " +
                            "WHERE c.GROUP_ID = (SELECT ID FROM GRUPO WHERE NOME = '" + groupNome + "' " +
                            "AND CRIADO_POR = '" + email + "') " +
                            "AND c.USER_ID = (SELECT ID FROM USERS WHERE EMAIL = '" + email + "') " +
                            "AND c.DESTINATARIO_ID = (SELECT ID FROM USERS WHERE EMAIL = '" + emailDestinatario + "')";

        //System.out.println(querySelect);
        String queryInsert = "INSERT INTO CONVITES (GROUP_ID, USER_ID, DESTINATARIO_ID, ESTADO) " +
                        "SELECT g.ID, u1.ID, u2.ID, 'pendente' " +
                        "FROM USERS u1 " +
                        "JOIN USERS u2 ON u2.EMAIL = '" + emailDestinatario + "' " +
                        "JOIN GRUPO g ON g.CRIADO_POR = u1.EMAIL AND g.NOME = '" + groupNome + "' " +
                        "WHERE u1.EMAIL = '" + email + "'";


        try{
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(querySelect);

            if (rs.next()){
                return Estados.ERRO_CRIA_CONVITE;
            }

            stmt.executeUpdate(queryInsert);
            versaoUpdate();
            return  Estados.GRUPO_CONVITE_COM_SUCESSO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Convites getConvites(String emailRecipiente){
        List<Convites> convitesLista = new ArrayList<>();
        Convites convite = null;
        String querySelect ="SELECT g.NOME AS NOMEGRUPO, u1.NOME AS NOMEREMETENTE, " +
                            "c.ESTADO AS ESTADO FROM CONVITES c " +
                            "JOIN GRUPO g ON c.GROUP_ID = g.ID " +
                            "JOIN USERS u1 ON c.USER_ID = u1.ID " +
                            "JOIN USERS u2 ON c.DESTINATARIO_ID = u2.ID " +
                            "WHERE u2.EMAIL = '" + emailRecipiente + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(querySelect);
            while(rs.next()){
                String nomeRemetenteDB = rs.getString("NOMEREMETENTE");
                String nomeGrupoDB = rs.getString("NOMEGRUPO");
                String estado = rs.getString("ESTADO");
                System.out.println("\n" + nomeRemetenteDB + "\n" + nomeGrupoDB + "\n" + estado + "\n");
                convite = new Convites();

                convite.setEstado(estado);
                convite.setGrupoNome(nomeGrupoDB);
                convite.setnomeRemetente(nomeRemetenteDB);
                convitesLista.add(convite);
                convite.setConvitesLista(convitesLista);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //return Estados.ERRO_VER_CONVITES;
        return convite;
    }

    public static Estados decideConvite(String grupoNome,String email, String decisao){


        String query = "UPDATE CONVITES " +
                        "SET ESTADO = '" + decisao + "' " +
                        "WHERE GROUP_ID = (SELECT ID FROM GRUPO WHERE NOME = '" + grupoNome + "') " +
                        "AND DESTINATARIO_ID = (SELECT ID FROM USERS WHERE EMAIL = '"+ email + "') " +
                        "AND ESTADO = 'pendente'";

        String queryIntegra = "INSERT INTO INTEGRA (USER_ID, GROUP_ID) " +
                        "SELECT (SELECT ID FROM USERS WHERE EMAIL = '" + email + "'), " +
                        "(SELECT ID FROM GRUPO WHERE NOME = '" + grupoNome + "')";
        try{
            Statement stmt = conn.createStatement();
            //System.out.println("cheguei aqui:)");
            if(decisao.equalsIgnoreCase("aceitar")) {
                stmt.executeUpdate(query);
                stmt.executeUpdate(queryIntegra);
                versaoUpdate();
                return Estados.GRUPO_ACEITE_CONVITE_COM_SUCESSO;
            }
            if (decisao.equalsIgnoreCase("recusar")){
                stmt.executeUpdate(query);
                versaoUpdate();
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Estados.ERRO_ACEITAR_CONVITE;
    }
    //HUGO confirmar isto depois
    //Não esquecer que ainda falta verificar se o utilizador em questoã es tem dívidas
    //Consultar enunciado!!!
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
                return Estados.ERRO_GRUPO_NAO_ENCONTRADO;
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
                return Estados.ERRO_GRUPO_NAO_ENCONTRADO;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao eliminar grupo: " + e.getMessage());
            return Estados.ERRO_GRUPO;
        }
    }

    public static Estados editarNomeGrupoDB(String email,String grupoNome, String grupoNovoNome){
        String query = "UPDATE GRUPO " +
                "SET NOME = '" + grupoNovoNome + "' " +
                "WHERE ID = (SELECT GROUP_ID FROM INTEGRA WHERE USER_ID = (" +
                "SELECT ID FROM USERS WHERE EMAIL = '" + email + "') " +
                "AND GROUP_ID = (SELECT ID FROM GRUPO WHERE NOME = '" + grupoNome + "'))";
        System.out.println(query);
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            versaoUpdate();
            stmt.close();
            //conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //return Estados.GRUPO_NOME_ALTERADO_COM_SUCESSO.setDados();
        return Estados.GRUPO_NOME_ALTERADO_COM_SUCESSO;
    }

    //fixed
    public static Grupos listarGruposDB(String solicitadoPor) {
        List<Grupos> grupoList = new ArrayList<>();
        Grupos grupos = null;
        String sql = "SELECT g.NOME " +
                    "FROM GRUPO g " +
                    "JOIN INTEGRA i ON g.ID = i.GROUP_ID " +
                    "JOIN USERS u ON i.USER_ID = u.ID " +
                    "WHERE u.EMAIL = '" + solicitadoPor + "'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nomeGrupo = rs.getString("NOME");
                grupos = new Grupos();
                grupos.setNomeGrupo(nomeGrupo);
                grupoList.add(grupos);
                grupos.setGruposList(grupoList);
                //grupoList.add(nomeGrupo);
            }
            //ACRESCENTEI ISTO <- Hugo
            stmt.close();
            rs.close();
            //----------------
        } catch (SQLException e) {
            System.err.println("Erro ao listar grupos: " + e.getMessage());
            //return null;
        }

        return grupos;
    }

    public static Estados inserirPagamento(String groupId, String pagaPor, String recebidoPor, double valor, LocalDate data) {
        try {
            // Pago Por, Verificacao
            String queryPagaPor = "SELECT 1 FROM INTEGRA WHERE USER_ID = ? AND GROUP_ID = ?";
            PreparedStatement pstmtPagaPor = conn.prepareStatement(queryPagaPor);
            pstmtPagaPor.setString(1, pagaPor);
            pstmtPagaPor.setString(2, groupId);
            ResultSet rsPagaPor = pstmtPagaPor.executeQuery();
            if (!rsPagaPor.next()) {
                return Estados.ERRO_USER_NAO_PERTENCE_GRUPO;
            }
            rsPagaPor.close();
            pstmtPagaPor.close();

            // Recebido Por, Verificacao
            String queryRecebidoPor = "SELECT 1 FROM INTEGRA WHERE USER_ID = ? AND GROUP_ID = ?";
            PreparedStatement pstmtRecebidoPor = conn.prepareStatement(queryRecebidoPor);
            pstmtRecebidoPor.setString(1, recebidoPor);
            pstmtRecebidoPor.setString(2, groupId);
            ResultSet rsRecebidoPor = pstmtRecebidoPor.executeQuery();
            if (!rsRecebidoPor.next()) {
                return Estados.ERRO_USER_NAO_PERTENCE_GRUPO;
            }
            rsRecebidoPor.close();
            pstmtRecebidoPor.close();


            String queryInsertPagamento = "INSERT INTO PAGAMENTOS (GROUP_ID, PAGA_POR, RECEBIDO_POR, VALOR, DATA_PAGAMENTO) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmtInsert = conn.prepareStatement(queryInsertPagamento);
            pstmtInsert.setString(1, groupId);
            pstmtInsert.setString(2, pagaPor);
            pstmtInsert.setString(3, recebidoPor);
            pstmtInsert.setDouble(4, valor);
            pstmtInsert.setDate(5, java.sql.Date.valueOf(data));
            pstmtInsert.executeUpdate();
            pstmtInsert.close();

            versaoUpdate();
            return Estados.USER_PAGAMENTO_INSERIDO_COM_SUCESSO;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir pagamento: " + e.getMessage());
            return Estados.ERRO_INSERIR_PAGAMENTO;
        }
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

    public static Estados editaUserBD(Integer nTelefone, String Email, String password){
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
        return Estados.USER_MODIFICADO_COM_SUCESSO;
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

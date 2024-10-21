package pt.isec.pd.db;

import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.modelos.Convites;
import pt.isec.pd.comum.modelos.Grupos;
import pt.isec.pd.comum.modelos.Pagamento;
import pt.isec.pd.comum.modelos.User;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bd {


    private static Connection conn = null;
    private static boolean estaConectado = false;
    private static final Object lock = new Object();
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
        return Estados.GRUPO_USER_INSERIDO_COM_SUCESSO;
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  Estados.GRUPO_CONVITE_COM_SUCESSO;
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
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(query);
                versaoUpdate();
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        //return Estados.GRUPO_NOME_ALTERADO_COM_SUCESSO.setDados();
        return Estados.GRUPO_NOME_ALTERADO_COM_SUCESSO;

    }

    /*public static List<Grupos> listarGruposDB(String solicitadoPor) {
        Grupos grupos = null;
        ArrayList<Grupos> grupoList = new ArrayList<>();
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
                //grupos.setGruposList(grupoList);
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
    return grupoList;

    }*/



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
        }
        return grupos;
    }

    public static Grupos getGrupoDB(String email,String grupoNome){
        Grupos grupo = null;

        String sql = "SELECT g.NOME " +
                "FROM GRUPO g " +
                "JOIN INTEGRA i ON g.ID = i.GROUP_ID " +
                "JOIN USERS u ON i.USER_ID = u.ID " +
                "WHERE u.EMAIL = '"+ email +"' AND g.NOME = '"+ grupoNome + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String nomeGrupoDB = rs.getString("NOME");
                grupo = new Grupos();
                grupo.setNomeGrupo(nomeGrupoDB);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao encontrar grupo: " + e.getMessage());
        }
        return grupo;
    }




    public static Estados inserirPagamento(String groupId, String pagaPor, String recebidoPor, double valor, String data) {
        String queryInsertPagamento = "INSERT INTO PAGAMENTO (GROUP_ID, PAGA_POR, RECEBIDO_POR, VALOR, DATA) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmtInsert = conn.prepareStatement(queryInsertPagamento);

            pstmtInsert.setString(1, groupId);
            pstmtInsert.setString(2, pagaPor);
            pstmtInsert.setString(3, recebidoPor);
            pstmtInsert.setDouble(4, valor);
            pstmtInsert.setString(5, data);

            pstmtInsert.executeUpdate();
            pstmtInsert.close();

            versaoUpdate();
            return Estados.USER_PAGAMENTO_INSERIDO_COM_SUCESSO;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir pagamento: " + e.getMessage());
            return Estados.ERRO_INSERIR_PAGAMENTO;
        }
    }

    public static List<Pagamento> listarPagamentosDB(String solicitadoPor) {
        List<Pagamento> pagamentoList = new ArrayList<>();
        String sql = "SELECT p.VALOR, p.DATA, p.PAGA_POR, p.RECEBIDO_POR " +
                "FROM PAGAMENTO p " +
                "JOIN GRUPO g ON p.GROUP_ID = g.ID " +
                "JOIN INTEGRA i ON g.ID = i.GROUP_ID " +
                "JOIN USERS u ON i.USER_ID = u.ID " +
                "WHERE u.EMAIL = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, solicitadoPor);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String groupId = rs.getString("GROUP_ID");
                    double valor = rs.getDouble("VALOR");
                    String data = rs.getString("DATA");
                    String pagaPor = rs.getString("PAGA_POR");
                    String recebidoPor = rs.getString("RECEBIDO_POR");

                    Pagamento pagamento = new Pagamento(valor, data, pagaPor, recebidoPor, groupId);
                    pagamentoList.add(pagamento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pagamentos: " + e.getMessage());
        }

        return pagamentoList;
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

                //System.out.println( "\nTABELA: \n" + NUMERO_VERSAO + "\n");
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

    public static Estados criaDespesa(String email,String grupo ,double despesa, String quemPagou, String descricao, String data ){
        String query = "INSERT INTO DESPESA (GROUP_ID, VALOR, DESCRICAO, DATA, PAGA_POR, REGISTADA_POR) " +
                "SELECT G.ID, " + despesa + ", '" + descricao + "', '" + data + "', U1.ID, U2.ID " +
                "FROM GRUPO G " +
                "JOIN INTEGRA I1 ON G.ID = I1.GROUP_ID " +
                "JOIN USERS U1 ON U1.ID = I1.USER_ID " +
                "JOIN USERS U2 ON U2.EMAIL = '" + email + "' " +
                "WHERE G.NOME = '" + grupo + "' " +
                "AND U1.EMAIL = '" + quemPagou + "' " +
                "AND EXISTS (SELECT 1 FROM INTEGRA i2 WHERE i2.GROUP_ID = g.ID AND i2.USER_ID IN (u1.ID, u2.ID))";

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            versaoUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Estados.USER_CRIA_DESPESA_COM_SUCESSO;

    }

    public static String verGasto(String email,String grupoNome){
        String valorTotal = null;
        String sql = "SELECT SUM(VALOR) AS DESPESATOTAL FROM DESPESA " +
                    "JOIN GRUPO G ON G.ID = DESPESA.GROUP_ID " +
                    "JOIN INTEGRA I ON g.ID = I.GROUP_ID " +
                    "JOIN USERS U ON U.ID = I.USER_ID " +
                    "WHERE G.NOME = '" + grupoNome + "'" + " AND U.EMAIL = '" + email + "'";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
                valorTotal = rs.getString("DESPESATOTAL");
                System.out.println(valorTotal);

        } catch (SQLException e) {
            System.err.println("Erro ao consultar os gastos totais: " + e.getMessage());
        }
        return valorTotal;
    }

    public static Estados export(String grupoNome,String nome) throws SQLException {

        String sqelementos = "SELECT U.NOME " +
                                "FROM USERS U " +
                                "JOIN INTEGRA I ON U.ID = I.USER_ID " +
                                "JOIN GRUPO G ON G.ID = I.GROUP_ID " +
                                "WHERE G.NOME = '" + grupoNome + "' " +
                                "AND EXISTS ( " +
                                "SELECT * " +
                                "FROM INTEGRA I2 " +
                                "JOIN USERS U2 ON I2.USER_ID = U2.ID " +
                                "WHERE I2.GROUP_ID = G.ID " +
                                "AND U2.EMAIL = '"+ nome +"')";
        String localFicheiro = "src/pt/isec/pd/Ficheiros/";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqelementos))
        {

            FileWriter fileWriter = new FileWriter(localFicheiro + grupoNome + " despesas.csv");
            fileWriter.write("Nome do grupo\n");
            fileWriter.write(grupoNome + "\n");
            fileWriter.write("\n");
            fileWriter.write("\n");
            fileWriter.write("Elementos\n");
            while(rs.next()){
                fileWriter.write(rs.getString("NOME") + ";");
            }
            fileWriter.write("\n");
            fileWriter.write("\n");
            fileWriter.write("Data;Responsável pelo registo da despesa;Valor;Pago por;A dividir com\n");


            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Estados.USER_EXPORTA_COM_SUCESSO;
    }

}

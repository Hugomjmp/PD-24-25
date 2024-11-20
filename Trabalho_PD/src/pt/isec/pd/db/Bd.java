package pt.isec.pd.db;

import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.modelos.*;
import pt.isec.pd.comum.modelos.mensagens.TotalDevidoInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Bd {


    private static Connection conn = null;
    private static boolean estaConectado = false;
    //private static final Object lock = new Object();

    public static boolean verificaExistenciaBD(String bd) {
        File ficheiroBD = new File(bd);
        return ficheiroBD.exists();
    }

    public static void ligaBD(String bd) {
        try {
            String link = "jdbc:sqlite:";
            System.out.println("A ligar à base de dados...");
            bd = "src/pt/isec/pd/db/" + bd + ".db";
            if (verificaExistenciaBD(bd)) {
                conn = DriverManager.getConnection(link + bd);
                //conn.setAutoCommit(false);
                //System.out.println("->" + conn);
                System.out.println("Ligação efectuada com sucesso!");
                setEstaConectado(true);
            } else {
                conn = DriverManager.getConnection(link + bd);
                criaTabelas();
                //conn.setAutoCommit(false);
                //System.out.println("->" + conn);
                System.out.println("Ligação efectuada com sucesso!");
                setEstaConectado(true);
                versaoUpdate();
            }
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
    private static void criaTabelas(/*String bd*/) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE CONVITES (\n" +
                    "    ID              INTEGER  PRIMARY KEY AUTOINCREMENT\n" +
                    "                             UNIQUE,\n" +
                    "    GROUP_ID        INTEGER  NOT NULL,\n" +
                    "    USER_ID         INTEGER,\n" +
                    "    DESTINATARIO_ID INTEGER,\n" +
                    "    ESTADO          TEXT,\n" +
                    "    DATA_ENVIO      DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, '+1 hour') ),\n" +
                    "    FOREIGN KEY (\n" +
                    "        USER_ID\n" +
                    "    )\n" +
                    "    REFERENCES USERS (ID),\n" +
                    "    FOREIGN KEY (\n" +
                    "        GROUP_ID\n" +
                    "    )\n" +
                    "    REFERENCES GRUPO (ID),\n" +
                    "    FOREIGN KEY (\n" +
                    "        DESTINATARIO_ID\n" +
                    "    )\n" +
                    "    REFERENCES USERS (ID) \n" +
                    ");");
            stmt.executeUpdate("CREATE TABLE DESPESA (\n" +
                    "    ID            INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                    "                          UNIQUE,\n" +
                    "    GROUP_ID      INTEGER,\n" +
                    "    VALOR,\n" +
                    "    DESCRICAO     TEXT,\n" +
                    "    DATA,\n" +
                    "    PAGA_POR      INTEGER REFERENCES USERS (ID),\n" +
                    "    REGISTADA_POR INTEGER REFERENCES USERS (ID),\n" +
                    "    FOREIGN KEY (\n" +
                    "        GROUP_ID\n" +
                    "    )\n" +
                    "    REFERENCES GRUPO (ID) ON DELETE CASCADE\n" +
                    ");");
            stmt.executeUpdate("CREATE TABLE DIVIDE_DESPESA (\n" +
                    "    DESPESA_ID  REFERENCES DESPESA (ID),\n" +
                    "    USER_ID     REFERENCES USERS (ID),\n" +
                    "    PRIMARY KEY (\n" +
                    "        DESPESA_ID,\n" +
                    "        USER_ID\n" +
                    "    )\n" +
                    ");");
            stmt.executeUpdate("CREATE TABLE GRUPO (\n" +
                    "    ID         INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                    "                       NOT NULL\n" +
                    "                       UNIQUE,\n" +
                    "    NOME       TEXT    NOT NULL,\n" +
                    "    CRIADO_POR         REFERENCES USERS (EMAIL) \n" +
                    ");");
            stmt.executeUpdate("CREATE TABLE INTEGRA (\n" +
                    "    USER_ID   INTEGER REFERENCES USERS (ID),\n" +
                    "    GROUP_ID  INTEGER REFERENCES GRUPO (ID),\n" +
                    "    PRIMARY KEY (\n" +
                    "        USER_ID,\n" +
                    "        GROUP_ID\n" +
                    "    )\n" +
                    ");");
            stmt.executeUpdate("CREATE TABLE PAGAMENTO (\n" +
                    "   ID           INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "   GROUP_ID     INTEGER REFERENCES GRUPO (ID),\n" +
                    "   DATA,\n" +
                    "   VALOR,\n" +
                    "   PAGA_POR             REFERENCES USERS (ID),\n" +
                    "   RECEBIDO_POR         REFERENCES USERS (ID)\n" +
                    ");");
            stmt.executeUpdate("CREATE TABLE USERS (\n" +
                    "    ID         INTEGER NOT NULL\n" +
                    "                       UNIQUE\n" +
                    "                       PRIMARY KEY AUTOINCREMENT,\n" +
                    "    NOME       TEXT    NOT NULL,\n" +
                    "    N_TELEFONE NUMERIC NOT NULL,\n" +
                    "    EMAIL      TEXT    NOT NULL\n" +
                    "                       UNIQUE,\n" +
                    "    PASSWORD   TEXT    NOT NULL\n" +
                    ");");
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

    public static Estados criaConvite(String email, String groupNome, String emailDestinatario) {

        String querySelect = "SELECT * FROM CONVITES c " +
                "WHERE c.GROUP_ID = (SELECT ID FROM GRUPO WHERE NOME = '" + groupNome + "' " +
                "AND CRIADO_POR = '" + email + "') " +
                "AND c.USER_ID = (SELECT ID FROM USERS WHERE EMAIL = '" + email + "') " +
                "AND c.DESTINATARIO_ID = (SELECT ID FROM USERS WHERE EMAIL = '" + emailDestinatario + "')";

        System.out.println(querySelect);
        String queryInsert = "INSERT INTO CONVITES (GROUP_ID, USER_ID, DESTINATARIO_ID, ESTADO) " +
                "SELECT g.ID, u1.ID, u2.ID, 'pendente' " +
                "FROM USERS u1 " +
                "JOIN USERS u2 ON u2.EMAIL = '" + emailDestinatario + "' " +
                "JOIN INTEGRA i ON i.USER_ID = u1.ID " +
                "JOIN GRUPO g ON g.ID = i.GROUP_ID " +
                "WHERE u1.EMAIL = '" + email + "' " +
                "AND g.NOME = '" + groupNome + "'";
        System.out.println(queryInsert);

        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(querySelect);

            if (rs.next()) {
                return Estados.ERRO_CRIA_CONVITE;
            }

            stmt.executeUpdate(queryInsert);
            versaoUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Estados.GRUPO_CONVITE_COM_SUCESSO;
    }

    public static Convites getConvites(String emailRecipiente) {
        List<Convites> convitesLista = new ArrayList<>();
        Convites convite = null;
        String querySelect = "SELECT g.NOME AS NOMEGRUPO, u1.NOME AS NOMEREMETENTE, " +
                "c.ESTADO AS ESTADO FROM CONVITES c " +
                "JOIN GRUPO g ON c.GROUP_ID = g.ID " +
                "JOIN USERS u1 ON c.USER_ID = u1.ID " +
                "JOIN USERS u2 ON c.DESTINATARIO_ID = u2.ID " +
                "WHERE u2.EMAIL = '" + emailRecipiente + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(querySelect);
            while (rs.next()) {
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

    public static Estados decideConvite(String grupoNome, String email, String decisao) {


        String query = "UPDATE CONVITES " +
                "SET ESTADO = '" + decisao + "' " +
                "WHERE GROUP_ID = (SELECT ID FROM GRUPO WHERE NOME = '" + grupoNome + "') " +
                "AND DESTINATARIO_ID = (SELECT ID FROM USERS WHERE EMAIL = '" + email + "') " +
                "AND ESTADO = 'pendente'";

        String queryIntegra = "INSERT INTO INTEGRA (USER_ID, GROUP_ID) " +
                "SELECT (SELECT ID FROM USERS WHERE EMAIL = '" + email + "'), " +
                "(SELECT ID FROM GRUPO WHERE NOME = '" + grupoNome + "')";
        try {
            Statement stmt = conn.createStatement();
            //System.out.println("cheguei aqui:)");
            if (decisao.equalsIgnoreCase("aceitar")) {
                stmt.executeUpdate(query);
                stmt.executeUpdate(queryIntegra);
                versaoUpdate();
                return Estados.GRUPO_ACEITE_CONVITE_COM_SUCESSO;
            }
            if (decisao.equalsIgnoreCase("recusar")) {
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

    public static Estados editarNomeGrupoDB(String email, String grupoNome, String grupoNovoNome) {
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

    public static Grupos getGrupoDB(String email, String grupoNome) {
        Grupos grupo = null;

        String sql = "SELECT g.NOME " +
                "FROM GRUPO g " +
                "JOIN INTEGRA i ON g.ID = i.GROUP_ID " +
                "JOIN USERS u ON i.USER_ID = u.ID " +
                "WHERE u.EMAIL = '" + email + "' AND g.NOME = '" + grupoNome + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String nomeGrupoDB = rs.getString("NOME");
                grupo = new Grupos();
                grupo.setNomeGrupo(nomeGrupoDB);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao encontrar grupo: " + e.getMessage());
        }
        return grupo;
    }

    public static Estados inserirPagamento(String grupoNome, String pagaPor, String recebidoPor, double valor, String data) {
        String query = "INSERT INTO PAGAMENTO (GROUP_ID, DATA, VALOR, PAGA_POR, RECEBIDO_POR)" +
                " SELECT" +
                " g.ID,'" + data + "', " + valor + ", " +
                "(SELECT ID FROM USERS WHERE EMAIL = '" + pagaPor + "'), " +
                "(SELECT ID FROM USERS WHERE EMAIL = '" + recebidoPor + "') " +
                "FROM GRUPO g " +
                "WHERE g.ID = (SELECT GROUP_ID " +
                "FROM INTEGRA " +
                "WHERE USER_ID = (SELECT ID FROM USERS WHERE EMAIL = '" + recebidoPor + "') " +
                "AND GROUP_ID = (SELECT ID FROM GRUPO WHERE NOME = '" + grupoNome + "'));";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();

            versaoUpdate();
            return Estados.USER_PAGAMENTO_INSERIDO_COM_SUCESSO;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir pagamento: " + e.getMessage());
            return Estados.ERRO_INSERIR_PAGAMENTO;
        }
    }

    public static Estados eliminarPagamento(String grupoNome/*, String data*/, double valor, String pagaPor, String recebidoPor) {
        String sql = "DELETE FROM PAGAMENTO " +
                "WHERE ID = ( " +
                "SELECT p.ID " +
                "FROM PAGAMENTO p " +
                "INNER JOIN INTEGRA i ON p.GROUP_ID = i.GROUP_ID " +
                "WHERE i.USER_ID = (SELECT ID FROM USERS WHERE EMAIL = '" + pagaPor + "') " +
                "AND p.GROUP_ID = (SELECT ID FROM GRUPO WHERE NOME = '" + grupoNome + "') " +
                "AND p.VALOR = " + valor +
                " AND p.PAGA_POR = (SELECT ID FROM USERS WHERE EMAIL = '" + pagaPor + "') " +
                "AND p.RECEBIDO_POR = (SELECT ID FROM USERS WHERE EMAIL = '" + recebidoPor + "') " +
                "LIMIT 1 " +
                ");";
        //System.out.println(sql);
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Erro ao eliminar pagamento: " + e.getMessage());
            return Estados.ERRO_ELIMINAR_PAGAMENTO;
        }
        return Estados.PAGAMENTO_ELIMINADO_COM_SUCESSO;
    }

    /*TODO
     *  ACABAR ISTO...*/
    public static Pagamento listarPagamentosDB(String nomeGrupo) {
        List<Pagamento> pagamentoList = new ArrayList<>();
        Pagamento pagamento = null;
        String sql = "SELECT p.ID, " +
                "p.DATA, " +
                "p.VALOR, " +
                "u1.EMAIL AS PAGA_POR, " +
                "u2.EMAIL AS RECEBIDO_POR " +
                "FROM PAGAMENTO p " +
                "INNER JOIN GRUPO g ON p.GROUP_ID = g.ID " +
                "INNER JOIN USERS u1 ON p.PAGA_POR = u1.ID " +
                "INNER JOIN USERS u2 ON p.RECEBIDO_POR = u2.ID " +
                "WHERE g.NOME = '" + nomeGrupo + "';";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String DATA = rs.getString("DATA");
                String VALOR = rs.getString("VALOR");
                String PAGA_POR = rs.getString("PAGA_POR");
                String RECEBIDO_POR = rs.getString("RECEBIDO_POR");
                pagamento = new Pagamento();
                pagamento.setPagaPor(PAGA_POR);
                pagamento.setData(DATA);
                pagamento.setValor(Double.parseDouble(VALOR));
                pagamento.setRecebidoPor(RECEBIDO_POR);
                pagamentoList.add(pagamento);
                pagamento.setPagamentos(pagamentoList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(pagamento);

        return pagamento;
    }

    public static Estados setUserDB(String nome, int nTelefone, String Email, String password) {
        try {
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

    public static Estados editaUserBD(Integer nTelefone, String Email, String password) {
        try {
            Statement stmt = conn.createStatement();
            if (nTelefone == null) {
                stmt.executeUpdate("UPDATE USERS" +
                        " SET PASSWORD = '" + password +
                        "' WHERE EMAIL=" + "'" + Email + "'");
                versaoUpdate();
            }
            if (password == null || password.isEmpty()) {
                stmt.executeUpdate("UPDATE USERS" +
                        " SET N_TELEFONE = '" + nTelefone +
                        "' WHERE EMAIL=" + "'" + Email + "'");
                versaoUpdate();
            }
            if (password != null && nTelefone != null) {
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

    public static User getUserDB(String email, String password) {
        User user = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS WHERE EMAIL='" +
                    email + "' AND PASSWORD= '" + password + "'");
            if (rs.next()) {
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

    public static void versaoUpdate() {
        int NUMERO_VERSAO = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NUMERO_VERSAO FROM VERSAO" +
                    " ORDER BY NUMERO_VERSAO DESC LIMIT 1");
            if (rs.next()) {
                NUMERO_VERSAO = rs.getInt("NUMERO_VERSAO");

                //System.out.println( "\nTABELA: \n" + NUMERO_VERSAO + "\n");
                NUMERO_VERSAO++; //incrementa a versão
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {

            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO VERSAO (NUMERO_VERSAO)" +
                    " VALUES ('" +
                    NUMERO_VERSAO +
                    "')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int obtemVersao() {
        int versao = 0;
        String query = "SELECT MAX(NUMERO_VERSAO) as NUMERO_VERSAO FROM VERSAO";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            versao = rs.getInt("NUMERO_VERSAO");

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("O Utilizador não existe!");
        }

        return versao;
    }

    public static Estados criaDespesa(String email, String grupo, double despesa, String quemPagou, String descricao, String data) {
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

    public static void divideDespesa(String grupo) {
        int nElementos;
        String queryNElementos = "SELECT COUNT(USER_ID) AS NUMERO_UTILIZADORES\n" +
                "FROM INTEGRA\n" +
                "WHERE GROUP_ID = +'" + grupo + "'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(queryNElementos);
            while (rs.next()) {
                nElementos = rs.getInt("NUMERO_UTILIZADORES");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Despesa historio(String grupo) {
        List<Despesa> despesaList = new ArrayList<>();
        Despesa despesa = null;
        String GRUPODB = null;
        String queryGrupoID = "SELECT ID " +
                "FROM GRUPO " +
                "WHERE NOME = '" + grupo + "'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rsID = stmt.executeQuery(queryGrupoID);
            if (rsID.next())
                GRUPODB = rsID.getString("ID");

            String query = "SELECT D.ID, D.DATA, D.VALOR, D.DESCRICAO, U1.NOME AS REGISTADA_POR, U.NOME AS PAGO_POR " +
                    "FROM DESPESA D " +
                    "JOIN USERS U ON D.PAGA_POR = U.ID " +
                    "JOIN USERS U1 ON D.REGISTADA_POR = U1.ID " +
                    "WHERE D.GROUP_ID = " + GRUPODB + " " +
                    "ORDER BY D.DATA ASC";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String ID = rs.getString("ID");
                String DATA = rs.getString("DATA");
                String VALOR = rs.getString("VALOR");
                String DESCRICAO = rs.getString("DESCRICAO");
                String REGISTADA_POR = rs.getString("REGISTADA_POR");
                String PAGO_POR = rs.getString("PAGO_POR");
                despesa = new Despesa();
                despesa.setIdDespesa(ID);
                despesa.setData(DATA);
                despesa.setDespesa(Double.parseDouble(VALOR));
                despesa.setDescricao(DESCRICAO);
                despesa.setEmail(REGISTADA_POR);
                despesa.setQuemPagou(PAGO_POR);
                despesaList.add(despesa);
                despesa.setDespesaList(despesaList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return despesa;
    }


    public static Estados eliminarDespesa(String email, String nomeGrupo, String ID) {
        String query = "DELETE FROM DESPESA\n" +
                "WHERE ID = (SELECT D.ID\n" +
                "    FROM DESPESA D\n" +
                "    INNER JOIN INTEGRA I ON D.GROUP_ID = I.GROUP_ID\n" +
                "    WHERE I.USER_ID = (SELECT ID FROM USERS WHERE EMAIL = '" + email + "')\n" +
                "    AND D.GROUP_ID = (SELECT ID FROM GRUPO WHERE NOME = '" + nomeGrupo + "')\n" +
                "    AND D.ID = " + ID + "\n" +
                "    LIMIT 1);";
        System.out.println(query);
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Erro ao eliminar pagamento: " + e.getMessage());
            return Estados.ERRO_ELIMINAR_DESPESA;
        }
        return Estados.DESPESA_ELIMINADA_COM_SUCESSO;
    }


    /*TODO
     *  ACABAR O EDITA DESPESA:...*/
    public static Estados editaDespesa(String email, String grupo, String despesa, String quemPagou, String descricao, String data, String ID_despesa) {

        try {
            Statement stmt = conn.createStatement();

            if (!data.isEmpty() && !email.isEmpty() && despesa.isEmpty() && quemPagou.isEmpty() && descricao.isEmpty()) {
                String sqldata = "UPDATE DESPESA " +
                        "SET DATA = '" + data + "' " +
                        "WHERE ID = " + ID_despesa;
                stmt.executeUpdate(sqldata);

                versaoUpdate();
            } else if (data.isEmpty() && !email.isEmpty() && despesa.isEmpty() && quemPagou.isEmpty() && descricao.isEmpty()) { /*TODO*/
                String sqlEmail = "UPDATE DESPESA " +
                        "SET REGISTADA_POR = '" + email + "' " +
                        "WHERE ID = " + ID_despesa;

                //stmt.executeUpdate();
                versaoUpdate();
            } else if (data.isEmpty() && !email.isEmpty() && !despesa.isEmpty() && quemPagou.isEmpty() && descricao.isEmpty()) {
                String sqlDespesa = "UPDATE DESPESA " +
                        "SET VALOR = '" + despesa + "' " +
                        "WHERE ID = " + ID_despesa;

                stmt.executeUpdate(sqlDespesa);
                versaoUpdate();
            } else if (data.isEmpty() && !email.isEmpty() && despesa.isEmpty() && !quemPagou.isEmpty() && descricao.isEmpty()) { /*TODO*/
                String sqlQuemPagou = "UPDATE DESPESA " +
                        "SET PAGA_POR = '" + quemPagou + "' " +
                        "WHERE ID = " + ID_despesa;

                //stmt.executeUpdate();
                versaoUpdate();
            } else if (data.isEmpty() && !email.isEmpty() && despesa.isEmpty() && quemPagou.isEmpty() && !descricao.isEmpty()) {
                String sqlDescricao = "UPDATE DESPESA " +
                        "SET DESCRICAO = '" + descricao + "' " +
                        "WHERE ID = " + ID_despesa;
                System.out.print(sqlDescricao);

                stmt.executeUpdate(sqlDescricao);
                versaoUpdate();
            } else {
                return Estados.ERRO_EDITAR_DESPESA;
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Estados.USER_EDITA_DESPESA_COM_SUCESSO;
    }


    public static String verGasto(String email, String grupoNome) {
        String valorTotal = "0";
        String sql = "SELECT SUM(VALOR) AS DESPESATOTAL FROM DESPESA " +
                "JOIN GRUPO G ON G.ID = DESPESA.GROUP_ID " +
                "JOIN INTEGRA I ON g.ID = I.GROUP_ID " +
                "JOIN USERS U ON U.ID = I.USER_ID " +
                "WHERE G.NOME = '" + grupoNome + "' AND U.EMAIL = '" + email + "'";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                valorTotal = rs.getString("DESPESATOTAL");
                if (valorTotal == null) {
                    valorTotal = "0";
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar os gastos totais: " + e.getMessage());
            valorTotal = "0";
        }

        return valorTotal;
    }

    public static String valorTotalDeve(String email, String grupoNome) {
        String valorTotal = "0";
        String sql = "SELECT SUM(VALOR) AS TOTAL_DEVE FROM DESPESA " +
                "JOIN GRUPO G ON G.ID = DESPESA.GROUP_ID " +
                "JOIN INTEGRA I ON g.ID = I.GROUP_ID " +
                "JOIN USERS U ON U.ID = I.USER_ID " +
                "WHERE G.NOME = '" + grupoNome + "' AND U.EMAIL = '" + email + "'";

        try (Statement stmt = conn.createStatement()) {

            try (ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    valorTotal = rs.getString("TOTAL_DEVE");
                    if (valorTotal == null) {
                        valorTotal = "0";
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar o valor total que deve: " + e.getMessage());
        }
        return valorTotal;
    }

    public static TotalDevidoInfo valorTotalDeveDividido(String email, String grupoNome) {
        String sql = "SELECT " +
                "COALESCE(SUM(D.VALOR) / NULLIF(COUNT(DISTINCT I.USER_ID), 0), 0) AS TOTAL_DEVIDO, " +
                "COUNT(DISTINCT I.USER_ID) AS NUMERO_PESSOAS, " +
                "COALESCE(SUM(CASE WHEN I.USER_ID = ? THEN D.VALOR ELSE 0 END), 0) AS VALOR_PAGO_POR_MEMBRO " + // Aqui, verificamos o valor pago pelo membro
                "FROM DESPESA D " +
                "JOIN GRUPO G ON G.ID = D.GROUP_ID " +
                "JOIN INTEGRA I ON G.ID = I.GROUP_ID " +
                "WHERE G.NOME = ?";

        TotalDevidoInfo resultado = new TotalDevidoInfo();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, grupoNome);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    resultado.setTotalDevido(rs.getDouble("TOTAL_DEVIDO"));
                    resultado.setNumeroPessoas(rs.getInt("NUMERO_PESSOAS"));
                    resultado.setValorPagoPorMembro(rs.getDouble("VALOR_PAGO_POR_MEMBRO"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar o valor total dividido: " + e.getMessage());
        }

        return resultado;
    }


    public static Estados export(String grupoNome, String nome) throws SQLException {

        String sqElementos = "SELECT U.NOME " +
                "FROM USERS U " +
                "JOIN INTEGRA I ON U.ID = I.USER_ID " +
                "JOIN GRUPO G ON G.ID = I.GROUP_ID " +
                "WHERE G.NOME = '" + grupoNome + "' " +
                "AND EXISTS ( " +
                "SELECT * " +
                "FROM INTEGRA I2 " +
                "JOIN USERS U2 ON I2.USER_ID = U2.ID " +
                "WHERE I2.GROUP_ID = G.ID " +
                "AND U2.EMAIL = '" + nome + "')";


        String sqDespesas = "SELECT D.DATA, U_REGISTADA.NOME AS REGISTADA_POR, D.VALOR, U_PAGA.NOME AS PAGA_POR " +
                "FROM DESPESA D " +
                "JOIN GRUPO G ON D.GROUP_ID = G.ID " +
                "JOIN USERS U_REGISTADA ON D.REGISTADA_POR = U_REGISTADA.ID " +
                "JOIN USERS U_PAGA ON D.PAGA_POR = U_PAGA.ID " +
                "WHERE G.NOME = '" + grupoNome + "'";

        String localFicheiro = "src/pt/isec/pd/Ficheiros/";
        try (Statement stmt = conn.createStatement();
             ResultSet rsElementos = stmt.executeQuery(sqElementos)) {

            List<String> membros = new ArrayList<>();
            while (rsElementos.next()) {
                membros.add(rsElementos.getString("NOME"));
            }


            FileWriter fileWriter = new FileWriter(localFicheiro + grupoNome + " despesas.csv");


            fileWriter.write("\"Nome do grupo\"\n");
            fileWriter.write("\"" + grupoNome + "\"\n\n");


            fileWriter.write("\"Elementos\"\n");
            for (String membro : membros) {
                fileWriter.write("\"" + membro + "\";");
            }
            fileWriter.write("\n\n");


            fileWriter.write("\"Data\";\"Responsável pelo registo da despesa\";\"Valor\";\"Pago por\";\"A dividir com\"\n");


            try (ResultSet rsDespesas = stmt.executeQuery(sqDespesas)) {
                while (rsDespesas.next()) {
                    String data = rsDespesas.getString("DATA");
                    String registadaPor = rsDespesas.getString("REGISTADA_POR");
                    double valor = rsDespesas.getDouble("VALOR");
                    String pagaPor = rsDespesas.getString("PAGA_POR");


                    List<String> dividirCom = new ArrayList<>(membros);
                    dividirCom.remove(pagaPor);

                    
                    fileWriter.write("\"" + data + "\";");
                    fileWriter.write("\"" + registadaPor + "\";");
                    fileWriter.write("\"" + valor + "\";");
                    fileWriter.write("\"" + pagaPor + "\";");
                    fileWriter.write("\"" + String.join("; ", dividirCom) + "\"\n");
                }
            }

            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar o arquivo: " + e.getMessage(), e);
        }

        return Estados.USER_EXPORTA_COM_SUCESSO;
    }


}

package pt.isec.pd.cliente.controladores;


import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.cliente.modelos.Dados;

import pt.isec.pd.comum.modelos.*;

import java.util.List;

public class ControladorPrincipal {

    static String nome;
    static String password;
    static String email;
    static String nTelefoneString;
    static int nTelefone;


    static Ligacao ligacao;

    static Dados dados;
    private User userAutenticado;

     public ControladorPrincipal(Ligacao ligacao){
        this.ligacao = ligacao;

        this.dados = new Dados();
    }

    public List<User> getUsers(){
        return dados.getUtilizadores();
    }

    /*
    public User getUserAutenticado(){
        return this.userAutenticado;
    }*/

    public boolean login(String email, String password) {
        String[] dadosLogin = {email, password};
        ControladorAutenticacaoCliente.login(ligacao, dadosLogin);
        recebeMensagem();

        User usuarioLogado = dados.getUtilizadorLogado();
        if (usuarioLogado != null) {
            this.userAutenticado = usuarioLogado;
            return true;
        }
        return false;
    }


    public void logout() {
        userAutenticado = null;
        dados.setUtilizadorLogado(null);
    }

    public boolean isUserAuthenticated() {
        return userAutenticado != null;
    }


    public int registo(String nome, String email, String password, String nTelefone) {



        if (!email.contains("@") && !email.contains(".com")) { //obriga o Cliente a introduzir o email no formato completo
            return 0;
        }

        try {//obriga o user a meter um número válido
            if (nTelefone.length() != 9) { //obriga o user a meter 9 dígitos
                Integer.parseInt(nTelefone); // para gerar exceção
                System.out.println("O número de telefone deve conter 9 dígitos.");
                return 1;
            }
        } catch (NumberFormatException e) {

            System.out.println("O número do telefone deve conter apenas números.");
            return 2;
        }
        String[] dadosRegisto = {email, nome, password, nTelefone};
        ControladorAutenticacaoCliente.registo(ligacao, dadosRegisto);
        recebeMensagem();
        return 3;
    }

    public void editaDados(String numeroTelefone, String password){
        Integer Telf = null;
        if (!numeroTelefone.isEmpty())
            try {
                Telf = Integer.parseInt(numeroTelefone);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }

        ControladorAutenticacaoCliente.edita(ligacao, Telf, dados.getUtilizadorLogado().getEmail(), password);
        recebeMensagem();
    }

    public void criarGrupo(String nomeGrupo){
        ControladorGrupoCliente.criaGrupo(ligacao, nomeGrupo, dados.getUtilizadorLogado().getEmail());
        recebeMensagem();
        ControladorGrupoCliente.insereGrupo(ligacao, nomeGrupo, dados.getUtilizadorLogado().getEmail());
        recebeMensagem();
    }


    public boolean editarGrupo(String nomeGrupo, String grupoSelecionado){
        if (grupoSelecionado == null) {
            return false;
        }
        ControladorGrupoCliente.editaGrupo(ligacao, dados.getUtilizadorLogado().getEmail(), grupoSelecionado, nomeGrupo);
        recebeMensagem();
        return true;
    }

    public String selecionarGrupo(String nomeGrupo){
        ControladorGrupoCliente.selecionaGrupo(ligacao, dados.getUtilizadorLogado().getEmail(), nomeGrupo);
        recebeMensagem();
        return dados.getGrupoSelecionado().getNomeGrupo();
    }


    public Grupos listarGrupos(){

        ControladorGrupoCliente.listarGrupos(ligacao, dados.getUtilizadorLogado().getEmail());
        return (Grupos) recebeMensagem();
    }
    public boolean criarConvite(String emailDestinatario, String grupoSelecionado){
        if (grupoSelecionado == null) {
            return false;
        }
        ControladorGrupoCliente.criaConvite(ligacao, dados.getUtilizadorLogado().getEmail(), emailDestinatario, grupoSelecionado);
        recebeMensagem();
        return true;
    }

    //FALTA VERIFICAÇÃO DE DIVIDAS!!!!!!!!!!!
    public String eliminarGrupo(String nomeGrupo, String grupoSelecionado){
        if (nomeGrupo.equalsIgnoreCase(grupoSelecionado)) {
            ControladorGrupoCliente.eliminaGrupo(ligacao, nomeGrupo, dados.getUtilizadorLogado().getEmail());
            recebeMensagem();
            return null;
        }else{
            ControladorGrupoCliente.eliminaGrupo(ligacao, nomeGrupo, dados.getUtilizadorLogado().getEmail());
            recebeMensagem();
            return grupoSelecionado;
        }

    }
    //VERIFICAR SE ISTO FUNCIONA...
    public String sairDoGrupo(String nomeGrupo, String grupoSelecionado){
        if (nomeGrupo.equalsIgnoreCase(grupoSelecionado)) {
            ControladorGrupoCliente.sairGrupo(ligacao, nomeGrupo, dados.getUtilizadorLogado().getEmail());
            recebeMensagem();
            return null;
        }else{
            ControladorGrupoCliente.sairGrupo(ligacao, nomeGrupo, dados.getUtilizadorLogado().getEmail());
            recebeMensagem();
            return grupoSelecionado;
        }

    }

    public void insereDespesa(String grupoSelecionado, double despesa, String quemPagou, String descricao, String data){
        ControladorDespesaCliente.inserirDespesa(ligacao, dados.getUtilizadorLogado().getEmail(), grupoSelecionado, despesa, quemPagou, descricao, data);
        recebeMensagem();
    }

    /*TODO
    *  terminar isto...*/
    public void editaDespesa(String grupoSelecionado,String ID_despesa ,String despesa, String quemPagou, String descricao, String data){
        ControladorDespesaCliente.editarDespesa(ligacao,dados.getUtilizadorLogado().getEmail(),grupoSelecionado,despesa,quemPagou,descricao,data, ID_despesa);
        recebeMensagem();
    }
    public void eliminaDespesa(String grupoSelecionado, String ID_despesa){

        ControladorDespesaCliente.eliminarDespesa(ligacao,dados.getUtilizadorLogado().getEmail(),grupoSelecionado,ID_despesa);
        recebeMensagem();
    }
    public Despesa mostrarDespesas(String grupoSelecionado){
        ControladorDespesaCliente.historicoDespesa(ligacao,grupoSelecionado);
        return (Despesa) recebeMensagem();

    }


    public void exportaFicheiro(String grupoSelecionado){
        ControladorDespesaCliente.exportarDespesas(ligacao, grupoSelecionado, dados.getUtilizadorLogado().getEmail());
        recebeMensagem();
    }

    public String consultaGastoTotal(String grupoSelecionado){

        ControladorDespesaCliente.verDespesaTotal(ligacao, dados.getUtilizadorLogado().getEmail(), grupoSelecionado);
        return (String) recebeMensagem();
    }
    public String consultarSaldos(String grupoSelecionado) {

        ControladorDespesaCliente.verDespesaTotal(ligacao, dados.getUtilizadorLogado().getEmail(), grupoSelecionado);
       return (String) recebeMensagem();
    }
    public String consultarDeve(String grupoSelecionado) {
        ControladorDespesaCliente.verTotalDeve(ligacao, dados.getUtilizadorLogado().getEmail(), grupoSelecionado);
        return (String) recebeMensagem();
    }



    public void efectuaPagamento(String grupoSelecionado/*,String pagaPor*/,String recebidoPor,double valor, String data ){

        ControladorPagamentoCliente.inserirPagamento(ligacao, dados.getUtilizadorLogado().getEmail(), grupoSelecionado/*, pagaPor*/, recebidoPor, valor, data);
        recebeMensagem();
    }
    public void eliminaPagamento(String grupoSelecionado/*, String data*/, double valor/*, String pagaPor*/, String recebidoPor) {
        ControladorPagamentoCliente.eliminaPagamento(ligacao, dados.getUtilizadorLogado().getEmail(), grupoSelecionado/*, data*/, valor/*, pagaPor*/, recebidoPor);
        recebeMensagem();
    }
    public Pagamento listarPagamento(String grupoSelecionado) {
        ControladorPagamentoCliente.listarPagamento(ligacao, dados.getUtilizadorLogado().getEmail(), grupoSelecionado);
        return (Pagamento) recebeMensagem();

    }
    public Convites mostrarConvites(){
        ControladorGrupoCliente.verConvites(ligacao, dados.getUtilizadorLogado().getEmail());
        return (Convites) recebeMensagem();
    }
    /*TODO
    *  falta meter aqui proteção para o caso de o user introduzir mal a palavra*/
    public void respondeConvite(String grupoNome, String decisao){
        ControladorGrupoCliente.decisaoConvites(ligacao, decisao, grupoNome, dados.getUtilizadorLogado().getEmail());
        recebeMensagem();
    }


   public static Object recebeMensagem(){

        RespostaServidorMensagem resposta = ligacao.recebeMensagem();
        System.out.println("Recebe Mensagem -> "+resposta);
        try {
            switch (resposta.getEstado()){
                case USER_LOGADO_COM_SUCESSO -> {
                    dados.setUtilizadorLogado((User) resposta.getConteudo());
                    return resposta.getEstado();
                }

               case USER_REGISTADO_COM_SUCESSO -> {


                }
                case USER_GRUPO_SELECIONADO_COM_SUCESSO -> {
                    dados.setGrupoSelecionado((Grupos) resposta.getConteudo());
                    return resposta.getEstado();
                }
/*              case ERRO_CRIA_CONVITE -> {


                }*/
                case CONSULTA_DESPESA_TOTAL_COM_SUCESSO -> {
                    String total;
                    /*System.out.println(" HELLO DESPESA TOTAL " + resposta.getEstado());
                    System.out.println("O gasto total no grupo foi de: " +  resposta.getConteudo() + "€");
                    return resposta.getEstado();*/
                    total = (String) resposta.getConteudo();
                    return total;
                }
                case USER_CRIA_DESPESA_COM_SUCESSO -> {
                    System.out.println(" HELLO DESPESA " + resposta.getEstado());
                    return resposta.getEstado();
                }

                case GRUPO_USER_INSERIDO_COM_SUCESSO ->{
                    System.out.println(" HELLO INTEGRA "+resposta.getEstado());
                    return  resposta.getEstado();
                }
                case GRUPO_REGISTADO_COM_SUCESSO ->{
                    System.out.println(" HELLO REGISTO"+resposta.getEstado());
                    return  resposta.getEstado();
                }
                case GRUPO_CONVITE_COM_SUCESSO -> {
                    System.out.println(" HELLO "+resposta.getEstado());
                    return  resposta.getEstado();
                }
                case GRUPO_NOME_ALTERADO_COM_SUCESSO -> {
                    System.out.println(" HELLO "+resposta.getEstado());
                    return resposta.getEstado();
                }
                case VER_CONVITES_COM_SUCESSO -> {
                    Convites convites = (Convites) resposta.getConteudo();
                    return convites;
                }

                case GRUPO_LISTADO_COM_SUCESSO -> { //tratar deste warnig depois
                    Grupos grupos = (Grupos) resposta.getConteudo();
                    //converteLista((Grupos) resposta.getConteudo());
                    //Vista.tabelaGrupos(grupos);
                    //System.out.println(" HELLO "+resposta.getEstado());
                    return grupos;
                    //return resposta.getEstado();

                }
                case PAGAMENTO_LISTADO_COM_SUCESSO -> {
                    Pagamento pagamento = (Pagamento) resposta.getConteudo();
                    return pagamento;
                }
                case USER_OBTEM_HISTORICO_DESPESA_COM_SUCESSO ->{

                    Despesa despesa = (Despesa) resposta.getConteudo();
                    return despesa;
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resposta.getEstado();

    }





   /* private static String[] registo(*//*User user*//*){
        boolean nTefValido = false;
        String [] dados = {nome, String.valueOf(nTelefone), email,password};
        Scanner scanner = new Scanner(System.in);
        System.out.print("Login E-mail: ");
        email = scanner.nextLine();
        while (!email.contains("@") && !email.contains(".com")){ //obriga o Cliente a introduzir o email no formato completo
            System.out.println("Tem de ter o seguinte formato: exemplo@exemplo.com");
            System.out.print("Login E-mail: ");
            email = scanner.nextLine();

        }
        dados[0] = email;
        //user.setEmail(email);
        System.out.print("NOME: ");
        dados[1] = scanner.nextLine();
        //user.setNome(scanner.nextLine());
        System.out.print("Password: ");
        password = scanner.nextLine();
        dados[2] = password;
        //user.setPassword(password);
        while (!nTefValido) { //obriga o user a meter um número válido
            try {
                System.out.print("Número de Telefone: ");
                nTelefoneString = scanner.nextLine();
                if (nTelefoneString.length() != 9){ //obriga o user a meter 9 dígitos
                    Integer.parseInt(nTelefoneString);
                    System.out.println("O número de telefone deve conter 9 dígitos.");
                    continue;
                }
                nTelefone = Integer.parseInt(nTelefoneString);
                nTefValido = true;
            } catch (NumberFormatException e) {
                System.out.println("O número do telefone deve conter apenas números.");
            }
        }
        dados[3] = String.valueOf(nTelefone);
        //user.setnTelefone(nTelefone);
        return dados;
    }*/

/*    private static String[] login(){
        Scanner scanner = new Scanner(System.in);
        String [] dados = {email,password};
        System.out.print("Login E-mail: ");
        dados[0] = scanner.nextLine();
        System.out.print("Password: ");
        dados[1] = scanner.nextLine();

        //System.out.println(dados[0]+dados[1]);
        return dados;
    }*/


    public static void main() throws InterruptedException {
/*

        String escolhaMenuPrincipal;
        int escolhaCliente;



        User user = new User();

        *//*RecebeMensagensThread recebeMensagensThread = new RecebeMensagensThread(ligacao, dados);
        Thread recebeThread = new Thread(recebeMensagensThread);
        recebeThread.start();*//*
        //System.out.println(escolhaMenuPrincipal);
        while (true) {

            if (dados.getUtilizadorLogado() == null) {
                escolhaMenuPrincipal = Vista.menuPrincipal();

                switch (escolhaMenuPrincipal) {
                    case "registo": {
                        enviaMensagem("registo");
                        //recebeMensagem();
                        break;
                    }
                    case "login": {

                        enviaMensagem("login");
                        //recebeMensagem();
                        //System.out.println(recebeMensagem());

                        System.out.println("DADOS--> " + dados.getUtilizadorLogado());


                        break;
                    }
                }


            }else{
                escolhaCliente = Vista.menuCliente();
                //System.out.println("RECEBI:" + escolhaCliente);
                switch (escolhaCliente){
                    case 1: //CRIAR GRUPO
                    {
                        System.out.println("CRIAR GRUPO");
                        enviaMensagem("cria_grupo");

                        break;
                    }
                    case 2: //Selecionar um GRUPO
                    {
                        System.out.println("SELECIONAR O GRUPO");
                        enviaMensagem("seleciona_grupo");

                        break;
                    }
                    case 3: //CONVIDAR PARA GRUPO
                    {
                        System.out.println("CONVIDAR PARA GRUPO");
                        enviaMensagem("cria_convite");

                        break;
                    }
                    case 4: //Editar nome Grupo
                    {
                        System.out.println("EDITAR GRUPO");
                        enviaMensagem("edita_grupo");

                        break;
                    }
                    case 5: //LISTAR CONVITES
                    {
                        System.out.println("LISTAR CONVITES");
                        enviaMensagem("ver_convites");

                        break;
                    }
                    case 6: //decisao de aceitar/recusar convites
                    {
                        System.out.println("DECISAO CONVITES");
                        enviaMensagem("decisao-convites");

                        break;
                    }
                    case 7: // LISTAR GRUPOS
                    {
                        System.out.println("LISTAR GRUPOS");
                        enviaMensagem("lista_grupo");

                        //recebeMensagem();
                        break;
                    }
                    case 8: //ELIMINAR GRUPOS
                    {
                        System.out.println("ELIMINAR GRUPOS");
                        enviaMensagem("elimina_grupo");

                        //recebeMensagem();
                        break;
                    }
                    case 9: //SAIR DE UM GRUPO
                    {
                        System.out.println("SAIR DE UM GRUPO");
                        enviaMensagem("sair_grupo");

                        //recebeMensagem();
                        break;
                    }
                    case 10: //VER GASTO TOTAL DE UM GRUPO
                    {
                        System.out.println("VER GASTO TOTAL DE UM GRUPO");
                        enviaMensagem("gasto_total");


                        break;
                    }
                    case 11: //VER HISTÓRICO DE UM GRUPO
                    {
                        System.out.println("VER HISTÓRICO DE UM GRUPO");

                        break;
                    }
                    case 12: //INSERIR UMA DESPESA
                    {
                        System.out.println("INSERIR UMA DESPESA");
                        enviaMensagem("insere_despesa");

                        break;
                    }
                    case 13: //GUARDAR DESPESAS PARA UM FICHEIRO
                    {
                        System.out.println("GUARDAR DESPESAS PARA UM FICHEIRO");
                        enviaMensagem("exportar_despesas");

                        break;
                    }
                    case 14: //EDITAR DESPESAS
                    {
                        System.out.println("EDITAR DESPESAS");
                        //Thread.sleep(500);
                        break;
                    }
                    case 15: //ELIMINAR DESPESAS
                    {
                        System.out.println("ELIMINAR DESPESAS");

                        break;
                    }
                    case 16: //EFECTUAR PAGAMENTO
                    {
                        System.out.println("EFECTUAR PAGAMENTO");
                        enviaMensagem("efetuar_pagamento");
                        //recebeMensagem();

                        break;
                    }
                    case 17: //LISTAR TODOS OS PAGAMENTOS
                    {
                        System.out.println("LISTAR TODOS OS PAGAMENTOS");

                        break;
                    }
                    case 18: //ELIMINAR PAGAMENTO EFETUADO
                    {
                        System.out.println("ELIMINAR PAGAMENTO EFETUADO");

                        break;
                    }
                    case 19: //CONSULTAR SALDO
                    {
                        System.out.println("CONSULTAR SALDO");

                        break;
                    }
                    case 20: //ALTERA DADOS USER
                    {
                        System.out.println("ALTERA DADOS DO USER");
                        enviaMensagem("altera-dados");

                        break;
                    }
                    case 21: //LOGOUT
                    {
                        System.out.println("LOGOUT");

                        break;
                    }

                }
            }

        }*/
    }

}

package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.cliente.modelos.Dados;
import pt.isec.pd.cliente.vistas.Vista;
import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.modelos.RespostaServidorMensagem;
import pt.isec.pd.comum.modelos.User;

import java.util.List;
import java.util.Scanner;

public class ControladorPrincipal {

    static String nome;
    static String password;
    static String email;
    static String nTelefoneString;
    static int nTelefone;
    static Ligacao ligacao;
    static Dados dados;





    public ControladorPrincipal(Ligacao ligacao){
        this.ligacao = ligacao;
        this.dados = new Dados();
    }

    public List<User> getUsers(){
        return dados.getUtilizadores();
    }

    public static void enviaMensagem(String escolha){
        String [] dadosLogin;
        String [] dadosRegisto;
        String grupo;
        Scanner scanner = new Scanner(System.in);
            if(dados.getUtilizadorLogado() == null){
                switch (escolha){
                    case "login": { //login
                        System.out.println("Envia: Login");
                        dadosLogin = ControladorPrincipal.login();
                        ControladorAutenticacaoCliente.login(ligacao, dadosLogin);
                        break;
                    }
                    case "registo":{ //Registo
                        System.out.println("Envia: Registo");
                        dadosRegisto = ControladorPrincipal.registo();
                        ControladorAutenticacaoCliente.registo(ligacao,dadosRegisto);
                        break;
                       // ControladorAutenticacaoCliente.registo(ligacao, user);
                       // ControladorPrincipal.registo(user);
                    }
                }
            } else {
                switch (escolha) {
                    case "cria_grupo":
                        System.out.println("Envia Cria Grupo:");
                        System.out.print("Nome do grupo:");
                        grupo = scanner.nextLine();
                        ControladorGrupoCliente.criaGrupo(ligacao, grupo, dados.getUtilizadorLogado().getEmail());
                        ControladorGrupoCliente.insereGrupo(ligacao,grupo,dados.getUtilizadorLogado().getEmail());
                        break;

                    case "elimina_grupo":
                        System.out.println("Envia Elimina Grupo:");
                        System.out.print("Nome do grupo a eliminar:");
                        grupo = scanner.nextLine();
                        ControladorGrupoCliente.eliminaGrupo(ligacao, grupo, dados.getUtilizadorLogado().getEmail());
                        break;


                    case "lista_grupo":
                        System.out.println("Envia Listar Grupos:");
                        ControladorGrupoCliente.listarGrupos(ligacao, dados.getUtilizadorLogado().getEmail());
                        break;

                    case "sair_grupo":
                        System.out.println("Envia Sair do Grupo:");
                        System.out.print("Nome do grupo do qual deseja sair: ");
                        grupo = scanner.nextLine();
                        ControladorGrupoCliente.sairGrupo(ligacao, grupo, dados.getUtilizadorLogado().getEmail());
                        break;

                    default:
                        System.out.println("Opção inválida. Por favor, tente novamente.");
                        break;
                }
            }


    }

    public static Estados recebeMensagem(){

        RespostaServidorMensagem resposta = ligacao.recebeMensagem();

        try {
            switch (resposta.getEstado()){
                case USER_LOGADO_COM_SUCESSO -> {
                    dados.setUtilizadorLogado((User) resposta.getConteudo());
                    return resposta.getEstado();
                }
                case USER_REGISTADO_COM_SUCESSO -> {


                }
                case GRUPO_LISTADO_COM_SUCESSO -> { //tratar deste warnig depois
                    List<String> grupos = (List<String>) resposta.getConteudo();

                    if (grupos != null && !grupos.isEmpty()) {
                        System.out.println("Grupos disponíveis:");
                        for (String grupo : grupos) {
                            System.out.println("- " + grupo);
                        }
                    } else {
                        System.out.println("Nenhum grupo disponível.");
                    }
                    return resposta.getEstado();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resposta.getEstado();
    }





    private static String[] registo(/*User user*/){
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
    }

    private static String[] login(){
        Scanner scanner = new Scanner(System.in);
        String [] dados = {email,password};
        System.out.print("Login E-mail: ");
        dados[0] = scanner.nextLine();
        System.out.print("Password: ");
        dados[1] = scanner.nextLine();

        //System.out.println(dados[0]+dados[1]);
        return dados;
    }


    public static void main() {


        String escolhaMenuPrincipal;
        int escolhaCliente;



        User user = new User();

        //System.out.println(escolhaMenuPrincipal);
        while (true) {

            if (dados.getUtilizadorLogado() == null) {
                escolhaMenuPrincipal = Vista.menuPrincipal();
                switch (escolhaMenuPrincipal) {
                    case "registo": {
                        enviaMensagem("registo");
                        recebeMensagem();
                        break;
                    }
                    case "login": {
                        enviaMensagem("login");
                        recebeMensagem();
                        //System.out.println(recebeMensagem());
                        break;
                    }
                }

            }else{
                escolhaCliente = Vista.menuCliente();
                System.out.println("RECEBI:" + escolhaCliente);
                switch (escolhaCliente){
                    case 1: //CRIAR GRUPO
                    {
                        System.out.println("CRIAR GRUPO");
                        enviaMensagem("cria_grupo");
                        break;
                    }
                    case 2: //CONVIDAR PARA GRUPO
                    {
                        System.out.println("CONVIDAR PARA GRUPO");
                        break;
                    }
                    case 3: //LISTAR CONVITES
                    {
                        System.out.println("LISTAR CONVITES");
                        break;
                    }
                    case 4: // LISTAR GRUPOS
                    {
                        System.out.println("LISTAR GRUPOS");
                        enviaMensagem("lista_grupo");
                        recebeMensagem();
                        break;
                    }
                    case 5: //ELIMINAR GRUPOS
                    {
                        System.out.println("ELIMINAR GRUPOS");
                        enviaMensagem("elimina_grupo");
                        recebeMensagem();
                        break;
                    }
                    case 6: //SAIR DE UM GRUPO
                    {
                        System.out.println("SAIR DE UM GRUPO");
                        enviaMensagem("sair_grupo");
                        recebeMensagem();
                        break;
                    }
                    case 7: //VER GASTO TOTAL DE UM GRUPO
                    {
                        System.out.println("VER GASTO TOTAL DE UM GRUPO");
                        break;
                    }
                    case 8: //VER HISTÓRICO DE UM GRUPO
                    {
                        System.out.println("VER HISTÓRICO DE UM GRUPO");
                        break;
                    }
                    case 9: //GUARDAR DESPESAS PARA UM FICHEIRO
                    {
                        System.out.println("GUARDAR DESPESAS PARA UM FICHEIRO");
                        break;
                    }
                    case 10: //EDITAR DESPESAS
                    {
                        System.out.println("EDITAR DESPESAS");
                        break;
                    }
                    case 11: //ELIMINAR DESPESAS
                    {
                        System.out.println("ELIMINAR DESPESAS");
                        break;
                    }
                    case 12: //EFECTUAR PAGAMENTO
                    {
                        System.out.println("EFECTUAR PAGAMENTO");
                        break;
                    }
                    case 13: //LISTAR TODOS OS PAGAMENTOS
                    {
                        System.out.println("LISTAR TODOS OS PAGAMENTOS");
                        break;
                    }
                    case 14: //ELIMINAR PAGAMENTO EFETUADO
                    {
                        System.out.println("ELIMINAR PAGAMENTO EFETUADO");
                        break;
                    }
                    case 15: //CONSULTAR SALDO
                    {
                        System.out.println("CONSULTAR SALDO");
                        break;
                    }
                    case 16: //LOGOUT
                    {
                        System.out.println("LOGOUT");
                        break;
                    }
                }
            }

        }
    }
}

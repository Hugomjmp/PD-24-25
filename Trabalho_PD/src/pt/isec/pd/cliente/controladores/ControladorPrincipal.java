package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.User;
import pt.isec.pd.comum.modelos.Login;
import pt.isec.pd.db.Bd;
import pt.isec.pd.servidores.controladores.ControladorAutenticacao;

import java.util.Scanner;

public class ControladorPrincipal {
    static Ligacao ligacao;
    static String nome;
    static String password;
    static String email;
    static String nTelefoneString;
    static int nTelefone;

    public ControladorPrincipal(Ligacao ligacao){
        this.ligacao = ligacao;
    }

    private static void registo(User user){
        boolean nTefValido = false;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Login E-mail: ");
        email = scanner.nextLine();
        while (!email.contains("@") && !email.contains(".com")){ //obriga o Cliente a introduzir o email no formato completo
            System.out.println("Tem de ter o seguinte formato: exemplo@exemplo.com");
            System.out.print("Login E-mail: ");
            email = scanner.nextLine();
        }
        user.setEmail(email);
        System.out.print("NOME: ");
        user.setNome(scanner.nextLine());
        System.out.print("Password: ");
        password = scanner.nextLine();
        user.setPassword(password);
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
        user.setnTelefone(nTelefone);

    }

    private static String[] login(){
        Scanner scanner = new Scanner(System.in);
        String [] dados = {email,password};

        System.out.print("Login E-mail: ");
        email = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();
        return dados;
    }
    private static String menu(){
        Scanner entrada = new Scanner(System.in);
        while (true) {
            System.out.println("MENU - Cliente");
            System.out.println("1 - login");
            System.out.println("2 - Registo");
            System.out.print("#> ");
            int opcao = entrada.nextInt();
            if (opcao == 1) {
                return "login";
            } else if (opcao == 2) {
                return "registo";
            } else {
                System.out.println("Escolha Invalida");

            }
        }
    }

    public static void main(/*String[] args*/) {

        String escolhaMenuPrincipal;
        String [] dados;
        escolhaMenuPrincipal = ControladorPrincipal.menu();
        User user = new User();
        System.out.println(escolhaMenuPrincipal);


        switch (escolhaMenuPrincipal) {
            case "registo": {
                ControladorPrincipal.registo(user);
                System.out.println(user);
                ligacao.enviaMensagem(ligacao.getSocket(), user);

            }
            case "login": {
                System.out.println("AQUI");
                dados = ControladorPrincipal.login();

                Bd.ligaBD("Base_de_dados");

                //Login login = new Login("a21220079@isec.pt", "1234");
                //ControladorAutenticacao.login(login);

            }

        }
        while (true);
    }
}

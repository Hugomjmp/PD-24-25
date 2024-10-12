package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.cliente.vistas.Vista;
import pt.isec.pd.comum.modelos.User;

import java.io.IOException;
import java.io.ObjectInputStream;
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
        dados[0] = scanner.nextLine();
        System.out.print("Password: ");
        dados[1] = scanner.nextLine();

        //System.out.println(dados[0]+dados[1]);
        return dados;
    }


    public static void main(/*String[] args*/) {


        String escolhaMenuPrincipal;
        String [] dadosLogin;

        escolhaMenuPrincipal = Vista.menuPrincipal();
        User user = new User();

        //System.out.println(escolhaMenuPrincipal);

        switch (escolhaMenuPrincipal) {
            case "registo": {
                ControladorPrincipal.registo(user);
                System.out.println(user);
                ControladorAutenticacaoCliente.registo(ligacao,user);
                //ligacao.enviaMensagem(ligacao.getSocket(), user);

            }
            case "login": {

                dadosLogin = ControladorPrincipal.login();
                //System.out.println(dados[0] + dados[1]);
                ControladorAutenticacaoCliente.login(ligacao,dadosLogin);

                try {
                    ObjectInputStream oin = new ObjectInputStream(ligacao.getSocket().getInputStream());
                    System.out.println(oin.readObject());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                //Login login = new Login("a21220079@isec.pt", "1234");
                //ControladorAutenticacao.login(login);


            }

        }
        while (true){
            Vista.menuCliente();
            System.out.print("Escolha #> ");

            Scanner scanner = new Scanner(System.in);
            System.out.println("\n");
            int opcao = scanner.nextInt();


        }
    }
}

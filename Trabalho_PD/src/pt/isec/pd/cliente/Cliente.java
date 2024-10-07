package pt.isec.pd.cliente;

import pt.isec.pd.formularios.UserCommandManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

    String nome;
    String password;
    String email;


    private boolean registo(){

        return true;
    }

    private String menu(){
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
                return "escolha";
            } else {
                System.out.println("Escolha Invalida");

            }
        }
    }


    public static void main(String[] args) {

        InetAddress servidorAddress;
        int servidorPorto;
        int argc = args.length;
        String escolhaMenuPrincipal;

        if (argc != 2){
            System.out.println("java Cliente IPAdress PORT");
        }

        try{

            servidorAddress = InetAddress.getByName(args[0]);
            servidorPorto = Integer.parseInt(args[1]);
            try (Socket socket = new Socket(servidorAddress,servidorPorto)){

                System.out.println("Ligacao ao Servidor com sucesso!");
                Cliente cliente = new Cliente();
                UserCommandManager command = new UserCommandManager();
                escolhaMenuPrincipal = cliente.menu();
                if(escolhaMenuPrincipal.equals("login")){
                    System.out.println("LOGIN");
                    command.setComando(escolhaMenuPrincipal);
                    System.out.println(command);
                }else{
                    System.out.println("REGISTO");
                    command.setComando(escolhaMenuPrincipal);
                    System.out.println(command);
                }



            }
        } catch (UnknownHostException e) {
            System.out.println("Destino desconhecido:\n\t" + e);
        } catch (IOException e) {
            System.out.println("Ocorreu um erro no acesso ao socket:\n\t" + e);
        }


    }
}

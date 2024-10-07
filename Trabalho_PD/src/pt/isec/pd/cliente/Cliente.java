package pt.isec.pd.cliente;

import pt.isec.pd.cliente.ligacao.Ligacao;

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


        int argc = args.length;
        String escolhaMenuPrincipal;

        if (argc != 2){
            System.out.println("java Cliente IPAdress PORT");
        }

        Ligacao ligacao = new Ligacao(args[0],args[1]);
        ligacao.enviaMensagem();




              /*  System.out.println("Ligacao ao Servidor com sucesso!");
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
                }*/







    }
}

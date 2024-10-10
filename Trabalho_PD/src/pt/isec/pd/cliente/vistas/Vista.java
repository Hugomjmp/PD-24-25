package pt.isec.pd.cliente.vistas;

import java.util.Scanner;

public class Vista {





    public static String menuPrincipal(){
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
}

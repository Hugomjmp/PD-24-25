package pt.isec.pd.cliente.vistas;

import pt.isec.pd.cliente.controladores.ControladorPrincipal;

import java.util.Scanner;

public class Vista {
    public static String menuPrincipal(){
        Scanner entrada = new Scanner(System.in);
        while (true) {
            System.out.println("+------------------+");
            System.out.println("|  MENU - Login    |");
            System.out.println("+------------------+");
            System.out.println("|  1 - login       |");
            System.out.println("|  2 - registo     |");
            System.out.println("+------------------+");
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
    public static int menuCliente(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("+------------------------------------------+");
            System.out.println("|               MENU - Cliente             |");
            System.out.println("+------------------------------------------+");
            System.out.println("|  1  - Cria Grupo.                        |");
            System.out.println("|  2  - Convidar para um grupo.            |");
            System.out.println("|  3  - Listar Convites.                   |");
            System.out.println("|  4  - Listar Grupos.                     |");
            System.out.println("|  5  - Eliminar de um Grupo.              |");
            System.out.println("|  6  - Sair de um Grupo.                  |");
            System.out.println("|  7  - Ver Gasto total de um grupo.       |");
            System.out.println("|  8  - Ver histório de um grupo.          |");
            System.out.println("|  9  - Guardar Despesas para um ficheiro. |");
            System.out.println("|  10 - Editar Despesa.                    |");
            System.out.println("|  11 - Eliminar uma Despesa.              |");
            System.out.println("|  12 - Efectuar Pagamento.                |");
            System.out.println("|  13 - Listar todos os Pagamentos.        |");
            System.out.println("|  14 - Eliminar pagamento efetuado.       |");
            System.out.println("|  15 - Consultar saldos.                  |");
            System.out.println("|  16 - Logout.                            |");
            System.out.println("+------------------------------------------+");
            int opcao = scanner.nextInt();
            if (opcao > 0 && opcao < 17 ){
                return opcao;
            }else{
                System.out.println("OPÇÃO INVÁLIDA!");
            }
        }
    }
    public static void tabelaConvites(String nome, String nomeGrupo, String estado){
        System.out.println("+------------------------------------------+");
        System.out.println("|                   Convites               |");
        System.out.println("+------------------------------------------+");
        System.out.println("| "+ nome + "| "+ nomeGrupo +" |" +  estado +"    |");
    }

}

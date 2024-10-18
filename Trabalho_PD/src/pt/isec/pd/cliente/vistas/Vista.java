package pt.isec.pd.cliente.vistas;

import pt.isec.pd.cliente.controladores.ControladorPrincipal;
import pt.isec.pd.comum.modelos.Convites;
import pt.isec.pd.comum.modelos.Grupos;

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
            System.out.println("+-------------------------------------------------------------------------------------+");
            System.out.println("|                                     MENU - Cliente                                  |");
            System.out.println("+-------------------------------------------------------------------------------------+");
            System.out.println("|  1  - Cria Grupo.                        |  11 - Guardar Despesas para um ficheiro. |");
            System.out.println("|  2  - Convidar para um grupo.            |  12 - Editar Despesa.                    |");
            System.out.println("|  3  - Editar nome do grupo               |  13 - Eliminar uma Despesa.              |");
            System.out.println("|  4  - Listar Convites.                   |  14 - Efectuar Pagamento.                |");
            System.out.println("|  5  - Aceitar/Recusar Convites.          |  15 - Listar todos os Pagamentos.        |");
            System.out.println("|  6  - Listar Grupos.                     |  16 - Eliminar pagamento efetuado.       |");
            System.out.println("|  7  - Eliminar de um Grupo.              |  17 - Consultar saldos.                  |");
            System.out.println("|  8  - Sair de um Grupo.                  |  18 - Alterar dados do Utilizador        |");
            System.out.println("|  9  - Ver Gasto total de um grupo.       |  19 - Logout.                            |");
            System.out.println("|  10 - Ver histório de um grupo.          |                                          |");
            System.out.println("+-------------------------------------------------------------------------------------+");
            int opcao = scanner.nextInt();
            if (opcao > 0 && opcao < 20 ){
                return opcao;
            }else{
                System.out.println("OPÇÃO INVÁLIDA!");
            }
        }
    }
    public static void tabelaConvites(Convites convites){
        int tamanhoNome = 0;
        int tamanhoNomeGrupo = 0;
        int tamanhoEstado = 0;
        int tamanhoTotal = 0;
        int espacosEsquerda;
        int espacosDireita;
        //calcular o maior dos nomes possíveis para adaptar a tabela
        for (Convites convite : convites.getConvitesLista() ) {
            tamanhoNome = Math.max(tamanhoNome,convite.getnomeRemetente().length());
            tamanhoNomeGrupo = Math.max(tamanhoNomeGrupo,convite.getGrupoNome().length());
            tamanhoEstado = Math.max(tamanhoEstado,convite.getEstado().length());
        }
        tamanhoTotal = tamanhoNome + tamanhoNomeGrupo + tamanhoEstado + 8;
        //linha 1
        System.out.print("+");
        for (int i=0;i<tamanhoTotal;i++)
            System.out.print("-");
        System.out.println("+");
        //mostra título da tabela
        System.out.print("|");
        espacosEsquerda = (tamanhoTotal - "Convites".length())/2;
        for (int i = 0; i < espacosEsquerda ; i++)
            System.out.print(" ");
        System.out.print("Convites");
        espacosDireita = tamanhoTotal - 8 - espacosEsquerda;
        for (int i = 0; i < espacosDireita ; i++)
            System.out.print(" ");
        System.out.println("|");
        System.out.print("+");
        for (int i=0;i<tamanhoTotal;i++)
            System.out.print("-");
        System.out.println("+");
        //linha 2
        System.out.print("+");
        for (int i=0;i<tamanhoTotal;i++)
            System.out.print("-");
        System.out.println("+");

        for (Convites convite : convites.getConvitesLista() ) {
        System.out.println("| "+ convite.getnomeRemetente() + " | "+ convite.getGrupoNome() +" | " +  convite.getEstado() +" |");

            System.out.print("+");
            for (int i=0;i<tamanhoTotal;i++)
                System.out.print("-");
            System.out.println("+");

        }

    }
    public static void tabelaGrupos(Grupos grupo){
        int tamanhoNome = 0;
        int tamanhoTotal = 0;
        int espacosEsquerda;
        int espacosDireita;
        int espacos = 0;
        //calcular o maior dos nomes possíveis para adaptar a tabela
        for (Grupos grupos : grupo.getGruposList() ) {
            tamanhoNome = Math.max(tamanhoNome,grupos.getNomeGrupo().length());;
        }
        tamanhoTotal = tamanhoNome + 2;
        //linha 1
        System.out.print("+");
        for (int i=0;i<tamanhoTotal;i++)
            System.out.print("-");
        System.out.println("+");
        //mostra título da tabela
        System.out.print("|");
        espacosEsquerda = (tamanhoTotal - "Grupos".length())/2;
        for (int i = 0; i < espacosEsquerda ; i++)
            System.out.print(" ");
        System.out.print("Grupos");
        espacosDireita = tamanhoTotal - 6 - espacosEsquerda;
        for (int i = 0; i < espacosDireita ; i++)
            System.out.print(" ");
        System.out.println("|");
        System.out.print("+");
        for (int i=0;i<tamanhoTotal;i++)
            System.out.print("-");
        System.out.println("+");
        //linha 2
        System.out.print("+");
        for (int i=0;i<tamanhoTotal;i++)
            System.out.print("-");
        System.out.println("+");

        for (Grupos grupos : grupo.getGruposList() ) {
            System.out.print("| "+ grupos.getNomeGrupo());
            espacos = tamanhoTotal - 1 - grupos.getNomeGrupo().length();
            for (int i = 0 ; i < espacos ; i++)
                System.out.print(" ");
            System.out.print("|");
            System.out.print("\n+");
            for (int i=0;i<tamanhoTotal;i++)
                System.out.print("-");
            System.out.println("+");

        }
        System.out.print("\n");

    }

}

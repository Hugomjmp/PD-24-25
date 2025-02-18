package pt.isec.pd.cliente.vistas;

import pt.isec.pd.cliente.controladores.ControladorPrincipal;
import pt.isec.pd.comum.modelos.Convites;
import pt.isec.pd.comum.modelos.Despesa;
import pt.isec.pd.comum.modelos.Grupos;
import pt.isec.pd.comum.modelos.Pagamento;

import java.util.List;
import java.util.Scanner;

public class Vista {
    public static void menuPrincipal() {
        Scanner entrada = new Scanner(System.in);

        System.out.println("+------------------+");
        System.out.println("|  MENU - Login    |");
        System.out.println("+------------------+");
        System.out.println("|  1 - login       |");
        System.out.println("|  2 - registo     |");
        System.out.println("+------------------+");
        System.out.print("#> ");


    }

    /*    public static String menuPrincipal(){
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
        }*/
    public static int menuCliente() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("+-------------------------------------------------------------------------------------+");
            System.out.println("|                                     MENU - Cliente                                  |");
            System.out.println("+-------------------------------------------------------------------------------------+");
            System.out.println("|  -1  - Cria Grupo.                        |  -12 - Inserir uma Despesa.               |");
            System.out.println("|  -2  - Selecionar um Grupo.               |  -13 - Guardar Despesas para um ficheiro. |");
            System.out.println("|  -3  - Convidar para um grupo.            |  -14 - Editar Despesa.                    |");
            System.out.println("|  -4  - Editar nome do grupo               |  -15 - Eliminar uma Despesa.              |");
            System.out.println("|  -5  - Listar Convites.                   |  -16 - Efectuar Pagamento.                |");
            System.out.println("|  -6  - Aceitar/Recusar Convites.          |  -17 - Listar todos os Pagamentos.        |");
            System.out.println("|  -7  - Listar Grupos.                     |  -18 - Eliminar pagamento efetuado.       |");
            System.out.println("|  -8  - Eliminar de um Grupo.              |  19 - Consultar saldos.                  |");
            System.out.println("|  -9  - Sair de um Grupo.                  |  -20 - Alterar dados do Utilizador        |");
            System.out.println("|  -10 - Ver Gasto total de um grupo.      |  -21 - Logout.                            |");
            System.out.println("|  -11 - Ver histório de um grupo.          |                                          |");
            System.out.println("+-------------------------------------------------------------------------------------+");
            System.out.print("#>");
            int opcao = scanner.nextInt();
            if (opcao > 0 && opcao < 22) {
                return opcao;
            } else {
                System.out.println("OPÇÃO INVÁLIDA!");
            }
        }
    }

    public static void menuPrincipalCliente() {
        System.out.println("+------------------------------------------+");
        System.out.println("|            MENU - Principal              |");
        System.out.println("+------------------------------------------+");
        System.out.println("|  1  - Grupo Menu.                        |");
        System.out.println("|  2  - Despesas Menu.                     |");
        System.out.println("|  3  - Pagamentos Menu.                   |");
        System.out.println("|  4  - Ver convites Menu.                 |");
        System.out.println("|  5  - Alterar Password ou nº Telefone.   |");
        System.out.println("|  6  - Logout.                            |");
        System.out.println("+------------------------------------------+");
        System.out.print("#>");
    }

    public static void menuGrupo() {
        System.out.println("+------------------------------------------+");
        System.out.println("|               MENU - Grupo               |");
        System.out.println("+------------------------------------------+");
        System.out.println("|  1  - Cria Grupo.                        |");
        System.out.println("|  2  - Selecionar um grupo.               |");
        System.out.println("|  3  - Editar nome do grupo               |");
        System.out.println("|  4  - Listar Grupos.                     |");
        System.out.println("|  5  - Eliminar um Grupo.                 |");
        System.out.println("|  6  - Sair de um Grupo.                  |");
        System.out.println("|  7  - Consultar Saldos                   |");
        System.out.println("|  8  - Back                               |");
        System.out.println("+------------------------------------------+");
        System.out.print("#>");
    }

    public static void menuConvites() {
        System.out.println("+------------------------------------------+");
        System.out.println("|             MENU - Convites              |");
        System.out.println("+------------------------------------------+");
        System.out.println("|  1  - Enviar um Convite.                 |");
        System.out.println("|  2  - Aceitar/ Recusar Convites.         |");
        System.out.println("|  3  - Listar Convites.                   |");
        System.out.println("|  4  - Back                               |");
        System.out.println("+------------------------------------------+");
        System.out.print("#>");
    }

    public static void menuDespesa() {
        System.out.println("+------------------------------------------+");
        System.out.println("|             MENU - Despesa               |");
        System.out.println("+------------------------------------------+");
        System.out.println("|  1  - Inserir uma Despesa.               |");
        System.out.println("|  2  - Editar uma Despesa.                |");
        System.out.println("|  3  - Eliminar uma Despesa.              |");
        System.out.println("|  4  - Ver gasto total.                   |");
        System.out.println("|  5  - Ver historico de Despesas.         |");
        System.out.println("|  6  - Exportar Despesas para um ficheiro |");
        System.out.println("|  7  - Back                               |");
        System.out.println("+------------------------------------------+");
        System.out.print("#>");
    }

    public static void menuPagamentos() {
        System.out.println("+------------------------------------------+");
        System.out.println("|            MENU - Pagamentos             |");
        System.out.println("+------------------------------------------+");
        System.out.println("|  1  - Efectuar Pagamento.                |");
        System.out.println("|  2  - Eliminar pagamento efetuado.       |");
        System.out.println("|  3  - Listar todos os Pagamentos.        |");
        System.out.println("|  4  - Back                               |");
        System.out.println("+------------------------------------------+");
        System.out.print("#>");
    }

    public static void tabelaConvites(Convites convites) {
        int tamanhoNome = 0;
        int tamanhoNomeGrupo = 0;
        int tamanhoEstado = 0;
        int tamanhoTotal = 0;
        int espacosEsquerda;
        int espacosDireita;
        //calcular o maior dos nomes possíveis para adaptar a tabela
        for (Convites convite : convites.getConvitesLista()) {
            tamanhoNome = Math.max(tamanhoNome, convite.getnomeRemetente().length());
            tamanhoNomeGrupo = Math.max(tamanhoNomeGrupo, convite.getGrupoNome().length());
            tamanhoEstado = Math.max(tamanhoEstado, convite.getEstado().length());
        }
        tamanhoTotal = tamanhoNome + tamanhoNomeGrupo + tamanhoEstado + 8;
        //linha 1
        System.out.print("+");
        for (int i = 0; i < tamanhoTotal; i++)
            System.out.print("-");
        System.out.println("+");
        //mostra título da tabela
        System.out.print("|");
        espacosEsquerda = (tamanhoTotal - "Convites".length()) / 2;
        for (int i = 0; i < espacosEsquerda; i++)
            System.out.print(" ");
        System.out.print("Convites");
        espacosDireita = tamanhoTotal - 8 - espacosEsquerda;
        for (int i = 0; i < espacosDireita; i++)
            System.out.print(" ");
        System.out.println("|");
        System.out.print("+");
        for (int i = 0; i < tamanhoTotal; i++)
            System.out.print("-");
        System.out.println("+");


        System.out.print("|");
        for (int i = 0; i < (tamanhoNome + 2 - "CONVITE DE".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("CONVITE DE");
        for (int i = 0; i < (tamanhoNome + 2 - "CONVITE DE".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("|");
        for (int i = 0; i < (tamanhoNomeGrupo + 2 - "GRUPO".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("GRUPO");
        for (int i = 0; i < (tamanhoNomeGrupo + 2 - "GRUPO".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("|");
        for (int i = 0; i < (tamanhoEstado + 3 - "PAGO POR".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("PAGO POR");
        for (int i = 0; i < (tamanhoEstado + 3 - "PAGO POR".length()) / 2; i++)
            System.out.print(" ");
        System.out.println("|");


        //linha 2
        System.out.print("+");
        for (int i = 0; i < tamanhoTotal; i++)
            System.out.print("-");
        System.out.println("+");

        for (Convites convite : convites.getConvitesLista()) {
            System.out.print("| " + convite.getnomeRemetente());
            for (int i = 0; i < tamanhoNome - String.valueOf(convite.getnomeRemetente()).length() + 1; i++)
                System.out.print(" ");
            System.out.print("| ");
            System.out.print(convite.getGrupoNome());
            for (int i = 0; i < tamanhoNomeGrupo - String.valueOf(convite.getGrupoNome()).length() + 1; i++)
                System.out.print(" ");
            System.out.print("| ");
            System.out.print(convite.getEstado());
            for (int i = 0; i < tamanhoEstado - String.valueOf(convite.getEstado()).length() + 1; i++)
                System.out.print(" ");
            System.out.print("|\n");
            System.out.print("+");
            for (int i = 0; i < tamanhoTotal; i++)
                System.out.print("-");
            System.out.println("+");

        }

    }

    public static void tabelaGrupos(Grupos grupo) {
        int tamanhoNome = 0;
        int tamanhoTotal = 0;
        int espacosEsquerda;
        int espacosDireita;
        int espacos = 0;
        //calcular o maior dos nomes possíveis para adaptar a tabela
        for (Grupos grupos : grupo.getGruposList()) {
            tamanhoNome = Math.max(tamanhoNome, grupos.getNomeGrupo().length());
            ;
        }
        tamanhoTotal = tamanhoNome + 2;
        //linha 1
        System.out.print("+");
        for (int i = 0; i < tamanhoTotal; i++)
            System.out.print("-");
        System.out.println("+");
        //mostra título da tabela
        System.out.print("|");
        espacosEsquerda = (tamanhoTotal - "Grupos".length()) / 2;
        for (int i = 0; i < espacosEsquerda; i++)
            System.out.print(" ");
        System.out.print("Grupos");
        espacosDireita = tamanhoTotal - 6 - espacosEsquerda;
        for (int i = 0; i < espacosDireita; i++)
            System.out.print(" ");
        System.out.println("|");
        System.out.print("+");
        for (int i = 0; i < tamanhoTotal; i++)
            System.out.print("-");
        System.out.println("+");
        //linha 2
        System.out.print("+");
        for (int i = 0; i < tamanhoTotal; i++)
            System.out.print("-");
        System.out.println("+");

        for (Grupos grupos : grupo.getGruposList()) {
            System.out.print("| " + grupos.getNomeGrupo());
            espacos = tamanhoTotal - 1 - grupos.getNomeGrupo().length();
            for (int i = 0; i < espacos; i++)
                System.out.print(" ");
            System.out.print("|");
            System.out.print("\n+");
            for (int i = 0; i < tamanhoTotal; i++)
                System.out.print("-");
            System.out.println("+");

        }
        System.out.print("\n");

    }


    public static void tabelaDespesas(Despesa despesas) {
        int tamanhoDescricao = 0;
        int tamanhoRegistadoPor = 0;
        int tamanhoPagoPor = 0;
        int tamanhoData = 0;
        int tamanhoValor = 0;
        int tamanhoID = 0;
        int tamanhoTotal = 0;
        int espacosEsquerda;
        int espacosDireita;
        //calcular o maior dos nomes possíveis para adaptar a tabela
        for (Despesa despesa : despesas.getDespesaList()) {
            tamanhoID = Math.max(tamanhoID, despesa.getIdDespesa().length());
            tamanhoDescricao = Math.max(tamanhoDescricao, despesa.getDescricao().length());
            tamanhoRegistadoPor = Math.max(tamanhoRegistadoPor, despesa.getEmail().length());
            tamanhoPagoPor = Math.max(tamanhoPagoPor, despesa.getQuemPagou().length());
            tamanhoData = Math.max(tamanhoData, despesa.getData().length());
            tamanhoValor = Math.max(tamanhoValor, String.valueOf(despesa.getDespesa()).length());

        }
        tamanhoTotal = tamanhoDescricao + tamanhoRegistadoPor + tamanhoPagoPor +
                tamanhoData + tamanhoValor + tamanhoID + 14;
        //linha 1
        System.out.print("+");
        for (int i = 0; i < tamanhoTotal + 4; i++)
            System.out.print("-");
        System.out.println("+");
        //mostra título da tabela
        System.out.print("|");
        espacosEsquerda = (tamanhoTotal - "Histórico De Despesas".length()) / 2;
        for (int i = 0; i < espacosEsquerda; i++)
            System.out.print(" ");
        System.out.print("Histórico De Despesas");
        espacosDireita = tamanhoTotal - 21 - espacosEsquerda;
        for (int i = 0; i < espacosDireita + 4; i++)
            System.out.print(" ");
        System.out.println("|");
        System.out.print("+");
        for (int i = 0; i < tamanhoTotal + 4; i++)
            System.out.print("-");
        System.out.println("+");
        System.out.print("|");
        for (int i = 0; i < (tamanhoID + 2 - "ID".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("ID");
        for (int i = 0; i < (tamanhoID + 2 - "ID".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("|");
        for (int i = 0; i < (tamanhoData + 2 - "DATA".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("DATA");
        for (int i = 0; i < (tamanhoData + 2 - "DATA".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("|");
        for (int i = 0; i < (tamanhoValor + 3 - "Valor".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("Valor");
        for (int i = 0; i < (tamanhoValor + 3 - "Valor".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("|");
        for (int i = 0; i < (tamanhoDescricao + 2 - "Descrição".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("Descrição");
        for (int i = 0; i < (tamanhoDescricao + 2 - "Descrição".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("|");
        for (int i = 0; i < (tamanhoRegistadoPor + 2 - "Registo".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("Registo");
        for (int i = 0; i < (tamanhoRegistadoPor + 2 - "Registo".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("|");
        for (int i = 0; i < (tamanhoPagoPor + 2 - "Pago".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("Pago");
        for (int i = 0; i < (tamanhoPagoPor + 2 - "Pago".length()) / 2; i++)
            System.out.print(" ");
        System.out.println("|");
        //linha 2
        System.out.print("+");
        for (int i = 0; i < tamanhoTotal + 4; i++)
            System.out.print("-");
        System.out.println("+");

        for (Despesa despesa : despesas.getDespesaList()) {

            System.out.print("| " + despesa.getIdDespesa());
            for (int i = 0; i < tamanhoID - String.valueOf(despesa.getIdDespesa()).length() + 1; i++)
                System.out.print(" ");
            System.out.print("| ");


            System.out.print(despesa.getData() + " | ");

            System.out.print(despesa.getDespesa() + "€");
            for (int i = 0; i < tamanhoValor - String.valueOf(despesa.getDespesa()).length() + 1; i++)
                System.out.print(" ");
            System.out.print("| ");
            System.out.print(despesa.getDescricao());

            for (int i = 0; i < tamanhoDescricao - despesa.getDescricao().length() + 1; i++)
                System.out.print(" ");

            System.out.print("| ");

            System.out.print(despesa.getEmail());
            for (int i = 0; i < tamanhoRegistadoPor - despesa.getEmail().length() + 1; i++)
                System.out.print(" ");
            System.out.print("| ");

            System.out.print(despesa.getQuemPagou());
            for (int i = 0; i < tamanhoPagoPor - despesa.getQuemPagou().length() + 1; i++)
                System.out.print(" ");
            System.out.print("|\n");


            System.out.print("+");
            for (int i = 0; i < tamanhoTotal + 4; i++)
                System.out.print("-");
            System.out.println("+");

        }
    }

    public static void tabelaPagamentos(Pagamento pagamento) {
        //List<Pagamento> listaPagamentos = pagamento.getPagamentos();
        int tamanhoTotal = 0;
        int tamanhoPagoPor = 0;
        int tamanhoRecebidoPor = 0;
        int tamanhoData = 0;
        int tamanhoValor = 0;
        int espacosEsquerda;
        int espacosDireita;
        int espacos = 0;
        //calcular o maior dos nomes possíveis para adaptar a tabela
        for (Pagamento pagamentos : pagamento.getPagamentos()) {
            tamanhoData = Math.max(tamanhoData, pagamentos.getData().length());
            tamanhoValor = Math.max(tamanhoValor, String.valueOf(pagamentos.getValor()).length());
            tamanhoPagoPor = Math.max(tamanhoPagoPor, pagamentos.getPagaPor().length());
            tamanhoRecebidoPor = Math.max(tamanhoRecebidoPor, pagamentos.getRecebidoPor().length());



        }
        System.out.println(tamanhoValor);
        tamanhoTotal = tamanhoValor + tamanhoPagoPor + tamanhoRecebidoPor + tamanhoData + 12;
        //linha 1
        System.out.print("+");
        for (int i = 0; i < tamanhoTotal + 1; i++)
            System.out.print("-");
        System.out.println("+");
        //mostra título da tabela
        System.out.print("|");
        espacosEsquerda = (tamanhoTotal - "Pagamentos".length()) / 2;
        for (int i = 0; i < espacosEsquerda; i++)
            System.out.print(" ");
        System.out.print("Pagamentos");
        espacosDireita = tamanhoTotal - 9 - espacosEsquerda;
        for (int i = 0; i < espacosDireita; i++)
            System.out.print(" ");
        System.out.println("|");
        System.out.print("+");
        for (int i = 0; i < tamanhoTotal + 1; i++)
            System.out.print("-");
        System.out.println("+");


        System.out.print("|");
        for (int i = 0; i < (tamanhoData + 2 - "DATA".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("DATA");
        for (int i = 0; i < (tamanhoData + 2 - "DATA".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("|");
        for (int i = 0; i < (tamanhoValor + 4 - "VALOR".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("VALOR");
        for (int i = 0; i < (tamanhoValor + 4 - "VALOR".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("|");
        for (int i = 0; i < (tamanhoPagoPor + 3 - "PAGO POR".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("PAGO POR");
        for (int i = 0; i < (tamanhoPagoPor + 3 - "PAGO POR".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("|");
        for (int i = 0; i < (tamanhoRecebidoPor + 2 - "ENVIADO A".length()) / 2; i++)
            System.out.print(" ");
        System.out.print("ENVIADO A");
        for (int i = 0; i < (tamanhoRecebidoPor + 2 - "ENVIADO A".length()) / 2; i++)
            System.out.print(" ");
        System.out.println("|");


        //linha 2
        System.out.print("+");
        for (int i = 0; i < tamanhoTotal + 1; i++)
            System.out.print("-");
        System.out.println("+");
        for (Pagamento pagamentos : pagamento.getPagamentos()) {

            System.out.print("| " + pagamentos.getData());
            System.out.print(" ");
            System.out.print("| ");

/*            System.out.print(despesa.getDespesa() + "€");
            for (int i = 0; i < tamanhoValor - String.valueOf(despesa.getDespesa()).length() + 1; i++)
                System.out.print(" ");
            System.out.print("| ");*/


            System.out.print(pagamentos.getValor() + "€");
            for (int i = 0; i < tamanhoValor - String.valueOf(pagamentos.getValor()).length() + 1; i++)
                System.out.print(" ");
            System.out.print("| ");



            System.out.print(pagamentos.getPagaPor());
            for (int i = 0; i < tamanhoPagoPor - pagamentos.getPagaPor().length() + 2; i++)
                System.out.print(" ");
            System.out.print("| ");

            System.out.print(pagamentos.getRecebidoPor());
            for (int i = 0; i < tamanhoRecebidoPor - pagamentos.getRecebidoPor().length() + 1; i++)
                System.out.print(" ");
            System.out.print("|\n");

            System.out.print("+");
            for (int i = 0; i < tamanhoTotal + 1; i++)
                System.out.print("-");
            System.out.println("+");
            //System.out.println(pagamentos.getData()+pagamentos.getValor()+pagamentos.getRecebidoPor()+pagamentos.getPagaPor());
        }
        /*if (listaPagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento disponível.");
            return;
        }

        int tamanhoGroupId = 0;
        int tamanhoData = 0;
        int tamanhoValor = 0;
        int tamanhoPagaPor = 0;
        int tamanhoRecebidoPor = 0;
        int tamanhoTotal = 0;
        int espacosEsquerda;
        int espacosDireita;

        // Calcular tamanhos
        for (Pagamento p : listaPagamentos) {
            tamanhoGroupId = Math.max(tamanhoGroupId, p.getGroupId().length());
            tamanhoData = Math.max(tamanhoData, p.getData().length());
            tamanhoValor = Math.max(tamanhoValor, String.valueOf(p.getValor()).length());
            tamanhoPagaPor = Math.max(tamanhoPagaPor, p.getPagaPor().length());
            tamanhoRecebidoPor = Math.max(tamanhoRecebidoPor, p.getRecebidoPor().length());
        }

        tamanhoTotal = tamanhoGroupId + tamanhoData + tamanhoValor + tamanhoPagaPor + tamanhoRecebidoPor + 16;

        // Cabeçalho da tabela
        System.out.print("+");
        for (int i = 0; i < tamanhoTotal + 4; i++) System.out.print("-");
        System.out.println("+");

        System.out.print("|");
        espacosEsquerda = (tamanhoTotal - "Histórico De Pagamentos".length()) / 2;
        for (int i = 0; i < espacosEsquerda; i++) System.out.print(" ");
        System.out.print("Histórico De Pagamentos");
        espacosDireita = tamanhoTotal - "Histórico De Pagamentos".length() - espacosEsquerda;
        for (int i = 0; i < espacosDireita + 4; i++) System.out.print(" ");
        System.out.println("|");

        System.out.print("+");
        for (int i = 0; i < tamanhoTotal + 4; i++) System.out.print("-");
        System.out.println("+");

        // Cabeçalho da tabela
        System.out.print("|");
        for (int i = 0; i < (tamanhoGroupId + 2 - "Group ID".length()) / 2; i++) System.out.print(" ");
        System.out.print("Group ID");
        for (int i = 0; i < (tamanhoGroupId + 2 - "Group ID".length()) / 2; i++) System.out.print(" ");
        System.out.print("|");

        for (int i = 0; i < (tamanhoData + 2 - "DATA".length()) / 2; i++) System.out.print(" ");
        System.out.print("DATA");
        for (int i = 0; i < (tamanhoData + 2 - "DATA".length()) / 2; i++) System.out.print(" ");
        System.out.print("|");

        for (int i = 0; i < (tamanhoValor + 3 - "Valor".length()) / 2; i++) System.out.print(" ");
        System.out.print("Valor");
        for (int i = 0; i < (tamanhoValor + 3 - "Valor".length()) / 2; i++) System.out.print(" ");
        System.out.print("|");

        for (int i = 0; i < (tamanhoPagaPor + 2 - "Pago Por".length()) / 2; i++) System.out.print(" ");
        System.out.print("Pago Por");
        for (int i = 0; i < (tamanhoPagaPor + 2 - "Pago Por".length()) / 2; i++) System.out.print(" ");
        System.out.print("|");

        for (int i = 0; i < (tamanhoRecebidoPor + 2 - "Recebido Por".length()) / 2; i++) System.out.print(" ");
        System.out.print("Recebido Por");
        for (int i = 0; i < (tamanhoRecebidoPor + 2 - "Recebido Por".length()) / 2; i++) System.out.print(" ");
        System.out.print("|");

        System.out.print("+");
        for (int i = 0; i < tamanhoTotal + 4; i++) System.out.print("-");
        System.out.println("+");

        // Listar pagamentos
        for (Pagamento p : listaPagamentos) {
            System.out.print("| " + p.getGroupId());
            for (int i = 0; i < tamanhoGroupId - p.getGroupId().length() + 1; i++) System.out.print(" ");
            System.out.print("| ");

            System.out.print(p.getData() + " | ");

            System.out.print(p.getValor() + "€");
            for (int i = 0; i < tamanhoValor - String.valueOf(p.getValor()).length() + 1; i++) System.out.print(" ");
            System.out.print("| ");

            System.out.print(p.getPagaPor());
            for (int i = 0; i < tamanhoPagaPor - p.getPagaPor().length() + 1; i++) System.out.print(" ");
            System.out.print("| ");

            System.out.print(p.getRecebidoPor());
            for (int i = 0; i < tamanhoRecebidoPor - p.getRecebidoPor().length() + 1; i++) System.out.print(" ");
            System.out.print("|");

            System.out.println();
            System.out.print("+");
            for (int i = 0; i < tamanhoTotal + 4; i++) System.out.print("-");
            System.out.println("+");
        }*/
    }
}
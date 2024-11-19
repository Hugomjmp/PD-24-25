package pt.isec.pd.cliente.UI;

import pt.isec.pd.cliente.controladores.ControladorPrincipal;
import pt.isec.pd.cliente.vistas.Vista;
import pt.isec.pd.comum.modelos.Convites;
import pt.isec.pd.comum.modelos.Despesa;
import pt.isec.pd.comum.modelos.Grupos;
import pt.isec.pd.comum.modelos.Pagamento;
import pt.isec.pd.db.Bd;

import java.util.List;
import java.util.Scanner;

import static pt.isec.pd.db.Bd.*;

public class ClienteConsolaUI {
    ControladorPrincipal cp;
    private Scanner scanner;
    String grupoSelecionado = null;

    public ClienteConsolaUI(ControladorPrincipal cp) {
        this.cp = cp;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        String escolha;
        do {
            if (!cp.isUserAuthenticated()) { 
                Vista.menuPrincipal();
                escolha = scanner.nextLine();
                switch (escolha) {
                    case "1":
                        if (login()) {
                            System.out.println("Login efetuado com sucesso.");
                        } else {
                            System.out.println("Falha no login.");
                        }
                        break;
                    case "2":
                        registo();
                        break;
                    default:
                        System.out.println("Escolha Invalida.");
                }
            } else {
                Vista.menuPrincipalCliente();
                escolha = scanner.nextLine();
                switch (escolha) {
                    case "1":
                        menuGrupos();
                        break;
                    case "2":
                        despesasMenu();
                        break;
                    case "3":
                        pagamentosMenu();
                        break;
                    case "4":
                        convitesMenu();
                        break;
                    case "5":
                        alteraDados();
                        break;
                    case "6":
                        cp.logout();
                        grupoSelecionado = null;
                        System.out.println("Logout efetuado com sucesso.");
                        break;
                    default:
                        System.out.println("Opção Inválida!");
                }
            }
        } while (true);
    }


    public boolean login() {
        boolean login;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Login E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        login = cp.login(email, password);
        if (login) {
            System.out.println("Login efectuado com sucesso");
            return true;
        } else {
            System.out.println("Falha no login");
            return false;
        }
    }

    public void registo() {
        int registo;
        String email, nome, password, nTelefoneString;
        System.out.print("Login E-mail: ");
        email = scanner.nextLine();
        System.out.print("NOME: ");
        nome = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();
        System.out.print("Número de Telefone: ");
        nTelefoneString = scanner.nextLine();
        registo = cp.registo(nome, email, password, nTelefoneString);
        if (registo == 0)
            System.out.println("Tem de ter o seguinte formato: exemplo@exemplo.com");
        else if (registo == 1)
            System.out.println("O número de telefone deve conter 9 digitos.");
        else if (registo == 2)
            System.out.println("O número do telefone deve conter apenas números.");
        else
            System.out.println("Registo efectuado com sucesso.");
    }

    public void alteraDados() {
        String numeroTelefone, pass;
        System.out.println("Deixar vazio o que não é para alterar.");
        System.out.print("Número de Telefone: ");
        numeroTelefone = scanner.nextLine();
        System.out.print("\nPassword:");
        pass = scanner.nextLine();
        cp.editaDados(numeroTelefone, pass);
    }

    public void menuGrupos() {
        String escolha;

        do {
            Vista.menuGrupo();
            escolha = scanner.nextLine();
            switch (escolha) {
                case "1":
                    criarGrupo();
                    break;
                case "2":
                    selecionarGrupo();
                    break;
                case "3":
                    editarNomeGrupo();
                    break;
                case "4":
                    listarGrupos();
                    break;
                case "5":
                    eliminarGrupo();
                    break;
                case "6":
                    sairGrupo();
                    break;
                case "7":
                    consultarSaldos();
                    break;
                default:
                    if (!escolha.equalsIgnoreCase("8"))
                        System.out.println("Opção Inválida!");
            }
        } while (!escolha.equalsIgnoreCase("8"));

    }

    public void criarGrupo() {
        String nomeGrupo;
        System.out.print("Nome do grupo: ");
        nomeGrupo = scanner.nextLine();
        cp.criarGrupo(nomeGrupo);

    }

    public void selecionarGrupo() {
        String nomeGrupo;
        System.out.print("Nome do Grupo que pretende selecionar: ");
        nomeGrupo = scanner.nextLine();
        grupoSelecionado = cp.selecionarGrupo(nomeGrupo);
        System.out.println(grupoSelecionado);
    }

    public void editarNomeGrupo() {
        String nomeGrupo;
        boolean confirmacao;
        System.out.print("Nome do Grupo que pretende alterar: ");
        nomeGrupo = scanner.nextLine();
        confirmacao = cp.editarGrupo(nomeGrupo, grupoSelecionado);
        if (confirmacao)
            System.out.println("Nome do grupo alterado com sucesso");
        else
            System.out.println("Tem de selecionar um grupo primeiro");

        grupoSelecionado = nomeGrupo;


    }

    public void listarGrupos() {
        Grupos grupos = cp.listarGrupos();
        Vista.tabelaGrupos(grupos);
    }

    public void eliminarGrupo() {
        String nomeGrupo;
        System.out.print("Nome do grupo a eliminar:");
        nomeGrupo = scanner.nextLine();
        grupoSelecionado = cp.eliminarGrupo(nomeGrupo, grupoSelecionado);
    }

    public void sairGrupo() {
        String nomeGrupo;
        System.out.print("Nome do grupo do qual deseja sair: ");
        nomeGrupo = scanner.nextLine();
        grupoSelecionado = cp.sairDoGrupo(nomeGrupo, grupoSelecionado);

    }

    public void despesasMenu() {
        String escolha;

        do {
            Vista.menuDespesa();
            escolha = scanner.nextLine();
            switch (escolha) {
                case "1":
                    criarDespesa();
                    break;
                case "2":
                    editarDespesa();
                    break;
                case "3":
                    eliminarDespesa();
                    break;
                case "4":
                    consultarGastoTotal();
                    break;
                case "5":
                    listarDespesas();
                    break;
                case "6":
                    exportarCSV();
                    break;
                default:
                    if (!escolha.equalsIgnoreCase("7"))
                        System.out.println("Opção Inválida!");
            }
        } while (!escolha.equalsIgnoreCase("7"));
    }

    public void criarDespesa() {
        double despesa;
        String quemPagou;
        String descricao;
        String data;
        System.out.print("\nDespesa: ");
        despesa = Double.parseDouble(scanner.nextLine());
        System.out.print("\nPago por (email): ");
        quemPagou = scanner.nextLine();
        System.out.print("\nDescricao: ");
        descricao = scanner.nextLine();
        System.out.println("\nPago em (DD-MM-YYYY): ");
        data = scanner.nextLine();
        cp.insereDespesa(grupoSelecionado, despesa, quemPagou, descricao, data);

    }
    public void consultarGastoTotal() {
        String gastoTotal = cp.consultaGastoTotal(grupoSelecionado);
        System.out.println("Gasto total do grupo " + grupoSelecionado + " é de: " + gastoTotal + "€");
    }
    public void consultarSaldos() {
        String gastoTotal = cp.consultarSaldos(grupoSelecionado);
        System.out.println("Gasto total do grupo " + grupoSelecionado + ": " + gastoTotal + "€");

        String totalDeve = cp.consultarDeve(grupoSelecionado);
        System.out.println("Você deve no total: " + totalDeve + "€");

        String totalDeveMembro = cp.consultaDeveMembro(grupoSelecionado);
        System.out.println(totalDeveMembro);

    }



    public void editarDespesa() {
        String valor;
        String quemPagou;
        String descricao;
        String data;
        String ID_despesa;
        Despesa despesa = cp.mostrarDespesas(grupoSelecionado);
        Vista.tabelaDespesas(despesa);
        System.out.print("ID: ");
        ID_despesa = scanner.nextLine();
        System.out.println("O que não quiser alterar deixar a vazio.");
        System.out.print("Modificar o Valor da despesa: ");
        valor = scanner.nextLine();
        System.out.print("Modificar quem pagou (email): ");
        quemPagou = scanner.nextLine();
        System.out.print("Modificar descrição: ");
        descricao = scanner.nextLine();
        System.out.print("Modificar a data (DD-MM-YYYY): ");
        data = scanner.nextLine();
        cp.editaDespesa(grupoSelecionado, ID_despesa, valor, quemPagou, descricao, data);
    }

    public void eliminarDespesa() {
        String ID_despesa;
        Despesa despesa = cp.mostrarDespesas(grupoSelecionado);
        Vista.tabelaDespesas(despesa);
        System.out.print("ID: ");
        ID_despesa = scanner.nextLine();
        cp.eliminaDespesa(grupoSelecionado, ID_despesa);
    } /*TODO*/

    public void listarDespesas() {
        Despesa despesa = cp.mostrarDespesas(grupoSelecionado);
        Vista.tabelaDespesas(despesa);

    }

    public void exportarCSV() {
        cp.exportaFicheiro(grupoSelecionado);
    }

    public void convitesMenu() {
        String escolha;

        do {
            Vista.menuConvites();
            escolha = scanner.nextLine();
            switch (escolha) {
                case "1":
                    convidarGrupo();
                    break;
                case "2":
                    decidirConvite();
                    break;
                case "3":
                    ListarConvites();
                    break;
                default:
                    if (!escolha.equalsIgnoreCase("4"))
                        System.out.println("Opção Inválida!");
            }
        } while (!escolha.equalsIgnoreCase("4"));
    }

    public void convidarGrupo() {
        String emailDestinatario;
        boolean confirmacao;
        System.out.print("Para quem quer enviar o convite (email):");
        emailDestinatario = scanner.nextLine();
        confirmacao = cp.criarConvite(emailDestinatario, grupoSelecionado);
        if (confirmacao)
            System.out.println("Convite enviado com sucesso");
        else
            System.out.println("Tem de selecionar um grupo primeiro");
    }

    public void decidirConvite() {
        String grupoNome, decisao;
        cp.mostrarConvites();
        System.out.print("\nGRUPO: ");
        grupoNome = scanner.nextLine();
        System.out.println("Decisao: aceitar\\ recusar");
        System.out.print("Decisao: ");
        decisao = scanner.nextLine();
        cp.respondeConvite(grupoNome, decisao);
    }

    public void ListarConvites() {
        Convites convites = cp.mostrarConvites();
        Vista.tabelaConvites(convites);
    }

    public void pagamentosMenu() {
        String escolha;

        do {
            Vista.menuPagamentos();
            escolha = scanner.nextLine();
            switch (escolha) {
                case "1":
                    efectuarPagamento();
                    break;
                case "2":
                    eliminarPagamento();
                    break;
                case "3":
                    listarPagamentos();
                    break;
                default:
                    if (!escolha.equalsIgnoreCase("4"))
                        System.out.println("Opção Inválida!");
            }
        } while (!escolha.equalsIgnoreCase("4"));
    }

    public void efectuarPagamento() {

        String recebidoPor, data;
        double valor;
        System.out.print("\nRecebido por (email): ");
        recebidoPor = scanner.nextLine();
        System.out.print("\nValor do pagamento: ");
        valor = Double.parseDouble(scanner.nextLine());
        System.out.print("\nData do pagamento (DD-MM-YYYY): ");
        data = scanner.nextLine();
        cp.efectuaPagamento(grupoSelecionado/*, pagaPor*/, recebidoPor, valor, data);
    }

    public void eliminarPagamento() {
        System.out.print("\nPara eliminar um pagamento, forneça o ID do pagamento:\n");
        String grupoSelecionado = this.grupoSelecionado;
        System.out.print("Valor do pagamento: ");
        double valor;
        try {
            valor = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Por favor, insira um número.");
            return;
        }
        System.out.print("Recebido por: ");
        String recebidoPor = scanner.nextLine();


        try {
            cp.eliminaPagamento(grupoSelecionado/*data*/, valor/*, pagaPor*/, recebidoPor);
            System.out.println("Pagamento eliminado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao eliminar pagamento: " + e.getMessage());
        }
    }

    public void listarPagamentos() {
        Pagamento pagamentos = cp.listarPagamento(grupoSelecionado);
        Vista.tabelaPagamentos(pagamentos);

    }

}


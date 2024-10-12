package pt.isec.pd.servidores;
import pt.isec.pd.db.Bd;
import pt.isec.pd.servidores.threads.ClienteThread;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.sql.*;

import static java.lang.Integer.parseInt;

public class ServidorPrincipal {

    static ServerSocket socketServidor;
    private static void mostraServidorDados(int serverPort,String port){
        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.print("+");
            for (int i = 0; i < ip.toString().length() ; i++)
                System.out.print("-");
            System.out.print("+");
            for (int i = 0; i < ("  PORT  |").length() - 1; i++)
                System.out.print("-");
            System.out.print("+");
            System.out.print("\n|   Servidor IP");
            for (int i = 0; i < ip.toString().length() - ("   Servidor IP").length() ; i++)
                System.out.print(" ");
            System.out.print("|");
            System.out.print("  PORT  |");
            System.out.print("\n+");
            for (int i = 0; i < ip.toString().length() ; i++)
                System.out.print("-");
            System.out.print("+");
            for (int i = 0; i < ("  PORT  |").length() - 1; i++)
                System.out.print("-");
            System.out.print("+");
            System.out.print("\n|"+ip +"|  "+ serverPort);
            for (int i = 0 ; i < (("  PORT  |").length() - 1) -(port.length() + 2) ; i++)
                System.out.print(" ");
            System.out.print("|\n");
            System.out.print("+");
            for (int i = 0; i < ip.toString().length() ; i++)
                System.out.print("-");
            System.out.print("+");
            for (int i = 0; i < ("  PORT  |").length() - 1; i++)
                System.out.print("-");
            System.out.print("+\n");


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws SQLException {

        int argc = args.length;
        int serverPort;

        if (argc != 4){
            System.out.println("java ServidorPrincipal PORT DATA_BASE_FILE");
            return;
        }

        serverPort = parseInt(args[0]);
        mostraServidorDados(serverPort,args[0]);



        //Login login = new Login("a21220079@isec.pt","1234");
        //ControladorAutenticacao.login(login); //controlador do sistema de autenticação
        //Registo registo = new Registo("batatas@gmail.com","batatas-fritas",124321021,"Batatas Friend");
        //ControladorAutenticacao.registo(registo); //controlador do sistema de autenticação
        //EditaConta edita = new EditaConta("batatas@gmail.com", "nopotatoes4u",null);
        //ControladorAutenticacao.edita(edita); //controlador do sistema de autenticação
        //Bd.getUserDB("'a21220079@isec.pt'", "1234"); //test
        //Bd.setUserDB("Maria Jaquelina", 159753123,"maria@jaque.com", "jaquelina2");
        //Bd.ligaBD(args[1]);
        //Bd.editaUserBD(123459876,"maria@jaque.com", "jaquelina2");    //test edita db
        //Bd.editaUserBD(1234,"maria@jaque.com", null);                 //test edita db
        //Bd.editaUserBD(null,"maria@jaque.com", "jaquelina5");         //test edita db
        //Bd.editaUserBD(999999999,"maria@jaque.com", "omgisjaquelina");//test edita db


        /*Ligacao lig = new Ligacao();*/
        int i = 0;


        try {
            socketServidor = new ServerSocket(serverPort); //cria o socket para o servidor
            System.out.println("O Servidor Principal foi inciado com sucesso!");
            Bd.ligaBD(args[1]); // ligar à base de dados
            while (true) {
                    if(Bd.isEstaConectado()){ //usar em threads
                        System.out.println(i);
                        //cria uma thread e atribui ao cliente que acabou de entrar
                        new ClienteThread(socketServidor.accept()).start();
                        System.out.println("Entrou um cliente!");
                        i++;
                    } else {
                        break;
                    }
            }

        } catch (IOException e) {
            System.out.println("Erro ao criar o socket.\n[ERRO]: " + e);
        }


    }
}

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
        Thread t;
        if (argc != 2){
            System.out.println("java ServidorPrincipal PORT DATA_BASE_FILE");
            return;
        }
        serverPort = parseInt(args[0]);
        mostraServidorDados(serverPort,args[0]);
        int i = 0;
        try {
            socketServidor = new ServerSocket(serverPort);//cria o socket para o servidor
            System.out.println("O Servidor Principal foi inciado com sucesso!");
            Bd.ligaBD(args[1]); // ligar à base de dados
            while (true) {
                    if(Bd.isEstaConectado()){ //usar em threads
                        if (i!=0)
                            System.out.println(i);
                        //cria uma thread e atribui ao cliente que acabou de entrar
                        t = new ClienteThread(socketServidor.accept());
                        t.start();
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

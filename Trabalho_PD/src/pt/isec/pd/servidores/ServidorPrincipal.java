package pt.isec.pd.servidores;
import java.awt.desktop.SystemEventListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Integer.parseInt;

public class ServidorPrincipal {
    public static void main(String[] args) {

        int argc = args.length;
        int serverPort;
        //System.out.println("Recebi: " + argc + " argumentos");

        if (argc != 2){
            System.out.println("java ServidorPrincipal PORT DATA_BASE_FILE");
        }
        System.out.println("");
        System.out.println("----RECEBI----");
        System.out.println("PORT: " + args[0]);
        System.out.println("DB: " + args[1]);
        System.out.println("--------------");

        serverPort = Integer.parseInt(args[0]);


        try{
            ServerSocket socketServidor = new ServerSocket(serverPort);
            System.out.println("O Servidor Principal foi inciado com sucesso!");
            while (true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

package pt.isec.pd.servidores;
import java.awt.desktop.SystemEventListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Integer.parseInt;

public class ServidorPrincipal {
    public static void main(String[] args) {

        int argc = args.length;
        int serverPort;
        String msg = null;
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


        try (ServerSocket socketServidor = new ServerSocket(serverPort)){

            System.out.println("O Servidor Principal foi inciado com sucesso!");
            while (true) {
                Socket socketCliente = socketServidor.accept();
                System.out.println("Entrou um cliente!");
                BufferedReader reader = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                msg = reader.readLine();
                System.out.println("Recebi do Cliente: " + msg);
                socketCliente.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

package pt.isec.pd.servidores;
import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.Mensagem;
import pt.isec.pd.comum.modelos.EditaConta;
import pt.isec.pd.comum.modelos.Login;
import pt.isec.pd.comum.modelos.Registo;
import pt.isec.pd.db.Bd;
import pt.isec.pd.servidores.controladores.ControladorAutenticacao;

import java.awt.desktop.SystemEventListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

import static java.lang.Integer.parseInt;

public class ServidorPrincipal {

    public static void main(String[] args) throws SQLException {

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
        //teste de ligar à BD
        //Bd.ligaBD(args[1]); // ligar à bd
        Login login = new Login("a21220079@isec.pt","1234");
        ControladorAutenticacao.login(login); //controlador do sistema de autenticação
        //Registo registo = new Registo("batatas@gmail.com","batatas-fritas",124321021,"Batatas Friend");
        //ControladorAutenticacao.registo(registo); //controlador do sistema de autenticação
        EditaConta edita = new EditaConta("batatas@gmail.com", "nopotatoes4u",null);
        ControladorAutenticacao.edita(edita); //controlador do sistema de autenticação
        //Bd.getUserDB("'a21220079@isec.pt'", "1234"); //test
        //Bd.setUserDB("Maria Jaquelina", 159753123,"maria@jaque.com", "jaquelina2");
        //Bd.editaUserBD(123459876,"maria@jaque.com", "jaquelina2");    //test edita db
        //Bd.editaUserBD(1234,"maria@jaque.com", null);                 //test edita db
        //Bd.editaUserBD(null,"maria@jaque.com", "jaquelina5");         //test edita db
        //Bd.editaUserBD(999999999,"maria@jaque.com", "omgisjaquelina");//test edita db


        Ligacao lig = new Ligacao();
        System.out.println("Antes do try!");


        try (ServerSocket socketServidor = new ServerSocket(serverPort)){
            System.out.println("depois do try!");
            System.out.println("O Servidor Principal foi inciado com sucesso!");
            while (true) {
                try(Socket socketCliente = socketServidor.accept()){

                    System.out.println("Entrou um cliente!");

                    System.out.println(lig.recebeMensagem(socketCliente).toString());
                    socketCliente.close();
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}

package pt.isec.pd.cliente.ligacao;

import pt.isec.pd.comum.Mensagem;
import pt.isec.pd.comum.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Ligacao {

    static InetAddress servidorEndereco;
    static int servidorPorto;
    private Socket socket = null;

    public Ligacao(String endereco, String porto){
        try {
            servidorEndereco = InetAddress.getByName(endereco);
            servidorPorto = Integer.parseInt(porto);
            //Cria o Socket para ligar ao Servidor Principal
            socket = new Socket(servidorEndereco,servidorPorto);
            System.out.println("Ligacao efectuada com sucesso!");
            socket.setSoTimeout(10 * 1000);

        } catch (UnknownHostException e) {
            System.out.println("Destino desconhecido:\n\t" + e);
        } catch (IOException e) {
            System.out.println("Ocorreu um erro no acesso ao socket:\n\t" + e);
        }
    }
    public Ligacao(){

    }
    //metodo que recebe o socket e a serialização do conteudo do objecto
    public void enviaMensagem(Socket socket, Serializable mensagem){
        try {
            ObjectOutputStream out;
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(mensagem); //colocar o objecto aqui serlizado a enviar
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public Socket getSocket(){
        return socket;
    }
    //método que recebe o socket e a serialização do conteudo da mensagem
    //rever isto, porque não estou a fazer a parte da serealizaçao
    //ver bem isto depois..
    public Mensagem recebeMensagem(Socket socket){

        try{
            ObjectInputStream in;
            in = new ObjectInputStream(socket.getInputStream());

            return (Mensagem) in.readObject();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}

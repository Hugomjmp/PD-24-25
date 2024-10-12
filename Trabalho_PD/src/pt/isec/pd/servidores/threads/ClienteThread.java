package pt.isec.pd.servidores.threads;

import pt.isec.pd.comum.RespostaServidorMensagem;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.servidores.controladores.ControladorMensagem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteThread extends Thread{
    Socket socketCliente;

    public ClienteThread(Socket socketCliente){
        this.socketCliente = socketCliente;
    }

    @Override
    public void run(){
        try{
            while (true){
                ObjectInputStream objectInputStream = new ObjectInputStream(socketCliente.getInputStream());
                Mensagem mensagem = (Mensagem) objectInputStream.readObject();
                ;

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketCliente.getOutputStream());
                objectOutputStream.writeObject(ControladorMensagem.respostaServidor(mensagem));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

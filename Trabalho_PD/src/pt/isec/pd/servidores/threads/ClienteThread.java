package pt.isec.pd.servidores.threads;

import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.RespostaServidorMensagem;
import pt.isec.pd.servidores.controladores.ControladorMensagemServidor;

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
                ObjectInputStream oin = new ObjectInputStream(socketCliente.getInputStream());
                Mensagem mensagem = (Mensagem) oin.readObject();
                System.out.println("Servidor Recebe -> " + mensagem);
                RespostaServidorMensagem resposta = ControladorMensagemServidor.respostaServidor(mensagem);

                ObjectOutputStream oout = new ObjectOutputStream(socketCliente.getOutputStream());
                oout.writeObject(resposta);
                oout.flush();
                System.out.println("Servidor Envia -> " + resposta);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

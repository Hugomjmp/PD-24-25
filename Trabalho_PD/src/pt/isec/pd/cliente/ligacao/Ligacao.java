package pt.isec.pd.cliente.ligacao;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
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
    public void enviaMensagem(){ // para teste
        try {
            OutputStream out = socket.getOutputStream();
            PrintStream pout = new PrintStream(out);
            pout.println("ENTREI :)");
            pout.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

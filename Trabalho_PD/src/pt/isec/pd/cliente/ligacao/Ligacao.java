package pt.isec.pd.cliente.ligacao;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Ligacao {

    static InetAddress servidorEndereco;
    static int servidorPorto;


    public Ligacao(String endereco, String porto){
        try {
            servidorEndereco = InetAddress.getByName(endereco);
            servidorPorto = Integer.parseInt(porto);
            //Cria o Socket para ligar ao Servidor Principal
            Socket socket = new Socket(servidorEndereco,servidorPorto);
            System.out.println("Ligacao efectuada com sucesso!");
            socket.setSoTimeout(10 * 1000);

        } catch (UnknownHostException e) {
            System.out.println("Destino desconhecido:\n\t" + e);
        } catch (IOException e) {
            System.out.println("Ocorreu um erro no acesso ao socket:\n\t" + e);
        }
    }
}

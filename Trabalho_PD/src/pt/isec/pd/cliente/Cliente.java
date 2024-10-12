package pt.isec.pd.cliente;

import pt.isec.pd.cliente.controladores.ControladorPrincipal;
import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.cliente.vistas.Vista;
import pt.isec.pd.comum.User;
import pt.isec.pd.comum.modelos.Login;
import pt.isec.pd.servidores.controladores.ControladorAutenticacao;

import java.util.Scanner;

public class Cliente {




    public static void main(String[] args) {
        int argc = args.length;

        if (argc != 2){
            System.out.println("java Cliente IPAdress PORT");
        }
        Ligacao ligacao = new Ligacao(args[0],args[1]);
        ControladorPrincipal controladorPrincipal = new ControladorPrincipal(ligacao);
        //Vista vista = new Vista(controladorPrincipal);
        controladorPrincipal.main();



    }
}

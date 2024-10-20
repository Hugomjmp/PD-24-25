package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.enumeracoes.Tipomensagemenum;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.InserePagamento;

public class ControladorPagamentoCliente {

    public static void inserirPagamento(Ligacao ligacao, String pagaPor, String recebidoPor, String grupoNome, double valor, String dataPagamentoStr) {

        if (valor <= 0) {
            System.out.println("Valor deve ser positivo.");
            return;
        }
        InserePagamento pagamento = new InserePagamento(grupoNome, pagaPor, recebidoPor, valor);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_INSERE_PAGAMENTO, pagamento);
        ligacao.enviaMensagem(mensagem);
        System.out.println("Pagamento enviado: " + mensagem);
    }
}

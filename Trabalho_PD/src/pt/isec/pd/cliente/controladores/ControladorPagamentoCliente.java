package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.enumeracoes.Tipomensagemenum;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.mensagens.EliminaPagamento;
import pt.isec.pd.comum.modelos.mensagens.InserePagamento;

public class ControladorPagamentoCliente {

    public static void inserirPagamento(Ligacao ligacao, String emailLogado, String grupoSelecionado, String pagaPor, String recebidoPor, double valor, String data) {

        if (valor <= 0) {
            System.out.println("Valor deve ser positivo.");
            return;
        }
        InserePagamento pagamento = new InserePagamento(grupoSelecionado, pagaPor, recebidoPor, valor, data);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_INSERE_PAGAMENTO, pagamento);
        ligacao.enviaMensagem(mensagem);
        System.out.println("Pagamento enviado: " + mensagem);
    }

    public static void eliminaPagamento(Ligacao ligacao, String emailLogado, String grupoSelecionado, String paymentId) {
        if (paymentId == null || paymentId.isEmpty()) {
            System.out.println("ID do pagamento deve ser fornecido.");
            return;
        }
        EliminaPagamento elpagamento = new EliminaPagamento(paymentId, grupoSelecionado);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_ELIMINA_PAGAMENTO_EFECTUADO, elpagamento);
        ligacao.enviaMensagem(mensagem);
        System.out.println("Pagamento eliminado: " + mensagem);
    }

}

package pt.isec.pd.cliente.controladores;

import pt.isec.pd.cliente.ligacao.Ligacao;
import pt.isec.pd.comum.enumeracoes.Tipomensagemenum;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.Pagamento;
import pt.isec.pd.comum.modelos.mensagens.EliminaPagamento;
import pt.isec.pd.comum.modelos.mensagens.InserePagamento;
import pt.isec.pd.comum.modelos.mensagens.ListarPagamentos;
import pt.isec.pd.db.Bd;

import java.util.List;

public class ControladorPagamentoCliente {

    public static void inserirPagamento(Ligacao ligacao, String emailLogado, String grupoSelecionado/*, String pagaPor*/, String recebidoPor, double valor, String data) {

        if (valor <= 0) {
            System.out.println("Valor deve ser positivo.");
            return;
        }
        InserePagamento pagamento = new InserePagamento(grupoSelecionado, emailLogado, recebidoPor, valor, data);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_INSERE_PAGAMENTO, pagamento);
        ligacao.enviaMensagem(mensagem);
        System.out.println("Pagamento enviado: " + mensagem);
    }

    public static void eliminaPagamento(Ligacao ligacao, String emailLogado, String grupoSelecionado/*, String data*/, double valor/*, String pagaPor*/, String recebidoPor) {

        if (grupoSelecionado == null || grupoSelecionado.isEmpty()) {
            System.out.println("ID do grupo deve ser fornecido.");
            return;
        }

        EliminaPagamento elpagamento = new EliminaPagamento(grupoSelecionado/*, data*/, valor, emailLogado, recebidoPor);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_ELIMINA_PAGAMENTO, elpagamento);
        ligacao.enviaMensagem(mensagem);
        System.out.println("Pedido de eliminação de pagamento enviado: " + mensagem);
    }


    public static void listarPagamento(Ligacao ligacao, String emailLogado, String grupoSelecionado) {

        ListarPagamentos listarPagamentos = new ListarPagamentos(grupoSelecionado, emailLogado);
        Mensagem mensagem = new Mensagem(Tipomensagemenum.USER_LISTA_PAGAMENTOS, listarPagamentos);
        ligacao.enviaMensagem(mensagem);

    }


}


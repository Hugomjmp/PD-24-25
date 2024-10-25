package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.enumeracoes.Tipomensagemenum;
import pt.isec.pd.comum.modelos.Mensagem;
import pt.isec.pd.comum.modelos.Pagamento;
import pt.isec.pd.comum.modelos.RespostaServidorMensagem;
import pt.isec.pd.comum.modelos.mensagens.EliminaPagamento;
import pt.isec.pd.comum.modelos.mensagens.InserePagamento;
import pt.isec.pd.comum.modelos.mensagens.ListarGrupo;
import pt.isec.pd.comum.modelos.mensagens.ListarPagamentos;
import pt.isec.pd.db.Bd;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ControladorPagamentoServidor {

    public static Estados InserirPagamento(InserePagamento inserePagamento) {
        try {
            Estados estado = Bd.inserirPagamento(
                    inserePagamento.getGroupId(),
                    inserePagamento.getPagaPor(),
                    inserePagamento.getRecebidoPor(),
                    inserePagamento.getValor(),
                    inserePagamento.getData()
            );

            return estado;
        } catch (Exception e) {
            System.err.println("Erro ao inserir pagamento: " + e.getMessage());
            return Estados.ERRO_INSERIR_PAGAMENTO;
        }
    }
    public static Estados eliminarPagamento(EliminaPagamento eliminarPagamento) {
        try {
            Estados estado = Bd.eliminarPagamento(
                    eliminarPagamento.getGroupId(),
                    eliminarPagamento.getData(),
                    eliminarPagamento.getValor(),
                    eliminarPagamento.getPagaPor(),
                    eliminarPagamento.getRecebidoPor()
            );

            return estado;
        } catch (Exception e) {
            System.err.println("Erro ao eliminar pagamento: " + e.getMessage());
            return Estados.ERRO_ELIMINAR_PAGAMENTO;
        }
    }

    public static Estados ListarPagamentos(ListarPagamentos listarPagamentos) {
        try {
            List<Pagamento> pagamentos = Bd.listarPagamentosDB(listarPagamentos.getSolicitadoPor());
            return pagamentos.isEmpty() ? Estados.ERRO_SEM_PAGAMENTOS : Estados.PAGAMENTO_LISTADO_COM_SUCESSO.setDados((Serializable) pagamentos);
        } catch (Exception e) {
            System.err.println("Erro ao listar pagamentos: " + e.getMessage());
            return Estados.ERRO_LISTAR_PAGAMENTO;
        }
    }
}

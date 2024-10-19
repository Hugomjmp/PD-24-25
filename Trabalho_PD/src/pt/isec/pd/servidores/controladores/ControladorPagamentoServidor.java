package pt.isec.pd.servidores.controladores;

import pt.isec.pd.comum.enumeracoes.Estados;
import pt.isec.pd.comum.modelos.mensagens.InserePagamento;
import pt.isec.pd.db.Bd;
import java.io.Serializable;

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
}

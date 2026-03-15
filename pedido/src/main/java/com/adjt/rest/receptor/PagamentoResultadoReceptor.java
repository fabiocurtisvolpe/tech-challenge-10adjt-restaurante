package com.adjt.rest.receptor;

import com.adjt.core.usecase.AtualizarPedidoUseCase;
import com.adjt.rest.event.PagamentoResultadoEvent;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class PagamentoResultadoReceptor {

    private static final Logger LOG = Logger.getLogger(PagamentoResultadoReceptor.class);

    private final AtualizarPedidoUseCase atualizarPedidoUseCase;

    @Inject
    public PagamentoResultadoReceptor(AtualizarPedidoUseCase atualizarPedidoUseCase) {
        this.atualizarPedidoUseCase = atualizarPedidoUseCase;
    }

    @Incoming("pagamento-resultado")
    public void processarResultado(JsonObject payload) {
        PagamentoResultadoEvent evento = payload.mapTo(PagamentoResultadoEvent.class);

        LOG.infof("Recebido resultado do pagamento para o pedido %d. Aprovado: %s",
                evento.pedidoId(), evento.aprovado());

        atualizarPedidoUseCase.run(evento.pedidoId(), evento.statusCode());

        LOG.infof("Status do pedido %d atualizado para %d", evento.pedidoId(), evento.statusCode());
    }
}

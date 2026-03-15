package com.adjt.pagamento.infra.messaging;

import com.adjt.pagamento.core.model.StatusPagamento;
import com.adjt.pagamento.core.usecase.ProcessarPagamentoUseCase;
import com.adjt.pagamento.infra.event.PedidoCriadoEvent;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class PedidoReceptor {

    private final ProcessarPagamentoUseCase useCase;

    public PedidoReceptor(ProcessarPagamentoUseCase useCase) {
        this.useCase = useCase;
    }

    @Incoming("pedido-recebido")
    @Outgoing("pagamento-finalizado")
    public Uni<JsonObject> consumirPedido(JsonObject payload) {
        PedidoCriadoEvent evento = payload.mapTo(PedidoCriadoEvent.class);
        Log.infof("Mensagem recebida do Pedido: %d", evento.pedidoId());

        return useCase.run(evento.valorTotal().longValue(),
                        evento.pedidoId().toString(),
                        evento.clienteId().toString())
                .map(resp -> {
                    StatusPagamento status = StatusPagamento.fromHttpStatus(resp.getStatus());
                    return new JsonObject()
                            .put("pedidoId", evento.pedidoId())
                            .put("statusCode", status.getHttpStatus())
                            .put("aprovado", status.isAprovado())
                            .put("status", status.name());
                });
    }
}

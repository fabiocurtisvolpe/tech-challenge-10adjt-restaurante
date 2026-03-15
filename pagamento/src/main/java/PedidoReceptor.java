import event.PedidoCriadoEvent;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import restclient.PagamentoService;

@ApplicationScoped
public class PedidoReceptor {

    private final PagamentoService pagamentoService;

    public PedidoReceptor(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @Incoming("pedido-recebido")
    @Outgoing("pagamento-finalizado")
    public Uni<JsonObject> consumirPedido(JsonObject payload) {
        PedidoCriadoEvent evento = payload.mapTo(PedidoCriadoEvent.class);
        Log.infof("Mensagem recebida do Pedido: %d", evento.pedidoId());

        return pagamentoService.processarPagamento(
                evento.valorTotal().longValue(),
                evento.pedidoId().toString(),
                evento.clienteId().toString()
        ).map(resp -> new JsonObject()
                .put("pedidoId", evento.pedidoId())
                .put("statusCode", resp.getStatus())
                .put("aprovado", resp.getStatus() == 201)
        );
    }
}

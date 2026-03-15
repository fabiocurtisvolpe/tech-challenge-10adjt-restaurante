package restclient;

import dto.RequisicaoPagamentoDTO;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import jakarta.ws.rs.core.Response;
@ApplicationScoped
public class PagamentoService {

    private static final Logger LOG = Logger.getLogger(PagamentoService.class);

    @Inject
    @RestClient
    PagamentoRestClient restClient;

    @Retry(maxRetries = 5, delay = 1000)
    @CircuitBreaker(
            requestVolumeThreshold = 4,
            delay = 5000
    )
    @Fallback(fallbackMethod = "fallbackPagamento")
    public Uni<Response> processarPagamento(Long valor, String pedidoId, String clienteId) {
        LOG.infof("Tentando processar pagamento para o pedido: %s", pedidoId);
        RequisicaoPagamentoDTO dto = new RequisicaoPagamentoDTO(valor, pedidoId, clienteId);

        return restClient.enviarPagamento(dto)
                .onItem().invoke(resp -> LOG.infof("Resposta do pagamento: %d", resp.getStatus()))
                .onItem().transformToUni(resp -> {
                    if (resp.getStatus() != 201) {
                        return Uni.createFrom().failure(
                                new RuntimeException("Serviço de pagamento retornou: " + resp.getStatus())
                        );
                    }
                    return Uni.createFrom().item(resp);
                });
    }

    public Uni<Response> fallbackPagamento(Long valor, String pedidoId, String clienteId) {
        LOG.errorf("#### FALLBACK ATIVADO #### Serviço indisponível para o pedido %s.", pedidoId);
        return Uni.createFrom().item(Response.status(503).entity("Pagamento movido para processamento assíncrono (Pendente)").build());
    }
}

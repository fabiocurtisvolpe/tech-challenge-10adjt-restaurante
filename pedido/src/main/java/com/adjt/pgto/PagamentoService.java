package com.adjt.pgto;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import jakarta.ws.rs.core.Response;
import com.adjt.pgto.dto.RequisicaoPagamentoDTO;

@ApplicationScoped
public class PagamentoService {

    private static final Logger LOG = Logger.getLogger(PagamentoService.class);

    @Inject
    @RestClient
    PagamentoRestClient restClient;

    @Retry(maxRetries = 5, delay = 1000) // Tenta 5 vezes com 1s de intervalo
    @CircuitBreaker(
            requestVolumeThreshold = 4, // Analisa as últimas 4 chamadas
            failureRatio = 0.5,         // Se 50% falharem, abre o circuito
            delay = 5000                // Mantém o circuito aberto por 5 segundos
    )
    @Fallback(fallbackMethod = "fallbackPagamento")
    public Uni<Response> processarPagamento(Long valor, String pedidoId, String clienteId) {
        LOG.infof("Tentando processar pagamento para o pedido: %s", pedidoId);

        RequisicaoPagamentoDTO dto = new RequisicaoPagamentoDTO(valor, pedidoId, clienteId);

        return restClient.enviarPagamento(dto)
                .onItem().invoke(resp -> LOG.infof("Resposta do pagamento: %d", resp.getStatus()));
    }

    // Método de escape caso o serviço de pagamento esteja fora do ar
    public Uni<Response> fallbackPagamento(Long valor, String pedidoId, String clienteId) {
        LOG.errorf("Serviço de pagamento indisponível para o pedido %s. Aplicando lógica de contingência.", pedidoId);
        return Uni.createFrom().item(Response.status(503).entity("Pagamento em processamento tardio").build());
    }
}

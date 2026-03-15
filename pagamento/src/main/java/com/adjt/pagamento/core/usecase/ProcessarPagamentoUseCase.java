package com.adjt.pagamento.core.usecase;

import com.adjt.pagamento.core.port.GatewayPagamentoPort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;

@ApplicationScoped
public class ProcessarPagamentoUseCase {

    private final GatewayPagamentoPort gateway;

    public ProcessarPagamentoUseCase(GatewayPagamentoPort gateway) {
        this.gateway = gateway;
    }

    @Retry(maxRetries = 5, delay = 1000)
    @CircuitBreaker(requestVolumeThreshold = 4, delay = 5000)
    @Fallback(fallbackMethod = "fallback")
    public Uni<Response> run(Long valor, String pedidoId, String clienteId) {
        return gateway.enviar(valor, pedidoId, clienteId);
    }

    public Uni<Response> fallback(Long valor, String pedidoId, String clienteId) {
        return Uni.createFrom().item(Response.status(503).build());
    }
}

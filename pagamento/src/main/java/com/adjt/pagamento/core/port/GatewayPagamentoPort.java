package com.adjt.pagamento.core.port;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public interface GatewayPagamentoPort {
    Uni<Response> enviar(Long valor, String pedidoId, String clienteId);
}

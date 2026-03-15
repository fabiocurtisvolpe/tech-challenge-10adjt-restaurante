package com.adjt.pagamento.infra.gateway;

import com.adjt.pagamento.core.port.GatewayPagamentoPort;
import com.adjt.pagamento.infra.gateway.client.PagamentoRestClient;
import com.adjt.pagamento.infra.gateway.dto.RequisicaoPagamentoDTO;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class PagamentoGateway implements GatewayPagamentoPort {

    @Inject
    @RestClient
    PagamentoRestClient restClient;

    @Override
    public Uni<Response> enviar(Long valor, String pedidoId, String clienteId) {
        return restClient.enviarPagamento(new RequisicaoPagamentoDTO(valor, pedidoId, clienteId));
    }
}

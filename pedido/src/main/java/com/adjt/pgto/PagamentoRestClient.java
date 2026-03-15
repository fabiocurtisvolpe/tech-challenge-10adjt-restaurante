package com.adjt.pgto;

import com.adjt.pgto.dto.RequisicaoPagamentoDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "pagamento-api")
@Path("/requisicao")
public interface PagamentoRestClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<Response> enviarPagamento(RequisicaoPagamentoDTO request);

    @GET
    @Path("/{pagamento_id}")
    Uni<Response> consultarStatus(@PathParam("pagamento_id") String pagamentoId);
}

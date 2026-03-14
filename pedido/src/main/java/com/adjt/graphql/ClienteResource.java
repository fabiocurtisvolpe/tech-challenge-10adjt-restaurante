package com.adjt.graphql;

import com.adjt.core.model.ClienteInfo;
import com.adjt.gprc.ClienteGrpcUseCase;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
@RolesAllowed("ROLE_CLIENTE")
public class ClienteResource {

    private final ClienteGrpcUseCase clienteGrpcUseCase;

    public ClienteResource(ClienteGrpcUseCase clienteGrpcUseCase) {
        this.clienteGrpcUseCase = clienteGrpcUseCase;
    }

    @Query("buscarCliente")
    @Description("Busca os detalhes de um cliente específico")
    public Uni<ClienteInfo> buscarCliente(@Name("id") Long id) {
        return clienteGrpcUseCase.buscarClientePorId(id);
    }
}

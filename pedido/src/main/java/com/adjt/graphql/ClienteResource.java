package com.adjt.graphql;

import com.adjt.core.model.ClienteInfo;
import com.adjt.gprc.ClienteGrpcUseCase;
import io.smallrye.common.annotation.Blocking;
import jakarta.annotation.security.RolesAllowed;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
@RolesAllowed("ROLE_CLIENTE")
public class ClienteResource {

    private final ClienteGrpcUseCase clienteGrpcUseCase;

    public ClienteResource(ClienteGrpcUseCase clienteGrpcUseCase) {
        this.clienteGrpcUseCase = clienteGrpcUseCase;
    }

    @Query("buscarCliente")
    @Blocking
    public ClienteInfo buscarCliente(Long id) {
        return clienteGrpcUseCase.buscarClientePorId(id)
                .await().indefinitely();
    }

    @Mutation("atualizarCliente")
    @Blocking
    public ClienteInfo atualizarCliente(ClienteInfo clienteInfo) {
        return clienteGrpcUseCase.atualizarCliente(clienteInfo)
                .await().indefinitely();
    }
}

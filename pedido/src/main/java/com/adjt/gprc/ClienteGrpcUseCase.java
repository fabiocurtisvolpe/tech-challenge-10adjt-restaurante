package com.adjt.gprc;

import com.adjt.cliente.ClienteIdRequest;
import com.adjt.cliente.ClienteService;
import com.adjt.core.model.ClienteInfo;
import com.adjt.gprc.mapper.ClientePedidoMapper;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteGrpcUseCase {

    @GrpcClient("cliente")
    ClienteService clienteService;

    public Uni<ClienteInfo> buscarClientePorId(Long id) {
        ClienteIdRequest request = ClienteIdRequest.newBuilder()
                .setId(id)
                .build();

        return clienteService.buscarPorId(request)
                .map(ClientePedidoMapper::toClienteInfo);
    }
}

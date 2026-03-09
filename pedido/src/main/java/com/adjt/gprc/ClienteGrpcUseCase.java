package com.adjt.gprc;

import com.adjt.cliente.ClienteIdRequest;
import com.adjt.cliente.ClienteUpdateRequest;
import com.adjt.cliente.ClienteService; // ← interface gerada pelo Quarkus
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

    public Uni<ClienteInfo> atualizarCliente(ClienteInfo clienteInfo) {
        ClienteUpdateRequest request = ClienteUpdateRequest.newBuilder()
                .setId(clienteInfo.getId())
                .setNome(clienteInfo.getNome())
                .setCpf(clienteInfo.getCpf())
                .setEmail(clienteInfo.getEmail())
                .build();

        return clienteService.atualizar(request)
                .map(ClientePedidoMapper::toClienteInfo);
    }
}

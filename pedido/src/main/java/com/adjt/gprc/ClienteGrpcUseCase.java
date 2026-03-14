package com.adjt.gprc;

import com.adjt.cliente.ClienteIdRequest;
import com.adjt.cliente.ClienteService;
import com.adjt.cliente.ValidarUsuarioRequest;
import com.adjt.core.model.ClienteInfo;
import com.adjt.gprc.mapper.ClientePedidoMapper;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

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

    public Uni<Boolean> validarUsuarioLogado(UUID uid, Long id) {
        ValidarUsuarioRequest req = ValidarUsuarioRequest.newBuilder()
                .setUid(uid.toString())
                .setId(id)
                .build();

        return clienteService.validarUsuarioLogado(req)
                .map(res -> res.getValido());
    }
}

package com.adjt.gprc;

import com.adjt.cliente.Cliente;
import com.adjt.cliente.ClienteIdRequest;
import com.adjt.cliente.ClienteUpdateRequest;
import com.adjt.cliente.MutinyClienteServiceGrpc;
import com.adjt.core.usecase.cliente.AtualizarClienteUseCase;
import com.adjt.core.usecase.cliente.ObterPorIdClienteUseCase;
import com.adjt.gprc.mapper.ClienteGrpcMapper;
import io.grpc.Status;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;


@GrpcService
public class ClienteGprcService extends MutinyClienteServiceGrpc.ClienteServiceImplBase {

    private final ObterPorIdClienteUseCase obterPorIdClienteUseCase;
    private final AtualizarClienteUseCase atualizarClienteUseCase;

    public ClienteGprcService(ObterPorIdClienteUseCase obterPorIdClienteUseCase,
                              AtualizarClienteUseCase atualizarClienteUseCase) {
        this.obterPorIdClienteUseCase = obterPorIdClienteUseCase;
        this.atualizarClienteUseCase = atualizarClienteUseCase;
    }

    @Override
    public Uni<Cliente> buscarPorId(ClienteIdRequest request) {
        return Uni.createFrom()
                .item(() -> obterPorIdClienteUseCase.run(request.getId()))
                .onItem().ifNull().failWith(() ->
                        Status.NOT_FOUND
                                .withDescription("Cliente não encontrado para id: " + request.getId())
                                .asRuntimeException()
                )
                .onItem().ifNotNull().transform(ClienteGrpcMapper::toProto);
    }

    @Override
    public Uni<Cliente> atualizar(ClienteUpdateRequest request) {
        return Uni.createFrom()
                .item(() -> atualizarClienteUseCase.run(ClienteGrpcMapper.toCoreModel(request)))
                .onItem().ifNull().failWith(() ->
                        Status.NOT_FOUND
                                .withDescription("Cliente não encontrado para id: " + request.getId())
                                .asRuntimeException()
                )
                .onItem().ifNotNull().transform(ClienteGrpcMapper::toProto)
                .onFailure(IllegalArgumentException.class).transform(e ->
                        Status.INVALID_ARGUMENT
                                .withDescription(e.getMessage())
                                .asRuntimeException()
                );
    }
}

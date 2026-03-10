package com.adjt.gprc;

import com.adjt.cliente.Cliente;
import com.adjt.cliente.ClienteIdRequest;
import com.adjt.cliente.MutinyClienteServiceGrpc;
import com.adjt.core.exception.NotificacaoException;
import com.adjt.core.usecase.cliente.ObterPorIdClienteUseCase;
import com.adjt.gprc.mapper.ClienteGrpcMapper;
import io.grpc.Status;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;

@GrpcService
public class ClienteGprcService extends MutinyClienteServiceGrpc.ClienteServiceImplBase {

    private final ObterPorIdClienteUseCase obterPorIdClienteUseCase;

    public ClienteGprcService(ObterPorIdClienteUseCase obterPorIdClienteUseCase) {
        this.obterPorIdClienteUseCase = obterPorIdClienteUseCase;
    }

    @Override
    @RunOnVirtualThread
    public Uni<Cliente> buscarPorId(ClienteIdRequest request) {
        return Uni.createFrom()
                .item(() -> obterPorIdClienteUseCase.run(request.getId()))

                .onItem().ifNull().failWith(() ->
                        Status.NOT_FOUND
                                .withDescription("Cliente não encontrado para id: " + request.getId())
                                .asRuntimeException()
                )

                .onFailure(NotificacaoException.class).transform(e -> {
                    // Se for erro de "não encontrado", usamos 404
                    if (e.getMessage().contains("não encontrado")) {
                        return Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException();
                    }
                    // Se for erro de validação de utilizador (‘id’ diferente), usamos PERMISSION_DENIED (403)
                    return Status.PERMISSION_DENIED.withDescription(e.getMessage()).asRuntimeException();
                })

                .onFailure().transform(e -> Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException())
                .onItem().ifNotNull().transform(ClienteGrpcMapper::toProto);
    }
}

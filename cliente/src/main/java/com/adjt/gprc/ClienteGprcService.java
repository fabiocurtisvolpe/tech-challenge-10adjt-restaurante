package com.adjt.gprc;

import com.adjt.cliente.*;
import com.adjt.base.core.exception.NotificacaoException;
import com.adjt.core.usecase.cliente.ObterPorIdClienteUseCase;
import com.adjt.core.util.MensagemUtil;
import com.adjt.data.entity.ClienteEntity;
import com.adjt.data.repository.jpa.ClienteRepository;
import com.adjt.gprc.mapper.ClienteGrpcMapper;
import io.grpc.Status;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

@GrpcService
public class ClienteGprcService extends MutinyClienteServiceGrpc.ClienteServiceImplBase {

    private final ObterPorIdClienteUseCase obterPorIdClienteUseCase;
    private final ClienteRepository repository;

    public ClienteGprcService(ObterPorIdClienteUseCase obterPorIdClienteUseCase,
                              ClienteRepository repositor ) {
        this.obterPorIdClienteUseCase = obterPorIdClienteUseCase;
        this.repository = repositor;
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

    @Override
    @RunOnVirtualThread
    public Uni<ValidarUsuarioResponse> validarUsuarioLogado(ValidarUsuarioRequest request) {

        UUID uuidParaValidar = UUID.fromString(request.getUid());
        Long idParaValidar = request.getId();

        boolean ehValido = realizarSuaLogica(uuidParaValidar, idParaValidar);

        return Uni.createFrom().item(
                ValidarUsuarioResponse.newBuilder()
                        .setValido(ehValido)
                        .build()
        );
    }

    private boolean realizarSuaLogica(UUID uid, Long id) {

        ClienteEntity entityLogada = this.repository.buscarPorKeycloakId(uid)
                .orElseThrow(() -> new NotificacaoException(MensagemUtil.CLIENTE_NAO_ENCONTRADO));

        return entityLogada.id.equals(id);
    }
}

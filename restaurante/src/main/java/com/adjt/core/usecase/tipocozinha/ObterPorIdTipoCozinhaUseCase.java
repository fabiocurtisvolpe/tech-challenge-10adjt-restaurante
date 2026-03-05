package com.adjt.core.usecase.tipocozinha;

import com.adjt.core.model.TipoCozinha;
import com.adjt.core.port.TipoCozinhaPort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObterPorIdTipoCozinhaUseCase {

    private final TipoCozinhaPort<TipoCozinha> tipoCozinhaPort;

    public ObterPorIdTipoCozinhaUseCase(TipoCozinhaPort<TipoCozinha> tipoCozinhaPort) {
        this.tipoCozinhaPort = tipoCozinhaPort;
    }

    public TipoCozinha run(Long id) {
        return tipoCozinhaPort.obterPorId(id);
    }

}

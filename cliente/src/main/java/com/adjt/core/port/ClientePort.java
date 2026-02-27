package com.adjt.core.port;

import java.util.UUID;

public interface ClientePort<Cliente> {

    Cliente criar(Cliente model);
    Cliente atualizar(Cliente model);
    Boolean excluir(Long id);
    Cliente obterPorId(Long id);

    Cliente obterPorCpf(String cpf);
    Cliente obterPorEmail(String email);
}

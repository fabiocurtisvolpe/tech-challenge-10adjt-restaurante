package com.adjt.core.port;

public interface ClientePort<Cliente> {

    Cliente criar(Cliente model);
    Cliente atualizar(Cliente model);
    Boolean excluir(Long id);
    Cliente obterPorId(Long id);

    Cliente obterPorCpf(String cpf);
    Cliente obterPorEmail(String email);
}

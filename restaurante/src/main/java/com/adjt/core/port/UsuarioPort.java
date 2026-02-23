package com.adjt.core.port;

public interface UsuarioPort<Usuario> {

    Usuario criar(Usuario model);
    Usuario atualizar(Usuario model);
    Boolean excluir(Long id);
    Usuario obterPorId(Long id);

    Usuario obterPorCpf(String cpf);
    Usuario obterPorEmail(String email);
}

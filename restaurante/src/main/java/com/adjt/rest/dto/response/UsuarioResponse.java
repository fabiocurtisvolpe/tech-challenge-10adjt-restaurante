package com.adjt.rest.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioResponse {

    public Long id;
    public String nome;
    public String cpf;
    public String email;
    public String senha;
    public LocalDateTime dtCadastro;
}

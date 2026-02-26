package com.adjt.rest.controller;

import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/perfil")
public class UsuarioResource {

    private final JsonWebToken jwt;

    public UsuarioResource(JsonWebToken jwt) {
        this.jwt = jwt;
    }

    @GET
    public String obterDados() {

        String username = jwt.getName();
        String email = jwt.getClaim("email");
        String userId = jwt.getSubject();

        return "Usuário: " + username + " | ID: " + userId + " | E-mail: " + email;
    }
}
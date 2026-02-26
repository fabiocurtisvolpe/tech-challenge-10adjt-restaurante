package com.adjt.rest.controller;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/perfil")
@Authenticated
public class UsuarioResource {

    private final JsonWebToken jwt;

    public UsuarioResource(JsonWebToken jwt) {
        this.jwt = jwt;
    }

    @GET
    public String obterDados() {
        String username = jwt.getClaim("preferred_username");
        String email = jwt.getClaim("email");
        String userId = jwt.getSubject();
        return "Usuário: " + username + " | ID: " + userId + " | E-mail: " + email;
    }
}
package com.adjt.rest.interceptor;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Provider
public class UserInterceptor implements ContainerRequestFilter {

    @Inject
    SecurityIdentity identity;

    @Inject
    UserContext userContext;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (identity.isAnonymous()) {
            return;
        }

        // No Quarkus OIDC, o Principal pode ser convertido para JsonWebToken
        if (identity.getPrincipal() instanceof JsonWebToken jwt) {
            userContext.setEmail(jwt.getClaim("email"));
            userContext.setNome(jwt.getName());

            String sub = jwt.getSubject();
            if (sub != null) {
                userContext.setKeycloakId(java.util.UUID.fromString(sub));
            }
        }
    }
}
package com.adjt.base.gprc.interceptor;

import com.adjt.base.rest.interceptor.UserContext;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.quarkus.grpc.GlobalInterceptor;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

@ApplicationScoped
@GlobalInterceptor
public class GrpcUserInterceptor implements ServerInterceptor {

    @Inject
    SecurityIdentity identity;

    @Inject
    UserContext userContext;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        if (!identity.isAnonymous()) {
            if (identity.getPrincipal() instanceof JsonWebToken jwt) {
                userContext.setEmail(jwt.getClaim("email"));
                userContext.setNome(jwt.getName());
                String sub = jwt.getSubject();
                if (sub != null) {
                    userContext.setKeycloakId(UUID.fromString(sub));
                }
            }
        }

        return next.startCall(call, headers);
    }
}

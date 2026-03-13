package com.adjt.gprc.interceptor;

import io.grpc.*;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
@io.quarkus.grpc.GlobalInterceptor
public class TokenPropagationInterceptor implements ClientInterceptor {

    @Inject
    SecurityIdentity identity;

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> methodDescriptor,
            CallOptions callOptions,
            Channel channel) {

        return new ForwardingClientCall.SimpleForwardingClientCall<>(channel.newCall(methodDescriptor, callOptions)) {

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {

                if (identity != null && identity.getPrincipal() instanceof JsonWebToken jwt) {
                    String rawToken = jwt.getRawToken();

                    if (rawToken != null) {
                        Metadata.Key<String> authKey = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
                        headers.put(authKey, "Bearer " + rawToken);
                    }
                }
                super.start(responseListener, headers);
            }
        };
    }
}

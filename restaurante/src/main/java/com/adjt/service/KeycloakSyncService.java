package com.adjt.service;

import com.adjt.data.repository.jpa.PerfilRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

@ApplicationScoped
public class KeycloakSyncService {

    private static final Logger LOG = Logger.getLogger(KeycloakSyncService.class);

    private final PerfilRepository perfilRepository;

    @ConfigProperty(name = "keycloak.admin.server-url")
    String serverUrl;

    @ConfigProperty(name = "keycloak.admin.realm")
    String adminRealm;

    @ConfigProperty(name = "keycloak.admin.client-id")
    String clientId;

    @ConfigProperty(name = "keycloak.admin.username")
    String username;

    @ConfigProperty(name = "keycloak.admin.password")
    String password;

    @ConfigProperty(name = "keycloak.admin.target-realm")
    String targetRealm;

    public KeycloakSyncService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public void onStart(@Observes StartupEvent ev) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(adminRealm)
                    .clientId(clientId)
                    .username(username)
                    .password(password)
                    .build();

            List<String> perfisDoBanco = perfilRepository.listAll()
                    .stream()
                    .map(p -> p.nome)
                    .toList();

            perfisDoBanco.forEach(roleName -> {
                try {
                    keycloak.realm(targetRealm).roles().create(
                            new RoleRepresentation(roleName, roleName + " role", false)
                    );
                    LOG.infof("Role criada no Keycloak: %s", roleName);
                } catch (WebApplicationException e) {
                    if (e.getResponse().getStatus() == 409) {
                        LOG.infof("Role já existe no Keycloak: %s", roleName);
                    } else {
                        LOG.errorf("Erro ao criar role %s: %s", roleName, e.getMessage());
                    }
                }
            });

            keycloak.close();
        } catch (Exception e) {
            LOG.error("Erro ao sincronizar roles com Keycloak", e);
        }
    }
}

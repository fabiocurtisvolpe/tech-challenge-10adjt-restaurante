package com.adjt.service;

import com.adjt.base.core.exception.NotificacaoException;

import com.adjt.core.model.Cliente;
import com.adjt.data.repository.jpa.PerfilRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jspecify.annotations.NonNull;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    private Keycloak getClient() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(adminRealm)
                .clientId(clientId)
                .username(username)
                .password(password)
                .build();
    }

    public void onStart(@Observes StartupEvent ev) {
        try (Keycloak keycloak = getClient()) {
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
        } catch (Exception e) {
            LOG.error("Erro ao sincronizar roles com Keycloak no startup", e);
        }
    }

    public UUID criarUsuario(Cliente cliente, String senha) {
        try (Keycloak keycloak = getClient()) {
            UserRepresentation user = getUserRepresentation(cliente);

            try (Response response = keycloak.realm(targetRealm).users().create(user)) {
                if (response.getStatus() == 201) {
                    String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

                    // Definir senha separadamente
                    CredentialRepresentation credential = new CredentialRepresentation();
                    credential.setType(CredentialRepresentation.PASSWORD);
                    credential.setValue(senha);
                    credential.setTemporary(false);

                    keycloak.realm(targetRealm).users().get(userId).resetPassword(credential);

                    atribuirRole(keycloak, userId);
                    LOG.infof("Cliente criado com sucesso no Keycloak: %s", cliente.getEmail());

                    return UUID.fromString(userId);

                }
                else if (response.getStatus() == 409) {
                    LOG.infof("Cliente %s já existe. Adicionando perfil de cliente...", cliente.getEmail());

                    UserRepresentation existingUser = keycloak.realm(targetRealm).users()
                            .search(cliente.getEmail(), true).getFirst();

                    atribuirRole(keycloak, existingUser.getId());
                    return UUID.fromString(existingUser.getId());
                }
                else {

                    String msg = "Erro ao criar usuário %s. Status: %d".formatted(cliente.getEmail(), response.getStatus());
                    LOG.error(msg);

                    throw new NotificacaoException(msg);
                }
            }
        } catch (Exception e) {
            LOG.error("Erro ao processar criação de usuário no Keycloak", e);
            throw new NotificacaoException("Erro ao processar criação de usuário no Keycloak");
        }
    }

    public void atualizarUsuario(Cliente cliente) {
        try (Keycloak keycloak = getClient()) {
            List<UserRepresentation> users = keycloak.realm(targetRealm).users().search(cliente.getEmail());

            if (!users.isEmpty()) {
                UserRepresentation user = users.getFirst();
                user.setFirstName(cliente.getNome());
                user.setLastName("-");

                if (!Objects.equals(cliente.getEmail(), user.getEmail())) {
                    user.setEmail(cliente.getEmail());
                    user.setUsername(cliente.getEmail());
                }

                user.setEmailVerified(true);
                keycloak.realm(targetRealm).users().get(user.getId()).update(user);
                LOG.infof("Usuário atualizado no Keycloak: %s", cliente.getEmail());

                keycloak.realm(targetRealm).users().get(user.getId()).update(user);
                LOG.infof("Usuário atualizado no Keycloak: %s", cliente.getEmail());
            }
        } catch (Exception e) {
            LOG.error("Erro ao atualizar usuário no Keycloak", e);
        }
    }

    public void excluirUsuario(String email) {
        try (Keycloak keycloak = getClient()) {
            List<UserRepresentation> users = keycloak.realm(targetRealm).users().search(email);
            if (!users.isEmpty()) {
                keycloak.realm(targetRealm).users().get(users.getFirst().getId()).logout();
                try (Response _ = keycloak.realm(targetRealm).users().delete(users.getFirst().getId())) {
                    LOG.infof("Usuário removido do Keycloak: %s", email);
                }
            }
        } catch (Exception e) {
            LOG.error("Erro ao excluir usuário no Keycloak", e);
        }
    }

    private static @NonNull UserRepresentation getUserRepresentation(Cliente cliente) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(cliente.getEmail());
        user.setEmail(cliente.getEmail());
        user.setFirstName(cliente.getNome());
        user.setLastName("-");
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setRequiredActions(Collections.emptyList());
        return user;
    }

    private void atribuirRole(Keycloak keycloak, String userId) {
        try {
            RoleRepresentation role = keycloak.realm(targetRealm).roles().get("ROLE_CLIENTE").toRepresentation();
            keycloak.realm(targetRealm).users().get(userId).roles().realmLevel().add(Collections.singletonList(role));
            LOG.infof("Role ROLE_CLIENTE atribuída ao usuário ID: %s", userId);
        } catch (Exception e) {
            LOG.error("Erro ao atribuir role ao usuário", e);
        }
    }
}

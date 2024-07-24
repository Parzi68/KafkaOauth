package com.example.kafkaoauth;

import jakarta.annotation.PostConstruct;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;

import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Provides a service for interacting with Keycloak, including fetching users and roles.
 * The service is initialized at startup and connects to a Keycloak server running on localhost.
 * It fetches all users and their associated roles from the "master" realm.
 */
@Service
public class KeycloakService {

    private String realm = "master";

//    @Autowired
//    private KafkaAdminService kafkaAdminService;

    private Keycloak keycloak;

    @PostConstruct
    public void init() {
        keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
                .realm("master")
                .clientId("admin-cli")
                .grantType(OAuth2Constants.PASSWORD)
                .username("admin")
                .password("admin")
                .build();
        fetchLdapUsersAndRoles();
    }
    public void fetchLdapUsersAndRoles() {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        List<UserRepresentation> users = usersResource.list();
        for (UserRepresentation user : users) {
            System.out.println("User: " + user.getUsername());

            List<RoleRepresentation> roles = usersResource.get(user.getId()).roles().realmLevel().listEffective();
            for (RoleRepresentation role : roles) {
                System.out.println("Role: " + role.getName());
//                if ("kafka-client-producer".equals(role.getName())) {
//                    kafkaAdminService.addWriteAclToRole(role.getName(), "myTopic");
//                }
            }
        }
    }
}

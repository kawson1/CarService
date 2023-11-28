package com.example.carservice.Configuration.Authentication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Car Service")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/CarService",
        callerQuery = "select password from clients where login = ?",
        groupsQuery = "select role from client__roles where id = (select id from clients where login = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthenticationConfig {
}

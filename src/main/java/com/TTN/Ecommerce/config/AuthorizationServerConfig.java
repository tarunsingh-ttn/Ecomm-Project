package com.TTN.Ecommerce.config;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.PasswordLookup;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Configuration(proxyBeanMethods = false)

public class AuthorizationServerConfig  {



    @Autowired
    private UserDetailsService userDetailsService;



    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.userDetailsService(userDetailsService)
                .formLogin(Customizer.withDefaults())

                .build();

    }




   @Bean
   public JWKSource<SecurityContext> jwkSource() {
       KeyPair keyPair = generateRsaKey();
       RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
       RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
       RSAKey rsaKey = new RSAKey.Builder(publicKey)
               .privateKey(privateKey)
               .keyID(UUID.randomUUID().toString())
               .build();
       JWKSet jwkSet = new JWKSet(rsaKey);
       return new ImmutableJWKSet<>(jwkSet);
   }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().issuer("http://localhost:8080").build();

    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registredClient = RegisteredClient.withId("couponservice")
                .clientId("user")
                .clientSecret(passwordEncoder.encode("9999"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:8080/code")

                .scope("read").scope("write")
                .tokenSettings(tokenSettings())
                .build();
        return new InMemoryRegisteredClientRepository(registredClient);

    }

    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofMinutes(30l)).build();

    }



}

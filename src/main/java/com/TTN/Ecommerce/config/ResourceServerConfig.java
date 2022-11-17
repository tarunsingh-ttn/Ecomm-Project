package com.TTN.Ecommerce.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {



        String clientId="user";


        String clientSecret="9999";

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                    .authorizeHttpRequests((authorize) -> authorize
//                            .antMatchers("/user/**").permitAll()
//                                    .antMatchers("/customer/**").hasAnyRole("CUSTOMER","ADMIN")
//                                    .antMatchers("/seller/**").hasAnyRole("SELLER","ADMIN")
//                                    .antMatchers("/admin/**").hasRole("ADMIN")

                                    .antMatchers("/api/**").permitAll()

                                    .anyRequest().authenticated()

                    ).csrf().disable()
                    .oauth2ResourceServer((oauth2) -> oauth2
                            .opaqueToken((opaque) -> opaque
                                    .introspectionUri("http://localhost:8080/oauth2/introspect")
                                    .introspectionClientCredentials(this.clientId, this.clientSecret)
                            )

                    ).formLogin(Customizer.withDefaults());
            return http.build();
        }


}

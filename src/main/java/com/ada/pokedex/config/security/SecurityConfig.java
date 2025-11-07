package com.ada.pokedex.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz

                        // 1. Endpoints Públicos (Auth, Swagger)
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // 2. Leitura Pública (GETs)
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()

                        // --- ATUALIZAÇÃO AQUI ---
                        // 3. Permissões de ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/api/pokedex/**", "/api/treinadores/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/pokedex/**", "/api/treinadores/**").hasRole("ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // <-- NOVA LINHA (Protege o AdminController)
                        // --- FIM DA ATUALIZAÇÃO ---

                        // 4. Permissões de Usuário (O resto)
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
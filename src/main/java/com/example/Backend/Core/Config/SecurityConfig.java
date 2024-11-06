package com.example.Backend.Core.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
        return http
                .csrf(csrf -> csrf.disable())  //Deshabilita CSRF para facilitar la API REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").permitAll() //Permite el acceso sin autenticación a login y registro
                        .requestMatchers("/automovil/**").permitAll()
                        .requestMatchers("/cliente/**").permitAll()
                        .requestMatchers("/contrato/**").permitAll()  //Protege las rutas de contrato
                        .requestMatchers("/empleado/**").permitAll()
                        .requestMatchers("/plan/**").permitAll()  //Protege las rutas de reporte
                        .requestMatchers("/uso/**").permitAll()
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  //Indica que no se manejarán sesiones de usario en el servidor
                .logout(logout ->logout.permitAll()) //Permite logout
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

}

package com.medcloud.challenge.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SegurancaConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic().and().csrf().disable();

        return http.build();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource) //
                .usersByUsernameQuery(
                        "SELECT u.nome, u.senha, u.enabled "
                        + "FROM usuario u "
                        + "WHERE nome = ?"
                ).authoritiesByUsernameQuery(
                        "SELECT u.nome, a.authority "
                        + "FROM authority a "
                        + "JOIN authority_usuario au "
                        + "USING(id_authority) "
                        + "JOIN usuario u "
                        + "USING(id_usuario) "
                        + "WHERE u.nome = ?"
                );
    }


    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}

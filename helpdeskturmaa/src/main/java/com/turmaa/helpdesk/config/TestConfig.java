package com.turmaa.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.turmaa.helpdesk.service.DBService;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    /** Inicia o banco H2 com dados de exemplo */
    @Bean
    public boolean instanciaDB() {
        dbService.instanciaDB();
        return true; // Bean só para disparar a carga ao subir o contexto
    }
}

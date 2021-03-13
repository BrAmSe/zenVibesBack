package com.bramse.zenvibes.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    private static final String URL = "jdbc:mysql://localhost:3306/zen_vibes";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        // Obtenemos los parametros de configuración del sistema
        Map<String, String> env = System.getenv();
        String databaseUrl = env.get("DATABASE_URL");
        String userName = env.get("DATABASE_USER");
        String password = env.get("DATABASE_PASSWORD");
        // Si están en blanco usamos los por defecto.
        if (databaseUrl == null || databaseUrl.isBlank()) {
            databaseUrl = URL;
        }
        if (userName == null || userName.isBlank()) {
            userName = USERNAME;
        }
        if (password == null || password.isBlank()) {
            password = PASSWORD;
        }
        // Configuramos el datasource.
        dataSourceBuilder.url(databaseUrl);
        dataSourceBuilder.username(userName);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
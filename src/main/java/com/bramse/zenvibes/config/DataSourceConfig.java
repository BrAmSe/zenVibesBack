package com.bramse.zenvibes.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    private static final String URL = "jdbc:mysql://localhost:3306/zen_vibes";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final Map<String, String> env = System.getenv();

    @Bean
    public DataSource getDataSource() {
        // Definimos los parametros de configuración
        String url = getDataSourceUrl();
        String user = getDataSourceUsername();
        String password = getDatasourcePassword();

        // Configuramos el Datasource
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(user);
        dataSourceBuilder.password(password);

        // Configuramos Flyway y lanzamos las migraciones
        Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
        flyway.migrate();

        // Devolvemos en datasource
        return dataSourceBuilder.build();
    }

    private String getDataSourceUrl() {
        String databaseUrl = env.get("DATABASE_URL");
        if (databaseUrl == null || databaseUrl.isBlank()) {
            databaseUrl = URL;
        }
        return databaseUrl;
    }

    private String getDataSourceUsername() {
        String username = env.get("DATABASE_USER");
        if (username == null || username.isBlank()) {
            username = USERNAME;
        }
        return username;
    }

    private String getDatasourcePassword() {
        String password = env.get("DATABASE_PASSWORD");
        if (password == null || password.isBlank()) {
            password = PASSWORD;
        }
        return password;
    }
}
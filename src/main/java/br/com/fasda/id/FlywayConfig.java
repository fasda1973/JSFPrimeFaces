package br.com.fasda.id;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.flywaydb.core.Flyway;

@WebListener
public class FlywayConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Iniciando Migração do Banco de Dados com Flyway...");
        
        try {
            Flyway flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost:3306/cursojsfprimefaces", "root", "brcd2605")
                .baselineOnMigrate(true)
                .load();

            flyway.migrate();
            
            System.out.println("Migração concluída com sucesso!");
        } catch (Exception e) {
            System.err.println("ERRO AO RODAR FLYWAY: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Nada a fazer aqui
    }
}
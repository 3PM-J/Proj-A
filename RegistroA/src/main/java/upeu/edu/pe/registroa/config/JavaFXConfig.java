package upeu.edu.pe.registroa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class JavaFXConfig {
    
    @Bean
    @Scope("prototype")
    public javafx.stage.Stage primaryStage() {
        return new javafx.stage.Stage();
    }
}
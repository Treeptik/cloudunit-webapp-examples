package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
@EnableMongoRepositories
public class Application {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/movie/**").allowedOrigins("*");
                registry.addMapping("/create").allowedOrigins("*");
                registry.addMapping("/delete/**").allowedOrigins("*");
                registry.addMapping("/update/**").allowedOrigins("*");
                registry.addMapping("/find/**").allowedOrigins("*");
                registry.addMapping("/findAll").allowedOrigins("*");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

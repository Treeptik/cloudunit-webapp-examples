import com.mongodb.Mongo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by angular5 on 12/05/16.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableMongoRepositories
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    public @Bean
    Mongo mongo() throws Exception {
        return new Mongo("dev-johndoe-xxx-mongo-2-6-1.mongo-2-6.cloud.unit");
    }

}

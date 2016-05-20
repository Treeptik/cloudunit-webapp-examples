package application;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static java.util.Collections.singletonList;

/**
 * Created by angular5 on 18/05/16.
 */
@Configuration
@EnableMongoRepositories
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoConfiguration {

    private final Logger log = LoggerFactory.getLogger(MongoConfig.class);

    private String host;
    private int port;
    private String username;
    private String database;
    private String password;

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    public String getDatabaseName() {
        return database;
    }

    @Override
    @Bean
    public Mongo mongo() {
        host = System.getenv("MONGO_HOST");
        port = Integer.parseInt(System.getenv("MONGO_PORT"));
        username = System.getenv("MONGO_USERNAME");
        database = System.getenv("MONGO_DB");
        password = System.getenv("MONGO_PWD");

        log.info("MongoDB Initialization");
        return new MongoClient(singletonList(new ServerAddress(host, port)),
                singletonList(MongoCredential.createCredential(username, database, password.toCharArray())));
    }
}

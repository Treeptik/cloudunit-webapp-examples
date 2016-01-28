package hello;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SpringBootApplication
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        try {
            String redisServer = System.getenv("CU_DATABASE_DNS_REDIS_1");
            String redisUser = System.getenv("CU_DATABASE_USER_REDIS_1");
            String redisPassword = System.getenv("CU_DATABASE_PASSWORD_REDIS_1");

            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            JedisPool pool = new JedisPool(jedisPoolConfig, redisServer);

            Jedis jedis = pool.getResource();
            jedis.auth(redisPassword);

            System.out.println("Connection to server sucessfully");
            System.out.println("Server is running: "+jedis.ping());

            jedis.lpush("greetings-list", "Redis is great");
            jedis.lpush("greetings-list", "Mongodb is... no coment");
            jedis.lpush("greetings-list", "Mysql is not a true database");
            jedis.lpush("greetings-list", "Postgre is awesome");

        } catch (Exception e){
        }

    }

}

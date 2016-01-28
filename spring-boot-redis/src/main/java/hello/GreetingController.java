package hello;

import hello.model.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class GreetingController {

    private final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    private Jedis jedis;

    @PostConstruct
    public void init() {
        String redisServer = System.getenv("CU_DATABASE_DNS_REDIS_1");
        String redisUser = System.getenv("CU_DATABASE_USER_REDIS_1");
        String redisPassword = System.getenv("CU_DATABASE_PASSWORD_REDIS_1");
        System.out.println("PostConstruct.redisServer : " + redisServer);
        System.out.println("PostConstruct.redisUser : " + redisUser);
        System.out.println("PostConstruct.redisPassword : " + redisPassword);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool pool = new JedisPool(jedisPoolConfig, redisServer);
        jedis = pool.getResource();
        jedis.auth(redisPassword);
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String greetingForm(Model model) {
        try {
            // Get the stored data and print it
            List<String> list = jedis.lrange("greetings-list", 0, -1);
            List<Greeting> greetings = list.stream().map(s -> {
                return new Greeting(s);
            }).collect(toList());
            model.addAttribute("greetings", greetings);
            model.addAttribute("greeting", new Greeting(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "greeting";
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
        try {
            jedis.lpush("greetings-list", greeting.getContent());
            List<String> list = jedis.lrange("greetings-list", 0, -1);
            List<Greeting> greetings = list.stream().map(s -> {
                return new Greeting(s);
            }).collect(toList());
            model.addAttribute("greetings", greetings);
            model.addAttribute("greeting", greeting);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "result";
    }

}
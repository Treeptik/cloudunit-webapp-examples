package fr.treeptik.examples;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private CommentDAO pizzaDAO;

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

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		List<Comment> comments = pizzaDAO.findAll();
		List<Greeting> greetings = greetings();
		model.addAttribute("comments", comments);
		model.addAttribute("greetings", greetings);
		return "index";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
		try {
			List<Comment> comments = pizzaDAO.findAll();
			if (greeting.getContent() != null && greeting.getContent().trim().length()>0) {
				jedis.lpush("greetings-list", greeting.getContent());
			}
			List<Greeting> greetings = greetings();
			model.addAttribute("comments", comments);
			model.addAttribute("greetings", greetings);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "result";
	}

	public List<Greeting> greetings() {
		List<String> list = jedis.lrange("greetings-list", 0, -1);
		List<Greeting> greetings = new ArrayList<>();
		for (String elt : list) {
			greetings.add(new Greeting(elt));
		}
		return greetings;
	}
}

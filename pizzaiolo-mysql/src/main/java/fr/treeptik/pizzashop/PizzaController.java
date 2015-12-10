package fr.treeptik.pizzashop;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class PizzaController {

	@Autowired
	private PizzaDAO pizzaDAO;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		List<Pizza> pizzas = pizzaDAO.findAll();
		model.addAttribute("pizzas", pizzas);
		model.addAttribute("envProps", listEnvProperties());
		model.addAttribute("systemProps", listSystemProperties());
		return "index";
	}

	private String listSystemProperties() {
		StringBuilder msg = new StringBuilder();
		Properties properties = System.getProperties();
		Enumeration<Object> enumeration = properties.keys();
		while (enumeration.hasMoreElements()) {
			msg.append("<tr>");
			String cle = (String) enumeration.nextElement();
			String value = properties.getProperty(cle);
			msg.append("<td>").append(cle).append("</td>");
			msg.append("<td>").append(value).append("</td>");
			msg.append("</tr>");
		}
		return msg.toString();
	}

	private String listEnvProperties() {
		StringBuilder msg = new StringBuilder();
		Map<String, String> env = System.getenv();
		Iterator<String> iter = env.keySet().iterator();
		while (iter.hasNext()) {
			msg.append("<tr>");
			String cle = (String)iter.next();
			String value = env.get(cle);
			msg.append("<td>").append(cle).append("</td>");
			msg.append("<td>").append(value).append("</td>");
			msg.append("</tr>");
		}
		return msg.toString();
	}

}

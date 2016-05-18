package com.journaldev.mongodb.listener;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@WebListener
public class MongoDBContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		MongoClient mongo = (MongoClient) sce.getServletContext()
							.getAttribute("MONGO_CLIENT");
		mongo.close();
		System.out.println("MongoClient closed successfully");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			String user = System.getenv("CU_DATABASE_USER_MONGO_1");
			String password = System.getenv("CU_DATABASE_PASSWORD_MONGO_1");
			System.out.println(user);
			System.out.println(password);
			ServletContext ctx = sce.getServletContext();

			int mongoPort = Integer.parseInt(ctx.getInitParameter("MONGODB_PORT"));
			ServerAddress serverAddress = new ServerAddress(System.getenv("CU_DATABASE_DNS_MONGO_1"), mongoPort);
			MongoCredential credential = MongoCredential.createMongoCRCredential(user, "admin", password.toCharArray());
			List<MongoCredential> credentials = new ArrayList();
			credentials.add(credential);

			MongoClient mongo = new MongoClient(serverAddress, credentials);

			System.out.println("MongoClient initialized successfully");
			sce.getServletContext().setAttribute("MONGO_CLIENT", mongo);
		} catch (UnknownHostException e) {
			throw new RuntimeException("MongoClient init failed");
		}
	}

}

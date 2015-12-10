package org.springframework.webflow.samples.booking.config;

import java.util.Collections;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="org.springframework.webflow.samples.booking")
public class DataAccessConfig {

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setJpaPropertyMap(Collections.singletonMap("hibernate.session_factory_name", "mySessionFactory"));
		return emf;
	}

	@Bean
	public DataSource dataSource() {
		
		String dnsMysql = System.getenv("CU_DATABASE_DNS_MYSQL_1");
		String databaseName = System.getenv("CU_DATABASE_NAME");
		String user = System.getenv("CU_DATABASE_USER_MYSQL_1");
		String password = System.getenv("CU_DATABASE_PASSWORD_MYSQL_1");
		
		String url = "jdbc:mysql://"+ dnsMysql +" :3306/" + databaseName +" user = " + user + " password = " + password;
		System.out.println(url);  

		DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://"+ dnsMysql +":3306/" + databaseName +"",user , password );		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		return dataSource;
	}

}

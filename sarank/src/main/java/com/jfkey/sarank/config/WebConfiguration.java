package com.jfkey.sarank.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * @author junfeng Liu
 * @time 10:23:12 AM Jan 19, 2018
 * @version v0.1.3
 * @desc this is config info
 */
@Configuration
@EnableNeo4jRepositories("com.jfkey.sarank.repository")
@EnableTransactionManagement
public class WebConfiguration {
	@Bean
	public org.neo4j.ogm.config.Configuration configuration() {
		org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
				.uri("bolt://192.168.1.195").credentials("neo4j", "admin123")
				.build();
		return configuration;
	}

	@Bean
	public SessionFactory sessionFactory() {
		return new SessionFactory(configuration(), "com.jfkey.sarank.domain");
	}

	@Bean
	public Neo4jTransactionManager transactionManager() {
		return new Neo4jTransactionManager(sessionFactory());
	}
	
}

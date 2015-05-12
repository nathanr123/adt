package com.cti.config;

import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.cti.*" })
@PropertySource("classpath:message.properties")
@Import({ SecurityConfig.class })
public class AppConfig {

	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "com.cti.model";

	@Resource
	private Environment env;

	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {

		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

		driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");

		driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/fms");

		driverManagerDataSource.setUsername("root");

		driverManagerDataSource.setPassword("cornet");

		return driverManagerDataSource;
	}
	
	@Bean
    public MessageSource messageSource() { 
        
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        
		messageSource.setBasename("message");
        
		return messageSource;
    }  

	@Bean
	public LocalSessionFactoryBean sessionFactory() {

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		sessionFactory.setDataSource(dataSource());

		sessionFactory
				.setPackagesToScan(new String[] { PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN });

		sessionFactory.setHibernateProperties(hibProperties());

		return sessionFactory;
	}

	private Properties hibProperties() {

		Properties prop = new Properties();
		
		prop.put("hibernate.format_sql", "true");
		
		prop.put("hibernate.show_sql", "false");
		
		prop.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		
		return prop;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(
			SessionFactory sessionFactory) {

		HibernateTransactionManager txManager = new HibernateTransactionManager();

		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

		viewResolver.setViewClass(JstlView.class);

		viewResolver.setPrefix("/WEB-INF/pages/");

		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

}
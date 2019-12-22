package dev.shermende.spring.intercept.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@PropertySource("/application.properties")
@ComponentScan("dev.shermende.spring.intercept")
@Configuration
@EnableInterceptor
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "dev.shermende.spring.intercept.db.repository")
public class TestConfiguration {
    private static final String ENTITIES = "dev.shermende.spring.intercept.db.entity";

    @Bean
    public Properties hibernateProperties(
            @Value("${hibernate.dialect}") String dialect,
            @Value("${hibernate.hbm2ddl.auto}") String hbm2ddl
    ) {
        final Properties properties = new Properties();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.hbm2ddl.auto", hbm2ddl);
        return properties;
    }

    @Bean
    public Properties jdbcProperties(
            @Value("${jdbc.url}") String url,
            @Value("${jdbc.driverClassName}") String driverClassName
    ) {
        final Properties properties = new Properties();
        properties.put("jdbc.url", url);
        properties.put("jdbc.driverClassName", driverClassName);
        return properties;
    }

    @Bean
    public DataSource dataSource(
            @Value("${jdbc.url}") String url,
            @Qualifier("jdbcProperties") Properties properties
    ) {
        return new DriverManagerDataSource(url, properties);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            @Qualifier("hibernateProperties") Properties hibernateProperties
    ) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(ENTITIES);
        em.setJpaProperties(hibernateProperties);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}

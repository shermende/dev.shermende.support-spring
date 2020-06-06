package dev.shermende.support.spring.configuration;


import dev.shermende.support.spring.db.entity.Payload;
import dev.shermende.support.spring.db.repository.PayloadRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackageClasses = {Payload.class})
@EnableJpaRepositories(basePackageClasses = {PayloadRepository.class})
public class TestingConfiguration {
}

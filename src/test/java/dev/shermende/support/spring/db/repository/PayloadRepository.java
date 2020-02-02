package dev.shermende.support.spring.db.repository;

import dev.shermende.support.spring.db.entity.InterceptArgumentEntity;
import dev.shermende.support.spring.db.entity.Payload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayloadRepository extends JpaRepository<Payload, Long> {

    Optional<Payload> findFirstByOrderById();

}
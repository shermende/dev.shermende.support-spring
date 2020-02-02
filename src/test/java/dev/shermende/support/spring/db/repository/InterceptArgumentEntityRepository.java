package dev.shermende.support.spring.db.repository;

import dev.shermende.support.spring.db.entity.InterceptArgumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterceptArgumentEntityRepository extends JpaRepository<InterceptArgumentEntity, Long> {

    Optional<InterceptArgumentEntity> findFirstByOrderById();

}

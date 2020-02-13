package dev.shermende.support.spring.db.repository;

import dev.shermende.support.spring.db.entity.InterceptResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterceptResultEntityRepository extends JpaRepository<InterceptResultEntity, Long> {

    Optional<InterceptResultEntity> findFirstByOrderById();

}

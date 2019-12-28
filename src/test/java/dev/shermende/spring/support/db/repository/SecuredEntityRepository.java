package dev.shermende.spring.support.db.repository;

import dev.shermende.spring.support.db.entity.SecuredEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecuredEntityRepository extends JpaRepository<SecuredEntity, Long> {

    Optional<SecuredEntity> findFirstByOrderByIdDesc();

}
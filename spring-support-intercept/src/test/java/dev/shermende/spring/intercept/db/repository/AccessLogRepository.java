package dev.shermende.spring.intercept.db.repository;

import dev.shermende.spring.intercept.db.entity.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

    Optional<AccessLog> findFirstByOrderByIdDesc();

}

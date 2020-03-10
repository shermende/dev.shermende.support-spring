package dev.shermende.support.spring.db.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterceptResultEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String interceptor;

    private String object;

}
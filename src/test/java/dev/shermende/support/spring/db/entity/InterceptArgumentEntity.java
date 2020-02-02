package dev.shermende.support.spring.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class InterceptArgumentEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String interceptor;

    private String object;

    public Long getId() {
        return id;
    }

    public InterceptArgumentEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getInterceptor() {
        return interceptor;
    }

    public InterceptArgumentEntity setInterceptor(String interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    public String getObject() {
        return object;
    }

    public InterceptArgumentEntity setObject(String object) {
        this.object = object;
        return this;
    }

}

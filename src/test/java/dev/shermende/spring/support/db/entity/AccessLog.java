package dev.shermende.spring.support.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class AccessLog {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String interceptor;

    @NotNull
    private Long viewerId;

    @NotNull
    private Long viewedId;

    @NotNull
    private String className;

    @NotNull
    private String toString;

    public Long getId() {
        return id;
    }

    public AccessLog setId(Long id) {
        this.id = id;
        return this;
    }

    public String getInterceptor() {
        return interceptor;
    }

    public AccessLog setInterceptor(String interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    public Long getViewerId() {
        return viewerId;
    }

    public AccessLog setViewerId(Long viewerId) {
        this.viewerId = viewerId;
        return this;
    }

    public Long getViewedId() {
        return viewedId;
    }

    public AccessLog setViewedId(Long viewedId) {
        this.viewedId = viewedId;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public AccessLog setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getToString() {
        return toString;
    }

    public AccessLog setToString(String toString) {
        this.toString = toString;
        return this;
    }

}

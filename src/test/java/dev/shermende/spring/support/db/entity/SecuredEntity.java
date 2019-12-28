package dev.shermende.spring.support.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SecuredEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String uuid;

    public Long getId() {
        return id;
    }

    public SecuredEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public SecuredEntity setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

}

package dev.shermende.support.spring.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Payload {

    @Id
    @GeneratedValue
    private Long id;

    private String uuid;

    public Long getId() {
        return id;
    }

    public Payload setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public Payload setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "uuid='" + uuid + '\'' +
                '}';
    }

}

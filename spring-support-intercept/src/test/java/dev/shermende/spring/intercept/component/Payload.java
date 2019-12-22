package dev.shermende.spring.intercept.component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Payload {

    @NotNull
    private Long id;

    @NotEmpty
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

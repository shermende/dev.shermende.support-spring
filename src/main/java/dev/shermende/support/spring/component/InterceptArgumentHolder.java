package dev.shermende.support.spring.component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterceptArgumentHolder {

    private Annotation annotation;

    private Object argument;

}

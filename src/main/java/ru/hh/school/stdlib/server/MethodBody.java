package ru.hh.school.stdlib.server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для серверного метода.
 * Метод наследника AnnotatedServer, помеченный этой аннотацией будет
 * вызываться при поступлении соответствующего запроса от клиента.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MethodBody {
	String value();
}

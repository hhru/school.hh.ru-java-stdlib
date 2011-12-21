package ru.hh.school.stdlib.server;

import ru.hh.school.stdlib.methods.MethodParser;

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
	/**
	 * Класс, определяющий тип запроса.
	 * @return Значение, переданное в аннотацию.
	 */
	Class<? extends MethodParser> value();
}

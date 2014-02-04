package ru.hh.school.stdlib.methods;

/**
 * Базовый интерфейс для классов, реализующих разбор запроса,
 * пришедшего на сервер.
 */
public interface MethodParser {

	/**
	 * Определяет, корректна ли строка по отношению к данному типу запроса.
	 */
	public abstract boolean tryParse(String line);

	/**
	 * Производит разбор строки запроса.
	 * @return Массив параметров запроса.
	 */
	public abstract String[] parse(String line);
}

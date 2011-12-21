package ru.hh.school.stdlib.methods;

/**
 * Базовый интерфейс для классов, реализующих разбор запроса,
 * пришедшего на сервер.
 */
public interface MethodParser {
	/**
	 * Возвращает уникальное имя (название) серверного метода.
	 * @return Имя серверного метода.
	 */
	public abstract String methodName();

	/**
	 * Определяет, корректна ли строка по отношению к данному типу запроса.
	 * @param line Строка с текстом запроса.
	 * @return True в том случае, если входная строка корректна для данного
	 * типа запроса. False в обратном случае.
	 */
	public abstract boolean tryParse(String line);

	/**
	 * Производит разбор строки запроса.
	 * @param line Строка с текстом запроса.
	 * @return Массив параметров запроса.
	 */
	public abstract String[] parse(String line);
}

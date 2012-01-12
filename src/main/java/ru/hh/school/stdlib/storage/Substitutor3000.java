package ru.hh.school.stdlib.storage;

/**
 * Интерфейс системы хранения данных.
 */
public interface Substitutor3000 {
	/**
	 * Сохраняет ключ с заданным значением.
	 */
	public void put(String key, String value);

	/**
	 * Возвращает значение ключа.
	 */
	public String get(String key);
}

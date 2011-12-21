package ru.hh.school.stdlib.storage;

/**
 * Интерфейс системы хранения данных.
 */
public interface Substitutor3000 {
	/**
	 * Сохраняет ключ с заданным значением.
	 * @param key Ключ.
	 * @param value Значение.
	 */
	public void put(String key, String value);

	/**
	 * Возвращает значение ключа.
	 * @param key Ключ.
	 * @return Значение.
	 */
	public String get(String key);
}

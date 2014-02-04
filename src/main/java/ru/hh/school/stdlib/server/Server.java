package ru.hh.school.stdlib.server;

/**
 * Самая базовая абстракция для сервера.
 */
public interface Server {
	/**
	 * Возвращает порт, к которому привязан сервер.
	 */
	public int getPort();

	/**
	 * Запускает сервер.
	 */
	public abstract void run() throws Exception;
}

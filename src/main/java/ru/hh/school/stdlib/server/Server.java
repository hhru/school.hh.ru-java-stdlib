package ru.hh.school.stdlib.server;

import java.io.IOException;

/**
 * Самая базовая абстракция для сервера.
 */
public interface Server {
	/**
	 * Возвращает порт, к которому привязан сервер.
	 * @return Номер порта.
	 */
	public int getPort();

	/**
	 * Запускает сервер.
	 * @throws IOException Генерируется при неудачном запуске сервера.
	 */
	public abstract void run() throws IOException;
}

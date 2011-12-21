package ru.hh.school.stdlib.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Реализует простой сервер с многопоточной обработкой запросов.
 */
public abstract class ThreadedServer implements Server {
	protected InetSocketAddress addr;
	protected ServerSocket serverSocket;
	private Executor executor;

	/**
	 * Создаёт экземпляр сервера с заданным адресом.
	 * @param addr Адрес сервера.
	 */
	public ThreadedServer(InetSocketAddress addr) {
		this.addr = addr;
		this.executor = Executors.newCachedThreadPool();
	}

	/**
	 * Возвращает порт, к которому привязан сервер.
	 * @return Номер порта.
	 */
	public int getPort() {
		return addr.getPort();
	}

	/**
	 * Запускает основной цикл серверной обработки.
	 * @throws IOException Генерируется при неудачном запуске сервера.
	 */
	public void run() throws IOException {
		serverSocket = new ServerSocket(this.getPort());
		System.out.println("Сервер ru.hh.school.stdlib запущен");

		while (true) {
			System.out.println("Ждём очередного запроса от пользователя...");
			this.processIncomingConnection();
		}
	}

	/**
	 * Обрабатывает поступивший от клиента запрос в отдельном потоке.
	 * @throws IOException Генерируется при неудачной обработке запроса.
	 */
	protected void processIncomingConnection() throws IOException {
		final Socket incomingSocket = serverSocket.accept();

		InetAddress client = incomingSocket.getInetAddress();
		System.out.println(String.format("Соединение с клиентом %s установлено", client.getHostName()));

		Runnable worker = new Runnable() {
			@Override
			public void run() {
				try {
					serveClient(incomingSocket);
				}
				catch (IOException e) {
					System.out.println(String.format(
						"Обработка запроса от клиента %s завершилась с ошибками",
						incomingSocket.getInetAddress().getHostName()
					));
				}
			}
		};

		executor.execute(worker);
	}

	/**
	 * Процедура обработки клиентского запроса. Запускается в отдельном потоке.
	 * @param incomingSocket Клиентский сокет.
	 * @throws IOException Генерируется при неудачной обработке запроса.
	 */
	protected abstract void serveClient(Socket incomingSocket) throws IOException;
}

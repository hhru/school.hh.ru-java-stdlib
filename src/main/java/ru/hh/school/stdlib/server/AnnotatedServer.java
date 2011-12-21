package ru.hh.school.stdlib.server;

import ru.hh.school.stdlib.methods.MethodParser;

import java.io.*;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Позволяет задавать процедуры, обрабатывающие клиентские запросы
 * как методы класса-наследника, помеченные аннотацией MethodBody.
 */
public abstract class AnnotatedServer extends ThreadedServer {
	private List<MethodParser> methodParsers = new ArrayList<MethodParser>();
	private Map<String, Method> methods = new HashMap<String, Method>();

	/**
	 * Создаёт экземпляр сервера с заданным адресом.
	 * @param addr Адрес сервера.
	 * @param implementation Класс наследника, содержащего реализацию серверных методов.
	 */
	public AnnotatedServer(InetSocketAddress addr, Class implementation) {
		super(addr);

		for (Method method : implementation.getMethods()) {
			if (method.isAnnotationPresent(MethodBody.class)) {
				MethodBody annotation = method.getAnnotation(MethodBody.class);

				try {
					MethodParser methodParser = annotation.value().newInstance();
					this.methodParsers.add(methodParser);
					this.methods.put(methodParser.methodName(), method);
				}
				catch (Exception e) {
					System.out.println("Некорректная сигнатура методов сервера.");
				}
			}
		}
	}

	/**
	 * Обрабатывает клиентский запрос.
	 * Запрос разбирается подходящей реализацией MethodParser и передаётся
	 * на обработку реализации соответствующего серверного метода.
	 * В соответствии со спецификацией протокола обмена клиент посылает запрос
	 * одной строкой, после этого можно заканчивать чтение, выдавать результат и
	 * закрывать сокет.
	 * @param incomingSocket Клиентский сокет.
	 * @throws IOException Генерируется при неудачной обработке клиентского запроса.
	 */
	protected void serveClient(Socket incomingSocket) throws IOException {
		BufferedReader inputReader = new BufferedReader(
			new InputStreamReader(incomingSocket.getInputStream()));
		String line = inputReader.readLine();
		MethodParser methodParser = null;

		for (MethodParser method : this.methodParsers) {
			if (method.tryParse(line)) {
				methodParser = method;
				break;
			}
		}

		if (methodParser != null) {
			String response = this.invokeMethod(methodParser, line);
			this.renderResponse(response, incomingSocket);
		} else {
			System.out.println("Вызван некорректный метод или метод с неверным числом параметров.");
			this.renderErrorResponse(incomingSocket);
		}

		inputReader.close();
		incomingSocket.close();
	}

	/**
	 * Вызывает реализацию серверного метода.
	 * Передаёт в эту реализацию параметры, полученные при разборе строки запроса.
	 * @param method MethodParser, разбирающий строку запроса.
	 * @param line Строка запроса, полученная от клиента.
	 * @return Ответ, полученный при выполнении метода.
	 */
	protected String invokeMethod(MethodParser method, String line) {
		try {
			Method m = this.methods.get(method.methodName());
			return (String)(m.invoke(this, method.parse(line)));
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 * Отправляет ответ сервера пользователю.
	 * @param response Строка ответа.
	 * @param incomingSocket Клиентский сокет.
	 * @throws IOException Генерируется при неудачной отправке ответа.
	 */
	protected void renderResponse(String response, Socket incomingSocket) throws IOException {
		BufferedWriter outputWriter = new BufferedWriter(
			new OutputStreamWriter(incomingSocket.getOutputStream()));

		outputWriter.write(response);
		outputWriter.close();
	}

	/**
	 * Отправляет ответ с сообщением об ошибке.
	 * @param incomingSocket Клиентский сокет.
	 * @throws IOException Генерируется при неудачной отправке ответа.
	 */
	protected void renderErrorResponse(Socket incomingSocket) throws IOException {
		this.renderResponse("ERROR\n", incomingSocket);
	}
}

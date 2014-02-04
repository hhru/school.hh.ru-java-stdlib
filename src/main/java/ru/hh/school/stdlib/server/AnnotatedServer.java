package ru.hh.school.stdlib.server;

import ru.hh.school.stdlib.methods.MethodParser;
import ru.hh.school.stdlib.methods.RegexpMethodParser;

import java.io.*;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Позволяет задавать процедуры, обрабатывающие клиентские запросы
 * как методы класса-наследника, помеченные аннотацией MethodBody.
 */
public abstract class AnnotatedServer extends ThreadedServer {
	private Map<Method, MethodParser> methods = new HashMap<Method, MethodParser>();

	public AnnotatedServer(InetSocketAddress addr, Class implementation) {
		super(addr);

		for (Method method : implementation.getMethods()) {
			if (method.isAnnotationPresent(MethodBody.class)) {
				MethodBody annotation = method.getAnnotation(MethodBody.class);
				MethodParser methodParser = new RegexpMethodParser(annotation.value());
				this.methods.put(method, methodParser);
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
	 */
	protected void serveClient(Socket incomingSocket) throws Exception {
		BufferedReader inputReader = new BufferedReader(
			new InputStreamReader(incomingSocket.getInputStream()));
		String request = inputReader.readLine();
		MethodParser methodParser = null;
		Method method = null;

		for (Map.Entry<Method, MethodParser> entry : this.methods.entrySet()) {
			if (entry.getValue().tryParse(request)) {
				methodParser = entry.getValue();
				method = entry.getKey();
				break;
			}
		}

		if (methodParser != null) {
			String response = (String)(method.invoke(this, methodParser.parse(request)));
			this.renderResponse(response, incomingSocket);
		} else {
			System.out.println("Вызван некорректный метод или метод с неверным числом параметров.");
			this.renderErrorResponse(incomingSocket);
		}

		inputReader.close();
		incomingSocket.close();
	}

	protected void renderResponse(String response, Socket incomingSocket) throws IOException {
		BufferedWriter outputWriter = new BufferedWriter(
			new OutputStreamWriter(incomingSocket.getOutputStream()));

		outputWriter.write(response);
		outputWriter.close();
	}

	protected void renderErrorResponse(Socket incomingSocket) throws IOException {
		this.renderResponse("ERROR\n", incomingSocket);
	}
}

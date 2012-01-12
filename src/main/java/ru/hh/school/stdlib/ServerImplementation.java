package ru.hh.school.stdlib;

import ru.hh.school.stdlib.server.AnnotatedServer;
import ru.hh.school.stdlib.server.MethodBody;
import ru.hh.school.stdlib.storage.KeyValueSubstitutor;
import ru.hh.school.stdlib.storage.Substitutor3000;

import java.net.InetSocketAddress;

public class ServerImplementation extends AnnotatedServer {
	private Substitutor3000 storage = new KeyValueSubstitutor();
	private Long timeout = 0L;

	public ServerImplementation(InetSocketAddress addr) {
		super(addr, ServerImplementation.class);
	}

	@MethodBody("GET\\s+([A-Za-z_\\$]+[A-Za-z_\\$0-9]*)")
	public String getMethod(String key) {
		this.sleep();
		String value = "";

		synchronized (this) {
			value = this.storage.get(key);
		}

		return String.format("VALUE\n%s\n", value);
	}

	@MethodBody("PUT\\s+([A-Za-z_\\$]+[A-Za-z_\\$0-9]*)\\s(.*)")
	public String putMethod(String key, String value) {
		this.sleep();

		synchronized (this) {
			this.storage.put(key, value);
		}

		return "OK\n";
	}
	
	@MethodBody("SET\\s+SLEEP\\s+(\\d+)")
	public String setSleepMethod(String value) {
		this.sleep();

		synchronized (this) {
			this.timeout = Long.parseLong(value);
		}

		return "OK\n";
	}

	protected void sleep() {
		try {
			Long timeout;
			synchronized (this) {
				timeout = this.timeout;
			}
			
			if (timeout > 0)
				Thread.sleep(timeout);
		}
		catch (InterruptedException e) {
			System.out.println("Ошибка: поток прерван.");
		}
	}
}

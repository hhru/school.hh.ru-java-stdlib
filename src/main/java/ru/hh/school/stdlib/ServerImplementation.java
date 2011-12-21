package ru.hh.school.stdlib;

import ru.hh.school.stdlib.methods.GetMethod;
import ru.hh.school.stdlib.methods.PutMethod;
import ru.hh.school.stdlib.methods.SetSleepMethod;
import ru.hh.school.stdlib.server.AnnotatedServer;
import ru.hh.school.stdlib.server.MethodBody;
import ru.hh.school.stdlib.storage.KeyValueSubstitutor;
import ru.hh.school.stdlib.storage.Substitutor3000;

import java.net.InetSocketAddress;

public class ServerImplementation extends AnnotatedServer {
	private Substitutor3000 storage = new KeyValueSubstitutor();
	private long timeout = 0;

	public ServerImplementation(InetSocketAddress addr) {
		super(addr, ServerImplementation.class);
	}

	@MethodBody(GetMethod.class)
	public String getMethod(String key) {
		this.sleep();
		String value = "";

		synchronized (this) {
			value = this.storage.get(key);
		}

		return String.format("VALUE\n%s\n", value);
	}

	@MethodBody(PutMethod.class)
	public String putMethod(String key, String value) {
		this.sleep();

		synchronized (this) {
			this.storage.put(key, value);
		}

		return "OK\n";
	}
	
	@MethodBody(SetSleepMethod.class)
	public String setSleepMethod(String value) {
		this.sleep();

		synchronized (this) {
			this.timeout = Long.parseLong(value);
		}

		return "OK\n";
	}

	protected void sleep() {
		try {
			if (this.timeout > 0)
				Thread.sleep(this.timeout);
		}
		catch (InterruptedException e) {
			System.out.println("Ошибка: поток прерван.");
		}
	}
}

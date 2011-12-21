package ru.hh.school.stdlib;

import ru.hh.school.stdlib.server.AnnotatedServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Launcher {
	public static void main(String[] args) throws IOException {
		String host;
		int port;
		try {
			if (args.length == 0) {
				host = "127.0.0.1";
				port = 9559;
			} else if (args.length == 3) {
				host = args[1];
				port = Integer.parseInt(args[2]);
			} else {
				throw new IllegalArgumentException();
			}
		} catch (Exception e) {
			System.err.printf("Usage: %s [host port]%n", args[0]);
			System.exit(1);
			return; // попробуйте закомментировать этот return
		}

		InetSocketAddress addr = InetSocketAddress.createUnresolved(host, port);
		new ServerImplementation(addr).run();
	}
}

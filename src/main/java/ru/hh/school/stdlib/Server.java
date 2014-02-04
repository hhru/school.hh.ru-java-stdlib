package ru.hh.school.stdlib;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket s;
    private Substitutor3000 sbst;
    private Long sleepTime;

    public Server(InetSocketAddress addr) {
        try {
            s = new ServerSocket(addr.getPort());
            sbst = new Substitutor3000();
            sleepTime = 0L;
        } catch (Exception e) {
            System.err.print("ERROR ");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void run() throws IOException {
        System.out.printf("Server started on port %d%n", this.getPort());
        ExecutorService exec = Executors.newCachedThreadPool();
        while (true) {
            Socket incoming = s.accept();
            exec.execute(new ClientHandler(incoming, this, sbst));
        }
    }

    public int getPort() {
        return s.getLocalPort();
    }

    public synchronized Long getSleepTime() {
        return sleepTime;
    }

    public synchronized void setSleepTime(Long sleepTime) {
        this.sleepTime = sleepTime;
    }
}

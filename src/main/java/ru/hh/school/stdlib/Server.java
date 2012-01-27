package ru.hh.school.stdlib;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Server implements Runnable {
    private int serverPort = 4444;
    private ServerSocket serverSocket = null;
    private Substitutor3000 substitutor;
    private int sleepTime = 0;

    public Server(InetSocketAddress addr) {
        this.serverPort = addr.getPort();
        substitutor = new Substitutor3000();
    }

    public void run() {
        openServerSocket();
        ExecutorService exec = Executors.newCachedThreadPool();
        while(true){
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException("Error accepting client connection", e);
            }
            exec.execute(new ServerThread(clientSocket, substitutor, this));
        }
    }

    public int getPort() {
        return serverPort;
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }

    public int getSleepTime() {
        synchronized (this) {
            return sleepTime;
        }
    }

    public void setSleepTime(int sleepTime) {
        synchronized (this) {
            this.sleepTime = sleepTime;
        }
    }
}
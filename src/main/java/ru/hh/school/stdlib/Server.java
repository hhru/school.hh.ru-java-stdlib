package ru.hh.school.stdlib;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private int serverPort = 4444;
    private ServerSocket serverSocket = null;
    private Substitutor3000 substitutor;

    public Server(InetSocketAddress addr) {
        this.serverPort = addr.getPort();
        substitutor = new Substitutor3000();
    }

    public void run() {
        openServerSocket();
        while(true){
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException("Error accepting client connection", e);
            }
            new Thread(new ServerThread(clientSocket, substitutor)).start();
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
}
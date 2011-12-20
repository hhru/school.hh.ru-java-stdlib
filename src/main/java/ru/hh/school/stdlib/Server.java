package ru.hh.school.stdlib;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private int serverPort = 4444;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;

    public static Substitutor3000 substitutor;

    public Server(InetSocketAddress addr) {
        this.serverPort = addr.getPort();
        substitutor = new Substitutor3000();
    }

    public void run() {

        openServerSocket();
        while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            new Thread(
                    new ServerThread(clientSocket)
            ).start();
        }
        System.out.println("Server Stopped.") ;
    }

    public int getPort() {
        return serverPort;
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
}